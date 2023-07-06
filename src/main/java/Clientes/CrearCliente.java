package Clientes;

import Objetos.Cliente;
import Objetos.Conexion;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
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
    private CrearCliente actual = this;
    private Connection mysql;
    private Conexion sql;

    public CrearCliente() {
        super("Crear Clientes");
        setSize(600,600);
        setLocationRelativeTo(null);
        setContentPane(panel);

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

                // Verificar si se están ingresando más de dos espacios en blanco seguidos
                if (e.getKeyChar() == ' ' && texto.endsWith(" ")) {
                    e.consume(); // Ignorar el evento y no agregar el espacio en blanco adicional
                    return;
                }

                // Convertir la primera letra en mayúscula
                if (texto.length() > 0) {
                    String primeraLetra = texto.substring(0, 1).toUpperCase();
                    String restoTexto = texto.substring(1);
                    texto = primeraLetra + restoTexto;
                    campoDomicilio.setText(texto);
                }

                Conexion.soloLetra(e, texto.length(), 200, caretPosition);
            }
        });

        campoTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String telefono = campoTelefono.getText();
                if (!Character.isDigit(e.getKeyChar()) || telefono.length() >= 8) {
                    e.consume(); // Evita que se escriban caracteres no numéricos o se exceda la longitud
                }
            }
        });

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
                StringBuilder mensaje = new StringBuilder("Faltó ingresar:\n");
                boolean camposVacios = false;

                // Verificar los campos de nombre, apellido, identidad, telefono, tipo de cliente y domicilio
                for (JTextField campo : campos) {
                    if (campo.getText().trim().isEmpty()) {
                        camposVacios = true;
                        mensaje.append(campo.getName()).append("\n");
                    }
                }

                // Verificar que el campo identidad no esté vacio
                String identidad = campoIdentidad.getText().trim();
                if (identidad.length() != 15) {
                    camposVacios = true;
                    mensaje.append("Identidad").append("\n");
                }

                // Verificar que el campo tipo de cliente no esté vacio
                if (!radioAldetalle.isSelected() && !radioMayorista.isSelected()) {
                    camposVacios = true;
                    mensaje.append("Tipo de Cliente").append("\n");
                }

                // Verificar que el campo domicilio no esté vacio
                String domicilio = campoDomicilio.getText().trim();
                if (!domicilio.isEmpty()) {
                    if (domicilio.length() < 2 || domicilio.length() > 200) {
                        JOptionPane.showMessageDialog(null, "El domicilio debe tener entre 2 y 200 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    camposVacios = true;
                    mensaje.append("Domicilio").append("\n");
                }

                // Mostrar mensaje de campos vacíos si es necesario
                if (camposVacios) {
                    JOptionPane.showMessageDialog(null, mensaje.toString(), "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar el campo de nombre, otras validaciones extras
                String nombre = campoNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 50) {
                        JOptionPane.showMessageDialog(null, "El nombre debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!nombre.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                        JOptionPane.showMessageDialog(null, "El nombre debe tener mínimo 2 letras; y máximo 1 espacio entre palabras. ", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Verificar el campo de apellido, otras validaciones extras
                String apellido = campoApellido.getText().trim();
                if (!apellido.isEmpty()) {
                    if (apellido.length() > 50) {
                        JOptionPane.showMessageDialog(null, "El apellido debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!apellido.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                        JOptionPane.showMessageDialog(null, "El apellido debe tener mínimo 2 letras; y máximo 1 espacio entre palabras. ", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Validar el formato de identidad solo si no está vacío, otras validaciones extras
                if (!identidad.isEmpty()) {
                    // Verificar la longitud de la identidad
                    if (identidad.length() != 15) {
                        JOptionPane.showMessageDialog(null, "La identidad debe tener 15 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Verificar que la identidad tenga el formato correcto
                    if (!identidad.matches("\\d{4}-\\d{4}-\\d{5}")) {
                        JOptionPane.showMessageDialog(null, "La identidad debe tener el formato ####-####-#####", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Quitar los guiones de la identidad para obtener solo los números
                    String numerosIdentidad = identidad.replace("-", "");

                    // Utilizar el método comprobarIdentidad de la clase Cliente
                    Cliente cliente = new Cliente();
                    boolean esIdentidadValida = cliente.comprobarIdentidad(numerosIdentidad);
                    if (!esIdentidadValida) {
                        JOptionPane.showMessageDialog(null, "La identidad ingresada no es válida", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Verificar el campo de telefono, otras validaciones extras
                String telefono = campoTelefono.getText().trim();
                if (!telefono.isEmpty()) {
                    if (telefono.length() != 8) {
                        JOptionPane.showMessageDialog(null, "El teléfono debe tener 8 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!telefono.matches("[389]\\d+")) {
                        JOptionPane.showMessageDialog(null, "El teléfono debe comenzar con 3, 8 o 9", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

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
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                // Mostrar mensaje de error indicando que la identidad ya está registrada
                String mensajeError = "La identidad " + identidad + " ya está registrada para el siguiente cliente:\n" +
                        "- Nombre: " + resultSet.getString("nombre") + "\n" +
                        "- Apellido: " + resultSet.getString("apellido") + "\n" +
                        "- Teléfono: " + resultSet.getString("telefono");
                JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
                return; // Detener el proceso de guardado
            }

            preparedStatement.setString(1, campoNombre.getText());
            preparedStatement.setString(2, campoApellido.getText());
            preparedStatement.setString(3, campoIdentidad.getText());
            preparedStatement.setString(4, campoTelefono.getText());
            preparedStatement.setString(5, campoDomicilio.getText());
            preparedStatement.setString(6, radioAldetalle.isSelected() ? "Al Detalle" : "Mayorista");

            preparedStatement.executeUpdate();

            // No es necesario cerrar explícitamente la conexión, ya que se cerrará automáticamente al finalizar el bloque try-with-resources
            ListaCliente cliente = new ListaCliente();
            cliente.setVisible(true);
            actual.dispose();

            // Mostrar mensaje de éxito
            String nombreCompleto = campoNombre.getText() + " " + campoApellido.getText();

            // Mensaje personalizado
            System.out.println("Cliente " + nombreCompleto + " ha sido registrado exitosamente.");
            JOptionPane.showMessageDialog(null, "Cliente " + nombreCompleto + " ha sido registrado exitosamente.", "Exito", JOptionPane.ERROR_MESSAGE);

        } catch (SQLException e) {
            String mensajeError = "Error al guardar el cliente: " + e.getMessage();
            JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}