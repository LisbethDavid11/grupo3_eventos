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

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.Font;
import javax.swing.JTree;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.Dimension;
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
		setBounds(100, 100, 1042, 670);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

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
		listaUsuarios = new ListaUsuarios(id);
		listaRoles = new ListaRoles(id);
		listaPermisos = new ListaPermisos();
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 51, 153));
		panel_1.setBounds(0, 0, 256, 681);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		ImageIcon icon = new ImageIcon( "img\\subMenu\\logo.png");
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
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaCliente.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1.setForeground(new Color(51, 204, 204));
			}
		});
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1.setIcon(new ImageIcon("img\\subMenu\\icons8-cliente-27.png"));
		lblNewLabel_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1.setBounds(26, 221, 199, 26);
		panel_1.add(lblNewLabel_1);

		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBackground(Color.BLACK);
		separator_1.setBounds(26, 208, 196, 2);
		panel_1.add(separator_1);
		
		
		JLabel lblNewLabel_1_2 = new JLabel("Empleados");
		lblNewLabel_1_2.setIcon(new ImageIcon("img\\subMenu\\icons8-empleado-27.png"));
		lblNewLabel_1_2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_2.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_2.setBounds(26, 258, 188, 26);
		lblNewLabel_1_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_2.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaEmpleados.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1_2.setForeground(new Color(51, 204, 204));
			}
		});
		panel_1.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Proveedores");
		lblNewLabel_1_3.setIcon(new ImageIcon("img\\subMenu\\icons8-proveedor-27.png"));
		lblNewLabel_1_3.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_3.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_3.setBounds(29, 289, 165, 37);
		lblNewLabel_1_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_3.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaProveedores.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1_3.setForeground(new Color(51, 204, 204));
			}
		});
		panel_1.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_5 = new JLabel("Usuarios");
		lblNewLabel_1_5.setIcon(new ImageIcon("img\\subMenu\\icons8-usuarios-27.png"));
		lblNewLabel_1_5.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_5.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_5.setBounds(26, 496, 210, 35);
		lblNewLabel_1_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_5.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaUsuarios.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1_5.setForeground(new Color(51, 204, 204));
			}
		});
		panel_1.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("Roles");
		lblNewLabel_1_2_1.setIcon(new ImageIcon("img\\subMenu\\icons8-roles-27.png"));
		lblNewLabel_1_2_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_2_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_2_1.setBounds(26, 542, 210, 35);
		lblNewLabel_1_2_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_2_1.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaRoles.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblNewLabel_1_2_1.setForeground(new Color(51, 204, 204));
			}
		});
		panel_1.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Permisos");
		lblNewLabel_1_3_1.setIcon(new ImageIcon("img\\subMenu\\icons8-end-user-27.png"));
		lblNewLabel_1_3_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_3_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_3_1.setBounds(26, 588, 210, 37);
		lblNewLabel_1_3_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblNewLabel_1_3_1.setForeground(Color.white);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				listaPermisos.setVisible(true);
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
		lblNewLabel_1_2_2.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_2_2.setBounds(57, 434, 81, 14);
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
						listaCliente.setVisible(true);
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
                menuItem1.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem1.setIcon(new ImageIcon("img\\subMenu\\icons8-comprar-27.png"));
                
                menuItem2.setOpaque(true);
                menuItem2.setBackground(new Color(0, 51, 153));
                menuItem2.setForeground(Color.white);
                menuItem2.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem2.setIcon(new ImageIcon("img\\subMenu\\icons8-historial-de-pedidos-27.png"));
                
                menuItem3.setOpaque(true);
                menuItem3.setBackground(new Color(0, 51, 153));
                menuItem3.setForeground(Color.white);
                menuItem3.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem3.setIcon(new ImageIcon("img\\subMenu\\icons8-la-venta-de-tierras-27.png"));
                
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
		lblNewLabel_1_1_1_2.setIcon(new ImageIcon("img\\subMenu\\icons8-circulacion-de-dinero-27.png"));
		lblNewLabel_1_1_1_2.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_1_1_2.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_1_1_1_2.setBounds(26, 428, 27, 27);
		panel_1.add(lblNewLabel_1_1_1_2);

		JLabel lblNewLabel_1_6 = new JLabel("Productos");
		lblNewLabel_1_6.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_6.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_6.setBounds(57, 358, 81, 14);
		panel_1.add(lblNewLabel_1_6);
		
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
                menuItem1.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem1.setIcon(new ImageIcon("img\\subMenu\\icons8-flor-27.png"));
                
                menuItem2.setOpaque(true);
                menuItem2.setBackground(new Color(0, 51, 153));
                menuItem2.setForeground(Color.white);
                menuItem2.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem2.setIcon(new ImageIcon("img\\subMenu\\icons8-análisis-de-stock-27.png"));
                
                menuItem3.setOpaque(true);
                menuItem3.setBackground(new Color(0, 51, 153));
                menuItem3.setForeground(Color.white);
                menuItem3.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem3.setIcon(new ImageIcon("img\\subMenu\\icons8-globos-27.png"));
                
                menuItem4.setOpaque(true);
                menuItem4.setBackground(new Color(0, 51, 153));
                menuItem4.setForeground(Color.white);
                menuItem4.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem4.setIcon(new ImageIcon("img\\subMenu\\icons8-armario-con-puerta-corredera-27.png"));
                
                menuItem5.setOpaque(true);
                menuItem5.setBackground(new Color(0, 51, 153));
                menuItem5.setForeground(Color.white);
                menuItem5.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem5.setIcon(new ImageIcon("img\\subMenu\\icons8-cartas-de-tarot-27.png"));
                
                menuItem6.setOpaque(true);
                menuItem6.setBackground(new Color(0, 51, 153));
                menuItem6.setForeground(Color.white);
                menuItem6.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem6.setIcon(new ImageIcon("img\\subMenu\\icons8-estadio--27.png"));
                
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
		lblNewLabel_1_1_5.setIcon(new ImageIcon("img\\subMenu\\icons8-productos-27.png"));
		lblNewLabel_1_1_5.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_1_5.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_1_1_5.setBounds(26, 352, 27, 27);
		panel_1.add(lblNewLabel_1_1_5);
		

		JLabel lblNewLabel_1_6_1 = new JLabel("Eventos");
		lblNewLabel_1_6_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_6_1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblNewLabel_1_6_1.setBounds(57, 396, 66, 14);
		panel_1.add(lblNewLabel_1_6_1);
		
		
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
                menuItem1.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem1.setIcon(new ImageIcon("img\\subMenu\\icons8-rueda-de-alfarero-27.png"));
                
                menuItem2.setOpaque(true);
                menuItem2.setBackground(new Color(0, 51, 153));
                menuItem2.setForeground(Color.white);
                menuItem2.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem2.setIcon(new ImageIcon("img\\subMenu\\icons8-desayuno-buffet-27.png"));
                
                menuItem3.setOpaque(true);
                menuItem3.setBackground(new Color(0, 51, 153));
                menuItem3.setForeground(Color.white);
                menuItem3.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem3.setIcon(new ImageIcon("img\\subMenu\\icons8-promoción-de-carrito-de-compras-27.png"));
                
                menuItem4.setOpaque(true);
                menuItem4.setBackground(new Color(0, 51, 153));
                menuItem4.setForeground(Color.white);
                menuItem4.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem4.setIcon(new ImageIcon("img\\subMenu\\icons8-arena-27.png"));
                
                menuItem5.setOpaque(true);
                menuItem5.setBackground(new Color(0, 51, 153));
                menuItem5.setForeground(Color.white);
                menuItem5.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem5.setIcon(new ImageIcon("img\\subMenu\\icons8-franquicia-27.png"));
                
                menuItem6.setOpaque(true);
                menuItem6.setBackground(new Color(0, 51, 153));
                menuItem6.setForeground(Color.white);
                menuItem6.setFont(new Font("Times New Roman", Font.BOLD, 18));
                menuItem6.setIcon(new ImageIcon("img\\subMenu\\icons8-alquiler-de-coches-27.png"));
                
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
		lblNewLabel_1_1_5_1.setIcon(new ImageIcon("img\\subMenu\\icons8-eventos-27.png"));
		lblNewLabel_1_1_5_1.setForeground(new Color(51, 204, 204));
		lblNewLabel_1_1_5_1.setFont(new Font("Times New Roman", Font.BOLD, 12));
		lblNewLabel_1_1_5_1.setBounds(26, 390, 27, 27);
		panel_1.add(lblNewLabel_1_1_5_1);
		
		JPanel panel = new JPanel() {
		    private BufferedImage backgroundImage;

		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        try {
		            backgroundImage = ImageIO.read(new File("img\\subMenu\\fondo-1252x1252.jpg"));
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		        if (backgroundImage != null) {
		            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
		        }
		    }
		};

		panel.setBackground(Color.WHITE);
		panel.setBounds(253, 0, 773, 681);
		contentPane.add(panel);
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
	
	
	class RoundedCornerLabel extends JLabel {
	    private int radius;

	    public RoundedCornerLabel( int radius) {
	        this.radius = radius;
	        setOpaque(false);
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        Graphics2D g2 = (Graphics2D) g.create();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setColor(getBackground());
	        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
	        super.paintComponent(g2);
	        g2.dispose();
	    }

	    @Override
	    public Dimension getPreferredSize() {
	        return super.getPreferredSize();
	    }
	}
}
