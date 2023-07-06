package Objetos;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Empleado {
    private int id;
    private String identidad;
    private String nombres;
    private String apellidos;
    private String genero;
    private String edad;
    private String correo;
    private String telefono;
    private String nombreContactoDeEmergencia;
    private String contactoDeEmergencia;
    private String direccion;
    private String tipoDeEmpleado;

    public static String nombreTabla = "empleados";
    public static final String[] columnas = {"N°", "Identidad", "Nombre Completo", "Teléfono", "Tipo de Empleado"};

    public Empleado() {
    }

    public Empleado(int id, String identidad, String nombres, String apellidos, String genero, String edad, String correo, String telefono, String nombreContactoDeEmergencia, String contactoDeEmergencia, String direccion, String tipoDeEmpleado) {
        this.id = id;
        this.identidad = identidad;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.genero = genero;
        this.edad = edad;
        this.correo = correo;
        this.telefono = telefono;
        this.nombreContactoDeEmergencia = nombreContactoDeEmergencia;
        this.contactoDeEmergencia = contactoDeEmergencia;
        this.direccion = direccion;
        this.tipoDeEmpleado = tipoDeEmpleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentidad() {
        return identidad;
    }

    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreContactoDeEmergencia() {
        return nombreContactoDeEmergencia;
    }

    public void setNombreContactoDeEmergencia(String nombreContactoDeEmergencia) {
        this.nombreContactoDeEmergencia = nombreContactoDeEmergencia;
    }

    public String getContactoDeEmergencia() {
        return contactoDeEmergencia;
    }

    public void setContactoDeEmergencia(String contactoDeEmergencia) {
        this.contactoDeEmergencia = contactoDeEmergencia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoDeEmpleado() {
        return tipoDeEmpleado;
    }

    public void setTipoDeEmpleado(String tipoDeEmpleado) {
        this.tipoDeEmpleado = tipoDeEmpleado;
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

    public static boolean comprobarIdentidad(String identidad) {
        String digitoRegionalStr = identidad.substring(0, 2);
        String digitoMunicipalStr = identidad.substring(2, 4);

        int digitoRegional = Integer.parseInt(digitoRegionalStr);
        int digitoMunicipal = Integer.parseInt(digitoMunicipalStr);

        if (digitoRegional < 1 || digitoRegional > 18) {
            return false;
        }

        int rangoMaximo = (digitoRegional == 9) ? 6 : 28;
        if (digitoMunicipal < 1 || digitoMunicipal > rangoMaximo) {
            return false;
        }

        return true;
    }


    public static boolean validarCorreo(String correo) {
        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(correo);
        return !matcher.find();
    }
}
