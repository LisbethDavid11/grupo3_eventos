/**
 * CrearClienteAlquiler.java
 *
 * Crear Cliente Alquiler
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-05
 */

package Alquileres;

import Objetos.Cliente;
import Objetos.Conexion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class CrearClienteAlquiler extends JFrame{
    // Campos de entrada
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoTelefono;
    private JFormattedTextField campoIdentidad;

    // Área de texto
    private JTextArea campoDomicilio;

    // Botones y radio botones
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JButton botonLimpiar;
    private JRadioButton radioMayorista;
    private JRadioButton radioAldetalle;

    // Paneles
    private JPanel panel;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

    // Etiquetas
    private JLabel label0;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;

    // Referencias y conexiones
    private CrearClienteAlquiler actual = this;
    private CrearClienteAlquiler crearCliente = this;
    private Connection mysql;
    private Conexion sql;
    private JFrame ventanaAnterior;

    // Colores y fuentes
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 17);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

    Color primaryColorCyan = new Color(0, 188, 212); // Cyan primario
    Color lightColorCyan = new Color(77, 208, 225); // Cyan claro
    Color darkColorCyan = new Color(0, 151, 167); // Cyan oscuro

    Color primaryColorAqua = new Color(0, 150, 136); // Aqua primario
    Color lightColorAqua = new Color(77, 182, 172); // Aqua claro
    Color darkColorAqua = new Color(0, 121, 107); // Aqua oscuro

    Color primaryColorRosado = new Color(233, 30, 99); // Rosado primario
    Color lightColorRosado = new Color(240, 98, 146); // Rosado claro
    Color darkColorRosado = new Color(194, 24, 91); // Rosado oscuro

    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    Color textColor = Color.decode("#212121");

    // Crea un margen de 15 píxeles desde el borde inferior
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public CrearClienteAlquiler(CrearAlquileres ventanaAnterior) {
        super("");
        setSize(600,480);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();
        this.ventanaAnterior = ventanaAnterior;

        campoDomicilio.setLineWrap(true);
        campoDomicilio.setWrapStyleWord(true);

        campoNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoNombre.getText();
                int length = text.length();
                int caretPosition = campoNombre.getCaretPosition();

                // Verificar si se está ingresando un espacio en blanco
                if (e.getKeyChar() == ' ') {
                    // Verificar si es el primer espacio en blanco
                    if (length == 0 || caretPosition == 0) {
                        e.consume(); // Ignorar el espacio en blanco
                    } else {
                        // Verificar si ya hay un espacio en blanco en el texto
                        boolean hasSpace = text.contains(" ");
                        if (hasSpace) {
                            e.consume(); // Ignorar el espacio en blanco adicional
                        }
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

        campoApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoApellido.getText();
                int length = text.length();
                int caretPosition = campoApellido.getCaretPosition();

                // Verificar si se está ingresando un espacio en blanco
                if (e.getKeyChar() == ' ') {
                    // Verificar si es el primer espacio en blanco
                    if (length == 0 || caretPosition == 0) {
                        e.consume(); // Ignorar el espacio en blanco
                    } else {
                        // Verificar si ya hay un espacio en blanco en el texto
                        boolean hasSpace = text.contains(" ");
                        if (hasSpace) {
                            e.consume(); // Ignorar el espacio en blanco adicional
                        }
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

        campoTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String telefono = campoTelefono.getText();
                if (!Character.isDigit(e.getKeyChar()) || telefono.length() >= 8) {
                    e.consume(); // Evita que se escriban caracteres no numéricos o se exceda la longitud
                }

                if (telefono.length() == 0 && (e.getKeyChar() != '2' && e.getKeyChar() != '3' && e.getKeyChar() != '8' && e.getKeyChar() != '9')) {
                    e.consume(); // Evita que se ingrese un dígito inválido como primer dígito
                }
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioAldetalle);
        buttonGroup.add(radioMayorista);
        buttonGroup.clearSelection();

        panel.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));

        campoDomicilio.setForeground(textColor);

        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.setBackground(darkColorRed);
        botonLimpiar.setFocusPainted(false);
        botonLimpiar.setBorder(margin);

        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBackground(darkColorBlue);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setBorder(margin);

        botonGuardar.setForeground(Color.WHITE);
        botonGuardar.setBackground(darkColorAqua);
        botonGuardar.setFocusPainted(false);
        botonGuardar.setBorder(margin);

        radioAldetalle.setForeground(textColor);
        radioAldetalle.setBackground(panel.getBackground());
        radioAldetalle.setFocusPainted(false);

        radioMayorista.setForeground(textColor);
        radioMayorista.setBackground(panel.getBackground());
        radioMayorista.setFocusPainted(false);

        label0.setForeground(textColor);
        label1.setForeground(textColor);
        label2.setForeground(textColor);
        label3.setForeground(textColor);
        label4.setForeground(textColor);
        label5.setForeground(textColor);

        campoDomicilio.setBackground(new Color(215, 215, 215));

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginDNI = new EmptyBorder(10, 0, 10, 0);
        campoIdentidad.setBorder(marginDNI);

        label1.setBorder(margin);
        label0.setFont(fontTitulo);

        try {
            MaskFormatter dni = new MaskFormatter("####-####-#####");
            campoIdentidad.setFormatterFactory(new DefaultFormatterFactory(dni));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ventanaAnterior.setVisible(true); // Muestra la ventana anterior
                actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                // Verificar si ya existe un cliente con el mismo teléfono
                if (validarTelefonoExistente(campoTelefono.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "El teléfono ingresado ya está asociado a otro cliente", "Validación", JOptionPane.ERROR_MESSAGE);
                    return; // Detener la ejecución del método
                }

                // Verificar si ya existe un cliente con la misma identidad
                if (validarIdentidadExistente(campoIdentidad.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "La identidad ingresada ya está asociada a otro cliente", "Validación", JOptionPane.ERROR_MESSAGE);
                    return; // Detener la ejecución del método
                }

                if (campoNombre.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Nombres\n";
                }

                if (campoApellido.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Apellidos\n";
                }

                if (campoTelefono.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Teléfono\n";
                }

                if (!radioMayorista.isSelected() && !radioAldetalle.isSelected()) {
                    validacion++;
                    mensaje += "Tipo de cliente\n";
                }

                String dni = campoIdentidad.getText().trim();
                if (dni.length() != 15) {
                    validacion++;
                    mensaje += "Identidad\n";
                }

                if (campoDomicilio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Domicilio\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombre = campoNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 50) {
                        JOptionPane.showMessageDialog(null, "El nombre de cliente debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!nombre.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]{2,}(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?")) {
                        JOptionPane.showMessageDialog(null, "El nombre de cliente debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de nombre de cliente no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String apellido = campoApellido.getText().trim();
                if (!apellido.isEmpty()) {
                    if (apellido.length() > 50) {
                        JOptionPane.showMessageDialog(null, "El apellido de cliente debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!apellido.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]{2,}(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?")) {
                        JOptionPane.showMessageDialog(null, "El apellido de cliente debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El apellido del cliente no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String telefono = campoTelefono.getText().trim();
                if (!telefono.isEmpty()) {
                    if (telefono.length() != 8) {
                        JOptionPane.showMessageDialog(null, "El número de teléfono debe tener exactamente 8 dígitos", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!telefono.matches("[2389]\\d{7}")) {
                        JOptionPane.showMessageDialog(null, "El número de teléfono debe empezar con 2, 3, 8 o 9", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de teléfono no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String identidad = campoIdentidad.getText().trim();
                if (!identidad.isEmpty()) {
                    if (identidad.length() != 15) {
                        JOptionPane.showMessageDialog(null, "La identidad debe tener 15 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!identidad.matches("\\d{4}-\\d{4}-\\d{5}")) {
                        JOptionPane.showMessageDialog(null, "La identidad debe tener el formato ####-####-#####", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String numerosIdentidad = identidad.replace("-", "");
                    Cliente cliente = new Cliente();
                    boolean esIdentidadValida = cliente.comprobarIdentidad(numerosIdentidad);
                    if (!esIdentidadValida) {
                        JOptionPane.showMessageDialog(null, "La identidad ingresada no es válida", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de identidad no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String domicilio = campoDomicilio.getText().trim();
                if (!domicilio.isEmpty()) {
                    if (domicilio.length() < 2 || domicilio.length() > 200) {
                        JOptionPane.showMessageDialog(null, "El domicilio debe tener entre 2 y 200 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de domicilio no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea guardar la información del cliente?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    guardar();

                    // Verifica si la ventana anterior es CrearVenta y actualiza los clientes
                    if (ventanaAnterior instanceof CrearAlquileres) {
                        CrearAlquileres ventanaVenta = (CrearAlquileres) ventanaAnterior;
                    }

                    ventanaAnterior.setVisible(true); // Muestra la ventana anterior
                    ventanaAnterior.cargarClientes();
                    actual.dispose(); // Cierra la ventana CrearCliente
                }

            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btnYes = new JButton("Sí");
                JButton btnNo = new JButton("No");

                // Personaliza los botones aquí
                btnYes.setBackground(darkColorAqua);
                btnNo.setBackground(darkColorRed);

                // Personaliza los fondos de los botones aquí
                btnYes.setForeground(Color.WHITE);
                btnNo.setForeground(Color.WHITE);

                // Elimina el foco
                btnYes.setFocusPainted(false);
                btnNo.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Estás seguro de que deseas limpiar los datos del cliente?",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        new Object[]{}, // no options
                        null
                );

                // Crea un JDialog
                JDialog dialog = optionPane.createDialog("Limpiar");

                // Añade ActionListener a los botones
                btnYes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        campoNombre.setText("");
                        campoApellido.setText("");
                        campoTelefono.setText("");
                        campoIdentidad.setText("");
                        campoDomicilio.setText("");
                        buttonGroup.clearSelection();
                        dialog.dispose();
                    }
                });

                btnNo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Acciones para el botón No
                        // No se hace nada, sólo se cierra el diálogo
                        dialog.dispose();
                    }
                });

                // Añade los botones al JOptionPane
                optionPane.setOptions(new Object[]{btnYes, btnNo});

                // Muestra el diálogo
                dialog.setVisible(true);
            }
        });
    }

    // Método para guardar los datos del cliente
    public void guardar(){
        sql = new Conexion();
        mysql = sql.conectamysql();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + Cliente.nombreTabla + "(`nombre`, `apellido`, `identidad`, `telefono`, `domicilio`, `tipo_cliente`) VALUES(?,?,?,?,?,?)")) {

            String identidad = campoIdentidad.getText().trim();
            String selectQuery = "SELECT * FROM " + Cliente.nombreTabla + " WHERE identidad = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
            selectStatement.setString(1, identidad);

            preparedStatement.setString(1, campoNombre.getText());
            preparedStatement.setString(2, campoApellido.getText());
            preparedStatement.setString(3, campoIdentidad.getText());
            preparedStatement.setString(4, campoTelefono.getText());
            preparedStatement.setString(5, campoDomicilio.getText());
            preparedStatement.setString(6, radioAldetalle.isSelected() ? "Al Detalle" : "Mayorista");
            preparedStatement.executeUpdate();

            String nombreCompleto = campoNombre.getText() + " " + campoApellido.getText();
            System.out.println("Cliente " + nombreCompleto + " ha sido registrado exitosamente.");
            JOptionPane.showMessageDialog(null, "Cliente " + nombreCompleto + " ha sido registrado exitosamente.", "Éxito", JOptionPane.DEFAULT_OPTION);
        } catch (SQLException e) {
            String mensajeError = "Error al guardar el cliente: " + e.getMessage();
            JOptionPane.showMessageDialog(null, "No se pudo realizar el registro del cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para validar si ya existe un cliente registrado con ese teléfono
    private boolean validarTelefonoExistente(String telefono) {
        try {
            mysql = sql.conectamysql();
            String query = "SELECT COUNT(*) FROM clientes WHERE telefono = ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, telefono);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mysql != null) {
                try {
                    mysql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    // Método para validar si ya existe un cliente registrado con esa identidad
    private boolean validarIdentidadExistente(String identidad) {
        try {
            mysql = sql.conectamysql();
            String query = "SELECT COUNT(*) FROM clientes WHERE Identidad = ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, identidad);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mysql != null) {
                try {
                    mysql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}