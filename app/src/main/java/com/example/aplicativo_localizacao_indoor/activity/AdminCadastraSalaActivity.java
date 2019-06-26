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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.Usuario;
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

public class AdminCadastraSalaActivity extends AppCompatActivity {

    private EditText etNomeSala, etNumeroSala;
    private Spinner spLocalSala;
    private Button btSelecionaPonto, btCadastrarSala;
    private Sala sala;
    private ArrayList corredor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cadastra_salas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        etNomeSala = findViewById(R.id.etNomeSala);
        etNumeroSala = findViewById(R.id.etNumeroSala);
        spLocalSala = findViewById(R.id.spLocalSala);
        btSelecionaPonto = findViewById(R.id.btSelecionaPonto);
        btCadastrarSala = findViewById(R.id.btCadastrarSala);

        final List<String> corredor = new ArrayList<>();

        sala = new Sala();

        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dados").child("locais");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AppSetup.locais.clear();
                corredor.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Local local = ds.getValue(Local.class);
                    local.setKey(ds.getKey());

                    AppSetup.locais.add(local);
                    for (Local lc : AppSetup.locais) {
                        corredor.add(lc.getCorredor());
                    }
                    Log.d("corredorTodo", corredor.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("erro de leitura", "Failed to read value.", error.toException());
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, corredor);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocalSala.setAdapter(adapter);
        spLocalSala.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sala.setLocal(AppSetup.locais.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btSelecionaPonto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AdminCadastraSalaActivity.this ,AdminSelecionaPontoActivity.class);
//                intent.putExtra("salaSelect", 1);
//                startActivity(intent);
                startActivity(new Intent(AdminCadastraSalaActivity.this, AdminSelecionaPontoActivity.class));
            }
        });

        btCadastrarSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etNomeSala.getText().toString().isEmpty() && !etNumeroSala.getText().toString().isEmpty() && !spLocalSala.toString().isEmpty()) {
                    sala.setNome(etNomeSala.getText().toString());
                    sala.setNumero(etNumeroSala.getText().toString());
//                sala.setlocal();
                    sala.setSituacao(true);
                    // obtém a referência do database e do nó
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("dados").child("salas");
                    myRef.push().setValue(sala)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AdminCadastraSalaActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                                    limparForm();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdminCadastraSalaActivity.this, "Falha ao realizar o cadastro", Toast.LENGTH_SHORT).show();
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

}
