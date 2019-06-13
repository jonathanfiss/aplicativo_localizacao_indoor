package com.example.aplicativo_localizacao_indoor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Local;
import com.example.aplicativo_localizacao_indoor.model.Sala;

import java.util.List;

public class ListaLocaisAdapter extends ArrayAdapter<Local> {

    private Context context;
    private List<Local> locais;

    public ListaLocaisAdapter(@NonNull Context context, @NonNull List<Local> locais) {
        super(context, 0, locais);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Devolve o objeto do modelo
        Local local = getItem(position);

        //infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista_sala, parent, false);
        }
//        //mapeia os componentes da UI para vincular os dados do objeto de modelo
//        TextView tvSSID = convertView.findViewById(R.id.tvSSIDAdapter);
//        TextView tvBSSID = convertView.findViewById(R.id.tvBSSIDItemAdapter);
//        TextView tvDBM = convertView.findViewById(R.id.tvDBM);
//        TextView tvDistancia = convertView.findViewById(R.id.tvDistancia);
//        ImageView imvFotoPontoRefAdapter = convertView.findViewById(R.id.imvFotoPontoRefAdapter);
//
//        //vincula os dados do objeto de modelo à view
//        tvSSID.setText(wiFiDetalhes.getSSID());
//        tvBSSID.setText(wiFiDetalhes.getBSSID());
//        tvDBM.setText(String.format("%s %s", wiFiDetalhes.getWiFiSignal(), context.getResources().getString(R.string.label_dbm)));
//
//        tvDistancia.setText(String.format("%s %.1f %s", context.getResources().getString(R.string.label_aprox), wiFiDetalhes.getDistacia(), context.getResources().getString(R.string.label_metros)));
//
        return convertView;
    }
}

