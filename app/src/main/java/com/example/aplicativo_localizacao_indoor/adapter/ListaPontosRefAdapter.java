package com.example.aplicativo_localizacao_indoor.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.PontoRef;

import java.util.List;

public class ListaPontosRefAdapter extends ArrayAdapter<PontoRef> {

    private Context context;
    private List<PontoRef> pontosref;

    public ListaPontosRefAdapter(@NonNull Context context, @NonNull List<PontoRef> pontosref) {
        super(context, 0, pontosref);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        //Devolve o objeto do modelo

        //infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista_pontos, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PontoRef pontoref = getItem(position);

        //vincula os dados do objeto de modelo à view
        holder.tvSsidAdapter.setText(pontoref.getSsid());
        holder.tvBssidAdapter.setText(pontoref.getBssid());
        holder.tvPatrimonioAdapter.setText(pontoref.getPatrimonio().toString());
//        holder.tvLocalAdapter.setText(pontoref.getLocal().getCorredor());

        return convertView;
    }

    private class ViewHolder {
        TextView tvSsidAdapter;
        TextView tvBssidAdapter;
        TextView tvPatrimonioAdapter;
        TextView tvLocalAdapter;


        private ViewHolder(View convertView) {
            //mapeia os componentes da UI para vincular os dados do objeto de modelo
            tvSsidAdapter = convertView.findViewById(R.id.tvSsidAdapter);
            tvBssidAdapter = convertView.findViewById(R.id.tvBssidAdapter);
            tvPatrimonioAdapter = convertView.findViewById(R.id.tvPatrimonioAdapter);
            tvLocalAdapter = convertView.findViewById(R.id.tvLocalAdapter);

        }
    }
}

