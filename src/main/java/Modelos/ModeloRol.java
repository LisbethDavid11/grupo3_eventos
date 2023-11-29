package Modelos;

import Objetos.Rol;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class ModeloRol extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre", "Descripción", "Eliminar"};
    private final List<Rol> roles;

    public ModeloRol(List<Rol> roles) {
        this.roles = roles;
    }

    @Override
    public int getRowCount() {
        return roles.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public Rol getRol(int index) {
        return roles.get(index);
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rol rol = roles.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return "  " + rol.getNombre();
            case 2:
                return "  " + rol.getDescripcion();
            case 3:
                return "X"; // Texto del botón
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 3) {
            return JButton.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 3;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 3) {
            // Suponiendo que 3 es el índice de la columna de eliminar
            removeRow(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < roles.size()) {
            roles.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
}
