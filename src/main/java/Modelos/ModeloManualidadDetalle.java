package Modelos;

import Objetos.Conexion;
import Objetos.ManualidadDetalle;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloManualidadDetalle extends AbstractTableModel {
    private final String[] columnas = {"N°", "Manualidad", "Material", "Cantidad", "Precio"};
    private final List<ManualidadDetalle> detallesManualidades;
    private final Conexion sql;

    public ModeloManualidadDetalle(List<ManualidadDetalle> detallesManualidades, Conexion sql) {
        this.detallesManualidades = detallesManualidades;
        this.sql = sql;
    }

    public List<ManualidadDetalle> getDetallesPorManualidad(int manualidadId) {
        List<ManualidadDetalle> detalles = new ArrayList<>();

        try {
            String query = "SELECT * FROM detalles_manualidades WHERE manualidad_id = ?";
            PreparedStatement statement = sql.conectamysql().prepareStatement(query);
            statement.setInt(1, manualidadId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ManualidadDetalle detalle = new ManualidadDetalle();
                detalle.setId(resultSet.getInt("id"));
                detalle.setManualidadId(resultSet.getInt("manualidad_id"));
                detalle.setMaterialId(resultSet.getInt("material_id"));
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
        return detallesManualidades.size();
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
        ManualidadDetalle detalleManualidad = detallesManualidades.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Manualidad
                return detalleManualidad.getManualidadId();
            case 2: // Material
                int materialId = detalleManualidad.getMaterialId();
                String materialNombre = obtenerNombreMaterial(materialId);
                return materialNombre;
            case 3: // Cantidad
                return detalleManualidad.getCantidad();
            case 4: // Precio
                double precio = detalleManualidad.getPrecio();
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
