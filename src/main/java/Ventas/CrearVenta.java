package Ventas;
import Clientes.CrearCliente;
import Clientes.ListaClientes;
import Desayunos.CrearDesayuno;
import Materiales.TextPrompt;
import Modelos.*;
import Objetos.*;
import org.jdatepicker.impl.JDatePickerImpl;
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
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.List;

public class CrearVenta extends JFrame {
    private JPanel panel1, panel2, panel3, panel4, panel5, panel6;
    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;
    public JButton guardarButton, cancelarButton, limpiarButton;
    public JTextField campoCodigo, campoFecha, campoCantidad, campoPrecio;
    public JComboBox<String> boxCliente, boxEmpleado, boxMaterial;
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11;
    private JButton botonCrear, imprimirButton, agregarMaterialButton, agregarArregloButton, agregarTarjetasButton, agregarFloresButton, agregarManualidadButton, agregarDesayunoButton, agregarProductoButton, cancelarProductoButton;
    private JTextField campoBusquedaMateriales;
    private JPanel panelCliente;
    private Conexion sql;
    private Connection mysql;
    public CrearVenta crearVenta = this;
    private Materiales.TextPrompt placeholder = new TextPrompt(" Buscar por nombre de producto", campoBusquedaMateriales);
    private List<PoliProducto> productosListTemporal = new ArrayList<>();
    private List<PoliMaterial> materialList = new ArrayList<>();
    private List<PoliMaterial> materialListTemporal = new ArrayList<>();
    private List<PoliFlor> floristeriaList = new ArrayList<>();
    private List<PoliFlor> floristeriaListTemporal = new ArrayList<>();
    private List<PoliTarjeta> tarjetaList = new ArrayList<>();
    private List<PoliTarjeta> tarjetaListTemporal = new ArrayList<>();
    private List<PoliArreglo> arregloList = new ArrayList<>();
    private List<PoliDesayuno> desayunoList = new ArrayList<>();
    private List<PoliManualidad> manualidadList = new ArrayList<>();
    private Map<String,String> tiposDescripcion = new HashMap<>();
    private Map<String,String> tiposTablas = new HashMap<>();
    private int selectTabla = 1;
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
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
    private JTextField[] campos = { campoCodigo, campoFecha, campoCantidad, campoPrecio };
    private JDatePickerImpl datePicker; // Declare the datePicker variable at the class level

    private double sumaISVExento = 0;

