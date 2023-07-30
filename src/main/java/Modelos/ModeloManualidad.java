package Modelos;

import Objetos.Conexion;
import Objetos.Manualidad;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloManualidad extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre de la manualidad", "Tipo", "Precio", "Mano de obra"};

    private final List<Manualidad> manualidades;
    private final Conexion sql;

    public ModeloManualidad(List<Manualidad> manualidades, Conexion sql) {
        this.manualidades = manualidades;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return manualidades.size();
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
        Manualidad manualidad = manualidades.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return manualidad.getNombre();
            case 2: // Tipo
                return manualidad.getTipo();
            case 3: // Precio
                double precio_manualidad = manualidad.getPrecio_manualidad();
                if (precio_manualidad < 0) {
                    precio_manualidad = 0;
                }
                String precioManualidadFormateada = String.format("L. %,.2f", precio_manualidad);
                return precioManualidadFormateada;
            case 4: // Precio
                double mano_obra = manualidad.getMano_obra();
                if (mano_obra < 0) {
                    mano_obra = 0;
                }
                String manoObraFormateado = String.format("L. %,.2f", mano_obra);
                return manoObraFormateado;
            default:
                return null;
        }
    }
}
