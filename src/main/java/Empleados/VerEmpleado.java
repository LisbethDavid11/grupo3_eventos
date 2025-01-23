/**
 * VerEmpleado.java
 *
 * Ver Empleado
 *
 * @author Alejandra Aroca
 * @version 1.0
 * @since 2024-05-05
 */

package Empleados;

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
    // Botón cancelar
    public JButton cancelarButton;

    // Panel
    public JPanel panel1;

    // Campos de texto
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

    // Etiquetas
    private JLabel lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel lbl7;
    private JLabel lbl8;
    private JLabel lbl9;
    private JLabel lbl10;
    private JLabel lbl11;

    // Referencia a la ventana de ver empleado
    public VerEmpleado mostrarEmpleado = this;

    // Referencia a la ventana actual
    private final VerEmpleado actual = this;

    // Conexión a la base de datos
    private Conexion sql;
    private Connection mysql;

    // ID del empleado
    private int id;

    // Fuentes y colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 20);
    Font font = new Font("Century Gothic", Font.BOLD, 15);
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

    // Colores para el botón "Amber"
    Color primaryColorAmber = new Color(255, 193, 7); // Amber primario
    Color lightColorAmber = new Color(255, 213, 79); // Amber claro
    Color darkColorAmber = new Color(255, 160, 0); // Amber oscuro

    // Colores para el botón "Verde lima"
    Color primaryColorVerdeLima = new Color(205, 220, 57); // Verde lima primario
    Color lightColorVerdeLima = new Color(220, 237, 200); // Verde lima claro
    Color darkColorVerdeLima = new Color(139, 195, 74); // Verde lima oscuro

    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public VerEmpleado(int id) {
        super("");
        setSize(600, 475);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();

        etiquetaDireccion.setLineWrap(true);
        etiquetaDireccion.setWrapStyleWord(true);

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

        panel1.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
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
        etiquetaDireccion.setForeground(textColor);

        Color textFieldColor = Color.decode("#F5F5F5");
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
        etiquetaDireccion.setBackground(textFieldColor);

        etiquetaNombre.setBorder(BorderFactory.createEmptyBorder());
        etiquetaApellido.setBorder(BorderFactory.createEmptyBorder());
        etiquetaIdentidad.setBorder(BorderFactory.createEmptyBorder());
        etiquetaGenero.setBorder(BorderFactory.createEmptyBorder());
        etiquetaEdad.setBorder(BorderFactory.createEmptyBorder());
        etiquetaCorreo.setBorder(BorderFactory.createEmptyBorder());
        etiquetaTelefono.setBorder(BorderFactory.createEmptyBorder());
        etiquetaNombreContacto.setBorder(BorderFactory.createEmptyBorder());
        etiquetaTelefonoContacto.setBorder(BorderFactory.createEmptyBorder());
        etiquetaTipo.setBorder(BorderFactory.createEmptyBorder());

        etiquetaNombre.setFont(font);
        etiquetaApellido.setFont(font);
        etiquetaIdentidad.setFont(font);
        etiquetaEdad.setFont(font);
        etiquetaGenero.setFont(font);
        etiquetaCorreo.setFont(font);
        etiquetaTelefono.setFont(font);
        etiquetaNombreContacto.setFont(font);
        etiquetaTelefonoContacto.setFont(font);
        etiquetaTipo.setFont(font);
        etiquetaDireccion.setFont(font);

        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setBackground(Color.decode("#263238"));
        cancelarButton.setFocusPainted(false);
        cancelarButton.setBorder(margin);

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        lbl1.setForeground(textColor2);
        lbl2.setForeground(textColor2);
        lbl3.setForeground(textColor2);
        lbl4.setForeground(textColor2);
        lbl5.setForeground(textColor2);
        lbl6.setForeground(textColor2);
        lbl7.setForeground(textColor2);
        lbl8.setForeground(textColor2);
        lbl9.setForeground(textColor2);
        lbl10.setForeground(textColor2);
        lbl11.setForeground(textColor2);

        lbl1.setFont(font2);
        lbl2.setFont(font2);
        lbl3.setFont(font2);
        lbl4.setFont(font2);
        lbl5.setFont(font2);
        lbl6.setFont(font2);
        lbl7.setFont(font2);
        lbl8.setFont(font2);
        lbl9.setFont(font2);
        lbl10.setFont(font2);
        lbl11.setFont(font2);

        cancelarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelarButton.setBackground(textColor2);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelarButton.setBackground(textColor);
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaEmpleados listaEmpleados = new ListaEmpleados();
                listaEmpleados.setVisible(true);
                actual.dispose();
            }
        });
    }

    // Método cargar datos del empleado
    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM " + Empleado.nombreTabla + " WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etiquetaIdentidad.setText(resultSet.getString(2));
                etiquetaNombre.setText(resultSet.getString(3));
                etiquetaApellido.setText(resultSet.getString(4));
                etiquetaGenero.setText(resultSet.getString(5));
                etiquetaEdad.setText(resultSet.getString(6) + " años");
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

    // Método Principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VerEmpleado verEmpleado = new VerEmpleado(1);
                verEmpleado.setVisible(true);
            }
        });
    }
}