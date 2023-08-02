package Modelos;

import Objetos.Conexion;
import Objetos.CompraDetalle;
import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModeloCompraDetalle extends AbstractTableModel {
    private final String[] columnas = {"N°", "Compra", "Material", "Cantidad", "Precio", "Total"};
    private final List<CompraDetalle> detallesCompras;
    private final Conexion sql;

    public ModeloCompraDetalle(List<CompraDetalle> detallesCompras, Conexion sql) {
        this.detallesCompras = detallesCompras;
        this.sql = sql;
    }

    public List<CompraDetalle> getDetallesPorCompra(int compraId) {
        List<CompraDetalle> detalles = new ArrayList<>();

        try {
            String query = "SELECT * FROM detalles_compras WHERE compra_id = ?";
            PreparedStatement statement = sql.conectamysql().prepareStatement(query);
            statement.setInt(1, compraId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CompraDetalle detalle = new CompraDetalle();
                detalle.setId(resultSet.getInt("id"));
                detalle.setCompraId(resultSet.getInt("compra_id"));
                detalle.setMaterialId(resultSet.getInt("material_id"));
                detalle.setCantidad(resultSet.getInt("cantidad"));
                detalle.setPrecio(resultSet.getDouble("precio"));
                // Remove this line: detalle.setTotal(resultSet.getDouble("total"));
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
        return detallesCompras.size();
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
        CompraDetalle detalleCompra = detallesCompras.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Compra
                return detalleCompra.getCompraId();
            case 2: // Material
                int materialId = detalleCompra.getMaterialId();
                String materialNombre = obtenerNombreMaterial(materialId);
                return materialNombre;
            case 3: // Cantidad
                return detalleCompra.getCantidad();
            case 4: // Precio
                return detalleCompra.getPrecio();
            case 5: // Total
                return detalleCompra.getCantidad() * detalleCompra.getPrecio();
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
