package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Ponto;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCadastraPontoActivityDetalhe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_ponto_detalhe);

        final Integer position = getIntent().getExtras().getInt("position");
//        Integer positionAnt = getIntent().getExtras().getInt("positionAnt");
//        Integer positionPost = getIntent().getExtras().getInt("positionPost");
        TextView tvSSID = findViewById(R.id.tvSSID);
        TextView tvBSSID = findViewById(R.id.tvBSSID);

        tvSSID.setText(AppSetup.wiFiDetalhes.get(position).getSSID());
        tvBSSID.setText(AppSetup.wiFiDetalhes.get(position).getBSSID());

        Button btPontoAnt = findViewById(R.id.btCadPontoRefAnt);
        Button btPontoPost = findViewById(R.id.btCadPontoRefPost);
        Button btCadPontoRef = findViewById(R.id.btCadPontoRef);
        btPontoAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminCadastraPontoActivity.class);
                intent.putExtra("ponto", "1");
                startActivity(intent);
            }
        });
        btPontoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminCadastraPontoActivity.class);
                intent.putExtra("ponto", "2");
                startActivity(intent);
            }
        });
        btCadPontoRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etPatrimonio = findViewById(R.id.etPatrimonio);
                Switch Anterior = findViewById(R.id.btCadPontoRefAntChecked);
                Switch Posterior = findViewById(R.id.btCadPontoRefPostChecked);
                Integer patrimonio = Integer.valueOf(etPatrimonio.getText().toString());
                Ponto ponto = new Ponto();
                ponto.setBssid(AppSetup.wiFiDetalhes.get(position).getBSSID());
                ponto.setSsid(AppSetup.wiFiDetalhes.get(position).getSSID());
                ponto.setPatrimonio(patrimonio);
                if (Anterior.isChecked()) {
                    ponto.setBssidAnt(null);
                } else {
//                  ponto.setBssidAnt(AppSetup.wiFiDetalhes.get(positionPost).getBSSID());
                }
                if (Posterior.isChecked()) {
                    ponto.setBssidPost(null);
                } else {
//                    ponto.setBssidPost(AppSetup.wiFiDetalhes.get(positionPost).getBSSID());
                }
                Log.d("teste", String.valueOf(findViewById(R.id.btCadPontoRefAntChecked)));

//        AppSetup.ponto.setBssidPost(AppSetup.wiFiDetalhes.get(positionPost).getBSSID());
                ponto.setSituacao(true);
                AppSetup.ponto = ponto;
                // obtém a referência do database e do nó
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("dados/pontoref");
                myRef.push().setValue(ponto);

            }
        });
        Log.d("teste", String.valueOf(AppSetup.wiFiDetalhes.get(position).getBSSID()));
    }

}
