package Modelos;

import Objetos.Conexion;
import Objetos.Floristeria;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModeloFloristeria extends AbstractTableModel {
    private final String[] columnas = {"N°", "Imagen", "Nombre", "Precio", "Proveedor"};

    private final List<Floristeria> floristerias;
    private final Conexion sql;

    public ModeloFloristeria(List<Floristeria> floristerias, Conexion sql) {
        this.floristerias = floristerias;
        this.sql = sql;
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
    public Class<?> getColumnClass(int columnIndex) {
        if (getColumnName(columnIndex).equals("Imagen")) {
            return ImageIcon.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Floristeria floristeria = floristerias.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Imagen
                ImageIcon imagenIcon = floristeria.getImagen();
                if (imagenIcon != null) {
                    // Redimensionar la imagen para que encaje en la celda de la tabla
                    Image imagenOriginal = imagenIcon.getImage();
                    Image imagenRedimensionada = imagenOriginal.getScaledInstance(150, 120, Image.SCALE_SMOOTH);
                    imagenIcon = new ImageIcon(imagenRedimensionada);

                    return imagenIcon;
                }
                return null;
            case 2: // Nombre
                return floristeria.getNombre();
            case 3: // Precio
                return floristeria.getPrecio();
            case 4: // Proveedor
                int proveedorId = floristeria.getProveedorId();
                String proveedorNombre = obtenerNombreProveedor(proveedorId);
                return proveedorNombre;
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 4 && aValue instanceof ImageIcon) {
            Floristeria floristeria = floristerias.get(rowIndex);
            floristeria.setImagen((ImageIcon) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }

    private String obtenerNombreProveedor(int proveedorId) {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT empresaProveedora FROM Proveedores WHERE id = ?")) {

            preparedStatement.setInt(1, proveedorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("empresaProveedora");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
}
