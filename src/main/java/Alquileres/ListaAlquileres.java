package Alquileres;
import Login.SesionUsuario;
import Manualidades.TextPrompt;
import Modelos.ModeloAlquileres;
import Modelos.ModeloEvento;
import Objetos.Alquiler;
import Objetos.Conexion;
import Objetos.Evento;
import com.toedter.calendar.JDateChooser;

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListaAlquileres extends JFrame {
    private JPanel panelPrincipal, panelTitulo, panelA, panelB;
    private final JDateChooser fecha_desde,fecha_hasta;
    private JButton botonEditar, botonCrear, botonVer, botonAdelante, botonAtras;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre del cliente, tipo y fecha ", campoBusqueda);
    private JLabel lblPagina;
    private JLabel lblTitulo;
    private JTable listaAlquileres;
    private JPanel panel_fecha;
    private JButton botonDevolucion;
    private List<Alquiler> listaalAlquilers;
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaAlquileres actual = this;
    private String busqueda = "";
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaAlquileres() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        JLabel jl_desde = new JLabel("Desde");
        panel_fecha.add(jl_desde);
        fecha_desde = new JDateChooser(new Date());
        fecha_desde.setDateFormatString("yyyy-MM-dd");
        fecha_desde.setPreferredSize(new Dimension(90, 30));
        panel_fecha.add(fecha_desde);

        JLabel jl_hasta = new JLabel("Hasta");
        panel_fecha.add(jl_hasta);
        fecha_hasta = new JDateChooser(new Date());
        fecha_hasta.setDateFormatString("yyyy-MM-dd");
        fecha_hasta.setPreferredSize(new Dimension(90, 30));
        panel_fecha.add(fecha_hasta);

        panel_fecha.setPreferredSize(new Dimension(270, 30));

        // Obtener la fecha actual
        Date fechaActual = new Date();

        // Restar 2 años a la fecha actual para la fecha "Desde"
        Calendar calDesde = Calendar.getInstance();
        calDesde.setTime(fechaActual);
        calDesde.add(Calendar.YEAR, -2);
        Date fechaDesdeDosAniosAntes = calDesde.getTime();

        // Sumar 30 días a la fecha actual para la fecha "Desde"
        Calendar calHasta = Calendar.getInstance();
        calHasta.setTime(fechaActual);
        calHasta.add(Calendar.MONTH, +1);
        Date FechaHasta30DiasDespues = calHasta.getTime();

        // Establecer las fechas en los componentes JDateChooser
        fecha_desde.setDate(fechaDesdeDosAniosAntes);
        fecha_hasta.setDate(FechaHasta30DiasDespues);

        listaAlquileres.setModel(cargarDatos());
        configurarTablaManualidades();

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
                listaAlquileres.setModel(cargarDatos());
                configurarTablaManualidades();
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
                listaAlquileres.setModel(cargarDatos());
                configurarTablaManualidades();
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
                listaAlquileres.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        fecha_desde.getDateEditor().getUiComponent().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                busqueda = campoBusqueda.getText();
                pagina = 0;
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaAlquileres.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        fecha_hasta.getDateEditor().getUiComponent().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                busqueda = campoBusqueda.getText();
                pagina = 0;
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaAlquileres.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });


        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               CrearAlquileres crearAlquileres = new CrearAlquileres();
                crearAlquileres.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaAlquileres.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
               VerAlquileres verAlquileres = new VerAlquileres(listaalAlquilers.get(listaAlquileres.getSelectedRow()).getId());
               verAlquileres.setVisible(true);
                actual.dispose();
            }
        });

        botonDevolucion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaAlquileres.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                if (listaalAlquilers.get(listaAlquileres.getSelectedRow()).getActivo().equals("I")) {
                    mostrarDialogoPersonalizadoAtencion("El mobiliario de este alquiler ya fue regresado.", Color.decode("#F57F17"));
                    return;
                }

                DevolucionesAlquileres devolucionesAlquileres = new DevolucionesAlquileres(listaalAlquilers.get(listaAlquileres.getSelectedRow()).getId());
                devolucionesAlquileres.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaAlquileres.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
               EditarAlquileres editarAlquileres = new EditarAlquileres(listaalAlquilers.get(listaAlquileres.getSelectedRow()).getId());
                editarAlquileres.setVisible(true);
                actual.dispose();
            }
        });

        JTableHeader header = listaAlquileres.getTableHeader();
        header.setForeground(Color.WHITE);

        int campoBusquedaHeight = 35;
        campoBusqueda.setPreferredSize(new Dimension(campoBusqueda.getPreferredSize().width, campoBusquedaHeight));

        panelPrincipal.setBackground(primaryColor);
        panelTitulo.setBackground(primaryColor);
        panelA.setBackground(primaryColor);
        panelB.setBackground(primaryColor);
        campoBusqueda.setBackground(Color.WHITE);
        botonAdelante.setBackground(darkColor);
        botonAtras.setBackground(darkColor);
        botonVer.setBackground(darkColor);
        botonEditar.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        botonDevolucion.setBackground(darkColor);
        header.setBackground(darkColor);

        botonAdelante.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonVer.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);
        botonDevolucion.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
        placeholder.setForeground(darkColor);
        lblPagina.setForeground(Color.WHITE);

        campoBusqueda.setFont(font);
        botonAdelante.setFont(font);
        botonAtras.setFont(font);
        botonVer.setFont(font);
        botonCrear.setFont(font);
        botonEditar.setFont(font);
        botonDevolucion.setFont(font);
        placeholder.setFont(font);
        lblPagina.setFont(font);
        lblTitulo.setFont(fontTitulo);

        botonAdelante.setFocusable(false);
        botonAtras.setFocusable(false);
        botonCrear.setFocusable(false);
        botonVer.setFocusable(false);
        botonEditar.setFocusable(false);
        botonDevolucion.setFocusable(false);

    }

    private void configurarTablaManualidades() {
        TableColumnModel columnModel = listaAlquileres.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(220);
        columnModel.getColumn(2).setPreferredWidth(220);
        columnModel.getColumn(3).setPreferredWidth(180);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(80);

        columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new LeftAlignedRenderer());
    }

    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class RightAlignedRenderer extends DefaultTableCellRenderer {
        public RightAlignedRenderer() {
            setHorizontalAlignment(RIGHT);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;
        }
    }

    private ModeloAlquileres cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT e.*, CONCAT(c.nombre, ' ', c.apellido) AS nombre_completo " +
                             "FROM alquileres e " +
                             "JOIN clientes c ON e.cliente_id = c.id " +
                             "WHERE (e.tipo LIKE CONCAT('%', ?, '%') " +
                             "OR CONCAT(c.nombre, ' ', c.apellido) LIKE CONCAT('%', ?, '%') " +
                             "OR e.descripcion LIKE CONCAT('%', ?, '%') " +
                             "OR DATE_FORMAT(e.fecha, '%d de %M %Y') LIKE CONCAT('%', ?, '%')) " +
                             "AND (e.fecha BETWEEN ? AND ?) " +
                             "LIMIT ?, 20"
             )
        ){
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);
            preparedStatement.setString(4, busqueda);
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd"); // Define el formato de fecha deseado
            String fechaDesde = formato.format(fecha_desde.getDate()); // Convierte la fecha a una cadena de texto en el formato especificado
            String fechaHasta = formato.format(fecha_hasta.getDate());
            preparedStatement.setString(5, fechaDesde);
            preparedStatement.setString(6, fechaHasta);
            preparedStatement.setInt(7, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaalAlquilers = new ArrayList<>();

            while (resultSet.next()) {
                Alquiler alquiler = new Alquiler();
                alquiler.setId(resultSet.getInt("id"));
                alquiler.setClienteId(resultSet.getInt("cliente_id"));
                alquiler.setDescripcion(resultSet.getString("descripcion"));
                alquiler.setTipo(resultSet.getString("tipo"));
                alquiler.setFecha(resultSet.getDate("fecha"));
                alquiler.setInicio(resultSet.getTime("hora_inicial"));
                alquiler.setFin(resultSet.getTime("hora_final"));
                alquiler.setActivo(resultSet.getString("activo"));
                listaalAlquilers.add(alquiler);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos", Color.decode("#C62828"));
            listaalAlquilers = new ArrayList<>();
        }

        if (listaAlquileres.getColumnCount() > 0) {
            TableColumn columnId = listaAlquileres.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloAlquileres(listaalAlquilers, sql);
    }

    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT COUNT(*) AS total " +
                             "FROM alquileres e " +
                             "JOIN clientes c ON e.cliente_id = c.id " +
                             "WHERE  (e.tipo LIKE CONCAT('%', ?, '%') " +
                             "OR CONCAT(c.nombre, ' ', c.apellido) LIKE CONCAT('%', ?, '%') " +
                             "OR e.descripcion LIKE CONCAT('%', ?, '%') " +
                             "OR DATE_FORMAT(e.fecha, '%d de %M %Y') LIKE CONCAT('%', ?, '%')) " +
                             "AND (e.fecha BETWEEN ? AND ?)"
             )) {

            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);
            preparedStatement.setString(4, busqueda);
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDesde = formato.format(fecha_desde.getDate());
            String fechaHasta = formato.format(fecha_hasta.getDate());
            preparedStatement.setString(5, fechaDesde);
            preparedStatement.setString(6, fechaHasta);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos", Color.decode("#C62828"));
        }

        int totalPageCount = count / 20;

        if (count % 20 > 0) {
            totalPageCount++;
        }

        return totalPageCount;
    }

    public void mostrarDialogoPersonalizadoError(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    public void mostrarDialogoPersonalizadoAtencion(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        ListaAlquileres listaAlquileres = new ListaAlquileres();
        listaAlquileres.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaAlquileres.setVisible(true);
    }
}
