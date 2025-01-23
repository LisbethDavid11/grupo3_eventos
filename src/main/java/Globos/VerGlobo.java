/**
 * VerGlobo.java
 *
 * Ver Globo
 *
 * @author Lisbeth David
 * @version 1.0
 * @since 2024-05-05
 */

package Globos;

import Objetos.Conexion;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerGlobo extends JFrame {
    // Paneles
    public JPanel panel1;
    public JPanel panelImg;

    // Botón
    public JButton cancelarButton;

    // Campos de texto
    private JTextField etiquetaCodigo;
    private JTextField etiquetaTipo;
    private JTextField etiquetaMaterial;
    private JTextField etiquetaPara;
    private JTextField etiquetaTamanio;
    private JTextField etiquetaColor;
    private JTextField etiquetaForma;
    private JTextField etiquetaPaquete;
    private JTextField etiquetaPortaGlobo;
    private JTextField etiquetaExistencia;
    private JTextField etiquetaPrecio;

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
    private JLabel lbl11;
    private JLabel lblImagen;

    // Otras variables
    private String imagePath = "";
    private int panelImgWidth = 200;
    private int panelImgHeight = 200;
    public VerGlobo mostrarGlobo = this;
    private final VerGlobo actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;

    // Fuente y colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 20);
    Font font = new Font("Century Gothic", Font.BOLD, 15);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

    // Colores etiquetaPara el botón "Cyan"
    Color primaryColorCyan = new Color(0, 188, 212); // Cyan primario
    Color lightColorCyan = new Color(77, 208, 225); // Cyan claro
    Color darkColorCyan = new Color(0, 151, 167); // Cyan oscuro

    // Colores etiquetaPara el botón "Aqua"
    Color primaryColorAqua = new Color(0, 150, 136); // Aqua primario
    Color lightColorAqua = new Color(77, 182, 172); // Aqua claro
    Color darkColorAqua = new Color(0, 121, 107); // Aqua oscuro

    // Colores etiquetaPara el botón "Rosado"
    Color primaryColorRosado = new Color(233, 30, 99); // Rosado primario
    Color lightColorRosado = new Color(240, 98, 146); // Rosado claro
    Color darkColorRosado = new Color(194, 24, 91); // Rosado oscuro

    // Colores etiquetaPara el botón "Amber"
    Color primaryColorAmber = new Color(255, 193, 7); // Amber primario
    Color lightColorAmber = new Color(255, 213, 79); // Amber claro
    Color darkColorAmber = new Color(255, 160, 0); // Amber oscuro

    // Colores etiquetaPara el botón "Verde lima"
    Color primaryColorVerdeLima = new Color(205, 220, 57); // Verde lima primario
    Color lightColorVerdeLima = new Color(220, 237, 200); // Verde lima claro
    Color darkColorVerdeLima = new Color(139, 195, 74); // Verde lima oscuro

    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public VerGlobo(int id) {
        super("");
        setSize(700, 675);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        this.id = id;
        mostrar();

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);

        // Establecer las dimensiones en el panelImg
        panelImg.setPreferredSize(panelImgSize);
        panelImg.setMaximumSize(panelImgSize);
        panelImg.setMinimumSize(panelImgSize);
        panelImg.setSize(panelImgSize);

        // Configurar el layout del panelImg como GridBagLayout
        panelImg.setLayout(new GridBagLayout());

        // Configurar restricciones de diseño etiquetaPara la etiqueta de imagen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        panelImg.add(lblImagen, gbc);

        etiquetaCodigo.setEditable(false);
        etiquetaCodigo.setFocusable(false);
        etiquetaTipo.setEditable(false);
        etiquetaTipo.setFocusable(false);
        etiquetaMaterial.setEditable(false);
        etiquetaMaterial.setFocusable(false);
        etiquetaPara.setEditable(false);
        etiquetaPara.setFocusable(false);
        etiquetaTamanio.setEditable(false);
        etiquetaTamanio.setFocusable(false);
        etiquetaColor.setEditable(false);
        etiquetaColor.setFocusable(false);
        etiquetaForma.setEditable(false);
        etiquetaForma.setFocusable(false);
        etiquetaPaquete.setEditable(false);
        etiquetaPaquete.setFocusable(false);
        etiquetaPortaGlobo.setEditable(false);
        etiquetaPortaGlobo.setFocusable(false);
        etiquetaExistencia.setEditable(false);
        etiquetaExistencia.setFocusable(false);
        etiquetaPrecio.setEditable(false);
        etiquetaPrecio.setFocusable(false);

        panel1.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));

        Color textColor = Color.decode("#263238");
        Color textColor2 = Color.decode("#607d8b");
        Color textFieldColor = Color.decode("#F5F5F5");

        etiquetaCodigo.setEditable(false);
        etiquetaCodigo.setFocusable(false);
        etiquetaCodigo.setForeground(textColor);
        etiquetaCodigo.setBackground(textFieldColor);
        etiquetaCodigo.setBorder(BorderFactory.createEmptyBorder());
        etiquetaCodigo.setFont(font);

        etiquetaTipo.setEditable(false);
        etiquetaTipo.setFocusable(false);
        etiquetaTipo.setForeground(textColor);
        etiquetaTipo.setBackground(textFieldColor);
        etiquetaTipo.setBorder(BorderFactory.createEmptyBorder());
        etiquetaTipo.setFont(font);

        etiquetaMaterial.setEditable(false);
        etiquetaMaterial.setFocusable(false);
        etiquetaMaterial.setForeground(textColor);
        etiquetaMaterial.setBackground(textFieldColor);
        etiquetaMaterial.setBorder(BorderFactory.createEmptyBorder());
        etiquetaMaterial.setFont(font);

        etiquetaPara.setEditable(false);
        etiquetaPara.setFocusable(false);
        etiquetaPara.setForeground(textColor);
        etiquetaPara.setBackground(textFieldColor);
        etiquetaPara.setBorder(BorderFactory.createEmptyBorder());
        etiquetaPara.setFont(font);

        etiquetaTamanio.setEditable(false);
        etiquetaTamanio.setFocusable(false);
        etiquetaTamanio.setForeground(textColor);
        etiquetaTamanio.setBackground(textFieldColor);
        etiquetaTamanio.setBorder(BorderFactory.createEmptyBorder());
        etiquetaTamanio.setFont(font);

        etiquetaColor.setEditable(false);
        etiquetaColor.setFocusable(false);
        etiquetaColor.setForeground(textColor);
        etiquetaColor.setBackground(textFieldColor);
        etiquetaColor.setBorder(BorderFactory.createEmptyBorder());
        etiquetaColor.setFont(font);

        etiquetaForma.setEditable(false);
        etiquetaForma.setFocusable(false);
        etiquetaForma.setForeground(textColor);
        etiquetaForma.setBackground(textFieldColor);
        etiquetaForma.setBorder(BorderFactory.createEmptyBorder());
        etiquetaForma.setFont(font);

        etiquetaPaquete.setEditable(false);
        etiquetaPaquete.setFocusable(false);
        etiquetaPaquete.setForeground(textColor);
        etiquetaPaquete.setBackground(textFieldColor);
        etiquetaPaquete.setBorder(BorderFactory.createEmptyBorder());
        etiquetaPaquete.setFont(font);

        etiquetaPortaGlobo.setEditable(false);
        etiquetaPortaGlobo.setFocusable(false);
        etiquetaPortaGlobo.setForeground(textColor);
        etiquetaPortaGlobo.setBackground(textFieldColor);
        etiquetaPortaGlobo.setBorder(BorderFactory.createEmptyBorder());
        etiquetaPortaGlobo.setFont(font);

        etiquetaExistencia.setEditable(false);
        etiquetaExistencia.setFocusable(false);
        etiquetaExistencia.setForeground(textColor);
        etiquetaExistencia.setBackground(textFieldColor);
        etiquetaExistencia.setBorder(BorderFactory.createEmptyBorder());
        etiquetaExistencia.setFont(font);

        etiquetaPrecio.setEditable(false);
        etiquetaPrecio.setFocusable(false);
        etiquetaPrecio.setForeground(textColor);
        etiquetaPrecio.setBackground(textFieldColor);
        etiquetaPrecio.setBorder(BorderFactory.createEmptyBorder());
        etiquetaPrecio.setFont(font);

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
        lbl8.setForeground(textColor2);
        lbl9.setForeground(textColor2);
        lbl10.setForeground(textColor2);
        lbl11.setForeground(textColor2);

        lbl1.setFont(font2);
        lbl2.setFont(font2);
        lbl3.setFont(font2);
        lbl4.setFont(font2);
        lbl5.setFont(font2);
        lbl6.setFont(font2);
        lbl7.setFont(font2);
        lbl8.setFont(font2);
        lbl9.setFont(font2);
        lbl10.setFont(font2);
        lbl11.setFont(font2);

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
                ListaGlobos listaGlobos = new ListaGlobos();
                listaGlobos.setVisible(true);
                actual.dispose();
            }
        });
    }

    // Método para cargar los datos del globo
    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM globos WHERE id = ?");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etiquetaCodigo.setText(resultSet.getString("codigo_globo"));
                etiquetaExistencia.setText(resultSet.getString("cantidad") + " unidades");
                etiquetaPaquete.setText(resultSet.getString("cantidad_paquete") + " unidades, por paquete");
                etiquetaColor.setText(resultSet.getString("color"));
                etiquetaPara.setText(resultSet.getString("para"));
                etiquetaForma.setText(resultSet.getString("forma"));
                etiquetaTamanio.setText(resultSet.getString("tamano"));
                etiquetaPrecio.setText("L. " + resultSet.getString( "precio"));
                etiquetaMaterial.setText(resultSet.getString("material"));
                etiquetaTipo.setText(resultSet.getString("tipo"));
                String portaGlobo = resultSet.getString("porta_globo");
                String portaGloboText = portaGlobo.equals("1") ? "Si incluye" : "No incluye";
                etiquetaPortaGlobo.setText(portaGloboText);

                String imagenNombre = resultSet.getString("imagen");
                String imagenPath = "img/globos/" + imagenNombre;

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
            VerGlobo verGlobo = new VerGlobo(1); // Pasa el ID de la floristería que deseas ver
            verGlobo.setVisible(true);
        });
    }
}