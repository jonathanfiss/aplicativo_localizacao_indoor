package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
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
    private Button btCadPontoRef, btCadPontoProx3, btCadPontoProx4;
    private TextView tvSSID, tvBSSID;
    private EditText etPatrimonio;
    private AutoCompleteTextView acLocalPonto;
    private Switch btCadPontoRefProx3Checked, btCadPontoRefProx4Checked;
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

        btCadPontoProx3 = findViewById(R.id.btCadPontoProx3);
        btCadPontoProx4 = findViewById(R.id.btCadPontoProx4);

        btCadPontoRefProx3Checked = findViewById(R.id.btCadPontoRefProx3Checked);
        btCadPontoRefProx4Checked = findViewById(R.id.btCadPontoRefProx4Checked);

        btCadPontoRef = findViewById(R.id.btCadPontoRef);


        btCadPontoProx3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminSelecionaPontoActivity.class);
                Activity_code = 3;
                intent.putExtra("Activity_code", Activity_code);
                startActivityForResult(intent, Activity_code);
            }
        });
        btCadPontoProx4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminCadastraPontoActivityDetalhe.this, AdminSelecionaPontoActivity.class);
                Activity_code = 4;
                intent.putExtra("Activity_code", Activity_code);
                startActivityForResult(intent, Activity_code);
            }
        });

        btCadPontoRefProx3Checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    pontoRef.setBssidPost("00");
                    btCadPontoProx3.setText(R.string.bt_cad_ponto_ref_post);
                    AppSetup.pontoPost = null;

                } else {
                    if (AppSetup.pontoPost != null) {
                        pontoRef.setBssidPost(AppSetup.pontoPost.getBSSID());
                    }
                }
            }
        });
        btCadPontoRefProx4Checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    pontoRef.setBssidPost2("00");
                    btCadPontoProx4.setText(R.string.bt_cad_ponto_ref_post);
                    AppSetup.pontoPost2 = null;

                } else {
                    if (AppSetup.pontoPost2 != null) {
                        pontoRef.setBssidPost2(AppSetup.pontoPost2.getBSSID());
                    }
                }
            }
        });
        if (AppSetup.salas.isEmpty()) {
            buscaSalas();
        }
        for (Local local : AppSetup.locais) {
            corredor.add(local.getCorredor());
        }

        acLocalPonto = findViewById(R.id.acLocalPonto);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, corredor);


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

                Log.d("resultado", pontoRef.toString());
                showWait(AdminCadastraPontoActivityDetalhe.this, R.string.builder_cadastro);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("dados").child("pontosref");
                myRef.push().setValue(pontoRef)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_cadastra_sucesso), Toast.LENGTH_SHORT).show();
                                dismissWait();
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_erro_cadastra), Toast.LENGTH_SHORT).show();
                                dismissWait();
                            }
                        });
            }
        });
    }

    protected void onActivityResult(int codigo, int resultado, Intent i) {
        btCadPontoRefProx3Checked = findViewById(R.id.btCadPontoRefProx3Checked);
        btCadPontoRefProx4Checked = findViewById(R.id.btCadPontoRefProx4Checked);
        // se o resultado de uma Activity for Activity_UM_DOIS...
        if (codigo == Activity_code) {
            // se o "i" (Intent) estiver preenchido, pega os seus dados (getExtras())
            Bundle params = i != null ? i.getExtras() : null;
            if (params != null) {
                Integer position = params.getInt("position");
                if (Activity_code == 3) {
                    AppSetup.pontoPost = AppSetup.wiFiDetalhes.get(position);
                    btCadPontoProx3.setText("Ponto posterior selecionado");
                    btCadPontoRefProx3Checked.setChecked(true);
                    Toast.makeText(AdminCadastraPontoActivityDetalhe.this, getString(R.string.toast_ponto_selecionado), Toast.LENGTH_SHORT).show();
                } else if (Activity_code == 4) {
                    AppSetup.pontoPost2 = AppSetup.wiFiDetalhes.get(position);
                    btCadPontoProx4.setText("Ponto posterior selecionado");
                    btCadPontoRefProx4Checked.setChecked(true);
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
