package Objetos;

import javax.swing.*;

public class Tarjeta {
    private int id;
    private String ocasion;
    private String descripcion;
    private ImageIcon imagen;
    private String disponible;
    private double precio_tarjeta;
    private double mano_obra;

    public static String nombreTabla = "tarjetas";

    public Tarjeta() {
    }

    public Object[] toTableRow() {
        return new Object[] {id, ocasion, descripcion, imagen, disponible, precio_tarjeta, mano_obra};
    }

    public Tarjeta(int id, String ocasion, String descripcion, ImageIcon imagen, String disponible, double precio_tarjeta, double mano_obra) {
        this.id = id;
        this.ocasion = ocasion;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.disponible = disponible;
        this.precio_tarjeta = precio_tarjeta;
        this.mano_obra = mano_obra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOcasion() {
        return ocasion;
    }

    public void setOcasion(String ocasion) {
        this.ocasion = ocasion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public double getPrecio_tarjeta() {
        return precio_tarjeta;
    }

    public void setPrecio_tarjeta(double precio_tarjeta) {
        this.precio_tarjeta = precio_tarjeta;
    }

    public double getMano_obra() {
        return mano_obra;
    }

    public void setMano_obra(double mano_obra) {
        this.mano_obra = mano_obra;
    }
}
