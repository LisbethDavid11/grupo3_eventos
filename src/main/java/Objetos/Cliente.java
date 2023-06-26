package Objetos;

public class Cliente {
    private int id;
    private String nombre;
    private String apellido;
    private String identidad;
    private String telefono;
    private String domicilio;
    private String tipo_cliente;

    public static String nombreTabla = "clientes";

    public static String[] columnasCampos = {
            "Nombre",
            "Apellido",
            "Identidad",
            "Teléfono",
            "Domicilio",
    };

    public static String[] columnasLista = {
            "#",
            "Nombre",
            "Apellido",
            "Identidad",
            "Teléfono",
    };

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
}
