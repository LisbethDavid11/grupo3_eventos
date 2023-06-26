import Clientes.ListaCliente;
import Empleados.VistaEmpleado;
import Proveedores.IndexProveedores;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SubMenu extends JFrame {

    private JButton proveedoresButton;
    private JButton empleadosButton;
    private JButton clientesButton;
    private JPanel panel;

    public SubMenu() {
        super("Botones");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);

        //Ebento para que abra la vista
        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaCliente cliente = new ListaCliente();
                cliente.setVisible(true);
            }
        });

        proveedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexProveedores proveedores = new IndexProveedores();
                proveedores.setVisible(true);
            }
        });

        empleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VistaEmpleado empleado = null;
                try {
                    empleado = new VistaEmpleado();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                empleado.setVisible(true);
            }
        });

    }
}
