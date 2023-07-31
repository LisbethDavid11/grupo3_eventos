package Globos;
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
import java.sql.SQLException;

public class CrearGlobo extends JFrame {
    private JTextField campoCodigo, campoPrecio, campoForma, campoTamanio, campoColor, campoCantidadPorPaquete;
    private JRadioButton radioButtonAire, radioButtonHelio, radioButtonAmbos, radioButtonSiNecesita, radioButtonNoNecesita;
    private JButton botonGuardar, botonCancelar;
    private JPanel panel, panel1, panel2, panel3;
    private JLabel lbl0, lbl1, lbl2, lbl3;
    private JComboBox comboBoxTipoEvento, comboBoxMaterial;
    private CrearGlobo actual = this;
    private Conexion sql;

    public CrearGlobo() {
        super("");
        setSize(500, 490);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();

        // Color de fondo del panel
        panel.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        radioButtonAire.setBackground(Color.decode("#F5F5F5"));
        radioButtonHelio.setBackground(Color.decode("#F5F5F5"));
        radioButtonAmbos.setBackground(Color.decode("#F5F5F5"));
        radioButtonSiNecesita.setBackground(Color.decode("#F5F5F5"));
        radioButtonNoNecesita.setBackground(Color.decode("#F5F5F5"));
        radioButtonAire.setFocusPainted(false);
        radioButtonHelio.setFocusPainted(false);
        radioButtonAmbos.setFocusPainted(false);
        radioButtonSiNecesita.setFocusPainted(false);
        radioButtonNoNecesita.setFocusPainted(false);

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

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

        // Inicializar JRadioButtons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonHelio);
        buttonGroup.add(radioButtonAire);
        buttonGroup.add(radioButtonAmbos);
        buttonGroup.clearSelection();

        ButtonGroup buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(radioButtonNoNecesita);
        buttonGroup2.add(radioButtonSiNecesita);
        buttonGroup2.clearSelection();

        campoPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoPrecio.getText();

                // Permitir solo dígitos y el carácter de punto decimal
                if (!Character.isDigit(c) && c != '.') {
                    e.consume(); // Ignorar cualquier otro carácter
                    return;
                }

                // Verificar si se excede el límite de caracteres
                if (text.length() >= 5 && c != '.' && !text.contains(".")) {
                    e.consume(); // Ignorar el carácter si se excede el límite de dígitos y no es un punto decimal
                    return;
                }

                // Verificar si ya hay un punto decimal y se intenta ingresar otro
                if (text.contains(".") && c == '.') {
                    e.consume(); // Ignorar el carácter si ya hay un punto decimal
                    return;
                }

                // Verificar la cantidad de dígitos después del punto decimal
                if (text.contains(".")) {
                    int dotIndex = text.indexOf(".");
                    int decimalDigits = text.length() - dotIndex - 1;
                    if (decimalDigits >= 2) {
                        e.consume();
                        return;
                    }
                }
            }
        });

        campoColor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoColor.getText();
                int length = text.length();

                if (length >= 30) {
                    e.consume();  // Ignorar la tecla si se alcanza la longitud máxima
                    return;
                }

                char c = e.getKeyChar();
                if (Character.isISOControl(c)) {
                    return;  // Permitir teclas de control como retroceso y eliminar
                }

                // Convertir la primera letra a mayúscula
                if (length == 0) {
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se intenta ingresar un carácter no permitido como primer carácter
                        return;
                    }
                    campoColor.setText(String.valueOf(Character.toUpperCase(c)));
                    e.consume();  // Ignorar el evento de tecla original para evitar que se agregue la letra dos veces
                    return;
                }

                // Verificar si se ingresó un espacio
                if (c == ' ') {
                    // Permitir solo un espacio entre palabras
                    if (length > 0 && text.charAt(length - 1) != ' ') {
                        return;
                    } else {
                        e.consume(); // Ignorar el evento si se intenta agregar más de un espacio consecutivo
                    }
                } else {
                    // Permitir solo letras
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se ingresa un carácter no permitido
                    }
                }
            }
        });

        campoForma.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoForma.getText();
                int length = text.length();

                if (length >= 30) {
                    e.consume();  // Ignorar la tecla si se alcanza la longitud máxima
                    return;
                }

                char c = e.getKeyChar();
                if (Character.isISOControl(c)) {
                    return;  // Permitir teclas de control como retroceso y eliminar
                }

                // Convertir la primera letra a mayúscula
                if (length == 0) {
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se intenta ingresar un carácter no permitido como primer carácter
                        return;
                    }
                    campoForma.setText(String.valueOf(Character.toUpperCase(c)));
                    e.consume();  // Ignorar el evento de tecla original para evitar que se agregue la letra dos veces
                    return;
                }

                // Verificar si se ingresó un espacio
                if (c == ' ') {
                    // Permitir solo un espacio entre palabras
                    if (length > 0 && text.charAt(length - 1) != ' ') {
                        return;
                    } else {
                        e.consume(); // Ignorar el evento si se intenta agregar más de un espacio consecutivo
                    }
                } else {
                    // Permitir solo letras
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se ingresa un carácter no permitido
                    }
                }
            }
        });

        campoTamanio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoTamanio.getText();
                int length = text.length();

                if (length >= 30) {
                    e.consume();  // Ignorar la tecla si se alcanza la longitud máxima
                    return;
                }

                char c = e.getKeyChar();
                if (Character.isISOControl(c)) {
                    return;  // Permitir teclas de control como retroceso y eliminar
                }

                // Verificar si se ingresó un espacio
                if (c == ' ') {
                    // Permitir solo un espacio entre palabras
                    if (length > 0 && text.charAt(length - 1) != ' ') {
                        return;
                    } else {
                        e.consume(); // Ignorar el evento si se intenta agregar más de un espacio consecutivo
                    }
                } else {
                    // Permitir solo letras, números y asterisco
                    if (!Character.isLetterOrDigit(c) && c != '*') {
                        e.consume(); // Ignorar el evento si se ingresa un carácter no permitido
                        return;
                    }

                    // Verificar si es el primer carácter y si no es un número
                    if (length == 0 && !Character.isDigit(c)) {
                        e.consume(); // Ignorar el evento si no se inicia con un número
                        return;
                    }
                }
            }
        });



        campoCantidadPorPaquete.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoCantidadPorPaquete.getText();
                if (!Character.isDigit(c)) {
                    e.consume();
                    return;
                }
                String newText = text + c;
                int value = 0;
                try {
                    value = Integer.parseInt(newText);
                } catch (NumberFormatException ex) {
                    e.consume();
                    return;
                }
                if (value < 0 || value > 9999) {
                    e.consume();
                }
            }
        });

        campoCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoCodigo.getText();
                int length = text.length();

                if (length >= 50) {
                    e.consume();  // Ignorar la tecla si se alcanza la longitud máxima
                    return;
                }

                char c = e.getKeyChar();
                if (Character.isISOControl(c)) {
                    return;  // Permitir teclas de control como retroceso y eliminar
                }

                String newText = text + c;
                if (!newText.matches("[a-zA-ZñÑ0-9_-]+")) {
                    e.consume();  // Ignorar la tecla si se introduce un carácter no permitido
                }
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaGlobos listaGlobos = new ListaGlobos();
                listaGlobos.setVisible(true);
                actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                if (campoCodigo.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "El código del globo\n";
                }

                String tipoEventoText = comboBoxTipoEvento.getSelectedItem().toString();
                if (tipoEventoText.equals("Seleccione el tipo")) {
                    validacion++;
                    mensaje += "El tipo de evento\n";
                }

                String tipoMaterialText = comboBoxMaterial.getSelectedItem().toString();
                if (tipoMaterialText.equals("Seleccione el material")) {
                    validacion++;
                    mensaje += "El tipo de material\n";
                }

                if (campoPrecio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "El precio\n";
                }

                if (campoCantidadPorPaquete.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "La cantidad de globos por paquete\n";
                }

                if (!radioButtonAire.isSelected() && !radioButtonHelio.isSelected() && !radioButtonAmbos.isSelected()) {
                    validacion++;
                    mensaje += "Seleccionar si es para aire, helio o ambos\n";
                }

                if (!radioButtonSiNecesita.isSelected() && !radioButtonNoNecesita.isSelected()) {
                    validacion++;
                    mensaje += "Seleccionar si es necesario el porta globos\n";
                }

                if (campoTamanio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "El tamaño\n";
                }

                if (campoColor.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "El color\n";
                }

                if (campoForma.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "La forma del globo\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String codigo = campoCodigo.getText().trim();
                if (!codigo.isEmpty()) {
                    if (codigo.length() > 100) {
                        JOptionPane.showMessageDialog(null, "El código debe tener como máximo 100 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!codigo.matches("[a-zA-ZñÑ0-9_-]+")) {
                        JOptionPane.showMessageDialog(null, "El código solo puede contener letras (mayúsculas y minúsculas), números y guiones (bajos, medios y altos).", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El campo de código no puede estar vacío.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String evento = comboBoxTipoEvento.getSelectedItem().toString();
                if (evento.equals("Seleccione el tipo")) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de evento.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String material = comboBoxMaterial.getSelectedItem().toString();
                if (material.equals("Seleccione el material")) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar el tipo de material.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String precioText = campoPrecio.getText().trim();
                if (precioText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltó ingresar el precio.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!precioText.matches("\\d{1,5}(\\.\\d{1,2})?")) {
                        JOptionPane.showMessageDialog(null, "Precio inválido. Debe tener el formato correcto (ejemplo: 1234 o 1234.56).", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        double precio = Double.parseDouble(precioText);
                        if (precio < 1.00 || precio > 99999.99) {
                            JOptionPane.showMessageDialog(null, "Precio fuera del rango válido (1.00 - 99999.99).", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                String cantidadText = campoCantidadPorPaquete.getText().trim();
                if (cantidadText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltó ingresar la cantidad por paquetes de globos.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!cantidadText.matches("\\d{1,4}")) {
                        JOptionPane.showMessageDialog(null, "Cantidad por paquete de globos inválida. Debe contener solo números entre 1 y 9999.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        int cantidad = Integer.parseInt(cantidadText);
                        if (cantidad < 1 || cantidad > 9999) {
                            JOptionPane.showMessageDialog(null, "Cantidad por paquete de globos fuera del rango válido (1 - 9999).", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                String tamanioText = campoTamanio.getText().trim();
                if (tamanioText.isEmpty() || tamanioText.length() < 2 || tamanioText.length() > 30) {
                    JOptionPane.showMessageDialog(null, "El tamaño debe tener entre 2 y 30 caracteres y no puede estar vacío.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!tamanioText.matches("[\\d\\w]+(\\s[\\d\\w]+)*(\\s\\*\\s[\\d\\w]+(\\s[\\d\\w]+)*)*")) {
                        JOptionPane.showMessageDialog(null, "Tamaño inválido. Debe comenzar con uno o dos dígitos o letras y puede tener un espacio seguido de un asterisco (*), una x y luego uno o dos dígitos o letras adicionales seguido de una palabra o unidades de medida.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String colorText = campoForma.getText().trim();
                if (colorText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltó ingresar el color.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!colorText.matches("^[A-ZÑ][a-zA-ZñÑ ]*$") || colorText.length() < 2 || colorText.length() > 30) {
                        JOptionPane.showMessageDialog(null, "Color inválido. Solo puede contener letras y un espacio entre palabras. Además, debe tener entre 2 y 30 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String formaText = campoForma.getText().trim();
                if (formaText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltó ingresar la forma.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!formaText.matches("^[A-ZÑ][a-zA-ZñÑ ]*$") || formaText.length() < 2 || formaText.length() > 30) {
                        JOptionPane.showMessageDialog(null, "Forma inválida. Solo puede contener letras y un espacio entre palabras. Además, debe tener entre 2 y 30 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea guardar la información del globo?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    guardarGlobos();
                    ListaGlobos listaGlobos = new ListaGlobos();
                    listaGlobos.setVisible(true);
                    actual.dispose();
                }
            }
        });
    }

    private void guardarGlobos() {
        String codigo = campoCodigo.getText().trim();
        double precio = Double.parseDouble(campoPrecio.getText().trim());
        String forma = campoForma.getText().trim();
        String tamanio = campoTamanio.getText().trim();
        String color = campoColor.getText().trim();
        int cantidadPorPaquete = Integer.parseInt(campoCantidadPorPaquete.getText().trim());
        String para = radioButtonHelio.isSelected() ? "Helio" : (radioButtonAire.isSelected() ? "Aire" : "Ambos");
        String portaGlobo = radioButtonSiNecesita.isSelected() ? "1" : "0";
        String tipoEvento = comboBoxTipoEvento.getSelectedItem().toString();
        String material = comboBoxMaterial.getSelectedItem().toString();
        int cantidad = 0;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO globos (codigo_globo, tipo, material, para, tamano, color, forma, cantidad_paquete, porta_globo, cantidad, precio) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, codigo);
            preparedStatement.setString(2, tipoEvento);
            preparedStatement.setString(3, material);
            preparedStatement.setString(4, para);
            preparedStatement.setString(5, tamanio);
            preparedStatement.setString(6, color);
            preparedStatement.setString(7, forma);
            preparedStatement.setInt(8, cantidadPorPaquete);
            preparedStatement.setString(9, portaGlobo);
            preparedStatement.setInt(10, cantidad);
            preparedStatement.setDouble(11, precio);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Globo guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el globo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        CrearGlobo crearGlobo = new CrearGlobo();
        crearGlobo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearGlobo.setVisible(true);
    }
}