package Modelos;

import Objetos.Cliente;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ModeloTablaClientes extends AbstractTableModel {
    private final Object[] columnas = Cliente.columnasLista;
    private List<Cliente> clientes = new ArrayList<>();

    public ModeloTablaClientes(List<Cliente> clientes) {
        super();
        this.clientes = clientes;

    }


    public List<Cliente> getlistaClientes(){
        return clientes;
    }

    public int getColumnCount() {
        // TODO Auto-generated method stub
        return columnas.length;
    }

    public Cliente getClientes(int posi) {
        return clientes.get(posi);
    }

    public int getRowCount() {
        // TODO Auto-generated method stub
        return clientes.size();
    }

    public String getColumnName(int column) {
        // TODO Auto-generated method stub
        return (String) columnas[column];
    }

    public Object getValueAt(int row, int columns) {
        return switch (columns) {
            case 0 -> row + 1;
            case 1 -> clientes.get(row).getNombre();
            case 2 -> clientes.get(row).getApellido();
            case 3 -> clientes.get(row).getIdentidad();
            case 4 -> clientes.get(row).getTelefono();
            default -> null;
        };
    }
}
