package Empleados;

import Objetos.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerEmpleado extends JFrame{
    public JLabel campoIdentidad;
    public JLabel campoNombres;
    public JLabel campoApellidos;
    public JLabel campoGenero;
    public JLabel campoEdad;
    public JLabel campoCorreo;
    public JLabel campoTelefono;
    public JLabel campoNombreContacto;
    public JLabel campoContacto;
    public JLabel campoDireccion;
    public JLabel campoTipo;
    public JLabel lblID;
    public JButton cancelarButton;
    public JPanel panel1;
    public JLabel lblnomConcat;
    public JLabel lblapeConcat;
    public VerEmpleado mostrarEmpleado = this;

    public VerEmpleado()  {
        super("Ver empleado");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    VistaEmpleado vistaEmpleado = new VistaEmpleado();
                    vistaEmpleado.setVisible(true);
                    mostrarEmpleado.dispose();

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

}//class
