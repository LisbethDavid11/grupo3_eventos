package Arreglos;

import Modelos.ModeloArreglo;
import Objetos.Arreglo;
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
    private JLabel lblPagina; // Label para mostrar la página actual y el total de páginas
    private List<Arreglo> listaArreglo;

    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaArreglo actual = this;
    private String busqueda = "";

    public ListaArreglo() {
        super("");
        setSize(850, 490);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaArreglos.setModel(cargarDatos());
        centrarDatosTabla();

        // Calcular el número total de páginas al inicio
        lblPagina.setText("Página " + (pagina / 20 + 1) + " de " + getTotalPageCount());


        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaArreglos.getRowCount() == 20) {
                    pagina += 20;
                    botonAtras.setEnabled(true);
                } else {
                    botonAdelante.setEnabled(false);
                }
                listaArreglos.setModel(cargarDatos());
                configuraColumnas();
                centrarDatosTabla();
                lblPagina.setText("Página " + (pagina / 20 + 1) + " de " + getTotalPageCount());

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
                listaArreglos.setModel(cargarDatos());
                centrarDatosTabla();
                lblPagina.setText("Página " + (pagina / 20 + 1) + " de " + getTotalPageCount());

            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0; // Reiniciar la paginación
                listaArreglos.setModel(cargarDatos());
                botonAtras.setEnabled(true);
                botonAdelante.setEnabled(true);
                centrarDatosTabla();
                lblPagina.setText("Página " + (pagina / 20 + 1) + " de " + getTotalPageCount());

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

        // Color de texto del campo de búsqueda y del label de la página
        campoBusqueda.setForeground(Color.WHITE);
        lblPagina.setForeground(Color.WHITE);

        // Color de fondo del campo de búsqueda
        campoBusqueda.setBackground(darkColor);

        // Color del placeholder del campo de búsqueda
        placeholder.changeAlpha(0.75f);
        placeholder.setForeground(Color.LIGHT_GRAY);
        placeholder.setFont(new Font("Nunito", Font.ITALIC, 11));
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
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM arreglos WHERE nombre LIKE CONCAT('%', ?, '%') LIMIT ?, 20")){
            preparedStatement.setString(1, busqueda);

            preparedStatement.setInt(2, pagina);
            ResultSet resultSet = preparedStatement.executeQuery();
            listaArreglo = new ArrayList<>();

            while (resultSet.next()) {
                Arreglo arreglo = new Arreglo();
                arreglo.setId(resultSet.getInt("id"));
                arreglo.setNombre(resultSet.getString("nombre"));
                arreglo.setPrecio(resultSet.getDouble("precio"));
                arreglo.setDisponible(resultSet.getString("disponible"));
                listaArreglo.add(arreglo);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            listaArreglo = new ArrayList<>();
        }


        if (listaArreglos.getColumnCount() > 0) {
            TableColumn columnId = listaArreglos.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50); // Puedes ajustar este valor según tus necesidades
        }

        return new ModeloArreglo(listaArreglo, sql);

    }

    private void configuraColumnas() {
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

        if (listaArreglos.getColumnCount() > 1) {
            TableColumn imageColumn = listaArreglos.getColumnModel().getColumn(1);
            imageColumn.setCellRenderer(renderer);
        }

        // Ajustar el ancho de la columna de ID
        if (listaArreglos.getColumnCount() > 0) {
            TableColumn columnId = listaArreglos.getColumnModel().getColumn(0);
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
        listaArreglos.setModel(cargarDatos());
        configuraColumnas();
        centrarDatosTabla();
        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

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
        ListaArreglo listaArreglo = new ListaArreglo();
        listaArreglo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaArreglo.setVisible(true);
    }
}
