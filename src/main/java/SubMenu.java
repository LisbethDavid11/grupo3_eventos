import Clientes.ListaCliente;
import Empleados.ListaEmpleados;
import Proveedores.IndexProveedores;
import Floristeria.ListaFloristeria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class SubMenu extends JFrame {

    private JButton proveedoresButton;
    private JButton empleadosButton;
    private JButton clientesButton;
    private JButton floristeriaButton;
    private JPanel panel;

    public SubMenu() {
        super("Botones");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);

        proveedoresButton = new JButton("Proveedores");
        empleadosButton = new JButton("Empleados");
        clientesButton = new JButton("Clientes");
        floristeriaButton = new JButton("Floristeria");

        // Evento para abrir la vista de clientes
        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaCliente cliente = new ListaCliente();
                cliente.setVisible(true);
            }
        });

        // Evento para abrir la vista de empleados
        empleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaEmpleados listaEmpleados = new ListaEmpleados();
                listaEmpleados.setVisible(true);
            }
        });

        floristeriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaFloristeria floristeria = new ListaFloristeria();
                floristeria.setVisible(true);
            }
        });

        // Evento para abrir la vista de proveedores
        proveedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexProveedores proveedores = new IndexProveedores();
                proveedores.setVisible(true);
            }
        });


        panel.setLayout(new GridLayout(5, 1)); // Establece el diseño de cuadrícula de 5 filas y 1 columna

        panel.add(proveedoresButton);
        panel.add(empleadosButton);
        panel.add(clientesButton);
        panel.add(floristeriaButton);
    }
}
