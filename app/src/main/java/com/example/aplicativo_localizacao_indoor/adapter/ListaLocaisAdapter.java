package com.example.aplicativo_localizacao_indoor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista_locais, parent, false);
        }
//        //mapeia os componentes da UI para vincular os dados do objeto de modelo
        TextView tvPredio = convertView.findViewById(R.id.tvPredioAdapter);
        TextView tvCorredor = convertView.findViewById(R.id.tvCorredorAdapter);
        TextView tvAndar = convertView.findViewById(R.id.tvAndarAdapter);
        TextView tvDescricao = convertView.findViewById(R.id.tvDescricaoAdapter);
        TextView tvData = convertView.findViewById(R.id.tvDataAdapter);
//
//        //vincula os dados do objeto de modelo à view
        tvPredio.setText(local.getPredio());
        tvCorredor.setText(local.getCorredor());
        tvAndar.setText(String.valueOf(local.getAndar()));
        tvDescricao.setText(local.getDescricao());
//        tvData.setText(local.getData_hora_modificado().toString());
        return convertView;
    }
}

