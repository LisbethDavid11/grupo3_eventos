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

    public JTextField campoNombres;
    public JTextField campoApellidos;
    public JTextField campoEdad;
    public JTextField campoDireccion;
    public JButton cancelarButton;
    public JButton guardarButton;
    private JPanel panel1;
    public JFormattedTextField campoIdentidad;
    public JRadioButton femeninoRadioButton;
    public JRadioButton masculinoRadioButton;
    public JTextField campoCorreo;
    public JTextField campoTelefono;
    public JTextField campoContacto;
    public JRadioButton temporalRadioButton;
    public JRadioButton permanenteRadioButton;
    public JTextField campoNombreContacto;
    public JLabel lblID;
    public JButton actualizarButton;
    private Conexion sql;
    private Connection mysql;
    public CrearEmpleado crearEmpleado = this;
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

        //Accion del boton actualizar
        actualizarButton.addActionListener(new ActionListener() {
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



                    ActualizarDatos();
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

        //metodo Actualizar datos
        public void ActualizarDatos() throws SQLException {
            sql = new Conexion();
            mysql = sql.conectamysql();
            PreparedStatement preparedStatement = mysql.prepareStatement("UPDATE Empleados SET Identidad=?, Nombres=?, Apellidos=?, Genero=?, Edad=?, Correo=?, Telefono=?, nombreContactoDeEmergencia=?, ContactoDeEmergencia=?, Direccion=?, TipoDeEmpleado=? WHERE idEmpleados=?");
            preparedStatement.setString(1,campoIdentidad.getText());
            preparedStatement.setString(2,campoNombres.getText());
            preparedStatement.setString(3,campoApellidos.getText());
            preparedStatement.setString(4,femeninoRadioButton.isSelected()?"Femenino":"Masculino");
            preparedStatement.setString(5,campoEdad.getText());
            preparedStatement.setString(6,campoCorreo.getText());
            preparedStatement.setString(7,campoTelefono.getText());
            preparedStatement.setString(8,campoNombreContacto.getText());
            preparedStatement.setString(9,campoContacto.getText());
            preparedStatement.setString(10,campoDireccion.getText());
            preparedStatement.setString(11,temporalRadioButton.isSelected()?"Temporal":"Permanente");
            preparedStatement.setString(12,lblID.getText());
            preparedStatement.execute();
        }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
