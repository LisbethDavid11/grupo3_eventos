/**
 * AcercaDe.java
 *
 * Acerca De
 *
 * @author Elsa Ramos
 * @version 1.0
 * @since 2024-05-05
 */

package SubMenu;

import javax.swing.*;
import java.awt.*;

public class AcercaDe extends JPanel {

    // Elementos para manejo de imagenes
    private ImageIcon iaLogo, unahLogo;

    // Etiquetas de texto
    private JLabel iaLogoLabel, unahLogoLabel;

    public AcercaDe() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(149, 165, 166));

        // Carga de imágenes
        iaLogo = new ImageIcon("img/logo ia.jpeg");
        unahLogo = new ImageIcon("img/logo unah.png");

        // Panel para las imágenes
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        imagePanel.setBackground(new Color(245, 245, 245));
        iaLogoLabel = new JLabel();
        unahLogoLabel = new JLabel();
        imagePanel.add(iaLogoLabel);
        imagePanel.add(unahLogoLabel);
        add(imagePanel, BorderLayout.NORTH);

        // Información del sistema en el centro
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(245, 245, 245)); // Fondo más claro para el contenido
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen alrededor del panel central

        JLabel nameLabel = new JLabel("EVENTOS CHELSEA", JLabel.CENTER);
        nameLabel.setFont(new Font("Century Gothic", Font.BOLD, 40)); // Fuente más grande para el nombre
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Asegura la alineación al centro
        centerPanel.add(nameLabel);

        centerPanel.add(Box.createVerticalStrut(5)); // Añade un pequeño espacio

        addCenteredTextToPanel(centerPanel,
                "Bienvenido a Eventos Chelsea, su socio de confianza en la creación de momentos inolvidables. \n\n" +
                        "Con una trayectoria destacada en arreglos florales, coordinación de eventos y servicios personalizados de floristería, " +
                        "nos enorgullecemos de nuestra habilidad para diseñar experiencias únicas y personalizadas. " +
                        "Visite nuestro local o contáctenos.\n\n" +
                        "Proyecto elaborado por estudiantes de la Licenciatura en Informática Administrativa " +
                        "de la Universidad Nacional Autónoma de Honduras, Centro Tecnológico de Danlí, durante el año 2023, en las asignaturas:"
                        +"Análisis y Diseño de Sistemas y Programación e Implementación de Sistemas."
        );

        centerPanel.add(Box.createVerticalStrut(5)); // Espacio antes del link

        // Version del software
        JLabel versionLabel = new JLabel("v1.0", JLabel.CENTER);
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Fuente más pequeña para la versión
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(versionLabel);

        centerPanel.add(Box.createVerticalStrut(5)); // Espacio después de la versión

        // Derechos de autor
        JLabel copyRightLabel = new JLabel("Copyright (c) 2023 Todos los derechos reservados.", JLabel.CENTER);
        copyRightLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Fuente más pequeña para el copyright
        copyRightLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(copyRightLabel);

        centerPanel.add(Box.createVerticalStrut(5)); // Espacio después de los derechos de autor

        // Colaboradores
        JLabel contributorsLabel = new JLabel("Dania Lagos, Danelly Aroca, Elsa Ramos, Lisbeth David, Skarleth Ferrera y MSc Gladys Nolasco.", JLabel.CENTER);
        contributorsLabel.setFont(new Font("Arial", Font.BOLD, 10)); // Fuente adecuada para los colaboradores
        contributorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(contributorsLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Botones en la parte inferior
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(149, 165, 166)); // Fondo al estilo Material para el panel de botones
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton closeButton = new JButton("Cerrar");
        styleMaterialButton(closeButton);
        buttonPanel.add(closeButton);
        closeButton.addActionListener(e -> {
            // Referencia al JDialog actual
            Window dialogWindow = SwingUtilities.getWindowAncestor((Component)e.getSource());
            dialogWindow.dispose();
        });

        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ajuste de tamaño de ventana
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeImages();
            }
        });
    }

    // Método para cambiar tamaño de imagen
    private void resizeImages() {
        // Calcula nuevas dimensiones basadas en el tamaño de la ventana
        int width = getWidth();
        int iaWidth = width / 10;
        int unahWidth = iaWidth * 400 / 225;

        // Actualiza los íconos con las nuevas dimensiones
        iaLogoLabel.setIcon(resizeIcon(iaLogo, iaWidth, iaWidth));
        unahLogoLabel.setIcon(resizeIcon(unahLogo, unahWidth, iaWidth));

        revalidate();
        repaint();
    }

    // Método para cambiar tamaño de icono
    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(width, height,  Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // Método para centrar el texto dentro del panel
    private void addCenteredTextToPanel(JPanel panel, String text) {
        JTextArea textArea = new JTextArea(3, 20); // Ajusta el número de líneas y la anchura según sea necesario
        textArea.setText(text);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBackground(UIManager.getColor("Label.background"));
        textArea.setFont(new Font("Century Gothic", Font.BOLD, 16));
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20)); // Añade un borde invisible para espacio extra
        textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(textArea);
    }

    // Método para darle un estilo materializado al botón
    private void styleMaterialButton(JButton button) {
        Color buttonColor = Color.decode("#2c3e50"); // Color principal para el fondo del botón
        Color hoverColor = Color.decode("#34495e"); // Color para el fondo del botón al pasar el ratón por encima

        button.setBackground(buttonColor);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25)); // Espacio alrededor del texto del botón
        // Sombra ligera para dar efecto de elevación
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        // Cambia el color del botón al pasar el ratón por encima para simular elevación
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor); // Cambia al color de hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(buttonColor); // Vuelve al color principal
            }
        });
    }

    // Método Principal
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 630);
        frame.setLocationRelativeTo(null);
        frame.add(new AcercaDe());
        frame.setVisible(true);
    }
}
