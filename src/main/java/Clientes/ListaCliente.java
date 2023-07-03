package Clientes;

import Modelos.ModeloClientes;
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

public class ListaCliente extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JTable listaClientes;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    Clientes.TextPrompt placeholder = new TextPrompt("Busca por identidad, nombres y apellidos", campoBusqueda);
    private JButton botonEditar;
    private JButton botoCrear;
    private List<Cliente> listaCliente;
    private int pagina=0;
    private Connection mysql;
    private Conexion sql;
    private ListaCliente actual = this;

    public ListaCliente(){
        super("LISTA DE CLIENTES");
        setSize(950,600);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");
        // Establecer el texto del campo de búsqueda como vacío
        campoBusqueda.setText("");

        // Cargar los datos en el modelo de la lista de clientes
        listaClientes.setModel(cargarDatos());

        // Agregar un KeyListener al campo de búsqueda
        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Cargar los datos en el modelo de la lista de clientes nuevamente
                listaClientes.setModel(cargarDatos());

                // Habilitar los botones "Atrás" y "Adelante"
                botonAtras.setEnabled(true);
                botonAdelante.setEnabled(true);
            }
        });

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaCliente.size() == 20){
                    pagina+=20;
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
                    pagina-=20;
                    botonAdelante.setEnabled(true);
                }else {
                    botonAtras.setEnabled(false);
                }
                listaClientes.setModel(cargarDatos());
            }
        });

        botoCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearCliente cliente = new CrearCliente();
                cliente.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaClientes.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila continuar");
                    return;
                }
                VerCliente cliente = new VerCliente(listaCliente.get(listaClientes.getSelectedRow()).getId());
                cliente.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaClientes.getSelectedRow() == -1){
                    JOptionPane.showMessageDialog(null,"Seleccione una fila continuar");
                    return;
                }
                EditarFormularioCliente cliente = new EditarFormularioCliente(listaCliente.get(listaClientes.getSelectedRow()).getId());
               cliente.setVisible(true);
               actual.dispose();
            }
        });
    }

    private ModeloClientes cargarDatos(){
        sql = new Conexion();
        mysql = sql.conectamysql();
        try {
            PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM "+Cliente.nombreTabla+" WHERE nombre LIKE CONCAT('%',?,'%') OR apellido LIKE CONCAT('%',?,'%') OR identidad LIKE CONCAT('%',?,'%') LIMIT ?,20");
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
                cliente.setDomicilio(resultSet.getString(6));
                cliente.setTipo_cliente(resultSet.getString(7));
                listaCliente.add(cliente);
            }
            mysql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"No hay conexión con la base de datos");
            listaCliente = new ArrayList<>();
        }
        return new ModeloClientes(Cliente.columnasCampos, listaCliente);
    }
}
