/**
 * PreviewImagen.java
 *
 * Preview Imagen
 *
 * @author Skarleth Ferrera
 * @version 1.0
 * @since 2024-05-05
 */

package Tarjetas;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PreviewImagen extends JFrame {
    private JPanel panel1;
    private JLabel jlabelImagen;
    private JButton regresarButton;

    public PreviewImagen(String ruta) {
        super("");
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

        // Crear el botón "REGRESAR" y establecer el color de fondo
        regresarButton = new JButton("REGRESAR");
        EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
        Color darkColorRosado = new Color(194, 24, 91); // Rosado oscuro
        regresarButton.setBackground(darkColorRosado);
        regresarButton.setForeground(Color.WHITE);
        regresarButton.setFocusPainted(false);
        regresarButton.setBorder(margin);

        // Configurar restricciones de diseño para el botón "REGRESAR"
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 1;
        gbcButton.fill = GridBagConstraints.HORIZONTAL;
        gbcButton.insets = new Insets(5, 0, 0, 0); // Espacio entre el botón y la imagen
        panel1.add(regresarButton, gbcButton);

        // Agregar ActionListener al botón "REGRESAR" para cerrar esta ventana y volver a la ventana "CrearTarjetas"
        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

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
