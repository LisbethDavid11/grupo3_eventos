package Objetos;

public class ResumenPromocion {
    private int id;
    private String descripcion;
    private int cantidad;
    private double precio;
    private double promocion;


    public static String nombreTabla = "resumen_promociones";

    public ResumenPromocion() {
    }

    public Object[] toTableRow() {
        return new Object[] {id, descripcion, cantidad, precio, promocion};
    }

    public ResumenPromocion(int id, String descripcion, int cantidad, double precio, double promocion) {
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precio = precio;
        this.promocion = promocion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public double getPromocion() {
        return promocion;
    }

    public void setPromocion(double promocion) {
        this.promocion = promocion;
    }
}
