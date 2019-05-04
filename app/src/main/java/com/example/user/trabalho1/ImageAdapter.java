package com.example.user.trabalho1;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.trabalho1.entidade.PontosTuristicos;

import org.w3c.dom.Text;

import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    //private Cursor cursor;
    //private DatabaseHandler db;
    private LayoutInflater inflater;
    private List<PontosTuristicos>  lista ;

    public ImageAdapter (Context context,List<PontosTuristicos>  lista ){
        this.context = context;
        //this.cursor = cursor;
        //this.db = db;
        this.lista = lista;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = inflater.inflate(R.layout.imagelayout, null);
        TextView tvNomeLista = (TextView) v.findViewById(R.id.tvNomeLista);
        ImageView imgLista = (ImageView) v.findViewById(R.id.imgLista);
        //List<PontosTuristicos>  lista = db.buscaPontosTuristicos();
        PontosTuristicos pontosTuristicos = lista.get(position);
        tvNomeLista.setText(pontosTuristicos.getNome());

        //desconverte a imagem
        byte[] decodedByte = Base64.decode(pontosTuristicos.getFoto(), 0);
        Bitmap fotoRecuperada = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        imgLista.setImageBitmap(fotoRecuperada);


        return  v;
    }
}
