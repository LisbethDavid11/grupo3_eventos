package Modelos;

import Objetos.Proveedor;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloProveedores extends AbstractTableModel {
    private final String[] columnas = {
            "N°", "Empresa Proveedora", "Teléfono","RTN", "Correo", "Nombre Vendedor", "Teléfono Vendedor"
    };
    private final List<Proveedor> proveedores;

    public ModeloProveedores(List<Proveedor> proveedores) {
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

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public Proveedor getProveedor(int index) {
        return proveedores.get(index);
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Proveedor proveedor = proveedores.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return proveedor.getEmpresaProveedora();
            case 2:
                return proveedor.getTelefono();
            case 3:
                return proveedor.getRtn();
            case 4:
                return proveedor.getCorreo();
            case 5:
                return proveedor.getNombreVendedor();
            case 6:
                return proveedor.getTelefonoVendedor();
            default:
                return null;
        }
    }
}
