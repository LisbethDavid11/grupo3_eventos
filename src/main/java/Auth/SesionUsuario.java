package Auth;

public class SesionUsuario {
    private static SesionUsuario instancia = new SesionUsuario();
    private String nombreUsuario;
    private String imagenUsuario;
    private SesionUsuario() {}

    public static SesionUsuario getInstance() {
        return instancia;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getImagenUsuario() {
        return imagenUsuario;
    }

    public void setImagenUsuario(String imagenUsuario) {
        this.imagenUsuario = imagenUsuario;
    }
}
