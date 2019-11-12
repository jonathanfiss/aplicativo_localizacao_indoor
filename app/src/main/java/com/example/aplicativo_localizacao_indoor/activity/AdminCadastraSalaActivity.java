package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.aplicativo_localizacao_indoor.setup.AppSetup.wiFiDetalhes;

public class AdminCadastraSalaActivity extends BaseActivity {

    private EditText etNomeSala, etNumeroSala;
    private AutoCompleteTextView acLocalSala;
    private Button btPontoRefProx1, btPontoRefProx2, btPontoRefProx3, btCadastrarSala;
    private Sala sala;
    private Switch btPontoRefProxChecked1, btPontoRefProxChecked2, btPontoRefProxChecked3;
    private static Integer Activity_code = 0;
    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_salas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        etNomeSala = findViewById(R.id.etNomeSala);
        etNumeroSala = findViewById(R.id.etNumeroSala);
        acLocalSala = findViewById(R.id.acLocalSala);
        btPontoRefProx1 = findViewById(R.id.btPontoRefProx1);

        btCadastrarSala = findViewById(R.id.btCadastrarSala);

        final List<String> corredor = new ArrayList<>();

        sala = new Sala();

        if (AppSetup.salas.isEmpty()) {
            buscaSalas();
        }

        for (Local local : AppSetup.locais) {
            corredor.add(local.getCorredor());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, corredor);
        acLocalSala = findViewById(R.id.acLocalSala);
        acLocalSala.setAdapter(adapter);

        btPontoRefProx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TaskPonto().execute();
            }
        });

        btCadastrarSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNomeSala.getText().toString().isEmpty() && !etNumeroSala.getText().toString().isEmpty() && !acLocalSala.getText().toString().isEmpty()) {
                    sala.setNome(etNomeSala.getText().toString());
                    sala.setNumero(etNumeroSala.getText().toString());
                    for (Local lc : AppSetup.locais) {
                        if (lc.getCorredor().contains(acLocalSala.getText())) {
                            sala.setLocal(lc);
                        }
                    }
                    sala.setSituacao(true);
                    int i = 1;
                    for (PontoRef pontoRef : AppSetup.pontosProx){
                        if (i == 1){
                            sala.setBssid_prox1(pontoRef.getBssid());
                            i++;
                        }else if (i == 2){
                            sala.setBssid_prox2(pontoRef.getBssid());
                            i++;
                        }else if (i == 3){
                            sala.setBssid_prox3(pontoRef.getBssid());
                        }
                    }

                    showWait(AdminCadastraSalaActivity.this, R.string.builder_cadastro);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("dados").child("salas");
                    myRef.push().setValue(sala)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dismissWait();
                                    Toast.makeText(AdminCadastraSalaActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                                    limparForm();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dismissWait();
                                    Toast.makeText(AdminCadastraSalaActivity.this, "Falha ao realizar o cadastro", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(AdminCadastraSalaActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskPonto extends AsyncTask<Void, List<WiFiDetalhe>, List<WiFiDetalhe>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            flag = true;
            showWait(AdminCadastraSalaActivity.this, R.string.builder_localizar);
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            try {
                int qtd = 0;
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                wifiManager.startScan();
                List<ScanResult> scanResults = wifiManager.getScanResults();
                AppSetup.wiFiDetalhes.clear();
                first:
                for (ScanResult result : scanResults) {
                    if (AppSetup.listaMacs.containsValue(result.BSSID)) {
                        for (PontoRef pontoRef : AppSetup.pontosRef) {
                            if (formataBSSID(result.BSSID).equals(formataBSSID(pontoRef.getBssid()))) {
                                AppSetup.pontosProx.add(pontoRef);
                                qtd++;
                            }
                            if (qtd == 3) {
                                break first;
                            }
                        }
                    }//else seria se o local não foi localizado
                    //txSelecionados
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return wiFiDetalhes;
        }

        @Override
        protected void onPostExecute(List<WiFiDetalhe> wiFiDetalhes) {
            super.onPostExecute(wiFiDetalhes);
            if (flag) {
                dismissWait();
                flag = false;
            }
        }
    }

    private void limparForm() {
        sala = new Sala();
        etNomeSala.setText(null);
        etNumeroSala.setText(null);
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

    protected void onActivityResult(int codigo, int resultado, Intent i) {
        // se o resultado de uma Activity for Activity_UM_DOIS...
        if (codigo == Activity_code) {
            // se o "i" (Intent) estiver preenchido, pega os seus dados (getExtras())
            Bundle params = i != null ? i.getExtras() : null;
            if (params != null) {
                Integer position = params.getInt("position");
                if (Activity_code == 3) {
                    AppSetup.wiFiDetalhesSelecionados.add(AppSetup.wiFiDetalhes.get(position));
                    btPontoRefProx1.setText("Ponto referência selecionado");
                    btPontoRefProxChecked1.setChecked(false);
                    Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                } else if (Activity_code == 4) {
                    AppSetup.wiFiDetalhesSelecionados.add(AppSetup.wiFiDetalhes.get(position));
                    btPontoRefProx2.setText("Ponto referência selecionado");
                    btPontoRefProxChecked2.setChecked(false);
                    Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                } else if (Activity_code == 5) {
                    AppSetup.wiFiDetalhesSelecionados.add(AppSetup.wiFiDetalhes.get(position));
                    btPontoRefProx3.setText("Ponto referência selecionado");
                    btPontoRefProxChecked3.setChecked(false);
                    Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

}
