package Empleados;

import Objetos.Conexion;
import Objetos.Empleados;

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
import java.util.Objects;

public class CrearEmpleado extends JFrame{

    private JTextField campoNombres;
    private JTextField campoApellidos;
    private JTextField campoEdad;
    private JTextField campoDireccion;
    private JButton cancelarButton;
    private JButton guardarButton;
    private JPanel panel1;
    private JFormattedTextField campoIdentidad;
    private JRadioButton femeninoRadioButton;
    private JRadioButton masculinoRadioButton;
    private JTextField campoCorreo;
    private JTextField campoTelefono;
    private JTextField campoContacto;
    private JRadioButton temporalRadioButton;
    private JRadioButton permanenteRadioButton;
    private JTextField campoNombreContacto;
    private Conexion sql;
    private Connection mysql;
    private CrearEmpleado crearEmpleado = this;
    public ButtonGroup grupogenero;
    public ButtonGroup grupoTipo;

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

        grupoTipo = new ButtonGroup();
        grupoTipo.add(temporalRadioButton);
        grupoTipo.add(permanenteRadioButton);
        temporalRadioButton.setSelected(true);

        try {
            MaskFormatter formatoIdentidad = new MaskFormatter("####-####-#####");
            campoIdentidad.setFormatterFactory(new DefaultFormatterFactory(formatoIdentidad));
        } catch (ParseException e) {
            throw new RuntimeException(e);

        }

        campoNombres.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e,campoNombres.getText().length(),49,campoNombres.getCaretPosition());
            }
        });
        campoApellidos.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e, campoApellidos.getText().length(),49,campoApellidos.getCaretPosition());
            }
        });
        campoNombreContacto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e, campoNombreContacto.getText().length(),49,campoNombreContacto.getCaretPosition());
            }
        });

        campoTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloNumeros(e,7, campoTelefono.getText().length());

            }
        });

        campoContacto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloNumeros(e,7,campoContacto.getText().length());
                }

        });




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
                    int validacion = 0;
                    int contador = 0;
                    String mensaje = "Faltó ingresar: \n";
                    for (JTextField campo : campos) {

                        if (Objects.equals( campo.getText().replaceAll("\\s+",""), "")){
                         mensaje += Empleados.ColumnasDeTabla[contador] + "\n";
                         validacion += 1;

                        }
                        contador += 1;

                    }
                    if (validacion > 0){
                        JOptionPane.showMessageDialog(null,mensaje);
                        return;
                    }
                    if (Integer.parseInt(campoEdad.getText()) <18 || Integer.parseInt(campoEdad.getText()) >60){
                        JOptionPane.showMessageDialog(null,"Su edad no es válida");
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
                    if (campoEdad.getText().length() <2){
                        JOptionPane.showMessageDialog(null,"Su edad debe contener 2 digitos");
                        return;
                    }

                    if (campoTelefono.getText().length() <8){
                        JOptionPane.showMessageDialog(null,"Su número de teléfono debe contener 8 digitos");
                        return;
                    }
                    if (campoContacto.getText().charAt(0) == '1' || campoContacto.getText().charAt(0) == '4' || campoContacto.getText().charAt(0) == '5' || campoContacto.getText().charAt(0) == '6' || campoContacto.getText().charAt(0) == '7' || campoContacto.getText().charAt(0) == '0'){
                        JOptionPane.showMessageDialog(null,"Su número de teléfono no es válido");
                        return;
                    }
                    if (campoContacto.getText().length() <8){
                        JOptionPane.showMessageDialog(null,"Su número de teléfono debe contener 8 digitos");
                        return;
                    }
                    if (!Empleados.ComprobarIdentidad(campoIdentidad.getText())){
                        JOptionPane.showMessageDialog(null,"Identidad no válida" );
                        return;
                    }
                    if (Empleados.ValidarCorreo(campoCorreo.getText())){
                        JOptionPane.showMessageDialog(null,"Formato de correo inválido.\n ejemplo:luis@xxx.xxx");
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
        PreparedStatement preparedStatement = mysql.prepareStatement("insert into " + Empleados.nombreDeTabla  + " (Identidad, Nombres, Apellidos, Genero, Edad, Correo, Telefono, NombreContactoDeEmergencia, ContactoDeEmergencia, Direccion, TipoDeEmpleado) values (?,?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1,campoIdentidad.getText());
        preparedStatement.setString(2,campoNombres.getText());
        preparedStatement.setString(3,campoApellidos.getText());
        preparedStatement.setString(4,femeninoRadioButton.isSelected()?"F":"M");
        preparedStatement.setString(5,campoEdad.getText());
        preparedStatement.setString(6,campoCorreo.getText());
        preparedStatement.setString(7,campoTelefono.getText());
        preparedStatement.setString(8,campoNombreContacto.getText());
        preparedStatement.setString(9,campoContacto.getText());
        preparedStatement.setString(10,campoDireccion.getText());
        preparedStatement.setString(11,temporalRadioButton.isSelected()?"Temporal":"Permanente");
       preparedStatement.execute();

    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
