package com.example.user.trabalho1;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.user.trabalho1.entidade.PontosTuristicos;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseHandler pontosTuristicosDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        pontosTuristicosDatabase = new DatabaseHandler(this);
        List<PontosTuristicos> pontosTuristicos = pontosTuristicosDatabase.buscaPontosTuristicos();
        LatLng lastPosition = null;
        for (PontosTuristicos pontoTuristico : pontosTuristicos) {
            lastPosition = new LatLng(pontoTuristico.getLatitude(), pontoTuristico.getLongitude());
            mMap.addMarker(new MarkerOptions().position(lastPosition).title(pontoTuristico.getNome()));
        }
        if (lastPosition != null)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPosition, 7));
    }
}
