package Tarjetas;
import Modelos.ModeloTarjetas;
import Objetos.Conexion;
import Objetos.Tarjeta;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
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

public class ListaTarjetas extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JTable listaTarjetas;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt("Buscar por ocasión o precio", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private JLabel lbltxt;
    private JLabel lbl0;
    private JCheckBox noCheckBox;
    private JCheckBox siCheckBox;
    private JLabel lblD;
    private ImageIcon imagen;
    private List<Tarjeta> tarjetaList;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaTarjetas actual = this;
    private String busqueda = "";

    public ListaTarjetas() {
        super("Listado Tarjetas");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaTarjetas.setModel(cargarDatos());
        configurarTablaTarjetas();
        mostrarTodos();

        lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

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
                listaTarjetas.setModel(cargarDatos());
                configurarTablaTarjetas();
                lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
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
                listaTarjetas.setModel(cargarDatos());
                configurarTablaTarjetas();
                lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0;
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaTarjetas.setModel(cargarDatos());
                configurarTablaTarjetas();
                lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        siCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!siCheckBox.isSelected() && !noCheckBox.isSelected()) {

                    siCheckBox.setSelected(true);
                }
                actualizarTabla();
            }
        });

        noCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!siCheckBox.isSelected() && !noCheckBox.isSelected()) {

                    noCheckBox.setSelected(true);
                }
                actualizarTabla();
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearTarjeta targetas = new CrearTarjeta();
                targetas.setVisible(true);
                actual.dispose();
            }
        });
        /*botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaMateriales.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                VerMaterial material = new VerMaterial(materialList.get(listaMateriales.getSelectedRow()).getId());
                material.setVisible(true);
                actual.dispose();
            }
        });*/

       /* botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaMateriales.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                EditarMaterial material = new EditarMaterial(materialList.get(listaMateriales.getSelectedRow()).getId());
                material.setVisible(true);
                actual.dispose();
            }
        });
*/
        // Colores de la paleta
        Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
        Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
        Color darkColor = Color.decode("#263238"); // Gris azul más oscuro
        
       /* botonVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonVer.setBackground(lightColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonVer.setBackground(darkColor);
            }
        });
*/
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
        lbltxt.setForeground(Color.WHITE);
        lblD.setForeground(Color.WHITE);

        noCheckBox.setBackground(primaryColor);
        noCheckBox.setForeground(Color.WHITE);
        noCheckBox.setFocusPainted(false);
        siCheckBox.setBackground(primaryColor);
        siCheckBox.setForeground(Color.WHITE);
        siCheckBox.setFocusPainted(false);

        // Color de fondo del campo de búsqueda
        campoBusqueda.setBackground(darkColor);

        // Color del placeholder del campo de búsqueda
        placeholder.changeAlpha(0.75f);
        placeholder.setForeground(Color.LIGHT_GRAY);
        placeholder.setFont(new Font("Nunito", Font.ITALIC, 11));

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);
    }

    private void configurarTablaTarjetas() {
        TableColumnModel columnModel = listaTarjetas.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(30);
        columnModel.getColumn(4).setPreferredWidth(50);
        columnModel.getColumn(5).setPreferredWidth(50);

        columnModel.getColumn(0).setCellRenderer(new ListaTarjetas.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaTarjetas.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaTarjetas.CenterAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaTarjetas.LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new ListaTarjetas.LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new ListaTarjetas.LeftAlignedRenderer());
    }

    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class RightAlignedRenderer extends DefaultTableCellRenderer {
        public RightAlignedRenderer() {
            setHorizontalAlignment(RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    private ModeloTarjetas cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT m.* FROM tarjetas m " +
                             "WHERE (m.ocasion LIKE CONCAT('%', ?, '%') OR m.precio_tarjeta = ? OR m.precio_tarjeta LIKE CONCAT('%', ?, '%')) " +
                             "AND ((? = 'Si' AND m.disponible = 'Si') OR (? = 'No' AND m.disponible = 'No')) " +
                             "LIMIT ?, 20"
             )
        ) {

            String disponibilidadSi = siCheckBox.isSelected() ? "Si" : "";
            String disponibilidadNo = noCheckBox.isSelected() ? "No" : "";
            String busquedaOcasionPrecio = busqueda;
            double precio_tarjeta = 0;
            try {
                precio_tarjeta = Double.parseDouble(busqueda);
            } catch (NumberFormatException ex) {
                // Si el valor no es numérico, dejarlo como 0
            }

            double mano_obra = 0;
            try {
                mano_obra = Double.parseDouble(busqueda);
            } catch (NumberFormatException ex) {
                // Si el valor no es numérico, dejarlo como 0
            }

            preparedStatement.setString(1, busqueda);
            preparedStatement.setDouble(2, precio_tarjeta);
            preparedStatement.setDouble(2, mano_obra);
            preparedStatement.setString(3, busqueda);
            preparedStatement.setString(4, disponibilidadSi);
            preparedStatement.setString(5, disponibilidadNo);
            preparedStatement.setInt(6, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            tarjetaList = new ArrayList<>();

            while (resultSet.next()) {
                Tarjeta tarjeta = new Tarjeta();
                tarjeta.setId(resultSet.getInt("id"));
                tarjeta.setOcasion(resultSet.getString("ocasion"));
                tarjeta.setCantidad(resultSet.getInt("cantidad"));
                tarjeta.setPrecio_tarjeta(resultSet.getDouble("precio_tarjeta"));
                tarjeta.setMano_obra(resultSet.getDouble("mano_obra"));
                tarjeta.setDisponible(resultSet.getString("disponible"));
                tarjeta.setDescripcion(resultSet.getString("descripcion"));
                tarjetaList.add(tarjeta);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            tarjetaList = new ArrayList<>();
        }

        if (listaTarjetas.getColumnCount() > 0) {
            TableColumn columnId = listaTarjetas.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloTarjetas(tarjetaList, sql);
    }

    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM tarjetas  WHERE ocasion LIKE CONCAT('%', ?, '%')")) {
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

    private void actualizarTabla() {
        listaTarjetas.setModel(cargarDatos());
        configurarTablaTarjetas();
        lbltxt.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
    }

    private void mostrarTodos() {
        siCheckBox.setSelected(true);
        noCheckBox.setSelected(true);
        actualizarTabla();
    }

    public static void main(String[] args) {
        ListaTarjetas listaMateriales = new ListaTarjetas();
        listaMateriales.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaMateriales.setVisible(true);
    }
}
