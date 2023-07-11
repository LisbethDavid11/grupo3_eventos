package Modelos;
import Objetos.Conexion;
import Objetos.DetalleCompra;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloDetallesCompras extends AbstractTableModel {
    private final String[] columnas = {"N°", "Compra", "Material", "Cantidad", "Precio"};
    private final List<DetalleCompra> detallesCompras;
    private final Conexion sql;

    public ModeloDetallesCompras(List<DetalleCompra> detallesCompras, Conexion sql) {
        this.detallesCompras = detallesCompras;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return detallesCompras.size();
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
        DetalleCompra detalleCompra = detallesCompras.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Compra
                return detalleCompra.getCompraId();
            case 2: // Material
                return detalleCompra.getMaterialId();
            case 3: // Cantidad
                return detalleCompra.getCantidad();
            case 4: // Precio
                return detalleCompra.getPrecio();
            default:
                return null;
        }
    }
}
