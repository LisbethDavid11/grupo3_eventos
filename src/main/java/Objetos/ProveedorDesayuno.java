package Objetos;

public class ProveedorDesayuno {
    public int idProveedor;
    public String empresaProveedora;
    public String nombreVendedor;

    public ProveedorDesayuno() {
    }

    public ProveedorDesayuno(int idProveedor, String empresaProveedora, String nombreVendedor) {
        this.idProveedor = idProveedor;
        this.empresaProveedora = empresaProveedora;
        this.nombreVendedor = nombreVendedor;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getEmpresaProveedora() {
        return empresaProveedora;
    }

    public void setEmpresaProveedora(String empresaProveedora) {
        this.empresaProveedora = empresaProveedora;
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    @Override
    public String toString() {
        if (idProveedor == 0)
            return "Seleccione un proveedor";
        else
            return idProveedor + " - " + empresaProveedora + " - " + nombreVendedor;
    }
}
