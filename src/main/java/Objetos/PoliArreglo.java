package Objetos;

public class PoliArreglo extends PoliProductosGeneral {

    public static String nombreTabla = "arreglos";
    public Object[] toTableRow() {
        return new Object[]{id, nombre, cantidad, precio};
    }

    public PoliArreglo() {
        super();
    }

    public PoliArreglo(int idDetalle, int id, String nombre, int cantidad, double precio, String tipo) {
        super(idDetalle, id, nombre, cantidad, precio, tipo);
    }

    @Override
    public int getID() {
        return super.getID();
    }

    @Override
    public String getNombre() {
        return super.getNombre();
    }

    @Override
    public int getCantidad() {
        return super.getCantidad();
    }

    @Override
    public double getPrecio() {
        return super.getPrecio();
    }

    @Override
    public void setID(int id) {
        super.setID(id);
    }

    @Override
    public void setNombre(String nombre) {
        super.setNombre(nombre);
    }

    @Override
    public void setCantidad(int cantidad) {
        super.setCantidad(cantidad);
    }

    @Override
    public void setPrecio(double precio) {
        super.setPrecio(precio);
    }

    @Override
    public String getTipo() {
        return super.getTipo();
    }

    @Override
    public void setTipo(String tipo) {
        super.setTipo(tipo);
    }

    @Override
    public int getIdDetalle() {
        return super.getIdDetalle();
    }

    @Override
    public void setIdDetalle(int idDetalle) {
        super.setIdDetalle(idDetalle);
    }
}
