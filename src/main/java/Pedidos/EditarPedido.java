package Pedidos;
import Eventos.EditarEvento;
import Materiales.TextPrompt;
import Modelos.*;
import Objetos.*;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class EditarPedido extends JFrame {
    private JTextField campoCodigo, campoFechaPedido, CampoTelefono;
    private JTextArea campoDescripcion, campoDireccion;
    private JRadioButton radioButtonDomicilio, radioButtonTienda;
    private JComboBox<ClienteEvento> comboBoxCliente;
    private JButton botonGuardar, botonCancelar, botonLimpiar, agregarArregloButton, agregarFlorButton, agregarManualidadButton, agregarTarjetaButton, agregarDesayunoButton, agregarMaterialButton;
    private JPanel panel;
    private JLabel lbl0, lbl1, lbl2, lbl3,lbl4,lbl5, lbl6,lbl7,lbl8,lbl9, lbl10, lbl11, lbl12, lbl13, lbl14;
    private JPanel panel1, panel2, panel3, panel4,panel5, panel6, campoFechaEntrega;
    private JTextField campoTelefono;
    private JButton cancelarButton;
    private JButton agregarButton;
    private JTextField campoBusquedaMateriales;
    private int categoriaSeleccionada = 0;
    private JTable jtableMateriales;
    private JPanel panelPrincipal;
    private JPanel panelSecundario;
    private JPanel panelOpcional;
    private JTextField campoPrecioEnvio;
    private JPanel panelEntrega;
    private JLabel lbl15;
    private EditarPedido actual = this;
    private Conexion sql;
    private JDatePickerImpl datePicker;
    private Pedido originalPedido;
    private int id;
    private List<PoliProducto> productosListTemporal = new ArrayList<>();
    private List<PoliProducto> productosListOrigina = new ArrayList<>();
    private List<PoliMaterial> materialList = new ArrayList<>();
    private List<PoliMaterial> materialListTemporal = new ArrayList<>();
    private List<PoliFlor> floristeriaList = new ArrayList<>();
    private List<PoliFlor> floristeriaListTemporal = new ArrayList<>();
    private List<PoliTarjeta> tarjetaList = new ArrayList<>();
    private List<PoliTarjeta> tarjetaListTemporal = new ArrayList<>();
    private List<PoliArreglo> arregloList = new ArrayList<>();
    private List<PoliArreglo> arregloListTemporal = new ArrayList<>();
    private List<PoliManualidad> manualidadList = new ArrayList<>();
    private List<PoliManualidad> manualidadListTemporal = new ArrayList<>();
    private List<PoliDesayuno> desayunoList = new ArrayList<>();
    private List<PoliDesayuno> desayunoListTemporal = new ArrayList<>();
    private Map<String,String> tiposDescripcion = new HashMap<>();
    private Map<String,String> tiposTablas = new HashMap<>();
    private int selectTabla = 1;
    private Materiales.TextPrompt placeholder = new TextPrompt(" Buscar por nombre de producto", campoBusquedaMateriales);

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

    // Colores para el botón "Amber"
    Color primaryColorAmber = new Color(255, 193, 7); // Amber primario
    Color lightColorAmber = new Color(255, 213, 79); // Amber claro
    Color darkColorAmber = new Color(255, 160, 0); // Amber oscuro

    // Colores para el botón "Verde lima"
    Color primaryColorVerdeLima = new Color(205, 220, 57); // Verde lima primario
    Color lightColorVerdeLima = new Color(220, 237, 200); // Verde lima claro
    Color darkColorVerdeLima = new Color(139, 195, 74); // Verde lima oscuro

    Color darkColorPink = new Color(233, 30, 99);

    // Crea un margen de 10 píxeles desde el borde inferior
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public EditarPedido(Pedido pedido, int id) {
        super("");
        setSize(1005, 680);
        setLocationRelativeTo(null);
        setContentPane(panel);
        this.id = id;
        this.originalPedido = pedido;
        sql = new Conexion();
        configurarTablaMateriales();

        tiposDescripcion.put("A","arreglo");
        tiposDescripcion.put("F","floristeria");
        tiposDescripcion.put("T","tarjeta");
        tiposDescripcion.put("M","material");
        tiposDescripcion.put("W","manualidad");
        tiposDescripcion.put("D","desayuno");

        tiposTablas.put("A","arreglos");
        tiposTablas.put("F","floristeria");
        tiposTablas.put("T","tarjetas");
        tiposTablas.put("M","materiales");
        tiposTablas.put("W","manualidades");
        tiposTablas.put("D","desayunos");

        comboBoxCliente.addItem(new ClienteEvento(0,"","")); // Agregar mensaje inicial
        cargarClientes();
        comboBoxCliente.setEnabled(false);

        JTableHeader header = jtableMateriales.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(Color.decode("#263238"));

        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoy");
        properties.put("text.month", "Mes");
        properties.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        datePicker = new JDatePickerImpl(datePanel, new EditarPedido.SimpleDateFormatter());  // Proporcionar un formateador
        datePicker.getJFormattedTextField().setForeground(Color.decode("#263238"));
        datePicker.getJFormattedTextField().setBackground(Color.decode("#D7D7D7"));
        datePicker.setBackground(Color.decode("#F5F5F5"));

        // Primero, obtén el botón del datePicker
        JButton button = (JButton) datePicker.getComponent(1);
        button.setForeground(Color.decode("#FFFFFF"));
        button.setBackground(Color.decode("#263238"));
        button.setFocusable(false);

        Calendar tomorrow = getTomorrow(); // Obtén el día siguiente al actual
        dateModel.addChangeListener(e -> {
            handleDateChange(dateModel, tomorrow);
        });

        campoFechaEntrega.add(datePicker);

        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);
        campoDireccion.setLineWrap(true);
        campoDireccion.setWrapStyleWord(true);

        campoDireccion.setEditable(false);
        campoFechaPedido.setEditable(false);
        campoCodigo.setEditable(false);
        campoTelefono.setEditable(false);

        campoDireccion.setFocusable(false);
        campoFechaPedido.setFocusable(false);
        campoCodigo.setFocusable(false);
        campoTelefono.setFocusable(false);

        // Color de fondo del panel
        panel.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));
        panelPrincipal.setBackground(Color.decode("#F5F5F5"));
        panelSecundario.setBackground(Color.decode("#F5F5F5"));
        panelOpcional.setBackground(Color.decode("#F5F5F5"));

        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setBackground(darkColorRed);
        cancelarButton.setFocusPainted(false);

        agregarButton.setForeground(Color.WHITE);
        agregarButton.setBackground(darkColorCyan);
        agregarButton.setFocusPainted(false);

        agregarArregloButton.setForeground(Color.WHITE);
        agregarArregloButton.setBackground(Color.decode("#00BCD4"));
        agregarArregloButton.setFocusPainted(false);

        agregarMaterialButton.setForeground(Color.WHITE);
        agregarMaterialButton.setBackground(Color.decode("#795548"));
        agregarMaterialButton.setFocusPainted(false);

        agregarTarjetaButton.setForeground(Color.WHITE);
        agregarTarjetaButton.setBackground(Color.decode("#E81E12"));
        agregarTarjetaButton.setFocusPainted(false);

        agregarFlorButton.setForeground(Color.WHITE);
        agregarFlorButton.setBackground(Color.decode("#2196F3"));
        agregarFlorButton.setFocusPainted(false);

        agregarManualidadButton.setForeground(Color.WHITE);
        agregarManualidadButton.setBackground(Color.decode("#E91E63"));
        agregarManualidadButton.setFocusPainted(false);

        agregarDesayunoButton.setForeground(Color.WHITE);
        agregarDesayunoButton.setBackground(Color.decode("#3F51B5"));
        agregarDesayunoButton.setFocusPainted(false);

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

        radioButtonDomicilio.setForeground(textColor);
        radioButtonDomicilio.setBackground(panel.getBackground());
        radioButtonDomicilio.setFocusPainted(false);

        radioButtonTienda.setForeground(textColor);
        radioButtonTienda.setBackground(panel.getBackground());
        radioButtonTienda.setFocusPainted(false);

        // Selecciona el botón correspondiente según el método de entrega del pedido
        if (pedido.getEntrega().equals("Domicilio")) {
            radioButtonDomicilio.setSelected(true);
            radioButtonTienda.setSelected(false);
            panelEntrega.setVisible(true);
        } else {
            radioButtonDomicilio.setSelected(false);
            radioButtonTienda.setSelected(true);
            panelEntrega.setVisible(false);
        }

        // Agregar ActionListener al JRadioButton de domicilio
        radioButtonDomicilio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButtonDomicilio.isSelected()) {
                    radioButtonDomicilio.setSelected(true);
                    radioButtonTienda.setSelected(false);
                    panelEntrega.setVisible(true);
                }
            }
        });

        // Agregar ActionListener al JRadioButton de tienda
        radioButtonTienda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (radioButtonTienda.isSelected()) {
                    radioButtonDomicilio.setSelected(false);
                    radioButtonTienda.setSelected(true);
                    panelEntrega.setVisible(false);
                    campoPrecioEnvio.setText("0.0");
                }
            }
        });

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl3.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl6.setForeground(textColor);

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);
        lbl9.setFont(fontTitulo);
        lbl10.setFont(fontTitulo);
        lbl11.setFont(fontTitulo);
        lbl15.setFont(fontTitulo);

        campoDescripcion.setForeground(textColor);
        campoDescripcion.setBackground(new Color(215, 215, 215));

        campoDireccion.setForeground(textColor);
        campoDireccion.setBackground(new Color(215, 215, 215));

        agregarMaterialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFlorButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetaButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosMaterial());
                categoriaSeleccionada = 6;
            }
        });

        agregarFlorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFlorButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetaButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosFloristeria());
                categoriaSeleccionada = 3;
            }
        });

        agregarArregloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFlorButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetaButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosArreglo());
                categoriaSeleccionada = 2;
            }
        });

        agregarTarjetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFlorButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetaButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosTarjeta());
                categoriaSeleccionada = 4;
            }
        });

        agregarManualidadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFlorButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetaButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosManualidad());
                categoriaSeleccionada = 1;
            }
        });

        agregarDesayunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFlorButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetaButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                jtableMateriales.setModel(cargarDatosDesayuno());
                categoriaSeleccionada = 5;
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(false);
                agregarButton.setVisible(false);
                cancelarButton.setVisible(false);
                agregarMaterialButton.setVisible(true);
                agregarFlorButton.setVisible(true);
                agregarArregloButton.setVisible(true);
                agregarTarjetaButton.setVisible(true);
                agregarManualidadButton.setVisible(true);
                agregarDesayunoButton.setVisible(true);
                jtableMateriales.setModel(cargarDetallesMateriales());
                actualizarLbl8y10();
                configurarTablaMateriales();
                jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new EditarPedido.ButtonRenderer());
                jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new EditarPedido.ButtonEditor());

            }
        });

        campoCodigo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoCodigo.getText();
                int length = text.length();
                int caretPosition = campoCodigo.getCaretPosition();

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

        campoFechaPedido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoFechaPedido.getText();

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

        campoPrecioEnvio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoPrecioEnvio.getText();

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

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaPedidos listaPedidos = new ListaPedidos();
                listaPedidos.setVisible(true);
                eliminarDetallesMaterial();
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
                    mensaje += "Código\n";
                }

                if (campoFechaPedido.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Fecha de pedido\n";
                }

                if (!radioButtonDomicilio.isSelected() && !radioButtonTienda.isSelected()) {
                    validacion++;
                    mensaje += "Tipo de entrega\n";
                }

                // OBTENER LA FECHA SELECCIONADA EN EL FORMATO "yyyy-MM-dd"
                java.util.Date fechaActual = (java.util.Date) datePicker.getModel().getValue(); // Obtiene la fecha del selector
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d 'de' MMMM yyyy");

                // Verificar que la fecha de entrega esté en el rango permitido (de mañana hasta 30 días a partir de hoy)
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1); // Establece la fecha mínima (mañana)
                Date fechaMinima = calendar.getTime();

                Calendar calendarFin = Calendar.getInstance();
                calendarFin.add(Calendar.DAY_OF_YEAR, 30); // Establece la fecha mínima (mañana)
                Date fechaMaxima = calendarFin.getTime();

                if (fechaActual.before(fechaMinima) || fechaActual.after(fechaMaxima)) {
                    mostrarDialogoPersonalizadoAtencion("       Corrige la fecha de entrega; ya que debe estar entre: \nel " + dateFormat.format(fechaMinima)
                            + " y el " + dateFormat.format(fechaMaxima), Color.decode("#F57F17"));
                    return; // No proceder con la actualización si la fecha no es válida
                }

                String precioText = campoPrecioEnvio.getText().trim();
                if (radioButtonDomicilio.isSelected()){
                    if (campoPrecioEnvio.getText().trim().isEmpty()) {
                        validacion++;
                        mensaje += "Precio del envío\n";
                    }  else {
                        if (!precioText.matches("\\d{1,5}(\\.\\d{1,2})?")) {
                            mostrarDialogoPersonalizadoAtencion("Precio de envío inválido. Debe tener el formato correcto (ejemplo: 1234 o 1234.56).", Color.decode("#F57F17"));
                            return;
                        } else {
                            double precio = Double.parseDouble(precioText);
                            if (precio < 1.00 || precio > 99999.99) {
                                mostrarDialogoPersonalizadoAtencion("Precio de envío fuera del rango válido (1.00 - 99999.99).", Color.decode("#F57F17"));
                                return;
                            }
                        }
                    }
                }

                if (datePicker.getJFormattedTextField().getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Fecha de entrega\n";
                }

                if (comboBoxCliente.getSelectedIndex() == 0) {
                    validacion++;
                    mensaje += "Seleccionar el cliente\n";
                }

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Descripción\n";
                }

                if (jtableMateriales.getRowCount() == 0) {
                    validacion++;
                    mensaje += "Lista de productos\n";
                }

                if (validacion > 0) {
                    mostrarDialogoPersonalizadoAtencion(mensaje, Color.decode("#F57F17"));
                    return;
                }

                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        mostrarDialogoPersonalizadoAtencion("La descripción debe tener entre 2 y 200 caracteres.", Color.decode("#F57F17"));
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
                        "¿Desea guardar la información del pedido?",
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
                        actualizarPedido();
                        dialog.dispose();
                        ListaPedidos listaPedidos = new ListaPedidos();
                        listaPedidos.setVisible(true);
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
                        "¿Estás seguro de que deseas restaurar los datos del pedido?",
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

        campoPrecioEnvio.getDocument().addDocumentListener(new DocumentListener() {
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

        jtableMateriales.setModel(cargarDetallesMateriales());
        jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new EditarPedido.ButtonRenderer());
        jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new EditarPedido.ButtonEditor());

        actualizarLbl8y10();
        configurarTablaMateriales();
        agregarButton.setVisible(false);
        cancelarButton.setVisible(false);
        campoBusquedaMateriales.setVisible(false);
        campoBusquedaMateriales.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Llama a la función correspondiente a la categoría actualmente seleccionada
                switch (categoriaSeleccionada) {
                    case 1:
                        jtableMateriales.setModel(cargarDatosManualidad());
                        break;
                    case 2:
                        jtableMateriales.setModel(cargarDatosArreglo());
                        break;
                    case 3:
                        jtableMateriales.setModel(cargarDatosFloristeria());
                        break;
                    case 4:
                        jtableMateriales.setModel(cargarDatosTarjeta());
                        break;
                    case 5:
                        jtableMateriales.setModel(cargarDatosDesayuno());
                        break;
                    case 6:
                        jtableMateriales.setModel(cargarDatosMaterial());
                        break;
                    default:
                        break;
                }
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Map<Integer,List> listas = new HashMap<>();
                listas.put(2,arregloList);
                listas.put(3,floristeriaList);
                listas.put(4,manualidadList);
                listas.put(5,tarjetaList);
                listas.put(6,desayunoList);
                listas.put(7,materialList);

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

                if ( l instanceof PoliFlor p){
                    id_materialEntero = p.getID();
                    id_material = "F-"+p.getID();

                }else  if ( l instanceof PoliMaterial p){
                    id_materialEntero = p.getID();
                    id_material = "M-"+p.getID();

                }else  if ( l instanceof PoliArreglo p){
                    id_materialEntero = p.getID();
                    id_material = "A-"+p.getID();

                }else  if ( l instanceof PoliTarjeta p){
                    id_materialEntero = p.getID();
                    id_material = "T-"+p.getID();

                }else  if ( l instanceof PoliManualidad p){
                    id_materialEntero = p.getID();
                    id_material = "W-"+p.getID();

                }else  if ( l instanceof PoliDesayuno p){
                    id_materialEntero = p.getID();
                    id_material = "D-"+p.getID();
                }

                for (PoliProducto materialTemporal : productosListTemporal) {
                    String id = materialTemporal.getTipo()+"-"+materialTemporal.getID();
                    if ( id.equals(id_material)) {
                        materialDuplicado = true;
                        break;
                    }
                }

                if (!materialDuplicado) {
                    // Llamar al método guardarDetallePedido con los tres argumentos
                    guardarDetallePedido(id_materialEntero, cantidadMaterial, l.getTipo());

                    // Crear el material temporal y agregarlo a la lista temporal
                    PoliProductosGeneral materialTemporal = new PoliMaterial();
                    materialTemporal.setID(id_materialEntero);
                    materialTemporal.setNombre( l.getNombre());
                    materialTemporal.setCantidad(cantidadMaterial);
                    materialTemporal.setPrecio(l.getPrecio());
                    materialTemporal.setTipo(l.getTipo());
                    productosListTemporal.add(materialTemporal);

                    campoBusquedaMateriales.setVisible(false);
                    agregarButton.setVisible(false);
                    cancelarButton.setVisible(false);
                    agregarMaterialButton.setVisible(true);
                    agregarFlorButton.setVisible(true);
                    agregarArregloButton.setVisible(true);
                    agregarTarjetaButton.setVisible(true);
                    agregarManualidadButton.setVisible(true);
                    agregarDesayunoButton.setVisible(true);
                    // Actualizar la tabla con los detalles actualizados
                    jtableMateriales.setModel(cargarDetallesMateriales());
                    jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new EditarPedido.ButtonRenderer());
                    jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new EditarPedido.ButtonEditor());

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
                            "El detalle, ya está presente en la tabla",
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

        comboBoxCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Maneja la selección de cliente aquí
                cargarInformacionClienteSeleccionado();
            }
        });

        jtableMateriales.setModel(cargarDetallesMateriales());
        cargarDatosEditar();
        productosListOrigina = cargarListaOrignal();
    }

    public void cargarDatosEditar() {
        campoDescripcion.setText(this.originalPedido.getDescripcion());
        campoCodigo.setText(String.valueOf(this.originalPedido.getCodigoPedido()));
        // Suponiendo que 'originalPedido.getFechaPedido()' devuelve un objeto java.util.Date
        java.util.Date fechaPedido = this.originalPedido.getFechaPedido();

        // Crear un SimpleDateFormat con el patrón deseado y el Locale para español
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d 'de' MMMM yyyy", new Locale("es", "ES"));

        // Formatear la fecha del pedido
        String formattedDate = (fechaPedido != null) ? dateFormat.format(fechaPedido) : "";

        // Asegurar que la primera letra sea mayúscula
        if (!formattedDate.isEmpty()) {
            formattedDate = Character.toUpperCase(formattedDate.charAt(0)) + formattedDate.substring(1);
        }

        // Establecer el texto formateado en el campo de fecha de pedido
        campoFechaPedido.setText(formattedDate);

        try (Connection mysql = sql.conectamysql();
             PreparedStatement precioEnvioStatement = mysql.prepareStatement("SELECT precio_envio FROM pedidos WHERE id = ?")
        ) {
            precioEnvioStatement.setInt(1, this.id);
            ResultSet precioEnvioResultSet = precioEnvioStatement.executeQuery();

            if (precioEnvioResultSet.next()) {
                double precioEnvio = precioEnvioResultSet.getDouble("precio_envio");

                // Formatea el precio de envío a dos decimales y conviértelo a String
                String precioEnvioStr = String.format("%.2f", precioEnvio);

                // Asigna el precio de envío al campoPrecioEnvio
                campoPrecioEnvio.setText(precioEnvioStr);
            } else {
                // Puedes manejar aquí el caso en el que no se encuentra el precio de envío
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }


        // Suponiendo que campoFechaEntrega es el nombre del panel que contiene el JDatePicker
        JDatePicker datePicker = actual.datePicker;

        Date fechaEntrega = this.originalPedido.getFechaEntrega(); // Asumiendo que getFechaEntrega() devuelve un objeto Date
        if (fechaEntrega != null) {
            // Establecer la fecha de entrega en el JDatePicker
            datePicker.getModel().setDate(fechaEntrega.getYear() + 1900, fechaEntrega.getMonth(), fechaEntrega.getDate());
            datePicker.getModel().setSelected(true);
        }

        // Cargar el tipo de entrega en los JRadioButtons
        String tipoEntrega = this.originalPedido.getEntrega(); // Cambia getTipoEntrega() a getEntrega() si ese es el nombre correcto
        if ("Domicilio".equals(tipoEntrega)) {
            radioButtonDomicilio.setSelected(true); // Selecciona el RadioButton Domicilio
        } else if ("Tienda".equals(tipoEntrega)) {
            radioButtonTienda.setSelected(true); // Selecciona el RadioButton Tienda
        }

        // Obtener el ID del cliente de la base de datos
        int clienteId = this.originalPedido.getClienteId();

        // Iterar a través de los elementos del JComboBox y seleccionar el cliente por su ID
        for (int i = 0; i < comboBoxCliente.getItemCount(); i++) {
            ClienteEvento cliente = (ClienteEvento) comboBoxCliente.getItemAt(i);
            if (cliente.getIdCliente() == clienteId) {
                comboBoxCliente.setSelectedItem(cliente);
                break; // Salir del bucle una vez que se haya encontrado el cliente
            }
        }

        jtableMateriales.setModel(cargarDetallesMateriales());

        calcularTotalTabla();
        actualizarLbl8y10();
        configurarTablaMateriales();

        jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new EditarPedido.ButtonRenderer());
        jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new EditarPedido.ButtonEditor());

    }

    private PoliModeloProducto cargarDetallesMateriales() {
        sql = new Conexion();
        productosListTemporal.clear(); // Limpiar la lista antes de agregar los materiales
        selectTabla = 1;

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT detalles_pedidos.*,'F' AS 'tipo', floristeria.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN floristeria ON floristeria.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'floristeria' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'T' AS 'tipo',tarjetas.descripcion AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN tarjetas ON tarjetas.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'tarjeta' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'G' AS 'tipo',globos.codigo_globo AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN globos ON globos.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'globo' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'A' AS 'tipo',arreglos.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN arreglos ON arreglos.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'arreglo' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'W' AS 'tipo',manualidades.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN manualidades ON manualidades.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'manualidad' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'D' AS 'tipo',desayunos.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN desayunos ON desayunos.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'desayuno' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'M' AS 'tipo',materiales.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN materiales ON materiales.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'material';"
             )
        ) {
            preparedStatement.setInt(1,this.id);
            preparedStatement.setInt(2,this.id);
            preparedStatement.setInt(3,this.id);
            preparedStatement.setInt(4,this.id);
            preparedStatement.setInt(5,this.id);
            preparedStatement.setInt(6,this.id);
            preparedStatement.setInt(7,this.id);

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
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
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

    private List<PoliProducto> cargarListaOrignal() {
        sql = new Conexion();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT detalles_pedidos.*,'F' AS 'tipo', floristeria.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN floristeria ON floristeria.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'floristeria' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'T' AS 'tipo',tarjetas.descripcion AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN tarjetas ON tarjetas.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'tarjeta' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'G' AS 'tipo',globos.codigo_globo AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN globos ON globos.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'globo' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'A' AS 'tipo',arreglos.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN arreglos ON arreglos.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'arreglo' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'W' AS 'tipo',manualidades.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN manualidades ON manualidades.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'manualidad' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'D' AS 'tipo',desayunos.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN desayunos ON desayunos.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'desayuno' "+

                             " UNION "+

                             " SELECT detalles_pedidos.*,'M' AS 'tipo',materiales.nombre AS 'nombre', (detalles_pedidos.cantidad * detalles_pedidos.precio) AS 'total' FROM detalles_pedidos "+
                             " JOIN materiales ON materiales.id = detalles_pedidos.detalle_id "+
                             " WHERE detalles_pedidos.pedido_id = ? AND detalles_pedidos.tipo_detalle = 'material';"
             )
        ) {
            preparedStatement.setInt(1,this.id);
            preparedStatement.setInt(2,this.id);
            preparedStatement.setInt(3,this.id);
            preparedStatement.setInt(4,this.id);
            preparedStatement.setInt(5,this.id);
            preparedStatement.setInt(6,this.id);
            preparedStatement.setInt(7,this.id);

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
            System.out.println(e.getMessage());mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            return new ArrayList<>();
        }
    }

    private void cargarClientes() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nombre, apellido FROM clientes");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ClienteEvento clienteEvento = new ClienteEvento(resultSet.getInt("id"),resultSet.getString("nombre"),resultSet.getString("apellido"));

                comboBoxCliente.addItem(clienteEvento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configurarTablaMateriales() {
        int columnCount = jtableMateriales.getColumnCount();
        if (columnCount > 0) {
            TableColumnModel columnModel = jtableMateriales.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(30); // Id
            columnModel.getColumn(1).setPreferredWidth(180); // Nombre
            columnModel.getColumn(2).setPreferredWidth(80);  // Precio
            columnModel.getColumn(3).setPreferredWidth(100); // Proveedor

            columnModel.getColumn(0).setCellRenderer(new EditarPedido.CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new EditarPedido.LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new EditarPedido.LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new EditarPedido.LeftAlignedRenderer());
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

    private String convertirFecha(String fechaCampo) {
        try {
            // Parsear la fecha en formato "Domingo, 30 de julio de 2023" a un objeto Date
            SimpleDateFormat formatoFechaCampo = new SimpleDateFormat("EEEE, d 'de' MMMM 'de' yyyy");
            Date fecha = formatoFechaCampo.parse(fechaCampo);

            // Formatear la fecha a "yyyy-MM-dd" para almacenarla en la base de datos
            SimpleDateFormat formatoFechaDB = new SimpleDateFormat("yyyy-MM-dd");
            return formatoFechaDB.format(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            return ""; // Devolver una cadena vacía si ocurre algún error al parsear la fecha
        }
    }

    private void actualizarPedido() {
        String descripcion = campoDescripcion.getText().trim();
        java.util.Date fechaActual = (java.util.Date) datePicker.getModel().getValue(); // Obtiene la fecha del selector

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEntrega = dateFormat.format(fechaActual); // Formatea la fecha como una cadena "yyyy-MM-dd"

        // Asegúrate de obtener el valor correcto para el RadioButton de entrega, por ejemplo, "Domicilio" o "Tienda".
        String tipoEntrega = radioButtonDomicilio.isSelected() ? "Domicilio" : "Tienda";
        String precioText = campoPrecioEnvio.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precioEnvio = Double.parseDouble(precioText);
        ClienteEvento cliente = (ClienteEvento) comboBoxCliente.getSelectedItem();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE pedidos SET descripcion = ?, fecha_entrega = ?,  entrega = ?, cliente_id = ?, precio_envio = ? WHERE id = ?")) {

            preparedStatement.setString(1, descripcion);
            preparedStatement.setString(2, fechaEntrega);
            preparedStatement.setString(3, tipoEntrega);
            preparedStatement.setInt(4, cliente.getIdCliente());
            preparedStatement.setDouble(5, precioEnvio);
            preparedStatement.setInt(6, id); // Utiliza el ID proporcionado en el constructor.


            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                mostrarDialogoPersonalizadoExito("Pedido actualizado exitosamente.", Color.decode("#263238"));
            } else {
                mostrarDialogoPersonalizadoError("No se encontró el pedido para actualizar.", Color.decode("#C62828"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al actualizar el pedido.", Color.decode("#C62828"));
        }
    }

    private void guardarDetallePedido(int id_material, int cantidad, String tipo) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO detalles_pedidos (tipo_detalle, detalle_id, cantidad, precio, pedido_id) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, tiposDescripcion.get(tipo));
            preparedStatement.setInt(2, id_material);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.setDouble(4, obtenerPrecioMaterialDesdeBD(id_material, tipo)); // Obtener el precio del material desde la base de datos
            preparedStatement.setInt(5, this.id);
            preparedStatement.executeUpdate();
            mostrarDialogoPersonalizadoExito("Detalle agregado exitosamente.", Color.decode("#263238"));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al agregar el detalle al pedido.", Color.decode("#C62828"));
        }
    }

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
                                                                "precio_default") +
                     " FROM " +
                     (tipo.equals("T") ? "tarjetas" :
                             tipo.equals("D") ? "desayunos" :
                                     tipo.equals("W") ? "manualidades" :
                                             tipo.equals("F") ? "floristeria" :
                                                     tipo.equals("A") ? "arreglos" :
                                                             tipo.equals("M") ? "materiales" :
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
                                                                    "precio_default");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al intentar obtener el precio del producto desde la base de datos", Color.decode("#C62828"));
        }

        return precio;
    }


    private PoliModeloArreglo cargarDatosArreglo() {
        sql = new Conexion();
        arregloList.clear();
        selectTabla = 2; // Puedes asignar un valor que represente la tabla de arreglos en tu base de datos.
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM arreglos WHERE nombre LIKE CONCAT('%', ?, '%')"
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PoliArreglo arreglo = new PoliArreglo();
                arreglo.setID(resultSet.getInt("id"));
                arreglo.setNombre(resultSet.getString("nombre"));
                arreglo.setCantidad(resultSet.getInt("cantidad"));
                arreglo.setPrecio(resultSet.getDouble("precio"));
                arreglo.setTipo("A"); // Puedes asignar un tipo específico para los arreglos.
                arregloList.add(arreglo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            arregloList = new ArrayList<>();
        }

        PoliModeloArreglo modeloArreglo = new PoliModeloArreglo(arregloList, sql);
        jtableMateriales.setModel(modeloArreglo);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloArreglo;
    }

    private PoliModeloFlor cargarDatosFloristeria() {
        sql = new Conexion();
        floristeriaList.clear();
        selectTabla = 3;
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

    private PoliModeloManualidad cargarDatosManualidad() {
        sql = new Conexion();
        manualidadList.clear();
        selectTabla = 4;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM manualidades WHERE nombre LIKE CONCAT('%', ?, '%')"
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PoliManualidad manualidad = new PoliManualidad();
                manualidad.setID(resultSet.getInt("id"));
                manualidad.setNombre(resultSet.getString("nombre"));
                manualidad.setCantidad(resultSet.getInt("cantidad"));
                manualidad.setPrecio(resultSet.getDouble("precio_manualidad"));
                manualidad.setTipo("W"); // Puedes asignar un tipo específico para las manualidades.
                manualidadList.add(manualidad);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            manualidadList = new ArrayList<>();
        }

        PoliModeloManualidad modeloManualidad = new PoliModeloManualidad(manualidadList, sql);
        jtableMateriales.setModel(modeloManualidad);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloManualidad;
    }

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

    private PoliModeloDesayuno cargarDatosDesayuno() {
        sql = new Conexion();
        desayunoList.clear();
        selectTabla = 6;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM desayunos WHERE nombre LIKE CONCAT('%', ?, '%')"
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PoliDesayuno desayuno = new PoliDesayuno();
                desayuno.setID(resultSet.getInt("id"));
                desayuno.setNombre(resultSet.getString("nombre"));
                desayuno.setCantidad(resultSet.getInt("cantidad"));
                desayuno.setPrecio(resultSet.getDouble("precio_desayuno"));
                desayuno.setTipo("D");
                desayunoList.add(desayuno);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            desayunoList = new ArrayList<>();
        }

        PoliModeloDesayuno modeloDesayuno = new PoliModeloDesayuno(desayunoList, sql);
        jtableMateriales.setModel(modeloDesayuno);
        configurarTablaMateriales();
        return modeloDesayuno;
    }

    private PoliModeloMaterial cargarDatosMaterial() {
        sql = new Conexion();
        materialList.clear();
        selectTabla = 7;
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

    private double extraerValorNumerico(String valor) {
        String valorNumerico = valor.replace(',', '.');
        try {
            return Double.parseDouble(valorNumerico);
        } catch (NumberFormatException e) {
            System.err.println("Se encontró un formato de número no válido. No se puede convertir a double: " + valor);
            return 0.0;
        }
    }

    private double calcularTotalTabla() {
        double sumaTotal = 0.0;
        TableModel modelo = jtableMateriales.getModel();
        PoliModeloProducto modeloProductos = (PoliModeloProducto) modelo;

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
        lbl11.setText(" " + sumaTotalFormateado);

        return sumaTotal;
    }

    private void actualizarLbl8y10() {
        // Calcular el subtotal de la tabla (antes de aplicar el descuento)
        double totalTabla = calcularTotalTabla();

        double costoEnvio = 0.0;
        try {
            costoEnvio = Double.parseDouble(campoPrecioEnvio.getText().replace(",", "."));
        } catch (NumberFormatException e) {

        }

        // Actualizar lbl9 solo con el valor de mano de obra
        lbl15.setText(String.format("%.2f", costoEnvio));

        // Calcular el total con el descuento y aumento
        double subTotal = (totalTabla + costoEnvio) * 0.85;
        double ISV = (totalTabla + costoEnvio) * 0.15;
        double totalConAumento = totalTabla + costoEnvio;


        // Actualizar lbl8 con el subtotal con descuento
        lbl9.setText(String.format("L. %.2f", subTotal));

        // Actualizar lbl10 con el total sin descuento ni aumento (es decir, el subtotal original)
        lbl10.setText(String.format("L. %.2f", ISV));

        // Actualizar lbl11 con el total con aumento
        lbl11.setText(String.format("L. %.2f", totalConAumento));
    }

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

    private void eliminarDetallesMaterial() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM detalles_pedidos WHERE pedido_id IS NULL OR pedido_id NOT IN (SELECT id FROM pedidos)"
             )) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                TableModel model = table.getModel();

                if (model instanceof PoliModeloProducto) {
                    PoliModeloProducto productoModel = (PoliModeloProducto) model;
                    PoliProducto producto = productoModel.getProducto(modelRow);

                    // Obtén el ID del detalle de pedido utilizando getIdDetalle
                    int detallePedidoId = producto.getIdDetalle();

                    try (Connection connection = sql.conectamysql();
                         PreparedStatement preparedStatement = connection.prepareStatement(
                                 "DELETE FROM detalles_pedidos WHERE id = ?")) {
                        preparedStatement.setInt(1, detallePedidoId);
                        preparedStatement.executeUpdate();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Manejo de excepciones en caso de error en la eliminación en la base de datos.
                    }

                    // Elimina el elemento tanto de la lista temporal como de la tabla
                    productoModel.removeProductAtIndex(modelRow);

                    fireEditingStopped();
                    calcularTotalTabla();
                    actualizarLbl8y10();
                }
            }
        }
    }

    public Calendar getTomorrow() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        return tomorrow;
    }

    public void handleDateChange(UtilDateModel dateModel, Calendar tomorrow) {
        java.util.Date selectedDate = dateModel.getValue();
        if (selectedDate != null) {
            // Calcula el rango permitido
            Calendar minDate = (Calendar) tomorrow.clone();
            Calendar maxDate = (Calendar) tomorrow.clone();
            maxDate.add(Calendar.DAY_OF_MONTH, 29);
        }
        setFormattedDate(selectedDate);
    }

    public boolean isDateOutOfRange(Date selectedDate, Calendar tomorrow) {
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);

        // Calcula la fecha límite (30 días después de mañana)
        Calendar maxDate = (Calendar) tomorrow.clone();
        maxDate.add(Calendar.DAY_OF_MONTH, 30);

        return selectedCal.before(tomorrow) || selectedCal.after(maxDate);
    }

    public void setFormattedDate(java.util.Date selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d 'de' MMMM yyyy", new Locale("es", "ES")); // Formato en español
        String formattedDate = (selectedDate != null) ? dateFormat.format(selectedDate) : "";

        // Asegura que la primera letra sea mayúscula
        if (!formattedDate.isEmpty()) {
            formattedDate = Character.toUpperCase(formattedDate.charAt(0)) + formattedDate.substring(1);
        }

        datePicker.getJFormattedTextField().setText(formattedDate);
    }

    public class SimpleDateFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "EEEE, d 'de' MMMM yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                if (value instanceof java.util.Date) {
                    return dateFormatter.format((java.util.Date) value);
                } else if (value instanceof Calendar) {
                    return dateFormatter.format(((Calendar) value).getTime());
                }
            }
            return "";
        }
    }

    private void cargarInformacionClienteSeleccionado() {
        // Obtiene el cliente seleccionado del JComboBox
        Object selectedItem = comboBoxCliente.getSelectedItem();

        if (selectedItem != null && !selectedItem.toString().equals("Seleccione un cliente")) {
            // Divide el texto para obtener el ID del cliente
            String clienteSeleccionado = selectedItem.toString();
            int idCliente = Integer.parseInt(clienteSeleccionado.split(" - ")[0]);

            // Realiza una consulta para obtener la información del cliente
            try (Connection connection = sql.conectamysql();
                 PreparedStatement preparedStatement = connection.prepareStatement("SELECT telefono, domicilio FROM clientes WHERE id = ?")) {
                preparedStatement.setInt(1, idCliente);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Obtiene el teléfono y la dirección del resultado de la consulta
                    String telefono = resultSet.getString("telefono");
                    String direccion = resultSet.getString("domicilio");

                    // Llena los campos correspondientes con la información obtenida
                    campoTelefono.setText(telefono);
                    campoDireccion.setText(direccion);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.INFORMATION_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    public void mostrarDialogoPersonalizadoError(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    public void mostrarDialogoPersonalizadoAtencion(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    private void limpiarTablaMateriales() {
        PoliModeloProducto emptyModel = new PoliModeloProducto(this.productosListOrigina);
        jtableMateriales.setModel(emptyModel);

        calcularTotalTabla();
        actualizarLbl8y10();
        configurarTablaMateriales();

        jtableMateriales.getColumnModel().getColumn(5).setCellRenderer(new EditarPedido.ButtonRenderer());
        jtableMateriales.getColumnModel().getColumn(5).setCellEditor(new EditarPedido.ButtonEditor());
    }
}