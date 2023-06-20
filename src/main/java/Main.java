import Empleados.VistaEmpleado;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        VistaEmpleado empleado = new VistaEmpleado();
        empleado.setVisible(true);

    }
}
