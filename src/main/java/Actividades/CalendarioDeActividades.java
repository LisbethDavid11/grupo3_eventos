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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarioDeActividades extends JFrame {
    private JLabel yearLabel, monthLabel, tituloLabel;
    private JButton prevButton, nextButton, crearButton;
    private JTable calendarTable;
    private List<Actividad> listaActividades;
    private Conexion sql;
    private JComboBox<String> yearBox;
    private DefaultTableModel calendarModel;
    private JScrollPane calendarScroll;
    private JScrollPane actividadesScroll;
    private int realYear, realMonth, realDay, currentYear, currentMonth;
    private int selectedRow = -1; // Para mantener un seguimiento del día seleccionado
    Font font = new Font("Century Gothic", Font.BOLD, 18);
    Font font2 = new Font("Century Gothic", Font.PLAIN, 10);
    Color darkColor = new Color(38, 50, 56);

    public CalendarioDeActividades() {
        this.setTitle("Calendario de Actividades");
        this.setSize(835, 535);
        setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        listaActividades = cargarDatos();
        actividadesScroll = new JScrollPane();
        this.add(actividadesScroll);
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
        crearButton.setBackground(new Color(0, 123, 255)); // Color azul Material Design
        calendarModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        calendarTable = new JTable(calendarModel);
        calendarScroll = new JScrollPane(calendarTable);

        this.add(yearLabel);
        this.add(monthLabel);
        this.add(yearBox);
        this.add(prevButton);
        this.add(nextButton);
        this.add(calendarScroll);
        this.add(crearButton);

        prevButton.setBounds(10, 10, 45, 20);
        monthLabel.setBounds(80, 10, 70, 20);
        nextButton.setBounds(160, 10, 45, 20);
        yearLabel.setBounds(260, 10, 55, 20);
        yearBox.setBounds(300, 10, 75, 20);
        tituloLabel.setBounds(410, 10, 70, 20);
        calendarScroll.setBounds(10, 40, 400, 419); // Mantén la ubicación del calendario

        crearButton.setBounds(410, 465, 400, 30);

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

        crearButton.setForeground(textColor);
        crearButton.setFocusPainted(false);

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

        crearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CrearActividad crearActividad = new CrearActividad();
                crearActividad.setVisible(true);
                dispose();
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
                Object value = calendarTable.getValueAt(row, col); // Obtén el valor de la celda
                if (value != null) {
                    String day = value.toString();
                    // Cambiar el fondo del día seleccionado y el día actual
                    calendarTable.setSelectionBackground(secondaryColor);
                    calendarTable.setSelectionForeground(textColor);

                    if (!day.equals("")) {
                        int selectedDay = Integer.parseInt(day.split(" ")[0]);
                        mostrarActividadesDelDia(selectedDay, currentMonth, currentYear);
                    }
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

            if (i == realDay && month == realMonth && year == realYear) {
                // Marca el día actual con un punto azul
                String dayWithDot = i + " <font color='#007BFF'>●</font>";
                calendarTable.setValueAt("<html>" + dayWithDot + "</html>", row, column);
            } else {
                boolean hasActivities = hasActivitiesOnDay(i, month, year);
                if (hasActivities) {
                    // Marca los días con actividades con un punto azul
                    String dayWithDot = i + " <font color='#007BFF'>●</font>";
                    calendarTable.setValueAt("<html>" + dayWithDot + "</html>", row, column);
                } else {
                    // No agrega el punto, solo el número del día
                    calendarTable.setValueAt(i, row, column);
                }
            }

        }
    }

    public void mostrarActividadesDelDia(int day, int month, int year) {

        // Filtra las actividades que coinciden con la fecha seleccionada
        List<Actividad> actividadesDelDia = new ArrayList<>();
        SimpleDateFormat dia = new SimpleDateFormat("d");
        SimpleDateFormat mes = new SimpleDateFormat("M");
        SimpleDateFormat anio = new SimpleDateFormat("yyyy");

        if (day > -1){
            for (Actividad actividad : listaActividades) {

                if (Integer.parseInt(dia.format(actividad.getFecha())) == day && Integer.parseInt(mes.format(actividad.getFecha()))-1 == month && Integer.parseInt(anio.format(actividad.getFecha())) == year) {
                    actividadesDelDia.add(actividad);
                }
            }
        }else {
            actividadesDelDia.addAll(listaActividades);
        }

        // Crea un panel para mostrar las actividades
        JPanel actividadesPanel = new JPanel();
        actividadesPanel.removeAll();
        actividadesPanel.setLayout(new BoxLayout(actividadesPanel, BoxLayout.Y_AXIS));

        // Crea un JLabel de tamaño 400x15 y agrega algún contenido si es necesario
        JLabel item = new JLabel();
        item.setPreferredSize(new Dimension(30, 20));

        item.setBackground(Color.white);
        item.setForeground(darkColor);
        // Agrega el JLabel al panel de actividades

        actividadesPanel.add(item);
        // Agrega las actividades al panel
        for (Actividad actividad : actividadesDelDia) {
            actividadesPanel.setBackground(darkColor);

            JLabel nombreLabel = new JLabel(actividad.getNombre());
            nombreLabel.setFont(font);
            nombreLabel.setForeground(Color.white);

            Date horaInicio = actividad.getInicio();
            Date horaFin = actividad.getFin();

            SimpleDateFormat formato = new SimpleDateFormat("hh:mm a");
            String horaFormateadaInicio = formato.format(horaInicio);
            String horaFormateadaFin = formato.format(horaFin);

            // Crea un panel para contener la información de la actividad
            JPanel actividadPanel = new JPanel();
            actividadPanel.setLayout(new GridLayout(1, 0)); // Utiliza GridLayout con una fila y columnas flexibles
            actividadPanel.setBackground(darkColor);
            JLabel horaInicioLabel = new JLabel(horaFormateadaInicio + " - " + horaFormateadaFin);
            horaInicioLabel.setFont(font2);
            horaInicioLabel.setForeground(Color.white);
            // Crea un ImageIcon con la ruta de la imagen
            ImageIcon iconoVer = new ImageIcon("img/verCarta.png");

            // Crea el botón "Ver" con el icono
            JButton verButton = new JButton(iconoVer);
            verButton.setBackground(darkColor); // Azul Material Design
            verButton.setBorderPainted(false);
            verButton.setFocusPainted(false);

            verButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Acción al hacer clic en el botón "Ver"
                    int actividadID = actividad.getId();
                    VerActividad verActividades = new VerActividad(actividadID);
                    verActividades.setVisible(true);
                    dispose();
                }
            });

            // Agrega los componentes al panel de la actividad
            actividadPanel.add(nombreLabel);
            actividadPanel.add(horaInicioLabel);
            actividadPanel.add(verButton);

            // Agrega el panel de la actividad al panel de actividades
            actividadesPanel.add(actividadPanel);
            actividadesPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        }


        this.remove(actividadesScroll);

        actividadesScroll = new JScrollPane(actividadesPanel);
        actividadesScroll.setBounds(410, 40, 400, 417); // Coloca el panel debajo del calendario

        actividadesScroll.setBackground(darkColor);
        this.add(actividadesScroll);
        actividadesScroll.getVerticalScrollBar().setValue(actividadesScroll.getVerticalScrollBar().getValue() + 1);

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

    public boolean hasActivitiesOnDay(int day, int month, int year) {
        SimpleDateFormat dia = new SimpleDateFormat("d");
        SimpleDateFormat mes = new SimpleDateFormat("M");
        SimpleDateFormat anio = new SimpleDateFormat("yyyy");

        for (Actividad actividad : listaActividades) {
            int actividadDay = Integer.parseInt(dia.format(actividad.getFecha()));
            int actividadMonth = Integer.parseInt(mes.format(actividad.getFecha())) - 1;
            int actividadYear = Integer.parseInt(anio.format(actividad.getFecha()));

            if (actividadDay == day && actividadMonth == month && actividadYear == year) {
                return true;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        CalendarioDeActividades calendarioDeActividades = new CalendarioDeActividades();
        calendarioDeActividades.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendarioDeActividades.setVisible(true);
    }

}
