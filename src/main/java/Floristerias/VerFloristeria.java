package Floristerias;

import Objetos.Conexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerFloristeria extends JFrame {
    private JPanel panel1, panel2;
    private JTextField etiquetaNombre, etiquetaPrecio, etiquetaProveedor;
    private JButton volverButton;
    private JLabel lblImagen, lbl0;
    private final VerFloristeria actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;

    public VerFloristeria(int id) {
        super("");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;

        // Ajusta el layout del panel2 a GridBagLayout
        panel2.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Crea la etiqueta para mostrar la imagen
        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(JLabel.CENTER);
        lblImagen.setVerticalAlignment(JLabel.CENTER);
        lblImagen.setMaximumSize(new Dimension(300, 300)); // Establece un tamaño máximo

        // Agrega la etiqueta de la imagen al panel2 con la restricción gbc
        panel2.add(lblImagen, gbc);

        mostrar();

        // Deshabilitar la edición de los JTextField
        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaPrecio.setEditable(false);
        etiquetaPrecio.setFocusable(false);
        etiquetaProveedor.setEditable(false);
        etiquetaProveedor.setFocusable(false);

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));

        // Color de texto para los JTextField
        Color textColor = Color.decode("#212121");
        etiquetaNombre.setForeground(textColor);
        etiquetaPrecio.setForeground(textColor);
        etiquetaProveedor.setForeground(textColor);

        // Color de fondo para los JTextField
        Color textFieldColor = Color.decode("#FFFFFF");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaPrecio.setBackground(textFieldColor);
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
            ListaFloristeria listaFloristeria = new ListaFloristeria();
            listaFloristeria.setVisible(true);
            actual.dispose();
        });
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT floristeria.*, Proveedores.empresaProveedora, Proveedores.nombreVendedor FROM floristeria JOIN Proveedores ON floristeria.proveedor_id = Proveedores.id WHERE floristeria.id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etiquetaNombre.setText(resultSet.getString("nombre"));
                etiquetaPrecio.setText(resultSet.getString("precio"));
                etiquetaProveedor.setText(resultSet.getString("empresaProveedora") + " (" + resultSet.getString("nombreVendedor") + ")");

                String imagenNombre = resultSet.getString("imagen");
                String imagenPath = "img/floristeria/" + imagenNombre;

                try {
                    File imagenFile = new File(imagenPath);
                    if (imagenFile.exists()) {
                        ImageIcon imagenIcono = new ImageIcon(imagenPath);
                        Image imagenOriginal = imagenIcono.getImage();

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
                        ImageIcon imagenIconoEscalado = new ImageIcon(imagenEscalada);

                        // Establece el tamaño máximo para el ImageIcon
                        imagenIconoEscalado.setImageObserver(lblImagen);

                        // Actualiza la etiqueta lblImagen con el ImageIcon escalado
                        lblImagen.setIcon(imagenIconoEscalado);
                    } else {
                        System.out.println("No se encontró la imagen: " + imagenPath);
                    }
                } catch (Exception e) {
                    System.out.println("Error al cargar la imagen: " + e.getMessage());
                }
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerFloristeria verFloristeria = new VerFloristeria(1); // Pasa el ID de la floristería que deseas ver
            verFloristeria.setVisible(true);
        });
    }
}
