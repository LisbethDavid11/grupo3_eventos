package Login;
import Objetos.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioService {

    private Conexion sql;

    public UsuarioService(Conexion sql) {
        this.sql = sql;
    }

    public boolean esAdministrador(int userId) {
        String query = "SELECT roles.nombre FROM usuarios INNER JOIN roles ON usuarios.rol_id = roles.id WHERE usuarios.id = ?";

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String rol = rs.getString("nombre");
                return "Administrador".equals(rol);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}