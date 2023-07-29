package Objetos;

public class DetalleVenta {
    private int id;
    private int ventaId;
    private int materialId;
    private int cantidad;
    private double precio;

    public static String nombreTabla = "detalles_ventas";

    public DetalleVenta() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, ventaId, materialId, cantidad, precio};
    }

    public DetalleVenta(int id, int ventaId, int materialId, int cantidad, double precio) {
        this.id = id;
        this.ventaId = ventaId;
        this.materialId = materialId;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public double calcularSubtotal() {
        return precio * cantidad;
    }

    // getters y setters

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

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
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
