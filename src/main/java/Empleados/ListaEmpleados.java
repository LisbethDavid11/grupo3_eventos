package Empleados;
import Modelos.ModeloEmpleado;
import Objetos.Conexion;
import Objetos.Empleado;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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

public class ListaEmpleados extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JTable listaEmpleados;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt("Buscar por identidad, nombres y apellidos", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private JLabel lblPagina;
    private JLabel lbl0;
    private ImageIcon imagen;
    private List<Empleado> listaEmpleado;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaEmpleados actual = this;
    private String busqueda = "";

    public ListaEmpleados() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaEmpleado = new ArrayList<>();

        listaEmpleados.setModel(cargarDatos());
        configurarTablaEmpleados();

        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

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
                listaEmpleados.setModel(cargarDatos());
                configurarTablaEmpleados();
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
                listaEmpleados.setModel(cargarDatos());
                configurarTablaEmpleados();
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
                listaEmpleados.setModel(cargarDatos());
                configurarTablaEmpleados();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        // Colores de la paleta
        Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
        Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
        Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearEmpleado empleado = new CrearEmpleado();
                empleado.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonVer.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonVer.setBackground(darkColor);
            }
        });

        botonAtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonAtras.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonAtras.setBackground(darkColor);
            }
        });

        botonAdelante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonAdelante.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonAdelante.setBackground(darkColor);
            }
        });

        botonCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonCrear.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonCrear.setBackground(darkColor);
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaEmpleados.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                VerEmpleado empleado = new VerEmpleado(listaEmpleado.get(listaEmpleados.getSelectedRow()).getId());
                empleado.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonEditar.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonEditar.setBackground(darkColor);
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaEmpleados.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                EditarEmpleado empleado = new EditarEmpleado(listaEmpleado.get(listaEmpleados.getSelectedRow()).getId());
                empleado.setVisible(true);
                actual.dispose();
            }
        });

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

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);
    }

    private void configurarTablaEmpleados() {
        TableColumnModel columnModel = listaEmpleados.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(110);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(110);

        columnModel.getColumn(0).setCellRenderer(new ListaEmpleados.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaEmpleados.CenterAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaEmpleados.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaEmpleados.CenterAlignedRenderer());
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

    private ModeloEmpleado cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM Empleados WHERE Nombres LIKE CONCAT('%', ?, '%') LIMIT ?, 20")) {
            preparedStatement.setString(1, campoBusqueda.getText());
            preparedStatement.setInt(2, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaEmpleado.clear();

            while (resultSet.next()) {
                Empleado empleado = new Empleado();
                empleado.setId(resultSet.getInt("id"));
                empleado.setIdentidad(resultSet.getString("Identidad"));
                empleado.setNombres(resultSet.getString("Nombres"));
                empleado.setApellidos(resultSet.getString("Apellidos"));
                empleado.setTelefono(resultSet.getString("Telefono"));

                listaEmpleado.add(empleado);
            }

            return new ModeloEmpleado(listaEmpleado);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return new ModeloEmpleado(new ArrayList<>());
        }
    }

    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM " + Empleado.nombreTabla + " WHERE nombres LIKE CONCAT('%',?,'%') OR apellidos LIKE CONCAT('%',?,'%') OR identidad LIKE CONCAT('%',?,'%')")) {
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total"); // Obtiene el total de elementos
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        int totalPageCount = (int) Math.ceil((double) count / 20); // Divide el total de elementos por 20 para obtener la cantidad de páginas completas

        return totalPageCount; // Retorna el total de páginas necesarias
    }

    public static void main(String[] args) {
        ListaEmpleados listaEmpleados = new ListaEmpleados();
        listaEmpleados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaEmpleados.setVisible(true);
    }
}
