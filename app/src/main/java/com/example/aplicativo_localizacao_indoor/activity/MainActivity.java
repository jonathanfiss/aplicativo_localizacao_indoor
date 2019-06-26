package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
<<<<<<< HEAD
import android.view.MenuInflater;
import android.view.View;
=======
import android.view.View;
import android.widget.Button;
>>>>>>> dev
import android.view.Menu;
import android.view.MenuItem;

import com.example.aplicativo_localizacao_indoor.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

<<<<<<< HEAD
        findViewById(R.id.btBuscarRota).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BuscarRotaActivity.class));
            }
        });

        findViewById(R.id.btLocalizar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocalizarActivity.class));
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.mn_login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
=======
        Button btLocalizar = findViewById(R.id.btLocalizar);
        Button btBuscarRota = findViewById(R.id.btRota);

        btLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocalizarActivity.class));
            }
        });
        btBuscarRota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RotaActivity.class));
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuitem_adm:
                startActivity(new Intent(MainActivity.this, LoginActivity.class ));
//                startActivity(new Intent(MainActivity.this, AdminMainActivity.class ));
                break;
>>>>>>> dev
        }
        return true;
    }
}
