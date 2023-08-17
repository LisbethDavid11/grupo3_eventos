package Modelos;

import Objetos.Conexion;
import Objetos.Material;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloProducto extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre", "Cantidad", "Precio", "Total", "Eliminar"};
    private final List<Material> materiales;
    private final Conexion sql;

    public ModeloProducto(List<Material> materiales, Conexion sql) {
        this.materiales = materiales;
        this.sql = sql;
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
            case 2: // Nombre
                return material.getCantidad();
            case 3: // Precio
                double precio = material.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("L. %.2f", precio);
                return precioFormateado;
            case 4: // Total (Precio * Cantidad)
                int cantidad = material.getCantidad();
                double total = material.getPrecio() * cantidad;
                String totalFormateado = String.format("%.2f", total);
                return totalFormateado;
            case 5: // Eliminar (Botón)
                return "X";

            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 5) {
            return JButton.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 5 && aValue instanceof String && aValue.equals("X")) {
            // Aquí puedes realizar la lógica para eliminar la fila en la base de datos si es necesario
            materiales.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < materiales.size()) {
            materiales.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }
}