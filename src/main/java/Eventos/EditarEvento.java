/**
 * EditarEvento.java
 *
 * Editar Evento
 *
 * @author Lisbeth David
 * @version 1.0
 * @since 2024-05-05
 */

package Eventos;

import Materiales.TextPrompt;
import Modelos.*;
import Objetos.*;
import Pedidos.EditarPedido;
import org.jdatepicker.JDatePicker;
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

public class EditarEvento extends JFrame {
    // Campos de texto
    private JTextField campoIdentidad;
    private JTextField campoTelefono;
    private JTextField campoBusquedaMateriales;

    // Etiquetas
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11;

    // Área de texto
    private JTextArea campoDireccion;

    // Botones
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JButton agregarButton;
    private JButton cancelarButton;
    private JButton botonLimpiar;
    private JButton agregarMobiliarioButton;
    private JButton agregarGloboButton;
    private JButton agregarArregloButton;
    private JButton agregarFloresButton;
    private JButton agregarManualidadesButton;

    // Tabla de productos
    private JTable tablaProductos;

    // Paneles
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JScrollPane panel4;
    private JPanel panel5;
    private JPanel panel6;
    private JPanel panel7;
    private JPanel panelFecha;
    private JPanel panelInicio;
    private JPanel panelFin;
    private JPanel jpanelDireccion;

    // JScrollPane
    private JScrollPane panelScroll;

    // Selectores de hora
    private JSpinner spinnerHora1;
    private JSpinner spinnerMin1;
    private JSpinner spinnerHora2;
    private JSpinner spinnerMin2;

    // ComboBox
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox<ClienteEvento> jbcClientes;
    private JComboBox jbcTipoEvento;

    // Variables
    private int categoriaSeleccionada = 0;
    private int selectTabla = 1;

    // Listas
    private List<PoliProducto> productosListTemporal = new ArrayList<>();
    private List<PoliProducto> productosListOrigina = new ArrayList<>();
    private List<PoliMobiliario> mobiliarioList = new ArrayList<>();
    private List<PoliMobiliario> mobiliarioListTemporal = new ArrayList<>();
    private List<PoliFlor> floristeriaList = new ArrayList<>();
    private List<PoliFlor> floristeriaListTemporal = new ArrayList<>();
    private List<PoliGlobo> globoList = new ArrayList<>();
    private List<PoliGlobo> globolListTemporal = new ArrayList<>();
    private List<PoliArreglo> arregloList = new ArrayList<>();
    private List<PoliArreglo> arregloListTemporal = new ArrayList<>();
    private List<PoliManualidad> manualidadList = new ArrayList<>();
    private List<PoliManualidad> manualidadListTemporal = new ArrayList<>();

    // Mapas
    private Map<String, String> tiposDescripcion = new HashMap<>();
    private Map<String, String> tiposTablas = new HashMap<>();

    // Ruta de la imagen
    private String imagePath = "";

    // Referencia a la ventana de edición de evento actual
    private EditarEvento actual = this;

    // Conexión a la base de datos
    private Conexion sql;
    private Connection mysql;

    // Nombre del archivo y URL de destino
    private String nombreFile;
    private String urlDestino = "";

    // Modelo de la tabla de productos
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
    private JDatePickerImpl datePicker; // Declare the datePicker variable at the class level
    private Evento originalEvento;
    private int id;

