package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.aplicativo_localizacao_indoor.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btLocalizar = findViewById(R.id.btLocalizar);


        btLocalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LocalizarActivity.class));
            }
        });

        public boolean onCreateOptionsMenu(Menu menu){
            getMenuInflater().inflate(R.menu.menu_activity_admin, menu);
            return true;
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item)
            switch (item.getItemId()) {
                case R.id.menuitem_adm: {
                    confirmaSalvar();
                    break;
                }
            }
            return true;
        }
    }
}
