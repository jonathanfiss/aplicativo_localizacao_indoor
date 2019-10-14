package com.example.aplicativo_localizacao_indoor.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                    Call call = new RetrofitSetup().getLocalService().inserir(local);

                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.isSuccessful()) {
                                dismissWait();
                                switch (response.code()) {
                                    case 201:
                                        Toast.makeText(AdminCadastraLocalActivity.this, getString(R.string.toast_cadastra_sucesso), Toast.LENGTH_SHORT).show();
                                        limparForm();
//                                        finish();
                                        break;
                                    case 503:
                                        Toast.makeText(AdminCadastraLocalActivity.this, getString(R.string.toast_erro_cadastra), Toast.LENGTH_SHORT).show();
                                        break;
                                    case 400:
                                        Toast.makeText(AdminCadastraLocalActivity.this, getString(R.string.toast_falta_dados_cadastra), Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            dismissWait();
                            Toast.makeText(AdminCadastraLocalActivity.this, getString(R.string.toast_erro_requisicao), Toast.LENGTH_SHORT).show();
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
