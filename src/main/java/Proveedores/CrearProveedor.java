package Proveedores;

import Objetos.Cliente;
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

import static Objetos.Proveedor.comprobarRTN;

public class CrearProveedor extends JFrame {
    public JTextField campoEmpresaProveedora;
    public JFormattedTextField campoRTN;
    public JTextField campoTelefono;
    public JTextField campoCorreo;
    public JTextArea campoDireccion;
    public JTextArea campoDescripcion;
    public JTextField campoNombreVendedor;
    public JTextField campoTelefonoVendedor;
    public JButton guardarButton;
    public JButton cancelarButton;
    private JPanel panel1;
    private JLabel lbl0,lbl1,lbl2,lbl3,lbl4,lbl5,lbl6,lbl7,lbl8,lbl9, lbl11;
    private Conexion sql;
    private Connection mysql;
    public CrearProveedor crearProveedor = this;
    public ButtonGroup grupoGenero;
    public ButtonGroup grupoTipo;

    // Colores personalizados
    Color primaryColor = Color.decode("#263238"); // Gris azul oscuro
    Color lightColor = Color.decode("#37474f"); // Gris azul claro
    Color darkColor = Color.decode("#000a12"); // Gris azul más oscuro
    Color textColor = Color.WHITE; // Texto blanco

    private JTextField[] campos = {
            campoRTN, campoEmpresaProveedora, campoTelefono, campoCorreo,campoNombreVendedor, campoTelefonoVendedor
    };

    public CrearProveedor() {
        super("Crear Empleados");
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
                    // Verificar si es el primer espacio en blanco
                    if (length == 0 || caretPosition == 0 || text.charAt(caretPosition - 1) == ' ') {
                        e.consume(); // Ignorar el espacio en blanco
                    }
                } else {
                    // Verificar la longitud del texto después de eliminar espacios en blanco
                    String trimmedText = text.replaceAll("\\s+", " ");
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

                // Verificar si el carácter no es una letra, guion, arroba o punto (excepto el primer punto)
                if (!Character.isLetter(e.getKeyChar()) && e.getKeyChar() != '-' && e.getKeyChar() != '@' && (e.getKeyChar() != '.' || correo.contains("."))) {
                    e.consume(); // Ignorar el carácter si no es una letra, guion, arroba o punto (excepto el primer punto)
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
        campoDireccion.setForeground(textColor);
        campoDescripcion.setForeground(textColor);

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

        lbl0.setForeground(textColor);
        lbl4.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl4.setForeground(textColor);
        lbl4.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);
        lbl8.setForeground(textColor);
        lbl9.setForeground(textColor);
        lbl6.setForeground(textColor);
        lbl7.setForeground(darkColorCyan);
        lbl11.setForeground(darkColorCyan);

        campoDireccion.setBackground(new Color(215, 215, 215));
        campoDescripcion.setBackground(new Color(215, 215, 215));

        // Establecer los iconos en los botones
        cancelarButton.setIcon(cancelIcon);
        guardarButton.setIcon(saveIcon);

        // Boton cancelar
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaProveedores listaProveedores = new ListaProveedores();
                listaProveedores.setVisible(true);
                crearProveedor.dispose();
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
                    if (validarRTNExistente(campoRTN.getText().trim())) {
                        JOptionPane.showMessageDialog(null, "El RTN ingresado ya está asociada a otro proveedor", "Validación", JOptionPane.ERROR_MESSAGE);
                        return; // Detener la ejecución del método
                    }

                    // Verificar si ya existe un empleado con el mismo teléfono
                    if (validarTelefonoExistente(campoTelefono.getText().trim())) {
                        JOptionPane.showMessageDialog(null, "El teléfono ingresado ya está asociado a otro empleado", "Validación", JOptionPane.ERROR_MESSAGE);
                        return; // Detener la ejecución del método
                    }

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

                    if (campoTelefono.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Teléfono del vendedor\n";
                    }

                    if (validacion > 0) {
                        JOptionPane.showMessageDialog(null, mensaje.toString(), "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String nombre = campoEmpresaProveedora.getText().trim();
                    if (!nombre.isEmpty()) {
                        if (nombre.length() > 50) {
                            JOptionPane.showMessageDialog(null, "El nombre de la empresa debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!nombre.matches("[a-zA-Z\\s.&]{2,}")) {
                            JOptionPane.showMessageDialog(null, "El nombre de la empresa debe tener mínimo 2 letras y puede contener espacios, puntos y el carácter '&'", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El campo de nombre de la empresa no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
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

                    String direccion = campoDireccion.getText().trim();
                    if (!direccion.isEmpty()) {
                        if (direccion.length() < 2 || direccion.length() > 200) {
                            JOptionPane.showMessageDialog(null, "La dirección de la empresa debe tener entre 2 y 200 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El campo de dirección no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String descripcion = campoDescripcion.getText().trim();
                    if (!descripcion.isEmpty()) {
                        if (descripcion.length() < 2 || descripcion.length() > 200) {
                            JOptionPane.showMessageDialog(null, "La descripción de la empresa debe tener entre 2 y 200 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El campo de descripción no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String nombreVendedor = campoNombreVendedor.getText().trim();
                    if (!nombreVendedor.isEmpty()) {
                        if (nombreVendedor.length() > 50) {
                            JOptionPane.showMessageDialog(null, "El nombre de vendedor debe tener como máximo 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (!nombreVendedor.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+)?")) {
                            JOptionPane.showMessageDialog(null, "El nombre de vendedor debe tener mínimo 2 letras; y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El campo de nombre de vendedor no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
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

                    GuardarDatos();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                ListaProveedores listaProveedores = new ListaProveedores();
                listaProveedores.setVisible(true);
                crearProveedor.dispose();
            }
        });
    }

    public void GuardarDatos() throws SQLException {
        sql = new Conexion();
        mysql = sql.conectamysql();
        PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO Proveedores (empresaProveedora, rtn, telefono, correo, direccion, descripcion, nombreVendedor, telefonoVendedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, campoEmpresaProveedora.getText());
        preparedStatement.setString(2, campoRTN.getText());
        preparedStatement.setString(3, campoTelefono.getText());
        preparedStatement.setString(4, campoCorreo.getText());
        preparedStatement.setString(5, campoDireccion.getText());
        preparedStatement.setString(6, campoDescripcion.getText());
        preparedStatement.setString(7, campoNombreVendedor.getText());
        preparedStatement.setString(8, campoTelefonoVendedor.getText());
        // preparedStatement.execute();

        preparedStatement.executeUpdate();

        boolean listaProveedoresAbierta = false;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof ListaProveedores) {
                listaProveedoresAbierta = true;
                break;
            }
        }

        // Abrir la ventana ListaCliente solo si no está abierta
        if (!listaProveedoresAbierta) {
            ListaProveedores proveedores = new ListaProveedores();
            proveedores.setVisible(true);
        }

        crearProveedor.dispose();

        // Mostrar mensaje de éxito
        String nombreCompleto = campoEmpresaProveedora.getText();

        // Mensaje personalizado
        System.out.println("Proveedor " + nombreCompleto + " ha sido registrado exitosamente.");
        JOptionPane.showMessageDialog(null, "Proveedor " + nombreCompleto + " ha sido registrado exitosamente.", "Éxito", JOptionPane.DEFAULT_OPTION);
    }



    // Método para validar si el teléfono ya está asociado a un empleado en la base de datos
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