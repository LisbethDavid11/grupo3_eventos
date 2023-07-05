package Clientes;

import Empleados.TextPrompt;
import Modelos.ModeloClientes;
import Objetos.Cliente;
import Objetos.Conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
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
    Empleados.TextPrompt placeholder = new TextPrompt("Busca por identidad, nombres y apellidos", campoBusqueda);
    private JButton botonEditar;
    private JButton botoCrear;
    private List<Cliente> listaCliente;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaCliente actual = this;

    public ListaCliente() {
        super("LISTA DE CLIENTES");
        setSize(950, 600);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaClientes.setModel(cargarDatos());
        centrarDatosTabla();

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                listaClientes.setModel(cargarDatos());
                botonAtras.setEnabled(true);
                botonAdelante.setEnabled(true);
                centrarDatosTabla();
            }
        });

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaCliente.size() == 20) {
                    pagina += 20;
                    botonAtras.setEnabled(true);
                } else {
                    botonAdelante.setEnabled(false);
                }
                listaClientes.setModel(cargarDatos());
                centrarDatosTabla();
            }
        });

        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pagina > 0) {
                    pagina -= 20;
                    botonAdelante.setEnabled(true);
                } else {
                    botonAtras.setEnabled(false);
                }
                listaClientes.setModel(cargarDatos());
                centrarDatosTabla();
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
                if (listaClientes.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.ERROR_MESSAGE);
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
                if (listaClientes.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EditarCliente cliente = new EditarCliente(listaCliente.get(listaClientes.getSelectedRow()).getId());
                cliente.setVisible(true);
                actual.dispose();
            }
        });
    }

    private void centrarDatosTabla() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumn idColumn = listaClientes.getColumnModel().getColumn(0); // Obtener la columna de ID
        int idColumnWidth = idColumn.getWidth();
        idColumn.setPreferredWidth(idColumnWidth / 16); // Reducir a un octavo el ancho actual de la columna de ID

        TableColumn identidadColumn = listaClientes.getColumnModel().getColumn(1); // Obtener la columna de identidad
        int identidadColumnWidth = identidadColumn.getWidth();
        identidadColumn.setPreferredWidth(identidadColumnWidth / 2); // Reducir a la mitad el ancho actual de la columna de identidad

        for (int i = 0; i < listaClientes.getColumnCount(); i++) {
            listaClientes.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private ModeloClientes cargarDatos() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        try {
            PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM " + Cliente.nombreTabla + " WHERE nombre LIKE CONCAT('%',?,'%') OR apellido LIKE CONCAT('%',?,'%') OR identidad LIKE CONCAT('%',?,'%') LIMIT ?,20");
            preparedStatement.setString(1, campoBusqueda.getText());
            preparedStatement.setString(2, campoBusqueda.getText());
            preparedStatement.setString(3, campoBusqueda.getText());
            preparedStatement.setInt(4, pagina);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaCliente = new ArrayList<>();
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellido(resultSet.getString("apellido"));
                cliente.setIdentidad(resultSet.getString("identidad"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setDomicilio(resultSet.getString("domicilio"));
                cliente.setTipo_cliente(resultSet.getString("tipo_cliente"));
                listaCliente.add(cliente);
            }
            mysql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            listaCliente = new ArrayList<>();
        }

        return new ModeloClientes(listaCliente);
    }

    public static void main(String[] args) {
        ListaCliente listaCliente = new ListaCliente();
        listaCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaCliente.setVisible(true);
    }
}
