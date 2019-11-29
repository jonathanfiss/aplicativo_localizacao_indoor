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
import com.example.aplicativo_localizacao_indoor.model.Rota;
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhe;

import java.util.List;

public class RotaAdapter extends ArrayAdapter<Rota> {

    private Context context;

    public RotaAdapter(@NonNull Context context, @NonNull List<Rota> rotas) {
        super(context, 0, (List<Rota>) rotas);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Devolve o objeto do modelo
        Rota rotas = getItem(position);

        //infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista_rotas, parent, false);
        }

        //mapeia os componentes da UI para vincular os dados do objeto de modelo
        TextView etInfoRota = convertView.findViewById(R.id.etInfoRota);


        //vincula os dados do objeto de modelo à view
        etInfoRota.setText(rotas.getLocal().getCorredor());


        return convertView;
    }
}
