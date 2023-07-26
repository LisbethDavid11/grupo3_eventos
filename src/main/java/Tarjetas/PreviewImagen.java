package Tarjetas;

import javax.swing.*;
import java.awt.*;

public class PreviewImagen extends JFrame {
    private JPanel panel1;
    private JLabel jlabelImagen;
    public PreviewImagen(String ruta) {
        super("Crear datos de tarjetas");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        // Establecer ancho y alto deseados para el panelImg
        int panelImgWidth = 900;
        int panelImgHeight = 600;

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);

        // Establecer las dimensiones en el panelImg
        panel1.setPreferredSize(panelImgSize);
        panel1.setMaximumSize(panelImgSize);
        panel1.setMinimumSize(panelImgSize);
        panel1.setSize(panelImgSize);

        // Configurar el layout del panelImg como GridBagLayout
        panel1.setLayout(new GridBagLayout());

        // Configurar restricciones de diseño para la etiqueta de imagen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        jlabelImagen.setHorizontalAlignment(SwingConstants.CENTER);
        panel1.add(jlabelImagen, gbc);


        ImageIcon originalIcon = new ImageIcon(ruta);

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

        jlabelImagen.setIcon(scaledIcon);
    }
}
