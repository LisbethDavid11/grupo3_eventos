package Objetos;

public class Promocion {
    private int id;
    private String descripcion;
    private java.util.Date inicio;
    private java.util.Date fin;

    public static String nombreTabla = "promociones";

    public Promocion() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, descripcion, inicio, fin};
    }

    public Promocion(int id, String descripcion, java.util.Date inicio, java.util.Date fin) {
        this.id = id;
        this.descripcion = descripcion;
        this.inicio = inicio;
        this.fin = fin;
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

    public java.util.Date getInicio() {
        return inicio;
    }

    public void setInicio(java.util.Date inicio) {
        this.inicio = inicio;
    }

    public java.util.Date getFin() {
        return fin;
    }

    public void setFin(java.util.Date fin) {
        this.fin = fin;
    }
}
