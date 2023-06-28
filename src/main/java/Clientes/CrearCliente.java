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
                int contador = 0;
                String mensaje = "Hay campos vacios: \n";
                for (JTextField campo: campos) {
                    if (campo.getText().replaceAll("\\s+","").replaceAll("[^\\dA-Za-z]","").equals("")){
                        validar+=1;
                        mensaje+=Cliente.columnasCampos[contador]+"\n";
                    }
                    contador+=1;
                }

                if (validar > 0){
                    JOptionPane.showMessageDialog(null,mensaje);
                    return;
                }
                if (campoDomicilio.getText().replaceAll("\\s+","").replaceAll("[^\\dA-Za-z]","").equals("")){
                    JOptionPane.showMessageDialog(null,"El Domicilio no pude estar vacio");
                    return;
                }

                if (campoDomicilio.getText().length() > 200){
                    JOptionPane.showMessageDialog(null,"El Domicilio no puede excer un maximo de 200 caracteres");
                    return;
                }

                String telefono = campoTelefono.getText();
                if (telefono.charAt(0) == '0' || telefono.charAt(0) == '1' || telefono.charAt(0) == '4' || telefono.charAt(0) == '5' || telefono.charAt(0) == '6' || telefono.charAt(0) == '7'){
                    JOptionPane.showMessageDialog(null,"Formato de teléfono invalido");
                    return;
                }
                if (telefono.length() > 8 || telefono.length() < 8){
                    JOptionPane.showMessageDialog(null,"Formato de teléfono solo admite 8 digitos");
                    return;
                }


                guardar();


            }
        });
    }

    public void guardar(){
        sql = new Conexion();
        mysql = sql.conectamysql();
        try {
            PreparedStatement preparedStatement = mysql.prepareStatement("INSERT INTO "+ Cliente.nombreTabla+"(`nombre`, `apellido`, `identidad`, `telefono`, `domicilio`, `tipo_cliente`) VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1,campoNombre.getText());
            preparedStatement.setString(2,campoApellido.getText());
            preparedStatement.setString(3,campoIdentidad.getText());
            preparedStatement.setString(4,campoTelefono.getText());
            preparedStatement.setString(5,campoDomicilio.getText());
            preparedStatement.setString(6,radioAldetalle.isSelected()?"Al Detalle":"Mayorista");

            preparedStatement.executeUpdate();

            mysql.close();


            ListaCliente cliente = new ListaCliente();
            cliente.setVisible(true);
            actual.dispose();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null,"No hay conexion");
        }
    }
}
