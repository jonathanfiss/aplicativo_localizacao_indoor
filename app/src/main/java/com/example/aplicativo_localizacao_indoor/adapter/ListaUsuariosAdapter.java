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
import com.example.aplicativo_localizacao_indoor.model.Usuario;

import java.util.List;

public class ListaUsuariosAdapter extends ArrayAdapter<Usuario> {


    private Context context;
    private List<Usuario> usuarios;

    public ListaUsuariosAdapter(@NonNull Context context, @NonNull List<Usuario> usuarios) {
        super(context, 0, usuarios);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Devolve o objeto do modelo
        Usuario usuario = getItem(position);

        //infla a view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lista_usuario, parent, false);
        }

        //mapeia os componentes da UI para vincular os dados do objeto de modelo
        TextView tvNomeCompleto = convertView.findViewById(R.id.tvDescricaoAdapter);
        TextView tvEmailAdapter = convertView.findViewById(R.id.tvEmailAdapter);
        TextView tvFuncaoAdapter = convertView.findViewById(R.id.tvDescricaoAdapter);
        TextView tvMatriculaAdapter = convertView.findViewById(R.id.tvMatriculaAdapter);

        //vincula os dados do objeto de modelo à view
        tvNomeCompleto.setText(usuario.getNome().concat(" "));
        tvEmailAdapter.setText(usuario.getEmail());
        tvFuncaoAdapter.setText(usuario.getFuncao());
        tvMatriculaAdapter.setText(usuario.getMatricula());

        return convertView;
    }
}
