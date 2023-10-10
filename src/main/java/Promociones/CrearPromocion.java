package Promociones;
import Materiales.TextPrompt;
import Modelos.*;
import Objetos.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.*;

public class CrearPromocion extends JFrame {
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;
    private JTextArea campoDescripcion;
    private JButton botonLimpiar, botonGuardar, botonCancelar, cancelarButton;
    private JPanel panel1, panel2, panel3, panel5, panel6, panel7, jpanelDescripcion;
    private JTable tablaProductos;
    private JScrollPane panel4;
    private JButton agregarButton;
    private JTextField campoBusquedaMateriales;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre de producto", campoBusquedaMateriales);
    private JLabel jtextCatidadTotalMateriales;
    private JPanel panelFechaInicial, panelFechaFinal;
    private JComboBox comboSeccion;
    private JLabel lablePrecio;
    private JTextField precio;
    private int selectTabla = 1;
    private List<PoliProducto> productosListTemporal = new ArrayList<>();
    private List<PoliMobiliario> mobiliarioList = new ArrayList<>();
    private List<PoliDesayuno> desayunoList = new ArrayList<>();
    private List<PoliMobiliario> mobiliarioListTemporal = new ArrayList<>();
    private List<PoliFlor> floristeriaList = new ArrayList<>();
    private List<PoliFlor> floristeriaListTemporal = new ArrayList<>();
    private List<PoliGlobo> globoList = new ArrayList<>();
    private List<PoliGlobo> globolListTemporal = new ArrayList<>();
    private List<PoliArreglo> arregloList = new ArrayList<>();
    private List<PoliTarjeta> targetList = new ArrayList<>();
    private List<PoliArreglo> arregloListTemporal = new ArrayList<>();
    private List<PoliManualidad> manualidadList = new ArrayList<>();
    private List<PoliMaterial> materialList = new ArrayList<>();
    private List<PoliManualidad> manualidadListTemporal = new ArrayList<>();
    private Map<String,String> tiposDescripcion = new HashMap<>();
    private Map<String,String> tiposTablas = new HashMap<>();
    private String imagePath = "";
    private CrearPromocion actual = this;
    private Conexion sql;
    private Connection mysql;
    private String nombreFile;
    private String urlDestino = "";
    private DefaultTableModel modeloProductos;
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
    private JDatePickerImpl datePicker; // Declare the datePicker variable at the class level
    private JDatePickerImpl datePicker2; // Declare the datePicker variable at the class level

    public CrearPromocion() {
        super("");
        setSize(1050, 680);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();
        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);
        configurarTablaMateriales();

        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoy");
        properties.put("text.month", "Mes");
        properties.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        datePicker = new JDatePickerImpl(datePanel, new CrearPromocion.SimpleDateFormatter());  // Proporcionar un formateador

        Calendar tomorrow = getTomorrow(); // Obtén el día siguiente al actual

        dateModel.addChangeListener(e -> {
            handleDateChange(dateModel, tomorrow);
        });

        // Show initial date in date field (puede ser el día siguiente al actual)
        handleDateChange(dateModel, tomorrow);

        panelFechaInicial.add(datePicker);

        UtilDateModel dateModel2 = new UtilDateModel();
        Properties properties2 = new Properties();
        properties2.put("text.today", "Hoy");
        properties2.put("text.month", "Mes");
        properties2.put("text.year", "Año");

        JDatePanelImpl datePanel2 = new JDatePanelImpl(dateModel2, properties2);
        datePicker2 = new JDatePickerImpl(datePanel2, new CrearPromocion.SimpleDateFormatter2());  // Proporcionar un formateador

        Calendar tomorrow2 = getTomorrow(); // Obtén el día siguiente al actual

        dateModel2.addChangeListener(e -> {
            handleDateChange2(dateModel2, tomorrow2);
        });

        // Show initial date in date field (puede ser el día siguiente al actual)
        handleDateChange2(dateModel2, tomorrow2);

        panelFechaFinal.add(datePicker2);

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

        tiposDescripcion.put("F","floristeria");
        tiposDescripcion.put("T","tarjeta");
        tiposDescripcion.put("G","globo");
        tiposDescripcion.put("M","material");
        tiposDescripcion.put("W","mobiliario");
        tiposDescripcion.put("A","arreglo");
        tiposDescripcion.put("D","desayuno");

