package Desayunos;
import Objetos.Conexion;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;

public class VerDesayunos extends JFrame {
    private JPanel panel1, panel3, panel4, panel5, panel6;
    private JTextField nombre, cantidad, mano_obra, precio_desayuno, proveedor;
    private JTable productos;
    private JButton cancelarButton;
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6, lbl7, lbl8, lbl9, lbl10, lbl11, lbl12, lblImagen;
    private JScrollPane panel2;
    private JTextArea descripcion;
    private JLabel lbl13;
    private Conexion sql;
    private Connection mysql;
    private int id;
    private VerDesayunos actual = this;
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
    public VerDesayunos(int id) {
        super("");
        setSize(850, 610);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;

        descripcion.setLineWrap(true);
        descripcion.setWrapStyleWord(true);

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);
        Dimension panelImgSize2 = new Dimension(panelImgWidth2, panelImgHeight2);

        // Establecer las dimensiones en el panelImg
        panel3.setPreferredSize(panelImgSize);
        panel3.setMaximumSize(panelImgSize);
        panel3.setMinimumSize(panelImgSize);
        panel3.setSize(panelImgSize);

        panel4.setPreferredSize(panelImgSize2);
        panel4.setMaximumSize(panelImgSize2);
        panel4.setMinimumSize(panelImgSize2);
        panel4.setSize(panelImgSize2);

        // Configurar el layout del panelImg como GridBagLayout
        panel3.setLayout(new GridBagLayout());

        // Configurar restricciones de diseño para la etiqueta de imagen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        panel3.add(lblImagen, gbc);
        mostrar();

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel6.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        nombre.setBorder(BorderFactory.createEmptyBorder());
        nombre.setBackground(Color.decode("#F5F5F5"));
        nombre.setForeground(textColor);
        nombre.setEditable(false);
        nombre.setFocusable(false);

        JTableHeader header = productos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(darkColorCyan);

        precio_desayuno.setBorder(BorderFactory.createEmptyBorder());
        precio_desayuno.setBackground(Color.decode("#F5F5F5"));
        precio_desayuno.setForeground(textColor);
        precio_desayuno.setEditable(false);
        precio_desayuno.setFocusable(false);

        descripcion.setBorder(BorderFactory.createEmptyBorder());
        descripcion.setBackground(Color.decode("#F5F5F5"));
        descripcion.setForeground(textColor);
        descripcion.setEditable(false);
        descripcion.setFocusable(false);

        cantidad.setBorder(BorderFactory.createEmptyBorder());
        cantidad.setBackground(Color.decode("#F5F5F5"));
        cantidad.setForeground(textColor);
        cantidad.setEditable(false);
        cantidad.setFocusable(false);

        proveedor.setBorder(BorderFactory.createEmptyBorder());
        proveedor.setBackground(Color.decode("#F5F5F5"));
        proveedor.setForeground(textColor);
        proveedor.setEditable(false);
        proveedor.setFocusable(false);

        mano_obra.setBorder(BorderFactory.createEmptyBorder());
        mano_obra.setBackground(Color.decode("#F5F5F5"));
        mano_obra.setForeground(textColor);
        mano_obra.setEditable(false);
        mano_obra.setFocusable(false);

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

