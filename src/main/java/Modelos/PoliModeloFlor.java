package Modelos;
import Objetos.Conexion;
import Objetos.PoliFlor;
import javax.swing.table.AbstractTableModel;
import java.util.List;
public class PoliModeloFlor extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Cantidad", "Precio", "Total"};
    private final List<PoliFlor> floristerias;
    private final Conexion sql;
    public PoliModeloFlor(List<PoliFlor> floristerias, Conexion sql) {
        this.floristerias = floristerias;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return floristerias.size();
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
        PoliFlor floristeria = floristerias.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return "F-" + floristeria.getID();
            case 1: // Nombre
                return " " + floristeria.getNombre();
            case 2: // Cantidad
                return " " + floristeria.getCantidad();
            case 3: // Precio
                double precio = floristeria.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format(" L. %,.2f", precio);
                return precioFormateado;
            case 4: // Total
                double total = floristeria.getPrecio() * floristeria.getCantidad();
                String totalFormateado = String.format(" L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
