package Modelos;

import Objetos.Conexion;
import Objetos.PoliTarjeta;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloTarjeta extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Cantidad", "Precio", "Total"};
    private final List<PoliTarjeta> tarjetas;
    private final Conexion sql;

    public PoliModeloTarjeta(List<PoliTarjeta> tarjetas, Conexion sql) {
        this.tarjetas = tarjetas;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return tarjetas.size();
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
        PoliTarjeta tarjeta = tarjetas.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return "T-" + tarjeta.getID();
            case 1: // Ocasión
                return tarjeta.getNombre();
            case 2: // Cantidad
                return tarjeta.getCantidad();
            case 3: // Precio
                double precio = tarjeta.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format(" L. %,.2f", precio);
                return precioFormateado;
            case 4: // Total
                double total = tarjeta.getPrecio() * tarjeta.getCantidad();
                String totalFormateado = String.format(" L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
