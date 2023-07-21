package Objetos;

public class Tarjeta {
    private int id;
    private String ocasion;
    private double precio;
    private String disponible;

    public static String nombreTabla = "tarjetas";

    public Tarjeta() {
    }

    public Object[] toTableRow() {
        return new Object[] {id,ocasion, precio, disponible};
    }

    public Tarjeta(int id, String ocasion, double precio, String disponible) {
        this.id = id;
        this.ocasion = ocasion;
        this.precio = precio;
        this.disponible = disponible;
    }

// getters y setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOcasion() {
        return ocasion;
    }

    public void setOcasion(String ocasion) {
        this.ocasion = ocasion;
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
}
