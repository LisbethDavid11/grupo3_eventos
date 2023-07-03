package Clientes;

import Objetos.Cliente;
import Objetos.Conexion;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;

public class CrearCliente extends JFrame{
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JFormattedTextField campoIdentidad;
    private JTextField campoTelefono;
    private JTextArea campoDomicilio;
    private JTextField[] campos = {
            campoNombre,
            campoApellido,
            campoIdentidad,
            campoTelefono,
    };
    private JRadioButton radioMayorista;
    private JRadioButton radioAldetalle;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JPanel panel;
    private CrearCliente actual = this;
    private Connection mysql;
    private Conexion sql;

    public CrearCliente() {
        super("Crear Clientes");
        setSize(500,500);
        setLocationRelativeTo(null);
        setContentPane(panel);

        campoDomicilio.setLineWrap(true);
        campoDomicilio.setWrapStyleWord(true);

        campoNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e,campoNombre.getText().length(),50,campoNombre.getCaretPosition());
            }
        });

        campoApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e,campoApellido.getText().length(),50,campoApellido.getCaretPosition());
            }
        });

        campoDomicilio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e, campoDomicilio.getText().length(),200, campoDomicilio.getCaretPosition());
            }
        });

        campoTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloNumeros(e,7, campoTelefono.getText().length());
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioAldetalle);
        buttonGroup.add(radioMayorista);

        radioAldetalle.setSelected(true);

        try {
            MaskFormatter dni = new MaskFormatter("####-####-#####");
            campoIdentidad.setFormatterFactory(new DefaultFormatterFactory(dni));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaCliente cliente = new ListaCliente();
                cliente.setVisible(true);
                actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validar = 0;
                String mensaje = "Hay campos vacíos: \n";

                for (int contador = 0; contador < campos.length; contador++) {
                    JTextField campo = campos[contador];
                    String textoCampo = campo.getText().replaceAll("\\s+", "").replaceAll("[^\\dA-Za-z]", "");

                    if (textoCampo.equals("") && !Cliente.columnasCampos[contador].equalsIgnoreCase("#") && !Cliente.columnasCampos[contador].equalsIgnoreCase("id")) {
                        validar++;
                        mensaje += Cliente.columnasCampos[contador] + "\n";
                    }
                }

                if (validar > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (campoDomicilio.getText().replaceAll("\\s+", "").replaceAll("[^\\dA-Za-z]", "").equals("")) {
                    JOptionPane.showMessageDialog(null, "El Domicilio no puede estar vacío", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (campoDomicilio.getText().length() > 200) {
                    JOptionPane.showMessageDialog(null, "El Domicilio no puede exceder un máximo de 200 caracteres", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String telefono = campoTelefono.getText();
                if (telefono.charAt(0) == '0' || telefono.charAt(0) == '1' || telefono.charAt(0) == '4' || telefono.charAt(0) == '5' || telefono.charAt(0) == '6' || telefono.charAt(0) == '7') {
                    JOptionPane.showMessageDialog(null, "Formato de teléfono inválido", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (telefono.length() != 8) {
                    JOptionPane.showMessageDialog(null, "El formato de teléfono solo admite 8 dígitos", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Cliente.comprobarIdentidad(campoIdentidad.getText())) {
                    JOptionPane.showMessageDialog(null, "Identidad no válida", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                guardar();
            }
        });


    }

    public void guardar(){
        sql = new Conexion();
        mysql = sql.conectamysql();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + Cliente.nombreTabla + "(`nombre`, `apellido`, `identidad`, `telefono`, `domicilio`, `tipo_cliente`) VALUES(?,?,?,?,?,?)")) {

            preparedStatement.setString(1, campoNombre.getText());
            preparedStatement.setString(2, campoApellido.getText());
            preparedStatement.setString(3, campoIdentidad.getText());
            preparedStatement.setString(4, campoTelefono.getText());
            preparedStatement.setString(5, campoDomicilio.getText());
            preparedStatement.setString(6, radioAldetalle.isSelected() ? "Al Detalle" : "Mayorista");

            preparedStatement.executeUpdate();

            // No es necesario cerrar explícitamente la conexión, ya que se cerrará automáticamente al finalizar el bloque try-with-resources
            ListaCliente cliente = new ListaCliente();
            cliente.setVisible(true);
            actual.dispose();

            // Mensaje personalizado
            System.out.println("Cliente " + campoNombre.getText() + " " + campoApellido.getText() + " agregado exitosamente.");

            // Mostrar mensaje de éxito
            String nombreCompleto = campoNombre.getText() + " " + campoApellido.getText();
            JOptionPane.showMessageDialog(null, "Cliente " + nombreCompleto + " ha sido registrado exitosamente.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }
    }
}
