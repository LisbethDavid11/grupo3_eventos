/**
 * VerFloristeria.java
 *
 * Ver Floristeria
 *
 * @author Alejandra Aroca
 * @version 1.0
 * @since 2024-05-05
 */

package Floristerias;

import Objetos.Conexion;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerFloristeria extends JFrame {
    private JPanel panel1, panel2, panel3, panel4, panel5;
    private JTextField etiquetaNombre, etiquetaCantidad ,etiquetaPrecio, etiquetaProveedor;
    private JButton volverButton;
    private JLabel lblImagen, lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private final VerFloristeria actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;
    private int panelImgWidth = 200;
    private int panelImgHeight = 200;

    // Fuentes y colores
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

    public VerFloristeria(int id) {
        super("");
        setSize(550, 550);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);

        // Establecer las dimensiones en el panelImg
        panel2.setPreferredSize(panelImgSize);
        panel2.setMaximumSize(panelImgSize);
        panel2.setMinimumSize(panelImgSize);
        panel2.setSize(panelImgSize);

        // Configurar el layout del panelImg como GridBagLayout
        panel2.setLayout(new GridBagLayout());

        // Configurar restricciones de diseño para la etiqueta de imagen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        panel2.add(lblImagen, gbc);
        mostrar();

        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaProveedor.setEditable(false);
        etiquetaProveedor.setFocusable(false);
        etiquetaCantidad.setEditable(false);
        etiquetaCantidad.setFocusable(false);
        etiquetaPrecio.setEditable(false);
        etiquetaPrecio.setFocusable(false);

        etiquetaCantidad.setBorder(BorderFactory.createEmptyBorder());
        etiquetaNombre.setBorder(BorderFactory.createEmptyBorder());
        etiquetaProveedor.setBorder(BorderFactory.createEmptyBorder());
        etiquetaPrecio.setBorder(BorderFactory.createEmptyBorder());

        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        etiquetaNombre.setForeground(textColor);
        etiquetaCantidad.setForeground(textColor);
        etiquetaPrecio.setForeground(textColor);
        etiquetaProveedor.setForeground(textColor);

        Color textFieldColor = Color.decode("#F5F5F5");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaCantidad.setBackground(textFieldColor);
        etiquetaPrecio.setBackground(textFieldColor);
        etiquetaProveedor.setBackground(textFieldColor);

        volverButton.setForeground(Color.WHITE);
        volverButton.setBackground(Color.decode("#263238"));
        volverButton.setFocusPainted(false);
        volverButton.setBorder(margin);

        Color primaryColor = new Color(33, 150, 243); // Azul primario
        Color lightColor = new Color(100, 181, 246); // Azul claro
        Color darkColor = new Color(25, 118, 210); // Azul oscuro

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        lbl1.setForeground(textColor2);
        lbl2.setForeground(textColor2);
        lbl3.setForeground(textColor2);
        lbl4.setForeground(textColor2);

        lbl1.setFont(font2);
        lbl2.setFont(font2);
        lbl3.setFont(font2);
        lbl4.setFont(font2);

        etiquetaNombre.setFont(font);
        etiquetaPrecio.setFont(font);
        etiquetaProveedor.setFont(font);
        etiquetaCantidad.setFont(font);

        volverButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor2);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor);
            }
        });

        volverButton.addActionListener(e -> {
            ListaFloristerias listaFloristeria = new ListaFloristerias();
            listaFloristeria.setVisible(true);
            actual.dispose();
        });
    }

    // Método para cargar los datos de las floristerias
    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT floristeria.*, Proveedores.empresaProveedora, Proveedores.nombreVendedor FROM floristeria JOIN Proveedores ON floristeria.proveedor_id = Proveedores.id WHERE floristeria.id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etiquetaNombre.setText(resultSet.getString("nombre"));
                etiquetaCantidad.setText(resultSet.getString("cantidad") + " unidades");
                etiquetaPrecio.setText("L. " + resultSet.getString( "precio"));
                etiquetaProveedor.setText(resultSet.getString("empresaProveedora") + " (" + resultSet.getString("nombreVendedor") + ")");

                String imagenNombre = resultSet.getString("imagen");
                String imagenPath = "img/floristeria/" + imagenNombre;

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
            }
        } catch (SQLException error) {
            System.out.println(error.getMessage());
        }
    }

    // Método Principal
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerFloristeria verFloristeria = new VerFloristeria(1); // Pasa el ID de la floristería que deseas ver
            verFloristeria.setVisible(true);
        });
    }
}
