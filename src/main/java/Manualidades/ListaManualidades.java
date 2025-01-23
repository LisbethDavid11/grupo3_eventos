/**
 * ListaManualidades.java
 *
 * Lista de Manualidades
 *
 * @author Elsa Ramos
 * @version 1.0
 * @since 2024-05-05
 */

package Manualidades;

import Modelos.ModeloManualidad;
import Objetos.Conexion;
import Objetos.Manualidad;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
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

public class ListaManualidades extends JFrame {
    // Paneles
    private JPanel panelPrincipal;
    private JPanel panelTitulo;
    private JPanel panelA;
    private JPanel panelB;

    // Botones
    private JButton botonVer;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JButton botonEditar;
    private JButton botonCrear;

    // Tabla
    private JTable listaManualidades;

    // Campo de texto
    private JTextField campoBusqueda;

    // Etiquetas
    private JLabel lblPagina;
    private JLabel lbl0;

    // Imagen
    private ImageIcon imagen;

    // Prompt de texto
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre u ocasión de la manualidad", campoBusqueda);

    // Lista de manualidades
    private List<Manualidad> listaManualidad;

    // Página actual
    private int pagina = 0;

    // Conexión a la base de datos
    private Connection mysql;
    private Conexion sql;

    // Instancia de la clase
    private ListaManualidades actual = this;

    // Búsqueda
    private String busqueda = "";

    // Fuente y colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaManualidades() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");


        listaManualidades.setModel(cargarDatos());
        configurarTablaManualidades();

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
                listaManualidades.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((pagina + 1) < getTotalPageCount()) {
                    pagina++;
                    actualizarInterfaz();
                }
            }
        });


        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0;
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaManualidades.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

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
                VerManualidades manualidad = new VerManualidades(listaManualidad.get(listaManualidades.getSelectedRow()).getId());
                manualidad.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaManualidades.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                EditarManualidad editarManualidad = new EditarManualidad(listaManualidad.get(listaManualidades.getSelectedRow()), listaManualidad.get(listaManualidades.getSelectedRow()).getId());
                editarManualidad.setVisible(true);
                actual.dispose();
            }
        });

        JTableHeader header = listaManualidades.getTableHeader();
        header.setForeground(Color.WHITE);

        int campoBusquedaHeight = 35;
        campoBusqueda.setPreferredSize(new Dimension(campoBusqueda.getPreferredSize().width, campoBusquedaHeight));

        panelPrincipal.setBackground(primaryColor);
        panelTitulo.setBackground(primaryColor);
        panelA.setBackground(primaryColor);
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

    // Método para configurar los parámetros y el comportamiento visual de la tabla de manualidades
    private void configurarTablaManualidades() {
        TableColumnModel columnModel = listaManualidades.getColumnModel();

        // Configura el ancho preferido para cada columna
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(60);
        columnModel.getColumn(4).setPreferredWidth(60);

        // Asigna renderizadores específicos para cada columna para controlar la alineación del texto
        columnModel.getColumn(0).setCellRenderer(new ListaManualidades.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaManualidades.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaManualidades.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaManualidades.LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new ListaManualidades.LeftAlignedRenderer());
    }

    // Clase para alinear el texto a la izquierda en las celdas de la tabla
    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Clase para alinear el texto al centro en las celdas de la tabla
    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Método para cargar los datos de manualidades desde la base de datos y los devuelve en un modelo de datos
    private ModeloManualidad cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT m.* " +
                             "FROM manualidades m " +
                             "WHERE m.nombre LIKE CONCAT('%', ?, '%') " +
                             "OR m.tipo LIKE CONCAT('%', ?, '%') " +
                             "LIMIT ?, 20"
             )
        ){
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setInt(3, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaManualidad = new ArrayList<>();

            while (resultSet.next()) {
                Manualidad manualidad = new Manualidad();
                manualidad.setId(resultSet.getInt("id"));
                manualidad.setNombre(resultSet.getString("nombre"));
                manualidad.setDescripcion(resultSet.getString("descripcion"));
                manualidad.setTipo(resultSet.getString("tipo"));
                manualidad.setPrecio_manualidad(resultSet.getDouble("precio_manualidad"));
                manualidad.setMano_obra(resultSet.getDouble("mano_obra"));
                listaManualidad.add(manualidad);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            listaManualidad = new ArrayList<>();
        }

        if (listaManualidades.getColumnCount() > 0) {
            TableColumn columnId = listaManualidades.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloManualidad(listaManualidad, sql);
    }

    // Método para calcular el número total de páginas basado en el número total de manualidades
    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM manualidades WHERE nombre LIKE CONCAT('%', ?, '%') OR tipo LIKE CONCAT('%', ?, '%')")) {
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }

        if (count == 0) {
            return 1;  // Asegura que siempre haya al menos una página.
        } else {
            return (count + 19) / 20;  // Calcula el número total de páginas necesario y redondea hacia arriba.
        }
    }

    // Método para actualizar la interfaz gráfica para reflejar los datos más recientes y la navegación de páginas
    private void actualizarInterfaz() {
        listaManualidades.setModel(cargarDatos());
        configurarTablaManualidades();
        int totalPaginas = getTotalPageCount();
        lblPagina.setText("Página " + (pagina + 1) + " de " + totalPaginas);
        botonAtras.setEnabled(pagina > 0);
        botonAdelante.setEnabled((pagina + 1) < totalPaginas);
    }

    // Método Principal
    public static void main(String[] args) {
        ListaManualidades listaManualidad = new ListaManualidades();
        listaManualidad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaManualidad.setVisible(true);
    }
}