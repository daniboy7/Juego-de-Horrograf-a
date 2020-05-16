package apps.ejemplo.horrografia;

public class Item {
    private int id;
    private String texto_item;
    private String forma_correcta;
    private String pista;
    private int nivel;
    private String descripcion_nivel;
    private int etapa;
    private String descripcion_etapa;
    private Double valor_error;
    private Double valor_puntuacion;
    private int errores_no_permitidos;
    private int minutos;
    private int numero_items;

    public Item(){

    }

    public Item(int id, String texto_item, String forma_correcta, String pista, int nivel, String descripcion_nivel, int etapa, String descripcion_etapa, Double valor_error, Double valor_puntuacion, int errores_no_permitidos, int minutos, int numero_items) {
        this.id = id;
        this.texto_item = texto_item;
        this.forma_correcta = forma_correcta;
        this.pista = pista;
        this.nivel = nivel;
        this.descripcion_nivel = descripcion_nivel;
        this.etapa = etapa;
        this.descripcion_etapa = descripcion_etapa;
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

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getDescripcion_nivel() {
        return descripcion_nivel;
    }

    public void setDescripcion_nivel(String descripcion_nivel) {
        this.descripcion_nivel = descripcion_nivel;
    }

    public int getEtapa() {
        return etapa;
    }

    public void setEtapa(int etapa) {
        this.etapa = etapa;
    }

    public String getDescripcion_etapa() {
        return descripcion_etapa;
    }

    public void setDescripcion_etapa(String descripcion_etapa) {
        this.descripcion_etapa = descripcion_etapa;
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
}
