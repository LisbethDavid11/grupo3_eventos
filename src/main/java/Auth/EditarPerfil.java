package Auth;

import Objetos.Conexion;
import SubMenu.SubMenu;
import org.mindrot.jbcrypt.BCrypt;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditarPerfil extends JFrame {
    private JTextField campoNombre, campoCorreo;
    private JButton botonGuardar, botonCancelar, botonCargarImagen, botonLimpiar;
    private JPanel panel, panelImg, panel1, panel3;
    private JLabel labelImagen;
    private JLabel label0, label1, label2, label3;
    private JPasswordField campoContrasenaAnterior;
    private JPasswordField campoContrasenaNueva;
    private JPasswordField campoContrasenaConfirmar;
    private JComboBox campoRol;
    private JButton botonMostrar;
    private String imagePath = "";
    private final EditarPerfil actual = this;
    private Conexion sql;
    private Connection mysql;
    // Establecer ancho y alto deseados para el panelImg
    private int panelImgWidth = 220;
    private int panelImgHeight = 220;
    private int id;
    Color darkColorRed = new Color(244, 67, 54);
    Color darkColorBlue = new Color(33, 150, 243);

    // Color de texto para los JTextField y JRadioButton
    Color textColor = Color.decode("#212121");
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

    private String nombreFile;
    private String urlDestino = "";


    // Crea un margen de 10 píxeles desde el borde inferior
    EmptyBorder margin = new EmptyBorder(15, 0, 15, 0);
    public EditarPerfil(int id) {
        super("");
        setSize(560, 640);
        setLocationRelativeTo(null);
        setContentPane(panel);
        sql = new Conexion();
        this.id = id;

        mostrar();
        defaultEchoChar = campoContrasenaAnterior.getEchoChar();
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
        labelImagen.setHorizontalAlignment(SwingConstants.CENTER);
        panelImg.add(labelImagen, gbc);

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

        campoCorreo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String correo = campoCorreo.getText();

                // Verificar si se excede la longitud máxima
                if (correo.length() >= 45) {
                    e.consume(); // Ignorar el evento si se alcanza el límite máximo de caracteres (45)
                    return;
                }

                // Verificar si se está ingresando un espacio en blanco
                if (Character.isWhitespace(e.getKeyChar())) {
                    e.consume(); // Ignorar el espacio en blanco
                    return;
                }

                // Verificar si el carácter no es una letra, guion, arroba o punto
                if (!Character.isLetter(e.getKeyChar()) && e.getKeyChar() != '-' && e.getKeyChar() != '@' && e.getKeyChar() != '.') {
                    e.consume(); // Ignorar el carácter si no es una letra, guion, arroba o punto
                    return;
                }
            }
        });

        panel.setBackground(Color.decode("#F5F5F5"));
        panelImg.setBackground(Color.decode("#F5F5F5"));
        panel1.setBackground(Color.decode("#F5F5F5"));
        panel3.setBackground(Color.decode("#F5F5F5"));

        botonLimpiar.setForeground(Color.WHITE);
        botonLimpiar.setBackground(darkColorRed);
        botonLimpiar.setFocusPainted(false);
        botonLimpiar.setBorder(margin);

        botonCancelar.setForeground(Color.WHITE);
        botonCancelar.setBackground(darkColorBlue);
        botonCancelar.setFocusPainted(false);
        botonCancelar.setBorder(margin);

        botonGuardar.setForeground(Color.WHITE);
        botonGuardar.setBackground(darkColorAqua);
        botonGuardar.setFocusPainted(false);
        botonGuardar.setBorder(margin);

        botonCargarImagen.setForeground(Color.WHITE);
        botonCargarImagen.setBackground(darkColorRosado);
        botonCargarImagen.setFocusPainted(false);
        botonCargarImagen.setBorder(margin);

        // Crea un margen de 15 píxeles desde el borde inferior
        EmptyBorder marginDNI = new EmptyBorder(10, 0, 10, 0);
        campoContrasenaAnterior.setBorder(marginDNI);
        campoContrasenaNueva.setBorder(marginDNI);
        campoContrasenaConfirmar.setBorder(marginDNI);

        label0.setForeground(textColor);
        label1.setForeground(textColor);
        label2.setForeground(textColor);
        label3.setForeground(textColor);
        label0.setFont(fontTitulo);

        EmptyBorder marginBotonMostrar = new EmptyBorder(10, 10, 10, 10);
        botonMostrar.setForeground(Color.white);
        botonMostrar.setBackground(darkColorBlue);
        botonMostrar.setBorder(marginBotonMostrar);
        botonMostrar.setFocusPainted(false);

        botonMostrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                mostrarContrasenas();
            }
        });

        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actual.dispose();
            }
        });

        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int validacion = 0;
                String mensaje = "Faltó ingresar: \n";

                String contrasenaAnterior = campoContrasenaAnterior.getText().trim();
                String contrasenaNueva = campoContrasenaNueva.getText().trim();
                String contrasenaConfirmar = campoContrasenaConfirmar.getText().trim();

                if (!contrasenaAnterior.isEmpty()) {
                    // Validar que la contraseña anterior sea correcta y tenga más de 8 caracteres
                    if (validarContrasena(id, contrasenaAnterior)) {
                        // La contraseña actual es correcta, proceder con la lógica de cambio de contraseña
                    } else {
                        mostrarDialogoPersonalizadoError("La contraseña actual no es correcta.", Color.decode("#C62828"));
                    }

                    if (contrasenaAnterior.length() <= 7) {
                        mostrarDialogoPersonalizadoError("La contraseña anterior tiene menos de 8 caracteres, verifica.", Color.decode("#C62828"));
                        return; // Detener la ejecución del método
                    }

                    // Validar las nuevas contraseñas
                    if (contrasenaNueva.length() <= 8 || contrasenaConfirmar.length() <= 8) {
                        mostrarDialogoPersonalizadoError("La nueva contraseña y su confirmación deben tener más de 8 caracteres.", Color.decode("#C62828"));
                        return; // Detener la ejecución del método
                    }

                    if (!contrasenaNueva.equals(contrasenaConfirmar)) {
                        mostrarDialogoPersonalizadoError("Las contraseñas nuevas no coinciden.", Color.decode("#C62828"));
                        return; // Detener la ejecución del método
                    }

                    if (contrasenaNueva.equals(contrasenaAnterior)) {
                        mostrarDialogoPersonalizadoError("La nueva contraseña no debe ser igual a la anterior.", Color.decode("#C62828"));
                        return; // Detener la ejecución del método
                    }
                }

                if (campoNombre.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Nombre completo\n";
                }

                if (campoCorreo.getText().trim().isEmpty()) {
                    validacion++;
                    mensaje += "Correo electrónico\n";
                }

                if (imagePath.isEmpty()) {
                    validacion++;
                    mensaje += "Imagen\n";
                }

                if (validacion > 0) {
                    mostrarDialogoPersonalizadoError(mensaje, Color.decode("#C62828"));
                    return;
                }

                String nombre = campoNombre.getText().trim();
                if (!nombre.isEmpty()) {
                    if (nombre.length() > 50) {
                        mostrarDialogoPersonalizadoError("El nombre de usuario debe tener como máximo 50 caracteres.", Color.decode("#C62828"));
                        return;
                    }

                    if (!nombre.matches("^[a-zA-ZñÑáéíóúÁÉÍÓÚ]+(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?(\\s[a-zA-ZñÑáéíóúÁÉÍÓÚ]+)?$")) {
                        mostrarDialogoPersonalizadoError("El nombre de usuario debe tener mínimo 2 letras y máximo 3 espacios (1 entre palabras).", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de nombre de usuario no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                String correoElectronico = campoCorreo.getText().trim();
                if (!correoElectronico.isEmpty()) {
                    // Verificar el formato del correo electrónico utilizando una expresión regular
                    if (!correoElectronico.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                        mostrarDialogoPersonalizadoError("El correo electrónico ingresado no tiene un formato válido.", Color.decode("#C62828"));
                        return;
                    }

                    // Verificar el dominio del correo electrónico
                    if (!correoElectronico.endsWith("@gmail.com") &&
                            !correoElectronico.endsWith("@unah.hn") &&
                            !correoElectronico.endsWith("@yahoo.com") &&
                            !correoElectronico.endsWith("@yahoo.es") &&
                            !correoElectronico.endsWith("@hotmail.com")) {
                        mostrarDialogoPersonalizadoError("El dominio del correo electrónico no es válido.", Color.decode("#C62828"));
                        return;
                    }
                } else {
                    mostrarDialogoPersonalizadoError("El campo de correo electrónico no puede estar vacío.", Color.decode("#C62828"));
                    return;
                }

                /*
                String correo = campoCorreo.getText().trim();
                // Verificar si el correo ya está registrado por otro usuario
                if (usuarioExistente(correo, idUsuarioActual)) {
                    mostrarDialogoPersonalizadoError("El correo electrónico ya está registrado. Utiliza otro correo.", Color.decode("#C62828"));
                    return;
                }
                */

                if (imagePath.isEmpty()) {
                    mostrarDialogoPersonalizadoError("Faltó cargar la imagen.", Color.decode("#C62828"));
                    return;
                }

                JButton btnSave = new JButton("Sí");
                JButton btnCancel = new JButton("No");

                // Personaliza los botones aquí
                btnSave.setBackground(darkColorAqua);
                btnCancel.setBackground(darkColorRed);

                // Personaliza los fondos de los botones aquí
                btnSave.setForeground(Color.WHITE);
                btnCancel.setForeground(Color.WHITE);

                // Elimina el foco
                btnSave.setFocusPainted(false);
                btnCancel.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Desea actualizar la información del perfil de usuario?\n  IMPORTANTE: No olvides tu usuario y contraseña.",
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
                        dialog.dispose();
                        actual.dispose();
                        cerrarVentanas();
                        actualizarUsuario();
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

                    String directorio = "img/usuarios/";

                    Date fecha = new Date();
                    SimpleDateFormat formatoFechaHora = new SimpleDateFormat("ddMMyyyy_HHmmss");
                    String fechaHora = formatoFechaHora.format(fecha);

                    // Generar un número aleatorio entre 0001 y 9999
                    int numeroAleatorio = (int) (Math.random() * 9999) + 1;
                    String numeroFormateado = String.format("%04d", numeroAleatorio); // Asegura el formato de 4 dígitos

                    nombreFile = "Usuario_" + fechaHora + " " + numeroFormateado + ".jpg";
                    urlDestino = directorio + nombreFile;

                    File directorioDestino = new File(directorio);
                    if (!directorioDestino.exists()) {
                        directorioDestino.mkdirs(); // Crea la carpeta si no existe
                    }

                    File finalDirectorio = new File(urlDestino);

                    try {
                        BufferedImage imagen = ImageIO.read(new File(imagePath));
                        boolean resultado = ImageIO.write(imagen, "jpg", finalDirectorio);

                        if (!resultado) {
                            mostrarDialogoPersonalizadoError("Error al guardar la imagen", Color.decode("#C62828"));
                            return; // Detiene la ejecución adicional si falla el guardado
                        }
                    } catch (IOException ex) {
                        mostrarDialogoPersonalizadoError("Error al procesar la imagen: " + ex.getMessage(), Color.decode("#C62828"));
                        ex.printStackTrace();
                        return;
                    }

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

                    labelImagen.setIcon(scaledIcon);
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
                btnNo.setBackground(darkColorRed);

                // Personaliza los fondos de los botones aquí
                btnYes.setForeground(Color.WHITE);
                btnNo.setForeground(Color.WHITE);

                // Elimina el foco
                btnYes.setFocusPainted(false);
                btnNo.setFocusPainted(false);

                // Crea un JOptionPane
                JOptionPane optionPane = new JOptionPane(
                        "¿Estás seguro de que deseas reestablecer los datos del usuario?",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.DEFAULT_OPTION,
                        null,
                        new Object[]{}, // no options
                        null
                );

                // Crea un JDialog
                JDialog dialog = optionPane.createDialog("Limpiar");

                // Añade ActionListener a los botones
                btnYes.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        campoCorreo.setText("");
                        campoNombre.setText("");
                        campoContrasenaAnterior.setText("");
                        campoContrasenaNueva.setText("");
                        campoContrasenaConfirmar.setText("");
                        campoRol.setSelectedItem(0);
                        imagePath = ""; // Restablecer la ruta de la imagen
                        mostrar(); // Vuelve a cargar los datos originales
                        dialog.dispose();
                    }
                });

                btnNo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Acciones para el botón No
                        // No se hace nada, sólo se cierra el diálogo
                        dialog.dispose();
                    }
                });

                // Añade los botones al JOptionPane
                optionPane.setOptions(new Object[]{btnYes, btnNo});

                // Muestra el diálogo
                dialog.setVisible(true);
            }
        });
    }

    private int idUsuarioActual;

    private char defaultEchoChar;

    private boolean esVisible = false;

    public void setIdUsuarioActual(int id) {
        this.idUsuarioActual = id;
    }

    private void mostrarContrasenas() {
        // Cambiar el estado de visibilidad de la contraseña
        esVisible = !esVisible;

        // Establecer el modo de eco dependiendo del estado de visibilidad
        char modoEco = esVisible ? (char) 0 : defaultEchoChar; // Usa el carácter de eco por defecto

        campoContrasenaAnterior.setEchoChar(modoEco);
        campoContrasenaNueva.setEchoChar(modoEco);
        campoContrasenaConfirmar.setEchoChar(modoEco);
    }

    private void mostrar() {
        sql = new Conexion();
        mysql = sql.conectamysql();

        try {
            PreparedStatement statement = mysql.prepareStatement("SELECT * FROM usuarios WHERE id = ?;");
            statement.setInt(1, this.id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                campoNombre.setText(resultSet.getString("nombre"));
                campoCorreo.setText(resultSet.getString("correo"));
                String rol = resultSet.getString("rol");
                int indiceRol;

                switch (rol) {
                    case "admin":
                        indiceRol = 1;
                        break;
                    case "general":
                        indiceRol = 2;
                        break;
                    case "variado":
                        indiceRol = 3;
                        break;
                    default:
                        indiceRol = -1; // O algún valor por defecto que indique una selección no válida
                }

                if (indiceRol != -1) {
                    campoRol.setSelectedIndex(indiceRol);
                }

                String imagenNombre = resultSet.getString("imagen");
                String imagenPath = "img/usuarios/" + imagenNombre;
                nombreFile = imagenNombre;
                try {
                    File imagenFile = new File(imagenPath);
                    if (imagenFile.exists()) {
                        // Cargar la imagen sin redimensionar
                        ImageIcon imagenIcono = new ImageIcon(imagenPath);
                        labelImagen.setIcon(imagenIcono);

                        Image imagenOriginal = imagenIcono.getImage();

                        // Calcular la escala para ajustar la imagen al tamaño deseado
                        double scale = Math.min((double) panelImgWidth / imagenIcono.getIconWidth(), (double) panelImgHeight / imagenIcono.getIconHeight());

                        // Calcular las nuevas dimensiones de la imagen redimensionada
                        int scaledWidth = (int) (imagenIcono.getIconWidth() * scale);
                        int scaledHeight = (int) (imagenIcono.getIconHeight() * scale);

                        // Redimensionar la imagen manteniendo su proporción
                        Image imagenRedimensionada = imagenOriginal.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_FAST);
                        ImageIcon imagenFinal = new ImageIcon(imagenRedimensionada);

                        // Establecer el tamaño del panel de imagen (panelImg)
                        Dimension panelImgSize = new Dimension(panelImgWidth, panelImgHeight);
                        panelImg.setPreferredSize(panelImgSize);
                        panelImg.setMaximumSize(panelImgSize);
                        panelImg.setMinimumSize(panelImgSize);
                        panelImg.setSize(panelImgSize);

                        labelImagen.setIcon(imagenFinal);
                        imagePath = imagenPath;
                    }
                } catch (Exception e) {
                    mostrarDialogoPersonalizadoError("Error al cargar la imagen del usuario.", Color.decode("#C62828"));
                }
            }
        } catch (SQLException e) {
            mostrarDialogoPersonalizadoError("Error al obtener los datos del usuario.", Color.decode("#C62828"));
        }
    }

    private boolean validarContrasena(int id, String contrasenaIngresada) {
        Conexion sql = new Conexion();
        Connection connection = sql.conectamysql();
        try {
            String query = "SELECT contrasena FROM usuarios WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, this.id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String contrasenaEncriptada = resultSet.getString("contrasena");
                return BCrypt.checkpw(contrasenaIngresada, contrasenaEncriptada);
            }
        } catch (SQLException e) {
            mostrarDialogoPersonalizadoError("Error al verificar la contraseña: " + e.getMessage(), Color.decode("#C62828"));
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean usuarioExistente(String correo, int id) {
        Conexion sql = new Conexion();
        Connection connection = sql.conectamysql();

        try {
            String query = "SELECT * FROM usuarios WHERE correo = ? AND id <> ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, correo);
            preparedStatement.setInt(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            mostrarDialogoPersonalizadoError("Error, cierra y vuelve a ejecutar", Color.decode("#C62828"));
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String obtenerNombreDeArchivo(String rutaCompleta) {
        File archivo = new File(rutaCompleta);
        return archivo.getName();
    }


    private void actualizarUsuario() {
        try {
            // Obtener solo el nombre del archivo de la imagen
            String nombreArchivoImagen = obtenerNombreDeArchivo(imagePath);
            String nombre = campoNombre.getText().trim();
            String contrasena = new String(campoContrasenaNueva.getPassword());
            String correo = campoCorreo.getText().trim();
            Object itemRol = campoRol.getSelectedItem();
            String rol = "";

            if (itemRol instanceof String) {
                rol = (String) itemRol;
                switch (rol) {
                    case "Administrador":
                        rol = "admin";
                        break;
                    case "General":
                        rol = "general";
                        break;
                    case "Variado":
                        rol = "variado";
                        break;
                    default:
                        return;
                }
            } else {
                return;
            }

            try (Connection connection = sql.conectamysql()) {
                String query;
                if (contrasena.isEmpty()) {
                    query = "UPDATE usuarios SET nombre = ?, correo = ?, imagen = ?, rol = ? WHERE id = ?";
                } else {
                    query = "UPDATE usuarios SET nombre = ?, correo = ?, imagen = ?, contrasena = ?, rol = ? WHERE id = ?";
                }

                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, nombre);
                preparedStatement.setString(2, correo);
                preparedStatement.setString(3, nombreFile);
                preparedStatement.setString(4, rol);

                if (!contrasena.isEmpty()) {
                    String contrasenaEncriptada = BCrypt.hashpw(contrasena, BCrypt.gensalt());
                    preparedStatement.setString(4, contrasenaEncriptada);
                    preparedStatement.setString(5, rol);
                    preparedStatement.setInt(6, id);
                } else {
                    preparedStatement.setInt(5, id);
                }

                preparedStatement.executeUpdate();
                mostrarDialogoPersonalizadoExito("     Usuario actualizado exitosamente.\nPor tu seguridad, vuelve a iniciar sesión.", Color.decode("#263238"));
            } catch (SQLException e) {
                e.printStackTrace();
                mostrarDialogoPersonalizadoError("Error al actualizar el usuario.", Color.decode("#C62828"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            mostrarDialogoPersonalizadoError("Error al actualizar el usuario.", Color.decode("#C62828"));
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
        JDialog dialog = optionPane.createDialog("Éxito");

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
                JOptionPane.WARNING_MESSAGE,   // Tipo de mensaje
                JOptionPane.DEFAULT_OPTION,        // Opción por defecto (no específica aquí)
                null,                              // Icono (puede ser null)
                new Object[]{},                    // No se usan opciones estándar
                null                               // Valor inicial (no necesario aquí)
        );

        // Añade el botón al JOptionPane
        optionPane.setOptions(new Object[]{btnAceptar});

        // Crea un JDialog para mostrar el JOptionPane
        JDialog dialog = optionPane.createDialog("Error");

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

    public void cerrarVentanas() {
        for (Frame frame : Frame.getFrames()) {
            if (frame instanceof JFrame) {
                frame.dispose();
            }
        }

        Login loginFrame = new Login();
        loginFrame.setVisible(true);
        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditarPerfil editarPerfil = new EditarPerfil(1);
            editarPerfil.setVisible(true);
        });
    }
}