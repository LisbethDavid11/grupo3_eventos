package Clientes;

import Objetos.Cliente;
import Objetos.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerCliente extends JFrame {
    private JPanel panel1;
    private JTextField etiquetaNombre;
    private JTextField etiquetaApellido;
    private JTextField etiquetaTelefono;
    private JTextField etiquetaIdentidad;
    private JTextArea etiquetaDomicilio;
    private JTextField etiquetaTipoCliente;
    private JButton volverButton;

    private final VerCliente actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;

    public VerCliente(int id) {
        super("Ver Registro de los Clientes");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();

        etiquetaDomicilio.setLineWrap(true);
        etiquetaDomicilio.setWrapStyleWord(true);

        // Deshabilitar la edición de los JTextField y el JTextArea
        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaApellido.setEditable(false);
        etiquetaApellido.setFocusable(false);
        etiquetaTelefono.setEditable(false);
        etiquetaTelefono.setFocusable(false);
        etiquetaIdentidad.setEditable(false);
        etiquetaIdentidad.setFocusable(false);
        etiquetaDomicilio.setEditable(false);
        etiquetaDomicilio.setFocusable(false);
        etiquetaTipoCliente.setEditable(false);
        etiquetaTipoCliente.setFocusable(false);

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaCliente indexCliente = new ListaCliente();
                indexCliente.setVisible(true);
                actual.dispose();
            }
        });
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM " + Cliente.nombreTabla + " WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();
            etiquetaNombre.setText(resultSet.getString(2));
            etiquetaApellido.setText(resultSet.getString(3));
            etiquetaIdentidad.setText(resultSet.getString(4));
            etiquetaTelefono.setText(resultSet.getString(5));
            etiquetaDomicilio.setText(resultSet.getString(6));
            etiquetaTipoCliente.setText(resultSet.getString(7));

        } catch (SQLException error) {
            // Mensaje de error
            System.out.println(error.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VerCliente verCliente = new VerCliente(1); // Pasa el ID del cliente que deseas ver
                verCliente.setVisible(true);
            }
        });
    }
}
