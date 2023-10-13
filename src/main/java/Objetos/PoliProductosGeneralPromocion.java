package Objetos;

public class PoliProductosGeneralPromocion implements PoliProductoPromocion{
    public int idDetalle;
    public int id;
    public String nombre;
    public int cantidad;
    public double precio;
    public double promocion;
    public String tipo;

    public PoliProductosGeneralPromocion() {
    }

    public PoliProductosGeneralPromocion(int idDetalle, int id, String nombre, int cantidad, double precio, String tipo, double promocion) {
        this.idDetalle = idDetalle;
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.tipo = tipo;
        this.promocion = promocion;
    }

    @Override
    public int getIdDetalle() {
        return this.idDetalle;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public int getCantidad() {
        return this.cantidad;
    }

    @Override
    public double getPrecio() {
        return this.precio;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String getTipo() {
        return this.tipo;
    }

    @Override
    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    @Override
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPromocion() {
        return promocion;
    }

    public void setPromocion(double promocion) {
        this.promocion = promocion;
    }
}
