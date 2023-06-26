package Cliente;

import Modelos.ModeloClientes;
import Objeto.Cliente;
import Objeto.Conexion;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IndexCliente  extends JFrame{

    private Conexion sql;
    private Connection mysql;

    private JPanel panel1;
    private JTable table1;
    private JButton nuevoButton;
    private JTextField jtfBuscar;
    private JButton editarButton;
    private JButton verButton;

    private int id_clienteVerEditar= 1;



    //cargar Datos de la tabla
    List<Cliente> lista = new ArrayList<>();

    private final IndexCliente actual = this;

    public IndexCliente()  {
        super("Lista de Clientes");
        setSize(600,600);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        editarButton.setVisible(false);
        verButton.setVisible(false);

        jtfBuscar.setText("");
        table1.setModel(cargarDatos());

        jtfBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                table1.setModel(cargarDatos());
            }
        });

        nuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrearFormularioCliente  form = new CrearFormularioCliente();
                form.setVisible(true);
                actual.dispose();
            }
        });

        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VerFormularioCliente  form = new VerFormularioCliente(id_clienteVerEditar);
                form.setVisible(true);
                actual.dispose();

            }
        });
       editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               EditarFormularioCliente  form= new EditarFormularioCliente(id_clienteVerEditar);
                form.setVisible(true);
                actual.dispose();
            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selection = table1.getSelectedRow();
                editarButton.setVisible(true);
                verButton.setVisible(true);
                id_clienteVerEditar=lista.get(selection).getId();
                actual .setSize(600,600);

            }
        });
    }

    public ModeloClientes  cargarDatos(){
        sql = new Conexion();
        mysql = sql.iniciar();

        try{
        PreparedStatement statement = mysql.prepareStatement("Select * from "+ Cliente.nombreTabla+" where id like concat('%' ,?, '%') or nombre like concat('%' ,?,'%') ");
        statement.setString(1, jtfBuscar.getText());
        statement.setString(2, "f");
        ResultSet resultSet = statement.executeQuery();
            lista = new ArrayList<>();
            while (resultSet.next()){
            Cliente cliente = new Cliente();

            cliente.setId(resultSet.getInt(1));
            cliente.setNombre(resultSet.getString(2));
                cliente.setApellido(resultSet.getString(3));
            cliente.setIdentidad(resultSet.getString(4));
            cliente.setTelefono(resultSet.getString(5));
            cliente.setDomicilio(resultSet.getString(6));
            cliente.setTipo_cliente(resultSet.getString(7));

                lista.add(cliente);
            }

            return new ModeloClientes(
                    Cliente.columnas,
                    lista
            );
        }catch (SQLException error){
            //mensaje de error
            System.out.println(error.getMessage());
            return null;
        }
    }
}

