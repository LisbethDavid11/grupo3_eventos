package Modelos;

import Objetos.Arreglo;
import Objetos.Conexion;
import Objetos.Floristeria;

import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;

public class ModeloArreglo extends AbstractTableModel {

    private final String[] columnas = {"N°", "Nombre del Arreglo", "Precio", "Disponible"};

    private final List<Arreglo> arreglos;
    private final Conexion sql;

    public ModeloArreglo(List<Arreglo> arreglos, Conexion sql) {
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
    public Class<?> getColumnClass(int columnIndex) {
        if (getColumnName(columnIndex).equals("Imagen")) {
            return ImageIcon.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Arreglo arreglo = arreglos.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return "   " + arreglo.getNombre();
            case 2: // Precio
                double precio = arreglo.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("   L. %,.2f", precio);
                return precioFormateado;
            case 3: // Disponible
                return arreglo.getDisponible();
            default:
                return null;
        }
    }
}
