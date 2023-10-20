import Auth.Login;

import java.sql.SQLException;
public class Main {
    public static void main(String[] args) throws SQLException {
        Login LoginFrame = new Login();
        LoginFrame.setVisible(true);
        LoginFrame.pack();
        LoginFrame.setLocationRelativeTo(null);
    }
}