package br.com.amssolucoes.controlefinanceiro;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.UUID;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    TextView txtNovaSenha;
    TextView txtCadastro;
    EditText edtUsuario;
    EditText edtSenha;
    CheckBox cbxSalvarSenha;
    Button btnEntrar;
    String usuario;
    String senha;
    Boolean salvarSenha;
    UsuarioCls usuarioCls;

    private void inicializaComponentes(){
        edtUsuario = (EditText) findViewById(R.id.EdtUsuario);
        edtSenha = (EditText) findViewById(R.id.EdtSenha);
        cbxSalvarSenha = (CheckBox) findViewById(R.id.CbxSalvarSenha);
        btnEntrar = (Button) findViewById(R.id.BtnEntrar);
        txtNovaSenha = (TextView) findViewById(R.id.LblNovaSenha);
        txtCadastro = (TextView) findViewById(R.id.LblCadastro);
        btnEntrar.setOnClickListener(LoginActivity.this);

        txtNovaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtUsuario.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(LoginActivity.this, R.string.campos_obrigatorios_email,
                        Toast.LENGTH_SHORT).show();
                }else{
                    if(usuarioCls.emailExistente(email)){
                        String novaSenha = geraSenhaAleatoria();
                        if(usuarioCls.updateSenha(email, novaSenha)){
                            if(enviaSenhaParaEmail(email, novaSenha)){
                                Toast.makeText(LoginActivity.this, R.string.email_enviado,
                                    Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LoginActivity.this,
                                    R.string.email_nao_enviado + ". " + R.string.verifique_conexao,
                                    Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(LoginActivity.this, R.string.erro_update,
                                Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, R.string.email_inexistente,
                            Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, R.string.activity_erroAcesso,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usuarioCls = new UsuarioCls(this);
        usuarioCls.open();

        inicializaComponentes();
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
        salvarSenha = cbxSalvarSenha.isChecked();

        if(usuario.isEmpty() || senha.isEmpty()){
            Toast.makeText(LoginActivity.this, R.string.campos_obrigatorios,
                Toast.LENGTH_SHORT).show();

        }else {
            if (checaLogin(usuario, senha, salvarSenha)) {
                try {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, R.string.activity_erroAcesso,
                        Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, R.string.login_invalido,
                    Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checaLogin(String AsUsuario, String AsSenha, boolean AbSalvarSenha){
        return usuarioCls.LoginCorreto(AsUsuario, AsSenha, AbSalvarSenha);
    }

    @NonNull
    private String geraSenhaAleatoria(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 6);
    }

    //!!! Implementar
    private boolean enviaSenhaParaEmail(String AsEmail, String AsNovaSenha){

        return true;
    }
}