package Modelos;

import Objetos.Conexion;
import Objetos.Tarjeta;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModeloTarjetas extends AbstractTableModel {
    private final String[] columnas = {"N°", "Ocasión", "Disponible", "Precio Tarjeta", "Mano de obra"};

    private final List<Tarjeta> tarjetas;
    private final Conexion sql;

    public ModeloTarjetas(List<Tarjeta> tarjetas, Conexion sql) {
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
            case 4: // Mano de obra
                double manoObra = tarjeta.getMano_obra();
                if (manoObra < 0) {
                    manoObra = 0;
                }
                String manoObraFormateada = String.format("L. %,.2f", manoObra);
                return manoObraFormateada;
            default:
                return null;
        }
    }
}
