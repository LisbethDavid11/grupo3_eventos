/**
 * ListaMobiliario.java
 *
 * Lista de Mobiliario
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-05
 */

package Mobiliario;

import Arreglos.TextPrompt;
import Modelos.ModeloMobiliario;
import Objetos.Conexion;
import Objetos.Mobiliario;
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

public class ListaMobiliario extends JFrame {
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

    // Campo de búsqueda
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre de mobiliario o color", campoBusqueda);

    // Etiquetas
    private JLabel lblPagina;
    private JLabel lbl0;

    // Tabla
    private JTable listaMobiliario;

    // Otras variables y conexiones
    private List<Mobiliario> listadoMobiliario;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaMobiliario actual = this;
    private String busqueda = "";

    // Fuentes y colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaMobiliario() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaMobiliario.setModel(cargarDatos());
        configurarTablaArreglos();

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
                listaMobiliario.setModel(cargarDatos());
                configurarTablaArreglos();
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
                listaMobiliario.setModel(cargarDatos());
                configurarTablaArreglos();
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
                listaMobiliario.setModel(cargarDatos());
                configurarTablaArreglos();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearMobiliario mobiliario = new CrearMobiliario();
                mobiliario.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaMobiliario.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerMobiliario mobiliario = new VerMobiliario(listadoMobiliario.get(listaMobiliario.getSelectedRow()));
                mobiliario.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaMobiliario.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                EditarMobiliario mo  = new EditarMobiliario(listadoMobiliario.get(listaMobiliario.getSelectedRow()));
                mo.setVisible(true);
                actual.dispose();
            }
        });

        JTableHeader header = listaMobiliario.getTableHeader();
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
        botonEditar.setFocusable(false);
        botonCrear.setFocusable(false);
        botonVer.setFocusable(false);

    }

    // Configura las propiedades de la tabla de mobiliario, como el ancho de las columnas y los renderizadores de celdas
    private void configurarTablaArreglos() {
        TableColumnModel columnModel = listaMobiliario.getColumnModel();

        // Define el ancho preferido para las columnas de la tabla
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(220);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(60);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(80);

        // Asigna renderizadores personalizados para las celdas, mejorando la presentación visual
        columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new LeftAlignedRenderer());
    }

    // Renderizador para alinear el texto a la izquierda en las celdas
    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT); // Alineación izquierda para el contenido de la celda
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Renderizador para centrar el texto en las celdas
    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER); // Alineación central para el contenido de la celda
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Carga los datos de mobiliario desde la base de datos y configura el modelo de la tabla
    private ModeloMobiliario cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM mobiliario WHERE color LIKE CONCAT('%', ?, '%') OR nombreMobiliario LIKE CONCAT('%', ?, '%') LIMIT ?, 20;"
             )
        ) {
            preparedStatement.setString(1, campoBusqueda.getText().isEmpty() ? "" : campoBusqueda.getText());
            preparedStatement.setString(2, campoBusqueda.getText().isEmpty() ? "" : campoBusqueda.getText());
            preparedStatement.setInt(3, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listadoMobiliario = new ArrayList<>();

            while (resultSet.next()) {
                Mobiliario mobiliario = new Mobiliario();
                mobiliario.setId(resultSet.getInt("id"));
                mobiliario.setNombreMobiliario(resultSet.getString("nombreMobiliario"));
                mobiliario.setColor(resultSet.getString("color"));
                mobiliario.setDescripcion(resultSet.getString("descripcion"));
                mobiliario.setCantidad(resultSet.getInt("cantidad"));
                mobiliario.setPrecioUnitario(resultSet.getFloat("precioUnitario"));
                mobiliario.setImagen(resultSet.getString("image"));
                listadoMobiliario.add(mobiliario);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos", Color.decode("#C62828"));
        }

        if (listaMobiliario.getColumnCount() > 0) {
            TableColumn columnId = listaMobiliario.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloMobiliario(listadoMobiliario, sql);
    }

    // Calcula el total de páginas necesarias para mostrar todos los registros de mobiliario
    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT COUNT(*) as total FROM mobiliario WHERE color LIKE CONCAT('%', ?, '%') OR nombreMobiliario LIKE CONCAT('%', ?, '%');"
             )
        ) {
            preparedStatement.setString(1, campoBusqueda.getText().isEmpty() ? "" : campoBusqueda.getText());
            preparedStatement.setString(2, campoBusqueda.getText().isEmpty() ? "" : campoBusqueda.getText());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos", Color.decode("#C62828"));
        }

        int registrosPorPagina = 20;
        int totalPageCount = (int) Math.ceil((double) count / registrosPorPagina);
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
        ListaMobiliario listaMobiliario1 = new ListaMobiliario();
        listaMobiliario1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaMobiliario1.setVisible(true);
    }
}
