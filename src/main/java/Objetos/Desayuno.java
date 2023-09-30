package Objetos;
import javax.swing.*;

public class Desayuno {
    private int id;
    private String imagen;
    private String nombre;
    private String descripcion;
    private int proveedor_id;
    private int cantidad;
    private double precio_desayuno;
    private double mano_obra;

    public static String nombreTabla = "desayunos";

    public Desayuno() {
    }

    public Object[] toTableRow() {
        return new Object[] {id, imagen, nombre, descripcion, proveedor_id, cantidad, precio_desayuno, mano_obra};
    }

    public Desayuno(int id, String imagen, String nombre, String descripcion, int proveedor_id, int cantidad, double precio_desayuno, double mano_obra) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.proveedor_id = proveedor_id;
        this.cantidad = cantidad;
        this.precio_desayuno = precio_desayuno;
        this.mano_obra = mano_obra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getProveedor_id() {
        return proveedor_id;
    }

    public void setProveedor_id(int proveedor_id) {
        this.proveedor_id = proveedor_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio_desayuno() {
        return precio_desayuno;
    }

    public void setPrecio_desayuno(double precio_desayuno) {
        this.precio_desayuno = precio_desayuno;
    }

    public double getMano_obra() {
        return mano_obra;
    }

    public void setMano_obra(double mano_obra) {
        this.mano_obra = mano_obra;
    }
}
