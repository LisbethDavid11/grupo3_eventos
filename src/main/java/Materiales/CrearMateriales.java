package Materiales;

import Objetos.Conexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrearMateriales extends JFrame {
    private JTextField campoNombre;
    private JTextField campoPrecio;
    private JTextArea campoDescripcion;
    private JRadioButton radioButtonSi;
    private JRadioButton radioButtonNo;
    private JComboBox<String> comboBoxProveedor;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JPanel panel;
    private JLabel lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private String imagePath = "";
    private CrearMateriales actual = this;
    private Conexion sql;

    public CrearMateriales() {
        super("");
        setSize(500, 490);
        setLocationRelativeTo(null);
        setContentPane(panel);

        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        sql = new Conexion();
        comboBoxProveedor.addItem("Seleccione un proveedor"); // Agregar mensaje inicial
        cargarProveedores();

        // Color de fondo del panel
        panel.setBackground(Color.decode("#F5F5F5"));

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

        // Color de texto de los botones
        botonCancelar.setForeground(Color.WHITE);
        botonGuardar.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonCancelar.setBackground(darkColorCyan);
        botonGuardar.setBackground(darkColorAqua);

        botonCancelar.setFocusPainted(false);
        botonGuardar.setFocusPainted(false);

        // Aplica el margen al botón
        botonGuardar.setBorder(margin);
        botonCancelar.setBorder(margin);

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);
        lbl4.setForeground(textColor);


        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

        // Inicializar JRadioButtons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonNo);
        buttonGroup.add(radioButtonSi);

        // Color de texto para el JTextArea
        campoDescripcion.setForeground(textColor);
        campoDescripcion.setBackground(new Color(215, 215, 215));

        // No seleccionar ningún botón de radio por defecto
        buttonGroup.clearSelection();

        campoNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoNombre.getText();
                int length = text.length();
                int caretPosition = campoNombre.getCaretPosition();

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

        campoPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoPrecio.getText();
                int caretPosition = campoPrecio.getCaretPosition();

                // Permitir solo dígitos y el carácter punto
                if (!(Character.isDigit(c) || c == '.')) {
                    e.consume(); // Ignorar cualquier otro carácter
                    return;
                }

                // Verificar si el carácter es un punto
                if (c == '.') {
                    // Verificar si ya hay un punto en el texto o si es el primer carácter
                    if (text.contains(".") || caretPosition == 0) {
                        e.consume(); // Ignorar el punto adicional
                        return;
                    }
                } else {
                    // Verificar si se excede el límite de caracteres después del punto
                    int dotIndex = text.indexOf('.');
                    if (dotIndex != -1 && caretPosition > dotIndex + 2) {
                        e.consume(); // Ignorar el carácter si se excede el límite de decimales
                        return;
                    }
                }

                // Verificar la longitud del texto antes y después del punto
                String[] parts = text.split("\\.");
                int integerLength = parts[0].length();
                int decimalLength = parts.length > 1 ? parts[1].length() : 0;

                // Verificar si se excede el límite de dígitos antes o después del punto
                if (integerLength >= 6 || decimalLength >= 2) {
                    e.consume(); // Ignorar el carácter si se excede el límite de dígitos
                    return;
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

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaMateriales listaMateriales = new ListaMateriales();
                listaMateriales.setVisible(true);
                actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";


                if (campoNombre.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Nombres\n";
                }

                if (campoPrecio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Precio\n";
                }

                if (!radioButtonSi.isSelected() && !radioButtonNo.isSelected()) {
                    validacion++;
                    mensaje += "Disponibilidad\n";
                }

                String proveedorText = comboBoxProveedor.getSelectedItem().toString();
                if (proveedorText.equals("Seleccione un proveedor")) {
                    validacion++;
                    mensaje += "Proveedor\n";
                }

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Descripción\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombre = campoNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 50) {
                        JOptionPane.showMessageDialog(null, "El nombre debe tener como máximo 50 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!nombre.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]+\\s*)*")) {
                        JOptionPane.showMessageDialog(null, "El nombre debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de nombre no puede estar vacío.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                String precioText = campoPrecio.getText().trim();
                if (precioText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltó ingresar el precio.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!precioText.matches("\\d{1,5}\\.\\d{2}")) {
                        JOptionPane.showMessageDialog(null, "Precio inválido. Debe tener el formato correcto (ejemplo: 1234.56).", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        double precio = Double.parseDouble(precioText);
                        if (precio < 1.00 || precio > 99999.99) {
                            JOptionPane.showMessageDialog(null, "Precio fuera del rango válido (1.00 - 99999.99).", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                String proveedors = comboBoxProveedor.getSelectedItem().toString();
                if (proveedors.equals("Seleccione un proveedor")) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un proveedor.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        JOptionPane.showMessageDialog(null, "La descripción debe tener entre 2 y 200 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                    }
                }
                guardarMateriales();
            }
        });


    }



    private void cargarProveedores() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, empresaProveedora, nombreVendedor FROM Proveedores");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idProveedor = resultSet.getInt("id");
                String empresaProveedora = resultSet.getString("empresaProveedora");
                String nombreVendedor = resultSet.getString("nombreVendedor");
                String proveedorText = idProveedor + " - " + empresaProveedora + " - " + nombreVendedor;
                comboBoxProveedor.addItem(proveedorText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void guardarMateriales() {
        String nombre = campoNombre.getText().trim();
        String precioText = campoPrecio.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precio = Double.parseDouble(precioText);
        String descripcion = campoDescripcion.getText().trim();
        String disponibilidad = radioButtonSi.isSelected() ? "Si" : "No";
        String proveedorText = comboBoxProveedor.getSelectedItem().toString();
        int idProveedor = Integer.parseInt(proveedorText.split(" - ")[0]);

        if (disponibilidad.equals("Seleccione la disponibilidad")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar la disponibilidad del material.", "Validación", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Faltó ingresar la descripción del material.", "Validación", JOptionPane.ERROR_MESSAGE);
            return;
        } else {
            if (descripcion.length() > 200) {
                JOptionPane.showMessageDialog(null, "La descripción debe tener como máximo 200 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO materiales (nombre, precio, descripcion, disponible, proveedor_id) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setDouble(2, precio);
            preparedStatement.setString(3, descripcion);
            preparedStatement.setString(4, disponibilidad);
            preparedStatement.setInt(5, idProveedor);
            preparedStatement.executeUpdate();


            JOptionPane.showMessageDialog(null, "Material creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Volver a la lista de materiales
            ListaMateriales listaMateriales = new ListaMateriales();
            listaMateriales.setVisible(true);
            actual.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el material", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
