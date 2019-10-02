package com.example.app;

public class Usuarios {
    private int id_usuario;
    private String usuario;
    private String password;
    private String correo;
    private String nombre;
    private String apellidos;
    private int edad;
    private String tipo;

    public  Usuarios(){

    }

    public Usuarios(int id_usuario, String usuario, String password, String correo, String nombre, String apellidos, int edad, String tipo) {
        this.id_usuario = id_usuario;
        this.usuario = usuario;
        this.password = password;
        this.correo = correo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.tipo = tipo;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
