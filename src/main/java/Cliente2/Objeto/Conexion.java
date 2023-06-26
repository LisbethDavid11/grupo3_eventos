package Objeto;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // Librer a de SQL
    public String driver = "com.mysql.cj.jdbc.Driver";

    // Nombre de la base de datos
    public String database = "eventos";
    public String user = "root";
    public String password = "d12345678";
    // Host
    public String hostname = "localhost";
    // Puerto predeterminado para SQL Server
    public String port = "3306";
    public String url = "jdbc:mysql://" + hostname + ":"+port+"/"+database;


    public Connection iniciar(){
        Connection c  = null;

        try{
            Class.forName(driver);
            c = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException | SQLException error ){
            JOptionPane.showMessageDialog(null,"Sin Conexion");
        }


        return c;
    }
    public static void  soloLetra(KeyEvent e, int tamano, int limite, int pos) {

        if (pos == 0 && e.getKeyChar() == KeyEvent.VK_SPACE) {
            e.consume();
        }

        if (tamano <= limite) {
            if (tamano == 0 && e.getKeyChar() == KeyEvent.VK_SPACE) {
                e.consume();
            } else {

                Character letra = e.getKeyChar();

                if (!Character.isLetter(letra) && letra != KeyEvent.VK_SPACE) {
                    e.consume();
                }
            }
        } else {
            e.consume();
        }

    }
    public static void soloNumeros(KeyEvent e, int i, int tamano) {

        if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {

        } else {
            if (tamano <= i) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '-') {
                    e.consume();
                }
            } else {
                e.consume();
            }
        }
    }
    public static void soloNumerosIdentidad(KeyEvent e, int i, int tamano) {

        if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {

        } else {
            if (tamano <= i) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '-') {
                    e.consume();
                }
            } else {
                e.consume();
            }
        }
        if (tamano == 4){

        }
    }
}
