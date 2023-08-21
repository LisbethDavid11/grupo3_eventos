package Modelos;

import Objetos.Conexion;
import Objetos.VentaDetalle;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloVentaDetalle extends AbstractTableModel {
    private final String[] columnas = {"N°", "Venta", "Producto", "Cantidad", "Precio", "Total"};
    private final List<VentaDetalle> detallesVentas;
    private final Conexion sql;

    public ModeloVentaDetalle(List<VentaDetalle> detallesVentas, Conexion sql) {
        this.detallesVentas = detallesVentas;
        this.sql = sql;
    }

    public List<VentaDetalle> getDetallesPorVenta(int ventaId) {
        List<VentaDetalle> detalles = new ArrayList<>();

        try {
            String query = "SELECT * FROM detalles_ventas WHERE venta_id = ?";
            PreparedStatement statement = sql.conectamysql().prepareStatement(query);
            statement.setInt(1, ventaId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                VentaDetalle detalle = new VentaDetalle();
                detalle.setId(resultSet.getInt("id"));
                detalle.setVentaId(resultSet.getInt("venta_id"));
                detalle.setTipoDetalle(resultSet.getString("tipo_detalle"));
                detalle.setDetalleId(resultSet.getInt("detalle_id"));
                detalle.setCantidad(resultSet.getInt("cantidad"));
                detalle.setPrecio(resultSet.getDouble("precio"));
                detalles.add(detalle);
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return detalles;
    }

    @Override
    public int getRowCount() {
        return detallesVentas.size();
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
        VentaDetalle detalleVenta = detallesVentas.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Venta
                return detalleVenta.getVentaId();
            case 2: // Producto
                int materialId = detalleVenta.getDetalleId();
                String productoNombre = obtenerNombreProducto(materialId);
                return productoNombre;
            case 3: // Cantidad
                return detalleVenta.getCantidad();
            case 4: // Precio
                return detalleVenta.getPrecio();
            case 5: // Total
                return detalleVenta.getCantidad() * detalleVenta.getPrecio();
            default:
                return null;
        }
    }

    private String obtenerNombreProducto(int materialId) {
        String nombreProducto = "";
        try {
            String query = "SELECT nombre FROM materiales WHERE id = ?";
            PreparedStatement statement = sql.conectamysql().prepareStatement(query);
            statement.setInt(1, materialId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nombreProducto = resultSet.getString("nombre");
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombreProducto;
    }
}
