package Objetos;

import javax.swing.ImageIcon;

public class Arreglo {
    private int id;
    private String nombre;
    private ImageIcon imagen;
    private double precio;
    private double cantidad;
    private String disponible;

    public static String nombreTabla = "arreglos";

    public Arreglo() {
    }

    public Object[] toTableRow() {
        return new Object[] {id, nombre, imagen, cantidad, precio, disponible };
    }

    public Arreglo(int id, String nombre, ImageIcon imagen, int cantidad, double precio, String disponible) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.cantidad = cantidad;
        this.precio = precio;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

}
