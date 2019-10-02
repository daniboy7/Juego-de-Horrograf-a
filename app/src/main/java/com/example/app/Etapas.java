package com.example.app;

public class Etapas {
    private int id;
    private String descripcion;
    private Double valor_error;
    private Double valor_puntuacion;
    private int errores_no_permitidos;
    private int minutos;
    private int numero_items;

    public Etapas(){

    }

    public Etapas(int id, String descripcion, Double valor_error, Double valor_puntuacion, int errores_no_permitidos, int minutos, int numero_items) {
        this.id = id;
        this.descripcion = descripcion;
        this.valor_error = valor_error;
        this.valor_puntuacion = valor_puntuacion;
        this.errores_no_permitidos = errores_no_permitidos;
        this.minutos = minutos;
        this.numero_items = numero_items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValor_error() {
        return valor_error;
    }

    public void setValor_error(Double valor_error) {
        this.valor_error = valor_error;
    }

    public Double getValor_puntuacion() {
        return valor_puntuacion;
    }

    public void setValor_puntuacion(Double valor_puntuacion) {
        this.valor_puntuacion = valor_puntuacion;
    }

    public int getErrores_no_permitidos() {
        return errores_no_permitidos;
    }

    public void setErrores_no_permitidos(int errores_no_permitidos) {
        this.errores_no_permitidos = errores_no_permitidos;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public int getNumero_items() {
        return numero_items;
    }

    public void setNumero_items(int numero_items) {
        this.numero_items = numero_items;
    }

    @Override
    public String toString(){
        return id+"";
    }

}
