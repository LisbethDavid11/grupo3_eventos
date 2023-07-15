package Compras;

import Clientes.ListaCliente;
import Objetos.Conexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class CrearCompra extends JFrame {
    private JPanel panel1;
    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;

    public JButton guardarButton, cancelarButton, limpiarButton, agregarButton;
    public JTextField campoCodigo, campoFecha, campoCantidad, campoPrecio;
    public JComboBox<String> boxProveedor, boxEmpleado, boxMaterial;
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5,lbl6, lbl7,lbl8,lbl9,lbl10;
    private Conexion sql;
    private Connection mysql;
    public CrearCompra crearCompra = this;
    Color textColor = Color.decode("#212121");
    Color darkColorCyan = new Color(0, 150, 136);
    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
    Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
    private JTextField[] campos = { campoCodigo, campoFecha, campoCantidad, campoPrecio };
    public CrearCompra() {
        super("Crear Empleados");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();
        mysql = sql.conectamysql();

        boxProveedor.addItem("Seleccione un proveedor"); // Agregar mensaje inicial
        cargarProveedores();

        boxEmpleado.addItem("Seleccione un empleado"); // Agregar mensaje inicial
        cargarEmpleados();

        boxMaterial.addItem("Seleccione un producto"); // Agregar mensaje inicial
        cargarMateriales();

        panel1.setBackground(Color.decode("#F5F5F5"));

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

        agregarButton.setForeground(Color.WHITE);
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

        // Crear el modelo de datos para la lista de productos
        modeloProductos = new DefaultTableModel();
        modeloProductos.addColumn("Nombre");
        modeloProductos.addColumn("Cantidad");
        modeloProductos.addColumn("Precio");
        modeloProductos.addColumn("Total");
        modeloProductos.addColumn("Eliminar"); // Nueva columna para el botón de eliminación

        // Asignar el modelo de datos a la JTable
        tablaProductos.setModel(modeloProductos);

        // Asignar el renderizador de botones a la columna correspondiente
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());




        campoPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoPrecio.getText();
                int caretPosition = campoPrecio.getCaretPosition();

                // Permitir solo dígitos y el carácter punto
                if (!(Character.isDigit(c) || c == '.')) {
                    e.consume(); // Ignorar cualquier otro carácter
                    return;
                }

                // Verificar si el carácter es un punto
                if (c == '.') {
                    // Verificar si ya hay un punto en el texto o si es el primer carácter
                    if (text.contains(".") || caretPosition == 0) {
                        e.consume(); // Ignorar el punto adicional
                        return;
                    }
                } else {
                    // Verificar si se excede el límite de caracteres después del punto
                    int dotIndex = text.indexOf('.');
                    if (dotIndex != -1 && caretPosition > dotIndex + 2) {
                        e.consume(); // Ignorar el carácter si se excede el límite de decimales
                        return;
                    }
                }

                // Verificar la longitud del texto antes y después del punto
                String[] parts = text.split("\\.");
                int integerLength = parts[0].length();
                int decimalLength = parts.length > 1 ? parts[1].length() : 0;

                // Verificar si se excede el límite de dígitos antes o después del punto
                if (integerLength >= 6 || decimalLength >= 2) {
                    e.consume(); // Ignorar el carácter si se excede el límite de dígitos
                    return;
                }
            }
        });

        campoCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoCantidad.getText();

                // Permitir solo dígitos
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignorar cualquier otro carácter
                    return;
                }

                // Verificar la longitud del texto
                String newText = text + c;
                int value = 0;
                try {
                    value = Integer.parseInt(newText);
                } catch (NumberFormatException ex) {
                    e.consume(); // Ignorar si no se puede convertir a entero
                    return;
                }

                // Verificar el rango de valores
                if (value < 1 || value > 9999) {
                    e.consume(); // Ignorar si está fuera del rango
                }
            }
        });


        // Dentro del método CrearCompra()
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());

