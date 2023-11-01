package Actividades;

import Desayunos.ListaDesayunos;
import Objetos.Conexion;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class CrearActividad extends JFrame {
    private JTextField campoNombre;
    private JLabel lbl0, lbl1, lbl2, lbl3, lbl4, lbl5, lbl6;
    private JTextArea campoDireccion, campoDescripcion;
    private JButton botonGuardar, botonCancelar;
    private JPanel panel1, panel2, panel3, panel4, jPanelDireccion, jPanelDescripcion, panelFecha, panelInicio, panelFin;
    private JSpinner spinnerHora1, spinnerMin1, spinnerHora2, spinnerMin2;
    private JComboBox comboBox1, comboBox2;
    private CrearActividad actual = this;
    private Conexion sql;
    private Connection mysql;
    Font fontTitulo = new Font("Century Gothic", Font.BOLD, 17);
    Font font = new Font("Century Gothic", Font.BOLD, 17);
    Font font2 = new Font("Century Gothic", Font.BOLD, 11);

    // Colores para el botón "Cyan"
    Color primaryColorCyan = new Color(0, 188, 212); // Cyan primario
    Color lightColorCyan = new Color(77, 208, 225); // Cyan claro
    Color darkColorCyan = new Color(0, 151, 167); // Cyan oscuro

    // Colores para el botón "Aqua"
    Color primaryColorAqua = new Color(0, 150, 136); // Aqua primario
    Color lightColorAqua = new Color(77, 182, 172); // Aqua claro
    Color darkColorAqua = new Color(0, 121, 107); // Aqua oscuro

    // Colores para el botón "Rosado"
    Color primaryColorRosado = new Color(233, 30, 99); // Rosado primario
    Color lightColorRosado = new Color(240, 98, 146); // Rosado claro
    Color darkColorRosado = new Color(194, 24, 91); // Rosado oscuro

    // Colores para el botón "Amber"
    Color primaryColorAmber = new Color(255, 193, 7); // Amber primario
    Color lightColorAmber = new Color(255, 213, 79); // Amber claro
    Color darkColorAmber = new Color(255, 160, 0); // Amber oscuro

    // Colores para el botón "Verde lima"
    Color primaryColorVerdeLima = new Color(205, 220, 57); // Verde lima primario
    Color lightColorVerdeLima = new Color(220, 237, 200); // Verde lima claro
    Color darkColorVerdeLima = new Color(139, 195, 74); // Verde lima oscuro

    Color darkColorPink = new Color(233, 30, 99);
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
    Color textColor = Color.decode("#212121");
    private JDatePickerImpl datePicker; // Declare the datePicker variable at the class level

    public CrearActividad() {
        super("");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();
        campoDireccion.setLineWrap(true);
        campoDireccion.setWrapStyleWord(true);

        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        // Horas
        SpinnerModel hourModel = new SpinnerNumberModel(1, 1, 12, 1);
        SpinnerModel hourModel1 = new SpinnerNumberModel(1, 1, 12, 1);
        SpinnerModel minuteModel = new SpinnerNumberModel(0, 0, 59, 1);
        SpinnerModel minuteModel1 = new SpinnerNumberModel(0, 0, 59, 1);

        spinnerHora1.setModel(hourModel);
        spinnerHora2.setModel(hourModel1);

        spinnerMin1.setModel(minuteModel);
        spinnerMin2.setModel(minuteModel1);

        // Calendario
        UtilDateModel dateModel = new UtilDateModel();
        Properties properties = new Properties();
        properties.put("text.today", "Hoy");
        properties.put("text.month", "Mes");
        properties.put("text.year", "Año");

        JDatePanelImpl datePanel = new JDatePanelImpl(dateModel, properties);
        datePicker = new JDatePickerImpl(datePanel, new CrearActividad.SimpleDateFormatter());  // Proporcionar un formateador
        datePicker.getJFormattedTextField().setForeground(Color.decode("#263238"));
        datePicker.getJFormattedTextField().setBackground(Color.decode("#D7D7D7"));
        datePicker.setBackground(Color.decode("#F5F5F5"));

        // Primero, obtén el botón del datePicker
        JButton button = (JButton) datePicker.getComponent(1);
        button.setForeground(Color.decode("#FFFFFF"));
        button.setBackground(Color.decode("#263238"));
        button.setFocusable(false);

        Calendar tomorrow = getTomorrow();
        dateModel.addChangeListener(e -> {
            handleDateChange(dateModel, tomorrow);
        });

        handleDateChange(dateModel, tomorrow);
        panelFecha.add(datePicker);
        panelFecha.setBackground(Color.decode("#F5F5F5"));

        // Establecer ancho y alto deseados para el paneldescripcion
        int panelDirWidth = 80;
        int panelDirHeight = 100;

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelDirSize = new Dimension(panelDirWidth, panelDirHeight);

        // Establecer las dimensiones en el jPanelDireccion
        jPanelDireccion.setPreferredSize(panelDirSize);
        jPanelDireccion.setMaximumSize(panelDirSize);
        jPanelDireccion.setMinimumSize(panelDirSize);
        jPanelDireccion.setSize(panelDirSize);

        // Establecer ancho y alto deseados para el paneldescripcion
        int panelDesWidth = 80;
        int panelDesHeight = 100;

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelDesSize = new Dimension(panelDesWidth, panelDesHeight);

        // Establecer las dimensiones en el jPanelDireccion
        jPanelDescripcion.setPreferredSize(panelDesSize);
        jPanelDescripcion.setMaximumSize(panelDesSize);
        jPanelDescripcion.setMinimumSize(panelDesSize);
        jPanelDescripcion.setSize(panelDesSize);

        // Color de fondo del panel
        jPanelDescripcion.setBackground(Color.decode("#F5F5F5"));
        jPanelDireccion.setBackground(Color.decode("#F5F5F5"));
        panelFecha.setBackground(Color.decode("#F5F5F5"));
        panelInicio.setBackground(Color.decode("#F5F5F5"));
        panelFin.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));

        campoDireccion.setForeground(textColor);
        campoDireccion.setBackground(new Color(215, 215, 215));

        campoDescripcion.setForeground(textColor);
        campoDescripcion.setBackground(new Color(215, 215, 215));

        botonCancelar.setBackground(darkColorBlue);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setBorder(margin);
        botonCancelar.setForeground(Color.WHITE);

        botonGuardar.setBackground(darkColorAqua);
        botonGuardar.setFocusPainted(false);
        botonGuardar.setBorder(margin);
        botonGuardar.setForeground(Color.WHITE);

        lbl0.setForeground(textColor);
        lbl1.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);
        lbl4.setForeground(textColor);
        lbl5.setForeground(textColor);
        lbl6.setForeground(textColor);

        lbl0.setBorder(margin);
        lbl0.setFont(fontTitulo);

        campoNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoNombre.getText();
                int length = text.length();
                int caretPosition = campoNombre.getCaretPosition();

                // Verificar si se está ingresando un espacio en blanco
                if (e.getKeyChar() == ' ') {
                    // Verificar si es el primer espacio en blanco o si hay varios espacios consecutivos
                    if (length == 0 || caretPosition == 0 || text.charAt(caretPosition - 1) == ' ') {
                        e.consume(); // Ignorar el espacio en blanco adicional
                    }
                } else {
                    // Verificar la longitud del texto después de eliminar espacios en blanco
                    String trimmedText = text.replaceAll("\\s+", " ");
                    int trimmedLength = trimmedText.length();

                    // Verificar si se está ingresando una letra
                    if (Character.isLetter(e.getKeyChar())) {
                        // Verificar si se excede el límite de caracteres
                        if (trimmedLength >= 100) {
                            e.consume(); // Ignorar la letra
                        } else {
                            // Convertir solo la primera letra a mayúscula
                            if (caretPosition == 0) {
                                e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                            }
                        }
                    } else {
                        e.consume(); // Ignorar cualquier otro tipo de carácter
                    }
                }
            }
        });

        campoDireccion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String texto = campoDireccion.getText();
                int caretPosition = campoDireccion.getCaretPosition();

                // Verificar la longitud del texto
                if (texto.length() >= 200) {
                    e.consume(); // Ignorar el evento si se alcanza el límite máximo de caracteres (200)
                    return;
                }

                // Verificar si se están ingresando más de un espacio en blanco seguido
                if (e.getKeyChar() == ' ' && texto.endsWith(" ")) {
                    e.consume(); // Ignorar el evento y no agregar el espacio en blanco adicional
                    return;
                }

                // Convertir la primera letra en mayúscula
                if (texto.length() == 0 && Character.isLowerCase(e.getKeyChar())) {
                    e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                }

                // Permitir números, letras, espacios, punto, coma y tildes
                if (!Character.isLetterOrDigit(e.getKeyChar()) && !Character.isSpaceChar(e.getKeyChar()) && e.getKeyChar() != '.' && e.getKeyChar() != ',' && !Character.isWhitespace(e.getKeyChar()) && !Character.isIdeographic(e.getKeyChar())) {
                    e.consume(); // Ignorar el evento si no es una letra, número, espacio, punto, coma o tilde
                }
            }
        });

        campoDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String texto = campoDescripcion.getText();
                int caretPosition = campoDescripcion.getCaretPosition();

                // Verificar la longitud del texto
                if (texto.length() >= 200) {
                    e.consume(); // Ignorar el evento si se alcanza el límite máximo de caracteres (200)
                    return;
                }

                // Verificar si se están ingresando más de un espacio en blanco seguido
                if (e.getKeyChar() == ' ' && texto.endsWith(" ")) {
                    e.consume(); // Ignorar el evento y no agregar el espacio en blanco adicional
                    return;
                }

                // Convertir la primera letra en mayúscula
                if (texto.length() == 0 && Character.isLowerCase(e.getKeyChar())) {
                    e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
                }

                // Permitir números, letras, espacios, punto, coma y tildes
                if (!Character.isLetterOrDigit(e.getKeyChar()) && !Character.isSpaceChar(e.getKeyChar()) && e.getKeyChar() != '.' && e.getKeyChar() != ',' && !Character.isWhitespace(e.getKeyChar()) && !Character.isIdeographic(e.getKeyChar())) {
                    e.consume(); // Ignorar el evento si no es una letra, número, espacio, punto, coma o tilde
                }
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    CalendarioDeActividades calendarioDeActividades = new CalendarioDeActividades();
                    calendarioDeActividades.setVisible(true);
                    actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                if (campoNombre.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Nombre de la actividad\n";
                }

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Descripción\n";
                }

                if (campoDireccion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Lugar de la actividad\n";
                }

                if (datePicker.getJFormattedTextField().getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Fecha\n";
                }

                // Obtener los valores de los selectores de hora y minuto para la hora inicial
                int horaInicial = (int) spinnerHora1.getValue();
                int minutoInicial = (int) spinnerMin1.getValue();

                // Obtener los valores de los selectores de hora y minuto para la hora final
                int horaFinal = (int) spinnerHora2.getValue();
                int minutoFinal = (int) spinnerMin2.getValue();

                // Obtener los valores de los JComboBox de AM/PM
                String amPmInicial = comboBox1.getSelectedItem().toString();
                String amPmFinal = comboBox2.getSelectedItem().toString();

                if (horaInicial == 0 && minutoInicial == 0) {
                    validacion++;
                    mensaje += "La hora inicial\n";
                }

                if (horaFinal == 0 && minutoFinal == 0) {
                    validacion++;
                    mensaje += "La hora final\n";
                }

                if (validacion > 0) {
                    mostrarDialogoPersonalizadoError(mensaje, Color.decode("#C62828"));
                    return;
                }

                if (horaFinal == horaInicial && minutoInicial == minutoFinal && amPmInicial == amPmFinal){
                    mostrarDialogoPersonalizadoError("La hora inicial no puede ser la misma que la hora final.", Color.decode("#C62828"));
                    return;
                }

                if (!campoDireccion.getText().trim().isEmpty()) {
                    String texto = campoDireccion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        mostrarDialogoPersonalizadoError("La dirección debe tener entre 2 y 200 caracteres.", Color.decode("#C62828"));
                    }
                }

                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        mostrarDialogoPersonalizadoError("La descripción debe tener entre 2 y 200 caracteres.", Color.decode("#C62828"));
                    }
                }

                JButton btnSave = new JButton("Sí");
                JButton btnCancel = new JButton("No");

                // Personaliza los botones aquí
                btnSave.setBackground(darkColorAqua);
                btnCancel.setBackground(darkColorPink);

                // Personaliza los fondos de los botones aquí
                btnSave.setForeground(Color.WHITE);
                btnCancel.setForeground(Color.WHITE);

                // Elimina el foco
                btnSave.setFocusPainted(false);
                btnCancel.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Desea guardar la información de la actividad?",
                        JOptionPane.QUESTION_MESSAGE,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        new Object[]{}, // no options
                        null
                );

                // Crea un JDialog
                JDialog dialog = optionPane.createDialog("Guardar");

                // Añade ActionListener a los botones
                btnSave.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Acciones para el botón Sí
                        guardarActividad();
                        dialog.dispose();
                        CalendarioDeActividades calendarioDeActividades = new CalendarioDeActividades();
                        calendarioDeActividades.setVisible(true);
                        actual.dispose();
                    }
                });

                btnCancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Acciones para el botón No
                        // No se hace nada, sólo se cierra el diálogo
                        dialog.dispose();
                    }
                });

                // Añade los botones al JOptionPane
                optionPane.setOptions(new Object[]{btnSave, btnCancel});

                // Muestra el diálogo
                dialog.setVisible(true);
            }
        });
    }

    public Calendar getTomorrow() {
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DAY_OF_MONTH, 1);
        tomorrow.set(Calendar.HOUR_OF_DAY, 0);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND, 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        return tomorrow;
    }

    public void handleDateChange(UtilDateModel dateModel, Calendar tomorrow) {
        java.util.Date selectedDate = dateModel.getValue();
        if (selectedDate != null && isDateOutOfRange(selectedDate, tomorrow)) {
            mostrarDialogoPersonalizadoError("La fecha seleccionada está fuera del rango permitido.", Color.decode("#C62828"));
            selectedDate = null; // Anula la selección en lugar de restablecerla a hoy
            dateModel.setValue(selectedDate);
        }
        setFormattedDate(selectedDate);
    }

    public boolean isDateOutOfRange(Date selectedDate, Calendar tomorrow) {
        Calendar selectedCal = Calendar.getInstance();
        selectedCal.setTime(selectedDate);

        // Calcula la fecha límite (30 días después de mañana)
        Calendar maxDate = (Calendar) tomorrow.clone();
        maxDate.add(Calendar.DAY_OF_MONTH, 30);

        return selectedCal.before(tomorrow) || selectedCal.after(maxDate);
    }

    public void setFormattedDate(java.util.Date selectedDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, d 'de' MMMM yyyy", new Locale("es", "ES")); // Formato en español
        String formattedDate = (selectedDate != null) ? dateFormat.format(selectedDate) : "";
        datePicker.getJFormattedTextField().setText(formattedDate);
    }

    public class SimpleDateFormatter extends JFormattedTextField.AbstractFormatter {

        private final String datePattern = "EEEE, d 'de' MMMM yyyy";
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                if (value instanceof java.util.Date) {
                    return dateFormatter.format((java.util.Date) value);
                } else if (value instanceof Calendar) {
                    return dateFormatter.format(((Calendar) value).getTime());
                }
            }
            return "";
        }
    }

    private void guardarActividad() {

        String nombre = campoNombre.getText().trim();
        String descripcion = campoDescripcion.getText().trim();
        String direccion = campoDireccion.getText().trim();

        Date fechaInicial = (Date) datePicker.getModel().getValue(); // Explicitly cast the value to Date
        String fecha = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicial);

        // Obtener los valores de los JComboBox de AM/PM
        String amPmInicial = comboBox1.getSelectedItem().toString();
        String amPmFinal = comboBox2.getSelectedItem().toString();

        // Obtener los valores de los selectores de hora y minuto para la hora inicial
        int horaInicial = (int) spinnerHora1.getValue();
        int minutoInicial = (int) spinnerMin1.getValue();

        // Convertir a formato de 24 horas
        if (amPmInicial.equals("PM")) {
            if (horaInicial < 12) {
                horaInicial += 12;
            }
        }

        // Obtener los valores de los selectores de hora y minuto para la hora final
        int horaFinal = (int) spinnerHora2.getValue();
        int minutoFinal = (int) spinnerMin2.getValue();

        // Convertir a formato de 24 horas
        if (amPmFinal.equals("PM")) {
            if (horaFinal < 12) {
                horaFinal += 12;
            }
        }

        // Crear objetos Time para la hora inicial y final
        Time inicio = Time.valueOf(String.format("%02d:%02d:00", horaInicial, minutoInicial));
        Time fin = Time.valueOf(String.format("%02d:%02d:00", horaFinal, minutoFinal));

        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO actividades (nombre, direccion, descripcion, fecha, inicio, fin) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, direccion);
            preparedStatement.setString(3, descripcion);
            preparedStatement.setString(4, fecha);
            preparedStatement.setTime(5, inicio);
            preparedStatement.setTime(6, fin);
            preparedStatement.executeUpdate();

            mostrarDialogoPersonalizadoExito("Actividad guardada exitosamente", Color.decode("#263238"));
            } catch (SQLException e) {
                e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al guardar la actividad", Color.decode("#C62828"));
        }
    }

    public void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("ACEPTAR");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.INFORMATION_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    public void mostrarDialogoPersonalizadoError(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("ACEPTAR");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.ERROR_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Validación");

        // Añade un ActionListener al botón
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose(); // Cierra el diálogo al hacer clic en "Aceptar"
            }
        });

        // Muestra el diálogo
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        CrearActividad crearActividad = new CrearActividad();
        crearActividad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearActividad.setVisible(true);
    }
}