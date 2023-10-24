package Objetos;

public class Alquiler {
    private int id;
    private int clienteId;
    private String descripcion;
    private String tipo;
    private java.util.Date fecha;
    private java.sql.Time inicio;
    private java.sql.Time fin;

    public static String nombreTabla = "eventos";

    public Alquiler() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, clienteId, descripcion, tipo, fecha, inicio, fin};
    }

    public Alquiler(int id, int clienteId, String descripcion, String tipo, java.util.Date fecha, java.sql.Time inicio, java.sql.Time fin) {
        this.id = id;
        this.clienteId = clienteId;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public java.util.Date getFecha() {
        return fecha;
    }

    public void setFecha(java.util.Date fecha) {
        this.fecha = fecha;
    }

    public java.sql.Time getInicio() {
        return inicio;
    }

    public void setInicio(java.sql.Time inicio) {
        this.inicio = inicio;
    }

    public java.sql.Time getFin() {
        return fin;
    }

    public void setFin(java.sql.Time fin) {
        this.fin = fin;
    }
}