// Agregar el ActionListener para el botón de eliminación
        tablaProductos.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));


        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea cancelar?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    boolean listaCompraAbierta = false;
                    Window[] windows = Window.getWindows();
                    for (Window window : windows) {
                        if (window instanceof ListaCliente) {
                            listaCompraAbierta = true;
                            break;
                        }
                    }

                    // Abrir la ventana ListaCliente solo si no está abierta
                    if (!listaCompraAbierta) {
                        ListaCompras compras = new ListaCompras();
                        compras.setVisible(true);
                    }

                    crearCompra.dispose();
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea limpiar?", "Confirmación", JOptionPane.YES_NO_OPTION);
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
                    mensaje += "La fecha\n";
                }

                String proveedorText = boxProveedor.getSelectedItem().toString();
                if (proveedorText.equals("Seleccione un proveedor")) {
                    validacion++;
                    mensaje += "El proveedor\n";
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

                // Verificar si el producto ya está agregado a la lista
                String producto = (String) boxMaterial.getSelectedItem();
                boolean productoExistente = false;
                for (int i = 0; i < modeloProductos.getRowCount(); i++) {
                    String productoEnLista = (String) modeloProductos.getValueAt(i, 0);
                    if (productoEnLista.equals(producto)) {
                        productoExistente = true;
                        break;
                    }
                }

                if (productoExistente) {
                    JOptionPane.showMessageDialog(null, "El producto ya está agregado a la lista.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Obtener los valores seleccionados y calculados
                int cantidad = Integer.parseInt(campoCantidad.getText());
                double precio = Double.parseDouble(campoPrecio.getText());
                double total = cantidad * precio;


                // Agregar la compra al modelo de datos de la lista de productos
                modeloProductos.addRow(new Object[]{producto, cantidad, precio, total, crearBotonEliminar()});


                // Limpiar los campos después de agregar la compra
                campoCantidad.setText("");
                campoPrecio.setText("");

                // Actualizar los totales
                actualizarTotales();
            }
        });


        // Inicializar campoCodigo
        String fechaActual = new SimpleDateFormat("ddMMyyyy").format(new Date());
        String horaActual = new SimpleDateFormat("HHmmss").format(new Date());
        String numeroVariable = generateRandomNumber(4); // Generar número aleatorio entre 0000 y 9999
        campoCodigo.setText("CP" + fechaActual + horaActual + numeroVariable);
        campoCodigo.setEditable(false);
        campoCodigo.setFocusable(false);

        // Inicializar campoFecha
        String fechaMostrar = new SimpleDateFormat("dd 'de' MMMM yyyy").format(new Date());
        campoFecha.setText(fechaMostrar);
        campoFecha.setEditable(false);
        campoFecha.setFocusable(false);
    }

    private class TablaModeloProductos extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Deshabilitar la edición de celdas en la tabla
        }
    }

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

    private void cargarMateriales() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nombre, cantidad_inventario FROM materiales");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String materialText = nombre ;
                boxMaterial.addItem(materialText);
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
                int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro que desea eliminar este producto?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    // Obtener el botón que disparó el evento
                    JButton boton = (JButton) e.getSource();

                    // Obtener la fila correspondiente al botón en el modelo de datos de la tabla
                    int filaSeleccionada = tablaProductos.convertRowIndexToModel(tablaProductos.getEditingRow());

                    // Eliminar la fila del modelo de datos de la lista de productos
                    modeloProductos.removeRow(filaSeleccionada);

                    // Actualizar los totales
                    actualizarTotales();
                }
            }
        });

        return botonEliminar;
    }




    private void guardarDatos() {
        // Obtener los valores de la compra y guardarlos en la base de datos
        String codigoCompra = campoCodigo.getText();

        // Obtener la fecha actual y formatearla
        Date fechaActual = new Date();
        String fechaCompra = new SimpleDateFormat("yyyy-MM-dd").format(fechaActual);

        // Suponiendo que tus JComboBox almacenan el ID en el primer carácter de la cadena seleccionada
        int proveedorId = Integer.parseInt(boxProveedor.getSelectedItem().toString().split(" - ")[0]);
        int empleadoId = Integer.parseInt(boxEmpleado.getSelectedItem().toString().split(" - ")[0]);

        // Insertar la compra en la tabla "compras"
        try (PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO compras (codigo_compra, fecha, proveedor_id, empleado_id) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, codigoCompra);
            preparedStatement.setString(2, fechaCompra);
            preparedStatement.setInt(3, proveedorId);
            preparedStatement.setInt(4, empleadoId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Obtener el ID de la última compra insertada
        int compraId = 0;
        try (PreparedStatement preparedStatement = mysql.prepareStatement("SELECT LAST_INSERT_ID()")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                compraId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Recorrer las filas del modelo de productos y guardar los detalles de la compra
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            String producto = (String) modeloProductos.getValueAt(i, 0);
            int cantidad = (int) modeloProductos.getValueAt(i, 1);
            double precio = (double) modeloProductos.getValueAt(i, 2);

            // Obtener el ID del material seleccionado
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

            // Insertar el detalle de la compra en la tabla "detalles_compras"
            try (PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO detalles_compras (compra_id, material_id, cantidad, precio) VALUES (?, ?, ?, ?)")) {
                preparedStatement.setInt(1, compraId);
                preparedStatement.setInt(2, materialId);
                preparedStatement.setInt(3, cantidad);
                preparedStatement.setDouble(4, precio);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Mostrar el JOptionPane y ejecutar la ventana ListaCompras solo una vez después de guardar todos los detalles de la compra
        boolean listaCompraAbierta = false;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof ListaCliente) {
                listaCompraAbierta = true;
                break;
            }
        }

        // Mostrar el JOptionPane solo si no está abierta la ventana ListaCompras
        if (!listaCompraAbierta) {
            // Mensaje personalizado
            System.out.println("Compra registrada exitosamente.");
            JOptionPane.showMessageDialog(null, "Compra registrada exitosamente.", "Éxito", JOptionPane.DEFAULT_OPTION);
        }

        crearCompra.dispose();

        // Abrir la ventana ListaCompras solo si no está abierta y el usuario hizo clic en el botón "OK" del JOptionPane
        if (!listaCompraAbierta) {
            ListaCompras compras = new ListaCompras();
            compras.setVisible(true);
        }
    }
    
    private void actualizarTotales() {
        double sumaTotal = 0.0;
        for (int i = 0; i < modeloProductos.getRowCount(); i++) {
            double total = (double) modeloProductos.getValueAt(i, 3);
            sumaTotal += total;
        }

        double subtotal = sumaTotal;
        double impuesto = subtotal * 0.15;
        double totalFinal = subtotal * 1.15;

        // Aplicar formato de dos decimales
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String sumaTotalFormatted = decimalFormat.format(sumaTotal);
        String impuestoFormatted = decimalFormat.format(impuesto);
        String totalFinalFormatted = decimalFormat.format(totalFinal);

        lbl8.setText(" " + sumaTotalFormatted);
        lbl9.setText(" " + impuestoFormatted);
        lbl10.setText(" " + totalFinalFormatted);
    }

    private String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // Generar dígito aleatorio entre 0 y 9
            sb.append(digit);
        }
        return sb.toString();
    }

    private void cancelar() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cancelar?", "Confirmar cancelación", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            boolean listaCompraAbierta = false;
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                if (window instanceof ListaCliente) {
                    listaCompraAbierta = true;
                    break;
                }
            }

            // Abrir la ventana ListaCliente solo si no está abierta
            if (!listaCompraAbierta) {
                ListaCompras compras = new ListaCompras();
                compras.setVisible(true);
            }

            crearCompra.dispose();
        }
    }

    private void limpiar() {
        // Limpiar los campos
        campoPrecio.setText("");
        campoCantidad.setText("");

        // Reestablecer los JComboBox
        boxProveedor.setSelectedIndex(0);
        boxEmpleado.setSelectedIndex(0);
        boxMaterial.setSelectedIndex(0);

        // Limpiar la tabla
        modeloProductos.setRowCount(0);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setForeground(Color.WHITE); // Establecer color de texto
            setBackground(darkColorPink); // Establecer color de fondo
            setFocusPainted(false); // Desactivar efecto de enfoque
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("X");
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;

        public ButtonEditor(JCheckBox checkBox) {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(this);

            // Personalización del botón
            button.setForeground(Color.WHITE);
            button.setBackground(darkColorPink);
            button.setFocusPainted(false);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("X");
            return button;
        }

        public Object getCellEditorValue() {
            return null;
        }

        public void actionPerformed(ActionEvent e) {
            // Obtener el botón que disparó el evento
            JButton boton = (JButton) e.getSource();

            // Obtener la fila correspondiente al botón en el modelo de datos de la tabla
            int filaSeleccionada = tablaProductos.convertRowIndexToModel(tablaProductos.getEditingRow());

            // Eliminar la fila del modelo de datos de la lista de productos
            modeloProductos.removeRow(filaSeleccionada);

            // Actualizar los totales
            actualizarTotales();
        }
    }
}


