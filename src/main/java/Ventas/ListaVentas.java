package Ventas;
import Modelos.ModeloCompra;
import Modelos.ModeloVenta;
import Modelos.ModeloVentaDetalle;
import Objetos.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
public class ListaVentas extends JFrame {
    private JPanel panelPrincipal;
    private JTable listaVentas;
    private JButton botonAtras,botonAdelante, botonCrear, botonImprimir;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt(" Buscar por código de venta, fecha ó nombre de cliente", campoBusqueda);
    private JLabel lbl0, lblPagina;
    private JComboBox fechaComboBox;
    private JButton botonVer;
    private JPanel panelB;
    private JPanel panelA;
    private JPanel panelTitulo;
    private List<Venta> ventaList;
    private int pagina = 0;
    private static Conexion sql;
    private String busqueda = "";
    private ListaVentas actual = this;

    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaVentas() {
        super("");
        setSize(990, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        ventaList = new ArrayList<>();
        listaVentas.setModel(cargarDatos());
        configurarTablaVentas();

        String mesSeleccionado = (String) fechaComboBox.getSelectedItem();
        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount(mesSeleccionado));

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        comboBoxModel.addElement("Todos");
        comboBoxModel.addElement("Enero");
        comboBoxModel.addElement("Febrero");
        comboBoxModel.addElement("Marzo");
        comboBoxModel.addElement("Abril");
        comboBoxModel.addElement("Mayo");
        comboBoxModel.addElement("Junio");
        comboBoxModel.addElement("Julio");
        comboBoxModel.addElement("Agosto");
        comboBoxModel.addElement("Septiembre");
        comboBoxModel.addElement("Octubre");
        comboBoxModel.addElement("Noviembre");
        comboBoxModel.addElement("Diciembre");

        fechaComboBox.setModel(comboBoxModel);

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
                listaVentas.setModel(cargarDatos());
                configurarTablaVentas();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount(mesSeleccionado));
            }
        });

        botonAdelante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ((pagina + 1) < getTotalPageCount(mesSeleccionado)) {
                    pagina++;
                    botonAtras.setEnabled(true);
                    if ((pagina + 1) == getTotalPageCount(mesSeleccionado)) {
                        botonAdelante.setEnabled(false);
                    }
                }
                listaVentas.setModel(cargarDatos());
                configurarTablaVentas();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount(mesSeleccionado));
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0;
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount(mesSeleccionado));
                botonAtras.setEnabled(pagina > 0);
                listaVentas.setModel(cargarDatos());
                configurarTablaVentas();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount(mesSeleccionado));
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearVenta crearVenta = new CrearVenta();
                crearVenta.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(listaVentas); // Obtén la ventana que contiene la lista de compras
                if (frame != null) {
                    frame.dispose(); // Cierra la ventana
                }
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaVentas.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerVentas ventas = new VerVentas(ventaList.get(listaVentas.getSelectedRow()).getId());
                ventas.setVisible(true);
                actual.dispose();
            }
        });

        botonImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaVentas.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }

                int filaSeleccionada = listaVentas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int indiceItemSeleccionado = listaVentas.convertRowIndexToModel(filaSeleccionada);
                    imprimirFactura(ventaList.get(indiceItemSeleccionado).getCodigoVenta());
                }
            }
        });

        JTableHeader header = listaVentas.getTableHeader();
        header.setForeground(Color.WHITE);

        int campoBusquedaHeight = 35;
        campoBusqueda.setPreferredSize(new Dimension(campoBusqueda.getPreferredSize().width, campoBusquedaHeight));

        int fechasHeight = 35;
        fechaComboBox.setPreferredSize(new Dimension(fechaComboBox.getPreferredSize().width, fechasHeight));

        panelPrincipal.setBackground(primaryColor);
        panelTitulo.setBackground(primaryColor);
        panelA.setBackground(primaryColor);
        panelB.setBackground(primaryColor);
        header.setBackground(darkColor);
        botonImprimir.setBackground(darkColor);
        botonCrear.setBackground(darkColor);
        botonAdelante.setBackground(darkColor);
        botonAtras.setBackground(darkColor);
        botonVer.setBackground(darkColor);
        fechaComboBox.setBackground(Color.WHITE);
        campoBusqueda.setBackground(Color.WHITE);

        placeholder.setForeground(darkColor);
        fechaComboBox.setForeground(primaryColor);
        botonImprimir.setForeground(Color.WHITE);
        botonVer.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonAdelante.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        campoBusqueda.setForeground(primaryColor);
        lblPagina.setForeground(Color.WHITE);

        campoBusqueda.setFont(font);
        botonAdelante.setFont(font);
        botonVer.setFont(font);
        botonAtras.setFont(font);
        botonCrear.setFont(font);
        botonImprimir.setFont(font);
        fechaComboBox.setFont(font);
        placeholder.setFont(font);
        lbl0.setFont(fontTitulo);
        lblPagina.setFont(font);

        botonAdelante.setFocusable(false);
        botonAtras.setFocusable(false);
        botonCrear.setFocusable(false);
        botonVer.setFocusable(false);
        botonImprimir.setFocusable(false);
        fechaComboBox.setFocusable(false);

        fechaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesSeleccionado = (String) fechaComboBox.getSelectedItem();
                actualizarModeloTablaConMesSeleccionado(mesSeleccionado);
            }
        });
    }

    private void configurarTablaVentas() {
        TableColumnModel columnModel = listaVentas.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(180);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(190);
        columnModel.getColumn(4).setPreferredWidth(190);
        columnModel.getColumn(5).setPreferredWidth(80);
        columnModel.getColumn(6).setPreferredWidth(80);
        columnModel.getColumn(7).setPreferredWidth(80);

        columnModel.getColumn(0).setCellRenderer(new CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new CenterAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(6).setCellRenderer(new LeftAlignedRenderer());
        columnModel.getColumn(7).setCellRenderer(new LeftAlignedRenderer());
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

    private int obtenerNumeroMes(String mesSeleccionado) {
        int numeroMes = 0;
        switch (mesSeleccionado) {
            case "Enero":
                numeroMes = 1;
                break;
            case "Febrero":
                numeroMes = 2;
                break;
            case "Marzo":
                numeroMes = 3;
                break;
            case "Abril":
                numeroMes = 4;
                break;
            case "Mayo":
                numeroMes = 5;
                break;
            case "Junio":
                numeroMes = 6;
                break;
            case "Julio":
                numeroMes = 7;
                break;
            case "Agosto":
                numeroMes = 8;
                break;
            case "Septiembre":
                numeroMes = 9;
                break;
            case "Octubre":
                numeroMes = 10;
                break;
            case "Noviembre":
                numeroMes = 11;
                break;
            case "Diciembre":
                numeroMes = 12;
                break;
            case "Todos":
                numeroMes = 0;
                break;
        }
        return numeroMes;
    }

    private void actualizarModeloTablaConMesSeleccionado(String mesSeleccionado) {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql()) {
            String query = "SELECT c.*, p.nombre" +
                    "FROM ventas c " +
                    "JOIN clientes p ON c.cliente_id = p.id " +
                    "JOIN usuarios e ON c.usuario_id = e.id ";

            boolean hasMesFilter = mesSeleccionado != null && !mesSeleccionado.equals("Todos");
            boolean hasBusquedaFilter = busqueda != null && !busqueda.isEmpty();

            if (hasMesFilter || hasBusquedaFilter) {
                query += "WHERE ";
            }

            if (hasMesFilter) {
                query += "MONTH(c.fecha) = ? ";
            }

            if (hasMesFilter && hasBusquedaFilter) {
                query += "AND ";
            }

            if (hasBusquedaFilter) {
                query += "(c.codigo_venta LIKE CONCAT('%', ?, '%') OR p.nombre LIKE CONCAT('%', ?, '%')) ";
            }

            query += "LIMIT ?, 20";

            PreparedStatement preparedStatement = mysql.prepareStatement(query);

            int parameterIndex = 1;

            if (hasMesFilter) {
                int numeroMes = obtenerNumeroMes(mesSeleccionado);
                preparedStatement.setInt(parameterIndex, numeroMes);
                parameterIndex++;
            }

            if (hasBusquedaFilter) {
                preparedStatement.setString(parameterIndex, busqueda);
                preparedStatement.setString(parameterIndex + 1, busqueda);
                parameterIndex += 2;
            }

            preparedStatement.setInt(parameterIndex, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            ventaList = new ArrayList<>();
            while (resultSet.next()) {
                Venta venta = new Venta();
                venta.setId(resultSet.getInt("id"));
                venta.setCodigoVenta(resultSet.getString("codigo_venta"));
                venta.setFecha(resultSet.getString("fecha"));
                venta.setClienteId(resultSet.getInt("cliente_id"));
                venta.setUsuarioId(resultSet.getInt("usuario_id"));
                ventaList.add(venta);
            }
            listaVentas.setModel(new ModeloVenta(ventaList, sql));
            configurarTablaVentas();
            lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount(mesSeleccionado));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            ventaList = new ArrayList<>();
        }
    }

    private int getTotalPageCount(String mesSeleccionado) {
        int count = 0;
        try (Connection mysql = sql.conectamysql()) {
            String query = "SELECT COUNT(*) AS total FROM " + Venta.nombreTabla;
            if (mesSeleccionado != null && !mesSeleccionado.equals("Todos")) {
                query += " WHERE MONTH(fecha) = ? AND codigo_venta LIKE CONCAT('%', ?, '%')";
            } else {
                query += " WHERE codigo_venta LIKE CONCAT('%', ?, '%')";
            }
            PreparedStatement preparedStatement = mysql.prepareStatement(query);
            if (mesSeleccionado != null && !mesSeleccionado.equals("Todos")) {
                int numeroMes = obtenerNumeroMes(mesSeleccionado);
                preparedStatement.setInt(1, numeroMes);
                preparedStatement.setString(2, busqueda);
            } else {
                preparedStatement.setString(1, busqueda);
            }
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }
            int totalPageCount = (int) Math.ceil((double) count / 20);
        return totalPageCount;
    }

    public static double calcularSubtotal(List<VentaDetalle> detalles) {
        double subtotal = 0.0;

        for (VentaDetalle detalle : detalles) {
            double precioDetalle = obtenerPrecioProducto(detalle.getId(), sql);
            subtotal += (detalle.getCantidad() * precioDetalle) * 0.85;
        }

        return subtotal;
    }

    public static double calcularISV(List<VentaDetalle> detalles) {
        double isv = 0.0;

        for (VentaDetalle detalle : detalles) {
            double precioDetalle = obtenerPrecioProducto(detalle.getId(), sql);
            isv += (detalle.getCantidad() * precioDetalle) * 0.15;
        }
        return isv;
    }

    private static double calcularTotal(List<VentaDetalle> detalles) {
        double subtotal = calcularSubtotal(detalles);
        double isv = calcularISV(detalles);
        return subtotal + isv;
    }

    public static double obtenerPrecioProducto(int detalleId, Conexion sql) {
        double precioProducto = 0.0;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT tipo_detalle, detalle_id FROM detalles_ventas WHERE id = ?")) {

            preparedStatement.setInt(1, detalleId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String tipoDetalle = rs.getString("tipo_detalle");
                int productoId = rs.getInt("detalle_id");

                switch (tipoDetalle) {
                    case "material":
                        try (PreparedStatement materialStatement = connection.prepareStatement(
                                "SELECT precio FROM materiales WHERE id = ?")) {

                            materialStatement.setInt(1, productoId);
                            ResultSet materialRs = materialStatement.executeQuery();
                            if (materialRs.next()) {
                                precioProducto = materialRs.getDouble("precio");
                            }
                        }
                        break;
                    case "floristeria":
                        try (PreparedStatement floristeriaStatement = connection.prepareStatement(
                                "SELECT precio FROM floristeria WHERE id = ?")) {

                            floristeriaStatement.setInt(1, productoId);
                            ResultSet floristeriaRs = floristeriaStatement.executeQuery();
                            if (floristeriaRs.next()) {
                                precioProducto = floristeriaRs.getDouble("precio");
                            }
                        }
                        break;
                    case "tarjeta":
                        try (PreparedStatement tarjetaStatement = connection.prepareStatement(
                                "SELECT precio_tarjeta FROM tarjetas WHERE id = ?")) {

                            tarjetaStatement.setInt(1, productoId);
                            ResultSet tarjetaRs = tarjetaStatement.executeQuery();
                            if (tarjetaRs.next()) {
                                precioProducto = tarjetaRs.getDouble("precio_tarjeta");
                            }
                        }
                        break;
                    case "manualidad":
                        try (PreparedStatement manualidadStatement = connection.prepareStatement(
                                "SELECT precio_manualidad FROM manualidades WHERE id = ?")) {

                            manualidadStatement.setInt(1, productoId);
                            ResultSet manualidadRs = manualidadStatement.executeQuery();
                            if (manualidadRs.next()) {
                                precioProducto = manualidadRs.getDouble("precio_manualidad");
                            }
                        }
                        break;
                    case "arreglo":
                        try (PreparedStatement arregloStatement = connection.prepareStatement(
                                "SELECT precio FROM arreglos WHERE id = ?")) {

                            arregloStatement.setInt(1, productoId);
                            ResultSet arregloRs = arregloStatement.executeQuery();
                            if (arregloRs.next()) {
                                precioProducto = arregloRs.getDouble("precio");
                            }
                        }
                        break;
                    case "desayuno":
                        try (PreparedStatement desayunoStatement = connection.prepareStatement(
                                "SELECT precio_desayuno FROM desayunos WHERE id = ?")) {

                            desayunoStatement.setInt(1, productoId);
                            ResultSet desayunoRs = desayunoStatement.executeQuery();
                            if (desayunoRs.next()) {
                                precioProducto = desayunoRs.getDouble("precio_desayuno");
                            }
                        }
                        break;

                    case "globo":
                        try (PreparedStatement globoStatement = connection.prepareStatement(
                                "SELECT precio FROM globos WHERE id = ?")) {

                            globoStatement.setInt(1, productoId);
                            ResultSet globoRs = globoStatement.executeQuery();
                            if (globoRs.next()) {
                                precioProducto = globoRs.getDouble("precio");
                            }
                        }
                        break;

                    case "resumen_promocion":
                        try (PreparedStatement promocionStatement = connection.prepareStatement(
                                "SELECT promocion FROM resumen_promociones WHERE id = ?")) {

                            promocionStatement.setInt(1, productoId);
                            ResultSet promocionRs = promocionStatement.executeQuery();
                            if (promocionRs.next()) {
                                precioProducto = promocionRs.getDouble("promocion");
                            }
                        }
                        break;

                    case "mobiliario":
                        try (PreparedStatement mobiliarioStatement = connection.prepareStatement(
                                "SELECT precioUnitario FROM mobiliario WHERE id = ?")) {

                            mobiliarioStatement.setInt(1, productoId);
                            ResultSet mobiliarioRs = mobiliarioStatement.executeQuery();
                            if (mobiliarioRs.next()) {
                                precioProducto = mobiliarioRs.getDouble("precioUnitario");
                            }
                        }
                        break;
                    default:
                        precioProducto = 0.0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }
            return precioProducto;
    }

    public static String obtenerNombreProducto(int detalleId, Conexion sql) {
        String nombreProducto = "Producto no encontrado";

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT tipo_detalle, detalle_id FROM detalles_ventas WHERE id = ?"
             )
        ) {
            preparedStatement.setInt(1, detalleId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String tipoDetalle = rs.getString("tipo_detalle");
                int detalleIdProducto = rs.getInt("detalle_id");

                String query = null;
                String columnName = null;

                switch (tipoDetalle) {
                    case "floristeria":
                        query = "SELECT nombre FROM floristeria WHERE id = ?";
                        columnName = "nombre";
                        break;
                    case "tarjeta":
                        query = "SELECT ocasion FROM tarjetas WHERE id = ?";
                        columnName = "ocasion";
                        break;
                    case "manualidad":
                        query = "SELECT nombre FROM manualidades WHERE id = ?";
                        columnName = "nombre";
                        break;
                    case "arreglo":
                        query = "SELECT nombre FROM arreglos WHERE id = ?";
                        columnName = "nombre";
                        break;
                    case "desayuno":
                        query = "SELECT nombre FROM desayunos WHERE id = ?";
                        columnName = "nombre";
                        break;
                    case "material":
                        query = "SELECT nombre FROM materiales WHERE id = ?";
                        columnName = "nombre";
                        break;
                    case "globo":
                        query = "SELECT codigo_globo FROM globos WHERE id = ?";
                        columnName = "codigo_globo";
                        break;
                    case "mobiliario":
                        query = "SELECT nombreMobiliario FROM mobiliario WHERE id = ?";
                        columnName = "nombreMobiliario";
                        break;
                    case "resumen_promocion":
                        query = "SELECT descripcion FROM resumen_promociones WHERE id = ?";
                        columnName = "descripcion";
                        break;
                    default:
                        return "Tipo de detalle no reconocido";
                }

                try (PreparedStatement productoStatement = connection.prepareStatement(query)) {
                    productoStatement.setInt(1, detalleIdProducto);
                    ResultSet productoRs = productoStatement.executeQuery();

                    if (productoRs.next()) {
                        nombreProducto = productoRs.getString(columnName);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }
            return nombreProducto;
    }

    private ModeloVenta cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT c.*, p.nombre " +
                             "FROM ventas c " +
                             "JOIN clientes p ON c.cliente_id = p.id " +
                             "JOIN usuarios e ON c.usuario_id = e.id " +
                             "WHERE c.codigo_venta LIKE CONCAT('%', ?, '%') " +
                             "OR DATE_FORMAT(c.fecha, '%d de %M %Y') LIKE CONCAT('%', ?, '%') " +
                             "OR p.nombre LIKE CONCAT('%', ?, '%') " +
                             "LIMIT ?, 20")) {

            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es"));

            preparedStatement.setString(1, busqueda);  // Búsqueda por código de venta
            preparedStatement.setString(2, busqueda);  // Búsqueda por fecha de la venta
            preparedStatement.setString(3, busqueda);  // Búsqueda por nombre o apellido del cliente
            preparedStatement.setInt(4, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            ventaList = new ArrayList<>();
            while (resultSet.next()) {
                Venta venta = new Venta();
                venta.setId(resultSet.getInt("id"));
                venta.setCodigoVenta(resultSet.getString("codigo_venta"));
                java.util.Date fecha = resultSet.getDate("fecha");
                if (fecha != null) {
                    venta.setFecha(formatoFecha.format(fecha));
                } else {
                    venta.setFecha("");
                }

                venta.setClienteId(resultSet.getInt("cliente_id"));
                venta.setUsuarioId(resultSet.getInt("usuario_id"));
                ventaList.add(venta);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            ventaList = new ArrayList<>();
        }
        if (listaVentas.getColumnCount() > 0) {
            TableColumn columnId = listaVentas.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }
        return new ModeloVenta(ventaList, sql);
    }

    public static void imprimirFactura(String codigo) {
        Venta venta = new Venta();

        try {
            sql = new Conexion();
            try (Connection mysql = sql.conectamysql();
                 PreparedStatement preparedStatement = mysql.prepareStatement(
                         "SELECT c.id, c.codigo_venta, c.fecha, c.cliente_id, e.id AS empleado_id, p.nombre AS nombre_cliente " +
                                 "FROM ventas c " +
                                 "JOIN clientes p ON c.cliente_id = p.id " +
                                 "JOIN usuarios e ON c.usuario_id = e.id " +
                                 "WHERE c.codigo_venta LIKE CONCAT('%', ?, '%')")) {

                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es"));
                preparedStatement.setString(1, codigo);  // Búsqueda por código de venta
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    venta.setId(resultSet.getInt("id"));
                    venta.setCodigoVenta(resultSet.getString("codigo_venta"));
                    java.util.Date fecha = resultSet.getDate("fecha");
                    venta.setFecha(fecha != null ? formatoFecha.format(fecha) : "");
                    venta.setClienteId(resultSet.getInt("cliente_id"));
                    //venta.setUsuarioId(resultSet.getInt("usuario_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Mejor manejo de excepciones
                // Manejo adicional de errores, si se requiere
            }


            // Crear un nuevo documento
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage(PDRectangle.LETTER);
            doc.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);

            // Establecer el tipo de letra y el tamaño para el encabezado
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.setLeading(14.5f);

            // Agregar encabezado
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("EMPRESA DE EVENTOS CHELSEA");
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLine();
            contentStream.showText("Barrio Tierra Blanca, 100 mts adelante de Pintogama.");
            contentStream.newLine();
            contentStream.showText("Teléfono: 9699-1168");
            contentStream.newLine();
            contentStream.showText("Factura N° " + codigo);
            contentStream.newLine();
            contentStream.showText("Fecha: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            contentStream.endText();

            // Establecer el tipo de letra y el tamaño para el cuerpo
            contentStream.setFont(PDType1Font.HELVETICA, 12);

            // Calcular el ancho de las columnas
            float[] columnWidths = {20, 200, 95, 95, 100};
            float tableHeight = 600;
            float tableWidth = page.getMediaBox().getWidth() - 100;
            float yStart = 650;
            float yPosition = yStart;
            int rowsPerPage = 20;
            int pageNumber = 1;
            int rowNumber = 0;

            // Agregar títulos de las columnas
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, yPosition);
            contentStream.showText("N°");
            contentStream.newLineAtOffset(columnWidths[0], 0);
            contentStream.showText("Producto");
            contentStream.newLineAtOffset(columnWidths[1], 0);
            contentStream.showText("Cantidad");
            contentStream.newLineAtOffset(columnWidths[2], 0);
            contentStream.showText("Precio");
            contentStream.newLineAtOffset(columnWidths[3], 0);
            contentStream.showText("Total");
            contentStream.endText();

            Conexion sql = new Conexion();
            ModeloVentaDetalle mdc = new ModeloVentaDetalle(new ArrayList<>(), sql);

            List<VentaDetalle> detalles = mdc.getDetallesPorVenta(venta.getId());
            for (VentaDetalle detalle : detalles) {
                if (rowNumber == rowsPerPage) {
                    contentStream.endText();
                    contentStream.close();
                    page = new PDPage(PDRectangle.LETTER);
                    doc.addPage(page);
                    contentStream = new PDPageContentStream(doc, page);
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yStart);
                    yPosition = yStart;
                    pageNumber++;
                    rowNumber = 0;
                    contentStream.showText("N°");
                    contentStream.newLineAtOffset(columnWidths[0], 0);
                    contentStream.showText("Producto");
                    contentStream.newLineAtOffset(columnWidths[1], 0);
                    contentStream.showText("Cantidad");
                    contentStream.newLineAtOffset(columnWidths[2], 0);
                    contentStream.showText("Precio");
                    contentStream.newLineAtOffset(columnWidths[3], 0);
                    contentStream.showText("Total");
                    contentStream.endText();
                }

                // Obtén el nombre del producto
                String nombreProducto = obtenerNombreProducto(detalle.getId(), sql);

                yPosition -= 20;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText(String.valueOf(rowNumber + 1));
                contentStream.newLineAtOffset(columnWidths[0], 0);
                contentStream.showText(obtenerNombreProducto(detalle.getId(), sql));


                contentStream.newLineAtOffset(columnWidths[1], 0);
                contentStream.showText(String.valueOf(detalle.getCantidad()));
                contentStream.newLineAtOffset(columnWidths[2], 0);
                contentStream.showText(String.format("L. %.2f", obtenerPrecioProducto(detalle.getId(), sql)));


                //contentStream.showText(String.format("L. %.2f", detalle.getPrecio()));
                contentStream.newLineAtOffset(columnWidths[3], 0);
                contentStream.showText(String.format("L. %.2f", detalle.getCantidad() * obtenerPrecioProducto(detalle.getId(), sql)));
                contentStream.endText();
                rowNumber++;
            }

            double subTotal = calcularSubtotal(detalles);
            double isv = calcularISV(detalles);
            double total = calcularTotal(detalles);

            // Agregar línea de separación
            contentStream.setLineWidth(1f);
            contentStream.moveTo(50, yPosition - 10);
            contentStream.lineTo(tableWidth, yPosition - 10);
            contentStream.stroke();

            // Agregar pie de página
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(tableWidth - 250, yPosition - 30);
            contentStream.showText("Total antes de Impuestos:");
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(tableWidth - 50, yPosition - 30);
            contentStream.showText("L. " + String.format("%.2f", subTotal));
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(tableWidth - 250, yPosition - 50);
            contentStream.showText("Impuestos sobre ventas (15%):");
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(tableWidth - 50, yPosition - 50);
            contentStream.showText("L. " + String.format("%.2f", isv));
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(tableWidth - 250, yPosition - 70);
            contentStream.showText("Total a pagar:");
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(tableWidth - 50, yPosition - 70);
            contentStream.showText("L. " + String.format("%.2f", total));
            contentStream.endText();

            // Cerrar el flujo de contenido y guardar el documento
            contentStream.close();

            // Obtener la fecha y hora actual en el formato deseado


            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
            String fechaActual = dateFormat.format(new Date());
            String horaActual = timeFormat.format(new Date());

            // Generar un número aleatorio de cuatro dígitos entre 0001 y 9999
            Random random = new Random();
            int numeroAleatorio = random.nextInt(9999 - 1 + 1) + 1;
            String numeroAleatorioFormateado = String.format("%04d", numeroAleatorio);

            // Generar el nombre del archivo PDF
            String nombreArchivo = "Factura de venta N° " + fechaActual + " " + horaActual + " " + numeroAleatorioFormateado + ".pdf";

            // Crear un objeto JFileChooser para permitir al usuario seleccionar dónde guardar el archivo PDF
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccione dónde guardar el archivo PDF");

            // Establecer el directorio actual del JFileChooser a la ruta del escritorio
            File desktopPath = new File(System.getProperty("user.home"), "Desktop");
            fileChooser.setCurrentDirectory(desktopPath);

            // Predeterminar el nombre del archivo PDF en el diálogo de selección de archivos
            fileChooser.setSelectedFile(new File(desktopPath, nombreArchivo));

            // Mostrar el diálogo de selección de archivos
            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // Si el usuario ha seleccionado una ubicación de almacenamiento, guardar el archivo PDF allí
                File fileToSave = fileChooser.getSelectedFile();
                doc.save(fileToSave.getAbsolutePath());

                // Mostrar un mensaje para informar al usuario que el archivo se ha guardado correctamente
                mostrarDialogoPersonalizadoExito("El archivo ha sido guardado exitosamente como: \n" + fileToSave.getName(), Color.decode("#263238"));
            } else {
                // Si el usuario ha cancelado el diálogo de selección de archivos, no guardar el archivo PDF
                mostrarDialogoPersonalizadoError("El archivo no ha sido guardado, se ha cancelado la acción.", Color.decode("#C62828"));
            }

            // Cerrar el documento
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.INFORMATION_MESSAGE,   // Tipo de mensaje
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

    public static void mostrarDialogoPersonalizadoError(String mensaje, Color colorFondoBoton) {
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
        ListaVentas listaVentas = new ListaVentas();
        listaVentas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaVentas.setVisible(true);
    }
}
