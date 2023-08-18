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
    private Conexion sql;
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

        /*
        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaCompras.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                VerCompras compras = new VerCompras(compraList.get(listaCompras.getSelectedRow()).getId());
                compras.setVisible(true);
                actual.dispose();
            }
        });

         */

        botonImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaVentas.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int filaSeleccionada = listaVentas.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int indiceItemSeleccionado = listaVentas.convertRowIndexToModel(filaSeleccionada);
                    imprimirFactura(indiceItemSeleccionado);
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
        fechaComboBox.setBackground(darkColor);
        campoBusqueda.setBackground(darkColor);

        placeholder.setForeground(Color.WHITE);
        fechaComboBox.setForeground(Color.WHITE);
        botonImprimir.setForeground(Color.WHITE);
        botonVer.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonAdelante.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        campoBusqueda.setForeground(Color.WHITE);
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
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(230);
        columnModel.getColumn(4).setPreferredWidth(230);
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

    private ModeloVenta cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT c.*, p.nombre, CONCAT(e.nombres, ' ', e.apellidos) AS apellido " +
                             "FROM ventas c " +
                             "JOIN clientes p ON c.cliente_id = p.id " +
                             "JOIN empleados e ON c.empleado_id = e.id " +
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
                venta.setEmpleadoId(resultSet.getInt("empleado_id"));
                ventaList.add(venta);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            ventaList = new ArrayList<>();
        }
        if (listaVentas.getColumnCount() > 0) {
            TableColumn columnId = listaVentas.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }
        return new ModeloVenta(ventaList, sql);
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
            String query = "SELECT c.*, p.nombre, CONCAT(e.Nombres, ' ', e.Apellidos) AS apellido " +
                    "FROM ventas c " +
                    "JOIN clientes p ON c.cliente_id = p.id " +
                    "JOIN empleados e ON c.empleado_id = e.id ";

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
                venta.setEmpleadoId(resultSet.getInt("empleado_id"));
                ventaList.add(venta);
            }
            listaVentas.setModel(new ModeloVenta(ventaList, sql));
            configurarTablaVentas();
            lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount(mesSeleccionado));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }
        int totalPageCount = (int) Math.ceil((double) count / 20);
        return totalPageCount;
    }

    private double calcularSubtotal(List<VentaDetalle> detalles) {
        double subtotal = 0.0;
        for (VentaDetalle detalle : detalles) {
            Material producto = obtenerMaterialPorId(detalle.getDetalleId());
            if (producto != null) {
                subtotal += detalle.getCantidad() * detalle.getPrecio();
            }
        }
        // Restar el 15% al subtotal
        subtotal *= 0.85;
        return subtotal;
    }

    private double calcularISV(List<VentaDetalle> detalles) {
        double isv = 0.0;
        for (VentaDetalle detalle : detalles) {
            Material producto = obtenerMaterialPorId(detalle.getDetalleId());
            if (producto != null) {
                isv += detalle.getCantidad() * detalle.getPrecio() * 0.15;
            }
        }
        return isv;
    }

    private double calcularTotal(List<VentaDetalle> detalles) {
        double subtotal = calcularSubtotal(detalles);
        double isv = calcularISV(detalles);
        return subtotal + isv;
    }

    public Material obtenerMaterialPorId(int materialId) {
        Material material = null;

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT nombre, exento FROM materiales WHERE id = ?")) {

            preparedStatement.setInt(1, materialId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String nombreMaterial = resultSet.getString("nombre");
                boolean exento = resultSet.getBoolean("exento");
                material = new Material(materialId, nombreMaterial, 0,0 , null, null, exento, 0);
            }

        } catch (SQLException e) {
            // En caso de error en la conexión o consulta, se muestra un mensaje de error.
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }

        return material;
    }

    public String obtenerNombreMaterial(int materialId, Conexion sql) {
        String nombreMaterial = "";
        boolean exento = false; // Variable para indicar si el material es exento

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT nombre FROM materiales WHERE id = ?")) {
            preparedStatement.setInt(1, materialId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nombreMaterial = resultSet.getString("nombre");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }
        return nombreMaterial;
    }

    public void imprimirFactura(int indiceItem) {
        try {
            // Obtener la compra asociada al ítem seleccionado
            Venta venta = ventaList.get(indiceItem);

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
            contentStream.showText("Material");
            contentStream.newLineAtOffset(columnWidths[1], 0);
            contentStream.showText("Cantidad");
            contentStream.newLineAtOffset(columnWidths[2], 0);
            contentStream.showText("Precio");
            contentStream.newLineAtOffset(columnWidths[3], 0);
            contentStream.showText("Total");
            contentStream.endText();

            // Agregar línea por línea
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
                    contentStream.showText("Material");
                    contentStream.newLineAtOffset(columnWidths[1], 0);
                    contentStream.showText("Cantidad");
                    contentStream.newLineAtOffset(columnWidths[2], 0);
                    contentStream.showText("Precio");
                    contentStream.newLineAtOffset(columnWidths[3], 0);
                    contentStream.showText("Total");
                    contentStream.endText();
                }

                yPosition -= 20;
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText(String.valueOf(rowNumber + 1));
                contentStream.newLineAtOffset(columnWidths[0], 0);
                contentStream.showText(obtenerNombreMaterial(detalle.getDetalleId(), sql));
                contentStream.newLineAtOffset(columnWidths[1], 0);
                contentStream.showText(String.valueOf(detalle.getCantidad()));
                contentStream.newLineAtOffset(columnWidths[2], 0);
                contentStream.showText(String.format("L. %.2f", detalle.getPrecio()));
                contentStream.newLineAtOffset(columnWidths[3], 0);
                contentStream.showText(String.format("L. %.2f", detalle.getCantidad() * detalle.getPrecio()));
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
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH-mm-ss");
            String fechaActual = dateFormat.format(new Date());
            String horaActual = timeFormat.format(new Date());

            // Generar un número aleatorio de cuatro dígitos entre 0001 y 9999
            Random random = new Random();
            int numeroAleatorio = random.nextInt(9999 - 1 + 1) + 1;
            String numeroAleatorioFormateado = String.format("%04d", numeroAleatorio);

            // Generar el nombre del archivo PDF
            String nombreArchivo = "Factura de venta N° " + fechaActual + " " + horaActual + " " + numeroAleatorioFormateado + ".pdf";

            // Reemplazar los caracteres no válidos en el nombre del archivo
            nombreArchivo = nombreArchivo.replace(":", "-");

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

                // Mostrar un JOptionPane para informar al usuario que el archivo se ha guardado correctamente
                JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente como: " + fileToSave.getName(), "Archivo guardado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Si el usuario ha cancelado el diálogo de selección de archivos, no guardar el archivo PDF
                JOptionPane.showMessageDialog(null, "Guardado cancelado", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Cerrar el documento
            doc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ListaVentas listaVentas = new ListaVentas();
        listaVentas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaVentas.setVisible(true);
    }
}
