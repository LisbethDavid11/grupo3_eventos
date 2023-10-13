package Objetos;

public class ProductoPromocion {
    private String id;
    private String nombre;
    private int cantidad;
    private double precio;
    private double promocion;

    public ProductoPromocion(String idPrefix, int id, String nombre, int cantidad, double precio, double promocion) {
        this.id = idPrefix + "-" + id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.promocion = promocion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
