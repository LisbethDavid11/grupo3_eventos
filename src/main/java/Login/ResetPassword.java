/**
 * ResetPassword.java
 *
 * Reestablecer Contraseña
 *
 * @author Alejandra Aroca
 * @version 1.0
 * @since 2024-05-05
 */

package Login;

import Objetos.Conexion;
import org.mindrot.jbcrypt.BCrypt;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class ResetPassword extends JFrame {
    // Fuente y colores
    Font fontTituloPrimario = new Font("Century Gothic", Font.BOLD, 32);
    Font fontCopyrigth = new Font("Century Gothic", Font.PLAIN, 11);
    Font fontTituloSecundario = new Font("Century Gothic", Font.BOLD, 24);
    Font font = new Font("Century Gothic", Font.BOLD, 14);
    Font fontSubTitulo = new Font("Century Gothic", Font.BOLD, 14);

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
    Color primaryColorBlue = new Color(33, 150, 243);
    Color darkColorBlue = new Color(13, 71, 161);
    Color lightColorBlue = new Color(66, 165, 245);
    Color darkColorGray = new Color(38, 50, 56);
    Color lightColorPrincipal = new Color(245,245,245);
    Color blancoColorPrincipal = new Color(255, 255, 255);
    Color textColor = Color.decode("#263238");
    EmptyBorder margin = new EmptyBorder(15, 0, 45, 0);
    EmptyBorder marginBoton = new EmptyBorder(15, 0, 15, 0);
    EmptyBorder marginBotonMostrar = new EmptyBorder(8, 5, 9, 5);
    EmptyBorder marginTextArea = new EmptyBorder(0, 0, 15, 0);

    // Paneles
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;

    // Etiquetas de texto
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;

    // Botones
    private JButton botonRecupera;
    private JButton botonLogin;
    private JButton botonMostrar;

    // Campos de texto
    private JTextField campoCorreo;
    private JTextArea textArea1;

    // Campos de texto para contraseñas
    private JPasswordField campoContrasenaAnterior;
    private JPasswordField campoContrasenaActual;
    private JPasswordField campoContrasenaConfirmar;

    // Otras variables
    private Conexion sql;
    private Connection mysql;
    private String mensajeRecuperacion = "Por favor, ingresa tu dirección de correo electrónico en el campo de texto y haz clic en 'Recuperar' para enviar la solicitud de recuperación de contraseña.";

    public ResetPassword() {
        super("");
        setSize(685,500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();

        textArea1.setText(mensajeRecuperacion);
        textArea1.setFocusable(false);
        textArea1.setFont(fontSubTitulo);
        textArea1.setWrapStyleWord(true);
        textArea1.setLineWrap(true);
        textArea1.setEditable(false); // Hacer el área de texto no editable si solo es para mostrar información
        textArea1.setBackground(new Color(245, 245, 245)); // Puedes cambiar el color si quieres
        textArea1.setBorder(BorderFactory.createCompoundBorder(textArea1.getBorder(), BorderFactory.createEmptyBorder(5, 0, 5, 0)));

        panel1.setBackground(lightColorPrincipal);
        panel2.setBackground(darkColorGray);
        panel3.setBackground(lightColorPrincipal);
        panel4.setBackground(lightColorPrincipal);
        panel5.setBackground(lightColorPrincipal);

        label1.setForeground(blancoColorPrincipal);
        label2.setForeground(blancoColorPrincipal);

        label1.setFont(fontTituloPrimario);
        label2.setFont(fontCopyrigth);
        label3.setFont(fontTituloSecundario);

        label3.setBorder(margin);
        botonRecupera.setBorder(marginBoton);
        textArea1.setBorder(marginTextArea);

        // Personalización de los botones al estilo Material UI
        personalizeButton(botonRecupera, darkColorBlue, lightColorBlue, darkColorBlue);
        personalizeButton(botonLogin, darkColorAqua, lightColorAqua, darkColorAqua);

        campoCorreo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String correo = campoCorreo.getText();
                char c = e.getKeyChar();

                // Verificar si se excede la longitud máxima
                if (correo.length() >= 45) {
                    e.consume(); // Ignorar el evento si se alcanza el límite máximo de caracteres (45)
                    return;
                }

                // Verificar si se está ingresando un espacio en blanco
                if (Character.isWhitespace(c)) {
                    e.consume(); // Ignorar el espacio en blanco
                    return;
                }

                // Verificar si el carácter es válido (letra, número, guion, arroba o punto)
                if (!Character.isLetterOrDigit(c) && c != '-' && c != '@' && c != '.') {
                    e.consume(); // Ignorar el carácter si no es válido
                }
            }
        });

        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });

        botonRecupera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String toEmail = campoCorreo.getText(); // Recibe el correo del usuario

                // Verifica si el correo existe en la base de datos
                boolean existe = verificarCorreoEnBaseDeDatos(toEmail);

                if (existe) {
                    String nuevaContrasena = generarContrasenaTemporal();
                    // Configura las propiedades del servidor SMTP de Hotmail
                    Properties props = new Properties();
                    props.put("mail.smtp.host", "smtp.office365.com"); // SMTP Host para Hotmail/Outlook
                    props.put("mail.smtp.port", "587"); // TLS Port
                    props.put("mail.smtp.auth", "true"); // Habilitar autenticación SMTP
                    props.put("mail.smtp.starttls.enable", "true"); // Habilitar STARTTLS

                    Authenticator auth = new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("ldavid@unah.hn", "David11mendoza.");
                        }
                    };

                    Session session = Session.getInstance(props, auth);
                    MimeMessage msg = new MimeMessage(session);

                    try {
                        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                        msg.setFrom(new InternetAddress("ldavid@unah.hn", "Eventos Chelsea"));
                        msg.setReplyTo(InternetAddress.parse("ldavid@unah.hn", false));
                        msg.setSubject("Recuperación de contraseña", "UTF-8");
                        msg.setText("Este es el mensaje de recuperación de contraseña; la nueva contraseña es: <b>" + nuevaContrasena + "</b>", "UTF-8", "html");
                        msg.setSentDate(new Date());

                        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
                        Transport.send(msg);

                        // Actualiza la contraseña en la base de datos
                        actualizarContrasenaEnBaseDatos(toEmail, nuevaContrasena);

                        mostrarDialogoPersonalizadoExito("El correo electrónico se ha enviado correctamente.\nRevisa la bandeja de entrada o la carpeta SPAM", Color.decode("#263238"));
                        dispose();
                        String correo = campoCorreo.getText();
                        Reset reset = new Reset(correo);
                        reset.setVisible(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                        mostrarDialogoPersonalizadoError("Error al enviar el correo de recuperación.", Color.decode("#C62828"));
                    }
                } else mostrarDialogoPersonalizadoError("Verifica que hayas ingresado una dirección de correo eletrónico correcta.", Color.decode("#C62828"));
            }
        });
    }

    // Método booleano para verificar la contraseña en la base de datos
    private boolean verificarCorreoEnBaseDeDatos(String correo) {
        // Asume que la clase Conexion tiene un método para conectarse a la base de datos
        Conexion sql = new Conexion();

        String query = "SELECT COUNT(*) FROM usuarios WHERE correo = ?";
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, correo);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1); // Obtiene el resultado de COUNT(*)
                return count > 0; // Si hay al menos un resultado, el correo existe
            }
        } catch (SQLException e) {
            // Manejo de errores, podrías por ejemplo imprimir el stack trace o loggear el error
            e.printStackTrace();
        }
        return false; // Retorna false si no se encontró el correo o si ocurrió un error
    }

    // Metódo para generar una contraseña temporal
    private String generarContrasenaTemporal() {
        // Puedes ampliar este método para generar contraseñas más complejas
        int longitud = 10; // Por ejemplo, una longitud de 10 caracteres
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(longitud);
        for (int i = 0; i < longitud; i++)
            sb.append(caracteres.charAt(rnd.nextInt(caracteres.length())));
        return sb.toString();
    }

    // Método para actualizar la contraseña en la base de datos
    private void actualizarContrasenaEnBaseDatos(String correo, String nuevaContrasena) {
        Conexion sql = new Conexion();
        try (Connection connection = sql.conectamysql()) {
            String contrasenaEncriptada = BCrypt.hashpw(nuevaContrasena, BCrypt.gensalt());
            String query = "UPDATE usuarios SET contrasena = ? WHERE correo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, contrasenaEncriptada);
                preparedStatement.setString(2, correo);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    // Actualización exitosa
                } else {
                    // Error al actualizar
                }
            }
        } catch (SQLException e) {
            // Manejar excepciones de SQL
        }
    }

    // Método para dialogo personalizar de éxito
    public void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("ACEPTAR");
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
        JDialog dialog = optionPane.createDialog("Éxito");

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

    // Método para dialogo personalizar de error
    public void mostrarDialogoPersonalizadoError(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("ACEPTAR");
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
        JDialog dialog = optionPane.createDialog("Error");

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

    // Método para personalizar botón
    private void personalizeButton(JButton button, Color primary, Color light, Color dark) {
        button.setForeground(Color.WHITE);
        button.setBackground(primary);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(font);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(light);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(primary);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(dark);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(light);
            }
        });
    }

    // Método Principal
    public static void main(String[] args) {
        ResetPassword reset = new ResetPassword();
        reset.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reset.setVisible(true);
    }
}
