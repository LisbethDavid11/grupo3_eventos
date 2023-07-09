package Objetos;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Proveedor {
    private int id;
    private String empresaProveedora;
    private String rtn;
    private String telefono;
    private String correo;
    private String direccion;
    private String descripcion;
    private String nombreVendedor;
    private String telefonoVendedor;

    public static String nombreTabla = "proveedores";

    public static String[] columnasTabla = {
            "Nombre",
            "RTN",
            "Teléfono",
            "Correo",
            "Descripción",
            "Nombre del Vendedor",
            "Teléfono del Vendedor"
    };

    public static String[] columnas = {
            "N",
            "Nombre de la Empresa",
            "RTN",
            "Teléfono",
            "Correo",
            "Dirección"
    };

    public Proveedor() {
        setId(0);
    }

    public Proveedor(int id, String empresaProveedora, String rtn, String telefono, String correo, String direccion, String descripcion, String nombreVendedor, String telefonoVendedor) {
        this.id = id;
        this.empresaProveedora = empresaProveedora;
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

    public String getEmpresaProveedora() {
        return empresaProveedora;
    }

    public void setEmpresaProveedora(String empresaProveedora) {
        this.empresaProveedora = empresaProveedora;
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

    public static boolean validarFormatoCorreo(String correo) {
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correo);
        return matcher.find();
    }

    private static final Map<String, Integer> RANGOS_DIGITOS_MUNICIPALES = new HashMap<>();

    static {
        RANGOS_DIGITOS_MUNICIPALES.put("01", 8);
        RANGOS_DIGITOS_MUNICIPALES.put("02", 10);
        RANGOS_DIGITOS_MUNICIPALES.put("03", 21);
        RANGOS_DIGITOS_MUNICIPALES.put("04", 23);
        RANGOS_DIGITOS_MUNICIPALES.put("05", 12);
        RANGOS_DIGITOS_MUNICIPALES.put("06", 16);
        RANGOS_DIGITOS_MUNICIPALES.put("07", 19);
        RANGOS_DIGITOS_MUNICIPALES.put("08", 28);
        RANGOS_DIGITOS_MUNICIPALES.put("09", 6);
        RANGOS_DIGITOS_MUNICIPALES.put("10", 17);
        RANGOS_DIGITOS_MUNICIPALES.put("11", 4);
        RANGOS_DIGITOS_MUNICIPALES.put("12", 19);
        RANGOS_DIGITOS_MUNICIPALES.put("13", 28);
        RANGOS_DIGITOS_MUNICIPALES.put("14", 16);
        RANGOS_DIGITOS_MUNICIPALES.put("15", 23);
        RANGOS_DIGITOS_MUNICIPALES.put("16", 28);
        RANGOS_DIGITOS_MUNICIPALES.put("17", 9);
        RANGOS_DIGITOS_MUNICIPALES.put("18", 11);
    }

    public static boolean comprobarRTN(String identidad) {
        String digitoRegionalStr = identidad.substring(0, 2);
        String digitoMunicipalStr = identidad.substring(2, 4);
        String anoNacimientoStr = identidad.substring(4, 8); // Nuevas líneas

        int digitoRegional = Integer.parseInt(digitoRegionalStr);
        int digitoMunicipal = Integer.parseInt(digitoMunicipalStr);
        int anoNacimiento = Integer.parseInt(anoNacimientoStr); // Nueva línea

        if (digitoRegional < 1 || digitoRegional > 18) {
            return false;
        }

        if (RANGOS_DIGITOS_MUNICIPALES.containsKey(digitoRegionalStr)) {
            int rangoMaximo = RANGOS_DIGITOS_MUNICIPALES.get(digitoRegionalStr);
            if (digitoMunicipal < 1 || digitoMunicipal > rangoMaximo) {
                return false;
            }
        }

        int anoActual = LocalDate.now().getYear();
        int edad = anoActual - anoNacimiento;
        if (edad < 18 || edad > 60) {
            return false;
        }

        return true;

    }


}
