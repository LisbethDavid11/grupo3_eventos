package Modelos;

import Objetos.Conexion;
import Objetos.PoliMobiliario;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloMobiliario extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Color", "Tipo de Evento", "Cantidad", "Precio Unitario", "Total"};
    private final List<PoliMobiliario> mobiliario;
    private final Conexion sql;

    public PoliModeloMobiliario(List<PoliMobiliario> mobiliario, Conexion sql) {
        this.mobiliario = mobiliario;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return mobiliario.size();
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
        PoliMobiliario mobiliarioItem = mobiliario.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return "M-" + mobiliarioItem.getID();
            case 1: // Nombre
                return mobiliarioItem.getNombre();
            case 2: // Color
                return mobiliarioItem.getColor();
            case 3: // Tipo de Evento
                return mobiliarioItem.getTipoEvento();
            case 4: // Cantidad
                return mobiliarioItem.getCantidad();
            case 5: // Precio Unitario
                double precioUnitario = mobiliarioItem.getPrecioUnitario();
                if (precioUnitario < 0) {
                    precioUnitario = 0;
                }
                String precioUnitarioFormateado = String.format("L. %.2f", precioUnitario);
                return precioUnitarioFormateado;
            case 6: // Total (Precio Unitario * Cantidad)
                int cantidad = mobiliarioItem.getCantidad();
                double total = mobiliarioItem.getPrecioUnitario() * cantidad;
                String totalFormateado = String.format("L. %.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
