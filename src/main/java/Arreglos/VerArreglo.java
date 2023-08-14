package Arreglos;

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
import java.text.DecimalFormat;

public class VerArreglo extends JFrame {
    private JPanel panel1, panelImg;
    private JTextField etiquetaNombre, etiquetaPrecio, etiquetaDisponible;
    private JButton volverButton;
    private JLabel lblImagen, lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private final VerArreglo actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;
    private int panelImgWidth = 220;
    private int panelImgHeight = 220;
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
    public VerArreglo(int id) {
        super("");
        setSize(550, 550);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        this.id = id;

        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);

        panelImg.setPreferredSize(panelImgSize);
        panelImg.setMaximumSize(panelImgSize);
        panelImg.setMinimumSize(panelImgSize);
        panelImg.setSize(panelImgSize);

        panelImg.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        panelImg.add(lblImagen, gbc);

        panel1.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));

        mostrar();

        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaPrecio.setEditable(false);
        etiquetaPrecio.setFocusable(false);
        etiquetaDisponible.setEditable(false);
        etiquetaDisponible.setFocusable(false);

        panel1.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        etiquetaNombre.setForeground(textColor);
        etiquetaPrecio.setForeground(textColor);
        etiquetaDisponible.setForeground(textColor);

        etiquetaDisponible.setBorder(BorderFactory.createEmptyBorder());
        etiquetaNombre.setBorder(BorderFactory.createEmptyBorder());
        etiquetaPrecio.setBorder(BorderFactory.createEmptyBorder());

        Color textFieldColor = Color.decode("#F5F5F5");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaPrecio.setBackground(textFieldColor);
        etiquetaDisponible.setBackground(textFieldColor);

        etiquetaNombre.setFont(font);
        etiquetaDisponible.setFont(font);
        etiquetaPrecio.setFont(font);

        volverButton.setForeground(Color.WHITE);
        volverButton.setBackground(Color.decode("#263238"));
        volverButton.setFocusPainted(false);
        volverButton.setBorder(margin);

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        lbl1.setForeground(textColor2);
        lbl2.setForeground(textColor2);
        lbl3.setForeground(textColor2);

        lbl1.setFont(font2);
        lbl2.setFont(font2);
        lbl3.setFont(font2);

        etiquetaNombre.setFont(font);
        etiquetaPrecio.setFont(font);
        etiquetaDisponible.setFont(font);

        volverButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor2);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(textColor);
            }
        });

        volverButton.addActionListener(e -> {
            ListaArreglos listaArreglos = new ListaArreglos();
            listaArreglos.setVisible(true);
            actual.dispose();
        });
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();
        DecimalFormat decimalFormat = new DecimalFormat("###,###.00");

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM arreglos WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double precioValue = resultSet.getDouble("precio");
                String formattedPrecio = "L. " + decimalFormat.format(precioValue);
                etiquetaPrecio.setText(formattedPrecio + "  ");
                etiquetaNombre.setText(resultSet.getString("nombre"));
                etiquetaDisponible.setText(resultSet.getString("disponible") + " está disponible");

                String imagenNombre = resultSet.getString("imagen");
                String imagenPath = "img/arreglos/" + imagenNombre;

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
            System.out.println("Error al obtener los datos del arreglo: " + error.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerArreglo verArreglo = new VerArreglo(1);
            verArreglo.setVisible(true);
        });
    }
}
