package br.com.amssolucoes.controlefinanceiro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    TextView txtNovaSenha;
    EditText edtUsuario;
    EditText edtSenha;
    CheckBox cbxSalvarSenha;
    Button btnEntrar;
    String usuario;
    String senha;
    Boolean salvarSenha;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private void inicializaComponentes(){
        edtUsuario = (EditText) findViewById(R.id.EdtUsuario);
        edtSenha = (EditText) findViewById(R.id.EdtSenha);
        cbxSalvarSenha = (CheckBox) findViewById(R.id.CbxSalvarSenha);
        btnEntrar = (Button) findViewById(R.id.BtnEntrar);
        txtNovaSenha = (TextView) findViewById(R.id.LblNovaSenha);
        btnEntrar.setOnClickListener(LoginActivity.this);

        txtNovaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //!!! Chamar tela para confirmar o email
                //!!! Enviar nova senha para o email
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inicializaComponentes();
    }

    @Override
    public void onClick(View v) {
        usuario = edtUsuario.getText().toString();
        senha = edtSenha.getText().toString();
        salvarSenha = cbxSalvarSenha.isChecked();

        if(checaLogin(usuario, senha)){
            try{
                Intent cadastroIntent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(cadastroIntent);
            }catch (Exception e){
                Toast.makeText(LoginActivity.this, R.string.activity_erroAcesso,
                        Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LoginActivity.this, R.string.login_invalido,
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checaLogin(String AsUsuario, String AsSenha){
        boolean resultado = false;
        //Verifica os dados no BD
        //Se existe, salva o nome do usuário nas variáveis globais
        // !!! Colocar o código do usuário nas preferencias compartilhadas para facilitar a busca
        if (loginCorreto(AsUsuario, AsSenha)){
            sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("Usuario", AsUsuario);

            resultado = true;
        }
        return resultado;
    }

    private boolean loginCorreto(String AsUsuario, String AsSenha){
        boolean resultado = false;
        try {
            //Enquanto o banco não está implementado, testamos via hardcode
            if(AsUsuario.equals("usuario") && AsSenha.equals("senha")){
                resultado = true;
            }
        }catch (Exception e){
            Toast.makeText(LoginActivity.this, R.string.bd_erroAcesso,
                    Toast.LENGTH_SHORT).show();
        }
        return resultado;
    }

}