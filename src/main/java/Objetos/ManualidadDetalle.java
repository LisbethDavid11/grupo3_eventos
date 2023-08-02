package Objetos;

public class ManualidadDetalle {
    private int id;
    private int manualidadId;
    private int materialId;
    private int cantidad;
    private double precio;

    public static String nombreTabla = "detalles_manualidades";

    public ManualidadDetalle() {
    }

    public Object[] toTableRow() {
        return new Object[]{id, manualidadId, materialId, cantidad};
    }

    public ManualidadDetalle(int id, int manualidadId, int materialId, int cantidad, double precio){
        this.id = id;
        this.manualidadId = manualidadId;
        this.materialId = materialId;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getManualidadId() {
        return manualidadId;
    }

    public void setManualidadId(int manualidadId) {
        this.manualidadId = manualidadId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
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
