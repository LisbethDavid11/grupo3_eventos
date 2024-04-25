package Alquileres;

import Materiales.TextPrompt;
import Modelos.PoliModeloMobiliario;
import Modelos.PoliModeloProducto;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

public class EditarAlquileres extends JFrame {
    private JTextField  campoTelefono;
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JTable tablaProductos;
    private JPanel panel1, panel2, panel3, panel5, panel6, panel7;
    private JButton agregarMobiliarioButton;
    private JScrollPane panel4;
    private JButton agregarButton;
    private JTextField campoBusquedaMateriales;
    int categoriaSeleccionada = 0;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre de producto", campoBusquedaMateriales);
    private JButton cancelarButton;
    private JTextField jbcClientes;
    private JPanel jpanelDireccion;
    private JLabel jtextCatidadTotalMateriales;
    private JButton botonLimpiar;
    private JTextField jbcTipoAlquiler;
    private JPanel panelFecha, panelInicio, panelFin;
    private JSpinner spinnerHora1;
    private JSpinner spinnerMin1;
    private JSpinner spinnerHora2;
    private JSpinner spinnerMin2;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField campoNombre;
    private JTextArea campoDescripcion;
    private int selectTabla = 1;
    private List<PoliProducto> productosListTemporal = new ArrayList<>();
    private List<PoliMobiliario> mobiliarioList = new ArrayList<>();
    private List<PoliMobiliario> mobiliarioListTemporal = new ArrayList<>();
    private List<PoliFlor> floristeriaList = new ArrayList<>();
    private List<PoliFlor> floristeriaListTemporal = new ArrayList<>();
    private List<PoliGlobo> globoList = new ArrayList<>();
    private List<PoliGlobo> globolListTemporal = new ArrayList<>();
    private Map<String,String> tiposDescripcion = new HashMap<>();
    private Map<String,String> tiposTablas = new HashMap<>();
    private String imagePath = "";
    private EditarAlquileres actual = this;
    private Conexion sql;

    private int id;
    private Connection mysql;

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

    public EditarAlquileres(int id) {
        super("");
        setSize(1080, 680);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();

        this.id = id;
        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);
        configurarTablaMateriales();

        SpinnerModel hourModel = new SpinnerNumberModel(1, 1, 12, 1);
        SpinnerModel hourModel1 = new SpinnerNumberModel(1, 1, 12, 1);
        SpinnerModel minuteModel = new SpinnerNumberModel(0, 0, 59, 1);
        SpinnerModel minuteModel1 = new SpinnerNumberModel(0, 0, 59, 1);
        spinnerHora1.setModel(hourModel);
        spinnerHora2.setModel(hourModel1);

        spinnerMin1.setModel(minuteModel);
        spinnerMin2.setModel(minuteModel1);

        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoy");
        properties.put("text.month", "Mes");
        properties.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        datePicker = new JDatePickerImpl(datePanel, new SimpleDateFormatter());  // Proporcionar un formateador

        Calendar tomorrow = getTomorrow(); // Obtén el día siguiente al actual

        dateModel.addChangeListener(e -> {
            handleDateChange(dateModel, tomorrow);
        });

        // Show initial date in date field (puede ser el día siguiente al actual)
        handleDateChange(dateModel, tomorrow);

        panelFecha.add(datePicker);

        // Establecer ancho y alto deseados para el paneldescripcion
        int panelDesWidth = 80;
        int panelDesHeight = 100;



        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelDesSize = new Dimension(panelDesWidth, panelDesHeight);

        // Establecer las dimensiones en el jpanelDireccion
        jpanelDireccion.setPreferredSize(panelDesSize);
        jpanelDireccion.setMaximumSize(panelDesSize);
        jpanelDireccion.setMinimumSize(panelDesSize);
        jpanelDireccion.setSize(panelDesSize);

        tiposDescripcion.put("W","mobiliario");
        tiposDescripcion.put("G","globo");
        tiposDescripcion.put("A","arreglo");
        tiposDescripcion.put("F","floristeria");
        tiposDescripcion.put("M","manualidad");

