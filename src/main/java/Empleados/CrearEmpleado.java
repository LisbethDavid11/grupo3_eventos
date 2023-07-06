package Empleados;

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

public class CrearEmpleado extends JFrame {
    public JTextField campoNombres;
    public JTextField campoApellidos;
    public JTextField campoEdad;
    public JTextArea campoDireccion;
    public JButton cancelarButton;
    public JButton guardarButton;
    private JPanel panel1;
    public JFormattedTextField campoIdentidad;
    public JRadioButton femeninoRadioButton;
    public JRadioButton masculinoRadioButton;
    public JTextField campoCorreo;
    public JTextField campoTelefono;
    public JTextField campoContactoEmergencia;
    public JRadioButton temporalRadioButton;
    public JRadioButton permanenteRadioButton;
    public JTextField campoNombreContacto;
    private JLabel lbl3;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl0;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel lbl7;
    private JLabel lbl8;
    private JLabel lbl9;
    private JLabel lbl10;
    private JLabel lbl11;
    private JLabel lbl12;
    private JLabel lbl13;

    private Conexion sql;
    private Connection mysql;
    public CrearEmpleado crearEmpleado = this;
    public ButtonGroup grupoGenero;
    public ButtonGroup grupoTipo;

    // Colores personalizados
    Color primaryColor = Color.decode("#263238"); // Gris azul oscuro
    Color lightColor = Color.decode("#37474f"); // Gris azul claro
    Color darkColor = Color.decode("#000a12"); // Gris azul más oscuro
    Color textColor = Color.WHITE; // Texto blanco

    private JTextField[] campos = {
            campoIdentidad,
            campoNombres,
            campoApellidos,
            campoEdad,
            campoCorreo,
            campoTelefono,
            campoContactoEmergencia
    };

