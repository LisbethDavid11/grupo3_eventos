/**
 * EditarPermiso.java
 *
 * Editar Permiso
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-06
 */

package Permisos;

import Login.SesionUsuario;
import Objetos.Conexion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditarPermisos extends JFrame{
    // Identificadores
    private final int id;
    private int id_permiso;

    // Botones
    private JButton botonCancelar;
    private JButton CREARButton;
    private JButton GUARDARButton;

    // Paneles
    private JPanel panel;
    private JPanel panel1;
    private JPanel panel2;

    // Etiqueta
    private JLabel label0;

    // Casillas de verificación
    private JCheckBox checkCliente;
    private JCheckBox checkEmpleados;
    private JCheckBox checkArreglos;
    private JCheckBox checkFloristeria;
    private JCheckBox checkUsuarios;
    private JCheckBox checkMateriales;
    private JCheckBox checkCompras;
    private JCheckBox checkProveedores;
    private JCheckBox checkTargetas;
    private JCheckBox checkManualidades;
    private JCheckBox checkDesayunos;
    private JCheckBox checkGlobos;
    private JCheckBox checkVentas;
    private JCheckBox checkMobiliario;
    private JCheckBox checkPromociones;
    private JCheckBox checkPedidos;
    private JCheckBox checkEventos;
    private JCheckBox checkActividades;
    private JCheckBox checkRoles;
    private JCheckBox checkAlquileres;

    // Lista desplegable
    private JLabel jcbRoles;

    // Referencia a la clase actual
    private EditarPermisos actual = this;

    // Conexión a la base de datos
    private Connection mysql;
    private Conexion sql;

    // Referencia a la clase EditarPermisos
    public EditarPermisos crearCliente = this;

    // Ventana anterior
    private JFrame ventanaAnterior;

    // Cuente y olores
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    // Color de texto para los JTextField y JRadioButton
    Color textColor = Color.decode("#212121");
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 17);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

    // Colores para el botón "Cyan"
    Color primaryColorCyan = new Color(0, 188, 212); // Cyan primario
    Color lightColorCyan = new Color(77, 208, 225); // Cyan claro
    Color darkColorCyan = new Color(0, 151, 167); // Cyan oscuro

    // Colores para el botón "Aqua"
    Color primaryColorAqua = new Color(0, 150, 136); // Aqua primario
    Color lightColorAqua = new Color(77, 182, 172); // Aqua claro
    Color darkColorAqua = new Color(0, 121, 107); // Aqua oscuro


    // Colores para el botón "Rosado"
    Color primaryColorRosado = new Color(233, 30, 99); // Rosado primario
    Color lightColorRosado = new Color(240, 98, 146); // Rosado claro
    Color darkColorRosado = new Color(194, 24, 91); // Rosado oscuro

    // Crea un margen de 10 píxeles desde el borde inferior
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public EditarPermisos(int id_rol) {
        super("");
        setSize(850,505);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();
        this.id = id_rol;
        mostrarRol();

        panel.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));

        CREARButton.setBackground(Color.decode("#5382a1"));

        CREARButton.setForeground(Color.WHITE);
        CREARButton.setBorder(margin);

        GUARDARButton.setFocusPainted(false);
        botonCancelar.setFocusPainted(false);
        CREARButton.setFocusPainted(false);;
        

        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBackground(darkColorBlue);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setBorder(margin);

        GUARDARButton.setForeground(Color.WHITE);
        GUARDARButton.setBackground(darkColorAqua);
        GUARDARButton.setFocusPainted(false);
        GUARDARButton.setBorder(margin);

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actual.dispose();
                ListaPermisos listaPermisos = new ListaPermisos();
                listaPermisos.setVisible(true);
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        GUARDARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarPermiso();
                ListaPermisos permisos = new ListaPermisos();
                permisos.setVisible(true);
                dispose();
            }
        });

        CREARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean chek =  !checkCliente.isSelected();
                checkCompras.setSelected(chek);
                checkCliente.setSelected(chek);
                checkEmpleados.setSelected(chek);
                checkArreglos.setSelected(chek);
                checkFloristeria.setSelected(chek);
                checkUsuarios.setSelected(chek);
                checkMateriales.setSelected(chek);
                checkProveedores.setSelected(chek);
                checkTargetas.setSelected(chek);
                checkManualidades.setSelected(chek);
                checkDesayunos.setSelected(chek);
                checkGlobos.setSelected(chek);
                checkVentas.setSelected(chek);
                checkMobiliario.setSelected(chek);
                checkPromociones.setSelected(chek);
                checkPedidos.setSelected(chek);
                checkEventos.setSelected(chek);
                checkActividades.setSelected(chek);
                checkRoles.setSelected(chek);
                checkAlquileres.setSelected(chek);
            }
        });
    }

    // Método para cargar los datos del permiso
    private void mostrarRol() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT p.id, r.nombre, r.descripcion, p.cliente, p.empleado, p.floristeria, p.arreglo, p.usuario, " +
                             "p.material, p.proveedor, p.compra, p.tarjeta, p.manualidad, p.globo, p.desayuno, p.venta, " +
                             "p.mobiliario, p.pedido, p.promocion, p.evento, p.actividad, p.alquiler, p.rol " +
                             "FROM permisos p INNER JOIN roles r ON p.id_rol = r.id WHERE p.id_rol = ?")) {

            preparedStatement.setInt(1, this.id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String nombreRol = rs.getString("nombre");
                String descripcionRol = rs.getString("descripcion");
                this.id_permiso = rs.getInt("id");

                StringBuilder permisos = new StringBuilder();
                checkCliente.setSelected(rs.getBoolean("cliente"));
                checkEmpleados.setSelected(rs.getBoolean("empleado"));
                checkFloristeria.setSelected(rs.getBoolean("floristeria"));
                checkArreglos.setSelected(rs.getBoolean("arreglo"));
                checkUsuarios.setSelected(rs.getBoolean("usuario"));
                checkMateriales.setSelected(rs.getBoolean("material"));
                checkProveedores.setSelected(rs.getBoolean("proveedor"));
                checkCompras.setSelected(rs.getBoolean("compra"));
                checkTargetas.setSelected(rs.getBoolean("tarjeta"));
                checkManualidades.setSelected(rs.getBoolean("manualidad"));
                checkGlobos.setSelected(rs.getBoolean("globo"));
                checkDesayunos.setSelected(rs.getBoolean("desayuno"));
                checkVentas.setSelected(rs.getBoolean("venta"));
                checkMobiliario.setSelected(rs.getBoolean("mobiliario"));
                checkPedidos.setSelected(rs.getBoolean("pedido"));
                checkPromociones.setSelected(rs.getBoolean("promocion"));
                checkEventos.setSelected(rs.getBoolean("evento"));
                checkActividades.setSelected(rs.getBoolean("actividad"));
                checkAlquileres.setSelected(rs.getBoolean("alquiler"));
                checkRoles.setSelected(rs.getBoolean("rol"));


                // Elimina la coma y el espacio extra al final, si existen
                if (permisos.length() > 0) {
                    permisos.delete(permisos.length() - 2, permisos.length());
                }

                jcbRoles.setText(nombreRol);
            } else {
                JOptionPane.showMessageDialog(this, "El rol con este ID no tiene permisos asignados.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del rol.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para guardar los datos del permiso
    private void guardarPermiso() {
        Integer idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();

        if (idUsuarioActual == 0) {
            mostrarDialogoPersonalizadoError("Recuerde que para poder crear un evento, debe iniciar sesión", Color.decode("#C62828"));
            dispose();

        } else {

            try (Connection connection = sql.conectamysql();
                PreparedStatement preparedStatement = connection.prepareStatement("update permisos set cliente=?, empleado=?, floristeria=?," +
                                " arreglo=?, usuario=?, material=?, proveedor=?, compra=?, tarjeta=?, manualidad=?, globo=?," +
                                " desayuno=?, venta=?, mobiliario=?, pedido=?, promocion=?, evento=?, actividad=?, alquiler=?, rol=? " +
                        " where permisos.id = ?")) {

                preparedStatement.setInt(21,  this.id_permiso);
                preparedStatement.setBoolean(1, checkCliente.isSelected());
                preparedStatement.setBoolean(2, checkEmpleados.isSelected());
                preparedStatement.setBoolean(3, checkFloristeria.isSelected());
                preparedStatement.setBoolean(4, checkArreglos.isSelected());
                preparedStatement.setBoolean(5, checkUsuarios.isSelected());
                preparedStatement.setBoolean(6, checkMateriales.isSelected());
                preparedStatement.setBoolean(7, checkProveedores.isSelected());
                preparedStatement.setBoolean(8, checkCompras.isSelected());
                preparedStatement.setBoolean(9, checkTargetas.isSelected());
                preparedStatement.setBoolean(10, checkManualidades.isSelected());
                preparedStatement.setBoolean(11, checkGlobos.isSelected());
                preparedStatement.setBoolean(12, checkDesayunos.isSelected());
                preparedStatement.setBoolean(13, checkVentas.isSelected());
                preparedStatement.setBoolean(14, checkMobiliario.isSelected());
                preparedStatement.setBoolean(15, checkPedidos.isSelected());
                preparedStatement.setBoolean(16, checkPromociones.isSelected());
                preparedStatement.setBoolean(17, checkEventos.isSelected());
                preparedStatement.setBoolean(18, checkActividades.isSelected());
                preparedStatement.setBoolean(19, checkAlquileres.isSelected());
                preparedStatement.setBoolean(20, checkRoles.isSelected());
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null,"Permisos actualizados con exitos");
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarDialogoPersonalizadoError("Error al guardar el pedido.", Color.decode("#C62828"));
            }
        }
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
        EditarPermisos permisos = new EditarPermisos(1);
        permisos.setVisible(true);
    }
}