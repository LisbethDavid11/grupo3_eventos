package Actividades;

import Objetos.Actividad;
import Objetos.Conexion;
import org.jdesktop.swingx.prompt.PromptSupport;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
    private JLabel yearLabel, monthLabel, tituloLabel, tituloPrincipalLabel;
    private JButton prevButton, nextButton, crearButton;
    private JTable calendarTable;
    private List<Actividad> listaActividades;
    private Conexion sql;
    private JComboBox<String> yearBox;
    private DefaultTableModel calendarModel;
    private JScrollPane calendarScroll;
    private JScrollPane actividadesScroll;
    private JPanel panelTitulo;
    private JTextField busquedaTextField;
    private int realYear, realMonth, realDay, currentYear, currentMonth;
    private int selectedRow = -1; // Para mantener un seguimiento del día seleccionado
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 20);
    Font font = new Font("Century Gothic", Font.BOLD, 16);
    Font font2 = new Font("Century Gothic", Font.PLAIN, 10);
    Font fontHeader = new Font("Century Gothic", Font.BOLD, 10);
    Color darkColor = new Color(38, 50, 56);
    Color primaryColor = new Color(55, 71, 79);    // Color primario
    Color secondaryColor = new Color(69, 90, 100); // Color secundario
    Color darkBlue = new Color(0, 123, 255);
    Color textColor = Color.WHITE;

    public CalendarioDeActividades() {
        this.setTitle("Calendario de Actividades");
        this.setSize(835, 505);
        setLocationRelativeTo(null);
        this.setLayout(null);

        listaActividades = cargarDatos();
        actividadesScroll = new JScrollPane();
        panelTitulo = new JPanel();

        this.add(actividadesScroll);
        mostrarActividadesDelDia(-1, currentMonth, currentYear);  // -1 indica que se deben mostrar todas las actividades
        calendarModel = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };

        tituloPrincipalLabel = new JLabel("CALENDARIO DE ACTIVIDADES");
        tituloLabel = new JLabel("ACTIVIDADES");
        yearBox = new JComboBox<>();
        yearLabel = new JLabel("Año:");
        monthLabel = new JLabel("Mes:");
        prevButton = new JButton("<");
        nextButton = new JButton(">");
        crearButton = new JButton("CREAR");
        calendarTable = new JTable(calendarModel);
        calendarScroll = new JScrollPane(calendarTable);
        busquedaTextField = new JTextField();
        PromptSupport.init("Buscar por nombre, hora incial u hora final", Color.GRAY, Color.WHITE, busquedaTextField);

        this.add(tituloPrincipalLabel);
        this.add(yearLabel);
        this.add(monthLabel);
        this.add(yearBox);
        this.add(prevButton);
        this.add(nextButton);
        this.add(calendarScroll);
        this.add(crearButton);
        this.add(panelTitulo);
        this.add(busquedaTextField);
        panelTitulo.add(tituloLabel);

        tituloPrincipalLabel.setBounds(10, 10, 475, 22);
        prevButton.setBounds(10, 50, 45, 22);
        monthLabel.setBounds(80, 50, 70, 22);
        nextButton.setBounds(160, 50, 45, 22);
        yearLabel.setBounds(260, 50, 55, 22);
        yearBox.setBounds(300, 50, 75, 22);
        panelTitulo.setBounds(410, 80, 400, 20);
        tituloLabel.setBounds(410, 68, 400, 22);
        calendarScroll.setBounds(10, 80, 400, 387);
        busquedaTextField.setBounds(410, 50, 300, 23);
        crearButton.setBounds(710, 50, 90, 22);

        // Establecer colores
        tituloLabel.setForeground(Color.white);
        tituloLabel.setFont(fontHeader);
        tituloPrincipalLabel.setFont(fontTitulo);

        panelTitulo.setBackground(darkBlue);

        yearLabel.setForeground(primaryColor);
        monthLabel.setForeground(primaryColor);

        yearBox.setBackground(primaryColor);
        yearBox.setForeground(textColor);
        yearBox.setFont(font);

        prevButton.setBackground(primaryColor);
        prevButton.setForeground(textColor);
        prevButton.setFocusPainted(false);
        prevButton.setFont(font);

        nextButton.setBackground(primaryColor);
        nextButton.setForeground(textColor);
        nextButton.setFocusPainted(false);
        nextButton.setFont(font);

        crearButton.setBackground(darkColor);
        crearButton.setForeground(textColor);
        crearButton.setFocusPainted(false);
        crearButton.setFont(font);

        calendarTable.setBackground(darkColor);
        calendarTable.setForeground(textColor);
        calendarTable.setFont(new Font(null, Font.BOLD, 22));

        JTableHeader encabezado = calendarTable.getTableHeader();
        encabezado.setForeground(Color.WHITE);
        encabezado.setBackground(darkBlue);
        encabezado.setFont(fontHeader);

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
        calendarTable.setRowHeight(61);
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
                if (e.getClickCount() == 1) { // Ensure that it responds to a single click
                    int row = calendarTable.getSelectedRow();
                    int col = calendarTable.getSelectedColumn();
                    Object value = calendarTable.getValueAt(row, col); // Obtener el valor de la celda

                    // Cambiar el fondo del día seleccionado y el día actual
                    calendarTable.setSelectionBackground(secondaryColor);
                    calendarTable.setSelectionForeground(textColor);

                    if (value != null) {
                        String day = value.toString(); // Convertir el valor a String

                        if (!day.isEmpty()) {
                            // Eliminar caracteres no numéricos antes de analizar el número
                            String strippedDay = day.replaceAll("[^0-9]", "");
                            if (!strippedDay.isEmpty()) {
                                int selectedDay = Integer.parseInt(strippedDay);
                                mostrarActividadesDelDia(selectedDay, currentMonth, currentYear);
                            }
                        }
                    }
                }
            }
        });


        busquedaTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterActivities();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterActivities();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Este método se usa generalmente con DocumentListeners de componentes como JTextPane, no es necesario para JTextField.
            }
        });




    }

    private void filterActivities() {
        String searchText = busquedaTextField.getText().toLowerCase();
        int selectedDay = selectedRow; // Obtén el día seleccionado, -1 si no hay ninguno seleccionado

        // Filtra las actividades según el día seleccionado y el texto de búsqueda
        List<Actividad> filteredActivities = new ArrayList<>();
        SimpleDateFormat dia = new SimpleDateFormat("d");
        SimpleDateFormat mes = new SimpleDateFormat("M");
        SimpleDateFormat anio = new SimpleDateFormat("yyyy");

        for (Actividad actividad : listaActividades) {
            int actividadDay = Integer.parseInt(dia.format(actividad.getFecha()));
            int actividadMonth = Integer.parseInt(mes.format(actividad.getFecha())) - 1;
            int actividadYear = Integer.parseInt(anio.format(actividad.getFecha()));

            // Filtrar por día seleccionado si es válido (-1 indica mostrar todas las actividades)
            boolean filterByDay = (selectedDay == -1) || (selectedDay == actividadDay);

            // Filtrar por nombre, hora inicial o hora final que contienen el texto de búsqueda
            boolean filterBySearchText = actividad.getNombre().toLowerCase().contains(searchText)
                    || actividad.getInicio().toString().toLowerCase().contains(searchText)
                    || actividad.getFin().toString().toLowerCase().contains(searchText);

            if (filterByDay && filterBySearchText) {
                filteredActivities.add(actividad);
            }
        }

        mostrarActividades(filteredActivities);
    }

    private void mostrarActividades(List<Actividad> actividades) {
        // Elimina el panel actual de actividades
        if (actividadesScroll != null) {
            this.remove(actividadesScroll);
        }

        // Crea un nuevo panel para mostrar las actividades filtradas
        JPanel actividadesPanel = new JPanel();
        actividadesPanel.setLayout(new BoxLayout(actividadesPanel, BoxLayout.Y_AXIS));
        actividadesPanel.setBackground(darkColor);

        for (Actividad actividad : actividades) {
            actividadesPanel.setBackground(darkColor);

            JLabel nombreLabel = new JLabel(" " + actividad.getNombre());
            nombreLabel.setFont(font);
            nombreLabel.setForeground(Color.white);

            Date horaInicio = actividad.getInicio();
            Date horaFin = actividad.getFin();

            SimpleDateFormat formato = new SimpleDateFormat("hh:mm a");
            String horaFormateadaInicio = formato.format(horaInicio);
            String horaFormateadaFin = formato.format(horaFin);

            JPanel actividadPanel = new JPanel();
            actividadPanel.setLayout(new GridLayout(3, 0));
            actividadPanel.setBackground(primaryColor);

            JLabel horaInicioLabel = new JLabel(" " + horaFormateadaInicio + " - " + horaFormateadaFin);
            horaInicioLabel.setFont(font2);
            horaInicioLabel.setForeground(Color.white);

            ImageIcon iconoVer = new ImageIcon("img/verCarta.png");
            int nuevoAncho = 30;
            int nuevoAlto = 30;
            Image imagen = iconoVer.getImage();
            Image nuevaImagen = imagen.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
            ImageIcon iconoRedimensionado = new ImageIcon(nuevaImagen);

            // Crear un JPanel que contendrá el icono y el texto
            JPanel panel = new JPanel(new BorderLayout());

            // Añadir el icono al JPanel
            panel.add(new JLabel(iconoRedimensionado), BorderLayout.WEST);

            // Crear un JLabel para el texto "VER"
            JLabel label = new JLabel("  VER LOS DATOS DE LA ACTIVIDAD");

            // Establecer el color de fondo y el color del texto
            label.setBackground(darkColor);
            label.setForeground(Color.white);

            // Alinear el texto al centro vertical
            label.setVerticalAlignment(JLabel.CENTER);

            // Añadir el JLabel al JPanel
            panel.add(label, BorderLayout.CENTER);

            // Configurar el JPanel
            panel.setBackground(darkColor);

            // Crear el botón con el JPanel como su contenido
            JButton verButton = new JButton();
            verButton.add(panel);
            verButton.setBackground(darkColor);

            // Configurar propiedades del botón
            verButton.setBorderPainted(false);
            verButton.setFocusPainted(false);

            verButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int actividadID = actividad.getId();
                    VerActividad verActividades = new VerActividad(actividadID);
                    verActividades.setVisible(true);
                    dispose();
                }
            });

            actividadPanel.add(nombreLabel);
            actividadPanel.add(horaInicioLabel);
            actividadPanel.add(verButton);

            actividadesPanel.add(actividadPanel);
            actividadesPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        }

        actividadesScroll = new JScrollPane(actividadesPanel);
        actividadesScroll.setBounds(410, 100, 400, 366);
        actividadesScroll.setBackground(darkColor);
        this.add(actividadesScroll);
        actividadesScroll.getVerticalScrollBar().setValue(actividadesScroll.getVerticalScrollBar().getValue() + 1);

        this.revalidate();
        this.repaint();
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
            String dayValue = String.valueOf(i); // Convierte el valor de i a una cadena
            calendarModel.setValueAt(dayValue, row, column);

            // Marcar el día actual con un color específico
            if (i == realDay && month == realMonth && year == realYear) {
                String dayWithDot = "<html><font color='red'>" + i + "</font></html>";
                calendarTable.setValueAt(dayWithDot, row, column);
            } else {
                boolean hasActivities = hasActivitiesOnDay(i, month, year);
                if (hasActivities) {
                    // Marcar los días con actividades con un color específico
                    String dayWithDot = "<html><font color='yellow'>" + i + " ❖</font></html>";
                    calendarTable.setValueAt(dayWithDot, row, column);
                } else {
                    // No agrega el punto, solo el número del día
                    calendarTable.setValueAt(dayValue, row, column);
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
        actividadesPanel.setBackground(darkColor);

        // Agrega las actividades al panel
        for (Actividad actividad : actividadesDelDia) {
            actividadesPanel.setBackground(darkColor);

            JLabel nombreLabel = new JLabel(" " + actividad.getNombre());
            nombreLabel.setFont(font);
            nombreLabel.setForeground(Color.white);

            Date horaInicio = actividad.getInicio();
            Date horaFin = actividad.getFin();

            SimpleDateFormat formato = new SimpleDateFormat("hh:mm a");
            String horaFormateadaInicio = formato.format(horaInicio);
            String horaFormateadaFin = formato.format(horaFin);

            // Crea un panel para contener la información de la actividad
            JPanel actividadPanel = new JPanel();
            actividadPanel.setLayout(new GridLayout(3, 0)); // Utiliza GridLayout con una fila y columnas flexibles
            actividadPanel.setBackground(primaryColor);

            JLabel horaInicioLabel = new JLabel(" " + horaFormateadaInicio + " - " + horaFormateadaFin);
            horaInicioLabel.setFont(font2);
            horaInicioLabel.setForeground(Color.white);
            // Crea un ImageIcon con la ruta de la imagen
            ImageIcon iconoVer = new ImageIcon("img/verCarta.png");

            // Define el nuevo tamaño que deseas para el icono
            int nuevoAncho = 30; // Ancho en píxeles
            int nuevoAlto = 30; // Alto en píxeles

            Image imagen = iconoVer.getImage();
            Image nuevaImagen = imagen.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
            ImageIcon iconoRedimensionado = new ImageIcon(nuevaImagen);

            // Crear un JPanel que contendrá el icono y el texto
            JPanel panel = new JPanel(new BorderLayout());

            // Añadir el icono al JPanel
            panel.add(new JLabel(iconoRedimensionado), BorderLayout.WEST);

            // Crear un JLabel para el texto "VER"
            JLabel label = new JLabel("  VER LOS DATOS DE LA ACTIVIDAD");

            // Establecer el color de fondo y el color del texto
            label.setBackground(darkColor);
            label.setForeground(Color.white);

            // Alinear el texto al centro vertical
            label.setVerticalAlignment(JLabel.CENTER);

            // Añadir el JLabel al JPanel
            panel.add(label, BorderLayout.CENTER);

            // Configurar el JPanel
            panel.setBackground(darkColor);

            // Crear el botón con el JPanel como su contenido
            JButton verButton = new JButton();
            verButton.add(panel);
            verButton.setBackground(darkColor);

            // Configurar propiedades del botón
            verButton.setBorderPainted(false);
            verButton.setFocusPainted(false);

            verButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
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
        actividadesScroll.setBounds(410, 100, 400, 366); // Coloca el panel debajo del calendario

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

