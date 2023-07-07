package Floristerias;

import Objetos.Conexion;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class CrearFloristeria extends JFrame {
    private JTextField campoNombre;
    private JFormattedTextField campoPrecio;
    private JComboBox<String> comboBoxProveedor;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JPanel panel;
    private CrearFloristeria actual = this;
    private Conexion sql;

    public CrearFloristeria() {
        super("Crear Floristería");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(panel);

        sql = new Conexion(); // Initialize the sql object

        try {
            MaskFormatter formatter = new MaskFormatter("L  ##,###.##");
            formatter.setPlaceholderCharacter('_');
            campoPrecio.setFormatterFactory(new DefaultFormatterFactory(formatter));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cargarProveedores();

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Volver a la lista de floristerías
                ListaFloristeria listaFloristeria = new ListaFloristeria();
                listaFloristeria.setVisible(true);
                actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validarCampos()) {
                    guardarFloristeria();
                }
            }
        });
    }

    private void cargarProveedores() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, empresaProveedora, nombreVendedor FROM Proveedores");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idProveedor = resultSet.getInt("id");
                String empresaProveedora = resultSet.getString("empresaProveedora");
                String nombreVendedor = resultSet.getString("nombreVendedor");
                String proveedorText = idProveedor + " - " + empresaProveedora + " - " + nombreVendedor;
                comboBoxProveedor.addItem(proveedorText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validarCampos() {
        String nombre = campoNombre.getText().trim();
        String precioText = campoPrecio.getText().replace("L ", "").replace(",", "").replace("_", "");
        String proveedorText = comboBoxProveedor.getSelectedItem().toString();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre para la floristería", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (precioText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingrese un precio para la floristería", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El formato del precio es inválido", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (proveedorText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione un proveedor para la floristería", "Validación", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void guardarFloristeria() {
        String nombre = campoNombre.getText().trim();
        String precioText = campoPrecio.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precio = Double.parseDouble(precioText);
        String proveedorText = comboBoxProveedor.getSelectedItem().toString();
        int idProveedor = Integer.parseInt(proveedorText.split(" - ")[0]);

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO floristeria (nombre, precio, idProveedor) VALUES (?, ?, ?)")) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setDouble(2, precio);
            preparedStatement.setInt(3, idProveedor);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Floristería creada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Volver a la lista de floristerías
            ListaFloristeria listaFloristeria = new ListaFloristeria();
            listaFloristeria.setVisible(true);
            actual.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar la floristería", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
