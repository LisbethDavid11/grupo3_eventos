package Modelos;

import Objetos.Conexion;
import Objetos.Globo;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloGlobo extends AbstractTableModel {

    private final String[] columnas = {"N°","Tamaño", "Material", "Color", "Forma", "Precio", "Cantidad por Paquete"};

    private final List<Globo> globos;
    private final Conexion sql;

    public ModeloGlobo(List<Globo> globos, Conexion sql) {
        this.globos = globos;
        this.sql = sql;
    }

    @Override
    public int getRowCount() {
        return globos.size();
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
        Globo globo = globos.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Tamaño
                return globo.getTamano();
            case 2: // Material
                return globo.getMaterial();
            case 3: // Color
                return globo.getColor();
            case 4: // Forma
                return globo.getForma();
            case 5: // Precio
                double precio = globo.getPrecio();
                if (precio < 0) {
                    precio = 0;
                }
                String precioFormateado = String.format("L. %,.2f", precio);
                return precioFormateado;
            case 6: // Cantidad por Paquete
                return globo.getCantidadPaquete();
            default:
                return null;
        }
    }
}
