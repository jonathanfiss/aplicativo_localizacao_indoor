package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

public class AdminCadastraPontoActivityDetalhe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_ponto_detalhe);

        Integer position = getIntent().getExtras().getInt("position");
        Integer positionAnt = getIntent().getExtras().getInt("positionAnt");
        Integer positionPost = getIntent().getExtras().getInt("positionPost");
        TextView tvSSID = findViewById(R.id.tvSSID);
        TextView tvBSSID = findViewById(R.id.tvBSSID);
        EditText etPatrimonio = findViewById(R.id.etPatrimonio);

        tvSSID.setText(AppSetup.wiFiDetalhes.get(position).getSSID());
        tvBSSID.setText(AppSetup.wiFiDetalhes.get(position).getBSSID());

        AppSetup.ponto.setBssid(AppSetup.wiFiDetalhes.get(position).getBSSID());
        AppSetup.ponto.setSsid(AppSetup.wiFiDetalhes.get(position).getSSID());
        AppSetup.ponto.setPatrimonio(Integer.valueOf(String.valueOf(etPatrimonio)));
        AppSetup.ponto.setBssidAnt(AppSetup.wiFiDetalhes.get(positionAnt).getBSSID());
        AppSetup.ponto.setBssidPost(AppSetup.wiFiDetalhes.get(positionPost).getBSSID());
        AppSetup.ponto.setSituacao(true);

        Button btPontoAnt = findViewById(R.id.btCadPontoRefAnt);
        Button btPontoPost = findViewById(R.id.btCadPontoRefPost);
        btPontoAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminCadastraPontoActivity.class);
                intent.putExtra("ponto","1");
                startActivity(intent);
            }
        });
        btPontoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminCadastraPontoActivity.class);
                intent.putExtra("ponto","2");
                startActivity(intent);
            }
        });
        Log.d("teste", String.valueOf(AppSetup.wiFiDetalhes.get(position).getBSSID()));
    }

}
