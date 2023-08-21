package Modelos;

import Objetos.Conexion;
import Objetos.VentaDetalle;
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

public class ModeloVenta extends AbstractTableModel {
    private final String[] columnas = {"N°", "Código", "Fecha", "Cliente", "Empleado", "Sub Total", "ISV", "Total"};
    private final List<Venta> ventas;
    private final Conexion sql;
    private final Map<Integer, String> clientes;
    private final Map<Integer, String> empleados;

    public ModeloVenta(List<Venta> ventas, Conexion sql) {
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
                return "  " + venta.getCodigoVenta();
            case 2: // Fecha
                return "  " + venta.getFecha();
            case 3: // Cliente
                int clienteId = venta.getClienteId();
                String clienteNombre = obtenerNombreCliente(clienteId);
                return "  " + clienteNombre;
            case 4: // Empleado
                int empleadoId = venta.getEmpleadoId();
                String empleadoNombre = obtenerNombreEmpleado(empleadoId);
                return "  " + empleadoNombre;
            case 5: // Sub Total
                double subTotal = obtenerSubTotal(venta.getId());
                return String.format("  L. %.2f", subTotal);
            case 6: // ISV
                double isv = obtenerISV(venta.getId());
                return String.format("  L. %.2f", isv);
            case 7: // Total
                double total = obtenerTotal(venta.getId());
                return String.format("  L. %.2f", total);
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

    private double obtenerSubTotal(int ventaId) {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT " +
                             "SUM(dv.cantidad * " +
                             "CASE " +
                             "WHEN dv.tipo_detalle = 'material' THEN m.precio " +
                             "WHEN dv.tipo_detalle = 'tarjeta' THEN t.precio_tarjeta " +
                             "WHEN dv.tipo_detalle = 'floristeria' THEN f.precio " +
                             "WHEN dv.tipo_detalle = 'manualidad' THEN ma.precio_manualidad " +
                             "WHEN dv.tipo_detalle = 'arreglo' THEN a.precio " +
                             "WHEN dv.tipo_detalle = 'desayuno' THEN d.precio_desayuno " +
                             "END) * 0.85 AS sub_total " +
                             "FROM detalles_ventas dv " +
                             "LEFT JOIN materiales m ON dv.tipo_detalle = 'material' AND dv.detalle_id = m.id " +
                             "LEFT JOIN tarjetas t ON dv.tipo_detalle = 'tarjeta' AND dv.detalle_id = t.id " +
                             "LEFT JOIN floristeria f ON dv.tipo_detalle = 'floristeria' AND dv.detalle_id = f.id " +
                             "LEFT JOIN manualidades ma ON dv.tipo_detalle = 'manualidad' AND dv.detalle_id = ma.id " +
                             "LEFT JOIN arreglos a ON dv.tipo_detalle = 'arreglo' AND dv.detalle_id = a.id " +
                             "LEFT JOIN desayunos d ON dv.tipo_detalle = 'desayuno' AND dv.detalle_id = d.id " +
                             "WHERE dv.venta_id = ?")) {

            preparedStatement.setInt(1, ventaId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("sub_total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0; // Si ocurre un error o no se encuentra el valor
    }

    private double obtenerISV(int ventaId) {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT " +
                             "SUM(dv.cantidad * " +
                             "CASE " +
                             "WHEN dv.tipo_detalle = 'material' THEN m.precio " +
                             "WHEN dv.tipo_detalle = 'tarjeta' THEN t.precio_tarjeta " +
                             "WHEN dv.tipo_detalle = 'floristeria' THEN f.precio " +
                             "WHEN dv.tipo_detalle = 'manualidad' THEN ma.precio_manualidad " +
                             "WHEN dv.tipo_detalle = 'arreglo' THEN a.precio " +
                             "WHEN dv.tipo_detalle = 'desayuno' THEN d.precio_desayuno " +
                             "END) * 0.15 AS isv " +
                             "FROM detalles_ventas dv " +
                             "LEFT JOIN materiales m ON dv.tipo_detalle = 'material' AND dv.detalle_id = m.id " +
                             "LEFT JOIN tarjetas t ON dv.tipo_detalle = 'tarjeta' AND dv.detalle_id = t.id " +
                             "LEFT JOIN floristeria f ON dv.tipo_detalle = 'floristeria' AND dv.detalle_id = f.id " +
                             "LEFT JOIN manualidades ma ON dv.tipo_detalle = 'manualidad' AND dv.detalle_id = ma.id " +
                             "LEFT JOIN arreglos a ON dv.tipo_detalle = 'arreglo' AND dv.detalle_id = a.id " +
                             "LEFT JOIN desayunos d ON dv.tipo_detalle = 'desayuno' AND dv.detalle_id = d.id " +
                             "WHERE dv.venta_id = ?")) {

            preparedStatement.setInt(1, ventaId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("isv");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0; // Si ocurre un error o no se encuentra el valor
    }

    private double obtenerTotal(int ventaId) {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT " +
                             "SUM(dv.cantidad * " +
                             "CASE " +
                             "WHEN dv.tipo_detalle = 'material' THEN m.precio " +
                             "WHEN dv.tipo_detalle = 'tarjeta' THEN t.precio_tarjeta " +
                             "WHEN dv.tipo_detalle = 'floristeria' THEN f.precio " +
                             "WHEN dv.tipo_detalle = 'manualidad' THEN ma.precio_manualidad " +
                             "WHEN dv.tipo_detalle = 'arreglo' THEN a.precio " +
                             "WHEN dv.tipo_detalle = 'desayuno' THEN d.precio_desayuno " +
                             "END) * 0.85 + " +
                             "SUM(dv.cantidad * " +
                             "CASE " +
                             "WHEN dv.tipo_detalle = 'material' THEN m.precio " +
                             "WHEN dv.tipo_detalle = 'tarjeta' THEN t.precio_tarjeta " +
                             "WHEN dv.tipo_detalle = 'floristeria' THEN f.precio " +
                             "WHEN dv.tipo_detalle = 'manualidad' THEN ma.precio_manualidad " +
                             "WHEN dv.tipo_detalle = 'arreglo' THEN a.precio " +
                             "WHEN dv.tipo_detalle = 'desayuno' THEN d.precio_desayuno " +
                             "END) * 0.15 AS total " +
                             "FROM detalles_ventas dv " +
                             "LEFT JOIN materiales m ON dv.tipo_detalle = 'material' AND dv.detalle_id = m.id " +
                             "LEFT JOIN tarjetas t ON dv.tipo_detalle = 'tarjeta' AND dv.detalle_id = t.id " +
                             "LEFT JOIN floristeria f ON dv.tipo_detalle = 'floristeria' AND dv.detalle_id = f.id " +
                             "LEFT JOIN manualidades ma ON dv.tipo_detalle = 'manualidad' AND dv.detalle_id = ma.id " +
                             "LEFT JOIN arreglos a ON dv.tipo_detalle = 'arreglo' AND dv.detalle_id = a.id " +
                             "LEFT JOIN desayunos d ON dv.tipo_detalle = 'desayuno' AND dv.detalle_id = d.id " +
                             "WHERE dv.venta_id = ?")) {

            preparedStatement.setInt(1, ventaId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0; // Si ocurre un error o no se encuentra el valor
    }

    private List<VentaDetalle> obtenerDetallesVenta(int ventaId) {
        List<VentaDetalle> detalles = new ArrayList<>();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM detalles_ventas WHERE venta_id = ?")) {

            preparedStatement.setInt(1, ventaId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int detalleId = resultSet.getInt("id");
                    String tipoDetalle = resultSet.getString("tipo_detalle");
                    int detalleEspecificoId = resultSet.getInt("detalle_id");
                    int cantidad = resultSet.getInt("cantidad");
                    double precio = resultSet.getDouble("precio");

                    // Aquí puedes agregar lógica para determinar el tipo de detalle específico y crear el objeto VentaDetalle adecuado
                    VentaDetalle detalle = new VentaDetalle(detalleId, ventaId, tipoDetalle, detalleEspecificoId, cantidad, precio);
                    detalles.add(detalle);
                }
            }

        } catch (SQLException e) {
            // Manejar la excepción de manera adecuada, como imprimir el error o lanzar una excepción personalizada
            e.printStackTrace(); // Imprime el error en la consola
            // Puedes lanzar una excepción personalizada aquí si lo prefieres
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
