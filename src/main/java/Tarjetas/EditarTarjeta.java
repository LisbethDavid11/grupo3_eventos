package Tarjetas;

import Modelos.ModeloMaterial;
import Modelos.ModeloProducto;
import Objetos.Conexion;
import Objetos.Material;
import Objetos.Tarjeta;
import Objetos.TarjetaDetalle;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EditarTarjeta extends JFrame {

    private JTextField campoPrecioTarjeta;
    private JTextArea campoDescripcion;
    private JRadioButton radioButtonSi;
    private JRadioButton radioButtonNo;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JPanel jpanelImagen, panel1, panel2, panel3, panel5, panel6;
    private JLabel lbl0;
    private JLabel lbl2;
    private JLabel lbl4;
    private JButton botonCargarImagen;
    private JButton agregarMaterialButton;
    private JTable jtableMateriales;
    private JLabel jlabelImagen;
    private JScrollPane jscrollMateriales, panel4;
    private JButton agregarButton;
    private JTextField campoBusquedaMateriales;
    private JButton cancelarButton;
    private JComboBox<String> jcbOcasion;
    private JPanel jpanelDescripcion;
    private JLabel jtextCatidadTotalMateriales;
    private JLabel lbl8;
    private JButton botonLimpiar;
    private JPanel panel7;
    private JTextField campoManoObra;
    private JLabel lbl9;
    private JLabel lbl10;
    private JPanel panel8;
    private List<Material> materialList = new ArrayList<>();
    private String imagePath = "";
    private EditarTarjeta actual = this;
    private Conexion sql;
    private String nombreFile;
    private String urlDestino = "";
    private DefaultTableModel modeloProductos;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre, proveedor o precio", campoBusquedaMateriales);

    private List<Material> materialListTemporal = new ArrayList<>();

    private Tarjeta originalTarjeta;

    Color textColor = Color.decode("#212121");
    Color darkColorCyan = new Color(0, 150, 136);
    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
    Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);

    public EditarTarjeta(Tarjeta tarjeta) {
        super("");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.originalTarjeta = tarjeta;

        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        sql = new Conexion();

        // Establecer ancho y alto deseados para el paneldescripcion
        int panelDesWidth = 80;
        int panelDesHeight = 100;

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelDesSize = new Dimension(panelDesWidth, panelDesHeight);

        // Establecer las dimensiones en el jpanelDescripcion
        jpanelDescripcion.setPreferredSize(panelDesSize);
        jpanelDescripcion.setMaximumSize(panelDesSize);
        jpanelDescripcion.setMinimumSize(panelDesSize);
        jpanelDescripcion.setSize(panelDesSize);

        // Establecer ancho y alto deseados para el panelImg
        int panelImgWidth = 70;
        int panelImgHeight = 150;

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);

        // Establecer las dimensiones en el panelImg
        jpanelImagen.setPreferredSize(panelImgSize);
        jpanelImagen.setMaximumSize(panelImgSize);
        jpanelImagen.setMinimumSize(panelImgSize);
        jpanelImagen.setSize(panelImgSize);

        // Configurar el layout del panelImg como GridBagLayout
        jpanelImagen.setLayout(new GridBagLayout());

        // Configurar restricciones de diseño para la etiqueta de imagen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        jlabelImagen.setHorizontalAlignment(SwingConstants.CENTER);
        jpanelImagen.add(jlabelImagen, gbc);

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));
        panel7.setBackground(Color.decode("#F5F5F5"));
        jpanelDescripcion.setBackground(Color.decode("#F5F5F5"));
        jpanelImagen.setBackground(Color.decode("#F5F5F5"));
        radioButtonSi.setBackground(Color.decode("#F5F5F5"));
        radioButtonNo.setBackground(Color.decode("#F5F5F5"));


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
        agregarMaterialButton.setForeground(Color.DARK_GRAY);
        botonCargarImagen.setForeground(Color.WHITE);
        botonLimpiar.setForeground(Color.WHITE);
        cancelarButton.setForeground(Color.WHITE);
        agregarButton.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonCancelar.setBackground(darkColorBlue);
        botonGuardar.setBackground(darkColorAqua);
        botonCargarImagen.setBackground(darkColorPink);
        agregarMaterialButton.setBackground(lightColorCyan);
        botonLimpiar.setBackground(darkColorRed);
        agregarButton.setBackground(darkColorCyan);
        cancelarButton.setBackground(darkColorRosado);

        botonCancelar.setFocusPainted(false);
        botonGuardar.setFocusPainted(false);
        botonCargarImagen.setFocusPainted(false);
        agregarMaterialButton.setFocusPainted(false);
        botonLimpiar.setFocusPainted(false);
        cancelarButton.setFocusPainted(false);
        agregarButton.setFocusPainted(false);

        // Aplica el margen al botón
        botonGuardar.setBorder(margin);
        botonCancelar.setBorder(margin);
        botonLimpiar.setBorder(margin);
        botonCancelar.setBorder(margin);
        botonLimpiar.setBorder(margin);

        lbl0.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl4.setForeground(textColor);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

        Font font10 = new Font(lbl10.getFont().getName(), lbl10.getFont().getStyle(), 16);
        lbl8.setFont(font10);
        lbl9.setFont(font10);
        lbl10.setFont(font10);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonNo);
        buttonGroup.add(radioButtonSi);

        // Color de texto para el JTextArea
        campoDescripcion.setForeground(textColor);
        campoDescripcion.setBackground(new Color(215, 215, 215));

        // No seleccionar ningún botón de radio por defecto
        buttonGroup.clearSelection();

        agregarMaterialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               campoBusquedaMateriales.setVisible(true);
               agregarButton.setVisible(true);
               cancelarButton.setVisible(true);
               agregarMaterialButton.setVisible(false);
               jtableMateriales.setModel(cargarDatosMateriales());
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(false);
                agregarButton.setVisible(false);
                cancelarButton.setVisible(false);
                agregarMaterialButton.setVisible(true);
                jtableMateriales.setModel(cargarDetallesMateriales());
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Estás seguro de que deseas limpiar todos los datos del materiale?",
                        "Confirmar limpieza",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );
                if (respuesta == JOptionPane.YES_OPTION) {
                    // Eliminar los detalles temporales
                    materialListTemporal.clear();

                    // Eliminar los detalles de materiales de la base de datos
                    eliminarDetallesMaterial();

                    // Limpiar la tabla
                    limpiarTablaMateriales();

                    // Actualizar el total de dinero en el campo de texto
                    lbl8.setText("0.00");

                    lbl10.setText("0.00");

                    // Crear un nuevo modelo de la tabla con la lista de materiales vacía
                    ModeloProducto nuevoModelo = new ModeloProducto(new ArrayList<>(), sql);

                    // Establecer el nuevo modelo en la tabla
                    jtableMateriales.setModel(nuevoModelo);

                    // Actualizar los totales después de limpiar la tabla
                    calcularTotalTabla();
                }
            }
        });


        campoPrecioTarjeta.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoPrecioTarjeta.getText();

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

        campoManoObra.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoManoObra.getText();

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
                if (texto.length() == 0 && Character.isLowerCase(e.getKeyChar())) {
                    e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                }

                // Permitir números, letras, espacios, punto, coma y tildes
                if (!Character.isLetterOrDigit(e.getKeyChar()) && !Character.isSpaceChar(e.getKeyChar()) && e.getKeyChar() != '.' && e.getKeyChar() != ',' && !Character.isWhitespace(e.getKeyChar()) && !Character.isIdeographic(e.getKeyChar())) {
                    e.consume(); // Ignorar el evento si no es una letra, número, espacio, punto, coma o tilde
                }
            }
        });

        // Listener para el campoManoObra
        campoManoObra.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizarLbl8y10();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizarLbl8y10();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizarLbl8y10();
            }
        });



        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    ListaTarjetas listaTarjeta = new ListaTarjetas();
                    listaTarjeta.setVisible(true);
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

                if (!radioButtonSi.isSelected() && !radioButtonNo.isSelected()) {
                    validacion++;
                    mensaje += "Disponibilidad\n";
                }

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Descripción\n";
                }

                if (jcbOcasion.getSelectedIndex() == 0) {
                    validacion++;
                    mensaje += "Ocasión\n";
                }

                if (campoPrecioTarjeta.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Precio de la tarjeta\n";
                }

                if (campoManoObra.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Mano de obra\n";
                }

                if (jtableMateriales.getRowCount() == 0) {
                    validacion++;
                    mensaje += "Los materiales\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        JOptionPane.showMessageDialog(null, "La descripción debe tener entre 2 y 200 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String precioText = campoPrecioTarjeta.getText().trim();
                // Replace commas with periods to handle decimal separator
                precioText = precioText.replaceAll(",", ".");

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
                        } else {
                            // Get the value from the JLabel "lbl10" and replace commas with periods
                            String lbl10Text = lbl10.getText().trim();
                            lbl10Text = lbl10Text.replaceAll(",", ".");
                            if (!lbl10Text.isEmpty()) {
                                double lbl10Value = Double.parseDouble(lbl10Text);
                                if (precio <= lbl10Value) {
                                    JOptionPane.showMessageDialog(null, "El precio debe ser mayor que el valor total después de gastos de materiales y mano de obra.", "Validación", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "El valor en lbl10 no es válido.", "Validación", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                }


                String manoObraText = campoManoObra.getText().trim();
                if (manoObraText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltó ingresar el precio por mano de obra.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!manoObraText.matches("\\d{1,5}(\\.\\d{1,2})?")) {
                        JOptionPane.showMessageDialog(null, "Precio por mano de obra inválido. Debe tener el formato correcto (ejemplo: 1234 o 1234.56).", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        double precio = Double.parseDouble(manoObraText);
                        if (precio < 1.00 || precio > 99999.99) {
                            JOptionPane.showMessageDialog(null, "Precio por mano de obra fuera del rango válido (1.00 - 99999.99).", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea guardar la información de la tarjeta?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    guardarMateriales();
                    ListaTarjetas listaTarjeta = new ListaTarjetas();
                    listaTarjeta.setVisible(true);
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

                    String directorio = "img/tarjetas/";

                    Date fecha = new Date();
                    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    
                    nombreFile  = formatoFecha.format(fecha)+".jpg" ;
                    urlDestino = directorio + nombreFile;

                    try {
                        File finalDirectorio = new File(urlDestino);

                        BufferedImage imagen = ImageIO.read(new File(imagePath));

                        ImageIO.write(imagen, "jpg", finalDirectorio);

                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }


                    ImageIcon originalIcon = new ImageIcon(imagePath);

                    // Obtener las dimensiones originales de la imagen
                    int originalWidth = originalIcon.getIconWidth();
                    int originalHeight = originalIcon.getIconHeight();

                    // Obtener las dimensiones del JPanel
                    int panelImgWidth = jpanelImagen.getWidth();
                    int panelImgHeight = jpanelImagen.getHeight();

                    // Calcular la escala para ajustar la imagen al JPanel
                    double scale = Math.min((double) panelImgWidth / originalWidth, (double) panelImgHeight / originalHeight);

                    // Calcular las nuevas dimensiones de la imagen redimensionada
                    int scaledWidth = (int) (originalWidth * scale);
                    int scaledHeight = (int) (originalHeight * scale);

                    // Redimensionar la imagen manteniendo su proporción
                    Image resizedImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    // Crear un nuevo ImageIcon a partir de la imagen redimensionada
                    ImageIcon scaledIcon = new ImageIcon(resizedImage);

                    jlabelImagen.setIcon(scaledIcon);
                }
            }
        });

        jtableMateriales.setModel(cargarDetallesMateriales());
        configurarTablaMateriales();
        agregarButton.setVisible(false);
        cancelarButton.setVisible(false);
        campoBusquedaMateriales.setVisible(false);
        campoBusquedaMateriales.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                jtableMateriales.setModel(cargarDatosMateriales());
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jtableMateriales.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Obtener la cantidad del material que deseas agregar (por ejemplo, mediante un cuadro de diálogo)
                int cantidadMaterial = obtenerCantidadMaterial();

                // Verificar que la cantidad sea válida y no se haya cancelado el cuadro de diálogo
                if (cantidadMaterial >= 1) {
                    // Verificar que la cantidad no sea mayor a la existente en la base de datos
                    int id_material = materialList.get(jtableMateriales.getSelectedRow()).getId();
                    int cantidadExistente = obtenerCantidadExistenteEnBaseDeDatos(id_material);
                    if (cantidadMaterial <= cantidadExistente) {
                        // Verificar que el precio sea mayor o igual a 1
                        double precioMaterial = materialList.get(jtableMateriales.getSelectedRow()).getPrecio();
                        if (precioMaterial >= 1) {
                            // Verificar si el material ya está presente en la lista temporal
                            boolean materialDuplicado = false;
                            for (Material materialTemporal : materialListTemporal) {
                                if (materialTemporal.getId() == id_material) {
                                    materialDuplicado = true;
                                    break;
                                }
                            }

                            if (!materialDuplicado) {
                                // Llamar al método guardarDetalleMaterial con los dos argumentos
                                guardarDetalleMaterial(id_material, cantidadMaterial);

                                // Crear el material temporal y agregarlo a la lista temporal
                                Material materialTemporal = new Material();
                                materialTemporal.setId(id_material);
                                materialTemporal.setNombre(materialList.get(jtableMateriales.getSelectedRow()).getNombre());
                                materialTemporal.setCantidad(cantidadMaterial);
                                materialTemporal.setPrecio(precioMaterial);
                                materialListTemporal.add(materialTemporal);

                                campoBusquedaMateriales.setVisible(false);
                                agregarButton.setVisible(false);
                                cancelarButton.setVisible(false);
                                agregarMaterialButton.setVisible(true);

                                // Actualizar la tabla con los detalles actualizados
                                jtableMateriales.setModel(cargarDetallesMateriales());
                                jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new EditarTarjeta.ButtonRenderer());
                                jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new EditarTarjeta.ButtonEditor());
                                actualizarLbl8y10();
                            } else {
                                JOptionPane.showMessageDialog(null, "El material ya está presente en la tabla", "Validación", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        } else {
                            JOptionPane.showMessageDialog(null, "La cantidad no debe ser mayor a la existente en la base de datos", "Validación", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "La cantidad debe ser mayor o igual a 1", "Validación", JOptionPane.ERROR_MESSAGE);
                    }
                }
        });

        jlabelImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(urlDestino.equals("")){

                }else {
                   // JOptionPane.showMessageDialog(null, urlDestino);
                    PreviewImagen imagen = new PreviewImagen(urlDestino);
                    imagen.setVisible(true);
                }
            }
        });



        cargarDatosEditar();

    }

    public void  cargarDatosEditar(){
        campoDescripcion.setText(this.originalTarjeta.getDescripcion());
        jcbOcasion.getModel().setSelectedItem(this.originalTarjeta.getOcasion());
        campoPrecioTarjeta.setText(this.originalTarjeta.getPrecio_tarjeta()+"");
        campoManoObra.setText(this.originalTarjeta.getMano_obra()+"");

        if (this.originalTarjeta.getDisponible().equals("Si")){
            radioButtonSi.setSelected(true);
        }else {
            radioButtonNo.setSelected(true);
        }

        String directorio = "img/tarjetas/"+this.originalTarjeta.getImagen();
        this.imagePath = directorio;
        this.urlDestino = directorio;
        this.nombreFile = this.originalTarjeta.getImagen();
        jtableMateriales.setModel(cargarDetallesMateriales());

        System.out.println(directorio);
        ImageIcon originalIcon = new ImageIcon(directorio);

        // Obtener las dimensiones originales de la imagen
        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();

        // Obtener las dimensiones del JPanel
        int panelImgWidth = jpanelImagen.getWidth();
        int panelImgHeight = jpanelImagen.getHeight();

        // Calcular la escala para ajustar la imagen al JPanel
        double scale = Math.min((double) panelImgWidth / originalWidth, (double) panelImgHeight / originalHeight);

        // Calcular las nuevas dimensiones de la imagen redimensionada
        int scaledWidth = (int) (originalWidth * scale);
        int scaledHeight = (int) (originalHeight * scale);

        // Redimensionar la imagen manteniendo su proporción
        Image resizedImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        // Crear un nuevo ImageIcon a partir de la imagen redimensionada
        ImageIcon scaledIcon = new ImageIcon(resizedImage);

        jlabelImagen.setIcon(scaledIcon);
        jtableMateriales.setModel(cargarDetallesMateriales());
        jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new EditarTarjeta.ButtonRenderer());
        jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new EditarTarjeta.ButtonEditor());
    }


    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    private int obtenerCantidadMaterial() {
        String input = JOptionPane.showInputDialog(this, "Ingrese la cantidad del material:", "Cantidad", JOptionPane.PLAIN_MESSAGE);
        if (input == null || input.isEmpty()) {
            return 0; // Si el usuario cancela el cuadro de diálogo o no ingresa un valor, retornamos 0
        }

        try {
            int cantidad = Integer.parseInt(input);
            return cantidad;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return 0; // Si el usuario ingresa un valor no numérico, retornamos 0 para que se intente nuevamente
        }
    }

    private int obtenerCantidadExistenteEnBaseDeDatos(int id_material) {
        int cantidadExistente = 0;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT cantidad FROM materiales WHERE id = ?")) {
            preparedStatement.setInt(1, id_material);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                cantidadExistente = resultSet.getInt("cantidad");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener la cantidad del material desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return cantidadExistente;
    }

    private void guardarMateriales() {
        String precioTarjetaText = campoPrecioTarjeta.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precio_tarjeta = Double.parseDouble(precioTarjetaText);
        String manoObraText = campoManoObra.getText().replace("L ", "").replace(",", "").replace("_", "");
        double mano_obra = Double.parseDouble(manoObraText);
        String descripcion = campoDescripcion.getText().trim();
        String disponibilidad = radioButtonSi.isSelected() ? "Si" : "No";
        int cantidad = 0;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE tarjetas SET ocasion = ?, disponible = ?, descripcion = ?, imagen = ?, cantidad = ?, precio_tarjeta = ?, mano_obra = ? WHERE id = ?",Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, jcbOcasion.getModel().getSelectedItem().toString());
            preparedStatement.setString(2, disponibilidad);
            preparedStatement.setString(3, descripcion);
            preparedStatement.setString(4, nombreFile); // Reemplaza "nombreFile" con el nombre de archivo de la imagen.
            preparedStatement.setInt(5, cantidad);
            preparedStatement.setDouble(6, precio_tarjeta);
            preparedStatement.setDouble(7, mano_obra);
            preparedStatement.setInt(8, this.originalTarjeta.getId());
            preparedStatement.executeUpdate();
/*
            try (PreparedStatement prepared = connection.prepareStatement(
                    "SELECT * FROM tarjetas_detalles WHERE id_tarjeta = ?")) {
                prepared.setInt(1, this.originalTarjeta.getId());
                ResultSet rs = prepared.executeQuery();

                while (rs.next()) {
                    int id_material = rs.getInt("id_material");
                    int cantidad_usada = rs.getInt("cantidad");

                    try (PreparedStatement updateStmt = connection.prepareStatement(
                            "UPDATE materiales SET cantidad = cantidad - ? WHERE id = ?")) {
                        updateStmt.setInt(1, cantidad_usada);
                        updateStmt.setInt(2, id_material);
                        updateStmt.executeUpdate();
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            }*/

            JOptionPane.showMessageDialog(null, "Tarjeta guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarDetalleMaterial(int id_material, int cantidad) {
        int cantidadExistente = obtenerCantidadExistenteEnBaseDeDatos(id_material);

        if (cantidad <= 0 || cantidad > cantidadExistente) {
            JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida o excede la cantidad disponible en la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tarjetas_detalles (id_material,id_tarjeta, cantidad, precio) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setInt(1, id_material);
            preparedStatement.setInt(2, this.originalTarjeta.getId());
            preparedStatement.setInt(3, cantidad);
            preparedStatement.setDouble(4, obtenerPrecioMaterialDesdeBD(id_material)); // Obtener el precio del material desde la base de datos
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Detalle agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el detalle de la tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private double obtenerPrecioMaterialDesdeBD(int id_material) {
        double precio = 0.0;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT precio FROM Materiales WHERE id = ?")) {
            preparedStatement.setInt(1, id_material);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                precio = resultSet.getDouble("precio");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el precio del material desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return precio;
    }

    private void limpiarTablaMateriales() {
        materialList.clear();
        DefaultTableModel emptyModel = new DefaultTableModel();
        jtableMateriales.setModel(emptyModel);
    }

    private void eliminarDetallesMaterial() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM tarjetas_detalles WHERE id_tarjeta = ?")) {
            preparedStatement.setInt(1, originalTarjeta.getId()); // Suponiendo que originalTarjeta contiene la tarjeta actual
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ModeloMaterial cargarDatosMateriales() {
        sql = new Conexion();
        materialList.clear();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT m.*, p.empresaProveedora " +
                             "FROM materiales m " +
                             "JOIN Proveedores p ON m.proveedor_id = p.id " +
                             "WHERE (m.nombre LIKE CONCAT('%', ?, '%') OR p.empresaProveedora LIKE CONCAT('%', ?, '%')OR m.precio LIKE CONCAT('%', ?, '%')) "
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());
            preparedStatement.setString(2, campoBusquedaMateriales.getText());
            preparedStatement.setString(3, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setNombre(resultSet.getString("nombre"));
                material.setCantidad(resultSet.getInt("cantidad"));
                material.setPrecio(resultSet.getDouble("precio"));
                material.setDisponible(resultSet.getString("disponible"));
                material.setDescripcion(resultSet.getString("descripcion"));
                material.setProveedorId(resultSet.getInt("proveedor_id"));
                materialList.add(material);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            materialList = new ArrayList<>();
        }

        if (jtableMateriales.getColumnCount() > 0) {
            TableColumn columnId = jtableMateriales.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloMaterial(materialList, sql);
    }

    private double calcularTotalTabla() {
        double sumaTotal = 0.0;

        TableModel modelo = jtableMateriales.getModel();
        if (modelo instanceof ModeloProducto) {
            ModeloProducto modeloProductos = (ModeloProducto) modelo;

            // Iterar por todas las filas del modelo
            for (int i = 0; i < modeloProductos.getRowCount(); i++) {
                try {
                    // Obtener el total de la fila y sumarlo al total general
                    Object valorCelda = modeloProductos.getValueAt(i, 4); // Suponiendo que la columna "total" está en el índice 4 (índice basado en 0)
                    if (valorCelda != null) {
                        String totalStr = valorCelda.toString();
                        double total = extraerValorNumerico(totalStr);
                        sumaTotal += total;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Se encontró un formato de número no válido. Se omite el cálculo para la fila " + i);
                    System.err.println("Valor que causa el error: " + modeloProductos.getValueAt(i, 4));
                }
            }

            // Actualizar el lbl8 con el total calculado
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String sumaTotalFormateado = decimalFormat.format(sumaTotal);
            lbl8.setText(" " + sumaTotalFormateado);

        } else if (modelo instanceof ModeloMaterial) {
            ModeloMaterial modeloMateriales = (ModeloMaterial) modelo;

            // Iterar por todas las filas del modelo
            for (int i = 0; i < modeloMateriales.getRowCount(); i++) {
                try {
                    // Obtener el total de la fila y sumarlo al total general
                    Object valorCelda = modeloMateriales.getValueAt(i, 4); // Suponiendo que la columna "total" está en el índice 4 (índice basado en 0)
                    if (valorCelda != null) {
                        String totalStr = valorCelda.toString();
                        double total = extraerValorNumerico(totalStr);
                        sumaTotal += total;
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Se encontró un formato de número no válido. Se omite el cálculo para la fila " + i);
                    System.err.println("Valor que causa el error: " + modeloMateriales.getValueAt(i, 4));
                }
            }

            // Actualizar el lbl8 con el total calculado
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            String sumaTotalFormateado = decimalFormat.format(sumaTotal);
            lbl8.setText(" " + sumaTotalFormateado);

        } else {
            throw new IllegalStateException("Modelo de tabla desconocido: " + modelo.getClass());
        }

        return sumaTotal;
    }

    private double extraerValorNumerico(String valor) {
        String valorNumerico = valor.replace(',', '.');
        try {
            return Double.parseDouble(valorNumerico);
        } catch (NumberFormatException e) {
            System.err.println("Se encontró un formato de número no válido. No se puede convertir a double: " + valor);
            return 0.0;
        }
    }

    private void actualizarLbl8y10() {
        double totalTabla = calcularTotalTabla();

        // Actualizar lbl8 con el total de la tabla
        lbl8.setText(String.format("%.2f", totalTabla));

        double manoObra = 0.0;
        try {
            manoObra = Double.parseDouble(campoManoObra.getText().replace(",", "."));
        } catch (NumberFormatException e) {

        }

        // Actualizar lbl9 solo con el valor de mano de obra
        lbl9.setText(String.format("%.2f", manoObra));

        // Calcular el total y actualizar lbl10
        double total = totalTabla + manoObra;
        lbl10.setText(String.format("%.2f", total));
    }

    private void configurarTablaMateriales() {
        int columnCount = jtableMateriales.getColumnCount();
        if (columnCount > 0) {
            TableColumnModel columnModel = jtableMateriales.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(20); // Id
            columnModel.getColumn(1).setPreferredWidth(200); // Nombre
            columnModel.getColumn(2).setPreferredWidth(60);  // Precio
            columnModel.getColumn(3).setPreferredWidth(100); // Proveedor
            columnModel.getColumn(4).setPreferredWidth(60);  // Disponible
            columnModel.getColumn(5).setCellEditor(new DefaultCellEditor(new JCheckBox()));
            columnModel.getColumn(5).setCellRenderer(new EditarTarjeta.ButtonRenderer());



            columnModel.getColumn(0).setCellRenderer(new EditarTarjeta.CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new EditarTarjeta.LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new EditarTarjeta.LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new EditarTarjeta.LeftAlignedRenderer());
            columnModel.getColumn(4).setCellRenderer(new EditarTarjeta.CenterAlignedRenderer());
        }
    }

    private ModeloProducto cargarDetallesMateriales() {
        sql = new Conexion();
        materialList.clear(); // Limpiar la lista antes de agregar los materiales

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT tarjetas_detalles.id, materiales.nombre, tarjetas_detalles.cantidad, materiales.precio, tarjetas_detalles.cantidad * materiales.precio AS total " +
                             "FROM tarjetas_detalles " +
                             "JOIN materiales ON materiales.id = tarjetas_detalles.id_material " +
                             "WHERE id_tarjeta = ?"
             )
        ) {
            preparedStatement.setInt(1,this.originalTarjeta.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            double precioTotalMateriales = 0.00;

            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setNombre(resultSet.getString("nombre"));
                material.setCantidad(resultSet.getInt("cantidad"));
                material.setPrecio(resultSet.getDouble("precio"));
                double total = resultSet.getDouble("total");
                precioTotalMateriales += total;
                materialList.add(material);
            }

            for (Material materialTemporal : materialListTemporal) {
                precioTotalMateriales += materialTemporal.getPrecio() * materialTemporal.getCantidad();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            materialList = new ArrayList<>();
        }

        // Configurar la tabla para mostrar los encabezados de las columnas
        JTableHeader tableHeader = jtableMateriales.getTableHeader();
        tableHeader.setVisible(true);

        // Configurar la tabla para mantener el ordenamiento de filas incluso sin encabezados visibles
        jtableMateriales.setAutoCreateRowSorter(true);

        // Configurar el ancho de las columnas y alineaciones de las celdas
        configurarTablaMateriales();

        return new ModeloProducto(materialList, sql);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setForeground(Color.WHITE);
            setBackground(darkColorPink);
            setFocusPainted(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("X");
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;
        private int row;
        private JTable table;

        public ButtonEditor() {
            button = new JButton("X");
            button.addActionListener(this);
            button.setFocusPainted(false);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            return button;
        }

        public Object getCellEditorValue() {
            return "X";
        }

        public void actionPerformed(ActionEvent e) {
            // Realiza la acción necesaria aquí, como eliminar una fila de la tabla y actualizar los datos
            // Acceder al modelo de la tabla usando el modelo que estás utilizando (ModeloProducto)
            ModeloProducto modelo = (ModeloProducto) table.getModel();

            // Obtener el id del material que se va a eliminar (puedes obtenerlo desde el modelo)
            int id_material = materialList.get(row).getId();

            // Eliminar el detalle de tarjeta de la base de datos
            eliminarDetalleTarjeta(id_material);

            // Remover la fila de la tabla
            modelo.removeRow(row);

            // Remover el material de la lista temporal
            Material materialTemporalToRemove = null;
            for (Material materialTemporal : materialListTemporal) {
                if (materialTemporal.getId() == id_material) {
                    materialTemporalToRemove = materialTemporal;
                    break;
                }
            }
            if (materialTemporalToRemove != null) {
                materialListTemporal.remove(materialTemporalToRemove);
            }

            // Asegúrate de llamar a fireEditingStopped() para finalizar la edición y actualizar la interfaz
            fireEditingStopped();
            calcularTotalTabla();
            actualizarLbl8y10();
        }
    }

    private void eliminarDetalleTarjeta(int id_material) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM tarjetas_detalles WHERE  id = ?")) {
            preparedStatement.setInt(1, id_material);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}