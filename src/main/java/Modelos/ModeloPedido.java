package Modelos;

import Objetos.Conexion;
import Objetos.Pedido;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ModeloPedido extends AbstractTableModel {
    private final String[] columnas = {"N°", "Código de Pedido", "Fecha de Pedido", "Fecha de Entrega", "Cliente", "Entrega", "Acción"};
    private final List<Pedido> pedidos;
    private final Conexion sql;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM',' yyyy");
    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public ModeloPedido(List<Pedido> pedidos, Conexion sql) {
        this.pedidos = pedidos;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return pedidos.size();
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
        Pedido pedido = pedidos.get(rowIndex);

        switch (columnIndex) {
            case 0: // ID
                return pedido.getId();
            case 1: // Código de Pedido
                return pedido.getCodigoPedido();
            case 2: // Fecha de Pedido
                return dateFormat.format(pedido.getFechaPedido());
            case 3: // Fecha de Entrega
                return dateFormat.format(pedido.getFechaEntrega());
            case 4: // Cliente
                return obtenerNombreCliente(pedido.getClienteId());
            case 5: // Entrega
                return pedido.getEntrega();
            case 6: // Columna del botón Vender
                return " → ";
            default:
                return null;
        }
    }

    private String obtenerNombreCliente(int clienteId) {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT nombre, apellido FROM clientes WHERE id = ?")) {
            preparedStatement.setInt(1, clienteId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                return nombre + " " + apellido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Devuelve null si no se encuentra el cliente o hay un error
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 6) {
            return JButton.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 6 && aValue instanceof String && aValue.equals(" → ")) {
            // Aquí puedes realizar la lógica para eliminar la fila en la base de datos si es necesario
            pedidos.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void removeRow(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < pedidos.size()) {
            pedidos.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public void removeProductAtIndex(int index) {
        if (index >= 0 && index < pedidos.size()) {
            pedidos.remove(index);
            fireTableRowsDeleted(index, index);
        }
    }
}
