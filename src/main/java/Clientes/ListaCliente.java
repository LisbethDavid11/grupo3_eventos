package Clientes;

import Modelos.ModeloTablaClientes;
import Objetos.Cliente;
import Objetos.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class ListaCliente extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonCrear;
    private JTable listaClientes;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    private JButton botonVer;
    private JButton botonEditar;
    private List<Cliente> listaCliente;
    private int pagina=0;
    private Connection mysql;
    private Conexion sql;
    private ListaCliente actual = this;



    public ListaCliente(){
        super("Lista de Clientes");
        setSize(500,500);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaClientes.setModel(cargarDatos());

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                listaClientes.setModel(cargarDatos());
                botonAtras.setEnabled(true);
                botonAdelante.setEnabled(true);
            }
        });

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaCliente.size() == 10){
                    pagina+=10;
                    botonAtras.setEnabled(true);
                }else {
                    botonAdelante.setEnabled(false);
                }
                listaClientes.setModel(cargarDatos());
            }
        });
        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pagina > 0){
                    pagina-=10;
                    botonAdelante.setEnabled(true);
                }else {
                    botonAtras.setEnabled(false);
                }
                listaClientes.setModel(cargarDatos());
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearCliente cliente = new CrearCliente();
                cliente.setVisible(true);
                actual.dispose();
            }
        });
    }


    private ModeloTablaClientes cargarDatos(){
        sql = new Conexion();
        mysql = sql.sql();
        try {
            PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM "+Cliente.nombreTabla+" WHERE nombre LIKE CONCAT('%',?,'%') OR apellido LIKE CONCAT('%',?,'%') OR identidad LIKE CONCAT('%',?,'%') LIMIT ?,10");
            preparedStatement.setString(1,campoBusqueda.getText());
            preparedStatement.setString(2,campoBusqueda.getText());
            preparedStatement.setString(3,campoBusqueda.getText());
            preparedStatement.setInt(4,pagina);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaCliente = new ArrayList<>();
            while (resultSet.next()){
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt(1));
                cliente.setNombre(resultSet.getString(2));
                cliente.setApellido(resultSet.getString(3));
                cliente.setIdentidad(resultSet.getString(4));
                cliente.setTelefono(resultSet.getString(5));

                listaCliente.add(cliente);
            }
            mysql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"No hay conexion");
            listaCliente = new ArrayList<>();
        }
        return new ModeloTablaClientes(listaCliente);
    }
}
