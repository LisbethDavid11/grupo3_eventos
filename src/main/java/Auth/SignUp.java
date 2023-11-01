package Auth;
import SubMenu.SubMenu;

import java.sql.*;

import Objetos.Conexion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import org.mindrot.jbcrypt.BCrypt;

public class SignUp extends javax.swing.JFrame {
    private Connection mysql;
    private Conexion sql;
 
    public SignUp() {
        initComponents();
    }

    private DatosUsuario guardarUsuario() {
        Conexion sql = new Conexion();

        try (Connection connection = sql.conectamysql()) {
            String nombre = jTextField1.getText().trim();
            String correo = jTextField2.getText().trim();
            String contrasena = new String(jPasswordField1.getPassword());
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
                preparedStatement.setString(5, rol);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    mostrarDialogoPersonalizadoExito("Registro exitoso. El usuario se ha creado correctamente.", Color.decode("#263238"));

                    // Obtener el ID generado
                    try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int id = generatedKeys.getInt(1);
                            return new DatosUsuario(id, nombre, correo, contrasenaEncriptada, imagenSeleccionada, rol); // Devuelve los datos del usuario
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("REGISTRO");
        setPreferredSize(new java.awt.Dimension(800, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        // Crea un ImageIcon con la imagen
        // javax.swing.ImageIcon icon = new javax.swing.ImageIcon("img/flor.png");
        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Eventos Chelsea");

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Copyright © Todos los derechos reservados.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(137, 137, 137)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(jLabel3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jLabel2)))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 108, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(64, 64, 64))
        );

        jPanel1.add(jPanel2);
        jPanel2.setBounds(0, 0, 400, 500);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setBackground(Color.darkGray);
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setText("REGISTRAR");

        jLabel5.setBackground(new java.awt.Color(102, 102, 102));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Nombre completo");

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(102, 102, 102));

        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = jTextField1.getText();
                int length = text.length();
                int caretPosition = jTextField1.getCaretPosition();

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

        jLabel6.setBackground(new java.awt.Color(102, 102, 102));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Correo electrónico");

        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(102, 102, 102));

        jLabel7.setBackground(new java.awt.Color(102, 102, 102));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("Contraseña");

        jPasswordField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPasswordField1.setForeground(new java.awt.Color(102, 102, 102));

        jLabel8.setText("¿Ya tienes una cuenta?");

        jButton1.setBackground(new java.awt.Color(0, 102, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setFocusPainted(false);
        jButton1.setText("REGISTRAR");

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jTextField1.getText().trim().isEmpty() && jPasswordField1.getText().trim().isEmpty() && jTextField2.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El nombre completo, correo electrónico y la contraseña no puede estar vacíos.", Color.decode("#C62828"));
                    return;
                }

                if (jTextField1.getText().trim().isEmpty() && jTextField2.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El nombre completo y el correo electrónico no puede estar vacíos.", Color.decode("#C62828"));
                    return;
                }

                if (jTextField2.getText().trim().isEmpty() && jPasswordField1.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El correo electrónico y la contraseña no puede estar vacíos.", Color.decode("#C62828"));
                    return;
                }

                if (jTextField1.getText().trim().isEmpty() && jPasswordField1.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El nombre completo y la contraseña no puede estar vacíos.", Color.decode("#C62828"));
                    return;
                }

                if (jTextField1.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El nombre completo no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                if (jTextField2.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("El correo electrónico no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                if (jPasswordField1.getText().trim().isEmpty()) {
                    mostrarDialogoPersonalizadoError("La contraseña no puede estar vacía.", Color.decode("#C62828"));
                    return;
                }

                String nombre = jTextField1.getText().trim();
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

                String correoElectronico = jTextField2.getText().trim();
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
                String correo = jTextField2.getText().trim();
                if (usuarioExistente(correo)) {
                    mostrarDialogoPersonalizadoError("El correo electrónico ya está registrado. Utiliza otro correo.", Color.decode("#C62828"));
                    return;
                }

                String contrasenia = jPasswordField1.getText().trim();
                if (contrasenia.length() < 8) {
                    mostrarDialogoPersonalizadoError("La contraseña debe tener al menos 8 caracteres.", Color.decode("#C62828"));
                    return;
                }

                JButton btnSave = new JButton("Sí");
                JButton btnCancel = new JButton("No");

                // Personaliza los botones aquí
                btnSave.setBackground(new java.awt.Color(0, 102, 102));
                btnCancel.setBackground(new java.awt.Color( 244, 67, 54));

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
                        DatosUsuario datosUsuario = guardarUsuario();
                        if (datosUsuario != null) {
                            SesionUsuario sesion = SesionUsuario.getInstance();
                            sesion.setIdUsuario(datosUsuario.getId());
                            sesion.setNombreUsuario(datosUsuario.getNombre());
                            sesion.setCorreoUsuario(datosUsuario.getCorreo());
                            sesion.setImagenUsuario(datosUsuario.getImagen());
                            sesion.setRolUsuario(datosUsuario.getRol());

                            SubMenu menu = new SubMenu();
                            menu.setIdUsuarioActual(datosUsuario.getId());
                            menu.setNombreUsuario(datosUsuario.getNombre());
                            menu.setImagenUsuario(datosUsuario.getImagen());
                            menu.setVisible(true);
                            dispose(); // Cierra el formulario actual
                        }
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

        jButton2.setBackground(new java.awt.Color(244, 67, 54));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setFocusPainted(false);
        jButton2.setText("INICIAR SESIÓN");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jLabel4))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel5)
                                .addComponent(jTextField1)
                                .addComponent(jLabel6)
                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                                .addComponent(jLabel7)
                                .addComponent(jPasswordField1))
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addGap(29, 29, 29)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3);
        jPanel3.setBounds(400, 0, 400, 500);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 113, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 126, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null); 
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;

}