    public CrearVenta() {
        super("");
        setSize(1050, 650);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();
        mysql = sql.conectamysql();
        generarCamposAutomaticamente();
        configurarTablaMateriales();

        tiposDescripcion.put("F","floristeria");
        tiposDescripcion.put("M","material");
        tiposDescripcion.put("X","manualidad");
        tiposDescripcion.put("T","tarjeta");
        tiposDescripcion.put("A","arreglo");
        tiposDescripcion.put("D","desayuno");


        tiposTablas.put("F","floristeria");
        tiposTablas.put("M","materiales");
        tiposTablas.put("X","manualidades");
        tiposTablas.put("T","tarjetas");
        tiposTablas.put("A","arreglos");
        tiposTablas.put("D","desayunos");


        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer,List> listas = new HashMap<>();
                listas.put(2,floristeriaList);
                listas.put(3,materialList);
                listas.put(4,manualidadList);
                listas.put(5,tarjetaList);
                listas.put(6,arregloList);
                listas.put(7,desayunoList);

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

                }else  if ( l instanceof PoliManualidad p){
                    id_materialEntero = p.getID();
                    id_material = "X-"+p.getID();

                }else  if ( l instanceof PoliTarjeta p){
                    id_materialEntero = p.getID();
                    id_material = "T-"+p.getID();

                }else  if ( l instanceof PoliArreglo p){
                    id_materialEntero = p.getID();
                    id_material = "A-"+p.getID();

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
                    // Llamar al método guardarDetalleDesayuno con los tres argumentos
                    guardarDetalleVenta(id_materialEntero, cantidadMaterial, l.getTipo());

                    // Crear el material temporal y agregarlo a la lista temporal
                    PoliProductosGeneral materialTemporal = new PoliMaterial();
                    materialTemporal.setID(id_materialEntero);
                    materialTemporal.setNombre( l.getNombre());
                    materialTemporal.setCantidad(cantidadMaterial);
                    materialTemporal.setPrecio(l.getPrecio());
                    materialTemporal.setTipo(l.getTipo());
                    productosListTemporal.add(materialTemporal);

                    campoBusquedaMateriales.setVisible(false);
                    agregarProductoButton.setVisible(false);
                    cancelarProductoButton.setVisible(false);
                    agregarMaterialButton.setVisible(true);
                    agregarFloresButton.setVisible(true);
                    agregarManualidadButton.setVisible(true);
                    agregarTarjetasButton.setVisible(true);
                    agregarArregloButton.setVisible(true);
                    agregarDesayunoButton.setVisible(true);
                    // Actualizar la tabla con los detalles actualizados
                    tablaProductos.setModel(cargarDetallesMateriales());
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

        boxCliente.addItem("Seleccione un cliente");
        cargarClientes();

        boxEmpleado.addItem("Seleccione un empleado");
        cargarEmpleados();

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));

        cancelarProductoButton.setForeground(Color.WHITE);
        cancelarProductoButton.setBackground(darkColorRosado);
        cancelarProductoButton.setFocusPainted(false);

        agregarProductoButton.setForeground(Color.WHITE);
        agregarProductoButton.setBackground(darkColorCyan);
        agregarProductoButton.setFocusPainted(false);

        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setBackground(darkColorRed);
        cancelarButton.setFocusPainted(false);
        cancelarButton.setBorder(margin);

        guardarButton.setForeground(Color.WHITE);
        guardarButton.setBackground(darkColorAqua);
        guardarButton.setFocusPainted(false);
        guardarButton.setBorder(margin);

        limpiarButton.setForeground(Color.WHITE);
        limpiarButton.setBackground(darkColorPink);
        limpiarButton.setFocusPainted(false);
        limpiarButton.setBorder(margin);

        imprimirButton.setForeground(Color.WHITE);
        imprimirButton.setBackground(darkColorVerdeLima);
        imprimirButton.setFocusPainted(false);
        imprimirButton.setBorder(margin);

        agregarMaterialButton.setForeground(Color.DARK_GRAY);
        agregarMaterialButton.setBackground(lightColorAmber);
        agregarMaterialButton.setFocusPainted(false);

        agregarDesayunoButton.setForeground(Color.DARK_GRAY);
        agregarDesayunoButton.setBackground(lightColorCyan);
        agregarDesayunoButton.setFocusPainted(false);

        agregarArregloButton.setForeground(Color.DARK_GRAY);
        agregarArregloButton.setBackground(lightColorAqua);
        agregarArregloButton.setFocusPainted(false);

        agregarFloresButton.setForeground(Color.DARK_GRAY);
        agregarFloresButton.setBackground(lightColorRosado);
        agregarFloresButton.setFocusPainted(false);

        agregarTarjetasButton.setForeground(Color.DARK_GRAY);
        agregarTarjetasButton.setBackground(lightColorVerdeLima);
        agregarTarjetasButton.setFocusPainted(false);

        agregarManualidadButton.setForeground(Color.DARK_GRAY);
        agregarManualidadButton.setBackground(Color.lightGray);
        agregarManualidadButton.setFocusPainted(false);

        JTableHeader header = tablaProductos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(darkColorCyan);

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);
        lbl4.setForeground(textColor);


        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        lbl8.setForeground(textColor);
        lbl8.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl8.setText("0.00");

        lbl9.setForeground(textColor);
        lbl9.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl9.setText("0.00");

        lbl10.setForeground(textColor);
        lbl10.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl10.setText("0.00");

        campoFecha.setFocusable(false);
        campoCodigo.setFocusable(false);
        boxCliente.setFocusable(false);
        boxEmpleado.setFocusable(false);

        agregarMaterialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarProductoButton.setVisible(true);
                cancelarProductoButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                tablaProductos.setModel(cargarDatosMaterial());
            }
        });

        agregarArregloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarProductoButton.setVisible(true);
                cancelarProductoButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                tablaProductos.setModel(cargarDatosArreglo());
            }
        });

        agregarFloresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarProductoButton.setVisible(true);
                cancelarProductoButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                tablaProductos.setModel(cargarDatosFloristeria());
            }
        });

        agregarTarjetasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarProductoButton.setVisible(true);
                cancelarProductoButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                tablaProductos.setModel(cargarDatosTarjeta());
            }
        });

        agregarDesayunoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarProductoButton.setVisible(true);
                cancelarProductoButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                tablaProductos.setModel(cargarDatosDesayuno());
            }
        });

        agregarManualidadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(true);
                agregarProductoButton.setVisible(true);
                cancelarProductoButton.setVisible(true);
                agregarMaterialButton.setVisible(false);
                agregarFloresButton.setVisible(false);
                agregarArregloButton.setVisible(false);
                agregarTarjetasButton.setVisible(false);
                agregarManualidadButton.setVisible(false);
                agregarDesayunoButton.setVisible(false);
                tablaProductos.setModel(cargarDatosManualidad());
            }
        });

        cancelarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                campoBusquedaMateriales.setVisible(false);
                agregarProductoButton.setVisible(false);
                cancelarProductoButton.setVisible(false);
                agregarMaterialButton.setVisible(true);
                agregarFloresButton.setVisible(true);
                agregarManualidadButton.setVisible(true);
                agregarTarjetasButton.setVisible(true);
                agregarArregloButton.setVisible(true);
                agregarDesayunoButton.setVisible(true);
                tablaProductos.setModel(cargarDetallesMateriales());
                actualizarLbl8y10();
                configurarTablaMateriales();
            }
        });


       /*
        campoCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoCantidad.getText();
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
        */

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
                if (newText.length() > 50) {
                    e.consume();  // Ignorar la tecla si se supera la longitud máxima
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Sí", "No"};
                int opcion = JOptionPane.showOptionDialog(null, "¿Está seguro que desea limpiar?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (opcion == JOptionPane.YES_OPTION) {
                    limpiar();
                }
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                if (campoCodigo.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "El código\n";
                }

                if (campoFecha.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "El código\n";
                }

                String clienteText = boxCliente.getSelectedItem().toString();
                if (clienteText.equals("Seleccione un cliente")) {
                    validacion++;
                    mensaje += "El cliente\n";
                }

                String empleadoText = boxEmpleado.getSelectedItem().toString();
                if (empleadoText.equals("Seleccione un empleado")) {
                    validacion++;
                    mensaje += "El empleado\n";
                }

                if (tablaProductos.getRowCount() == 0) {
                    validacion++;
                    mensaje += "Los productos\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                guardarDatos();
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearCliente cliente = new CrearCliente(crearVenta); // Pasa la referencia a CrearVenta
                cliente.setVisible(true);
                crearVenta.dispose();
            }
        });

        botonCrear.setBackground(darkColorBlue);
        botonCrear.setForeground(Color.WHITE);
        botonCrear.setFocusable(false);

        tablaProductos.setModel(cargarDetallesMateriales());
        actualizarLbl8y10();
        configurarTablaMateriales();
        agregarProductoButton.setVisible(false);
        cancelarProductoButton.setVisible(false);
        campoBusquedaMateriales.setVisible(false);
        campoBusquedaMateriales.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                tablaProductos.setModel(cargarDatosMaterial());
            }
        });
    }

    public void cargarClientes() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nombre, apellido FROM clientes");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idCliente = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String clienteText = idCliente + " - " + nombre + " - " + apellido;
                boxCliente.addItem(clienteText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void cargarEmpleados() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, Nombres, Apellidos FROM Empleados");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idEmpleado = resultSet.getInt("id");
                String nombres = resultSet.getString("Nombres");
                String apellidos = resultSet.getString("Apellidos");
                String empleadoText = idEmpleado + " - " + nombres + " " + apellidos;
                boxEmpleado.addItem(empleadoText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JButton crearBotonEliminar() {
        JButton botonEliminar = new JButton("Eliminar");
        botonEliminar.setForeground(Color.WHITE);
        botonEliminar.setBackground(darkColorRed);
        botonEliminar.setFocusPainted(false);
        botonEliminar.setBorder(margin);

        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Sí", "No"};
                int opcion = JOptionPane.showOptionDialog(null, "¿Está seguro que desea eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (opcion == JOptionPane.YES_OPTION) {
                    JButton boton = (JButton) e.getSource();
                    int filaSeleccionada = tablaProductos.convertRowIndexToModel(tablaProductos.getEditingRow());
                    modeloProductos.removeRow(filaSeleccionada);
                    actualizarTotales();
                }
            }
        });
        return botonEliminar;
    }

    private void guardarDatos() {
        Object[] options = {"Sí", "No"};
        int confirmacionGuardar = JOptionPane.showOptionDialog(null, "¿Desea guardar la venta?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (confirmacionGuardar != JOptionPane.YES_OPTION) {
            return;
        }

        String codigoVenta = campoCodigo.getText();
        String fechaCampo = campoFecha.getText();

        // Convertir la fecha en formato "Domingo, 30 de julio de 2023" a "2023-07-30"
        String fechaVenta = convertirFecha(fechaCampo);

        int clienteId = Integer.parseInt(boxCliente.getSelectedItem().toString().split(" - ")[0]);
        int empleadoId = Integer.parseInt(boxEmpleado.getSelectedItem().toString().split(" - ")[0]);

        try (PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO ventas (codigo_venta, fecha, cliente_id, empleado_id) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, codigoVenta);
            preparedStatement.setString(2, fechaVenta);
            preparedStatement.setInt(3, clienteId);
            preparedStatement.setInt(4, empleadoId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int ventaId = 0;
        try (PreparedStatement preparedStatement = mysql.prepareStatement("SELECT LAST_INSERT_ID()")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                ventaId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            String producto = (String) modeloProductos.getValueAt(i, 0);
            int cantidad = (int) modeloProductos.getValueAt(i, 1);
            double precio = (double) modeloProductos.getValueAt(i, 2);
            int materialId = 0;
            try (PreparedStatement preparedStatement = mysql.prepareStatement("SELECT id FROM materiales WHERE nombre = ?")) {
                preparedStatement.setString(1, producto);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    materialId = resultSet.getInt("id");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            boolean exento = false; // Valor por defecto
            if (modeloProductos.getValueAt(i, 4).toString().equalsIgnoreCase("Exento")) {
                exento = true;
            }
            try (PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO detalles_ventas (venta_id, material_id, cantidad, precio) VALUES (?, ?, ?, ?)")) {
                preparedStatement.setInt(1, ventaId);
                preparedStatement.setInt(2, materialId);
                preparedStatement.setInt(3, cantidad);
                preparedStatement.setDouble(4, precio);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Actualizar la cantidad en la tabla "materiales"
            int cantidadAnterior = 0;
            try (PreparedStatement consultaCantidad = mysql.prepareStatement("SELECT cantidad FROM materiales WHERE id = ?")) {
                consultaCantidad.setInt(1, materialId);
                ResultSet resultSet = consultaCantidad.executeQuery();
                if (resultSet.next()) {
                    cantidadAnterior = resultSet.getInt("cantidad");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            int nuevaCantidad = cantidadAnterior - cantidad;

            try (PreparedStatement actualizarCantidad = mysql.prepareStatement("UPDATE materiales SET cantidad = ? WHERE id = ?")) {
                actualizarCantidad.setInt(1, nuevaCantidad);
                actualizarCantidad.setInt(2, materialId);
                actualizarCantidad.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        boolean listaVentaAbierta = false;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof ListaClientes) {
                listaVentaAbierta = true;
                break;
            }
        }

        if (!listaVentaAbierta) {
            JOptionPane.showMessageDialog(null, "Venta registrada exitosamente.", "Éxito", JOptionPane.DEFAULT_OPTION);
        }

        crearVenta.dispose();

        ListaVentas ventas = new ListaVentas();
        ventas.setVisible(true);
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

    private void actualizarTotales() {
        double sumaSubtotal = 0.0;
        double sumaISV = 0.0;
        double sumaISVExento = 0.0;
        double sumaTotal = 0.0;
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            try {
                double subtotal = Double.parseDouble(modeloProductos.getValueAt(i, 3).toString().replace(",", "."));
                double isv;
                if (modeloProductos.getValueAt(i, 4).toString().equalsIgnoreCase("Exento")) {
                    isv = subtotal;  // Exempted amount goes into ISV
                    sumaISVExento += isv;
                } else {
                    isv = Double.parseDouble(modeloProductos.getValueAt(i, 4).toString().replace(",", "."));
                    sumaISV += isv;

                    sumaSubtotal += subtotal;

                }
                double total = Double.parseDouble(modeloProductos.getValueAt(i, 5).toString().replace(",", "."));
                sumaTotal += total;

            } catch (NumberFormatException e) {
                // Handle the case when the string cannot be parsed as a double
                // You can choose to display an error message or take any other appropriate action
                System.err.println("Invalid number format encountered. Skipping calculation for row " + i);
            }
        }
        double isvExento = sumaISVExento;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        String sumaSubtotalFormatted = decimalFormat.format(sumaSubtotal);

        String sumaISVFormatted = decimalFormat.format(sumaISV);
        String sumaISVExentoFormatted = decimalFormat.format(isvExento);

        String sumaTotalFormatted = decimalFormat.format(sumaTotal);
        lbl8.setText(" " + sumaSubtotalFormatted);
        lbl9.setText(" " + sumaISVFormatted);
        lbl10.setText(" " + sumaTotalFormatted);
    }

    private void cancelar() {
        Object[] options = {"Sí", "No"};
        int dialogResult = JOptionPane.showOptionDialog(null, "¿Está seguro de que desea cancelar?", "Confirmar cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (dialogResult == JOptionPane.YES_OPTION) {
            boolean listaVentaAbierta = false;
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof ListaClientes) {
                    listaVentaAbierta = true;
                    break;
                }
            }
            if (!listaVentaAbierta) {
                ListaVentas ventas = new ListaVentas();
                ventas.setVisible(true);
            }
            crearVenta.dispose();
        }
    }

    private void limpiar() {
        boxCliente.setSelectedIndex(0);
        boxEmpleado.setSelectedIndex(0);
        boxMaterial.setSelectedIndex(0);
        campoCantidad.setText("");
        campoPrecio.setText("");
        modeloProductos.setRowCount(0);
        lbl8.setText("0.00");
        lbl9.setText("0.00");
        lbl10.setText("0.00");
    }

    private boolean existeProductoEnTabla(String producto) {
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            String nombreProducto = (String) modeloProductos.getValueAt(i, 0);
            if (nombreProducto.equals(producto)) {
                return true;
            }
        }
        return false;
    }

    private void configurarTablaMateriales() {
        int columnCount = tablaProductos.getColumnCount();
        if (columnCount > 0) {
            TableColumnModel columnModel = tablaProductos.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(20); // Id
            columnModel.getColumn(1).setPreferredWidth(200); // Nombre
            columnModel.getColumn(2).setPreferredWidth(60);  // Precio
            columnModel.getColumn(3).setPreferredWidth(100); // Proveedor

            columnModel.getColumn(0).setCellRenderer(new CrearVenta.CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new CrearVenta.LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new CrearVenta.LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new CrearVenta.LeftAlignedRenderer());
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

    public boolean isDateOutOfRange(Date selectedDate, Calendar firstDayOfMonth, Calendar today) {
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);
        return selectedCal.before(firstDayOfMonth) || selectedCal.after(today);
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

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void generarCamposAutomaticamente() {
        // Generar campoCodigo
        String codigo = generarCodigo();
        campoCodigo.setText(codigo);

        // Generar campoFecha
        String fecha = obtenerFechaActual();
        campoFecha.setText(fecha);
    }

    private String generarCodigo() {
        // Obtener fecha actual
        Date fechaActual = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fecha = dateFormat.format(fechaActual);

        // Obtener hora actual en formato "a.m." o "p.m."
        SimpleDateFormat hourFormat = new SimpleDateFormat("hhmmss");
        String hora = hourFormat.format(fechaActual).toUpperCase();

        // Generar número aleatorio de 5 dígitos
        Random random = new Random();
        int numeroAleatorio = random.nextInt(100000);

        // Formatear el código final
        String codigo = "V-" + fecha + "-" + hora + "-" + String.format("%05d", numeroAleatorio);
        return codigo;
    }

    private String obtenerFechaActual() {
        // Obtener fecha actual
        Date fechaActual = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
        String fecha = dateFormat.format(fechaActual);

        // Capitalizar primera letra del día de la semana
        fecha = fecha.substring(0, 1).toUpperCase() + fecha.substring(1);

        return fecha;
    }














    private void guardarDetalleVenta(int id_material, int cantidad, String tipo) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO detalles_desayunos (tipo_detalle, detalle_id, cantidad,precio) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, tiposDescripcion.get(tipo));
            preparedStatement.setInt(2, id_material);
            preparedStatement.setInt(3, cantidad);
            preparedStatement.setDouble(4, obtenerPrecioMaterialDesdeBD(id_material,tipo)); // Obtener el precio del material desde la base de datos
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Detalle agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al agregar el detalle de la manualidad", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double obtenerPrecioMaterialDesdeBD(int id_material, String tipo) {
        double precio = 0.0;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT "+(tipo.equals("T")?"precio_tarjeta":"precio")+" FROM "+tiposTablas.get(tipo)+" WHERE id = ?")) {
            preparedStatement.setInt(1, id_material);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                precio = resultSet.getDouble(tipo.equals("T")?"precio_tarjeta":"precio");
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

        // Actualizar el lbl8 con el total calculado
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String sumaTotalFormateado = decimalFormat.format(sumaTotal);
        lbl8.setText(" " + sumaTotalFormateado);

        return sumaTotal;
    }

    private void actualizarLbl8y10() {
        // Calcular el subtotal de la tabla (antes de aplicar el descuento)
        double totalTabla = calcularTotalTabla();

        // Calcular el total con el descuento y aumento
        double subTotal = totalTabla * 0.85;
        double ISV = totalTabla * 0.15;
        double totalConAumento = totalTabla;

        // Actualizar lbl8 con el subtotal con descuento
        lbl8.setText(String.format("L. %.2f", subTotal));

        // Actualizar lbl9 con el total sin descuento ni aumento (es decir, el subtotal original)
        lbl9.setText(String.format("L. %.2f", ISV));

        // Actualizar lbl10 con el total con aumento
        lbl10.setText(String.format("L. %.2f", totalConAumento));
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
        sql = new Conexion();
        productosListTemporal.clear(); // Limpiar la lista antes de agregar los materiales
        selectTabla = 1;

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT detalles_ventas.*,'F' AS 'tipo', floristeria.nombre AS 'nombre', (detalles_ventas.cantidad * detalles_ventas.precio) AS 'total' FROM detalles_ventas "+
                             " JOIN floristeria ON floristeria.id = detalles_ventas.detalle_id "+
                             " WHERE detalles_ventas.venta_id IS NULL AND detalles_ventas.tipo_detalle = 'floristeria' "+

                             " UNION "+

                             " SELECT detalles_ventas.*,'T' AS 'tipo',tarjetas.descripcion AS 'nombre', (detalles_ventas.cantidad * detalles_ventas.precio) AS 'total' FROM detalles_ventas "+
                             " JOIN tarjetas ON tarjetas.id = detalles_ventas.detalle_id "+
                             " WHERE detalles_ventas.venta_id IS NULL AND detalles_ventas.tipo_detalle = 'tarjeta' "+

                             " UNION "+

                             " SELECT detalles_ventas.*,'X' AS 'tipo',manualidades.nombre AS 'nombre', (detalles_ventas.cantidad * detalles_ventas.precio) AS 'total' FROM detalles_ventas "+
                             " JOIN manualidades ON manualidades.id = detalles_ventas.detalle_id "+
                             " WHERE detalles_ventas.venta_id IS NULL AND detalles_ventas.tipo_detalle = 'manualidad' "+

                             " UNION "+

                             " SELECT detalles_ventas.*,'A' AS 'tipo',arreglos.nombre AS 'nombre', (detalles_ventas.cantidad * detalles_ventas.precio) AS 'total' FROM detalles_ventas "+
                             " JOIN arreglos ON arreglos.id = detalles_ventas.detalle_id "+
                             " WHERE detalles_ventas.venta_id IS NULL AND detalles_ventas.tipo_detalle = 'arreglo' "+

                             " UNION "+

                             " SELECT detalles_ventas.*,'D' AS 'tipo',desayunos.nombre AS 'nombre', (detalles_ventas.cantidad * detalles_ventas.precio) AS 'total' FROM detalles_ventas "+
                             " JOIN desayunos ON desayunos.id = detalles_ventas.detalle_id "+
                             " WHERE detalles_ventas.venta_id IS NULL AND detalles_ventas.tipo_detalle = 'desayuno' "+

                             " UNION "+

                             " SELECT detalles_desayunos.*,'M' AS 'tipo',materiales.nombre AS 'nombre', (detalles_desayunos.cantidad * detalles_desayunos.precio) AS 'total' FROM detalles_desayunos "+
                             " JOIN materiales ON materiales.id = detalles_desayunos.detalle_id "+
                             " WHERE detalles_desayunos.desayuno_id IS NULL AND detalles_desayunos.tipo_detalle = 'material';"
             )
        ) {
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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            floristeriaList = new ArrayList<>();
        }

        PoliModeloFlor modeloFlor = new PoliModeloFlor(floristeriaList, sql);
        tablaProductos.setModel(modeloFlor);
        configurarTablaMateriales();
        return modeloFlor;
    }

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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            materialList = new ArrayList<>();
        }

        PoliModeloMaterial modeloMaterial = new PoliModeloMaterial(materialList, sql);
        tablaProductos.setModel(modeloMaterial);
        configurarTablaMateriales();
        return modeloMaterial;
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
                manualidad.setPrecio(resultSet.getDouble("precio"));
                manualidad.setTipo("X");
                manualidadList.add(manualidad);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            manualidadList = new ArrayList<>();
        }

        PoliModeloManualidad modeloManualidad = new PoliModeloManualidad(manualidadList, sql);
        tablaProductos.setModel(modeloManualidad);
        configurarTablaMateriales();
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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            tarjetaList = new ArrayList<>();
        }

        PoliModeloTarjeta modeloTarjeta = new PoliModeloTarjeta(tarjetaList, sql);
        tablaProductos.setModel(modeloTarjeta);
        configurarTablaMateriales();
        return modeloTarjeta;
    }

    private PoliModeloArreglo cargarDatosArreglo() {
        sql = new Conexion();
        arregloList.clear();
        selectTabla = 6;
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
                arreglo.setTipo("A");
                arregloList.add(arreglo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            arregloList = new ArrayList<>();
        }

        PoliModeloArreglo modeloArreglo = new PoliModeloArreglo(arregloList, sql);
        tablaProductos.setModel(modeloArreglo);
        configurarTablaMateriales();
        return modeloArreglo;
    }

    private PoliModeloDesayuno cargarDatosDesayuno() {
        sql = new Conexion();
        desayunoList.clear();
        selectTabla = 7;
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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            desayunoList = new ArrayList<>();
        }

        PoliModeloDesayuno modeloDesayuno = new PoliModeloDesayuno(desayunoList, sql);
        tablaProductos.setModel(modeloDesayuno);
        configurarTablaMateriales();
        return modeloDesayuno;
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

        public ButtonEditor(JCheckBox checkBox) {
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
                DefaultTableModel model = (DefaultTableModel) table.getModel();

                // Verificar si el modelo de la tabla tiene la fila que se intenta eliminar
                if (modelRow >= 0 && modelRow < model.getRowCount()) {
                    fireEditingStopped(); // Mover la llamada a fireEditingStopped() aquí
                    model.removeRow(modelRow);
                    actualizarTotales();
                }
            }
        }
    }




    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CrearVenta frame = new CrearVenta();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