        tiposTablas.put("F","floristeria");
        tiposTablas.put("T","tarjetas");
        tiposTablas.put("G","globos");
        tiposTablas.put("M","materiales");
        tiposTablas.put("W","mobiliario");
        tiposTablas.put("A","arreglos");
        tiposTablas.put("D","desayunos");

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));
        panel7.setBackground(Color.decode("#F5F5F5"));
        panelFechaFinal.setBackground(Color.decode("#F5F5F5"));
        panelFechaInicial.setBackground(Color.decode("#F5F5F5"));
        jpanelDescripcion.setBackground(Color.decode("#F5F5F5"));

        DefaultTableModel modeloProductos = new DefaultTableModel();

        JTableHeader header = tablaProductos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(darkColorCyan);

        // Color de texto para los JTextField
        Color textColor = Color.decode("#212121");

        // Crea un margen de 10 píxeles desde el borde inferior
        EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

        // Color de texto de los botones
        botonCancelar.setForeground(Color.WHITE);
        botonGuardar.setForeground(Color.WHITE);
        botonLimpiar.setForeground(Color.WHITE);
        cancelarButton.setForeground(Color.WHITE);
        agregarButton.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonCancelar.setBackground(darkColorBlue);
        botonGuardar.setBackground(darkColorAqua);
        botonLimpiar.setBackground(darkColorRed);
        agregarButton.setBackground(darkColorCyan);
        cancelarButton.setBackground(darkColorRed);

        botonCancelar.setFocusPainted(false);
        botonGuardar.setFocusPainted(false);
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
        lbl1.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);
        lbl0.setFont(fontTitulo);
        lbl4.setFont(font);
        lbl5.setFont(font);
        lbl6.setFont(font);

        // Color de texto para el JTextArea
        campoDescripcion.setForeground(textColor);
        campoDescripcion.setBackground(new Color(215, 215, 215));

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(false);
                agregarButton.setVisible(false);
                cancelarButton.setVisible(false);
                tablaProductos.setModel(cargarDetallesMateriales());
                //actualizarLbl8y10();
                configurarTablaMateriales();
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
                        // Acciones para el botón Sí
                        mobiliarioListTemporal.clear();
                        productosListTemporal.clear();
                        eliminarDetallesMaterial();
                        limpiarTablaMateriales();
                        lbl4.setText("");
                        lbl6.setText("0.00");

                        PoliModeloProducto nuevoModelo = new PoliModeloProducto(new ArrayList<>());
                        tablaProductos.setModel(nuevoModelo);
                        configurarTablaMateriales();

                        //calcularTotalTabla();
                        //actualizarLbl8y10();

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
                    ListaPromociones listaPromociones = new ListaPromociones();
                    listaPromociones.setVisible(true);
                    actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Descripción\n";
                }

                if (tablaProductos.getRowCount() == 0) {
                    validacion++;
                    mensaje += "Lista de productos\n";
                }

                if (datePicker.getJFormattedTextField().getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Fecha inicial\n";
                }

                if (datePicker2.getJFormattedTextField().getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Fecha final\n";
                }

                if (precio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Precio\n";
                }

                // Nueva validación para verificar que la fecha final sea mayor que la inicial
                java.util.Date fechaInicial = dateModel.getValue();
                java.util.Date fechaFinal = dateModel2.getValue();

                if (fechaInicial != null && fechaFinal != null && fechaInicial.after(fechaFinal)) {
                    JOptionPane.showMessageDialog(null, "La fecha final debe ser mayor que la fecha inicial.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        JOptionPane.showMessageDialog(null, "La dirección debe tener entre 2 y 200 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
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
                        "¿Desea guardar la información del evento?",
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
                        guardarPromocion();
                        dialog.dispose();
                        ListaPromociones listaPromociones = new ListaPromociones();
                        listaPromociones.setVisible(true);
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

        tablaProductos.setModel(cargarDetallesMateriales());
        tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new CrearPromocion.ButtonRenderer());
        tablaProductos.getColumnModel().getColumn(5).setCellEditor(new CrearPromocion.ButtonEditor());

        //actualizarLbl8y10();
        configurarTablaMateriales();
        agregarButton.setVisible(false);
        cancelarButton.setVisible(false);
        campoBusquedaMateriales.setVisible(false);
        campoBusquedaMateriales.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                switch (selectTabla) {
                    case 1 -> tablaProductos.setModel(cargarDatosDesayuno());
                    case 2 -> tablaProductos.setModel(cargarDatosMobiliario());
                    case 3 -> tablaProductos.setModel(cargarDatosFloristeria());
                    case 4 -> tablaProductos.setModel(cargarDatosGlobo());
                    case 5 -> tablaProductos.setModel(cargarDatosArreglo());
                    case 6 -> tablaProductos.setModel(cargarDatosTarjetas());
                    case 7 -> tablaProductos.setModel(cargarDatosMaterial());

                }

            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Map<Integer,List> listas = new HashMap<>();
                listas.put(1,desayunoList);
                listas.put(2,mobiliarioList);
                listas.put(3,floristeriaList);
                listas.put(4,globoList);
                listas.put(5,arregloList);
                listas.put(6,targetList);
                listas.put(7,materialList);

                if (tablaProductos.getSelectedRow() == -1) {
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
                PoliProducto l = (PoliProducto) listas.get(selectTabla).get(tablaProductos.getSelectedRow());
                String id_material = "";
                int id_materialEntero = 0;

                boolean materialDuplicado = false;

                if ( l instanceof PoliFlor p){
                    id_materialEntero = p.getID();
                    id_material = "F-"+p.getID();

                }else  if ( l instanceof PoliMaterial p){
                    id_materialEntero = p.getID();
                    id_material = "M-"+p.getID();

                }else  if ( l instanceof PoliGlobo p){
                    id_materialEntero = p.getID();
                    id_material = "G-"+p.getID();

                }else  if ( l instanceof PoliTarjeta p){
                    id_materialEntero = p.getID();
                    id_material = "T-"+p.getID();

                }else  if ( l instanceof PoliDesayuno p){
                    id_materialEntero = p.getID();
                    id_material = "D-"+p.getID();

                }else  if ( l instanceof PoliArreglo p){
                    id_materialEntero = p.getID();
                    id_material = "A-"+p.getID();

                }else  if ( l instanceof PoliMobiliario p){
                    id_materialEntero = p.getID();
                    id_material = "W-"+p.getID();

                }

                for (PoliProducto materialTemporal : productosListTemporal) {
                    String id = materialTemporal.getTipo()+"-"+materialTemporal.getID();
                        if ( id.equals(id_material)) {
                            materialDuplicado = true;
                            break;
                        }
                }

                if (!materialDuplicado) {
                    guardarDetallePromocion(id_materialEntero, cantidadMaterial, l.getTipo());
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
                    // Actualizar la tabla con los detalles actualizados
                    tablaProductos.setModel(cargarDetallesMateriales());
                    tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new CrearPromocion.ButtonRenderer());
                    tablaProductos.getColumnModel().getColumn(5).setCellEditor(new CrearPromocion.ButtonEditor());

                    configurarTablaMateriales();
                    //actualizarLbl8y10();
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
        comboSeccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                switch (comboSeccion.getSelectedItem().toString()) {
                    case "MOBILIARIO" -> {
                        campoBusquedaMateriales.setVisible(true);
                        agregarButton.setVisible(true);
                        cancelarButton.setVisible(true);
                        tablaProductos.setModel(cargarDatosMobiliario());
                    }
                    case "FLORES" -> {
                        campoBusquedaMateriales.setVisible(true);
                        agregarButton.setVisible(true);
                        cancelarButton.setVisible(true);
                        tablaProductos.setModel(cargarDatosFloristeria());
                    }
                    case "ARREGLOS" -> {
                        campoBusquedaMateriales.setVisible(true);
                        agregarButton.setVisible(true);
                        cancelarButton.setVisible(true);
                        tablaProductos.setModel(cargarDatosArreglo());
                    }
                    case "GLOBOS" -> {
                        campoBusquedaMateriales.setVisible(true);
                        agregarButton.setVisible(true);
                        cancelarButton.setVisible(true);
                        tablaProductos.setModel(cargarDatosGlobo());
                    }
                    case "TARGETAS" -> {
                        campoBusquedaMateriales.setVisible(true);
                        agregarButton.setVisible(true);
                        cancelarButton.setVisible(true);
                        tablaProductos.setModel(cargarDatosTarjetas());
                    }
                    case "MATERIALES" -> {
                        campoBusquedaMateriales.setVisible(true);
                        agregarButton.setVisible(true);
                        cancelarButton.setVisible(true);
                        tablaProductos.setModel(cargarDatosMaterial());
                    }
                    case "DESAYUNOS" -> {
                        campoBusquedaMateriales.setVisible(true);
                        agregarButton.setVisible(true);
                        cancelarButton.setVisible(true);
                        tablaProductos.setModel(cargarDatosDesayuno());
                    }

                    default -> JOptionPane.showMessageDialog(null, "Elija una opcion correcta");
                }
            }
        });
    }

    private void configurarTablaMateriales() {
        int columnCount = tablaProductos.getColumnCount();
        if (columnCount > 0) {
            TableColumnModel columnModel = tablaProductos.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(30); // Id
            columnModel.getColumn(1).setPreferredWidth(180); // Nombre
            columnModel.getColumn(2).setPreferredWidth(80);  // Precio
            columnModel.getColumn(3).setPreferredWidth(100); // Cliente

            columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
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

    private double obtenerPrecioMaterialDesdeBD(int id_material, String tipo) {
        double precio = 0.0;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT " +
                     (tipo.equals("T") ? "precio_tarjeta" :
                             tipo.equals("D") ? "precio_desayuno" :
                                     tipo.equals("M") ? "precio" :
                                             tipo.equals("F") ? "precio" :
                                                     tipo.equals("A") ? "precio" :
                                                             tipo.equals("W") ? "precioUnitario" :
                                                                     tipo.equals("G") ? "precio" :
                                                                     "precio_default") +
                     " FROM " +
                     (tipo.equals("T") ? "tarjetas" :
                             tipo.equals("D") ? "desayunos" :
                                     tipo.equals("M") ? "materiales" :
                                             tipo.equals("F") ? "floristeria" :
                                                     tipo.equals("A") ? "arreglos" :
                                                                     tipo.equals("W") ? "mobiliario" :
                                                                             tipo.equals("G") ? "globos" :
                                                                     "default_table") +
                     " WHERE id = ?")) {
            preparedStatement.setInt(1, id_material);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                precio = resultSet.getDouble(
                        tipo.equals("T") ? "precio_tarjeta" :
                                tipo.equals("D") ? "precio_desayuno" :
                                        tipo.equals("M") ? "precio" :
                                                tipo.equals("F") ? "precio" :
                                                        tipo.equals("A") ? "precio" :
                                                                        tipo.equals("W") ? "precioUnitario" :
                                                                                tipo.equals("G") ? "precio" :
                                                                        "precio_default");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el precio del material desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return precio;
    }

    private void limpiarTablaMateriales() {
        mobiliarioList.clear();
        DefaultTableModel emptyModel = new DefaultTableModel();
        tablaProductos.setModel(emptyModel);
    }

    private void eliminarDetallesMaterial() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM detalles_desayunos WHERE desayuno_id IS NULL")) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    private double calcularTotalTabla() {
        double sumaTotal = 0.0;
        TableModel modelo = tablaProductos.getModel();
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

        // Actualizar el lbl9 con el total calculado
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String sumaTotalFormateado = decimalFormat.format(sumaTotal);
        lbl9.setText(" " + sumaTotalFormateado);

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

        // Actualizar lbl9 con el total de la tabla
        lbl9.setText(String.format("%.2f", totalTabla));

        double manoObra = 0.0;
        try {
            manoObra = Double.parseDouble(campoManoObra.getText().replace(",", "."));
        } catch (NumberFormatException e) {

        }

        // Actualizar lbl10 solo con el valor de mano de obra
        lbl10.setText(String.format("%.2f", manoObra));

        // Calcular el total y actualizar lbl11
        double total = totalTabla + manoObra;
        lbl11.setText(String.format("%.2f", total));
    }

     */
    private PoliModeloProducto cargarDetallesMateriales() {
        sql = new Conexion();
        selectTabla = 0;

        double precioTotalMateriales = 0.00;

        for (PoliProducto productosGeneral: productosListTemporal) {
                PoliProductosGeneral material = new PoliProductosGeneral();
                material.setIdDetalle(productosGeneral.getIdDetalle());
                material.setID(productosGeneral.getID());
                material.setNombre(productosGeneral.getNombre());
                material.setCantidad(productosGeneral.getCantidad());
                material.setPrecio(productosGeneral.getPrecio());
                material.setTipo(productosGeneral.getTipo());
                double total = productosGeneral.getPrecio() * productosGeneral.getCantidad();
                precioTotalMateriales += total;
            }

        // Configurar la tabla para mostrar los encabezados de las columnas
        JTableHeader tableHeader = tablaProductos.getTableHeader();
        tableHeader.setVisible(true);

        lbl6.setText(String.format("%.2f",precioTotalMateriales));
        // Configurar la tabla para mantener el ordenamiento de filas incluso sin encabezados visibles
        tablaProductos.setAutoCreateRowSorter(true);

        // Configurar el ancho de las columnas y alineaciones de las celdas
        configurarTablaMateriales();

        return new PoliModeloProducto(productosListTemporal);
    }

    private PoliModeloDesayuno cargarDatosDesayuno() {
        sql = new Conexion();
        desayunoList.clear();
        selectTabla = 1; // Puedes asignar un valor que represente la tabla de mobiliario en tu base de datos.
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
                desayuno.setTipo("D"); // Puedes asignar un tipo específico para el mobiliario.
                desayunoList.add(desayuno);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            desayunoList = new ArrayList<>();
        }

        PoliModeloDesayuno modeloDesayuno = new PoliModeloDesayuno(desayunoList, sql);
        tablaProductos.setModel(modeloDesayuno);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloDesayuno;
    }

    private PoliModeloMobiliario cargarDatosMobiliario() {
        sql = new Conexion();
        mobiliarioList.clear();
        selectTabla = 2; // Puedes asignar un valor que represente la tabla de mobiliario en tu base de datos.
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM mobiliario WHERE nombreMobiliario LIKE CONCAT('%', ?, '%')"
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PoliMobiliario mobiliario = new PoliMobiliario();
                mobiliario.setID(resultSet.getInt("id"));
                mobiliario.setNombre(resultSet.getString("nombreMobiliario"));
                mobiliario.setColor(resultSet.getString("color"));
                mobiliario.setCantidad(resultSet.getInt("cantidad"));
                mobiliario.setPrecioUnitario(resultSet.getFloat("precioUnitario"));
                mobiliario.setTipo("W"); // Puedes asignar un tipo específico para el mobiliario.
                mobiliarioList.add(mobiliario);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            mobiliarioList = new ArrayList<>();
        }

        PoliModeloMobiliario modeloMobiliario = new PoliModeloMobiliario(mobiliarioList, sql);
        tablaProductos.setModel(modeloMobiliario);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloMobiliario;
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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            floristeriaList = new ArrayList<>();
        }

        PoliModeloFlor modeloFlor = new PoliModeloFlor(floristeriaList, sql);
        tablaProductos.setModel(modeloFlor);
        configurarTablaMateriales();
        return modeloFlor;
    }

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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            globoList = new ArrayList<>();
        }

        PoliModeloGlobo modeloGlobo = new PoliModeloGlobo(globoList, sql);
        tablaProductos.setModel(modeloGlobo);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloGlobo;
    }

    private PoliModeloArreglo cargarDatosArreglo() {
        sql = new Conexion();
        arregloList.clear();
        selectTabla = 5; // Puedes asignar un valor que represente la tabla de arreglos en tu base de datos.
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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            arregloList = new ArrayList<>();
        }

        PoliModeloArreglo modeloArreglo = new PoliModeloArreglo(arregloList, sql);
        tablaProductos.setModel(modeloArreglo);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloArreglo;
    }

    private PoliModeloTarjetas cargarDatosTarjetas() {
        sql = new Conexion();
        targetList.clear();
        selectTabla = 6; // Puedes asignar un valor que represente la tabla de arreglos en tu base de datos.
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM tarjetas WHERE descripcion LIKE CONCAT('%', ?, '%')"
             )
        ) {
            preparedStatement.setString(1, campoBusquedaMateriales.getText());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                PoliTarjeta tarjeta = new PoliTarjeta();
                tarjeta.setID(resultSet.getInt("id"));
                tarjeta.setNombre(resultSet.getString("descripcion"));
                tarjeta.setCantidad(resultSet.getInt("cantidad"));
                tarjeta.setPrecio(resultSet.getDouble("precio_tarjeta"));
                tarjeta.setTipo("T"); // Puedes asignar un tipo específico para los arreglos.
                targetList.add(tarjeta);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            targetList = new ArrayList<>();
        }

        PoliModeloTarjetas modeloTarjetas = new PoliModeloTarjetas(targetList, sql);
        tablaProductos.setModel(modeloTarjetas);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloTarjetas;
    }

