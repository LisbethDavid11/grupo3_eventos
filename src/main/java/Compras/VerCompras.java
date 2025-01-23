/**
 * VerCompras.java
 *
 * Ver Compras
 *
 * @author Lisbeth David
 * @version 1.0
 * @since 2024-05-05
 */

package Compras;

import Objetos.Conexion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class VerCompras extends JFrame {
    // Panel
    private JPanel panel1;

    // Campos de texto
    private JTextField codigo_compra;
    private JTextField fecha;
    private JTextField proveedor;
    private JTextField empleado;

    // Tabla de productos
    private JTable productos;

    // Botón de cancelar
    private JButton cancelarButton;

    // Etiquetas
    private JLabel lbl0;
    private JLabel lbl8;
    private JLabel lbl9;
    private JLabel lbl10;
    private JLabel lbl12;
    private JLabel lbl4;
    private JLabel lbl3;
    private JLabel lbl2;
    private JLabel lbl1;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel lbl7;
    private JLabel lbl13;

    // Panel de desplazamiento
    private JScrollPane panel2;

    // Panel para diseño
    private JPanel panel3;

    // Conexión a la base de datos
    private Conexion sql;
    private Connection mysql;

    // Identificador
    private int id;

    // Referencia a la ventana de visualización de compras actual
    private VerCompras actual = this;

    // Fuentes
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 20);
    Font font = new Font("Century Gothic", Font.BOLD, 15);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

    // Colores
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

    // Margen
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public VerCompras(int id) {
        super("");
        setSize(700, 470);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();

        codigo_compra.setEditable(false);
        codigo_compra.setFocusable(false);
        empleado.setEditable(false);
        empleado.setFocusable(false);
        proveedor.setEditable(false);
        proveedor.setFocusable(false);
        fecha.setEditable(false);
        fecha.setFocusable(false);

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        codigo_compra.setForeground(textColor);
        proveedor.setForeground(textColor);
        empleado.setForeground(textColor);
        proveedor.setForeground(textColor);

        Color textFieldColor = Color.decode("#F5F5F5");
        codigo_compra.setBackground(textFieldColor);
        fecha.setBackground(textFieldColor);
        proveedor.setBackground(textFieldColor);
        empleado.setBackground(textFieldColor);

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));

        codigo_compra.setBorder(BorderFactory.createEmptyBorder());
        fecha.setBorder(BorderFactory.createEmptyBorder());
        proveedor.setBorder(BorderFactory.createEmptyBorder());
        empleado.setBorder(BorderFactory.createEmptyBorder());

        codigo_compra.setFont(font);
        fecha.setFont(font);
        proveedor.setFont(font);
        empleado.setFont(font);

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
                ListaCompras listaCompras = new ListaCompras();
                listaCompras.setVisible(true);
                actual.dispose();
            }
        });
    }

    // Método para mostrar los datos
    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        DecimalFormat decimalFormat = new DecimalFormat("#,###.00");

        double sumExentoFalse = 0;
        double sumIsv = 0;
        double sumExentoTrue = 0;

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM compras WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                codigo_compra.setText(resultSet.getString("codigo_compra"));
                java.util.Date date = resultSet.getDate("fecha");
                SimpleDateFormat formatter = new SimpleDateFormat("dd 'de' MMMM, yyyy", new Locale("es", "ES"));
                fecha.setText(formatter.format(date));

                int proveedorId = resultSet.getInt("proveedor_id");
                int empleadoId = resultSet.getInt("empleado_id");
                String proveedorNombre = obtenerNombreProveedor(proveedorId);
                String empleadoNombre = obtenerNombreEmpleado(empleadoId);
                proveedor.setText(proveedorNombre);
                empleado.setText(empleadoNombre);

                DefaultTableModel modeloProductos = new DefaultTableModel();
                modeloProductos.addColumn("N°");
                modeloProductos.addColumn("Producto");
                modeloProductos.addColumn("Cantidad");
                modeloProductos.addColumn("Precio");
                modeloProductos.addColumn("SubTotal");
                modeloProductos.addColumn("ISV");
                modeloProductos.addColumn("Total");

                PreparedStatement detallesStatement = mysql.prepareStatement("SELECT m.nombre, dc.cantidad, dc.precio, m.exento FROM detalles_compras dc JOIN materiales m ON dc.material_id = m.id WHERE dc.compra_id = ?");
                detallesStatement.setInt(1, this.id);
                ResultSet detallesResultSet = detallesStatement.executeQuery();

                int numeroMaterial = 1;
                while (detallesResultSet.next()) {
                    String nombreMaterial = detallesResultSet.getString("nombre");
                    int cantidad = detallesResultSet.getInt("cantidad");
                    double precio = detallesResultSet.getDouble("precio");
                    double subtotal = cantidad * precio;
                    boolean exento = detallesResultSet.getBoolean("exento");

                    String isv;
                    String total;

                    if (exento) {
                        isv = "Exento";
                        total = "L. " + decimalFormat.format(subtotal);
                        sumExentoTrue += subtotal;
                    } else {
                        double impuesto = subtotal * 0.15;
                        total = "L. " + decimalFormat.format(subtotal + impuesto);
                        isv = "L. " + decimalFormat.format(impuesto);
                        sumExentoFalse += subtotal;
                        sumIsv += impuesto;
                    }

                    Object[] fila = {numeroMaterial, nombreMaterial, cantidad, "L. " + decimalFormat.format(precio), "L. " + decimalFormat.format(subtotal), isv, total};
                    modeloProductos.addRow(fila);

                    numeroMaterial++;
                }

                productos.setModel(modeloProductos);

                lbl8.setText("L. " + decimalFormat.format(sumExentoFalse));
                lbl9.setText("L. " + decimalFormat.format(sumIsv));
                lbl12.setText("L. " + decimalFormat.format(sumExentoTrue));
                lbl10.setText("L. " + decimalFormat.format(sumExentoFalse + sumIsv + sumExentoTrue));

            } else {
                JOptionPane.showMessageDialog(null, "La compra con el ID " + this.id + " no fue encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    // Método para obtener nombre del proveedor
    private String obtenerNombreProveedor(int proveedorId) {
        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT empresaProveedora FROM Proveedores WHERE id = ?;");
            statement.setInt(1, proveedorId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    // Método para obtener nombre del empleado
    private String obtenerNombreEmpleado(int empleadoId) {
        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT Nombres, Apellidos FROM Empleados WHERE id = ?;");
            statement.setInt(1, empleadoId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString(1) + " " + resultSet.getString(2);
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
        return null;
    }

    // Método Principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerCompras verCompras = new VerCompras(1); // Pasa el ID de la compra que deseas ver
            verCompras.setVisible(true);
        });
    }
}
