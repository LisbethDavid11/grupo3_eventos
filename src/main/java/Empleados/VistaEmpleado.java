package Empleados;

import Modelos.ModeloDeEmpleado;
import Objetos.Conexion;
import Objetos.Empleados;

import javax.swing.*;
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

public class VistaEmpleado extends JFrame {
    private JPanel panel1;
    public JTable table1;
    private JButton nuevoEmpleadoButton;
    private JScrollPane scrollPane1;
    private JTextField barrabusqueda;
    private JButton botonatras;
    private JButton botonadelante;
    private JButton editarEmpleadoButton;
    private JButton verEmpleadoButton;
    private int intervalo = 0;
    private Conexion sql;
    private Connection mysql;
    private VistaEmpleado vistaEmpleado = this;
    private List<Empleados> ListaEmpleados = new ArrayList<>();
    public String dato0,dato1,dato2,dato3,dato4,dato5,dato6,dato7,dato8,dato9,dato10,dato11;



    public VistaEmpleado() throws SQLException {
      super("Lista De Empleados");
      setSize(600,600);
      setLocationRelativeTo(null);
      setContentPane(panel1);
      barrabusqueda.setText("");
      table1.setModel(CargarDatos());

      //barra busqueda
      barrabusqueda.addKeyListener(new KeyAdapter() {
          @Override
          public void keyReleased(KeyEvent e) {
              try {
                  ListaEmpleados = new ArrayList<>();
                  table1.setModel(CargarDatos());
              } catch (SQLException ex) {
                  throw new RuntimeException(ex);
              }

          }
      });

      //boton nuevo empleado

      nuevoEmpleadoButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              CrearEmpleado crearEmpleado = new CrearEmpleado();
              crearEmpleado.setVisible(true);
              vistaEmpleado.dispose();
              crearEmpleado.actualizarButton.setVisible(false);


          }
      });

      //boton adelante de la paginacion
      botonadelante.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              if (ListaEmpleados.size() == 10){
                  intervalo += 10;
                  botonatras.setEnabled(true);
                  try {
                      ListaEmpleados = new ArrayList<>();
                      table1.setModel(CargarDatos());
                  } catch (SQLException ex) {
                      throw new RuntimeException(ex);
                  }

              }else{
                  botonadelante.setEnabled(false);
              }
          }
      });

      //boton atras paginacion
      botonatras.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              if (intervalo == 0){
                  botonatras.setEnabled(false);

              }else{
                  intervalo -= 10;
                  botonadelante.setEnabled(true);
                  try {
                      ListaEmpleados = new ArrayList<>();
                      table1.setModel(CargarDatos());
                  } catch (SQLException ex) {
                      throw new RuntimeException(ex);
                  }

              }
          }
      });


      //Accion del boton editar

        editarEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int filaseleccionada;
                    filaseleccionada = table1.getSelectedRow();
                    if (filaseleccionada == -1) {
                        JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila");
                    } else {
                        CrearEmpleado editarEmpleado = new CrearEmpleado();
                        editarEmpleado.setVisible(true);
                        vistaEmpleado.dispose();
                        editarEmpleado.guardarButton.setVisible(false);

                        String id = table1.getValueAt(filaseleccionada, 0).toString();
                        Conexion objCon = new Conexion();
                        Connection conn = objCon.conectamysql();
                        try {
                            PreparedStatement stmtr = conn.prepareStatement("select * from Empleados where eventos.empleados.idEmpleados="+id);
                            ResultSet rsr = stmtr.executeQuery();

                            if (rsr.next()) {
                                dato0 = rsr.getString("idEmpleados");
                                dato1 = rsr.getString("Identidad");
                                dato2 = rsr.getString("Nombres");
                                dato3 = rsr.getString("Apellidos");
                                dato4 = rsr.getString("Genero");
                                dato5 = rsr.getString("Edad");
                                dato6 = rsr.getString("Correo");
                                dato7 = rsr.getString("Telefono");
                                dato8 = rsr.getString("NombreContactoDeEmergencia");
                                dato9 = rsr.getString("ContactoDeEmergencia");
                                dato10 = rsr.getString("Direccion");
                                dato11 = rsr.getString("TipoDeEmpleado");
                            }
                            ;
                            stmtr.close();
                            rsr.close();
                            conn.close();
                        } catch (Exception eu) {
                            eu.printStackTrace();
                        }

                        editarEmpleado.lblID.setText(dato0);
                        editarEmpleado.campoIdentidad.setText(dato1);
                        editarEmpleado.campoNombres.setText(dato2);
                        editarEmpleado.campoApellidos.setText(dato3);
                        if (dato4=="Femenino"){
                            editarEmpleado.femeninoRadioButton.setSelected(true);
                            editarEmpleado.masculinoRadioButton.setSelected(false);
                        }else {
                            editarEmpleado.femeninoRadioButton.setSelected(false);
                            editarEmpleado.masculinoRadioButton.setSelected(true);
                        }
                        editarEmpleado.campoEdad.setText(dato5);
                        editarEmpleado.campoCorreo.setText(dato6);
                        editarEmpleado.campoTelefono.setText(dato7);
                        editarEmpleado.campoNombreContacto.setText(dato8);
                        editarEmpleado.campoContacto.setText(dato9);
                        editarEmpleado.campoDireccion.setText(dato10);
                        if (dato11=="Temporal"){
                            editarEmpleado.temporalRadioButton.setSelected(true);
                            editarEmpleado.permanenteRadioButton.setSelected(false);
                        }else {
                            editarEmpleado.permanenteRadioButton.setSelected(false);
                            editarEmpleado.temporalRadioButton.setSelected(true);
                        }
                    }
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex + "\nInténtelo nuevamente",
                            " .::Error En la Operacion::.", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //boton ver
        verEmpleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int filaseleccionada;
                    filaseleccionada = table1.getSelectedRow();
                    if (filaseleccionada == -1) {
                        JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila");
                    } else {
                        VerEmpleado ver = new VerEmpleado();
                        ver.setVisible(true);
                        vistaEmpleado.dispose();

                        String id = table1.getValueAt(filaseleccionada, 0).toString();
                        Conexion objCon = new Conexion();
                        Connection conn = objCon.conectamysql();
                        try {
                            PreparedStatement stmtr = conn.prepareStatement("select * from Empleados where eventos.empleados.idEmpleados=" + id);
                            ResultSet rsr = stmtr.executeQuery();

                            if (rsr.next()) {
                                dato0 = rsr.getString("idEmpleados");
                                dato1 = rsr.getString("Identidad");
                                dato2 = rsr.getString("Nombres");
                                dato3 = rsr.getString("Apellidos");
                                dato4 = rsr.getString("Genero");
                                dato5 = rsr.getString("Edad");
                                dato6 = rsr.getString("Correo");
                                dato7 = rsr.getString("Telefono");
                                dato8 = rsr.getString("NombreContactoDeEmergencia");
                                dato9 = rsr.getString("ContactoDeEmergencia");
                                dato10 = rsr.getString("Direccion");
                                dato11 = rsr.getString("TipoDeEmpleado");
                            }

                            stmtr.close();
                            rsr.close();
                            conn.close();
                        } catch (Exception eu) {
                            eu.printStackTrace();
                        }

                        ver.lblID.setText(dato0);
                        ver.campoIdentidad.setText(dato1);
                        ver.campoNombres.setText(dato2);
                        ver.campoApellidos.setText(dato3);
                        if (dato4 == "Femenino") {
                            ver.campoGenero.setText("Femenino");
                        } else {
                            ver.campoGenero.setText("Masculino");
                        }
                        ver.campoEdad.setText(dato5);
                        ver.campoCorreo.setText(dato6);
                        ver.campoTelefono.setText(dato7);
                        ver.campoNombreContacto.setText(dato8);
                        ver.campoContacto.setText(dato9);
                        ver.campoDireccion.setText(dato10);
                        if (dato11 == "Temporal") {
                            ver.campoTipo.setText("Temporal");
                        } else {
                            ver.campoTipo.setText("Permanente");

                        }
                        ver.lblnomConcat.setText(dato2);
                        ver.lblapeConcat.setText(dato3);
                    }
                } catch (HeadlessException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex + "\nInténtelo nuevamente",
                            " .::Error En la Operacion::.", JOptionPane.ERROR_MESSAGE);
                }
            }



        });
    }

    public void verMetodo(){
        try {
            int filaseleccionada;
            filaseleccionada = table1.getSelectedRow();
            if (filaseleccionada == -1) {
                JOptionPane.showMessageDialog(null, "No se ha seleccionado ninguna fila");
            } else {
                VerEmpleado ver = new VerEmpleado();
                vistaEmpleado.dispose();

                String id = table1.getValueAt(filaseleccionada, 0).toString();
                Conexion objCon = new Conexion();
                Connection conn = objCon.conectamysql();
                try {
                    PreparedStatement stmtr = conn.prepareStatement("select * from Empleados where eventos.empleados.idEmpleados=" + id);
                    ResultSet rsr = stmtr.executeQuery();

                    if (rsr.next()) {
                        dato0 = rsr.getString("idEmpleados");
                        dato1 = rsr.getString("Identidad");
                        dato2 = rsr.getString("Nombres");
                        dato3 = rsr.getString("Apellidos");
                        dato4 = rsr.getString("Genero");
                        dato5 = rsr.getString("Edad");
                        dato6 = rsr.getString("Correo");
                        dato7 = rsr.getString("Telefono");
                        dato8 = rsr.getString("NombreContactoDeEmergencia");
                        dato9 = rsr.getString("ContactoDeEmergencia");
                        dato10 = rsr.getString("Direccion");
                        dato11 = rsr.getString("TipoDeEmpleado");
                    }

                    stmtr.close();
                    rsr.close();
                    conn.close();
                } catch (Exception eu) {
                    eu.printStackTrace();
                }

                ver.lblID.setText(dato0);
                ver.campoIdentidad.setText(dato1);
                ver.campoNombres.setText(dato2);
                ver.campoApellidos.setText(dato3);
                if (dato4 == "Femenino") {
                    ver.campoGenero.setText("Femenino");
                } else {
                    ver.campoGenero.setText("Masculino");
                }
                ver.campoEdad.setText(dato5);
                ver.campoCorreo.setText(dato6);
                ver.campoTelefono.setText(dato7);
                ver.campoNombreContacto.setText(dato8);
                ver.campoContacto.setText(dato9);
                ver.campoDireccion.setText(dato10);
                if (dato11 == "Temporal") {
                    ver.campoTipo.setText("Temporal");
                } else {
                    ver.campoTipo.setText("Permanente");

                }
            }
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex + "\nInténtelo nuevamente",
                    " .::Error En la Operacion::.", JOptionPane.ERROR_MESSAGE);
        }
    }



      //consulta barra de busqueda
      public ModeloDeEmpleado CargarDatos() throws SQLException {
          sql = new Conexion();
          mysql = sql.conectamysql();
          PreparedStatement preparedStatement = mysql.prepareStatement("select * from " + Empleados.nombreDeTabla + " where Identidad like concat('%',?,'%') or Nombres like concat('%',?,'%') or Apellidos like concat('%',?,'%') limit ?, 10");
          preparedStatement.setString(1,barrabusqueda.getText());
          preparedStatement.setString(2,barrabusqueda.getText());
          preparedStatement.setString(3,barrabusqueda.getText());
          preparedStatement.setInt(4,intervalo);
          ResultSet resultado = preparedStatement.executeQuery();

          while (resultado.next()){
              Empleados empleados = new Empleados();
              empleados.setId(resultado.getInt(1));
              empleados.setIdentidad(resultado.getString(2));
              empleados.setNombres(resultado.getString(3));
              empleados.setApellidos(resultado.getString(4));
              empleados.setGenero(resultado.getString(5));
              empleados.setEdad(resultado.getInt(6));
              empleados.setCorreo(resultado.getString(7));
              empleados.setTelefono(resultado.getString(8));
              empleados.setContacto(resultado.getString(9));
              empleados.setDireccion(resultado.getString(10));
              empleados.setTipoDeEmpleado(resultado.getString(11));

              ListaEmpleados.add(empleados);
          }
          return new ModeloDeEmpleado(Empleados.Columnas,ListaEmpleados);
      }


    private void createUIComponents(){

  }

}
