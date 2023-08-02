package Objetos;

public class TarjetaDetalle {
    private int id;
    private int idMaterial;
    private int idTarjeta;
    private int cantidad;
    private double precio;

    public static String nombreTabla = "tarjetas_detalles";

    public TarjetaDetalle() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, idMaterial, idTarjeta, cantidad, precio};
    }

    public TarjetaDetalle(int id, int idMaterial, int idTarjeta, int cantidad, double precio) {
        this.id = id;
        this.idMaterial = idMaterial;
        this.idTarjeta = idTarjeta;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(int idMaterial) {
        this.idMaterial = idMaterial;
    }

    public int getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(int idTarjeta) {
        this.idTarjeta = idTarjeta;
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
