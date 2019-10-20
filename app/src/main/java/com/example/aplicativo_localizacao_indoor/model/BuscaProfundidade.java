package com.example.aplicativo_localizacao_indoor.model;

import java.util.ArrayList;
import java.util.Stack;

public class BuscaProfundidade extends Busca{
    private Stack<Vertice> visitar;

    public BuscaProfundidade(Vertice inicio, Vertice procurado) {
        super(inicio, procurado);
        visitar = new Stack<Vertice>();
    }
    public BuscaProfundidade(){
        super(null, null);
    }

    public Stack<Vertice> getVisitar() {
        return visitar;
    }
    public Vertice remover(){
        return this.visitar.pop();
    }
    public int encontrarCaminho(ArrayList<Vertice> vertices){
        if(this.getVisitados().contains(this.getProcurando())){
            return 0;
        }else{
            if(vertices.isEmpty()){
                return -1;
            }else{
                for (Vertice v: vertices){
                    if (!this.getVisitados().contains(v) && !this.getVisitar().contains(v)){
                        this.inserirAVisitar(v);
                    }
                }
                return -1;
            }
        }
    }
    public void inserirAVisitar(Vertice v){
        this.visitar.add(v);
    }
}
