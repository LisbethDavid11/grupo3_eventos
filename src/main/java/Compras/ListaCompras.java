/**
 * ListaCompras.java
 *
 * Lista de Compras
 *
 * @author Lisbeth David
 * @version 1.0
 * @since 2024-05-05
 */

package Compras;

import Modelos.ModeloCompra;
import Modelos.ModeloCompraDetalle;
import Objetos.Compra;
import Objetos.Conexion;
import Objetos.CompraDetalle;
import Objetos.Material;
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

public class ListaCompras extends JFrame {
    // Paneles
    private JPanel panelPrincipal;
    private JPanel panelTitulo;
    private JPanel panelA;
    private JPanel panelB;

    // Tabla
    private JTable listaCompras;

    // Botones
    private JButton botonAtras;
    private JButton botonAdelante;
    private JButton botonCrear;
    private JButton botonVer;
    private JButton botonImprimir;

    // Campo de texto
    private JTextField campoBusqueda;

    // Placeholder para el campo de búsqueda
    private TextPrompt placeholder = new TextPrompt(" Buscar por código de compra, fecha ó proveedor ", campoBusqueda);

    // Etiquetas
    private JLabel lbl0;
    private JLabel lblPagina;

    // ComboBox para la fecha
    private JComboBox fechaComboBox;

    // Lista de compras
    private List<Compra> compraList;

    // Página actual
    private int pagina = 0;

    // Conexión a la base de datos
    private Conexion sql;

    // Búsqueda
    private String busqueda = "";

    // Referencia a la ventana de lista de compras actual
    private ListaCompras actual = this;

