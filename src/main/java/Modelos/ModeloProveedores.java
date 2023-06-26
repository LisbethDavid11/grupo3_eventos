package Modelos;

import Objetos.Proveedores;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloProveedores extends AbstractTableModel {

    //Crearemos una variable para crear las columnas
    private final String[] columnas;
    private List<Proveedores> proveedores;

    public ModeloProveedores(String[] columnas, List<Proveedores> proveedores) {
        this.columnas = columnas;
        this.proveedores = proveedores;
    }

    @Override
    public int getRowCount() {
        return proveedores.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    public List<Proveedores> getProveedore() {

        return proveedores;
    }

    public Proveedores getProveedores(int index) {

        return proveedores.get(index);
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    //Genera toda la renderización de la tabla
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        return switch (columnIndex) {
            case 0 -> proveedores.get(rowIndex).getId();
            case 1 -> proveedores.get(rowIndex).getNombre();
            case 2 -> proveedores.get(rowIndex).getRtn();
            case 3 -> proveedores.get(rowIndex).getTelefono();
            case 4 -> proveedores.get(rowIndex).getCorreo();
            case 5 -> proveedores.get(rowIndex).getDireccion();
            case 6 -> proveedores.get(rowIndex).getDescripcion();
            case 7 -> proveedores.get(rowIndex).getNombreVendedor();
            case 8 -> proveedores.get(rowIndex).getTelefonoVendedor();
            default -> null;
        };
    }
}
