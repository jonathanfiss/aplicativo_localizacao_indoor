package com.example.aplicativo_localizacao_indoor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.PontoReferenciaAdapter;

public class AdminPontoReferenciaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ponto_referencia);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ListView lvPontoRef = findViewById(R.id.lv_ponto_ref);
        lvPontoRef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
//        lvPontoRef.setAdapter(new PontoReferenciaAdapter(AdminListaPontoReferenciaActivity.this, Classe com os dados ));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
