package Objetos;

import Modelos.ModeloMobiliario;
import com.mysql.cj.xdevapi.Client;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Mobiliario {
    private int id;
    private String nombreMobiliario;
    private String color;
    private String descripcion;
    private int cantidad;
    private float precioUnitario;
    private String imagen;
    private boolean disponibilidad;

    public static String nombreTabla = "mobiliario";

    public Mobiliario() {
    }

    public Object[] toTableRow() {
        return new Object[] {
                id,nombreMobiliario,color,descripcion,cantidad,
        precioUnitario,imagen,disponibilidad

        };
    }

    public Mobiliario(int id, String nombreMobiliario, String color, String descripcion, int cantidad, float precioUnitario, String imagen, boolean disponibilidad) {
        this.id = id;
        this.nombreMobiliario = nombreMobiliario;
        this.color = color;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.imagen = imagen;
        this.disponibilidad = disponibilidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMobiliario() {
        return nombreMobiliario;
    }

    public void setNombreMobiliario(String nombreMobiliario) {
        this.nombreMobiliario = nombreMobiliario;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
