package Objetos;

public class VentaDetalle {
    private int id;
    private int ventaId;
    private String tipoDetalle;
    private int detalleId;
    private int cantidad;
    private double precio;

    public static String nombreTabla = "detalles_ventas";

    public VentaDetalle() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, ventaId, tipoDetalle, detalleId, cantidad, precio};
    }

    public VentaDetalle(int id, int ventaId, String tipoDetalle, int detalleId, int cantidad, double precio) {
        this.id = id;
        this.ventaId = ventaId;
        this.tipoDetalle = tipoDetalle;
        this.detalleId = detalleId;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public String getTipoDetalle() {
        return tipoDetalle;
    }

    public void setTipoDetalle(String tipoDetalle) {
        this.tipoDetalle = tipoDetalle;
    }

    public int getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(int detalleId) {
        this.detalleId = detalleId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
