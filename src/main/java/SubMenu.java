import Arreglos.ListaArreglo;
import Clientes.ListaCliente;
import Empleados.ListaEmpleados;
import Floristerias.ListaFloristeria;
import Materiales.ListaMateriales;
import Proveedores.ListaProveedores;

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
    private JButton materialesButton;
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
        floristeriaButton = new JButton("FLORISTERIA");
        arreglosButton = new JButton("ARREGLOS");
        materialesButton = new JButton("MATERIALES");
        proveedoresButton = new JButton("PROVEEDORES");

        // Configurar tamaño y estilo de los botones
        Dimension buttonSizeSmall = new Dimension(100, 30);
        clientesButton.setPreferredSize(buttonSizeSmall);
        empleadosButton.setPreferredSize(buttonSizeSmall);
        floristeriaButton.setPreferredSize(buttonSizeSmall);
        arreglosButton.setPreferredSize(buttonSizeSmall);
        materialesButton.setPreferredSize(buttonSizeSmall);
        proveedoresButton.setPreferredSize(buttonSizeSmall);

        // Configurar estilo de los paneles
        panel2.setBackground(Color.DARK_GRAY);
        panel3.setBackground(Color.DARK_GRAY);

        // Agregar los botones al panel2
        panel2.add(clientesButton);
        panel2.add(empleadosButton);
        panel2.add(floristeriaButton);
        panel2.add(arreglosButton);
        panel2.add(materialesButton);
        panel2.add(proveedoresButton);

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

        materialesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaMateriales materiales = new ListaMateriales();
                materiales.setVisible(true);
            }
        });

        proveedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaProveedores proveedores = new ListaProveedores();
                proveedores.setVisible(true);
            }
        });

    }
}
