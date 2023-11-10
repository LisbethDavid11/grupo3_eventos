package Login;

import Objetos.Conexion;
import SubMenu.SubMenu;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class SignUp extends JFrame {
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
    private JButton botonRegistrar;
    private JButton botonLogin;
    private JButton botonRecuperar;
    private JButton botonMostrar;
    private JTextField campoNombre;
    private JTextField campoCorreo;
    private JPasswordField campoContrasena;
    private Conexion sql;
    private Connection mysql;
    private char defaultEchoChar;
    private boolean esVisible = false;

    public SignUp() {
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
        botonRegistrar.setBorder(margin);
        botonMostrar.setBorder(marginBotonMostrar);

        // Personalización de los botones al estilo Material UI
        personalizeButton(botonRegistrar, darkColorBlue, lightColorBlue, darkColorBlue);
        personalizeButton(botonLogin, darkColorAqua, lightColorAqua, darkColorAqua);
        personalizeButton(botonRecuperar, darkColorRed, lightColorRosado, darkColorRosado);
        personalizeButton(botonMostrar, darkColorGray, darkColorGray, darkColorGray);

        botonRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (campoNombre.getText().trim().isEmpty() && campoContrasena.getText().trim().isEmpty() && campoCorreo.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El nombre completo, correo electrónico y la contraseña no puede estar vacíos.", Color.decode("#C62828"));
                    return;
                }

                if (campoNombre.getText().trim().isEmpty() && campoCorreo.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El nombre completo y el correo electrónico no puede estar vacíos.", Color.decode("#C62828"));
                    return;
                }

                if (campoCorreo.getText().trim().isEmpty() && campoContrasena.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El correo electrónico y la contraseña no puede estar vacíos.", Color.decode("#C62828"));
                    return;
                }

                if (campoNombre.getText().trim().isEmpty() && campoContrasena.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El nombre completo y la contraseña no puede estar vacíos.", Color.decode("#C62828"));
                    return;
                }

                if (campoNombre.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El nombre completo no puede estar vacío.", Color.decode("#C62828"));
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

                String nombre = campoNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 50) {
                        mostrarDialogoPersonalizadoError("El nombre de usuario debe tener como máximo 50 caracteres.", Color.decode("#C62828"));
                        return;
                    }

                    if (!nombre.matches("^[a-zA-ZñÑáéíóúÁÉÍÓÚ]+(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?$")) {
                        mostrarDialogoPersonalizadoError("El nombre de usuario debe tener mínimo 2 letras y máximo 3 espacios (1 entre palabras).", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de nombre de usuario no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String correoElectronico = campoCorreo.getText().trim();
                if (!correoElectronico.isEmpty()) {
                    // Verificar el formato del correo electrónico utilizando una expresión regular
                    if (!correoElectronico.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        mostrarDialogoPersonalizadoError("El correo electrónico ingresado no tiene un formato válido.", Color.decode("#C62828"));
                        return;
                    }

                    // Verificar el dominio del correo electrónico
                    if (!correoElectronico.endsWith("@gmail.com") &&
                            !correoElectronico.endsWith("@unah.hn") &&
                            !correoElectronico.endsWith("@yahoo.com") &&
                            !correoElectronico.endsWith("@yahoo.es") &&
                            !correoElectronico.endsWith("@hotmail.com")) {
                        mostrarDialogoPersonalizadoError("El dominio del correo electrónico no es válido.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de correo electrónico no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                // Verificar si el correo ya está registrado
                String correo = campoCorreo.getText().trim();
                if (usuarioExistente(correo)) {
                    mostrarDialogoPersonalizadoError("El correo electrónico ya está registrado. Utiliza otro correo.", Color.decode("#C62828"));
                    return;
                }

                String contrasenia = campoContrasena.getText().trim();
                if (contrasenia.length() < 8) {
                    mostrarDialogoPersonalizadoError("La contraseña debe tener al menos 8 caracteres.", Color.decode("#C62828"));
                    return;
                }

                JButton btnSave = new JButton("Sí");
                JButton btnCancel = new JButton("No");

                // Personaliza los botones aquí
                btnSave.setBackground(new Color(0, 102, 102));
                btnCancel.setBackground(new Color( 244, 67, 54));

                // Personaliza los fondos de los botones aquí
                btnSave.setForeground(Color.WHITE);
                btnCancel.setForeground(Color.WHITE);

                // Elimina el foco
                btnSave.setFocusPainted(false);
                btnCancel.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Desea guardar la información del usuario?",
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        new Object[]{}, // no options
                        null
                );

                // Crea un JDialog
                JDialog dialog = optionPane.createDialog("Guardar");

                // Añade ActionListener a los botones
                btnSave.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Acciones para el botón Sí
                        dialog.dispose();
                        dialog.dispose();

                        /*
                        DatosUsuario datosUsuario = guardarUsuario();
                        if (datosUsuario != null) {
                            SesionUsuario sesion = SesionUsuario.getInstance();
                            sesion.setIdUsuario(datosUsuario.getId());
                            sesion.setNombreUsuario(datosUsuario.getNombre());
                            sesion.setCorreoUsuario(datosUsuario.getCorreo());
                            sesion.setImagenUsuario(datosUsuario.getImagen());
                            //sesion.setRolUsuario(datosUsuario.getRol());

                            SubMenu menu = new SubMenu();
                            menu.setIdUsuarioActual(datosUsuario.getId());
                            menu.setNombreUsuario(datosUsuario.getNombre());
                            menu.setImagenUsuario(datosUsuario.getImagen());
                            menu.setVisible(true);
                            dispose(); // Cierra el formulario actual

                         */

                    }
                });

                btnCancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Acciones para el botón No
                        // No se hace nada, sólo se cierra el diálogo
                        dialog.dispose();
                    }
                });

                // Añade los botones al JOptionPane
                optionPane.setOptions(new Object[]{btnSave, btnCancel});

                // Muestra el diálogo
                dialog.setVisible(true);

            }
        });

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                mostrarContrasena();
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

        botonRecuperar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ResetPassword reset = new ResetPassword();
                reset.setVisible(true);
                dispose();
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
                    // Verificar si es el primer espacio en blanco o ya se ingresaron tres espacios
                    if (length == 0 || text.chars().filter(ch -> ch == ' ').count() >= 3) {
                        e.consume(); // Ignorar el espacio en blanco
                    } else if (caretPosition > 0 && text.charAt(caretPosition - 1) == ' ') {
                        e.consume(); // Ignorar espacios en blanco adicionales
                    }
                } else {
                    // Verificar la longitud del texto después de eliminar espacios en blanco
                    String trimmedText = text.replaceAll(" ", "");
                    int trimmedLength = trimmedText.length();

                    // Verificar si se está ingresando una letra
                    if (Character.isLetter(e.getKeyChar())) {
                        // Verificar si se excede el límite de caracteres
                        if (trimmedLength >= 50) {
                            e.consume(); // Ignorar la letra
                        } else {
                            // Verificar si es el primer carácter o el carácter después de un espacio en blanco
                            if (length == 0 || (caretPosition > 0 && text.charAt(caretPosition - 1) == ' ')) {
                                // Convertir la letra a mayúscula
                                e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                            }
                        }
                    } else {
                        e.consume(); // Ignorar cualquier otro tipo de carácter
                    }
                }
            }
        });

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

    private DatosUsuario guardarUsuario() {
        Conexion sql = new Conexion();

        try (Connection connection = sql.conectamysql()) {
            String nombre = campoNombre.getText().trim();
            String correo = campoCorreo.getText().trim();
            String contrasena = new String(campoContrasena.getPassword());
            String rol = "general"; // Asumiendo que todos los nuevos usuarios serán 'general'

            String contrasenaEncriptada = BCrypt.hashpw(contrasena, BCrypt.gensalt());

            // Suponiendo que se elige una imagen de forma predeterminada
            String[] imagenes = {"imagen 1.jpg", "imagen 2.jpg", "imagen 3.jpg", "imagen 4.jpg", "imagen 5.jpg", "imagen 6.jpg", "imagen 7.jpg", "imagen 8.jpg", "imagen 9.jpg", "imagen 10.jpg"};
            String imagenSeleccionada = imagenes[new Random().nextInt(imagenes.length)];

            String query = "INSERT INTO usuarios (nombre, correo, contrasena, imagen, rol) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, correo);
                preparedStatement.setString(3, contrasenaEncriptada);
                preparedStatement.setString(4, imagenSeleccionada);
                //preparedStatement.setString(5, rol);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    mostrarDialogoPersonalizadoExito("Registro exitoso. El usuario se ha creado correctamente.", Color.decode("#263238"));

                    // Obtener el ID generado
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            //return new DatosUsuario(id, nombre, correo, contrasenaEncriptada, imagenSeleccionada, rol); // Devuelve los datos del usuario
                        }
                    }
                } else {
                    mostrarDialogoPersonalizadoError("Error al registrar el usuario. Inténtalo de nuevo.", Color.decode("#C62828"));
                }
            }
        } catch (SQLException e) {
            mostrarDialogoPersonalizadoError("Error al registrar el usuario: " + e.getMessage(), Color.decode("#C62828"));
        }
        return null;
    }

    private boolean usuarioExistente(String correo) {
        Conexion sql = new Conexion();
        Connection connection = sql.conectamysql();

        try {
            String query = "SELECT * FROM usuarios WHERE correo = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, correo);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Si resultSet.next() devuelve true, significa que ya existe un usuario con el mismo correo
            return resultSet.next();
        } catch (SQLException e) {
            mostrarDialogoPersonalizadoError("Error, cierra y vuelve a ejecutar", Color.decode("#C62828"));
            return false;
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

    private void mostrarContrasena() {
        // Cambiar el estado de visibilidad de la contraseña
        esVisible = !esVisible;

        // Establecer el modo de eco dependiendo del estado de visibilidad
        char modoEco = esVisible ? (char) 0 : defaultEchoChar; // Usa el carácter de eco por defecto

        campoContrasena.setEchoChar(modoEco);
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
        SignUp register = new SignUp();
        register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        register.setVisible(true);
    }
}
