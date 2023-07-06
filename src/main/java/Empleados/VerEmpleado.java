package Empleados;

import Objetos.Cliente;
import Objetos.Conexion;
import Objetos.Empleado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerEmpleado extends JFrame {
    public JLabel campoApellidos;
    public JLabel campoGenero;
    public JLabel campoEdad;
    public JLabel campoCorreo;
    public JLabel campoTelefono;
    public JLabel campoNombreContacto;
    public JLabel campoContacto;
    public JLabel campoTipo;
    public JLabel lblID;
    public JButton cancelarButton;
    public JPanel panel1;
    public JLabel lblnomConcat;
    public JLabel lblapeConcat;
    private JTextArea etiquetaDireccion;
    private JTextField etiquetaIdentidad;
    private JTextField etiquetaNombre;
    private JTextField etiquetaApellido;
    private JTextField etiquetaGenero;
    private JTextField etiquetaEdad;
    private JTextField etiquetaCorreo;
    private JTextField etiquetaTelefono;
    private JTextField etiquetaNombreContacto;
    private JTextField etiquetaTelefonoContacto;
    private JTextField etiquetaTipo;
    public VerEmpleado mostrarEmpleado = this;
    private final VerEmpleado actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;
    private int empleadoId; // Variable de instancia para almacenar el ID del empleado

    public VerEmpleado(int id) {
        super("");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();

        etiquetaDireccion.setLineWrap(true);
        etiquetaDireccion.setWrapStyleWord(true);

        // Deshabilitar la edición de los JTextField y el JTextArea
        etiquetaIdentidad.setEditable(false);
        etiquetaIdentidad.setFocusable(false);
        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaApellido.setEditable(false);
        etiquetaApellido.setFocusable(false);
        etiquetaEdad.setEditable(false);
        etiquetaEdad.setFocusable(false);
        etiquetaGenero.setEditable(false);
        etiquetaGenero.setFocusable(false);
        etiquetaCorreo.setEditable(false);
        etiquetaCorreo.setFocusable(false);
        etiquetaTelefono.setEditable(false);
        etiquetaTelefono.setFocusable(false);
        etiquetaDireccion.setEditable(false);
        etiquetaDireccion.setFocusable(false);
        etiquetaNombreContacto.setEditable(false);
        etiquetaNombreContacto.setFocusable(false);
        etiquetaTelefonoContacto.setEditable(false);
        etiquetaTelefonoContacto.setFocusable(false);
        etiquetaTipo.setEditable(false);
        etiquetaTipo.setFocusable(false);

        // Colores de la paleta
        Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
        Color lightColor = Color.decode("#cfd8dc"); // Gris claro
        Color darkColor = Color.decode("#263238"); // Gris oscuro

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));


        // Color de texto para los JTextField
        Color textColor = Color.decode("#263238");
        etiquetaNombre.setForeground(textColor);
        etiquetaNombre.setForeground(textColor);
        etiquetaApellido.setForeground(textColor);
        etiquetaIdentidad.setForeground(textColor);
        etiquetaGenero.setForeground(textColor);
        etiquetaEdad.setForeground(textColor);
        etiquetaCorreo.setForeground(textColor);
        etiquetaTelefono.setForeground(textColor);
        etiquetaNombreContacto.setForeground(textColor);
        etiquetaTelefonoContacto.setForeground(textColor);
        etiquetaTipo.setForeground(textColor);

        // Color de fondo para los JTextField
        Color textFieldColor = Color.decode("#FFFFFF");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaApellido.setBackground(textFieldColor);
        etiquetaIdentidad.setBackground(textFieldColor);
        etiquetaGenero.setBackground(textFieldColor);
        etiquetaEdad.setBackground(textFieldColor);
        etiquetaCorreo.setBackground(textFieldColor);
        etiquetaTelefono.setBackground(textFieldColor);
        etiquetaNombreContacto.setBackground(textFieldColor);
        etiquetaTelefonoContacto.setBackground(textFieldColor);
        etiquetaTipo.setBackground(textFieldColor);

        // Color de texto para el JTextArea
        etiquetaDireccion.setForeground(textColor);

        // Color de fondo para el JTextArea
        etiquetaDireccion.setBackground(textFieldColor);

        // Color de fondo y texto para el botón
        Color buttonColor = Color.decode("#37474f");
        cancelarButton.setBackground(buttonColor);
        cancelarButton.setForeground(textFieldColor);
        // Quitar el icono del botón
        cancelarButton.setIcon(null);

        // Color de fondo y texto para el botón
        cancelarButton.setBackground(primaryColor);
        cancelarButton.setForeground(Color.white);

        // Establecer tono oscuro al pasar el cursor sobre el botón
        Color darkerColor = primaryColor.darker();
        cancelarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelarButton.setBackground(darkerColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelarButton.setBackground(primaryColor);
            }
        });

        // Eliminar borde alrededor del texto del botón
        cancelarButton.setBorderPainted(false);
        cancelarButton.setFocusPainted(false);




        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaEmpleados listaEmpleados = new ListaEmpleados();
                listaEmpleados.setVisible(true);
                actual.dispose();
            }
        });
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM " + Empleado.nombreTabla + " WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // Verificar si hay un resultado antes de obtener los valores
                etiquetaIdentidad.setText(resultSet.getString(2));
                etiquetaNombre.setText(resultSet.getString(3));
                etiquetaApellido.setText(resultSet.getString(4));
                etiquetaGenero.setText(resultSet.getString(5));
                etiquetaEdad.setText(resultSet.getString(6));
                etiquetaCorreo.setText(resultSet.getString(7));
                etiquetaTelefono.setText(resultSet.getString(8));
                etiquetaNombreContacto.setText(resultSet.getString(9));
                etiquetaTelefonoContacto.setText(resultSet.getString(10));
                etiquetaDireccion.setText(resultSet.getString(11));
                etiquetaTipo.setText(resultSet.getString(12));
            }

        } catch (SQLException error) {
            // Mensaje de error
            System.out.println(error.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VerEmpleado verEmpleado = new VerEmpleado(1); // Pasa el ID del cliente que deseas ver
                verEmpleado.setVisible(true);
            }
        });
    }
}