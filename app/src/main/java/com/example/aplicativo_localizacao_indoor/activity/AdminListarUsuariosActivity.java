package com.example.aplicativo_localizacao_indoor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.ListaUsuariosAdapter;
import com.example.aplicativo_localizacao_indoor.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminListarUsuariosActivity extends AppCompatActivity {
    private ListView listaUsuarios;
    private static String TAG = "Lista de usuarios";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listar_usuarios);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listaUsuarios = findViewById(R.id.lv_lista_usuarios);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("user");
        // Read from the database
        myRef.orderByChild("situacao").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Usuario> usuarios = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Log.d("data", ds.getValue().toString());
                    Usuario usuario = ds.getValue(Usuario.class);
                    usuario.setKey(ds.getKey());
                    usuarios.add(usuario);
                }
                listaUsuarios.setAdapter(new ListaUsuariosAdapter(AdminListarUsuariosActivity.this, usuarios));
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
