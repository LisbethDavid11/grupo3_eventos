package Objetos;

public class Compra {
    private int id;
    private String codigoCompra;
    private String fecha;
    private int proveedorId;
    private int empleadoId;

    public static String nombreTabla = "compras";
    public Compra() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, codigoCompra, fecha, proveedorId, empleadoId};
    }

    public Compra(int id, String codigoCompra, String fecha, int proveedorId, int empleadoId) {
        this.id = id;
        this.codigoCompra = codigoCompra;
        this.fecha = fecha;
        this.proveedorId = proveedorId;
        this.empleadoId = empleadoId;
    }

    // getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoCompra() {
        return codigoCompra;
    }

    public void setCodigoCompra(String codigoCompra) {
        this.codigoCompra = codigoCompra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }
}
