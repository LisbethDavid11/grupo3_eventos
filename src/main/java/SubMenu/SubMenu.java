package SubMenu;

import Arreglos.ListaArreglos;
import Clientes.ListaClientes;
import Compras.ListaCompras;
import Desayunos.ListaDesayunos;
import Empleados.ListaEmpleados;
import Floristerias.ListaFloristerias;
import Globos.ListaGlobos;
import Manualidades.ListaManualidades;
import Materiales.ListaMateriales;
import Proveedores.ListaProveedores;
import Tarjetas.ListaTarjetas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubMenu extends JFrame {
    private JButton proveedoresButton, empleadosButton, clientesButton, floristeriaButton, arreglosButton, materialesButton, comprasButton,tarjetaButton, manualidadesButton, globosButton, desayunosButton;
    private JPanel panel, panel2, panel3;
    private ListaClientes listaCliente;
    private ListaEmpleados listaEmpleados;
    private ListaFloristerias listaFloristeria;
    private ListaArreglos listaArreglo;
    private ListaMateriales listaMateriales;
    private ListaProveedores listaProveedores;
    private ListaCompras listaCompras;
    private ListaTarjetas listaTarjetas;
    private ListaManualidades listaManualidades;
    private ListaGlobos listaGlobos;
    private ListaDesayunos listaDesayunos;

    public SubMenu() {
        super("Menú Principal");
        setSize(980, 620);
        setLocationRelativeTo(null);

        panel = new JPanel(new GridLayout(1, 1)); // Cambia a una sola fila

        // Establecer colores Material Design a los botones
        clientesButton = new JButton("Clientes");
        clientesButton.setBackground(Color.decode("#F44336"));
        clientesButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        empleadosButton = new JButton("Empleados");
        empleadosButton.setBackground(Color.decode("#9C27B0"));
        empleadosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        floristeriaButton = new JButton("Floristeria");
        floristeriaButton.setBackground(Color.decode("#3F51B5"));
        floristeriaButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        arreglosButton = new JButton("Arreglos");
        arreglosButton.setBackground(Color.decode("#4CAF50"));
        arreglosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        materialesButton = new JButton("Materiales");
        materialesButton.setBackground(Color.decode("#FFC107"));
        materialesButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        proveedoresButton = new JButton("Proveedores");
        proveedoresButton.setBackground(Color.decode("#795548"));
        proveedoresButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        comprasButton = new JButton("Compras");
        comprasButton.setBackground(Color.decode("#F44336"));
        comprasButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        tarjetaButton = new JButton("Tarjetas");
        tarjetaButton.setBackground(Color.decode("#E81E63"));
        tarjetaButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        manualidadesButton = new JButton("Manualidades");
        manualidadesButton.setBackground(Color.decode("#E81E12"));
        manualidadesButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        globosButton = new JButton("Globos");
        globosButton.setBackground(Color.decode("#FFC107"));
        globosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        desayunosButton = new JButton("Desayunos");
        desayunosButton.setBackground(Color.decode("#F44336"));
        desayunosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        panel.add(clientesButton);
        panel.add(empleadosButton);
        panel.add(floristeriaButton);
        panel.add(arreglosButton);
        panel.add(materialesButton);
        panel.add(proveedoresButton);
        panel.add(comprasButton);
        panel.add(tarjetaButton);
        panel.add(manualidadesButton);
        panel.add(globosButton);
        panel.add(desayunosButton);

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
        comprasButton.setFocusPainted(false);
        tarjetaButton.setFocusPainted(false);
        manualidadesButton.setFocusPainted(false);
        globosButton.setFocusPainted(false);
        desayunosButton.setFocusPainted(false);

        clientesButton.setForeground(Color.WHITE);
        proveedoresButton.setForeground(Color.WHITE);
        materialesButton.setForeground(Color.WHITE);
        floristeriaButton.setForeground(Color.WHITE);
        empleadosButton.setForeground(Color.WHITE);
        arreglosButton.setForeground(Color.WHITE);
        comprasButton.setForeground(Color.WHITE);
        tarjetaButton.setForeground(Color.WHITE);
        manualidadesButton.setForeground(Color.WHITE);
        globosButton.setForeground(Color.WHITE);
        desayunosButton.setForeground(Color.WHITE);

        add(panel3);

        // Crear instancias de las ventanas
        listaCliente = new ListaClientes();
        listaEmpleados = new ListaEmpleados();
        listaFloristeria = new ListaFloristerias();
        listaArreglo = new ListaArreglos();
        listaMateriales = new ListaMateriales();
        listaProveedores = new ListaProveedores();
        listaCompras = new ListaCompras();
        listaTarjetas = new ListaTarjetas();
        listaManualidades = new ListaManualidades();
        listaGlobos = new ListaGlobos();
        listaDesayunos = new ListaDesayunos();

        clientesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaCliente.setVisible(true);
            }
        });

        empleadosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaEmpleados.setVisible(true);
            }
        });

        floristeriaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaFloristeria.setVisible(true);
            }
        });

        arreglosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaArreglo.setVisible(true);
            }
        });

        materialesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaMateriales.setVisible(true);
            }
        });

        proveedoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaProveedores.setVisible(true);
            }
        });

        comprasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaCompras.setVisible(true);
            }
        });

        tarjetaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaTarjetas.setVisible(true);
            }
        });

        manualidadesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaManualidades.setVisible(true);
            }
        });

        globosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaGlobos.setVisible(true);
            }
        });

        desayunosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaDesayunos.setVisible(true);
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
