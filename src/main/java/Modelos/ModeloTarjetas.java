package Modelos;
import Objetos.Conexion;
import Objetos.Tarjeta;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloTarjetas extends AbstractTableModel {
    private final String[] columnas = {"N°", "Ocasión", "Disponible", "Existencia", "Precio", "Total"};

    private final List<Tarjeta> tarjetas;

    public ModeloTarjetas(List<Tarjeta> tarjetas, Conexion sql) {
        this.tarjetas = tarjetas;
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
        Tarjeta tarjeta = tarjetas.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Ocasión
                return tarjeta.getOcasion();
            case 2: // Disponible
                return tarjeta.getDisponible();
            case 3: // Cantidad
                return tarjeta.getCantidad();
            case 4: // Precio Tarjeta
                double precioTarjeta = tarjeta.getPrecio_tarjeta();
                if (precioTarjeta < 0) {
                    precioTarjeta = 0;
                }
                String precioTarjetaFormateado = String.format("L. %,.2f", precioTarjeta);
                return precioTarjetaFormateado;
            case 5: // Total (Precio * Cantidad)
                int cantidad = tarjeta.getCantidad();
                double total = tarjeta.getPrecio_tarjeta() * cantidad;
                String totalFormateado = String.format("L. %,.2f", total);
                return totalFormateado;
            default:
                return null;
        }
    }
}