//    private PoliModeloManualidad cargarDatosManualidad() {
//        sql = new Conexion();
//        manualidadList.clear();
//        selectTabla = 9; // Puedes asignar un valor que represente la tabla de manualidades en tu base de datos.
//        try (Connection mysql = sql.conectamysql();
//             PreparedStatement preparedStatement = mysql.prepareStatement(
//                     "SELECT * FROM manualidades WHERE nombre LIKE CONCAT('%', ?, '%')"
//             )
//        ) {
//            preparedStatement.setString(1, campoBusquedaMateriales.getText());
//
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                PoliManualidad manualidad = new PoliManualidad();
//                manualidad.setID(resultSet.getInt("id"));
//                manualidad.setNombre(resultSet.getString("nombre"));
//                manualidad.setCantidad(resultSet.getInt("cantidad"));
//                manualidad.setPrecio(resultSet.getDouble("precio_manualidad"));
//                manualidad.setTipo("M"); // Puedes asignar un tipo específico para las manualidades.
//                manualidadList.add(manualidad);
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
//            manualidadList = new ArrayList<>();
//        }
//
//        PoliModeloManualidad modeloManualidad = new PoliModeloManualidad(manualidadList, sql);
//        tablaProductos.setModel(modeloManualidad);
//        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
//        return modeloManualidad;
//    }

    private PoliModeloMaterial cargarDatosMaterial() {
        sql = new Conexion();
        materialList.clear();

        selectTabla = 7; // Puedes asignar un valor que represente la tabla de manualidades en tu base de datos.
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
                material.setTipo("M"); // Puedes asignar un tipo específico para las manualidades.
                materialList.add(material);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            materialList = new ArrayList<>();
        }

        PoliModeloMaterial modeloMaterial = new PoliModeloMaterial(materialList, sql);
        tablaProductos.setModel(modeloMaterial);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
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
                TableModel model = table.getModel();  // Obtener el modelo de la tabla

                // Verificar si el modelo de la tabla es un PoliModeloProducto
                if (model instanceof PoliModeloProducto) {
                    PoliModeloProducto productoModel = (PoliModeloProducto) model;

                    // Eliminar el producto tanto de la lista temporal como de la tabla
                    productoModel.removeProductAtIndex(modelRow);

                    double precioTotalMateriales = 0.00;
                    for (PoliProducto productosGeneral: productosListTemporal) {
                        PoliProductosGeneral material = new PoliProductosGeneral();
                        material.setIdDetalle(productosGeneral.getIdDetalle());
                        material.setID(productosGeneral.getID());
                        material.setNombre(productosGeneral.getNombre());
                        material.setCantidad(productosGeneral.getCantidad());
                        material.setPrecio(productosGeneral.getPrecio());
                        material.setTipo(productosGeneral.getTipo());
                        double total = productosGeneral.getPrecio() * productosGeneral.getCantidad();
                        precioTotalMateriales += total;
                    }
                    lbl6.setText(String.format("%.2f",precioTotalMateriales));
                }

                fireEditingStopped(); // Mover esta línea aquí para asegurarte de que se complete la edición
                //calcularTotalTabla();
                //actualizarLbl8y10();
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
        if (selectedDate != null && isDateOutOfRange(selectedDate, tomorrow)) {
            JOptionPane.showMessageDialog(null, "La fecha seleccionada está fuera del rango permitido", "Error", JOptionPane.ERROR_MESSAGE);
            selectedDate = null; // Anula la selección en lugar de restablecerla a hoy
            dateModel.setValue(selectedDate);
        }
        setFormattedDate(selectedDate);
    }

    public void handleDateChange2(UtilDateModel dateModel, Calendar tomorrow) {
        java.util.Date selectedDate2 = dateModel.getValue();
        if (selectedDate2 != null && isDateOutOfRange2(selectedDate2, tomorrow)) {
            JOptionPane.showMessageDialog(null, "La fecha seleccionada está fuera del rango permitido", "Error", JOptionPane.ERROR_MESSAGE);
            selectedDate2 = null; // Anula la selección en lugar de restablecerla a hoy
            dateModel.setValue(selectedDate2);
        }
        setFormattedDate2(selectedDate2);
    }

    public boolean isDateOutOfRange(java.util.Date selectedDate, Calendar tomorrow) {
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);

        // Compara con el día siguiente al actual
        return selectedCal.before(tomorrow);
    }

    public boolean isDateOutOfRange2(java.util.Date selectedDate, Calendar tomorrow) {
        Calendar selectedCal2 = Calendar.getInstance();
        selectedCal2.setTime(selectedDate);

        // Compara con el día siguiente al actual
        return selectedCal2.before(tomorrow);
    }

    public void setFormattedDate(java.util.Date selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy"); // Desired date format
        String formattedDate = (selectedDate != null) ? dateFormat.format(selectedDate) : "";
        datePicker.getJFormattedTextField().setText(formattedDate);
    }

    public void setFormattedDate2(java.util.Date selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy"); // Desired date format
        String formattedDate = (selectedDate != null) ? dateFormat.format(selectedDate) : "";
        datePicker2.getJFormattedTextField().setText(formattedDate);
    }

    public class SimpleDateFormatter extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                if (value instanceof Date) {
                    return dateFormatter.format((Date) value);
                } else if (value instanceof Calendar) {
                    return dateFormatter.format(((Calendar) value).getTime());
                }
            }
            return "";
        }
    }

    public class SimpleDateFormatter2 extends JFormattedTextField.AbstractFormatter {
        private final String datePattern = "yyyy-MM-dd";
        private final SimpleDateFormat dateFormatter2 = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter2.parse(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                if (value instanceof Date) {
                    return dateFormatter2.format((Date) value);
                } else if (value instanceof Calendar) {
                    return dateFormatter2.format(((Calendar) value).getTime());
                }
            }
            return "";
        }
    }


    private void guardarPromocion() {
        String precioText = precio.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precio = Double.parseDouble(precioText);

        String descripcion = campoDescripcion.getText().trim();

        Date fechaInicial = (Date) datePicker.getModel().getValue(); // Explicitly cast the value to Date
        String fechaI = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicial);


        Date fechaFinal = (Date) datePicker2.getModel().getValue(); // Explicitly cast the value to Date
        String fechaF = new SimpleDateFormat("yyyy-MM-dd").format(fechaFinal);


        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO promociones (descripcion, inicio, fin, precio) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, descripcion);
            preparedStatement.setString(2, fechaI);
            preparedStatement.setString(3, fechaF);
            preparedStatement.setDouble(4, precio);

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int lastId = 0;
            if (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }

            try (PreparedStatement prepared = connection.prepareStatement(
                    "UPDATE detalles_promociones SET promocion_id = ? WHERE promocion_id IS NULL")) {
                prepared.setInt(1, lastId);
                prepared.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
                materialList = new ArrayList<>();
            }

            JOptionPane.showMessageDialog(null, "Promoción guardada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la promoción", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarDetallePromocion(int id_material, int cantidad, String tipo) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO detalles_promociones (tipo_detalle, detalle_id, cantidad,precio) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, tiposDescripcion.get(tipo));
            preparedStatement.setInt(2, id_material);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.setDouble(4, obtenerPrecioMaterialDesdeBD(id_material,tipo)); // Obtener el precio del material desde la base de datos
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Detalle agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el detalle de la promoción", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        CrearPromocion crearPromocion = new CrearPromocion();
        crearPromocion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearPromocion.setVisible(true);
    }
}