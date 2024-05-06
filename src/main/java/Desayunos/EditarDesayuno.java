/**
 * Editar Desayuno.java
 *
 * Editar Desayuno
 *
 * @author Alejandra Aroca
 * @version 1.0
 * @since 2024-05-05
 */

package Desayunos;

import Manualidades.PreviewImagen;
import Materiales.TextPrompt;
import Modelos.*;
import Objetos.*;
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
import java.util.*;
import java.util.Date;
import java.util.List;

public class EditarDesayuno extends JFrame {
    // Campos de texto
    private JTextField campoPrecioDesayuno;
    private JTextField campoManoObra;
    private JTextField campoNombre;

    // Área de texto
    private JTextArea campoDescripcion;

    // Botones
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JButton botonCargarImagen;
    private JButton agregarMaterialButton;
    private JButton agregarGloboButton;
    private JButton agregarTarjetasButton;
    private JButton agregarFloresButton;
    private JButton agregarButton;
    private JButton cancelarButton;
    private JButton botonLimpiar;

    // Paneles
    private JPanel jpanelImagen;
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private JPanel jpanelDescripcion;

    // Etiquetas
    private JLabel lbl0;
    private JLabel lbl2;
    private JLabel lbl4;
    private JLabel jlabelImagen;
    private JLabel jtextCatidadTotalMateriales;
    private JLabel lbl8;
    private JLabel lbl9;
    private JLabel lbl10;

    // Tabla de materiales
    private JTable jtableMateriales;

    // ComboBox de proveedores
    private JComboBox<ProveedorDesayuno> jcbProveedores;

    // Campo de búsqueda de materiales
    private JTextField campoBusquedaMateriales;

    // Placeholder para el campo de búsqueda de materiales
    private TextPrompt placeholder;

    // Otras variables
    private int selectTabla = 1;
    private List<PoliProducto> productosListTemporal = new ArrayList<>();
    private List<PoliProducto> productosListOrigina = new ArrayList<>();
    private List<PoliMaterial> materialList = new ArrayList<>();
    private List<PoliMaterial> materialListTemporal = new ArrayList<>();
    private List<PoliFlor> floristeriaList = new ArrayList<>();
    private List<PoliFlor> floristeriaListTemporal = new ArrayList<>();
    private List<PoliTarjeta> tarjetaList = new ArrayList<>();
    private List<PoliTarjeta> tarjetaListTemporal = new ArrayList<>();
    private List<PoliGlobo> globoList = new ArrayList<>();
    private List<PoliGlobo> globolListTemporal = new ArrayList<>();
    private Map<String, String> tiposDescripcion = new HashMap<>();
    private Map<String, String> tiposTablas = new HashMap<>();
    private String imagePath = "";
    private EditarDesayuno actual = this;
    private Conexion sql;
    private Connection mysql;
    private String nombreFile;
    private String urlDestino = "";
    private DefaultTableModel modeloProductos;

    // Fuentes y colores
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

    // Colores para el botón "Amber"
    Color primaryColorAmber = new Color(255, 193, 7); // Amber primario
    Color lightColorAmber = new Color(255, 213, 79); // Amber claro
    Color darkColorAmber = new Color(255, 160, 0); // Amber oscuro

    // Colores para el botón "Verde lima"
    Color primaryColorVerdeLima = new Color(205, 220, 57); // Verde lima primario
    Color lightColorVerdeLima = new Color(220, 237, 200); // Verde lima claro
    Color darkColorVerdeLima = new Color(139, 195, 74); // Verde lima oscuro

    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
    Color textColor = Color.decode("#212121");

    private Desayuno originalDesayuno;
    private int id;
    private String nombreArchivoImagen;
    private boolean imagenCambiada = false; // Variable de control
    private List<PoliProductosGeneral> productosSeleccionados = new ArrayList<>();

    // Establecer ancho y alto deseados para el panelImg
    int panelImgWidth = 70;
    int panelImgHeight = 150;

    public EditarDesayuno(Desayuno desayuno, int id) {
        super("");
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        this.id = id;
        this.originalDesayuno = desayuno;
        sql = new Conexion();
        configurarTablaMateriales();
        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        tiposDescripcion.put("F", "floristeria");
        tiposDescripcion.put("T", "tarjeta");
        tiposDescripcion.put("G", "globo");
        tiposDescripcion.put("M", "material");

        tiposTablas.put("F", "floristeria");
        tiposTablas.put("T", "tarjetas");
        tiposTablas.put("G", "globos");
        tiposTablas.put("M", "materiales");

        jcbProveedores.addItem(new ProveedorDesayuno(0, "", "")); // Agregar mensaje inicial
        cargarProveedores();

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

        JTableHeader header = jtableMateriales.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(Color.decode("#263238"));

        botonCancelar.setBackground(darkColorBlue);
        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setBorder(margin);

        botonGuardar.setBackground(darkColorAqua);
        botonGuardar.setForeground(Color.WHITE);
        botonGuardar.setFocusPainted(false);
        botonGuardar.setBorder(margin);

        botonCargarImagen.setBackground(darkColorPink);
        botonCargarImagen.setForeground(Color.WHITE);
        botonCargarImagen.setFocusPainted(false);

        botonLimpiar.setBackground(darkColorRed);
        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.setFocusPainted(false);
        botonLimpiar.setBorder(margin);

        cancelarButton.setBackground(darkColorRed);
        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setFocusPainted(false);

        agregarButton.setBackground(darkColorCyan);
        agregarButton.setForeground(Color.WHITE);
        agregarButton.setFocusPainted(false);

        agregarMaterialButton.setBackground(Color.decode("#795548"));
        agregarMaterialButton.setForeground(Color.WHITE);
        agregarMaterialButton.setFocusPainted(false);

        agregarGloboButton.setBackground(Color.decode("#9C27B0"));
        agregarGloboButton.setForeground(Color.WHITE);
        agregarGloboButton.setFocusPainted(false);

        agregarTarjetasButton.setBackground(Color.decode("#E81E12"));
        agregarTarjetasButton.setForeground(Color.WHITE);
        agregarTarjetasButton.setFocusPainted(false);

        agregarFloresButton.setBackground(Color.decode("#2196F3"));
        agregarFloresButton.setForeground(Color.WHITE);
        agregarFloresButton.setFocusPainted(false);

        lbl0.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl4.setForeground(textColor);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);

