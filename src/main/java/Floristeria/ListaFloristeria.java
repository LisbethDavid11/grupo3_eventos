package Floristeria;

import Modelos.ModeloFloristeria;
import Objetos.Conexion;
import Objetos.Floristeria;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListaFloristeria extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JTable listaFloristerias;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt("Buscar por nombre", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private JLabel lbltxt;
    private ImageIcon imagen;
    private List<Floristeria> listaFloristeria;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaFloristeria actual = this;

    private String busqueda = "";

    public ListaFloristeria() {
        super("");
        setSize(850, 490);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");


        listaFloristerias.setModel(cargarDatos());
        centrarDatosTabla();

        // Calcular el número total de páginas al inicio
        lbltxt.setText("Página " + (pagina / 20 + 1) + " de " + ((getTotalCount() - 1) / 20 + 1));

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaFloristerias.getRowCount() == 20) {
                    pagina += 20;
                    botonAtras.setEnabled(true);
                } else {
                    botonAdelante.setEnabled(false);
                }
                listaFloristerias.setModel(cargarDatos());
                configuraColumnas();
                centrarDatosTabla();
                lbltxt.setText("Página " + (pagina / 20 + 1) + " de " + ((getTotalCount() - 1) / 20 + 1));
            }
        });

        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pagina > 0) {
                    pagina -= 20;
                    botonAdelante.setEnabled(true);
                } else {
                    botonAtras.setEnabled(false);
                }
                listaFloristerias.setModel(cargarDatos());
                centrarDatosTabla();
                lbltxt.setText("Página " + (pagina / 20 + 1) + " de " + ((getTotalCount() - 1) / 20 + 1));
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0; // Reiniciar la paginación
                listaFloristerias.setModel(cargarDatos());
                botonAtras.setEnabled(true);
                botonAdelante.setEnabled(true);
                centrarDatosTabla();
                lbltxt.setText("Página " + (pagina / 20 + 1) + " de " + ((getTotalCount() - 1) / 20 + 1));
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearFloristeria floristeria = new CrearFloristeria();
                floristeria.setVisible(true);
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
        lbltxt.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonVer.setBackground(darkColor);
        botonAtras.setBackground(darkColor);
        botonAdelante.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        botonEditar.setBackground(darkColor);

        // Color de fondo de la tabla
        listaFloristerias.setBackground(lightColor);

        // Color de texto del campo de búsqueda
        campoBusqueda.setForeground(Color.WHITE);

        // Color de fondo del campo de búsqueda
        campoBusqueda.setBackground(darkColor);

        // Color del placeholder del campo de búsqueda
        placeholder.changeAlpha(0.6f);
        placeholder.setForeground(lightColor);
        placeholder.setFont(new Font("Nunito", Font.ITALIC, 11));


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

        botonEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonEditar.setBackground(lightColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonEditar.setBackground(darkColor);
            }
        });
    }

    private void centrarDatosTabla() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < listaFloristerias.getColumnCount(); i++) {
            listaFloristerias.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private ModeloFloristeria cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT f.*, p.empresaProveedora FROM Floristeria f JOIN Proveedores p ON f.proveedor_id = p.id WHERE f.nombre LIKE CONCAT('%', ?, '%') LIMIT ?, 20")){
            preparedStatement.setString(1, busqueda);

            preparedStatement.setInt(2, pagina);
            ResultSet resultSet = preparedStatement.executeQuery();
            listaFloristeria = new ArrayList<>();

            while (resultSet.next()) {
                Floristeria floristeria = new Floristeria();
                floristeria.setId(resultSet.getInt("id"));
                floristeria.setNombre(resultSet.getString("nombre"));
                floristeria.setPrecio(resultSet.getDouble("precio"));
                floristeria.setProveedorId(resultSet.getInt("proveedor_id"));
                listaFloristeria.add(floristeria);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            listaFloristeria = new ArrayList<>();
        }


        if (listaFloristerias.getColumnCount() > 0) {
            TableColumn columnId = listaFloristerias.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50); // Puedes ajustar este valor según tus necesidades
        }

        return new ModeloFloristeria(listaFloristeria, sql);

    }

    private void configuraColumnas() {
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

        if (listaFloristerias.getColumnCount() > 1) {
            TableColumn imageColumn = listaFloristerias.getColumnModel().getColumn(1);
            imageColumn.setCellRenderer(renderer);
        }

        // Ajustar el ancho de la columna de ID
        if (listaFloristerias.getColumnCount() > 0) {
            TableColumn columnId = listaFloristerias.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50); // Puedes ajustar este valor según tus necesidades
        }
    }

    private int getTotalCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM Floristeria f WHERE f.nombre LIKE CONCAT('%', ?, '%')")){
            preparedStatement.setString(1, busqueda);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }
        return count;
    }



    public static void main(String[] args) {
        ListaFloristeria listaFloristeria = new ListaFloristeria();
        listaFloristeria.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaFloristeria.setVisible(true);
    }
}
