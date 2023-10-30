package Objetos;

public class Evento {
    private int id;
    private int clienteId;
    private String direccion;
    private String tipo;
    private java.util.Date fecha;
    private java.sql.Time inicio;
    private java.sql.Time fin;
    private String estado;
    private String codigoEvento;
    private int usuario_id;

    public static String nombreTabla = "eventos";

    public Evento() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, clienteId, direccion, tipo, fecha, inicio, fin, codigoEvento};
    }

    public Evento(int id, int clienteId, String direccion, String tipo, java.util.Date fecha, java.sql.Time inicio, java.sql.Time fin, String estado, int usuario_id, String codigoEvento) {
        this.id = id;
        this.clienteId = clienteId;
        this.direccion = direccion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.inicio = inicio;
        this.fin = fin;
        this.estado = estado;
        this.usuario_id = usuario_id;
        this.codigoEvento = codigoEvento;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getCodigoEvento() {
        return codigoEvento;
    }

    public void setCodigoEvento(String codigoEvento) {
        this.codigoEvento = codigoEvento;
    }
}
