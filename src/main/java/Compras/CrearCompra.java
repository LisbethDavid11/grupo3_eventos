/**
 * Crear Compra.java
 *
 * Crear Compra
 *
 * @author Lisbeth David
 * @version 1.0
 * @since 2024-05-05
 */

package Compras;

import Clientes.ListaClientes;
import Objetos.Conexion;
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
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class CrearCompra extends JFrame {
    // Panel
    private JPanel panel1;

    // Tabla de productos y modelo
    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;

    // Botones
    public JButton guardarButton;
    public JButton cancelarButton;
    public JButton limpiarButton;
    public JButton agregarButton;

    // Campos de texto
    public JTextField campoCodigo;
    public JTextField campoFecha;
    public JTextField campoCantidad;
    public JTextField campoPrecio;

    // Comboboxes
    public JComboBox<String> boxProveedor;
    public JComboBox<String> boxEmpleado;
    public JComboBox<String> boxMaterial;

    // Etiquetas
    private JLabel lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel lbl7;
    private JLabel lbl8;
    private JLabel lbl9;
    private JLabel lbl10;
    private JLabel lbl11;
    private JLabel lbl12;

    // Paneles
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;

    // Conexión a la base de datos
    private Conexion sql;
    private Connection mysql;

    // Referencia a la ventana de creación de compra
    public CrearCompra crearCompra = this;

    // Variable para la suma de ISV exento
    private double sumaISVExento = 0;

    // Colores
    Color textColor = Color.decode("#212121");
    Color darkColorCyan = new Color(0, 150, 136);
    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    // Margen
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    // Fuentes
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 17);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

    // Array de campos de texto
    private JTextField[] campos = { campoCodigo, campoFecha, campoCantidad, campoPrecio };

    // Selector de fecha
    private JDatePickerImpl datePicker; // Declara la variable datePicker a nivel de clase

    public CrearCompra() {
        super("");
        setSize(1100, 850);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();
        mysql = sql.conectamysql();

        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoy");
        properties.put("text.month", "Mes");
        properties.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        datePicker = new JDatePickerImpl(datePanel, new SimpleDateFormatter());  // Proporcionar un formateador

        Calendar today = Calendar.getInstance();
        Calendar firstDayOfMonth = getFirstDayOfMonth();

        dateModel.addChangeListener(e -> {
            handleDateChange(dateModel, firstDayOfMonth, today);
        });

        // Show initial date in date field
        handleDateChange(dateModel, firstDayOfMonth, today);

        panel3.add(datePicker);

        boxProveedor.addItem("Seleccione un proveedor");
        cargarProveedores();

        boxEmpleado.addItem("Seleccione un empleado");
        cargarEmpleados();

        boxMaterial.addItem("Seleccione un producto");
        cargarMateriales();

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));

        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setBackground(darkColorRed);
        cancelarButton.setFocusPainted(false);
        cancelarButton.setBorder(margin);

        guardarButton.setForeground(Color.WHITE);
        guardarButton.setBackground(darkColorCyan);
        guardarButton.setFocusPainted(false);
        guardarButton.setBorder(margin);

        limpiarButton.setForeground(Color.WHITE);
        limpiarButton.setBackground(darkColorPink);
        limpiarButton.setFocusPainted(false);
        limpiarButton.setBorder(margin);

        agregarButton.setForeground(Color.DARK_GRAY);
        agregarButton.setBackground(darkColorBlue);
        agregarButton.setFocusPainted(false);
        agregarButton.setBorder(margin);

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);
        lbl4.setForeground(textColor);
        lbl5.setForeground(textColor);
        lbl6.setForeground(textColor);
        lbl7.setForeground(textColor);
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

        lbl12.setForeground(textColor);
        lbl12.setHorizontalAlignment(SwingConstants.RIGHT);
        lbl12.setText("0.00");

        modeloProductos = new DefaultTableModel();
        modeloProductos.addColumn("Nombre");
        modeloProductos.addColumn("Cantidad");
        modeloProductos.addColumn("Precio");
        modeloProductos.addColumn("Sub Total");
        modeloProductos.addColumn("ISV");
        modeloProductos.addColumn("Total");
        modeloProductos.addColumn("Eliminar");

        tablaProductos.setModel(modeloProductos);
        tablaProductos.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tablaProductos.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
        configurarTablaProductos();

        JTableHeader header = tablaProductos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(Color.decode("#263238"));

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

        // Agrega un ActionListener al JComboBox "boxMaterial"
        boxMaterial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener el nombre del producto seleccionado en el JComboBox
                String nombreProducto = boxMaterial.getSelectedItem().toString();

                // Obtener el precio del producto desde la base de datos
                double precioProducto = obtenerPrecioProducto(nombreProducto);

                // Formatear el precio con el símbolo punto (.) como separador decimal
                DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
                DecimalFormat decimalFormat = new DecimalFormat("##,##0.00", symbols);
                String precioFormateado = decimalFormat.format(precioProducto);

                // Establecer el precio formateado en el JTextField "campoPrecio"
                campoPrecio.setText(precioFormateado);
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

                if (datePicker.getJFormattedTextField().getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "La fecha\n";
                }

                String proveedorText = boxProveedor.getSelectedItem().toString();
                if (proveedorText.equals("Seleccione un proveedor")) {
                    validacion++;
                    mensaje += "El proveedor\n";
                }

                // Verificar si ya existe un empleado con la misma identidad
                if (validarCodigoExistente(campoCodigo.getText().trim())) {
                    mostrarDialogoPersonalizadoError("El código ingresado ya está asociada a otra compra.", Color.decode("#C62828"));
                    return; // Detener la ejecución del método
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

                guardarDatos(datePicker);

                ListaCompras listaCompras = new ListaCompras();
                listaCompras.setVisible(true);
                crearCompra.dispose();
            }
        });

        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                String productoText = boxMaterial.getSelectedItem().toString();
                if (productoText.equals("Seleccione un producto")) {
                    validacion++;
                    mensaje += "El producto\n";
                }

                if (campoCantidad.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "La cantidad\n";
                }

                if (campoPrecio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "El precio\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (campoPrecio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "El precio\n";
                } else {
                    double precio = Double.parseDouble(campoPrecio.getText().trim());
                    if (!((precio >= 1 && precio <= 99999) || (precio >= 1 && precio <= 999.99))) {
                        JOptionPane.showMessageDialog(null, "El precio debe estar entre 1 y 99999 o entre 1 y 999.99", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                if (campoCantidad.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "La cantidad\n";
                } else {
                    int cantidad = Integer.parseInt(campoCantidad.getText().trim());
                    if (!(cantidad >= 1 && cantidad <= 99999)) {
                        JOptionPane.showMessageDialog(null, "La cantidad debe estar entre 1 y 99999", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String codigo = campoCodigo.getText().trim();
                if (codigo.isEmpty()) {
                    validacion++;
                    mensaje += "El código\n";
                } else if (codigo.length() < 1 || codigo.length() > 50) {
                    JOptionPane.showMessageDialog(null, "El código debe tener entre 1 y 50 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                agregarProducto();
            }
        });
    }

    // Método para cargar los proveedores
    private void cargarProveedores() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, empresaProveedora, nombreVendedor FROM Proveedores");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idProveedor = resultSet.getInt("id");
                String empresaProveedora = resultSet.getString("empresaProveedora");
                String nombreVendedor = resultSet.getString("nombreVendedor");
                String proveedorText = idProveedor + " - " + empresaProveedora + " - " + nombreVendedor;
                boxProveedor.addItem(proveedorText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para cargar los empleados
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

    // Método para cargar los materiales
    private void cargarMateriales() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nombre FROM materiales");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String materialText = nombre;
                boxMaterial.addItem(materialText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para el precio del producto
    private double obtenerPrecioProducto(String nombreProducto) {
        double precio = 0.0;
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT precio FROM materiales WHERE nombre = ?")) {

            preparedStatement.setString(1, nombreProducto);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Obtener el precio como cadena desde la base de datos
                String precioStr = resultSet.getString("precio");

                // Reemplazar la coma por el punto en el precio para el formato adecuado
                precioStr = precioStr.replace(",", ".");

                // Convertir el precio formateado a double
                precio = Double.parseDouble(precioStr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return precio;
    }

    // Método para el botón de eliminar
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

    // Método para guardar los datos de la compra
    private void guardarDatos(JDatePickerImpl datePicker) {
        sql = new Conexion();
        mysql = sql.conectamysql();

        Object[] options = {"Sí", "No"};
        int confirmacionGuardar = JOptionPane.showOptionDialog(null, "¿Desea guardar la compra?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (confirmacionGuardar != JOptionPane.YES_OPTION) {
            return;
        }

        String codigo_compra = campoCodigo.getText();
        Date fechaActual = (Date) datePicker.getModel().getValue(); // Explicitly cast the value to Date
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(fechaActual);
        int proveedor_id = Integer.parseInt(boxProveedor.getSelectedItem().toString().split(" - ")[0]);
        int empleado_id = Integer.parseInt(boxEmpleado.getSelectedItem().toString().split(" - ")[0]);



        try (Connection connection = sql.conectamysql();
                PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO compras (codigo_compra, fecha, proveedor_id, empleado_id) VALUES (?, ?, ?, ?)"))  {
            preparedStatement.setString(1, codigo_compra);
            preparedStatement.setString(2, fecha);
            preparedStatement.setInt(3, proveedor_id);
            preparedStatement.setInt(4, empleado_id);
            preparedStatement.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        }
        int compraId = 0;
        try (PreparedStatement preparedStatement = mysql.prepareStatement("SELECT LAST_INSERT_ID()")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                compraId = resultSet.getInt(1);
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
            try (PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO detalles_compras (compra_id, material_id, cantidad, precio) VALUES (?, ?, ?, ?)")) {
                preparedStatement.setInt(1, compraId);
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

            int nuevaCantidad = cantidadAnterior + cantidad;

            try (PreparedStatement actualizarCantidad = mysql.prepareStatement("UPDATE materiales SET cantidad = ? WHERE id = ?")) {
                actualizarCantidad.setInt(1, nuevaCantidad);
                actualizarCantidad.setInt(2, materialId);
                actualizarCantidad.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (PreparedStatement actualizarPrecio = mysql.prepareStatement("UPDATE materiales SET precio = ? WHERE id = ?")) {
                actualizarPrecio.setDouble(1, precio);
                actualizarPrecio.setInt(2, materialId);
                actualizarPrecio.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        boolean listaCompraAbierta = false;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof ListaClientes) {
                listaCompraAbierta = true;
                break;
            }
        }
        if (!listaCompraAbierta) {
            JOptionPane.showMessageDialog(null, "Compra registrada exitosamente.", "Éxito", JOptionPane.DEFAULT_OPTION);
        }
        crearCompra.dispose();

        if (!listaCompraAbierta) {
            ListaCompras compras = new ListaCompras();
            compras.setVisible(true);
        }
    }

    // Método para actualizar los totales
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
        lbl12.setText(" " + sumaISVExentoFormatted);
    }

    // Método para cancelar la acción de guardar los datos
    private void cancelar() {
        Object[] options = {"Sí", "No"};
        int dialogResult = JOptionPane.showOptionDialog(null, "¿Está seguro de que desea cancelar?", "Confirmar cancelación", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (dialogResult == JOptionPane.YES_OPTION) {
            boolean listaCompraAbierta = false;
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof ListaClientes) {
                    listaCompraAbierta = true;
                    break;
                }
            }
            if (!listaCompraAbierta) {
                ListaCompras compras = new ListaCompras();
                compras.setVisible(true);
            }
            crearCompra.dispose();
        }
    }

    // Método para limpiar los datos
    private void limpiar() {
        campoCodigo.setText("");
        boxProveedor.setSelectedIndex(0);
        boxEmpleado.setSelectedIndex(0);
        boxMaterial.setSelectedIndex(0);
        campoCantidad.setText("");
        campoPrecio.setText("");
        modeloProductos.setRowCount(0);
        datePicker.getJFormattedTextField().setText("");
        lbl8.setText("0.00");
        lbl9.setText("0.00");
        lbl10.setText("0.00");
        lbl12.setText("0.00");
    }

    // Método para saber si existe ya el prodto en la tabla
    private boolean existeProductoEnTabla(String producto) {
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            String nombreProducto = (String) modeloProductos.getValueAt(i, 0);
            if (nombreProducto.equals(producto)) {
                return true;
            }
        }
        return false;
    }

    // Método booleano para saber si el producto está excento de ISV
    public boolean obtenerExentoPorNombre(String nombreMaterial) {
        boolean exento = false;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT exento FROM materiales WHERE nombre = ?")) {

            preparedStatement.setString(1, nombreMaterial);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exento = resultSet.getBoolean("exento");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }
        return exento;
    }

    // Método para agregar productos a la tabla
    private void agregarProducto() {

        String producto = boxMaterial.getSelectedItem().toString();
        if (producto.equals("Seleccione un producto")) {
            mostrarMensajeError("Debe seleccionar un producto válido.");
            return;
        }

        if (existeProductoEnTabla(producto)) {
            mostrarMensajeError("El producto ya ha sido agregado.");
            return;
        }

        int cantidad = Integer.parseInt(campoCantidad.getText());
        double precio = Double.parseDouble(campoPrecio.getText());
        double subtotal = cantidad * precio;
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String isv;
        String total;

        boolean exento = obtenerExentoPorNombre(producto);

        if (exento) {
            isv = "Exento";
            total = decimalFormat.format(subtotal);
            sumaISVExento += subtotal;  // Sumar al total de productos exentos
        } else {
            double impuesto = subtotal * 0.15;
            double totalProducto = subtotal + impuesto;
            isv = decimalFormat.format(impuesto);
            total = decimalFormat.format(totalProducto);
        }

        Object[] fila = { producto, cantidad, precio, subtotal, isv, total, crearBotonEliminar() };
        modeloProductos.addRow(fila);
        campoCantidad.setText("");
        campoPrecio.setText("");
        actualizarTotales();
    }

    // Método para configurar la tabla
    private void configurarTablaProductos() {
        TableColumnModel columnModel = tablaProductos.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(200); // Nombre
        columnModel.getColumn(1).setPreferredWidth(50);  // Cantidad
        columnModel.getColumn(2).setPreferredWidth(60); // Precio
        columnModel.getColumn(3).setPreferredWidth(60); // Sub Total
        columnModel.getColumn(4).setPreferredWidth(60);  // ISV
        columnModel.getColumn(5).setPreferredWidth(60); // Total

        columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new LeftAlignedRenderer());
    }

    // Clase para alinear los elementos a la izquierda
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

    // Clase para alinear los elementos al centro
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

    // Método para obtener el primer día del mes
    public Calendar getFirstDayOfMonth() {
        Calendar firstDayOfMonth = Calendar.getInstance();
        firstDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstDayOfMonth.set(Calendar.HOUR_OF_DAY, 0);
        firstDayOfMonth.set(Calendar.MINUTE, 0);
        firstDayOfMonth.set(Calendar.SECOND, 0);
        firstDayOfMonth.set(Calendar.MILLISECOND, 0);
        return firstDayOfMonth;
    }

    // Método para cambiar la fecha
    public void handleDateChange(UtilDateModel dateModel, Calendar firstDayOfMonth, Calendar today) {
        Date selectedDate = dateModel.getValue();
        if (selectedDate != null && isDateOutOfRange(selectedDate, firstDayOfMonth, today)) {
            JOptionPane.showMessageDialog(null, "La fecha seleccionada está fuera del rango permitido", "Error", JOptionPane.ERROR_MESSAGE);
            selectedDate = today.getTime();
            dateModel.setValue(selectedDate);
        }
        setFormattedDate(selectedDate);
    }

    // Método booleano para determinar si la fecha está fuera de rango
    public boolean isDateOutOfRange(Date selectedDate, Calendar firstDayOfMonth, Calendar today) {
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);
        return selectedCal.before(firstDayOfMonth) || selectedCal.after(today);
    }

    // Método para establecer el formato a la fecha
    public void setFormattedDate(Date selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd 'de' MMMM yyyy"); // Desired date format
        String formattedDate = (selectedDate != null) ? dateFormat.format(selectedDate) : "";
        datePicker.getJFormattedTextField().setText(formattedDate);
    }

    // Clase para formatear la fecha de manera simple
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

    // Método para mostrar el mensaje en cualquier caso de error
    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
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

    // Método para validar que no exista una compra registrada
    private boolean validarCodigoExistente(String codigo_compra) {
        try {
            mysql = sql.conectamysql();
            String query = "SELECT COUNT(*) FROM compras WHERE codigo_compra = ?";
            PreparedStatement statement = mysql.prepareStatement(query);
            statement.setString(1, codigo_compra);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (mysql != null) {
                try {
                    mysql.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
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

    // Método Principal
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CrearCompra frame = new CrearCompra();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
