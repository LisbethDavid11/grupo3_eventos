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
    private JPanel panel1;
    private JTextArea etiquetaDescripcion;
    private JTextField etiquetaNombre, etiquetaPrecio, etiquetaCantidad, etiquetaDisponible, etiquetaProveedor;
    private JButton volverButton;
    private JLabel lbl0;
    private final VerMaterial actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;

    public VerMaterial(int id) {
        super("");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;

        etiquetaDescripcion.setLineWrap(true);
        etiquetaDescripcion.setWrapStyleWord(true);

        // Deshabilitar la edición de los JTextField
        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaPrecio.setEditable(false);
        etiquetaPrecio.setFocusable(false);
        etiquetaCantidad.setEditable(false);
        etiquetaCantidad.setFocusable(false);
        etiquetaDisponible.setEditable(false);
        etiquetaDisponible.setFocusable(false);
        etiquetaDescripcion.setEditable(false);
        etiquetaDescripcion.setFocusable(false);
        etiquetaProveedor.setEditable(false);
        etiquetaProveedor.setFocusable(false);

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));

        // Color de texto para los JTextField
        Color textColor = Color.decode("#212121");
        etiquetaNombre.setForeground(textColor);
        etiquetaPrecio.setForeground(textColor);
        etiquetaCantidad.setForeground(textColor);
        etiquetaDisponible.setForeground(textColor);
        etiquetaDescripcion.setForeground(textColor);
        etiquetaProveedor.setForeground(textColor);

        // Color de fondo para los JTextField
        Color textFieldColor = Color.decode("#FFFFFF");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaPrecio.setBackground(textFieldColor);
        etiquetaCantidad.setBackground(textFieldColor);
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
                etiquetaPrecio.setText(resultSet.getString("precio"));
                etiquetaCantidad.setText(resultSet.getString("cantidad_inventario"));
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
            VerMaterial verMaterial = new VerMaterial(1); // Pasa el ID del material que deseas ver
            verMaterial.setVisible(true);
        });
    }
}
