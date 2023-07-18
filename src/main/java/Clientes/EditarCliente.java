package Clientes;
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

public class EditarCliente extends  JFrame{
    private JPanel panel1;
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoTelefono;
    private JFormattedTextField campoIdentidad;
    private JTextArea campoDomicilio;
    private JLabel label1,label2,label3,label4,label5,label6,label7;
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
        super("");
        setSize(600,480);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();

        tipoClienteGroup = new ButtonGroup();
        tipoClienteGroup.add(mayoristaRadioButton);
        tipoClienteGroup.add(alDetalleRadioButton);

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
            }
        });

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));

        // Color de texto para los JTextField
        Color textColor = Color.decode("#212121");

        // Cargar los iconos en blanco
        ImageIcon cancelIcon = new ImageIcon("cancel_icon_white.png");
        ImageIcon saveIcon = new ImageIcon("save_icon_white.png");
        ImageIcon updateIcon = new ImageIcon("update_icon_white.png");

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

        // Color de texto para el JTextArea
        campoDomicilio.setForeground(textColor);
        // Color de texto de los botones
        cancelarButton.setForeground(Color.WHITE);
        guardarButton.setForeground(Color.WHITE);

        // Color de fondo de los botones
        cancelarButton.setBackground(darkColorCyan);
        guardarButton.setBackground(darkColorAqua);

        cancelarButton.setFocusPainted(false);
        guardarButton.setFocusPainted(false);

        // Aplica el margen al botón
        cancelarButton.setBorder(margin);
        guardarButton.setBorder(margin);

        alDetalleRadioButton.setBackground(Color.decode("#F5F5F5"));
        alDetalleRadioButton.setForeground(textColor);
        mayoristaRadioButton.setBackground(Color.decode("#F5F5F5"));
        mayoristaRadioButton.setForeground(textColor);

        label1.setForeground(textColor);
        label2.setForeground(textColor);
        label3.setForeground(textColor);
        label4.setForeground(textColor);
        label5.setForeground(textColor);
        label6.setForeground(textColor);
        label7.setForeground(textColor);

        campoDomicilio.setBackground(new Color(215, 215, 215));

        // Establecer los iconos en los botones
        cancelarButton.setIcon(cancelIcon);
        guardarButton.setIcon(saveIcon);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        label1.setBorder(marginTitulo);

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(label1.getFont().getName(), label1.getFont().getStyle(), 18);
        label1.setFont(fontTitulo);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginDNI = new EmptyBorder(10, 0, 10, 0);
        campoIdentidad.setBorder(marginDNI);

        try {
            MaskFormatter dni = new MaskFormatter("####-####-#####");
            campoIdentidad.setFormatterFactory(new DefaultFormatterFactory(dni));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                // Asume que tienes el ID del cliente disponible como una variable llamada clienteId
                Integer clienteId = id; // Utiliza la variable id de la clase

                // Verificar si ya existe un cliente con la misma identidad (ignorando el cliente actual)
                if (validarIdentidadExistente(campoIdentidad.getText().trim(), clienteId)) {
                    JOptionPane.showMessageDialog(null, "La identidad ingresada ya está asociada a otro cliente", "Validación", JOptionPane.ERROR_MESSAGE);
                    return; // Detener la ejecución del método
                }

                // Verificar si ya existe un cliente con el mismo teléfono (ignorando el cliente actual)
                if (validarTelefonoExistente(campoTelefono.getText().trim(), clienteId)) {
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

                if (campoTelefono.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Teléfono\n";
                }

                if (!mayoristaRadioButton.isSelected() && !alDetalleRadioButton.isSelected()) {
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

                    if (!nombre.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                        JOptionPane.showMessageDialog(null, "El nombre de cliente debe tener mínimo 2 letras; y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
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

                    if (!apellido.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                        JOptionPane.showMessageDialog(null, "El apellido de cliente debe tener mínimo 2 letras; y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
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
            String identidad = resultSet.getString("identidad");
            campoIdentidad.setValue(identidad);

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
            System.out.println("Cliente " + nombreCompleto + " ha sido actualizado exitosamente.");
            JOptionPane.showMessageDialog(null, "Cliente " + nombreCompleto + " ha sido actualizado exitosamente.", "Éxito", JOptionPane.DEFAULT_OPTION);
        } catch (SQLException e) {
            String mensajeError = "Error al guardar el cliente: " + e.getMessage();
            JOptionPane.showMessageDialog(null, "No se pudo realizar la actualización del cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validarTelefonoExistente(String telefono, Integer clienteId) {
        try {
            mysql = sql.conectamysql();
            String query = "SELECT COUNT(*) FROM clientes WHERE telefono = ? AND id != ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, telefono);
            statement.setInt(2, clienteId);
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

    private boolean validarIdentidadExistente(String identidad, Integer clienteId) {
        try {
            mysql = sql.conectamysql();

            String query = "SELECT COUNT(*) FROM clientes WHERE Identidad = ? AND id != ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, identidad);
            statement.setInt(2, clienteId);

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