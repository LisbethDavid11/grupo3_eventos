package Empleados;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VerEmpleado extends JFrame {
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
    private int empleadoId; // Variable de instancia para almacenar el ID del empleado

    public VerEmpleado(int empleadoId) {
        super("");
        this.empleadoId = empleadoId; // Asignar el ID del empleado a la variable de instancia
        setSize(600, 600);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaEmpleados listaEmpleados = new ListaEmpleados();
                listaEmpleados.setVisible(true);
                mostrarEmpleado.dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VerEmpleado verEmpleado = new VerEmpleado(1); // Pasa el ID del cliente que deseas ver
                verEmpleado.setVisible(true);
            }
        });
    }
}