/**
 * ListaEventos.java
 *
 * Lista de Eventos
 *
 * @author Lisbeth David
 * @version 1.0
 * @since 2024-05-05
 */

package Eventos;

import Manualidades.TextPrompt;
import Modelos.ModeloEvento;
import Objetos.Conexion;
import Objetos.Evento;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.*;
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

public class ListaEventos extends JFrame {
    // Paneles
    private JPanel panelPrincipal;
    private JPanel panelTitulo;
    private JPanel panelA;
    private JPanel panelB;
    private JPanel panel_fecha;

    // Componentes de fecha
    private final JDateChooser fecha_desde;
    private final JDateChooser fecha_hasta;

    // Botones
    private JButton botonEditar;
    private JButton botonCrear;
    private JButton botonVer;
    private JButton botonAdelante;
    private JButton botonAtras;

    // Campo de búsqueda
    private JTextField campoBusqueda;

    // Placeholder para el campo de búsqueda
    private TextPrompt placeholder = new TextPrompt(" Buscar por nombre del cliente, fecha ó tipo", campoBusqueda);

    // Etiquetas
    private JLabel lblPagina;
    private JLabel lblTitulo;

    // Tabla de eventos
    private JTable listaEventos;

    // Lista de eventos
    private List<Evento> listaEvento;

    // Otras variables
    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaEventos actual = this;
    private String busqueda = "";
    private Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    private Font font = new Font("Century Gothic", Font.BOLD, 11);
    private Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    private Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    private Color darkColor = Color.decode("#263238"); // Gris azul más oscuro
    private Color darkColorRed = new Color(244, 67, 54);
    private Color darkColorBlue = new Color(33, 150, 243);

