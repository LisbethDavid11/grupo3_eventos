package Materiales;
import Objetos.Conexion;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerMaterial extends JFrame {
    private JPanel panel1, panel2, panel3, panel4, panel5;
    private JTextArea etiquetaDescripcion;
    private JTextField etiquetaNombre, etiquetaCantidad, etiquetaPrecio, etiquetaDisponible, etiquetaProveedor, etiquetaExento;
    private JButton volverButton;
    private JLabel lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel lbl6;
    private JLabel lbl7;
    private final VerMaterial actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;
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
    public VerMaterial(int id) {
        super("");
        setSize(550, 460);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;

        etiquetaDescripcion.setLineWrap(true);
        etiquetaDescripcion.setWrapStyleWord(true);

        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaCantidad.setEditable(false);
        etiquetaCantidad.setFocusable(false);
        etiquetaPrecio.setEditable(false);
        etiquetaPrecio.setFocusable(false);
        etiquetaDisponible.setEditable(false);
        etiquetaDisponible.setFocusable(false);
        etiquetaExento.setEditable(false);
        etiquetaExento.setFocusable(false);
        etiquetaDescripcion.setEditable(false);
        etiquetaDescripcion.setFocusable(false);
        etiquetaProveedor.setEditable(false);
        etiquetaProveedor.setFocusable(false);

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));

        etiquetaCantidad.setBorder(BorderFactory.createEmptyBorder());
        etiquetaNombre.setBorder(BorderFactory.createEmptyBorder());
        etiquetaProveedor.setBorder(BorderFactory.createEmptyBorder());
        etiquetaPrecio.setBorder(BorderFactory.createEmptyBorder());
        etiquetaExento.setBorder(BorderFactory.createEmptyBorder());
        etiquetaDisponible.setBorder(BorderFactory.createEmptyBorder());

        etiquetaNombre.setFont(font);
        etiquetaProveedor.setFont(font);
        etiquetaCantidad.setFont(font);
        etiquetaPrecio.setFont(font);
        etiquetaExento.setFont(font);
        etiquetaDisponible.setFont(font);
        etiquetaDescripcion.setFont(font);

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        etiquetaNombre.setForeground(textColor);
        etiquetaCantidad.setForeground(textColor);
        etiquetaPrecio.setForeground(textColor);
        etiquetaExento.setForeground(textColor);
        etiquetaDisponible.setForeground(textColor);
        etiquetaDescripcion.setForeground(textColor);
        etiquetaProveedor.setForeground(textColor);

        Color textFieldColor = Color.decode("#F5F5F5");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaCantidad.setBackground(textFieldColor);
        etiquetaPrecio.setBackground(textFieldColor);
        etiquetaExento.setBackground(textFieldColor);
        etiquetaDisponible.setBackground(textFieldColor);
        etiquetaDescripcion.setBackground(textFieldColor);
        etiquetaProveedor.setBackground(textFieldColor);

        volverButton.setForeground(Color.WHITE);
        volverButton.setBackground(Color.decode("#263238"));
        volverButton.setFocusPainted(false);
        volverButton.setBorder(margin);

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

        volverButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor2);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor);
            }
        });

        volverButton.addActionListener(e -> {
            ListaMateriales listaMateriales = new ListaMateriales();
            listaMateriales.setVisible(true);
            actual.dispose();
        });
        mostrar();
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT materiales.*, Proveedores.empresaProveedora, Proveedores.nombreVendedor FROM materiales JOIN Proveedores ON materiales.proveedor_id = Proveedores.id WHERE materiales.id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etiquetaNombre.setText(resultSet.getString("nombre"));
                etiquetaCantidad.setText(resultSet.getString("cantidad") + " unidades");
                etiquetaPrecio.setText("L. " + resultSet.getString( "precio"));
                String exento = resultSet.getString("exento").equals("1") ? "Si está exento de ISV" : "No está exento de ISV";
                etiquetaExento.setText(exento);
                etiquetaDisponible.setText(resultSet.getString("disponible"));
                etiquetaDescripcion.setText(resultSet.getString("descripcion"));
                etiquetaProveedor.setText(resultSet.getString("empresaProveedora") + " (" + resultSet.getString("nombreVendedor") + ")");
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerMaterial verMaterial = new VerMaterial(1);
            verMaterial.setVisible(true);
        });
    }
}
