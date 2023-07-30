package Modelos;

import Objetos.Conexion;
import Objetos.DetalleDesayuno;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloDetallesDesayuno extends AbstractTableModel {
    private final String[] columnas = {"N°", "Desayuno", "Material", "Globo", "Tarjeta", "Floristeria", "Cantidad", "Precio"};
    private final List<DetalleDesayuno> detallesDesayuno;
    private final Conexion sql;

    public ModeloDetallesDesayuno(List<DetalleDesayuno> detallesDesayuno, Conexion sql) {
        this.detallesDesayuno = detallesDesayuno;
        this.sql = sql;
    }

    public List<DetalleDesayuno> getDetallesPorDesayuno(int desayunoId) {
        List<DetalleDesayuno> detalles = new ArrayList<>();

        try {
            String query = "SELECT * FROM detalles_desayunos WHERE desayuno_id = ?";
            PreparedStatement statement = sql.conectamysql().prepareStatement(query);
            statement.setInt(1, desayunoId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                DetalleDesayuno detalle = new DetalleDesayuno();
                detalle.setId(resultSet.getInt("id"));
                detalle.setDesayunoId(resultSet.getInt("desayuno_id"));
                // detalle.setMaterialId(resultSet.getInt("material_id"));
                // detalle.setGloboId(resultSet.getInt("globo_id"));
                // detalle.setTarjetaId(resultSet.getInt("tarjeta_id"));
                // detalle.setFloristeriaId(resultSet.getInt("floristeria_id"));
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
        return detallesDesayuno.size();
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
        DetalleDesayuno detalleDesayuno = detallesDesayuno.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Desayuno
                return detalleDesayuno.getDesayunoId();
            /*
                case 2: // Material
                int materialId = detalleDesayuno.getMaterialId();
                String materialNombre = obtenerNombreMaterial(materialId);
                return materialNombre;
            case 3: // Globo
                int globoId = detalleDesayuno.getGloboId();
                String globoNombre = obtenerNombreGlobo(globoId);
                return globoNombre;
            case 4: // Tarjeta
                int tarjetaId = detalleDesayuno.getTarjetaId();
                String tarjetaNombre = obtenerNombreTarjeta(tarjetaId);
                return tarjetaNombre;
            case 5: // Floristeria
                int floristeriaId = detalleDesayuno.getFloristeriaId();
                String floristeriaNombre = obtenerNombreFloristeria(floristeriaId);
                return floristeriaNombre;

             */
            case 3: // Cantidad
                return detalleDesayuno.getCantidad();
            case 4: // Precio
                double precio = detalleDesayuno.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("L. %,.2f", precio);
                return precioFormateado;
            default:
                return null;
        }
    }

    // Las funciones obtenerNombreMaterial, obtenerNombreGlobo, obtenerNombreTarjeta, obtenerNombreFloristeria se deben implementar de manera similar a obtenerNombreMaterial en el ejemplo original.
}
