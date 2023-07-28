package Proveedores;
import Empleados.TextPrompt;
import Materiales.ListaMateriales;
import Modelos.ModeloProveedores;
import Objetos.Conexion;
import Objetos.Proveedor;
import Tarjetas.ListaTarjetas;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
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
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JTable listaProveedores;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    TextPrompt placeholder = new TextPrompt("Buscar por empresa, vendedor y RTN", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private JLabel lblPagina;
    private JLabel lbl0;
    private List<Proveedor> listaProveedor;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaProveedores actual = this;
    private String busqueda = "";

    public ListaProveedores() {
        super("Lista Proveedores");
        setSize(860, 520);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        lbl0.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        listaProveedores.setModel(cargarDatos());
        configurarTablaProveedores();

        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

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



        // Colores de la paleta
        Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
        Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
        Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

        // Color de fondo
        panelPrincipal.setBackground(primaryColor);

        // Color de texto de los botones
        botonVer.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonAdelante.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonVer.setBackground(darkColor);
        botonAtras.setBackground(darkColor);
        botonAdelante.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        botonEditar.setBackground(darkColor);

        // Color de texto del campo de búsqueda y del label de la página
        campoBusqueda.setForeground(Color.WHITE);
        lblPagina.setForeground(Color.WHITE);

        // Color de fondo del campo de búsqueda
        campoBusqueda.setBackground(darkColor);

        // Color del placeholder del campo de búsqueda
        placeholder.changeAlpha(0.75f);
        placeholder.setForeground(Color.LIGHT_GRAY);
        placeholder.setFont(new Font("Nunito", Font.ITALIC, 11));
    }

    private void configurarTablaProveedores() {
        TableColumnModel columnModel = listaProveedores.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(60);
        columnModel.getColumn(3).setPreferredWidth(100);
        columnModel.getColumn(4).setPreferredWidth(170);
        columnModel.getColumn(5).setPreferredWidth(150);
        columnModel.getColumn(6).setPreferredWidth(60);

        columnModel.getColumn(0).setCellRenderer(new ListaProveedores.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaProveedores.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaProveedores.CenterAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaProveedores.LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new ListaProveedores.LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new ListaProveedores.LeftAlignedRenderer());
        columnModel.getColumn(6).setCellRenderer(new ListaProveedores.CenterAlignedRenderer());
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

    private ModeloProveedores cargarDatos() {
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

        return new ModeloProveedores(listaProveedor);
    }

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

    public static void main(String[] args) {
        ListaProveedores listaProveedores = new ListaProveedores();
        listaProveedores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaProveedores.setVisible(true);
    }
}
