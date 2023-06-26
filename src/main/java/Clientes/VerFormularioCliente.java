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

public class VerFormularioCliente extends  JFrame{

    private JPanel panel1;
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JTextField campoTelefono;
    private JFormattedTextField campoIdentidad;
    private JTextField campoDomicilio;
    public ButtonGroup grupoTipo_cliente;
    private JRadioButton mayoristaRadioButton;
    private JRadioButton alDetalleRadioButton;
    private JButton volverButton;

    private final VerFormularioCliente actual = this;
    private Conexion sql;

    private Connection mysql;

    private int id;
    private JTextField[] campos = {campoNombre, campoApellido, campoIdentidad, campoTelefono, campoDomicilio};

    public VerFormularioCliente(int id) {
        super("Ver Registro de los Clientes");
        setSize(500,500);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        grupoTipo_cliente = new ButtonGroup();
        grupoTipo_cliente.add(mayoristaRadioButton);
        grupoTipo_cliente.add(alDetalleRadioButton);
        mayoristaRadioButton.setSelected(true);

        this.id= id;
        mostrar();
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM "+ Cliente.nombreTabla+" where id = ?;");
            statement.setInt(1, this.id);
            ResultSet resulset = statement.executeQuery();

            resulset.next();
           campoNombre.setText(resulset.getString(2));
           campoApellido.setText(resulset.getString(3));
           campoIdentidad.setText(resulset.getString(4));
           campoTelefono.setText(resulset.getString(5));
           campoDomicilio.setText(resulset.getString(6));
           mayoristaRadioButton.setText(resulset.getString(7));

        }catch (SQLException error){
            //mensaje de error
            System.out.println(error.getMessage());
        }

}

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}