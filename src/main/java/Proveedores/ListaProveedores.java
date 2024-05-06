/**
 * ListaProveedores.java
 *
 * Lista de Proveedores
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-05
 */

package Proveedores;

import Empleados.TextPrompt;
import Modelos.ModeloProveedor;
import Objetos.Conexion;
import Objetos.Proveedor;
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

public class ListaProveedores extends JFrame {
    // Panel principal
    private JPanel panelPrincipal;

    // Botones
    private JButton botonVer;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JButton botonEditar;
    private JButton botonCrear;

    // Tabla
    private JTable listaProveedores;

    // Campo de búsqueda
    private JTextField campoBusqueda;

    // TextPrompt para el campo de búsqueda
    private TextPrompt placeholder;

    // Etiquetas
    private JLabel lblPagina;
    private JLabel lbl0;

    // Paneles
    private JPanel panelA;
    private JPanel panelTitulo;
    private JPanel panelB;

    // Lista de proveedores
    private List<Proveedor> listaProveedor;

    // Página actual
    private int pagina = 0;

    // Conexión a la base de datos
    private Connection mysql;
    private Conexion sql;

    // Referencia a la lista de proveedores actual
    private ListaProveedores actual = this;

    // Búsqueda actual
    private String busqueda = "";

    // Fuentes y colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaProveedores() {
        super("Lista Proveedores");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaProveedores.setModel(cargarDatos());
        configurarTablaProveedores();

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
                listaProveedores.setModel(cargarDatos());
                configurarTablaProveedores();
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
                listaProveedores.setModel(cargarDatos());
                configurarTablaProveedores();
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
                listaProveedores.setModel(cargarDatos());
                configurarTablaProveedores();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearProveedor proveedores = new CrearProveedor();
                proveedores.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaProveedores.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EditarProveedores proveedores = new EditarProveedores(listaProveedor.get(listaProveedores.getSelectedRow()).getId());
                proveedores.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaProveedores.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                VerProveedores proveedores = new VerProveedores(listaProveedor.get(listaProveedores.getSelectedRow()).getId());
                proveedores.setVisible(true);
                actual.dispose();
            }
        });

        JTableHeader header = listaProveedores.getTableHeader();
        header.setForeground(Color.WHITE);

        int campoBusquedaHeight = 35;
        campoBusqueda.setPreferredSize(new Dimension(campoBusqueda.getPreferredSize().width, campoBusquedaHeight));

        panelPrincipal.setBackground(primaryColor);
        panelTitulo.setBackground(primaryColor);
        panelA.setBackground(primaryColor);
        panelTitulo.setBackground(primaryColor);
        panelB.setBackground(primaryColor);
        header.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        botonEditar.setBackground(darkColor);
        botonAdelante.setBackground(darkColor);
        botonAtras.setBackground(darkColor);
        botonVer.setBackground(darkColor);
        campoBusqueda.setBackground(Color.WHITE);

        placeholder.setForeground(darkColor);
        botonVer.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonAdelante.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
        lblPagina.setForeground(Color.WHITE);

        campoBusqueda.setFont(font);
        botonAdelante.setFont(font);
        botonVer.setFont(font);
        botonEditar.setFont(font);
        botonAtras.setFont(font);
        botonCrear.setFont(font);
        placeholder.setFont(font);
        lbl0.setFont(fontTitulo);
        lblPagina.setFont(font);

        botonAdelante.setFocusable(false);
        botonAtras.setFocusable(false);
        botonCrear.setFocusable(false);
        botonVer.setFocusable(false);
    }

    // Método para configurar la tabla
    private void configurarTablaProveedores() {
        TableColumnModel columnModel = listaProveedores.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(150);
        columnModel.getColumn(5).setPreferredWidth(60);

        columnModel.getColumn(0).setCellRenderer(new ListaProveedores.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaProveedores.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaProveedores.CenterAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaProveedores.CenterAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new ListaProveedores.LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new ListaProveedores.CenterAlignedRenderer());
    }

    // Clase para alinear la columna a la izquierda
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

    // Clase para alinear la columna al centro
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

    // Método para cargar los datos
    private ModeloProveedor cargarDatos() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        try {
            PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM " + Proveedor.nombreTabla + " WHERE empresaProveedora LIKE CONCAT('%',?,'%') OR nombreVendedor LIKE CONCAT('%',?,'%') OR rtn LIKE CONCAT('%',?,'%') LIMIT ?,20");
            preparedStatement.setString(1, campoBusqueda.getText());
            preparedStatement.setString(2, campoBusqueda.getText());
            preparedStatement.setString(3, campoBusqueda.getText());
            preparedStatement.setInt(4, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaProveedor = new ArrayList<>();
            while (resultSet.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(resultSet.getInt("id"));
                proveedor.setEmpresaProveedora(resultSet.getString("empresaProveedora"));
                proveedor.setRtn(resultSet.getString("rtn"));
                proveedor.setTelefono(resultSet.getString("telefono"));
                proveedor.setCorreo(resultSet.getString("correo"));
                proveedor.setNombreVendedor(resultSet.getString("nombreVendedor"));
                proveedor.setTelefonoVendedor(resultSet.getString("telefonoVendedor"));
                listaProveedor.add(proveedor);
            }
            mysql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos","Error", JOptionPane.ERROR_MESSAGE);
            listaProveedor = new ArrayList<>();
        }

        return new ModeloProveedor(listaProveedor);
    }

    // Método para la paginación
    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM " + Proveedor.nombreTabla + " WHERE empresaProveedora LIKE CONCAT('%',?,'%') OR nombreVendedor LIKE CONCAT('%',?,'%') OR rtn LIKE CONCAT('%',?,'%')")) {
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);

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

    // Método Principal
    public static void main(String[] args) {
        ListaProveedores listaProveedores = new ListaProveedores();
        listaProveedores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaProveedores.setVisible(true);
    }
}
