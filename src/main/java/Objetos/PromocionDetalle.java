package Objetos;

public class PromocionDetalle {
    private int id;
    private int promocionId;
    private String tipoDetalle;
    private int detalleId;
    private int cantidad;
    private double precio;

    public static String nombreTabla = "detalles_promociones";

    public PromocionDetalle() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, promocionId, tipoDetalle, detalleId, cantidad, precio};
    }

    public PromocionDetalle(int id, int promocionId, String tipoDetalle, int detalleId, int cantidad, double precio) {
        this.id = id;
        this.promocionId = promocionId;
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

    public int getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(int promocionId) {
        this.promocionId = promocionId;
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
