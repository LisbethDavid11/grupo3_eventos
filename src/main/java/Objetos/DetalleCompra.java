package Objetos;

public class DetalleCompra {
    private int id;
    private int compraId;
    private int materialId;
    private int cantidad;
    private double precio;

    public static String nombreTabla = "detalles_compras";

    public DetalleCompra() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, compraId, materialId, cantidad, precio};
    }
    public DetalleCompra(int id, int compraId, int materialId, int cantidad, double precio){
        this.id = id;
        this.compraId = compraId;
        this.materialId = materialId;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public double calcularSubtotal() {
        return precio * cantidad;
    }

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
}
