/**
 * ListaMateriales.java
 *
 * Lista de Materiales
 *
 * @author Dania Lagos
 * @version 1.0
 * @since 2024-05-05
 */

package Materiales;

import Modelos.ModeloMaterial;
import Objetos.Conexion;
import Objetos.Material;
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

public class ListaMateriales extends JFrame {
    // Paneles
    private JPanel panelPrincipal;
    private JPanel panelA;
    private JPanel panelB;
    private JPanel panelTitulo;

    // Botones
    private JButton botonVer;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JButton botonEditar;
    private JButton botonCrear;

    // Tabla de materiales
    private JTable listaMateriales;

    // Campo de búsqueda
    private JTextField campoBusqueda;

    // Placeholder para el campo de búsqueda
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre de material o de empresa proveedora", campoBusqueda);

    // Etiquetas
    private JLabel lblPagina;
    private JLabel lbl0;
    private JLabel lblD;

    // Checkboxes
    private JCheckBox noCheckBox;
    private JCheckBox siCheckBox;

    // Lista de materiales
    private List<Material> materialList;

    // Índice de página
    private int pagina = 0;

    // Conexiones a la base de datos
    private Connection mysql;
    private Conexion sql;

    // Instancia de la clase
    private ListaMateriales actual = this;

    // Cadena de búsqueda
    private String busqueda = "";

    // Fuentes y Colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaMateriales() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaMateriales.setModel(cargarDatos());
        configurarTablaMateriales();
        mostrarTodos();

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
                listaMateriales.setModel(cargarDatos());
                configurarTablaMateriales();
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
                listaMateriales.setModel(cargarDatos());
                configurarTablaMateriales();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText().trim();
                pagina = 0;  // Asegura que siempre regrese a la primera página.
                listaMateriales.setModel(cargarDatos());
                configurarTablaMateriales();
                actualizarBotonesPaginacion();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        siCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!siCheckBox.isSelected() && !noCheckBox.isSelected()) {
                    siCheckBox.setSelected(true);
                }
                pagina = 0; // Restablece a la primera página.
                actualizarTabla();
            }
        });

        noCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!siCheckBox.isSelected() && !noCheckBox.isSelected()) {
                    noCheckBox.setSelected(true);
                }
                pagina = 0; // Restablece a la primera página.
                actualizarTabla();
            }
        });


        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearMaterial materiales = new CrearMaterial();
                materiales.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaMateriales.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerMaterial material = new VerMaterial(materialList.get(listaMateriales.getSelectedRow()).getId());
                material.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaMateriales.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                EditarMaterial material = new EditarMaterial(materialList.get(listaMateriales.getSelectedRow()).getId());
                material.setVisible(true);
                actual.dispose();
            }
        });

        JTableHeader header = listaMateriales.getTableHeader();
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
        siCheckBox.setBackground(primaryColor);
        noCheckBox.setBackground(primaryColor);

        placeholder.setForeground(darkColor);
        botonVer.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonAdelante.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
        lblPagina.setForeground(Color.WHITE);
        siCheckBox.setForeground(Color.WHITE);
        noCheckBox.setForeground(Color.WHITE);
        lblD.setForeground(Color.WHITE);

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
        botonVer.setFocusable(false);

    }

    // Método para configurar las columnas y renderizadores de la tabla de materiales
    private void configurarTablaMateriales() {
        TableColumnModel columnModel = listaMateriales.getColumnModel();

        // Establece el ancho preferido de las columnas
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(130);
        columnModel.getColumn(3).setPreferredWidth(50);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(60);
        columnModel.getColumn(6).setPreferredWidth(60);

        // Configura renderizadores para la alineación del contenido en las celdas
        columnModel.getColumn(0).setCellRenderer(new ListaMateriales.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaMateriales.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaMateriales.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaMateriales.CenterAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new ListaMateriales.LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new ListaMateriales.LeftAlignedRenderer());
        columnModel.getColumn(6).setCellRenderer(new ListaMateriales.LeftAlignedRenderer());
    }

    // Renderizador para alinear el texto a la izquierda
    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Renderizador para alinear el texto al centro
    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Método para cargar datos de materiales desde la base de datos
    private ModeloMaterial cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT m.*, p.empresaProveedora " +
                             "FROM Materiales m " +
                             "JOIN Proveedores p ON m.proveedor_id = p.id " +
                             "WHERE (m.nombre LIKE CONCAT('%', ?, '%') OR p.empresaProveedora LIKE CONCAT('%', ?, '%')) " +
                             "AND ((? = 'Si' AND m.disponible = 'Si') OR (? = 'No' AND m.disponible = 'No')) " +
                             "LIMIT ?, 20"
             )
        ) {
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, siCheckBox.isSelected() ? "Si" : "");
            preparedStatement.setString(4, noCheckBox.isSelected() ? "No" : "");
            preparedStatement.setInt(5, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            materialList = new ArrayList<>();

            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setNombre(resultSet.getString("nombre"));
                material.setDisponible(resultSet.getString("disponible"));
                material.setDescripcion(resultSet.getString("descripcion"));
                material.setProveedorId(resultSet.getInt("proveedor_id"));
                material.setCantidad(resultSet.getInt("cantidad"));
                material.setPrecio(resultSet.getDouble("precio"));
                materialList.add(material);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos", Color.decode("#C62828"));
            materialList = new ArrayList<>();
        }

        if (listaMateriales.getColumnCount() > 0) {
            TableColumn columnId = listaMateriales.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloMaterial(materialList, sql);
    }

    // Método para calcular el total de páginas de materiales disponibles
    private int getTotalPageCount() {
        int count = 0;
        boolean aplicarFiltroEstado = true;
        String estado = "";
        if (siCheckBox.isSelected() && noCheckBox.isSelected()) {
            aplicarFiltroEstado = false;
        } else if (siCheckBox.isSelected()) {
            estado = "Si";
        } else if (noCheckBox.isSelected()) {
            estado = "No";
        }

        String sqlQuery = "SELECT COUNT(*) AS total FROM Materiales m JOIN Proveedores p ON m.proveedor_id = p.id WHERE (m.nombre LIKE CONCAT('%', ?, '%') OR p.empresaProveedora LIKE CONCAT('%', ?, '%'))";
        if (aplicarFiltroEstado) {
            sqlQuery += " AND m.disponible = ?";
        }

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            if (aplicarFiltroEstado) {
                preparedStatement.setString(3, estado);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }

        int registrosPorPagina = 20;
        int totalPageCount = (count + registrosPorPagina - 1) / registrosPorPagina;
        if (totalPageCount == 0) {
            totalPageCount = 1;  // Asegura que siempre haya al menos una página.
        }

        return totalPageCount;
    }

    // Método para actualizar los botones de paginación
    private void actualizarBotonesPaginacion() {
        int totalPaginas = getTotalPageCount();
        botonAtras.setEnabled(pagina > 0);
        botonAdelante.setEnabled((pagina + 1) < totalPaginas);
    }

    // Método para actualizar los datos de la tabla
    private void actualizarTabla() {
        listaMateriales.setModel(cargarDatos());
        configurarTablaMateriales();
        actualizarBotonesPaginacion();
        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
    }

    // Método para mostrar todos los datos
    private void mostrarTodos() {
        siCheckBox.setSelected(true);
        noCheckBox.setSelected(true);
        actualizarTabla();
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
        ListaMateriales listaMateriales = new ListaMateriales();
        listaMateriales.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaMateriales.setVisible(true);
    }
}
