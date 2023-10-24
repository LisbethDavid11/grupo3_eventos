package Actividades;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerActividad extends JFrame {
    private JPanel panel1;
    private JTextField campoNombre;
    private JTextField campoFecha;
    private JTextField campoFin;
    private JTextField campoInicio;
    private JTextArea campoDireccion;
    private JButton volverButton;
    private JLabel lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JTextArea campoDescripcion;
    private JLabel lbl6;
    private final VerActividad actual = this;
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
    public VerActividad(int id) {
        super("");
        setSize(580, 470);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();

        campoDireccion.setLineWrap(true);
        campoDireccion.setWrapStyleWord(true);

        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        campoNombre.setEditable(false);
        campoNombre.setFocusable(false);
        campoFecha.setEditable(false);
        campoFecha.setFocusable(false);
        campoFin.setEditable(false);
        campoFin.setFocusable(false);
        campoInicio.setEditable(false);
        campoInicio.setFocusable(false);
        campoDireccion.setEditable(false);
        campoDireccion.setFocusable(false);
        campoDescripcion.setEditable(false);
        campoDescripcion.setFocusable(false);


        panel1.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        campoNombre.setForeground(textColor);
        campoFecha.setForeground(textColor);
        campoFin.setForeground(textColor);
        campoInicio.setForeground(textColor);
        campoDireccion.setForeground(textColor);
        campoDescripcion.setForeground(textColor);

        Color textFieldColor = Color.decode("#F5F5F5");
        campoNombre.setBackground(textFieldColor);
        campoFecha.setBackground(textFieldColor);
        campoFin.setBackground(textFieldColor);
        campoInicio.setBackground(textFieldColor);;
        campoDireccion.setBackground(textFieldColor);
        campoDescripcion.setBackground(textFieldColor);

        campoNombre.setBorder(BorderFactory.createEmptyBorder());
        campoFecha.setBorder(BorderFactory.createEmptyBorder());
        campoInicio.setBorder(BorderFactory.createEmptyBorder());
        campoFin.setBorder(BorderFactory.createEmptyBorder());
        campoDireccion.setBorder(BorderFactory.createEmptyBorder());
        campoDescripcion.setBorder(BorderFactory.createEmptyBorder());

        campoNombre.setFont(font);
        campoFecha.setFont(font);
        campoInicio.setFont(font);
        campoFin.setFont(font);
        campoDireccion.setFont(font);
        campoDescripcion.setFont(font);

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
                CalendarioDeActividades calendarioDeActividades = new CalendarioDeActividades();
                calendarioDeActividades.setVisible(true);
                actual.dispose();
            }
        });
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM actividades WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) { // Verificar si hay un resultado antes de obtener los valores
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d 'de' MMMM, yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

                String nombre = resultSet.getString("nombre");
                java.sql.Date fecha = resultSet.getDate("fecha");
                java.sql.Time inicio = resultSet.getTime("inicio");
                java.sql.Time fin = resultSet.getTime("fin");
                String descripcion = resultSet.getString("descripcion");
                String direccion = resultSet.getString("direccion");

                campoNombre.setText(nombre);
                campoFecha.setText(dateFormat.format(fecha));
                campoInicio.setText(timeFormat.format(inicio));
                campoFin.setText(timeFormat.format(fin));
                campoDescripcion.setText(descripcion);
                campoDireccion.setText(direccion);
            }
        } catch (SQLException error) {
            // Mensaje de error
            System.out.println(error.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VerActividad verActividad = new VerActividad(1);
                verActividad.setVisible(true);
            }
        });
    }
}
