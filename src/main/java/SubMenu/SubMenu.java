package SubMenu;

import Actividades.CalendarioDeActividades;
import Alquileres.ListaAlquileres;
import Arreglos.ListaArreglos;
import Clientes.ListaClientes;
import Compras.ListaCompras;
import Desayunos.ListaDesayunos;
import Empleados.ListaEmpleados;
import Eventos.ListaEventos;
import Floristerias.ListaFloristerias;
import Globos.ListaGlobos;
import Login.Login;
import Login.ListaUsuarios;
import Login.SesionUsuario;
import Login.VerPerfil;
import Manualidades.ListaManualidades;
import Materiales.ListaMateriales;
import Mobiliario.ListaMobiliario;
import Objetos.Rol;
import Pedidos.ListaPedidos;
import Permisos.ListaPermisos;
import Promociones.ListaPromociones;
import Proveedores.ListaProveedores;
import Roles.ListaRoles;
import Tarjetas.ListaTarjetas;
import Ventas.ListaVentas;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SubMenu extends JFrame {
    private JButton rolesButton,usuariosButton, proveedoresButton, empleadosButton, clientesButton, floristeriaButton, arreglosButton, materialesButton, comprasButton,tarjetaButton, manualidadesButton, globosButton, desayunosButton, ventasButton, mobiliarioButton, pedidosButton, eventosButton, promocionesButton, actividadesButton, alquileresButton, permisosButton;
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
    private ListaRoles listaRoles;
    private ListaUsuarios listaUsuarios;
    private ListaPermisos listaPermisos;
    private JPanel navbarPanel;
    private JLabel userLabel, userNameLabel;
    private JPopupMenu userMenu;
    private String nombre;
    private String imagen;
    private int id;
    private int usuarioActualId;

    public SubMenu(Rol permisos) {
        super("Menú Principal");
        setSize(1100, 640);
        setLocationRelativeTo(null);
        // Inicialización y configuración del Navbar
        setupNavbar();
        // Añadir el navbar al JFrame
        add(navbarPanel, BorderLayout.NORTH);
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

        // Establecer colores Material Design a los botones
        usuariosButton = new JButton("Usuarios");
        usuariosButton.setBackground(Color.decode("#F44336"));
        usuariosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        rolesButton = new JButton("Roles");
        rolesButton.setBackground(Color.decode("#9C27B0"));
        rolesButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        permisosButton = new JButton("Permisos");
        permisosButton.setBackground(Color.decode("#6D97C1"));
        permisosButton.setPreferredSize(new Dimension(100, 40)); // Ajustar tamaño

        // Crear un GridLayout con 2 filas y 8 columnas
        GridLayout gridLayout = new GridLayout(3, 8);

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
        panel.add(usuariosButton);
        panel.add(rolesButton);
        panel.add(permisosButton);

        // Crea el panel de imagen y lo coloca en el centro
        ImagePanel imagenPanel = new ImagePanel();
        panel2 = new JPanel(new BorderLayout());
        panel2.add(imagenPanel, BorderLayout.CENTER);

        // Crea un panel para contener los botones y el panel de imagen
        panel3 = new JPanel(new BorderLayout());
        panel3.add(panel, BorderLayout.SOUTH);
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
        usuariosButton.setFocusPainted(false);
        rolesButton.setFocusPainted(false);
        permisosButton.setFocusPainted(false);

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
        usuariosButton.setForeground(Color.WHITE);
        rolesButton.setForeground(Color.WHITE);
        permisosButton.setForeground(Color.WHITE);

        promocionesButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isPromocion());
        eventosButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isEvento());
        materialesButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isMaterial());
        floristeriaButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isFloristeria());
        empleadosButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isEmpleado());
        arreglosButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isArreglo());
        comprasButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isCompra());
        tarjetaButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isTarjeta());
        manualidadesButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isManualidad());
        globosButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isGlobo());
        desayunosButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isDesayuno());
        ventasButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isVenta());
        mobiliarioButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isMobiliario());
        pedidosButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isPedido());
        actividadesButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isActividad());
        clientesButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isCliente());
        proveedoresButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isProveedor());
        alquileresButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isAlquiler());
        usuariosButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isUsuario());
        rolesButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isRol());
        permisosButton.setEnabled(SesionUsuario.user.getRol().getPermisos().isRol());


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
        listaUsuarios = new ListaUsuarios(id);
        listaRoles = new ListaRoles(id);
        listaPermisos = new ListaPermisos();

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
            public void actionPerformed(ActionEvent e) {
                listaAlquileres.setVisible(true);
            }
        });

        usuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                ListaUsuarios listaUsuarios = new ListaUsuarios(idUsuarioActual);
                listaUsuarios.setVisible(true);
            }
        });

        rolesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                ListaRoles listaRoles = new ListaRoles(idUsuarioActual);
                listaRoles.setVisible(true);
            }
        });
        permisosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                ListaPermisos listaPermisos1 = new ListaPermisos();
                listaPermisos1.setVisible(true);
            }
        });
    }

    public void setNombreUsuario(String nombre) {
        this.nombre = nombre;
        userNameLabel.setText("Bienvenido, " + nombre + " ▼ ");
    }


    public void setImagenUsuario(String imagen) {
        this.imagen = imagen;
        String imagePath = "img/usuarios/" + imagen; // Ruta actualizada según la imagen
        userLabel.setIcon(new ImageIcon(getRoundedImage(imagePath, 40, 40)));
    }

    private int idUsuarioActual;

    public void setIdUsuarioActual(int id) {
        this.idUsuarioActual = id;
    }

    private void setupNavbar() {
        navbarPanel = new JPanel();
        navbarPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        navbarPanel.setBackground(Color.decode("#607D8B")); // Color del Navbar

        userLabel = new JLabel();
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Margen para la etiqueta

        // Configurar etiqueta con el nombre del usuario
        userNameLabel = new JLabel("Nombre de Usuario");
        userNameLabel.setForeground(Color.WHITE); // Color del texto
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente y tamaño
        userNameLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Margen para la etiqueta

        // Icono en forma de V
        ImageIcon vIcon = new ImageIcon("path_to_v_icon.jpg"); // Ruta al ícono en forma de V
        JLabel vLabel = new JLabel(vIcon);
        vLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // Menú Popup para las opciones del usuario
        userMenu = new JPopupMenu();
        JMenuItem menuItemPerfil = new JMenuItem("Perfil");
        menuItemPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                VerPerfil verPerfil = new VerPerfil(idUsuarioActual);
                verPerfil.setVisible(true);
            }
        });

        JMenuItem menuItemAcercaDe = new JMenuItem("Acerca de");
        menuItemAcercaDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(); // Crea un nuevo diálogo
                dialog.setTitle("Acerca De"); // Establece el título
                dialog.setContentPane(new AcercaDe()); // añade el panel AcercaDe
                dialog.setSize(950, 630); // Establece el tamaño del diálogo
                dialog.setModal(true); // Hace que el diálogo bloquee las otras ventanas hasta que se cierre
                dialog.setLocationRelativeTo(null); // Centra el diálogo en la pantalla
                dialog.setVisible(true); // Hace visible el diálogo
            }
        });

        // Suponiendo que estás en una clase que extiende JFrame
        JMenuItem menuItemCerrarSesion = new JMenuItem("Cerrar sesión");
        menuItemCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btnYes = new JButton("Sí");
                JButton btnNo = new JButton("No");

                // Personaliza los botones aquí
                btnYes.setBackground( Color.decode("#263238"));
                btnNo.setBackground(Color.decode("#C62828"));

                // Personaliza los fondos de los botones aquí
                btnYes.setForeground(Color.WHITE);
                btnNo.setForeground(Color.WHITE);

                // Elimina el foco
                btnYes.setFocusPainted(false);
                btnNo.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Estás seguro de que deseas cerrar sesión?",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        new Object[]{}, // no options
                        null
                );

                // Crea un JDialog
                JDialog dialog = optionPane.createDialog("Cerrar sesión");

                // Añade ActionListener a los botones
                btnYes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        dialog.dispose();
                        Login login = new Login();
                        login.setVisible(true);
                    }
                });

                btnNo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Acciones para el botón No
                        // No se hace nada, sólo se cierra el diálogo
                        dialog.dispose();
                    }
                });

                // Añade los botones al JOptionPane
                optionPane.setOptions(new Object[]{btnYes, btnNo});

                // Muestra el diálogo
                dialog.setVisible(true);
            }
        });

        userMenu.add(menuItemPerfil);
        userMenu.add(menuItemAcercaDe);
        userMenu.add(menuItemCerrarSesion);

        // Evento para mostrar el menú al hacer clic en el nombre del usuario
        userNameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userMenu.show(userNameLabel, e.getX(), e.getY());
            }
        });

        navbarPanel.add(userLabel);
        navbarPanel.add(vLabel);
        navbarPanel.add(userNameLabel);
    }

    private Image getRoundedImage(String imagePath, int width, int height) {
        if (imagePath == null || imagePath.isEmpty()) {
            System.out.println("Image path is null or empty");
            return null; // o retornar una imagen por defecto
        }

        BufferedImage srcImg = null;
        try {
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                System.out.println("File does not exist: " + imagePath);
                return null; // o retornar una imagen por defecto
            }
            srcImg = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar el archivo: " + imagePath);
            return null; // o retornar una imagen por defecto
        }

        // Escalado de imagen con alta calidad
        Image scaledImg = srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        // Mejorar la calidad de renderizado
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Aplicar redondeo
        g2.fillRoundRect(0, 0, width, height, width, height);
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(scaledImg, 0, 0, null);

        g2.dispose();
        return resizedImg;
    }
}
