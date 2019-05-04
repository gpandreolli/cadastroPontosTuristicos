package com.example.user.trabalho1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.trabalho1.entidade.PontosTuristicos;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PontosTuristicosActivity extends AppCompatActivity implements LocationListener {

    private Button btIncluirPontosTuristicos;
    private FormularioHelper helper;
    private DatabaseHandler pontosTuristicosDatabase;
    private ImageView ivFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontos_turisticos);


        btIncluirPontosTuristicos = (Button) findViewById(R.id.btIncluirDisciplina);
        helper = new FormularioHelper(this);
        ivFoto = findViewById(R.id.ivFoto);

        Intent intent = getIntent();
        PontosTuristicos pontosTuristicos = (PontosTuristicos) intent.getSerializableExtra("pontoTuristico");

        //   LocationListener para recuperação dos dados do GPS
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (pontosTuristicos != null) {
            helper.preecheFormulario(pontosTuristicos);
        } else {
            //        é solicitada a verificação prévia
            //        da permissão no arquivo de manifest.xml
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 0, this);



        }





        //verifica se o gps do celular está ligado, caso não esteja é mostrada uma mensagem
        //e manda para a tela de habilitar o gps
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this,"Habilitar GPS", Toast.LENGTH_LONG).show();
            Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);
        }



    }



    public void btIncluirPontosTuristicos(View view) {

        if(helper.etNome.getText().toString().equals("")){
            helper.etNome.setError("Campo [nome] deve ser preenchido.");
            helper.etNome.requestFocus();
            return;
        }
        if (helper.ivFoto.getDrawable() == null){
            Toast.makeText(this,"Insira uma imagem!",Toast.LENGTH_LONG).show();
            return;

        }
        else {
            PontosTuristicos pontosTuristicos = helper.pegaPontoTuristico();
            pontosTuristicosDatabase = new DatabaseHandler(this);

            if (pontosTuristicos.getCodPontoTuristico() != 0) {
                pontosTuristicosDatabase.altera(pontosTuristicos);
                Toast.makeText(this, "EDIÇÃO REALIZADA COM SUCESSO!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                pontosTuristicosDatabase.incluirPontosTuristicos(pontosTuristicos);
                Toast.makeText(this, "INCLUSÃO REALIZADA COM SUCESSO!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        helper.tvLatitude.setText(String.valueOf(latitude));
        helper.tvLongitude.setText(String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    //Botão Tirar foto
    public void btTirarFotoOnclik(View view) {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//intent onde ira abrir camera
        startActivityForResult(intent, 1000);// abre a intent da camera
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            FileInputStream fis = null;

            try {
                Bitmap foto = (Bitmap) data.getExtras().get("data");
                ivFoto.setImageBitmap(foto);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                foto.compress(Bitmap.CompressFormat.PNG, 90, baos);
                byte[] b = baos.toByteArray();

               helper.imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

            } catch (Exception e) {
                Toast.makeText( this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG ).show();
            }
        }

    }
}

