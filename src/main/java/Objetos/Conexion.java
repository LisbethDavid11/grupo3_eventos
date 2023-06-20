package Objetos;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String drivers = "com.mysql.cj.jdbc.Driver";
    private String database = "eventos";
    private String user = "root";
    private String password = "Th4danelly18";
    private String hostname = "localhost";
    private String port = "3306";
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database;

    public Connection conectamysql(){
       Connection c = null;

       try {
           Class.forName(drivers);
           c = DriverManager.getConnection(url,user,password);

       }catch (SQLException error){
           JOptionPane.showMessageDialog(null, "Servidor fuera de linea!!!");
       } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);

       }
        return c;
    }



}
