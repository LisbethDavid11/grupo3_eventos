/**
 * VerPedidos.java
 *
 * Ver Pedidos
 *
 * @author Lisbeth David
 * @version 1.0
 * @since 2024-05-05
 */

package Pedidos;

import Objetos.Conexion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
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

public class VerPedidos extends JFrame {
    // Paneles
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panel6;

    // Campos de texto
    private JTextField campoCodigo;
    private JTextField campoFechaPedido;
    private JTextField campoEntrega;
    private JTextField campoFechaEntrega;
    private JTextField campoCliente;
    private JTextField campoPrecio;

    // Tabla
    private JTable productos;

    // Botón
    private JButton cancelarButton;

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
    private JLabel lbl13;
    private JLabel lbl14;
    private JLabel lblImagen;

    // ScrollPane
    private JScrollPane panel2;

    // Área de texto
    private JTextArea campoDescripcion;

    // Conexión a la base de datos
    private Conexion sql;
    private Connection mysql;

    // ID
    private int id;

    // Referencia a la clase actual
    private VerPedidos actual = this;

    // Fuentes
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
    private int panelImgWidth2 = 200;
    private int panelImgHeight2 = 200;

    public VerPedidos(int id) {
        super("");
        setSize(850, 610);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;

        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        campoCodigo.setBorder(BorderFactory.createEmptyBorder());
        campoCodigo.setBackground(Color.decode("#F5F5F5"));
        campoCodigo.setForeground(textColor);
        campoCodigo.setEditable(false);
        campoCodigo.setFocusable(false);

        JTableHeader header = productos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(darkColorCyan);

        campoFechaEntrega.setBorder(BorderFactory.createEmptyBorder());
        campoFechaEntrega.setBackground(Color.decode("#F5F5F5"));
        campoFechaEntrega.setForeground(textColor);
        campoFechaEntrega.setEditable(false);
        campoFechaEntrega.setFocusable(false);

        campoDescripcion.setBorder(BorderFactory.createEmptyBorder());
        campoDescripcion.setBackground(Color.decode("#F5F5F5"));
        campoDescripcion.setForeground(textColor);
        campoDescripcion.setEditable(false);
        campoDescripcion.setFocusable(false);

        campoFechaPedido.setBorder(BorderFactory.createEmptyBorder());
        campoFechaPedido.setBackground(Color.decode("#F5F5F5"));
        campoFechaPedido.setForeground(textColor);
        campoFechaPedido.setEditable(false);
        campoFechaPedido.setFocusable(false);

        campoCliente.setBorder(BorderFactory.createEmptyBorder());
        campoCliente.setBackground(Color.decode("#F5F5F5"));
        campoCliente.setForeground(textColor);
        campoCliente.setEditable(false);
        campoCliente.setFocusable(false);

        campoEntrega.setBorder(BorderFactory.createEmptyBorder());
        campoEntrega.setBackground(Color.decode("#F5F5F5"));
        campoEntrega.setForeground(textColor);
        campoEntrega.setEditable(false);
        campoEntrega.setFocusable(false);

        campoPrecio.setBorder(BorderFactory.createEmptyBorder());
        campoPrecio.setBackground(Color.decode("#F5F5F5"));
        campoPrecio.setForeground(textColor);
        campoPrecio.setEditable(false);
        campoPrecio.setFocusable(false);

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
        lbl11.setForeground(textColor2);
        lbl12.setForeground(textColor2);
        lbl13.setForeground(textColor2);
        lbl14.setForeground(textColor2);

        lbl1.setFont(font2);
        lbl2.setFont(font2);
        lbl3.setFont(font2);
        lbl4.setFont(font2);
        lbl5.setFont(font2);
        lbl6.setFont(font2);
        lbl7.setFont(font2);
        lbl11.setFont(font2);
        lbl12.setFont(font2);
        lbl13.setFont(font2);
        lbl14.setFont(font2);

        campoCodigo.setFont(font);
        campoFechaEntrega.setFont(font);
        campoFechaPedido.setFont(font);
        campoDescripcion.setFont(font);
        campoCliente.setFont(font);
        campoEntrega.setFont(font);
        campoPrecio.setFont(font);
        lbl8.setFont(font);
        lbl9.setFont(font);
        lbl10.setFont(font);

        cancelarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cancelarButton.setBackground(textColor2);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                cancelarButton.setBackground(textColor);
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaPedidos listaPedidos = new ListaPedidos();
                listaPedidos.setVisible(true);
                actual.dispose();
            }
        });

        mostrar();
        configurarTablaMateriales();
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        double suma = 0;

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT pedidos.*, clientes.nombre, clientes.apellido FROM pedidos JOIN clientes ON pedidos.cliente_id = clientes.id WHERE pedidos.id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Crea un formato personalizado para mostrar las fechas
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM, yyyy");

                // Obtén las fechas del ResultSet
                String fechaPedidoString = resultSet.getString("fecha_pedido");
                String fechaEntregaString = resultSet.getString("fecha_entrega");

                // Convierte las cadenas de fecha en objetos Date
                SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd");

                Date fechaPedido = formatoOriginal.parse(fechaPedidoString);
                Date fechaEntrega = formatoOriginal.parse(fechaEntregaString);

                // Formatea las fechas en el estilo deseado
                String fechaPedidoFormateada = formatoFecha.format(fechaPedido);
                String fechaEntregaFormateada = formatoFecha.format(fechaEntrega);

                // Establece los textos en tus campos de texto
                campoFechaPedido.setText(fechaPedidoFormateada);
                campoFechaEntrega.setText(fechaEntregaFormateada);
                campoCodigo.setText(resultSet.getString("codigo_pedido"));

                campoEntrega.setText(resultSet.getString("entrega"));

                double precioEnvio = resultSet.getDouble("precio_envio");
                String formattedPrecio;

                if (precioEnvio <= 0) {
                    formattedPrecio = "L. 0.00";
                } else {
                    formattedPrecio = "L. " + decimalFormat.format(precioEnvio);
                }

                campoPrecio.setText(formattedPrecio + "  ");

                campoDescripcion.setText(resultSet.getString("descripcion"));
                campoCliente.setText(resultSet.getString("nombre") + " " + resultSet.getString("apellido"));

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
                                "FROM detalles_pedidos dv " +
                                "LEFT JOIN materiales m ON dv.detalle_id = m.id AND dv.tipo_detalle = 'material' " +
                                "LEFT JOIN tarjetas t ON dv.detalle_id = t.id AND dv.tipo_detalle = 'tarjeta' " +
                                "LEFT JOIN floristeria f ON dv.detalle_id = f.id AND dv.tipo_detalle = 'floristeria' " +
                                "LEFT JOIN manualidades ma ON dv.detalle_id = ma.id AND dv.tipo_detalle = 'manualidad' " +
                                "LEFT JOIN arreglos a ON dv.detalle_id = a.id AND dv.tipo_detalle = 'arreglo' " +
                                "LEFT JOIN desayunos d ON dv.detalle_id = d.id AND dv.tipo_detalle = 'desayuno' " +
                                "WHERE dv.pedido_id = ?;"
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

                // Formatear el número "suma" y establecerlo en lbl8
                String formattedLbl8 = "L. " + decimalFormat.format(suma);
                lbl8.setText(formattedLbl8 + "  ");

                // Obtener el valor de "precio_envio" del resultado
                String precioEnvioString = resultSet.getString("precio_envio");
                double precioEnvioValue = 0.0; // Valor por defecto

                if (precioEnvioString != null) {
                    precioEnvioString = precioEnvioString.replace(",", ""); // Eliminar las comas del número
                    precioEnvioValue = Double.parseDouble(precioEnvioString);
                }

                String formattedLbl9 = "L. " + decimalFormat.format(precioEnvioValue);
                lbl9.setText(formattedLbl9 + "  ");

                // Calcular la suma de "suma" y "precio_envioValue", formatearla y establecerla en lbl10
                double sumaLbl8Lbl9 = suma + precioEnvioValue;
                String sumaFormateada = "L. " + decimalFormat.format(sumaLbl8Lbl9);

                lbl10.setText(sumaFormateada + "  ");

            } else {
                JOptionPane.showMessageDialog(null, "El pedido con el ID " + this.id + " no fue encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
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

            columnModel.getColumn(0).setCellRenderer(new VerPedidos.CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new VerPedidos.LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new VerPedidos.LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new VerPedidos.LeftAlignedRenderer());
            columnModel.getColumn(4).setCellRenderer(new VerPedidos.LeftAlignedRenderer());
        }
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerPedidos verPedidos = new VerPedidos(1);
            verPedidos.setVisible(true);
        });
    }
}
