package Modelos;

import Objetos.Conexion;
import Objetos.Promocion;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ModeloPromocion extends AbstractTableModel {
    private final String[] columnas = {"N°", "Descripción","Precio", "Fecha Inicial", "Fecha Final"};
    private final List<Promocion> promociones;
    private final Conexion sql;
    private final SimpleDateFormat fechaFormat = new SimpleDateFormat("dd 'de' MMMM',' yyyy");
    public ModeloPromocion(List<Promocion> promociones, Conexion sql) {
        this.promociones = promociones;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return promociones.size();
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
        Promocion promocion = promociones.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Descripción
                return "   " + promocion.getDescripcion();
            case 2: // precio
                return String.format("L. %.2f",promocion.getPrecio());
            case 3: // Fecha de inicio
                return "   " + fechaFormat.format(promocion.getInicio());
            case 4: // Fecha de fin
                return "   " + fechaFormat.format(promocion.getFin());
            default:
                return null;
        }
    }
}
