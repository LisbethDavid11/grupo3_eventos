package Modelos;

import Objetos.Conexion;
import Objetos.Evento;

import javax.swing.table.AbstractTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ModeloEvento extends AbstractTableModel {
    private final String[] columnas = {"N°", "Cliente", "Tipo", "Fecha", "Hora inicial", "Hora final"};
    private final List<Evento> eventos;
    private final Conexion sql;
    private final SimpleDateFormat fechaFormat = new SimpleDateFormat("dd 'de' MMMM',' yyyy");
    private final SimpleDateFormat horaFormat = new SimpleDateFormat("hh:mm a");

    public ModeloEvento(List<Evento> eventos, Conexion sql) {
        this.eventos = eventos;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return eventos.size();
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
        Evento evento = eventos.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Cliente
                // Debes obtener el nombre del cliente relacionado con el evento desde tu base de datos
                int clienteId = evento.getClienteId(); // Obtiene el ID del cliente desde el evento
                String nombreCliente = obtenerNombreCliente(clienteId); // Llama a una función para obtener el nombre del cliente
                return "   " + nombreCliente;
            case 2: // Tipo
                return "   " + evento.getTipo();
            case 3: // Fecha
                return "   " + fechaFormat.format(evento.getFecha());
            case 4: // Inicio
                return "   " + horaFormat.format(evento.getInicio());
            case 5: // Fin
                return "   " + horaFormat.format(evento.getFin());
            default:
                return null;
        }
    }

    private String obtenerNombreCliente(int clienteId) {
        String nombreCompleto = "";

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT CONCAT(nombre, ' ', apellido) AS nombre_completo FROM clientes WHERE id = ?"
             )) {
            preparedStatement.setInt(1, clienteId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                nombreCompleto = resultSet.getString("nombre_completo");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return nombreCompleto;
    }
}