    public CrearEmpleado() {
        super("Crear Empleados");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();

        // Color de fondo
        panel1.setBackground(lightColor);

        campoDireccion.setLineWrap(true);
        campoDireccion.setWrapStyleWord(true);

        grupoGenero = new ButtonGroup();
        grupoGenero.add(femeninoRadioButton);
        grupoGenero.add(masculinoRadioButton);
        grupoGenero.clearSelection();

        grupoTipo = new ButtonGroup();
        grupoTipo.add(temporalRadioButton);
        grupoTipo.add(permanenteRadioButton);
        grupoTipo.clearSelection();

        try {
            MaskFormatter formatoIdentidad = new MaskFormatter("####-####-#####");
            campoIdentidad.setFormatterFactory(new DefaultFormatterFactory(formatoIdentidad));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        // Asignar nombres a los campos de texto
        campoNombres.setName("Nombre");
        campoApellidos.setName("Apellido");
        campoIdentidad.setName("Identidad");
        campoTelefono.setName("Teléfono");

        campoNombres.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoNombres.getText();
                int length = text.length();
                int caretPosition = campoNombres.getCaretPosition();

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

        campoApellidos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoApellidos.getText();
                int length = text.length();
                int caretPosition = campoApellidos.getCaretPosition();

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

        campoEdad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String edad = campoEdad.getText();
                if (!Character.isDigit(e.getKeyChar()) || edad.length() >= 2) {
                    e.consume(); // Evita que se escriban caracteres no numéricos o se exceda la longitud
                }

                // Verificar restricciones adicionales para el primer y segundo dígito
                if (edad.length() == 0 && (e.getKeyChar() < '1' || e.getKeyChar() > '6')) {
                    e.consume(); // Evita que se ingrese un dígito inválido como primer dígito
                } else if (edad.length() == 1) {
                    if (edad.charAt(0) == '1' && (e.getKeyChar() != '8' && e.getKeyChar() != '9')) {
                        e.consume(); // Evita que se ingrese un dígito inválido como segundo dígito si el primer dígito es 1
                    } else if (edad.charAt(0) == '6' && (e.getKeyChar() != '0')) {
                        e.consume(); // Evita que se ingrese un dígito inválido como segundo dígito si el primer dígito es 6
                    }
                }
            }
        });

        campoDireccion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String texto = campoDireccion.getText();
                int caretPosition = campoDireccion.getCaretPosition();

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

        campoNombreContacto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoNombreContacto.getText();
                int length = text.length();
                int caretPosition = campoNombreContacto.getCaretPosition();

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

        campoContactoEmergencia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String telefono = campoContactoEmergencia.getText();
                if (!Character.isDigit(e.getKeyChar()) || telefono.length() >= 8) {
                    e.consume(); // Evita que se escriban caracteres no numéricos o se exceda la longitud
                }

                if (telefono.length() == 0 && (e.getKeyChar() != '2' && e.getKeyChar() != '3' && e.getKeyChar() != '8' && e.getKeyChar() != '9')) {
                    e.consume(); // Evita que se ingrese un dígito inválido como primer dígito
                }
            }
        });

        cancelarButton.setBackground(darkColor);
        cancelarButton.setForeground(textColor);
        guardarButton.setBackground(darkColor);
        guardarButton.setForeground(textColor);

        temporalRadioButton.setBackground(lightColor);
        temporalRadioButton.setForeground(textColor);
        permanenteRadioButton.setBackground(lightColor);
        permanenteRadioButton.setForeground(textColor);
        femeninoRadioButton.setBackground(lightColor);
        femeninoRadioButton.setForeground(textColor);
        masculinoRadioButton.setBackground(lightColor);
        masculinoRadioButton.setForeground(textColor);

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);
        lbl4.setForeground(textColor);
        lbl5.setForeground(textColor);
        lbl6.setForeground(textColor);
        lbl7.setForeground(textColor);
        lbl8.setForeground(textColor);
        lbl9.setForeground(textColor);
        lbl10.setForeground(textColor);
        lbl11.setForeground(textColor);
        lbl12.setForeground(textColor);
        lbl13.setForeground(textColor);

        // Cargar los iconos en blanco
        ImageIcon cancelIcon = new ImageIcon("cancel_icon_white.png");
        ImageIcon saveIcon = new ImageIcon("save_icon_white.png");
        ImageIcon updateIcon = new ImageIcon("update_icon_white.png");

        // Establecer los iconos en los botones
        cancelarButton.setIcon(cancelIcon);
        guardarButton.setIcon(saveIcon);

        // Establecer el color de texto en blanco para los botones
        cancelarButton.setForeground(textColor);
        guardarButton.setForeground(textColor);

        // Establecer el fondo oscuro para los botones
        cancelarButton.setBackground(darkColor);
        guardarButton.setBackground(darkColor);

        // Boton cancelar
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaEmpleados listaEmpleados = new ListaEmpleados();
                listaEmpleados.setVisible(true);
                crearEmpleado.dispose();
            }
        });

        // Boton guardar
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int validacion = 0;
                    String mensaje = "Faltó ingresar: \n";

                    // Verificar si ya existe un empleado con la misma identidad
                    if (validarIdentidadExistente(campoIdentidad.getText().trim())) {
                        JOptionPane.showMessageDialog(null, "La identidad ingresada ya está asociada a otro empleado", "Validación", JOptionPane.ERROR_MESSAGE);
                        return; // Detener la ejecución del método
                    }

                    // Verificar si ya existe un empleado con el mismo teléfono
                    if (validarTelefonoExistente(campoTelefono.getText().trim())) {
                        JOptionPane.showMessageDialog(null, "El teléfono ingresado ya está asociado a otro empleado", "Validación", JOptionPane.ERROR_MESSAGE);
                        return; // Detener la ejecución del método
                    }

                    // Verificar que el DNI no se encuentre vacio
                    String dni = campoIdentidad.getText().trim();
                    if (dni.length() != 15) {
                        validacion++;
                        mensaje += "Identidad\n";
                    }

                    // Verificar el nombre
                    if (campoNombres.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Nombres\n";
                    }

                    if (campoApellidos.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Apellidos\n";
                    }

                    // Verificar si se seleccionó un género
                    if (!femeninoRadioButton.isSelected() && !masculinoRadioButton.isSelected()) {
                        validacion++;
                        mensaje += "Género\n";
                    }

                    if (campoEdad.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Edad\n";
                    } else {
                        int edad = Integer.parseInt(campoEdad.getText());
                        if (edad < 18 || edad > 60) {
                            JOptionPane.showMessageDialog(null, "La edad debe estar entre 18 y 60 años", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    if (campoCorreo.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Correo\n";
                    }

                    if (campoTelefono.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Teléfono\n";
                    }

                    if (campoNombreContacto.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Nombre del contacto de emergencia\n";
                    }

                    if (campoContactoEmergencia.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Teléfono del contacto de emergencia\n";
                    }

                    if (campoDireccion.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Dirección\n";
                    }

                    // Verificar si se seleccionó un tipo de empleado
                    if (!temporalRadioButton.isSelected() && !permanenteRadioButton.isSelected()) {
                        validacion++;
                        mensaje += "Tipo de empleado\n";
                    }


                    // Mostrar mensaje de campos vacíos si es necesario
                    if (validacion > 0) {
                        JOptionPane.showMessageDialog(null, mensaje.toString(), "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String nombre = campoNombres.getText().trim();
                    if (!nombre.isEmpty()) {
                        if (nombre.length() > 50) {
                            JOptionPane.showMessageDialog(null, "El nombre debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!nombre.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                            JOptionPane.showMessageDialog(null, "El nombre debe tener mínimo 2 letras; y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El campo de nombre no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String apellidos = campoApellidos.getText().trim();
                    if (!apellidos.isEmpty()) {
                        if (apellidos.length() > 50) {
                            JOptionPane.showMessageDialog(null, "Los apellidos deben tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!apellidos.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                            JOptionPane.showMessageDialog(null, "Los apellidos deben tener mínimo 2 letras; y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El campo de apellidos no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String correoElectronico = campoCorreo.getText().trim();

                    if (!correoElectronico.isEmpty()) {
                        // Verificar el formato del correo electrónico utilizando una expresión regular
                        if (!correoElectronico.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                            JOptionPane.showMessageDialog(null, "El correo electrónico ingresado no tiene un formato válido", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El campo de correo electrónico no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String nombreContacto = campoNombreContacto.getText().trim();
                    if (!nombreContacto.isEmpty()) {
                        if (nombreContacto.length() > 50) {
                            JOptionPane.showMessageDialog(null, "El nombre de contacto debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!nombreContacto.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                            JOptionPane.showMessageDialog(null, "El nombre de contacto debe tener mínimo 2 letras; y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El campo de nombre de contacto no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String domicilio = campoDireccion.getText().trim();

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

                    String contactoEmergencia = campoContactoEmergencia.getText().trim();

                    if (!contactoEmergencia.isEmpty()) {
                        if (contactoEmergencia.length() != 8) {
                            JOptionPane.showMessageDialog(null, "El número de contacto de emergencia debe tener exactamente 8 dígitos", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!contactoEmergencia.matches("[2389]\\d{7}")) {
                            JOptionPane.showMessageDialog(null, "El número de contacto de emergencia debe empezar con 2, 3, 8 o 9", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El campo de contacto de emergencia no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
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

                    GuardarDatos();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                ListaEmpleados listaEmpleados = new ListaEmpleados();
                listaEmpleados.setVisible(true);
                crearEmpleado.dispose();
            }
        });
    }

    //Consulta insertar
    public void GuardarDatos() throws SQLException {
        sql = new Conexion();
        mysql = sql.conectamysql();
        PreparedStatement preparedStatement = mysql.prepareStatement("insert into " + Empleado.nombreTabla  + " (Identidad, Nombres, Apellidos, Genero, Edad, Correo, Telefono, NombreContactoDeEmergencia, ContactoDeEmergencia, Direccion, TipoDeEmpleado) values (?,?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,campoIdentidad.getText());
        preparedStatement.setString(2,campoNombres.getText());
        preparedStatement.setString(3,campoApellidos.getText());
        preparedStatement.setString(4,femeninoRadioButton.isSelected()?"F":"M");
        preparedStatement.setString(5,campoEdad.getText());
        preparedStatement.setString(6,campoCorreo.getText());
        preparedStatement.setString(7,campoTelefono.getText());
        preparedStatement.setString(8,campoNombreContacto.getText());
        preparedStatement.setString(9, campoContactoEmergencia.getText());
        preparedStatement.setString(10,campoDireccion.getText());
        preparedStatement.setString(11,temporalRadioButton.isSelected()?"Temporal":"Permanente");

//        preparedStatement.execute();

        preparedStatement.executeUpdate();

        // No es necesario cerrar explícitamente la conexión, ya que se cerrará automáticamente al finalizar el bloque try-with-resources

        // Verificar si la ventana ListaCliente ya está abierta
        boolean listaEmpleadoAbierta = false;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof ListaEmpleados) {
                listaEmpleadoAbierta = true;
                break;
            }
        }

        // Abrir la ventana ListaCliente solo si no está abierta
        if (!listaEmpleadoAbierta) {
            ListaEmpleados empleados = new ListaEmpleados();
            empleados.setVisible(true);
        }

        crearEmpleado.dispose();

        // Mostrar mensaje de éxito
        String nombreCompleto = campoNombres.getText() + " " + campoApellidos.getText();

        // Mensaje personalizado
        System.out.println("Empleado " + nombreCompleto + " ha sido registrado exitosamente.");
        JOptionPane.showMessageDialog(null, "Cliente " + nombreCompleto + " ha sido registrado exitosamente.", "Éxito", JOptionPane.ERROR_MESSAGE);


    }



    // Método para validar si el teléfono ya está asociado a un empleado en la base de datos
    private boolean validarTelefonoExistente(String telefono) {
        try {
            // Crear la conexión a la base de datos (usando el objeto 'mysql' y 'sql' definidos en la clase)
            mysql = sql.conectamysql();

            // Preparar la consulta SQL
            String query = "SELECT COUNT(*) FROM empleados WHERE Telefono = ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, telefono);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Obtener el resultado
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retorna true si el conteo es mayor que cero (existe al menos un empleado con ese teléfono)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión a la base de datos
            if (mysql != null) {
                try {
                    mysql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false; // En caso de error, se asume que no existe un empleado con ese teléfono
    }

    // Método para validar si la identidad ya está asociada a un empleado en la base de datos
    private boolean validarIdentidadExistente(String identidad) {
        try {
            // Crear la conexión a la base de datos (usando el objeto 'mysql' y 'sql' definidos en la clase)
            mysql = sql.conectamysql();

            // Preparar la consulta SQL
            String query = "SELECT COUNT(*) FROM empleados WHERE Identidad = ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, identidad);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Obtener el resultado
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retorna true si el conteo es mayor que cero (existe al menos un empleado con esa identidad)
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión a la base de datos
            if (mysql != null) {
                try {
                    mysql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false; // En caso de error, se asume que no existe un empleado con esa identidad
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
