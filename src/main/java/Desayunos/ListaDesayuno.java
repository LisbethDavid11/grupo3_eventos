package Desayunos;

import Manualidades.TextPrompt;
import Modelos.ModeloDesayuno;
import Objetos.Conexion;
import Objetos.Desayuno;
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

public class ListaDesayuno extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt("Buscar por nombre del desayuno, precio o proveedor", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private JLabel lbltxt;
    private JLabel lbl0;
    private ImageIcon imagen;
    private JTable listaDesayunos;
    private List<Desayuno> listaDesayuno;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaDesayuno actual = this;
    private String busqueda = "";

    public ListaDesayuno() {
        super("");
        setSize(850, 510);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaDesayunos.setModel(cargarDatos());
        configurarTablaManualidades();

        lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

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
                listaDesayunos.setModel(cargarDatos());
                configurarTablaManualidades();
                lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
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
                listaDesayunos.setModel(cargarDatos());
                configurarTablaManualidades();
                lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0;
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaDesayunos.setModel(cargarDatos());
                configurarTablaManualidades();
                lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        /*

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearManualidad manualidad = new CrearManualidad();
                manualidad.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaManualidades.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // VerManualidad manualidad = new VerManualidad(listaManualidad.get(listaManualidades.getSelectedRow()).getId());
                // manualidad.setVisible(true);
                // actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaManualidades.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                // EditarManualidad manualidad = new EditarManualidad(listaManualidad.get(listaManualidades.getSelectedRow()).getId());
                // manualidad.setVisible(true);
                // actual.dispose();
            }
        });
         */

        // Colores de la paleta
        Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
        Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
        Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

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

        botonEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonEditar.setBackground(lightColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonEditar.setBackground(darkColor);
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
        lbltxt.setForeground(Color.WHITE);

        // Color de fondo del campo de búsqueda
        campoBusqueda.setBackground(darkColor);

        // Color del placeholder del campo de búsqueda
        placeholder.changeAlpha(0.75f);
        placeholder.setForeground(Color.LIGHT_GRAY);
        placeholder.setFont(new Font("Nunito", Font.ITALIC, 11));
    }

    private void configurarTablaManualidades() {
        TableColumnModel columnModel = listaDesayunos.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(110);
        columnModel.getColumn(3).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(50);

        columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new LeftAlignedRenderer());
    }

    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class RightAlignedRenderer extends DefaultTableCellRenderer {
        public RightAlignedRenderer() {
            setHorizontalAlignment(RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    private ModeloDesayuno cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT d.* " +
                             "FROM desayunos d " +
                             "JOIN proveedores p ON d.proveedor_id = p.id " +
                             "WHERE d.nombre LIKE CONCAT('%', ?, '%') " +
                             "OR p.empresaProveedora LIKE CONCAT('%', ?, '%') " +
                             "OR d.precio_desayuno LIKE CONCAT('%', ?, '%') " +
                             "LIMIT ?, 20"
             )
        ){
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);
            preparedStatement.setInt(4, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaDesayuno = new ArrayList<>();

            while (resultSet.next()) {
                Desayuno desayuno = new Desayuno();
                desayuno.setId(resultSet.getInt("id"));
                desayuno.setNombre(resultSet.getString("nombre"));
                desayuno.setDescripcion(resultSet.getString("descripcion"));
                desayuno.setProveedor_id(resultSet.getInt("proveedor_id"));
                desayuno.setPrecio_desayuno(resultSet.getDouble("precio_desayuno"));
                desayuno.setMano_obra(resultSet.getDouble("mano_obra"));
                listaDesayuno.add(desayuno);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            listaDesayuno = new ArrayList<>();
        }

        if (listaDesayunos.getColumnCount() > 0) {
            TableColumn columnId = listaDesayunos.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloDesayuno(listaDesayuno, sql);
    }

    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM desayunos WHERE nombre LIKE CONCAT('%', ?, '%')")) {
            preparedStatement.setString(1, busqueda);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }

        int totalPageCount = count / 20;

        if (count % 20 > 0) {
            totalPageCount++;
        }

        return totalPageCount;
    }

    public static void main(String[] args) {
        ListaDesayuno listaDesayuno = new ListaDesayuno();
        listaDesayuno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaDesayuno.setVisible(true);
    }
}
