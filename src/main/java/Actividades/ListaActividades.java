/**
 * ListaActividades.java
 *
 * Lista de Actividades
 *
 * @author Dania Lagos
 * @version 1.0
 * @since 2024-05-05
 */

package Actividades;

import Modelos.ModeloActividad;
import Objetos.Actividad;
import Objetos.Conexion;
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

public class ListaActividades extends JFrame {
    // Paneles
    private JPanel panelPrincipal; // Panel principal que contiene todos los componentes
    private JPanel panelTitulo; // Panel para el título
    private JPanel panelA; // Panel A
    private JPanel panelB; // Panel B

    // Componentes de fecha
    private final JDateChooser fecha_desde; // Selector de fecha desde
    private final JDateChooser fecha_hasta; // Selector de fecha hasta

    // Botones
    private JButton botonEditar; // Botón para editar
    private JButton botonCrear; // Botón para crear
    private JButton botonVer; // Botón para ver
    private JButton botonAdelante; // Botón para avanzar página
    private JButton botonAtras; // Botón para retroceder página
    private JButton botonCambiar; // Botón para cambiar (¿Cambiar qué?)

    // Campo de búsqueda
    private JTextField campoBusqueda; // Campo de búsqueda
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre, descripción ó fecha", campoBusqueda);

    // Etiquetas
    private JLabel lblPagina; // Etiqueta para mostrar la página actual
    private JLabel lblTitulo; // Etiqueta para el título

    // Tabla
    private JTable listaActividades; // Tabla para mostrar las actividades

    // Otros componentes
    private JPanel panel_fecha; // Panel para las fechas (¿Fecha desde y hasta?)
    private List<Actividad> listaActividad; // Lista de actividades
    private int pagina = 0; // Número de página actual
    private Connection mysql; // Conexión a la base de datos MySQL
    private Conexion sql; // Clase de conexión (¿A qué tipo de base de datos?)
    private ListaActividades actual = this; // Instancia de la clase ListaActividades actual
    private String busqueda = ""; // Término de búsqueda

