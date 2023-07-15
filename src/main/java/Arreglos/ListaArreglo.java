package Arreglos;
import Clientes.EditarCliente;
import Modelos.ModeloArreglo;
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
    private JLabel lblPagina;
    private List<Arreglo> listaArreglo;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaArreglo actual = this;
    private String busqueda = "";

    public ListaArreglo() {
        super("");
        setSize(850, 500);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaArreglos.setModel(cargarDatos());
        centrarDatosTabla();

        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((pagina + 1) < getTotalPageCount()) {
                    pagina++;
                    botonAtras.setEnabled(true);
                    if ((pagina + 1) == getTotalPageCount()) {
                        botonAdelante.setEnabled(false);
                    }
                }
                listaArreglos.setModel(cargarDatos());
                centrarDatosTabla();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pagina > 0) {
                    pagina--;
                    botonAdelante.setEnabled(true);
                    if (pagina == 0) {
                        botonAtras.setEnabled(false);
                    }
                }
                listaArreglos.setModel(cargarDatos());
                centrarDatosTabla();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0;
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaArreglos.setModel(cargarDatos());
                centrarDatosTabla();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
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

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaArreglos.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                EditarArreglo arreglo = new EditarArreglo(listaArreglo.get(listaArreglos.getSelectedRow()).getId());
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
            preparedStatement.setInt(2, pagina * 20);

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
            columnId.setPreferredWidth(50);
        }

        return new ModeloArreglo(listaArreglo, sql);
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

        if (count % 20 > 0) {
            totalPageCount++;
        }

        return totalPageCount;
    }

    public static void main(String[] args) {
        ListaArreglo listaArreglo = new ListaArreglo();
        listaArreglo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaArreglo.setVisible(true);
    }
}
