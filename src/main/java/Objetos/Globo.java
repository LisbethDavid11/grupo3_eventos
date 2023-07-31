package Objetos;

public class Globo {
    private int id;
    private String codigoGlobo;
    private String tipo;
    private String material;
    private String para;
    private String tamano;
    private String color;
    private String forma;
    private int cantidadPaquete;
    private boolean portaGlobo;
    private int cantidad;
    private double precio;

    public static String nombreTabla = "globos";

    public Globo() {
    }

    public Object[] toTableRow() {
        return new Object[] {
                id, codigoGlobo, tipo, material, para, tamano, color, forma,
                cantidadPaquete, portaGlobo, cantidad, precio
        };
    }

    public Globo(int id, String codigoGlobo, String tipo, String material,
                 String para, String tamano, String color, String forma,
                 int cantidadPaquete, boolean portaGlobo, int cantidad, double precio) {
        this.id = id;
        this.codigoGlobo = codigoGlobo;
        this.tipo = tipo;
        this.material = material;
        this.para = para;
        this.tamano = tamano;
        this.color = color;
        this.forma = forma;
        this.cantidadPaquete = cantidadPaquete;
        this.portaGlobo = portaGlobo;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoGlobo() {
        return codigoGlobo;
    }

    public void setCodigoGlobo(String codigoGlobo) {
        this.codigoGlobo = codigoGlobo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getForma() {
        return forma;
    }

    public void setForma(String forma) {
        this.forma = forma;
    }

    public int getCantidadPaquete() {
        return cantidadPaquete;
    }

    public void setCantidadPaquete(int cantidadPaquete) {
        this.cantidadPaquete = cantidadPaquete;
    }

    public boolean isPortaGlobo() {
        return portaGlobo;
    }

    public void setPortaGlobo(boolean portaGlobo) {
        this.portaGlobo = portaGlobo;
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
