package com.example.aplicativo_localizacao_indoor.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.ListaSalasAdapter;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminListarSalasActivity extends BaseActivity {
    private ListView listaSalas;
    private static String TAG = "Lista de salas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listar_salas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listaSalas = findViewById(R.id.lv_lista_salas);

        if (AppSetup.salas.isEmpty()) {
            buscaSalas();
        }
        listaSalas.setAdapter(new ListaSalasAdapter(AdminListarSalasActivity.this, AppSetup.salas));

        listaSalas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dialogLongClink(position);
                return true;
            }
        });
    }

    private void dialogLongClink(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //adiciona um título e uma mensagem
        builder.setTitle("Deseja excluir?");
        //adiciona os botões
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showWait(AdminListarSalasActivity.this, R.string.builder_excluindo);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("dados/salas").child(AppSetup.salas.get(position).getKey());
                myRef.removeValue();
                AppSetup.salas.remove(AppSetup.salas.get(position));
                listaSalas.setAdapter(new ListaSalasAdapter(AdminListarSalasActivity.this, AppSetup.salas));
                dismissWait();
//                Log.d("sala", AppSetup.salas.get(position).to);
//excluir
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
