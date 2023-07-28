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
    private final String[] columnas = {"N°", "Nombre de la manualidad", "Descripcion", "Tipo", "Precio"};

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
            case 2: // Descripcion
                return manualidad.getDescripcion();
            case 3: // Tipo
                return manualidad.getTipo();
            case 4: // Precio
                double precio = manualidad.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("L. %,.2f", precio);
                return precioFormateado;
            default:
                return null;
        }
    }
}