    public ListaEventos() {
        super("");
        setSize(950, 505);
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

        JTableHeader header = listaEventos.getTableHeader();
        header.setForeground(Color.WHITE);
        listaEventos.setModel(cargarDatos());
        configurarTablaManualidades();
        listaEventos.getColumnModel().getColumn(7).setCellRenderer(new ListaEventos.ButtonRenderer());
        listaEventos.getColumnModel().getColumn(7).setCellEditor(new ListaEventos.ButtonEditor());

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
                listaEventos.setModel(cargarDatos());
                configurarTablaManualidades();
                listaEventos.getColumnModel().getColumn(7).setCellRenderer(new ListaEventos.ButtonRenderer());
                listaEventos.getColumnModel().getColumn(7).setCellEditor(new ListaEventos.ButtonEditor());
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
                listaEventos.setModel(cargarDatos());
                configurarTablaManualidades();
                listaEventos.getColumnModel().getColumn(7).setCellRenderer(new ListaEventos.ButtonRenderer());
                listaEventos.getColumnModel().getColumn(7).setCellEditor(new ListaEventos.ButtonEditor());
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
                listaEventos.setModel(cargarDatos());
                configurarTablaManualidades();
                listaEventos.getColumnModel().getColumn(7).setCellRenderer(new ListaEventos.ButtonRenderer());
                listaEventos.getColumnModel().getColumn(7).setCellEditor(new ListaEventos.ButtonEditor());
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
                listaEventos.setModel(cargarDatos());
                configurarTablaManualidades();
                listaEventos.getColumnModel().getColumn(7).setCellRenderer(new ListaEventos.ButtonRenderer());
                listaEventos.getColumnModel().getColumn(7).setCellEditor(new ListaEventos.ButtonEditor());
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
                listaEventos.setModel(cargarDatos());
                configurarTablaManualidades();
                listaEventos.getColumnModel().getColumn(7).setCellRenderer(new ListaEventos.ButtonRenderer());
                listaEventos.getColumnModel().getColumn(7).setCellEditor(new ListaEventos.ButtonEditor());

                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });


        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearEvento crearEvento = new CrearEvento();
                crearEvento.setVisible(true);
                actual.dispose();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaEventos.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerEventos verEventos = new VerEventos(listaEvento.get(listaEventos.getSelectedRow()).getId());
                verEventos.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaEventos.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                EditarEvento editarEvento = new EditarEvento(listaEvento.get(listaEventos.getSelectedRow()), listaEvento.get(listaEventos.getSelectedRow()).getId());
                editarEvento.setVisible(true);
                actual.dispose();
            }
        });

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
        header.setBackground(darkColor);

        botonAdelante.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonVer.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
        placeholder.setForeground(darkColor);
        lblPagina.setForeground(Color.WHITE);

        campoBusqueda.setFont(font);
        botonAdelante.setFont(font);
        botonAtras.setFont(font);
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

    // Método para configurar las columnas y sus renderizadores en la tabla de eventos
    private void configurarTablaManualidades() {
        TableColumnModel columnModel = listaEventos.getColumnModel();

        // Establece el ancho preferido de las columnas
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(180);
        columnModel.getColumn(3).setPreferredWidth(160);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(80);
        columnModel.getColumn(6).setPreferredWidth(100);
        columnModel.getColumn(7).setPreferredWidth(100);

        // Asigna renderizadores para alinear el contenido de las celdas
        columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(6).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(7).setCellRenderer(new LeftAlignedRenderer());
    }

    // Clase para alinear texto a la izquierda en celdas de una tabla
    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT); // Alineación a la izquierda
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Clase para alinear texto al centro en celdas de una tabla
    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER); // Alineación al centro
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Método para cargar datos de eventos desde la base de datos y devolver un modelo de datos
    private ModeloEvento cargarDatos() {
        sql = new Conexion(); // Establece conexión con la base de datos
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT e.*, CONCAT(c.nombre, ' ', c.apellido) AS nombre_completo, " +
                             "CASE " +
                             "    WHEN e.estado IN ('Pendiente', 'En Proceso') THEN e.estado " +
                             "    ELSE NULL " +
                             "END AS estado_filtrado " +
                             "FROM eventos e " +
                             "JOIN clientes c ON e.cliente_id = c.id " +
                             "WHERE (e.estado <> 'Terminado') AND (" +
                             "    e.tipo LIKE CONCAT('%', ?, '%') " +
                             "    OR CONCAT(c.nombre, ' ', c.apellido) LIKE CONCAT('%', ?, '%') " +
                             "    OR e.direccion LIKE CONCAT('%', ?, '%') " +
                             "    OR DATE_FORMAT(e.fecha, '%d de %M %Y') LIKE CONCAT('%', ?, '%')" +
                             ") " +
                             "AND (e.fecha BETWEEN ? AND ?) " +
                             "LIMIT ?, 20"
             )
        ){
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);
            preparedStatement.setString(4, busqueda);
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaDesde = formato.format(fecha_desde.getDate());
            String fechaHasta = formato.format(fecha_hasta.getDate());
            preparedStatement.setString(5, fechaDesde);
            preparedStatement.setString(6, fechaHasta);
            preparedStatement.setInt(7, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            listaEvento = new ArrayList<>();

            while (resultSet.next()) {
                Evento evento = new Evento();
                evento.setId(resultSet.getInt("id"));
                evento.setClienteId(resultSet.getInt("cliente_id"));
                evento.setDireccion(resultSet.getString("direccion"));
                evento.setTipo(resultSet.getString("tipo"));
                evento.setFecha(resultSet.getDate("fecha"));
                evento.setInicio(resultSet.getTime("inicio"));
                evento.setFin(resultSet.getTime("fin"));
                evento.setCodigoEvento(resultSet.getString("codigo_evento"));
                evento.setEstado(resultSet.getString("estado"));
                listaEvento.add(evento);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos", Color.decode("#C62828"));
            listaEvento = new ArrayList<>();
        }

        if (listaEventos.getColumnCount() > 0) {
            TableColumn columnId = listaEventos.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloEvento(listaEvento, sql);
    }

    // Método para calcular el número total de páginas basado en la cantidad de registros
    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT COUNT(*) AS total " +
                             "FROM eventos e " +
                             "JOIN clientes c ON e.cliente_id = c.id " +
                             "WHERE (e.tipo LIKE CONCAT('%', ?, '%') " +
                             "OR CONCAT(c.nombre, ' ', c.apellido) LIKE CONCAT('%', ?, '%') " +
                             "OR e.direccion LIKE CONCAT('%', ?, '%') " +
                             "OR DATE_FORMAT(e.fecha, '%d de %M %Y') LIKE CONCAT('%', ?, '%')) " +
                             "AND (e.fecha BETWEEN ? AND ?);"
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
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }

        int registrosPorPagina = 20;
        int totalPageCount = (count + registrosPorPagina - 1) / registrosPorPagina;
        if (totalPageCount == 0) {
            totalPageCount = 1;  // Asegura que siempre haya al menos una página.
        }

        return totalPageCount;
    }

    // Renderizador de celda que actúa como un botón
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true); // Asegura que el color de fondo se muestre correctamente
            setForeground(Color.WHITE); // Establece el color del texto del botón
            setBackground(darkColor); // Establece el color de fondo del botón
            setFocusPainted(false); // Desactiva el borde de foco para mejorar la estética
        }

        // Método para personalizar el componente de la celda, asignando un texto estático
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Comenzar"); // Establece el texto del botón
            return this; // Devuelve el botón como componente de renderizado
        }
    }

    // Editor de celda que también actúa como un botón
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;
        private int row, col;
        private JTable table;

        public ButtonEditor() {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(this); // Registra el botón para recibir eventos de acción
            button.setForeground(Color.WHITE); // Establece el color del texto
            button.setBackground(darkColor); // Establece el color de fondo
            button.setFocusPainted(false); // Desactiva el borde de foco
        }

        // Método para obtener el componente del editor, que es un botón con un texto fijo
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("Comenzar"); // Establece el texto del botón
            this.table = table;
            this.row = row;
            this.col = column;
            return button;
        }

        // Devuelve el valor de la celda que en este caso es constante
        public Object getCellEditorValue() {
            return "Comenzar";
        }

        // Maneja los eventos de clic en el botón, iniciando una acción
        public void actionPerformed(ActionEvent e) {
            // Lógica personalizada para manejar acciones al hacer clic en el botón
            // Incluye operaciones de base de datos y actualización de la interfaz de usuario
        }
    }

    // Método para actualizar el estado de un evento en la base de datos y reflejarlo en la interfaz de usuario
    private String actualizarEstadoEvento(Evento evento, ModeloEvento eventoModel, int modelRow) {
        String nuevoEstado = "";
        String estadoActual = evento.getEstado();
        int eventoId = evento.getId();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE eventos SET estado = ? WHERE id = ?")) {
            if ("Pendiente".equals(estadoActual)) {
                nuevoEstado = "En Proceso";
            } else if ("En Proceso".equals(estadoActual)) {
                nuevoEstado = "Terminado";
            }

            preparedStatement.setString(1, nuevoEstado);
            preparedStatement.setInt(2, eventoId);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                evento.setEstado(nuevoEstado);
                eventoModel.fireTableRowsUpdated(modelRow, modelRow);
            } else {
                return "No actualizado";
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "Error";
        }

        return nuevoEstado;
    }

    // Método para mostrar un diálogo personalizado de éxito
    public void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado "OK"
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Establece el color de fondo del botón
        btnAceptar.setForeground(Color.WHITE); // Establece el color del texto del botón
        btnAceptar.setFocusPainted(false); // Elimina el borde del foco alrededor del botón

        // Crea un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(
                mensaje,                             // Texto del mensaje a mostrar
                JOptionPane.INFORMATION_MESSAGE,     // Tipo de mensaje (información)
                JOptionPane.DEFAULT_OPTION,          // Opción por defecto
                null,                                // Sin icono
                new Object[]{},                      // Sin opciones estándar
                null                                 // Sin valor inicial
        );

        // Configura el JOptionPane para usar el botón personalizado
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón para cerrar el diálogo cuando se presione
        btnAceptar.addActionListener(e -> dialog.dispose());

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    // Método para mostrar un diálogo personalizado de error
    public void mostrarDialogoPersonalizadoError(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado "OK"
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Establece el color de fondo del botón
        btnAceptar.setForeground(Color.WHITE); // Establece el color del texto del botón
        btnAceptar.setFocusPainted(false); // Elimina el borde del foco alrededor del botón

        // Crea un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(
                mensaje,                             // Texto del mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,         // Tipo de mensaje (advertencia)
                JOptionPane.DEFAULT_OPTION,          // Opción por defecto
                null,                                // Sin icono
                new Object[]{},                      // Sin opciones estándar
                null                                 // Sin valor inicial
        );

        // Configura el JOptionPane para usar el botón personalizado
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón para cerrar el diálogo cuando se presione
        btnAceptar.addActionListener(e -> dialog.dispose());

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    // Método para mostrar un diálogo personalizado de atención
    public void mostrarDialogoPersonalizadoAtencion(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado "OK"
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Establece el color de fondo del botón
        btnAceptar.setForeground(Color.WHITE); // Establece el color del texto del botón
        btnAceptar.setFocusPainted(false); // Elimina el borde del foco alrededor del botón

        // Crea un JOptionPane para mostrar el mensaje
        JOptionPane optionPane = new JOptionPane(
                mensaje,                             // Texto del mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,         // Tipo de mensaje (advertencia)
                JOptionPane.DEFAULT_OPTION,          // Opción por defecto
                null,                                // Sin icono
                new Object[]{},                      // Sin opciones estándar
                null                                 // Sin valor inicial
        );

        // Configura el JOptionPane para usar el botón personalizado
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón para cerrar el diálogo cuando se presione
        btnAceptar.addActionListener(e -> dialog.dispose());

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    // Método Principal
    public static void main(String[] args) {
        ListaEventos listaEventos = new ListaEventos();
        listaEventos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaEventos.setVisible(true);
    }
}
