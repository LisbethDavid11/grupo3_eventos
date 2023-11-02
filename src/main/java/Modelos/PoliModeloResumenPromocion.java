package Modelos;

import Objetos.Conexion;
import Objetos.PoliResumenPromocion;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloResumenPromocion extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Cantidad", "Precio Promoción", "Total Promoción"};
    private final List<PoliResumenPromocion> promociones;
    private final Conexion sql;

    public PoliModeloResumenPromocion(List<PoliResumenPromocion> promociones, Conexion sql) {
        this.promociones = promociones;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return promociones.size();
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
        PoliResumenPromocion promocion = promociones.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return "MA-" + promocion.getID();
            case 1: // Nombre
                return promocion.getNombre();
            case 2: // Cantidad
                return promocion.getCantidad();
            case 3: // Precio
                double precio = promocion.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("L. %.2f", precio);
                return precioFormateado;
            case 4: // Total (Precio * Cantidad)
                int cantidad = promocion.getCantidad();
                double total = promocion.getPrecio() * cantidad;
                String totalFormateado = String.format("%.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
