package com.example.app;

public class Etapas {
    private int id;
    private String descripcion;
    private Double valor_error;
    private Double valor_puntuacion;
    private int errores_no_permitidos;
    private int minutos;

    public Etapas(){

    }

    public Etapas(int id, String descripcion,Double valor_error,Double valor_puntuacion, int errores_no_permitidos,int minutos){
        this.id = id;
        this.descripcion = descripcion;
        this.valor_error = valor_error;
        this.valor_puntuacion = valor_puntuacion;
        this.errores_no_permitidos = errores_no_permitidos;
        this.minutos = minutos;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId(){
        return id;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }

    @Override
    public String toString(){
        return id+"";
    }

}
