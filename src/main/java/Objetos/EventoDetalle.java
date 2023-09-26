package Objetos;

public class EventoDetalle {
    private int id;
    private int eventoId;
    private String tipoDetalle;
    private int detalleId;
    private int cantidad;
    private double precio;

    public static String nombreTabla = "detalles_eventos";

    public EventoDetalle() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, eventoId, tipoDetalle, detalleId, cantidad, precio};
    }

    public EventoDetalle(int id, int eventoId, String tipoDetalle, int detalleId, int cantidad, double precio) {
        this.id = id;
        this.eventoId = eventoId;
        this.tipoDetalle = tipoDetalle;
        this.detalleId = detalleId;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    public String getTipoDetalle() {
        return tipoDetalle;
    }

    public void setTipoDetalle(String tipoDetalle) {
        this.tipoDetalle = tipoDetalle;
    }

    public int getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(int detalleId) {
        this.detalleId = detalleId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
