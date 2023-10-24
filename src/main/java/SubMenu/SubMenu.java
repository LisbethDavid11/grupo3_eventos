package SubMenu;

import Actividades.CalendarioDeActividades;
import Actividades.ListaActividades;
import Alquileres.ListaAlquileres;
import Arreglos.ListaArreglos;
import Clientes.ListaClientes;
import Compras.ListaCompras;
import Desayunos.ListaDesayunos;
import Empleados.ListaEmpleados;
import Eventos.ListaEventos;
import Floristerias.ListaFloristerias;
import Globos.ListaGlobos;
import Manualidades.ListaManualidades;
import Materiales.ListaMateriales;
import Mobiliario.ListaMobiliario;
import Pedidos.ListaPedidos;
import Promociones.ListaPromociones;
import Proveedores.ListaProveedores;
import Tarjetas.ListaTarjetas;
import Ventas.ListaVentas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SubMenu extends JFrame {
    private JButton proveedoresButton, empleadosButton, clientesButton, floristeriaButton, arreglosButton, materialesButton, comprasButton,tarjetaButton, manualidadesButton, globosButton, desayunosButton, ventasButton, mobiliarioButton, pedidosButton, eventosButton, promocionesButton, actividadesButton, alquileresButton;
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
    private ListaVentas listaVentas;
    private ListaMobiliario listaMobiliario;
    private ListaPedidos listaPedidos;
    private ListaPromociones listaPromociones;
    private ListaEventos listaEventos;
    private CalendarioDeActividades listaActividades;
    private ListaAlquileres listaAlquileres;

    public SubMenu() {
        super("Menú Principal");
        setSize(1100, 620);
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
        comprasButton.setBackground(Color.decode("#E91E63")); // Cambio de color
        comprasButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        tarjetaButton = new JButton("Tarjetas");
        tarjetaButton.setBackground(Color.decode("#E81E12"));
        tarjetaButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        manualidadesButton = new JButton("Manualidades");
        manualidadesButton.setBackground(Color.decode("#2196F3")); // Cambio de color
        manualidadesButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        globosButton = new JButton("Globos");
        globosButton.setBackground(Color.decode("#00BCD4")); // Cambio de color
        globosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        desayunosButton = new JButton("Desayunos");
        desayunosButton.setBackground(Color.decode("#2196F3")); // Cambio de color
        desayunosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        ventasButton = new JButton("Ventas");
        ventasButton.setBackground(Color.decode("#8BC34A")); // Cambio de color
        ventasButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        mobiliarioButton = new JButton("Mobiliario");
        mobiliarioButton.setBackground(Color.decode("#FF9800"));
        mobiliarioButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        pedidosButton = new JButton("Pedidos");
        pedidosButton.setBackground(Color.decode("#607D8B"));
        pedidosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        promocionesButton = new JButton("Promociones");
        promocionesButton.setBackground(Color.decode("#673AB7"));
        promocionesButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        eventosButton = new JButton("Eventos");
        eventosButton.setBackground(Color.decode("#CDDC39"));
        eventosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        actividadesButton = new JButton("Actividades");
        actividadesButton.setBackground(Color.decode("#FF5722"));
        actividadesButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        alquileresButton = new JButton("Alquileres");
        alquileresButton.setBackground(Color.decode("#e68b84"));
        alquileresButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        // Crear un GridLayout con 2 filas y 8 columnas
        GridLayout gridLayout = new GridLayout(2, 8);

        // Establecer el diseño del panel
        panel.setLayout(gridLayout);

        // Agregar los botones al panel
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
        panel.add(pedidosButton);
        panel.add(ventasButton);
        panel.add(mobiliarioButton);
        panel.add(promocionesButton);
        panel.add(eventosButton);
        panel.add(actividadesButton);
        panel.add(alquileresButton);

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
        ventasButton.setFocusPainted(false);
        mobiliarioButton.setFocusPainted(false);
        pedidosButton.setFocusPainted(false);
        promocionesButton.setFocusPainted(false);
        eventosButton.setFocusPainted(false);
        actividadesButton.setFocusPainted(false);
        alquileresButton.setFocusPainted(false);

        promocionesButton.setForeground(Color.WHITE);
        eventosButton.setForeground(Color.WHITE);
        materialesButton.setForeground(Color.WHITE);
        floristeriaButton.setForeground(Color.WHITE);
        empleadosButton.setForeground(Color.WHITE);
        arreglosButton.setForeground(Color.WHITE);
        comprasButton.setForeground(Color.WHITE);
        tarjetaButton.setForeground(Color.WHITE);
        manualidadesButton.setForeground(Color.WHITE);
        globosButton.setForeground(Color.WHITE);
        desayunosButton.setForeground(Color.WHITE);
        ventasButton.setForeground(Color.WHITE);
        mobiliarioButton.setForeground(Color.WHITE);
        pedidosButton.setForeground(Color.WHITE);
        actividadesButton.setForeground(Color.WHITE);
        clientesButton.setForeground(Color.WHITE);
        proveedoresButton.setForeground(Color.WHITE);
        alquileresButton.setForeground(Color.WHITE);
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
        listaVentas = new ListaVentas();
        listaMobiliario = new ListaMobiliario();
        listaPedidos = new ListaPedidos();
        listaPromociones = new ListaPromociones();
        listaEventos = new ListaEventos();
        listaActividades = new CalendarioDeActividades();
        listaAlquileres = new ListaAlquileres();

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

        ventasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaVentas.setVisible(true);
            }
        });

        mobiliarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaMobiliario.setVisible(true);
            }
        });

        pedidosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaPedidos.setVisible(true);
            }
        });

        eventosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaEventos.setVisible(true);
            }
        });

        promocionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaPromociones.setVisible(true);
            }
        });

        actividadesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaActividades.setVisible(true);
            }
        });

        alquileresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { listaAlquileres.setVisible(true);

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
