/**
 * CrearProveedor.java
 *
 * CrearProveedor
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-05
 */

package Proveedores;

import Objetos.Conexion;
import Objetos.Proveedor;
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
import java.util.regex.Pattern;

public class CrearProveedor extends JFrame {
    // Botones
    public JButton botonGuardar;
    public JButton botonCancelar;
    public JButton botonLimpiar;

    // Campos de texto
    public JTextField campoEmpresaProveedora;
    public JTextField campoNombreVendedor;
    public JTextField campoTelefonoVendedor;
    public JTextField campoCorreo;
    public JTextField campoTelefono;
    public JFormattedTextField campoRTN;

    // Áreas de texto
    public JTextArea campoDireccion;
    public JTextArea campoDescripcion;

    // Paneles
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;

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
    private JLabel lbl11;

    // Conexión a la base de datos
    private Conexion sql;
    private Connection mysql;

    // Instancia de la clase
    public CrearProveedor crearProveedor = this;

    // Fuente y colores
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

    public CrearProveedor() {
        super("Crear Proveedor");
        setSize(600, 570);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();

        campoDireccion.setLineWrap(true);
        campoDireccion.setWrapStyleWord(true);

        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        try {
            MaskFormatter formatoRTN = new MaskFormatter("####-####-######");
            campoRTN.setFormatterFactory(new DefaultFormatterFactory(formatoRTN));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        campoEmpresaProveedora.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoEmpresaProveedora.getText();
                int length = text.length();
                int caretPosition = campoEmpresaProveedora.getCaretPosition();

                // Verificar si se está ingresando un espacio en blanco
                if (e.getKeyChar() == ' ') {
                    // Verificar si es el primer espacio en blanco o si hay varios espacios consecutivos
                    if (length == 0 || caretPosition == 0 || text.charAt(caretPosition - 1) == ' ') {
                        e.consume(); // Ignorar el espacio en blanco adicional
                    }
                } else {
                    // Verificar la longitud del texto después de eliminar espacios en blanco
                    String trimmedText = text.replaceAll("\\s+", " ");
                    int trimmedLength = trimmedText.length();

                    // Verificar si se está ingresando una letra
                    if (Character.isLetter(e.getKeyChar())) {
                        // Verificar si se excede el límite de caracteres
                        if (trimmedLength >= 100) {
                            e.consume(); // Ignorar la letra
                        } else {
                            // Convertir solo la primera letra a mayúscula
                            if (caretPosition == 0) {
                                e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                            }
                        }
                    } else {
                        e.consume(); // Ignorar cualquier otro tipo de carácter
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

                // Convertir la primera letra en mayúscula si se ingresa un nuevo párrafo
                if (texto.length() == 0 || texto.substring(caretPosition - 1, caretPosition).equals("\n")) {
                    e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                }

                // Permitir números, letras, espacios, punto, coma y tildes
                if (!Character.isLetterOrDigit(e.getKeyChar()) && !Character.isSpaceChar(e.getKeyChar()) && e.getKeyChar() != '.' && e.getKeyChar() != ',' && !Character.isWhitespace(e.getKeyChar()) && !Character.isIdeographic(e.getKeyChar())) {
                    e.consume(); // Ignorar el evento si no es una letra, número, espacio, punto, coma o tilde
                }
            }
        });

        campoDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String texto = campoDescripcion.getText();
                int caretPosition = campoDescripcion.getCaretPosition();

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

        campoCorreo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String correo = campoCorreo.getText();

                // Verificar si se excede la longitud máxima
                if (correo.length() >= 45) {
                    e.consume(); // Ignorar el evento si se alcanza el límite máximo de caracteres (45)
                    return;
                }

                // Verificar si se está ingresando un espacio en blanco
                if (Character.isWhitespace(e.getKeyChar())) {
                    e.consume(); // Ignorar el espacio en blanco
                    return;
                }

                // Verificar si el carácter no es una letra, guion, arroba o punto
                if (!Character.isLetter(e.getKeyChar()) && e.getKeyChar() != '-' && e.getKeyChar() != '@' && e.getKeyChar() != '.') {
                    e.consume(); // Ignorar el carácter si no es una letra, guion, arroba o punto
                    return;
                }
            }
        });

        campoTelefonoVendedor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String telefono = campoTelefonoVendedor.getText();
                if (!Character.isDigit(e.getKeyChar()) || telefono.length() >= 8) {
                    e.consume(); // Evita que se escriban caracteres no numéricos o se exceda la longitud
                }

                if (telefono.length() == 0 && (e.getKeyChar() != '2' && e.getKeyChar() != '3' && e.getKeyChar() != '8' && e.getKeyChar() != '9')) {
                    e.consume(); // Evita que se ingrese un dígito inválido como primer dígito
                }
            }
        });

        campoNombreVendedor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoNombreVendedor.getText();
                int length = text.length();
                int caretPosition = campoNombreVendedor.getCaretPosition();

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

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));

        // Color de texto para el JTextArea
        campoDireccion.setForeground(textColor);
        campoDescripcion.setForeground(textColor);

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

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);
        lbl4.setForeground(textColor);
        lbl5.setForeground(textColor);
        lbl6.setForeground(textColor);
        lbl8.setForeground(textColor);
        lbl9.setForeground(textColor);
        lbl7.setForeground(darkColorCyan);
        lbl11.setForeground(darkColorCyan);

        campoDireccion.setBackground(new Color(215, 215, 215));
        campoDescripcion.setBackground(new Color(215, 215, 215));

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginRTN = new EmptyBorder(10, 0, 10, 0);
        campoRTN.setBorder(marginRTN);

        // Boton cancelar
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea cancelar el registro del proveedor?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    ListaProveedores listaProveedores = new ListaProveedores();
                    listaProveedores.setVisible(true);
                    crearProveedor.dispose();
                }
            }
        });

        // Boton guardar
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                // Verificar el nombre
                if (campoEmpresaProveedora.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Nombre de la empresa\n";
                }


                if (campoCorreo.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Correo electrónico\n";
                }

                if (campoTelefono.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Teléfono\n";
                }

                String rtn = campoRTN.getText().trim();
                if (rtn.length() != 16) {
                    validacion++;
                    mensaje += "RTN\n";
                }

                if (campoDireccion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Dirección\n";
                }

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Descripción\n";
                }

                if (campoNombreVendedor.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Nombre del vendedor\n";
                }

                if (campoTelefonoVendedor.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Teléfono del vendedor\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje.toString(), "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar si ya existe un empleado con la misma identidad
                if (validarRTNExistente(campoRTN.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "El RTN ingresado ya está asociada a otro proveedor", "Validación", JOptionPane.ERROR_MESSAGE);
                    return; // Detener la ejecución del método
                }

                // Verificar si ya existe un empleado con el mismo teléfono
                if (validarTelefonoExistente(campoTelefono.getText().trim())) {
                    JOptionPane.showMessageDialog(null, "El teléfono ingresado ya está asociado a otro empleado", "Validación", JOptionPane.ERROR_MESSAGE);
                    return; // Detener la ejecución del método
                }

                String nombre = campoEmpresaProveedora.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 100) {
                        JOptionPane.showMessageDialog(null, "El nombre debe tener como máximo 100 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!nombre.matches("[a-zA-ZñÑ]{2,}(\\s[a-zA-ZñÑ]+\\s*)*")) {
                        JOptionPane.showMessageDialog(null, "El nombre debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de nombre no puede estar vacío.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String correoElectronico = campoCorreo.getText().trim();
                if (!correoElectronico.isEmpty()) {
                    // Verificar el formato del correo electrónico utilizando una expresión regular
                    if (!correoElectronico.matches("^[A-Za-zñÑ0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        JOptionPane.showMessageDialog(null, "El correo electrónico ingresado no tiene un formato válido", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de correo electrónico no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
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

                String numerortn = campoRTN.getText().trim();
                if (!numerortn.isEmpty()) {
                    if (numerortn.length() != 16) {
                        JOptionPane.showMessageDialog(null, "El RTN debe tener 16 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!numerortn.matches("\\d{4}-\\d{4}-\\d{6}")) {
                        JOptionPane.showMessageDialog(null, "El RTN debe tener el formato ####-####-######", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String numerosIdentidad = numerortn.replace("-", "");
                    Proveedor proveedor = new Proveedor();
                    boolean esIdentidadValida = proveedor.comprobarRTN(numerosIdentidad);
                    if (!esIdentidadValida) {
                        JOptionPane.showMessageDialog(null, "El RTN ingresado no es válido", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo RTN no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String direccion = campoDireccion.getText().trim();
                if (!campoDireccion.getText().trim().isEmpty()) {
                    String texto = campoDireccion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200 || contieneSoloNumeros(texto)) {
                        JOptionPane.showMessageDialog(null, "El campo dirección debe tener entre 2 y 200 caracteres y no puede contener solo números.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Validación de la descripción
                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200 || contieneSoloNumeros(texto)) {
                        JOptionPane.showMessageDialog(null, "El campo descripción debe tener entre 2 y 200 caracteres y no puede contener solo números.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String vendedor = campoNombreVendedor.getText().trim();
                if (!vendedor.isEmpty()) {
                    if (vendedor.length() > 50) {
                        JOptionPane.showMessageDialog(null, "El nombre de empleado debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!vendedor.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ]{2,}(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?")) {
                        JOptionPane.showMessageDialog(null, "El nombre de empleado debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de nombre de empleado no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String telefonoVendedor = campoTelefonoVendedor.getText().trim();
                if (!telefonoVendedor.isEmpty()) {
                    if (telefonoVendedor.length() != 8) {
                        JOptionPane.showMessageDialog(null, "El número de teléfono del vendedor debe tener exactamente 8 dígitos", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!telefonoVendedor.matches("[2389]\\d{7}")) {
                        JOptionPane.showMessageDialog(null, "El número de teléfono del vendedor debe empezar con 2, 3, 8 o 9", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de teléfono del vendedor no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea guardar la información del proveedor?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    GuardarDatos();
                    ListaProveedores listaProveedores = new ListaProveedores();
                    listaProveedores.setVisible(true);
                    crearProveedor.dispose();
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
                        "¿Estás seguro de que deseas limpiar los datos del proveedor?",
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
                        campoEmpresaProveedora.setText("");
                        campoNombreVendedor.setText("");
                        campoCorreo.setText("");
                        campoTelefono.setText("");
                        campoTelefonoVendedor.setText("");
                        campoRTN.setText("");
                        campoDireccion.setText("");
                        campoDescripcion.setText("");
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

    // Método para guardar los datos del proveedor
    public void GuardarDatos() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        try (
            PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO Proveedores (empresaProveedora, rtn, telefono, correo, direccion, descripcion, nombreVendedor, telefonoVendedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")){
            preparedStatement.setString(1, campoEmpresaProveedora.getText());
            preparedStatement.setString(2, campoRTN.getText());
            preparedStatement.setString(3, campoTelefono.getText());
            preparedStatement.setString(4, campoCorreo.getText());
            preparedStatement.setString(5, campoDireccion.getText());
            preparedStatement.setString(6, campoDescripcion.getText());
            preparedStatement.setString(7, campoNombreVendedor.getText());
            preparedStatement.setString(8, campoTelefonoVendedor.getText());
            preparedStatement.executeUpdate();

            System.out.println("Proveedor " + campoEmpresaProveedora.getText() + " registrado exitosamente.");
            JOptionPane.showMessageDialog(null, "Proveedor " + campoEmpresaProveedora.getText() + " registrado exitosamente.", "Éxito", JOptionPane.DEFAULT_OPTION);
        } catch (SQLException e) {
            String mensajeError = "Error al guardar el proveedor: " + e.getMessage();
            JOptionPane.showMessageDialog(null, "No se pudo realizar el registro del proveedor", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para validar telefóno existente
    private boolean validarTelefonoExistente(String telefono) {
        try {
            // Crear la conexión a la base de datos (usando el objeto 'mysql' y 'sql' definidos en la clase)
            mysql = sql.conectamysql();

            // Preparar la consulta SQL
            String query = "SELECT COUNT(*) FROM proveedores WHERE Telefono = ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, telefono);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Obtener el resultado
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retorna true si el conteo es mayor que cero (existe al menos un proveedor con ese teléfono)
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

    // Método para validar RTN existente
    private boolean validarRTNExistente(String rtn) {
        try {
            // Crear la conexión a la base de datos (usando el objeto 'mysql' y 'sql' definidos en la clase)
            mysql = sql.conectamysql();

            // Preparar la consulta SQL
            String query = "SELECT COUNT(*) FROM proveedores WHERE rtn = ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, rtn);

            // Ejecutar la consulta
            ResultSet resultSet = statement.executeQuery();

            // Obtener el resultado
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Retorna true si el conteo es mayor que cero (existe al menos un proveedor con ese RTN)
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
        return false; // En caso de error, se asume que no existe un proveedor con ese RTN
    }

    // Método para verificar si una cadena contiene solo números
    private boolean contieneSoloNumeros(String texto) {
        return Pattern.matches("[0-9]+", texto);
    }

    // Método Principal
    public static void main(String[] args) {
        CrearProveedor crearProveedor = new CrearProveedor();
        crearProveedor.setVisible(true);
    }
}
