package Modelos;

import Objetos.Compra;
import Objetos.Conexion;
import Objetos.DetalleCompra;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ModeloCompras extends AbstractTableModel {
    private final String[] columnas = {"ID", "Código", "Fecha", "Proveedor", "Empleado", "Sub Total", "ISV", "Total"};
    private final List<Compra> compras;
    private final Conexion sql;
    private final Map<Integer, String> proveedores;
    private final Map<Integer, String> empleados;

    public ModeloCompras(List<Compra> compras, Conexion sql) {
        this.compras = compras;
        this.sql = sql;
        this.proveedores = new HashMap<>();
        this.empleados = new HashMap<>();
        cargarProveedores();
        cargarEmpleados();
    }

    @Override
    public int getRowCount() {
        return compras.size();
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
        Compra compra = compras.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;

            case 1: // Código
                return compra.getCodigoCompra();

            case 2: // Fecha
                return compra.getFecha();

            case 3: // Proveedor
                int proveedorId = compra.getProveedorId();
                String proveedorNombre = obtenerNombreProveedor(proveedorId);
                return proveedorNombre;

            case 4: // Empleado
                int empleadoId = compra.getEmpleadoId();
                String empleadoNombre = obtenerNombreEmpleado(empleadoId);
                return empleadoNombre;

            case 5: // Sub Total
                double subTotal = calcularSubTotal(compra);
                return String.format("L. %.2f", subTotal);

            case 6: // ISV
                double isv = calcularISV(compra);
                return String.format("L. %.2f", isv);

            case 7: // Total
                double total = calcularTotal(compra);
                return String.format("L. %.2f", total);

            default:
                return null;
        }
    }

    private String obtenerNombreProveedor(int proveedorId) {
        return proveedores.get(proveedorId);
    }

    private String obtenerNombreEmpleado(int empleadoId) {
        return empleados.get(empleadoId);
    }

    private void cargarProveedores() {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT id, empresaProveedora FROM proveedores")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("empresaProveedora");
                proveedores.put(id, nombre);
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

    private double calcularSubTotal(Compra compra) {
        double subTotal = 0;

        // Obtener los detalles de compra asociados a la compra actual
        List<DetalleCompra> detalles = obtenerDetallesCompra(compra.getId());

        // Calcular el subtotal sumando los subtotales de cada detalle de compra
        for (DetalleCompra detalle : detalles) {
            subTotal += detalle.getPrecio() * detalle.getCantidad();
        }

        return subTotal;
    }

    private double calcularISV(Compra compra) {
        double subTotal = calcularSubTotal(compra);
        return subTotal * 0.15;
    }

    private double calcularTotal(Compra compra) {
        double subTotal = calcularSubTotal(compra);
        return subTotal * 1.15;
    }

    private List<DetalleCompra> obtenerDetallesCompra(int compraId) {
        List<DetalleCompra> detalles = new ArrayList<>();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM detalles_compras WHERE compra_id = ?")) {
            preparedStatement.setInt(1, compraId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int detalleId = resultSet.getInt("id");
                int materialId = resultSet.getInt("material_id");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");

                DetalleCompra detalle = new DetalleCompra(detalleId, compraId, materialId, cantidad, precio);
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalles;
    }

}
