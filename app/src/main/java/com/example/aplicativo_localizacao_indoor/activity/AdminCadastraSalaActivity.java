package com.example.aplicativo_localizacao_indoor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        etNomeSala = findViewById(R.id.etNomeSala);
        etNumeroSala = findViewById(R.id.etNumeroSala);
        spLocalSala = findViewById(R.id.spLocalSala);
        btSelecionaPonto = findViewById(R.id.btSelecionaPonto);
        btCadastrarSala = findViewById(R.id.btCadastrarSala);

        corredor = new ArrayList<>();
        sala = new Sala();

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dados/local");
// Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                corredor.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Local local = ds.getValue(Local.class);
                    local.setKey(ds.getKey());
                    if (AppSetup.local.size()>0){
                        for (Local lc: AppSetup.local){
                            if (!local.getCorredor().equals(lc.getCorredor())){
                                corredor.add(lc.getCorredor());
                                Log.d("corredorUnidade",lc.getCorredor());
                                Log.d("corredorUnid",corredor.toString());
                            }
                        }
                    }
                    AppSetup.local.add(local);

                }
                Log.d("corredorTodo",corredor.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("erro de leitura", "Failed to read value.", error.toException());
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, corredor);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocalSala.setAdapter(adapter);

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
                if (!etNomeSala.getText().toString().isEmpty() && !etNumeroSala.getText().toString().isEmpty() && !spLocalSala.toString().isEmpty()){
                    sala.setNome(etNomeSala.getText().toString());
                    sala.setNumero(etNumeroSala.getText().toString());
//                sala.setlocal();
                    sala.setSituacao(true);
                    // obtém a referência do database e do nó
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("dados/sala");
                    myRef.push().setValue(sala);
                    Toast.makeText(AdminCadastraSalaActivity.this, "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                    limparForm();
                }else{
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
}
