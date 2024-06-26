/**
 * VerManualidades.java
 *
 * Ver Manualidades
 *
 * @author Elsa Ramos
 * @version 1.0
 * @since 2024-05-05
 */

package Manualidades;

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

public class VerManualidades extends JFrame {
    // Panel
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;

    // Campos de texto
    private JTextField nombre;
    private JTextField tipo;
    private JTextField precio_manualidad;
    private JTextField mano_obra;

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
    private JLabel lbl13;
    private JLabel lblImagen;

    // Área de texto
    private JTextArea descripcion;

    // ScrollPane
    private JScrollPane panel2;

    // Conexión a la base de datos
    private Conexion sql;
    private Connection mysql;

    // ID
    private int id;

    // Instancia de la clase
    private VerManualidades actual = this;

    // Fuente y colores
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

    public VerManualidades(int id) {
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

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        nombre.setBorder(BorderFactory.createEmptyBorder());
        nombre.setBackground(Color.decode("#F5F5F5"));
        nombre.setForeground(textColor);
        nombre.setEditable(false);
        nombre.setFocusable(false);

        precio_manualidad.setBorder(BorderFactory.createEmptyBorder());
        precio_manualidad.setBackground(Color.decode("#F5F5F5"));
        precio_manualidad.setForeground(textColor);
        precio_manualidad.setEditable(false);
        precio_manualidad.setFocusable(false);

        descripcion.setBorder(BorderFactory.createEmptyBorder());
        descripcion.setBackground(Color.decode("#F5F5F5"));
        descripcion.setForeground(textColor);
        descripcion.setEditable(false);
        descripcion.setFocusable(false);

        tipo.setBorder(BorderFactory.createEmptyBorder());
        tipo.setBackground(Color.decode("#F5F5F5"));
        tipo.setForeground(textColor);
        tipo.setEditable(false);
        tipo.setFocusable(false);

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

        nombre.setFont(font);
        precio_manualidad.setFont(font);
        tipo.setFont(font);
        descripcion.setFont(font);
        lbl8.setFont(font);
        lbl9.setFont(font);
        lbl10.setFont(font);

        JTableHeader header = productos.getTableHeader();
        header.setForeground(Color.WHITE);
        header.setBackground(darkColorCyan);

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
                ListaManualidades listaManualidades = new ListaManualidades();
                listaManualidades.setVisible(true);
                actual.dispose();
            }
        });

        configurarTablaMateriales();
    }

    // Método para cargar los datos de la manualidad
    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");
        double suma = 0;

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM manualidades WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nombre.setText(resultSet.getString("nombre"));
                tipo.setText(resultSet.getString("tipo"));
                double precioManualidadValue = resultSet.getDouble("precio_manualidad");
                String formattedPrecioManualidad = "L. " + decimalFormat.format(precioManualidadValue);
                precio_manualidad.setText(formattedPrecioManualidad + "  ");
                descripcion.setText(resultSet.getString("descripcion"));

                DefaultTableModel modeloProductos = new DefaultTableModel();
                modeloProductos.addColumn("N°");
                modeloProductos.addColumn("Producto");
                modeloProductos.addColumn("Cantidad");
                modeloProductos.addColumn("Precio");
                modeloProductos.addColumn("Total");

                PreparedStatement detallesStatement = mysql.prepareStatement("SELECT m.nombre, dc.cantidad, dc.precio FROM detalles_manualidades dc JOIN materiales m ON dc.material_id = m.id WHERE dc.manualidad_id = ?");
                detallesStatement.setInt(1, this.id);
                ResultSet detallesResultSet = detallesStatement.executeQuery();

                int numeroMaterial = 1;
                while (detallesResultSet.next()) {
                    String nombreMaterial = detallesResultSet.getString("nombre");
                    int cantidad = detallesResultSet.getInt("cantidad");
                    double precio = detallesResultSet.getDouble("precio");
                    double subtotal = cantidad * precio;
                    suma += subtotal;

                    Object[] fila = {numeroMaterial, "  " + nombreMaterial, "  " + cantidad + " unidades", "  L. " + decimalFormat.format(precio), "  L. " + decimalFormat.format(subtotal)};
                    modeloProductos.addRow(fila);

                    numeroMaterial++;
                }

                productos.setModel(modeloProductos);

                DecimalFormat decimalFormats = new DecimalFormat("###,###.00");

                String formattedLbl8 = "L. " + decimalFormats.format(suma);
                lbl8.setText(formattedLbl8 + "  ");

                String manoObraString = resultSet.getString("mano_obra");
                manoObraString = manoObraString.replace(",", ""); // Eliminar las comas del número
                double manoObraValue = 0.0;

                try {
                    manoObraValue = decimalFormats.parse(manoObraString).doubleValue();
                } catch (ParseException e) {
                    e.printStackTrace(); // Maneja esta excepción adecuadamente en tu aplicación
                }

                String formattedLbl9 = "L. " + decimalFormats.format(manoObraValue);
                lbl9.setText(formattedLbl9 + "  ");

                double sumaLbl8Lbl9 = Double.parseDouble(formattedLbl8.replace("L. ", "").replace(",", "")) + manoObraValue;
                String sumaFormateada = "L. " + decimalFormats.format(sumaLbl8Lbl9);

                lbl10.setText(sumaFormateada + "  ");

            } else {
                JOptionPane.showMessageDialog(null, "La manualidad con el ID " + this.id + " no fue encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            String imagenNombre = resultSet.getString("imagen");
            String imagenPath = "img/manualidades/" + imagenNombre;

            try {
                File imagenFile = new File(imagenPath);
                if (imagenFile.exists()) {
                    Image imagenOriginal = ImageIO.read(imagenFile);

                    // Ajusta el tamaño de la imagen para que se ajuste al tamaño predeterminado del panel
                    int anchoPanelPredeterminado = 300;
                    int altoPanelPredeterminado = 300;

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

    // Método para configurar la tabla
    private void configurarTablaMateriales() {
        int columnCount = productos.getColumnCount();
        if (columnCount > 0) {
            TableColumnModel columnModel = productos.getColumnModel();

            columnModel.getColumn(0).setPreferredWidth(20);
            columnModel.getColumn(1).setPreferredWidth(220);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(100);
            columnModel.getColumn(4).setPreferredWidth(100);

            columnModel.getColumn(0).setCellRenderer(new VerManualidades.CenterAlignedRenderer());
            columnModel.getColumn(1).setCellRenderer(new VerManualidades.LeftAlignedRenderer());
            columnModel.getColumn(2).setCellRenderer(new VerManualidades.LeftAlignedRenderer());
            columnModel.getColumn(3).setCellRenderer(new VerManualidades.LeftAlignedRenderer());
            columnModel.getColumn(4).setCellRenderer(new VerManualidades.LeftAlignedRenderer());
        }
    }

    // Clase para alinear los elementos al centro
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

    // Clase para alinear los elementos a la izquierda
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
            VerManualidades verManualidades = new VerManualidades(1);
            verManualidades.setVisible(true);
        });
    }
}
