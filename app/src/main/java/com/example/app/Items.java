package com.example.app;

public class Items {
    private int id ;
    private String texto_item;
    private String forma_correcta;
    private String pista;
    private int etapa;
    private int nivel;

    public Items(){
    }

    public Items(int id, String texto_item, String forma_correcta, String pista, int etapa, int nivel) {
        this.id = id;
        this.texto_item = texto_item;
        this.forma_correcta = forma_correcta;
        this.pista = pista;
        this.etapa = etapa;
        this.nivel = nivel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto_item() {
        return texto_item;
    }

    public void setTexto_item(String texto_item) {
        this.texto_item = texto_item;
    }

    public String getForma_correcta() {
        return forma_correcta;
    }

    public void setForma_correcta(String forma_correcta) {
        this.forma_correcta = forma_correcta;
    }

    public String getPista() {
        return pista;
    }

    public void setPista(String pista) {
        this.pista = pista;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

}
