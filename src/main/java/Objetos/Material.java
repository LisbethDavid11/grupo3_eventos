package Objetos;

public class Material {
    private int id;
    private String nombre;
    private double precio;
    private String disponible;
    private String descripcion;
    private int proveedorId;

    public static String nombreTabla = "materiales";

    public Material() {
    }

    public Object[] toTableRow() {
        return new Object[] {id, nombre, precio, disponible, descripcion, proveedorId};
    }

    public Material(int id, String nombre, double precio, String disponible, String descripcion, int proveedorId) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.disponible = disponible;
        this.descripcion = descripcion;
        this.proveedorId = proveedorId;
    }

    // getters y setters

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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

}
