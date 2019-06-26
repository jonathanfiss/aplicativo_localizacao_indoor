package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCadastraPontoActivityDetalhe extends AppCompatActivity {
    private Button btPontoAnt, btPontoPost, btCadPontoRef;
    private TextView tvSSID, tvBSSID;
    private EditText etPatrimonio;
    private Switch Anterior, Posterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_ponto_detalhe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final Integer position = getIntent().getExtras().getInt("position");
//        Integer positionAnt = getIntent().getExtras().getInt("positionAnt");
//        Integer positionPost = getIntent().getExtras().getInt("positionPost");
        tvSSID = findViewById(R.id.tvSSID);
        tvBSSID = findViewById(R.id.tvBSSID);

        tvSSID.setText(AppSetup.wiFiDetalhes.get(position).getSSID());
        tvBSSID.setText(AppSetup.wiFiDetalhes.get(position).getBSSID());

        btPontoAnt = findViewById(R.id.btCadPontoRefAnt);
        btPontoPost = findViewById(R.id.btCadPontoRefPost);
        btCadPontoRef = findViewById(R.id.btCadPontoRef);
        btPontoAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminSelecionaPontoActivity.class);
                intent.putExtra("retorna", "anterior");
                startActivity(intent);
            }
        });
        btPontoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminSelecionaPontoActivity.class);
                intent.putExtra("retorna", "posterior");
                startActivity(intent);
            }
        });
        btCadPontoRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPatrimonio = findViewById(R.id.etPatrimonio);
                Anterior = findViewById(R.id.btCadPontoRefAntChecked);
                Posterior = findViewById(R.id.btCadPontoRefPostChecked);
                Integer patrimonio = Integer.valueOf(etPatrimonio.getText().toString());
                PontoRef pontoRef = new PontoRef();
                pontoRef.setBssid(AppSetup.wiFiDetalhes.get(position).getBSSID());
                pontoRef.setSsid(AppSetup.wiFiDetalhes.get(position).getSSID());
                pontoRef.setPatrimonio(patrimonio);
                if (Anterior.isChecked()) {
                    pontoRef.setBssidAnt("0");
                } else {
                }
                if (Posterior.isChecked()) {
                    pontoRef.setBssidPost("0");
                } else {
                }
                Log.d("teste", String.valueOf(findViewById(R.id.btCadPontoRefAntChecked)));

                pontoRef.setSituacao(true);
                // obtém a referência do database e do nó
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("dados").child("pontosref");
                myRef.push().setValue(pontoRef)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_cadastra_ponto_sucesso), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_erro_cadastra_ponto), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
        Log.d("teste", String.valueOf(AppSetup.wiFiDetalhes.get(position).getBSSID()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
