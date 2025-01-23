/**
 * VerAlquileres.java
 *
 * Ver Alquileres
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-05
 */

package Alquileres;

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
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VerAlquileres extends JFrame {
    // Paneles
    private JPanel panel1, panel3, panel4, panel5, panel6;

    // Campos de entrada
    private JTextField campoTipo, campoFecha, campoHoraFin, campoHoraInicio, campoCliente;
    private JTextField campoTelefono;

    // Tabla
    private JTable productos;

    // Botones
    private JButton cancelarButton;

    // Etiquetas
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl7, lbl10, lbl11, lbl12, lbl13, lbl5, lblImagen;

    // ScrollPane
    private JScrollPane panel2;

    // Área de texto
    private JTextArea campoDireccion;

    // Otras variables y conexiones
    private Conexion sql;
    private Connection mysql;
    private int id;
    private VerAlquileres actual = this;

    // Fuentes y colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 20);
    Font font = new Font("Century Gothic", Font.BOLD, 15);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

    Color primaryColorCyan = new Color(0, 188, 212); // Cyan primario
    Color lightColorCyan = new Color(77, 208, 225); // Cyan claro
    Color darkColorCyan = new Color(0, 151, 167); // Cyan oscuro

    Color primaryColorAqua = new Color(0, 150, 136); // Aqua primario
    Color lightColorAqua = new Color(77, 182, 172); // Aqua claro
    Color darkColorAqua = new Color(0, 121, 107); // Aqua oscuro

    Color primaryColorRosado = new Color(233, 30, 99); // Rosado primario
    Color lightColorRosado = new Color(240, 98, 146); // Rosado claro
    Color darkColorRosado = new Color(194, 24, 91); // Rosado oscuro

    Color primaryColorAmber = new Color(255, 193, 7); // Amber primario
    Color lightColorAmber = new Color(255, 213, 79); // Amber claro
    Color darkColorAmber = new Color(255, 160, 0); // Amber oscuro

    Color primaryColorVerdeLima = new Color(205, 220, 57); // Verde lima primario
    Color lightColorVerdeLima = new Color(220, 237, 200); // Verde lima claro
    Color darkColorVerdeLima = new Color(139, 195, 74); // Verde lima oscuro

    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    // Variables para dimensiones de imagen
    private int panelImgWidth = 200;
    private int panelImgHeight = 200;
    private int panelImgWidth2 = 200;
    private int panelImgHeight2 = 200;

    public VerAlquileres(int id) {
        super("");
        setSize(850, 610);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;

        campoDireccion.setLineWrap(true);
        campoDireccion.setWrapStyleWord(true);

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        campoTipo.setBorder(BorderFactory.createEmptyBorder());
        campoTipo.setBackground(Color.decode("#F5F5F5"));
        campoTipo.setForeground(textColor);
        campoTipo.setEditable(false);
        campoTipo.setFocusable(false);

        JTableHeader header = productos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(darkColorCyan);

        campoHoraInicio.setBorder(BorderFactory.createEmptyBorder());
        campoHoraInicio.setBackground(Color.decode("#F5F5F5"));
        campoHoraInicio.setForeground(textColor);
        campoHoraInicio.setEditable(false);
        campoHoraInicio.setFocusable(false);

        campoDireccion.setBorder(BorderFactory.createEmptyBorder());
        campoDireccion.setBackground(Color.decode("#F5F5F5"));
        campoDireccion.setForeground(textColor);
        campoDireccion.setEditable(false);
        campoDireccion.setFocusable(false);

        campoFecha.setBorder(BorderFactory.createEmptyBorder());
        campoFecha.setBackground(Color.decode("#F5F5F5"));
        campoFecha.setForeground(textColor);
        campoFecha.setEditable(false);
        campoFecha.setFocusable(false);

        campoCliente.setBorder(BorderFactory.createEmptyBorder());
        campoCliente.setBackground(Color.decode("#F5F5F5"));
        campoCliente.setForeground(textColor);
        campoCliente.setEditable(false);
        campoCliente.setFocusable(false);

        campoTelefono.setBorder(BorderFactory.createEmptyBorder());
        campoTelefono.setBackground(Color.decode("#F5F5F5"));
        campoTelefono.setForeground(textColor);
        campoTelefono.setEditable(false);
        campoTelefono.setFocusable(false);

        campoHoraFin.setBorder(BorderFactory.createEmptyBorder());
        campoHoraFin.setBackground(Color.decode("#F5F5F5"));
        campoHoraFin.setForeground(textColor);
        campoHoraFin.setEditable(false);
        campoHoraFin.setFocusable(false);

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
        lbl7.setForeground(textColor2);
        lbl11.setForeground(textColor2);
        lbl12.setForeground(textColor2);
        lbl13.setForeground(textColor2);
        lbl5.setForeground(textColor2);

        lbl1.setFont(font2);
        lbl2.setFont(font2);
        lbl3.setFont(font2);
        lbl4.setFont(font2);
        lbl7.setFont(font2);
        lbl11.setFont(font2);
        lbl12.setFont(font2);
        lbl13.setFont(font2);

        campoTipo.setFont(font);
        campoHoraInicio.setFont(font);
        campoFecha.setFont(font);
        campoDireccion.setFont(font);
        campoCliente.setFont(font);
        campoHoraFin.setFont(font);
        campoTelefono.setFont(font);

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
                ListaAlquileres listaAlquileres = new ListaAlquileres();
                listaAlquileres.setVisible(true);
                actual.dispose();
            }
        });

        mostrar();
        configurarTablaMateriales();
    }

    // Método para cargar los valores del alquiler
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

                // Establece los textos en tus campos de texto
                campoFecha.setText(fechaPedidoFormateada);

                campoTipo.setText(resultSet.getString("tipo"));

                // Obtén los valores de tiempo de inicio y fin desde el ResultSet
                Time horaInicio = resultSet.getTime("hora_inicial");
                Time horaFin = resultSet.getTime("hora_final");

                // Formatea los valores de tiempo en el estilo deseado
                SimpleDateFormat formatoHoraFormateada = new SimpleDateFormat("hh:mm a");
                String horaInicioFormateada = formatoHoraFormateada.format(horaInicio);
                String horaFinFormateada = formatoHoraFormateada.format(horaFin);

                // Establece los textos en tus campos de texto
                campoHoraInicio.setText(horaInicioFormateada);
                campoHoraFin.setText(horaFinFormateada);

                campoDireccion.setText(resultSet.getString("descripcion"));
                campoCliente.setText(resultSet.getString("nombre") + " " + resultSet.getString("apellido"));
                campoTelefono.setText(resultSet.getString("telefono"));

                DefaultTableModel modeloProductos = new DefaultTableModel();
                modeloProductos.addColumn("N°");
                modeloProductos.addColumn("Producto");
                modeloProductos.addColumn("Cantidad");
                modeloProductos.addColumn("Precio");
                modeloProductos.addColumn("Total");

                PreparedStatement detallesStatement = mysql.prepareStatement(
                        "SELECT da.cantidad, " +
                                " t.nombreMobiliario, " +
                                " t.precioUnitario " +
                                "FROM detalles_alquileres da " +
                                "LEFT JOIN mobiliario t ON da.detalle_id = t.id " +
                                "WHERE da.alquiler_id = ?;"
                );
                detallesStatement.setInt(1, this.id);
                ResultSet detallesResultSet = detallesStatement.executeQuery();

                int numeroDetalle = 1;
                while (detallesResultSet.next()) {
                    String nombreDetalle = detallesResultSet.getString("nombreMobiliario");
                    int cantidad = detallesResultSet.getInt("cantidad");
                    double precioDetalle = detallesResultSet.getDouble("precioUnitario");
                    double subtotal = cantidad * precioDetalle; // Aplicar el factor 0.85 según tu lógica
                    suma += subtotal;

                    Object[] fila = {numeroDetalle, nombreDetalle, cantidad + " unidades", "L. " + decimalFormat.format(precioDetalle), "L. " + decimalFormat.format(subtotal)};
                    modeloProductos.addRow(fila);

                    numeroDetalle++;
                }

                // Llenar la tabla de productos con el modelo
                productos.setModel(modeloProductos);


                // Formatear el número "suma" y establecerlo en lbl9
                String formattedLbl10 = "L. " + decimalFormat.format(suma);
                lbl10.setText(formattedLbl10 + "  ");

            } else {
                JOptionPane.showMessageDialog(null, "El pedido con el ID " + this.id + " no fue encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para configurar la table de materiales
    private void configurarTablaMateriales() {
        int columnCount = productos.getColumnCount();
        if (columnCount > 0) {
            TableColumnModel columnModel = productos.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(20);
            columnModel.getColumn(1).setPreferredWidth(220);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(100);
            columnModel.getColumn(4).setPreferredWidth(100);

            columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
            columnModel.getColumn(4).setCellRenderer(new LeftAlignedRenderer());
        }
    }

    // Clase para alinear al centro los campos
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

    // Clase para alinear a la izquierda los campos
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

    // Método Principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerAlquileres verEventos = new VerAlquileres(1);
            verEventos.setVisible(true);
        });
    }
}
