package Empleados;

import Objetos.Conexion;
import Objetos.Empleado;

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

public class CrearEmpleado extends JFrame {
    public JButton botonCancelar, botonGuardar;
    public JTextField campoNombres, campoApellidos, campoEdad, campoCorreo, campoTelefono, campoNombreContacto, campoContactoEmergencia;
    public JTextArea campoDireccion;
    private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
    public JFormattedTextField campoIdentidad;
    public JRadioButton femeninoRadioButton, masculinoRadioButton;
    public JRadioButton temporalRadioButton, permanenteRadioButton;
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11, lbl12, lbl13;
    private JButton botonLimpiar;
    private Conexion sql;
    private Connection mysql;
    public CrearEmpleado crearEmpleado = this;
    public ButtonGroup grupoGenero, grupoTipo;
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    // Color de texto para los JTextField y JRadioButton
    Color textColor = Color.decode("#212121");
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 17);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

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
    public CrearEmpleado() {
        super("Crear Empleados");
        setSize(600, 620);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();

        campoDireccion.setLineWrap(true);
        campoDireccion.setWrapStyleWord(true);

        try {
            MaskFormatter formatoIdentidad = new MaskFormatter("####-####-#####");
            campoIdentidad.setFormatterFactory(new DefaultFormatterFactory(formatoIdentidad));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

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

                // Permitir números, letras, espacios, punto, coma y tildes
                if (!Character.isLetterOrDigit(e.getKeyChar()) && !Character.isSpaceChar(e.getKeyChar()) && e.getKeyChar() != '.' && e.getKeyChar() != ',' && !Character.isWhitespace(e.getKeyChar()) && !Character.isIdeographic(e.getKeyChar())) {
                    e.consume(); // Ignorar el evento si no es una letra, número, espacio, punto, coma o tilde
                    return;
                }

                // Convertir la primera letra en mayúscula si se ingresa un nuevo párrafo
                if (texto.length() == 0 || texto.substring(caretPosition - 1, caretPosition).equals("\n")) {
                    e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
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

        grupoGenero = new ButtonGroup();
        grupoGenero.add(femeninoRadioButton);
        grupoGenero.add(masculinoRadioButton);
        grupoGenero.clearSelection();

        grupoTipo = new ButtonGroup();
        grupoTipo.add(temporalRadioButton);
        grupoTipo.add(permanenteRadioButton);
        grupoTipo.clearSelection();

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));

        // Color de texto para el JTextArea
        campoDireccion.setForeground(textColor);

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

        temporalRadioButton.setForeground(textColor);
        temporalRadioButton.setBackground(panel1.getBackground());
        temporalRadioButton.setFocusPainted(false);

        permanenteRadioButton.setForeground(textColor);
        permanenteRadioButton.setBackground(panel1.getBackground());
        permanenteRadioButton.setFocusPainted(false);

        femeninoRadioButton.setForeground(textColor);
        femeninoRadioButton.setBackground(panel1.getBackground());
        femeninoRadioButton.setFocusPainted(false);

        masculinoRadioButton.setForeground(textColor);
        masculinoRadioButton.setBackground(panel1.getBackground());
        masculinoRadioButton.setFocusPainted(false);

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
        lbl12.setForeground(darkColorCyan);
        lbl13.setForeground(darkColorCyan);

        campoDireccion.setBackground(new Color(215, 215, 215));

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginDNI = new EmptyBorder(10, 0, 10, 0);
        campoIdentidad.setBorder(marginDNI);

        // Boton cancelar
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaEmpleados listaEmpleados = new ListaEmpleados();
                listaEmpleados.setVisible(true);
                crearEmpleado.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                        JOptionPane.showMessageDialog(null, "El nombre de empleado debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!nombre.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]{2,}(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?")) {
                        JOptionPane.showMessageDialog(null, "El nombre de empleado debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de nombre de empleado no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String apellido = campoApellidos.getText().trim();
                if (!apellido.isEmpty()) {
                    if (apellido.length() > 50) {
                        JOptionPane.showMessageDialog(null, "El apellido de empleado debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!apellido.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]{2,}(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?")) {
                        JOptionPane.showMessageDialog(null, "El apellido de empleado debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El apellido del empleado no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
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
                    if (!nombreContacto.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]{2,}(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?")) {
                        JOptionPane.showMessageDialog(null, "El nombre de contacto debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
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

                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea guardar la información del empleado?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    GuardarDatos();
                    ListaEmpleados listaEmpleados = new ListaEmpleados();
                    listaEmpleados.setVisible(true);
                    crearEmpleado.dispose();
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
                        "¿Estás seguro de que deseas limpiar los datos del empleado?",
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
                        campoNombres.setText("");
                        campoApellidos.setText("");
                        campoTelefono.setText("");
                        campoIdentidad.setText("");
                        campoDireccion.setText("");
                        campoContactoEmergencia.setText("");
                        campoNombreContacto.setText("");
                        campoCorreo.setText("");
                        campoEdad.setText("");
                        grupoGenero.clearSelection();
                        grupoTipo.clearSelection();
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

    public void GuardarDatos() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + Empleado.nombreTabla + " (Identidad, Nombres, Apellidos, Genero, Edad, Correo, Telefono, NombreContactoDeEmergencia, ContactoDeEmergencia, Direccion, TipoDeEmpleado) VALUES (?,?,?,?,?,?,?,?,?,?,?)")) {

            preparedStatement.setString(1, campoIdentidad.getText());
            preparedStatement.setString(2, campoNombres.getText());
            preparedStatement.setString(3, campoApellidos.getText());
            preparedStatement.setString(4, femeninoRadioButton.isSelected() ? "Femenino" : "Masculino");
            preparedStatement.setString(5, campoEdad.getText());
            preparedStatement.setString(6, campoCorreo.getText());
            preparedStatement.setString(7, campoTelefono.getText());
            preparedStatement.setString(8, campoNombreContacto.getText());
            preparedStatement.setString(9, campoContactoEmergencia.getText());
            preparedStatement.setString(10, campoDireccion.getText());
            preparedStatement.setString(11, temporalRadioButton.isSelected() ? "Temporal" : "Permanente");
            preparedStatement.executeUpdate();

            String nombreCompleto = campoNombres.getText() + " " + campoApellidos.getText();
            System.out.println("Empleado " + nombreCompleto + " ha sido registrado exitosamente.");
            JOptionPane.showMessageDialog(null, "Empleado " + nombreCompleto + " ha sido registrado exitosamente.", "Éxito", JOptionPane.DEFAULT_OPTION);
        } catch (SQLException e) {
            String mensajeError = "Error al guardar el empleado: " + e.getMessage();
            JOptionPane.showMessageDialog(null, "No se pudo realizar el registro del empleado", "Error", JOptionPane.ERROR_MESSAGE);
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
            String query = "SELECT COUNT(*) FROM empleados WHERE Identidad = ?";
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
