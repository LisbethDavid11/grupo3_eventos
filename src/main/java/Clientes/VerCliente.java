package Clientes;

import Objetos.Cliente;
import Objetos.Conexion;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerCliente extends JFrame {
    private JPanel panel1;
    private JTextField etiquetaNombre;
    private JTextField etiquetaApellido;
    private JTextField etiquetaTelefono;
    private JTextField etiquetaIdentidad;
    private JTextArea etiquetaDomicilio;
    private JTextField etiquetaTipoCliente;
    private JButton volverButton;
    private JLabel lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private final VerCliente actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;
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
    public VerCliente(int id) {
        super("");
        setSize(550, 370);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();

        etiquetaDomicilio.setLineWrap(true);
        etiquetaDomicilio.setWrapStyleWord(true);

        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaApellido.setEditable(false);
        etiquetaApellido.setFocusable(false);
        etiquetaTelefono.setEditable(false);
        etiquetaTelefono.setFocusable(false);
        etiquetaIdentidad.setEditable(false);
        etiquetaIdentidad.setFocusable(false);
        etiquetaDomicilio.setEditable(false);
        etiquetaDomicilio.setFocusable(false);
        etiquetaTipoCliente.setEditable(false);
        etiquetaTipoCliente.setFocusable(false);

        panel1.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        etiquetaNombre.setForeground(textColor);
        etiquetaApellido.setForeground(textColor);
        etiquetaTelefono.setForeground(textColor);
        etiquetaIdentidad.setForeground(textColor);
        etiquetaTipoCliente.setForeground(textColor);
        etiquetaDomicilio.setForeground(textColor);

        Color textFieldColor = Color.decode("#F5F5F5");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaApellido.setBackground(textFieldColor);
        etiquetaTelefono.setBackground(textFieldColor);
        etiquetaIdentidad.setBackground(textFieldColor);
        etiquetaTipoCliente.setBackground(textFieldColor);
        etiquetaDomicilio.setBackground(textFieldColor);

        etiquetaNombre.setBorder(BorderFactory.createEmptyBorder());
        etiquetaApellido.setBorder(BorderFactory.createEmptyBorder());
        etiquetaIdentidad.setBorder(BorderFactory.createEmptyBorder());
        etiquetaTelefono.setBorder(BorderFactory.createEmptyBorder());
        etiquetaDomicilio.setBorder(BorderFactory.createEmptyBorder());
        etiquetaTipoCliente.setBorder(BorderFactory.createEmptyBorder());

        etiquetaNombre.setFont(font);
        etiquetaApellido.setFont(font);
        etiquetaIdentidad.setFont(font);
        etiquetaTelefono.setFont(font);
        etiquetaDomicilio.setFont(font);
        etiquetaTipoCliente.setFont(font);

        volverButton.setForeground(Color.WHITE);
        volverButton.setBackground(Color.decode("#263238"));
        volverButton.setFocusPainted(false);
        volverButton.setBorder(margin);

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        lbl1.setForeground(textColor2);
        lbl2.setForeground(textColor2);
        lbl3.setForeground(textColor2);
        lbl4.setForeground(textColor2);
        lbl5.setForeground(textColor2);
        lbl6.setForeground(textColor2);

        lbl1.setFont(font2);
        lbl2.setFont(font2);
        lbl3.setFont(font2);
        lbl4.setFont(font2);
        lbl5.setFont(font2);
        lbl6.setFont(font2);

        volverButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor2);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaClientes indexCliente = new ListaClientes();
                indexCliente.setVisible(true);
                actual.dispose();
            }
        });
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM " + Cliente.nombreTabla + " WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // Verificar si hay un resultado antes de obtener los valores
                etiquetaNombre.setText(resultSet.getString(2));
                etiquetaApellido.setText(resultSet.getString(3));
                etiquetaIdentidad.setText(resultSet.getString(4));
                etiquetaTelefono.setText(resultSet.getString(5));
                etiquetaDomicilio.setText(resultSet.getString(6));
                etiquetaTipoCliente.setText(resultSet.getString(7));
            }

        } catch (SQLException error) {
            // Mensaje de error
            System.out.println(error.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VerCliente verCliente = new VerCliente(1);
                verCliente.setVisible(true);
            }
        });
    }
}
