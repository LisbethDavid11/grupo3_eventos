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
        StringBuilder nombres = new StringBuilder();

        if (permiso.isCliente()) {
            nombres.append("Cliente").append(", ");
        }
        if (permiso.isEmpleado()) {
            nombres.append("Empleado").append(", ");
        }
        if (permiso.isFloristeria()) {
            nombres.append("Floristeria").append(", ");
        }
        if (permiso.isArreglo()) {
            nombres.append("Arreglo").append(", ");
        }
        if (permiso.isUsuario()) {
            nombres.append("Usuario").append(", ");
        }
        if (permiso.isMaterial()) {
            nombres.append("Material").append(", ");
        }
        if (permiso.isProveedor()) {
            nombres.append("Proveedor").append(", ");
        }
        if (permiso.isCompra()) {
            nombres.append("Compra").append(", ");
        }
        if (permiso.isTarjeta()) {
            nombres.append("Tarjeta").append(", ");
        }
        if (permiso.isManualidad()) {
            nombres.append("Manualidad").append(", ");
        }
        if (permiso.isGlobo()) {
            nombres.append("Globo").append(", ");
        }
        if (permiso.isDesayuno()) {
            nombres.append("Desayuno").append(", ");
        }
        if (permiso.isVenta()) {
            nombres.append("Venta").append(", ");
        }
        if (permiso.isMobiliario()) {
            nombres.append("Mobiliario").append(", ");
        }
        if (permiso.isPedido()) {
            nombres.append("Pedido").append(", ");
        }
        if (permiso.isPromocion()) {
            nombres.append("Promocion").append(", ");
        }
        if (permiso.isEvento()) {
            nombres.append("Evento").append(", ");
        }
        if (permiso.isActividad()) {
            nombres.append("Actividad").append(", ");
        }
        if (permiso.isAlquiler()) {
            nombres.append("Alquiler").append(", ");
        }
        if (permiso.isRol()) {
            nombres.append("Rol").append(", ");
        }


        return nombres.toString();
    }





}