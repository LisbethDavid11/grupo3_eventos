/**
 * EditarEmpleado.java
 *
 * Editar Empleado
 *
 * @author Alejandra Aroca
 * @version 1.0
 * @since 2024-05-05
 */

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

public class EditarEmpleado extends JFrame {
    // Botones
    public JButton botonCancelar;
    public JButton botonGuardar;
    private JButton botonLimpiar;

    // Campos de texto
    public JTextField campoNombres;
    public JTextField campoApellidos;
    public JTextField campoEdad;
    public JTextField campoCorreo;
    public JTextField campoTelefono;
    public JTextField campoNombreContacto;
    public JTextField campoContactoEmergencia;

    // Área de texto
    public JTextArea campoDireccion;

    // Campo de texto formateado
    public JFormattedTextField campoIdentidad;

    // Botones de selección
    public JRadioButton femeninoRadioButton;
    public JRadioButton masculinoRadioButton;
    public JRadioButton temporalRadioButton;
    public JRadioButton permanenteRadioButton;

    // Grupos de botones
    public ButtonGroup grupoGenero;
    public ButtonGroup grupoTipo;

    // Etiquetas
    private JLabel lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
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

    // Paneles
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;

    // Conexión a la base de datos
    private Conexion sql;
    private Connection mysql;

    // Referencia a la ventana de edición de empleado actual
    public EditarEmpleado actual = this;

    // Fuentes y colores
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
    private int id;

