package Objetos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Empleados {
    public int id;
    public String Identidad;
    public String Genero;
    public String correo;
    public String telefono;
    public String contacto;
    public String nombres;
    public String apellidos;
    public int edad;
    public String direccion;
    public String TipoDeEmpleado;
    public String nombreContactoDeEmergencia;
    public static String nombreDeTabla = "Empleados";
    public static String[] ColumnasDeTabla={
            "Identidad",
            "Nombres",
            "Apellidos",
            "Edad",
            "Correo electrónico",
            "Teléfono",
            "Nombre contacto de emergencia",
            "Contacto de emergencia",
            "Dirección"
    };


    public static String[] Columnas={
            "N",
            "Identidad",
            "Nombres",
            "Apellidos",
            "Teléfono"

    };

    public Empleados() {
    }

    public Empleados(int id, String identidad, String genero, String correo, String telefono, String contacto, String nombres, String apellidos, int edad, String direccion, String tipoDeEmpleado, String nombreContactoDeEmergencia) {
        this.id = id;
        Identidad = identidad;
        Genero = genero;
        this.correo = correo;
        this.telefono = telefono;
        this.contacto = contacto;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.edad = edad;
        this.direccion = direccion;
        TipoDeEmpleado = tipoDeEmpleado;
        this.nombreContactoDeEmergencia = nombreContactoDeEmergencia;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentidad() {
        return Identidad;
    }

    public void setIdentidad(String identidad) {
        Identidad = identidad;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getTipoDeEmpleado() {
        return TipoDeEmpleado;
    }

    public void setTipoDeEmpleado(String tipoDeEmpleado) {
        TipoDeEmpleado = tipoDeEmpleado;
    }

    public String getNombreContactoDeEmergencia() {
        return nombreContactoDeEmergencia;
    }

    public void setNombreContactoDeEmergencia(String nombreContactoDeEmergencia) {
        this.nombreContactoDeEmergencia = nombreContactoDeEmergencia;
    }

    public static boolean ComprobarIdentidad(String identidad){
        String digitoRegional = identidad.substring(0,2);
        String digitoMunicipal = identidad.substring(2,4);
        System.out.println(digitoRegional + " " + digitoMunicipal);
        if (Integer.parseInt(digitoRegional) <1 || Integer.parseInt(digitoRegional) >18){

            return false;
        }
        switch (digitoRegional){
            case "01"->{
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >8){
                    return false;
                }

            }
            case "02"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >10) {
                    return false;
                }
            }
            case "03"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >21) {
                    return false;
                }
            }
            case "04"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >23) {
                    return false;
                }
            }
            case "05"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >12) {
                    return false;
                }
            }
            case "06"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >16) {
                    return false;
                }
            }
            case "07"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >19) {
                    return false;
                }
            }
            case "08"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >28) {
                    return false;
                }
            }
            case "09"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >6) {
                    return false;
                }
            }
            case "10"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >17) {
                    return false;
                }
            }
            case "11"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >4) {
                    return false;
                }
            }
            case "12"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >19) {
                    return false;
                }
            }
            case "13"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >28) {
                    return false;
                }
            }
            case "14"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >16) {
                    return false;
                }
            }
            case "15"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >23) {
                    return false;
                }
            }
            case "16"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >28) {
                    return false;
                }
            }
            case "17"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >9) {
                    return false;
                }
            }
            case "18"-> {
                if (Integer.parseInt(digitoMunicipal) <1 || Integer.parseInt(digitoMunicipal) >11) {
                    return false;
                }
            }
            default -> {
                return true;
            }


        }


        return true;
    }
    public static boolean ValidarCorreo(String correo){
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


        Matcher mather = pattern.matcher(correo);

        if (mather.find()) {
           return false;
        } else {
            return true;
        }
    }
}
