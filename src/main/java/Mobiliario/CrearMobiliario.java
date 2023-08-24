package Mobiliario;

import Objetos.Cliente;
import Objetos.Conexion;
import Objetos.Empleado;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CrearMobiliario extends JFrame {
    private JTextField campoNombreMobiliario, campoCantidad, campoPrecioU;
    private JRadioButton radioButtonSiNecesita, radioButtonNoNecesita;
    private JButton botonGuardar, botonCancelar, botonLimpiar;
    private JPanel panel1, panel2, panel3, panel5, panel6, panel7;
    private JLabel lbl0, lbl2, lbl3;
    private JComboBox comboBoxCliente, comboBoxEmpleado;
    private String imagePath = "";
    private JPanel panelImg;
    private JLabel imagenLabel;
    private JButton botonCargarImagen;
    private JTextArea txtaDescripcion;
    private JComboBox comboTipoEvento;
    private JTextField lbl_total;
    private JXDatePicker jdatechosser;
    private CrearMobiliario actual = this;
    private Conexion sql;

    public CrearMobiliario() {
        super("");
        setSize(800, 490);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        sql = new Conexion();

        // Establecer ancho y alto deseados para el panelImg
        int panelImgWidth = 200;
        int panelImgHeight = 200;

        // Crear una instancia de Dimension con las dimensiones deseadas
        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);

        // Establecer las dimensiones en el panelImg
        panelImg.setPreferredSize(panelImgSize);
        panelImg.setMaximumSize(panelImgSize);
        panelImg.setMinimumSize(panelImgSize);
        panelImg.setSize(panelImgSize);

        // Configurar el layout del panelImg como GridBagLayout
        panelImg.setLayout(new GridBagLayout());

        // Configurar restricciones de diseño para la etiqueta de imagen
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        imagenLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panelImg.add(imagenLabel, gbc);


        jdatechosser.getEditor().setEditable(false);

        // Color de fondo del panel1
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel2.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panel5.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));
        radioButtonSiNecesita.setBackground(Color.decode("#F5F5F5"));
        radioButtonNoNecesita.setBackground(Color.decode("#F5F5F5"));
        radioButtonSiNecesita.setFocusPainted(false);
        radioButtonNoNecesita.setFocusPainted(false);

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

        Color darkColorPink = new Color(233, 30, 99);
        Color darkColorRed = new Color(244, 67, 54);
        Color darkColorBlue = new Color(33, 150, 243);

        // Crea un margen de 10 píxeles desde el borde inferior
        EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);

        // Color de texto de los botones
        botonCancelar.setForeground(Color.WHITE);
        botonGuardar.setForeground(Color.WHITE);
        botonLimpiar.setForeground(Color.WHITE);
        botonCargarImagen.setForeground(Color.WHITE);

        // Color de fondo de los botones
        botonCancelar.setBackground(darkColorBlue);
        botonGuardar.setBackground(darkColorAqua);
        botonLimpiar.setBackground(darkColorRosado);
        botonCargarImagen.setBackground(darkColorRed);

        botonCancelar.setFocusPainted(false);
        botonGuardar.setFocusPainted(false);
        botonLimpiar.setFocusPainted(false);
        botonCargarImagen.setFocusPainted(false);

        // Aplica el margen al botón
        botonGuardar.setBorder(margin);
        botonCancelar.setBorder(margin);
        botonLimpiar.setBorder(margin);

        lbl0.setForeground(textColor);
        lbl2.setForeground(textColor);
        lbl3.setForeground(textColor);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginTitulo = new EmptyBorder(15, 0, 15, 0);
        lbl0.setBorder(marginTitulo);

        // Crear una fuente con un tamaño de 18 puntos
        Font fontTitulo = new Font(lbl0.getFont().getName(), lbl0.getFont().getStyle(), 18);
        lbl0.setFont(fontTitulo);

        ButtonGroup buttonGroup2 = new ButtonGroup();
        buttonGroup2.add(radioButtonNoNecesita);
        buttonGroup2.add(radioButtonSiNecesita);

        campoPrecioU.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {

                double total = Double.parseDouble(campoCantidad.getText().isEmpty()?"0":campoCantidad.getText()) * Double.parseDouble(campoPrecioU.getText().isEmpty()?"0":campoPrecioU.getText());
                lbl_total.setText(String.format("%.2f",total));
            }

            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoPrecioU.getText();

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
                        e.consume();
                        return;
                    }
                }

            }
        });

        campoNombreMobiliario.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campoNombreMobiliario.getText();
                int length = text.length();

                if (length >= 30) {
                    e.consume();  // Ignorar la tecla si se alcanza la longitud máxima
                    return;
                }

                char c = e.getKeyChar();
                if (Character.isISOControl(c)) {
                    return;  // Permitir teclas de control como retroceso y eliminar
                }

                // Convertir la primera letra a mayúscula
                if (length == 0) {
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se intenta ingresar un carácter no permitido como primer carácter
                        return;
                    }
                    campoNombreMobiliario.setText(String.valueOf(Character.toUpperCase(c)));
                    e.consume();  // Ignorar el evento de tecla original para evitar que se agregue la letra dos veces
                    return;
                }

                // Verificar si se ingresó un espacio
                if (c == ' ') {
                    // Permitir solo un espacio entre palabras
                    if (length > 0 && text.charAt(length - 1) != ' ') {
                        return;
                    } else {
                        e.consume(); // Ignorar el evento si se intenta agregar más de un espacio consecutivo
                    }
                } else {
                    // Permitir solo letras
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se ingresa un carácter no permitido
                    }
                }
            }
        });

        txtaDescripcion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = txtaDescripcion.getText();
                int length = text.length();

                if (length >=255) {
                    e.consume();  // Ignorar la tecla si se alcanza la longitud máxima
                    return;
                }

                char c = e.getKeyChar();
                if (Character.isISOControl(c)) {
                    return;  // Permitir teclas de control como retroceso y eliminar
                }

                // Convertir la primera letra a mayúscula
                if (length == 0) {
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se intenta ingresar un carácter no permitido como primer carácter
                        return;
                    }
                    txtaDescripcion.setText(String.valueOf(Character.toUpperCase(c)));
                    e.consume();  // Ignorar el evento de tecla original para evitar que se agregue la letra dos veces
                    return;
                }

                // Verificar si se ingresó un espacio
                if (c == ' ') {
                    // Permitir solo un espacio entre palabras
                    if (length > 0 && text.charAt(length - 1) != ' ') {
                        return;
                    } else {
                        e.consume(); // Ignorar el evento si se intenta agregar más de un espacio consecutivo
                    }
                } else {
                    // Permitir solo letras
                    if (!Character.isLetter(c)) {
                        e.consume(); // Ignorar el evento si se ingresa un carácter no permitido
                    }
                }
            }
        });
        campoCantidad.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {

                double total = Double.parseDouble(campoCantidad.getText().isEmpty()?"0":campoCantidad.getText()) * Double.parseDouble(campoPrecioU.getText().isEmpty()?"0":campoPrecioU.getText());
                lbl_total.setText(String.format("%.2f",total));
            }
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = campoCantidad.getText();

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
                        e.consume();
                        return;
                    }
                }
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaMobiliario listaGlobos = new ListaMobiliario();
                listaGlobos.setVisible(true);
                actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                if (imagePath.isEmpty()) {
                    validacion++;
                    mensaje += "Imagen\n";
                }
                String nombreMobiliario = campoNombreMobiliario.getText().trim();
                if (nombreMobiliario.trim().isEmpty()) {
                    validacion++;
                    mensaje += "El nombre de mobiliario\n";
                }
                String tipoEvento  = comboTipoEvento.getSelectedItem().toString().trim();
                if (tipoEvento.equals("Seleccione el tipo de Evento")) {
                    validacion++;
                    mensaje += "El tipo de evento\n";
                }

                String id_cliente =comboBoxCliente.getSelectedItem().toString().trim();
                if (id_cliente.equals("Seleccione el Cliente")) {
                    validacion++;
                    mensaje += "El Cliente\n";
                }

                String id_empleado = comboBoxEmpleado.getSelectedItem().toString().trim();
                if (id_empleado.equals("Seleccione el Empleado")) {
                    validacion++;
                    mensaje += "El Empleado\n";
                }

                if (campoPrecioU.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "El precio unitario\n";
                }

                String descripcion = txtaDescripcion.getText().trim();
                if (descripcion.trim().isEmpty()) {
                    validacion++;
                    mensaje += "La descripción\n";
                }

                String cantidad = campoCantidad.getText().trim();
                if (cantidad.trim().isEmpty()) {
                    validacion++;
                    mensaje += "La cantidad\n";
                }

                String fechaEntrega = jdatechosser.getEditor().getText().trim();
                if (fechaEntrega.trim().isEmpty()) {
                    validacion++;
                    mensaje += "La fecha entrega\n";
                }

                String disponibilidad = radioButtonSiNecesita.getText().trim();
                if (cantidad.trim().isEmpty()) {
                    validacion++;
                    mensaje += "disponibilidad\n";
                }





                if (validacion > 0) {
                    JOptionPane.showMessageDialog(null, mensaje, "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String precioText = campoPrecioU.getText().trim();
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

                String cantidadText = campoCantidad.getText().trim();
                if (cantidadText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Faltó ingresar la cantidad por paquetes de globos.", "Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!cantidadText.matches("\\d{1,4}")) {
                        JOptionPane.showMessageDialog(null, "Cantidad por paquete de globos inválida. Debe contener solo números entre 1 y 9999.", "Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        int cantidad2 = Integer.parseInt(cantidadText);
                        if (cantidad2 < 1 || cantidad2 > 9999) {
                            JOptionPane.showMessageDialog(null, "Cantidad por paquete de globos fuera del rango válido (1 - 9999).", "Validación", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }

                int respuesta = JOptionPane.showOptionDialog(
                        null,
                        "¿Desea guardar la información del mobiliario?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{"Sí", "No"},
                        "No"
                );

                if (respuesta == JOptionPane.YES_OPTION) {
                    guardarMobiliario();

                }
            }
        });

        botonCargarImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("FileChooser.openButtonText", "Cargar");
                UIManager.put("FileChooser.cancelButtonText", "Cancelar");
                UIManager.put("FileChooser.lookInLabelText", "Ver en");
                UIManager.put("FileChooser.fileNameLabelText", "Nombre del archivo");
                UIManager.put("FileChooser.filesOfTypeLabelText", "Archivos del tipo");
                UIManager.put("FileChooser.upFolderToolTipText", "Subir un nivel");
                UIManager.put("FileChooser.homeFolderToolTipText", "Escritorio");
                UIManager.put("FileChooser.newFolderToolTipText", "Crear nueva carpeta");
                UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
                UIManager.put("FileChooser.newFolderButtonText", "Crear nueva carpeta");
                UIManager.put("FileChooser.renameFileButtonText", "Renombrar archivo");
                UIManager.put("FileChooser.deleteFileButtonText", "Eliminar archivo");
                UIManager.put("FileChooser.filterLabelText", "Tipo de archivo");
                UIManager.put("FileChooser.detailsViewButtonToolTipText", "Detalles");
                UIManager.put("FileChooser.fileSizeHeaderText", "Tamaño");
                UIManager.put("FileChooser.fileDateHeaderText", "Fecha de modificación");

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar imagen"); // Cambiar título del diálogo

                fileChooser.setFileFilter(new FileNameExtensionFilter("Imágenes", "jpg", "jpeg", "png", "bmp", "gif"));

                int seleccion = fileChooser.showOpenDialog(actual);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    imagePath = file.getAbsolutePath();
                    ImageIcon originalIcon = new ImageIcon(imagePath);

                    // Obtener las dimensiones originales de la imagen
                    int originalWidth = originalIcon.getIconWidth();
                    int originalHeight = originalIcon.getIconHeight();

                    // Obtener las dimensiones del JPanel
                    int panelImgWidth = panelImg.getWidth();
                    int panelImgHeight = panelImg.getHeight();

                    // Calcular la escala para ajustar la imagen al JPanel
                    double scale = Math.min((double) panelImgWidth / originalWidth, (double) panelImgHeight / originalHeight);

                    // Calcular las nuevas dimensiones de la imagen redimensionada
                    int scaledWidth = (int) (originalWidth * scale);
                    int scaledHeight = (int) (originalHeight * scale);

                    // Redimensionar la imagen manteniendo su proporción
                    Image resizedImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

                    // Crear un nuevo ImageIcon a partir de la imagen redimensionada
                    ImageIcon scaledIcon = new ImageIcon(resizedImage);

                    imagenLabel.setIcon(scaledIcon);
                }
            }
        });

        botonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton btnYes = new JButton("Sí");
                JButton btnNo = new JButton("No");

                // Personaliza los botones aquí
                btnYes.setBackground(darkColorAqua);
                btnNo.setBackground(darkColorPink);

                // Personaliza los fondos de los botones aquí
                btnYes.setForeground(Color.WHITE);
                btnNo.setForeground(Color.WHITE);

                // Elimina el foco
                btnYes.setFocusPainted(false);
                btnNo.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Estás seguro de que deseas limpiar los datos?",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        new Object[]{}, // no options
                        null
                );

                // Crea un JDialog
                JDialog dialog = optionPane.createDialog("Limpiar");

                btnYes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        limpiarCampos();
                        dialog.dispose();
                    }
                });

                btnNo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialog.dispose();
                    }
                });
                optionPane.setOptions(new Object[]{btnYes, btnNo});
                dialog.setVisible(true);
            }
        });
        cargarCliente();
        cargarEmpleado();

    }

    private void guardarMobiliario() {

        double precio =Double.parseDouble(campoPrecioU.getText().trim());
        String nombreMobiliario = campoNombreMobiliario.getText().trim();
        String tipoEvento  = comboTipoEvento.getSelectedItem().toString().trim();
        String id_cliente =comboBoxCliente.getSelectedItem().toString().trim().split("-")[0];
        String id_empleado = comboBoxEmpleado.getSelectedItem().toString().trim().split("-")[0];
        String descripcion = txtaDescripcion.getText().trim();
        String cantidad = campoCantidad.getText().trim();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String fechaEntrega = format.format(jdatechosser.getDate());
        String image = imagePath;
        int disponibilidad = radioButtonSiNecesita.isSelected() ? 1 : 0 ;



        try (Connection connection = sql.conectamysql();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO mobiliario (nombreMobiliario,tipoEvento,id_cliente,id_empleado,descripcion,cantidad,precioUnitario,fechaEntrega,image,disponibilidad) VALUES (?,?,?,?,?,?,?,?,?,?)")) {

            // Generar el nombre de la imagen
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
            String fechaActual = dateFormat.format(new Date());
            String nombreImagen = "imagen " + fechaActual + " " + generarNumeroAleatorio(0, 9999);

            // Guardar la imagen en la carpeta
            String rutaImagen = nombreImagen + obtenerExtensionImagen(imagePath);
            File destino = new File("img/mobiliario/" + rutaImagen);

            try {
                // Copiar el archivo seleccionado a la ubicación destino
                Path origenPath = Path.of(imagePath);
                Files.copy(origenPath, destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            preparedStatement.setString(1, nombreMobiliario);
            preparedStatement.setString(2, tipoEvento);
            preparedStatement.setInt(3, Integer.parseInt(id_cliente));
            preparedStatement.setInt(4, Integer.parseInt(id_empleado));
            preparedStatement.setString(5, descripcion);
            preparedStatement.setInt(6, Integer.parseInt(cantidad));
            preparedStatement.setDouble(7, precio);
            preparedStatement.setString(8, fechaEntrega);
            preparedStatement.setString(9, image);
            preparedStatement.setInt(10,disponibilidad);
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Mobiliario guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            ListaMobiliario listaMobiliario = new ListaMobiliario();
            listaMobiliario.setVisible(true);
            actual.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el mobiliario", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCliente(){
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM eventos.clientes"
             )
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            comboBoxCliente.addItem("Seleccione el Cliente");
            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setApellido(resultSet.getString("apellido"));

                comboBoxCliente.addItem(cliente.getId()+"-"+cliente.getNombre()+" "+cliente.getApellido());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }
    }

    private void cargarEmpleado(){
        sql = new Conexion();
        try (Connection mysql = sql.conectamysql();
             PreparedStatement preparedStatement = mysql.prepareStatement(
                     "SELECT * FROM Empleados"
             )
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            comboBoxEmpleado.addItem("Seleccione el Empleado");
            while (resultSet.next()) {
                Empleado empleado1 = new Empleado();
                empleado1.setId(resultSet.getInt("id"));
                empleado1.setIdentidad(resultSet.getString("Identidad"));
                empleado1.setNombres(resultSet.getString("Nombres"));
                empleado1.setApellidos(resultSet.getString("Apellidos"));
                empleado1.setTelefono(resultSet.getString("Telefono"));

                comboBoxEmpleado.addItem(empleado1.getId()+"-"+empleado1.getNombres()+" "+empleado1.getApellidos());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexión con la base de datos");
        }
    }

    private String obtenerExtensionImagen(String imagePath) {
        int extensionIndex = imagePath.lastIndexOf(".");
        if (extensionIndex != -1) {
            return imagePath.substring(extensionIndex);
        }
        return "";
    }

    private void limpiarCampos() {
       campoPrecioU.setText("0");
       campoNombreMobiliario.setText("");
        comboTipoEvento.setSelectedIndex(0);
        comboBoxCliente.setSelectedIndex(0);
       comboBoxEmpleado.setSelectedIndex(0);
        txtaDescripcion.setText("");
        campoCantidad.setText("0");
        lbl_total.setText("0");
        jdatechosser.getEditor().setText("");
    }

    private String generarNumeroAleatorio(int min, int max) {
        Random random = new Random();
        int numeroAleatorio = random.nextInt(max - min + 1) + min;
        return String.format("%04d", numeroAleatorio);
    }

    public static void main(String[] args) {
        CrearMobiliario crearGlobo = new CrearMobiliario();
        crearGlobo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearGlobo.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}