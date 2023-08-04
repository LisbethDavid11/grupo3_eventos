package Modelos;

import Objetos.PoliProducto;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloProducto extends AbstractTableModel {
    private final String[] columnas = {"ID", "Nombre", "Cantidad", "Precio", "Total"};
    private final List<PoliProducto> productos;

    public PoliModeloProducto(List<PoliProducto> productos) {
        this.productos = productos;
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
            default:
                return null;
        }
    }
}
