//package com.example.aplicativo_localizacao_indoor.activity;
//
//import android.net.wifi.ScanResult;
//import android.support.v4.view.ViewCompat;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.example.aplicativo_localizacao_indoor.R;
//import com.example.aplicativo_localizacao_indoor.model.LocalList;
//import com.example.aplicativo_localizacao_indoor.model.PontoRef;
//import com.example.aplicativo_localizacao_indoor.model.Sala;
//import com.example.aplicativo_localizacao_indoor.model.SalaList;
//import com.example.aplicativo_localizacao_indoor.service.RetrofitSetup;
//import com.example.aplicativo_localizacao_indoor.setup.AppSetup;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//
//public class testeActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_teste);
//
//        if (!scanResults.isEmpty() && !AppSetup.pontosRef.isEmpty())
//            for (ScanResult result : scanResults) {
//                for (final PontoRef ponto : AppSetup.pontosRef) {
//                    if (formataBSSID(result.BSSID).equals(formataBSSID(ponto.getBssid()))) {
//                        AppSetup.pontoRef = ponto;
//
//                        scanResults.clear();
//
//                        Log.d("PontoRef busca", result.toString());
//                        Call<LocalList> call = new RetrofitSetup().getLocalService().getLocalById(ponto.getLocal_id().toString());
//
//                        call.enqueue(new Callback<LocalList>() {
//                            @Override
//                            public void onResponse(Call<LocalList> call, Response<LocalList> response) {
//                                if (response.isSuccessful()) {
//                                    AppSetup.local = null;
//                                    LocalList localList = response.body();
//                                    AppSetup.local = localList.getLocalLists().get(0);
//                                    AppSetup.pontoRef.setLocal(localList.getLocalLists().get(0));
//                                    Log.d("PontoRef Local", AppSetup.pontoRef.toString());
//                                    tvLocaliza.setText(getResources().getText(R.string.frase_voce) + " " +
//                                            "" + getResources().getText(R.string.frase_predio) + " " +
//                                            "" + AppSetup.pontoRef.getLocal().getPredio() + " " +
//                                            "" + getResources().getText(R.string.frase_andar) + " " +
//                                            "" + AppSetup.pontoRef.getLocal().getAndar() + " " +
//                                            "" + getResources().getText(R.string.frase_corredor) + " " +
//                                            "" + AppSetup.pontoRef.getLocal().getCorredor());
//                                    AppSetup.salas.clear();
//                                    Call<SalaList> call2 = new RetrofitSetup().getSalaRefService().getSalaById(ponto.getLocal_id().toString());
//
//                                    call2.enqueue(new Callback<SalaList>() {
//                                        @Override
//                                        public void onResponse(Call<SalaList> call, Response<SalaList> response) {
//                                            if (response.isSuccessful()) {
//                                                Log.d("sala", "ok");
//                                                SalaList salaList = response.body();
//                                                String txtsalas = "";
//                                                for (Sala sala : salaList.getSalasLists()) {
//                                                    sala.setLocal(AppSetup.local);
//                                                    AppSetup.salas.add(sala);
//                                                    Log.d("sala", sala.toString());
//                                                }
//                                                int cont = 0;
//                                                int tamanho = AppSetup.salas.size();
//                                                for (Sala sala : AppSetup.salas) {
//                                                    if (txtsalas.isEmpty()) {
//                                                        txtsalas = sala.getNome();
//                                                    } else {
//                                                        txtsalas = txtsalas.concat(sala.getNome());
//                                                    }
//                                                    cont++;
//                                                    if (tamanho > cont) {
//                                                        txtsalas = txtsalas.concat(", ");
//                                                    }
//                                                }
//                                                dismissWait();
//
//                                                if (tamanho > 1) {
//                                                    tvLocaliza.setText(getResources().getText(R.string.frase_voce) + " " +
//                                                            "" + getResources().getText(R.string.frase_predio) + " " +
//                                                            "" + AppSetup.pontoRef.getLocal().getPredio() + " " +
//                                                            "" + getResources().getText(R.string.frase_andar) + " " +
//                                                            "" + AppSetup.pontoRef.getLocal().getAndar() + " " +
//                                                            "" + getResources().getText(R.string.frase_corredor) + " " +
//                                                            "" + AppSetup.pontoRef.getLocal().getCorredor() + " " +
//                                                            "" + getResources().getText(R.string.frase_salas) + " " +
//                                                            "" + txtsalas);
//                                                    ViewCompat.setAccessibilityLiveRegion(tvLocaliza, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
//                                                } else {
//                                                    tvLocaliza.setText(getResources().getText(R.string.frase_voce) + " " +
//                                                            "" + getResources().getText(R.string.frase_predio) + " " +
//                                                            "" + AppSetup.pontoRef.getLocal().getPredio() + " " +
//                                                            "" + getResources().getText(R.string.frase_andar) + " " +
//                                                            "" + AppSetup.pontoRef.getLocal().getAndar() + " " +
//                                                            "" + getResources().getText(R.string.frase_corredor) + " " +
//                                                            "" + AppSetup.pontoRef.getLocal().getCorredor() + " " +
//                                                            "" + getResources().getText(R.string.frase_sala) + " " +
//                                                            "" + txtsalas);
//                                                    ViewCompat.setAccessibilityLiveRegion(tvLocaliza, ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE);
//
//                                                }
//
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onFailure(Call<SalaList> call, Throwable t) {
//                                            Log.d("sala", "erro");
//
//                                            Toast.makeText(LocalizarActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<LocalList> call, Throwable t) {
//                                Toast.makeText(LocalizarActivity.this, "Não foi possível realizar a requisição", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                        break;
//                    }
//                }
//            }
//
//    }
//}
