package com.example.user.trabalho1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.trabalho1.entidade.PontosTuristicos;

import java.io.File;


public class FormularioHelper {

    public  EditText etNome;
    private final EditText etDescricao;
    public TextView tvLatitude;
    public TextView tvLongitude;
    public ImageView ivFoto;
    public  String imageEncoded;


    private PontosTuristicos pontoTuristico;

    public FormularioHelper(PontosTuristicosActivity activity) {
        etNome = (EditText) activity.findViewById(R.id.etNome);
        etDescricao = (EditText) activity.findViewById(R.id.etDescricao);
        tvLatitude = (TextView) activity.findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) activity.findViewById(R.id.tvLongitude);
        ivFoto = (ImageView) activity.findViewById(R.id.ivFoto);
        pontoTuristico = new PontosTuristicos();

    }

    public PontosTuristicos pegaPontoTuristico() {
        pontoTuristico.setNome(etNome.getText().toString());
        pontoTuristico.setDescricao(etDescricao.getText().toString());
        pontoTuristico.setLatitude(Double.parseDouble((String) tvLatitude.getText()));
        pontoTuristico.setLongitude(Double.parseDouble((String) tvLongitude.getText()));
        pontoTuristico.setFoto(imageEncoded);
        return pontoTuristico;
    }

    public void preecheFormulario(PontosTuristicos pontoTuristico) {
        etNome.setText(pontoTuristico.getNome());
        etDescricao.setText(pontoTuristico.getDescricao());
        tvLatitude.setText(pontoTuristico.getLatitude().toString());
        tvLongitude.setText(pontoTuristico.getLongitude().toString());

        byte[] decodedByte = Base64.decode(pontoTuristico.getFoto(), 0);
        Bitmap fotoRecuperada = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);

        ivFoto.setImageBitmap(fotoRecuperada);

        //guardando o aluno no atributo que acabou de ser criado, dessa forma guarda-se o id do aluno junto
        this.pontoTuristico = pontoTuristico;
    }
}
