package Objetos;

import javax.swing.*;

public class Floristeria {
    private int id;
    private ImageIcon imagen;
    private String nombre;
    private int cantidad;
    private double precio;
    private int proveedorId;

    public static String nombreTabla = "floristeria";

    public Floristeria() {
    }

    public Object[] toTableRow() {
        return new Object[] {id, imagen, nombre, cantidad, precio, proveedorId};
    }

    public Floristeria(int id, ImageIcon imagen, String nombre, int cantidad, double precio,  int proveedorId) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.proveedorId = proveedorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
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

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }
}
