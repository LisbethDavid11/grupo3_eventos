package Ventas;
import Objetos.Conexion;
import Objetos.VentaDetalle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VerVentas extends JFrame {
    private JPanel panel1;
    private JTextField codigo_venta,fecha, cliente, usuario;
    private JTable productos;
    private JButton cancelarButton;
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl13;
    private JScrollPane panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JButton imprimirButton;
    private static Conexion sql;
    private Connection mysql;
    private int id;
    private VerVentas actual = this;
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 20);
    Font font = new Font("Century Gothic", Font.BOLD, 15);
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
    private int panelImgWidth = 200;
    private int panelImgHeight = 200;

    private int panelImgWidth2 = 300;
    private int panelImgHeight2 = 200;
    public VerVentas(int id) {
        super("");
        setSize(850, 610);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        this.id = id;
        mostrar();

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        codigo_venta.setBorder(BorderFactory.createEmptyBorder());
        codigo_venta.setBackground(Color.decode("#F5F5F5"));
        codigo_venta.setForeground(textColor);
        codigo_venta.setEditable(false);
        codigo_venta.setFocusable(false);

        fecha.setBorder(BorderFactory.createEmptyBorder());
        fecha.setBackground(Color.decode("#F5F5F5"));
        fecha.setForeground(textColor);
        fecha.setEditable(false);
        fecha.setFocusable(false);

        cliente.setBorder(BorderFactory.createEmptyBorder());
        cliente.setBackground(Color.decode("#F5F5F5"));
        cliente.setForeground(textColor);
        cliente.setEditable(false);
        cliente.setFocusable(false);

        usuario.setBorder(BorderFactory.createEmptyBorder());
        usuario.setBackground(Color.decode("#F5F5F5"));
        usuario.setForeground(textColor);
        usuario.setEditable(false);
        usuario.setFocusable(false);

        imprimirButton.setForeground(Color.WHITE);
        imprimirButton.setBackground(darkColorVerdeLima);
        imprimirButton.setFocusPainted(false);
        imprimirButton.setBorder(margin);

        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setBackground(Color.decode("#263238"));
        cancelarButton.setFocusPainted(false);
        cancelarButton.setBorder(margin);

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        lbl1.setForeground(textColor2);
        lbl2.setForeground(textColor2);
        lbl3.setForeground(textColor2);
        lbl4.setForeground(textColor2);
        lbl5.setForeground(textColor2);
        lbl6.setForeground(textColor2);
        lbl7.setForeground(textColor2);
        lbl13.setForeground(textColor2);

        lbl1.setFont(font2);
        lbl2.setFont(font2);
        lbl3.setFont(font2);
        lbl4.setFont(font2);
        lbl5.setFont(font2);
        lbl6.setFont(font2);
        lbl7.setFont(font2);
        lbl13.setFont(font2);

        codigo_venta.setFont(font);
        fecha.setFont(font);
        cliente.setFont(font);
        usuario.setFont(font);

        lbl8.setFont(font);
        lbl9.setFont(font);
        lbl10.setFont(font);

        JTableHeader header = productos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(darkColorCyan);

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaVentas listaVentas = new ListaVentas();
                listaVentas.setVisible(true);
                actual.dispose();
            }
        });

        imprimirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoVenta = codigo_venta.getText();
                ListaVentas.imprimirFactura(codigoVenta);
            }
        });

        configurarTablaMateriales();
    }

    private void configurarTablaMateriales() {
        int columnCount = productos.getColumnCount();
        if (columnCount > 0) {
            TableColumnModel columnModel = productos.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(20);
            columnModel.getColumn(1).setPreferredWidth(220);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(100);
            columnModel.getColumn(4).setPreferredWidth(100);

            columnModel.getColumn(0).setCellRenderer(new VerVentas.CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new VerVentas.LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new VerVentas.LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new VerVentas.LeftAlignedRenderer());
            columnModel.getColumn(4).setCellRenderer(new VerVentas.LeftAlignedRenderer());
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

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        double suma = 0;

        try {
            PreparedStatement statement = mysql.prepareStatement(
                    "SELECT v.*, CONCAT(c.nombre, ' ', c.apellido) AS nombre_cliente, CONCAT(e.nombre) AS nombre_usuario " +
                            "FROM ventas v " +
                            "LEFT JOIN clientes c ON v.cliente_id = c.id " +
                            "LEFT JOIN usuarios e ON v.usuario_id = e.id " +
                            "WHERE v.id = ?;"
            );
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Llenar los campos de la interfaz con los datos de la venta
                codigo_venta.setText(resultSet.getString("codigo_venta"));
                cliente.setText(resultSet.getString("nombre_cliente"));
                usuario.setText(resultSet.getString("nombre_usuario"));
                String fechaBD = resultSet.getString("fecha");
                Date fechaDate = null;
                SimpleDateFormat formatoSalida = new SimpleDateFormat("EEEE d 'de' MMMM, yyyy", new Locale("es", "ES"));
                String[] formatosPosibles = {"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"};

                for (String formato : formatosPosibles) {
                    try {
                        fechaDate = new SimpleDateFormat(formato).parse(fechaBD);
                        break; // Sale del bucle si el parseo es exitoso
                    } catch (ParseException e) {
                        // Intenta con el siguiente formato si hay una excepción
                    }
                }

                if (fechaDate != null) {
                    String fechaFormateada = formatoSalida.format(fechaDate);

                    // Asegurar que la primera letra sea mayúscula
                    fechaFormateada = Character.toUpperCase(fechaFormateada.charAt(0)) + fechaFormateada.substring(1);
                    fecha.setText(fechaFormateada);
                } else {
                    System.out.println("Formato de fecha no reconocido.");
                    // Manejar la situación cuando ninguno de los formatos funciona
                }

                DefaultTableModel modeloProductos = new DefaultTableModel();
                modeloProductos.addColumn("N°");
                modeloProductos.addColumn("Producto");
                modeloProductos.addColumn("Cantidad");
                modeloProductos.addColumn("Precio");
                modeloProductos.addColumn("Total");

                PreparedStatement detallesStatement = mysql.prepareStatement(
                        "SELECT dv.cantidad, " +
                                "CASE dv.tipo_detalle " +
                                "   WHEN 'material' THEN m.nombre " +
                                "   WHEN 'tarjeta' THEN t.ocasion " +
                                "   WHEN 'floristeria' THEN f.nombre " +
                                "   WHEN 'manualidad' THEN ma.nombre " +
                                "   WHEN 'arreglo' THEN a.nombre " +
                                "   WHEN 'desayuno' THEN d.nombre " +
                                "END AS nombre_detalle, " +
                                "CASE dv.tipo_detalle " +
                                "   WHEN 'material' THEN m.precio " +
                                "   WHEN 'tarjeta' THEN t.precio_tarjeta " +
                                "   WHEN 'floristeria' THEN f.precio " +
                                "   WHEN 'manualidad' THEN ma.precio_manualidad " +
                                "   WHEN 'arreglo' THEN a.precio " +
                                "   WHEN 'desayuno' THEN d.precio_desayuno " +
                                "END AS precio_detalle " +
                                "FROM detalles_ventas dv " +
                                "LEFT JOIN materiales m ON dv.detalle_id = m.id AND dv.tipo_detalle = 'material' " +
                                "LEFT JOIN tarjetas t ON dv.detalle_id = t.id AND dv.tipo_detalle = 'tarjeta' " +
                                "LEFT JOIN floristeria f ON dv.detalle_id = f.id AND dv.tipo_detalle = 'floristeria' " +
                                "LEFT JOIN manualidades ma ON dv.detalle_id = ma.id AND dv.tipo_detalle = 'manualidad' " +
                                "LEFT JOIN arreglos a ON dv.detalle_id = a.id AND dv.tipo_detalle = 'arreglo' " +
                                "LEFT JOIN desayunos d ON dv.detalle_id = d.id AND dv.tipo_detalle = 'desayuno' " +
                                "WHERE dv.venta_id = ?;"
                );
                detallesStatement.setInt(1, this.id);
                ResultSet detallesResultSet = detallesStatement.executeQuery();

                int numeroDetalle = 1;
                while (detallesResultSet.next()) {
                    String nombreDetalle = detallesResultSet.getString("nombre_detalle");
                    int cantidad = detallesResultSet.getInt("cantidad");
                    double precioDetalle = detallesResultSet.getDouble("precio_detalle");
                    double subtotal = cantidad * precioDetalle; // Aplicar el factor 0.85 según tu lógica
                    suma += subtotal;

                    Object[] fila = {numeroDetalle, nombreDetalle, cantidad + " unidades", "L. " + decimalFormat.format(precioDetalle), "L. " + decimalFormat.format(subtotal)};
                    modeloProductos.addRow(fila);

                    numeroDetalle++;
                }

                // Llenar la tabla de productos con el modelo
                productos.setModel(modeloProductos);

                // Calcular los valores totales
                double subtotal = suma * 0.85; // Aplicar el factor 0.85 según tu lógica
                double isv = suma * 0.15; // Aplicar el factor 0.15 según tu lógica
                double total = subtotal + isv;

                // Actualizar etiquetas con los resultados de los cálculos
                String formattedSuma = "L. " + decimalFormat.format(subtotal);
                lbl8.setText(formattedSuma);

                String formattedISV = "L. " + decimalFormat.format(isv);
                lbl9.setText(formattedISV);

                String formattedTotal = "L. " + decimalFormat.format(total);
                lbl10.setText(formattedTotal);

            } else {
                JOptionPane.showMessageDialog(null, "La venta con el ID " + this.id + " no fue encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerVentas verVentas = new VerVentas(1);
            verVentas.setVisible(true);
        });
    }
}
