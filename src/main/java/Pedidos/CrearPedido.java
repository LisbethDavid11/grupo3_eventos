package Pedidos;

import Objetos.Conexion;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class CrearPedido extends JFrame {
    private JTextField campoCodigo, campoFechaPedido, CampoTelefono;
    private JTextArea campoDescripcion, campoDireccion;
    private JRadioButton radioButtonDomicilio, radioButtonTienda;
    private JComboBox<String> comboBoxCliente;
    private JButton botonGuardar, botonCancelar, botonLimpiar;
    private JPanel panel;
    private JLabel lbl0, lbl1, lbl2, lbl3,lbl4, lbl5, lbl6,lbl7, lbl8;
    private JPanel panel1, panel2, panel3, panel4, campoFechaEntrega;
    private JTextField campoTelefono;
    private String imagePath = "";
    private CrearPedido actual = this;
    private Conexion sql;
    private JDatePickerImpl datePicker; // Declare the datePicker variable at the class level
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

    // Crea un margen de 10 píxeles desde el borde inferior
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public CrearPedido() {
        super("");
        setSize(600, 610);
        setLocationRelativeTo(null);
        setContentPane(panel);
        generarCamposAutomaticamente();

        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoy");
        properties.put("text.month", "Mes");
        properties.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        datePicker = new JDatePickerImpl(datePanel, new CrearPedido.SimpleDateFormatter());  // Proporcionar un formateador

        Calendar tomorrow = getTomorrow(); // Obtén el día siguiente al actual

        dateModel.addChangeListener(e -> {
            handleDateChange(dateModel, tomorrow);
        });

// Show initial date in date field (puede ser el día siguiente al actual)
        handleDateChange(dateModel, tomorrow);

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

        sql = new Conexion();
        comboBoxCliente.addItem("Seleccione un cliente"); // Agregar mensaje inicial
        cargarClientes();

        // Color de fondo del panel
        panel.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));

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

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl3.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl6.setForeground(textColor);

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        // Inicializar JRadioButtons
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonTienda);
        buttonGroup.add(radioButtonDomicilio);
        buttonGroup.clearSelection();

        campoDescripcion.setForeground(textColor);
        campoDescripcion.setBackground(new Color(215, 215, 215));

        campoDireccion.setForeground(textColor);
        campoDireccion.setBackground(new Color(215, 215, 215));

        comboBoxCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Maneja la selección de cliente aquí
                cargarInformacionClienteSeleccionado();
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

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaPedidos listaPedidos = new ListaPedidos();
                listaPedidos.setVisible(true);
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
                    mensaje += "Nombre\n";
                }

                if (campoFechaPedido.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Precio\n";
                }

                if (!radioButtonDomicilio.isSelected() && !radioButtonTienda.isSelected()) {
                    validacion++;
                    mensaje += "La entrega\n";
                }

                if (datePicker.getJFormattedTextField().getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "La fecha\n";
                }

                String clienteText = comboBoxCliente.getSelectedItem().toString();
                if (clienteText.equals("Seleccione un cliente")) {
                    validacion++;
                    mensaje += "El cliente\n";
                }

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "La descripción\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String clientes = comboBoxCliente.getSelectedItem().toString();
                if (clientes.equals("Seleccione un cliente")) {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
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
                        "¿Desea guardar la información del pedido?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    guardarPedido();
                    ListaPedidos listaPedidos = new ListaPedidos();
                    listaPedidos.setVisible(true);
                    actual.dispose();
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
                btnNo.setBackground(darkColorRed);

                // Personaliza los fondos de los botones aquí
                btnYes.setForeground(Color.WHITE);
                btnNo.setForeground(Color.WHITE);

                // Elimina el foco
                btnYes.setFocusPainted(false);
                btnNo.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Estás seguro de que deseas limpiar los datos del pedido?",
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
                        datePicker.getJFormattedTextField().setText("");
                        comboBoxCliente.setSelectedIndex(0);
                        buttonGroup.clearSelection();
                        campoTelefono.setText("");
                        campoDescripcion.setText("");
                        campoDireccion.setText("");
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
    }

    // Método para cargar los clientes en el JComboBox
    private void cargarClientes() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nombre, apellido FROM clientes");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idCliente = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String clienteText = idCliente + " - " + nombre + " " + apellido;
                comboBoxCliente.addItem(clienteText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public void generarCamposAutomaticamente() {
        // Generar campoCodigo
        String codigo = generarCodigo();
        campoCodigo.setText(codigo);

        // Generar campoFecha
        String fecha = obtenerFechaActual();
        campoFechaPedido.setText(fecha);
    }

    private String generarCodigo() {
        // Obtener fecha actual
        java.util.Date fechaActual = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fecha = dateFormat.format(fechaActual);

        // Obtener hora actual en formato "a.m." o "p.m."
        SimpleDateFormat hourFormat = new SimpleDateFormat("hhmmss");
        String hora = hourFormat.format(fechaActual).toUpperCase();

        // Generar número aleatorio de 5 dígitos
        Random random = new Random();
        int numeroAleatorio = random.nextInt(100000);

        // Formatear el código final
        String codigo = "PD" + fecha + "-" + hora + "-" + String.format("%05d", numeroAleatorio);
        return codigo;
    }

    private String obtenerFechaActual() {
        // Obtener fecha actual
        java.util.Date fechaActual = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
        String fecha = dateFormat.format(fechaActual);

        // Capitalizar primera letra del día de la semana
        fecha = fecha.substring(0, 1).toUpperCase() + fecha.substring(1);

        return fecha;
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

    // Método para guardar la información del pedido en la base de datos
    private void guardarPedido() {
        String fechaCampo = campoFechaPedido.getText();
        String fechaPedido = convertirFecha(fechaCampo);

        String codigoPedido = campoCodigo.getText().trim();

        // OBTENER LA FECHA SELECCIONADA EN EL FORMATO "yyyy-MM-dd"
        java.util.Date fechaActual = (java.util.Date) datePicker.getModel().getValue(); // Obtiene la fecha del selector

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEntrega = dateFormat.format(fechaActual); // Formatea la fecha como una cadena "yyyy-MM-dd"


        String descripcion = campoDescripcion.getText().trim();
        String entrega = radioButtonDomicilio.isSelected() ? "Domicilio" : "Tienda";
        String clienteText = comboBoxCliente.getSelectedItem().toString();
        int clienteId = Integer.parseInt(clienteText.split(" - ")[0]);

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO pedidos (codigo_pedido, fecha_pedido, fecha_entrega, descripcion, cliente_id, entrega) VALUES (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setString(1, codigoPedido);
            preparedStatement.setString(2, fechaPedido);
            preparedStatement.setString(3, fechaEntrega);
            preparedStatement.setString(4, descripcion);
            preparedStatement.setInt(5, clienteId);
            preparedStatement.setString(6, entrega);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Pedido guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el pedido", "Error", JOptionPane.ERROR_MESSAGE);
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


    public boolean isDateOutOfRange(java.util.Date selectedDate, Calendar tomorrow) {
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);

        // Compara con el día siguiente al actual
        return selectedCal.before(tomorrow);
    }


    public void setFormattedDate(java.util.Date selectedDate) {
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
                if (value instanceof java.util.Date) {
                    return dateFormatter.format((java.util.Date) value);
                } else if (value instanceof Calendar) {
                    return dateFormatter.format(((Calendar) value).getTime());
                }
            }
            return "";
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CrearPedido frame = new CrearPedido();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}