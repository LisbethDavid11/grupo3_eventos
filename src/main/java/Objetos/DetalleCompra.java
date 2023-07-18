package Objetos;

public class DetalleCompra {
    private int id;
    private int compraId;
    private int materialId;
    private int cantidad;
    private double precio;
    private boolean exento;

    public static String nombreTabla = "detalles_compras";

    public DetalleCompra() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, compraId, materialId, cantidad, precio};
    }
    public DetalleCompra(int id, int compraId, int materialId, int cantidad, double precio, boolean exento){
        this.id = id;
        this.compraId = compraId;
        this.materialId = materialId;
        this.cantidad = cantidad;
        this.precio = precio;
        this.exento = exento;
    }

    public double calcularSubtotal() {
        return precio * cantidad;
    }

    public double calcularISV() {
        return calcularSubtotal() * 0.15;
    }

    public double calcularTotal() {
        return calcularSubtotal() * 1.15;
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
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

    public boolean isExento() {
        return exento;
    }
}
