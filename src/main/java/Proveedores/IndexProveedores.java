package Proveedores;


import Modelos.ModeloProveedores;
import Objetos.Conexion;
import Objetos.Proveedores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IndexProveedores extends JFrame{

    private Conexion sql;
    private Connection mysql;
    private JTable table1;
    private JButton nuevoButton;
    private JTextField jtBuscar;
    TextPrompt placeholder = new TextPrompt("Busca por empresa, rtn y dirección", jtBuscar);
    private JPanel panel1;
    private JButton Atras;
    private JButton Adelante;
    private int paginacion = 0;

    //Datos de la tabla
    //Crear un arreglo lista
    List<Proveedores> lista = new ArrayList<>();
    private final IndexProveedores actual = this;
    public IndexProveedores() {
        super("Datos de proveedores");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        jtBuscar.setText("");

        placeholder.changeStyle(Font.ITALIC);
        placeholder.setForeground(Color.blue);


        table1.setModel(cargarDatos());

        jtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                table1.setModel(cargarDatos());
            }
        });

        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearFormularioProveedores form = new CrearFormularioProveedores();
                form.setVisible(true);
                actual.dispose();
            }
        });
        Adelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(lista.size() == 20){
                    paginacion +=20;
                    table1.setModel(cargarDatos());
                    Atras.setEnabled(true);
                }else {
                    Adelante.setEnabled(false);
                }
            }
        });
        Atras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(paginacion > 0){
                    paginacion -=20;
                    table1.setModel(cargarDatos());
                    Adelante.setEnabled(true);
                }else {
                    Atras.setEnabled(false);
                }
            }
        });
    }

    private void guardar(){


    }



    //En este metódo vamos a instanciar mysql
    public ModeloProveedores cargarDatos() {

        //Con estos datos ya podemos prepara realizar consultas
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("Select * from "+ Proveedores.nombreTabla+" where rtn like concat('%',?,'%')or direccion like concat('%',?,'%')or id like concat('%',?,'%')or empresaProveedora like concat('%',?,'%') limit ?,20 ");
            statement.setString(1,jtBuscar.getText());
            statement.setString(2,jtBuscar.getText());
            statement.setString(3,jtBuscar.getText());
            statement.setString(4,jtBuscar.getText());
            statement.setInt(5,paginacion);


            ResultSet resultSet = statement.executeQuery();
            lista = new ArrayList<>();

            while (resultSet.next()) {
                Proveedores proveedores = new Proveedores();

                proveedores.setId(resultSet.getInt(1));
                proveedores.setNombre(resultSet.getString(2));
                proveedores.setRtn(resultSet.getString(3));
                proveedores.setTelefono(resultSet.getString(4));
                proveedores.setCorreo(resultSet.getString(5));
                proveedores.setDireccion(resultSet.getString(6));

                //Todos los datos se guardaran en esta lista
                lista.add(proveedores);

            }

            return new ModeloProveedores(
                    Proveedores.columnas,
                    lista
            );

        } catch (SQLException erro) {
            //Mensaje de error para mostrar
            System.out.println(erro.getMessage());
            return null;
        }

    }

        private void createUIComponents () {
            // TODO: place custom component creation code here
        }
    }
