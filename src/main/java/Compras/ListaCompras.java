package Compras;
import Modelos.ModeloCompras;
import Modelos.ModeloDetallesCompras;
import Objetos.Compra;
import Objetos.Conexion;
import Objetos.DetalleCompra;
import Objetos.Material;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
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
public class ListaCompras extends JFrame {
    private JPanel panelPrincipal;
    private JTable listaCompras;
    private JButton botonAtras,botonAdelante, botonCrear, botonImprimir;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt("Buscar por código de compra, fecha o proveedor ", campoBusqueda);
    private JLabel lbl0, lblPagina;
    private JComboBox fechaComboBox;
    private List<Compra> compraList;
    private int pagina = 0;
    private Conexion sql;
    private String busqueda = "";

    public ListaCompras() {
        super("");
        setSize(990, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        compraList = new ArrayList<>();
        listaCompras.setModel(cargarDatos());
        centrarDatosTabla();

        Color primaryColor = Color.decode("#37474f");
        Color lightColor = Color.decode("#cfd8dc");
        Color darkColor = Color.decode("#263238");

        panelPrincipal.setBackground(primaryColor);
        fechaComboBox.setBackground(primaryColor);
        fechaComboBox.setForeground(Color.WHITE);

        botonAtras.setBackground(darkColor);
        botonAtras.setForeground(Color.WHITE);
        botonAtras.setFocusPainted(false);
        botonAdelante.setBackground(darkColor);
        botonAdelante.setForeground(Color.WHITE);
        botonAdelante.setFocusPainted(false);
        botonCrear.setBackground(darkColor);
        botonCrear.setForeground(Color.WHITE);
        botonCrear.setFocusPainted(false);
        campoBusqueda.setBackground(darkColor);
        campoBusqueda.setForeground(Color.WHITE);
        lblPagina.setForeground(Color.WHITE);

        placeholder.changeAlpha(0.75f);
        placeholder.setForeground(Color.LIGHT_GRAY);
        placeholder.setFont(new Font("Nunito", Font.ITALIC, 11));

        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());

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
                listaCompras.setModel(cargarDatos());
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
                listaCompras.setModel(cargarDatos());
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
                listaCompras.setModel(cargarDatos());
                centrarDatosTabla();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
            }
        });

        // Agrega el ActionListener al JComboBox
        fechaComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mesSeleccionado = (String) fechaComboBox.getSelectedItem();
                actualizarModeloTablaConMesSeleccionado(mesSeleccionado);
            }
        });

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

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearCompra crearCompra = new CrearCompra();
                crearCompra.setVisible(true);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(listaCompras); // Obtén la ventana que contiene la lista de compras
                if (frame != null) {
                    frame.dispose(); // Cierra la ventana
                }
            }
        });

        botonImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaCompras.getSelectedRow() == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int filaSeleccionada = listaCompras.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int indiceItemSeleccionado = listaCompras.convertRowIndexToModel(filaSeleccionada);
                    imprimirFactura(indiceItemSeleccionado);
                }
            }
        });


    }



    private void centrarDatosTabla() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < listaCompras.getColumnCount(); i++) {
            listaCompras.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    private ModeloCompras cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT c.*, p.empresaProveedora, CONCAT(e.Nombres, ' ', e.Apellidos) AS empleadoNombre " +
                             "FROM compras c " +
                             "JOIN proveedores p ON c.proveedor_id = p.id " +
                             "JOIN empleados e ON c.empleado_id = e.id " +
                             "WHERE c.codigo_compra LIKE CONCAT('%', ?, '%') " +
                             "OR DATE_FORMAT(c.fecha, '%d de %M %Y') LIKE CONCAT('%', ?, '%') " +
                             "OR p.empresaProveedora LIKE CONCAT('%', ?, '%') " +
                             "LIMIT ?, 20")) {

            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd 'de' MMMM yyyy", new Locale("es"));

            preparedStatement.setString(1, busqueda);  // Búsqueda por código de compra
            preparedStatement.setString(2, busqueda);  // Búsqueda por fecha de la compra
            preparedStatement.setString(3, busqueda);  // Búsqueda por empresa proveedora
            preparedStatement.setInt(4, pagina * 20);

            ResultSet resultSet = preparedStatement.executeQuery();
            compraList = new ArrayList<>();
            while (resultSet.next()) {
                Compra compra = new Compra();
                compra.setId(resultSet.getInt("id"));
                compra.setCodigoCompra(resultSet.getString("codigo_compra"));

                java.util.Date fecha = resultSet.getDate("fecha");
                if (fecha != null) {
                    compra.setFecha(formatoFecha.format(fecha));
                } else {
                    compra.setFecha("");
                }

                compra.setProveedorId(resultSet.getInt("proveedor_id"));
                compra.setEmpleadoId(resultSet.getInt("empleado_id"));
                compraList.add(compra);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            compraList = new ArrayList<>();
        }
        if (listaCompras.getColumnCount() > 0) {
            TableColumn columnId = listaCompras.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }
        return new ModeloCompras(compraList, sql);
    }

    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement("SELECT COUNT(*) AS total FROM " + Compra.nombreTabla + " WHERE codigo_compra LIKE CONCAT('%',?,'%')")) {
            preparedStatement.setString(1, busqueda);
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

    private double calcularSubtotal(List<DetalleCompra> detalles) {
        double subtotal = 0.0;
        for (DetalleCompra detalle : detalles) {
            Material material = obtenerMaterialPorId(detalle.getMaterialId());
            if (material != null && !material.isExento()) {
                subtotal += detalle.getCantidad() * detalle.getPrecio();
            }
        }
        return subtotal;
    }

    private double calcularISV(List<DetalleCompra> detalles) {
        double isv = 0.0;
        for (DetalleCompra detalle : detalles) {
            Material material = obtenerMaterialPorId(detalle.getMaterialId());
            if (material != null && !material.isExento()) {
                isv += detalle.getCantidad() * detalle.getPrecio() * 0.15;
            }
        }
        return isv;
    }

    private double calcularExento(List<DetalleCompra> detalles) {
        double exento = 0.0;
        for (DetalleCompra detalle : detalles) {
            Material material = obtenerMaterialPorId(detalle.getMaterialId());
            if (material != null && material.isExento()) {
                exento += detalle.getCantidad() * detalle.getPrecio();
            }
        }
        return exento;
    }

    private double calcularTotal(List<DetalleCompra> detalles) {
        double subtotal = calcularSubtotal(detalles);
        double isv = calcularISV(detalles);
        double exento = calcularExento(detalles);
        return subtotal + isv + exento;
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

                // Aquí utilizamos el constructor apropiado para crear un objeto Material.
                // Los campos que no se obtienen de la base de datos se inicializan con valores predeterminados.
                material = new Material(materialId, nombreMaterial, 0, null, null, exento, 0);
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
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT nombre, exento FROM materiales WHERE id = ?")) {
            preparedStatement.setInt(1, materialId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                nombreMaterial = resultSet.getString("nombre");
                exento = resultSet.getBoolean("exento");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }

        if (exento) {
            nombreMaterial += " (Exento)";
        }

        return nombreMaterial;
    }

    public void imprimirFactura(int indiceItem) {
        try {
            // Obtener la compra asociada al ítem seleccionado
            Compra compra = compraList.get(indiceItem);

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
            ModeloDetallesCompras mdc = new ModeloDetallesCompras(new ArrayList<>(), sql);

            List<DetalleCompra> detalles = mdc.getDetallesPorCompra(compra.getId());
            for (DetalleCompra detalle : detalles) {
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
                contentStream.showText(obtenerNombreMaterial(detalle.getMaterialId(), sql));
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
            double exento = calcularExento(detalles);
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
            contentStream.showText("Exento:");
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(tableWidth - 50, yPosition - 70);
            contentStream.showText("L. " + String.format("%.2f", exento));
            contentStream.endText();

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(tableWidth - 250, yPosition - 90);
            contentStream.showText("Total:");
            contentStream.endText();
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            contentStream.newLineAtOffset(tableWidth - 50, yPosition - 90);
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
            String nombreArchivo = "Factura " + fechaActual + " " + horaActual + " " + numeroAleatorioFormateado + ".pdf";

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
            PreparedStatement preparedStatement;
            if (mesSeleccionado.equals("Todos")) {
                preparedStatement = mysql.prepareStatement(
                        "SELECT c.*, p.empresaProveedora, CONCAT(e.Nombres, ' ', e.Apellidos) AS empleadoNombre " +
                                "FROM compras c " +
                                "JOIN proveedores p ON c.proveedor_id = p.id " +
                                "JOIN empleados e ON c.empleado_id = e.id " +
                                "LIMIT ?, 20");
                preparedStatement.setInt(1, pagina * 20);
            } else {
                preparedStatement = mysql.prepareStatement(
                        "SELECT c.*, p.empresaProveedora, CONCAT(e.Nombres, ' ', e.Apellidos) AS empleadoNombre " +
                                "FROM compras c " +
                                "JOIN proveedores p ON c.proveedor_id = p.id " +
                                "JOIN empleados e ON c.empleado_id = e.id " +
                                "WHERE MONTH(c.fecha) = ? " +
                                "LIMIT ?, 20");
                int numeroMes = obtenerNumeroMes(mesSeleccionado);
                preparedStatement.setInt(1, numeroMes);
                preparedStatement.setInt(2, pagina * 20);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            compraList = new ArrayList<>();
            while (resultSet.next()) {
                Compra compra = new Compra();
                compra.setId(resultSet.getInt("id"));
                compra.setCodigoCompra(resultSet.getString("codigo_compra"));
                compra.setFecha(resultSet.getString("fecha"));
                compra.setProveedorId(resultSet.getInt("proveedor_id"));
                compra.setEmpleadoId(resultSet.getInt("empleado_id"));
                compraList.add(compra);
            }

            listaCompras.setModel(new ModeloCompras(compraList, sql));
            centrarDatosTabla();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
            compraList = new ArrayList<>();
        }
    }




    public static void main(String[] args) {
        ListaCompras listaCompras = new ListaCompras();
        listaCompras.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaCompras.setVisible(true);
    }
}
