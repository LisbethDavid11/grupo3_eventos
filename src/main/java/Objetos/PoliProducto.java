package Objetos;

public interface PoliProducto {
    int getIdDetalle();
    int getID();
    String getNombre();
    int getCantidad();
    double getPrecio();
    String getTipo();

    void setIdDetalle(int idDetalle);
    void setID(int id);
    void setNombre(String nombre);
    void setCantidad(int cantidad);
    void setPrecio(double precio);
    void setTipo(String tipo);
}
