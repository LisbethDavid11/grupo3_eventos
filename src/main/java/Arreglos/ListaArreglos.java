/**
 * ListaArreglos.java
 *
 * Lista de Arreglos
 *
 * @author Elsa Ramos
 * @version 1.0
 * @since 2024-05-05
 */

package Arreglos;

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
    // Paneles
    private JPanel panelPrincipal;
    private JPanel panelTitulo;
    private JPanel panelA;
    private JPanel panelB;

    // Botones
    private JButton botonAdelante;
    private JButton botonAtras;
    private JButton botonVer;
    private JButton botonEditar;
    private JButton botonCrear;

    // Tabla
    private JTable listaArreglos;

    // Campo de texto
    private JTextField campoBusqueda;

    // Etiquetas
    private JLabel lblDisponibilidad;
    private JLabel lblTitulo;
    private JLabel lblPagina;

    // Casillas de verificación
    private JCheckBox noCheckBox;
    private JCheckBox siCheckBox;

    // Lista de arreglos
    private List<Arreglo> listaArreglo;

    // Página actual
    private int pagina = 0;

    // Conexión a la base de datos
    private Conexion sql;

    // Referencia al formulario actual
    private ListaArreglos actual = this;

    // Búsqueda
    private String busqueda = "";
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre, descripción ó fecha", campoBusqueda);

    // Fuentes
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);

    // Colores
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
                busqueda = campoBusqueda.getText().trim();
                pagina = 0;  // Asegura que siempre regrese a la primera página.
                listaArreglos.setModel(cargarDatos());
                configurarTablaArreglos();
                actualizarBotonesPaginacion();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        siCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!siCheckBox.isSelected() && !noCheckBox.isSelected()) {
                    siCheckBox.setSelected(true);
                }
                pagina = 0; // Restablece a la primera página.
                actualizarTabla();
            }
        });

        noCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!siCheckBox.isSelected() && !noCheckBox.isSelected()) {
                    noCheckBox.setSelected(true);
                }
                pagina = 0; // Restablece a la primera página.
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

    // Método para configurar las columnas y renderizadores de una tabla de arreglos
    private void configurarTablaArreglos() {
        TableColumnModel columnModel = listaArreglos.getColumnModel();
        // Configura el ancho preferido de las columnas
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(300);
        columnModel.getColumn(2).setPreferredWidth(110);
        columnModel.getColumn(3).setPreferredWidth(110);

        // Asigna renderizadores para alinear el contenido de las celdas
        columnModel.getColumn(0).setCellRenderer(new ListaArreglos.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaArreglos.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaArreglos.CenterAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaArreglos.CenterAlignedRenderer());
    }

    // Clase para alinear el texto a la izquierda en celdas de tabla
    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT); // Alineación a la izquierda
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Clase para alinear el texto al centro en celdas de tabla
    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER); // Alineación al centro
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Método para cargar los datos de arreglos desde la base de datos
    private ModeloArreglo cargarDatos() {
        sql = new Conexion(); // Establece conexión con la base de datos
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT * FROM arreglos WHERE nombre LIKE CONCAT('%', ?, '%') AND (? = 'Si' AND disponible = 'Si' OR ? = 'No' AND disponible = 'No') LIMIT ?, 20")){
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, siCheckBox.isSelected() ? "Si" : "");
            preparedStatement.setString(3, noCheckBox.isSelected() ? "No" : "");
            preparedStatement.setInt(4, pagina * 20);

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

    // Método para calcular el número total de páginas basado en los registros disponibles
    private int getTotalPageCount() {
        int count = 0;
        boolean aplicarFiltroEstado = true;
        String estado = "";
        if (siCheckBox.isSelected() && noCheckBox.isSelected()) {
            aplicarFiltroEstado = false; // Ambos seleccionados, incluye todos los registros
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
        int totalPageCount = (count + registrosPorPagina - 1) / registrosPorPagina;
        if (totalPageCount == 0) {
            totalPageCount = 1;  // Asegura que siempre haya al menos una página
        }

        return totalPageCount;
    }

    // Método para actualizar los botones de paginación basado en la página actual y el total de páginas
    private void actualizarBotonesPaginacion() {
        int totalPaginas = getTotalPageCount();
        botonAtras.setEnabled(pagina > 0);
        botonAdelante.setEnabled((pagina + 1) < totalPaginas);
    }

    // Método para actualizar los datos de la tabla y los controles de paginación
    private void actualizarTabla() {
        listaArreglos.setModel(cargarDatos());
        configurarTablaArreglos();
        actualizarBotonesPaginacion();
        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
    }

    // Método para mostrar todos los registros, seleccionando ambos checkboxes de filtro
    private void mostrarTodos() {
        siCheckBox.setSelected(true);
        noCheckBox.setSelected(true);
        actualizarTabla();
    }

    // Método Principal
    public static void main(String[] args) {
        ListaArreglos listaArreglo = new ListaArreglos();
        listaArreglo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaArreglo.setVisible(true);
    }
}
