/**
 * ListaClientes.java
 *
 * Lista de Clientes
 *
 * @author Dania Lagos
 * @version 1.0
 * @since 2024-05-05
 */

package Clientes;

import Arreglos.TextPrompt;
import Modelos.ModeloCliente;
import Objetos.Cliente;
import Objetos.Conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
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

public class ListaClientes extends JFrame {
    // Paneles
    private JPanel panelPrincipal;
    private JPanel panelTitulo;
    private JPanel panelA;
    private JPanel panelB;

    // Botones
    private JButton botonCrear;
    private JButton botonVer;
    private JButton botonEditar;
    private JButton botonAtras;
    private JButton botonAdelante;

    // Tabla
    private JTable listaClientes;

    // Campo de búsqueda
    private JTextField campoBusqueda;

    // TextPrompt para el campo de búsqueda
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre, descripción ó fecha", campoBusqueda);

    // Etiquetas
    private JLabel lblPagina;
    private JLabel lbl0;

    // Lista de clientes
    private List<Cliente> listaCliente;

    // Página actual
    private int pagina = 0;

    // Conexión a la base de datos
    private Connection mysql;
    private Conexion sql;

    // Referencia a la lista de clientes actual
    private ListaClientes actual = this;

    // Búsqueda actual
    private String busqueda = "";

    // Fuentes y colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaClientes() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaClientes.setModel(cargarDatos());
        configurarTablaClientes();

        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

        botonAtras.setEnabled(false);
        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pagina > 0) {
                    pagina--;
                    botonAdelante.setEnabled(true);
                    if (pagina == 0) {
                        botonAtras.setEnabled(false);
                    }
                }
                listaClientes.setModel(cargarDatos());
                configurarTablaClientes();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((pagina + 1) < getTotalPageCount()) {
                    pagina++;
                    botonAtras.setEnabled(true);
                    if ((pagina + 1) == getTotalPageCount()) {
                        botonAdelante.setEnabled(false);
                    }
                }
                listaClientes.setModel(cargarDatos());
                configurarTablaClientes();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0;
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaClientes.setModel(cargarDatos());
                configurarTablaClientes();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });


        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearCliente cliente = new CrearCliente(actual); // Pasa la referencia a ListaClientes
                cliente.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaClientes.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                EditarCliente cliente = new EditarCliente(listaCliente.get(listaClientes.getSelectedRow()).getId());
                cliente.setVisible(true);
                actual.dispose();
            }
        });

        // Establecer color de fondo para el encabezado
        JTableHeader header = listaClientes.getTableHeader();
        header.setForeground(Color.WHITE);

        int campoBusquedaHeight = 35;
        campoBusqueda.setPreferredSize(new Dimension(campoBusqueda.getPreferredSize().width, campoBusquedaHeight));

        panelPrincipal.setBackground(primaryColor);
        panelTitulo.setBackground(primaryColor);
        panelA.setBackground(primaryColor);
        panelB.setBackground(primaryColor);
        campoBusqueda.setBackground(Color.WHITE);
        botonAdelante.setBackground(darkColor);
        botonAtras.setBackground(darkColor);
        botonVer.setBackground(darkColor);
        botonEditar.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        header.setBackground(darkColor);

        botonAdelante.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonVer.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
        placeholder.setForeground(darkColor);
        lblPagina.setForeground(Color.WHITE);

        campoBusqueda.setFont(font);
        botonAdelante.setFont(font);
        botonAtras.setFont(font);
        botonVer.setFont(font);
        botonCrear.setFont(font);
        botonEditar.setFont(font);
        placeholder.setFont(font);
        lblPagina.setFont(font);
        lbl0.setFont(fontTitulo);

        botonAdelante.setFocusable(false);
        botonAtras.setFocusable(false);
        botonCrear.setFocusable(false);
        botonVer.setFocusable(false);
        botonEditar.setFocusable(false);

    }

    // Método para configurar las columnas y sus renderizadores en la tabla de clientes
    private void configurarTablaClientes() {
        TableColumnModel columnModel = listaClientes.getColumnModel();

        // Establece el ancho preferido de las columnas
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(110);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(110);
        columnModel.getColumn(4).setPreferredWidth(110);

        // Asigna renderizadores para alinear el contenido de las celdas
        columnModel.getColumn(0).setCellRenderer(new ListaClientes.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaClientes.CenterAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaClientes.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaClientes.CenterAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new ListaClientes.CenterAlignedRenderer());
    }

    // Renderer para alinear texto a la izquierda en celdas de una tabla
    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT); // Alineación a la izquierda
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Renderer para alinear texto al centro en celdas de una tabla
    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER); // Alineación al centro
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Método para cargar los datos de clientes desde la base de datos
    private ModeloCliente cargarDatos() {
        sql = new Conexion(); // Establece conexión con la base de datos
        Connection mysql = sql.conectamysql();
        try {
            PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM " + Cliente.nombreTabla + " WHERE nombre LIKE CONCAT('%',?,'%') OR apellido LIKE CONCAT('%',?,'%') OR identidad LIKE CONCAT('%',?,'%') LIMIT ?,20");
            preparedStatement.setString(1, campoBusqueda.getText());
            preparedStatement.setString(2, campoBusqueda.getText());
            preparedStatement.setString(3, campoBusqueda.getText());
            preparedStatement.setInt(4, pagina * 20);

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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            listaCliente = new ArrayList<>();
        }
        return new ModeloCliente(listaCliente);
    }

    // Método para calcular el número total de páginas necesarias para mostrar todos los clientes
    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM " + Cliente.nombreTabla + " WHERE nombre LIKE CONCAT('%',?,'%') OR apellido LIKE CONCAT('%',?,'%') OR identidad LIKE CONCAT('%',?,'%')")) {
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        int registrosPorPagina = 20;
        int totalPageCount = (count + registrosPorPagina - 1) / registrosPorPagina;
        if (totalPageCount == 0) {
            totalPageCount = 1;  // Asegura que siempre haya al menos una página
        }

        return totalPageCount;
    }

    // Método Principal
    public static void main(String[] args) {
        ListaClientes listaCliente = new ListaClientes();
        listaCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaCliente.setVisible(true);
    }
}
