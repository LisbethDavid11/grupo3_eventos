package Roles;
import Objetos.Conexion;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerRol extends JFrame {
    private Conexion sql;
    private int id;
    private String nombreRol;

    // Colores para los permisos
    private final Color[] coloresPermisos = {
            new Color(44, 62, 80),   // Cliente
            new Color(142, 68, 173), // Empleado
            new Color(41, 128, 185), // Floristería
            new Color(44, 62, 80),
            new Color(142, 68, 173),
            new Color(41, 128, 185),
            new Color(39, 174, 96),
            new Color(22, 160, 133),
            new Color(127, 140, 141),
            new Color(192, 57, 43),
            new Color(211, 84, 0),
            new Color(243, 156, 18),
            new Color(64, 64, 122),
            new Color(34, 112, 147),
            new Color(204, 142, 53),
            new Color(179, 57, 57),
            new Color(33, 140, 116),
            new Color(113, 88, 226),
            new Color(197, 108, 240),
            new Color(75, 75, 75),
            new Color(255, 56, 56),
            new Color(255, 184, 184),
            new Color(103, 230, 220),
    };

    public VerRol(int id) {
        super("");
        setSize(740, 525);
        setLocationRelativeTo(null);
        sql = new Conexion();
        this.id = id;

        setLayout(new BorderLayout(0, 0));

        // Panel para Título y Nombre del Rol
        JPanel tituloPanel = new JPanel();
        tituloPanel.setLayout(new BoxLayout(tituloPanel, BoxLayout.Y_AXIS));
        tituloPanel.setBackground(new Color(245,245,245));

        JLabel titleLabel = new JLabel("VISUALIZAR DATOS DEL ROL", JLabel.CENTER);
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 40));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloPanel.add(titleLabel);

        JLabel nombreLabel = new JLabel(); // Se establecerá en mostrarRol()
        nombreLabel.setFont(new Font("Century Gothic", Font.BOLD, 30));
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloPanel.add(nombreLabel);

        add(tituloPanel, BorderLayout.NORTH);

        // Panel para los permisos
        JPanel permisosPanel = new JPanel();
        permisosPanel.setLayout(new BoxLayout(permisosPanel, BoxLayout.Y_AXIS));
        permisosPanel.setBackground(new Color(245, 245, 245));
        permisosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(permisosPanel, BorderLayout.CENTER);

        // Botón para cerrar la ventana
        JButton closeButton = new JButton("Cerrar");
        styleMaterialButton(closeButton);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaRoles listaRoles = new ListaRoles();
                listaRoles.setVisible(true);
                dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(149, 165, 166)); // Fondo gris detrás del botón
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        mostrarRol(nombreLabel, permisosPanel);
    }

    private void mostrarRol(JLabel nombreLabel, JPanel permisosPanel) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM roles WHERE id = ?")) {
            preparedStatement.setInt(1, this.id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                nombreRol = rs.getString("nombre");
                nombreLabel.setText(nombreRol);
                addPermisosPanel(permisosPanel, rs);
            } else {
                JOptionPane.showMessageDialog(this, "El rol con este nombre no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del rol.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPermisosPanel(JPanel panel, ResultSet rs) throws SQLException {
        // Panel para organizar los permisos en 3 columnas
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Ajusta el número de filas/columnas según sea necesario
        gridPanel.setBackground(new Color(245, 245, 245));

        String[] permisos = {"cliente", "empleado", "floristeria", "arreglo", "usuario", "material", "proveedor", "compra", "tarjeta", "manualidad", "globo", "desayuno", "venta", "mobiliario", "pedido", "promocion", "evento", "actividad", "alquiler", "rol"};
        for (int i = 0; i < permisos.length; i++) {
            if (rs.getBoolean(permisos[i])) {
                JLabel permisoLabel = new JLabel(permisos[i]);
                permisoLabel.setOpaque(true);
                permisoLabel.setBackground(coloresPermisos[i % coloresPermisos.length]);
                permisoLabel.setForeground(Color.WHITE); // Texto en color blanco
                permisoLabel.setFont(new Font("Arial", Font.BOLD, 12));
                permisoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Espacio alrededor del texto
                permisoLabel.setHorizontalAlignment(JLabel.CENTER); // Centrar texto en el label
                // Ajustar las esquinas redondeadas
                permisoLabel.setBorder(new RoundedBorder(10)); // 10 es el radio del arco para las esquinas redondeadas

                gridPanel.add(permisoLabel);
            }
        }
        panel.add(gridPanel);
    }

    private static class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }


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

    public static void main(String[] args) {
        VerRol verRol = new VerRol(1);
        verRol.setVisible(true);
    }
}
