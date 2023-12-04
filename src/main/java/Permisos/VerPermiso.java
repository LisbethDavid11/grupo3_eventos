package Permisos;

import Login.SesionUsuario;
import Objetos.Conexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerPermiso extends JFrame{
    private JTextField campoNombre;
    private JTextArea campoDomicilio, campoPermisos;
    private JButton volverButton;
    private JPanel panel, panel1;
    private JLabel label0, label1, label2, label3;
    private VerPermiso actual = this;
    private Connection mysql;
    private Conexion sql;
    public VerPermiso verPermiso = this;
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    // Color de texto para los JTextField y JRadioButton
    Color textColor = Color.decode("#212121");
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 20);
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

    int id;
    public VerPermiso(int id) {
        super("");
        setSize(500,450);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();
        this.id = id;
        mostrarRol();

        campoDomicilio.setLineWrap(true);
        campoDomicilio.setWrapStyleWord(true);

        campoPermisos.setLineWrap(true);
        campoPermisos.setWrapStyleWord(true);

        panel.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));

        campoNombre.setEditable(false);
        campoNombre.setFocusable(false);

        campoDomicilio.setEditable(false);
        campoDomicilio.setFocusable(false);
        campoPermisos.setEditable(false);
        campoPermisos.setFocusable(false);

        campoNombre.setBorder(BorderFactory.createEmptyBorder());
        campoDomicilio.setBorder(BorderFactory.createEmptyBorder());
        campoPermisos.setBorder(BorderFactory.createEmptyBorder());

        campoNombre.setFont(font);
        campoDomicilio.setFont(font);
        campoPermisos.setFont(font);

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        campoNombre.setForeground(textColor);
        campoDomicilio.setForeground(textColor);
        campoPermisos.setForeground(textColor);

        Color textFieldColor = Color.decode("#F5F5F5");
        campoNombre.setBackground(textFieldColor);
        campoDomicilio.setBackground(textFieldColor);
        campoPermisos.setBackground(textFieldColor);

        volverButton.setForeground(Color.WHITE);
        volverButton.setBackground(Color.decode("#263238"));
        volverButton.setFocusPainted(false);
        volverButton.setBorder(margin);

        label0.setBorder(margin);
        label0.setFont(fontTitulo);

        label1.setForeground(textColor2);
        label2.setForeground(textColor2);
        label3.setForeground(textColor2);

        label1.setFont(font2);
        label2.setFont(font2);
        label3.setFont(font2);

        volverButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor2);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor);
            }
        });

        campoNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoNombre.getText();
                int length = text.length();
                int caretPosition = campoNombre.getCaretPosition();

                // Verificar si se está ingresando un espacio en blanco
                if (e.getKeyChar() == ' ') {
                    // Verificar si es el primer espacio en blanco o si hay varios espacios consecutivos
                    if (length == 0 || caretPosition == 0 || text.charAt(caretPosition - 1) == ' ') {
                        e.consume(); // Ignorar el espacio en blanco adicional
                    }
                } else {
                    // Verificar la longitud del texto después de eliminar espacios en blanco
                    String trimmedText = text.replaceAll("\\s+", " ");
                    int trimmedLength = trimmedText.length();

                    // Verificar si se está ingresando una letra
                    if (Character.isLetterOrDigit(e.getKeyChar())) {
                        // Verificar si se excede el límite de caracteres
                        if (trimmedLength >= 100) {
                            e.consume(); // Ignorar la letra
                        } else {
                            // Convertir solo la primera letra a mayúscula
                            if (caretPosition == 0) {
                                e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                            }
                        }
                    } else {
                        e.consume(); // Ignorar cualquier otro tipo de carácter
                    }
                }
            }
        });

        campoDomicilio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String texto = campoDomicilio.getText();
                int caretPosition = campoDomicilio.getCaretPosition();

                // Verificar la longitud del texto
                if (texto.length() >= 200) {
                    e.consume(); // Ignorar el evento si se alcanza el límite máximo de caracteres (200)
                    return;
                }

                // Verificar si se están ingresando más de un espacio en blanco seguido
                if (e.getKeyChar() == ' ' && texto.endsWith(" ")) {
                    e.consume(); // Ignorar el evento y no agregar el espacio en blanco adicional
                    return;
                }

                // Convertir la primera letra en mayúscula
                if (texto.length() == 0 && Character.isLowerCase(e.getKeyChar())) {
                    e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                }

                // Permitir números, letras, espacios, punto, coma y tildes
                if (!Character.isLetterOrDigit(e.getKeyChar()) && !Character.isSpaceChar(e.getKeyChar()) && e.getKeyChar() != '.' && e.getKeyChar() != ',' && !Character.isWhitespace(e.getKeyChar()) && !Character.isIdeographic(e.getKeyChar())) {
                    e.consume(); // Ignorar el evento si no es una letra, número, espacio, punto, coma o tilde
                }
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaPermisos listaPermisos = new ListaPermisos();
                listaPermisos.setVisible(true);
                dispose();
            }
        });

    }

    private void mostrarRol() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT r.nombre, r.descripcion, p.cliente, p.empleado, p.floristeria, p.arreglo, p.usuario, " +
                             "p.material, p.proveedor, p.compra, p.tarjeta, p.manualidad, p.globo, p.desayuno, p.venta, " +
                             "p.mobiliario, p.pedido, p.promocion, p.evento, p.actividad, p.alquiler, p.rol " +
                             "FROM permisos p INNER JOIN roles r ON p.id_rol = r.id WHERE p.id_rol = ?")) {

            preparedStatement.setInt(1, this.id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String nombreRol = rs.getString("nombre");
                String descripcionRol = rs.getString("descripcion");

                StringBuilder permisos = new StringBuilder();
                if (rs.getBoolean("cliente")) permisos.append("Cliente, ");
                if (rs.getBoolean("empleado")) permisos.append("Empleado, ");
                if (rs.getBoolean("floristeria")) permisos.append("Floristeria, ");
                if (rs.getBoolean("arreglo")) permisos.append("Arreglo, ");
                if (rs.getBoolean("usuario")) permisos.append("Usuario, ");
                if (rs.getBoolean("material")) permisos.append("Material, ");
                if (rs.getBoolean("proveedor")) permisos.append("Proveedor, ");
                if (rs.getBoolean("compra")) permisos.append("Compra, ");
                if (rs.getBoolean("tarjeta")) permisos.append("Tarjeta, ");
                if (rs.getBoolean("manualidad")) permisos.append("Manualidad, ");
                if (rs.getBoolean("globo")) permisos.append("Globo, ");
                if (rs.getBoolean("desayuno")) permisos.append("Desayuno, ");
                if (rs.getBoolean("venta")) permisos.append("Venta, ");
                if (rs.getBoolean("mobiliario")) permisos.append("Mobiliario, ");
                if (rs.getBoolean("pedido")) permisos.append("Pedido, ");
                if (rs.getBoolean("promocion")) permisos.append("Promocion, ");
                if (rs.getBoolean("evento")) permisos.append("Evento, ");
                if (rs.getBoolean("actividad")) permisos.append("Actividad, ");
                if (rs.getBoolean("alquiler")) permisos.append("Alquiler, ");
                if (rs.getBoolean("rol")) permisos.append("Rol, ");

                // Elimina la coma y el espacio extra al final, si existen
                if (permisos.length() > 0) {
                    permisos.delete(permisos.length() - 2, permisos.length());
                }

                campoNombre.setText(nombreRol);
                campoDomicilio.setText(descripcionRol);
                campoPermisos.setText(permisos.toString());
            } else {
                JOptionPane.showMessageDialog(this, "El rol con este ID no tiene permisos asignados.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del rol.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        VerPermiso verPermiso = new VerPermiso(1);
        verPermiso.setVisible(true);
    }
}