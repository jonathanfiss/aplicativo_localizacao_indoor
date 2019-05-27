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
import com.example.aplicativo_localizacao_indoor.model.WiFiDetalhes;

import java.text.NumberFormat;
import java.util.List;

public class PontoReferenciaAdapter extends ArrayAdapter<WiFiDetalhes> {

    private Context context;
    private List<WiFiDetalhes> wiFiDetalhes;

    public PontoReferenciaAdapter(@NonNull Context context, @NonNull List<WiFiDetalhes> wiFiDetalhes){
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

        //vincula os dados do objeto de modelo à view
        tvSSID.setText(wiFiDetalhes.getSSID());
        tvBSSID.setText(wiFiDetalhes.getBSSID());

//        if (produto.getUrl_foto() != null) {
//            //aqui vai vincular a foto do produto vindo do firebase usando a biblioteca Picasso
//        } else {
//            imvFoto.setImageResource(R.drawable.img_carrinho_de_compras);
//        }


        return convertView;
    }
}
