package apps.ejemplo.horrografia;

public class Partidas {
    private int id_partida;
    private String usuario;
    private String fecha;
    private int nivel;
    private String estado;
    private Double puntuacion;
    private int errores;
    private int aciertos;

    public Partidas(){

    }

    public Partidas(int id_partida, String usuario, String fecha, int nivel, String estado, Double puntuacion, int errores, int aciertos) {
        this.id_partida = id_partida;
        this.usuario = usuario;
        this.fecha = fecha;
        this.nivel = nivel;
        this.estado = estado;
        this.puntuacion = puntuacion;
        this.errores = errores;
        this.aciertos = aciertos;
    }

    public int getId_partida() {
        return id_partida;
    }

    public void setId_partida(int id_partida) {
        this.id_partida = id_partida;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getErrores() {
        return errores;
    }

    public void setErrores(int errores) {
        this.errores = errores;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }
}
