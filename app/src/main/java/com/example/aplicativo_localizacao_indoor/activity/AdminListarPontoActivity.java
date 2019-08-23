package com.example.aplicativo_localizacao_indoor.activity;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.ListaPontosRefAdapter;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminListarPontoActivity extends AppCompatActivity {
    private ListView listaPontoRef;
    private static String TAG = "Lista de pontos referencia";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listar_ponto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listaPontoRef = findViewById(R.id.lv_lista_pontos_ref);
        new TaskBuscaPontos().execute();
        // Write a message to the database



        listaPontoRef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listaPontoRef.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialogLongClink(position);
                return true;
            }
        });
    }

    //    AsyncTask <Params, Progress, Result>:
    class TaskBuscaPontos extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DatabaseReference myRef = database.getReference("dados").child("pontosref");

            // Read from the database
            myRef.orderByChild("situacao").equalTo(true).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    AppSetup.pontosRef.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        PontoRef ponto = ds.getValue(PontoRef.class);
//                        ponto.setKey(ds.getKey());
                        AppSetup.pontosRef.add(ponto);
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //carrega os dados na View
            listaPontoRef.setAdapter(new ListaPontosRefAdapter(AdminListarPontoActivity.this, AppSetup.pontosRef));
        }
    }

    private void dialogLongClink(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //adiciona um título e uma mensagem
        builder.setTitle(R.string.title_opcao);
//        builder.setMessage(R.string.mensagem_exclui);
        //adiciona os botões
        builder.setPositiveButton(R.string.alertdialog_editar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("dados").child("pontosref").child(AppSetup.pontosRef.get(position).getKey()).child("situacao");
//                myRef.setValue("true");
            }
        });
        builder.setNegativeButton(R.string.alertdialog_excluir, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("dados").child("pontosref").child(AppSetup.pontosRef.get(position).getKey()).child("situacao");
//                myRef.setValue("false");
            }
        });
        builder.show();
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
