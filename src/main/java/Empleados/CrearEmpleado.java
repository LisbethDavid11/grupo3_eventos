package Empleados;

import Objetos.Conexion;
import Objetos.Empleados;
import com.mysql.cj.util.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class CrearEmpleado extends JFrame{

    private JTextField campoNombres;
    private JTextField campoApellidos;
    private JTextField campoEdad;
    private JTextField campoDireccion;
    private JButton cancelarButton;
    private JButton guardarButton;
    private JPanel panel1;
    private JTextField campoIdentidad;
    private JRadioButton femeninoRadioButton;
    private JRadioButton masculinoRadioButton;
    private JTextField campoCorreo;
    private JTextField campoTelefono;
    private JTextField campoContacto;
    private Conexion sql;
    private Connection mysql;
    private CrearEmpleado crearEmpleado = this;
    public ButtonGroup grupogenero;

    private JTextField[] campos = {campoIdentidad, campoNombres, campoApellidos, campoEdad, campoCorreo, campoTelefono, campoContacto, campoDireccion};

    public CrearEmpleado() {
        super("Crear Empleados");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        grupogenero = new ButtonGroup();
        grupogenero.add(femeninoRadioButton);
        grupogenero.add(masculinoRadioButton);
        femeninoRadioButton.setSelected(true);


        //boton cancelar
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    VistaEmpleado vistaEmpleado = new VistaEmpleado();
                    vistaEmpleado.setVisible(true);
                    crearEmpleado.dispose();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        //boton guardar
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    for (JTextField campo : campos) {
                        if (Objects.equals( campo.getText().replaceAll("\\s+",""), "")){
                            JOptionPane.showMessageDialog(null,"Hay campos vacíos");
                            return;
                        }

                    }
                    if (Integer.parseInt(campoEdad.getText()) <18 || Integer.parseInt(campoEdad.getText()) >60){
                        JOptionPane.showMessageDialog(null,"Su edad no es válida");
                        return;
                    }
                    if (campoTelefono.getText().charAt(0) == '1' || campoTelefono.getText().charAt(0) == '4' || campoTelefono.getText().charAt(0) == '5' || campoTelefono.getText().charAt(0) == '6' || campoTelefono.getText().charAt(0) == '7' || campoTelefono.getText().charAt(0) == '0'){
                        JOptionPane.showMessageDialog(null,"Su número de teléfono no es válido");
                        return;
                    }
                    if (campoContacto.getText().charAt(0) == '1' || campoTelefono.getText().charAt(0) == '4' || campoTelefono.getText().charAt(0) == '5' || campoTelefono.getText().charAt(0) == '6' || campoTelefono.getText().charAt(0) == '7' || campoTelefono.getText().charAt(0) == '0'){
                        JOptionPane.showMessageDialog(null,"Su número de teléfono no es válido");
                        return;
                    }
                    if (!Empleados.ComprobarIdentidad(campoIdentidad.getText())){
                        JOptionPane.showMessageDialog(null,"Identidad no válida" );
                        return;
                    }


                    GuardarDatos();
                } catch (SQLException ex) {

                    throw new RuntimeException(ex);
                }
                try {
                    VistaEmpleado vistaEmpleado = new VistaEmpleado();
                    vistaEmpleado.setVisible(true);
                    crearEmpleado.dispose();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }

        });

    }

    //Consulta insertar

    public void GuardarDatos() throws SQLException {
        sql = new Conexion();
        mysql = sql.conectamysql();
        PreparedStatement preparedStatement = mysql.prepareStatement("insert into " + Empleados.nombreDeTabla  + " (Identidad, Nombres, Apellidos, Genero, Edad, Correo, Telefono, ContactoDeEmergencia, Direccion) values (?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,campoIdentidad.getText());
        preparedStatement.setString(2,campoNombres.getText());
        preparedStatement.setString(3,campoApellidos.getText());
        preparedStatement.setString(4,femeninoRadioButton.isSelected()?"F":"M");
        preparedStatement.setString(5,campoEdad.getText());
        preparedStatement.setString(6,campoCorreo.getText());
        preparedStatement.setString(7,campoTelefono.getText());
        preparedStatement.setString(8,campoContacto.getText());
        preparedStatement.setString(9,campoDireccion.getText());
       preparedStatement.execute();

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
