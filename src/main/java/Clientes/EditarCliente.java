package Clientes;

import Objetos.Cliente;
import Objetos.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EditarCliente extends  JFrame{

    private JPanel panel1;
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoTelefono;
    private JFormattedTextField campoIdentidad;
    private JTextArea campoDomicilio;

    private JRadioButton mayoristaRadioButton;
    private JRadioButton alDetalleRadioButton;
    private ButtonGroup tipoClienteGroup;
    private JButton cancelarButton;
    private JButton guardarButton;

    private final EditarCliente actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;
    private JTextField[] campos = new JTextField[]{
            campoNombre,
            campoApellido,
            campoIdentidad,
            campoTelefono,
    };
    public EditarCliente(int id) {
        super("Editar Registro de los Clientes");
        setSize(600,600);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();

        tipoClienteGroup = new ButtonGroup();
        tipoClienteGroup.add(mayoristaRadioButton);
        tipoClienteGroup.add(alDetalleRadioButton);


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

        campoIdentidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String identidad = campoIdentidad.getText();
                if (!Character.isDigit(e.getKeyChar()) || identidad.length() >= 15) {
                    e.consume(); // Evita que se escriban caracteres no numéricos o se exceda la longitud
                }
            }
        });



        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaCliente indexCliente = new ListaCliente();
                indexCliente.setVisible(true);
                actual.dispose();

            }
        });

        guardarButton.addActionListener(new ActionListener() {
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
                if (!alDetalleRadioButton.isSelected() && !mayoristaRadioButton.isSelected()) {
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
                ListaCliente indexCliente = new ListaCliente();
                indexCliente.setVisible(true);
                actual.dispose();
            }
        });
    }
    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        mayoristaRadioButton.setText("Mayorista");
        alDetalleRadioButton.setText("Al Detalle");

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM " + Cliente.nombreTabla + " WHERE id = ?;");
            statement.setInt(1, this.id); // Se corrige el índice del parámetro
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            campoNombre.setText(resultSet.getString(2));
            campoApellido.setText(resultSet.getString(3));
            campoIdentidad.setText(resultSet.getString(4));
            campoTelefono.setText(resultSet.getString(5));
            campoDomicilio.setText(resultSet.getString(6));

            String tipoCliente = resultSet.getString(7);

            if (tipoCliente.equals("Mayorista")) {

                mayoristaRadioButton.setText("Mayorista");
                mayoristaRadioButton.setSelected(true);

                alDetalleRadioButton.setText("Al Detalle");
                alDetalleRadioButton.setSelected(false);

            } else if (tipoCliente.equals("Al Detalle")) {

                mayoristaRadioButton.setText("Mayorista");
                mayoristaRadioButton.setSelected(false);

                alDetalleRadioButton.setText("Al Detalle");
                alDetalleRadioButton.setSelected(true);

            }

            System.out.println(statement.execute());

        } catch (SQLException error) {
            //mensaje de error
            System.out.println(error.getMessage());
        }
    }

    private void guardar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        String tipoCliente;
        if (mayoristaRadioButton.isSelected()) {
            tipoCliente = "Mayorista";
        } else {
            tipoCliente = "Al Detalle";
        }

        try {
            // Verificar si ya existe un cliente con la misma identidad
            String identidad = campoIdentidad.getText().trim();
            String selectQuery = "SELECT * FROM " + Cliente.nombreTabla + " WHERE identidad = ? AND id <> ?";
            PreparedStatement selectStatement = mysql.prepareStatement(selectQuery);
            selectStatement.setString(1, identidad);
            selectStatement.setInt(2, this.id);
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

            // Actualizar cliente
            PreparedStatement statement = mysql.prepareStatement("UPDATE " + Cliente.nombreTabla + " SET `nombre` = ?, `apellido` = ?, `identidad` = ?, `telefono` = ?, `domicilio` = ?, `tipo_cliente` = ? WHERE id = ?");
            statement.setString(1, campoNombre.getText());
            statement.setString(2, campoApellido.getText());
            statement.setString(3, campoIdentidad.getText());
            statement.setString(4, campoTelefono.getText());
            statement.setString(5, campoDomicilio.getText());
            statement.setString(6, tipoCliente);
            statement.setInt(7, this.id);

            System.out.println(statement.execute());

            // Mostrar mensaje de éxito
            String nombreCompleto = campoNombre.getText() + " " + campoApellido.getText();
            JOptionPane.showMessageDialog(null, "Cliente " + nombreCompleto + " ha sido actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            String mensajeError = "Error al guardar el cliente: " + e.getMessage();
            JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }






}