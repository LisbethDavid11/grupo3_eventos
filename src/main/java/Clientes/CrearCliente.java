package Clientes;

import Objetos.Cliente;
import Objetos.Conexion;
import Objetos.Empleado;

import javax.swing.*;
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

public class CrearCliente extends JFrame{
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JFormattedTextField campoIdentidad;
    private JTextField campoTelefono;
    private JTextArea campoDomicilio;
    private JTextField[] campos = new JTextField[]{
            campoNombre,
            campoApellido,
            campoIdentidad,
            campoTelefono,
    };
    private JRadioButton radioMayorista;
    private JRadioButton radioAldetalle;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JPanel panel;
    private JLabel label1,label2,label3,label4,label5,label6,label7;
    private CrearCliente actual = this;
    private Connection mysql;
    private Conexion sql;

    public CrearCliente crearCliente = this;

    Color primaryColor = Color.decode("#263238"); // Gris azul oscuro
    Color lightColor = Color.decode("#37474f"); // Gris azul claro
    Color darkColor = Color.decode("#000a12"); // Gris azul más oscuro
    Color textColor = Color.WHITE; // Texto blanco

    public CrearCliente() {
        super("Crear Clientes");
        setSize(600,400);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();

        panel.setBackground(lightColor);

        campoDomicilio.setLineWrap(true);
        campoDomicilio.setWrapStyleWord(true);

        // Asignar nombres a los campos de texto
        campoNombre.setName("Nombre");
        campoApellido.setName("Apellido");
        campoIdentidad.setName("Identidad");
        campoTelefono.setName("Teléfono");

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
                if (texto.length() == 0 || texto.substring(caretPosition - 1, caretPosition).equals(" ")) {
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

        botonCancelar.setBackground(darkColor);
        botonCancelar.setForeground(textColor);
        botonGuardar.setBackground(darkColor);
        botonGuardar.setForeground(textColor);

        radioAldetalle.setBackground(lightColor);
        radioAldetalle.setForeground(textColor);
        radioMayorista.setBackground(lightColor);
        radioMayorista.setForeground(textColor);

        label1.setForeground(textColor);
        label2.setForeground(textColor);
        label3.setForeground(textColor);
        label4.setForeground(textColor);
        label5.setForeground(textColor);
        label6.setForeground(textColor);
        label7.setForeground(textColor);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioAldetalle);
        buttonGroup.add(radioMayorista);

        // No seleccionar ningún botón de radio por defecto
        buttonGroup.clearSelection();

        try {
            MaskFormatter dni = new MaskFormatter("####-####-#####");
            campoIdentidad.setFormatterFactory(new DefaultFormatterFactory(dni));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Cargar los iconos en blanco
        ImageIcon cancelIcon = new ImageIcon("cancel_icon_white.png");
        ImageIcon saveIcon = new ImageIcon("save_icon_white.png");
        ImageIcon updateIcon = new ImageIcon("update_icon_white.png");

        // Establecer los iconos en los botones
        botonCancelar.setIcon(cancelIcon);
        botonGuardar.setIcon(saveIcon);

        // Establecer el color de texto en blanco para los botones
        botonCancelar.setForeground(textColor);
        botonGuardar.setForeground(textColor);

        // Establecer el fondo oscuro para los botones
        botonCancelar.setBackground(darkColor);
        botonGuardar.setBackground(darkColor);

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaCliente cliente = new ListaCliente();
                cliente.setVisible(true);
                actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                // Verificar si ya existe un empleado con la misma identidad
                if (validarIdentidadExistente(campoIdentidad.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "La identidad ingresada ya está asociada a otro cliente", "Validación", JOptionPane.ERROR_MESSAGE);
                    return; // Detener la ejecución del método
                }

                // Verificar si ya existe un empleado con el mismo teléfono
                if (validarTelefonoExistente(campoTelefono.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "El teléfono ingresado ya está asociado a otro cliente", "Validación", JOptionPane.ERROR_MESSAGE);
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

                String dni = campoIdentidad.getText().trim();
                if (dni.length() != 15) {
                    validacion++;
                    mensaje += "Identidad\n";
                }

                if (campoTelefono.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Teléfono\n";
                }

                if (!radioMayorista.isSelected() && !radioAldetalle.isSelected()) {
                    validacion++;
                    mensaje += "Tipo de cliente\n";
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

                    if (!nombre.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                        JOptionPane.showMessageDialog(null, "El nombre de cliente debe tener mínimo 2 letras; y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de nombre de cliente no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String apellido = campoNombre.getText().trim();
                if (!apellido.isEmpty()) {
                    if (apellido.length() > 50) {
                        JOptionPane.showMessageDialog(null, "El apellido de cliente debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!apellido.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                        JOptionPane.showMessageDialog(null, "El apellido de cliente debe tener mínimo 2 letras; y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El apellido de nombre de cliente no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
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

                if (!radioAldetalle.isSelected() && !radioMayorista.isSelected()) {
                    validacion++;
                    mensaje += "Tipo de empleado\n";
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

                    Empleado empleado = new Empleado();
                    boolean esIdentidadValida = empleado.comprobarIdentidad(numerosIdentidad);
                    if (!esIdentidadValida) {
                        JOptionPane.showMessageDialog(null, "La identidad ingresada no es válida", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de identidad no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                guardar();

                ListaCliente listaClientes = new ListaCliente();
                listaClientes.setVisible(true);
                crearCliente.dispose();
                guardar();
            }
        });
    }

    public void guardar(){
        sql = new Conexion();
        mysql = sql.conectamysql();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + Cliente.nombreTabla + "(`nombre`, `apellido`, `identidad`, `telefono`, `domicilio`, `tipo_cliente`) VALUES(?,?,?,?,?,?)")) {

            // Verificar si ya existe un cliente con la misma identidad
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

            // No es necesario cerrar explícitamente la conexión, ya que se cerrará automáticamente al finalizar el bloque try-with-resources

            // Verificar si la ventana ListaCliente ya está abierta
            boolean listaClienteAbierta = false;
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof ListaCliente) {
                    listaClienteAbierta = true;
                    break;
                }
            }

            // Abrir la ventana ListaCliente solo si no está abierta
            if (!listaClienteAbierta) {
                ListaCliente cliente = new ListaCliente();
                cliente.setVisible(true);
            }

            actual.dispose();

            // Mostrar mensaje de éxito
            String nombreCompleto = campoNombre.getText() + " " + campoApellido.getText();

            // Mensaje personalizado
            System.out.println("Cliente " + nombreCompleto + " ha sido registrado exitosamente.");
            JOptionPane.showMessageDialog(null, "Cliente " + nombreCompleto + " ha sido registrado exitosamente.", "Éxito", JOptionPane.ERROR_MESSAGE);

        } catch (SQLException e) {

        }
    }

    private boolean validarTelefonoExistente(String telefono) {
        try {
            mysql = sql.conectamysql();
            String query = "SELECT COUNT(*) FROM empleados WHERE Telefono = ?";
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