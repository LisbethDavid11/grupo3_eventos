package Modelos;

import Objetos.PoliProducto;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloProducto extends AbstractTableModel {
    private final String[] columnas = {"ID", "Nombre", "Cantidad", "Precio", "Total", "Eliminar"};
    private final List<PoliProducto> productos;

    public PoliModeloProducto(List<PoliProducto> productos) {
        this.productos = productos;
    }

    public List<PoliProducto> getList() {
        return this.productos;
    }
    public PoliProducto getProducto(int id) {
        return this.productos.get(id);
    }
    @Override
    public int getRowCount() {
        return productos.size();
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
        PoliProducto material = productos.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return material.getTipo()+"-" + material.getID();
            case 1: // Nombre
                return material.getNombre();
            case 2: // Cantidad
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
            productos.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < productos.size()) {
            productos.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void removeProductAtIndex(int index) {
        if (index >= 0 && index < productos.size()) {
            productos.remove(index);
            fireTableRowsDeleted(index, index);
        }
    }

}