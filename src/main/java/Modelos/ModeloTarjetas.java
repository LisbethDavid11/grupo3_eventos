package Modelos;
import Objetos.Conexion;
import Objetos.Tarjeta;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloTarjetas extends AbstractTableModel {
    private final String[] columnas = {"N°", "Ocasión", "Disponible", "Precio"};

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
            case 3: // Precio Tarjeta
                double precioTarjeta = tarjeta.getPrecio_tarjeta();
                if (precioTarjeta < 0) {
                    precioTarjeta = 0;
                }
                String precioTarjetaFormateado = String.format("L. %,.2f", precioTarjeta);
                return precioTarjetaFormateado;
            default:
                return null;
        }
    }
}

