package Modelos;

import Objetos.Conexion;
import Objetos.PoliGlobo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloGlobo extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Cantidad", "Precio", "Total"};
    private final List<PoliGlobo> globos;
    private final Conexion sql;

    public PoliModeloGlobo(List<PoliGlobo> globos, Conexion sql) {
        this.globos = globos;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return globos.size();
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
        PoliGlobo globo = globos.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return "G-" + globo.getID();
            case 1: // Código
                return globo.getNombre();
            case 2: // Cantidad
                return globo.getCantidad();
            case 3: // Precio
                double precio = globo.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format(" L. %,.2f", precio);
                return precioFormateado;
            case 4: // Total
                double total = globo.getPrecio() * globo.getCantidad();
                String totalFormateado = String.format(" L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