        lbl0.setFont(fontTitulo);
        lbl8.setFont(font);
        lbl9.setFont(font);
        lbl10.setFont(font);

        // Color de texto para el JTextArea
        campoDescripcion.setForeground(textColor);
        campoDescripcion.setBackground(new Color(215, 215, 215));

        agregarMaterialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarGloboButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosMaterial());
            }
        });

        agregarFloresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarGloboButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosFloristeria());
            }
        });

        agregarGloboButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarGloboButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosGlobo());
            }
        });

        agregarTarjetasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarGloboButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosTarjeta());
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(false);
                agregarButton.setVisible(false);
                cancelarButton.setVisible(false);
                agregarMaterialButton.setVisible(true);
                agregarFloresButton.setVisible(true);
                agregarGloboButton.setVisible(true);
                agregarTarjetasButton.setVisible(true);
                jtableMateriales.setModel(cargarDetallesMateriales());
                actualizarLbl8y10();
                configurarTablaMateriales();
                jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new EditarDesayuno.ButtonRenderer());
                jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new EditarDesayuno.ButtonEditor());
            }
        });

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

        campoPrecioDesayuno.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoPrecioDesayuno.getText();

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

        agregarButton.setVisible(false);
        cancelarButton.setVisible(false);
        campoBusquedaMateriales.setVisible(false);
        campoBusquedaMateriales.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                jtableMateriales.setModel(cargarDatosMaterial());
            }
        });

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
                ListaDesayunos listaDesayuno = new ListaDesayunos();
                listaDesayuno.setVisible(true);
                actual.dispose();
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

                    String directorio = "img/desayunos/";

                    Date fecha = new Date();
                    SimpleDateFormat formatoFechaHora = new SimpleDateFormat("ddMMyyyy_HHmmss");
                    String fechaHora = formatoFechaHora.format(fecha);

                    // Generar un número aleatorio entre 0001 y 9999
                    int numeroAleatorio = (int) (Math.random() * 9999) + 1;
                    String numeroFormateado = String.format("%04d", numeroAleatorio); // Asegura el formato de 4 dígitos

                    nombreFile = "Desayuno_" + fechaHora + " " + numeroFormateado + ".jpg";
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
                            mostrarDialogoPersonalizadoError("Error al guardar la imagen.", Color.decode("#C62828"));
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

        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btnYes = new JButton("Sí");
                JButton btnNo = new JButton("No");

                // Personaliza los botones aquí
                btnYes.setBackground(darkColorAqua);
                btnNo.setBackground(darkColorPink);

                // Personaliza los fondos de los botones aquí
                btnYes.setForeground(Color.WHITE);
                btnNo.setForeground(Color.WHITE);

                // Elimina el foco
                btnYes.setFocusPainted(false);
                btnNo.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Estás seguro de que deseas limpiar la tabla de detalles?",
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
                        limpiarTablaMateriales();
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

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer, List> listas = new HashMap<>();
                listas.put(2, floristeriaList);
                listas.put(3, materialList);
                listas.put(4, globoList);
                listas.put(5, tarjetaList);

                if (jtableMateriales.getSelectedRow() == -1) {
                    // Crea un botón personalizado
                    JButton btnOK = new JButton("OK");
                    btnOK.setBackground(darkColorAqua);
                    btnOK.setForeground(Color.WHITE);
                    btnOK.setFocusPainted(false);

                    // Crea un JOptionPane
                    JOptionPane optionPane = new JOptionPane(
                            "Seleccione una fila para continuar",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.DEFAULT_OPTION,
                            null,
                            new Object[]{}, // no options
                            null
                    );

                    // Añade el botón al JOptionPane
                    optionPane.setOptions(new Object[]{btnOK});

                    // Crea un JDialog y muestra el JOptionPane
                    JDialog dialog = optionPane.createDialog("Seleccionar");

                    // Añade un ActionListener al botón
                    btnOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Cuando se hace clic en el botón, simplemente se cierra el diálogo
                            dialog.dispose();
                        }
                    });

                    // Muestra el diálogo
                    dialog.setVisible(true);

                    return;
                }

                // Obtener la cantidad del material que deseas agregar (por ejemplo, mediante un cuadro de diálogo)
                int cantidadMaterial = obtenerCantidadMaterial();

                // Verifica si la cantidad es válida (por ejemplo, mayor que cero) antes de continuar
                if (cantidadMaterial <= 0) {
                    return;
                }

                // Verificar que el material ya está presente en la lista temporal
                PoliProducto l = (PoliProducto) listas.get(selectTabla).get(jtableMateriales.getSelectedRow());
                String id_material = "";
                int id_materialEntero = 0;

                boolean materialDuplicado = false;

                if (l instanceof PoliFlor p) {
                    id_materialEntero = p.getID();
                    id_material = "F-" + p.getID();
                } else if (l instanceof PoliMaterial p) {
                    id_materialEntero = p.getID();
                    id_material = "M-" + p.getID();
                } else if (l instanceof PoliGlobo p) {
                    id_materialEntero = p.getID();
                    id_material = "G-" + p.getID();
                } else if (l instanceof PoliTarjeta p) {
                    id_materialEntero = p.getID();
                    id_material = "T-" + p.getID();
                }

                for (PoliProducto materialTemporal : productosListTemporal) {
                    String id = materialTemporal.getTipo() + "-" + materialTemporal.getID();
                    // JOptionPane.showMessageDialog(null, id);
                    if (id.equals(id_material)) {
                        materialDuplicado = true;
                        break;
                    }
                }

                if (!materialDuplicado) {
                    // Llamar al método guardarDetalleDesayuno con los tres argumentos
                    guardarDetalleDesayuno(id_materialEntero, cantidadMaterial, l.getTipo());

                    // Crear el material temporal y agregarlo a la lista temporal
                    PoliProductosGeneral materialTemporal = new PoliMaterial();
                    materialTemporal.setID(id_materialEntero);
                    materialTemporal.setNombre(l.getNombre());
                    materialTemporal.setCantidad(cantidadMaterial);
                    materialTemporal.setPrecio(l.getPrecio());
                    materialTemporal.setTipo(l.getTipo());
                    productosListTemporal.add(materialTemporal);

                    campoBusquedaMateriales.setVisible(false);
                    agregarButton.setVisible(false);
                    cancelarButton.setVisible(false);
                    agregarMaterialButton.setVisible(true);
                    agregarFloresButton.setVisible(true);
                    agregarGloboButton.setVisible(true);
                    agregarTarjetasButton.setVisible(true);
                    // Actualizar la tabla con los detalles actualizados
                    jtableMateriales.setModel(cargarDetallesMateriales());
                    jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new EditarDesayuno.ButtonRenderer());
                    jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new EditarDesayuno.ButtonEditor());

                    configurarTablaMateriales();
                    actualizarLbl8y10();
                } else {
                    // Crea un botón personalizado
                    JButton btnOK = new JButton("OK");
                    btnOK.setBackground(darkColorAqua);
                    btnOK.setForeground(Color.WHITE);
                    btnOK.setFocusPainted(false);

                    // Crea un JOptionPane
                    JOptionPane optionPane = new JOptionPane(
                            "El detalle ya está presente en la tabla",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.DEFAULT_OPTION,
                            null,
                            new Object[]{}, // no options
                            null
                    );

                    // Añade el botón al JOptionPane
                    optionPane.setOptions(new Object[]{btnOK});

                    // Crea un JDialog y muestra el JOptionPane
                    JDialog dialog = optionPane.createDialog("Validación");

                    // Añade un ActionListener al botón
                    btnOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Cuando se hace clic en el botón, simplemente se cierra el diálogo
                            dialog.dispose();
                        }
                    });

                    // Muestra el diálogo
                    dialog.setVisible(true);
                }
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                if (imagenCambiada && imagePath.isEmpty()) {
                    validacion++;
                    mensaje += "Imagen\n";
                }

                if (campoNombre.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Nombre del desayuno sorpresa\n";
                }

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Descripción\n";
                }

                if (jcbProveedores.getSelectedIndex() == 0) {
                    validacion++;
                    mensaje += "Seleccionar el proveedor\n";
                }

                if (campoPrecioDesayuno.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Precio del desayuno\n";
                }

                if (campoManoObra.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Mano de obra\n";
                }

                if (jtableMateriales.getRowCount() == 0) {
                    validacion++;
                    mensaje += "La lista de productos\n";
                }

                if (validacion > 0) {
                    mostrarDialogoPersonalizadoError(mensaje, Color.decode("#C62828"));
                    return;
                }

                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        mostrarDialogoPersonalizadoError("La descripción debe tener entre 2 y 200 caracteres.", Color.decode("#C62828"));
                    }
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

                String precioText = campoPrecioDesayuno.getText().trim();
                precioText = precioText.replaceAll(",", ".");

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
                        } else {
                            // Get the value from the JLabel "lbl10" and replace commas with periods
                            String lbl10Text = lbl10.getText().trim();
                            lbl10Text = lbl10Text.replaceAll(",", ".");
                            if (!lbl10Text.isEmpty()) {
                                double lbl10Value = Double.parseDouble(lbl10Text);
                                if (precio <= lbl10Value) {
                                    mostrarDialogoPersonalizadoError("El precio debe ser mayor que el valor total después de gastos de materiales y mano de obra.", Color.decode("#C62828"));
                                    return;
                                }
                            } else {
                                mostrarDialogoPersonalizadoError("El valor total después de gastos de materiales y mano de obra es inválido.", Color.decode("#C62828"));
                                return;
                            }
                        }
                    }
                }

                String manoObraText = campoManoObra.getText().trim();
                if (manoObraText.isEmpty()) {
                    mostrarDialogoPersonalizadoError("Faltó ingresar el precio por mano de obra.", Color.decode("#C62828"));
                    return;
                } else {
                    if (!manoObraText.matches("\\d{1,5}(\\.\\d{1,2})?")) {
                        mostrarDialogoPersonalizadoError("Precio por mano de obra inválido. Debe tener el formato correcto (ejemplo: 1234 o 1234.56).", Color.decode("#C62828"));
                        return;
                    } else {
                        double precio = Double.parseDouble(manoObraText);
                        if (precio < 1.00 || precio > 99999.99) {
                            mostrarDialogoPersonalizadoError("Precio por mano de obra fuera del rango válido (1.00 - 99999.99).", Color.decode("#C62828"));
                            return;
                        }
                    }
                }

                JButton btnSave = new JButton("Sí");
                JButton btnCancel = new JButton("No");

                // Personaliza los botones aquí
                btnSave.setBackground(darkColorAqua);
                btnCancel.setBackground(darkColorPink);

                // Personaliza los fondos de los botones aquí
                btnSave.setForeground(Color.WHITE);
                btnCancel.setForeground(Color.WHITE);

                // Elimina el foco
                btnSave.setFocusPainted(false);
                btnCancel.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Desea actualizar la información del desayuno sorpresa?",
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
                        if (!imagePath.isEmpty()) {
                            imagenCambiada = true;
                        }
                        actualizarDesayuno();
                        dialog.dispose();
                        ListaDesayunos listaDesayunos = new ListaDesayunos();
                        listaDesayunos.setVisible(true);
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

        jlabelImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (urlDestino.equals("")) {

                } else {
                    JOptionPane.showMessageDialog(null, urlDestino);
                    PreviewImagen imagen = new PreviewImagen(urlDestino);
                    imagen.setVisible(true);
                }
            }
        });

        jtableMateriales.setModel(cargarDetallesMateriales());
        cargarDatosEditar();
        productosListOrigina = cargarListaOrignal();

    }

    // Método para configurar la tabla
    private void configurarTablaMateriales() {
        int columnCount = jtableMateriales.getColumnCount();
        if (columnCount > 0) {
            TableColumnModel columnModel = jtableMateriales.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(30); // Id
            columnModel.getColumn(1).setPreferredWidth(180); // Nombre
            columnModel.getColumn(2).setPreferredWidth(80);  // Precio
            columnModel.getColumn(3).setPreferredWidth(100); // Proveedor

            columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
        }
    }

    // Clase para alinear los datos al centro
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

    // Clase para alinear los datos a la izquierda
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

    // Método para guardar los datos del desayuno
    private void actualizarDesayuno() {
        String nombre = campoNombre.getText().trim();
        String precioManualidadText = campoPrecioDesayuno.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precio_manualidad = Double.parseDouble(precioManualidadText);
        String manoObraText = campoManoObra.getText().replace("L ", "").replace(",", "").replace("_", "");
        double mano_obra = Double.parseDouble(manoObraText);
        String descripcion = campoDescripcion.getText().trim();

        ProveedorDesayuno tipo = (ProveedorDesayuno) jcbProveedores.getModel().getSelectedItem();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE desayunos SET imagen = ?, nombre = ?, descripcion = ?, proveedor_id = ?, precio_desayuno = ?, mano_obra = ? WHERE id = ?")) {

            preparedStatement.setString(1, nombreFile); // Reemplaza "nombreFile" con el nombre de archivo de la imagen.
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, descripcion);
            preparedStatement.setInt(4, tipo.getIdProveedor());
            preparedStatement.setDouble(5, precio_manualidad);
            preparedStatement.setDouble(6, mano_obra);
            preparedStatement.setInt(7, id); // Utiliza el ID proporcionado en el constructor.

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                mostrarDialogoPersonalizadoExito("Desayuno actualizado exitosamente.", Color.decode("#263238"));
            } else {
                mostrarDialogoPersonalizadoError("No se encontró el desayuno para actualizar.", Color.decode("#C62828"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al actualizar el desayuno.", Color.decode("#C62828"));
        }
    }

    // Método para guardar los detalles del desayuno
    private void guardarDetalleDesayuno(int id_material, int cantidad, String tipo) {
        double availableQuantity = obtenerCantidadMaterialDesdeBD(id_material, tipo);

        if (cantidad <= 0) {
            mostrarDialogoPersonalizadoError("La cantidad debe ser mayor a 0", Color.decode("#C62828"));
            return;
        } else if (cantidad > availableQuantity) {
            mostrarDialogoPersonalizadoError("El número ingresado es mayor a la cantidad disponible en la base de datos.", Color.decode("#C62828"));
            return;
        }

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO detalles_desayunos (tipo_detalle, detalle_id, cantidad,precio,desayuno_id) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, tiposDescripcion.get(tipo));
            preparedStatement.setInt(2, id_material);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.setDouble(4, obtenerPrecioMaterialDesdeBD(id_material,tipo));// Obtener el precio del material desde la base de datos
            preparedStatement.setInt(5, this.id);
            preparedStatement.executeUpdate();
            mostrarDialogoPersonalizadoExito("Detalle agregado exitosamente", Color.decode("#263238"));
           } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al agregar el detalle del desayuno", Color.decode("#C62828"));
        }
    }

    // Método para obtener la cantidad desde la base de datos
    private int obtenerCantidadMaterialDesdeBD(int id_material, String tipo) {
        int availableQuantity = 0;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT cantidad FROM " + tiposTablas.get(tipo) + " WHERE id = ?"
             )) {
            preparedStatement.setInt(1, id_material);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                availableQuantity = resultSet.getInt("cantidad");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al intentar obtener la cantidad desde la base de datos", Color.decode("#C62828"));
        }

        return availableQuantity;
    }

    // Método para obtener el precio desde la base de datos
    private double obtenerPrecioMaterialDesdeBD(int id_material, String tipo) {
        double precio = 0.0;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                     (tipo.equals("T") ? "precio_tarjeta" :
                             tipo.equals("D") ? "precio_desayuno" :
                                     tipo.equals("W") ? "precio_manualidad" :
                                             tipo.equals("F") ? "precio" :
                                                     tipo.equals("A") ? "precio" :
                                                             tipo.equals("M") ? "precio" :
                                                                     tipo.equals("G") ? "precio" :
                                                                             "precio_default") +
                     " FROM " +
                     (tipo.equals("T") ? "tarjetas" :
                             tipo.equals("D") ? "desayunos" :
                                     tipo.equals("W") ? "manualidades" :
                                             tipo.equals("F") ? "floristeria" :
                                                     tipo.equals("A") ? "arreglos" :
                                                             tipo.equals("M") ? "materiales" :
                                                                     tipo.equals("G") ? "globos" :
                                                                             "default_table") +
                     " WHERE id = ?")) {
            preparedStatement.setInt(1, id_material);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                precio = resultSet.getDouble(
                        tipo.equals("T") ? "precio_tarjeta" :
                                tipo.equals("D") ? "precio_desayuno" :
                                        tipo.equals("W") ? "precio_manualidad" :
                                                tipo.equals("F") ? "precio" :
                                                        tipo.equals("A") ? "precio" :
                                                                tipo.equals("M") ? "precio" :
                                                                        tipo.equals("G") ? "precio" :
                                                                                "precio_default");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al obtener el precio del producto desde la base de datos.", Color.decode("#C62828"));
        }
        return precio;
    }

    // Método para limpiar los datos de la tabla
    private void limpiarTablaMateriales() {
        PoliModeloProducto emptyModel = new PoliModeloProducto(this.productosListOrigina);
        jtableMateriales.setModel(emptyModel);

        calcularTotalTabla();
        actualizarLbl8y10();
        configurarTablaMateriales();

        jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());
    }

    // Método para eliminar los datos de la tabla
    private void eliminarDetallesMaterialID(int id) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM detalles_desayunos WHERE id = ?")) {
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            // JOptionPane.showMessageDialog(null,"Eliminado con exito");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar los datos de los productos
    private PoliModeloProducto cargarDetallesMateriales() {
        sql = new Conexion();
        productosListTemporal.clear(); // Limpiar la lista antes de agregar los materiales
        selectTabla = 1;

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT detalles_desayunos.*,'F' AS 'tipo', floristeria.nombre AS 'nombre', (detalles_desayunos.cantidad * detalles_desayunos.precio) AS 'total' FROM detalles_desayunos "+
                     " JOIN floristeria ON floristeria.id = detalles_desayunos.detalle_id "+
                     " WHERE detalles_desayunos.desayuno_id = ? AND detalles_desayunos.tipo_detalle = 'floristeria' "+

                     " UNION "+

                     " SELECT detalles_desayunos.*,'T' AS 'tipo',tarjetas.descripcion AS 'nombre', (detalles_desayunos.cantidad * detalles_desayunos.precio) AS 'total' FROM detalles_desayunos "+
                     " JOIN tarjetas ON tarjetas.id = detalles_desayunos.detalle_id "+
                     " WHERE detalles_desayunos.desayuno_id = ? AND detalles_desayunos.tipo_detalle = 'tarjeta' "+

                     " UNION "+

                     " SELECT detalles_desayunos.*,'G' AS 'tipo',globos.codigo_globo AS 'nombre', (detalles_desayunos.cantidad * detalles_desayunos.precio) AS 'total' FROM detalles_desayunos "+
                     " JOIN globos ON globos.id = detalles_desayunos.detalle_id "+
                     " WHERE detalles_desayunos.desayuno_id = ? AND detalles_desayunos.tipo_detalle = 'globo' "+

                     " UNION "+

                     " SELECT detalles_desayunos.*,'M' AS 'tipo',materiales.nombre AS 'nombre', (detalles_desayunos.cantidad * detalles_desayunos.precio) AS 'total' FROM detalles_desayunos "+
                     " JOIN materiales ON materiales.id = detalles_desayunos.detalle_id "+
                     " WHERE detalles_desayunos.desayuno_id = ? AND detalles_desayunos.tipo_detalle = 'material';"
             )
        ) {
            preparedStatement.setInt(1,this.id);
            preparedStatement.setInt(2,this.id);
            preparedStatement.setInt(3,this.id);
            preparedStatement.setInt(4,this.id);

            ResultSet resultSet = preparedStatement.executeQuery();

            double precioTotalMateriales = 0.00;

            while (resultSet.next()) {
                PoliProductosGeneral material = new PoliProductosGeneral();
                material.setIdDetalle(resultSet.getInt("id"));
                material.setID(resultSet.getInt("detalle_id"));
                material.setNombre(resultSet.getString("nombre"));
                material.setCantidad(resultSet.getInt("cantidad"));
                material.setPrecio(resultSet.getDouble("precio"));
                material.setTipo(resultSet.getString("tipo"));
                double total = resultSet.getDouble("total");
                precioTotalMateriales += total;
                productosListTemporal.add(material);
            }



        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos", Color.decode("#C62828"));
            productosListTemporal = new ArrayList<>();
        }

        // Configurar la tabla para mostrar los encabezados de las columnas
        JTableHeader tableHeader = jtableMateriales.getTableHeader();
        tableHeader.setVisible(true);

        // Configurar la tabla para mantener el ordenamiento de filas incluso sin encabezados visibles
        jtableMateriales.setAutoCreateRowSorter(true);

        // Configurar el ancho de las columnas y alineaciones de las celdas
        configurarTablaMateriales();

        return new PoliModeloProducto(productosListTemporal);
    }

    // Método para cargar los datos de la lista original
    private List<PoliProducto> cargarListaOrignal() {
        sql = new Conexion();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT detalles_desayunos.*,'F' AS 'tipo', floristeria.nombre AS 'nombre', (detalles_desayunos.cantidad * detalles_desayunos.precio) AS 'total' FROM detalles_desayunos "+
                             " JOIN floristeria ON floristeria.id = detalles_desayunos.detalle_id "+
                             " WHERE detalles_desayunos.desayuno_id = ? AND detalles_desayunos.tipo_detalle = 'floristeria' "+

                             " UNION "+

                             " SELECT detalles_desayunos.*,'T' AS 'tipo',tarjetas.descripcion AS 'nombre', (detalles_desayunos.cantidad * detalles_desayunos.precio) AS 'total' FROM detalles_desayunos "+
                             " JOIN tarjetas ON tarjetas.id = detalles_desayunos.detalle_id "+
                             " WHERE detalles_desayunos.desayuno_id = ? AND detalles_desayunos.tipo_detalle = 'tarjeta' "+

                             " UNION "+

                             " SELECT detalles_desayunos.*,'G' AS 'tipo',globos.codigo_globo AS 'nombre', (detalles_desayunos.cantidad * detalles_desayunos.precio) AS 'total' FROM detalles_desayunos "+
                             " JOIN globos ON globos.id = detalles_desayunos.detalle_id "+
                             " WHERE detalles_desayunos.desayuno_id = ? AND detalles_desayunos.tipo_detalle = 'globo' "+

                             " UNION "+

                             " SELECT detalles_desayunos.*,'M' AS 'tipo',materiales.nombre AS 'nombre', (detalles_desayunos.cantidad * detalles_desayunos.precio) AS 'total' FROM detalles_desayunos "+
                             " JOIN materiales ON materiales.id = detalles_desayunos.detalle_id "+
                             " WHERE detalles_desayunos.desayuno_id = ? AND detalles_desayunos.tipo_detalle = 'material';"
             )
        ) {
            preparedStatement.setInt(1,this.id);
            preparedStatement.setInt(2,this.id);
            preparedStatement.setInt(3,this.id);
            preparedStatement.setInt(4,this.id);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<PoliProducto> li = new ArrayList<>();
            while (resultSet.next()) {
                PoliProducto material = new PoliProductosGeneral();
                material.setIdDetalle(resultSet.getInt("id"));
                material.setID(resultSet.getInt("detalle_id"));
                material.setNombre(resultSet.getString("nombre"));
                material.setCantidad(resultSet.getInt("cantidad"));
                material.setPrecio(resultSet.getDouble("precio"));
                material.setTipo(resultSet.getString("tipo"));
                li.add(material);
            }

            return li;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            return new ArrayList<>();
        }
    }

    // Método para cargar los datos de las flores
    private PoliModeloFlor cargarDatosFloristeria() {
        sql = new Conexion();
        floristeriaList.clear();
        selectTabla = 2;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT f.*, p.empresaProveedora " +
                             "FROM floristeria f " +
                             "JOIN Proveedores p ON f.proveedor_id = p.id " +
                             "WHERE f.nombre LIKE CONCAT('%', ?, '%') OR p.empresaProveedora LIKE CONCAT('%', ?, '%')"
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());
            preparedStatement.setString(2, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PoliFlor floristeria = new PoliFlor();
                floristeria.setID(resultSet.getInt("id"));
                floristeria.setNombre(resultSet.getString("nombre"));
                floristeria.setCantidad(resultSet.getInt("cantidad"));
                floristeria.setPrecio(resultSet.getDouble("precio"));
                floristeria.setTipo("F");
                floristeriaList.add(floristeria);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            floristeriaList = new ArrayList<>();
        }

        PoliModeloFlor modeloFlor = new PoliModeloFlor(floristeriaList, sql);
        jtableMateriales.setModel(modeloFlor);
        configurarTablaMateriales();
        return modeloFlor;
    }

    // Método para cargar los datos de los materiales
    private PoliModeloMaterial cargarDatosMaterial() {
        sql = new Conexion();
        materialList.clear();
        selectTabla = 3;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM materiales WHERE nombre LIKE CONCAT('%', ?, '%')"
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PoliMaterial material = new PoliMaterial();
                material.setID(resultSet.getInt("id"));
                material.setNombre(resultSet.getString("nombre"));
                material.setCantidad(resultSet.getInt("cantidad"));
                material.setPrecio(resultSet.getDouble("precio"));
                material.setTipo("M");
                materialList.add(material);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            materialList = new ArrayList<>();
        }

        PoliModeloMaterial modeloMaterial = new PoliModeloMaterial(materialList, sql);
        jtableMateriales.setModel(modeloMaterial);
        configurarTablaMateriales();
        return modeloMaterial;
    }

    // Método para cargar los datos de los globos
    private PoliModeloGlobo cargarDatosGlobo() {
        sql = new Conexion();
        globoList.clear();
        selectTabla = 4;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM globos WHERE codigo_globo LIKE CONCAT('%', ?, '%')"
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PoliGlobo globo = new PoliGlobo();
                globo.setID(resultSet.getInt("id"));
                globo.setNombre(resultSet.getString("codigo_globo"));
                globo.setCantidad(resultSet.getInt("cantidad"));
                globo.setPrecio(resultSet.getDouble("precio"));
                globo.setTipo("G");
                globoList.add(globo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            globoList = new ArrayList<>();
        }

        PoliModeloGlobo modeloGlobo = new PoliModeloGlobo(globoList, sql);
        jtableMateriales.setModel(modeloGlobo);
        configurarTablaMateriales();
        return modeloGlobo;
    }

    // Método para cargar los datos de las tarjetas
    private PoliModeloTarjeta cargarDatosTarjeta() {
        sql = new Conexion();
        tarjetaList.clear();
        selectTabla = 5;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM tarjetas WHERE ocasion LIKE CONCAT('%', ?, '%')"
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PoliTarjeta tarjeta = new PoliTarjeta();
                tarjeta.setID(resultSet.getInt("id"));
                tarjeta.setNombre(resultSet.getString("descripcion"));
                tarjeta.setOcasion(resultSet.getString("ocasion"));
                tarjeta.setCantidad(resultSet.getInt("cantidad"));
                tarjeta.setPrecio(resultSet.getDouble("precio_tarjeta"));
                tarjeta.setTipo("T");
                tarjetaList.add(tarjeta);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            tarjetaList = new ArrayList<>();
        }

        PoliModeloTarjeta modeloTarjeta = new PoliModeloTarjeta(tarjetaList, sql);
        jtableMateriales.setModel(modeloTarjeta);
        configurarTablaMateriales();
        return modeloTarjeta;
    }

    // Método para obtener la cantidad de material ingresada por el usuario
    private int obtenerCantidadMaterial() {
        final int[] cantidadMaterial = new int[] {-1};

        JTextField field = new JTextField();

        JButton btnOK = new JButton("Aceptar");
        btnOK.setBackground(darkColorAqua);
        btnOK.setForeground(Color.WHITE);
        btnOK.setFocusPainted(false);

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.setBackground(darkColorPink);
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);

        Object[] message = {
                "Ingrese la cantidad del detalle seleccionado:", field
        };

        Object[] options = {btnOK, btnCancel};

        JOptionPane optionPane = new JOptionPane(
                message,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                options,
                btnOK // default option is btnOK
        );

        JDialog dialog = optionPane.createDialog("Cantidad");

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cantidadMaterial[0] = Integer.parseInt(field.getText());

                    if (cantidadMaterial[0] < 1) {
                        showErrorDialog("La cantidad debe ser mayor o igual a 1");
                        cantidadMaterial[0] = -1;
                    }

                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    showErrorDialog("La cantidad debe ser un número válido");
                }
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);

        return cantidadMaterial[0];
    }

    // Método para mostrar un mensaje en caso de error
    private void showErrorDialog(String message) {
        JButton btnOK = new JButton("Aceptar");
        btnOK.setBackground(darkColorAqua);
        btnOK.setForeground(Color.WHITE);
        btnOK.setFocusPainted(false);

        JOptionPane optionPane = new JOptionPane(
                message,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{},
                null
        );

        optionPane.setOptions(new Object[]{btnOK});

        JDialog dialog = optionPane.createDialog("Error");

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.setVisible(true);
    }

    // Método para cargar los proveedores
    private void cargarProveedores() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, empresaProveedora, nombreVendedor FROM Proveedores");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ProveedorDesayuno proveedorDesayuno = new ProveedorDesayuno(resultSet.getInt("id"),resultSet.getString("empresaProveedora"),resultSet.getString("nombreVendedor"));

                jcbProveedores.addItem(proveedorDesayuno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Clase para renderizar el botón
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

    // Clase para el botón
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;
        private int row, col;
        private JTable table;

        public ButtonEditor() {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(this);
            button.setForeground(Color.WHITE);
            button.setBackground(darkColorPink);
            button.setFocusPainted(false);
            button.setBorder(margin);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("X");
            this.table = table;
            this.row = row;
            this.col = column;
            return button;
        }

        public Object getCellEditorValue() {
            return "X";
        }

        public void actionPerformed(ActionEvent e) {
            if (table != null) {
                int modelRow = table.convertRowIndexToModel(row);
                PoliModeloProducto model = (PoliModeloProducto) table.getModel();
                PoliProducto producto = model.getProducto(modelRow);
                model.removeRow(modelRow);

                eliminarDetallesMaterialID(producto.getIdDetalle());

                jtableMateriales.setModel(model);

                calcularTotalTabla();
                actualizarLbl8y10();
                configurarTablaMateriales();

                jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
                jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());
            }
        }

        // Método para calcular la suma de una columna específica en un modelo de tabla
        private double calcularTotalColumna(DefaultTableModel model, int columnIndex) {
            double sumaTotal = 0.0;
            for (int i = 0; i < model.getRowCount(); i++) {
                Object valorCelda = model.getValueAt(i, columnIndex);
                if (valorCelda != null) {
                    String totalStr = valorCelda.toString();
                    double total = extraerValorNumerico(totalStr);
                    sumaTotal += total;
                }
            }
            return sumaTotal;
        }
    }

    // Método para calcular el total de la tabla
    private double calcularTotalTabla() {
        double sumaTotal = 0.0;
        PoliModeloProducto modelo = (PoliModeloProducto) jtableMateriales.getModel();

        List<PoliProducto> lista = modelo.getList();

        for (PoliProducto p: lista) {
            try {

                sumaTotal += p.getCantidad() * p.getPrecio();

            } catch (NumberFormatException e) {
                System.err.println("Se encontró un formato de número no válido. Se omite el cálculo para la fila " + p.getID());
                System.err.println("Valor que causa el error: " + p.getNombre());
            }
        }

        // Actualizar el lbl8 con el total calculado
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String sumaTotalFormateado = decimalFormat.format(sumaTotal);
        lbl8.setText(" " + sumaTotalFormateado);

        return sumaTotal;
    }

    // Método para extraer el valor númerico del total
    private double extraerValorNumerico(String valor) {
        String valorNumerico = valor.replace(',', '.');
        try {
            return Double.parseDouble(valorNumerico);
        } catch (NumberFormatException e) {
            System.err.println("Se encontró un formato de número no válido. No se puede convertir a double: " + valor);
            return 0.0;
        }
    }

    // Método para actualizar datos
    private void actualizarLbl8y10() {
        double totalTabla = calcularTotalTabla();
        lbl8.setText(String.format("%.2f", totalTabla));

        // Verifica si campoManoObra contiene un valor válido
        double manoObra = 0.0;
        String manoObraText = campoManoObra.getText().replace(",", ".");
        if (!manoObraText.isEmpty()) {
            try {
                manoObra = Double.parseDouble(manoObraText);
            } catch (NumberFormatException e) {
                // Maneja la excepción si el valor de mano de obra no es válido
            }
        }

        lbl9.setText(String.format("%.2f", manoObra));

        double total = totalTabla + manoObra;
        lbl10.setText(String.format("%.2f", total));
    }

    // Método para escalar la imagen al label
    private ImageIcon escalarImagen(Image imagenOriginal, int anchoPanel, int altoPanel) {
        double proporcionAncho = (double) anchoPanel / imagenOriginal.getWidth(null);
        double proporcionAlto = (double) altoPanel / imagenOriginal.getHeight(null);

        double proporcionEscalamiento = Math.min(proporcionAncho, proporcionAlto);
        int anchoEscalado = (int) (imagenOriginal.getWidth(null) * proporcionEscalamiento);
        int altoEscalado = (int) (imagenOriginal.getHeight(null) * proporcionEscalamiento);

        Image imagenEscalada = imagenOriginal.getScaledInstance(anchoEscalado, altoEscalado, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    // Método para cargar los datos de editar
    public void cargarDatosEditar() {
        campoDescripcion.setText(this.originalDesayuno.getDescripcion());
        campoPrecioDesayuno.setText(String.valueOf(this.originalDesayuno.getPrecio_desayuno()));
        campoManoObra.setText(String.valueOf(this.originalDesayuno.getMano_obra()));
        campoNombre.setText(String.valueOf(this.originalDesayuno.getNombre()));

        // Obtener el ID del proveedor de la base de datos
        int proveedorId = this.originalDesayuno.getProveedor_id();

        // Iterar a través de los elementos del JComboBox y seleccionar el proveedor por su ID
        for (int i = 0; i < jcbProveedores.getItemCount(); i++) {
            ProveedorDesayuno proveedor = (ProveedorDesayuno) jcbProveedores.getItemAt(i);
            if (proveedor.getIdProveedor() == proveedorId) {
                jcbProveedores.setSelectedItem(proveedor);
                break; // Salir del bucle una vez que se haya encontrado el proveedor
            }
        }

        jtableMateriales.setModel(cargarDetallesMateriales());

        String imagenNombre = this.originalDesayuno.getImagen();
        nombreArchivoImagen = imagenNombre;  // Asigna el valor del nombre de la imagen
        String imagenPath = "img/desayunos/" + imagenNombre;

        try {
            File imagenFile = new File(imagenPath);
            if (imagenFile.exists()) {
                Image imagenOriginal = ImageIO.read(imagenFile);

                // Utiliza las dimensiones del panel para el escalado
                int anchoPanel = jpanelImagen.getWidth();
                int altoPanel = jpanelImagen.getHeight();

                // Escalar la imagen usando la función escalarImagen
                ImageIcon imagenIcono = escalarImagen(imagenOriginal, anchoPanel * 3, altoPanel);

                // Configura la etiqueta con la imagen escalada
                jlabelImagen.setIcon(imagenIcono);
                jlabelImagen.setHorizontalAlignment(SwingConstants.CENTER);
            } else {
                System.out.println("No se encontró la imagen: " + imagenPath);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen: " + e.getMessage());
        }

        calcularTotalTabla();
        actualizarLbl8y10();
        configurarTablaMateriales();

        jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());

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