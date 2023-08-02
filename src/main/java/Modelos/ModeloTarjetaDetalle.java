package Modelos;

import Objetos.Conexion;
import Objetos.TarjetaDetalle;

import javax.swing.table.AbstractTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModeloTarjetaDetalle extends AbstractTableModel {
    private final String[] columnas = {"N°", "Tarjeta", "Material", "Cantidad", "Precio"};
    private final List<TarjetaDetalle> detallesTarjetas;
    private final Conexion sql;

    public ModeloTarjetaDetalle(List<TarjetaDetalle> detallesTarjetas, Conexion sql) {
        this.detallesTarjetas = detallesTarjetas;
        this.sql = sql;
    }

    public List<TarjetaDetalle> getDetallesPorTarjeta(int tarjetaId) {
        List<TarjetaDetalle> detalles = new ArrayList<>();

        try {
            String query = "SELECT * FROM tarjetas_detalles WHERE id_tarjeta = ?";
            PreparedStatement statement = sql.conectamysql().prepareStatement(query);
            statement.setInt(1, tarjetaId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                TarjetaDetalle detalle = new TarjetaDetalle();
                detalle.setId(resultSet.getInt("id"));
                detalle.setIdMaterial(resultSet.getInt("id_material"));
                detalle.setIdTarjeta(resultSet.getInt("id_tarjeta"));
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
        return detallesTarjetas.size();
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
        TarjetaDetalle detalleTarjeta = detallesTarjetas.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Tarjeta
                return detalleTarjeta.getIdTarjeta();
            case 2: // Material
                int materialId = detalleTarjeta.getIdMaterial();
                String materialNombre = obtenerNombreMaterial(materialId);
                return materialNombre;
            case 3: // Cantidad
                return detalleTarjeta.getCantidad();
            case 4: // Precio
                double precio = detalleTarjeta.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("L. %,.2f", precio);
                return precioFormateado;
            default:
                return null;
        }
    }

    private String obtenerNombreMaterial(int materialId) {
        String nombreMaterial = "";
        try {
            String query = "SELECT nombre FROM materiales WHERE id = ?";
            PreparedStatement statement = sql.conectamysql().prepareStatement(query);
            statement.setInt(1, materialId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nombreMaterial = resultSet.getString("nombre");
            }

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombreMaterial;
    }
}
