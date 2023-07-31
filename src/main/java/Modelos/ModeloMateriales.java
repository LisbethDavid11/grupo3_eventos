package Modelos;
import Objetos.Conexion;
import Objetos.Material;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloMateriales extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre", "Proveedor", "Disponible", "Existencia", "Precio", "Total"};
    private final List<Material> materiales;
    private final Conexion sql;
    private final Map<Integer, String> proveedores;

    public ModeloMateriales(List<Material> materiales, Conexion sql) {
        this.materiales = materiales;
        this.sql = sql;
        this.proveedores = new HashMap<>();
        cargarProveedores();
    }

    @Override
    public int getRowCount() {
        return materiales.size();
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
        Material material = materiales.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return material.getNombre();
            case 2: // Proveedor
                int proveedorId = material.getProveedorId();
                String proveedorNombre = obtenerNombreProveedor(proveedorId);
                return proveedorNombre;
            case 3: // Disponible
                return material.getDisponible();
            case 4: // Nombre
                return material.getCantidad();
            case 5: // Precio
                double precio = material.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("L. %,.2f", precio);
                return precioFormateado;
            case 6: // Total (Precio * Cantidad)
                int cantidad = material.getCantidad();
                double total = material.getPrecio() * cantidad;
                String totalFormateado = String.format("L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }

    private String obtenerNombreProveedor(int proveedorId) {
        return proveedores.get(proveedorId);
    }

    private void cargarProveedores() {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT id, empresaProveedora FROM Proveedores")) {

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
}
