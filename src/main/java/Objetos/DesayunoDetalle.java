package Objetos;

public class DesayunoDetalle {
    private int id;
    private int desayunoId;
    private int productoId;

    private int materialId;
    private int floristeriaId;
    private int globoId;
    private int tarjetaId;
    private int cantidad;
    private double precio;

    public static String nombreTabla = "detalles_desayunos";

    public DesayunoDetalle() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, desayunoId, productoId, cantidad, precio};
    }

    public DesayunoDetalle(int id, int desayunoId, int productoId, int cantidad, double precio){
        this.id = id;
        this.desayunoId = desayunoId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDesayunoId() {
        return desayunoId;
    }

    public void setDesayunoId(int desayunoId) {
        this.desayunoId = desayunoId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
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
