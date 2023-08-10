package Tarjetas;

import Modelos.ModeloMaterial;
import Modelos.ModeloProducto;
import Objetos.Conexion;
import Objetos.Material;
import Objetos.Tarjeta;

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

public class VerTarjeta extends JFrame {

    private JTextField campoPrecioTarjeta;
    private JTextArea campoDescripcion;
    private JRadioButton radioButtonSi;
    private JRadioButton radioButtonNo;
    private JButton botonCancelar;
    private JPanel jpanelImagen, panel1, panel2, panel3, panel5, panel6;
    private JLabel lbl0;
    private JLabel lbl2;
    private JLabel lbl4;

    private JTable jtableMateriales;
    private JLabel jlabelImagen;
    private JScrollPane jscrollMateriales, panel4;

    private JTextField campoBusquedaMateriales;
    private JComboBox<String> jcbOcasion;
    private JPanel jpanelDescripcion;
    private JLabel jtextCatidadTotalMateriales;
    private JLabel lbl8;
    private JPanel panel7;
    private JTextField campoManoObra;
    private JLabel lbl9;
    private JLabel lbl10;
    private List<Material> materialList = new ArrayList<>();
    private String imagePath = "";
    private VerTarjeta actual = this;
    private Conexion sql;
    private String nombreFile;
    private String urlDestino = "";
    private DefaultTableModel modeloProductos;

    private List<Material> materialListTemporal = new ArrayList<>();

    private Tarjeta originalTarjeta;

    Color textColor = Color.decode("#212121");
    Color darkColorCyan = new Color(0, 150, 136);
    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
    Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);

    public VerTarjeta(Tarjeta tarjeta) {
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

        DefaultTableModel modeloProductos = new DefaultTableModel();


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

        // Color de fondo de los botones
        botonCancelar.setBackground(darkColorCyan);

        botonCancelar.setFocusPainted(false);

        // Aplica el margen al botón
        botonCancelar.setBorder(margin);
        botonCancelar.setBorder(margin);

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



        jtableMateriales.setModel(cargarDetallesMateriales());
        configurarTablaMateriales();

        campoBusquedaMateriales.setVisible(false);
        campoBusquedaMateriales.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                jtableMateriales.setModel(cargarDatosMateriales());
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

            columnModel.getColumn(0).setCellRenderer(new VerTarjeta.CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new VerTarjeta.LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new VerTarjeta.LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new VerTarjeta.LeftAlignedRenderer());
            columnModel.getColumn(4).setCellRenderer(new VerTarjeta.CenterAlignedRenderer());
        }
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




    private ModeloMaterial cargarDatosMateriales() {
        sql = new Conexion();
        materialList.clear();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT m.*, p.empresaProveedora " +
                             "FROM materiales m " +
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

}