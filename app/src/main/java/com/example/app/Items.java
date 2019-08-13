package com.example.app;

public class Items {
    private int id ;
    private String frase;
    private String frase_correcta;
    private int etapa;
    private int nivel;

    public Items(){

    }

    public Items(int id, String frase, String frase_correcta,int etapa,int nivel){
        this.id = id;
        this.frase = frase;
        this.frase_correcta = frase_correcta;
        this.etapa = etapa;
        this.nivel = nivel;
    }

    public void setFrase(String frase){
        this.frase = frase;
    }

    public String getFrase(){
        return frase;
    }

    public void setFrase_correcta(String frase_correcta) {
        this.frase_correcta = frase_correcta;
    }

    public String getFrase_correcta(){
        return frase_correcta;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public int getEtapa(){
        return etapa;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getNivel()
    {
        return nivel;
    }
}
