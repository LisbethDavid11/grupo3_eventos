package Manualidades;
import Modelos.ModeloMateriales;
import Objetos.Conexion;
import Objetos.Material;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrearManualidad extends JFrame {
    private JTextField campoPrecio, campoNombre, campoBusquedaMateriales;
    private JTextArea campoDescripcion;
    private JButton botonGuardar, botonCancelar, agregarMaterialButton, botonCargarImagen, agregarButton, cancelarButton;
    private JPanel jpanelImagen, panel1, panel2, panel3, panel5, panel6;
    private JTable jtableMateriales;
    private JLabel jlabelImagen, jtextCatidadTotalMateriales, jtMaterialTotaldinero, lbl0, lbl2, lbl4;
    private JComboBox<String> jbcTipo;
    private JPanel jpanelDescripcion;
    private JButton botonLimpiar;
    private JScrollPane jscrollMateriales, panel4;
    private List<Material> materialList = new ArrayList<>();
    private String imagePath = "";
    private CrearManualidad actual = this;
    private Conexion sql;
    private String nombreFile;
    private String urlDestino = "";

    public CrearManualidad() {
        super("");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setContentPane(panel1);

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
        jpanelImagen.setBackground(Color.decode("#F5F5F5"));

        Font fontBold = new Font(jtextCatidadTotalMateriales.getFont().getName(), Font.BOLD, jtMaterialTotaldinero.getFont().getSize());
        jtextCatidadTotalMateriales.setFont(fontBold);
        jtMaterialTotaldinero.setFont(fontBold);


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
        botonCargarImagen.setForeground(Color.DARK_GRAY);
        botonLimpiar.setForeground(Color.WHITE);
        cancelarButton.setForeground(Color.WHITE);
        agregarButton.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonCancelar.setBackground(darkColorCyan);
        botonGuardar.setBackground(darkColorAqua);
        botonCargarImagen.setBackground(lightColorAqua);
        agregarMaterialButton.setBackground(lightColorCyan);
        botonLimpiar.setBackground(darkColorRosado);
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
                        "¿Estás seguro de que deseas limpiar la tabla de materiales?",
                        "Confirmar limpieza",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );
                if (respuesta == JOptionPane.YES_OPTION) {
                    limpiarTablaMateriales();
                    eliminarDetallesMaterial();

                    jtextCatidadTotalMateriales.setText("0");
                    jtMaterialTotaldinero.setText("0.00");
                }
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


        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    ListaManualidad listaManualidad = new ListaManualidad();
                    listaManualidad.setVisible(true);
                    actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                if (campoPrecio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Precio\n";
                }

                if (jbcTipo.getSelectedIndex() == 0) {
                    validacion++;
                    mensaje += "Tipo\n";
                }

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Descripción\n";
                }

                if (jtableMateriales.getRowCount() == 0) {
                    validacion++;
                    mensaje += "Los materiales\n";
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
                        JOptionPane.showMessageDialog(null, "El nombre de la manualidad debe tener como máximo 100 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!nombre.matches("[a-zA-ZñÑ]{2,}(\\s[a-zA-ZñÑ]+\\s*)*")) {
                        JOptionPane.showMessageDialog(null, "El nombre de la manualidad debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El nombre de la manualidad no puede estar vacío.", "Validación", JOptionPane.ERROR_MESSAGE);
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

                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        JOptionPane.showMessageDialog(null, "La descripción debe tener entre 2 y 200 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                    }
                }
                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea guardar la información de la manualidad?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    guardarManualidad();
                    ListaManualidad listaManualidad = new ListaManualidad();
                    listaManualidad.setVisible(true);
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

                    String directorio = "img/manualidades/";

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
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                guardarDetalleMaterial(materialList.get(jtableMateriales.getSelectedRow()).getId());
                campoBusquedaMateriales.setVisible(false);
                agregarButton.setVisible(false);
                cancelarButton.setVisible(false);
                agregarMaterialButton.setVisible(true);
                jtableMateriales.setModel(cargarDetallesMateriales());
            }
        });

        jlabelImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(urlDestino.equals("")){

                }else {
                    JOptionPane.showMessageDialog(null, urlDestino);
                    PreviewImagen imagen = new PreviewImagen(urlDestino);
                    imagen.setVisible(true);
                }
            }
        });
    }

    private void configurarTablaMateriales() {
        TableColumnModel columnModel = jtableMateriales.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20); // Id
        columnModel.getColumn(1).setPreferredWidth(200);  // Nombre
        columnModel.getColumn(2).setPreferredWidth(60); // Precio
        columnModel.getColumn(3).setPreferredWidth(100); // Proveedor
        columnModel.getColumn(4).setPreferredWidth(60); // Disponible

        columnModel.getColumn(0).setCellRenderer(new CrearManualidad.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new CrearManualidad.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new CrearManualidad.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new CrearManualidad.LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new CrearManualidad.CenterAlignedRenderer());
    }

    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class RightAlignedRenderer extends DefaultTableCellRenderer {
        public RightAlignedRenderer() {
            setHorizontalAlignment(RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    private void guardarManualidad() {

        String precioText = campoPrecio.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precio = Double.parseDouble(precioText);
        String descripcion = campoDescripcion.getText().trim();
        String nombre = campoNombre.getText().trim();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO manualidades (imagen, nombre, descripcion, tipo, precio) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, nombreFile);
            preparedStatement.setString(2, nombre);
            preparedStatement.setString(3, descripcion);
            preparedStatement.setString(4, jbcTipo.getModel().getSelectedItem().toString());
            preparedStatement.setDouble(5, precio);
            preparedStatement.executeUpdate();

            ResultSet resultSet = connection.createStatement().executeQuery("SELECT LAST_INSERT_ID()");
            int lasId = 0;
            if (resultSet.next()){
                lasId = resultSet.getInt(1);
            }

            try (Connection mysql = sql.conectamysql();
                 PreparedStatement preparedStatement2 = mysql.prepareStatement(
                         "SELECT distinct * FROM detalles_manualidades "+
                                 " where manualidad_id is null"
                 )
            ) {

                ResultSet resultSet2 = preparedStatement2.executeQuery();
                while (resultSet2.next()) {
                    PreparedStatement prepared = mysql.prepareStatement(
                            "UPDATE `eventos`.`detalles_manualidades` SET `manualidad_id` = ? where id = ?;"
                    );
                    prepared.setInt(1,lasId);
                    prepared.setInt(2,resultSet2.getInt("id"));
                    prepared.executeUpdate();
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
                materialList = new ArrayList<>();
            }


            JOptionPane.showMessageDialog(null, "Manualidad guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la manualidad", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarDetalleMaterial(int material_id) {

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO detalles_manualidades (material_id) VALUES (?)")) {
            preparedStatement.setInt(1, material_id);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Detalle agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el detalle de la manualidad", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private ModeloMateriales cargarDatosMateriales() {
        sql = new Conexion();
        materialList.clear();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT m.*, p.empresaProveedora " +
                             "FROM Materiales m " +
                             "JOIN Proveedores p ON m.proveedor_id = p.id " +
                             "WHERE (m.nombre LIKE CONCAT('%', ?, '%') OR p.empresaProveedora LIKE CONCAT('%', ?, '%')) "
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());
            preparedStatement.setString(2, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setNombre(resultSet.getString("nombre"));
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

        return new ModeloMateriales(materialList, sql);
    }

    private ModeloMateriales cargarDetallesMateriales() {
        sql = new Conexion();
        materialList.clear();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT distinct materiales.* FROM detalles_manualidades " +
                             "join materiales on materiales.id = detalles_manualidades.material_id" +
                             " where manualidad_id is null"
             )
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            int cantidaMateriales = 0;
            double precioTotalMateriales = 0.00;

            while (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setNombre(resultSet.getString("nombre"));
                material.setPrecio(resultSet.getDouble("precio"));
                material.setDisponible(resultSet.getString("disponible"));
                material.setDescripcion(resultSet.getString("descripcion"));
                material.setProveedorId(resultSet.getInt("proveedor_id"));
                precioTotalMateriales += material.getPrecio();
                materialList.add(material);
                cantidaMateriales += 1;
            }
            jtextCatidadTotalMateriales.setText(String.valueOf(cantidaMateriales));
            jtMaterialTotaldinero.setText(String.format("%.2f", precioTotalMateriales));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            materialList = new ArrayList<>();
        }

        if (jtableMateriales.getColumnCount() > 0) {
            TableColumn columnId = jtableMateriales.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloMateriales(materialList, sql);
    }

    private void limpiarTablaMateriales() {
        materialList.clear();
        DefaultTableModel emptyModel = new DefaultTableModel();
        jtableMateriales.setModel(emptyModel);
    }

    private void eliminarDetallesMaterial() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM detalles_manualidades WHERE manualidad_id IS NULL")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CrearManualidad crearManualidad = new CrearManualidad();
        crearManualidad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearManualidad.setVisible(true);
    }
}