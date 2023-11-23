package Permisos;
import Modelos.ModeloPermisos;
import Objetos.Conexion;
import Objetos.Pedido;
import Objetos.Permisos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CrearPermisos extends JFrame{
    private JButton  botonCancelar;
    private JPanel panel, panel1, panel2;
    private JLabel label0;

    private JTable jt_permisos;
    private CrearPermisos actual = this;
    private Connection mysql;
    private Conexion sql;
    public CrearPermisos crearCliente = this;
    private JFrame ventanaAnterior;
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    // Color de texto para los JTextField y JRadioButton
    Color textColor = Color.decode("#212121");
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 17);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

    // Colores para el botón "Cyan"
    Color primaryColorCyan = new Color(0, 188, 212); // Cyan primario
    Color lightColorCyan = new Color(77, 208, 225); // Cyan claro
    Color darkColorCyan = new Color(0, 151, 167); // Cyan oscuro

    // Colores para el botón "Aqua"
    Color primaryColorAqua = new Color(0, 150, 136); // Aqua primario
    Color lightColorAqua = new Color(77, 182, 172); // Aqua claro
    Color darkColorAqua = new Color(0, 121, 107); // Aqua oscuro


    // Colores para el botón "Rosado"
    Color primaryColorRosado = new Color(233, 30, 99); // Rosado primario
    Color lightColorRosado = new Color(240, 98, 146); // Rosado claro
    Color darkColorRosado = new Color(194, 24, 91); // Rosado oscuro

    // Crea un margen de 10 píxeles desde el borde inferior
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public CrearPermisos(int id_rol) {
        super("");
        setSize(850,505);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();

        panel.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));

        JTableHeader header = jt_permisos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(darkColorRosado);

        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBackground(darkColorBlue);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setBorder(margin);

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actual.dispose();
                ListaPermisos listaPermisos = new ListaPermisos();
                listaPermisos.setVisible(true);
            }
        });

        List<Permisos> permisos = traer_ventanas_rol(id_rol);
        ModeloPermisos modeloPermisos = new ModeloPermisos(permisos);
        jt_permisos.setModel(modeloPermisos);
        // Agregar el editor de celda con el evento de los checkboxes
        TableColumnModel columnModel = jt_permisos.getColumnModel();

        for (int columnIndex = 2; columnIndex <= 5; columnIndex++) {
            columnModel.getColumn(columnIndex).setCellEditor(new ModeloPermisos.CheckBoxEditor(permisos));
        }

    }
    public  List<Permisos> traer_ventanas_rol(int id){
        List<Permisos> permisos = new ArrayList<>();

        sql = new Conexion();
        try {
            Connection mysql = sql.conectamysql();
            PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM roles WHERE roles.id = ?;");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            resultSet.next();
            List<String> column = new ArrayList<>();
            label0.setText("INGRESAR PERMISOS DEL ROL: "+resultSet.getString("nombre"));
            for (int i = 1; i <= columnCount; i++) {
                if (3<=i){
                    if (resultSet.getBoolean(metaData.getColumnName(i))){
                        column.add(metaData.getColumnName(i));
                    }else {
                        PreparedStatement preparedStatement1 = mysql.prepareStatement("DELETE FROM eventos.permisos WHERE id_rol = ? AND nombre_ventana = ?;");
                        preparedStatement1.setInt(1, id);
                        preparedStatement1.setString(2,metaData.getColumnName(i));
                        preparedStatement1.executeUpdate();
                    }
                }
            }

            for (String c: column ) {

                    System.out.println("Nombre de columna: " +  c);
                    PreparedStatement preparedStatement1 = mysql.prepareStatement("SELECT * FROM eventos.permisos WHERE id_rol = ? AND nombre_ventana = ?;");
                    preparedStatement1.setInt(1, id);
                    preparedStatement1.setString(2,c);
                    resultSet = preparedStatement1.executeQuery();

                    Permisos permiso = new Permisos();
                    if (!resultSet.next()) {
                        // Si no hay resultados, el registro no existe, entonces crearlo
                        PreparedStatement preparedStatement2 = mysql.prepareStatement("INSERT INTO eventos.permisos (id_rol, nombre_ventana, crear, editar, ver, listar) VALUES (?,?,1,1,1,1);", Statement.RETURN_GENERATED_KEYS);
                        preparedStatement2.setInt(1, id);
                        preparedStatement2.setString(2, c);
                        preparedStatement2.executeUpdate();
                        System.out.println("Se ha creado un nuevo registro.");
                        ResultSet generatedKeys = preparedStatement2.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            int idGenerado = generatedKeys.getInt(1);
                            permiso.setId(idGenerado);
                            permiso.setNombre(c);
                            permiso.setCrear(true);
                            permiso.setEditar(true);
                            permiso.setVer(true);
                            permiso.setListar(true);
                            permisos.add(permiso);
                        }

                        preparedStatement2.close();
                    } else {
                        permiso.setId(resultSet.getInt("id"));
                        permiso.setNombre(resultSet.getString("nombre_ventana"));
                        permiso.setCrear(resultSet.getBoolean("crear"));
                        permiso.setEditar(resultSet.getBoolean("editar"));
                        permiso.setVer(resultSet.getBoolean("ver"));
                        permiso.setListar(resultSet.getBoolean("listar"));
                        permisos.add(permiso);
                    }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return permisos;
    }
    public static void main(String[] args) {
        CrearPermisos permisos = new CrearPermisos(1);
        permisos.setVisible(true);
    }
}