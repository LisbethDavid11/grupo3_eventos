package Objetos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Proveedores {
    //Campos del objeto Proveedores
     private int id;
     private String nombre;
    private String rtn;
    private String telefono;
    private String correo;
    private String direccion;
    private String descripcion;
    private String nombreVendedor;
    private String telefonoVendedor;

    //Cuidado no olvidar poner el nombre de la tabla, porque no funciona la base de datos.
    public static String nombreTabla = "proveedores";

    //Esto es lo que se maneja en la base de datos

    public static String[] columnasTabla = {
            "Nombre",
            "rtn",
            "telefono",
            "correo",
            "descripcion",
            "nombreVendedor",
            "telefonoVendedor"
    };

    //Estos datos de las columnas es lo que vera el ususario

    public static String[] columnas = {
            "N",
            "Nombre Empresa",
            "RTN",
            "Teléfono",
            "Correo",
            "Dirección"
    };

    //Crear un objeto vacío mediante un cosntructor


    public Proveedores() {
        setId(0);
    }

    //Constructor que se rellene para crear objetos que ya existen y así mismo editarlos

    public Proveedores(int id, String nombre, String rtn, String telefono, String correo, String direccion, String descripcion, String nombreVendedor, String telefonoVendedor) {
        this.id = id;
        this.nombre = nombre;
        this.rtn = rtn;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.descripcion = descripcion;
        this.nombreVendedor = nombreVendedor;
        this.telefonoVendedor = telefonoVendedor;
    }

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

    public String getRtn() {
        return rtn;
    }

    public void setRtn(String rtn) {
        this.rtn = rtn;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {

        return descripcion;
    }

    public void setDescripcion(String descripcion) {

        this.descripcion = descripcion;
    }

    public String getNombreVendedor() {

        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {

        this.nombreVendedor = nombreVendedor;
    }

    public String getTelefonoVendedor() {

        return telefonoVendedor;
    }

    public void setTelefonoVendedor(String telefonoVendedor) {

        this.telefonoVendedor = telefonoVendedor;
    }

    public static boolean validarFormatoTelefono(String telefono){
        try{
            int tel = Integer.parseInt(telefono);
        }catch (NumberFormatException error){
            return false;
        }

        if(telefono.charAt(0) == '1' || telefono.charAt(0) == '4' || telefono.charAt(0) == '5' || telefono.charAt(0) == '6' || telefono.charAt(0) == '7' || telefono.charAt(0) == '0' ){
            return false;
        }
        return true;
    }

    public static boolean validarFormatoCorreo(String correo){

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher matcher = pattern.matcher(correo);

        if (!matcher.find()){
            return false;
        }

        return true;
    }

    public static boolean validarFormatoNombre(String nombre){

        Pattern pattern = Pattern.compile("^[_A-Za-z-\\+]");

        Matcher matcher = pattern.matcher(nombre);

        if (!matcher.find()){
            return false;
        }

        return true;
    }
}


