package Objetos;

public class ClienteAlquiler {
    private int idCliente;
    private String nombreCliente;
    private String apellidoCliente;

    public ClienteAlquiler() {
    }

    public ClienteAlquiler(int idCliente, String nombreCliente, String apellidoCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    @Override
    public String toString() {
        if (idCliente == 0)
            return "Seleccione un cliente";
        else
            return idCliente + " - " + nombreCliente + " " + apellidoCliente;
    }
}
