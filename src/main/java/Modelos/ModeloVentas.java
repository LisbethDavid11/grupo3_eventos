package Modelos;

import Objetos.Conexion;
import Objetos.DetalleVenta;
import Objetos.Venta;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloVentas extends AbstractTableModel {
    private final String[] columnas = {"N°", "Código", "Fecha", "Cliente", "Empleado", "Sub Total", "ISV", "Total"};
    private final List<Venta> ventas;
    private final Conexion sql;
    private final Map<Integer, String> clientes;
    private final Map<Integer, String> empleados;

    public ModeloVentas(List<Venta> ventas, Conexion sql) {
        this.ventas = ventas;
        this.sql = sql;
        this.clientes = new HashMap<>();
        this.empleados = new HashMap<>();
        cargarClientes();
        cargarEmpleados();
    }

    @Override
    public int getRowCount() {
        return ventas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Venta venta = ventas.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;

            case 1: // Código
                return venta.getCodigoVenta();

            case 2: // Fecha
                return venta.getFecha();

            case 3: // Cliente
                int clienteId = venta.getClienteId();
                String clienteNombre = obtenerNombreCliente(clienteId);
                return clienteNombre;

            case 4: // Empleado
                int empleadoId = venta.getEmpleadoId();
                String empleadoNombre = obtenerNombreEmpleado(empleadoId);
                return empleadoNombre;

            case 5: // Sub Total
                double subTotal = calcularSubTotal(venta);
                return String.format("L. %.2f", subTotal);

            case 6: // ISV
                double isv = calcularISV(venta);
                return String.format("L. %.2f", isv);

            case 7: // Total
                double total = calcularTotal(venta);
                return String.format("L. %.2f", total);

            default:
                return null;
        }
    }

    private String obtenerNombreCliente(int clienteId) {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT id, nombre, apellido FROM clientes WHERE id = ?")) {
            preparedStatement.setInt(1, clienteId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombres = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellido");
                return nombres + " " + apellidos;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Devuelve null si no se encuentra el cliente o hay un error
    }

    private String obtenerNombreEmpleado(int empleadoId) {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT id, Nombres, Apellidos FROM Empleados WHERE id = ?")) {
            preparedStatement.setInt(1, empleadoId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombres = resultSet.getString("Nombres");
                String apellidos = resultSet.getString("Apellidos");
                return nombres + " " + apellidos;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Devuelve null si no se encuentra el empleado o hay un error
    }

    private double calcularSubTotal(Venta venta) {
        double subTotal = 0.0;

        // Obtener los detalles de venta asociados a la venta actual
        List<DetalleVenta> detalles = obtenerDetallesVenta(venta.getId());

        // Calcular el subtotal sumando el subtotal de cada detalle de venta
        for (DetalleVenta detalle : detalles) {
            subTotal += detalle.getPrecio() * detalle.getCantidad();
        }

        // Aplicar el descuento del 15% al subtotal
        subTotal *= 0.85;

        return subTotal;
    }

    private double calcularISV(Venta venta) {
        double totalISV = 0.0;

        // Obtener los detalles de venta asociados a la venta actual
        List<DetalleVenta> detalles = obtenerDetallesVenta(venta.getId());

        // Calcular el ISV sumando los montos de los detalles de venta
        for (DetalleVenta detalle : detalles) {
            // Calcular el monto del ISV para este detalle y agregarlo al total
            double montoDetalle = detalle.getPrecio() * detalle.getCantidad();
            double isvDetalle = montoDetalle * 0.15;
            totalISV += isvDetalle;
        }

        return totalISV;
    }

    private double calcularTotal(Venta venta) {
        // Calcular subtotal y ISV
        double subTotal = calcularSubTotal(venta);
        double isv = calcularISV(venta);

        // Sumar subtotal e ISV para obtener el total
        double total = subTotal + isv;

        return total;
    }



    private List<DetalleVenta> obtenerDetallesVenta(int ventaId) {
        List<DetalleVenta> detalles = new ArrayList<>();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM detalles_ventas WHERE venta_id = ?")) {
            preparedStatement.setInt(1, ventaId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int detalleId = resultSet.getInt("id");
                int materialId = resultSet.getInt("material_id");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");

                DetalleVenta detalle = new DetalleVenta(detalleId, ventaId, materialId, cantidad, precio);
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalles;
    }

    private void cargarClientes() {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT id, nombre, apellido FROM clientes")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombreCompleto = resultSet.getString("nombre") + " " + resultSet.getString("apellido");
                clientes.put(id, nombreCompleto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarEmpleados() {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT id, nombres, apellidos FROM empleados")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombreCompleto = resultSet.getString("nombres") + " " + resultSet.getString("apellidos");
                empleados.put(id, nombreCompleto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
