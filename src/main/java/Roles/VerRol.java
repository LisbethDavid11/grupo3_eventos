package Roles;
import Login.SesionUsuario;
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
    private String descripcion;

    public VerRol(int id) {
        super("");
        setSize(505, 300);
        setLocationRelativeTo(null);
        sql = new Conexion();
        this.id = id;

        setLayout(new BorderLayout(0, 0));

        // Panel para Título y Nombre del Rol
        JPanel tituloPanel = new JPanel();
        tituloPanel.setLayout(new BoxLayout(tituloPanel, BoxLayout.Y_AXIS));
        tituloPanel.setBackground(new Color(245,245,245));

        JLabel titleLabel = new JLabel("VISUALIZAR DATOS DEL ROL", JLabel.CENTER);
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.decode("#2196F3"));
        tituloPanel.add(titleLabel);

        JLabel nombreLabel = new JLabel(); // Se establecerá en mostrarRol()
        nombreLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        tituloPanel.add(nombreLabel);

        add(tituloPanel, BorderLayout.NORTH);

        // Panel para los permisos
        JPanel permisosPanel = new JPanel();
        permisosPanel.setLayout(new BoxLayout(permisosPanel, BoxLayout.Y_AXIS));
        permisosPanel.setBackground(new Color(245, 245, 245));
        permisosPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(permisosPanel, BorderLayout.CENTER);

        JTextArea descripcionTextArea = new JTextArea();
        descripcionTextArea.setFont(new Font("Century Gothic", Font.BOLD, 18));
        descripcionTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcionTextArea.setOpaque(false);
        descripcionTextArea.setLineWrap(true); // Para que el texto se ajuste automáticamente al ancho
        descripcionTextArea.setWrapStyleWord(true); // Para que las palabras se rompan correctamente
        descripcionTextArea.setEditable(false);
        descripcionTextArea.setFocusable(false);
        permisosPanel.add(descripcionTextArea);

        // Botón para cerrar la ventana
        JButton closeButton = new JButton("Cerrar");
        styleMaterialButton(closeButton);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                ListaRoles listaRoles = new ListaRoles(idUsuarioActual);
                listaRoles.setVisible(true);
                dispose();
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(149, 165, 166)); // Fondo gris detrás del botón
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        mostrarRol(nombreLabel, descripcionTextArea);
    }

    private void mostrarRol(JLabel nombreLabel, JTextArea descripcionTextArea) {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM roles WHERE id = ?")) {
            preparedStatement.setInt(1, this.id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                nombreRol = rs.getString("nombre");
                nombreLabel.setText(nombreRol);
                descripcion = rs.getString("descripcion");
                descripcionTextArea.setText(descripcion);

            } else {
                JOptionPane.showMessageDialog(this, "El rol con este nombre no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos del rol.", "Error", JOptionPane.ERROR_MESSAGE);
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
