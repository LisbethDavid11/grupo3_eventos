package Modelos;

import Objetos.Conexion;
import Objetos.Pedido;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ModeloPedido extends AbstractTableModel {
    private final String[] columnas = {"ID", "Código de Pedido", "Fecha de Pedido", "Fecha de Entrega", "Descripción", "Cliente ID", "Entrega"};
    private final List<Pedido> pedidos;
    private final Conexion sql;

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
                return pedido.getFechaPedido();
            case 3: // Fecha de Entrega
                return pedido.getFechaEntrega();
            case 4: // Descripción
                return pedido.getDescripcion();
            case 5: // Cliente ID
                /*
                int clienteId = pedido.getClienteId();
                String clienteNombre = obtenerNombreCliente(clienteId);
                return "  " + clienteNombre;
                 */
            case 6: // Entrega
                return pedido.getEntrega();
            default:
                return null;
        }
    }

    /*

    private String obtenerNombreCliente(int clienteId) {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT id, nombre, apellido FROM clientes WHERE id = ?")) {
            preparedStatement.setInt(1, clienteId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombres = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellido");
                return nombres + " " + apellidos;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Devuelve null si no se encuentra el cliente o hay un error
    }

    private void cargarClientes() {
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT id, nombre, apellido FROM clientes")) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombreCompleto = resultSet.getString("nombre") + " " + resultSet.getString("apellido");
                clientes.put(id, nombreCompleto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     */
}
