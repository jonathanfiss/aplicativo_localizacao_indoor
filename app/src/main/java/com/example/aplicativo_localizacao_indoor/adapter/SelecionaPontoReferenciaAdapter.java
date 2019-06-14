package com.example.aplicativo_localizacao_indoor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;
import com.example.aplicativo_localizacao_indoor.setup.AppSetup;

import java.util.List;

public class SelecionaPontoReferenciaAdapter extends ArrayAdapter<WiFiDetalhe> {

    private Context context;
    private List<WiFiDetalhe> wiFiDetalhes;
    private CardView cv_seleciona_lista;


    public SelecionaPontoReferenciaAdapter(@NonNull Context context, @NonNull List<WiFiDetalhe> wiFiDetalhes) {
        super(context, 0, wiFiDetalhes);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Devolve o objeto do modelo
        WiFiDetalhe wiFiDetalhes = getItem(position);

        //infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ponto_referencia_adapter, parent, false);
        }

        if (AppSetup.wiFiDetalhesSelecionados.contains(wiFiDetalhes.getBSSID())){
            cv_seleciona_lista = convertView.findViewById(R.id.cv_seleciona_lista);
            cv_seleciona_lista.setCardBackgroundColor(context.getResources().getColor(R.color.colorCinza));
            cv_seleciona_lista.setCardElevation(0);
        }

        //mapeia os componentes da UI para vincular os dados do objeto de modelo
        TextView tvSSID = convertView.findViewById(R.id.tvNomeCompletoAdapter);
        TextView tvBSSID = convertView.findViewById(R.id.tvEmailAdapter);
        TextView tvDBM = convertView.findViewById(R.id.tvDBM);
        TextView tvDistancia = convertView.findViewById(R.id.tvFuncaoAdapter);
        ImageView imvFotoPontoRefAdapter = convertView.findViewById(R.id.imvFotoPontoRefAdapter);

        //vincula os dados do objeto de modelo à view
        tvSSID.setText(wiFiDetalhes.getSSID());
        tvBSSID.setText(wiFiDetalhes.getBSSID());
        tvDBM.setText(String.format("%s %s", wiFiDetalhes.getWiFiSignal(), context.getResources().getString(R.string.label_dbm)));

        tvDistancia.setText(String.format("%s %.1f %s", context.getResources().getString(R.string.label_aprox), wiFiDetalhes.getDistacia(), context.getResources().getString(R.string.label_metros)));

        if (wiFiDetalhes.getWiFiSignal() >= -55) {
            imvFotoPontoRefAdapter.setImageResource(R.drawable.ic_signal_wifi_4_bar_green_24dp);
        } else if (wiFiDetalhes.getWiFiSignal() >= -65 && wiFiDetalhes.getWiFiSignal() < -55) {
            imvFotoPontoRefAdapter.setImageResource(R.drawable.ic_signal_wifi_3_bar_light_green_24dp);
        } else if (wiFiDetalhes.getWiFiSignal() >= -75 && wiFiDetalhes.getWiFiSignal() < -65) {
            imvFotoPontoRefAdapter.setImageResource(R.drawable.ic_signal_wifi_2_bar_yellow_24dp);

        } else if (wiFiDetalhes.getWiFiSignal() >= -85 && wiFiDetalhes.getWiFiSignal() < -75) {
            imvFotoPontoRefAdapter.setImageResource(R.drawable.ic_signal_wifi_1_bar_red_24dp);

        } else if (wiFiDetalhes.getWiFiSignal() < -85 ) {
            imvFotoPontoRefAdapter.setImageResource(R.drawable.ic_signal_wifi_0_bar_black_24dp);
        }
        return convertView;
    }
}


