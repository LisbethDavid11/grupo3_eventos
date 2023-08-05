package Objetos;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Tarjeta {
    private int id;
    private String ocasion;
    private String descripcion;
    private String imagen;
    private String disponible;
    private int cantidad;
    private double precio_tarjeta;
    private double mano_obra;

    public static String nombreTabla = "tarjetas";

    private List<TarjetaDetalle> detalles;

    public Tarjeta() {
        this.detalles = new ArrayList<>();
    }

    public Object[] toTableRow() {
        return new Object[] {id, ocasion, descripcion, imagen, disponible, cantidad, precio_tarjeta, mano_obra};
    }

    public Tarjeta(int id, String ocasion, String descripcion, String imagen, String disponible, int cantidad, double precio_tarjeta, double mano_obra) {
        this.id = id;
        this.ocasion = ocasion;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.disponible = disponible;
        this.cantidad = cantidad;
        this.precio_tarjeta = precio_tarjeta;
        this.mano_obra = mano_obra;
        this.detalles = new ArrayList<>();
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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

    public List<TarjetaDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<TarjetaDetalle> detalles) {
        this.detalles = detalles;
    }

    public void addDetalles(TarjetaDetalle detalles) {
        this.detalles.add(detalles);
    }
}
