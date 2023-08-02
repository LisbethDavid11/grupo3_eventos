package Modelos;

import Objetos.Empleado;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloEmpleado extends AbstractTableModel {

    private final String[] columnas = {
            "N°",
            "Identidad",
            "Nombre Completo",
            "Teléfono"
    };
    private final List<Empleado> empleados;

    public ModeloEmpleado(List<Empleado> empleados) {
        if (empleados == null) {
            throw new IllegalArgumentException("La lista de empleados no puede ser nula");
        }
        this.empleados = empleados;
    }

    @Override
    public int getRowCount() {
        return empleados.size();
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
        Empleado empleado = empleados.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return empleado.getIdentidad();
            case 2:
                return "   " + empleado.getNombres() + " " + empleado.getApellidos();
            case 3:
                return formatearTelefono(empleado.getTelefono());
            default:
                return null;
        }
    }

    private String formatearTelefono(String telefono) {
        return telefono.substring(0, 4) + "-" + telefono.substring(4, 8);
    }

}