    public EditarEmpleado(int id) {
        super("");
        setSize(600, 620);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();
        this.id = id;
        mostrar();

        campoDireccion.setLineWrap(true);
        campoDireccion.setWrapStyleWord(true);

        grupoGenero = new ButtonGroup();
        grupoGenero.add(femeninoRadioButton);
        grupoGenero.add(masculinoRadioButton);

        grupoTipo = new ButtonGroup();
        grupoTipo.add(temporalRadioButton);
        grupoTipo.add(permanenteRadioButton);

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
        campoCorreo.setName("Correo");
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

        grupoTipo = new ButtonGroup();
        grupoTipo.add(temporalRadioButton);
        grupoTipo.add(permanenteRadioButton);

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

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    ListaEmpleados listaEmpleados = new ListaEmpleados();
                    listaEmpleados.setVisible(true);
                    actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Integer empleadoId = id;
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                // Verificar si ya existe un empleado con el mismo correo
                if (validarCorreoExistente(campoCorreo.getText().trim(), empleadoId)) {
                    mostrarDialogoPersonalizadoError("El correo electrónico ya está asociado a otro empleado.", Color.decode("#C62828"));
                    return; // Detener la ejecución del método
                }

                // Verificar si ya existe un empleado con la misma identidad
                if (validarIdentidadExistente(campoIdentidad.getText().trim(), empleadoId)) {
                    mostrarDialogoPersonalizadoError("La identidad ingresada ya está asociada a otro empleado.", Color.decode("#C62828"));
                    return; // Detener la ejecución del método
                }

                // Verificar si ya existe un empleado con el mismo teléfono
                if (validarTelefonoExistente(campoTelefono.getText().trim(), empleadoId)) {
                    mostrarDialogoPersonalizadoError("El teléfono ingresado ya está asociado a otro empleado.", Color.decode("#C62828"));
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

                // Verificar si se seleccionó un tipo de empleado
                if (!permanenteRadioButton.isSelected() && !temporalRadioButton.isSelected()) {
                    validacion++;
                    mensaje += "Tipo de empleado\n";
                }

                if (campoEdad.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Edad\n";
                } else {
                    int edad = Integer.parseInt(campoEdad.getText());
                    if (edad < 18 || edad > 60) {
                        mostrarDialogoPersonalizadoError("La edad debe estar entre 18 y 60 años.", Color.decode("#C62828"));
                        return;
                    }
                }

                if (campoCorreo.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Correo electrónico\n";
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

                // Mostrar mensaje de campos vacíos si es necesario
                if (validacion > 0) {
                    mostrarDialogoPersonalizadoError(mensaje, Color.decode("#C62828"));
                    return;
                }

                String nombre = campoNombres.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 50) {
                        mostrarDialogoPersonalizadoError("El nombre de empleado debe tener como máximo 50 caracteres.", Color.decode("#C62828"));
                        return;
                    }

                    if (!nombre.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]{2,}(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?")) {
                        mostrarDialogoPersonalizadoError("El nombre de empleado debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de nombre de empleado no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String apellido = campoApellidos.getText().trim();
                if (!apellido.isEmpty()) {
                    if (apellido.length() > 50) {
                        mostrarDialogoPersonalizadoError("El apellido de empleado debe tener como máximo 50 caracteres.", Color.decode("#C62828"));
                        return;
                    }

                    if (!apellido.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]{2,}(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?")) {
                        mostrarDialogoPersonalizadoError("El apellido de empleado debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El apellido del empleado no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String correoElectronico = campoCorreo.getText().trim();
                if (!correoElectronico.isEmpty()) {
                    // Verificar el formato del correo electrónico utilizando una expresión regular
                    if (!correoElectronico.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        mostrarDialogoPersonalizadoError("El correo electrónico ingresado no tiene un formato válido.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de correo electrónico no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String nombreContacto = campoNombreContacto.getText().trim();
                if (!nombreContacto.isEmpty()) {
                    if (nombreContacto.length() > 50) {
                        mostrarDialogoPersonalizadoError("El nombre de contacto debe tener como máximo 50 caracteres.", Color.decode("#C62828"));
                        return;
                    }
                    if (!nombreContacto.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]{2,}(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?")) {
                        mostrarDialogoPersonalizadoError("El nombre de contacto debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de nombre de contacto no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String domicilio = campoDireccion.getText().trim();
                if (!domicilio.isEmpty()) {
                    if (domicilio.length() < 2 || domicilio.length() > 200) {
                        mostrarDialogoPersonalizadoError("El domicilio debe tener entre 2 y 200 caracteres.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de domicilio no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String telefono = campoTelefono.getText().trim();
                if (!telefono.isEmpty()) {
                    if (telefono.length() != 8) {
                        mostrarDialogoPersonalizadoError("El número de teléfono debe tener exactamente 8 dígitos.", Color.decode("#C62828"));
                        return;
                    }

                    if (!telefono.matches("[2389]\\d{7}")) {
                        mostrarDialogoPersonalizadoError("El número de teléfono debe empezar con 2, 3, 8 o 9.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de teléfono no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String contactoEmergencia = campoContactoEmergencia.getText().trim();
                if (!contactoEmergencia.isEmpty()) {
                    if (contactoEmergencia.length() != 8) {
                        mostrarDialogoPersonalizadoError("El número de contacto de emergencia debe tener exactamente 8 dígitos.", Color.decode("#C62828"));
                        return;
                    }

                    if (!contactoEmergencia.matches("[2389]\\d{7}")) {
                        mostrarDialogoPersonalizadoError("El número de contacto de emergencia debe empezar con 2, 3, 8 o 9.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de contacto de emergencia no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String identidad = campoIdentidad.getText().trim();
                if (!identidad.isEmpty()) {
                    if (identidad.length() != 15) {
                        mostrarDialogoPersonalizadoError("La identidad debe tener 15 caracteres.", Color.decode("#C62828"));
                        return;
                    }

                    if (!identidad.matches("\\d{4}-\\d{4}-\\d{5}")) {
                        mostrarDialogoPersonalizadoError("La identidad debe tener otro formato valido, ejemplo: 0703-1980-12345.", Color.decode("#C62828"));
                        return;
                    }

                    String numerosIdentidad = identidad.replace("-", "");
                    Empleado empleado = new Empleado();
                    boolean esIdentidadValida = empleado.comprobarIdentidad(numerosIdentidad);
                    if (!esIdentidadValida) {
                        mostrarDialogoPersonalizadoError("La identidad ingresada no es válida.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de identidad no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                JButton btnSave = new JButton("Sí");
                JButton btnCancel = new JButton("No");

                // Personaliza los botones aquí
                btnSave.setBackground(darkColorAqua);
                btnCancel.setBackground(darkColorRed);

                // Personaliza los fondos de los botones aquí
                btnSave.setForeground(Color.WHITE);
                btnCancel.setForeground(Color.WHITE);

                // Elimina el foco
                btnSave.setFocusPainted(false);
                btnCancel.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Desea guardar la información del empleado?",
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
                        GuardarDatos();
                        dialog.dispose();
                        ListaEmpleados listaEmpleados = new ListaEmpleados();
                        listaEmpleados.setVisible(true);
                        actual.dispose();
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
                        mostrar();
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

    // Método booleano para validar el teléfono
    private boolean validarTelefonoExistente(String telefono, Integer empleadoId) {
        try {
            mysql = sql.conectamysql();
            String query = "SELECT COUNT(*) FROM empleados WHERE telefono = ? AND id != ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, telefono);
            statement.setInt(2, empleadoId);
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

    // Método booleano para validar la identidad
    private boolean validarIdentidadExistente(String identidad, Integer empleadoId) {
        try {
            mysql = sql.conectamysql();

            String query = "SELECT COUNT(*) FROM empleados WHERE Identidad = ? AND id != ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, identidad);
            statement.setInt(2, empleadoId);

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

    // Método booleano para validar
    private boolean validarCorreoExistente(String correo, Integer empleadoId) {
        try {
            mysql = sql.conectamysql();

            String query = "SELECT COUNT(*) FROM empleados WHERE Correo = ? AND id != ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, correo);
            statement.setInt(2, empleadoId);

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

    // Método para cargar los datos del empleado
    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        femeninoRadioButton.setText("Femenino");
        masculinoRadioButton.setText("Masculino");

        permanenteRadioButton.setText("Permanente");
        temporalRadioButton.setText("Temporal");

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM " + Empleado.nombreTabla + " WHERE id = ?;");
            statement.setInt(1, this.id); // Se corrige el índice del parámetro
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            String identidad = resultSet.getString("identidad");
            campoIdentidad.setValue(identidad);

            campoIdentidad.setText(resultSet.getString(2));
            campoNombres.setText(resultSet.getString(3));
            campoApellidos.setText(resultSet.getString(4));
            String genero = resultSet.getString(5);

            if (genero.equals("Femenino")) {

                femeninoRadioButton.setText("Femenino");
                femeninoRadioButton.setSelected(true);

                masculinoRadioButton.setText("Masculino");
                masculinoRadioButton.setSelected(false);

            } else if (genero.equals("Masculino")) {

                femeninoRadioButton.setText("Femenino");
                femeninoRadioButton.setSelected(false);

                masculinoRadioButton.setText("Masculino");
                masculinoRadioButton.setSelected(true);
            }

            campoEdad.setText(resultSet.getString(6));
            campoCorreo.setText(resultSet.getString(7));
            campoTelefono.setText(resultSet.getString(8));
            campoNombreContacto.setText(resultSet.getString(9));
            campoContactoEmergencia.setText(resultSet.getString(10));
            campoDireccion.setText(resultSet.getString(11));

            String tipo = resultSet.getString(12);

            if (tipo.equals("Permanente")) {

                permanenteRadioButton.setText("Permanente");
                permanenteRadioButton.setSelected(true);

                temporalRadioButton.setText("Temporal");
                temporalRadioButton.setSelected(false);

            } else if (tipo.equals("Temporal")) {

                permanenteRadioButton.setText("Permanente");
                permanenteRadioButton.setSelected(false);

                temporalRadioButton.setText("Temporal");
                temporalRadioButton.setSelected(true);
            }

            System.out.println(statement.execute());

        } catch (SQLException error) {
            //mensaje de error
            System.out.println(error.getMessage());
        }
    }

    // Método para guardar los datos del empleado
    public void GuardarDatos() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        String genero;
        if (femeninoRadioButton.isSelected()) {
            genero = "Femenino";
        } else {
            genero = "Masculino";
        }

        String tipo;
        if (temporalRadioButton.isSelected()) {
            tipo = "Temporal";
        } else {
            tipo = "Permanente";
        }

        try (Connection connection = sql.conectamysql();
             PreparedStatement statement = connection.prepareStatement("UPDATE " + Empleado.nombreTabla + " SET Identidad = ?, Nombres = ?, Apellidos = ?, Genero = ?, Edad = ?, Correo = ?, Telefono = ?, NombreContactoDeEmergencia = ?, ContactoDeEmergencia = ?, Direccion = ?, TipoDeEmpleado = ? WHERE id = ?")) {

            statement.setString(1, campoIdentidad.getText());
            statement.setString(2, campoNombres.getText());
            statement.setString(3, campoApellidos.getText());
            statement.setString(4, genero);
            statement.setString(5, campoEdad.getText());
            statement.setString(6, campoCorreo.getText());
            statement.setString(7, campoTelefono.getText());
            statement.setString(8, campoNombreContacto.getText());
            statement.setString(9, campoContactoEmergencia.getText());
            statement.setString(10, campoDireccion.getText());
            statement.setString(11, tipo);
            statement.setInt(12, this.id);
            statement.executeUpdate();

            String nombreCompleto = campoNombres.getText() + " " + campoApellidos.getText();
            mostrarDialogoPersonalizadoExito("Empleado " + nombreCompleto + " ha sido actualizado exitosamente.", Color.decode("#263238"));
        } catch (SQLException e) {
            mostrarDialogoPersonalizadoError("No se pudo realizar la actualización del empleado", Color.decode("#C62828"));
        }
    }

    // Método para mostrar un diálogo personalizado de éxito
    public void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado "OK"
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Establece el color de fondo del botón
        btnAceptar.setForeground(Color.WHITE); // Establece el color del texto del botón
        btnAceptar.setFocusPainted(false); // Elimina el borde del foco alrededor del botón

        // Crea un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(
                mensaje,                             // Texto del mensaje a mostrar
                JOptionPane.INFORMATION_MESSAGE,     // Tipo de mensaje (información)
                JOptionPane.DEFAULT_OPTION,          // Opción por defecto
                null,                                // Sin icono
                new Object[]{},                      // Sin opciones estándar
                null                                 // Sin valor inicial
        );

        // Configura el JOptionPane para usar el botón personalizado
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón para cerrar el diálogo cuando se presione
        btnAceptar.addActionListener(e -> dialog.dispose());

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    // Método para mostrar un diálogo personalizado de error
    public void mostrarDialogoPersonalizadoError(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado "OK"
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Establece el color de fondo del botón
        btnAceptar.setForeground(Color.WHITE); // Establece el color del texto del botón
        btnAceptar.setFocusPainted(false); // Elimina el borde del foco alrededor del botón

        // Crea un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(
                mensaje,                             // Texto del mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,         // Tipo de mensaje (advertencia)
                JOptionPane.DEFAULT_OPTION,          // Opción por defecto
                null,                                // Sin icono
                new Object[]{},                      // Sin opciones estándar
                null                                 // Sin valor inicial
        );

        // Configura el JOptionPane para usar el botón personalizado
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón para cerrar el diálogo cuando se presione
        btnAceptar.addActionListener(e -> dialog.dispose());

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    // Método para mostrar un diálogo personalizado de atención
    public void mostrarDialogoPersonalizadoAtencion(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado "OK"
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Establece el color de fondo del botón
        btnAceptar.setForeground(Color.WHITE); // Establece el color del texto del botón
        btnAceptar.setFocusPainted(false); // Elimina el borde del foco alrededor del botón

        // Crea un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(
                mensaje,                             // Texto del mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,         // Tipo de mensaje (advertencia)
                JOptionPane.DEFAULT_OPTION,          // Opción por defecto
                null,                                // Sin icono
                new Object[]{},                      // Sin opciones estándar
                null                                 // Sin valor inicial
        );

        // Configura el JOptionPane para usar el botón personalizado
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón para cerrar el diálogo cuando se presione
        btnAceptar.addActionListener(e -> dialog.dispose());

        // Muestra el diálogo
        dialog.setVisible(true);
    }

}
