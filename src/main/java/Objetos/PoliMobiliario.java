package Objetos;

public class PoliMobiliario extends PoliProductosGeneral {
    private String nombreMobiliario;
    private String color;
    private String tipoEvento;
    private String descripcion;
    private String image;
    private int disponibilidad;
    private int id_cliente;
    private int id_empleado;
    private double precioUnitario;
    private String fechaEntrega;

    public static String nombreTabla = "mobiliario";

    public Object[] toTableRow() {
        return new Object[]{id, nombreMobiliario, color, tipoEvento, descripcion, image, disponibilidad, id_cliente, id_empleado, cantidad, precioUnitario, fechaEntrega};
    }

    public PoliMobiliario() {
        super();
    }

    public PoliMobiliario(int idDetalle, int id, String nombre, int cantidad, double precio, String tipo,
                          String nombreMobiliario, String color, String tipoEvento, String descripcion, String image, int disponibilidad, int id_cliente, int id_empleado, double precioUnitario, String fechaEntrega) {
        super(idDetalle, id, nombre, cantidad, precio, tipo);
        this.nombreMobiliario = nombreMobiliario;
        this.color = color;
        this.tipoEvento = tipoEvento;
        this.descripcion = descripcion;
        this.image = image;
        this.disponibilidad = disponibilidad;
        this.id_cliente = id_cliente;
        this.id_empleado = id_empleado;
        this.precioUnitario = precioUnitario;
        this.fechaEntrega = fechaEntrega;
    }

    public String getNombreMobiliario() {
        return nombreMobiliario;
    }

    public void setNombreMobiliario(String nombreMobiliario) {
        this.nombreMobiliario = nombreMobiliario;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
