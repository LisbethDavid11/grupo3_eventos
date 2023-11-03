package Login;

import Objetos.Conexion;
import org.jdesktop.swingx.prompt.PromptSupport;
import org.mindrot.jbcrypt.BCrypt;
import javax.mail.*;
import javax.mail.internet.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Reset extends JFrame {
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
    EmptyBorder margin = new EmptyBorder(15, 0, 40, 0);
    EmptyBorder marginBoton = new EmptyBorder(15, 0, 15, 0);
    EmptyBorder marginTextArea = new EmptyBorder(0, 0, 15, 0);
    EmptyBorder marginBotonMostrar = new EmptyBorder(8, 5, 9, 5);

    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JButton botonRecupera;
    private JButton botonMostrar;
    private JPasswordField campoContrasenaAnterior;
    private JPasswordField campoContrasenaActual;
    private JPasswordField campoContrasenaConfirmar;
    private JTextArea textArea1;
    private Conexion sql;
    private Connection mysql;
    private String mensajeRecuperacion = "Por favor, ingresa la contraseña actual que se te envió a la dirección de correo electrónico, así mismo la nueva contraseña y la confirmación.";
    private char defaultEchoChar;
    private boolean esVisible = false;
    private String correoUsuario;

    public Reset(String correoUsuario) {
        super("");
        setSize(685,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        sql = new Conexion();
        this.correoUsuario = correoUsuario;


        defaultEchoChar = campoContrasenaAnterior.getEchoChar();

        PromptSupport.init("  Ingresa la contraseña anterior", Color.GRAY, null, campoContrasenaAnterior);
        PromptSupport.init("  Ingresa la nueva contraseña", Color.GRAY, null, campoContrasenaActual);
        PromptSupport.init("  Confirmá la nueva contraseña", Color.GRAY, null, campoContrasenaConfirmar);

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
        botonMostrar.setBorder(marginBoton);
        textArea1.setBorder(marginTextArea);

        // Personalización de los botones al estilo Material UI
        personalizeButton(botonRecupera, darkColorBlue, lightColorBlue, darkColorBlue);
        personalizeButton(botonMostrar, darkColorGray, darkColorGray, darkColorGray);

        botonRecupera.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarContrasena();
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarContrasenas();
            }
        });
    }


    private boolean verificarContrasenaAnterior(String correo, char[] contrasena) {
        // Aquí iría el código para conectar con la base de datos y verificar la contraseña
        Conexion sql = new Conexion();
        try (Connection connection = sql.conectamysql()) {
            String query = "SELECT contrasena FROM usuarios WHERE correo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, correo);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    String contrasenaGuardada = resultSet.getString("contrasena");
                    return BCrypt.checkpw(new String(contrasena), contrasenaGuardada);
                }
            }
        } catch (SQLException e) {
            // Manejar excepciones de SQL
            e.printStackTrace();
        }
        return false;
    }

    private void cambiarContrasena() {
        String contrasenaAnterior = new String(campoContrasenaAnterior.getPassword());
        String nuevaContrasena = new String(campoContrasenaActual.getPassword());
        String confirmarContrasena = new String(campoContrasenaConfirmar.getPassword());

        if (!verificarContrasenaAnterior(correoUsuario, contrasenaAnterior.toCharArray())) {
            mostrarDialogoPersonalizadoError("La contraseña anterior no es correcta.", Color.decode("#C62828"));
            return;
        }

        if (!nuevaContrasena.equals(confirmarContrasena)) {
            mostrarDialogoPersonalizadoError("Las contraseñas no coinciden.", Color.decode("#C62828"));
            return;
        }

        // Aquí deberías validar la fortaleza de la nueva contraseña según tu política de seguridad

        // Si todo es correcto, actualizamos la contraseña en la base de datos
        actualizarContrasenaEnBaseDatos(correoUsuario, nuevaContrasena);
        // Mostrar mensaje de éxito o manejar el caso de error
    }

    private void actualizarContrasenaEnBaseDatos(String correoUsuario, String nuevaContrasena) {
        Conexion sql = new Conexion();
        try (Connection connection = sql.conectamysql()) {
            System.out.println(nuevaContrasena);
            System.out.println(correoUsuario);
            String contrasenaEncriptada = BCrypt.hashpw(nuevaContrasena, BCrypt.gensalt());
            String query = "UPDATE usuarios SET contrasena = ? WHERE correo = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, contrasenaEncriptada);
                preparedStatement.setString(2, correoUsuario);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    mostrarDialogoPersonalizadoExito("La contraseña ha sido actualizada con éxito, inicia sesión.", Color.decode("#263238"));
                    dispose();
                    Login login = new Login();
                    login.setVisible(true);
                } else {
                    mostrarDialogoPersonalizadoError("La contraseña ha podido ser actualizada.", Color.decode("#C62828"));
                }
            }
        } catch (SQLException e) {
            mostrarDialogoPersonalizadoError("Error al intentar conectar con la base de datos.", Color.decode("#C62828"));

        }
    }

    private void mostrarContrasenas() {
        // Cambiar el estado de visibilidad de la contraseña
        esVisible = !esVisible;

        // Establecer el modo de eco dependiendo del estado de visibilidad
        char modoEco = esVisible ? (char) 0 : defaultEchoChar; // Usa el carácter de eco por defecto

        campoContrasenaAnterior.setEchoChar(modoEco);
        campoContrasenaActual.setEchoChar(modoEco);
        campoContrasenaConfirmar.setEchoChar(modoEco);
    }

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

    public static void main(String[] args) {
        Reset reset = new Reset(null);
        reset.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reset.setVisible(true);
    }
}
