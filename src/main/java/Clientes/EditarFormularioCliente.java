package Clientes;


import Objetos.Cliente;
import Objetos.Conexion;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EditarFormularioCliente extends  JFrame{

    private JPanel panel1;
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoTelefono;
    private JFormattedTextField campoIdentidad;
    private JTextArea campoDomicilio;
    public ButtonGroup grupoTipo_cliente;
    private JRadioButton mayoristaRadioButton;
    private JRadioButton alDetalleRadioButton;
    private JButton cancelarButton;
    private JButton guardarButton;

    private final EditarFormularioCliente actual = this;
    private Conexion sql;
    private Connection mysql;
    private int id;
    private JTextField[] campos = {campoNombre, campoApellido, campoIdentidad, campoTelefono};



    public EditarFormularioCliente(int id) {
        super("Editar Registro de los Clientes");
        setSize(500,500);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();


        grupoTipo_cliente = new ButtonGroup();
        grupoTipo_cliente.add(mayoristaRadioButton);
        grupoTipo_cliente.add(alDetalleRadioButton);
        mayoristaRadioButton.setSelected(true);


        campoNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e, campoNombre.getText().length(),49, campoNombre.getCaretPosition());
            }
        });
        campoApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e, campoApellido.getText().length(),49, campoApellido.getCaretPosition());
            }
        });


        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaCliente indexCliente = new ListaCliente();
                indexCliente.setVisible(true);
                actual.dispose();

            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int validacion = 0;
                    int contador = 0;
                    String mensaje = "Hay campos vacios: \n";
                    for (JTextField campo : campos) {

                        if (!Objects.equals(campo.getText().replaceAll("\\s+", ""), "")) {
                        } else {
                            mensaje += Cliente.columnasCampos[contador] + "\n";
                            validacion += 1;

                        }
                        contador += 1;

                    }
                    if (validacion > 0){
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

                    if (campoTelefono.getText().charAt(0) == '1' || campoTelefono.getText().charAt(0) == '4' || campoTelefono.getText().charAt(0) == '5' || campoTelefono.getText().charAt(0) == '6' || campoTelefono.getText().charAt(0) == '7' || campoTelefono.getText().charAt(0) == '0'){
                        JOptionPane.showMessageDialog(null,"Su número de teléfono no es válido");
                        return;
                    }
                    if (campoIdentidad.getText().length() <15){
                        JOptionPane.showMessageDialog(null,"Su número de identidad debe contener 15 digitos incluyendo guiones");
                        return;
                    }
                    if (campoTelefono.getText().length() <8){
                        JOptionPane.showMessageDialog(null,"Su número de teléfono debe contener 8 digitos");
                        return;
                    }

                    guardar();
                } catch (SQLException ex) {

                    throw new RuntimeException(ex);
                }
                ListaCliente indexCliente = new ListaCliente();
                indexCliente.setVisible(true);
                actual.dispose();

            }
        });
    }
    private void   mostrar(){
        sql = new Conexion();
        mysql = sql.conectamysql();

        try{
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM "+Cliente.nombreTabla+" where id = ?;");
            statement.setInt(1, this.id);
            ResultSet resulset = statement.executeQuery();

            resulset.next();
           campoNombre.setText(resulset.getString(2));
           campoApellido.setText(resulset.getString(3));
           campoIdentidad.setText(resulset.getString(4));
           campoTelefono.setText(resulset.getString(5));
           campoDomicilio.setText(resulset.getString(6));
           mayoristaRadioButton.setText(resulset.getString(7));
           System.out.println(statement.execute());

        }catch (SQLException error){
            //mensaje de error
            System.out.println(error.getMessage());
        }
}
    private void  guardar() throws SQLException {
        sql = new Conexion();
        mysql = sql.conectamysql();

            PreparedStatement statement = mysql.prepareStatement("UPDATE "+Cliente.nombreTabla+" SET `nombre` = ? , `apellido` = ?, `identidad` = ? , `telefono` = ? , `domicilio` = ? , `tipo_cliente` = ? WHERE id = ?");
            statement.setString(1,campoNombre.getText());
            statement.setString(2, campoApellido.getText());
            statement.setString(3, campoIdentidad.getText());
            statement.setString(4, campoTelefono.getText());
            statement.setString(5, campoDomicilio.getText());
            statement.setString(6, mayoristaRadioButton.isSelected()?"Mayorista":"Al detalle");
            statement.setInt(7, this.id);
            System.out.println(statement.execute());


        }
        private void createUIComponents() {
            // TODO: place custom component creation code here
        }
    }