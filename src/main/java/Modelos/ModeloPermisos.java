package Modelos;

import Objetos.Permisos;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ModeloPermisos extends AbstractTableModel {
    private final String[] columnas = {"N°", "Rol", "Permisos"};

    private final List<Permisos> permisos;


    public ModeloPermisos(List<Permisos> permisos) {
        this.permisos = permisos;
    }

    @Override
    public int getRowCount() {
        return permisos.size();
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
        Permisos permiso = permisos.get(rowIndex);
        if (permisos.isEmpty() || rowIndex >= permisos.size()) {
            if (rowIndex == 0 && columnIndex == 0) {
                return "No hay datos";
            }
            return null;
        }


        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre de Permiso
                return permiso.obtenerNombreRol();
            case 2: // Permiso (booleano)
                return obtenerNombresPermisos(permiso);
            default:
                return null;
        }
    }

    private String obtenerNombresPermisos(Permisos permiso) {
        boolean algunPermiso = false;
        StringBuilder nombres = new StringBuilder();

        if (permiso.isCliente()) {
            nombres.append("Cliente").append(", ");
            algunPermiso = true;
        }
        if (permiso.isEmpleado()) {
            nombres.append("Empleado").append(", ");
            algunPermiso = true;
        }
        if (permiso.isFloristeria()) {
            nombres.append("Floristería").append(", ");
            algunPermiso = true;
        }
        if (permiso.isArreglo()) {
            nombres.append("Arreglo").append(", ");
            algunPermiso = true;
        }
        if (permiso.isUsuario()) {
            nombres.append("Usuario").append(", ");
            algunPermiso = true;
        }
        if (permiso.isMaterial()) {
            nombres.append("Material").append(", ");
            algunPermiso = true;
        }
        if (permiso.isProveedor()) {
            nombres.append("Proveedor").append(", ");
            algunPermiso = true;
        }
        if (permiso.isCompra()) {
            nombres.append("Compra").append(", ");
            algunPermiso = true;
        }
        if (permiso.isTarjeta()) {
            nombres.append("Tarjeta").append(", ");
            algunPermiso = true;
        }
        if (permiso.isManualidad()) {
            nombres.append("Manualidad").append(", ");
            algunPermiso = true;
        }
        if (permiso.isGlobo()) {
            nombres.append("Globo").append(", ");
            algunPermiso = true;
        }
        if (permiso.isDesayuno()) {
            nombres.append("Desayuno").append(", ");
            algunPermiso = true;
        }
        if (permiso.isVenta()) {
            nombres.append("Venta").append(", ");
            algunPermiso = true;
        }
        if (permiso.isMobiliario()) {
            nombres.append("Mobiliario").append(", ");
            algunPermiso = true;
        }
        if (permiso.isPedido()) {
            nombres.append("Pedido").append(", ");
            algunPermiso = true;
        }
        if (permiso.isPromocion()) {
            nombres.append("Promoción").append(", ");
            algunPermiso = true;
        }
        if (permiso.isEvento()) {
            nombres.append("Evento").append(", ");
            algunPermiso = true;
        }
        if (permiso.isActividad()) {
            nombres.append("Actividad").append(", ");
            algunPermiso = true;
        }
        if (permiso.isAlquiler()) {
            nombres.append("Alquiler").append(", ");
            algunPermiso = true;
        }
        if (permiso.isRol()) {
            nombres.append("Rol").append(", ");
            algunPermiso = true;
        }
        // Si no se ha encontrado ningún permiso
        if (!algunPermiso) {
            return null;
        }

        // Elimina la última coma y espacio extra
        return nombres.substring(0, nombres.length() - 2);
    }
}