package Modelos;

import Objetos.Cliente;
import Objetos.Conexion;
import Objetos.Empleado;
import Objetos.Mobiliario;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloMobiliario extends AbstractTableModel {

    private final String[] columnas = {"N°","Fecha", "Tipo de Evento", "Cliente", "Empleado", "Total"};

    private final List<Mobiliario> mobiliarios;
    private final Conexion sql;

    public ModeloMobiliario(List<Mobiliario> mobiliarios, Conexion sql) {
        this.mobiliarios = mobiliarios;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return mobiliarios.size();
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
        Mobiliario mobiliario = mobiliarios.get(rowIndex);
        Cliente cliente = mobiliario.traer_cliente(sql);
        Empleado empleado = mobiliario.traer_empleado(sql);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Fecha
                return "  " + mobiliario.getFechaEntrega();
            case 2: // Tipo de Evento
                return "  " + mobiliario.getTipoEvento();
            case 3: // Cliente
                return cliente.getNombre()+" "+cliente.getApellido() ;
            case 4: // Empleado
                return empleado.getNombres()+" "+empleado.getApellidos() ;
            case 5: // Total
                double precio = mobiliario.getPrecioUnitario();
                double cantidad = mobiliario.getCantidad();
                if (precio < 0) {
                    precio = 0;
                }
                return String.format("  L. %,.2f", precio*cantidad);
            default:
                return null;
        }
    }
}
