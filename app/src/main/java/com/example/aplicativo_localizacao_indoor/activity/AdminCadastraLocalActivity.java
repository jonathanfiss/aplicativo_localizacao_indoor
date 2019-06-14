package com.example.aplicativo_localizacao_indoor.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminCadastraLocalActivity extends AppCompatActivity {

    private Local local;
    private RadioGroup radioGroupAndar;
    private AutoCompleteTextView autoCompleteSetor;
    private EditText etDescricaoLocal;
    private Button btCadLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_local);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        btCadLocal = findViewById(R.id.btCadLocal);

        String[] SETORES = new String[]{"Principal", "Tecnologia em sistemas para Internet", "Design", "Caldelas"};

        local = new Local();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SETORES);
        autoCompleteSetor = (AutoCompleteTextView) findViewById(R.id.acCorredor);
        autoCompleteSetor.setAdapter(adapter);

        etDescricaoLocal = findViewById(R.id.etDescricaoLocal);

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
                if (!etDescricaoLocal.getText().toString().isEmpty() && !autoCompleteSetor.getText().toString().isEmpty() && !radioGroupAndar.isSelected()) {
                    local.setCorredor(autoCompleteSetor.getText().toString());
                    local.setDescricao(etDescricaoLocal.getText().toString());
                    local.setSituacao(true);
                    // obtém a referência do database e do nó
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
                }else{
                    Toast.makeText(AdminCadastraLocalActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void limparForm() {
        local = new Local();
        radioGroupAndar.clearCheck();
        autoCompleteSetor.setText(null);
        etDescricaoLocal.setText(null);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
