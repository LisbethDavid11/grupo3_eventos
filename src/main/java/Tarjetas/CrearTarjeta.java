package Tarjetas;

import Objetos.Conexion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrearTarjeta extends JFrame {

    private JTextField campoPrecio;
    private JTextArea campoDescripcion;
    private JRadioButton radioButtonSi;
    private JRadioButton radioButtonNo;
    private JButton botonGuardar;
    private JButton botonCancelar;
    private JPanel panel;
    private JLabel lbl0;
    private JLabel lbl2;
    private JLabel lbl4;
    private JRadioButton cumpleañosRadioButton;
    private JRadioButton bodasRadioButton;
    private JRadioButton bautizoRadioButton;
    private JRadioButton a14DeFebreroRadioButton;
    private JRadioButton a15DeSeptRadioButton;
    private JButton botonCargarImagen;
    private JButton agregarMaterialButton;
    private JTable table1;
    private String imagePath = "";
    private CrearTarjeta actual = this;
    private Conexion sql;

    public CrearTarjeta() {
        super("");
        setSize(600, 590);
        setLocationRelativeTo(null);
        setContentPane(panel);

        campoDescripcion.setLineWrap(true);
        campoDescripcion.setWrapStyleWord(true);

        sql = new Conexion();


        // Color de fondo del panel
        panel.setBackground(Color.decode("#F5F5F5"));
        radioButtonSi.setBackground(Color.decode("#F5F5F5"));
        radioButtonNo.setBackground(Color.decode("#F5F5F5"));

        // Color de texto para los JTextField
        Color textColor = Color.decode("#212121");

        // Cargar los iconos en blanco
        ImageIcon cancelIcon = new ImageIcon("cancel_icon_white.png");
        ImageIcon saveIcon = new ImageIcon("save_icon_white.png");
        ImageIcon updateIcon = new ImageIcon("update_icon_white.png");

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

        // Crea un margen de 10 píxeles desde el borde inferior
        EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

        // Color de texto de los botones
        botonCancelar.setForeground(Color.WHITE);
        botonGuardar.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonCancelar.setBackground(darkColorCyan);
        botonGuardar.setBackground(darkColorAqua);

        botonCancelar.setFocusPainted(false);
        botonGuardar.setFocusPainted(false);

        // Aplica el margen al botón
        botonGuardar.setBorder(margin);
        botonCancelar.setBorder(margin);

        lbl0.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl4.setForeground(textColor);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

        // Inicializar JRadioButtons
        ButtonGroup buttonGroupOcasion = new ButtonGroup();
        buttonGroupOcasion.add(cumpleañosRadioButton);
        buttonGroupOcasion.add(bautizoRadioButton);
        buttonGroupOcasion.add(a14DeFebreroRadioButton);
        buttonGroupOcasion.add(a15DeSeptRadioButton);
        buttonGroupOcasion.add(bodasRadioButton);

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radioButtonNo);
        buttonGroup.add(radioButtonSi);

        // Color de texto para el JTextArea
        campoDescripcion.setForeground(textColor);
        campoDescripcion.setBackground(new Color(215, 215, 215));

        // No seleccionar ningún botón de radio por defecto
        buttonGroup.clearSelection();

        campoPrecio.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoPrecio.getText();

                // Permitir solo dígitos y el carácter de punto decimal
                if (!Character.isDigit(c) && c != '.') {
                    e.consume(); // Ignorar cualquier otro carácter
                    return;
                }

                // Verificar si se excede el límite de caracteres
                if (text.length() >= 5 && c != '.' && !text.contains(".")) {
                    e.consume(); // Ignorar el carácter si se excede el límite de dígitos y no es un punto decimal
                    return;
                }

                // Verificar si ya hay un punto decimal y se intenta ingresar otro
                if (text.contains(".") && c == '.') {
                    e.consume(); // Ignorar el carácter si ya hay un punto decimal
                    return;
                }

                // Verificar la cantidad de dígitos después del punto decimal
                if (text.contains(".")) {
                    int dotIndex = text.indexOf(".");
                    int decimalDigits = text.length() - dotIndex - 1;
                    if (decimalDigits >= 2) {
                        e.consume(); // Ignorar el carácter si se excede la cantidad de dígitos después del punto decimal
                        return;
                    }
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
                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea cancelar el registro de tarjeta?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    ListaTarjetas listaTarjeta = new ListaTarjetas();
                    listaTarjeta.setVisible(true);
                    actual.dispose();
                }
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";


                if (campoPrecio.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Precio\n";
                }

                if (!radioButtonSi.isSelected() && !radioButtonNo.isSelected()) {
                    validacion++;
                    mensaje += "Disponibilidad\n";
                }

                if (campoDescripcion.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Descripción\n";
                }

                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                String precioText = campoPrecio.getText().trim();
                if (precioText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltó ingresar el precio.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!precioText.matches("\\d{1,5}(\\.\\d{1,2})?")) {
                        JOptionPane.showMessageDialog(null, "Precio inválido. Debe tener el formato correcto (ejemplo: 1234 o 1234.56).", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        double precio = Double.parseDouble(precioText);
                        if (precio < 1.00 || precio > 99999.99) {
                            JOptionPane.showMessageDialog(null, "Precio fuera del rango válido (1.00 - 99999.99).", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }


                if (!campoDescripcion.getText().trim().isEmpty()) {
                    String texto = campoDescripcion.getText().trim();
                    int longitud = texto.length();

                    if (longitud < 2 || longitud > 200) {
                        JOptionPane.showMessageDialog(null, "La descripción debe tener entre 2 y 200 caracteres.", "Validación", JOptionPane.ERROR_MESSAGE);
                    }
                }
                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea guardar la información de la tarjeta?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    guardarMateriales();
                    ListaTarjetas listaTarjeta = new ListaTarjetas();
                    listaTarjeta.setVisible(true);
                    actual.dispose();
                }
            }
        });
    }

    private void guardarMateriales() {

        String precioText = campoPrecio.getText().replace("L ", "").replace(",", "").replace("_", "");
        double precio = Double.parseDouble(precioText);
        String descripcion = campoDescripcion.getText().trim();

        JRadioButton[] radioocasiones = {
                bautizoRadioButton,
                bodasRadioButton,
                cumpleañosRadioButton,
                a14DeFebreroRadioButton,
                a15DeSeptRadioButton
        };
        String[] ocasiones = {
                "Bautizo",
                "Boda",
                "Cumpleaños",
                "14 de Febrero",
                "15 de Septiembre"
        };
        String ocasion = "";
        for (int i = 0; i<radioocasiones.length;i++) {
            if (radioocasiones[i].isSelected()) {
                ocasion = ocasiones[i];
            }
        }


        String disponibilidad = radioButtonSi.isSelected() ? "Si" : "No";


            try (Connection connection = sql.conectamysql();
                 PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO tarjetas (ocasion, precio, disponible) VALUES (?, ?, ?)")) {
                preparedStatement.setString(1, ocasion);
                preparedStatement.setDouble(2, precio);
                //preparedStatement.setString(3, descripcion);
                preparedStatement.setString(3, disponibilidad);
                //preparedStatement.setInt(5, idProveedor);
                preparedStatement.executeUpdate();

                JOptionPane.showMessageDialog(null, "Tarjeta guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar la tarjeta", "Error", JOptionPane.ERROR_MESSAGE);
            }
    }
}