    // Fuentes y colores
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17); // Fuente para los títulos
    Font font = new Font("Century Gothic", Font.BOLD, 11); // Fuente para otros componentes

    Color primaryColor = Color.decode("#37474f"); // Color primario (gris azul oscuro)
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaActividades() {
        super("");
        setSize(920, 505);
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

        // Establecer las fechas predeterminadas en los componentes JDateChooser
        fecha_desde.setDate(fechaDesdeDosAniosAntes);
        fecha_hasta.setDate(FechaHasta30DiasDespues);

        // Establece el modelo de datos para la lista de actividades utilizando los datos obtenidos mediante el método cargarDatos().
        listaActividades.setModel(cargarDatos());

        // Configura la apariencia y propiedades de la tabla de actividades de manualidades.
        configurarTablaManualidades();

        // Actualiza la etiqueta de página para mostrar la página actual y el total de páginas disponibles.
        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

        // Acción realizada al presionar el botón para avanzar a la siguiente página de actividades
        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Incrementa la página actual si hay más páginas disponibles
                if ((pagina + 1) < getTotalPageCount()) {
                    pagina++;
                    botonAtras.setEnabled(true);
                    if ((pagina + 1) == getTotalPageCount()) {
                        botonAdelante.setEnabled(false);
                    }
                }
                // Actualiza la tabla y la etiqueta de página
                listaActividades.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        // Acción realizada al presionar el botón para retroceder a la página anterior de actividades
        botonAtras.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Decrementa la página actual si no es la primera página
                if (pagina > 0) {
                    pagina--;
                    botonAdelante.setEnabled(true);
                    if (pagina == 0) {
                        botonAtras.setEnabled(false);
                    }
                }
                // Actualiza la tabla y la etiqueta de página
                listaActividades.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        // Acción realizada al ingresar texto en el campo de búsqueda de actividades o modificar las fechas
        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                // Actualiza la búsqueda y reinicia la paginación
                busqueda = campoBusqueda.getText();
                pagina = 0;
                // Actualiza la disponibilidad de los botones de navegación, la tabla y la etiqueta de página
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaActividades.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        // Acción realizada al cambiar la fecha de inicio de actividades
        fecha_desde.getDateEditor().getUiComponent().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                // Actualiza la búsqueda y reinicia la paginación al cambiar la fecha
                busqueda = campoBusqueda.getText();
                pagina = 0;
                // Actualiza la disponibilidad de los botones de navegación, la tabla y la etiqueta de página
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaActividades.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        // Acción realizada al cambiar la fecha de fin de actividades
        fecha_hasta.getDateEditor().getUiComponent().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                // Actualiza la búsqueda y reinicia la paginación al cambiar la fecha
                busqueda = campoBusqueda.getText();
                pagina = 0;
                // Actualiza la disponibilidad de los botones de navegación, la tabla y la etiqueta de página
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                listaActividades.setModel(cargarDatos());
                configurarTablaManualidades();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        // Acción realizada al presionar el botón para crear una nueva actividad
        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre la ventana para crear una nueva actividad y cierra la ventana actual
                CrearActividad crearActividad = new CrearActividad();
                crearActividad.setVisible(true);
                actual.dispose();
            }
        });

        // Acción realizada al presionar el botón para ver los detalles de una actividad seleccionada
        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Verifica si se ha seleccionado una actividad y muestra sus detalles
                if (listaActividades.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                VerActividad verActividades = new VerActividad(listaActividad.get(listaActividades.getSelectedRow()).getId());
                verActividades.setVisible(true);
                actual.dispose();
            }
        });

        // Acción realizada al presionar el botón para cambiar al calendario de actividades
        botonCambiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abre el calendario de actividades y cierra la ventana actual
                CalendarioDeActividades calendarioDeActividades = new CalendarioDeActividades();
                calendarioDeActividades.setVisible(true);
                dispose();
            }
        });

        // Configura el color del texto del encabezado de la tabla de actividades
        JTableHeader header = listaActividades.getTableHeader();
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
        botonCambiar.setBackground(darkColor);
        botonVer.setBackground(darkColor);
        botonEditar.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        header.setBackground(darkColor);

        botonAdelante.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonVer.setForeground(Color.WHITE);
        botonCambiar.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
        placeholder.setForeground(darkColor);
        lblPagina.setForeground(Color.WHITE);

        campoBusqueda.setFont(font);
        botonAdelante.setFont(font);
        botonAtras.setFont(font);
        botonCambiar.setFont(font);
        botonVer.setFont(font);
        botonCrear.setFont(font);
        botonEditar.setFont(font);
        placeholder.setFont(font);
        lblPagina.setFont(font);
        lblTitulo.setFont(fontTitulo);

        botonAdelante.setFocusable(false);
        botonAtras.setFocusable(false);
        botonCrear.setFocusable(false);
        botonVer.setFocusable(false);
        botonEditar.setFocusable(false);
    }

    // Método para configurar el tamaño y alineación de las columnas en una tabla de actividades
    private void configurarTablaManualidades() {
        TableColumnModel columnModel = listaActividades.getColumnModel();

        // Establece el ancho preferido para cada columna
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(250);
        columnModel.getColumn(3).setPreferredWidth(150);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(80);

        // Establece alineación de los datos en las celdas
        columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new LeftAlignedRenderer());
    }

    // Clase para alinear texto a la izquierda en celdas de una tabla
    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT);  // Establece alineación a la izquierda
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;  // Devuelve la celda configurada
        }
    }

    // Clase para alinear texto al centro en celdas de una tabla
    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER);  // Establece alineación al centro
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return cell;  // Devuelve la celda configurada
        }
    }

    // Método para cargar datos de actividades desde la base de datos
    private ModeloActividad cargarDatos() {
        sql = new Conexion();  // Establece conexión con la base de datos
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT a.* " +
                             "FROM actividades a " +
                             "WHERE (a.nombre LIKE CONCAT('%', ?, '%') " +
                             "OR a.descripcion LIKE CONCAT('%', ?, '%') " +
                             "OR DATE_FORMAT(a.fecha, '%d de %M %Y') LIKE CONCAT('%', ?, '%')) " +
                             "AND (a.fecha BETWEEN ? AND ?) " +
                             "LIMIT ?, 20"
             )
        ) {
            // Configuración de los parámetros de búsqueda y filtrado
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDesde = formato.format(fecha_desde.getDate());
            String fechaHasta = formato.format(fecha_hasta.getDate());
            preparedStatement.setString(4, fechaDesde);
            preparedStatement.setString(5, fechaHasta);
            preparedStatement.setInt(6, pagina * 20);

            // Ejecución de consulta y manejo de resultados
            ResultSet resultSet = preparedStatement.executeQuery();
            listaActividad = new ArrayList<>();

            while (resultSet.next()) {
                Actividad actividad = new Actividad();
                actividad.setId(resultSet.getInt("id"));
                actividad.setNombre(resultSet.getString("nombre"));
                actividad.setDescripcion(resultSet.getString("descripcion"));
                actividad.setDireccion(resultSet.getString("direccion"));
                actividad.setFecha(resultSet.getDate("fecha"));
                actividad.setInicio(resultSet.getTime("inicio"));
                actividad.setFin(resultSet.getTime("fin"));
                listaActividad.add(actividad);
            }

            // Manejo de la visibilidad de la tabla en base a los resultados obtenidos
            if (listaActividad.isEmpty()) {
                listaActividades.setVisible(false);
                JOptionPane.showMessageDialog(null, "No hay resultados para esta búsqueda");
            } else {
                listaActividades.setVisible(true);
                if (listaActividades.getColumnCount() > 0) {
                    TableColumn columnId = listaActividades.getColumnModel().getColumn(0);
                    columnId.setPreferredWidth(50);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            listaActividad = new ArrayList<>();
        }

        if (listaActividades.getColumnCount() > 0) {
            TableColumn columnId = listaActividades.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloActividad(listaActividad, sql);
    }

    // Método para calcular el número total de páginas necesarias para mostrar todas las actividades
    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT COUNT(*) AS total " +
                             "FROM actividades a " +
                             "WHERE (a.nombre LIKE CONCAT('%', ?, '%') " +
                             "OR a.descripcion LIKE CONCAT('%', ?, '%') " +
                             "OR DATE_FORMAT(a.fecha, '%d de %M %Y') LIKE CONCAT('%', ?, '%')) " +
                             "AND (a.fecha BETWEEN ? AND ?)"
             )) {
            // Configuración de los parámetros de búsqueda y filtrado
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDesde = formato.format(fecha_desde.getDate());
            String fechaHasta = formato.format(fecha_hasta.getDate());
            preparedStatement.setString(4, fechaDesde);
            preparedStatement.setString(5, fechaHasta);

            // Ejecución de consulta y manejo de resultados
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

    // Método Principal
    public static void main(String[] args) {
        ListaActividades listaActividades = new ListaActividades();
        listaActividades.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaActividades.setVisible(true);
    }
}
