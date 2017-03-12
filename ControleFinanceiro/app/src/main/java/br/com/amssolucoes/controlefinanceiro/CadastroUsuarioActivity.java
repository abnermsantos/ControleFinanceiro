package br.com.amssolucoes.controlefinanceiro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastroUsuarioActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edtUsuario;
    EditText edtSenha;
    EditText edtApelido;
    Button btnCadastrar;
    String usuario;
    String senha;
    String apelido;
    UsuarioCls usuarioCls;

    private void inicializaComponentes(){
        edtUsuario = (EditText) findViewById(R.id.EdtCadUsuario);
        edtSenha = (EditText) findViewById(R.id.EdtCadSenha);
        edtApelido = (EditText) findViewById(R.id.EdtCadApelido);
        btnCadastrar = (Button) findViewById(R.id.BtnCadUsuario);
        btnCadastrar.setOnClickListener(CadastroUsuarioActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        inicializaComponentes();

        usuarioCls = new UsuarioCls(this);
        usuarioCls.open();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDelegate().onDestroy();
        usuarioCls.close();
    }

    @Override
    public void onClick(View v) {
        usuario = edtUsuario.getText().toString();
        senha = edtSenha.getText().toString();
        apelido = edtApelido.getText().toString();

        if(usuario.isEmpty() || senha.isEmpty()){
            Toast.makeText(CadastroUsuarioActivity.this, R.string.campos_obrigatorios,
                Toast.LENGTH_SHORT).show();
        }else{
            if(validaEmail(usuario)){
                if(!usuarioCls.emailExistente(usuario)){
                    boolean usuarioInserido = usuarioCls.insereUsuario(usuario, senha, apelido);
                    if(usuarioInserido){
                        Toast.makeText(CadastroUsuarioActivity.this, R.string.cadastro_sucesso,
                            Toast.LENGTH_SHORT).show();
                        try {
                            Intent intent = new Intent(CadastroUsuarioActivity.this, MainActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(CadastroUsuarioActivity.this, R.string.activity_erroAcesso,
                                Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CadastroUsuarioActivity.this, R.string.usuario_nao_inserido,
                            Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroUsuarioActivity.this, R.string.usuario_existente,
                        Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(CadastroUsuarioActivity.this, R.string.email_invalido,
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validaEmail(String AsEmail){
        boolean resultado = false;
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(AsEmail);
        boolean matchFound = m.matches();

        if (matchFound) {
            resultado = true;
        }
        return resultado;
    }
}
