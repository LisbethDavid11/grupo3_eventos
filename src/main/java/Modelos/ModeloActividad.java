package Modelos;

import Objetos.Conexion;
import Objetos.Actividad;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ModeloActividad extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre", "Descripción", "Fecha", "Inicio", "Fin"};

    private final List<Actividad> actividades;
    private final Conexion sql;
    private final SimpleDateFormat fechaFormat = new SimpleDateFormat("dd 'de' MMMM',' yyyy");
    private final SimpleDateFormat horaFormat = new SimpleDateFormat("hh:mm a");

    public ModeloActividad(List<Actividad> actividades, Conexion sql) {
        this.actividades = actividades;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return actividades.size();
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
        Actividad actividad = actividades.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return " " + actividad.getNombre();
            case 2: // Descripción
                return " " + actividad.getDescripcion();
            case 3: // Fecha
                return " " + fechaFormat.format(actividad.getFecha());
            case 4: // Inicio
                return " " + horaFormat.format(actividad.getInicio());
            case 5: // Fin
                return " " + horaFormat.format(actividad.getFin());
            default:
                return null;
        }
    }
}
