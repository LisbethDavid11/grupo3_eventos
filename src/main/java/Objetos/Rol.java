package Objetos;

public class Rol {
    private int id;
    private String nombre;
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

    public Rol() {
    }

    public Rol(int id, String nombre, boolean cliente, boolean empleado, boolean floristeria, boolean arreglo, boolean usuario, boolean material, boolean proveedor, boolean compra, boolean tarjeta, boolean manualidad, boolean globo, boolean desayuno, boolean venta, boolean mobiliario, boolean pedido, boolean promocion, boolean evento, boolean actividad, boolean alquiler, boolean rol) {
        this.id = id;
        this.nombre = nombre;
        this.cliente = cliente;
        this.empleado = empleado;
        this.floristeria = floristeria;
        this.arreglo = arreglo;
        this.usuario = usuario;
        this.material = material;
        this.proveedor = proveedor;
        this.compra = compra;
        this.tarjeta = tarjeta;
        this.manualidad = manualidad;
        this.globo = globo;
        this.desayuno = desayuno;
        this.venta = venta;
        this.mobiliario = mobiliario;
        this.pedido = pedido;
        this.promocion = promocion;
        this.evento = evento;
        this.actividad = actividad;
        this.alquiler = alquiler;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isCliente() {
        return cliente;
    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    public boolean isEmpleado() {
        return empleado;
    }

    public void setEmpleado(boolean empleado) {
        this.empleado = empleado;
    }

    public boolean isFloristeria() {
        return floristeria;
    }

    public void setFloristeria(boolean floristeria) {
        this.floristeria = floristeria;
    }

    public boolean isArreglo() {
        return arreglo;
    }

    public void setArreglo(boolean arreglo) {
        this.arreglo = arreglo;
    }

    public boolean isUsuario() {
        return usuario;
    }

    public void setUsuario(boolean usuario) {
        this.usuario = usuario;
    }

    public boolean isMaterial() {
        return material;
    }

    public void setMaterial(boolean material) {
        this.material = material;
    }

    public boolean isProveedor() {
        return proveedor;
    }

    public void setProveedor(boolean proveedor) {
        this.proveedor = proveedor;
    }

    public boolean isCompra() {
        return compra;
    }

    public void setCompra(boolean compra) {
        this.compra = compra;
    }

    public boolean isTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(boolean tarjeta) {
        this.tarjeta = tarjeta;
    }

    public boolean isManualidad() {
        return manualidad;
    }

    public void setManualidad(boolean manualidad) {
        this.manualidad = manualidad;
    }

    public boolean isGlobo() {
        return globo;
    }

    public void setGlobo(boolean globo) {
        this.globo = globo;
    }

    public boolean isDesayuno() {
        return desayuno;
    }

    public void setDesayuno(boolean desayuno) {
        this.desayuno = desayuno;
    }

    public boolean isVenta() {
        return venta;
    }

    public void setVenta(boolean venta) {
        this.venta = venta;
    }

    public boolean isMobiliario() {
        return mobiliario;
    }

    public void setMobiliario(boolean mobiliario) {
        this.mobiliario = mobiliario;
    }

    public boolean isPedido() {
        return pedido;
    }

    public void setPedido(boolean pedido) {
        this.pedido = pedido;
    }

    public boolean isPromocion() {
        return promocion;
    }

    public void setPromocion(boolean promocion) {
        this.promocion = promocion;
    }

    public boolean isEvento() {
        return evento;
    }

    public void setEvento(boolean evento) {
        this.evento = evento;
    }

    public boolean isActividad() {
        return actividad;
    }

    public void setActividad(boolean actividad) {
        this.actividad = actividad;
    }

    public boolean isAlquiler() {
        return alquiler;
    }

    public void setAlquiler(boolean alquiler) {
        this.alquiler = alquiler;
    }

    public boolean isRol() {
        return rol;
    }

    public void setRol(boolean rol) {
        this.rol = rol;
    }
}
