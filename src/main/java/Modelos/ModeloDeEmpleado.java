package Modelos;

import Objetos.Empleados;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloDeEmpleado extends AbstractTableModel {
    private final String [] Columnas;
    private final List <Empleados> empleados;

    public ModeloDeEmpleado(String[] columnas, List<Empleados> empleados) {
        Columnas = columnas;
        this.empleados = empleados;
    }

    @Override
    public int getRowCount() {
        return this.empleados.size();
    }

    @Override
    public int getColumnCount() {
        return this.Columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return this.Columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex){
            case 0 -> this.empleados.get(rowIndex).getId();
            case 1 -> this.empleados.get(rowIndex).getIdentidad();
            case 2 -> this.empleados.get(rowIndex).getNombres();
            case 3-> this.empleados.get(rowIndex).getApellidos();
            case 4 -> this.empleados.get(rowIndex).getTelefono();
            default -> null;
        };
    }
}
