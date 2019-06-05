package com.example.aplicativo_localizacao_indoor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.activity.AdminSelecionaPontoActivity;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhes;

import java.util.List;

public class PontoReferenciaAdapter extends ArrayAdapter<WiFiDetalhes> {

    private Context context;
    private List<WiFiDetalhes> wiFiDetalhes;

    public PontoReferenciaAdapter(@NonNull Context context, @NonNull List<WiFiDetalhes> wiFiDetalhes) {
        super(context, 0, wiFiDetalhes);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Devolve o objeto do modelo
        WiFiDetalhes wiFiDetalhes = getItem(position);

        //infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ponto_referencia_adapter, parent, false);
        }

        //mapeia os componentes da UI para vincular os dados do objeto de modelo
        TextView tvSSID = convertView.findViewById(R.id.tvSSIDAdapter);
        TextView tvBSSID = convertView.findViewById(R.id.tvBSSIDItemAdapter);
        TextView tvDBM = convertView.findViewById(R.id.tvDBM);
        TextView tvDistancia = convertView.findViewById(R.id.tvDistancia);
        ImageView imvFotoPontoRefAdapter = convertView.findViewById(R.id.imvFotoPontoRefAdapter);

        //vincula os dados do objeto de modelo à view
        tvSSID.setText(wiFiDetalhes.getSSID());
        tvBSSID.setText(wiFiDetalhes.getBSSID());
        tvDBM.setText(String.valueOf(wiFiDetalhes.getWiFiSignal()));
        tvDistancia.setText(wiFiDetalhes.getDistacia().toString());
//        tvDBM.setText(String.valueOf(wiFiDetalhes.getWiFiSignal()).concat(String.valueOf(R.string.tv_dbm)));
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
