package Roles;

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

public class EditarRol extends JFrame {
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);
    private JTextField nombreField;
    private JCheckBox clienteCheckBox, empleadoCheckBox, floristeriaCheckBox, arregloCheckBox, usuarioCheckBox, materialCheckBox, proveedorCheckBox, compraCheckBox, tarjetaCheckBox, manualidadCheckBox, globoCheckBox, desayunoCheckBox, ventaCheckBox, mobiliarioCheckBox, pedidoCheckBox, promocionCheckBox, eventoCheckBox, actividadCheckBox, alquilerCheckBox, rolCheckBox;
    private Conexion sql;
    private JFrame mainFrame;

    public int id;
    public EditarRol(int id) {
        super("");
        setSize(850, 505);
        setLocationRelativeTo(null);
        sql = new Conexion();
        this.id = id;

        setLayout(new BorderLayout(0, 20)); // Espacio entre componentes
        setBackground(new Color(238, 238, 238)); // Fondo claro estilo Material

        // Panel central con el contenido
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(245, 245, 245)); // Fondo más claro para el contenido
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margen alrededor del panel central

        // Título
        JLabel titleLabel = new JLabel("ACTUALIZAR LOS DATOS DEL ROL", JLabel.CENTER);
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

        // Botón Actualizar
        JButton saveButton = new JButton("Actualizar");
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
                ListaRoles listaRoles = new ListaRoles();
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
                JCheckBox[] checkBoxes = {clienteCheckBox, empleadoCheckBox, floristeriaCheckBox, arregloCheckBox, usuarioCheckBox, materialCheckBox, proveedorCheckBox, compraCheckBox, tarjetaCheckBox, manualidadCheckBox, globoCheckBox, desayunoCheckBox, ventaCheckBox, mobiliarioCheckBox, pedidoCheckBox, promocionCheckBox, eventoCheckBox, actividadCheckBox, alquilerCheckBox, rolCheckBox};

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

                // Para el administrador, permitir todos los permisos
                if (id == 1 && contadorSeleccionados < checkBoxes.length) {
                    mostrarDialogoPersonalizadoAtencion("El administrador debe seleccionar todos los permisos.", Color.decode("#F57F17"));
                    return;
                } else if (id != 1 && contadorSeleccionados == 20) {
                    mostrarDialogoPersonalizadoAtencion("No puede seleccionar todos los permisos; ya que fueron asignados al administrador.", Color.decode("#F57F17"));
                    return;
                } else if (id != 1 && contadorSeleccionados == 0) {
                    mostrarDialogoPersonalizadoAtencion("Debe seleccionar por lo menos un permiso.", Color.decode("#F57F17"));
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
                        "¿Desea actualizar la información del rol de usuario?",
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
                        actualizarRol();
                        dialog.dispose();
                        ListaRoles listaRoles = new ListaRoles();
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
        mostrarRol();
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

    private void actualizarRol() {
        String nombre = nombreField.getText().trim();
        try (Connection connection = sql.conectamysql();
             PreparedStatement checkStmt = connection.prepareStatement(
                     "SELECT COUNT(*) FROM roles WHERE id = ?")) {
            checkStmt.setInt(1, this.id);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE roles SET cliente=?, empleado=?, floristeria=?, arreglo=?, usuario=?, material=?, proveedor=?, compra=?, tarjeta=?, manualidad=?, globo=?, desayuno=?, venta=?, mobiliario=?, pedido=?, promocion=?, evento=?, actividad=?, alquiler=?, rol=?,nombre=? WHERE id = ?")) {
                    preparedStatement.setBoolean(1, clienteCheckBox.isSelected());
                    preparedStatement.setBoolean(2, empleadoCheckBox.isSelected());
                    preparedStatement.setBoolean(3, floristeriaCheckBox.isSelected());
                    preparedStatement.setBoolean(4,arregloCheckBox.isSelected());
                    preparedStatement.setBoolean(5, usuarioCheckBox.isSelected());
                    preparedStatement.setBoolean(6, materialCheckBox.isSelected());
                    preparedStatement.setBoolean(7, proveedorCheckBox.isSelected());
                    preparedStatement.setBoolean(8, compraCheckBox.isSelected());
                    preparedStatement.setBoolean(9, tarjetaCheckBox.isSelected());
                    preparedStatement.setBoolean(10, manualidadCheckBox.isSelected());
                    preparedStatement.setBoolean(11, globoCheckBox.isSelected());
                    preparedStatement.setBoolean(12, desayunoCheckBox.isSelected());
                    preparedStatement.setBoolean(13, ventaCheckBox.isSelected());
                    preparedStatement.setBoolean(14, mobiliarioCheckBox.isSelected());
                    preparedStatement.setBoolean(15,pedidoCheckBox.isSelected());
                    preparedStatement.setBoolean(16, promocionCheckBox.isSelected());
                    preparedStatement.setBoolean(17, eventoCheckBox.isSelected());
                    preparedStatement.setBoolean(18, actividadCheckBox.isSelected());
                    preparedStatement.setBoolean(19, alquilerCheckBox.isSelected());
                    preparedStatement.setBoolean(20, rolCheckBox.isSelected());
                    preparedStatement.setString(21, nombre);
                    preparedStatement.setInt(22, id);

                    preparedStatement.executeUpdate();
                    mostrarDialogoPersonalizadoExito("Rol actualizado exitosamente.", Color.decode("#263238"));
                }
            } else {
                mostrarDialogoPersonalizadoError("El rol con no existe.", Color.decode("#C62828"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al actualizar el rol.", Color.decode("#C62828"));
        }
    }

    private void mostrarRol() {
        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM roles WHERE id = ?")) {
            preparedStatement.setInt(1, this.id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                clienteCheckBox.setSelected(rs.getBoolean("cliente"));
                empleadoCheckBox.setSelected(rs.getBoolean("empleado"));
                floristeriaCheckBox.setSelected(rs.getBoolean("floristeria"));
                arregloCheckBox.setSelected(rs.getBoolean("arreglo"));
                usuarioCheckBox.setSelected(rs.getBoolean("usuario"));
                materialCheckBox.setSelected(rs.getBoolean("material"));
                proveedorCheckBox.setSelected(rs.getBoolean("proveedor"));
                compraCheckBox.setSelected(rs.getBoolean("compra"));
                tarjetaCheckBox.setSelected(rs.getBoolean("tarjeta"));
                manualidadCheckBox.setSelected(rs.getBoolean("manualidad"));
                globoCheckBox.setSelected(rs.getBoolean("globo"));
                desayunoCheckBox.setSelected(rs.getBoolean("desayuno"));
                ventaCheckBox.setSelected(rs.getBoolean("venta"));
                mobiliarioCheckBox.setSelected(rs.getBoolean("mobiliario"));
                pedidoCheckBox.setSelected(rs.getBoolean("pedido"));
                promocionCheckBox.setSelected(rs.getBoolean("promocion"));
                eventoCheckBox.setSelected(rs.getBoolean("evento"));
                actividadCheckBox.setSelected(rs.getBoolean("actividad"));
                alquilerCheckBox.setSelected(rs.getBoolean("alquiler"));
                rolCheckBox.setSelected(rs.getBoolean("rol"));
                nombreField.setText(rs.getString("nombre"));

                } else {
                mostrarDialogoPersonalizadoError("El rol con este nombre no existe.", Color.decode("#C62828"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al cargar los datos del rol.", Color.decode("#C62828"));
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
        EditarRol editarRol = new EditarRol(1);
        editarRol.setVisible(true);
    }
}
