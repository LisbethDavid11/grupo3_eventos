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
import Login.ListaUsuarios;
import Login.Login;
import Login.SesionUsuario;
import Login.VerPerfil;
import Manualidades.ListaManualidades;
import Materiales.ListaMateriales;
import Mobiliario.ListaMobiliario;
import Pedidos.ListaPedidos;
import Permisos.ListaPermisos;
import Promociones.ListaPromociones;
import Proveedores.ListaProveedores;
import Roles.ListaRoles;
import Tarjetas.ListaTarjetas;
import Ventas.ListaVentas;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SubMenuDashboard extends JFrame {
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
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int id;
	private JPanel navbar;
	private JLabel userLabel, userNameLabel;
	private JPopupMenu userMenu;
	private String nombre;
	private String imagen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SubMenuDashboard frame = new SubMenuDashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SubMenuDashboard() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1040, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);


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

		int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
		listaUsuarios = new ListaUsuarios(idUsuarioActual);
		listaRoles = new ListaRoles(idUsuarioActual);
		listaPermisos = new ListaPermisos();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 51, 153));
		panel_1.setBounds(0, 0, 266, 681);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		ImageIcon icon = new ImageIcon( "img/logo.png");
		Image img = icon.getImage();

		Image newImg = img.getScaledInstance(210, 154, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImg);

		lblNewLabel.setIcon(icon);
		lblNewLabel.setBounds(26, 22, 210, 167);
		panel_1.add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBackground(Color.BLACK);
		separator.setBounds(26, 326, 196, 2);
		panel_1.add(separator);
		
		JLabel lblNewLabel_1 = new JLabel("Clientes");
		lblNewLabel_1.setEnabled(SesionUsuario.user.getRol().getPermisos().isCliente());
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaCliente.setVisible(SesionUsuario.user.getRol().getPermisos().isCliente());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1.setForeground(new Color(51, 204, 204));
			}
		});
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1.setIcon(new ImageIcon("img/icons8-cliente-27.png"));
		lblNewLabel_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1.setBounds(26, 221, 199, 26);
		panel_1.add(lblNewLabel_1);

		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBackground(Color.BLACK);
		separator_1.setBounds(26, 208, 196, 2);
		panel_1.add(separator_1);
		
		
		JLabel lblNewLabel_1_2 = new JLabel("Empleados");
		lblNewLabel_1_2.setIcon(new ImageIcon("img/icons8-empleado-27.png"));
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_2.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_2.setBounds(26, 258, 188, 26);
		lblNewLabel_1_2.setEnabled(SesionUsuario.user.getRol().getPermisos().isEmpleado());
		lblNewLabel_1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_2.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaEmpleados.setVisible(SesionUsuario.user.getRol().getPermisos().isEmpleado());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1_2.setForeground(new Color(51, 204, 204));
			}
		});
		panel_1.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Proveedores");
		lblNewLabel_1_3.setIcon(new ImageIcon("img/icons8-proveedor-27.png"));
		lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_3.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_3.setBounds(29, 289, 165, 37);
		lblNewLabel_1_3.setEnabled(SesionUsuario.user.getRol().getPermisos().isProveedor());
		lblNewLabel_1_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_3.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaProveedores.setVisible(SesionUsuario.user.getRol().getPermisos().isProveedor());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1_3.setForeground(new Color(51, 204, 204));
			}
		});
		panel_1.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_5 = new JLabel("Usuarios");
		lblNewLabel_1_5.setIcon(new ImageIcon("img/icons8-usuarios-27.png"));
		lblNewLabel_1_5.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_5.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_5.setBounds(26, 496, 210, 35);
		lblNewLabel_1_5.setEnabled(SesionUsuario.user.getRol().getPermisos().isUsuario());
		lblNewLabel_1_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_5.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaUsuarios.setVisible(SesionUsuario.user.getRol().getPermisos().isUsuario());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1_5.setForeground(new Color(51, 204, 204));
			}
		});
		panel_1.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Roles");
		lblNewLabel_1_2_1.setIcon(new ImageIcon("img/icons8-roles-27.png"));
		lblNewLabel_1_2_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_2_1.setBounds(26, 542, 210, 35);
		lblNewLabel_1_2_1.setEnabled(SesionUsuario.user.getRol().getPermisos().isRol());
		lblNewLabel_1_2_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_2_1.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaRoles.setVisible(SesionUsuario.user.getRol().getPermisos().isRol());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1_2_1.setForeground(new Color(51, 204, 204));
			}
		});
		panel_1.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Permisos");
		lblNewLabel_1_3_1.setIcon(new ImageIcon("img/icons8-end-user-27.png"));
		lblNewLabel_1_3_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_3_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_3_1.setBounds(26, 588, 210, 37);
		lblNewLabel_1_3_1.setEnabled(SesionUsuario.user.getRol().getPermisos().isRol());
		lblNewLabel_1_3_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_3_1.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaPermisos.setVisible(SesionUsuario.user.getRol().getPermisos().isRol());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1_3_1.setForeground(new Color(51, 204, 204));
			}
		});
		panel_1.add(lblNewLabel_1_3_1);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setForeground(Color.BLACK);
		separator_1_1.setBackground(Color.BLACK);
		separator_1_1.setBounds(29, 483, 196, 2);
		panel_1.add(separator_1_1);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("Comercio");
		lblNewLabel_1_2_2.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_2_2.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_2_2.setBounds(57, 434, 85, 13);
		lblNewLabel_1_2_2.setEnabled(SesionUsuario.user.getRol().getPermisos().isVenta() ||
									SesionUsuario.user.getRol().getPermisos().isPedido() ||
									SesionUsuario.user.getRol().getPermisos().isCompra());
		panel_1.add(lblNewLabel_1_2_2);

		lblNewLabel_1_2_2.addMouseListener(new MouseAdapter() {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem menuItem1 = new JMenuItem("Compras");
            JMenuItem menuItem2 = new JMenuItem("Pedidos");
            JMenuItem menuItem3 = new JMenuItem("Ventas");
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_2_2.setForeground(Color.white);

                menuItem1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						listaCompras.setVisible(true);
					}
				});

                menuItem2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaPedidos.setVisible(true);
                    }
                });
                
                menuItem3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaVentas.setVisible(true);
                    }
                });
                
                
                menuItem1.setOpaque(true);
                menuItem1.setBackground(new Color(0, 51, 153));
                menuItem1.setForeground(Color.white);
                menuItem1.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem1.setIcon(new ImageIcon("img/icons8-comprar-27.png"));
				menuItem1.setEnabled(SesionUsuario.user.getRol().getPermisos().isCompra());

				menuItem2.setOpaque(true);
                menuItem2.setBackground(new Color(0, 51, 153));
                menuItem2.setForeground(Color.white);
                menuItem2.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem2.setIcon(new ImageIcon("img/icons8-historial-de-pedidos-27.png"));
				menuItem2.setEnabled(SesionUsuario.user.getRol().getPermisos().isPedido());

                menuItem3.setOpaque(true);
                menuItem3.setBackground(new Color(0, 51, 153));
                menuItem3.setForeground(Color.white);
                menuItem3.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem3.setIcon(new ImageIcon("img/icons8-la-venta-de-tierras-27.png"));
				menuItem3.setEnabled(SesionUsuario.user.getRol().getPermisos().isVenta());

                menu.add(menuItem1);
                menu.add(menuItem2);
                menu.add(menuItem3);

                menu.show(lblNewLabel_1_2_2, lblNewLabel_1_2_2.getWidth(), 0);
			}
            
            @Override
            public void mouseExited(MouseEvent e) {

                lblNewLabel_1_2_2.setForeground(new Color(51, 204, 204)); 
            }
        });
		
		JLabel lblNewLabel_1_1_1_2 = new JLabel("icon");
		lblNewLabel_1_1_1_2.setIcon(new ImageIcon("img/icons8-circulacion-de-dinero-27.png"));
		lblNewLabel_1_1_1_2.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_1_1_2.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblNewLabel_1_1_1_2.setBounds(26, 428, 27, 27);
		panel_1.add(lblNewLabel_1_1_1_2);

		JLabel lblNewLabel_1_6 = new JLabel("Productos");
		lblNewLabel_1_6.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_6.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_6.setBounds(57, 358, 85, 14);
		panel_1.add(lblNewLabel_1_6);
		lblNewLabel_1_1_1_2.setEnabled(SesionUsuario.user.getRol().getPermisos().isFloristeria() ||
				SesionUsuario.user.getRol().getPermisos().isMaterial() ||
				SesionUsuario.user.getRol().getPermisos().isGlobo() ||
				SesionUsuario.user.getRol().getPermisos().isMobiliario() ||
				SesionUsuario.user.getRol().getPermisos().isTarjeta() ||
				SesionUsuario.user.getRol().getPermisos().isArreglo());
		lblNewLabel_1_6.setEnabled(SesionUsuario.user.getRol().getPermisos().isFloristeria() ||
				SesionUsuario.user.getRol().getPermisos().isMaterial() ||
				SesionUsuario.user.getRol().getPermisos().isGlobo() ||
				SesionUsuario.user.getRol().getPermisos().isMobiliario() ||
				SesionUsuario.user.getRol().getPermisos().isTarjeta() ||
				SesionUsuario.user.getRol().getPermisos().isArreglo());
		lblNewLabel_1_6.addMouseListener(new MouseAdapter() {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem menuItem1 = new JMenuItem("Floristeria");
            JMenuItem menuItem2 = new JMenuItem("Materiales");
            JMenuItem menuItem3 = new JMenuItem("Globos");
            JMenuItem menuItem4 = new JMenuItem("Mobiliario");
            JMenuItem menuItem5 = new JMenuItem("Tarjetas");
            JMenuItem menuItem6 = new JMenuItem("Arreglos");
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_6.setForeground(Color.white);

                menuItem1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						listaFloristeria.setVisible(true);
					}
				});

                menuItem2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaMateriales.setVisible(true);
                    }
                });
                
                menuItem3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaGlobos.setVisible(true);
                    }
                });
                
                menuItem4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaMobiliario.setVisible(true);
                    }
                });
                
                menuItem5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaTarjetas.setVisible(true);
                    }
                });
                
                menuItem6.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaArreglo.setVisible(true);
                    }
                });

                menuItem1.setOpaque(true);
                menuItem1.setBackground(new Color(0, 51, 153));
                menuItem1.setForeground(Color.white);
                menuItem1.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem1.setIcon(new ImageIcon("img/icons8-flor-27.png"));
				menuItem1.setEnabled(SesionUsuario.user.getRol().getPermisos().isFloristeria());
                
                menuItem2.setOpaque(true);
                menuItem2.setBackground(new Color(0, 51, 153));
                menuItem2.setForeground(Color.white);
                menuItem2.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem2.setIcon(new ImageIcon("img/icons8-analisis-de-stock-27.png"));
				menuItem2.setEnabled(SesionUsuario.user.getRol().getPermisos().isMaterial());

                menuItem3.setOpaque(true);
                menuItem3.setBackground(new Color(0, 51, 153));
                menuItem3.setForeground(Color.white);
                menuItem3.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem3.setIcon(new ImageIcon("img/icons8-globos-27.png"));
				menuItem3.setEnabled(SesionUsuario.user.getRol().getPermisos().isGlobo());

                menuItem4.setOpaque(true);
                menuItem4.setBackground(new Color(0, 51, 153));
                menuItem4.setForeground(Color.white);
                menuItem4.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem4.setIcon(new ImageIcon("img/icons8-armario-con-puerta-corredera-27.png"));
				menuItem4.setEnabled(SesionUsuario.user.getRol().getPermisos().isMobiliario());
                
                menuItem5.setOpaque(true);
                menuItem5.setBackground(new Color(0, 51, 153));
                menuItem5.setForeground(Color.white);
                menuItem5.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem5.setIcon(new ImageIcon("img/icons8-cartas-de-tarot-27.png"));
				menuItem5.setEnabled(SesionUsuario.user.getRol().getPermisos().isTarjeta());
                
                menuItem6.setOpaque(true);
                menuItem6.setBackground(new Color(0, 51, 153));
                menuItem6.setForeground(Color.white);
                menuItem6.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem6.setIcon(new ImageIcon("img/icons8-estadio--27.png"));
				menuItem6.setEnabled(SesionUsuario.user.getRol().getPermisos().isArreglo());
                
                menu.add(menuItem1);
                menu.add(menuItem2);
                menu.add(menuItem3);
                menu.add(menuItem4);
                menu.add(menuItem5);
                menu.add(menuItem6);

                menu.show(lblNewLabel_1_6, lblNewLabel_1_6.getWidth(), 0);
			}
            
            @Override
            public void mouseExited(MouseEvent e) {

            	lblNewLabel_1_6.setForeground(new Color(51, 204, 204)); 
            }
        });
		
		
		JLabel lblNewLabel_1_1_5 = new JLabel("icon");
		lblNewLabel_1_1_5.setIcon(new ImageIcon("img/icons8-productos-27.png"));
		lblNewLabel_1_1_5.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_1_5.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_1_1_5.setBounds(26, 352, 27, 27);
		panel_1.add(lblNewLabel_1_1_5);
		

		JLabel lblNewLabel_1_6_1 = new JLabel("Eventos");
		lblNewLabel_1_6_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_6_1.setFont(new Font("Times New Roman", Font.BOLD, 16));
		lblNewLabel_1_6_1.setBounds(57, 396, 66, 14);
		panel_1.add(lblNewLabel_1_6_1);
		lblNewLabel_1_1_5.setEnabled(SesionUsuario.user.getRol().getPermisos().isManualidad() ||
				SesionUsuario.user.getRol().getPermisos().isDesayuno() ||
				SesionUsuario.user.getRol().getPermisos().isPromocion() ||
				SesionUsuario.user.getRol().getPermisos().isEvento() ||
				SesionUsuario.user.getRol().getPermisos().isActividad() ||
				SesionUsuario.user.getRol().getPermisos().isAlquiler());
		lblNewLabel_1_6_1.setEnabled(SesionUsuario.user.getRol().getPermisos().isManualidad() ||
				SesionUsuario.user.getRol().getPermisos().isDesayuno() ||
				SesionUsuario.user.getRol().getPermisos().isPromocion() ||
				SesionUsuario.user.getRol().getPermisos().isEvento() ||
				SesionUsuario.user.getRol().getPermisos().isActividad() ||
				SesionUsuario.user.getRol().getPermisos().isAlquiler());
		
		lblNewLabel_1_6_1.addMouseListener(new MouseAdapter() {
            JPopupMenu menu = new JPopupMenu();
            JMenuItem menuItem1 = new JMenuItem("Manualidades");
            JMenuItem menuItem2 = new JMenuItem("Desayunos");
            JMenuItem menuItem3 = new JMenuItem("Promociones");
            JMenuItem menuItem4 = new JMenuItem("Eventos");
            JMenuItem menuItem5 = new JMenuItem("Actividades");
            JMenuItem menuItem6 = new JMenuItem("Alquileres");
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_6_1.setForeground(Color.white);

                menuItem1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						listaManualidades.setVisible(true);
					}
				});

                menuItem2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaDesayunos.setVisible(true);
                    }
                });
                
                menuItem3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaPromociones.setVisible(true);
                    }
                });
                
                menuItem4.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaEventos.setVisible(true);
                    }
                });
                
                menuItem5.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaActividades.setVisible(true);
                    }
                });
                
                menuItem6.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
						listaAlquileres.setVisible(true);
                    }
                });
                
                
                menuItem1.setOpaque(true);
                menuItem1.setBackground(new Color(0, 51, 153));
                menuItem1.setForeground(Color.white);
                menuItem1.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem1.setIcon(new ImageIcon("img/icons8-rueda-de-alfarero-27.png"));
				menuItem1.setEnabled(SesionUsuario.user.getRol().getPermisos().isManualidad());
                
                menuItem2.setOpaque(true);
                menuItem2.setBackground(new Color(0, 51, 153));
                menuItem2.setForeground(Color.white);
                menuItem2.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem2.setIcon(new ImageIcon("img/icons8-desayuno-buffet-27.png"));
				menuItem2.setEnabled(SesionUsuario.user.getRol().getPermisos().isDesayuno());

                menuItem3.setOpaque(true);
                menuItem3.setBackground(new Color(0, 51, 153));
                menuItem3.setForeground(Color.white);
                menuItem3.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem3.setIcon(new ImageIcon("img/icons8-promocion-de-carrito-de-compras-27.png"));
				menuItem3.setEnabled(SesionUsuario.user.getRol().getPermisos().isPromocion());

                menuItem4.setOpaque(true);
                menuItem4.setBackground(new Color(0, 51, 153));
                menuItem4.setForeground(Color.white);
                menuItem4.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem4.setIcon(new ImageIcon("img/icons8-arena-27.png"));
				menuItem4.setEnabled(SesionUsuario.user.getRol().getPermisos().isEvento());
                
                menuItem5.setOpaque(true);
                menuItem5.setBackground(new Color(0, 51, 153));
                menuItem5.setForeground(Color.white);
                menuItem5.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem5.setIcon(new ImageIcon("img/icons8-franquicia-27.png"));
				menuItem5.setEnabled(SesionUsuario.user.getRol().getPermisos().isActividad());
                
                menuItem6.setOpaque(true);
                menuItem6.setBackground(new Color(0, 51, 153));
                menuItem6.setForeground(Color.white);
                menuItem6.setFont(new Font("Times New Roman", Font.BOLD, 16));
                menuItem6.setIcon(new ImageIcon("img/icons8-alquiler-de-coches-27.png"));
				menuItem6.setEnabled(SesionUsuario.user.getRol().getPermisos().isAlquiler());
                
                menu.add(menuItem1);
                menu.add(menuItem2);
                menu.add(menuItem3);
                menu.add(menuItem4);
                menu.add(menuItem5);
                menu.add(menuItem6);

                menu.show(lblNewLabel_1_6_1, lblNewLabel_1_6_1.getWidth(), 0);
			}
            
            @Override
            public void mouseExited(MouseEvent e) {

            	lblNewLabel_1_6_1.setForeground(new Color(51, 204, 204)); 
            }
        });
		
		JLabel lblNewLabel_1_1_5_1 = new JLabel("icon");
		lblNewLabel_1_1_5_1.setIcon(new ImageIcon("img/icons8-eventos-27.png"));
		lblNewLabel_1_1_5_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_1_5_1.setFont(new Font("Times New Roman", Font.BOLD, 11));
		lblNewLabel_1_1_5_1.setBounds(26, 390, 27, 27);
		panel_1.add(lblNewLabel_1_1_5_1);
		
		JPanel panel = new JPanel() {
		    private BufferedImage backgroundImage;

		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        try {
		            backgroundImage = ImageIO.read(new File("img/fondo-1252x1252.jpg"));
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        if (backgroundImage != null) {
		            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		        }
		    }
		};

		panel.setBackground(Color.WHITE);
		panel.setBounds(253, 55, 773, 626);
		contentPane.add(panel);

		setupNavbar(this);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}


	private void setupNavbar(SubMenuDashboard subMenuDashboard) {
		navbar = new JPanel();
		navbar.setBounds(255, 0, 771, 55);
		FlowLayout fl_navbar = new FlowLayout();
		fl_navbar.setAlignment(FlowLayout.RIGHT);
		navbar.setLayout(fl_navbar);
		navbar.setBackground(Color.decode("#607D8B")); // Color del Navbar

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

		JMenuItem menuItemAcercaDe = new JMenuItem("Acerca De");
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
		JMenuItem menuItemCerrarSesion = new JMenuItem("Cerrar Sesión");
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

		navbar.add(userLabel);
		navbar.add(vLabel);
		navbar.add(userNameLabel);
		subMenuDashboard.getContentPane().add(navbar);
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

	private int idUsuarioActual;

	public void setIdUsuarioActual(int id) {
		this.idUsuarioActual = id;
	}


}
