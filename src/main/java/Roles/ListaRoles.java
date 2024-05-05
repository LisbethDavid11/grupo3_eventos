/**
 * ListaRoles.java
 *
 * Lista de Roles
 *
 * @author Lisbeth David
 * @version 1.0
 * @since 2024-05-05
 */

package Roles;

import Arreglos.TextPrompt;
import Modelos.ModeloRol;
import Objetos.Conexion;
import Objetos.Rol;
import javax.swing.*;
import javax.swing.table.*;
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
    // Paneles
    private JPanel panelPrincipal;
    private JPanel panelTitulo;

    // Botones
    private JButton botonCrear;
    private JButton botonVer;
    private JButton botonAtras;
    private JButton botonAdelante;

    // Tabla
    private JTable listaRoles;

    // Campo de búsqueda
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre", campoBusqueda);

    // Etiquetas
    private JLabel lblPagina;
    private JLabel lbl0;

    // Paneles adicionales
    private JPanel panelA;
    private JPanel panelB;

    // Lista de roles
    private List<Rol> listaRol;

    // Página actual
    private int pagina = 0;

    // Conexión a la base de datos
    private Connection mysql;
    private Conexion sql;

    // Referencia al formulario actual
    private ListaRoles actual = this;

    // Búsqueda
    private String busqueda = "";

    // Fuentes
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);

    // Colores
    Color primaryColor = Color.decode("#37474f");
    Color lightColor = Color.decode("#cfd8dc");
    Color darkColor = Color.decode("#263238");
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    // Identificador
    public int id;

    public ListaRoles(int id) {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");
        Conexion sql = new Conexion(); // Asume que esto crea una conexión válida
        this.id = id;

        listaRoles.setModel(cargarDatos());
        configurarTablaRoles();
        listaRoles.getColumnModel().getColumn(3).setCellRenderer(new ListaRoles.ButtonRenderer());
        listaRoles.getColumnModel().getColumn(3).setCellEditor(new ListaRoles.ButtonEditor());
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
                listaRoles.getColumnModel().getColumn(3).setCellRenderer(new ListaRoles.ButtonRenderer());
                listaRoles.getColumnModel().getColumn(3).setCellEditor(new ListaRoles.ButtonEditor());
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
                listaRoles.getColumnModel().getColumn(3).setCellRenderer(new ListaRoles.ButtonRenderer());
                listaRoles.getColumnModel().getColumn(3).setCellEditor(new ListaRoles.ButtonEditor());
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
                listaRoles.getColumnModel().getColumn(3).setCellRenderer(new ListaRoles.ButtonRenderer());
                listaRoles.getColumnModel().getColumn(3).setCellEditor(new ListaRoles.ButtonEditor());
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

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaRoles.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerRol rol = new VerRol(listaRol.get(listaRoles.getSelectedRow()).getId());
                rol.setVisible(true);
                actual.dispose();
            }
        });

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
        botonCrear.setBackground(darkColor);
        header.setBackground(darkColor);

        botonAdelante.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonVer.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
        placeholder.setForeground(darkColor);
        lblPagina.setForeground(Color.WHITE);

        campoBusqueda.setFont(font);
        botonAdelante.setFont(font);
        botonAtras.setFont(font);
        botonVer.setFont(font);
        botonCrear.setFont(font);
        placeholder.setFont(font);
        lblPagina.setFont(font);
        lbl0.setFont(fontTitulo);

        botonAdelante.setFocusable(false);
        botonAtras.setFocusable(false);
        botonCrear.setFocusable(false);
        botonVer.setFocusable(false);

    }

    // Método para configurar la tabla
    private void configurarTablaRoles() {
        TableColumnModel columnModel = listaRoles.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(400);
        columnModel.getColumn(3).setPreferredWidth(50);

        columnModel.getColumn(0).setCellRenderer(new ListaRoles.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaRoles.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaRoles.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaRoles.CenterAlignedRenderer());
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

    // Método para cargar los datos del rol
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
                rol.setDescripcion(resultSet.getString("descripcion"));
                listaRol.add(rol);
            }
            mysql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            listaRol = new ArrayList<>();
        }
        return new ModeloRol(listaRol);
    }

    // Método para obtener el total de la paginación
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

            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }

        int totalPageCount = (int) Math.ceil((double) count / 20); // Divide el total de elementos por 20 para obtener la cantidad de páginas completas

        return totalPageCount; // Retorna el total de páginas necesarias
    }

    // Clase para renderizar el botón en la celda
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setForeground(Color.WHITE);
            setBackground(darkColor);
            setFocusPainted(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("X");
            return this;
        }
    }

    // Clase del botón
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;
        private int row, col;
        private JTable table;

        public ButtonEditor() {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(this);
            button.setForeground(Color.WHITE);
            button.setBackground(darkColor);
            button.setFocusPainted(false);
            //button.setBorder(margin);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("X");
            this.table = table;
            this.row = row;
            this.col = column;
            return button;
        }

        public Object getCellEditorValue() {
            return "X";
        }

        public void actionPerformed(ActionEvent e) {

            JButton btnSave = new JButton("Sí");
            JButton btnCancel = new JButton("No");

            // Personaliza los botones aquí
            btnSave.setBackground(darkColorBlue);
            btnCancel.setBackground(darkColorRed);

            // Personaliza los fondos de los botones aquí
            btnSave.setForeground(Color.WHITE);
            btnCancel.setForeground(Color.WHITE);

            // Elimina el foco
            btnSave.setFocusPainted(false);
            btnCancel.setFocusPainted(false);

            // Crea un JOptionPane
            JOptionPane optionPane = new JOptionPane(
                    "¿Desea eliminar el rol de usuario?",
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    new Object[]{}, // no options
                    null
            );

            // Crea un JDialog
            JDialog dialog = optionPane.createDialog("Eliminar rol");

            // Añade ActionListener a los botones
            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Verificar si el usuario es administrador
                    if (esUsuarioAdministrador(id)) {
                        if (table != null) {
                            int modelRow = table.convertRowIndexToModel(row);
                            TableModel model = table.getModel();

                            if (model instanceof ModeloRol) {
                                ModeloRol rolModel = (ModeloRol) model;
                                Rol rol = rolModel.getRol(modelRow);
                                int rolId = rol.getId();

                                // Restricción para roles específicos
                                if (rolId == 1) {
                                    dialog.dispose();
                                    mostrarDialogoPersonalizadoError("No se puede eliminar el rol de administrador.", Color.decode("#C62828"));
                                    fireEditingCanceled();
                                    return; // Salir del método si se intenta eliminar un rol restringido
                                }

                                if (rolId == 2) {
                                    dialog.dispose();
                                    mostrarDialogoPersonalizadoError("No se puede eliminar el rol de cajero principal.", Color.decode("#C62828"));
                                    fireEditingCanceled();
                                    return; // Salir del método si se intenta eliminar un rol restringido
                                }

                                try (Connection connection = sql.conectamysql()) {
                                    // Verificar si hay usuarios asociados a este rol
                                    boolean tieneUsuarios = false;
                                    try (PreparedStatement checkUsuarios = connection.prepareStatement(
                                            "SELECT COUNT(*) FROM usuarios WHERE rol_id = ?")) {
                                        checkUsuarios.setInt(1, rolId);
                                        ResultSet rs = checkUsuarios.executeQuery();
                                        if (rs.next() && rs.getInt(1) > 0) {
                                            tieneUsuarios = true;
                                        }
                                    }

                                    if (!tieneUsuarios) {
                                        // Eliminar el rol si no hay usuarios asociados
                                        try (PreparedStatement deleteRol = connection.prepareStatement(
                                                "DELETE FROM roles WHERE id = ?")) {
                                            deleteRol.setInt(1, rolId);
                                            deleteRol.executeUpdate();

                                            // Elimina el rol de la lista y actualiza la tabla
                                            rolModel.removeRow(modelRow);
                                            table.repaint();
                                            table.revalidate();
                                            dialog.dispose();
                                            mostrarDialogoPersonalizadoExito("El rol de usuario " + rol.getNombre() +" ha sido eliminado con éxito.", Color.decode("#263238"));
                                            // Otros métodos de actualización si es necesario
                                        }
                                    } else {
                                        // Manejar el caso donde hay usuarios asociados
                                        dialog.dispose();
                                        mostrarDialogoPersonalizadoError("No se puede eliminar el rol porque existen usuarios asociados.", Color.decode("#C62828"));
                                        fireEditingCanceled();
                                    }
                                } catch (SQLException ex) {
                                    ex.printStackTrace();
                                    // Manejo de excepciones en caso de error en la eliminación en la base de datos
                                }
                            }
                        }

                        dialog.dispose();
                        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
                        listaRoles.setModel(cargarDatos());
                        configurarTablaRoles();
                        listaRoles.getColumnModel().getColumn(3).setCellRenderer(new ListaRoles.ButtonRenderer());
                        listaRoles.getColumnModel().getColumn(3).setCellEditor(new ListaRoles.ButtonEditor());
                        fireEditingStopped();
                    } else {
                        dialog.dispose();
                        mostrarDialogoPersonalizadoError("No tienes permiso para eliminar este rol de usuario.", Color.decode("#C62828"));
                        fireEditingCanceled();
                    }
                }
            });

            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    fireEditingCanceled();
                }
            });

            optionPane.setOptions(new Object[]{btnSave, btnCancel});
            dialog.setVisible(true);
        }
    }

    // Método booleano para determinar si el rol es administrador
    public boolean esUsuarioAdministrador(int idUser) {
        String query = "SELECT roles.nombre FROM usuarios INNER JOIN roles ON usuarios.rol_id = roles.id WHERE usuarios.id = ?";

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idUser);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("nombre");
                return "Administrador".equals(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
        ListaRoles listaRoles = new ListaRoles(1);
        listaRoles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaRoles.setVisible(true);
    }
}
