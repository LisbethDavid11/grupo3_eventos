package Arreglos;

import Objetos.Conexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class EditarArreglo extends JFrame {
    private JTextField campoNombre, campoPrecio;
    private JRadioButton radioButtonSi, radioButtonNo;
    private JButton botonGuardar, botonCancelar, botonCargarImagen;
    private JPanel panel, panelImg;
    private JLabel labelImagen;
    private JLabel lbl0, lbl1, lbl2, lbl3;
    private String imagePath = "";
    private final EditarArreglo actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;

    public EditarArreglo(int arregloId) {
        super("");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();

        this.id = arregloId;
        mostrar();

        // Establecer ancho y alto deseados para el panelImg
        int panelImgWidth = 200;
        int panelImgHeight = 200;

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);

        // Establecer las dimensiones en el panelImg
        panelImg.setPreferredSize(panelImgSize);
        panelImg.setMaximumSize(panelImgSize);
        panelImg.setMinimumSize(panelImgSize);
        panelImg.setSize(panelImgSize);

        // Configurar el layout del panelImg como GridBagLayout
        panelImg.setLayout(new GridBagLayout());

        // Configurar restricciones de diseño para la etiqueta de imagen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
        panelImg.add(labelImagen, gbc);

        // Color de fondo del panel
        panel.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));

        // Color de texto para los JTextField y JRadioButton
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

        // Color de texto de los botones y JRadioButton
        botonCancelar.setForeground(Color.WHITE);
        botonGuardar.setForeground(Color.WHITE);
        botonCargarImagen.setForeground(Color.WHITE);
        radioButtonSi.setForeground(textColor);
        radioButtonNo.setForeground(textColor);

        // Color de fondo de los botones y JRadioButton
        botonCancelar.setBackground(darkColorCyan);
        botonGuardar.setBackground(darkColorAqua);
        botonCargarImagen.setBackground(darkColorRosado);
        radioButtonSi.setBackground(panel.getBackground());
        radioButtonNo.setBackground(panel.getBackground());

        botonCancelar.setFocusPainted(false);
        botonGuardar.setFocusPainted(false);
        botonCargarImagen.setFocusPainted(false);
        radioButtonSi.setFocusPainted(false);
        radioButtonNo.setFocusPainted(false);

        // Aplica el margen al botón
        botonGuardar.setBorder(margin);
        botonCancelar.setBorder(margin);
        botonCargarImagen.setBorder(margin);

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

        // Inicializar JRadioButtons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonNo);
        buttonGroup.add(radioButtonSi);

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
                        if (trimmedLength >= 100) {
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
                if (integerLength > 5 || decimalLength > 2) {
                    e.consume(); // Ignorar el carácter si se excede el límite de dígitos
                    return;
                }
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Volver a la lista de floristerías
                ListaArreglo listaArreglo = new ListaArreglo();
                listaArreglo.setVisible(true);
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
                    mensaje += "Nombre\n";
                }

                if (campoPrecio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Precio\n";
                }

                if (!radioButtonSi.isSelected() && !radioButtonNo.isSelected()) {
                    validacion++;
                    mensaje += "Disponibilidad\n";
                }

                if (imagePath.isEmpty()) {
                    validacion++;
                    mensaje += "Imagen\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String nombre = campoNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 100) {
                        JOptionPane.showMessageDialog(null, "El nombre debe tener como máximo 100 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
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

                if (!radioButtonSi.isSelected() && !radioButtonNo.isSelected()) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar la disponibilidad.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (imagePath.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltó cargar la imagen.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                guardarArreglo();
            }
        });

        botonCargarImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int seleccion = fileChooser.showOpenDialog(actual);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    imagePath = file.getAbsolutePath();
                    ImageIcon originalIcon = new ImageIcon(imagePath);

                    // Obtener las dimensiones originales de la imagen
                    int originalWidth = originalIcon.getIconWidth();
                    int originalHeight = originalIcon.getIconHeight();

                    // Obtener las dimensiones del JPanel
                    int panelImgWidth = panelImg.getWidth();
                    int panelImgHeight = panelImg.getHeight();

                    // Calcular la escala para ajustar la imagen al JPanel
                    double scale = Math.min((double) panelImgWidth / originalWidth, (double) panelImgHeight / originalHeight);

                    // Calcular las nuevas dimensiones de la imagen redimensionada
                    int scaledWidth = (int) (originalWidth * scale);
                    int scaledHeight = (int) (originalHeight * scale);

                    // Redimensionar la imagen manteniendo su proporción
                    Image resizedImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    // Crear un nuevo ImageIcon a partir de la imagen redimensionada
                    ImageIcon scaledIcon = new ImageIcon(resizedImage);

                    labelImagen.setIcon(scaledIcon);
                }
            }
        });
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM arreglos WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                campoNombre.setText(resultSet.getString("nombre"));
                double precio = resultSet.getDouble("precio");
                DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                symbols.setDecimalSeparator('.');
                DecimalFormat decimalFormat = new DecimalFormat("0.00", symbols);
                campoPrecio.setText(decimalFormat.format(precio));

                String imagenNombre = resultSet.getString("imagen");
                String imagenPath = "img/arreglos/" + imagenNombre;

                try {
                    File imagenFile = new File(imagenPath);
                    if (imagenFile.exists()) {
                        ImageIcon imagenIcono = new ImageIcon(imagenPath);
                        Image imagenOriginal = imagenIcono.getImage();

                        // Ajusta el tamaño de la imagen para que se ajuste al panelImg
                        Image imagenRedimensionada = imagenOriginal.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                        ImageIcon imagenFinal = new ImageIcon(imagenRedimensionada);

                        labelImagen.setIcon(imagenFinal);
                        imagePath = imagenPath;
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al cargar la imagen del arreglo.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                String disponible = resultSet.getString("disponible");
                if (disponible.equals("Si")) {
                    radioButtonSi.setSelected(true);
                } else {
                    radioButtonNo.setSelected(true);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener los datos del arreglo.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        botonGuardar.setIcon(new ImageIcon("update_icon_white.png"));
    }

    private void guardarArreglo() {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            Random rand = new Random();
            int num = rand.nextInt(900) + 100;
            String imagenNombre = format.format(new Date()) + num + ".jpg";
            String destino = "img/arreglos/" + imagenNombre;

            File imagenOrigen = new File(imagePath);
            File imagenDestino = new File(destino);
            Files.copy(imagenOrigen.toPath(), imagenDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);

            String nombre = campoNombre.getText().trim();
            double precio = Double.parseDouble(campoPrecio.getText().trim());
            String disponible = radioButtonSi.isSelected() ? "Sí" : "No";

            PreparedStatement statement = mysql.prepareStatement("UPDATE arreglos SET nombre = ?, precio = ?, imagen = ?, disponible = ? WHERE id = ?;");
            statement.setString(1, nombre);
            statement.setDouble(2, precio);
            statement.setString(3, imagenNombre);
            statement.setString(4, disponible);
            statement.setInt(5, id);
            statement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Arreglo actualizado correctamente.", "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE);

            ListaArreglo listaArreglo = new ListaArreglo();
            listaArreglo.setVisible(true);
            actual.dispose();
        } catch (IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el arreglo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditarArreglo frame = new EditarArreglo(1); // Reemplazar el valor 1 por el ID del arreglo que se desea editar
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
