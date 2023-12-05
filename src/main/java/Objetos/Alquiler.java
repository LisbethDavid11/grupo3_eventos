package Objetos;

import java.sql.Time;
import java.util.Date;

public class Alquiler {
    private int id;
    private int clienteId;
    private String descripcion;
    private String tipo;
    private Date fecha;
    private Time inicio;
    private Time fin;

    private String activo;

    public static String nombreTabla = "eventos";

    public Alquiler() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, clienteId, descripcion, tipo, fecha, inicio, fin};
    }

    public Alquiler(int id, int clienteId, String descripcion, String tipo, Date fecha, Time inicio, Time fin) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getInicio() {
        return inicio;
    }

    public void setInicio(Time inicio) {
        this.inicio = inicio;
    }

    public Time getFin() {
        return fin;
    }

    public void setFin(Time fin) {
        this.fin = fin;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getActivo() {
        return activo;
    }
}
