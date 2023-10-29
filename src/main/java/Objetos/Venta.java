package Objetos;

public class Venta {
    private int id;
    private String codigoVenta;
    private String fecha;
    private int clienteId;
    private int usuarioId;

    public static String nombreTabla = "ventas";

    public Venta() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, codigoVenta, fecha, clienteId, usuarioId};
    }

    public Venta(int id, String codigoVenta, String fecha, int clienteId, int empleadoId) {
        this.id = id;
        this.codigoVenta = codigoVenta;
        this.fecha = fecha;
        this.clienteId = clienteId;
        this.usuarioId = usuarioId;
    }

    // getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoVenta() {
        return codigoVenta;
    }

    public void setCodigoVenta(String codigoVenta) {
        this.codigoVenta = codigoVenta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }
}
