package com.example.aplicativo_localizacao_indoor.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.ListaLocaisAdapter;
import com.example.aplicativo_localizacao_indoor.adapter.ListaSalasAdapter;
import com.example.aplicativo_localizacao_indoor.model.Sala;
import com.example.aplicativo_localizacao_indoor.model.SalaList;
import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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

        buscaDados();

        listaSalas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogLongClink(position);
            }
        });
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
                Call call = new RetrofitSetup().getSalaRefService().excluir(String.valueOf(AppSetup.salas.get(position).getId_sala()));

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            dismissWait();
                            switch (response.code()){
                                case 200:
                                    Toast.makeText(AdminListarSalasActivity.this, getString(R.string.toast_cadastra_sucesso), Toast.LENGTH_SHORT).show();

                                    buscaDados();
//                                        finish();
                                    break;
                                case 503:
                                    Toast.makeText(AdminListarSalasActivity.this, getString(R.string.toast_erro_cadastra), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        dismissWait();
                        Toast.makeText(AdminListarSalasActivity.this, getString(R.string.toast_erro_requisicao), Toast.LENGTH_SHORT).show();
                    }
                });
                Log.d("lala", call.toString());
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    public void buscaDados(){
        showWait(AdminListarSalasActivity.this, R.string.builder_buscando_dados);
        Call<SalaList> call = new RetrofitSetup().getSalaRefService().getSala();

        call.enqueue(new Callback<SalaList>() {
            @Override
            public void onResponse(Call<SalaList> call, Response<SalaList> response) {
                if (response.isSuccessful()) {
                    SalaList salaList = response.body();
                    AppSetup.salas.clear();
                    AppSetup.salas.addAll(salaList.getSalasLists());
                    listaSalas.setAdapter(new ListaSalasAdapter(AdminListarSalasActivity.this, AppSetup.salas));
                    dismissWait();
                }
            }

            @Override
            public void onFailure(Call<SalaList> call, Throwable t) {
                Toast.makeText(AdminListarSalasActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
                dismissWait();
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
