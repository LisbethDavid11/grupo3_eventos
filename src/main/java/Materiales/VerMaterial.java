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
    private final VerMaterial actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;

    public VerMaterial(int id) {
        super("");
        setSize(550, 460);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;

        etiquetaDescripcion.setLineWrap(true);
        etiquetaDescripcion.setWrapStyleWord(true);

        // Deshabilitar la edición de los JTextField
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

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));

        // Aplicamos un borde vacío al JTextField para eliminar el borde
        etiquetaCantidad.setBorder(BorderFactory.createEmptyBorder());
        etiquetaNombre.setBorder(BorderFactory.createEmptyBorder());
        etiquetaProveedor.setBorder(BorderFactory.createEmptyBorder());
        etiquetaPrecio.setBorder(BorderFactory.createEmptyBorder());
        etiquetaExento.setBorder(BorderFactory.createEmptyBorder());
        etiquetaDisponible.setBorder(BorderFactory.createEmptyBorder());

        Font font = new Font("Century Gothic", Font.BOLD, 15);
        // Aplicamos la fuente personalizada al JTextField
        etiquetaNombre.setFont(font);
        etiquetaProveedor.setFont(font);
        etiquetaCantidad.setFont(font);
        etiquetaPrecio.setFont(font);
        etiquetaExento.setFont(font);
        etiquetaDisponible.setFont(font);
        etiquetaDescripcion.setFont(font);

        // Color de texto para los JTextField
        Color textColor = Color.decode("#1976D2");
        etiquetaNombre.setForeground(textColor);
        etiquetaCantidad.setForeground(textColor);
        etiquetaPrecio.setForeground(textColor);
        etiquetaExento.setForeground(textColor);
        etiquetaDisponible.setForeground(textColor);
        etiquetaDescripcion.setForeground(textColor);
        etiquetaProveedor.setForeground(textColor);

        // Color de fondo para los JTextField
        Color textFieldColor = Color.decode("#F5F5F5");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaCantidad.setBackground(textFieldColor);
        etiquetaPrecio.setBackground(textFieldColor);
        etiquetaExento.setBackground(textFieldColor);
        etiquetaDisponible.setBackground(textFieldColor);
        etiquetaDescripcion.setBackground(textFieldColor);
        etiquetaProveedor.setBackground(textFieldColor);

        // Color de texto de los botones
        volverButton.setForeground(Color.WHITE);

        // Color de fondo de los botones
        volverButton.setBackground(Color.decode("#263238"));
        volverButton.setFocusPainted(false);

        Color primaryColor = new Color(33, 150, 243); // Azul primario
        Color lightColor = new Color(100, 181, 246); // Azul claro
        Color darkColor = new Color(25, 118, 210); // Azul oscuro

        volverButton.setBackground(primaryColor);

        // Crea un margen de 10 píxeles desde el borde inferior
        EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

        // Aplica el margen al botón
        volverButton.setBorder(margin);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

        volverButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(darkColor);
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
