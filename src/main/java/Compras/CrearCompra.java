package Compras;
import Clientes.ListaCliente;
import Objetos.Conexion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
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

public class CrearCompra extends JFrame {
    private JPanel panel1, panel2;
    private JTable tablaProductos;
    private DefaultTableModel modeloProductos;

    public JButton guardarButton, cancelarButton, limpiarButton, agregarButton;
    public JTextField campoCodigo, campoFecha, campoCantidad, campoPrecio;
    public JComboBox<String> boxProveedor, boxEmpleado, boxMaterial;
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11;
    private JCheckBox siCheckBox;
    private double sumaISVExento = 0.0;
    private JLabel lbl12;
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
        super("");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();
        mysql = sql.conectamysql();

        boxProveedor.addItem("Seleccione un proveedor");
        cargarProveedores();

        boxEmpleado.addItem("Seleccione un empleado");
        cargarEmpleados();

        boxMaterial.addItem("Seleccione un producto");
        cargarMateriales();

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        siCheckBox.setBackground(Color.decode("#F5F5F5"));
        siCheckBox.setFocusPainted(false);

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
        lbl11.setForeground(textColor);
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

        tablaProductos.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tablaProductos.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
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

                agregarProducto();
            }
        });

        siCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (siCheckBox.isSelected()) {
                    campoPrecio.setEnabled(true);
                } else {
                    campoPrecio.setEnabled(true);
                }
            }
        });

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
                String materialText = nombre;
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
        int confirmacionGuardar = JOptionPane.showConfirmDialog(null, "¿Desea guardar la compra?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacionGuardar != JOptionPane.YES_OPTION) {
            return; // El usuario ha cancelado la operación
        }

        String codigoCompra = campoCodigo.getText();
        Date fechaActual = new Date();
        String fechaCompra = new SimpleDateFormat("yyyy-MM-dd").format(fechaActual);
        int proveedorId = Integer.parseInt(boxProveedor.getSelectedItem().toString().split(" - ")[0]);
        int empleadoId = Integer.parseInt(boxEmpleado.getSelectedItem().toString().split(" - ")[0]);
        try (PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO compras (codigo_compra, fecha, proveedor_id, empleado_id) VALUES (?, ?, ?, ?)")) {
            preparedStatement.setString(1, codigoCompra);
            preparedStatement.setString(2, fechaCompra);
            preparedStatement.setInt(3, proveedorId);
            preparedStatement.setInt(4, empleadoId);
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
            try (PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO detalles_compras (compra_id, material_id, cantidad, precio, exento) VALUES (?, ?, ?, ?, ?)")) {
                preparedStatement.setInt(1, compraId);
                preparedStatement.setInt(2, materialId);
                preparedStatement.setInt(3, cantidad);
                preparedStatement.setDouble(4, precio);
                preparedStatement.setBoolean(5, exento);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        boolean listaCompraAbierta = false;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof ListaCliente) {
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
                }
                double total = Double.parseDouble(modeloProductos.getValueAt(i, 5).toString().replace(",", "."));
                sumaSubtotal += subtotal;
                sumaTotal += total;
            } catch (NumberFormatException e) {
                // Handle the case when the string cannot be parsed as a double
                // You can choose to display an error message or take any other appropriate action
                System.err.println("Invalid number format encountered. Skipping calculation for row " + i);
            }
        }
        double isvExento = sumaISVExento * 0.15;  // Calcular el 15% del total de productos exentos
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
            if (!listaCompraAbierta) {
                ListaCompras compras = new ListaCompras();
                compras.setVisible(true);
            }
            crearCompra.dispose();
        }
    }

    private void limpiar() {
        campoCodigo.setText("");
        boxProveedor.setSelectedIndex(0);
        boxEmpleado.setSelectedIndex(0);
        boxMaterial.setSelectedIndex(0);
        campoCantidad.setText("");
        campoPrecio.setText("");
        modeloProductos.setRowCount(0);
        lbl8.setText("0.00");
        lbl9.setText("0.00");
        lbl10.setText("0.00");
        lbl12.setText("0.00");
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

    private void agregarProducto() {

        String producto = boxMaterial.getSelectedItem().toString();

        if (existeProductoEnTabla(producto)) {
            mostrarMensajeError("El producto ya ha sido agregado.");
            return;
        }

        int cantidad = Integer.parseInt(campoCantidad.getText());
        double precio;
        String isv;
        String total;
        if (siCheckBox.isSelected()) {
            precio = Double.parseDouble(campoPrecio.getText());
            double subtotal = cantidad * precio;
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            isv = "Exento";
            total = decimalFormat.format(subtotal);
            sumaISVExento += subtotal;  // Sumar al total de productos exentos
        } else {
            precio = Double.parseDouble(campoPrecio.getText());
            double subtotal = cantidad * precio;
            double impuesto = subtotal * 0.15;
            double totalProducto = subtotal + impuesto;
            DecimalFormat decimalFormat = new DecimalFormat("#.00");
            isv = decimalFormat.format(impuesto);
            total = decimalFormat.format(totalProducto);
        }
        Object[] fila = { producto, cantidad, precio, cantidad * precio, isv, total, crearBotonEliminar() };
        modeloProductos.addRow(fila);
        campoCantidad.setText("");
        campoPrecio.setText("");
        actualizarTotales();
    }

    private int validarCampos() {
        int contador = 0;
        for (JTextField campo : campos) {
            if (campo.getText().isEmpty()) {
                campo.setBorder(BorderFactory.createLineBorder(Color.RED));
                contador++;
            } else {
                campo.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
            }
        }
        if (boxProveedor.getSelectedIndex() == 0) {
            boxProveedor.setBorder(BorderFactory.createLineBorder(Color.RED));
            contador++;
        } else {
            boxProveedor.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("ComboBox.border"));
        }
        if (boxEmpleado.getSelectedIndex() == 0) {
            boxEmpleado.setBorder(BorderFactory.createLineBorder(Color.RED));
            contador++;
        } else {
            boxEmpleado.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("ComboBox.border"));
        }
        return contador;
    }

    private int validarCamposProducto() {
        int contador = 0;
        if (boxMaterial.getSelectedIndex() == 0) {
            boxMaterial.setBorder(BorderFactory.createLineBorder(Color.RED));
            contador++;
        } else {
            boxMaterial.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("ComboBox.border"));
        }
        if (campoCantidad.getText().isEmpty()) {
            campoCantidad.setBorder(BorderFactory.createLineBorder(Color.RED));
            contador++;
        } else {
            campoCantidad.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        }
        if (!siCheckBox.isSelected() && campoPrecio.getText().isEmpty()) {
            campoPrecio.setBorder(BorderFactory.createLineBorder(Color.RED));
            contador++;
        } else {
            campoPrecio.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        }
        return contador;
    }

    private void mostrarMensajeError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
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
            return button;
        }

        public Object getCellEditorValue() {
            return "X";
        }

        public void actionPerformed(ActionEvent e) {
            JButton boton = (JButton) e.getSource();
            int filaSeleccionada = tablaProductos.convertRowIndexToModel(tablaProductos.getEditingRow());
            modeloProductos.removeRow(filaSeleccionada);
            actualizarTotales();
        }
    }

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
