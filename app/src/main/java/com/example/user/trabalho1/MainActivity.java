package com.example.user.trabalho1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private ListView lvMenu;
    private final  Class<?>[] menus = {
            PontosTuristicosActivity.class,
            ListActivity.class,
            MapaActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvMenu = (ListView) findViewById(R.id.lvMenu);

        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos < menus.length) {
                    Intent i = new Intent(MainActivity.this, menus[pos]);
                    startActivity(i);
                }
            }
        });
    }


}
