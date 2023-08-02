package Modelos;

import Objetos.Cliente;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloCliente extends AbstractTableModel {
    private final String[] columnas = {"N°", "Identidad", "Nombre Completo", "Teléfono", "Tipo"};
    private final List<Cliente> clientes;
    public ModeloCliente(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Cliente getCliente(int index) {
        return clientes.get(index);
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = clientes.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return cliente.getIdentidad();
            case 2:
                return "   " + cliente.getNombre() + " " + cliente.getApellido();
            case 3:
                return formatearTelefono(cliente.getTelefono());
            case 4:
                return cliente.getTipo_cliente();
            default:
                return null;
        }
    }

    private String formatearTelefono(String telefono) {
        return telefono.substring(0, 4) + "-" + telefono.substring(4, 8);
    }
}