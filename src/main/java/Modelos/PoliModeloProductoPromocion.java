package Modelos;

import Objetos.PoliProductoPromocion;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloProductoPromocion extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Cantidad", "Precio", "Total","Precio promoción", "Total promoción", "Eliminar"};
    private final List<PoliProductoPromocion> productos;

    public PoliModeloProductoPromocion(List<PoliProductoPromocion> productos) {
        this.productos = productos;
    }

    public List<PoliProductoPromocion> getList() {
        return this.productos;
    }
    public PoliProductoPromocion getProducto(int id) {
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
        PoliProductoPromocion material = productos.get(rowIndex);

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
            case 5: // Precio promocion
                double promocion = material.getPromocion();
                if (promocion < 0) {
                    promocion = 0;
                }
                String precioFormateadoPromocion = String.format("L. %.2f", promocion);
                return precioFormateadoPromocion;
            case 6: // Total promocion (Precio * Cantidad)
                int cantidadPromocion = material.getCantidad();
                double totalPromocion = material.getPromocion() * cantidadPromocion;
                String totalFormateadoPromocion = String.format("%.2f", totalPromocion);
                return totalFormateadoPromocion;
            case 7: // Eliminar (Botón)
                return "X";
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 7) {
            return JButton.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 7;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 7 && aValue instanceof String && aValue.equals("X")) {
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