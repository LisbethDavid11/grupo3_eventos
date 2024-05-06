/**
 * ListaPermisos.java
 *
 * Lista de Permisos
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-05
 */

package Permisos;

import Arreglos.TextPrompt;
import Modelos.ModeloPermisos;
import Objetos.Conexion;
import Objetos.Permisos;
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
    // Paneles
    private JPanel panelPrincipal;
    private JPanel panelTitulo;
    private JPanel panelA;
    private JPanel panelB;

    // Botones
    private JButton botonCrear;
    private JButton botonVer;
    private JButton botonEditar;
    private JButton botonAtras;
    private JButton botonAdelante;

    // Tabla
    private JTable tablaPermisos;

    // Campo de búsqueda
    private JTextField campoBusqueda;

    // TextPrompt para el campo de búsqueda
    private TextPrompt placeholder;

    // Etiquetas
    private JLabel lblPagina;
    private JLabel lbl0;

    // Lista de permisos
    private List<Permisos> listaRol;

    // Página actual
    private int pagina = 0;

    // Conexión a la base de datos
    private Connection mysql;
    private Conexion sql;

    // Referencia a la lista de permisos actual
    private ListaPermisos actual = this;

    // Búsqueda actual
    private String busqueda = "";

    // Fuentes y colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    // ID
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

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tablaPermisos.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }

                // Obtener el permiso seleccionado
                Permisos permisoSeleccionado = listaRol.get(tablaPermisos.getSelectedRow());

                // Verificar si todos los permisos son false
                if (todosFalse(permisoSeleccionado)) {
                    mostrarDialogoPersonalizadoError("No hay permisos asignados.", Color.decode("#C62828"));

                    return; // No se muestra la ventana de permisos
                }

                // Mostrar la ventana de permisos
                VerPermiso rol = new VerPermiso(permisoSeleccionado.getId());
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
                EditarPermisos permisos = new EditarPermisos(listaRol.get(tablaPermisos.getSelectedRow()).getId());
                permisos.setVisible(true);
                actual.dispose();
            }
        });

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

    //Método para configurar la tabla
    private void configurarTablaPermisos() {
        TableColumnModel columnModel = tablaPermisos.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(50);

        columnModel.getColumn(0).setCellRenderer(new ListaPermisos.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaPermisos.LeftAlignedRenderer());
    }

    // Clase para alinear a la izquierda la columna
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

    // Clase para alinear al centro la columna
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

    // Método para verificar si todos los permisos son false
    private boolean todosFalse(Permisos permiso) {
        return !permiso.isCliente() && !permiso.isEmpleado() && !permiso.isFloristeria() &&
                !permiso.isArreglo() && !permiso.isUsuario() && !permiso.isMaterial() &&
                !permiso.isProveedor() && !permiso.isCompra() && !permiso.isTarjeta() &&
                !permiso.isManualidad() && !permiso.isGlobo() && !permiso.isDesayuno() &&
                !permiso.isVenta() && !permiso.isMobiliario() && !permiso.isPedido() &&
                !permiso.isPromocion() && !permiso.isEvento() && !permiso.isActividad() &&
                !permiso.isAlquiler() && !permiso.isRol();
    }

    // Método para cargar los datos de
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

    // Método para la paginación
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
        ListaPermisos listaRoles = new ListaPermisos();
        listaRoles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaRoles.setVisible(true);
    }
}
