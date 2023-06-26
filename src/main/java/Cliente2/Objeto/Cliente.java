package Objeto;

public class Cliente {
    /**
     estos son los campos de este objeto, para extraer los datos que
     aqui los vamos a guardar
     */
    private int id;
    private String nombre;
    private String apellido;
    private String identidad;
    private String telefono;
    private String domicilio;
    private String tipo_cliente;
    public static String nombreTabla = "clientes"; /** nombre de la base de datos*/
    /**
     estos es en la base de datos, como se
     van a llamar las columnas
     */
    public static String[] columnasdeTable = {
            //"Id",
            "nombre",
            "apellido",
            "identidad",
            "telefono",
            "domicilio",
            "tipo_cliente"
    };
    /**
     estas son las columnas que lleva la tabla,
     las que va ver el usuario
     */
    public static String[] columnas = {
            "#",
            "Nombre",
            "Apellido",
            "Identidad",
            "Telefono",
            "Domicilio",
            "Tipo_cliente"
    };

    public Cliente() {

        setId(0);
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

}

