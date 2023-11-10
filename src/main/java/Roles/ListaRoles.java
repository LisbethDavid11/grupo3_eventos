package Roles;

import Arreglos.TextPrompt;
import Modelos.ModeloRol;
import Objetos.Conexion;
import Objetos.Rol;

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

public class ListaRoles extends JFrame {
    private JPanel panelPrincipal, panelTitulo;
    private JButton botonCrear, botonVer, botonEditar, botonAtras, botonAdelante;
    private JTable listaRoles;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre",campoBusqueda);
    private JLabel lblPagina, lbl0;
    private JPanel panelA;
    private JPanel panelB;
    private List<Rol> listaRol;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaRoles actual = this;
    private String busqueda = "";
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro
    public ListaRoles() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaRoles.setModel(cargarDatos());
        configurarTablaRoles();

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
                listaRoles.setModel(cargarDatos());
                configurarTablaRoles();
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
                listaRoles.setModel(cargarDatos());
                configurarTablaRoles();
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
                listaRoles.setModel(cargarDatos());
                configurarTablaRoles();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });


        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actual.dispose();
                CrearRol roles = new CrearRol();
                roles.setVisible(true);
            }
        });

        /*
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
        */

        // Establecer color de fondo para el encabezado
        JTableHeader header = listaRoles.getTableHeader();
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

    private void configurarTablaRoles() {
        TableColumnModel columnModel = listaRoles.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(400);

        columnModel.getColumn(0).setCellRenderer(new ListaRoles.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaRoles.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaRoles.LeftAlignedRenderer());
    }

    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class RightAlignedRenderer extends DefaultTableCellRenderer {
        public RightAlignedRenderer() {
            setHorizontalAlignment(RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    private ModeloRol cargarDatos() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        try {
            PreparedStatement preparedStatement = mysql.prepareStatement(
                    "SELECT * FROM roles WHERE nombre LIKE CONCAT('%',?,'%') LIMIT ?,20");
            preparedStatement.setString(1, busqueda);
            preparedStatement.setInt(2, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaRol = new ArrayList<>();
            while (resultSet.next()) {
                Rol rol = new Rol();
                rol.setId(resultSet.getInt("id"));
                rol.setNombre(resultSet.getString("nombre"));
                rol.setCliente(resultSet.getBoolean("cliente"));
                rol.setEmpleado(resultSet.getBoolean("empleado"));
                rol.setFloristeria(resultSet.getBoolean("floristeria"));
                rol.setArreglo(resultSet.getBoolean("arreglo"));
                rol.setUsuario(resultSet.getBoolean("usuario"));
                rol.setMaterial(resultSet.getBoolean("material"));
                rol.setProveedor(resultSet.getBoolean("proveedor"));
                rol.setCompra(resultSet.getBoolean("compra"));
                rol.setTarjeta(resultSet.getBoolean("tarjeta"));
                rol.setManualidad(resultSet.getBoolean("manualidad"));
                rol.setGlobo(resultSet.getBoolean("globo"));
                rol.setDesayuno(resultSet.getBoolean("desayuno"));
                rol.setVenta(resultSet.getBoolean("venta"));
                rol.setMobiliario(resultSet.getBoolean("mobiliario"));
                rol.setPedido(resultSet.getBoolean("pedido"));
                rol.setPromocion(resultSet.getBoolean("promocion"));
                rol.setEvento(resultSet.getBoolean("evento"));
                rol.setActividad(resultSet.getBoolean("actividad"));
                rol.setAlquiler(resultSet.getBoolean("alquiler"));
                rol.setRol(resultSet.getBoolean("rol"));
                // ... Continúa con el resto de los campos booleanos ...
                listaRol.add(rol);
            }
            mysql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            listaRol = new ArrayList<>();
        }
        return new ModeloRol(listaRol);
    }


    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM roles WHERE nombre LIKE CONCAT('%',?,'%')")) {
            preparedStatement.setString(1, busqueda);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total"); // Obtiene el total de elementos
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos","Error", JOptionPane.ERROR_MESSAGE);
        }

        int totalPageCount = (int) Math.ceil((double) count / 20); // Divide el total de elementos por 20 para obtener la cantidad de páginas completas

        return totalPageCount; // Retorna el total de páginas necesarias
    }

    public static void main(String[] args) {
        ListaRoles listaRoles = new ListaRoles();
        listaRoles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaRoles.setVisible(true);
    }
}
