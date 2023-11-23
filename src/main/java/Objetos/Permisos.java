package Objetos;

import java.util.Date;

public class Permisos {
    private int id;
    private int id_permiso;
    private String nombre;
    private String nombre_permiso;
    private boolean crear;
    private boolean editar;
    private boolean ver;
    private boolean listar;

    public Permisos() {
    }
    public Permisos(int id, String nombre, boolean crear, boolean editar, boolean ver, boolean listar) {
        this.id = id;
        this.nombre = nombre;
        this.crear = crear;
        this.editar = editar;
        this.ver = ver;
        this.listar = listar;
    }

    public Permisos(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.crear = true;
        this.editar = true;
        this.ver = true;
        this.listar = true;
    }

    public Permisos(String nombre) {
        this.nombre = nombre;
        this.crear = true;
        this.editar = true;
        this.ver = true;
        this.listar = true;
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

    public boolean isCrear() {
        return crear;
    }

    public void setCrear(boolean crear) {
        this.crear = crear;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }

    public boolean isVer() {
        return ver;
    }

    public void setVer(boolean ver) {
        this.ver = ver;
    }

    public boolean isListar() {
        return listar;
    }

    public void setListar(boolean listar) {
        this.listar = listar;
    }

    public String getNombre_permiso() {
        return nombre_permiso;
    }

    public void setNombre_permiso(String nombre_permiso) {
        this.nombre_permiso = nombre_permiso;
    }

    public int getId_permiso() {
        return id_permiso;
    }

    public void setId_permiso(int id_permiso) {
        this.id_permiso = id_permiso;
    }
}
