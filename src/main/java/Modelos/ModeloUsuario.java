package Modelos;

import Objetos.Conexion;
import Objetos.Usuario;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModeloUsuario extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre", "Correo", "Rol", "Eliminar"};
    private final List<Usuario> usuarios;
    private final Conexion sql;

    public ModeloUsuario(List<Usuario> usuarios, Conexion sql) {
        this.usuarios = usuarios;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return usuarios.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usuario usuario = usuarios.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return "  " + usuario.getNombre();
            case 2: // Correo
                return "  " + usuario.getCorreo();
            case 3: // Rol
                return "  " + obtenerNombreRol(usuario.getRolId());
            case 4: // Eliminar (Botón)
                return "X";
            default:
                return null;
        }
    }

    private String obtenerNombreRol(int rolId) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT nombre FROM roles WHERE id = ?")) {

            preparedStatement.setInt(1, rolId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Desconocido"; // Valor por defecto en caso de error
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 4) {
            return JButton.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 4) {
            // Suponiendo que 3 es el índice de la columna de eliminar
            removeRow(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < usuarios.size()) {
            usuarios.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
}
