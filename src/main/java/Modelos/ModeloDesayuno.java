package Modelos;

import Objetos.Conexion;
import Objetos.Desayuno;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloDesayuno extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre del Desayuno", "Proveedor", "Precio", "Mano de Obra"};
    private final List<Desayuno> desayunos;
    private final Map<Integer, String> proveedores;
    private final Conexion sql;

    public ModeloDesayuno(List<Desayuno> desayunos, Conexion sql) {
        this.desayunos = desayunos;
        this.sql = sql;

        this.proveedores = new HashMap<>();
        cargarProveedores();
    }

    @Override
    public int getRowCount() {
        return desayunos.size();
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
        Desayuno desayuno = desayunos.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return "   " + desayuno.getNombre();
            case 2: // ID del Proveedor
                int proveedorId = desayuno.getProveedor_id();
                String proveedorNombre = obtenerNombreProveedor(proveedorId);
                return "   " + proveedorNombre;
            case 3: // Precio del Desayuno
                double precio = desayuno.getPrecio_desayuno();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("  L. %,.2f", precio);
                return precioFormateado;
            case 4: // Mano de Obra
                double mano_obra = desayuno.getMano_obra();
                if (mano_obra < 0) {
                    mano_obra = 0;
                }
                String manoObraFormateado = String.format("  L. %,.2f", mano_obra);
                return manoObraFormateado;
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
