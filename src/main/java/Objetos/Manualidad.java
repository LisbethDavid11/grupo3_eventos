package Objetos;

import javax.swing.*;

public class Manualidad {
    private int id;
    private ImageIcon imagen;
    private String nombre;
    private String descripcion;
    private String tipo;
    private double precio;

    public static String nombreTabla = "manualidades";

    public Manualidad() {
    }

    public Object[] toTableRow() {
        return new Object[] {id, imagen, nombre, descripcion, tipo, precio};
    }

    public Manualidad(int id, ImageIcon imagen, String nombre, String descripcion, String tipo, double precio) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
