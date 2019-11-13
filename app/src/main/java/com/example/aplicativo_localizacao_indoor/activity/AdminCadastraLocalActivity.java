package com.example.aplicativo_localizacao_indoor.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCadastraLocalActivity extends BaseActivity {

    private Local local;
    private RadioGroup radioGroupAndar;
    private EditText etDescricaoLocal, etPredio, etCorredor;
    private Button btCadLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_local);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btCadLocal = findViewById(R.id.btCadLocal);

        local = new Local();

        etDescricaoLocal = findViewById(R.id.etDescricaoLocal);
        etPredio = findViewById(R.id.etPredio);
        etCorredor = findViewById(R.id.etCorredor);

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (source.charAt(i) != ' ' && !Character.isLetterOrDigit(source.charAt(i))) { // Accept only letter & digits ; otherwise just return
                        Toast.makeText(AdminCadastraLocalActivity.this,"Não é possivel inserir esse carácter",Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                return null;
            }

        };

        InputFilter filterCorredor = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (source.charAt(i) != ' ' && !Character.isLetterOrDigit(source.charAt(i))) { // Accept only letter & digits ; otherwise just return
                        Toast.makeText(AdminCadastraLocalActivity.this,"Não é possivel inserir esse carácter",Toast.LENGTH_SHORT).show();
                        return "";
                    }
                }
                if (AppSetup.listaCorredores.containsValue(source.toString().toLowerCase())) {
                    Toast.makeText(AdminCadastraLocalActivity.this,"Esse corredor já foi cadastrado.",Toast.LENGTH_SHORT).show();
                    return source;
                }
                return null;
            }

        };

        etDescricaoLocal.setFilters(new InputFilter[]{filter});
        etPredio.setFilters(new InputFilter[]{filter});
        etCorredor.setFilters(new InputFilter[]{filterCorredor});

        radioGroupAndar = findViewById(R.id.radioGroupAndar);
        radioGroupAndar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonAndar1: {
                        local.setAndar(1);
                        Log.d("checked", "1 andar");
                        break;
                    }
                    case R.id.radioButtonAndar2: {
                        local.setAndar(2);
                        Log.d("checked", "2 andar");
                        break;
                    }
                    case R.id.radioButtonAndar3: {
                        local.setAndar(3);
                        Log.d("checked", "3 andar");
                        break;
                    }
                }
            }
        });
        btCadLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etDescricaoLocal.getText().toString().isEmpty() && !etPredio.getText().toString().isEmpty() && !etCorredor.getText().toString().isEmpty() && !radioGroupAndar.isSelected()) {
                    local.setPredio(etPredio.getText().toString());
                    local.setCorredor(etCorredor.getText().toString());
                    local.setDescricao(etDescricaoLocal.getText().toString());
                    local.setSituacao(true);

                    showWait(AdminCadastraLocalActivity.this, R.string.builder_cadastro);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("dados/locais");
                    myRef.push().setValue(local)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AdminCadastraLocalActivity.this, "Local salvo com sucesso", Toast.LENGTH_SHORT).show();
                                    limparForm();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AdminCadastraLocalActivity.this, "Fallha ao salvar local", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(AdminCadastraLocalActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void limparForm() {
        local = new Local();
        etPredio.setText(null);
        radioGroupAndar.clearCheck();
        etCorredor.setText(null);
        etDescricaoLocal.setText(null);
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
