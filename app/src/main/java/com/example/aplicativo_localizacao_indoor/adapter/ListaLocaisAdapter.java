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

        ViewHolder holder;
        Local local = getItem(position);

        //infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista_locais, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

//        //vincula os dados do objeto de modelo à view
        holder.tvPredio.setText(local.getPredio());
        holder.tvCorredor.setText(local.getCorredor());
        holder.tvAndar.setText(String.valueOf(local.getAndar()));
        holder.tvDescricao.setText(local.getDescricao());
//        holder.tvData.setText(local.getData_hora_modificado().toString());
        return convertView;
    }
    private class ViewHolder {
        TextView tvPredio;
        TextView tvCorredor;
        TextView tvAndar;
        TextView tvDescricao;
        TextView tvData;

        private ViewHolder(View convertView) {
            //mapeia os componentes da UI para vincular os dados do objeto de modelo
            tvPredio = convertView.findViewById(R.id.tvPredioAdapter);
            tvCorredor = convertView.findViewById(R.id.tvCorredorAdapter);
            tvAndar = convertView.findViewById(R.id.tvAndarAdapter);
            tvDescricao = convertView.findViewById(R.id.tvDescricaoAdapter);
            tvData = convertView.findViewById(R.id.tvDataAdapter);
        }
    }
}

