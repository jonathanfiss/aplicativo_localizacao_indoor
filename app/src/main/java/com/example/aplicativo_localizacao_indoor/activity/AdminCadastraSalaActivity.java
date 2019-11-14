package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
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
    private Button btPontoRefProx, btCadastrarSala;
    private Sala sala;
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

        btCadastrarSala = findViewById(R.id.btCadastrarSala);

        final List<String> corredor = new ArrayList<>();

        sala = new Sala();

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (source.charAt(i) != ' ' && !Character.isLetterOrDigit(source.charAt(i))) { // Accept only letter & digits ; otherwise just return
                        Toast.makeText(AdminCadastraSalaActivity.this,"Não é possivel inserir esse carácter",Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }

        };

        etNomeSala.setFilters(new InputFilter[]{filter});
        etNumeroSala.setFilters(new InputFilter[]{filter});
        acLocalSala.setFilters(new InputFilter[]{filter});

        if (AppSetup.salas.isEmpty()) {
            buscaSalas();
        }

        for (Local local : AppSetup.locais) {
            corredor.add(local.getCorredor());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, corredor);
        acLocalSala = findViewById(R.id.acLocalSala);
        acLocalSala.setAdapter(adapter);

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


                    new TaskPonto().execute();
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
            showWait(AdminCadastraSalaActivity.this, R.string.builder_cadastro);
        }

        @Override
        protected List<WiFiDetalhe> doInBackground(Void... voids) {
            List<ScanResult> scanResults;
            try {
                int qtd = 0;
                AppSetup.pontosProx.clear();
                WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                wifiManager.startScan();
                do{
                    scanResults = wifiManager.getScanResults();
                }while (scanResults.isEmpty());
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
            Log.d("Cad salas", "pontos proximos selecionados");
            if (flag) {
                dismissWait();
                flag = false;
            }
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
        }
    }

    private void limparForm() {
        sala = new Sala();
        etNomeSala.setText(null);
        etNumeroSala.setText(null);
        acLocalSala.setText(null);
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
