package Modelos;

import Objeto.Cliente;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloClientes extends AbstractTableModel {

    private final String[] columnas;

    private final List<Cliente>  clients;

    public ModeloClientes(String[] columnas, List<Cliente> clients) {
        this.columnas = columnas;
        this.clients = clients;
    }

    @Override
    public int getRowCount() {
        return clients.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    public List<Cliente> getClients() {
        return clients;
    }

    public Cliente getCliente(int index) {
        return clients.get(index);
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        return switch (columnIndex) {
            case 0 -> clients.get(rowIndex).getId();
            case 1 -> clients.get(rowIndex).getNombre();
            case 2 -> clients.get(rowIndex).getApellido();
            case 3 -> clients.get(rowIndex).getIdentidad();
            case 4 -> clients.get(rowIndex).getTelefono();
            case 5 -> clients.get(rowIndex).getDomicilio();
            case 6 -> clients.get(rowIndex).getTipo_cliente();
            default -> null;
        };
    }
}
