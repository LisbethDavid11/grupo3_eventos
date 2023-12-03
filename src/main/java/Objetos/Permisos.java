package Objetos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Permisos {
    private int id;
    private int idRol;
    private boolean cliente;
    private boolean empleado;
    private boolean floristeria;
    private boolean arreglo;
    private boolean usuario;
    private boolean material;
    private boolean proveedor;
    private boolean compra;
    private boolean tarjeta;
    private boolean manualidad;
    private boolean globo;
    private boolean desayuno;
    private boolean venta;
    private boolean mobiliario;
    private boolean pedido;
    private boolean promocion;
    private boolean evento;
    private boolean actividad;
    private boolean alquiler;
    private boolean rol;

    // Constructor
    public Permisos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public boolean isCliente() {
        if (getIdRol() == 1)
            return true;
        else
            return cliente;

    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    public boolean isEmpleado() {
        if (getIdRol() == 1)
            return true;
        else
        return empleado;
    }

    public void setEmpleado(boolean empleado) {
        this.empleado = empleado;
    }

    public boolean isFloristeria() {
        if (getIdRol() == 1)
            return true;
        else
            return floristeria;
    }

    public void setFloristeria(boolean floristeria) {
        this.floristeria = floristeria;
    }

    public boolean isArreglo() {
        if (getIdRol() == 1)
            return true;
        else
            return arreglo;
    }

    public void setArreglo(boolean arreglo) {
        this.arreglo = arreglo;
    }

    public boolean isUsuario() {
        if (getIdRol() == 1)
            return true;
        else
            return usuario;
    }

    public void setUsuario(boolean usuario) {
        this.usuario = usuario;
    }

    public boolean isMaterial() {
        if (getIdRol() == 1)
            return true;
        else
            return material;
    }

    public void setMaterial(boolean material) {
        this.material = material;
    }

    public boolean isProveedor() {

        if (getIdRol() == 1)
            return true;
        else
            return proveedor;
    }

    public void setProveedor(boolean proveedor) {
        this.proveedor = proveedor;
    }

    public boolean isCompra() {
        if (getIdRol() == 1)
            return true;
        else
            return compra;
    }

    public void setCompra(boolean compra) {
        this.compra = compra;
    }

    public boolean isTarjeta() {
        if (getIdRol() == 1)
            return true;
        else
            return tarjeta;
    }

    public void setTarjeta(boolean tarjeta) {
        this.tarjeta = tarjeta;
    }

    public boolean isManualidad() {
        if (getIdRol() == 1)
            return true;
        else
            return manualidad;
    }

    public void setManualidad(boolean manualidad) {
        this.manualidad = manualidad;
    }

    public boolean isGlobo() {
        if (getIdRol() == 1)
            return true;
        else
            return globo;
    }

    public void setGlobo(boolean globo) {
        this.globo = globo;
    }

    public boolean isDesayuno() {
        if (getIdRol() == 1)
            return true;
        else
            return desayuno;
    }

    public void setDesayuno(boolean desayuno) {
        this.desayuno = desayuno;
    }

    public boolean isVenta() {
        if (getIdRol() == 1)
            return true;
        else
            return venta;
    }

    public void setVenta(boolean venta) {
        this.venta = venta;
    }

    public boolean isMobiliario() {
        if (getIdRol() == 1)
            return true;
        else
            return mobiliario;
    }

    public void setMobiliario(boolean mobiliario) {
        this.mobiliario = mobiliario;
    }

    public boolean isPedido() {
        if (getIdRol() == 1)
            return true;
        else
            return pedido;
    }

    public void setPedido(boolean pedido) {
        this.pedido = pedido;
    }

    public boolean isPromocion() {
        if (getIdRol() == 1)
            return true;
        else
            return promocion;
    }

    public void setPromocion(boolean promocion) {
        this.promocion = promocion;
    }

    public boolean isEvento() {
        if (getIdRol() == 1)
            return true;
        else
            return evento;
    }

    public void setEvento(boolean evento) {
        this.evento = evento;
    }

    public boolean isActividad() {
        if (getIdRol() == 1)
            return true;
        else
            return actividad;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    public boolean isAlquiler() {
        if (getIdRol() == 1)
            return true;
        else
            return alquiler;
    }

    public void setAlquiler(boolean alquiler) {
        this.alquiler = alquiler;
    }

    public boolean isRol() {
        if (getIdRol() == 1)
            return true;
        else
            return rol;
    }

    public void setRol(boolean rol) {
        this.rol = rol;
    }

    public String obtenerNombreRol() {
        String nombreRol = null;
        Conexion sql = new Conexion();
        Connection mysql = sql.conectamysql();
        try{
            String sqlcon = "SELECT nombre FROM roles WHERE id = ?";
            try (PreparedStatement statement = mysql.prepareStatement(sqlcon)) {
                statement.setInt(1, getIdRol());

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        nombreRol = resultSet.getString("nombre");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Manejo de errores, puedes personalizar según tus necesidades.
        }

        return nombreRol;
    }
}
