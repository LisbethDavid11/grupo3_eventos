package Compras;
import Arreglos.ListaArreglo;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VerCompras extends JFrame {
    private JPanel panel1;
    private JTextField codigo_compra;
    private JTextField fecha;
    private JTextField proveedor;
    private JTextField empleado;
    private JTable productos;
    private JButton cancelarButton;
    private JLabel lbl0, lbl8, lbl9, lbl10, lbl12;
    private JScrollPane panel2;
    private Conexion sql;
    private Connection mysql;
    private int id;
    private VerCompras actual = this;
    Color textColor = Color.decode("#212121");
    Color darkColorCyan = new Color(0, 150, 136);
    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
    Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);

    public VerCompras(int id) {
        super("Ver Compras");
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
        codigo_compra.setForeground(textColor);
        proveedor.setForeground(textColor);
        empleado.setForeground(textColor);
        proveedor.setForeground(textColor);

        Color textFieldColor = Color.decode("#FFFFFF");
        codigo_compra.setBackground(textFieldColor);
        fecha.setBackground(textFieldColor);
        proveedor.setBackground(textFieldColor);
        empleado.setBackground(textFieldColor);

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));

        cancelarButton.setForeground(Color.WHITE);
        cancelarButton.setBackground(Color.decode("#263238"));
        cancelarButton.setFocusPainted(false);
        Color primaryColors = new Color(244, 67, 54); // Rojo primario
        Color lightColors = new Color(239, 154, 154); // Rojo claro
        Color darkColors = new Color(211, 47, 47); // Rojo oscuro

        cancelarButton.setBackground(primaryColors);

        // Crea un margen de 10 píxeles desde el borde inferior
        EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

        // Aplica el margen al botón
        cancelarButton.setBorder(margin);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaCompras listaCompras = new ListaCompras();
                listaCompras.setVisible(true);
                actual.dispose();
            }
        });
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        DecimalFormat decimalFormat = new DecimalFormat("#.00");

        double sumExentoFalse = 0;
        double sumIsv = 0;
        double sumExentoTrue = 0;

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM compras WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                codigo_compra.setText(resultSet.getString("codigo_compra"));

                // Mostrar la fecha actual
                Date date = new Date();
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
                        total = decimalFormat.format(subtotal);
                        sumExentoTrue += subtotal;
                    } else {
                        double impuesto = subtotal * 0.15;
                        total = decimalFormat.format(subtotal + impuesto);
                        isv = decimalFormat.format(impuesto);
                        sumExentoFalse += subtotal;
                        sumIsv += impuesto;
                    }

                    Object[] fila = {numeroMaterial, nombreMaterial, cantidad, precio, decimalFormat.format(subtotal), isv, total};
                    modeloProductos.addRow(fila);

                    numeroMaterial++;
                }

                productos.setModel(modeloProductos);

                lbl8.setText(decimalFormat.format(sumExentoFalse));
                lbl9.setText(decimalFormat.format(sumIsv));
                lbl12.setText(decimalFormat.format(sumExentoTrue));
                lbl10.setText(decimalFormat.format(sumExentoFalse + sumIsv + sumExentoTrue));

            } else {
                JOptionPane.showMessageDialog(null, "La compra con el ID " + this.id + " no fue encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerCompras verCompras = new VerCompras(1); // Pasa el ID de la compra que deseas ver
            verCompras.setVisible(true);
        });
    }
}
