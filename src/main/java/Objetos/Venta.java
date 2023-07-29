package Objetos;

public class Venta {
    private int id;
    private String codigoVenta;
    private String fecha;
    private int clienteId;
    private int empleadoId;

    public static String nombreTabla = "ventas";

    public Venta() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, codigoVenta, fecha, clienteId, empleadoId};
    }

    public Venta(int id, String codigoVenta, String fecha, int clienteId, int empleadoId) {
        this.id = id;
        this.codigoVenta = codigoVenta;
        this.fecha = fecha;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
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

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }
}
