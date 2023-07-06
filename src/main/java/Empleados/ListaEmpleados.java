package Empleados;

import Modelos.ModeloEmpleado;
import Objetos.Conexion;
import Objetos.Empleado;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaEmpleados extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JTable listaEmpleados;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt("Buscar por nombre", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private ImageIcon imagen;

    private List<Empleado> listaEmpleado;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaEmpleados actual = this;

    public ListaEmpleados() {
        super("");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaEmpleado = new ArrayList<>(); // Crear una instancia de listaEmpleado

        listaEmpleados.setModel(cargarDatos());
        centrarDatosTabla();

        // Establecer el renderizador personalizado para la columna de imágenes
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value instanceof ImageIcon) {
                    ImageIcon icon = (ImageIcon) value;
                    label.setIcon(icon);
                    label.setText(null);
                } else {
                    label.setIcon(null);
                    label.setText(value != null ? value.toString() : "");
                }
                return label;
            }
        };
        TableColumn imageColumn = listaEmpleados.getColumnModel().getColumn(1);
        imageColumn.setCellRenderer(renderer);

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                pagina = 0; // Restablecer la página a 0 al realizar una nueva búsqueda
                listaEmpleados.setModel(cargarDatos());
                botonAtras.setEnabled(false); // Deshabilitar el botón de retroceso
                botonAdelante.setEnabled(true);
                centrarDatosTabla();
            }
        });

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pagina += 20;
                listaEmpleados.setModel(cargarDatos());
                botonAtras.setEnabled(true);
                centrarDatosTabla();
            }
        });

        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pagina > 0) {
                    pagina -= 20;
                }
                listaEmpleados.setModel(cargarDatos());
                botonAdelante.setEnabled(true);
                centrarDatosTabla();
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearEmpleado empleado = new CrearEmpleado();
                empleado.setVisible(true);
                actual.dispose();
            }
        });

        // Colores de la paleta
        Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
        Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
        Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

        // Color de fondo
        panelPrincipal.setBackground(primaryColor);

        // Color de texto de los botones
        botonVer.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonAdelante.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonVer.setBackground(darkColor);
        botonAtras.setBackground(darkColor);
        botonAdelante.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        botonEditar.setBackground(darkColor);

        // Color de fondo de la tabla
        listaEmpleados.setBackground(lightColor);

        // Color de texto del campo de búsqueda
        campoBusqueda.setForeground(Color.WHITE);

        // Color de fondo del campo de búsqueda
        campoBusqueda.setBackground(darkColor);

        // Color del placeholder del campo de búsqueda
        placeholder.changeAlpha(0.6f);
        placeholder.setForeground(lightColor);
        placeholder.setFont(new Font("Arial", Font.BOLD, 12));

        // Color de los bordes de los botones al pasar el cursor sobre ellos
        botonVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonVer.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonVer.setBackground(darkColor);
            }
        });

        botonAtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonAtras.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonAtras.setBackground(darkColor);
            }
        });

        botonAdelante.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonAdelante.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonAdelante.setBackground(darkColor);
            }
        });

        botonCrear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonCrear.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonCrear.setBackground(darkColor);
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaEmpleados.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                VerEmpleado empleado = new VerEmpleado(listaEmpleado.get(listaEmpleados.getSelectedRow()).getId());
                empleado.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonEditar.setBackground(lightColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonEditar.setBackground(darkColor);
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaEmpleados.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EditarEmpleado empleado = new EditarEmpleado(listaEmpleado.get(listaEmpleados.getSelectedRow()).getId());
                empleado.setVisible(true);
                actual.dispose();
            }
        });
    }

    private void centrarDatosTabla() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < listaEmpleados.getColumnCount(); i++) {
            listaEmpleados.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private ModeloEmpleado cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM Empleados WHERE Nombres LIKE CONCAT('%', ?, '%') LIMIT ?, 20")) {

            preparedStatement.setString(1, campoBusqueda.getText());
            preparedStatement.setInt(2, pagina);
            ResultSet resultSet = preparedStatement.executeQuery();
            listaEmpleado.clear(); // Limpiar la lista antes de agregar nuevos elementos

            while (resultSet.next()) {
                Empleado empleado = new Empleado();

                empleado.setId(resultSet.getInt("id"));
                empleado.setIdentidad(resultSet.getString("Identidad"));
                empleado.setNombres(resultSet.getString("Nombres"));
                empleado.setApellidos(resultSet.getString("Apellidos"));
                empleado.setTelefono(resultSet.getString("Telefono"));

                listaEmpleado.add(empleado);
            }

            return new ModeloEmpleado(listaEmpleado);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return new ModeloEmpleado(new ArrayList<>());
        }
}

    public static void main(String[] args) {
        ListaEmpleados listaEmpleados = new ListaEmpleados();
        listaEmpleados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaEmpleados.setVisible(true);
    }
}