        nombre.setFont(font);
        precio_desayuno.setFont(font);
        cantidad.setFont(font);
        descripcion.setFont(font);
        proveedor.setFont(font);
        mano_obra.setFont(font);
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
                ListaDesayunos listaDesayunos = new ListaDesayunos();
                listaDesayunos.setVisible(true);
                actual.dispose();
            }
        });

        configurarTablaMateriales();
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        double suma = 0;

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT desayunos.*, Proveedores.empresaProveedora, Proveedores.nombreVendedor FROM desayunos JOIN Proveedores ON desayunos.proveedor_id = Proveedores.id WHERE desayunos.id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nombre.setText(resultSet.getString("nombre"));
                cantidad.setText(resultSet.getString("cantidad") + " unidades");
                double precioDesayunoValor = resultSet.getDouble("precio_desayuno");
                String formattedPrecioDesayuno = "L. " + decimalFormat.format(precioDesayunoValor);
                precio_desayuno.setText(formattedPrecioDesayuno + "  ");
                double manoObraValor = resultSet.getDouble("mano_obra");
                String formattedManoObra = "L. " + decimalFormat.format(manoObraValor);
                mano_obra.setText(formattedManoObra + "  ");
                descripcion.setText(resultSet.getString("descripcion"));
                proveedor.setText(resultSet.getString("empresaProveedora") + " (" + resultSet.getString("nombreVendedor") + ")");

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
                                "   WHEN 'globo' THEN g.codigo_globo " +
                                "   WHEN 'tarjeta' THEN t.ocasion " +
                                "   WHEN 'floristeria' THEN f.nombre " +
                                "END AS nombre_detalle, " +
                                "CASE dv.tipo_detalle " +
                                "   WHEN 'material' THEN m.precio " +
                                "   WHEN 'globo' THEN g.precio " +
                                "   WHEN 'tarjeta' THEN t.precio_tarjeta " +
                                "   WHEN 'floristeria' THEN f.precio " +
                                "END AS precio_detalle " +
                                "FROM detalles_desayunos dv " +
                                "LEFT JOIN materiales m ON dv.detalle_id = m.id AND dv.tipo_detalle = 'material' " +
                                "LEFT JOIN globos g ON dv.detalle_id = g.id AND dv.tipo_detalle = 'globo' " +
                                "LEFT JOIN tarjetas t ON dv.detalle_id = t.id AND dv.tipo_detalle = 'tarjeta' " +
                                "LEFT JOIN floristeria f ON dv.detalle_id = f.id AND dv.tipo_detalle = 'floristeria' " +
                                "WHERE dv.desayuno_id = ?;"
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

                String formattedLbl8 = "L. " + decimalFormat.format(suma);
                lbl8.setText(formattedLbl8 + "  ");

                String manoObraString = resultSet.getString("mano_obra");
                manoObraString = manoObraString.replace(",", ""); // Eliminar las comas del número
                double manoObraValue = Double.parseDouble(manoObraString);

                String formattedLbl9 = "L. " + decimalFormat.format(manoObraValue);
                lbl9.setText(formattedLbl9 + "  ");

                double sumaLbl8Lbl9 = suma + manoObraValue;
                String sumaFormateada = "L. " + decimalFormat.format(sumaLbl8Lbl9);

                lbl10.setText(sumaFormateada + "  ");

            } else {
                JOptionPane.showMessageDialog(null, "El desayuno con el ID " + this.id + " no fue encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            String imagenNombre = resultSet.getString("imagen");
            String imagenPath = "img/desayunos/" + imagenNombre;

            try {
                File imagenFile = new File(imagenPath);
                if (imagenFile.exists()) {
                    Image imagenOriginal = ImageIO.read(imagenFile);

                    // Ajusta el tamaño de la imagen para que se ajuste al tamaño predeterminado del panel
                    int anchoPanelPredeterminado = 200;
                    int altoPanelPredeterminado = 200;

                    // Calcula las proporciones de escalamiento para ajustar la imagen al tamaño del panel
                    double proporcionAncho = (double) anchoPanelPredeterminado / imagenOriginal.getWidth(null);
                    double proporcionAlto = (double) altoPanelPredeterminado / imagenOriginal.getHeight(null);

                    // Escala la imagen utilizando la proporción más pequeña para evitar distorsiones
                    double proporcionEscalamiento = Math.min(proporcionAncho, proporcionAlto);
                    int anchoEscalado = (int) (imagenOriginal.getWidth(null) * proporcionEscalamiento);
                    int altoEscalado = (int) (imagenOriginal.getHeight(null) * proporcionEscalamiento);

                    // Crea una nueva imagen escalada con las dimensiones calculadas
                    Image imagenEscalada = imagenOriginal.getScaledInstance(anchoEscalado, altoEscalado, Image.SCALE_SMOOTH);

                    // Crea un ImageIcon a partir de la imagen escalada
                    ImageIcon imagenIcono = new ImageIcon(imagenEscalada);

                    // Actualiza la etiqueta lblImagen con el ImageIcon
                    lblImagen.setIcon(imagenIcono);
                } else {
                    System.out.println("No se encontró la imagen: " + imagenPath);
                }
            } catch (Exception e) {
                System.out.println("Error al cargar la imagen: " + e.getMessage());
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
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

            columnModel.getColumn(0).setCellRenderer(new VerDesayunos.CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new VerDesayunos.LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new VerDesayunos.LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new VerDesayunos.LeftAlignedRenderer());
            columnModel.getColumn(4).setCellRenderer(new VerDesayunos.LeftAlignedRenderer());
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerDesayunos verDesayunos = new VerDesayunos(1);
            verDesayunos.setVisible(true);
        });
    }
}
