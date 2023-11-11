package Roles;

import Login.SesionUsuario;
import Objetos.Conexion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrearRol extends JFrame {
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    private JTextField nombreField;
    private JCheckBox clienteCheckBox, empleadoCheckBox, floristeriaCheckBox, arregloCheckBox, usuarioCheckBox, materialCheckBox, proveedorCheckBox, compraCheckBox, tarjetaCheckBox, manualidadCheckBox, globoCheckBox, desayunoCheckBox, ventaCheckBox, mobiliarioCheckBox, pedidoCheckBox, promocionCheckBox, eventoCheckBox, actividadCheckBox, alquilerCheckBox, rolCheckBox;
    private Conexion sql;
    private JFrame mainFrame;
    public CrearRol() {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);

        sql = new Conexion();
        setLayout(new BorderLayout(0, 20)); // Espacio entre componentes
        setBackground(new Color(238, 238, 238)); // Fondo claro estilo Material

        // Panel central con el contenido
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(245, 245, 245)); // Fondo más claro para el contenido
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen alrededor del panel central

        // Título
        JLabel titleLabel = new JLabel("INGRESAR LOS DATOS DEL ROL", JLabel.CENTER);
        titleLabel.setFont(new Font("Century Gothic", Font.BOLD, 22)); // Fuente grande para el título
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Alineación al centro
        centerPanel.add(titleLabel);

        centerPanel.add(Box.createVerticalStrut(10)); // Espacio tras el título

        // Campo para el nombre del rol
        nombreField = new JTextField(20); // Correcto, asigna a la variable de instancia
        nombreField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.GRAY));
        nombreField.setOpaque(false);
        nombreField.setFont(new Font("Century Gothic", Font.BOLD, 14));
        centerPanel.add(createLabeledField("Nombre del Rol:", nombreField));

        clienteCheckBox = createCheckbox("Clientes");
        empleadoCheckBox = createCheckbox("Empleados");
        floristeriaCheckBox = createCheckbox("Floristerías");
        arregloCheckBox = createCheckbox("Arreglos");
        usuarioCheckBox = createCheckbox("Usuarios");
        materialCheckBox = createCheckbox("Materiales");
        proveedorCheckBox = createCheckbox("Proveedores");
        compraCheckBox = createCheckbox("Compras");
        tarjetaCheckBox = createCheckbox("Tarjetas");
        manualidadCheckBox = createCheckbox("Manualidades");
        globoCheckBox = createCheckbox("Globos");
        desayunoCheckBox = createCheckbox("Desayunos");
        ventaCheckBox = createCheckbox("Ventas");
        mobiliarioCheckBox = createCheckbox("Mobiliarios");
        pedidoCheckBox = createCheckbox("Pedidos");
        promocionCheckBox = createCheckbox("Promociones");
        eventoCheckBox = createCheckbox("Eventos");
        actividadCheckBox = createCheckbox("Actividades");
        alquilerCheckBox = createCheckbox("Alquileres");
        rolCheckBox = createCheckbox("Roles");

        // Panel para checkboxes organizados en tres columnas
        JPanel checkboxPanel = new JPanel(new GridLayout(0, 3, 10, 10)); // Filas dinámicas, 3 columnas, espacio entre elementos
        checkboxPanel.setBackground(new Color(245, 245, 245)); // Fondo claro

        checkboxPanel.add(clienteCheckBox);
        checkboxPanel.add(empleadoCheckBox);
        checkboxPanel.add(floristeriaCheckBox);
        checkboxPanel.add(arregloCheckBox);
        checkboxPanel.add(usuarioCheckBox);
        checkboxPanel.add(materialCheckBox);
        checkboxPanel.add(proveedorCheckBox);
        checkboxPanel.add(compraCheckBox);
        checkboxPanel.add(tarjetaCheckBox);
        checkboxPanel.add(manualidadCheckBox);
        checkboxPanel.add(globoCheckBox);
        checkboxPanel.add(desayunoCheckBox);
        checkboxPanel.add(ventaCheckBox);
        checkboxPanel.add(mobiliarioCheckBox);
        checkboxPanel.add(pedidoCheckBox);
        checkboxPanel.add(promocionCheckBox);
        checkboxPanel.add(eventoCheckBox);
        checkboxPanel.add(actividadCheckBox);
        checkboxPanel.add(alquilerCheckBox);
        checkboxPanel.add(rolCheckBox);

        centerPanel.add(checkboxPanel);

        add(centerPanel, BorderLayout.CENTER);

        // Panel para botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(238, 238, 238)); // Fondo al estilo Material
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Botón Guardar
        JButton saveButton = new JButton("Guardar");
        styleMaterialButton(saveButton, "#2c3e50", "#34495e");
        buttonPanel.add(saveButton);

        // Botón Cancelar
        JButton cancelButton = new JButton("Cancelar");
        styleMaterialButton(cancelButton, "#c0392b", "#e74c3c");
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        nombreField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = nombreField.getText();
                int length = text.length();
                int caretPosition = nombreField.getCaretPosition();

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

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                ListaRoles listaRoles = new ListaRoles(idUsuarioActual);
                listaRoles.setVisible(true);
                dispose();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                // Contador para el número de checkboxes seleccionados
                int contadorSeleccionados = 0;
                JCheckBox[] checkBoxes = {clienteCheckBox, empleadoCheckBox, floristeriaCheckBox, arregloCheckBox, usuarioCheckBox, materialCheckBox, proveedorCheckBox, compraCheckBox, tarjetaCheckBox, manualidadCheckBox, globoCheckBox, desayunoCheckBox, ventaCheckBox, mobiliarioCheckBox, pedidoCheckBox, promocionCheckBox, eventoCheckBox, actividadCheckBox, alquilerCheckBox};

                for (JCheckBox checkBox : checkBoxes) {
                    if (checkBox.isSelected()) {
                        contadorSeleccionados++;
                    }
                }

                if (nombreField.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Nombre del rol\n";
                }

                // Verifica si no se seleccionó ningún checkbox o si se seleccionaron todos
                if (contadorSeleccionados == 0) {
                    validacion++;
                    mensaje += "Permisos\n";
                }

                if (validacion > 0) {
                    mostrarDialogoPersonalizadoAtencion(mensaje, Color.decode("#F57F17"));
                    return;
                }

                // Verifica si no se seleccionó ningún checkbox o si se seleccionaron todos
                if (contadorSeleccionados == 0) {
                    mostrarDialogoPersonalizadoAtencion("Debe seleccionar al menos un permiso.", Color.decode("#F57F17"));
                    return;
                } else if (contadorSeleccionados == checkBoxes.length) {
                    mostrarDialogoPersonalizadoAtencion("No puede seleccionar todos los permisos:\nYa se asignaron al administrador.", Color.decode("#F57F17"));
                    return;
                }

                String nombre = nombreField.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 100) {
                        mostrarDialogoPersonalizadoAtencion("El nombre debe tener como máximo 100 caracteres.", Color.decode("#F57F17"));
                        return;
                    }

                    if (!nombre.matches("[a-zA-ZñÑ]{2,}(\\s[a-zA-ZñÑ]+\\s*)*")) {
                        mostrarDialogoPersonalizadoAtencion("El nombre debe tener mínimo 2 letras y máximo 1 espacio entre palabras.", Color.decode("#F57F17"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoAtencion("El campo de nombre no puede estar vacío.", Color.decode("#F57F17"));
                    return;
                }

                JButton btnSave = new JButton("Sí");
                JButton btnCancel = new JButton("No");

                // Personaliza los botones aquí
                btnSave.setBackground(darkColorBlue);
                btnCancel.setBackground(darkColorRed);

                // Personaliza los fondos de los botones aquí
                btnSave.setForeground(Color.WHITE);
                btnCancel.setForeground(Color.WHITE);

                // Elimina el foco
                btnSave.setFocusPainted(false);
                btnCancel.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Desea guardar la información rol de usuario?",
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
                        guardarRol();
                        dialog.dispose();
                        int idUsuarioActual = SesionUsuario.getInstance().getIdUsuario();
                        ListaRoles listaRoles = new ListaRoles(idUsuarioActual);
                        listaRoles.setVisible(true);
                        dispose();
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

    private JPanel createLabeledField(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(245, 245, 245)); // Fondo claro
        panel.add(new JLabel(label));
        panel.add(textField);
        return panel;
    }

    private JCheckBox createCheckbox(String label) {
        JCheckBox checkBox = new JCheckBox(label);
        checkBox.setBackground(new Color(245, 245, 245)); // Fondo claro
        checkBox.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return checkBox;
    }

    private void styleMaterialButton(JButton button, String color, String hoverColor) {
        button.setBackground(Color.decode(color));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode(hoverColor));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode(color));
            }
        });
    }

    private void guardarRol() {
        String nombre = nombreField.getText().trim();
        boolean cliente = clienteCheckBox.isSelected();
        boolean empleado = empleadoCheckBox.isSelected();
        boolean floristeria = floristeriaCheckBox.isSelected();
        boolean arreglo = arregloCheckBox.isSelected();
        boolean usuario = usuarioCheckBox.isSelected();
        boolean material = materialCheckBox.isSelected();
        boolean proveedor = proveedorCheckBox.isSelected();
        boolean compra = compraCheckBox.isSelected();
        boolean tarjeta = tarjetaCheckBox.isSelected();
        boolean manualidad = manualidadCheckBox.isSelected();
        boolean globo = globoCheckBox.isSelected();
        boolean desayuno = desayunoCheckBox.isSelected();
        boolean venta = ventaCheckBox.isSelected();
        boolean mobiliario = mobiliarioCheckBox.isSelected();
        boolean pedido = pedidoCheckBox.isSelected();
        boolean promocion = promocionCheckBox.isSelected();
        boolean evento = eventoCheckBox.isSelected();
        boolean actividad = actividadCheckBox.isSelected();
        boolean alquiler = alquilerCheckBox.isSelected();
        boolean rol = rolCheckBox.isSelected();

        try (Connection connection = sql.conectamysql();
             PreparedStatement checkStmt = connection.prepareStatement(
                     "SELECT COUNT(*) FROM roles WHERE nombre = ?")) {
                    checkStmt.setString(1, nombre);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next() && rs.getInt(1) > 0) {
                        mostrarDialogoPersonalizadoError("Un rol con este nombre ya existe.", Color.decode("#C62828"));
                    } else {

                        try (PreparedStatement preparedStatement = connection.prepareStatement(
                             "INSERT INTO roles (nombre, cliente, empleado, floristeria, arreglo, usuario, material, proveedor, compra, tarjeta, manualidad, globo, desayuno, venta, mobiliario, pedido, promocion, evento, actividad, alquiler, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setBoolean(2, cliente);
                    preparedStatement.setBoolean(3, empleado);
                    preparedStatement.setBoolean(4, floristeria);
                    preparedStatement.setBoolean(5, arreglo);
                    preparedStatement.setBoolean(6, usuario);
                    preparedStatement.setBoolean(7, material);
                    preparedStatement.setBoolean(8, proveedor);
                    preparedStatement.setBoolean(9, compra);
                    preparedStatement.setBoolean(10, tarjeta);
                    preparedStatement.setBoolean(11, manualidad);
                    preparedStatement.setBoolean(12, globo);
                    preparedStatement.setBoolean(13, desayuno);
                    preparedStatement.setBoolean(14, venta);
                    preparedStatement.setBoolean(15, mobiliario);
                    preparedStatement.setBoolean(16, pedido);
                    preparedStatement.setBoolean(17, promocion);
                    preparedStatement.setBoolean(18, evento);
                    preparedStatement.setBoolean(19, actividad);
                    preparedStatement.setBoolean(20, alquiler);
                    preparedStatement.setBoolean(21, rol);

                    preparedStatement.executeUpdate();
                    mostrarDialogoPersonalizadoExito("Rol guardado exitosamente.", Color.decode("#263238"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al guardar el rol.", Color.decode("#C62828"));
        }
    }

    public void mostrarDialogoPersonalizadoExito(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
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
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,   // Tipo de mensaje
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

    public void mostrarDialogoPersonalizadoAtencion(String mensaje, Color colorFondoBoton) {
        // Crea un botón personalizado
        JButton btnAceptar = new JButton("OK");
        btnAceptar.setBackground(colorFondoBoton); // Color de fondo del botón
        btnAceptar.setForeground(Color.WHITE);
        btnAceptar.setFocusPainted(false);

        // Crea un JOptionPane
        JOptionPane optionPane = new JOptionPane(
                mensaje,                           // Mensaje a mostrar
                JOptionPane.WARNING_MESSAGE,   // Tipo de mensaje
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
        CrearRol roles = new CrearRol();
        roles.setVisible(true);
    }
}
