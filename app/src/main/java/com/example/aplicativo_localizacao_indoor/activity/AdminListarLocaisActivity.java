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
import com.example.aplicativo_localizacao_indoor.adapter.ListaLocaisAdapter;
import com.example.aplicativo_localizacao_indoor.model.LocalList;
import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminListarLocaisActivity extends BaseActivity {
    private ListView listaLocais;
    private static String TAG = "Lista de locais";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listar_locais);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listaLocais = findViewById(R.id.lv_lista_locais);
        buscaDados();

        listaLocais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogLongClink(position);
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
                showWait(AdminListarLocaisActivity.this, R.string.builder_excluindo);
                Call call = new RetrofitSetup().getLocalService().excluir(String.valueOf(AppSetup.locais.get(position).getId()));

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            dismissWait();
                            switch (response.code()){
                                case 200:
                                    Toast.makeText(AdminListarLocaisActivity.this, getString(R.string.toast_cadastra_sucesso), Toast.LENGTH_SHORT).show();
                                    buscaDados();
//                                        finish();
                                    break;
                                case 503:
                                    Toast.makeText(AdminListarLocaisActivity.this, getString(R.string.toast_erro_cadastra), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        dismissWait();
                        Toast.makeText(AdminListarLocaisActivity.this, getString(R.string.toast_erro_requisicao), Toast.LENGTH_SHORT).show();
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
        Call<LocalList> call = new RetrofitSetup().getLocalService().getLocal();
        showWait(AdminListarLocaisActivity.this, R.string.builder_buscando_dados);

        call.enqueue(new Callback<LocalList>() {
            @Override
            public void onResponse(Call<LocalList> call, Response<LocalList> response) {
                if (response.isSuccessful()) {
                    LocalList localList = response.body();
                    AppSetup.locais.clear();
                    AppSetup.locais.addAll(localList.getLocalLists());
                    listaLocais.setAdapter(new ListaLocaisAdapter(AdminListarLocaisActivity.this, AppSetup.locais));
                    dismissWait();
                }
            }

            @Override
            public void onFailure(Call<LocalList> call, Throwable t) {
                Toast.makeText(AdminListarLocaisActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
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
