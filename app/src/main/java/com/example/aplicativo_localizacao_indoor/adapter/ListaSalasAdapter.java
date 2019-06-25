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
import com.example.aplicativo_localizacao_indoor.model.Sala;

import java.util.List;

public class ListaSalasAdapter extends ArrayAdapter<Sala> {

    private Context context;
    private List<Sala> salas;

    public ListaSalasAdapter(@NonNull Context context, @NonNull List<Sala> salas) {
        super(context, 0, salas);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Devolve o objeto do modelo
        ViewHolder holder;

        //infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista_sala, parent, false);
         holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Sala sala = getItem(position);
        //vincula os dados do objeto de modelo à view
        holder.tvNomeSalaAdapter.setText(sala.getNome());
        holder.tvNumeroSalaAdapter.setText(sala.getNumero());
//        holder.tvLocalSalaAdapter.setText(sala.getLocal().getCorredor());

        return convertView;
    }
    private class ViewHolder {
        TextView tvNomeSalaAdapter;
        TextView tvLocalSalaAdapter;
        TextView tvNumeroSalaAdapter;

        private ViewHolder(View convertView) {
            //mapeia os componentes da UI para vincular os dados do objeto de modelo
            tvNomeSalaAdapter = convertView.findViewById(R.id.tvNomeSalaAdapter);
            tvLocalSalaAdapter = convertView.findViewById(R.id.tvLocalSalaAdapter);
            tvNumeroSalaAdapter = convertView.findViewById(R.id.tvNumeroSalaAdapter);
        }
    }
}

