package Materiales;

import Modelos.ModeloMateriales;
import Objetos.Conexion;
import Objetos.Material;

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

public class ListaMateriales extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JTable listaMateriales;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt("Buscar por nombre", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private JLabel lbltxt;
    private ImageIcon imagen;
    private List<Material> materialList;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaMateriales actual = this;

    private String busqueda = "";

    public ListaMateriales() {
        super("");
        setSize(850, 490);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");


        listaMateriales.setModel(cargarDatos());
        centrarDatosTabla();

        // Calcular el número total de páginas al inicio
        lbltxt.setText("Página " + (pagina / 20 + 1) + " de " + getTotalPageCount());

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaMateriales.getRowCount() == 20) {
                    pagina += 20;
                    botonAtras.setEnabled(true);
                } else {
                    botonAdelante.setEnabled(false);
                }
                listaMateriales.setModel(cargarDatos());
                configuraColumnas();
                centrarDatosTabla();
                lbltxt.setText("Página " + (pagina / 20 + 1) + " de " + getTotalPageCount());
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
                listaMateriales.setModel(cargarDatos());
                centrarDatosTabla();
                lbltxt.setText("Página " + (pagina / 20 + 1) + " de " + getTotalPageCount());
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0; // Reiniciar la paginación
                listaMateriales.setModel(cargarDatos());
                botonAtras.setEnabled(true);
                botonAdelante.setEnabled(true);
                centrarDatosTabla();
                lbltxt.setText("Página " + (pagina / 20 + 1) + " de " + getTotalPageCount());
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearMateriales materiales = new CrearMateriales();
                materiales.setVisible(true);
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
        listaMateriales.setBackground(lightColor);

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

        for (int i = 0; i < listaMateriales.getColumnCount(); i++) {
            listaMateriales.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private ModeloMateriales cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT f.*, p.empresaProveedora FROM Materiales f JOIN Proveedores p ON f.proveedor_id = p.id WHERE f.nombre LIKE CONCAT('%', ?, '%') LIMIT ?, 20")){
            preparedStatement.setString(1, busqueda);
            preparedStatement.setInt(2, pagina);
            ResultSet resultSet = preparedStatement.executeQuery();
            materialList = new ArrayList<>();

            while (resultSet.next()) {
                Material material = new Material();

                material.setId(resultSet.getInt("id"));
                material.setNombre(resultSet.getString("nombre"));
                material.setPrecio(resultSet.getDouble("precio"));
                material.setDisponible(resultSet.getString("disponible"));
                material.setDescripcion(resultSet.getString("descripcion"));
                material.setProveedorId(resultSet.getInt("proveedor_id"));
                materialList.add(material);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            materialList = new ArrayList<>();
        }


        if (listaMateriales.getColumnCount() > 0) {
            TableColumn columnId = listaMateriales.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50); // Puedes ajustar este valor según tus necesidades
        }

        return new ModeloMateriales(materialList, sql);
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

        if (listaMateriales.getColumnCount() > 1) {
            TableColumn imageColumn = listaMateriales.getColumnModel().getColumn(1);
            imageColumn.setCellRenderer(renderer);
        }

        // Ajustar el ancho de la columna de ID
        if (listaMateriales.getColumnCount() > 0) {
            TableColumn columnId = listaMateriales.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50); // Puedes ajustar este valor según tus necesidades
        }
    }

    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM Materiales f WHERE f.nombre LIKE CONCAT('%', ?, '%')")) {
            preparedStatement.setString(1, busqueda);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }

        int totalPageCount = count / 20;
        if (count % 20 != 0) {
            totalPageCount++; // Añade una página adicional si hay elementos restantes
        }

        return totalPageCount;
    }

    private void loadPage() {
        listaMateriales.setModel(cargarDatos());
        configuraColumnas();
        centrarDatosTabla();
        lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

        // Deshabilitar el botón de adelante si estamos en la última página
        botonAdelante.setEnabled(pagina < getTotalPageCount() - 1);

        // Deshabilitar el botón de atrás si estamos en la primera página
        botonAtras.setEnabled(pagina > 0);
    }

    private void btnAdelanteActionPerformed(ActionEvent evt) {
        pagina++;
        loadPage();
    }

    private void btnAtrasActionPerformed(ActionEvent evt) {
        pagina--;
        loadPage();
    }



    public static void main(String[] args) {
        ListaMateriales listaMateriales = new ListaMateriales();
        listaMateriales.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaMateriales.setVisible(true);
    }
}
