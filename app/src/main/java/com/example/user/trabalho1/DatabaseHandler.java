package com.example.user.trabalho1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.example.user.trabalho1.entidade.PontosTuristicos;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler  extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "banco5";

    public DatabaseHandler(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL("CREATE TABLE IF NOT EXISTS pontosTuristicos(_id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, descricao TEXT, latitude REAL, longitude REAL, foto TEXT)" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(bd);
    }
    //método para transformar a imagem em um ByteArray, para que possa ser inserido no banco como BLOB
    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap =  ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void incluirPontosTuristicos(PontosTuristicos pontosTuristicos){

        SQLiteDatabase bd = getWritableDatabase();

        ContentValues registro = new ContentValues();
        //na coluna nome coloca o nome informado no EditText de nome do formulario
        registro.put("nome", pontosTuristicos.getNome());
        registro.put("descricao", pontosTuristicos.getDescricao());
        registro.put("latitude", pontosTuristicos.getLatitude());
        registro.put("longitude", pontosTuristicos.getLongitude());
        registro.put("foto", pontosTuristicos.getFoto());//salva a imagem como BLOB utilizando o metodo que transforma imagem em byteArray


        //o insert vai olhar para o Content Values e vai tratar o conteúdo "pontosTuristicos.getNome()"
        //antes de montar a query sql que estamos querendo executar , evitando dessa forma que sejam
        //executados comandos "sql injection"
        bd.insert("pontosTuristicos", null, registro);
    }

    public List<PontosTuristicos> buscaPontosTuristicos() {

        String sql = "SELECT * FROM pontosTuristicos";
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(sql,null);

        List<PontosTuristicos> pontosTuristicos = new ArrayList<PontosTuristicos>();
        while (  c.moveToNext()){
            //cada linha do resultado representa um ponto turistico
            PontosTuristicos pT = new PontosTuristicos();
            pT.setCodPontoTuristico(c.getInt(c.getColumnIndex("_id")));
            pT.setNome(c.getString(c.getColumnIndex("nome")));
            pT.setDescricao(c.getString(c.getColumnIndex("descricao")));
            pT.setLatitude(c.getDouble(c.getColumnIndex("latitude")));
            pT.setLongitude(c.getDouble(c.getColumnIndex("longitude")));
            pT.setFoto(c.getString(c.getColumnIndex("foto")));

            pontosTuristicos.add(pT);
        }
        //avisa o android que terminou a consulta e desta forma pode liberar a memória
        c.close();
        return pontosTuristicos;
    }

    public void deleta(PontosTuristicos pontosTuristicos){

        SQLiteDatabase database = getReadableDatabase();

        String[] params = {String.valueOf(pontosTuristicos.getCodPontoTuristico())};
        database.delete("pontosTuristicos", "_id = ?", params);
    }

    public void altera(PontosTuristicos pontosTuristicos) {
        SQLiteDatabase database = getReadableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nome", pontosTuristicos.getNome());
        registro.put("descricao", pontosTuristicos.getDescricao());

        String[] params = {String.valueOf(pontosTuristicos.getCodPontoTuristico())};
        database.update("pontosTuristicos", registro, "_id = ?", params);
    }

    public Cursor findAllAsCursor(){
        String sql = "SELECT * FROM pontosTuristicos";
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(sql,null);
        c.moveToFirst();
        return c;
    }
}
