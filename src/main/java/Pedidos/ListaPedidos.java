package Pedidos;

import Desayunos.EditarDesayuno;
import Desayunos.VerDesayunos;
import Modelos.ModeloPedido;
import Modelos.PoliModeloProducto;
import Objetos.Conexion;
import Objetos.Pedido;
import Objetos.PoliProducto;

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
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "Seleccione una fila para continuar","Validación",JOptionPane.WARNING_MESSAGE);
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

        listaPedidos.getColumnModel().getColumn(7).setCellRenderer(new ListaPedidos.ButtonRenderer());
        listaPedidos.getColumnModel().getColumn(7).setCellEditor(new ListaPedidos.ButtonEditor());

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
        columnModel.getColumn(1).setPreferredWidth(130);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(120);
        columnModel.getColumn(4).setPreferredWidth(80);
        columnModel.getColumn(5).setPreferredWidth(45);
        columnModel.getColumn(6).setPreferredWidth(45);
        columnModel.getColumn(7).setPreferredWidth(40);

        columnModel.getColumn(0).setCellRenderer(new ListaPedidos.CenterAlignedRenderer());
        columnModel.getColumn(1).setCellRenderer(new ListaPedidos.CenterAlignedRenderer());
        columnModel.getColumn(2).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
        columnModel.getColumn(3).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
        columnModel.getColumn(4).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
        columnModel.getColumn(5).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
        columnModel.getColumn(6).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
        columnModel.getColumn(7).setCellRenderer(new ListaPedidos.LeftAlignedRenderer());
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
                     "SELECT p.id, p.codigo_pedido, p.fecha_pedido, p.fecha_entrega, p.descripcion, p.cliente_id, p.entrega, p.estado " +
                             "FROM pedidos p " +
                             "INNER JOIN clientes c ON p.cliente_id = c.id " +
                             "WHERE p.codigo_pedido LIKE CONCAT('%', ?, '%') OR " + // Filtro por código de pedido
                             "p.fecha_entrega LIKE CONCAT('%', ?, '%') OR " + // Filtro por fecha de entrega
                             "c.nombre LIKE CONCAT('%', ?, '%') " + // Filtro por nombre de cliente
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
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
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
                             "WHERE p.codigo_pedido LIKE CONCAT('%', ?, '%') OR " + // Filtro por código de pedido
                             "p.fecha_entrega LIKE CONCAT('%', ?, '%') OR " + // Filtro por fecha de entrega
                             "c.nombre LIKE CONCAT('%', ?, '%')"
                                )) { // Filtro por descripción

            preparedStatement.setString(1, busqueda);
            preparedStatement.setString(2, busqueda);
            preparedStatement.setString(3, busqueda);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
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
        listaPedidos.getColumnModel().getColumn(7).setCellRenderer(new ListaPedidos.ButtonRenderer());
        listaPedidos.getColumnModel().getColumn(7).setCellEditor(new ListaPedidos.ButtonEditor());
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
            setText("VENDER");
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
            button.setText("VENDER");
            this.table = table;
            this.row = row;
            this.col = column;
            return button;
        }

        public Object getCellEditorValue() {
            return "VENDER";
        }

        public void actionPerformed(ActionEvent e) {
            if (table != null) {
                int modelRow = table.convertRowIndexToModel(row);
                TableModel model = table.getModel();

                if (model instanceof PoliModeloProducto) {
                    PoliModeloProducto productoModel = (PoliModeloProducto) model;
                    PoliProducto producto = productoModel.getProducto(modelRow);

                    // Obtén el ID del detalle de pedido utilizando getIdDetalle
                    int detallePedidoId = producto.getIdDetalle();

                    try (Connection connection = sql.conectamysql()) {
                        // Primero, obtén el ID del pedido asociado con el detalle
                        int pedidoId;
                        try (PreparedStatement preparedStatement = connection.prepareStatement(
                                "SELECT pedido_id FROM detalles_pedidos WHERE id = ?")) {
                            preparedStatement.setInt(1, detallePedidoId);
                            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                                if (resultSet.next()) {
                                    pedidoId = resultSet.getInt("pedido_id");
                                } else {
                                    // Manejar el caso donde no se encuentra el pedido
                                    return;
                                }
                            }
                        }

                        // Luego, actualiza el estado del pedido a "Enviado"
                        try (PreparedStatement preparedStatement = connection.prepareStatement(
                                "UPDATE pedidos SET estado = 'Enviado' WHERE id = ?")) {
                            preparedStatement.setInt(1, pedidoId);
                            preparedStatement.executeUpdate();
                        }



                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        // Manejo de excepciones en caso de error en la base de datos.
                    }
                    fireEditingStopped();
                }
            }
        }
    }

    public static void main(String[] args) {
        ListaPedidos listaPedidos = new ListaPedidos();
        listaPedidos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        listaPedidos.setVisible(true);
    }
}
