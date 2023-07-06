package Arreglos;

import Modelos.ModeloArreglo;
import Modelos.ModeloFloristeria;
import Objetos.Arreglo;
import Objetos.Conexion;

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

public class ListaArreglo extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JTable listaArreglos;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt("Buscar por nombre", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private ImageIcon imagen;

    private List<Arreglo> listaArreglo;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaArreglo actual = this;

    public ListaArreglo() {
        super("");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaArreglos.setModel(cargarDatos());
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
        TableColumn imageColumn = listaArreglos.getColumnModel().getColumn(1);
        imageColumn.setCellRenderer(renderer);

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                listaArreglos.setModel(cargarDatos());
                botonAtras.setEnabled(true);
                botonAdelante.setEnabled(true);
                centrarDatosTabla();
            }
        });

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int totalElementos = listaArreglos.getRowCount();
                int paginasTotales = totalElementos / 20 + (totalElementos % 20 > 0 ? 1 : 0);
                int elementosRestantes = totalElementos % 20;

                if (pagina < paginasTotales - 1) {
                    pagina++;
                } else if (pagina == paginasTotales - 1 && elementosRestantes > 0) {
                    pagina++;
                }

                botonAtras.setEnabled(true);
                botonAdelante.setEnabled(pagina < paginasTotales - 1 || (pagina == paginasTotales - 1 && elementosRestantes > 0));

                listaArreglos.setModel(cargarDatos());
                centrarDatosTabla();
            }
        });

        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pagina > 0) {
                    pagina--;
                }

                botonAtras.setEnabled(pagina > 0);
                botonAdelante.setEnabled(true);

                listaArreglos.setModel(cargarDatos());
                centrarDatosTabla();
            }
        });



        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearArreglo arreglo = new CrearArreglo();
                arreglo.setVisible(true);
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
        listaArreglos.setBackground(lightColor);

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

        for (int i = 0; i < listaArreglos.getColumnCount(); i++) {
            listaArreglos.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private ModeloArreglo cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM arreglos WHERE nombre LIKE CONCAT('%', ?, '%') LIMIT ?, 20")) {

            preparedStatement.setString(1, campoBusqueda.getText());
            preparedStatement.setInt(2, pagina);
            ResultSet resultSet = preparedStatement.executeQuery();
            listaArreglo = new ArrayList<>();

            while (resultSet.next()) {
                Arreglo arreglo = new Arreglo();

                // Obtener el nombre de la imagen correspondiente
                String nombreImagen = resultSet.getString("imagen");
                // Crear la ruta completa de la imagen
                String rutaImagen = "C:/Laragon/www/grupo3_eventos/src/main/resources/img/" + nombreImagen;

                arreglo.setId(resultSet.getInt("id"));
                arreglo.setNombre(resultSet.getString("nombre"));
                arreglo.setPrecio(resultSet.getDouble("precio"));
                arreglo.setDisponible(resultSet.getString("disponible"));

                // Crear un objeto ImageIcon con la ruta de la imagen
                ImageIcon imagen = cargarImagen(rutaImagen);
                arreglo.setImagen(imagen); // Establecer la imagen en el objeto Floristeria

                listaArreglo.add(arreglo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            listaArreglo = new ArrayList<>();
        }
        return new ModeloArreglo(listaArreglo, sql);
    }

    private ImageIcon cargarImagen(String rutaImagen) {
        try {
            File file = new File(rutaImagen);
            if (file.exists()) {
                ImageIcon imagenIcon = new ImageIcon(file.getAbsolutePath());
                if (imagenIcon.getIconWidth() > 0) {
                    return imagenIcon;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        ListaArreglo listaArreglo = new ListaArreglo();
        listaArreglo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaArreglo.setVisible(true);
    }
}
