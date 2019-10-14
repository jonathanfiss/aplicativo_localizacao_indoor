package com.example.aplicativo_localizacao_indoor.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.adapter.ListaPontosRefAdapter;
import com.example.aplicativo_localizacao_indoor.model.PontoRefList;
import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminListarPontoActivity extends BaseActivity {
    private ListView listaPontoRef;
    private static String TAG = "Lista de pontos referencia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_listar_ponto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listaPontoRef = findViewById(R.id.lv_lista_pontos_ref);
        buscaDados();

        listaPontoRef.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogLongClink(position);
            }
        });
    }

    private void buscaDados(){
        showWait(AdminListarPontoActivity.this, R.string.builder_buscando_dados);
        Call<PontoRefList> call = new RetrofitSetup().getPontoRefService().getPonto();

        call.enqueue(new Callback<PontoRefList>() {
            @Override
            public void onResponse(Call<PontoRefList> call, Response<PontoRefList> response) {
                if (response.isSuccessful()) {
                    PontoRefList pontoRefList = response.body();
                    AppSetup.pontosRef.clear();
                    AppSetup.pontosRef.addAll(pontoRefList.getPontoref());
                    listaPontoRef.setAdapter(new ListaPontosRefAdapter(AdminListarPontoActivity.this, AppSetup.pontosRef));
                    dismissWait();
                }
            }

            @Override
            public void onFailure(Call<PontoRefList> call, Throwable t) {
                dismissWait();
                Toast.makeText(AdminListarPontoActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
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
                showWait(AdminListarPontoActivity.this, R.string.builder_excluindo);
                Call call = new RetrofitSetup().getPontoRefService().excluir(String.valueOf(AppSetup.pontosRef.get(position).getId_ponto()));

                call.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if(response.isSuccessful()){
                            dismissWait();
                            switch (response.code()){
                                case 200:
                                    Toast.makeText(AdminListarPontoActivity.this, getString(R.string.toast_cadastra_sucesso), Toast.LENGTH_SHORT).show();

                                    buscaDados();
//                                        finish();
                                    break;
                                case 503:
                                    Toast.makeText(AdminListarPontoActivity.this, getString(R.string.toast_erro_cadastra), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        dismissWait();
                        Toast.makeText(AdminListarPontoActivity.this, getString(R.string.toast_erro_requisicao), Toast.LENGTH_SHORT).show();
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
