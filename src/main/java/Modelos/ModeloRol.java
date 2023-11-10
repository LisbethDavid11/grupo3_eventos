package Modelos;

import Objetos.Rol;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class ModeloRol extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre", "Accesos"};
    private final List<Rol> roles;

    public ModeloRol(List<Rol> roles) {
        this.roles = roles;
    }

    @Override
    public int getRowCount() {
        return roles.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public Rol getRol(int index) {
        return roles.get(index);
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rol rol = roles.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return "  " + rol.getNombre();
            case 2:
                return "  " + obtenerCadenaAccesos(rol);
            default:
                return null;
        }
    }

    private String obtenerCadenaAccesos(Rol rol) {
        List<String> accesos = List.of(
                rol.isCliente() ? "Cliente" : "",
                rol.isEmpleado() ? "Empleado" : "",
                rol.isFloristeria() ? "Floristería" : "",
                rol.isArreglo() ? "Arreglo" : "",
                rol.isUsuario() ? "Usuario" : "",
                rol.isMaterial() ? "Material" : "",
                rol.isProveedor() ? "Proveedor" : "",
                rol.isCompra() ? "Compra" : "",
                rol.isTarjeta() ? "Tarjeta" : "",
                rol.isManualidad() ? "Manualidad" : "",
                rol.isGlobo() ? "Globo" : "",
                rol.isDesayuno() ? "Desayuno" : "",
                rol.isVenta() ? "Venta" : "",
                rol.isMobiliario() ? "Mobiliario" : "",
                rol.isPedido() ? "Pedido" : "",
                rol.isPromocion() ? "Promoción" : "",
                rol.isEvento() ? "Evento" : "",
                rol.isActividad() ? "Actividad" : "",
                rol.isAlquiler() ? "Alquiler" : "",
                rol.isRol() ? "Rol" : ""
        );

        // Filtrar y unir las cadenas no vacías con comas
        return accesos.stream().filter(s -> !s.isEmpty()).collect(Collectors.joining(", "));
    }

}
