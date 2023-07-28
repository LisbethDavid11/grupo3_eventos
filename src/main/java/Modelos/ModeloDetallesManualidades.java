package Modelos;

import Objetos.Conexion;
import Objetos.DetalleManualidad;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloDetallesManualidades extends AbstractTableModel {
    private final String[] columnas = {"N°", "Manualidad", "Material", "Cantidad"};
    private final List<DetalleManualidad> detallesManualidades;
    private final Conexion sql;

    public ModeloDetallesManualidades(List<DetalleManualidad> detallesManualidades, Conexion sql) {
        this.detallesManualidades = detallesManualidades;
        this.sql = sql;
    }

    public List<DetalleManualidad> getDetallesPorManualidad(int manualidadId) {
        List<DetalleManualidad> detalles = new ArrayList<>();

        try {
            String query = "SELECT * FROM detalles_manualidades WHERE manualidad_id = ?";
            PreparedStatement statement = sql.conectamysql().prepareStatement(query);
            statement.setInt(1, manualidadId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DetalleManualidad detalle = new DetalleManualidad();
                detalle.setId(resultSet.getInt("id"));
                detalle.setManualidadId(resultSet.getInt("manualidad_id"));
                detalle.setMaterialId(resultSet.getInt("material_id"));
                detalle.setCantidad(resultSet.getInt("cantidad"));
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
        DetalleManualidad detalleManualidad = detallesManualidades.get(rowIndex);

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
