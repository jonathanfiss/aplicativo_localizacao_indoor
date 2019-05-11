package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aplicativo_localizacao_indoor.R;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Button btCadastrar = findViewById(R.id.btCadastrar);
        Button btListar = findViewById(R.id.btListar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AdminCadastrarActivity.class));
            }
        });
        btListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminMainActivity.this, AdminListaPontoReferenciaActivity.class));
            }
        });
    }
}
