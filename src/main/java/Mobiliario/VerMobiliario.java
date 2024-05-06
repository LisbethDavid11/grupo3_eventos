/**
 * VerMobiliario.java
 *
 * Ver Mobiliario
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-05
 */

package Mobiliario;

import Objetos.Conexion;
import Objetos.Mobiliario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class VerMobiliario extends JFrame {
    // Campos de texto
    private JTextField campoNombreMobiliario;
    private JTextField campoCantidad;
    private JTextField campoPrecioU;
    private JTextField comboColor;

    // RadioButtons
    private JRadioButton radioButtonSiNecesita;
    private JRadioButton radioButtonNoNecesita;

    // Botones
    private JButton botonVolver;

    // Paneles
    private JPanel panel1;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JPanel panelImg;

    // JLabels
    private JLabel lbl0;
    private JLabel lbl1;
    private JLabel lbl2;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JLabel imagenLabel;

    // Área de texto
    private JTextArea txtaDescripcion;

    // Otras variables
    private String imagePath = "";
    private VerMobiliario actual = this;
    private Conexion sql;

    // Fuente y colores
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    // Color de texto para los JTextField y JRadioButton
    Color textColor = Color.decode("#212121");
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 17);
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

    // Crea un margen de 10 píxeles desde el borde inferior
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

    public VerMobiliario(Mobiliario mobiliario) {
        super("Ver datos de mobiliario");
        setSize(700, 650);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();

        txtaDescripcion.setLineWrap(true);
        txtaDescripcion.setWrapStyleWord(true);

        // Establecer ancho y alto deseados para el panelImg
        int panelImgWidth = 200;
        int panelImgHeight = 200;

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);

        // Establecer las dimensiones en el panelImg
        panelImg.setPreferredSize(panelImgSize);
        panelImg.setMaximumSize(panelImgSize);
        panelImg.setMinimumSize(panelImgSize);
        panelImg.setSize(panelImgSize);

        // Configurar el layout del panelImg como GridBagLayout
        panelImg.setLayout(new GridBagLayout());

        // Configurar restricciones de diseño para la etiqueta de imagen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelImg.add(imagenLabel, gbc);

        panelImg.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel4.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));

        botonVolver.setForeground(Color.WHITE);
        botonVolver.setBackground(Color.decode("#263238"));
        botonVolver.setFocusPainted(false);
        botonVolver.setBorder(margin);


        lbl0.setForeground(textColor);
        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        campoPrecioU.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoPrecioU.getText();

                // Permitir solo dígitos y el carácter de punto decimal
                if (!Character.isDigit(c) && c != '.') {
                    e.consume(); // Ignorar cualquier otro carácter
                    return;
                }

                // Verificar si se excede el límite de caracteres
                if (text.length() >= 5 && c != '.' && !text.contains(".")) {
                    e.consume(); // Ignorar el carácter si se excede el límite de dígitos y no es un punto decimal
                    return;
                }

                // Verificar si ya hay un punto decimal y se intenta ingresar otro
                if (text.contains(".") && c == '.') {
                    e.consume(); // Ignorar el carácter si ya hay un punto decimal
                    return;
                }

                // Verificar la cantidad de dígitos después del punto decimal
                if (text.contains(".")) {
                    int dotIndex = text.indexOf(".");
                    int decimalDigits = text.length() - dotIndex - 1;
                    if (decimalDigits >= 2) {
                        e.consume();
                        return;
                    }
                }

            }
        });

        campoNombreMobiliario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoNombreMobiliario.getText();
                int length = text.length();

                if (length >= 30) {
                    e.consume();  // Ignorar la tecla si se alcanza la longitud máxima
                    return;
                }

                char c = e.getKeyChar();
                if (Character.isISOControl(c)) {
                    return;  // Permitir teclas de control como retroceso y eliminar
                }

                // Convertir la primera letra a mayúscula
                if (length == 0) {
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se intenta ingresar un carácter no permitido como primer carácter
                        return;
                    }
                    campoNombreMobiliario.setText(String.valueOf(Character.toUpperCase(c)));
                    e.consume();  // Ignorar el evento de tecla original para evitar que se agregue la letra dos veces
                    return;
                }

                // Verificar si se ingresó un espacio
                if (c == ' ') {
                    // Permitir solo un espacio entre palabras
                    if (length > 0 && text.charAt(length - 1) != ' ') {
                        return;
                    } else {
                        e.consume(); // Ignorar el evento si se intenta agregar más de un espacio consecutivo
                    }
                } else {
                    // Permitir solo letras
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se ingresa un carácter no permitido
                    }
                }
            }
        });

        txtaDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = txtaDescripcion.getText();
                int length = text.length();

                if (length >=255) {
                    e.consume();  // Ignorar la tecla si se alcanza la longitud máxima
                    return;
                }

                char c = e.getKeyChar();
                if (Character.isISOControl(c)) {
                    return;  // Permitir teclas de control como retroceso y eliminar
                }

                // Convertir la primera letra a mayúscula
                if (length == 0) {
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se intenta ingresar un carácter no permitido como primer carácter
                        return;
                    }
                    txtaDescripcion.setText(String.valueOf(Character.toUpperCase(c)));
                    e.consume();  // Ignorar el evento de tecla original para evitar que se agregue la letra dos veces
                    return;
                }

                // Verificar si se ingresó un espacio
                if (c == ' ') {
                    // Permitir solo un espacio entre palabras
                    if (length > 0 && text.charAt(length - 1) != ' ') {
                        return;
                    } else {
                        e.consume(); // Ignorar el evento si se intenta agregar más de un espacio consecutivo
                    }
                } else {
                    // Permitir solo letras
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se ingresa un carácter no permitido
                    }
                }
            }
        });

        campoCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

            }
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoCantidad.getText();

                // Permitir solo dígitos y el carácter de punto decimal
                if (!Character.isDigit(c)) {
                    e.consume(); // Ignorar cualquier otro carácter
                    return;
                }

                // Verificar si se excede el límite de caracteres
                if (text.length() >= 4) {
                    e.consume(); // Ignorar el carácter si se excede el límite de dígitos y no es un punto decimal
                    return;
                }
            }
        });

        botonVolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaMobiliario mobiliario1 = new ListaMobiliario();
                mobiliario1.setVisible(true);
                actual.dispose();
            }
        });

        txtaDescripcion.setBackground(new Color(215, 215, 215));



        txtaDescripcion.setText(mobiliario.getDescripcion());
        campoNombreMobiliario.setText(mobiliario.getNombreMobiliario());
        campoCantidad.setText(String.valueOf(mobiliario.getCantidad()));
        comboColor.setText(mobiliario.getColor().toString());
        campoPrecioU.setText(String.valueOf(mobiliario.getPrecioUnitario()));


        ImageIcon originalIcon = new ImageIcon(mobiliario.getImagen());

        // Obtener las dimensiones originales de la imagen
        int originalWidth = originalIcon.getIconWidth();
        int originalHeight = originalIcon.getIconHeight();

        // Calcular la escala para ajustar la imagen al JPanel
        double scale = Math.min((double) panelImgWidth / originalWidth, (double) panelImgHeight / originalHeight);

        // Calcular las nuevas dimensiones de la imagen redimensionada
        int scaledWidth = (int) (originalWidth * scale);
        int scaledHeight = (int) (originalHeight * scale);

        // Redimensionar la imagen manteniendo su proporción
        Image resizedImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        // Crear un nuevo ImageIcon a partir de la imagen redimensionada
        ImageIcon scaledIcon = new ImageIcon(resizedImage);

        imagenLabel.setIcon(scaledIcon);
    }

    // Método para obtener extensión de imagen
    private String obtenerExtensionImagen(String imagePath) {
        int extensionIndex = imagePath.lastIndexOf(".");
        if (extensionIndex != -1) {
            return imagePath.substring(extensionIndex);
        }
        return "";
    }

    // Método para generar un número aleatorio
    private String generarNumeroAleatorio(int min, int max) {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(max - min + 1) + min;
        return String.format("%04d", numeroAleatorio);
    }

    // Método para TODO
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}