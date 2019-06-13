package com.example.aplicativo_localizacao_indoor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.ListaSalasAdapter;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminListarSalasActivity extends AppCompatActivity {
    private ListView listaSalas;
    private static String TAG = "Lista de salas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listar_salas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listaSalas = findViewById(R.id.lv_lista_salas);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dados/sala");

        // Read from the database
        myRef.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                Log.d(TAG, "Value is: " + dataSnapshot.getValue());

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Sala sala = ds.getValue(Sala.class);
//                    sala.setKey(ds.getKey());
                    AppSetup.salas.add(sala);
                }

                //carrega os dados na View

                listaSalas.setAdapter(new ListaSalasAdapter(AdminListarSalasActivity.this, AppSetup.salas));

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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
