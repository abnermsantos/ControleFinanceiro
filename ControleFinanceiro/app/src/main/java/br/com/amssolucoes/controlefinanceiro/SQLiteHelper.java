package br.com.amssolucoes.controlefinanceiro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "controleFinanceiro.db";
    public static final int DATABASE_VERSION = 1;

    //Tabela Usuário
    public static final String TABLE_USUARIO = "usuario";
    public static final String KEY_CODIGO = "codigo";
    public static final String EMAIL = "email";
    public static final String SENHA = "senha";
    public static final String SALVARSENHA = "salvarSenha";
    public static final String APELIDO = "apelido";


    private static final String CREATE_TABLE_USUARIO =
        "CREATE TABLE "+ TABLE_USUARIO +"("+ KEY_CODIGO  + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + EMAIL +" TEXT NOT NULL, " + SENHA +" TEXT NOT NULL, " + APELIDO + " TEXT, "
            + SALVARSENHA + " INTEGER NOT NULL);";

    /*public static final String CREATE_TABLE_USUARIO =
        "create table usuario("+
            "codigo integer primary key autoincrement,"+
            "email text not null,"+
            "senha text not null,"+
            "salvarSenha integer);";*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}