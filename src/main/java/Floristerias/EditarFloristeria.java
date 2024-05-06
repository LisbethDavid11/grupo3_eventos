/**
 * EditarFloristeria.java
 *
 * Editar Floristeria
 *
 * @author Alejandra Aroca
 * @version 1.0
 * @since 2024-05-05
 */

package Floristerias;

import Objetos.Conexion;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditarFloristeria extends JFrame {
    // Campos de texto
    private JTextField campoNombre;
    private JTextField campoPrecio;

    // ComboBox de proveedor
    private JComboBox<String> comboBoxProveedor;

    // Botones
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JButton botonCargarImagen;
    private JButton botonLimpiar;

    // Paneles
    private JPanel panel;
    private JPanel panelImg;
    private JPanel panel1;
    private JPanel panel2;

    // Etiquetas
    private JLabel imagenLabel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label0;

    // Ruta de la imagen
    private String imagePath = "";

    // Referencia a la ventana de edición de floristería actual
    private EditarFloristeria actual = this;

    // Conexión a la base de datos
    private Conexion sql;

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
    private String nombreFile;
    private String urlDestino = "";
    private int idFloristeria;
    private int panelImgWidth = 200;
    private int panelImgHeight = 200;

    public EditarFloristeria(int idFloristeria) {
        super("");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);

        this.idFloristeria = idFloristeria;
        sql = new Conexion();

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
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelImg.add(imagenLabel, gbc);

        comboBoxProveedor.addItem("Seleccione un proveedor"); // Agregar mensaje inicial
        cargarProveedores();

        // Color de fondo del panel
        panel.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));

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
                        e.consume(); // Ignorar el carácter si se excede la cantidad de dígitos después del punto decimal
                        return;
                    }
                }
            }
        });

        panel.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));

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

        botonCargarImagen.setForeground(Color.WHITE);
        botonCargarImagen.setBackground(darkColorRosado);
        botonCargarImagen.setFocusPainted(false);
        botonCargarImagen.setBorder(margin);

        label0.setForeground(textColor);
        label1.setForeground(textColor);
        label2.setForeground(textColor);
        label3.setForeground(textColor);
        label0.setFont(fontTitulo);

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaFloristerias listaFloristeria = new ListaFloristerias();
                listaFloristeria.setVisible(true);
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

                String proveedorText = comboBoxProveedor.getSelectedItem().toString();
                if (proveedorText.equals("Seleccione un proveedor")) {
                    validacion++;
                    mensaje += "Proveedor\n";
                }

                if (imagePath.isEmpty()) {
                    validacion++;
                    mensaje += "Imagen\n";
                }

                if (validacion > 0) {
                    mostrarDialogoPersonalizadoError(mensaje, Color.decode("#C62828"));
                    return;
                }

                String nombre = campoNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 100) {
                        mostrarDialogoPersonalizadoError("El nombre de la flor debe tener como máximo 100 caracteres.", Color.decode("#C62828"));
                        return;
                    }

                    if (!nombre.matches("[a-zA-ZñÑ]{2,}(\\s[a-zA-ZñÑ]+\\s*)*")) {
                        mostrarDialogoPersonalizadoError("El nombre de la flor debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El nombre de la flor no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String precioText = campoPrecio.getText().trim();
                if (precioText.isEmpty()) {
                    mostrarDialogoPersonalizadoError("Faltó ingresar el precio.", Color.decode("#C62828"));
                    return;
                } else {
                    if (!precioText.matches("\\d{1,5}(\\.\\d{1,2})?")) {
                        mostrarDialogoPersonalizadoError("Precio inválido. Debe tener el formato correcto (ejemplo: 12345 o 123.45).", Color.decode("#C62828"));
                        return;
                    } else {
                        double precio = Double.parseDouble(precioText);
                        if (precio < 1.00 || precio > 99999.99) {
                            mostrarDialogoPersonalizadoError("Precio fuera del rango válido (1.00 - 99999.99).", Color.decode("#C62828"));
                            return;
                        }
                    }
                }

                String proveedors = comboBoxProveedor.getSelectedItem().toString();
                if (proveedors.equals("Seleccione un proveedor")) {
                    mostrarDialogoPersonalizadoError("Debe seleccionar un proveedor.", Color.decode("#C62828"));
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
                        "¿Desea actualizar la información de la flor?",
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
                        guardarFloristeria();
                        dialog.dispose();
                        ListaFloristerias listaFloristerias = new ListaFloristerias();
                        listaFloristerias.setVisible(true);
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

        botonCargarImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("FileChooser.openButtonText", "Cargar");
                UIManager.put("FileChooser.cancelButtonText", "Cancelar");
                UIManager.put("FileChooser.lookInLabelText", "Ver en");
                UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
                UIManager.put("FileChooser.filesOfTypeLabelText", "Archivos del tipo");
                UIManager.put("FileChooser.upFolderToolTipText", "Subir un nivel");
                UIManager.put("FileChooser.homeFolderToolTipText", "Escritorio");
                UIManager.put("FileChooser.newFolderToolTipText", "Crear nueva carpeta");
                UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
                UIManager.put("FileChooser.newFolderButtonText", "Crear nueva carpeta");
                UIManager.put("FileChooser.renameFileButtonText", "Renombrar archivo");
                UIManager.put("FileChooser.deleteFileButtonText", "Eliminar archivo");
                UIManager.put("FileChooser.filterLabelText", "Tipo de archivo");
                UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalles");
                UIManager.put("FileChooser.fileSizeHeaderText", "Tamaño");
                UIManager.put("FileChooser.fileDateHeaderText", "Fecha de modificación");

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar imagen"); // Cambiar título del diálogo

                fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "bmp", "gif"));

                int seleccion = fileChooser.showOpenDialog(actual);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    imagePath = file.getAbsolutePath();

                    String directorio = "img/floristeria/";

                    Date fecha = new Date();
                    SimpleDateFormat formatoFechaHora = new SimpleDateFormat("ddMMyyyy_HHmmss");
                    String fechaHora = formatoFechaHora.format(fecha);

                    // Generar un número aleatorio entre 0001 y 9999
                    int numeroAleatorio = (int) (Math.random() * 9999) + 1;
                    String numeroFormateado = String.format("%04d", numeroAleatorio); // Asegura el formato de 4 dígitos

                    nombreFile = "Flor_" + fechaHora + " " + numeroFormateado + ".jpg";
                    urlDestino = directorio + nombreFile;

                    File directorioDestino = new File(directorio);
                    if (!directorioDestino.exists()) {
                        directorioDestino.mkdirs(); // Crea la carpeta si no existe
                    }

                    File finalDirectorio = new File(urlDestino);

                    try {
                        BufferedImage imagen = ImageIO.read(new File(imagePath));
                        boolean resultado = ImageIO.write(imagen, "jpg", finalDirectorio);

                        if (!resultado) {
                            mostrarDialogoPersonalizadoError("Error al guardar la imagen", Color.decode("#C62828"));
                            return; // Detiene la ejecución adicional si falla el guardado
                        }
                    } catch (IOException ex) {
                        mostrarDialogoPersonalizadoError("Error al procesar la imagen: " + ex.getMessage(), Color.decode("#C62828"));
                        ex.printStackTrace();
                        return;
                    }

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

                    imagenLabel.setIcon(scaledIcon);
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
                        "¿Estás seguro de que deseas reestablecer los datos de la flor?",
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
                        campoPrecio.setText("");
                        campoNombre.setText("");
                        comboBoxProveedor.setSelectedIndex(0);
                        cargarDatosFloristeria(idFloristeria);
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

        cargarDatosFloristeria(idFloristeria);
    }

    // Método para cargar los datos de la floristeria
    private void cargarDatosFloristeria(int idFloristeria) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM floristeria WHERE id = ?")) {

            preparedStatement.setInt(1, idFloristeria);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String imagen = resultSet.getString("imagen");
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                int proveedorId = resultSet.getInt("proveedor_id");

                // Mostrar la imagen
                mostrarImagen(imagen);

                // Establecer los valores en los campos de texto y el combobox
                campoNombre.setText(nombre);
                campoPrecio.setText(String.format("%.2f", resultSet.getDouble("precio")).replace(",", "."));
                seleccionarProveedor(proveedorId);
            } else {
                mostrarDialogoPersonalizadoError("Error al cargar la flor", Color.decode("#C62828"));
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al cargar los datos de la flor.", Color.decode("#C62828"));
            dispose();
        }
    }

    // Método para mostrar la imagen
    private void mostrarImagen(String imagen) {
        imagePath = "img/floristeria/" + imagen;

        File file = new File(imagePath);
        if (file.exists()) {
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

            imagenLabel.setIcon(scaledIcon);
        }
    }

    // Método para seleccionar proveedor
    private void seleccionarProveedor(int proveedorId) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, empresaProveedora, nombreVendedor FROM Proveedores WHERE id = ?")) {

            preparedStatement.setInt(1, proveedorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String empresaProveedora = resultSet.getString("empresaProveedora");
                String nombreVendedor = resultSet.getString("nombreVendedor");
                String proveedorText = proveedorId + " - " + empresaProveedora + " - " + nombreVendedor;
                comboBoxProveedor.setSelectedItem(proveedorText);
            } else {
                mostrarDialogoPersonalizadoError("El proveedor no ha sido encontrado", Color.decode("#C62828"));
                dispose();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al cargar el proveedor asociado a la flor", Color.decode("#C62828"));
            dispose();
        }
    }

    // Método para cargar los datos de los proveedores
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

    // Método para guardar los datos de la floristeria
    private void guardarFloristeria() {
        String nombre = campoNombre.getText().trim();
        String precioText = campoPrecio.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precio = Double.parseDouble(campoPrecio.getText().trim());
        String proveedorText = comboBoxProveedor.getSelectedItem().toString();
        int idProveedor = Integer.parseInt(proveedorText.split(" - ")[0]);

            try (Connection connection = sql.conectamysql();
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE floristeria SET imagen = ?, nombre = ?, precio = ?, proveedor_id = ? WHERE id = ?")) {
                preparedStatement.setString(1, nombreFile);
                preparedStatement.setString(2, nombre);
                preparedStatement.setDouble(3, precio);
                preparedStatement.setInt(4, idProveedor);
                preparedStatement.setInt(5, idFloristeria);
                preparedStatement.executeUpdate();

                mostrarDialogoPersonalizadoExito("Flor actualizada exitosamente", Color.decode("#263238"));
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarDialogoPersonalizadoError("Error al actualizar la flor.", Color.decode("#C62828"));
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

    // Método Principal
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditarFloristeria frame = new EditarFloristeria(1); // Reemplaza el 1 con el ID de la floristería que deseas editar
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
