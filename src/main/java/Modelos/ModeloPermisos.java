package Modelos;

import Objetos.Conexion;
import Objetos.Permisos;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModeloPermisos extends AbstractTableModel {
    private final String[] columnas = {"N°", "Nombre", "Crear", "Editar", "Listar", "Ver"};

    private final List<Permisos> permisos;


    public ModeloPermisos(List<Permisos> permisos) {
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

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Permisos permiso = permisos.get(rowIndex);

        switch (columnIndex) {
            case 0: // N°
                return rowIndex + 1;
            case 1: // Nombre
                return permiso.getNombre();
            case 2: // Crear
                return permiso.isCrear();
            case 3: // Editar
                return permiso.isEditar();
            case 4: // Listar
                return permiso.isVer();
            case 5: // Ver
                return permiso.isListar();
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex >= 2 && columnIndex <= 5) {
            return Boolean.class; // Las columnas de checkboxes son de tipo Boolean
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 2 && columnIndex <= 5; // Hacer editable las columnas de checkboxes
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex >= 2 && columnIndex <= 5 && aValue instanceof Boolean) {
            switch (columnIndex) {
                case 2: // Crear
                    permisos.get(rowIndex).setCrear((Boolean) aValue);
                    break;
                case 3: // Editar
                    permisos.get(rowIndex).setEditar((Boolean) aValue);
                    break;
                case 4: // Listar
                    permisos.get(rowIndex).setVer((Boolean) aValue);
                    break;
                case 5: // Ver
                    permisos.get(rowIndex).setListar((Boolean) aValue);
                    break;
                default:
                    break;
            }
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    // Renderizador para mostrar el JCheckBox en la tabla
    public static class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
        public CheckBoxRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            setSelected(value != null && (Boolean) value);
            return this;
        }
    }

    // Editor para permitir la edición del JCheckBox en la tabla
    public static class CheckBoxEditor extends AbstractCellEditor implements TableCellEditor {
        private final JCheckBox checkBox;
        private HashMap<Integer,String> columnas = new HashMap<>();
        private int lastSelectedRow = -1;
        private int lastSelectedColumn = -1;
        public CheckBoxEditor(List<Permisos> permisos) {
            columnas.put(2, "crear");
            columnas.put(3, "editar");
            columnas.put(4, "ver");
            columnas.put(5, "listar");
            checkBox = new JCheckBox();
            checkBox.setHorizontalAlignment(JLabel.CENTER);
            checkBox.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {

                                               if (lastSelectedRow==-1){

                                               }else {
                                                   System.out.println("Fila " + permisos.get(lastSelectedRow).getNombre() + ", Columna " + lastSelectedColumn +
                                                           (checkBox.isSelected() ? " checkeada." : " descheckeada."));
                                                   checkUncheck(permisos.get(lastSelectedRow).getId(),columnas.get(lastSelectedColumn),checkBox.isSelected());
                                               }
                                           }
                                       }
            );
        }

        public void checkUncheck(int id, String col,  boolean check) {
            Conexion sql = new Conexion();
            try {
                Connection mysql = sql.conectamysql();
                PreparedStatement preparedStatement = mysql.prepareStatement("UPDATE permisos SET "+col+" = ? WHERE id = ?;");
                preparedStatement.setBoolean(1, check);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value instanceof Boolean) {
                checkBox.setSelected((Boolean) value);
            }
            lastSelectedColumn = column;
            lastSelectedRow = row;
            return checkBox;
        }

        @Override
        public Object getCellEditorValue() {
            return checkBox.isSelected();
        }
    }

}