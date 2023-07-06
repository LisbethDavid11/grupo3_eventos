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
import java.util.List;


public class ModeloArreglo extends AbstractTableModel {

    private final String[] columnas = {"N°", "Imagen", "Nombre", "Precio", "Disponible"};

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
            case 1: // Imagen
                ImageIcon imagenIcon = arreglo.getImagen();
                if (imagenIcon != null) {
                    // Redimensionar la imagen para que encaje en la celda de la tabla
                    Image imagenOriginal = imagenIcon.getImage();
                    Image imagenRedimensionada = imagenOriginal.getScaledInstance(150, 120, Image.SCALE_SMOOTH);
                    imagenIcon = new ImageIcon(imagenRedimensionada);

                    return imagenIcon;
                }
                return null;
            case 2: // Nombre
                return arreglo.getNombre();
            case 3: // Precio
                return arreglo.getPrecio();
            case 4: // Disponible
                return arreglo.getDisponible();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 4 && aValue instanceof ImageIcon) {
            Arreglo arreglo = arreglos.get(rowIndex);
            arreglo.setImagen((ImageIcon) aValue);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 4;
    }
}
