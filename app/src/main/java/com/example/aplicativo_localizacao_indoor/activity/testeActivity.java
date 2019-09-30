package com.example.aplicativo_localizacao_indoor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.aplicativo_localizacao_indoor.R;
import com.example.aplicativo_localizacao_indoor.model.Aresta;
import com.example.aplicativo_localizacao_indoor.model.Grafo;
import com.example.aplicativo_localizacao_indoor.model.Vertice;

public class testeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        Grafo g = new Grafo();
        Vertice a = g.addVertice("a");
        Vertice b = g.addVertice("b");
        Vertice c = g.addVertice("c");
        Vertice d = g.addVertice("d");
        Vertice e = g.addVertice("e");
        Vertice f = g.addVertice("f");
        Aresta ab = g.addAresta(a, b);
        Aresta bc = g.addAresta(b, c);
        Aresta bd = g.addAresta(b, d);
        Aresta ba = g.addAresta(b, a);
        Aresta cb = g.addAresta(c, b);
        Aresta cd = g.addAresta(c, d);
        Aresta db = g.addAresta(d, b);
        Aresta dc = g.addAresta(d, c);
        Aresta de = g.addAresta(d, e);
        Aresta ed = g.addAresta(e, d);
        Aresta ef = g.addAresta(e, f);
        Aresta fe = g.addAresta(f, e);
        Log.d("grafos", g.toString());
    }
}
