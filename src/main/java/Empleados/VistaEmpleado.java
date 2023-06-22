package Empleados;

import ModeladoDeTabla.ModeloDeEmpleado;
import Objetos.Conexion;
import Objetos.Empleados;

import javax.swing.*;
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
    private JTable table1;
    private JButton nuevoEmpleadoButton;
    private JScrollPane scrollPane1;
    private JTextField barrabusqueda;
    private JButton botonatras;
    private JButton botonadelante;
    private int intervalo = 0;
    private Conexion sql;
    private Connection mysql;
    private VistaEmpleado vistaEmpleado = this;
    private List<Empleados> ListaEmpleados = new ArrayList<>();



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
