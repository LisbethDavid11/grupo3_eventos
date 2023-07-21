package Modelos;

import Objetos.Conexion;
import Objetos.Tarjeta;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloTajetas extends AbstractTableModel {
    private final String[] columnas = {"N°", "Ocasión", "Precio", "Disponible"};
    private final List<Tarjeta> tarjetas;
    private final Conexion sql;


    public ModeloTajetas(List<Tarjeta> tarjetas, Conexion sql) {
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
            case 1: // Ocasion
                return tarjeta.getOcasion();
            case 2: // Precio
                double precio = tarjeta.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                return String.format("L. %,.2f", precio);
            case 3: // Disponible
                return tarjeta.getDisponible();
            default:
                return null;
        }
    }

}
