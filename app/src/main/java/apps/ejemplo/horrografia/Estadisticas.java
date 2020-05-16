package apps.ejemplo.horrografia;


public class Estadisticas {

    private  int total_errores;

    private int total_aciertos;

    private String descripcion_nivel;

    public Estadisticas(){

    }

    public Estadisticas(int total_errores, int total_aciertos, String descripcion_nivel) {
        this.total_errores = total_errores;
        this.total_aciertos = total_aciertos;
        this.descripcion_nivel = descripcion_nivel;
    }

    public int getTotal_errores() {
        return total_errores;
    }

    public void setTotal_errores(int total_errores) {
        this.total_errores = total_errores;
    }

    public String getDescripcion_nivel() {
        return descripcion_nivel;
    }

    public void setDescripcion_nivel(String descripcion_nivel) {
        this.descripcion_nivel = descripcion_nivel;
    }

    public int getTotal_aciertos() {
        return total_aciertos;
    }

    public void setTotal_aciertos(int total_aciertos) {
        this.total_aciertos = total_aciertos;
    }
}
