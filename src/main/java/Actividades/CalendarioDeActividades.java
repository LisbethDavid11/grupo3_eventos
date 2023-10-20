package Actividades;
import Objetos.Actividad;
import Objetos.Conexion;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarioDeActividades extends JFrame {
    private JLabel yearLabel, monthLabel, tituloLabel;
    private JButton prevButton, nextButton, crearButton, editarButton, verButton, cambiarButton;
    private JTable calendarTable;
    private List<Actividad> listaActividades;
    private Conexion sql;
    private JComboBox<String> yearBox;
    private DefaultTableModel calendarModel;
    private JScrollPane calendarScroll;
    private JScrollPane actividadesScroll;
    private int realYear, realMonth, realDay, currentYear, currentMonth;
    private int selectedRow = -1; // Para mantener un seguimiento del día seleccionado

    public CalendarioDeActividades() {
        this.setTitle("Calendario de Actividades");
        this.setSize(835, 535);
        setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listaActividades = cargarDatos();
        actividadesScroll = new JScrollPane();
        mostrarActividadesDelDia(-1, currentMonth, currentYear);  // -1 indica que se deben mostrar todas las actividades

        Color darkColor = new Color(38, 50, 56);       // Color de fondo
        Color primaryColor = new Color(55, 71, 79);    // Color primario
        Color secondaryColor = new Color(69, 90, 100); // Color secundario
        Color textColor = Color.WHITE;

        tituloLabel = new JLabel("Actividades");
        yearLabel = new JLabel("Año:");
        monthLabel = new JLabel("Mes:");
        yearBox = new JComboBox<>();
        prevButton = new JButton("<");
        nextButton = new JButton(">");

        crearButton = new JButton("CREAR");
        editarButton = new JButton("EDITAR");
        verButton = new JButton(" VER ");
        cambiarButton = new JButton("LISTAR");

        calendarModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        calendarTable = new JTable(calendarModel);
        calendarScroll = new JScrollPane(calendarTable);
        actividadesScroll = new JScrollPane(null);

        this.add(yearLabel);
        this.add(monthLabel);
        this.add(yearBox);
        this.add(prevButton);
        this.add(nextButton);
        this.add(calendarScroll);
        this.add(actividadesScroll);
        this.add(crearButton);
        this.add(editarButton);
        this.add(verButton);
        this.add(cambiarButton);

        prevButton.setBounds(10, 10, 45, 20);
        monthLabel.setBounds(80, 10, 70, 20);
        nextButton.setBounds(160, 10, 45, 20);
        yearLabel.setBounds(260, 10, 55, 20);
        yearBox.setBounds(300, 10, 75, 20);
        tituloLabel.setBounds(410, 10, 70, 20);
        calendarScroll.setBounds(10, 40, 400, 419); // Mantén la ubicación del calendario
        actividadesScroll.setBounds(410, 40, 400, 417); // Coloca el panel debajo del calendario

        crearButton.setBounds(410, 465, 90, 30);
        editarButton.setBounds(515, 465, 90, 30);
        verButton.setBounds(620, 465, 85, 30);
        cambiarButton.setBounds(725, 465, 85, 30);

        // Establecer colores
        tituloLabel.setForeground(primaryColor);
        yearLabel.setForeground(primaryColor);
        monthLabel.setForeground(primaryColor);
        yearBox.setBackground(primaryColor);
        yearBox.setForeground(textColor);
        prevButton.setBackground(primaryColor);
        prevButton.setForeground(textColor);
        prevButton.setFocusPainted(false);
        nextButton.setBackground(primaryColor);
        nextButton.setForeground(textColor);
        nextButton.setFocusPainted(false);
        calendarTable.setBackground(darkColor);
        calendarTable.setForeground(textColor);
        actividadesScroll.setBackground(darkColor);

        crearButton.setBackground(darkColor);
        crearButton.setForeground(textColor);
        editarButton.setBackground(darkColor);
        editarButton.setForeground(textColor);
        verButton.setBackground(darkColor);
        verButton.setForeground(textColor);
        cambiarButton.setBackground(darkColor);
        cambiarButton.setForeground(textColor);

        crearButton.setFocusPainted(false);
        editarButton.setFocusPainted(false);
        verButton.setFocusPainted(false);
        cambiarButton.setFocusPainted(false);

        String[] headers = {"Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb"};
        for (String header : headers) {
            calendarModel.addColumn(header);
        }

        calendarTable.getParent().setBackground(calendarTable.getBackground());
        calendarTable.getTableHeader().setResizingAllowed(false);
        calendarTable.getTableHeader().setReorderingAllowed(false);
        calendarTable.setColumnSelectionAllowed(true);
        calendarTable.setRowSelectionAllowed(true);
        calendarTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        calendarTable.setRowHeight(66);
        calendarModel.setColumnCount(7);
        calendarModel.setRowCount(6);

        GregorianCalendar cal = new GregorianCalendar();
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH);
        realMonth = cal.get(GregorianCalendar.MONTH);
        realYear = cal.get(GregorianCalendar.YEAR);
        currentMonth = realMonth;
        currentYear = realYear;

        int month = currentMonth;
        int year = currentYear;

        for (int i = year - 100; i <= year + 100; i++) {
            yearBox.addItem(String.valueOf(i));
        }

        refreshCalendar(month, year);

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentMonth == 0) {
                    currentMonth = 11;
                    currentYear -= 1;
                } else {
                    currentMonth -= 1;
                }
                refreshCalendar(currentMonth, currentYear);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentMonth == 11) {
                    currentMonth = 0;
                    currentYear += 1;
                } else {
                    currentMonth += 1;
                }
                refreshCalendar(currentMonth, currentYear);
            }
        });

        cambiarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               ListaActividades listaActividades = new ListaActividades();
               listaActividades.setVisible(true);
               dispose();
            }
        });

        crearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CrearActividad crearActividad = new CrearActividad();
                crearActividad.setVisible(true);
                dispose();
            }
        });

        verButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Seleccione un día en el calendario para ver actividades", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int selectedDay = Integer.parseInt(calendarTable.getValueAt(selectedRow, calendarTable.getSelectedColumn()).toString());
                mostrarActividadesDelDia(selectedDay, currentMonth, currentYear);
            }
        });


        yearBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (yearBox.getSelectedItem() != null) {
                    String b = yearBox.getSelectedItem().toString();
                    currentYear = Integer.parseInt(b);
                    refreshCalendar(currentMonth, currentYear);
                }
            }
        });

        calendarTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = calendarTable.getSelectedRow();
                int col = calendarTable.getSelectedColumn();
                String day = calendarTable.getValueAt(row, col).toString();
                // Cambiar el fondo del día seleccionado y el día actual
                calendarTable.setSelectionBackground(secondaryColor);
                calendarTable.setSelectionForeground(textColor);
                if (!day.equals("")) {
                    JOptionPane.showMessageDialog(null, "Has seleccionado el día: " + day);
                }

                if (!day.equals("")) {
                    int selectedDay = Integer.parseInt(day);
                    mostrarActividadesDelDia(selectedDay, currentMonth, currentYear);
                }
            }
        });

    }

    public void refreshCalendar(int month, int year) {
        String[] months = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        int numberOfDays, startOfMonth;

        prevButton.setEnabled(true);
        nextButton.setEnabled(true);
        if (month == 0 && year <= realYear - 10) {
            prevButton.setEnabled(false);
        }
        if (month == 11 && year >= realYear + 100) {
            nextButton.setEnabled(false);
        }

        yearBox.setSelectedItem(String.valueOf(year));
        monthLabel.setText(months[month]);

        calendarModel.setRowCount(0);
        calendarModel.setRowCount(6);

        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        numberOfDays = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        startOfMonth = cal.get(GregorianCalendar.DAY_OF_WEEK);

        for (int i = 1; i <= numberOfDays; i++) {
            int row = (i + startOfMonth - 2) / 7;
            int column = (i + startOfMonth - 2) % 7;
            calendarModel.setValueAt(i, row, column);

            // Marcar el día actual
            if (i == realDay && month == realMonth && year == realYear) {
                calendarTable.setValueAt(i + " ●", row, column); // Agrega el punto
            }
        }
    }

    public void mostrarActividadesDelDia(int day, int month, int year) {
        actividadesScroll.getViewport().removeAll();

        // Filtra las actividades que coinciden con la fecha seleccionada
        List<Actividad> actividadesDelDia = new ArrayList<>();
        for (Actividad actividad : listaActividades) {
            if (actividad.getFecha().getDate() == day && actividad.getFecha().getMonth() == month && actividad.getFecha().getYear() == year) {
                actividadesDelDia.add(actividad);
            }
        }

        // Crea un panel para mostrar las actividades
        JPanel actividadesPanel = new JPanel();
        actividadesPanel.setLayout(new BoxLayout(actividadesPanel, BoxLayout.Y_AXIS));

        // Agrega las actividades al panel
        for (Actividad actividad : actividadesDelDia) {
            JLabel nombreLabel = new JLabel(actividad.getNombre());
            JLabel horaInicioLabel = new JLabel("Inicio: " + actividad.getInicio().toString());
            JLabel horaFinLabel = new JLabel("Fin: " + actividad.getFin().toString());

            actividadesPanel.add(nombreLabel);
            actividadesPanel.add(horaInicioLabel);
            actividadesPanel.add(horaFinLabel);
            actividadesPanel.add(new JSeparator(JSeparator.HORIZONTAL));
            System.out.println(actividad.getNombre());
        }

        // Asigna el panel al componente actividadesScroll
        actividadesScroll.setViewportView(actividadesPanel);

    }


    public List<Actividad> cargarDatos() {
        List<Actividad> listaActividades = new ArrayList<>();

        // Establece una conexión a la base de datos
        Connection conn = null;
        try {
            sql = new Conexion();
            conn = sql.conectamysql(); // Get the database connection
            String query = "SELECT * FROM actividades";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                Date fecha = rs.getDate("fecha");
                Date inicio = rs.getTime("inicio");
                Date fin = rs.getTime("fin");

                Actividad actividad = new Actividad(id, nombre, fecha, inicio, fin); // Assuming that descripcion and direccion are not needed

                listaActividades.add(actividad);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return listaActividades;
    }


    public static void main(String[] args) {
        CalendarioDeActividades calendarioDeActividades = new CalendarioDeActividades();
        calendarioDeActividades.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendarioDeActividades.setVisible(true);
    }

}
