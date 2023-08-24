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
    private String tipoEvento;
    private int cliente;
    private int empleado;
    private String descripcion;
    private int cantidad;
    private float precioUnitario;
    private String fechaEntrega;
    private String imagen;
    private boolean disponibilidad;

    public static String nombreTabla = "mobiliario";

    public Mobiliario() {
    }

    public Object[] toTableRow() {
        return new Object[] {
                id,nombreMobiliario,tipoEvento,cliente,empleado,descripcion,cantidad,
        precioUnitario,fechaEntrega,imagen,disponibilidad

        };
    }

    public Mobiliario(int id, String nombreMobiliario, String tipoEvento, int cliente, int empleado, String descripcion, int cantidad, float precioUnitario, String fechaEntrega, String imagen, boolean disponibilidad) {
        this.id = id;
        this.nombreMobiliario = nombreMobiliario;
        this.tipoEvento = tipoEvento;
        this.cliente = cliente;
        this.empleado = empleado;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fechaEntrega = fechaEntrega;
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

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getEmpleado() {
        return empleado;
    }

    public void setEmpleado(int empleado) {
        this.empleado = empleado;
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

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
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

    public Cliente traer_cliente(Conexion sql) {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM eventos.clientes where id = ?;"
             )
        ) {
            preparedStatement.setInt(1, getCliente());

            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellido(resultSet.getString("apellido"));
                cliente.setIdentidad(resultSet.getString("identidad"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setDomicilio(resultSet.getString("domicilio"));
                cliente.setTipo_cliente(resultSet.getString("tipo_cliente"));
                return cliente;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }


        return new Cliente();
    }

    public Empleado traer_empleado(Conexion sql) {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM Empleados WHERE id = ?;"
             )
        ) {
            preparedStatement.setInt(1, getEmpleado());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Empleado empleado1 = new Empleado();
                empleado1.setId(resultSet.getInt("id"));
                empleado1.setIdentidad(resultSet.getString("Identidad"));
                empleado1.setNombres(resultSet.getString("Nombres"));
                empleado1.setApellidos(resultSet.getString("Apellidos"));
                empleado1.setTelefono(resultSet.getString("Telefono"));
                return empleado1;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }


        return new Empleado();
    }
}