    // Fuentes
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);

    // Colores
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro

    public ListaCompras() {
        super("");
        setSize(990, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        compraList = new ArrayList<>();
        listaCompras.setModel(cargarDatos());
        configurarTablaCompras();

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
                listaCompras.setModel(cargarDatos());
                configurarTablaCompras();
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
                listaCompras.setModel(cargarDatos());
                configurarTablaCompras();
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
                listaCompras.setModel(cargarDatos());
                configurarTablaCompras();
                lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount(mesSeleccionado));
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

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaCompras.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerCompras compras = new VerCompras(compraList.get(listaCompras.getSelectedRow()).getId());
                compras.setVisible(true);
                actual.dispose();
            }
        });

        botonImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaCompras.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }

                int filaSeleccionada = listaCompras.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    int indiceItemSeleccionado = listaCompras.convertRowIndexToModel(filaSeleccionada);
                    imprimirFactura(indiceItemSeleccionado);
                }
            }
        });

        JTableHeader header = listaCompras.getTableHeader();
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
        fechaComboBox.setForeground(darkColor);
        botonImprimir.setForeground(Color.WHITE);
        botonVer.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonAdelante.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
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

    }

    // Método para configurar las columnas y sus renderizadores en la tabla de compras
    private void configurarTablaCompras() {
        TableColumnModel columnModel = listaCompras.getColumnModel();

        // Establece el ancho preferido de cada columna
        columnModel.getColumn(0).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(140);
        columnModel.getColumn(2).setPreferredWidth(170);
        columnModel.getColumn(3).setPreferredWidth(210);
        columnModel.getColumn(4).setPreferredWidth(210);
        columnModel.getColumn(5).setPreferredWidth(90);
        columnModel.getColumn(6).setPreferredWidth(90);
        columnModel.getColumn(7).setPreferredWidth(90);

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

    // Clase para alinear el texto a la izquierda en celdas de una tabla
    class LeftAlignedRenderer extends DefaultTableCellRenderer {
        public LeftAlignedRenderer() {
            setHorizontalAlignment(LEFT); // Alineación a la izquierda
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Clase para alinear el texto al centro en celdas de una tabla
    class CenterAlignedRenderer extends DefaultTableCellRenderer {
        public CenterAlignedRenderer() {
            setHorizontalAlignment(CENTER); // Alineación al centro
        }

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    // Método para calcular el subtotal de las compras excluyendo materiales exentos
    private double calcularSubtotal(List<CompraDetalle> detalles) {
        double subtotal = 0.0;
        for (CompraDetalle detalle : detalles) {
            Material material = obtenerMaterialPorId(detalle.getMaterialId());
            if (material != null && !material.isExento()) {
                subtotal += detalle.getCantidad() * detalle.getPrecio();
            }
        }
        return subtotal;
    }

    // Método para calcular el Impuesto Sobre Venta (ISV) para los materiales no exentos
    private double calcularISV(List<CompraDetalle> detalles) {
        double isv = 0.0;
        for (CompraDetalle detalle : detalles) {
            Material material = obtenerMaterialPorId(detalle.getMaterialId());
            if (material != null && !material.isExento()) {
                isv += detalle.getCantidad() * detalle.getPrecio() * 0.15; // Supone un ISV del 15%
            }
        }
        return isv;
    }

    // Método para calcular el total de materiales exentos
    private double calcularExento(List<CompraDetalle> detalles) {
        double exento = 0.0;
        for (CompraDetalle detalle : detalles) {
            Material material = obtenerMaterialPorId(detalle.getMaterialId());
            if (material != null && material.isExento()) {
                exento += detalle.getCantidad() * detalle.getPrecio();
            }
        }
        return exento;
    }

    // Método para calcular el total combinado de subtotal, ISV y exento
    private double calcularTotal(List<CompraDetalle> detalles) {
        double subtotal = calcularSubtotal(detalles);
        double isv = calcularISV(detalles);
        double exento = calcularExento(detalles);
        return subtotal + isv + exento;
    }

    // Método para obtener un material por su ID, retornando un objeto Material si es encontrado
    public Material obtenerMaterialPorId(int materialId) {
        Material material = null;
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT nombre, exento FROM materiales WHERE id = ?")) {
            preparedStatement.setInt(1, materialId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String nombreMaterial = resultSet.getString("nombre");
                boolean exento = resultSet.getBoolean("exento");
                material = new Material(materialId, nombreMaterial, 0, 0, null, null, exento, 0);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }
        return material;
    }

    // Método para obtener el nombre de un material por su ID, añadiendo "(Exento)" si el material está exento de impuestos
    public String obtenerNombreMaterial(int materialId, Conexion sql) {
        String nombreMaterial = "";
        boolean exento = false;
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
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }
        if (exento) {
            nombreMaterial += " (Exento)";
        }
        return nombreMaterial;
    }

    // Método para cargar datos de compras desde la base de datos y devolver un modelo de datos para una tabla
    private ModeloCompra cargarDatos() {
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
            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);
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
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            compraList = new ArrayList<>();
        }
        if (listaCompras.getColumnCount() > 0) {
            TableColumn columnId = listaCompras.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }
        return new ModeloCompra(compraList, sql);
    }

    // Método para calcular el número total de páginas necesarias para visualizar todas las compras
    private int getTotalPageCount(String mesSeleccionado) {
        int count = 0;
        try (Connection mysql = sql.conectamysql()) {
            String query = "SELECT COUNT(*) AS total FROM " + Compra.nombreTabla;
            if (mesSeleccionado != null && !mesSeleccionado.equals("Todos")) {
                query += " WHERE MONTH(fecha) = ? AND codigo_compra LIKE CONCAT('%', ?, '%')";
            } else {
                query += " WHERE codigo_compra LIKE CONCAT('%', ?, '%')";
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
        int registrosPorPagina = 20;
        int totalPageCount = (count + registrosPorPagina - 1) / registrosPorPagina;
        if (totalPageCount == 0) {
            totalPageCount = 1;
        }
        return totalPageCount;
    }

    // Método para obtener el número del mes
    private int obtenerNumeroMes(String mesSeleccionado) {
        int numeroMes = 0;  // Inicializa el número de mes a 0.
        switch (mesSeleccionado) {  // Evalúa el mes seleccionado.
            case "Enero":
                numeroMes = 1;  // Enero corresponde al mes 1.
                break;
            case "Febrero":
                numeroMes = 2;  // Febrero corresponde al mes 2.
                break;
            case "Marzo":
                numeroMes = 3;  // Marzo corresponde al mes 3.
                break;
            case "Abril":
                numeroMes = 4;  // Abril corresponde al mes 4.
                break;
            case "Mayo":
                numeroMes = 5;  // Mayo corresponde al mes 5.
                break;
            case "Junio":
                numeroMes = 6;  // Junio corresponde al mes 6.
                break;
            case "Julio":
                numeroMes = 7;  // Julio corresponde al mes 7.
                break;
            case "Agosto":
                numeroMes = 8;  // Agosto corresponde al mes 8.
                break;
            case "Septiembre":
                numeroMes = 9;  // Septiembre corresponde al mes 9.
                break;
            case "Octubre":
                numeroMes = 10;  // Octubre corresponde al mes 10.
                break;
            case "Noviembre":
                numeroMes = 11;  // Noviembre corresponde al mes 11.
                break;
            case "Diciembre":
                numeroMes = 12;  // Diciembre corresponde al mes 12.
                break;
            case "Todos":
                numeroMes = 0;  // La opción "Todos" no corresponde a un mes específico, por lo que se asigna 0.
                break;
        }
        return numeroMes;  // Retorna el número del mes.
    }

    // Método para actualizar el modelo de la tabla de compras según el mes seleccionado, implementando filtros de búsqueda
    private void actualizarModeloTablaConMesSeleccionado(String mesSeleccionado) {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql()) {
            String query = "SELECT c.*, p.empresaProveedora, CONCAT(e.Nombres, ' ', e.Apellidos) AS empleadoNombre " +
                    "FROM compras c " +
                    "JOIN proveedores p ON c.proveedor_id = p.id " +
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
                query += "(c.codigo_compra LIKE CONCAT('%', ?, '%') OR p.empresaProveedora LIKE CONCAT('%', ?, '%')) ";
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
            listaCompras.setModel(new ModeloCompra(compraList, sql));
            configurarTablaCompras();
            lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount(mesSeleccionado));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            compraList = new ArrayList<>();
        }
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

    // Método para mostrar o imprimir una factura de compra
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
            ModeloCompraDetalle mdc = new ModeloCompraDetalle(new ArrayList<>(), sql);

            List<CompraDetalle> detalles = mdc.getDetallesPorCompra(compra.getId());
            for (CompraDetalle detalle : detalles) {
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
            String nombreArchivo = "Factura de compra N° " + fechaActual + " " + horaActual + " " + numeroAleatorioFormateado + ".pdf";

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

    // Método Principal
    public static void main(String[] args) {
        ListaCompras listaCompras = new ListaCompras();
        listaCompras.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaCompras.setVisible(true);
    }
}
