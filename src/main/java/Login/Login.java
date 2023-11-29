package Login;

import Objetos.Conexion;
import Objetos.Rol;
import SubMenu.SubMenu;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends JFrame {
    Font fontTituloPrimario = new Font("Century Gothic", Font.BOLD, 32);
    Font fontCopyrigth = new Font("Century Gothic", Font.PLAIN, 11);
    Font fontTituloSecundario = new Font("Century Gothic", Font.BOLD, 24);
    Font font = new Font("Century Gothic", Font.BOLD, 14);

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
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
    EmptyBorder marginBotonMostrar = new EmptyBorder(8, 5, 9, 5);
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JButton botonLogin;
    private JButton botonRecuperar;
    private JButton botonMostrar;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private Conexion sql;
    private Connection mysql;
    private char defaultEchoChar;
    private boolean esVisible = false;

    public Login() {
        super("");
        setSize(745,500);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();

        defaultEchoChar = campoContrasena.getEchoChar();
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
        botonLogin.setBorder(margin);
        botonMostrar.setBorder(marginBotonMostrar);

        // Personalización de los botones al estilo Material UI
        personalizeButton(botonLogin, darkColorBlue, lightColorBlue, darkColorBlue);
        personalizeButton(botonRecuperar, darkColorRed, lightColorRosado, darkColorRosado);
        personalizeButton(botonMostrar, darkColorGray, darkColorGray, darkColorGray);

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

        campoContrasena.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String contrasena = campoContrasena.getText();
                char c = e.getKeyChar();

                // Verificar si se excede la longitud máxima
                if (contrasena.length() >= 25) {
                    e.consume(); // Ignorar el evento si se alcanza el límite máximo de caracteres (25)
                    return;
                }

                // Verificar si se está ingresando un espacio en blanco
                if (Character.isWhitespace(c)) {
                    e.consume(); // Ignorar el espacio en blanco
                    return;
                }

                // Para las contraseñas, usualmente se permite una mayor variedad de caracteres.
                // Aquí puedes agregar las restricciones que consideres necesarias.
                // El siguiente ejemplo permite letras, números y algunos caracteres especiales comunes.
                if (!Character.isLetterOrDigit(c) && c != '-' && c != '@' && c != '.' && c != '!' && c != '#' && c != '$' && c != '%' && c != '&' && c != '*') {
                    e.consume();
                }
            }
        });



        botonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                if (campoCorreo.getText().trim().isEmpty() && campoContrasena.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El correo electrónico y la contraseña no puede estar vacíos.", Color.decode("#C62828"));
                    return;
                }

                if (campoCorreo.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El correo electrónico no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                if (campoContrasena.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("La contraseña no puede estar vacía.", Color.decode("#C62828"));
                    return;
                }

                login(evt);
            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                mostrarContrasena();
            }
        });

        botonRecuperar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ResetPassword reset = new ResetPassword();
                reset.setVisible(true);
                dispose();
            }
        });
    }

    private DatosUsuario verificarCredenciales(String correo, String contrasena) {
        Conexion sql = new Conexion();
        Connection connection = sql.conectamysql();
        try {
            String query = "SELECT id, nombre, correo, contrasena, imagen FROM usuarios WHERE correo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, correo);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String contrasenaEncriptada = resultSet.getString("contrasena");
                if (BCrypt.checkpw(contrasena, contrasenaEncriptada)) {

                    return new DatosUsuario(
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("correo"),
                            resultSet.getString("contrasena"), // Considerar seguridad
                            resultSet.getString("imagen")
                    );
                }
            }
            return null;
        } catch (SQLException e) {
            mostrarDialogoPersonalizadoError("Error al verificar las credenciales" + e.getMessage(), Color.decode("#C62828"));
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void login(java.awt.event.ActionEvent evt) {
        String correo = campoCorreo.getText();
        String contrasena = new String(campoContrasena.getPassword());
        DatosUsuario datosUsuario = verificarCredenciales(correo, contrasena);

        if (datosUsuario != null) {
            SesionUsuario sesion = SesionUsuario.getInstance();
            sesion.setIdUsuario(datosUsuario.getId());
            sesion.setNombreUsuario(datosUsuario.getNombre());
            sesion.setCorreoUsuario(datosUsuario.getCorreo());
            sesion.setImagenUsuario(datosUsuario.getImagen());
            sesion.setRolId(datosUsuario.getRolId());

            mostrarDialogoPersonalizadoExito("Inicio de sesión exitoso. Bienvenido " + datosUsuario.getNombre() + ".", Color.decode("#263238"));

            // Crea una instancia de SubMenu y pasa los permisos
            SubMenu menu = new SubMenu(datosUsuario.getRol());
            menu.setIdUsuarioActual(datosUsuario.getId());
            menu.setNombreUsuario(datosUsuario.getNombre());
            menu.setImagenUsuario(datosUsuario.getImagen());
            menu.setVisible(true);
            this.dispose();
        } else {
            mostrarDialogoPersonalizadoError("Credenciales incorrectas. Inténtalo de nuevo.", Color.decode("#C62828"));
        }
    }

    private void mostrarContrasena() {
        // Cambiar el estado de visibilidad de la contraseña
        esVisible = !esVisible;

        // Establecer el modo de eco dependiendo del estado de visibilidad
        char modoEco = esVisible ? (char) 0 : defaultEchoChar; // Usa el carácter de eco por defecto

        campoContrasena.setEchoChar(modoEco);
    }

    private void personalizeButton(JButton button, Color primary, Color light, Color dark) {
        button.setForeground(blancoColorPrincipal);
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

    public static void main(String[] args) {
        Login login = new Login();
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.setVisible(true);
    }
}
