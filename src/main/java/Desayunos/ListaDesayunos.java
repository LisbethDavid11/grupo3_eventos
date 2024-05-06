/**
 * ListaDesayunos.java
 *
 * Lista de Desayunos
 *
 * @author Alejandra Aroca
 * @version 1.0
 * @since 2024-05-05
 */

package Desayunos;

import Manualidades.TextPrompt;
import Modelos.ModeloDesayuno;
import Objetos.Conexion;
import Objetos.Desayuno;
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

public class ListaDesayunos extends JFrame {
    // Paneles
    private JPanel panelPrincipal;
    private JPanel panelTitulo;
    private JPanel panelA;
    private JPanel panelB;

    // Botones
    private JButton botonEditar;
    private JButton botonCrear;
    private JButton botonVer;
    private JButton botonAdelante;
    private JButton botonAtras;

    // Campo de búsqueda
    private JTextField campoBusqueda;

    // Placeholder para el campo de búsqueda
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre del cliente, fecha ó tipo", campoBusqueda);

    // Etiqueta de página
    private JLabel lblPagina;

    // Etiqueta de título
    private JLabel lblTitulo;

    // Tabla de desayunos
    private JTable listaDesayunos;

    // Lista de desayunos
    private List<Desayuno> listaDesayuno;

    // Número de página
    private int pagina = 0;

    // Conexión a la base de datos MySQL
    private Connection mysql;

    // Conexión al objeto de la base de datos
    private Conexion sql;

    // Referencia a la ventana actual de la lista de desayunos
    private ListaDesayunos actual = this;

    // Búsqueda actual
    private String busqueda = "";

    // Fuentes
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);

    // Colores
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaDesayunos() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaDesayunos.setModel(cargarDatos());
        configurarTablaManualidades();

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
                listaDesayunos.setModel(cargarDatos());
                configurarTablaManualidades();
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
                listaDesayunos.setModel(cargarDatos());
                configurarTablaManualidades();
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
                listaDesayunos.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });


        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearDesayuno crearDesayuno = new CrearDesayuno();
                crearDesayuno.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaDesayunos.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerDesayunos verDesayunos = new VerDesayunos(listaDesayuno.get(listaDesayunos.getSelectedRow()).getId());
                verDesayunos.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaDesayunos.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                EditarDesayuno editarDesayuno = new EditarDesayuno(listaDesayuno.get(listaDesayunos.getSelectedRow()), listaDesayuno.get(listaDesayunos.getSelectedRow()).getId());
                editarDesayuno.setVisible(true);
                actual.dispose();
            }
        });

        JTableHeader header = listaDesayunos.getTableHeader();
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
        lblTitulo.setFont(fontTitulo);

        botonAdelante.setFocusable(false);
        botonAtras.setFocusable(false);
        botonCrear.setFocusable(false);
        botonVer.setFocusable(false);
        botonEditar.setFocusable(false);

    }

    // Método para configurar las columnas y sus renderizadores en la tabla de desayunos
    private void configurarTablaManualidades() {
        TableColumnModel columnModel = listaDesayunos.getColumnModel();

        // Establece el ancho preferido de las columnas
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(110);
        columnModel.getColumn(3).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(50);

        // Asigna renderizadores para alinear el contenido de las celdas
        columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new LeftAlignedRenderer());
    }

    // Clase para alinear texto a la izquierda en celdas de una tabla
    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT); // Alineación a la izquierda
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Clase para alinear texto al centro en celdas de una tabla
    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER); // Alineación al centro
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Método para cargar datos de desayunos desde la base de datos y devolver un modelo de datos
    private ModeloDesayuno cargarDatos() {
        sql = new Conexion(); // Establece conexión con la base de datos
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
                desayuno.setImagen(resultSet.getString("imagen"));
                desayuno.setNombre(resultSet.getString("nombre"));
                desayuno.setDescripcion(resultSet.getString("descripcion"));
                desayuno.setProveedor_id(resultSet.getInt("proveedor_id"));
                desayuno.setPrecio_desayuno(resultSet.getDouble("precio_desayuno"));
                desayuno.setMano_obra(resultSet.getDouble("mano_obra"));
                listaDesayuno.add(desayuno);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            listaDesayuno = new ArrayList<>();
        }

        if (listaDesayunos.getColumnCount() > 0) {
            TableColumn columnId = listaDesayunos.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloDesayuno(listaDesayuno, sql);
    }

    // Método para calcular el número total de páginas basado en la cantidad de registros
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
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }

        int registrosPorPagina = 20;
        int totalPageCount = (count + registrosPorPagina - 1) / registrosPorPagina;
        if (totalPageCount == 0) {
            totalPageCount = 1;  // Asegura que siempre haya al menos una página.
        }

        return totalPageCount;
    }

    // Método para mostrar un diálogo personalizado de éxito
    public void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado "OK"
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Establece el color de fondo del botón
        btnAceptar.setForeground(Color.WHITE); // Establece el color del texto del botón
        btnAceptar.setFocusPainted(false); // Elimina el borde del foco alrededor del botón

        // Crea un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(
                mensaje,                             // Texto del mensaje a mostrar
                JOptionPane.INFORMATION_MESSAGE,     // Tipo de mensaje (información)
                JOptionPane.DEFAULT_OPTION,          // Opción por defecto
                null,                                // Sin icono
                new Object[]{},                      // Sin opciones estándar
                null                                 // Sin valor inicial
        );

        // Configura el JOptionPane para usar el botón personalizado
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón para cerrar el diálogo cuando se presione
        btnAceptar.addActionListener(e -> dialog.dispose());

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    // Método para mostrar un diálogo personalizado de error
    public void mostrarDialogoPersonalizadoError(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado "OK"
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Establece el color de fondo del botón
        btnAceptar.setForeground(Color.WHITE); // Establece el color del texto del botón
        btnAceptar.setFocusPainted(false); // Elimina el borde del foco alrededor del botón

        // Crea un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(
                mensaje,                             // Texto del mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,         // Tipo de mensaje (advertencia)
                JOptionPane.DEFAULT_OPTION,          // Opción por defecto
                null,                                // Sin icono
                new Object[]{},                      // Sin opciones estándar
                null                                 // Sin valor inicial
        );

        // Configura el JOptionPane para usar el botón personalizado
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón para cerrar el diálogo cuando se presione
        btnAceptar.addActionListener(e -> dialog.dispose());

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    // Método para mostrar un diálogo personalizado de atención
    public void mostrarDialogoPersonalizadoAtencion(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado "OK"
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Establece el color de fondo del botón
        btnAceptar.setForeground(Color.WHITE); // Establece el color del texto del botón
        btnAceptar.setFocusPainted(false); // Elimina el borde del foco alrededor del botón

        // Crea un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(
                mensaje,                             // Texto del mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,         // Tipo de mensaje (advertencia)
                JOptionPane.DEFAULT_OPTION,          // Opción por defecto
                null,                                // Sin icono
                new Object[]{},                      // Sin opciones estándar
                null                                 // Sin valor inicial
        );

        // Configura el JOptionPane para usar el botón personalizado
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón para cerrar el diálogo cuando se presione
        btnAceptar.addActionListener(e -> dialog.dispose());

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    // Método Principal
    public static void main(String[] args) {
        ListaDesayunos listaDesayuno = new ListaDesayunos();
        listaDesayuno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaDesayuno.setVisible(true);
    }
}
