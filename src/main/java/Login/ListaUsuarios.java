package Login;

import Arreglos.TextPrompt;
import Modelos.ModeloUsuario;
import Objetos.Conexion;
import Objetos.Usuario;

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

public class ListaUsuarios extends JFrame {
    private JPanel panelPrincipal, panelTitulo;
    private JButton botonCrear, botonVer, botonEditar, botonAtras, botonAdelante;
    private JTable listaUsuarios;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre ó correo electrónico", campoBusqueda);
    private JLabel lblPagina, lbl0;
    private JPanel panelA;
    private JPanel panelB;
    public List<Usuario> listaUsuario;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaUsuarios actual = this;
    private String busqueda = "";
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    public int id;

    public ListaUsuarios(int id) {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");
        Conexion sql = new Conexion(); // Asume que esto crea una conexión válida
        this.id = id;

        listaUsuarios.setModel(cargarDatos());
        configurarTablaClientes();
        listaUsuarios.getColumnModel().getColumn(4).setCellRenderer(new ListaUsuarios.ButtonRenderer());
        listaUsuarios.getColumnModel().getColumn(4).setCellEditor(new ListaUsuarios.ButtonEditor());
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
                listaUsuarios.setModel(cargarDatos());
                configurarTablaClientes();
                listaUsuarios.getColumnModel().getColumn(4).setCellRenderer(new ListaUsuarios.ButtonRenderer());
                listaUsuarios.getColumnModel().getColumn(4).setCellEditor(new ListaUsuarios.ButtonEditor());
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
                listaUsuarios.setModel(cargarDatos());
                configurarTablaClientes();
                listaUsuarios.getColumnModel().getColumn(4).setCellRenderer(new ListaUsuarios.ButtonRenderer());
                listaUsuarios.getColumnModel().getColumn(4).setCellEditor(new ListaUsuarios.ButtonEditor());
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
                listaUsuarios.setModel(cargarDatos());
                configurarTablaClientes();
                listaUsuarios.getColumnModel().getColumn(4).setCellRenderer(new ListaUsuarios.ButtonRenderer());
                listaUsuarios.getColumnModel().getColumn(4).setCellEditor(new ListaUsuarios.ButtonEditor());
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                if (usuarioEsAdministradorSesion( idUsuarioActual)) {
                    SignUp crearUsuario = new SignUp(); // Pasa la referencia a listaUsuarios
                    crearUsuario.setVisible(true);
                    dispose();
                } else {
                    mostrarDialogoPersonalizadoError("No cuentas con los permisos necesarios para crear un usuario.", Color.decode("#C62828"));
                }
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                if (usuarioEsAdministradorSesion(idUsuarioActual)) {
                    if (listaUsuarios.getSelectedRow() == -1) {
                        mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                        return;
                    }
                    EditarUsuario cliente = new EditarUsuario(listaUsuario.get(listaUsuarios.getSelectedRow()).getId(), idUsuarioActual);
                    cliente.setVisible(true);
                    actual.dispose();
                } else {
                    mostrarDialogoPersonalizadoError("No cuentas con los permisos necesarios para editar un usuario.", Color.decode("#C62828"));
                }
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                if (listaUsuarios.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerUsuario usuario = new VerUsuario(listaUsuario.get(listaUsuarios.getSelectedRow()).getId(), idUsuarioActual);
                usuario.setVisible(true);
                actual.dispose();
            }
        });

        // Establecer color de fondo para el encabezado
        JTableHeader header = listaUsuarios.getTableHeader();
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

    private void configurarTablaClientes() {
        TableColumnModel columnModel = listaUsuarios.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(210);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(110);
        columnModel.getColumn(4).setPreferredWidth(50);

        columnModel.getColumn(0).setCellRenderer(new ListaUsuarios.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaUsuarios.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaUsuarios.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaUsuarios.LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new ListaUsuarios.CenterAlignedRenderer());
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

    private ModeloUsuario cargarDatos() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        try {
            PreparedStatement preparedStatement = mysql.prepareStatement(
                    "SELECT * FROM usuarios WHERE nombre LIKE CONCAT('%',?,'%') OR correo LIKE CONCAT('%',?,'%') " +
                            "LIMIT ?,20"
            );
            preparedStatement.setString(1, campoBusqueda.getText());
            preparedStatement.setString(2, campoBusqueda.getText());
            preparedStatement.setInt(3, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaUsuario = new ArrayList<>();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(resultSet.getInt("id"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setCorreo(resultSet.getString("correo"));
                usuario.setRolId(resultSet.getInt("rol_id"));
                listaUsuario.add(usuario);
            }
            mysql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            listaUsuario = new ArrayList<>();
        }
        return new ModeloUsuario(listaUsuario, sql);
    }

    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT COUNT(*) AS total FROM usuarios WHERE nombre LIKE CONCAT('%',?,'%') OR correo LIKE CONCAT('%',?,'%')"
             )) {
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }

        int registrosPorPagina = 20;
        int totalPageCount = (int) Math.ceil((double) count / registrosPorPagina);
        if (totalPageCount == 0) {
            totalPageCount = 1;  // Asegura que siempre haya al menos una página, incluso si no hay resultados.
        }
        return totalPageCount;
    }

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
                    "¿Desea eliminar el usuario?",
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    new Object[]{}, // no options
                    null
            );

            // Crea un JDialog
            JDialog dialog = optionPane.createDialog("Eliminar usuario");

            // Añade ActionListener a los botones
            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (table != null) {
                        int selectedRow = table.getSelectedRow();
                        if (selectedRow >= 0) {
                            // Convertir el índice de la fila de la vista al modelo
                            int modelRow = table.convertRowIndexToModel(selectedRow);
                            ModeloUsuario usuarioModel = (ModeloUsuario) table.getModel();

                            Usuario usuario = usuarioModel.getUsuarios().get(modelRow);
                            int usuarioId = usuario.getId();
                            int usuarioSesion = id;

                            System.out.println();

                            if (usuarioEsAdministradorSesion(usuarioSesion) && usuarioId != 1 && !esUsuarioAdministrador(usuarioId)) {
                                // Aquí va tu lógica existente para eliminar el usuario// Inicia la conexión
                                try (Connection connection = sql.conectamysql()) {
                                    // Asegúrate de que las transacciones sean atómicas
                                    connection.setAutoCommit(false);

                                    // Actualiza las tablas pedidos, ventas y eventos
                                    try (PreparedStatement updatePedidos = connection.prepareStatement("UPDATE pedidos SET usuario_id = 1 WHERE usuario_id = ?")) {
                                        updatePedidos.setInt(1, usuarioId);
                                        updatePedidos.executeUpdate();
                                    }

                                    try (PreparedStatement updateVentas = connection.prepareStatement("UPDATE ventas SET usuario_id = 1 WHERE usuario_id = ?")) {
                                        updateVentas.setInt(1, usuarioId);
                                        updateVentas.executeUpdate();
                                    }

                                    try (PreparedStatement updateEventos = connection.prepareStatement("UPDATE eventos SET usuario_id = 1 WHERE usuario_id = ?")) {
                                        updateEventos.setInt(1, usuarioId);
                                        updateEventos.executeUpdate();
                                    }

                                    // Elimina el usuario
                                    try (PreparedStatement deleteUser = connection.prepareStatement("DELETE FROM usuarios WHERE id = ?")) {
                                        deleteUser.setInt(1, usuarioId);
                                        deleteUser.executeUpdate();
                                    }

                                    // Confirma las operaciones
                                    connection.commit();

                                    // Elimina el usuario del modelo y actualiza la tabla

                                    usuarioModel.removeRow(modelRow);

                                    dialog.dispose();
                                    lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
                                    listaUsuarios.setModel(cargarDatos());
                                    configurarTablaClientes();
                                    listaUsuarios.getColumnModel().getColumn(4).setCellRenderer(new ListaUsuarios.ButtonRenderer());
                                    listaUsuarios.getColumnModel().getColumn(4).setCellEditor(new ListaUsuarios.ButtonEditor());
                                    fireEditingStopped();
                                    table.repaint();
                                    table.revalidate();

                                    mostrarDialogoPersonalizadoExito("El usuario "+ usuario.getNombre() +" se ha eliminado con éxito.", Color.decode("#263238"));
                                    } catch (SQLException ex) {
                                    ex.printStackTrace();

                                    dialog.dispose();
                                    mostrarDialogoPersonalizadoError("Error al eliminar el usuario: " + ex.getMessage(), Color.decode("#C62828"));
                                    // En caso de error, revierte las operaciones
                                    try (Connection connection = sql.conectamysql()) {
                                        connection.rollback();
                                    } catch (SQLException exRollback) {
                                        exRollback.printStackTrace();
                                    }
                                }
                            } else {
                                dialog.dispose();
                                if (usuarioId == 1 || esUsuarioAdministrador(usuarioId)) {
                                    mostrarDialogoPersonalizadoError("No se puede eliminar un usuario administrador.", Color.decode("#C62828"));
                                } else {
                                    mostrarDialogoPersonalizadoError("No cuentas con los permisos necesarios para eliminar un usuario.", Color.decode("#C62828"));
                                }
                                fireEditingCanceled();
                            }
                        }
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

    public boolean usuarioEsAdministradorSesion(int userId) {
        String query = "SELECT roles.nombre FROM usuarios INNER JOIN roles ON usuarios.rol_id = roles.id WHERE usuarios.id = ?";

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, this.id);
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

    public void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.INFORMATION_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    public void mostrarDialogoPersonalizadoError(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.ERROR_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    public void mostrarDialogoPersonalizadoAtencion(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        ListaUsuarios listaUsuarios = new ListaUsuarios(1);
        listaUsuarios.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaUsuarios.setVisible(true);
    }
}
