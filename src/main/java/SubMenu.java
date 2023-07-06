import Arreglos.ListaArreglo;
import Clientes.ListaCliente;
import Empleados.ListaEmpleados;
import Floristeria.ListaFloristeria;
import Proveedores.IndexProveedores;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubMenu extends JFrame {

    private JButton proveedoresButton;
    private JButton empleadosButton;
    private JButton clientesButton;
    private JButton floristeriaButton;
    private JButton arreglosButton;
    private JButton otroButton;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;

    public SubMenu() {
        super("Botones");
        setSize(800, 400);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(2, 1));
        panel2 = new JPanel(new GridLayout(1, 6));
        panel3 = new JPanel(new GridLayout(1, 2));

        clientesButton = new JButton("CLIENTES");
        empleadosButton = new JButton("EMPLEADOS");
        proveedoresButton = new JButton("PROVEEDORES");
        floristeriaButton = new JButton("FLORISTERIA");
        arreglosButton = new JButton("ARREGLOS");
        otroButton = new JButton("OTRO");

        // Configurar tamaño y estilo de los botones
        Dimension buttonSizeSmall = new Dimension(100, 30);
        clientesButton.setPreferredSize(buttonSizeSmall);
        empleadosButton.setPreferredSize(buttonSizeSmall);
        proveedoresButton.setPreferredSize(buttonSizeSmall);
        floristeriaButton.setPreferredSize(buttonSizeSmall);
        arreglosButton.setPreferredSize(buttonSizeSmall);
        otroButton.setPreferredSize(buttonSizeSmall);

        // Configurar estilo de los paneles
        panel2.setBackground(Color.DARK_GRAY);
        panel3.setBackground(Color.DARK_GRAY);

        // Agregar los botones al panel2
        panel2.add(clientesButton);
        panel2.add(empleadosButton);
        panel2.add(proveedoresButton);
        panel2.add(floristeriaButton);
        panel2.add(arreglosButton);
        panel2.add(otroButton);

        panel.add(panel2);
        panel.add(panel3);

        setContentPane(panel);

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

        proveedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexProveedores proveedores = new IndexProveedores();
                proveedores.setVisible(true);
            }
        });

        floristeriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaFloristeria floristeria = new ListaFloristeria();
                floristeria.setVisible(true);
            }
        });

        arreglosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaArreglo arreglo = new ListaArreglo();
                arreglo.setVisible(true);
            }
        });

    }
}
