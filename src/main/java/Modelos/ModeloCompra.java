package Modelos;

import Objetos.Compra;
import Objetos.Conexion;
import Objetos.CompraDetalle;
import Objetos.Material;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class ModeloCompra extends AbstractTableModel {
    private final String[] columnas = {"N°", "Código", "Fecha", "Proveedor", "Empleado", "Sub Total", "ISV", "Total"};
    private final List<Compra> compras;
    private final Conexion sql;
    private final Map<Integer, String> proveedores;
    private final Map<Integer, String> empleados;

    public ModeloCompra(List<Compra> compras, Conexion sql) {
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
                return "   " + compra.getCodigoCompra();

            case 2: // Fecha
                return "   " + compra.getFecha();

            case 3: // Proveedor
                int proveedorId = compra.getProveedorId();
                String proveedorNombre = obtenerNombreProveedor(proveedorId);
                return "   " + proveedorNombre;

            case 4: // Empleado
                int empleadoId = compra.getEmpleadoId();
                String empleadoNombre = obtenerNombreEmpleado(empleadoId);
                return "   " + empleadoNombre;

            case 5: // Sub Total
                double subTotal = calcularSubTotal(compra);
                return String.format("  L. %.2f", subTotal);

            case 6: // ISV
                double isv = calcularISV(compra);
                return String.format("  L. %.2f", isv);

            case 7: // Total
                double total = calcularTotal(compra);
                return String.format("  L. %.2f", total);

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
        List<CompraDetalle> detalles = obtenerDetallesCompra(compra.getId());

        // Calcular el subtotal sumando los subtotales de cada detalle de compra
        for (CompraDetalle detalle : detalles) {
            subTotal += detalle.getPrecio() * detalle.getCantidad();
        }

        return subTotal;
    }

    private double calcularISV(Compra compra) {
        double totalISV = 0.0;
        List<CompraDetalle> detalles = obtenerDetallesCompra(compra.getId());

        for (CompraDetalle detalle : detalles) {
            Material material = obtenerMaterialPorId(detalle.getMaterialId());

            if (material != null && !material.isExento()) {
                double montoDetalle = detalle.getPrecio() * detalle.getCantidad();
                double isvDetalle = montoDetalle * 0.15;
                totalISV += isvDetalle;
            }
        }

        return totalISV;
    }
    private double calcularTotal(Compra compra) {
        double subTotal = calcularSubTotal(compra);
        double total = 0.0;
        double impuesto = 0.15; // Impuesto sobre ventas (ISV) del 15%

        // Obtener los detalles de compra asociados a la compra actual
        List<CompraDetalle> detalles = obtenerDetallesCompra(compra.getId());

        // Calcular el total sumando los montos de los detalles de compra
        for (CompraDetalle detalle : detalles) {
            // Obtener el material asociado a este detalle
            Material material = obtenerMaterialPorId(detalle.getMaterialId());
            double montoDetalle = detalle.getPrecio() * detalle.getCantidad();

            if (material != null && material.isExento()) {
                // Si el material es exento, no se aplica ISV
                total += montoDetalle;
            } else {
                // Si el material no es exento, se aplica el 15% de ISV
                total += montoDetalle * (1 + impuesto);
            }
        }

        return total;
    }


    public Material obtenerMaterialPorId(int materialId) {
        Material material = null;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT nombre, exento FROM materiales WHERE id = ?")) {

            preparedStatement.setInt(1, materialId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombreMaterial = resultSet.getString("nombre");
                boolean exento = resultSet.getBoolean("exento");

                // Aquí utilizamos el constructor apropiado para crear un objeto Material.
                // Los campos que no se obtienen de la base de datos se inicializan con valores predeterminados.
                material = new Material(materialId, nombreMaterial, 0,0, null, null, exento, 0);
            }

        } catch (SQLException e) {
            // En caso de error en la conexión o consulta, se muestra un mensaje de error.
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }

        return material;
    }

    private List<CompraDetalle> obtenerDetallesCompra(int compraId) {
        List<CompraDetalle> detalles = new ArrayList<>();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM detalles_compras WHERE compra_id = ?")) {
            preparedStatement.setInt(1, compraId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int detalleId = resultSet.getInt("id");
                int materialId = resultSet.getInt("material_id");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");

                CompraDetalle detalle = new CompraDetalle(detalleId, compraId, materialId, cantidad, precio);
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalles;
    }

    private boolean obtenerExentoMaterial(int materialId) {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT exento FROM materiales WHERE id = ?")) {
            preparedStatement.setInt(1, materialId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBoolean("exento");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Por defecto, se asume que no es exento si ocurre un error en la consulta
    }
}
