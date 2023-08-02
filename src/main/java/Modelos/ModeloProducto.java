package Modelos;

import Objetos.Conexion;
import Objetos.Material;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloProducto extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre", "Cantidad", "Precio", "Total"};
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
            default:
                return null;
        }
    }
}
