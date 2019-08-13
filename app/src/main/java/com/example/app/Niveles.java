package com.example.app;

public class Niveles {
    private  int id;
    private  String descripcion;

    public Niveles(){

    }

    public Niveles(int id, String descripcion){
        this.id = id;
        this.descripcion = descripcion;
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
