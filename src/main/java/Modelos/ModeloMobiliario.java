package Modelos;

import Objetos.Cliente;
import Objetos.Conexion;
import Objetos.Empleado;
import Objetos.Mobiliario;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloMobiliario extends AbstractTableModel {

    private final String[] columnas = {"N°","Nombre del mobiliario", "Color", "Cantidad", "Precio", "Total" };

    private final List<Mobiliario> mobiliarios;
    private final Conexion sql;

    public ModeloMobiliario(List<Mobiliario> mobiliarios, Conexion sql) {
        this.mobiliarios = mobiliarios;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return mobiliarios.size();
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
        Mobiliario mobiliario = mobiliarios.get(rowIndex);
        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return "  " + mobiliario.getNombreMobiliario();
            case 2: // Color
                return "  " + mobiliario.getColor();
            case 3: // Cantidad
                return "  " + mobiliario.getCantidad();
            case 4: // Precio
                double precio = mobiliario.getPrecioUnitario();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("  L. %,.2f", precio);
                return precioFormateado;
            case 5: // Total (Precio * Cantidad)
                int cantidad = mobiliario.getCantidad();
                double total = mobiliario.getPrecioUnitario() * cantidad;
                String totalFormateado = String.format("  L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
