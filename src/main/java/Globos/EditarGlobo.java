package Globos;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class EditarGlobo extends JFrame {
    private JTextField campoCodigo, campoPrecio, campoForma, campoTamanio, campoColor, campoCantidadPorPaquete;
    private JRadioButton radioButtonAire, radioButtonHelio, radioButtonAmbos, radioButtonSiNecesita, radioButtonNoNecesita;
    private JButton botonGuardar, botonCancelar, botonLimpiar;
    private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
    private JLabel lbl0, lbl1, lbl2, lbl3;
    private JComboBox comboBoxTipoEvento, comboBoxMaterial;
    private String imagePath = "";
    private JPanel panelImg;
    private JLabel imagenLabel;
    private JButton botonCargarImagen;
    private EditarGlobo actual = this;
    private Conexion sql;
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
    private int idGlobo;

    public EditarGlobo(int idGlobo) {
        super("");
        setSize(750, 650);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        this.idGlobo = idGlobo;
        sql = new Conexion();

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
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelImg.add(imagenLabel, gbc);

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

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));

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

        radioButtonAire.setForeground(textColor);
        radioButtonAire.setBackground(panel1.getBackground());
        radioButtonAire.setFocusPainted(false);

        radioButtonHelio.setForeground(textColor);
        radioButtonHelio.setBackground(panel1.getBackground());
        radioButtonHelio.setFocusPainted(false);

        radioButtonAmbos.setForeground(textColor);
        radioButtonAmbos.setBackground(panel1.getBackground());
        radioButtonAmbos.setFocusPainted(false);

        radioButtonSiNecesita.setForeground(textColor);
        radioButtonSiNecesita.setBackground(panel1.getBackground());
        radioButtonSiNecesita.setFocusPainted(false);

        radioButtonNoNecesita.setForeground(textColor);
        radioButtonNoNecesita.setBackground(panel1.getBackground());
        radioButtonNoNecesita.setFocusPainted(false);

        comboBoxTipoEvento.setBackground(panel1.getBackground());
        comboBoxMaterial.setBackground(panel1.getBackground());

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);

        lbl0.setBorder(margin);
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

                if (imagePath.isEmpty()) {
                    validacion++;
                    mensaje += "Imagen\n";
                }

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
                    actualizarGlobos(idGlobo);
                    ListaGlobos listaGlobos = new ListaGlobos();
                    listaGlobos.setVisible(true);
                    actual.dispose();
                }
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
                        "¿Estás seguro de que deseas reestablecer los datos del globo?",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        new Object[]{}, // no options
                        null
                );

                // Crea un JDialog
                JDialog dialog = optionPane.createDialog("Limpiar");

                btnYes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        campoCodigo.setText("");
                        campoPrecio.setText("");
                        campoForma.setText("");
                        campoTamanio.setText("");
                        campoColor.setText("");
                        campoCantidadPorPaquete.setText("");
                        buttonGroup.clearSelection();
                        buttonGroup2.clearSelection();
                        comboBoxTipoEvento.setSelectedIndex(0);
                        comboBoxMaterial.setSelectedIndex(0);
                        cargarDatosGlobo(idGlobo);
                        dialog.dispose();
                    }
                });

                btnNo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
                optionPane.setOptions(new Object[]{btnYes, btnNo});
                dialog.setVisible(true);
            }
        });

        cargarDatosGlobo(idGlobo);
    }

    private void cargarDatosGlobo(int idGlobo) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM globos WHERE id = ?")) {

            preparedStatement.setInt(1, idGlobo);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String imagen = resultSet.getString("imagen");
                String codigo = resultSet.getString("codigo_globo");
                String forma = resultSet.getString("forma");
                String tamanio = resultSet.getString("tamano");
                String color = resultSet.getString("color");
                int cantidadPorPaquete = resultSet.getInt("cantidad_paquete");
                String para = resultSet.getString("para");
                String portaGlobo = resultSet.getString("porta_globo");
                String tipoEvento = resultSet.getString("tipo");
                String material = resultSet.getString("material");
                int cantidad = resultSet.getInt("cantidad");
                double precio = resultSet.getDouble("precio");

                // Mostrar la imagen (reemplaza 'mostrarImagen(imagen)' con el código que uses para mostrar imágenes)
                mostrarImagen(imagen);

                // Establecer los valores en los campos de texto y otros componentes
                campoCodigo.setText(codigo);
                campoForma.setText(forma);
                campoTamanio.setText(tamanio);
                campoColor.setText(color);
                campoCantidadPorPaquete.setText(Integer.toString(cantidadPorPaquete));
                if (para.equals("Helio")) {
                    radioButtonHelio.setSelected(true);
                } else if (para.equals("Aire")) {
                    radioButtonAire.setSelected(true);
                } else {
                    radioButtonAmbos.setSelected(true);
                }
                if (portaGlobo.equals("1")) {
                    radioButtonSiNecesita.setSelected(true);
                } else {
                    radioButtonNoNecesita.setSelected(true);
                }
                comboBoxTipoEvento.setSelectedItem(tipoEvento);
                comboBoxMaterial.setSelectedItem(material);
                campoCantidadPorPaquete.setText(Integer.toString(cantidad));
                campoPrecio.setText(String.format("%.2f", precio).replace(",", "."));
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el globo con el ID proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
                // Si lo necesitas, realiza alguna acción adicional aquí
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al cargar los datos del globo", "Error", JOptionPane.ERROR_MESSAGE);
            // Si lo necesitas, realiza alguna acción adicional aquí
        }
    }


    private void actualizarGlobos(int id) {
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

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE globos SET imagen = ?, codigo_globo = ?, tipo = ?, material = ?, para = ?, tamano = ?, color = ?, forma = ?, cantidad_paquete = ?, porta_globo = ?, precio = ? WHERE id = ?")) {

            // Generar el nombre de la imagen
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            String fechaActual = dateFormat.format(new Date());
            String nombreImagen = "imagen " + fechaActual + " " + generarNumeroAleatorio(0, 9999);

            // Guardar la imagen en la carpeta
            String rutaImagen = nombreImagen + obtenerExtensionImagen(imagePath);
            File destino = new File("img/globos/" + rutaImagen);

            try {
                // Copiar el archivo seleccionado a la ubicación destino
                Path origenPath = Path.of(imagePath);
                Files.copy(origenPath, destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            preparedStatement.setString(1, rutaImagen);
            preparedStatement.setString(2, codigo);
            preparedStatement.setString(3, tipoEvento);
            preparedStatement.setString(4, material);
            preparedStatement.setString(5, para);
            preparedStatement.setString(6, tamanio);
            preparedStatement.setString(7, color);
            preparedStatement.setString(8, forma);
            preparedStatement.setInt(9, cantidadPorPaquete);
            preparedStatement.setString(10, portaGlobo);
            preparedStatement.setDouble(11, precio);
            preparedStatement.setInt(12, id);  // Set the ID for the WHERE clause
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Datos actualizados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar los datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private String obtenerExtensionImagen(String imagePath) {
        int extensionIndex = imagePath.lastIndexOf(".");
        if (extensionIndex != -1) {
            return imagePath.substring(extensionIndex);
        }
        return "";
    }

    private void limpiarCampos(int idGlobo) {
        this.idGlobo = idGlobo;
        cargarDatosGlobo(idGlobo);
    }

    private String generarNumeroAleatorio(int min, int max) {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(max - min + 1) + min;
        return String.format("%04d", numeroAleatorio);
    }

    private void mostrarImagen(String imagen) {
        imagePath = "img/globos/" + imagen;

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

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EditarGlobo frame = new EditarGlobo(1); // Reemplaza el 1 con el ID de la globo que deseas editar
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}