package Objetos;

import java.util.HashMap;
import java.util.Map;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String identidad;
    private String telefono;
    private String domicilio;
    private String tipo_cliente;
    public static String nombreTabla = "clientes";
    public static final String[] columnas = {"N°", "Identidad", "Nombre Completo", "Teléfono", "Tipo", "Domicilio"};

    public Cliente() {
    }

    public Cliente(int id, String nombre, String apellido, String identidad, String telefono, String domicilio, String tipo_cliente) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identidad = identidad;
        this.telefono = telefono;
        this.domicilio = domicilio;
        this.tipo_cliente = tipo_cliente;
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
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getIdentidad() {
        return identidad;
    }
    public void setIdentidad(String identidad) {
        this.identidad = identidad;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDomicilio() {
        return domicilio;
    }
    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
    public String getTipo_cliente() {
        return tipo_cliente;
    }
    public void setTipo_cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
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

        if (RANGOS_DIGITOS_MUNICIPALES.containsKey(digitoRegionalStr)) {
            int rangoMaximo = RANGOS_DIGITOS_MUNICIPALES.get(digitoRegionalStr);
            if (digitoMunicipal < 1 || digitoMunicipal > rangoMaximo) {
                return false;
            }
        }

        return true;
    }
}