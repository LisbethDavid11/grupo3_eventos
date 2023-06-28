package Objetos;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String drivers = "com.mysql.cj.jdbc.Driver";
    private String database = "eventos";
    private String user = "root";
    private String password = "";
    private String hostname = "localhost";
    private String port = "3306";
    private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database+"?serverTimezone=UTC";

    public Connection conectamysql(){
       Connection c = null;

       try {
           Class.forName(drivers);
           c = DriverManager.getConnection(url,user,password);

       }catch (SQLException error){
           System.out.println(error);
           JOptionPane.showMessageDialog(null, "Servidor fuera de linea!!!");
       } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);

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
    public static void soloNumeroIdentidad(KeyEvent e, int i, int tamano) {

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
