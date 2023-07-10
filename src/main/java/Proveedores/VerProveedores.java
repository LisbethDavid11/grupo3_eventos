package Proveedores;

import Clientes.VerCliente;
import Objetos.Conexion;
import Objetos.Proveedor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerProveedores extends JFrame {
    private JPanel panel1;
    private JTextField etiquetaNombre;
    private JTextField etiquetaCorreo;
    private JTextField etiquetaTelefono;
    private JTextField etiquetaRTN;
    private JTextField etiquetaNombreVendedor;
    private JTextField etiquetaTelefonoVendedor;
    private JTextArea etiquetaDireccion;
    private JTextArea etiquetaDescripcion;
    private JButton volverButton;

    private final VerProveedores actual = this;

    private Conexion sql;
    private Connection mysql;
    private int id;

    public VerProveedores(int id){
        super("");
        setSize(550,350);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        this.id = id;
        mostrar();

        etiquetaDireccion.setLineWrap(true);
        etiquetaDireccion.setWrapStyleWord(true);

        etiquetaDescripcion.setLineWrap(true);
        etiquetaDescripcion.setWrapStyleWord(true);

        //Deshabilitamos la edición de los JTexfield y TextArea
        etiquetaNombre.setEditable(false);
        etiquetaNombre.setFocusable(false);
        etiquetaCorreo.setEditable(false);
        etiquetaCorreo.setFocusable(false);
        etiquetaTelefono.setEditable(false);
        etiquetaTelefono.setFocusable(false);
        etiquetaRTN.setEditable(false);
        etiquetaRTN.setFocusable(false);
        etiquetaNombreVendedor.setEditable(false);
        etiquetaNombreVendedor.setFocusable(false);
        etiquetaTelefonoVendedor.setEditable(false);
        etiquetaTelefonoVendedor.setFocusable(false);
        etiquetaDireccion.setEditable(false);
        etiquetaDireccion.setFocusable(false);
        etiquetaDescripcion.setEditable(false);
        etiquetaDescripcion.setFocusable(false);

        // Color de fondo del panel
        panel1.setBackground(Color.decode("#F5F5F5"));

        // Color de texto para los JTextField
        Color textColor = Color.decode("#212121");
        etiquetaNombre.setForeground(textColor);
        etiquetaCorreo.setForeground(textColor);
        etiquetaTelefono.setForeground(textColor);
        etiquetaRTN.setForeground(textColor);
        etiquetaNombreVendedor.setForeground(textColor);
        etiquetaTelefonoVendedor.setForeground(textColor);

        // Color de fondo para los JTextField
        Color textFieldColor = Color.decode("#FFFFFF");
        etiquetaNombre.setBackground(textFieldColor);
        etiquetaCorreo.setBackground(textFieldColor);
        etiquetaTelefono.setBackground(textFieldColor);
        etiquetaRTN.setBackground(textFieldColor);
        etiquetaNombreVendedor.setBackground(textFieldColor);
        etiquetaTelefonoVendedor.setBackground(textFieldColor);

        // Color de texto para el JTextArea
        etiquetaDireccion.setForeground(textColor);
        etiquetaDescripcion.setForeground(textColor);

        // Color de fondo para el JTextArea
        etiquetaDireccion.setBackground(textFieldColor);
        etiquetaDescripcion.setBackground(textFieldColor);

        // Color de texto de los botones
        volverButton.setForeground(Color.WHITE);

        // Color de fondo de los botones
        volverButton.setBackground(Color.decode("#263238"));
        volverButton.setFocusPainted(false);


        Color primaryColor = new Color(33, 150, 243); // Azul primario
        Color lightColor = new Color(100, 181, 246); // Azul claro
        Color darkColor = new Color(25, 118, 210); // Azul oscuro

        volverButton.setBackground(primaryColor);

        // Crea un margen de 10 píxeles desde el borde inferior
        EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

        // Aplica el margen al botón
        volverButton.setBorder(margin);

        volverButton.addMouseListener(new java.awt.event.MouseAdapter(){


            public void mouseEntered(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(lightColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                volverButton.setBackground(darkColor);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaProveedores listaProveedores = new ListaProveedores();
                listaProveedores.setVisible(true);
                actual.dispose();
            }
        });
    }

    private void mostrar(){
        sql = new Conexion();
        mysql = sql.conectamysql();

        try{
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM " + Proveedor.nombreTabla + " WHERE id = ?;");
            statement.setInt(1,this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                etiquetaNombre.setText(resultSet.getString(2));
                etiquetaRTN.setText(resultSet.getString(3));
                etiquetaTelefono.setText(resultSet.getString(4));
                etiquetaCorreo.setText(resultSet.getString(5));
                etiquetaDireccion.setText(resultSet.getString(6));
                etiquetaDescripcion.setText(resultSet.getString(7));
                etiquetaNombreVendedor.setText(resultSet.getString(8));
                etiquetaTelefonoVendedor.setText(resultSet.getString(9));
            }
        } catch (SQLException error) {
            // Mensaje de error
            System.out.println(error.getMessage());
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                VerProveedores verProveedores = new VerProveedores(1); // Pasa el ID del cliente que deseas ver
                verProveedores.setVisible(true);
            }
        });
    }
}


