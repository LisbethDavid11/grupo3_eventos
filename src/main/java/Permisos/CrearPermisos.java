/**
 * CrearPermiso.java
 *
 * Crear Permiso
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-06
 */

package Permisos;

import Login.SesionUsuario;
import Objetos.Conexion;
import Objetos.Rol;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CrearPermisos extends JFrame{
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
    private JComboBox<Rol> jcbRoles;

    // Referencia a la clase actual
    private CrearPermisos actual = this;

    // Conexión a la base de datos
    private Connection mysql;
    private Conexion sql;

    // Referencia a la clase CrearPermisos
    public CrearPermisos crearCliente = this;

    // Ventana anterior
    private JFrame ventanaAnterior;

    // Fuentes y colores
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

    public CrearPermisos(int id_rol) {
        super("");
        setSize(850,505);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();

        cargar();

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

    // Método para ver los roles
    public void ver_modl(){
        if (jcbRoles.getModel().getSize() <= 0) {
            JOptionPane.showMessageDialog(null,"No hay mas roles para asignarle permisos");
            ListaPermisos permisos = new ListaPermisos();
            permisos.setVisible(true);
            dispose();
        }
    }

    // Método para cargar los datos de los roles
    public void cargar(){

        sql = new Conexion();
        try {
            Connection mysql = sql.conectamysql();
            PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM roles " +
                    "left join permisos on permisos.id_rol = roles.id " +
                    "where permisos.id is null and nombre != 'Administrador'");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Rol rol = new Rol();
                rol.setNombre(resultSet.getString("nombre"));
                rol.setId(resultSet.getInt("id"));
                jcbRoles.addItem(rol);
            }




        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Método para guardar los permisos
    private void guardarPermiso() {
        Integer idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();

        if (idUsuarioActual == 0) {
            mostrarDialogoPersonalizadoError("Recuerde que para poder crear un evento, debe iniciar sesión", Color.decode("#C62828"));
            dispose();

        } else {

            try (Connection connection = sql.conectamysql();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO permisos (id_rol, cliente, empleado, floristeria," +
                                " arreglo, usuario, material, proveedor, compra, tarjeta, manualidad, globo," +
                                " desayuno, venta, mobiliario, pedido, promocion, evento, actividad, alquiler, rol)" +
                                " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                preparedStatement.setInt(1, ((Rol) jcbRoles.getSelectedItem()).getId());
                preparedStatement.setBoolean(2, checkCliente.isSelected());
                preparedStatement.setBoolean(3, checkEmpleados.isSelected());
                preparedStatement.setBoolean(4, checkFloristeria.isSelected());
                preparedStatement.setBoolean(5, checkArreglos.isSelected());
                preparedStatement.setBoolean(6, checkUsuarios.isSelected());
                preparedStatement.setBoolean(7, checkMateriales.isSelected());
                preparedStatement.setBoolean(8, checkProveedores.isSelected());
                preparedStatement.setBoolean(9, checkCompras.isSelected());
                preparedStatement.setBoolean(10, checkTargetas.isSelected());
                preparedStatement.setBoolean(11, checkManualidades.isSelected());
                preparedStatement.setBoolean(12, checkGlobos.isSelected());
                preparedStatement.setBoolean(13, checkDesayunos.isSelected());
                preparedStatement.setBoolean(14, checkVentas.isSelected());
                preparedStatement.setBoolean(15, checkMobiliario.isSelected());
                preparedStatement.setBoolean(16, checkPedidos.isSelected());
                preparedStatement.setBoolean(17, checkPromociones.isSelected());
                preparedStatement.setBoolean(18, checkEventos.isSelected());
                preparedStatement.setBoolean(19, checkActividades.isSelected());
                preparedStatement.setBoolean(20, checkAlquileres.isSelected());
                preparedStatement.setBoolean(21, checkRoles.isSelected());
                preparedStatement.executeUpdate();

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
        CrearPermisos permisos = new CrearPermisos(1);
        permisos.setVisible(true);
    }
}