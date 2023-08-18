package Objetos;

import javax.swing.*;

public class Manualidad {
    private int id;
    private ImageIcon imagen;
    private String nombre;
    private String descripcion;
    private String tipo;
    private int cantidad;
    private double precio_manualidad;
    private double mano_obra;

    public static String nombreTabla = "manualidades";

    public Manualidad() {
    }

    public Object[] toTableRow() {
        return new Object[] {id, imagen, nombre, descripcion, tipo, cantidad, precio_manualidad, mano_obra};
    }

    public Manualidad(int id, ImageIcon imagen, String nombre, String descripcion, String tipo, int cantidad, double precio_manualidad, double mano_obra) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precio_manualidad = precio_manualidad;
        this.mano_obra = mano_obra;
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

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_manualidad() {
        return precio_manualidad;
    }

    public void setPrecio_manualidad(double precio_manualidad) {
        this.precio_manualidad = precio_manualidad;
    }

    public double getMano_obra() {
        return mano_obra;
    }

    public void setMano_obra(double mano_obra) {
        this.mano_obra = mano_obra;
    }
}
