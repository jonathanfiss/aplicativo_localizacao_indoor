package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.LocalList;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.Usuario;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminCadastraSalaActivity extends BaseActivity {

    private EditText etNomeSala, etNumeroSala;
    private AutoCompleteTextView acLocalSala;
    private Button btPontoRefProx1, btPontoRefProx2, btPontoRefProx3, btCadastrarSala;
    private Sala sala;
    private Switch btPontoRefProxChecked1, btPontoRefProxChecked2, btPontoRefProxChecked3;
    private static Integer Activity_code = 0;


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
        btPontoRefProx2 = findViewById(R.id.btPontoRefProx2);
        btPontoRefProx3 = findViewById(R.id.btPontoRefProx3);
        btPontoRefProxChecked1 = findViewById(R.id.btPontoRefProxChecked1);
        btPontoRefProxChecked2 = findViewById(R.id.btPontoRefProxChecked2);
        btPontoRefProxChecked3 = findViewById(R.id.btPontoRefProxChecked3);

        btCadastrarSala = findViewById(R.id.btCadastrarSala);

        final List<String> corredor = new ArrayList<>();

        sala = new Sala();

        Call<LocalList> call = new RetrofitSetup().getLocalService().getLocal();

        call.enqueue(new Callback<LocalList>() {
            @Override
            public void onResponse(Call<LocalList> call, Response<LocalList> response) {
                if (response.isSuccessful()) {
                    LocalList localList = response.body();
                    for (Local local : localList.getLocalLists()){
                        corredor.add(local.getCorredor());
                    }
                    AppSetup.locais.addAll(localList.getLocalLists());
                }
            }

            @Override
            public void onFailure(Call<LocalList> call, Throwable t) {
                Toast.makeText(AdminCadastraSalaActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, corredor);
        acLocalSala = findViewById(R.id.acLocalSala);
        acLocalSala.setAdapter(adapter);

        btPontoRefProx1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraSalaActivity.this, AdminSelecionaPontoActivity.class);
                Activity_code = 1;
                intent.putExtra("Activity_code", Activity_code);
                startActivityForResult(intent, Activity_code);
            }
        });
        btPontoRefProxChecked1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                } else {

                }
            }
        });

        btPontoRefProx2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraSalaActivity.this, AdminSelecionaPontoActivity.class);
                Activity_code = 2;
                intent.putExtra("Activity_code", Activity_code);
                startActivityForResult(intent, Activity_code);
            }
        });
        btPontoRefProxChecked2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                } else {

                }
            }
        });

        btPontoRefProx3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraSalaActivity.this, AdminSelecionaPontoActivity.class);
                Activity_code = 3;
                intent.putExtra("Activity_code", Activity_code);
                startActivityForResult(intent, Activity_code);
            }
        });
        btPontoRefProxChecked3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {


                } else {

                }
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

//                    sala.setBssid_prox();

                    showWait(AdminCadastraSalaActivity.this, R.string.builder_cadastro);
                    Call call = new RetrofitSetup().getSalaRefService().inserir(sala);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if(response.isSuccessful()){
                                dismissWait();
                                switch (response.code()){
                                    case 201:
                                        Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_cadastra_sucesso), Toast.LENGTH_SHORT).show();
                                        limparForm();
//                                        finish();
                                        break;
                                    case 503:
                                        Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_erro_cadastra), Toast.LENGTH_SHORT).show();
                                        break;
                                    case 400:
                                        Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_falta_dados_cadastra), Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call call, Throwable t) {
                            dismissWait();
                            Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_erro_requisicao), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AdminCadastraSalaActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
                if (Activity_code == 1) {
                    AppSetup.wiFiDetalhesSelecionados.add(AppSetup.wiFiDetalhes.get(position));
                    btPontoRefProx1.setText("Ponto referência selecionado");
                    btPontoRefProxChecked1.setChecked(false);
                    Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                } else if (Activity_code == 2) {
                    AppSetup.wiFiDetalhesSelecionados.add(AppSetup.wiFiDetalhes.get(position));
                    btPontoRefProx2.setText("Ponto referência selecionado");
                    btPontoRefProxChecked2.setChecked(false);
                    Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                }else if (Activity_code == 3) {
                    AppSetup.wiFiDetalhesSelecionados.add(AppSetup.wiFiDetalhes.get(position));
                    btPontoRefProx3.setText("Ponto referência selecionado");
                    btPontoRefProxChecked3.setChecked(false);
                    Toast.makeText(AdminCadastraSalaActivity.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

}