    public EditarEvento(Evento evento, int id) {
        super("");
        setSize(1080, 680);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        this.id = id;
        this.originalEvento = evento;
        sql = new Conexion();
        campoDireccion.setLineWrap(true);
        campoDireccion.setWrapStyleWord(true);
        configurarTablaMateriales();

        SpinnerModel hourModel = new SpinnerNumberModel(1, 1, 12, 1);
        SpinnerModel hourModel1 = new SpinnerNumberModel(1, 1, 12, 1);
        SpinnerModel minuteModel = new SpinnerNumberModel(0, 0, 59, 1);
        SpinnerModel minuteModel1 = new SpinnerNumberModel(0, 0, 59, 1);
        spinnerHora1.setModel(hourModel);
        spinnerHora2.setModel(hourModel1);

        spinnerMin1.setModel(minuteModel);
        spinnerMin2.setModel(minuteModel1);

        JTableHeader header = tablaProductos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(Color.decode("#263238"));

        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoy");
        properties.put("text.month", "Mes");
        properties.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        datePicker = new JDatePickerImpl(datePanel, new EditarEvento.SimpleDateFormatter());  // Proporcionar un formateador
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

        jbcClientes.addItem(new ClienteEvento(0,"","")); // Agregar mensaje inicial
        cargarClientes();
        jbcClientes.setEnabled(false);

        jbcClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el cliente seleccionado del JComboBox
                ClienteEvento clienteSeleccionado = (ClienteEvento) jbcClientes.getSelectedItem();

                // Verificar si se seleccionó un cliente válido
                if (clienteSeleccionado != null) {
                    int idCliente = clienteSeleccionado.getIdCliente();

                    // Realizar una consulta SQL para obtener la información del cliente
                    String consulta = "SELECT identidad, telefono, domicilio FROM clientes WHERE id = ?";

                    try (Connection connection = sql.conectamysql();
                         PreparedStatement preparedStatement = connection.prepareStatement(consulta)) {

                        preparedStatement.setInt(1, idCliente);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            // Asignar la identidad al campoIdentidad
                            campoIdentidad.setText(resultSet.getString("identidad"));

                            // Asignar el teléfono al campoTelefono
                            campoTelefono.setText(resultSet.getString("telefono"));

                            // Asignar la dirección al campoDireccion
                            campoDireccion.setText(resultSet.getString("domicilio"));
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    // Si se selecciona el mensaje inicial, limpiar los campos
                    campoIdentidad.setText("");
                    campoTelefono.setText("");
                    campoDireccion.setText("");
                }
            }
        });

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
        agregarGloboButton.setForeground(Color.DARK_GRAY);
        agregarArregloButton.setForeground(Color.DARK_GRAY);
        agregarFloresButton.setForeground(Color.DARK_GRAY);
        agregarManualidadesButton.setForeground(Color.DARK_GRAY);

        // Color de fondo de los botones
        botonCancelar.setBackground(darkColorBlue);
        botonGuardar.setBackground(darkColorAqua);
        agregarMobiliarioButton.setBackground(lightColorAmber);
        agregarFloresButton.setBackground(lightColorAqua);
        agregarGloboButton.setBackground(lightColorCyan);
        agregarArregloButton.setBackground(lightColorRosado);
        agregarManualidadesButton.setBackground(lightColorVerdeLima);
        botonLimpiar.setBackground(darkColorRed);
        agregarButton.setBackground(darkColorCyan);
        cancelarButton.setBackground(darkColorRed);

        botonCancelar.setFocusPainted(false);
        botonGuardar.setFocusPainted(false);
        agregarMobiliarioButton.setFocusPainted(false);
        agregarArregloButton.setFocusPainted(false);
        agregarFloresButton.setFocusPainted(false);
        agregarGloboButton.setFocusPainted(false);
        agregarManualidadesButton.setFocusPainted(false);
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
        lbl4.setForeground(textColor);
        lbl5.setForeground(textColor);
        lbl6.setForeground(textColor);
        lbl7.setForeground(textColor);
        lbl8.setForeground(textColor);

        campoDireccion.setEditable(false);
        campoIdentidad.setEditable(false);
        campoTelefono.setEditable(false);

        campoDireccion.setFocusable(false);
        campoIdentidad.setFocusable(false);
        campoTelefono.setFocusable(false);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);
        lbl0.setFont(fontTitulo);
        lbl9.setFont(font);
        lbl10.setFont(font);
        lbl11.setFont(font);

        // Color de texto para el JTextArea
        campoDireccion.setForeground(textColor);
        campoDireccion.setBackground(new Color(215, 215, 215));

        agregarMobiliarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriaSeleccionada = 4;
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMobiliarioButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarGloboButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarManualidadesButton.setVisible(false);
                tablaProductos.setModel(cargarDatosMobiliario());
            }
        });

        agregarFloresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriaSeleccionada = 3;
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMobiliarioButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarGloboButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarManualidadesButton.setVisible(false);
                tablaProductos.setModel(cargarDatosFloristeria());
            }
        });

        agregarGloboButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriaSeleccionada = 5;
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMobiliarioButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarGloboButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarManualidadesButton.setVisible(false);
                tablaProductos.setModel(cargarDatosGlobo());
            }
        });

        agregarArregloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriaSeleccionada = 2;
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMobiliarioButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarGloboButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarManualidadesButton.setVisible(false);
                tablaProductos.setModel(cargarDatosArreglo());
            }
        });

        agregarManualidadesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoriaSeleccionada = 1;
                campoBusquedaMateriales.setVisible(true);
                agregarButton.setVisible(true);
                cancelarButton.setVisible(true);
                agregarMobiliarioButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarGloboButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarManualidadesButton.setVisible(false);
                tablaProductos.setModel(cargarDatosManualidad());
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(false);
                agregarButton.setVisible(false);
                cancelarButton.setVisible(false);
                agregarMobiliarioButton.setVisible(true);
                agregarFloresButton.setVisible(true);
                agregarGloboButton.setVisible(true);
                agregarArregloButton.setVisible(true);
                agregarManualidadesButton.setVisible(true);
                tablaProductos.setModel(cargarDetallesMateriales());
                //actualizarLbl8y10();
                configurarTablaMateriales();
                tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new EditarEvento.ButtonRenderer());
                tablaProductos.getColumnModel().getColumn(5).setCellEditor(new EditarEvento.ButtonEditor());
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

        campoDireccion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String texto = campoDireccion.getText();
                int caretPosition = campoDireccion.getCaretPosition();

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

                    ListaEventos listaEventos = new ListaEventos();
                    listaEventos.setVisible(true);
                    actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                if (campoDireccion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Dirección del cliente\n";
                }

                if (jbcClientes.getSelectedIndex() == 0) {
                    validacion++;
                    mensaje += "Seleccionar el cliente\n";
                }

                if (jbcTipoEvento.getSelectedIndex() == 0) {
                    validacion++;
                    mensaje += "Seleccionar el evento\n";
                }

                if (tablaProductos.getRowCount() == 0) {
                    validacion++;
                    mensaje += "La lista de productos\n";
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

                if (horaInicial == 1 && minutoInicial == 0) {
                    validacion++;
                    mensaje += "Hora inicial\n";
                }

                if (horaFinal == 1 && minutoFinal == 0) {
                    validacion++;
                    mensaje += "Hora final\n";
                }

                if (validacion > 0) {
                    mostrarDialogoPersonalizadoAtencion(mensaje, Color.decode("#F57F17"));
                    return;
                }

                // OBTENER LA FECHA SELECCIONADA EN EL FORMATO "yyyy-MM-dd"
                java.util.Date fechaActual = (java.util.Date) datePicker.getModel().getValue(); // Obtiene la fecha del selector
                SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d 'de' MMMM yyyy");

                // Verificar que la fecha de entrega esté en el rango permitido (de mañana hasta 30 días a partir de hoy)
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1); // Establece la fecha mínima (mañana)
                Date fechaMinima = calendar.getTime();

                Calendar calendarFin = Calendar.getInstance();
                calendarFin.add(Calendar.DAY_OF_YEAR, 30); // Establece la fecha mínima (mañana)
                Date fechaMaxima = calendarFin.getTime();

                if (fechaActual.before(fechaMinima) || fechaActual.after(fechaMaxima)) {
                    mostrarDialogoPersonalizadoAtencion("       Corrige la fecha del evento ya que debe estár entre: \nel " + dateFormat.format(fechaMinima)
                            + " y el " + dateFormat.format(fechaMaxima), Color.decode("#F57F17"));
                    return; // No proceder con la actualización si la fecha no es válida
                }


                if (!campoDireccion.getText().trim().isEmpty()) {
                    String texto = campoDireccion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        mostrarDialogoPersonalizadoAtencion("La dirección debe tener entre 2 y 200 caracteres.", Color.decode("#F57F17"));
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
                        // Acciones para el botón Sí
                        guardarEvento();

                        dialog.dispose();
                        ListaEventos listaEventos = new ListaEventos();
                        listaEventos.setVisible(true);
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
        tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new EditarEvento.ButtonRenderer());
        tablaProductos.getColumnModel().getColumn(5).setCellEditor(new EditarEvento.ButtonEditor());

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
                    case 1:
                        tablaProductos.setModel(cargarDatosManualidad());
                        break;
                    case 2:
                        tablaProductos.setModel(cargarDatosArreglo());
                        break;
                    case 3:
                        tablaProductos.setModel(cargarDatosFloristeria());
                        break;
                    case 4:
                        tablaProductos.setModel(cargarDatosMobiliario());
                        break;
                    case 5:
                        tablaProductos.setModel(cargarDatosGlobo());
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
                listas.put(1,mobiliarioList);
                listas.put(2,floristeriaList);
                listas.put(3,globoList);
                listas.put(4,arregloList);
                listas.put(5,manualidadList);

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

                } else  if ( l instanceof PoliMobiliario p){
                    id_materialEntero = p.getID();
                    id_material = "M-"+p.getID();

                } else  if ( l instanceof PoliGlobo p){
                    id_materialEntero = p.getID();
                    id_material = "G-"+p.getID();

                } else  if ( l instanceof PoliArreglo p){
                    id_materialEntero = p.getID();
                    id_material = "A-"+p.getID();

                } else  if ( l instanceof PoliManualidad p){
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
                    // Llamar al método guardarDetalleEvento con los tres argumentos
                    guardarDetalleEvento(id_materialEntero, cantidadMaterial, l.getTipo());

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
                    agregarMobiliarioButton.setVisible(true);
                    agregarFloresButton.setVisible(true);
                    agregarGloboButton.setVisible(true);
                    agregarArregloButton.setVisible(true);
                    agregarManualidadesButton.setVisible(true);
                    // Actualizar la tabla con los detalles actualizados
                    tablaProductos.setModel(cargarDetallesMateriales());
                    tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new EditarEvento.ButtonRenderer());
                    tablaProductos.getColumnModel().getColumn(5).setCellEditor(new EditarEvento.ButtonEditor());

                    configurarTablaMateriales();
                    calcularTotalTabla();
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

        tablaProductos.setModel(cargarDetallesMateriales());
        cargarDatosEditar();
        productosListOrigina = cargarListaOrignal();
    }

    // Método para configurar la tabla
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

    // Clase para alinear los elementos al centro
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

    // Clase para alinear los elementos a la izquierda
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

    // Método para obtener el precio desde la base de datos
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
            mostrarDialogoPersonalizadoError("Error al intentar obtener el precio del producto desde la base de datos.", Color.decode("#C62828"));
        }

        return precio;
    }

    // Método para limpiar la tabla
    private void limpiarTablaMateriales() {
        PoliModeloProducto emptyModel = new PoliModeloProducto(this.productosListOrigina);
        tablaProductos.setModel(emptyModel);

        calcularTotalTabla();
        //actualizarLbl8y10();
        configurarTablaMateriales();

        tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new EditarEvento.ButtonRenderer());
        tablaProductos.getColumnModel().getColumn(5).setCellEditor(new EditarEvento.ButtonEditor());
    }

    // Método para calcular el valor total de la tabla
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
        lbl11.setText(" " + sumaTotalFormateado);

        return sumaTotal;
    }

    // Método para extraer el valor númerico
    private double extraerValorNumerico(String valor) {
        String valorNumerico = valor.replace(',', '.');
        try {
            return Double.parseDouble(valorNumerico);
        } catch (NumberFormatException e) {
            System.err.println("Se encontró un formato de número no válido. No se puede convertir a double: " + valor);
            return 0.0;
        }
    }

    // Método para cargar los datos de los mobiliarios
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
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            mobiliarioList = new ArrayList<>();
        }

        PoliModeloMobiliario modeloMobiliario = new PoliModeloMobiliario(mobiliarioList, sql);
        tablaProductos.setModel(modeloMobiliario);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloMobiliario;
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
        tablaProductos.setModel(modeloFlor);
        configurarTablaMateriales();
        return modeloFlor;
    }

    // Método para cargar los datos de los globos
    private PoliModeloGlobo cargarDatosGlobo() {
        sql = new Conexion();
        globoList.clear();
        selectTabla = 3;
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
        tablaProductos.setModel(modeloGlobo);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloGlobo;
    }

    // Método para cargar los datos de los arreglos
    private PoliModeloArreglo cargarDatosArreglo() {
        sql = new Conexion();
        arregloList.clear();

        selectTabla = 4; // Puedes asignar un valor que represente la tabla de arreglos en tu base de datos.
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
        tablaProductos.setModel(modeloArreglo);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloArreglo;
    }

    // Método para cargar los datos de las manualidades
    private PoliModeloManualidad cargarDatosManualidad() {
        sql = new Conexion();
        manualidadList.clear();

        selectTabla = 5; // Puedes asignar un valor que represente la tabla de manualidades en tu base de datos.
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
                manualidad.setTipo("M"); // Puedes asignar un tipo específico para las manualidades.
                manualidadList.add(manualidad);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            manualidadList = new ArrayList<>();
        }

        PoliModeloManualidad modeloManualidad = new PoliModeloManualidad(manualidadList, sql);
        tablaProductos.setModel(modeloManualidad);
        configurarTablaMateriales(); // Asegúrate de que este método configure la tabla adecuada.
        return modeloManualidad;
    }

    // Método para obtener la cantidad ingresada por el usuario
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
                        mostrarDialogoPersonalizadoError("La cantidad debe ser mayor o igual a 1.", Color.decode("#C62828"));
                        cantidadMaterial[0] = -1;
                    }

                    dialog.dispose();
                } catch (NumberFormatException ex) {
                    mostrarDialogoPersonalizadoError("La cantidad debe ser un número válido.", Color.decode("#C62828"));
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

    // Clase para agregar el botón a la celda
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

                    // Obtén el ID del detalle de evento utilizando getIdDetalle
                    int detalleEventoId = producto.getIdDetalle();

                    try (Connection connection = sql.conectamysql();
                         PreparedStatement preparedStatement = connection.prepareStatement(
                                 "DELETE FROM detalles_eventos WHERE id = ?")) {
                        preparedStatement.setInt(1, detalleEventoId);
                        preparedStatement.executeUpdate();
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

    // Método para cargar los datos del cliente
    private void cargarClientes() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nombre, apellido FROM clientes");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                ClienteEvento clienteEvento = new ClienteEvento(resultSet.getInt("id"), resultSet.getString("nombre"), resultSet.getString("apellido"));

                jbcClientes.addItem(clienteEvento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener el día siguiente
    public Calendar getTomorrow() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        return tomorrow;
    }

    // Método para cambiar la fecha
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

    // Método booleano para el rango de la fecha
    public boolean isDateOutOfRange(Date selectedDate, Calendar tomorrow) {
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);

        // Calcula la fecha límite (30 días después de mañana)
        Calendar maxDate = (Calendar) tomorrow.clone();
        maxDate.add(Calendar.DAY_OF_MONTH, 30);

        return selectedCal.before(tomorrow) || selectedCal.after(maxDate);
    }

    // Método para establecer la fecha
    public void setFormattedDate(java.util.Date selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE d 'de' MMMM yyyy", new Locale("es", "ES")); // Formato en español
        String formattedDate = (selectedDate != null) ? dateFormat.format(selectedDate) : "";

        // Asegura que la primera letra sea mayúscula
        if (!formattedDate.isEmpty()) {
            formattedDate = Character.toUpperCase(formattedDate.charAt(0)) + formattedDate.substring(1);
        }

        datePicker.getJFormattedTextField().setText(formattedDate);
    }

    // Clase para dar un formato simple a la fecha
    public class SimpleDateFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "EEEE d 'de' MMMM yyyy";
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

    // Método para cargar los datos del evento
    public void cargarDatosEditar() {
        campoDireccion.setText(this.originalEvento.getDireccion());

        // Suponiendo que campoFechaEntrega es el nombre del panel que contiene el JDatePicker
        JDatePicker datePicker = actual.datePicker;

        Date fecha = this.originalEvento.getFecha(); // Asumiendo que getFechaEntrega() devuelve un objeto Date
        if (fecha != null) {
            // Establecer la fecha de entrega en el JDatePicker
            datePicker.getModel().setDate(fecha.getYear() + 1900, fecha.getMonth(), fecha.getDate());
            datePicker.getModel().setSelected(true);
        }

        // Obtener el ID del cliente de la base de datos
        int clienteId = this.originalEvento.getClienteId();

        // Iterar a través de los elementos del JComboBox y seleccionar el cliente por su ID
        for (int i = 0; i < jbcClientes.getItemCount(); i++) {
            ClienteEvento cliente = (ClienteEvento) jbcClientes.getItemAt(i);
            if (cliente.getIdCliente() == clienteId) {
                jbcClientes.setSelectedItem(cliente);
                break; // Salir del bucle una vez que se haya encontrado el cliente
            }
        }

        // Obtain the 'tipo' value from the originalEvento
        String tipoEvento = this.originalEvento.getTipo();

        // Iterate through the elements of the jbcTipoEvento JComboBox and select the tipoEvento
        for (int i = 0; i < jbcTipoEvento.getItemCount(); i++) {
            String item = (String) jbcTipoEvento.getItemAt(i);
            if (item.equals(tipoEvento)) {
                jbcTipoEvento.setSelectedItem(item);
                break; // Exit the loop once the matching tipoEvento is found
            }
        }

        // Cargar hora inicial
        java.sql.Time inicioTime = this.originalEvento.getInicio();
        if (inicioTime != null) {
            // Get the hour and minute for 'inicio'
            int inicioHour = inicioTime.getHours();
            int inicioMinute = inicioTime.getMinutes();

            // Convert to 12-hour format
            int inicioAmPm = 0; // Default to AM
            if (inicioHour >= 12) {
                if (inicioHour > 12) {
                    inicioHour -= 12;
                }
                inicioAmPm = 1; // Set to PM if the hour is 12 or greater
            }

            // Ensure that the 'inicioHour' is between 1 and 12
            if (inicioHour < 1) {
                inicioHour = 1; // You can choose a default value (e.g., 1) if the hour is out of range
            }

            // Set the hours and minutes for 'inicio' in the spinners
            spinnerHora1.setValue(inicioHour);
            spinnerMin1.setValue(inicioMinute);

            // Set AM/PM for 'inicio'
            comboBox1.setSelectedIndex(inicioAmPm);
        }

        // Cargar hora final
        java.sql.Time finTime = this.originalEvento.getFin();
        if (finTime != null) {
            // Get the hour and minute for 'fin'
            int finHour = finTime.getHours();
            int finMinute = finTime.getMinutes();

            // Convert to 12-hour format
            int finAmPm = 0; // Default to AM
            if (finHour >= 12) {
                if (finHour > 12) {
                    finHour -= 12;
                }
                finAmPm = 1; // Set to PM if the hour is 12 or greater
            }

            // Ensure that the 'finHour' is between 1 and 12
            if (finHour < 1) {
                finHour = 1; // You can choose a default value (e.g., 1) if the hour is out of range
            }

            // Set the hours and minutes for 'fin' in the spinners
            spinnerHora2.setValue(finHour);
            spinnerMin2.setValue(finMinute);

            // Set AM/PM for 'fin'
            comboBox2.setSelectedIndex(finAmPm);
        }


        tablaProductos.setModel(cargarDetallesMateriales());

        calcularTotalTabla();
        //actualizarLbl8y10();
        configurarTablaMateriales();

        tablaProductos.getColumnModel().getColumn(5).setCellRenderer(new EditarEvento.ButtonRenderer());
        tablaProductos.getColumnModel().getColumn(5).setCellEditor(new EditarEvento.ButtonEditor());
    }

    // Método para cargar la lista de productos
    private PoliModeloProducto cargarDetallesMateriales() {
        sql = new Conexion();
        productosListTemporal.clear(); // Limpiar la lista antes de agregar los materiales
        selectTabla = 1;

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT detalles_eventos.*,'F' AS 'tipo', floristeria.nombre AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN floristeria ON floristeria.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'floristeria' "+

                             " UNION "+

                             " SELECT detalles_eventos.*,'W' AS 'tipo',mobiliario.nombreMobiliario AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN mobiliario ON mobiliario.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'mobiliario' "+

                             " UNION "+

                             " SELECT detalles_eventos.*,'G' AS 'tipo',globos.codigo_globo AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN globos ON globos.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'globo' "+

                             " UNION "+

                             " SELECT detalles_eventos.*,'A' AS 'tipo',arreglos.nombre AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN arreglos ON arreglos.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'arreglo' "+

                             " UNION "+

                             " SELECT detalles_eventos.*,'M' AS 'tipo',manualidades.nombre AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN manualidades ON manualidades.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'manualidad'; "
             )
        ) {
            preparedStatement.setInt(1,this.id);
            preparedStatement.setInt(2,this.id);
            preparedStatement.setInt(3,this.id);
            preparedStatement.setInt(4,this.id);
            preparedStatement.setInt(5,this.id);

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
        JTableHeader tableHeader = tablaProductos.getTableHeader();
        tableHeader.setVisible(true);

        // Configurar la tabla para mantener el ordenamiento de filas incluso sin encabezados visibles
        tablaProductos.setAutoCreateRowSorter(true);

        // Configurar el ancho de las columnas y alineaciones de las celdas
        configurarTablaMateriales();

        return new PoliModeloProducto(productosListTemporal);
    }

    // Método para cargar la lista original
    private List<PoliProducto> cargarListaOrignal() {
        sql = new Conexion();

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT detalles_eventos.*,'F' AS 'tipo', floristeria.nombre AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN floristeria ON floristeria.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'floristeria' "+

                             " UNION "+

                             " SELECT detalles_eventos.*,'W' AS 'tipo',mobiliario.nombreMobiliario AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN mobiliario ON mobiliario.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'mobiliario' "+

                             " UNION "+

                             " SELECT detalles_eventos.*,'G' AS 'tipo',globos.codigo_globo AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN globos ON globos.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'globo' "+

                             " UNION "+

                             " SELECT detalles_eventos.*,'A' AS 'tipo',arreglos.nombre AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN arreglos ON arreglos.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'arreglo' "+

                             " UNION "+

                             " SELECT detalles_eventos.*,'M' AS 'tipo',manualidades.nombre AS 'nombre', (detalles_eventos.cantidad * detalles_eventos.precio) AS 'total' FROM detalles_eventos "+
                             " JOIN manualidades ON manualidades.id = detalles_eventos.detalle_id "+
                             " WHERE detalles_eventos.evento_id = ? AND detalles_eventos.tipo_detalle = 'manualidad';"
             )
        ) {
            preparedStatement.setInt(1,this.id);
            preparedStatement.setInt(2,this.id);
            preparedStatement.setInt(3,this.id);
            preparedStatement.setInt(4,this.id);
            preparedStatement.setInt(5,this.id);

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

    // Método para guardar los datos del evento
    private void guardarEvento() {
        String direccion = campoDireccion.getText().trim();
        Date fechaInicial = (Date) datePicker.getModel().getValue();
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicial);

        int cliente_id = Integer.parseInt(jbcClientes.getSelectedItem().toString().split(" - ")[0]);
        String tipo = jbcTipoEvento.getSelectedItem().toString().trim();

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
             PreparedStatement preparedStatement = connection.prepareStatement("UPDATE eventos SET cliente_id = ?, direccion = ?, tipo = ?, fecha = ?, inicio = ?, fin = ? WHERE id = ?")) {
            preparedStatement.setInt(1, cliente_id);
            preparedStatement.setString(2, direccion);
            preparedStatement.setString(3, tipo);
            preparedStatement.setString(4, fecha);
            preparedStatement.setTime(5, inicio);
            preparedStatement.setTime(6, fin);
            preparedStatement.setInt(7, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                mostrarDialogoPersonalizadoExito("Evento actualizado exitosamente.", Color.decode("#263238"));
            } else {
                mostrarDialogoPersonalizadoError("No se encontró el evento para actualizar.", Color.decode("#C62828"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al actualizar el evento.", Color.decode("#C62828"));
        }
    }

    // Método para guardar los detalles del evento
    private void guardarDetalleEvento(int id_material, int cantidad, String tipo) {
        double availableQuantity = obtenerCantidadMaterialDesdeBD(id_material, tipo);

        if (cantidad <= 0) {
            mostrarDialogoPersonalizadoError("La cantidad debe ser mayor a 0.", Color.decode("#C62828"));
            return;
        } else if (cantidad > availableQuantity) {
            mostrarDialogoPersonalizadoError("El número ingresado es mayor a la cantidad disponible en la base de datos.", Color.decode("#C62828"));
            return;
        }

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO detalles_eventos (tipo_detalle, detalle_id, cantidad, precio, evento_id) VALUES (?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, tiposDescripcion.get(tipo));
            preparedStatement.setInt(2, id_material);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.setDouble(4, obtenerPrecioMaterialDesdeBD(id_material, tipo)); // Obtener el precio del material desde la base de datos
            preparedStatement.setInt(5, this.id);
            preparedStatement.executeUpdate();

            mostrarDialogoPersonalizadoExito("Detalle agregado exitosamente.", Color.decode("#263238"));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al agregar el detalle del evento.", Color.decode("#C62828"));
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