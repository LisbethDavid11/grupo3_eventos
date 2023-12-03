package Pedidos;

import Login.SesionUsuario;
import Modelos.ModeloPedido;
import Objetos.Conexion;
import Objetos.Pedido;
import Ventas.ListaVentas;

import javax.swing.*;
import javax.swing.table.*;
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

public class ListaPedidos extends JFrame {
    private JPanel panelPrincipal;
    private JButton botonVer;
    private JTable listaPedidos;
    private List<Pedido> pedidoList;
    private JButton botonAtras;
    private JButton botonAdelante;
    private JTextField campoBusqueda;
    private TextPrompt placeholder = new TextPrompt(" Buscar por código, fecha de entrega o cliente", campoBusqueda);
    private JButton botonEditar;
    private JButton botonCrear;
    private JLabel lblPagina, lbl0, lblD;
    private JCheckBox noCheckBox;
    private JCheckBox siCheckBox;
    private JPanel panelA;
    private JPanel panelB;
    private JPanel panelTitulo;

    private int pagina = 0;
    private Connection mysql;
    private Conexion sql;
    private ListaPedidos actual = this;
    private String busqueda = "";
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 11);
    Color primaryColor = Color.decode("#37474f"); // Gris azul oscuro
    Color lightColor = Color.decode("#cfd8dc"); // Gris azul claro
    Color darkColor = Color.decode("#263238"); // Gris azul más oscuro
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    public ListaPedidos() {
        super("");
        setSize(950, 505);
        setLocationRelativeTo(null);
        setContentPane(panelPrincipal);
        campoBusqueda.setText("");

        listaPedidos.setModel(cargarDatos());
        configurarTablaMateriales();

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
                actualizarTabla();
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
                actualizarTabla();
            }
        });

        botonVer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaPedidos.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                VerPedidos verPedidos = new VerPedidos(pedidoList.get(listaPedidos.getSelectedRow()).getId());
                verPedidos.setVisible(true);
                actual.dispose();
            }
        });

        botonEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listaPedidos.getSelectedRow() == -1) {
                    mostrarDialogoPersonalizadoAtencion("Seleccione una fila para continuar.", Color.decode("#F57F17"));
                    return;
                }
                EditarPedido editarPedido = new EditarPedido(pedidoList.get(listaPedidos.getSelectedRow()), pedidoList.get(listaPedidos.getSelectedRow()).getId());
                editarPedido.setVisible(true);
                actual.dispose();
            }
        });

        campoBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                busqueda = campoBusqueda.getText();
                pagina = 0;
                botonAdelante.setEnabled((pagina + 1) < getTotalPageCount());
                botonAtras.setEnabled(pagina > 0);
                actualizarTabla();
            }
        });

        botonCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearPedido pedidos = new CrearPedido();
                pedidos.setVisible(true);
                actual.dispose();
            }
        });

        JTableHeader header = listaPedidos.getTableHeader();
        header.setForeground(Color.WHITE);

        listaPedidos.getColumnModel().getColumn(6).setCellRenderer(new ListaPedidos.ButtonRenderer());
        listaPedidos.getColumnModel().getColumn(6).setCellEditor(new ListaPedidos.ButtonEditor());

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

        placeholder.setForeground(darkColor);
        botonVer.setForeground(Color.WHITE);
        botonAtras.setForeground(Color.WHITE);
        botonAdelante.setForeground(Color.WHITE);
        botonCrear.setForeground(Color.WHITE);
        botonEditar.setForeground(Color.WHITE);
        campoBusqueda.setForeground(darkColor);
        lblPagina.setForeground(Color.WHITE);

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

    private void configurarTablaMateriales() {
        TableColumnModel columnModel = listaPedidos.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(10);
        columnModel.getColumn(1).setPreferredWidth(120);
        columnModel.getColumn(2).setPreferredWidth(110);
        columnModel.getColumn(3).setPreferredWidth(110);
        columnModel.getColumn(4).setPreferredWidth(70);
        columnModel.getColumn(5).setPreferredWidth(35);
        columnModel.getColumn(6).setPreferredWidth(85);

        columnModel.getColumn(0).setCellRenderer(new ListaPedidos.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaPedidos.CenterAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
        columnModel.getColumn(6).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
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

    private ModeloPedido cargarDatos() {
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT p.id, p.codigo_pedido, p.fecha_pedido, p.fecha_entrega, p.descripcion, " +
                             "p.cliente_id, p.entrega, p.estado FROM pedidos p " +
                             "INNER JOIN clientes c ON p.cliente_id = c.id " +
                             "WHERE p.estado = 'Proceso' AND (" +
                             "p.codigo_pedido LIKE CONCAT('%', ?, '%') OR " +
                             "p.fecha_entrega LIKE CONCAT('%', ?, '%') OR " +
                             "c.nombre LIKE CONCAT('%', ?, '%')) " +
                             "LIMIT ?, 20"
                                )) {  // Límite de 20 registros por página

            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);
            preparedStatement.setInt(4, pagina * 20); // Calcula el offset según la página actual

            ResultSet resultSet = preparedStatement.executeQuery();
            pedidoList = new ArrayList<>();

            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setId(resultSet.getInt("id"));
                pedido.setCodigoPedido(resultSet.getString("codigo_pedido"));
                pedido.setFechaPedido(resultSet.getDate("fecha_pedido"));
                pedido.setFechaEntrega(resultSet.getDate("fecha_entrega"));
                pedido.setDescripcion(resultSet.getString("descripcion"));
                pedido.setClienteId(resultSet.getInt("cliente_id"));
                pedido.setEntrega(resultSet.getString("entrega"));
                pedido.setEstado(resultSet.getString("estado"));
                pedidoList.add(pedido);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
            pedidoList = new ArrayList<>();
        }

        if (listaPedidos.getColumnCount() > 0) {
            TableColumn columnId = listaPedidos.getColumnModel().getColumn(0);
            columnId.setPreferredWidth(50);
        }

        return new ModeloPedido(pedidoList, sql);
    }

    private int getTotalPageCount() {
        int count = 0;
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT COUNT(*) AS total FROM pedidos p " +
                             "INNER JOIN clientes c ON p.cliente_id = c.id " +
                             "WHERE p.estado = 'Proceso' AND (" + // Solo pedidos en estado "Proceso"
                             "p.codigo_pedido LIKE CONCAT('%', ?, '%') OR " + // Filtro por código de pedido
                             "p.fecha_entrega LIKE CONCAT('%', ?, '%') OR " + // Filtro por fecha de entrega
                             "c.nombre LIKE CONCAT('%', ?, '%'))" // Filtro por nombre de cliente
             )) {

            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            mostrarDialogoPersonalizadoError("No hay conexión con la base de datos.", Color.decode("#C62828"));
        }

        int totalPageCount = count / 20;

        if (count % 20 > 0) {
            totalPageCount++;
        }

        return totalPageCount;
    }

    private void actualizarTabla() {
        listaPedidos.setModel(cargarDatos());
        configurarTablaMateriales();
        listaPedidos.getColumnModel().getColumn(6).setCellRenderer(new ListaPedidos.ButtonRenderer());
        listaPedidos.getColumnModel().getColumn(6).setCellEditor(new ListaPedidos.ButtonEditor());
        lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setForeground(Color.WHITE);
            setBackground(darkColor);
            setFocusPainted(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Realizar Pedido");
            return this;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JButton button;
        private int row, col;
        private JTable table;

        public ButtonEditor() {
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(this);
            button.setForeground(Color.WHITE);
            button.setBackground(darkColor);
            button.setFocusPainted(false);
            //button.setBorder(margin);
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("Realizar Pedido");
            this.table = table;
            this.row = row;
            this.col = column;
            return button;
        }

        public Object getCellEditorValue() {
            return "Realizar Pedido";
        }

        public void actionPerformed(ActionEvent e) {

            JButton btnSave = new JButton("Sí");
            JButton btnCancel = new JButton("No");

            // Personaliza los botones aquí
            btnSave.setBackground(darkColorBlue);
            btnCancel.setBackground(darkColorRed);

            // Personaliza los fondos de los botones aquí
            btnSave.setForeground(Color.WHITE);
            btnCancel.setForeground(Color.WHITE);

            // Elimina el foco
            btnSave.setFocusPainted(false);
            btnCancel.setFocusPainted(false);

            // Crea un JOptionPane
            JOptionPane optionPane = new JOptionPane(
                    "¿Desea realizar la venta de este pedido, e imprimir la factura?",
                    JOptionPane.QUESTION_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null,
                    new Object[]{}, // no options
                    null
            );

            // Crea un JDialog
            JDialog dialog = optionPane.createDialog("Vender");

            // Añade ActionListener a los botones
            btnSave.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String codigoVenta = null; // Inicializa la variable fuera del if

                    if (table != null) {
                        int modelRow = table.convertRowIndexToModel(table.getSelectedRow());
                        TableModel model = table.getModel();

                        if (model instanceof ModeloPedido) {
                            ModeloPedido pedidoModel = (ModeloPedido) model;
                            if (modelRow >= 0 && modelRow < pedidoModel.getRowCount()) {
                                Pedido pedido = pedidoModel.getPedidos().get(modelRow);
                                actualizarEstadoPedido(pedido, pedidoModel, modelRow);
                                codigoVenta = pedido.getCodigoPedido(); // Obtiene el código de pedido
                            }
                        }
                    }

                    dialog.dispose();

                    if (codigoVenta != null) {
                        mostrarDialogoPersonalizadoExito("   La venta del pedido, ha sido realizada con éxito.\nSeleccione el lugar donde guardará la factura de venta.", Color.decode("#263238"));
                        ListaVentas.imprimirFactura(codigoVenta);
                    }

                    listaPedidos.setModel(cargarDatos());
                    configurarTablaMateriales();

                    lblPagina.setText("Página " + (pagina + 1) + " de " + getTotalPageCount());
                    listaPedidos.getColumnModel().getColumn(6).setCellRenderer(new ListaPedidos.ButtonRenderer());
                    listaPedidos.getColumnModel().getColumn(6).setCellEditor(new ListaPedidos.ButtonEditor());
                    fireEditingStopped();
                }
            });

            btnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                    fireEditingCanceled();
                }
            });

            optionPane.setOptions(new Object[]{btnSave, btnCancel});
            dialog.setVisible(true);
        }
    }

    private void actualizarEstadoPedido(Pedido pedido, ModeloPedido pedidoModel, int modelRow) {
        int pedidoId = pedido.getId();

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE pedidos SET estado = 'Enviado' WHERE id = ?")) {
            preparedStatement.setInt(1, pedidoId);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                //System.out.println("El estado del pedido ID " + pedidoId + " ha sido actualizado a 'Enviado'.");
                pedido.setEstado("Enviado");
                pedidoModel.fireTableRowsUpdated(modelRow, modelRow);
            } else {
                //System.out.println("No se pudo actualizar el estado del pedido ID " + pedidoId + ".");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            //System.out.println("Error en la base de datos: " + ex.getMessage());
        }
    }

    public void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
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
        ListaPedidos listaPedidos = new ListaPedidos();
        listaPedidos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaPedidos.setVisible(true);
    }
}
