/**
 * EditarArreglo.java
 *
 * Editar Arreglo
 *
 * @author Elsa Ramos
 * @version 1.0
 * @since 2024-05-05
 */

package Arreglos;

import Objetos.Conexion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    // Campos de texto
    private JTextField campoNombre;
    private JTextField campoPrecio;

    // Botones de radio
    private JRadioButton radioButtonSi;
    private JRadioButton radioButtonNo;

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
    private JPanel panel3;

    // Etiquetas
    private JLabel labelImagen;
    private JLabel label0;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;

    // Ruta de la imagen
    private String imagePath = "";

    // Referencia al formulario actual
    private final EditarArreglo actual = this;

    // Conexión a la base de datos
    private Conexion sql;
    private Connection mysql;

    // Tamaño deseado para el panelImg
    private int panelImgWidth = 220;
    private int panelImgHeight = 220;

    // ID del elemento
    private int id;

    // Indicador de cambio de imagen
    private boolean imageChanged = false;

    // Colores
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    Color textColor = Color.decode("#212121");

    // Fuentes
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 17);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

    // Colores para botones
    Color primaryColorCyan = new Color(0, 188, 212);
    Color lightColorCyan = new Color(77, 208, 225);
    Color darkColorCyan = new Color(0, 151, 167);

    Color primaryColorAqua = new Color(0, 150, 136);
    Color lightColorAqua = new Color(77, 182, 172);
    Color darkColorAqua = new Color(0, 121, 107);

    Color primaryColorRosado = new Color(233, 30, 99);
    Color lightColorRosado = new Color(240, 98, 146);
    Color darkColorRosado = new Color(194, 24, 91);

    // Nombre de archivo y destino para la imagen
    private String nombreFile;
    private String urlDestino = "";

    // Margen para componentes
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public EditarArreglo(int arregloId) {
        super("");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();

        this.id = arregloId;
        mostrar();

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

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonNo);
        buttonGroup.add(radioButtonSi);

        panel.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));

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

        radioButtonSi.setForeground(textColor);
        radioButtonSi.setBackground(panel.getBackground());
        radioButtonSi.setFocusPainted(false);

        radioButtonNo.setForeground(textColor);
        radioButtonNo.setBackground(panel.getBackground());
        radioButtonNo.setFocusPainted(false);

        label0.setForeground(textColor);
        label1.setForeground(textColor);
        label2.setForeground(textColor);
        label3.setForeground(textColor);
        label0.setFont(fontTitulo);

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaArreglos listaArreglo = new ListaArreglos();
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
                    mostrarDialogoPersonalizadoError(mensaje, Color.decode("#C62828"));
                    return;
                }

                String nombre = campoNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 100) {
                        mostrarDialogoPersonalizadoError("El nombre debe tener como máximo 100 caracteres.", Color.decode("#C62828"));
                        return;
                    }

                    if (!nombre.matches("[a-zA-ZñÑ]{2,}(\\s[a-zA-ZñÑ]+\\s*)*")) {
                        mostrarDialogoPersonalizadoError("El nombre debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de nombre no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String precioText = campoPrecio.getText().trim();
                if (precioText.isEmpty()) {
                    mostrarDialogoPersonalizadoError("Faltó ingresar el precio.", Color.decode("#C62828"));
                    return;
                } else {
                    if (!precioText.matches("\\d{1,5}(\\.\\d{1,2})?")) {
                        mostrarDialogoPersonalizadoError("Precio inválido. Debe tener el formato correcto (ejemplo: 1234 o 1234.56).", Color.decode("#C62828"));
                        return;
                    } else {
                        double precio = Double.parseDouble(precioText);
                        if (precio < 1.00 || precio > 99999.99) {
                            mostrarDialogoPersonalizadoError("Precio fuera del rango válido (1.00 - 99999.99).", Color.decode("#C62828"));
                            return;
                        }
                    }
                }

                if (!radioButtonSi.isSelected() && !radioButtonNo.isSelected()) {
                    mostrarDialogoPersonalizadoError("Debe seleccionar la disponibilidad.", Color.decode("#C62828"));
                    return;
                }

                if (imagePath.isEmpty()) {
                    mostrarDialogoPersonalizadoError("Faltó cargar la imagen.", Color.decode("#C62828"));
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
                        "¿Desea actualizar la información del arreglo?",
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
                        actualizarArreglo();
                        dialog.dispose();
                        ListaArreglos listaArreglo = new ListaArreglos();
                        listaArreglo.setVisible(true);
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
                    imageChanged = true;
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
                        "¿Estás seguro de que deseas reestablecer los datos del arreglo?",
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
                        imagePath = ""; // Restablecer la ruta de la imagen

                        buttonGroup.clearSelection();
                        mostrar(); // Vuelve a cargar los datos originales
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

    // Método para cargar los datos del arreglo
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
                        // Cargar la imagen sin redimensionar
                        ImageIcon imagenIcono = new ImageIcon(imagenPath);
                        labelImagen.setIcon(imagenIcono);

                        Image imagenOriginal = imagenIcono.getImage();

                        // Calcular la escala para ajustar la imagen al tamaño deseado
                        double scale = Math.min((double) panelImgWidth / imagenIcono.getIconWidth(), (double) panelImgHeight / imagenIcono.getIconHeight());

                        // Calcular las nuevas dimensiones de la imagen redimensionada
                        int scaledWidth = (int) (imagenIcono.getIconWidth() * scale);
                        int scaledHeight = (int) (imagenIcono.getIconHeight() * scale);

                        // Redimensionar la imagen manteniendo su proporción
                        Image imagenRedimensionada = imagenOriginal.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_FAST);
                        ImageIcon imagenFinal = new ImageIcon(imagenRedimensionada);

                        // Establecer el tamaño del panel de imagen (panelImg)
                        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);
                        panelImg.setPreferredSize(panelImgSize);
                        panelImg.setMaximumSize(panelImgSize);
                        panelImg.setMinimumSize(panelImgSize);
                        panelImg.setSize(panelImgSize);

                        labelImagen.setIcon(imagenFinal);
                        imagePath = imagenPath;


                    }
                } catch (Exception e) {
                    mostrarDialogoPersonalizadoError("Error al cargar la imagen del arreglo.", Color.decode("#C62828"));


                }

                String disponible = resultSet.getString("disponible");
                if (disponible.equals("Si")) {
                    radioButtonSi.setSelected(true);
                } else {
                    radioButtonNo.setSelected(true);
                }
            }
        } catch (SQLException e) {
            mostrarDialogoPersonalizadoError("Error al obtener los datos del arreglo.", Color.decode("#C62828"));
        }
    }

    // Método para actualizar los datos del arreglo
    private void actualizarArreglo() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            String fechaActual = dateFormat.format(new Date());
            String nombreImagen = "Arreglo " + fechaActual + " " + generarNumeroAleatorio(0, 9999);

            String rutaImagen = nombreImagen + obtenerExtensionImagen(imagePath);
            File destino = new File("img/arreglos/" + rutaImagen);

            try {
                // Copiar el archivo seleccionado a la ubicación destino
                Path origenPath = Path.of(imagePath);
                Files.copy(origenPath, destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                mostrarDialogoPersonalizadoError("Error al guardar la imagen.", Color.decode("#C62828"));
                return;
            }

            String nombre = campoNombre.getText().trim();
            String precioText = campoPrecio.getText().replace("L ", "").replace(",", "").replace("_", "");
            double precio = Double.parseDouble(precioText);
            String disponible = radioButtonSi.isSelected() ? "Si" : "No";

            try (Connection connection = sql.conectamysql();
                 PreparedStatement preparedStatement = connection.prepareStatement("UPDATE arreglos SET nombre = ?, precio = ?, imagen = ?, disponible = ? WHERE id = ?")) {

                preparedStatement.setString(1, nombre);
                preparedStatement.setDouble(2, precio);
                preparedStatement.setString(3, rutaImagen);
                preparedStatement.setString(4, disponible);
                preparedStatement.setInt(5, id);
                preparedStatement.executeUpdate();

                mostrarDialogoPersonalizadoExito("Arreglo actualizado exitosamente.", Color.decode("#263238"));
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarDialogoPersonalizadoError("Error al actualizar el arreglo.", Color.decode("#C62828"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al actualizar el arreglo.", Color.decode("#C62828"));
        }
    }

    // Método para obtener la extensión de la imagen
    private String obtenerExtensionImagen(String imagePath) {
        int extensionIndex = imagePath.lastIndexOf(".");
        if (extensionIndex != -1) {
            return imagePath.substring(extensionIndex);
        }
        return "";
    }

    // Método para generar un número aleatorio para el nombre de la imagen
    private String generarNumeroAleatorio(int min, int max) {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(max - min + 1) + min;
        return String.format("%04d", numeroAleatorio);
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
                    EditarArreglo frame = new EditarArreglo(1); // Reemplazar el valor 1 por el ID del arreglo que se desea editar
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
