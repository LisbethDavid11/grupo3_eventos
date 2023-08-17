package Modelos;
import Objetos.Conexion;
import Objetos.PoliArreglo;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloArreglo extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Cantidad", "Precio", "Total"};
    private final List<PoliArreglo> arreglos;
    private final Conexion sql;

    public PoliModeloArreglo(List<PoliArreglo> arreglos, Conexion sql) {
        this.arreglos = arreglos;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return arreglos.size();
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
        PoliArreglo arreglo = arreglos.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return "A-" + arreglo.getID();
            case 1: // Código
                return arreglo.getNombre();
            case 2: // Cantidad
                return arreglo.getCantidad();
            case 3: // Precio
                double precio = arreglo.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format(" L. %,.2f", precio);
                return precioFormateado;
            case 4: // Total
                double total = arreglo.getPrecio() * arreglo.getCantidad();
                String totalFormateado = String.format(" L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
