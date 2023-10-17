package Promociones;

import Modelos.PoliModeloProducto;
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
import java.util.ArrayList;
import java.util.Date;

public class VerPromociones extends JFrame {
    private JPanel panel1, panel3, panel4, panel5, panel6;
    private JTextField campoFechaInicio, campoFechaFin;
    private JTable productos;
    private JButton cancelarButton, eliminarButton;
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10;
    private JScrollPane panel2;
    private JTextArea campoDescripcion;
    private Conexion sql;
    private Connection mysql;
    private int id;
    private VerPromociones actual = this;
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

    public VerPromociones(int id) {
        super("");
        setSize(850, 610);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;

        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");

        JTableHeader header = productos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(darkColorCyan);

        campoFechaInicio.setBorder(BorderFactory.createEmptyBorder());
        campoFechaInicio.setBackground(Color.decode("#F5F5F5"));
        campoFechaInicio.setForeground(textColor);
        campoFechaInicio.setEditable(false);
        campoFechaInicio.setFocusable(false);

        campoFechaFin.setBorder(BorderFactory.createEmptyBorder());
        campoFechaFin.setBackground(Color.decode("#F5F5F5"));
        campoFechaFin.setForeground(textColor);
        campoFechaFin.setEditable(false);
        campoFechaFin.setFocusable(false);

        campoDescripcion.setBorder(BorderFactory.createEmptyBorder());
        campoDescripcion.setBackground(Color.decode("#F5F5F5"));
        campoDescripcion.setForeground(textColor);
        campoDescripcion.setEditable(false);
        campoDescripcion.setFocusable(false);

        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setBackground(Color.decode("#263238"));
        cancelarButton.setFocusPainted(false);
        cancelarButton.setBorder(margin);

        eliminarButton.setForeground(Color.WHITE);
        eliminarButton.setBackground(darkColorRed);
        eliminarButton.setFocusPainted(false);
        eliminarButton.setBorder(margin);

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        lbl1.setForeground(textColor2);
        lbl2.setForeground(textColor2);
        lbl3.setForeground(textColor2);
        lbl4.setForeground(textColor2);
        lbl5.setForeground(textColor2);
        lbl6.setForeground(textColor2);
        lbl7.setForeground(textColor2);

        lbl1.setFont(font2);
        lbl2.setFont(font2);
        lbl3.setFont(font2);
        lbl4.setFont(font2);
        lbl5.setFont(font2);
        lbl6.setFont(font2);
        lbl7.setFont(font2);

        campoFechaFin.setFont(font);
        campoFechaInicio.setFont(font);
        campoDescripcion.setFont(font);

        lbl8.setFont(font);
        lbl9.setFont(font);
        lbl10.setFont(font);

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaPromociones listaPromociones = new ListaPromociones();
                listaPromociones.setVisible(true);
                actual.dispose();
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
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
                        "¿Estás seguro de que deseas eliminar la actual promoción?",
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
                        eliminarPromocion();
                        ListaPromociones listaPromociones = new ListaPromociones();
                        listaPromociones.setVisible(true);
                        actual.dispose();
                        dialog.dispose();
                        JOptionPane.showMessageDialog(null, "Promoción eliminada exitosamente","Validación",JOptionPane.DEFAULT_OPTION);

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

        mostrar();
        configurarTablaMateriales();
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        double suma = 0;
        double sumaPromocion = 0;

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT descripcion, inicio, fin FROM promociones WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Crea un formato personalizado para mostrar las fechas
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM, yyyy");

                // Obtén las fechas del ResultSet
                String fechaPedidoString = resultSet.getString("inicio");
                String fechaEntregaString = resultSet.getString("fin");

                // Convierte las cadenas de fecha en objetos Date
                SimpleDateFormat formatoOriginal = new SimpleDateFormat("yyyy-MM-dd");

                Date fechaPedido = formatoOriginal.parse(fechaPedidoString);
                Date fechaEntrega = formatoOriginal.parse(fechaEntregaString);

                // Formatea las fechas en el estilo deseado
                String fechaPedidoFormateada = formatoFecha.format(fechaPedido);
                String fechaEntregaFormateada = formatoFecha.format(fechaEntrega);

                // Establece los textos en tus campos de texto
                campoFechaInicio.setText(fechaPedidoFormateada);
                campoFechaFin.setText(fechaEntregaFormateada);
                campoDescripcion.setText(resultSet.getString("descripcion"));

                DefaultTableModel modeloProductos = new DefaultTableModel();
                modeloProductos.addColumn("N°");
                modeloProductos.addColumn("Producto");
                modeloProductos.addColumn("Cantidad");
                modeloProductos.addColumn("Precio");
                modeloProductos.addColumn("Total");
                modeloProductos.addColumn("Precio (promoción)");
                modeloProductos.addColumn("Total (promoción)");

                PreparedStatement detallesStatement = mysql.prepareStatement(
                        "SELECT dv.cantidad, dv.promocion, " +
                                "CASE dv.tipo_detalle " +
                                "   WHEN 'material' THEN m.nombre " +
                                "   WHEN 'tarjeta' THEN t.ocasion " +
                                "   WHEN 'floristeria' THEN f.nombre " +
                                "   WHEN 'manualidad' THEN ma.nombre " +
                                "   WHEN 'globo' THEN g.codigo_globo " +
                                "   WHEN 'mobiliario' THEN mo.nombreMobiliario " +
                                "   WHEN 'arreglo' THEN a.nombre " +
                                "   WHEN 'desayuno' THEN d.nombre " +
                                "END AS nombre_detalle, " +
                                "CASE dv.tipo_detalle " +
                                "   WHEN 'material' THEN m.precio " +
                                "   WHEN 'tarjeta' THEN t.precio_tarjeta " +
                                "   WHEN 'floristeria' THEN f.precio " +
                                "   WHEN 'manualidad' THEN ma.precio_manualidad " +
                                "   WHEN 'globo' THEN g.precio " +
                                "   WHEN 'mobiliario' THEN mo.precioUnitario " +
                                "   WHEN 'arreglo' THEN a.precio " +
                                "   WHEN 'desayuno' THEN d.precio_desayuno " +
                                "END AS precio_detalle " +
                                "FROM detalles_promociones dv " +
                                "LEFT JOIN materiales m ON dv.detalle_id = m.id AND dv.tipo_detalle = 'material' " +
                                "LEFT JOIN tarjetas t ON dv.detalle_id = t.id AND dv.tipo_detalle = 'tarjeta' " +
                                "LEFT JOIN floristeria f ON dv.detalle_id = f.id AND dv.tipo_detalle = 'floristeria' " +
                                "LEFT JOIN manualidades ma ON dv.detalle_id = ma.id AND dv.tipo_detalle = 'manualidad' " +
                                "LEFT JOIN globos g ON dv.detalle_id = g.id AND dv.tipo_detalle = 'globo' " +
                                "LEFT JOIN mobiliario mo ON dv.detalle_id = mo.id AND dv.tipo_detalle = 'mobiliario' " +
                                "LEFT JOIN arreglos a ON dv.detalle_id = a.id AND dv.tipo_detalle = 'arreglo' " +
                                "LEFT JOIN desayunos d ON dv.detalle_id = d.id AND dv.tipo_detalle = 'desayuno' " +
                                "WHERE dv.promocion_id = ?;"
                );
                detallesStatement.setInt(1, this.id);
                ResultSet detallesResultSet = detallesStatement.executeQuery();

                int numeroDetalle = 1;
                while (detallesResultSet.next()) {
                    String nombreDetalle = detallesResultSet.getString("nombre_detalle");
                    int cantidad = detallesResultSet.getInt("cantidad");
                    double precioDetalle = detallesResultSet.getDouble("precio_detalle");
                    double precioPromocion = detallesResultSet.getDouble("promocion");

                    double subtotal = cantidad * precioDetalle; // Aplicar el factor 0.85 según tu lógica
                    suma += subtotal;

                    double subtotalPromocion = cantidad * precioPromocion; // Aplicar el factor 0.85 según tu lógica
                    sumaPromocion += subtotalPromocion;

                    Object[] fila = {numeroDetalle, nombreDetalle, cantidad + " unidades", "L. " + decimalFormat.format(precioDetalle), "L. " + decimalFormat.format(subtotal)
                            , "L. " + decimalFormat.format(precioPromocion), "L. " + decimalFormat.format(subtotalPromocion)};
                    modeloProductos.addRow(fila);

                    numeroDetalle++;
                }

                // Llenar la tabla de productos con el modelo
                productos.setModel(modeloProductos);

                // Formatear el número "suma" y establecerlo en lbl8
                String formattedLbl8 = "L. " + decimalFormat.format(suma);
                lbl8.setText(formattedLbl8 + "  ");

                // Formatear el número "suma" y establecerlo en lbl8
                String formattedLbl9 = "L. " + decimalFormat.format(sumaPromocion);
                lbl9.setText(formattedLbl9 + "  ");

                // Calcular la suma de "suma" y "precio_envioValue", formatearla y establecerla en lbl10
                double sumaLbl8Lbl9 = suma - sumaPromocion;
                String sumaFormateada = "L. " + decimalFormat.format(sumaLbl8Lbl9);

                lbl10.setText(sumaFormateada + "  ");

            } else {
                JOptionPane.showMessageDialog(null, "La promoción con el ID " + this.id + " no fue encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void eliminarPromocion() {
        try (Connection connection = sql.conectamysql()) {
            connection.setAutoCommit(false); // Iniciar una transacción

            // Eliminar los detalles de la promoción
            try (PreparedStatement deleteDetalles = connection.prepareStatement("DELETE FROM detalles_promociones WHERE promocion_id = ?")) {
                deleteDetalles.setInt(1, this.id);
                deleteDetalles.executeUpdate();
            } catch (SQLException e) {
                connection.rollback(); // Deshacer la transacción en caso de error
                e.printStackTrace();
            }

            // Eliminar la promoción
            try (PreparedStatement deletePromocion = connection.prepareStatement("DELETE FROM promociones WHERE id = ?")) {
                deletePromocion.setInt(1, this.id);
                deletePromocion.executeUpdate();
            } catch (SQLException e) {
                connection.rollback(); // Deshacer la transacción en caso de error
                e.printStackTrace();
            }

            connection.commit(); // Confirmar la transacción
        } catch (SQLException e) {
            e.printStackTrace();
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
            columnModel.getColumn(5).setPreferredWidth(100);
            columnModel.getColumn(6).setPreferredWidth(100);

            columnModel.getColumn(0).setCellRenderer(new VerPromociones.CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new VerPromociones.LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new VerPromociones.LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new VerPromociones.LeftAlignedRenderer());
            columnModel.getColumn(4).setCellRenderer(new VerPromociones.LeftAlignedRenderer());
            columnModel.getColumn(5).setCellRenderer(new VerPromociones.LeftAlignedRenderer());
            columnModel.getColumn(6).setCellRenderer(new VerPromociones.LeftAlignedRenderer());
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
            VerPromociones verPromociones = new VerPromociones(1);
            verPromociones.setVisible(true);
        });
    }
}
