package br.com.amssolucoes.controlefinanceiro;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class UsuarioCls {
    Context context;
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public UsuarioCls(Context AcContext){
        this.context = AcContext;
    }

    public void open() throws SQLException {
        dbHelper = new SQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
        database.close();
    }

    public boolean LoginCorreto(String AsUsuario, String AsSenha, boolean AbSalvarSenha){
        boolean resultado = false;

        /*try {
            ContentValues values = new ContentValues();
            values.put(SQLiteHelper.EMAIL, AsUsuario);
            values.put(SQLiteHelper.SENHA, AsSenha);
            values.put(SQLiteHelper.SALVARSENHA, AbSalvarSenha ? 1 : 0);
            database.insert(SQLiteHelper.TABLE_USUARIO, null, values);
        }catch(Exception e){
            System.out.println(e);
        }*/

        try {
            Cursor cursor = database.query(SQLiteHelper.TABLE_USUARIO, new String[]
                {SQLiteHelper.KEY_CODIGO, SQLiteHelper.EMAIL, SQLiteHelper.SENHA,
                    SQLiteHelper.SALVARSENHA, SQLiteHelper.APELIDO},
                SQLiteHelper.EMAIL + "=? AND " + SQLiteHelper.SENHA + "=?",
                new String[]{AsUsuario, AsSenha}, null, null, null);
            
            if ((cursor!=null) && (cursor.getCount() > 0)) {
                cursor.moveToFirst();

                String apelido = "";
                if (cursor.getString(3) != null) {
                    apelido = cursor.getString(3);
                }

                int salvarSenha = AbSalvarSenha ? 1 : 0;
                /*if(cursor.getInt(2) != salvarSenha){
                String sqlUpdate = "update usuario set salvarSenha = " + salvarSenha +
                    "where email = " + AsUsuario;
                database.rawQuery(sqlUpdate, null);
                }*/

                // Salva o codigo e apelido do usuário nas variáveis globais
                sharedPreferences = context.getSharedPreferences("MyPref", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("CodUsuario", cursor.getString(0));
                editor.putString("Apelido", apelido);
                resultado = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return resultado;
    }

}
