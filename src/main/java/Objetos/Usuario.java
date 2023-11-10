package Objetos;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private String contrasena; // Considerar medidas de seguridad para manejar contraseñas
    private String imagen;
    private int rolId; // ID del rol asociado

    public static String nombreTabla = "usuarios";

    public Usuario() {
    }

    // Constructor con todos los campos
    public Usuario(int id, String nombre, String correo, String contrasena, String imagen, int rolId) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.imagen = imagen;
        this.rolId = rolId;
    }

    // Métodos getters y setters
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    // Método para convertir a fila de tabla (si es necesario para una interfaz gráfica)
    public Object[] toTableRow() {
        return new Object[] {id, nombre, correo, imagen, rolId};
    }
}
