package Modelos;
import Objetos.Conexion;
import Objetos.PoliDesayuno;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PoliModeloDesayuno extends AbstractTableModel {
    private final String[] columnas = {"Id", "Nombre", "Cantidad", "Precio", "Total"};
    private final List<PoliDesayuno> desayunos;
    private final Conexion sql;

    public PoliModeloDesayuno(List<PoliDesayuno> desayunos, Conexion sql) {
        this.desayunos = desayunos;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return desayunos.size();
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
        PoliDesayuno desayuno = desayunos.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return "D-" + desayuno.getID();
            case 1: // Código
                return desayuno.getNombre();
            case 2: // Cantidad
                return desayuno.getCantidad();
            case 3: // Precio
                double precio = desayuno.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format(" L. %,.2f", precio);
                return precioFormateado;
            case 4: // Total
                double total = desayuno.getPrecio() * desayuno.getCantidad();
                String totalFormateado = String.format(" L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
