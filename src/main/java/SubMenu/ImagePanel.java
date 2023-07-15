package SubMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

class ImagePanel extends JPanel {
    private Image imagen;

    public ImagePanel() {
        try {
            // Reemplaza "img/fondo-principal.jpg" con la ruta de tu imagen de fondo
            imagen = ImageIO.read(new File("img/fondo-principal.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la imagen
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);

        // Configurar el Graphics2D para dibujar el texto
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Configurar el color y la fuente del texto
        g2d.setColor(new Color(255, 255, 255, 200)); // Color de texto semitransparente (blanco con opacidad)
        g2d.setFont(new Font("Arial", Font.BOLD, 16));

        // Dibujar el texto
        String titulo = "EMPRESA DE EVENTOS CHELSEA";
        String cuerpo = "\nBienvenido a nuestra empresa, líder en el sector de arreglos, eventos, floristería y más.  " ;

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Centrar el texto horizontalmente
        int tituloWidth = g2d.getFontMetrics().stringWidth(titulo);
        int cuerpoWidth = g2d.getFontMetrics().stringWidth(cuerpo);

        int tituloX = (panelWidth - tituloWidth) / 2;
        int cuerpoX = (panelWidth - cuerpoWidth) / 2;

        // Posición vertical del texto
        int tituloY = panelHeight / 2 - 20;

        // Dividir el cuerpo en líneas
        String[] lineasCuerpo = cuerpo.split("\n");

        int lineHeight = g2d.getFontMetrics().getHeight();
        int cuerpoY = panelHeight / 2 - (lineasCuerpo.length * lineHeight) / 2;

        // Dibujar el título
        g2d.drawString(titulo, tituloX, tituloY);

        // Dibujar el cuerpo línea por línea
        g2d.setFont(new Font("Arial", Font.PLAIN, 14));
        for (String linea : lineasCuerpo) {
            g2d.drawString(linea, cuerpoX, cuerpoY);
            cuerpoY += lineHeight;
        }

        g2d.dispose();
    }
}
