package Arreglos;

import Objetos.Conexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerArreglo extends JFrame {
    private JPanel panel1, panel2;
    private JTextField etiquetaNombre, etiquetaPrecio, etiquetaDisponible;
    private JButton volverButton;
    private JLabel lblImagen, lbl0;
    private final VerArreglo actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;

    public VerArreglo(int id) {
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

        // Deshabilitar la edición de los JTextField y el JTextArea
        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaPrecio.setEditable(false);
        etiquetaPrecio.setFocusable(false);
        etiquetaDisponible.setEditable(false);
        etiquetaDisponible.setFocusable(false);

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));

        // Color de texto para los JTextField
        Color textColor = Color.decode("#212121");
        etiquetaNombre.setForeground(textColor);
        etiquetaPrecio.setForeground(textColor);
        etiquetaDisponible.setForeground(textColor);

        // Color de fondo para los JTextField
        Color textFieldColor = Color.decode("#FFFFFF");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaPrecio.setBackground(textFieldColor);
        etiquetaDisponible.setBackground(textFieldColor);

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
            ListaArreglo listaArreglos = new ListaArreglo();
            listaArreglos.setVisible(true);
            actual.dispose();
        });
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM arreglos WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etiquetaNombre.setText(resultSet.getString("nombre"));
                etiquetaPrecio.setText(resultSet.getString("precio"));
                etiquetaDisponible.setText(resultSet.getString("disponible"));

                String imagenNombre = resultSet.getString("imagen");
                String imagenPath = "img/arreglos/" + imagenNombre;

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
                        imagenIcono = new ImageIcon(imagenEscalada);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VerArreglo verArreglo = new VerArreglo(1); // Pasa el ID del arreglo que deseas ver
            verArreglo.setVisible(true);
        });
    }
}
