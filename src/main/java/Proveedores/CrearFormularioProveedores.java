package Proveedores;

import Objetos.Conexion;
import Objetos.Empleados;
import Objetos.Proveedores;

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

public class CrearFormularioProveedores extends JFrame {
    private JPanel panel1;
    private JTextField jtDescripcion;
    private JFormattedTextField jtTelefono;
    private JTextField jtNombre;
    private JFormattedTextField jtVendedorTelefono;
    private JFormattedTextField jtRTN;
    private JTextField jtCorreo;
    private JTextArea jtDireccion;
    private JTextField jtVendedorAsignado;
    private JButton guardarButton;
    private JButton cancelarButton;
    private final CrearFormularioProveedores actual = this;
    private Conexion sql;
    private Connection mysql;

    private JTextField[] campos = {
            jtNombre,
            jtRTN,
            jtCorreo,
            jtTelefono,
            jtDescripcion,
            jtVendedorAsignado,
            jtVendedorTelefono
    };

    public CrearFormularioProveedores() {
        super("Registro de proveedores");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setContentPane(panel1);

        jtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e,jtNombre.getText().length(),40,jtNombre.getCaretPosition());
            }
        });

        jtVendedorAsignado.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e,jtVendedorAsignado.getText().length(),40,jtVendedorAsignado.getCaretPosition());
            }
        });


        jtDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloLetra(e,jtDescripcion.getText().length(),40,jtDescripcion.getCaretPosition());
            }
        });

        jtTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloNumeros(e,7, jtTelefono.getText().length());

            }
        });

        jtVendedorTelefono.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Conexion.soloNumeros(e,7, jtVendedorTelefono.getText().length());

            }
        });


        try {
                    MaskFormatter formatter = new MaskFormatter("####-####-######");


                    jtRTN.setFormatterFactory(new DefaultFormatterFactory(formatter));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }


                cancelarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        IndexProveedores indexProveedores = new IndexProveedores();
                        indexProveedores.setVisible(true);
                        actual.dispose();
                    }
                });
                guardarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Validaciones
                        int validar = 0;
                        int contador = 0;
                        int posicion = 0;
                        String mensaje = "Faltó Ingresar:\n";
                        for (JTextField campo : campos) {
                            if (campo.getText().replaceAll("\\s+", "").equals("")) {
                                validar += 1;
                                mensaje += Proveedores.columnasTabla[contador] + "\n";
                                posicion = contador;
                            }
                            contador += 1;
                        }

                        if (validar > 0) {
                            JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.INFORMATION_MESSAGE);

                            if (validar == 1) {
                                campos[posicion].requestFocus();
                            }
                            return;
                        }

                        if (jtDireccion.getText().replaceAll("\\s+", "").equals("")) {
                            JOptionPane.showMessageDialog(null, "La dirección no puede estar vacía", "Validación", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        if (!Proveedores.validarFormatoNombre(jtVendedorAsignado.getText())) {
                            JOptionPane.showMessageDialog(null, "El nombre del vendedor solo debe contener letras", "Validación", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        if (!Proveedores.validarFormatoNombre(jtNombre.getText())) {
                            JOptionPane.showMessageDialog(null, "El nombre de la empresa solo debe contener letras", "Validación", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        if (jtTelefono.getText().charAt(0) == '1' || jtTelefono.getText().charAt(0) == '4' || jtTelefono.getText().charAt(0) == '5' || jtTelefono.getText().charAt(0) == '6' || jtTelefono.getText().charAt(0) == '7' || jtTelefono.getText().charAt(0) == '0') {
                            JOptionPane.showMessageDialog(null, "Su número de teléfono no es válido", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        if (jtVendedorTelefono.getText().charAt(0) == '1' || jtVendedorTelefono.getText().charAt(0) == '4' || jtVendedorTelefono.getText().charAt(0) == '5' || jtVendedorTelefono.getText().charAt(0) == '6' || jtVendedorTelefono.getText().charAt(0) == '7' ||jtVendedorTelefono.getText().charAt(0) == '0') {
                            JOptionPane.showMessageDialog(null, "Su número de teléfono no es válido", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }


                        if (!Proveedores.validarFormatoCorreo(jtCorreo.getText())){
                            JOptionPane.showMessageDialog(null,"Formato invalido. Ej: email@xxx.xxx");
                            return;
                        }



                    boolean des = guardar();

                        if (des) ;
                        IndexProveedores indexProveedores = new IndexProveedores();
                        indexProveedores.setVisible(true);
                        actual.dispose();
                    }
                });
            }


            private boolean guardar() {
                sql = new Conexion();
                mysql = sql.conectamysql();

                try {
                    PreparedStatement statement = mysql.prepareStatement("INSERT INTO " + Proveedores.nombreTabla + " (`empresaProveedora`,`rtn`, `telefono`, `correo`, `direccion`,`descripcion`,`nombreVendedor`,`telefonoVendedor`) VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
                    statement.setString(1, jtNombre.getText());
                    statement.setString(2, jtRTN.getText());
                    statement.setString(3, jtTelefono.getText());
                    statement.setString(4, jtCorreo.getText());
                    statement.setString(5, jtDireccion.getText());
                    statement.setString(6, jtDescripcion.getText());
                    statement.setString(7, jtVendedorAsignado.getText());
                    statement.setString(8, jtVendedorTelefono.getText());
                    System.out.println(statement.executeLargeUpdate());
                    return true;

                } catch (SQLException erro) {
                    //Mensaje de error para mostrar
                    System.out.println(erro.getMessage());
                    return false;
                }
            }


            private void createUIComponents() {
                // TODO: place custom component creation code here
            }
        }
