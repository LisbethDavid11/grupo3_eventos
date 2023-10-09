package Modelos;

import Objetos.Conexion;
import Objetos.PoliGlobo;
import Objetos.PoliTarjeta;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloTargetas extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Cantidad", "Precio", "Total"};
    private final List<PoliTarjeta> targeta;
    private final Conexion sql;

    public PoliModeloTargetas(List<PoliTarjeta> targeta, Conexion sql) {
        this.targeta = targeta;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return targeta.size();
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
        PoliTarjeta targeta = this.targeta.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return "T-" + targeta.getID();
            case 1: // Código
                return targeta.getNombre();
            case 2: // Cantidad
                return targeta.getCantidad();
            case 3: // Precio
                double precio = targeta.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format(" L. %,.2f", precio);
                return precioFormateado;
            case 4: // Total
                double total = targeta.getPrecio() * targeta.getCantidad();
                String totalFormateado = String.format(" L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