        tiposTablas.put("W","mobiliario");
        tiposTablas.put("G","globos");
        tiposTablas.put("A","arreglos");
        tiposTablas.put("F","floristeria");
        tiposTablas.put("M","manualidades");



        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));
        panel7.setBackground(Color.decode("#F5F5F5"));
        jpanelDireccion.setBackground(Color.decode("#F5F5F5"));
        panelFecha.setBackground(Color.decode("#F5F5F5"));
        panelInicio.setBackground(Color.decode("#F5F5F5"));
        panelFin.setBackground(Color.decode("#F5F5F5"));

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
        agregarMobiliarioButton.setForeground(Color.DARK_GRAY);
        botonLimpiar.setForeground(Color.WHITE);
        cancelarButton.setForeground(Color.WHITE);
        agregarButton.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonCancelar.setBackground(darkColorBlue);
        botonGuardar.setBackground(darkColorAqua);
        agregarMobiliarioButton.setBackground(lightColorAmber);
        botonLimpiar.setBackground(darkColorRed);
        agregarButton.setBackground(darkColorCyan);
        cancelarButton.setBackground(darkColorRed);

        botonCancelar.setFocusPainted(false);
        botonGuardar.setFocusPainted(false);
        agregarMobiliarioButton.setFocusPainted(false);
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
        lbl3.setForeground(textColor);
        lbl4.setForeground(textColor);
        lbl5.setForeground(textColor);
        lbl6.setForeground(textColor);
        lbl7.setForeground(textColor);
        lbl8.setForeground(textColor);

        campoDescripcion.setEditable(true);
        campoTelefono.setEditable(false);

        campoTelefono.setFocusable(false);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);
        lbl0.setFont(fontTitulo);
        lbl9.setFont(font);
        lbl10.setFont(font);
        lbl11.setFont(font);

        // Color de texto para el JTextArea
        campoDescripcion.setForeground(textColor);
        campoDescripcion.setBackground(new Color(215, 215, 215));

        agregarMobiliarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriaSeleccionada = 4;
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMobiliarioButton.setVisible(false);
                tablaProductos.setModel(cargarDatosMobiliario());
            }
        });






        final int id_editar = this.id;
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(false);
                agregarButton.setVisible(false);
                cancelarButton.setVisible(false);
                agregarMobiliarioButton.setVisible(true);
                PreparedStatement detallesStatement = null;
                double suma =0;
                try {
                    detallesStatement = mysql.prepareStatement(
                            "SELECT da.id," +
                                    " da.detalle_id," +
                                    " da.cantidad, " +
                                    " t.nombreMobiliario, " +
                                    " t.precioUnitario " +
                                    "FROM detalles_alquileres da " +
                                    "LEFT JOIN mobiliario t ON da.detalle_id = t.id " +
                                    "WHERE da.alquiler_id = ?;"
                    );
                    detallesStatement.setInt(1, id_editar);
                    ResultSet detallesResultSet = detallesStatement.executeQuery();
                    List<PoliProducto> productos = new ArrayList<>();
                    int numeroDetalle = 1;
                    while (detallesResultSet.next()) {
                        PoliProductosGeneral poliProducto = new PoliProductosGeneral();
                        poliProducto.setID(detallesResultSet.getInt("id"));
                        poliProducto.setNombre(detallesResultSet.getString("nombreMobiliario"));
                        poliProducto.setCantidad(detallesResultSet.getInt("cantidad"));
                        poliProducto.setPrecio(detallesResultSet.getDouble("precioUnitario"));
                        poliProducto.setIdDetalle(detallesResultSet.getInt("detalle_id"));
                        poliProducto.setTipo("M");

                        int cantidad = detallesResultSet.getInt("cantidad");
                        double precioDetalle = detallesResultSet.getDouble("precioUnitario");
                        double subtotal = cantidad * precioDetalle; // Aplicar el factor 0.85 según tu lógica
                        suma += subtotal;

                        productos.add(poliProducto);
                    }

                    // Llenar la tabla de productos con el modelo
                    tablaProductos.setModel(new PoliModeloProducto(productos));
                    tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
                    tablaProductos.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());
                    DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
                    // Formatear el número "suma" y establecerlo en lbl9
                    String formattedLbl11 = "L. " + decimalFormat.format(suma);
                    lbl11.setText(formattedLbl11 + "  ");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                configurarTablaMateriales();
                tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new EditarAlquileres.ButtonRenderer());
                tablaProductos.getColumnModel().getColumn(5).setCellEditor(new EditarAlquileres.ButtonEditor());
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
                       mostrar();
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

                    ListaAlquileres listaAlquileres = new ListaAlquileres();
                listaAlquileres.setVisible(true);
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
                    mensaje += "Fecha\n";
                }

                // Obtener los valores de los selectores de hora y minuto para la hora inicial
                int horaInicial = (int) spinnerHora1.getValue();
                int minutoInicial = (int) spinnerMin1.getValue();

                // Obtener los valores de los selectores de hora y minuto para la hora final
                int horaFinal = (int) spinnerHora2.getValue();
                int minutoFinal = (int) spinnerMin2.getValue();

                if (horaInicial == 0 && minutoInicial == 0) {
                    validacion++;
                    mensaje += "La hora inicial\n";
                }

                if (horaFinal == 0 && minutoFinal == 0) {
                    validacion++;
                    mensaje += "La hora final\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validación de la descripción
                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200 || contieneSoloNumeros(texto)) {
                        JOptionPane.showMessageDialog(null, "La descripción debe tener entre 2 y 200 caracteres y no puede contener solo números.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String amPmInicial = comboBox1.getSelectedItem().toString();
                String amPmFinal = comboBox2.getSelectedItem().toString();

                // Convertir a formato de 24 horas
                if (amPmInicial.equals("PM")) {
                    if (horaInicial < 12) {
                        horaInicial += 12;
                    }
                }

                // Convertir a formato de 24 horas
                if (amPmFinal.equals("PM")) {
                    if (horaFinal < 12) {
                        horaFinal += 12;
                    }
                }

                // Crear objetos Time para la hora inicial y final
                Time inicio = Time.valueOf(String.format("%02d:%02d:00", horaInicial, minutoInicial));
                Time fin = Time.valueOf(String.format("%02d:%02d:00", horaFinal, minutoFinal));

                if (inicio.compareTo(fin) >= 0) {
                    JOptionPane.showMessageDialog(null,"La hora de inicio debe ser mayor que el tiempo de finalización.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
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
                        "¿Desea guardar la información del Alquiler?",
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
                        guardarAlquiler();

                        dialog.dispose();
                        ListaAlquileres listaAlquileres = new ListaAlquileres();
                        listaAlquileres.setVisible(true);
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
        tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tablaProductos.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());

        //actualizarLbl8y10();
        configurarTablaMateriales();
        agregarButton.setVisible(false);
        cancelarButton.setVisible(false);
        campoBusquedaMateriales.setVisible(false);
        campoBusquedaMateriales.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Llama a la función correspondiente a la categoría actualmente seleccionada
                switch (categoriaSeleccionada) {
                    case 4:
                        tablaProductos.setModel(cargarDatosMobiliario());
                        break;
                    default:
                        break;
                }
            }
        });
        final int id_Alquiler = this.id;
        agregarButton.addActionListener(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {

                Map<Integer,List> listas = new HashMap<>();
                listas.put(1,mobiliarioList);
                listas.put(2,floristeriaList);
                listas.put(3,globoList);

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

                } else  if ( l instanceof PoliManualidad p){
                    id_materialEntero = p.getID();
                    id_material = "M-"+p.getID();

                } else  if ( l instanceof PoliGlobo p){
                    id_materialEntero = p.getID();
                    id_material = "G-"+p.getID();

                } else  if ( l instanceof PoliArreglo p){
                    id_materialEntero = p.getID();
                    id_material = "A-"+p.getID();

                } else  if ( l instanceof PoliMobiliario p){
                    id_materialEntero = p.getID();
                    id_material = "W-"+p.getID();
                }

                int availableQuantity = obtenerCantidadMaterialDesdeBD(id_materialEntero, l.getTipo());

                if (cantidadMaterial <= 0) {
                    showErrorDialog("La cantidad debe ser mayor o igual a 1");
                    return;
                } else if (cantidadMaterial > availableQuantity) {
                    showErrorDialog("La cantidad supera la cantidad disponible en existencia");
                    return;
                }


                for (PoliProducto materialTemporal : productosListTemporal) {
                    String id = materialTemporal.getTipo()+"-"+materialTemporal.getID();
                        if ( id.equals(id_material)) {
                            materialDuplicado = true;
                            break;
                        }
                }
                DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
                if (!materialDuplicado) {
                    guardarDetalleAlquiler(id_materialEntero, cantidadMaterial, l.getTipo());

                    campoBusquedaMateriales.setVisible(false);
                    agregarButton.setVisible(false);
                    cancelarButton.setVisible(false);
                    agregarMobiliarioButton.setVisible(true);

                try{
                    PreparedStatement detallesStatement = mysql.prepareStatement(
                            "SELECT da.id," +
                                    " da.detalle_id," +
                                    " da.cantidad, " +
                                    " t.nombreMobiliario, " +
                                    " t.precioUnitario " +
                                    "FROM detalles_alquileres da " +
                                    "LEFT JOIN mobiliario t ON da.detalle_id = t.id " +
                                    "WHERE da.alquiler_id = ?;"
                    );
                    detallesStatement.setInt(1, id_Alquiler);
                    ResultSet detallesResultSet = detallesStatement.executeQuery();
                    List<PoliProducto> productos = new ArrayList<>();
                    double suma = 0;
                    int numeroDetalle = 1;
                    while (detallesResultSet.next()) {
                        PoliProductosGeneral poliProducto = new PoliProductosGeneral();
                        poliProducto.setID(detallesResultSet.getInt("id"));
                        poliProducto.setNombre(detallesResultSet.getString("nombreMobiliario"));
                        poliProducto.setCantidad(detallesResultSet.getInt("cantidad"));
                        poliProducto.setPrecio(detallesResultSet.getDouble("precioUnitario"));
                        poliProducto.setIdDetalle(detallesResultSet.getInt("detalle_id"));
                        poliProducto.setTipo("M");

                        int cantidad = detallesResultSet.getInt("cantidad");
                        double precioDetalle = detallesResultSet.getDouble("precioUnitario");
                        double subtotal = cantidad * precioDetalle; // Aplicar el factor 0.85 según tu lógica
                        suma += subtotal;

                        productos.add(poliProducto);
                    }

                    // Llenar la tabla de productos con el modelo
                    tablaProductos.setModel(new PoliModeloProducto(productos));
                    tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
                    tablaProductos.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());
                        // Formatear el número "suma" y establecerlo en lbl9
                        String formatted11 = "L. " + decimalFormat.format(suma);
                        lbl11.setText(formatted11 + "  ");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

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


        mostrar();
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
                                             tipo.equals("F") ? "precio" :
                                                     tipo.equals("A") ? "precio" :
                                                             tipo.equals("W") ? "precioUnitario" :
                                                                     tipo.equals("M") ? "precio_manualidad" :
                                                                        tipo.equals("G") ? "precio" :
                                                                             "precio_default") +
                     " FROM " +
                     (tipo.equals("T") ? "tarjetas" :
                             tipo.equals("D") ? "desayunos" :
                                             tipo.equals("F") ? "floristeria" :
                                                     tipo.equals("A") ? "arreglos" :
                                                             tipo.equals("W") ? "mobiliario" :
                                                                     tipo.equals("M") ? "manualidades" :
                                                                             tipo.equals("G") ? "globos" :
                                                                             "default_table") +
                     " WHERE id = ?")) {
            preparedStatement.setInt(1, id_material);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                precio = resultSet.getDouble(
                        tipo.equals("T") ? "precio_tarjeta" :
                                tipo.equals("D") ? "precio_desayuno" :
                                                tipo.equals("F") ? "precio" :
                                                        tipo.equals("A") ? "precio" :
                                                                tipo.equals("W") ? "precioUnitario" :
                                                                        tipo.equals("M") ? "precio_manualidad" :
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
        if(sumaTotal == 0){

            lbl11.setText("0.00");
        }else{
            String sumaTotalFormateado = decimalFormat.format(sumaTotal);
            lbl11.setText(" " + sumaTotalFormateado);
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


    private PoliModeloProducto cargarDetallesMateriales() {
        selectTabla = 0;

        double precioTotalMateriales = 0.00;

        System.out.println(productosListTemporal.size());
        for (PoliProducto productosGeneral : productosListTemporal) {

            double total = productosGeneral.getCantidad() * productosGeneral.getPrecio();
            precioTotalMateriales += total;
        }

        lbl11.setText(String.format("%.2f",precioTotalMateriales));

        // Configurar la tabla para mostrar los encabezados de las columnas
        JTableHeader tableHeader = tablaProductos.getTableHeader();
        tableHeader.setVisible(true);

        // Configurar la tabla para mantener el ordenamiento de filas incluso sin encabezados visibles
        tablaProductos.setAutoCreateRowSorter(true);

        // Configurar el ancho de las columnas y alineaciones de las celdas
        configurarTablaMateriales();

        return new PoliModeloProducto(productosListTemporal);
    }

    private PoliModeloMobiliario cargarDatosMobiliario() {
        sql = new Conexion();
        mobiliarioList.clear();
        selectTabla = 1; // Puedes asignar un valor que represente la tabla de mobiliario en tu base de datos.
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
                mobiliario.setPrecio(resultSet.getDouble("precioUnitario"));
                mobiliario.setPrecioUnitario(resultSet.getDouble("precioUnitario"));
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
                TableModel model = table.getModel();

                if (model instanceof PoliModeloProducto) {
                    PoliModeloProducto productoModel = (PoliModeloProducto) model;
                    PoliProducto producto = productoModel.getProducto(modelRow);

                    // Obtén el ID del detalle de pedido utilizando getIdDetalle
                    int detallePedidoId = producto.getID();


                    try {
                        Connection connection = sql.conectamysql();
                         PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT * FROM detalles_alquileres WHERE id = ?");
                         preparedStatement1.setInt(1,detallePedidoId);
                         ResultSet resultSet = preparedStatement1.executeQuery();
                        System.out.println("Id detalle "+detallePedidoId);
                        if(resultSet.next()){
                            System.out.println(resultSet.getInt("detalle_id"));
                            PreparedStatement preparedStatemen3 = connection.prepareStatement("update mobiliario SET cantidad = cantidad+? WHERE id = ?");
                            preparedStatemen3.setInt(1,resultSet.getInt("cantidad"));
                            preparedStatemen3.setInt(2,resultSet.getInt("detalle_id"));
                            preparedStatemen3.executeUpdate();

                            PreparedStatement preparedStatement = connection.prepareStatement(
                                    "DELETE FROM detalles_alquileres WHERE id = ?");
                            preparedStatement.setInt(1, detallePedidoId);
                            preparedStatement.executeUpdate();
                        }


                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Manejo de excepciones en caso de error en la eliminación en la base de datos.
                    }

                    // Elimina el elemento tanto de la lista temporal como de la tabla
                    productoModel.removeProductAtIndex(modelRow);
                    calcularTotalTabla();
                    configurarTablaMateriales();
                    fireEditingStopped();


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
        Date selectedDate = dateModel.getValue();
        if (selectedDate != null && isDateOutOfRange(selectedDate, tomorrow)) {
            JOptionPane.showMessageDialog(null, "La fecha seleccionada está fuera del rango permitido", "Error", JOptionPane.ERROR_MESSAGE);
            selectedDate = null; // Anula la selección en lugar de restablecerla a hoy
            dateModel.setValue(selectedDate);
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


    public void setFormattedDate(Date selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy"); // Desired date format
        String formattedDate = (selectedDate != null) ? dateFormat.format(selectedDate) : "";
        datePicker.getJFormattedTextField().setText(formattedDate);
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


    private void guardarAlquiler() {
        String descripcion = campoDescripcion.getText().trim();

        Date fechaInicial = (Date) datePicker.getModel().getValue(); // Explicitly cast the value to Date
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicial);

        // Obtener los valores de los JComboBox de AM/PM
        String amPmInicial = comboBox1.getSelectedItem().toString();
        String amPmFinal = comboBox2.getSelectedItem().toString();

        // Obtener los valores de los selectores de hora y minuto para la hora inicial
        int horaInicial = (int) spinnerHora1.getValue();
        int minutoInicial = (int) spinnerMin1.getValue();

        // Convertir a formato de 24 horas
        if (amPmInicial.equals("PM")) {
            if (horaInicial < 12) {
                horaInicial += 12;
            }
        }

        // Obtener los valores de los selectores de hora y minuto para la hora final
        int horaFinal = (int) spinnerHora2.getValue();
        int minutoFinal = (int) spinnerMin2.getValue();

        // Convertir a formato de 24 horas
        if (amPmFinal.equals("PM")) {
            if (horaFinal < 12) {
                horaFinal += 12;
            }
        }

        // Crear objetos Time para la hora inicial y final
        Time inicio = Time.valueOf(String.format("%02d:%02d:00", horaInicial, minutoInicial));
        Time fin = Time.valueOf(String.format("%02d:%02d:00", horaFinal, minutoFinal));

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE alquileres SET fecha = ?, descripcion = ?,hora_inicial = ?, hora_final = ? WHERE id = ?")) {
            preparedStatement.setString(1, fecha);
            preparedStatement.setString(2,descripcion);
            preparedStatement.setTime(3,inicio);
            preparedStatement.setTime(4, fin);
            preparedStatement.setInt(5, this.id);
            preparedStatement.executeUpdate();


            JOptionPane.showMessageDialog(null, "Alquiler Actualizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al actualizar el alquiler", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            JOptionPane.showMessageDialog(null, "Error al obtener la cantidad desde la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return availableQuantity;
    }

    private int guardarDetalleAlquiler(int id_material, int cantidad, String tipo) {
        double availableQuantity = obtenerCantidadMaterialDesdeBD(id_material, tipo);

        if (cantidad <= 0) {
            showErrorDialog("La cantidad debe ser mayor a 0.");
            return 0;
        } else if (cantidad > availableQuantity) {
            showErrorDialog("El número ingresado es mayor a la cantidad disponible en la base de datos.");
            return 0;
        }
        try {
            Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO detalles_alquileres (tipo_detalle, detalle_id, cantidad,precio,alquiler_id) VALUES (?, ?, ?, ?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tiposDescripcion.get(tipo));
            preparedStatement.setInt(2, id_material);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.setDouble(4, obtenerPrecioMaterialDesdeBD(id_material,tipo)); // Obtener el precio del material desde la base de datos
            preparedStatement.setInt(5, this.id);
            preparedStatement.executeUpdate();

            PreparedStatement preparedStatement2 = connection.prepareStatement("UPDATE mobiliario SET cantidad = cantidad - ? where id= ?");
            preparedStatement2.setInt(1, cantidad);
            preparedStatement2.setInt(2, id_material);
            preparedStatement2.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int lastId = 0;
            if (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }

            JOptionPane.showMessageDialog(null, "Detalle agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return lastId;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el detalle del alquiler", "Error", JOptionPane.ERROR_MESSAGE);
        }


        return 0;
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        double suma = 0;

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT alquileres.*, clientes.nombre, clientes.apellido, clientes.telefono FROM alquileres LEFT JOIN clientes ON alquileres.cliente_id = clientes.id WHERE alquileres.id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Crea un formato personalizado para mostrar las fechas
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM, yyyy");

                // Obtén las fechas del ResultSet
                String fechaPedidoString = resultSet.getString("fecha");

                // Convierte las cadenas de fecha en objetos Date
                SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaPedido = formatoOriginal.parse(fechaPedidoString);

                // Formatea las fechas en el estilo deseado
                String fechaPedidoFormateada = formatoFecha.format(fechaPedido);


                if (fechaPedido != null) {
                    // Establecer la fecha de entrega en el JDatePicker
                    datePicker.getModel().setDate(fechaPedido.getYear() + 1900, fechaPedido.getMonth(), fechaPedido.getDate());
                    datePicker.getModel().setSelected(true);
                }


                jbcTipoAlquiler.setText(resultSet.getString("tipo"));


                // Obtén los valores de tiempo de inicio y fin desde el ResultSet
                Time horaInicio = resultSet.getTime("hora_inicial");
                Time horaFin = resultSet.getTime("hora_final");

                // Formatea los valores de tiempo en el estilo deseado
                SimpleDateFormat formatoHoraFormatead1 = new SimpleDateFormat("hh");
                SimpleDateFormat formatoMinFormateada2 = new SimpleDateFormat("mm");
                SimpleDateFormat formatoAMPMFormateada2 = new SimpleDateFormat("a");
                String horaInicioFormateada = formatoHoraFormatead1.format(horaInicio);
                String horaFinFormateada = formatoHoraFormatead1.format(horaFin);

                // Establece los textos en tus campos de texto
                spinnerHora1.setValue(Integer.parseInt(horaInicioFormateada));
                spinnerHora2.setValue(Integer.parseInt(horaFinFormateada));

                String horaInicioFormateada2 = formatoMinFormateada2.format(horaInicio);
                String horaFinFormateada2 = formatoMinFormateada2.format(horaFin);

                spinnerMin1.setValue(Integer.parseInt(horaInicioFormateada2));
                spinnerMin2.setValue(Integer.parseInt(horaFinFormateada2));

                String horaFinFormateadaAM = formatoAMPMFormateada2.format(horaInicio);
                String horaFinFormateadaPM = formatoAMPMFormateada2.format(horaFin);

                System.out.println(horaFinFormateadaAM);
                if(horaFinFormateadaAM.equals("a. m.")){
                    comboBox1.setSelectedItem("AM");
                }else {
                    comboBox1.setSelectedItem("PM");
                }
                System.out.println(horaFinFormateadaPM);
                if(horaFinFormateadaPM.equals("a. m.")){
                    comboBox2.setSelectedItem("AM");
                }else {
                    comboBox2.setSelectedItem("PM");
                }

                campoDescripcion.setText(resultSet.getString("descripcion"));
                jbcClientes.setText(resultSet.getString("nombre") + " " + resultSet.getString("apellido"));
                campoTelefono.setText(resultSet.getString("telefono"));

                PreparedStatement detallesStatement = mysql.prepareStatement(
                        "SELECT da.id," +
                                " da.detalle_id," +
                                " da.cantidad, " +
                                " t.nombreMobiliario, " +
                                " t.precioUnitario " +
                                "FROM detalles_alquileres da " +
                                "LEFT JOIN mobiliario t ON da.detalle_id = t.id " +
                                "WHERE da.alquiler_id = ?;"
                );
                detallesStatement.setInt(1, this.id);
                ResultSet detallesResultSet = detallesStatement.executeQuery();
                List<PoliProducto> productos = new ArrayList<>();
                int numeroDetalle = 1;
                while (detallesResultSet.next()) {
                    PoliProductosGeneral poliProducto = new PoliProductosGeneral();
                    poliProducto.setID(detallesResultSet.getInt("id"));
                    poliProducto.setNombre(detallesResultSet.getString("nombreMobiliario"));
                    poliProducto.setCantidad(detallesResultSet.getInt("cantidad"));
                    poliProducto.setPrecio(detallesResultSet.getDouble("precioUnitario"));
                    poliProducto.setIdDetalle(detallesResultSet.getInt("detalle_id"));
                    poliProducto.setTipo("M");

                    int cantidad = detallesResultSet.getInt("cantidad");
                    double precioDetalle = detallesResultSet.getDouble("precioUnitario");
                    double subtotal = cantidad * precioDetalle; // Aplicar el factor 0.85 según tu lógica
                    suma += subtotal;

                    productos.add(poliProducto);
                }

                // Llenar la tabla de productos con el modelo
                tablaProductos.setModel(new PoliModeloProducto(productos));
                tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
                tablaProductos.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor());

                // Formatear el número "suma" y establecerlo en lbl9
                String formattedLbl11 = "L. " + decimalFormat.format(suma);
                lbl11.setText(formattedLbl11 + "  ");

            } else {
                JOptionPane.showMessageDialog(null, "El pedido con el ID " + this.id + " no fue encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean contieneSoloNumeros(String texto) {
        return !Pattern.matches(".*[a-zA-Z].*[0-9@#$].*|.*[a-zA-Z].*", texto);
    }

    public static void main(String[] args) {
       EditarAlquileres crearAlquileres = new EditarAlquileres(1);
       crearAlquileres.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       crearAlquileres.setVisible(true);
    }
}