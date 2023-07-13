package SubMenu;

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
    private JPanel panel, panel2, panel3;

    public SubMenu() {
        super("Menú Principal");
        setSize(700, 400);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(1, 6)); // Cambia a una sola fila

        // Establecer colores Material Design a los botones
        clientesButton = new JButton("Clientes");
        clientesButton.setBackground(Color.decode("#F44336"));
        empleadosButton = new JButton("Empleados");
        empleadosButton.setBackground(Color.decode("#9C27B0"));
        floristeriaButton = new JButton("Floristeria");
        floristeriaButton.setBackground(Color.decode("#3F51B5"));
        arreglosButton = new JButton("Arreglos");
        arreglosButton.setBackground(Color.decode("#4CAF50"));
        materialesButton = new JButton("Materiales");
        materialesButton.setBackground(Color.decode("#FFC107"));
        proveedoresButton = new JButton("Proveedores");
        proveedoresButton.setBackground(Color.decode("#795548"));

        panel.add(clientesButton);
        panel.add(empleadosButton);
        panel.add(floristeriaButton);
        panel.add(arreglosButton);
        panel.add(materialesButton);
        panel.add(proveedoresButton);

        // Crea el panel de imagen y lo coloca en el centro
        ImagePanel imagenPanel = new ImagePanel();
        panel2 = new JPanel(new BorderLayout());
        panel2.add(imagenPanel, BorderLayout.CENTER);

        // Crea un panel para contener los botones y el panel de imagen
        panel3 = new JPanel(new BorderLayout());
        panel3.add(panel, BorderLayout.NORTH);
        panel3.add(panel2, BorderLayout.CENTER);

        clientesButton.setFocusPainted(false);
        proveedoresButton.setFocusPainted(false);
        materialesButton.setFocusPainted(false);
        floristeriaButton.setFocusPainted(false);
        empleadosButton.setFocusPainted(false);
        arreglosButton.setFocusPainted(false);

        clientesButton.setForeground(Color.WHITE);
        proveedoresButton.setForeground(Color.WHITE);
        materialesButton.setForeground(Color.WHITE);
        floristeriaButton.setForeground(Color.WHITE);
        empleadosButton.setForeground(Color.WHITE);
        arreglosButton.setForeground(Color.WHITE);

        add(panel3);

        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaCliente cliente = new ListaCliente();
                cliente.setVisible(true);
            }
        });

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SubMenu subMenu = new SubMenu();
                subMenu.setVisible(true);
            }
        });
    }
}
