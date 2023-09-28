package Modelos;

import Objetos.Conexion;
import Objetos.PoliGlobo;
import Objetos.PoliManualidad;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloManualidad extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Cantidad", "Precio", "Total"};
    private final List<PoliManualidad> manualidades;
    private final Conexion sql;

    public PoliModeloManualidad(List<PoliManualidad> manualidades, Conexion sql) {
        this.manualidades = manualidades;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return manualidades.size();
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
        PoliManualidad manualidad = manualidades.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return "W-" + manualidad.getID();
            case 1: // Código
                return manualidad.getNombre();
            case 2: // Cantidad
                return manualidad.getCantidad();
            case 3: // Precio
                double precio = manualidad.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format(" L. %,.2f", precio);
                return precioFormateado;
            case 4: // Total
                double total = manualidad.getPrecio() * manualidad.getCantidad();
                String totalFormateado = String.format(" L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
