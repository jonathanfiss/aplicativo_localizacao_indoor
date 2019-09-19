package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.ListaPontosRefAdapter;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.LocalList;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
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

public class AdminCadastraPontoActivityDetalhe extends BaseActivity {
    private Button btPontoAnt, btPontoPost, btCadPontoRef;
    private TextView tvSSID, tvBSSID;
    private EditText etPatrimonio;
    private AutoCompleteTextView acLocalPonto;
    private Switch Anterior, Posterior;
    private static int Activity_code = 0;
    private PontoRef pontoRef = new PontoRef();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_ponto_detalhe);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        final List<String> corredor = new ArrayList<>();

        final Integer position = getIntent().getExtras().getInt("position");

        AppSetup.pontoWiFi = AppSetup.wiFiDetalhes.get(position);

        tvSSID = findViewById(R.id.tvSSID);
        tvBSSID = findViewById(R.id.tvBSSID);
        acLocalPonto = findViewById(R.id.acLocalPonto);

        tvSSID.setText(AppSetup.wiFiDetalhes.get(position).getSSID());
        tvBSSID.setText(AppSetup.wiFiDetalhes.get(position).getBSSID());

        btPontoAnt = findViewById(R.id.btCadPontoRefAnt);
        btPontoPost = findViewById(R.id.btCadPontoRefPost);
        btCadPontoRef = findViewById(R.id.btCadPontoRef);
        Anterior = findViewById(R.id.btCadPontoRefAntChecked);
        Posterior = findViewById(R.id.btCadPontoRefPostChecked);

        btPontoAnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminSelecionaPontoActivity.class);
                Activity_code = 1;
                intent.putExtra("Activity_code", Activity_code);
                startActivityForResult(intent, Activity_code);
            }
        });
        btPontoPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminSelecionaPontoActivity.class);
                Activity_code = 2;
                intent.putExtra("Activity_code", Activity_code);
                startActivityForResult(intent, Activity_code);
            }
        });
        Anterior.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pontoRef.setBssidAnt("00");
                    btPontoAnt.setText(R.string.bt_cad_ponto_ref_ant);
                    AppSetup.pontoAnt = null;


                } else {
                    if (AppSetup.pontoAnt != null) {
                        pontoRef.setBssidAnt(AppSetup.pontoAnt.getBSSID());
                    }
                }
            }
        });
        Posterior.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    pontoRef.setBssidPost("00");
                    btPontoPost.setText(R.string.bt_cad_ponto_ref_post);
                    AppSetup.pontoPost = null;

                } else {
                    if (AppSetup.pontoPost != null) {
                        pontoRef.setBssidPost(AppSetup.pontoPost.getBSSID());
                    }
                }
            }
        });
        Call<LocalList> call = new RetrofitSetup().getLocalService().getLocal();

        call.enqueue(new Callback<LocalList>() {
            @Override
            public void onResponse(Call<LocalList> call, Response<LocalList> response) {
                if (response.isSuccessful()) {
                    LocalList localList = response.body();
                    for (Local local : localList.getLocalLists()) {
                        corredor.add(local.getCorredor());
                    }
                    AppSetup.locais.addAll(localList.getLocalLists());
                }
            }

            @Override
            public void onFailure(Call<LocalList> call, Throwable t) {
                Toast.makeText(AdminCadastraPontoActivityDetalhe.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
            }
        });


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, corredor);
        acLocalPonto = findViewById(R.id.acLocalPonto);
        acLocalPonto.setAdapter(adapter);
        btCadPontoRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPatrimonio = findViewById(R.id.etPatrimonio);

                Integer patrimonio = Integer.valueOf(etPatrimonio.getText().toString());
                pontoRef.setBssid(AppSetup.pontoWiFi.getBSSID());
                pontoRef.setSsid(AppSetup.pontoWiFi.getSSID());
                pontoRef.setPatrimonio(patrimonio);
                for (Local lc : AppSetup.locais) {
                    if (lc.getCorredor().contains(acLocalPonto.getText())) {
                        pontoRef.setLocal(lc);
                    }
                }


                pontoRef.setSituacao(true);
                Log.d("resultado", pontoRef.toString());
                showWait(AdminCadastraPontoActivityDetalhe.this, R.string.builder_cadastro);
                Call call = new RetrofitSetup().getPontoRefService().inserir(pontoRef);

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.isSuccessful()) {
                            dismissWait();
                            switch (response.code()) {
                                case 201:
                                    Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_cadastra_sucesso), Toast.LENGTH_SHORT).show();
                                    finish();
                                    break;
                                case 503:
                                    Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_erro_cadastra), Toast.LENGTH_SHORT).show();
                                    break;
                                case 400:
                                    Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_falta_dados_cadastra), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        dismissWait();
                        Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_erro_requisicao), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    protected void onActivityResult(int codigo, int resultado, Intent i) {
        Anterior = findViewById(R.id.btCadPontoRefAntChecked);
        Posterior = findViewById(R.id.btCadPontoRefPostChecked);
        // se o resultado de uma Activity for Activity_UM_DOIS...
        if (codigo == Activity_code) {
            // se o "i" (Intent) estiver preenchido, pega os seus dados (getExtras())
            Bundle params = i != null ? i.getExtras() : null;
            if (params != null) {
                Integer position = params.getInt("position");
                if (Activity_code == 1) {
                    AppSetup.pontoAnt = AppSetup.wiFiDetalhes.get(position);
                    btPontoAnt.setText("Ponto anterior selecionado");
                    Anterior.setChecked(false);
//                    btPontoPost.setBackgroundColor(R.color.colorVerdeEscuro);
                    Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                } else if (Activity_code == 2) {
                    AppSetup.pontoPost = AppSetup.wiFiDetalhes.get(position);
//                    btPontoPost.setBackgroundColor(R.color.colorVerdeEscuro);
                    btPontoPost.setText("Ponto posterior selecionado");
                    Posterior.setChecked(false);
                    Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                }
            }
        }
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
