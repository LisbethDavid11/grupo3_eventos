package Modelos;

import Objetos.Conexion;
import Objetos.Permisos;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ModeloPermisosLista extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre", "Crear", "Editar", "Ver", "Listar"};

    private final List<Permisos> permisos;


    public ModeloPermisosLista(List<Permisos> permisos) {
        this.permisos = permisos;
    }

    @Override
    public int getRowCount() {
        return permisos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    public Permisos getPermiso(int column) {
        return permisos.get(column);
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Permisos permiso = permisos.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return permiso.getNombre();
            case 2: // Crear
                return permiso.isCrear() ? "Permitido" : "No Permitido";
            case 3: // Editar
                return permiso.isEditar() ? "Permitido" : "No Permitido";
            case 4: // Listar
                return permiso.isVer() ? "Permitido" : "No Permitido";
            case 5: // Ver
                return permiso.isListar() ? "Permitido" : "No Permitido";
            default:
                return null;
        }
    }


}