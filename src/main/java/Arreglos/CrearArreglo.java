package Arreglos;

import Objetos.Conexion;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

public class CrearArreglo extends JFrame {
    private JTextField campoNombre;
    private JFormattedTextField campoPrecio;
    private JComboBox<String> comboBoxProveedor;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JPanel panel;
    private JRadioButton radioButtonSi;
    private JRadioButton radioButtonNo;
    private JButton buscarImagenButton;
    private JLabel label1, label2, label3, label4, label5;
    private CrearArreglo actual = this;
    private Conexion sql;
    Color primaryColor = Color.decode("#263238"); // Gris azul oscuro
    Color lightColor = Color.decode("#37474f"); // Gris azul claro
    Color darkColor = Color.decode("#000a12"); // Gris azul más oscuro
    Color textColor = Color.WHITE; // Texto blanco

    public CrearArreglo() {
        super("Crear Arreglo");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(panel);

        sql = new Conexion();

        panel.setBackground(lightColor);

        botonCancelar.setBackground(darkColor);
        botonCancelar.setForeground(textColor);

        botonGuardar.setBackground(darkColor);
        botonGuardar.setForeground(textColor);

        buscarImagenButton.setBackground(darkColor);
        buscarImagenButton.setForeground(textColor);

        radioButtonSi.setBackground(lightColor);
        radioButtonSi.setForeground(textColor);

        radioButtonNo.setBackground(lightColor);
        radioButtonNo.setForeground(textColor);

        label1.setForeground(textColor);
        label2.setForeground(textColor);
        label3.setForeground(textColor);
        label4.setForeground(textColor);
        label5.setForeground(textColor);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonSi);
        buttonGroup.add(radioButtonNo);

        // No seleccionar ningún botón de radio por defecto
        buttonGroup.clearSelection();

        try {
            MaskFormatter formatter = new MaskFormatter("L  ##,###.##");
            formatter.setPlaceholderCharacter('_');
            campoPrecio.setFormatterFactory(new DefaultFormatterFactory(formatter));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Cargar los iconos en blanco
        ImageIcon cancelIcon = new ImageIcon("cancel_icon_white.png");
        ImageIcon saveIcon = new ImageIcon("save_icon_white.png");
        ImageIcon updateIcon = new ImageIcon("update_icon_white.png");

        // Establecer los iconos en los botones
        botonCancelar.setIcon(cancelIcon);
        botonGuardar.setIcon(saveIcon);
        buscarImagenButton.setIcon(updateIcon);

        // Establecer el color de texto en blanco para los botones
        botonCancelar.setForeground(textColor);
        botonGuardar.setForeground(textColor);
        buscarImagenButton.setForeground(textColor);

        // Establecer el fondo oscuro para los botones
        botonCancelar.setBackground(darkColor);
        botonGuardar.setBackground(darkColor);
        buscarImagenButton.setBackground(darkColor);

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Volver a la lista de floristerías
                ListaArreglo listaArreglo = new ListaArreglo();
                listaArreglo.setVisible(true);
                actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    guardarArreglo();
                }
            }
        });
    }


    private boolean validarCampos() {
        String nombre = campoNombre.getText().trim();
        String precioText = campoPrecio.getText().replace("L ", "").replace(",", "").replace("_", "");

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre para el arreglo", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (precioText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un precio para el arreglo", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El formato del precio es inválido", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }



        return true;
    }

    private void guardarArreglo() {
        String nombre = campoNombre.getText().trim();
        String precioText = campoPrecio.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precio = Double.parseDouble(precioText);
        String proveedorText = comboBoxProveedor.getSelectedItem().toString();
        int idProveedor = Integer.parseInt(proveedorText.split(" - ")[0]);

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO arreglos (nombre, imagen, precio, disponible ) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setDouble(2, precio);
            preparedStatement.setInt(3, idProveedor);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Arreglo creado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Volver a la lista de floristerías
            ListaArreglo listaArreglo = new ListaArreglo();
            listaArreglo.setVisible(true);
            actual.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el arreglo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
