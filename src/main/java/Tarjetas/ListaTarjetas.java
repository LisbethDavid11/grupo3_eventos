package Tarjetas;
import Login.SesionUsuario;
import Modelos.ModeloTarjeta;
import Objetos.Conexion;
import Objetos.Tarjeta;
import Objetos.TarjetaDetalle;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
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
    private TextPrompt placeholder = new TextPrompt(" Buscar por ocasión ó precio", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private JLabel lblPagina;
    private JLabel lbl0;
    private JCheckBox noCheckBox;
    private JCheckBox siCheckBox;
    private JLabel lblD;
    private JPanel panelTitulo;
    private JPanel panelB;
    private JPanel panelA;
    private ImageIcon imagen;
    private List<Tarjeta> tarjetaList;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaTarjetas actual = this;
    private String busqueda = "";
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro
    public ListaTarjetas() {
        super("Listado Tarjetas");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaTarjetas.setModel(cargarDatos());
        configurarTablaTarjetas();
        mostrarTodos();

        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

        botonAtras.setEnabled(false);
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
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

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
                listaTarjetas.setModel(cargarDatos());
                configurarTablaTarjetas();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
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
        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaTarjetas.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                VerTarjeta verTarjeta = new VerTarjeta(tarjetaList.get(listaTarjetas.getSelectedRow()));
                verTarjeta.setVisible(true);
                actual.dispose();
            }
        });

       botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaTarjetas.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                EditarTarjeta editarTarjeta = new EditarTarjeta(tarjetaList.get(listaTarjetas.getSelectedRow()));
                editarTarjeta.setVisible(true);
                actual.dispose();
            }
        });
        
       botonVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonVer.setBackground(lightColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonVer.setBackground(darkColor);
            }
        });

        JTableHeader header = listaTarjetas.getTableHeader();
        header.setForeground(Color.WHITE);

        int campoBusquedaHeight = 35;
        campoBusqueda.setPreferredSize(new Dimension(campoBusqueda.getPreferredSize().width, campoBusquedaHeight));

        panelPrincipal.setBackground(primaryColor);
        panelTitulo.setBackground(primaryColor);
        panelA.setBackground(primaryColor);
        panelB.setBackground(primaryColor);
        header.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        botonEditar.setBackground(darkColor);
        botonAdelante.setBackground(darkColor);
        botonAtras.setBackground(darkColor);
        botonVer.setBackground(darkColor);
        campoBusqueda.setBackground(Color.WHITE);
        siCheckBox.setBackground(primaryColor);
        noCheckBox.setBackground(primaryColor);

        placeholder.setForeground(darkColor);
        botonVer.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonAdelante.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
        lblPagina.setForeground(Color.WHITE);
        siCheckBox.setForeground(Color.WHITE);
        noCheckBox.setForeground(Color.WHITE);
        lblD.setForeground(Color.WHITE);

        campoBusqueda.setFont(font);
        botonAdelante.setFont(font);
        botonVer.setFont(font);
        botonEditar.setFont(font);
        botonAtras.setFont(font);
        botonCrear.setFont(font);
        placeholder.setFont(font);
        lbl0.setFont(fontTitulo);
        lblPagina.setFont(font);

        botonAdelante.setFocusable(false);
        botonAtras.setFocusable(false);
        botonAtras.setFocusable(false);
        botonEditar.setFocusable(false);
        botonVer.setFocusable(false);

    }

    private void configurarTablaTarjetas() {
        TableColumnModel columnModel = listaTarjetas.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(50);
        columnModel.getColumn(3).setPreferredWidth(30);


        columnModel.getColumn(0).setCellRenderer(new ListaTarjetas.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaTarjetas.LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaTarjetas.CenterAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaTarjetas.LeftAlignedRenderer());

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

    private ModeloTarjeta cargarDatos() {
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
                tarjeta.setImagen(resultSet.getString("imagen"));
                tarjeta.setCantidad(resultSet.getInt("cantidad"));
                tarjeta.setPrecio_tarjeta(resultSet.getDouble("precio_tarjeta"));
                tarjeta.setMano_obra(resultSet.getDouble("mano_obra"));
                tarjeta.setDisponible(resultSet.getString("disponible"));
                tarjeta.setDescripcion(resultSet.getString("descripcion"));


                PreparedStatement preparedS = mysql.prepareStatement(
                        "SELECT * FROM eventos.tarjetas_detalles where id_tarjeta = ?;"
                );
                preparedS.setInt(1, tarjeta.getId());
                ResultSet resultS = preparedS.executeQuery();

                while (resultS.next()) {
                    TarjetaDetalle tarjetaDetalle = new TarjetaDetalle();
                    tarjetaDetalle.setId(resultS.getInt("id"));
                    tarjetaDetalle.setIdMaterial(resultS.getInt("id_material"));
                    tarjetaDetalle.setIdTarjeta(resultS.getInt("id_tarjeta"));
                    tarjetaDetalle.setCantidad(resultS.getInt("cantidad"));
                    tarjetaDetalle.setPrecio(resultS.getDouble("precio"));
                    tarjeta.addDetalles(tarjetaDetalle);
                }

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

        return new ModeloTarjeta(tarjetaList, sql);
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

        int registrosPorPagina = 20;
        int totalPageCount = (count + registrosPorPagina - 1) / registrosPorPagina;
        if (totalPageCount == 0) {
            totalPageCount = 1;  // Asegura que siempre haya al menos una página.
        }

        return totalPageCount;
    }

    private void actualizarTabla() {
        listaTarjetas.setModel(cargarDatos());
        configurarTablaTarjetas();
        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
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
