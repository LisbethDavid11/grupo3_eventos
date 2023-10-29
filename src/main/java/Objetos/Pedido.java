package Objetos;

import java.sql.Date;

public class Pedido {
    private int id;
    private String codigoPedido;
    private Date fechaPedido;
    private Date fechaEntrega;
    private String descripcion;
    private int clienteId;
    private String estado;
    private double precioEnvio; // Cambié el tipo de precioEnvio a double
    private int usuarioId;
    private String entrega;

    public static String nombreTabla = "pedidos";

    public Pedido() {
    }

    public Pedido(int id, String codigoPedido, Date fechaPedido, Date fechaEntrega, String descripcion, int clienteId, String entrega, double precioEnvio, int usuarioId, String estado) {
        this.id = id;
        this.codigoPedido = codigoPedido;
        this.fechaPedido = fechaPedido;
        this.fechaEntrega = fechaEntrega;
        this.descripcion = descripcion;
        this.clienteId = clienteId;
        this.entrega = entrega;
        this.precioEnvio = precioEnvio;
        this.usuarioId = usuarioId;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public double getPrecioEnvio() {
        return precioEnvio;
    }

    public void setPrecioEnvio(double precioEnvio) {
        this.precioEnvio = precioEnvio;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
