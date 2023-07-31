package Modelos;
import Objetos.Conexion;
import Objetos.Floristeria;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloFloristeria extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre de la flor", "Proveedor", "Existencia", "Precio", "Total"};
    private final List<Floristeria> floristerias;
    private final Conexion sql;
    private final Map<Integer, String> proveedores;

    public ModeloFloristeria(List<Floristeria> floristerias, Conexion sql) {
        this.floristerias = floristerias;
        this.sql = sql;
        this.proveedores = new HashMap<>();
        cargarProveedores();
    }

    @Override
    public int getRowCount() {
        return floristerias.size();
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
        Floristeria floristeria = floristerias.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return floristeria.getNombre();
            case 2: // Proveedor
                int proveedorId = floristeria.getProveedorId();
                String proveedorNombre = obtenerNombreProveedor(proveedorId);
                return proveedorNombre;
            case 3: // Cantidad
                return floristeria.getCantidad();
            case 4: // Precio
                double precio = floristeria.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("L. %,.2f", precio);
                return precioFormateado;
            case 5: // Total
                double total = floristeria.getPrecio() * floristeria.getCantidad();
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
