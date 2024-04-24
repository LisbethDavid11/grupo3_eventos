package Arreglos;
import Login.SesionUsuario;
import Modelos.ModeloArreglo;
import Objetos.Arreglo;
import Objetos.Conexion;
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

public class ListaArreglos extends JFrame {
    private JPanel panelPrincipal, panelTitulo;
    private JButton botonAdelante, botonAtras, botonVer, botonEditar, botonCrear;
    private JTable listaArreglos;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre del arreglo", campoBusqueda);
    private JLabel lblDisponibilidad, lblTitulo, lblPagina;
    private JCheckBox noCheckBox, siCheckBox;
    private JPanel panelA;
    private JPanel panelB;
    private List<Arreglo> listaArreglo;
    private int pagina = 0;
    private Conexion sql;
    private ListaArreglos actual = this;
    private String busqueda = "";
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaArreglos() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaArreglos.setModel(cargarDatos());
        configurarTablaArreglos();
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
                listaArreglos.setModel(cargarDatos());
                configurarTablaArreglos();
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
                listaArreglos.setModel(cargarDatos());
                configurarTablaArreglos();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0; // Reiniciar la paginación
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaArreglos.setModel(cargarDatos());
                configurarTablaArreglos();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        siCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!noCheckBox.isSelected()) {
                    siCheckBox.setSelected(true);
                }
                pagina = 0; // Reiniciar la paginación
                actualizarTabla();
            }
        });

        noCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!siCheckBox.isSelected()) {
                    noCheckBox.setSelected(true);
                }
                pagina = 0; // Reiniciar la paginación
                actualizarTabla();
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

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaArreglos.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                VerArreglo arreglo = new VerArreglo(listaArreglo.get(listaArreglos.getSelectedRow()).getId());
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

        JTableHeader header = listaArreglos.getTableHeader();
        header.setForeground(Color.WHITE);

        int campoBusquedaHeight = 35;
        campoBusqueda.setPreferredSize(new Dimension(campoBusqueda.getPreferredSize().width, campoBusquedaHeight));

        panelPrincipal.setBackground(primaryColor);
        panelTitulo.setBackground(primaryColor);
        panelA.setBackground(primaryColor);
        panelB.setBackground(primaryColor);
        campoBusqueda.setBackground(Color.WHITE);
        noCheckBox.setBackground(primaryColor);
        siCheckBox.setBackground(primaryColor);
        botonAdelante.setBackground(darkColor);
        botonAtras.setBackground(darkColor);
        botonVer.setBackground(darkColor);
        botonEditar.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        header.setBackground(darkColor);

        botonAdelante.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonVer.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);
        placeholder.setForeground(darkColor);
        noCheckBox.setForeground(Color.WHITE);
        siCheckBox.setForeground(Color.WHITE);
        lblPagina.setForeground(Color.WHITE);
        lblDisponibilidad.setForeground(Color.WHITE);

        campoBusqueda.setFont(font);
        botonAdelante.setFont(font);
        botonAtras.setFont(font);
        botonVer.setFont(font);
        botonCrear.setFont(font);
        botonEditar.setFont(font);
        noCheckBox.setFont(font);
        siCheckBox.setFont(font);
        placeholder.setFont(font);
        lblDisponibilidad.setFont(font);
        lblPagina.setFont(font);
        lblTitulo.setFont(fontTitulo);

        noCheckBox.setFocusPainted(false);
        siCheckBox.setFocusPainted(false);
        botonAdelante.setFocusable(false);
        botonAtras.setFocusable(false);
        botonCrear.setFocusable(false);
        botonVer.setFocusable(false);
        botonEditar.setFocusable(false);
        noCheckBox.setFocusable(false);
        siCheckBox.setFocusable(false);

    }

    private void configurarTablaArreglos() {
        TableColumnModel columnModel = listaArreglos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(110);
        columnModel.getColumn(3).setPreferredWidth(110);

        columnModel.getColumn(0).setCellRenderer(new ListaArreglos.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaArreglos.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaArreglos.CenterAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaArreglos.CenterAlignedRenderer());
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
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

    private ModeloArreglo cargarDatos() {
        sql = new Conexion();
        List<Arreglo> listaArreglo = new ArrayList<>();
        String estado = "";
        boolean aplicarFiltroEstado = true;

        if (siCheckBox.isSelected() && noCheckBox.isSelected()) {
            aplicarFiltroEstado = false; // Ambos seleccionados, busca todos los arreglos
        } else if (siCheckBox.isSelected()) {
            estado = "Si"; // Solo el checkbox "Sí" está seleccionado
        } else if (noCheckBox.isSelected()) {
            estado = "No"; // Solo el checkbox "No" está seleccionado
        }

        String sqlQuery = "SELECT * FROM arreglos WHERE nombre LIKE CONCAT('%', ?, '%')";
        if (aplicarFiltroEstado) {
            sqlQuery += " AND disponible = ?";
        }
        sqlQuery += " LIMIT ?, 20";

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(sqlQuery)){
            preparedStatement.setString(1, busqueda);
            if (aplicarFiltroEstado) {
                preparedStatement.setString(2, estado);
                preparedStatement.setInt(3, pagina * 20);
            } else {
                preparedStatement.setInt(2, pagina * 20);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
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
        }

        return new ModeloArreglo(listaArreglo, sql);
    }

    private int getTotalPageCount() {
        int count = 0;
        boolean aplicarFiltroEstado = true;
        String estado = "";
        if (siCheckBox.isSelected() && noCheckBox.isSelected()) {
            aplicarFiltroEstado = false; // Ambos seleccionados, busca todos los registros
        } else if (siCheckBox.isSelected()) {
            estado = "Si";
        } else if (noCheckBox.isSelected()) {
            estado = "No";
        }

        String sqlQuery = "SELECT COUNT(*) AS total FROM arreglos WHERE nombre LIKE CONCAT('%', ?, '%')";
        if (aplicarFiltroEstado) {
            sqlQuery += " AND disponible = ?";
        }

        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, busqueda);
            if (aplicarFiltroEstado) {
                preparedStatement.setString(2, estado);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }

        int registrosPorPagina = 20;
        int totalPageCount = count / registrosPorPagina;
        if (count % registrosPorPagina != 0) {
            totalPageCount++;
        }

        return totalPageCount;
    }

    private void actualizarTabla() {
        listaArreglos.setModel(cargarDatos());
        configurarTablaArreglos();
        int totalPaginas = getTotalPageCount();
        lblPagina.setText("Página " + (pagina + 1) + " de " + totalPaginas);
        botonAdelante.setEnabled((pagina + 1) < totalPaginas);
        botonAtras.setEnabled(pagina > 0);
    }

    private void mostrarTodos() {
        siCheckBox.setSelected(true);
        noCheckBox.setSelected(true);
        actualizarTabla();
    }

    public static void main(String[] args) {
        ListaArreglos listaArreglo = new ListaArreglos();
        listaArreglo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaArreglo.setVisible(true);
    }
}
