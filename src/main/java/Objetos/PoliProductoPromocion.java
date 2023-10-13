package Objetos;

public interface PoliProductoPromocion {
    int getIdDetalle();
    int getID();
    String getNombre();
    int getCantidad();
    double getPrecio();
    double getPromocion();
    String getTipo();

    void setIdDetalle(int idDetalle);
    void setID(int id);
    void setNombre(String nombre);
    void setCantidad(int cantidad);
    void setPrecio(double precio);
    void setPromocion(double promocion);
    void setTipo(String tipo);
}
