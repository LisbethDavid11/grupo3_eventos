package Cliente;

import Objeto.Cliente;
import Objeto.Conexion;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Objects;

public class CrearFormularioCliente extends  JFrame{

    private JPanel panel1;
    private JTextField campoNombre;
    private JTextField campoApellido;
    private JFormattedTextField campoIdentidad;
    private JTextField campoTelefono;
    private JTextField campoDomicilio;
    public ButtonGroup grupoTipo_cliente;
    private JRadioButton mayoristaRadioButton;
    private JRadioButton alDetalleRadioButton;
    private JButton guardarButton;
    private JButton cancelarButton;
    private final CrearFormularioCliente actual = this;
    private Conexion sql;
    private Connection mysql;
    private JTextField[] campos = {campoNombre, campoApellido, campoIdentidad, campoTelefono, campoDomicilio};



    public CrearFormularioCliente() {
        super("Nuevo Registro de Cliente");
        setSize(600,600);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        grupoTipo_cliente = new ButtonGroup();
        grupoTipo_cliente.add(mayoristaRadioButton);
        grupoTipo_cliente.add(alDetalleRadioButton);
        mayoristaRadioButton.setSelected(true);

        try {
            MaskFormatter formatoIdentidad= new MaskFormatter("####-####-#####");
            campoIdentidad.setFormatterFactory(new DefaultFormatterFactory(formatoIdentidad));
        } catch (ParseException e) {
            throw new RuntimeException(e);

        }
        campoNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e, campoNombre.getText().length(),49, campoNombre.getCaretPosition());
            }
        });
        campoApellido.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e, campoApellido.getText().length(),49, campoApellido.getCaretPosition());
            }
        });
        campoDomicilio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e, campoDomicilio.getText().length(),49, campoDomicilio.getCaretPosition());
            }
        });
        campoTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloNumeros(e,7, campoTelefono.getText().length());

            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IndexCliente indexCliente = new IndexCliente();
                indexCliente.setVisible(true);
                actual.dispose();

            }
        });
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int validacion = 0;
                    int contador = 0;
                    String mensaje = "\"Faltan campos a ingresar: \n";
                    for (JTextField campo : campos) {

                        if (!Objects.equals(campo.getText().replaceAll("\\s+", ""), "")) {
                        } else {
                            mensaje += Cliente.columnasdeTable[contador] + "\n";
                            validacion += 1;

                        }
                        contador += 1;

                    }
                    if (validacion > 0){
                        JOptionPane.showMessageDialog(null,mensaje);
                        return;
                    }
                    if (campoTelefono.getText().charAt(0) == '1' || campoTelefono.getText().charAt(0) == '4' || campoTelefono.getText().charAt(0) == '5' || campoTelefono.getText().charAt(0) == '6' || campoTelefono.getText().charAt(0) == '7' || campoTelefono.getText().charAt(0) == '0'){
                        JOptionPane.showMessageDialog(null,"Su número de teléfono no es válido");
                        return;
                    }
                    if (campoIdentidad.getText().length() <15){
                        JOptionPane.showMessageDialog(null,"Su número de identidad debe contener 15 digitos incluyendo guiones");
                        return;
                    }
                    if (campoTelefono.getText().length() <8){
                        JOptionPane.showMessageDialog(null,"Su número de teléfono debe contener 8 digitos");
                        return;
                    }
                    if (!Cliente.ComprobarIdentidad(campoIdentidad.getText())){
                        JOptionPane.showMessageDialog(null,"Identidad no válida" );
                        return;
                    }
                guardar();
                } catch (SQLException ex) {

                    throw new RuntimeException(ex);
                }
                IndexCliente indexCliente = new IndexCliente();
                indexCliente.setVisible(true);
                actual.dispose();

            }

        });
    }


    private void guardar() throws  SQLException {
        sql = new Conexion();
        mysql = sql.iniciar();

            PreparedStatement statement = mysql.prepareStatement("INSERT INTO "+Cliente.nombreTabla+"(`nombre`, `apellido`, `identidad`, `telefono`, `domicilio`, `tipo_cliente`) VALUES (?, ?, ?, ?, ?, ?);");
            statement.setString(1, campoNombre.getText());
            statement.setString(2, campoApellido.getText());
            statement.setString(3, campoIdentidad.getText());
            statement.setString(4, campoTelefono.getText());
            statement.setString(5, campoDomicilio.getText());
            statement.setString(6, mayoristaRadioButton.isSelected()?"Mayorista":"Al detalle");
            System.out.println(statement.execute());
}

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}