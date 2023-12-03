package Permisos;

import Arreglos.TextPrompt;
import Modelos.ModeloPermisos;
import Modelos.ModeloRol;
import Objetos.Conexion;
import Objetos.Permisos;
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

public class ListaPermisos extends JFrame {
    private JPanel panelPrincipal, panelTitulo;
    private JButton botonCrear, botonVer, botonEditar, botonAtras, botonAdelante;
    private JTable tablaPermisos;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre",campoBusqueda);
    private JLabel lblPagina, lbl0;
    private JPanel panelA;
    private JPanel panelB;
    private List<Permisos> listaRol;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaPermisos actual = this;
    private String busqueda = "";
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    public int id;

    public ListaPermisos() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");
        Conexion sql = new Conexion(); // Asume que esto crea una conexión válida

        tablaPermisos.setModel(cargarDatos());
        configurarTablaPermisos();
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
                tablaPermisos.setModel(cargarDatos());
                configurarTablaPermisos();

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
                tablaPermisos.setModel(cargarDatos());
                configurarTablaPermisos();
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
                tablaPermisos.setModel(cargarDatos());
                configurarTablaPermisos();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });


        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    actual.dispose();
                    CrearPermisos permisos = new CrearPermisos(0);
                    permisos.setVisible(true);
                    permisos.ver_modl();


            }
        });

        /*botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tablaPermisos.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerRol rol = new VerRol(listaRol.get(tablaPermisos.getSelectedRow()).getId());
                rol.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tablaPermisos.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                EditarRol cliente = new EditarRol(listaRol.get(tablaPermisos.getSelectedRow()).getId());
                cliente.setVisible(true);
                actual.dispose();
            }
        });*/

        // Establecer color de fondo para el encabezado
        JTableHeader header = tablaPermisos.getTableHeader();
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

    private void configurarTablaPermisos() {
        TableColumnModel columnModel = tablaPermisos.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(50);

        columnModel.getColumn(0).setCellRenderer(new ListaPermisos.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaPermisos.LeftAlignedRenderer());
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

    private ModeloPermisos cargarDatos() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        try {
            PreparedStatement preparedStatement = mysql.prepareStatement("SELECT *, permisos.id as id_permisos, roles.id as id_roles " +
                    "FROM permisos " +
                    " inner join roles on roles.id = permisos.id_rol WHERE nombre LIKE CONCAT('%',?,'%') LIMIT ?,20");
            preparedStatement.setString(1, busqueda);
            preparedStatement.setInt(2, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaRol = new ArrayList<>();

            while (resultSet.next()) {
                Permisos rol = new Permisos();
                rol.setId(resultSet.getInt("id_permisos"));
                rol.setIdRol(resultSet.getInt("id_roles"));
                rol.setCliente(resultSet.getBoolean("cliente"));
                rol.setEmpleado(resultSet.getBoolean("empleado"));
                rol.setFloristeria(resultSet.getBoolean("floristeria"));
                rol.setArreglo(resultSet.getBoolean("arreglo"));
                rol.setUsuario(resultSet.getBoolean("usuario"));
                rol.setMaterial(resultSet.getBoolean("material"));
                rol.setProveedor(resultSet.getBoolean("proveedor"));
                rol.setCompra(resultSet.getBoolean("compra"));
                rol.setTarjeta(resultSet.getBoolean("tarjeta"));
                rol.setManualidad(resultSet.getBoolean("manualidad"));
                rol.setGlobo(resultSet.getBoolean("globo"));
                rol.setDesayuno(resultSet.getBoolean("desayuno"));
                rol.setVenta(resultSet.getBoolean("venta"));
                rol.setMobiliario(resultSet.getBoolean("mobiliario"));
                rol.setPedido(resultSet.getBoolean("pedido"));
                rol.setPromocion(resultSet.getBoolean("promocion"));
                rol.setEvento(resultSet.getBoolean("evento"));
                rol.setActividad(resultSet.getBoolean("actividad"));
                rol.setAlquiler(resultSet.getBoolean("alquiler"));
                rol.setRol(resultSet.getBoolean("rol"));


                // ... Continúa con el resto de los campos booleanos ...
                listaRol.add(rol);
            }
            mysql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            listaRol = new ArrayList<>();
        }
        return new ModeloPermisos(listaRol);
    }


    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM permisos " +
                     " inner join roles on roles.id = permisos.id_rol WHERE nombre LIKE CONCAT('%',?,'%')")) {
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
                        tablaPermisos.setModel(cargarDatos());
                        configurarTablaPermisos();
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
        ListaPermisos listaRoles = new ListaPermisos();
        listaRoles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaRoles.setVisible(true);
    }
}
