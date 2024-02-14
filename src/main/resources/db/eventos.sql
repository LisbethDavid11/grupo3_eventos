DROP DATABASE IF EXISTS eventos;
CREATE DATABASE eventos;
USE eventos;

-- Agregar tabla "roles" para el usuario
DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200) NOT NULL
);

DROP TABLE IF EXISTS permisos;
CREATE TABLE permisos (
  id int NOT NULL AUTO_INCREMENT,
  id_rol int NOT NULL,
  cliente BOOLEAN,
  empleado BOOLEAN,
  floristeria BOOLEAN,
  arreglo BOOLEAN,
  usuario BOOLEAN,
  material BOOLEAN,
  proveedor BOOLEAN,
  compra BOOLEAN,
  tarjeta BOOLEAN,
  manualidad BOOLEAN,
  globo BOOLEAN,
  desayuno BOOLEAN,
  venta BOOLEAN,
  mobiliario BOOLEAN,
  pedido BOOLEAN,
  promocion BOOLEAN,
  evento BOOLEAN,
  actividad BOOLEAN,
  alquiler BOOLEAN,
  rol BOOLEAN,
  PRIMARY KEY (id)
);

-- Agregar tabla "usuarios" para el inicio de sesión y registro
DROP TABLE IF EXISTS usuarios;
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(100) NOT NULL,
    imagen VARCHAR(100) NOT NULL,
	rol_id INT,
    FOREIGN KEY (rol_id) REFERENCES roles(id)
);

-- Agregar tabla "clientes"
DROP TABLE IF EXISTS clientes;
CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    identidad VARCHAR(15) NOT NULL UNIQUE,
    telefono VARCHAR(8) NOT NULL,
    domicilio VARCHAR(200) NOT NULL,
    tipo_cliente ENUM('Mayorista', 'Al Detalle') NOT NULL
);

-- Agregar tabla "proveedores"
DROP TABLE IF EXISTS Proveedores;
CREATE TABLE Proveedores (
	id INT AUTO_INCREMENT PRIMARY KEY,
	empresaProveedora VARCHAR(100),
	rtn VARCHAR(16),
	telefono VARCHAR(9),
	correo VARCHAR(45),
	direccion VARCHAR(200),
	descripcion VARCHAR(200),
	nombreVendedor VARCHAR(100),
	telefonoVendedor VARCHAR(45)
);

-- Agregar tabla "floristerias"
DROP TABLE IF EXISTS floristeria;
CREATE TABLE floristeria (
    id INT PRIMARY KEY AUTO_INCREMENT,
    imagen VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(7, 2) NOT NULL,
    proveedor_id INT,
    FOREIGN KEY (proveedor_id) REFERENCES Proveedores(id)
);

-- Agregar tabla "empleados"
DROP TABLE IF EXISTS Empleados;
CREATE TABLE Empleados (
	id INT PRIMARY KEY AUTO_INCREMENT,
	Identidad VARCHAR(15) UNIQUE NOT NULL,
	Nombres VARCHAR(50) NOT NULL,
	Apellidos VARCHAR(50) NOT NULL,
    Genero ENUM('Femenino', 'Masculino') NOT NULL,
	Edad CHAR(2) NOT NULL,
	Correo VARCHAR(50) NOT NULL,
	Telefono CHAR(8) UNIQUE NOT NULL,
	nombreContactoDeEmergencia VARCHAR(50) NOT NULL,
	ContactoDeEmergencia VARCHAR(50) NOT NULL,
	Direccion VARCHAR(200) NOT NULL,
	TipoDeEmpleado ENUM('Permanente', 'Temporal') NOT NULL
);

-- Agregar tabla "arreglos"
DROP TABLE IF EXISTS arreglos;
CREATE TABLE arreglos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    imagen VARCHAR(100) NOT NULL,
	cantidad INT NOT NULL,
    precio DECIMAL(7, 2) NOT NULL CHECK (precio BETWEEN 0.00 AND 99999.99),
    disponible ENUM('Si', 'No') NOT NULL
);

-- Agregar tabla "materiales"
DROP TABLE IF EXISTS materiales;
CREATE TABLE materiales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL CHECK (LENGTH(nombre) BETWEEN 2 AND 100),
	cantidad INT NOT NULL,
    precio DECIMAL(7, 2) NOT NULL CHECK (precio BETWEEN 0.00 AND 99999.99),
    disponible ENUM('Si', 'No') NOT NULL,
    descripcion VARCHAR(200) NOT NULL CHECK (LENGTH(descripcion) BETWEEN 2 AND 200),
	exento BOOLEAN,
    proveedor_id INT,
    FOREIGN KEY (proveedor_id) REFERENCES Proveedores(id)
);

-- Agregar tabla "compras"
DROP TABLE IF EXISTS compras;
CREATE TABLE compras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    codigo_compra CHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    proveedor_id INT,
    empleado_id INT,
    FOREIGN KEY (proveedor_id) REFERENCES proveedores(id),
    FOREIGN KEY (empleado_id) REFERENCES empleados(id)
);

-- Agregar tabla "detalles_compras"
DROP TABLE IF EXISTS detalles_compras;
CREATE TABLE detalles_compras (
    id INT PRIMARY KEY AUTO_INCREMENT,
    compra_id INT,
    material_id INT,
    cantidad INT NOT NULL,
    precio DECIMAL(9, 2) NOT NULL,
    FOREIGN KEY (compra_id) REFERENCES compras(id),
    FOREIGN KEY (material_id) REFERENCES materiales(id)
);

-- Crear tabla "tarjetas"
DROP TABLE IF EXISTS tarjetas;
CREATE TABLE tarjetas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    ocasion VARCHAR(100) NOT NULL,
    disponible ENUM('Si', 'No') NOT NULL,
    descripcion TEXT,
    imagen VARCHAR(100),
    cantidad INT NOT NULL,
    precio_tarjeta DECIMAL(7, 2) NOT NULL CHECK (precio_tarjeta BETWEEN 0.00 AND 99999.99),
    mano_obra DECIMAL(7, 2) NOT NULL CHECK (mano_obra BETWEEN 0.00 AND 99999.99)
);

-- Agregar tabla "tarjetas_detalles"
DROP TABLE IF EXISTS tarjetas_detalles;
CREATE TABLE tarjetas_detalles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_material INT,
	id_tarjeta INT,
    cantidad INT NOT NULL,
    precio DECIMAL(9, 2) NOT NULL,
    FOREIGN KEY (id_material) REFERENCES materiales(id),
    FOREIGN KEY (id_tarjeta) REFERENCES tarjetas(id)
);

-- Agregar tabla "manualidades"
DROP TABLE IF EXISTS manualidades;
CREATE TABLE manualidades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    imagen VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL CHECK (LENGTH(nombre) BETWEEN 2 AND 100),
    descripcion VARCHAR(200) NOT NULL CHECK (LENGTH(descripcion) BETWEEN 2 AND 200),
    tipo ENUM('Centros de mesas', 'Letras personalizadas', 'Cajas personalizadas', 'Arcos para eventos', 'Fotografía personalizadas') NOT NULL,
	cantidad INT NOT NULL,
    precio_manualidad DECIMAL(7, 2) NOT NULL CHECK (precio_manualidad BETWEEN 0.00 AND 99999.99),
    mano_obra DECIMAL(7, 2) NOT NULL CHECK (mano_obra BETWEEN 0.00 AND 99999.99)
);

-- Agregar tabla "detalles_manualidades"
DROP TABLE IF EXISTS detalles_manualidades;
CREATE TABLE detalles_manualidades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    manualidad_id INT,
    material_id INT,
	cantidad INT NOT NULL,
    precio DECIMAL(9, 2) NOT NULL,
    FOREIGN KEY (manualidad_id) REFERENCES manualidades(id),
    FOREIGN KEY (material_id) REFERENCES materiales(id)
);

-- Agregar tabla "globos"
DROP TABLE IF EXISTS globos;
CREATE TABLE globos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    imagen VARCHAR(100) NOT NULL,
    codigo_globo CHAR(50) NOT NULL,
    tipo ENUM('Cumpleaños', 'Graduación', 'San Valentín', 'Baby Shower', 'Boda', 'Otros') NOT NULL,
    material ENUM('Latex', 'Aluminio') NOT NULL,
    para ENUM('Aire', 'Helio', 'Ambos') NOT NULL,
    tamano CHAR(50) NOT NULL,
    color CHAR(50) NOT NULL,
    forma CHAR(50) NOT NULL,
    cantidad_paquete INT,
    porta_globo BOOLEAN,
    cantidad INT NOT NULL,
    precio DECIMAL(7, 2) NOT NULL
);

-- Agregar tabla "desayunos"
DROP TABLE IF EXISTS desayunos;
CREATE TABLE desayunos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    imagen VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    proveedor_id INT,
	cantidad INT NOT NULL,
    precio_desayuno DECIMAL(7, 2) NOT NULL CHECK (precio_desayuno BETWEEN 0.00 AND 99999.99),
    mano_obra DECIMAL(7, 2) NOT NULL CHECK (mano_obra BETWEEN 0.00 AND 99999.99),
    FOREIGN KEY (proveedor_id) REFERENCES Proveedores(id)
);

-- Agregar tabla "detalles_desayunos"
DROP TABLE IF EXISTS detalles_desayunos;
CREATE TABLE detalles_desayunos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    desayuno_id INT,
    tipo_detalle ENUM('material', 'globo', 'tarjeta', 'floristeria') NOT NULL,
    detalle_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(9, 2) NOT NULL,
    FOREIGN KEY (desayuno_id) REFERENCES desayunos(id)
);

CREATE TABLE mobiliario (
  id INT PRIMARY KEY AUTO_INCREMENT,
  nombreMobiliario VARCHAR(50) NOT NULL,
  color VARCHAR(50) NOT NULL,
  descripcion TEXT NOT NULL,
  cantidad INT NOT NULL,
  precioUnitario DECIMAL(7, 2) NOT NULL CHECK (precioUnitario BETWEEN 0.00 AND 99999.99),
  image TEXT NOT NULL
);

-- Agregar tabla "ventas"
DROP TABLE IF EXISTS ventas;
CREATE TABLE ventas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    codigo_venta CHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    cliente_id INT,
    usuario_id INT,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Agregar tabla "detalles_ventas"
DROP TABLE IF EXISTS detalles_ventas;
CREATE TABLE detalles_ventas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    venta_id INT,
    tipo_detalle ENUM('material', 'manualidad', 'tarjeta', 'floristeria', 'desayuno','arreglo',  'globo', 'mobiliario', 'resumen_promocion') NOT NULL,
    detalle_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(9, 2) NOT NULL,
    FOREIGN KEY (venta_id) REFERENCES ventas(id)
);

-- Agregar tabla "pedidos"
DROP TABLE IF EXISTS pedidos;
CREATE TABLE pedidos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    codigo_pedido CHAR(50) NOT NULL,
    fecha_pedido DATE NOT NULL,
    fecha_entrega DATE NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    cliente_id INT NOT NULL,
	usuario_id INT,
    entrega ENUM('Domicilio', 'Tienda') NOT NULL, 
	precio_envio DOUBLE NOT NULL,
    estado ENUM('Proceso', 'Enviado', 'Entregado') NOT NULL, 
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Agregar tabla "detalles_pedidos"
DROP TABLE IF EXISTS detalles_pedidos;
CREATE TABLE detalles_pedidos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pedido_id INT,
    tipo_detalle ENUM('arreglo', 'floristeria', 'manualidad', 'tarjeta', 'desayuno','material' ) NOT NULL,
    detalle_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(9, 2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id)
);

-- Agregar tabla "eventos"
DROP TABLE IF EXISTS eventos;
CREATE TABLE eventos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    cliente_id INT,
    direccion VARCHAR(200) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    codigo_evento CHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    inicio TIME NOT NULL,
    fin TIME NOT NULL,
    usuario_id INT,
    estado ENUM('Pendiente', 'En Proceso', 'Terminado') NOT NULL, 
    FOREIGN KEY (cliente_id) REFERENCES clientes(id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Agregar tabla "detalles_eventos"
DROP TABLE IF EXISTS detalles_eventos;
CREATE TABLE detalles_eventos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    evento_id INT,
    tipo_detalle ENUM('mobiliario', 'floristeria', 'globo', 'arreglo', 'manualidad' ) NOT NULL,
    detalle_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(9, 2) NOT NULL,
    FOREIGN KEY (evento_id) REFERENCES eventos(id)
);

-- Agregar tabla "promociones"
DROP TABLE IF EXISTS promociones;
CREATE TABLE promociones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(200) NOT NULL,
    inicio DATE NOT NULL,
    fin DATE NOT NULL
);

-- Agregar tabla "detalles_promociones"
DROP TABLE IF EXISTS detalles_promociones;
CREATE TABLE detalles_promociones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    promocion_id INT,
    tipo_detalle ENUM('mobiliario', 'floristeria', 'globo', 'arreglo', 'manualidad','tarjeta','material','desayuno') NOT NULL,
    detalle_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(9, 2) NOT NULL,
    promocion DECIMAL(9, 2) NOT NULL,
    FOREIGN KEY (promocion_id) REFERENCES promociones(id)
);

DROP TABLE IF EXISTS resumen_promociones;
CREATE TABLE resumen_promociones (
    id INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(200) NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(9, 2) NOT NULL,
    promocion DECIMAL(9, 2) NOT NULL
);

-- Agregar tabla "actividades"
DROP TABLE IF EXISTS actividades;
CREATE TABLE actividades (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(200) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
	fecha DATE NOT NULL,
    inicio TIME NOT NULL,
    fin TIME NOT NULL
);

DROP TABLE IF EXISTS alquileres;
CREATE TABLE IF NOT EXISTS alquileres (
  id int NOT NULL AUTO_INCREMENT,
  cliente_id int NOT NULL,
  empleado_id int NOT NULL,
  descripcion text NOT NULL,
  tipo varchar(45) NOT NULL,
  fecha date NOT NULL,
  hora_inicial time NOT NULL,
  hora_final time NOT NULL,
  activo enum('A','I') DEFAULT 'A',
  condiciones varchar(255) DEFAULT NULL,
  fechaDev date DEFAULT NULL,
  PRIMARY KEY (id)
);

DROP TABLE IF EXISTS detalles_alquileres;
CREATE TABLE IF NOT EXISTS detalles_alquileres (
  id int NOT NULL AUTO_INCREMENT,
  alquiler_id int DEFAULT NULL,
  tipo_detalle enum('mobiliario') NOT NULL,
  detalle_id int NOT NULL,
  cantidad int NOT NULL,
  precio decimal(9,2) NOT NULL,
  cantidad_devuelta int DEFAULT NULL,
  indennizacion decimal(9,2) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY alquiler_id (alquiler_id),
  CONSTRAINT detalles_alquileres_ibfk_1 FOREIGN KEY (alquiler_id) REFERENCES alquileres (id)
);

DELIMITER $$
CREATE TRIGGER after_employee_insert 
AFTER INSERT ON Empleados
FOR EACH ROW 
BEGIN
    -- Declarar variables
    DECLARE nombreUsuario VARCHAR(100);
    DECLARE imagenUsuario VARCHAR(100);

    -- Generar nombre de usuario como 'nombre + apellido'
    SET nombreUsuario = CONCAT(NEW.Nombres, ' ', NEW.Apellidos);

    -- Seleccionar una imagen aleatoria
    SET imagenUsuario = CONCAT('imagen ', FLOOR(1 + (RAND() * 10)), '.jpg'); -- Esto generará 'imagen X.jpg' donde X es un número del 1 al 10

    -- Insertar en la tabla de usuarios
    INSERT INTO usuarios (nombre, correo, contrasena, imagen, rol_id) 
    VALUES (nombreUsuario, NEW.Correo, '$2a$10$.WTJkwWrj/X59jq5M6Y2JOLfIh9uzS1DYCWjjXBScTK8aM74Df5E.', imagenUsuario, 2);
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER after_employee_update 
AFTER UPDATE ON Empleados
FOR EACH ROW 
BEGIN
    -- Actualizar nombre y correo en usuarios
    UPDATE usuarios
    SET nombre = CONCAT(NEW.Nombres, ' ', NEW.Apellidos),
        correo = NEW.Correo
    WHERE correo = OLD.Correo; -- Suponiendo que el correo es una clave única o identificador
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER after_pedido_update
AFTER UPDATE ON pedidos
FOR EACH ROW
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE a_id, a_cantidad INT;
    DECLARE a_tipo VARCHAR(50);
    DECLARE cur CURSOR FOR SELECT tipo_detalle, detalle_id, cantidad FROM detalles_pedidos WHERE pedido_id = NEW.id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    IF OLD.estado != 'Enviado' AND NEW.estado = 'Enviado' THEN
        INSERT INTO ventas (codigo_venta, fecha, cliente_id, usuario_id)
        VALUES (NEW.codigo_pedido, NEW.fecha_entrega, NEW.cliente_id, NEW.usuario_id);

        SET @venta_id = LAST_INSERT_ID();

        INSERT INTO detalles_ventas (venta_id, tipo_detalle, detalle_id, cantidad, precio)
        SELECT @venta_id, tipo_detalle, detalle_id, cantidad, precio
        FROM detalles_pedidos
        WHERE pedido_id = NEW.id;

        -- Abrir el cursor
        OPEN cur;

        read_loop: LOOP
            FETCH cur INTO a_tipo, a_id, a_cantidad;
            IF done THEN
                LEAVE read_loop;
            END IF;

            -- Actualizar las cantidades en las tablas correspondientes
            CASE 
                WHEN a_tipo = 'floristeria' THEN
                    UPDATE floristeria SET cantidad = cantidad - a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'arreglo' THEN
                    UPDATE arreglos SET cantidad = cantidad - a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'material' THEN
                    UPDATE materiales SET cantidad = cantidad - a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'tarjeta' THEN
                    UPDATE tarjetas SET cantidad = cantidad - a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'desayuno' THEN
                    UPDATE desayunos SET cantidad = cantidad - a_cantidad WHERE id = a_id;
				WHEN a_tipo = 'manualidad' THEN
                    UPDATE manualidades SET cantidad = cantidad - a_cantidad WHERE id = a_id;
            END CASE;
        END LOOP;

        -- Cerrar el cursor
        CLOSE cur;
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER after_evento_proceso
AFTER UPDATE ON eventos
FOR EACH ROW
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE a_id, a_cantidad INT;
    DECLARE a_tipo VARCHAR(50);
    DECLARE cur CURSOR FOR SELECT tipo_detalle, detalle_id, cantidad FROM detalles_eventos WHERE evento_id = NEW.id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
    

    IF OLD.estado != 'En Proceso' AND NEW.estado = 'En Proceso' THEN
        INSERT INTO ventas (codigo_venta, fecha, cliente_id, usuario_id)
        VALUES (NEW.codigo_evento, NEW.fecha, NEW.cliente_id, NEW.usuario_id);

        SET @venta_id = LAST_INSERT_ID();

        INSERT INTO detalles_ventas (venta_id, tipo_detalle, detalle_id, cantidad, precio)
        SELECT @venta_id, tipo_detalle, detalle_id, cantidad, precio
        FROM detalles_eventos
        WHERE evento_id = NEW.id;

        -- Abrir el cursor
        OPEN cur;

        read_loop: LOOP
            FETCH cur INTO a_tipo, a_id, a_cantidad;
            IF done THEN
                LEAVE read_loop;
            END IF;

            -- Actualizar las cantidades en las tablas correspondientes
             CASE 
                WHEN a_tipo = 'mobiliario' THEN
                    UPDATE mobiliario SET cantidad = cantidad - a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'floristeria' THEN
                    UPDATE floristeria SET cantidad = cantidad - a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'globo' THEN
                    UPDATE globos SET cantidad = cantidad - a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'arreglo' THEN
                    UPDATE arreglos SET cantidad = cantidad - a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'manualidad' THEN
                    UPDATE manualidades SET cantidad = cantidad - a_cantidad WHERE id = a_id;
            END CASE;
        END LOOP;
        -- Cerrar el cursor
        CLOSE cur;
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER after_evento_terminado
AFTER UPDATE ON eventos
FOR EACH ROW
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE a_id, a_cantidad INT;
    DECLARE a_tipo VARCHAR(50);
    DECLARE cur CURSOR FOR SELECT tipo_detalle, detalle_id, cantidad FROM detalles_eventos WHERE evento_id = NEW.id;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    IF OLD.estado != 'Terminado' AND NEW.estado = 'Terminado' THEN

        OPEN cur;

        read_loop: LOOP
            FETCH cur INTO a_tipo, a_id, a_cantidad;
            IF done THEN
                LEAVE read_loop;
            END IF;

            CASE 
                WHEN a_tipo = 'mobiliario' THEN
                    UPDATE mobiliario SET cantidad = cantidad + a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'floristeria' THEN
                    UPDATE floristeria SET cantidad = cantidad + a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'globo' THEN
                    UPDATE globos SET cantidad = cantidad + a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'arreglo' THEN
                    UPDATE arreglos SET cantidad = cantidad + a_cantidad WHERE id = a_id;
                WHEN a_tipo = 'manualidad' THEN
                    UPDATE manualidades SET cantidad = cantidad + a_cantidad WHERE id = a_id;
                -- Agregar más casos según sea necesario
            END CASE;
        END LOOP;

        CLOSE cur;
    END IF;
END$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER after_detalle_insert
AFTER INSERT ON detalles_promociones
FOR EACH ROW
BEGIN
    DECLARE precio DECIMAL(9, 2);
    DECLARE promocion DECIMAL(9, 2);
    DECLARE cantidad INT;
    DECLARE existeResumen INT;

    -- Calcula los totales considerando la cantidad
    SELECT SUM(detalles_promociones.cantidad * detalles_promociones.precio), 
           SUM(detalles_promociones.cantidad * detalles_promociones.promocion),
           SUM(detalles_promociones.cantidad)
    INTO precio, promocion, cantidad
    FROM detalles_promociones
    WHERE promocion_id = NEW.promocion_id;

    -- Verifica si ya existe un resumen
    SELECT COUNT(*)
    INTO existeResumen
    FROM resumen_promociones
    WHERE id = NEW.promocion_id;

    -- Actualiza o inserta en resumen_promociones
    IF existeResumen > 0 THEN
        UPDATE resumen_promociones
        SET cantidad = cantidad, precio = precio, promocion = promocion
        WHERE id = NEW.promocion_id;
    ELSE
        INSERT INTO resumen_promociones (id, descripcion, cantidad, precio, promocion)
        SELECT id, descripcion, cantidad, precio, promocion
        FROM promociones
        WHERE id = NEW.promocion_id;
    END IF;
END$$
DELIMITER ;

-- Insertar roles 
INSERT INTO roles (nombre, descripcion) VALUES
    ('Administrador','Descripción del rol 1'),
    ('Cajero principal','Descripción del rol 2'),
    ('Gerente','Descripción del rol 3'),
	('Cajero','Descripción del rol 4'),
	('Conserje','Descripción del rol 5'),
	('Anexo','Descripción del rol 6'),
	('Otro','Descripción del rol 7');

-- Insertar un registro de administrador en la tabla de usuarios
INSERT INTO usuarios (imagen, nombre, correo, contrasena, rol_id) VALUES
    ('imagen 1.jpg','Administrador', 'admin', '$2a$10$.WTJkwWrj/X59jq5M6Y2JOLfIh9uzS1DYCWjjXBScTK8aM74Df5E.', 1),
    ('imagen 2.jpg','Dania Lagos', 'daniameliruiz@gmail.com', '$2a$10$.WTJkwWrj/X59jq5M6Y2JOLfIh9uzS1DYCWjjXBScTK8aM74Df5E.', 1),
    ('imagen 3.jpg','Danelly Aroca', 'danearoca@gmail.com', '$2a$10$.WTJkwWrj/X59jq5M6Y2JOLfIh9uzS1DYCWjjXBScTK8aM74Df5E.', 3),
    ('imagen 4.jpg','Lic. Gladys', 'gladys', '$2a$10$.WTJkwWrj/X59jq5M6Y2JOLfIh9uzS1DYCWjjXBScTK8aM74Df5E.', 1),
    ('imagen 5.jpg','Lisbeth David', 'lisbethdavid711@gmail.com', '$2a$10$.WTJkwWrj/X59jq5M6Y2JOLfIh9uzS1DYCWjjXBScTK8aM74Df5E.', 3),
    ('imagen 6.jpg','Skarleth Ferrera', 'skarleth', '$2a$10$.WTJkwWrj/X59jq5M6Y2JOLfIh9uzS1DYCWjjXBScTK8aM74Df5E.', 2),
    ('imagen 7.jpg','Elsa Ramos', 'ceciliaramos1206@gmail.com', '$2a$10$.WTJkwWrj/X59jq5M6Y2JOLfIh9uzS1DYCWjjXBScTK8aM74Df5E.', 2),
	('imagen 7.jpg','Dark Net', 'sandra@dinant.com.org', '$2a$10$.WTJkwWrj/X59jq5M6Y2JOLfIh9uzS1DYCWjjXBScTK8aM74Df5E.', 3);
    
INSERT INTO permisos (id_rol, cliente, empleado, floristeria, arreglo, usuario, material, proveedor, compra, tarjeta, manualidad, globo, desayuno, venta, mobiliario, pedido, promocion, evento, actividad, alquiler, rol) VALUES
(1, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true),
(2, false, true, true, true, true, true, true, true, true, true, true, true, false, false, true, true, true, true, false, false),
(3, false, false, false, true, true, false, true, true, true, true, true, false, true, true, true, true, false, false, false, false),
(4, true, false, true, false, true, true, false, true, true, true, true, true, true, true, true, true, false, false, false, true),
(5, false, true, true, false, true, true, false, true, true, true, true, true, true, true, true, true, false, false, false, true),
(6, false, true, true, false, true, false, true, true, true, true, true, true, true, true, true, true, false, false, false, true),
(7, false, true, true, false, true, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false);

INSERT INTO clientes (nombre, apellido, identidad, telefono, domicilio, tipo_cliente) VALUES
('Luka', 'Modrik', '0703-1997-01588', '95779921','304 Oak Street, Apt 6A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Al Detalle'),
('Matheo', 'Kovacic', '0701-1991-02948','98643569', '456 Elm Avenue, Unit 3B, Willowbrook Manor, Los Angeles, California, 90001-5678, United States of America', 'Mayorista'),
('Romelu', 'Lukaku', '0703-1992-01139', '33256798','789 Maple Drive, Suite 12C, Riverfront Condos, New York, New York, 10001-2345, United States of America', 'Mayorista'),
('Cristian', 'Pulisic', '0703-1997-89290','33568792', '123 Pine Street, Apt 9D, Lakeside Apartments, Chicago, Illinois, 60601-7890, United States of America', 'Al Detalle'),
('Samantha', 'Bravo', '0703-1992-09827', '98998643','567 Cedar Lane, Unit 5F, Hillside Heights, Miami, Florida, 33101-8901, United States of America', 'Al Detalle'),
('Diego', 'Costa', '0702-1989-11234', '99999999','890 Oak Lane, Apt 2C, Hillside Manor, San Francisco, California, 94102-5678, United States of America', 'Mayorista'),
('Isabella', 'Gómez', '0705-1995-23465', '88888888','567 Elm Street, Unit 9A, Willowbrook Apartments, Los Angeles, California, 90001-1234, United States of America', 'Al Detalle'),
('Gareth', 'Bale', '0704-1994-33456', '77777777','123 Maple Avenue, Suite 5D, Riverfront Condos, New York, New York, 10001-7890, United States of America', 'Mayorista'),
('Alexis', 'Sánchez', '0707-1995-44567', '66666666','456 Pine Drive, Apt 8B, Lakeside Apartments, Chicago, Illinois, 60601-2345, United States of America', 'Al Detalle'),
('Paul', 'Pogba', '0708-1996-55678', '55555555','789 Cedar Lane, Unit 3E, Hillside Heights, Miami, Florida, 33101-6789, United States of America', 'Al Detalle'),
('Marco', 'Reus', '0701-1991-66489', '44444444','234 Elm Street, Apt 4F, Bayview Heights Apartments, San Francisco, California, 94102-9012, United States of America', 'Mayorista'),
('Dani', 'Alves', '0706-1983-77490', '33333333','567 Maple Avenue, Suite 2A, Riverfront Condos, New York, New York, 10001-3456, United States of America', 'Al Detalle'),
('Hirving', 'Lozano', '0709-1995-84903', '22222222','890 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Mayorista'),
('Antoine', 'Griezmann', '0705-1991-94512', '11111111','123 Cedar Lane, Unit 6B, Hillside Heights, Miami, Florida, 33101-2345, United States of America', 'Al Detalle'),
('Edinson', 'Cavani', '0703-1987-04423', '00000000','456 Elm Street, Apt 9D, Bayview Heights Apartments, San Francisco, California, 94102-9012, United States of America', 'Al Detalle'),
('David', 'Silva', '0706-1986-11424', '98765432','789 Maple Drive, Suite 12C, Riverfront Condos, New York, New York, 10001-3456, United States of America', 'Mayorista'),
('Neymar', 'Jr.', '0702-1992-22445', '87654321','234 Pine Drive, Apt 5A, Lakeside Apartments, Chicago, Illinois, 60601-5678, United States of America', 'Al Detalle'),
('Sergio', 'Ramos', '0708-1986-34456', '76543210','567 Cedar Lane, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Mayorista'),
('Kevin', 'De Bruyne', '0704-1991-44467', '65432109','890 Elm Avenue, Unit 8C, Willowbrook Manor, Los Angeles, California, 90001-6789, United States of America', 'Al Detalle'),
('Robert', 'Lewandowski', '0701-1988-55483', '54321098','123 Maple Drive, Suite 10D, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Al Detalle'),
('Lucía', 'González', '0703-1997-87422', '98765432', '123 Elm Street, Apt 1A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Al Detalle'),
('Sebastián', 'Martínez', '0701-1994-91421', '87654321', '456 Oak Avenue, Unit 4B, Willowbrook Manor, Los Angeles, California, 90001-5678, United States of America', 'Mayorista'),
('Valentina', 'Hernández', '0703-1994-82324', '76543210', '789 Pine Drive, Suite 9C, Riverfront Condos, New York, New York, 10001-2345, United States of America', 'Al Detalle'),
('Mateo', 'López', '0703-1996-39441', '65432109', '567 Cedar Lane, Apt 5D, Lakeside Apartments, Chicago, Illinois, 60601-7890, United States of America', 'Mayorista'),
('Isabella', 'García', '0705-1994-38291', '54321098', '890 Maple Avenue, Unit 8B, Hillside Heights, Miami, Florida, 33101-8901, United States of America', 'Al Detalle'),
('Daniel', 'Pérez', '0702-1990-19942', '43210987', '123 Oak Lane, Apt 2C, Hillside Manor, San Francisco, California, 94102-5678, United States of America', 'Mayorista'),
('María', 'Rodríguez', '0705-1994-84833', '32109876', '456 Elm Street, Unit 9A, Willowbrook Apartments, Los Angeles, California, 90001-1234, United States of America', 'Al Detalle'),
('Alejandro', 'Herrera', '0707-1992-89100', '21098765', '789 Pine Drive, Suite 5D, Riverfront Condos, New York, New York, 10001-7890, United States of America', 'Mayorista'),
('Valeria', 'Gómez', '0708-1995-88392', '10987654', '234 Cedar Lane, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-2345, United States of America', 'Al Detalle'),
('Joaquín', 'Sánchez', '0709-1993-83911', '09876543', '567 Elm Street, Unit 6B, Hillside Heights, Miami, Florida, 33101-6789, United States of America', 'Mayorista'),
('Fernanda', 'Torres', '0701-1997-39854', '98765432', '890 Maple Avenue, Apt 3F, Bayview Heights Apartments, San Francisco, California, 94102-9012, United States of America', 'Al Detalle'),
('Carlos', 'Ruíz', '0706-1990-91130', '87654321', '123 Oak Lane, Suite 2A, Riverfront Condos, New York, New York, 10001-3456, United States of America', 'Al Detalle'),
('Sofía', 'Guzmán', '0709-1996-75779', '76543210', '456 Cedar Lane, Unit 9B, Lakeside Apartments, Chicago, Illinois, 60601-5678, United States of America', 'Mayorista'),
('Andrés', 'Romero', '0703-1998-34887', '65432109', '789 Elm Avenue, Apt 7C, Willowbrook Manor, Los Angeles, California, 90001-6789, United States of America', 'Al Detalle'),
('Mariana', 'Chávez', '0702-1995-69978', '54321098', '234 Pine Drive, Suite 4A, Hillside Heights, Miami, Florida, 33101-2345, United States of America', 'Mayorista'),
('Diego', 'Vargas', '0708-1994-90116', '43210987', '567 Cedar Lane, Apt 5D, Hillside Heights, Miami, Florida, 33101-7890, United States of America', 'Al Detalle'),
('Victoria', 'Luna', '0704-1996-98154', '32109876', '890 Elm Street, Unit 8B, Willowbrook Apartments, Los Angeles, California, 90001-5678, United States of America', 'Mayorista'),
('Julián', 'Mendoza', '0701-1992-9920', '21098765', '123 Maple Drive, Suite 5D, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Al Detalle'),
('Luciana', 'Ortega', '0703-1997-81919', '10987654', '456 Cedar Lane, Apt 3B, Lakeside Apartments, Chicago, Illinois, 60601-5678, United States of America', 'Mayorista'),
('David', 'Guerrero', '0708-1994-46712', '09876543', '789 Elm Street, Suite 7A, Hillside Heights, Miami, Florida, 33101-9012, United States of America', 'Al Detalle'),
('Lucía', 'González', '0703-1997-87332', '98765432', '123 Elm Street, Apt 1A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Al Detalle'),
('Sebastián', 'Martínez', '0701-1993-91161', '87654321', '456 Oak Avenue, Unit 4B, Willowbrook Manor, Los Angeles, California, 90001-5678, United States of America', 'Mayorista'),
('Valentina', 'Hernández', '0703-1998-82334', '76543210', '789 Pine Drive, Suite 9C, Riverfront Condos, New York, New York, 10001-2345, United States of America', 'Al Detalle'),
('Mateo', 'López', '0703-1996-92836', '65432109', '567 Cedar Lane, Apt 5D, Lakeside Apartments, Chicago, Illinois, 60601-7890, United States of America', 'Mayorista'),
('Isabella', 'García', '0705-1995-17382', '54321098', '890 Maple Avenue, Unit 8B, Hillside Heights, Miami, Florida, 33101-8901, United States of America', 'Al Detalle'),
('Daniel', 'Pérez', '0702-1990-91020', '43210987', '123 Oak Lane, Apt 2C, Hillside Manor, San Francisco, California, 94102-5678, United States of America', 'Mayorista'),
('María', 'Rodríguez', '0705-1994-29011', '32109876', '456 Elm Street, Unit 9A, Willowbrook Apartments, Los Angeles, California, 90001-1234, United States of America', 'Al Detalle'),
('Alejandro', 'Herrera', '0707-1992-12221', '21098765', '789 Pine Drive, Suite 5D, Riverfront Condos, New York, New York, 10001-7890, United States of America', 'Mayorista'),
('Valeria', 'Gómez', '0708-1995-56421', '10987654', '234 Cedar Lane, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-2345, United States of America', 'Al Detalle'),
('Joaquín', 'Sánchez', '0709-1993-12781', '09876543', '567 Elm Street, Unit 6B, Hillside Heights, Miami, Florida, 33101-6789, United States of America', 'Mayorista'),
('Fernanda', 'Torres', '0701-1997-2110', '98765432', '890 Maple Avenue, Apt 3F, Bayview Heights Apartments, San Francisco, California, 94102-9012, United States of America', 'Al Detalle'),
('Carlos', 'Ruíz', '0706-1990-44035', '87654321', '123 Oak Lane, Suite 2A, Riverfront Condos, New York, New York, 10001-3456, United States of America', 'Al Detalle'),
('Sofía', 'Guzmán', '0709-1996-92109', '76543210', '456 Cedar Lane, Unit 9B, Lakeside Apartments, Chicago, Illinois, 60601-5678, United States of America', 'Mayorista'),
('Andrés', 'Romero', '0703-1998-34117', '65432109', '789 Elm Avenue, Apt 7C, Willowbrook Manor, Los Angeles, California, 90001-6789, United States of America', 'Al Detalle'),
('Mariana', 'Chávez', '0702-1995-61378', '54321098', '234 Pine Drive, Suite 4A, Hillside Heights, Miami, Florida, 33101-2345, United States of America', 'Mayorista'),
('Diego', 'Vargas', '0708-1994-00815', '43210987', '567 Cedar Lane, Apt 5D, Hillside Heights, Miami, Florida, 33101-7890, United States of America', 'Al Detalle'),
('Victoria', 'Luna', '0704-1996-90034', '32109876', '890 Elm Street, Unit 8B, Willowbrook Apartments, Los Angeles, California, 90001-5678, United States of America', 'Mayorista'),
('Julián', 'Mendoza', '0701-1992-42454', '21098765', '123 Maple Drive, Suite 5D, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Al Detalle'),
('Luciana', 'Ortega', '0703-1997-65459', '10987654', '456 Cedar Lane, Apt 3B, Lakeside Apartments, Chicago, Illinois, 60601-5678, United States of America', 'Mayorista'),
('David', 'Guerrero', '0708-1994-99967', '09876543', '789 Elm Street, Suite 7A, Hillside Heights, Miami, Florida, 33101-9012, United States of America', 'Al Detalle'),
('Elena', 'Herrera', '0702-1994-83462', '43210987', '567 Cedar Lane, Apt 5D, Hillside Heights, Miami, Florida, 33101-7890, United States of America', 'Al Detalle'),
('Manuel', 'Vargas', '0708-1992-96734', '32109876', '890 Elm Street, Unit 8B, Willowbrook Apartments, Los Angeles, California, 90001-5678, United States of America', 'Mayorista'),
('Carolina', 'Luna', '0704-1995-42302', '21098765', '123 Maple Drive, Suite 5D, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Al Detalle'),
('Santiago', 'Ortega', '0703-1996-65019', '10987654', '456 Cedar Lane, Apt 3B, Lakeside Apartments, Chicago, Illinois, 60601-5678, United States of America', 'Mayorista'),
('Valentina', 'Guerrero', '0708-1993-98007', '09876543', '789 Elm Street, Suite 7A, Hillside Heights, Miami, Florida, 33101-9012, United States of America', 'Al Detalle'),
('Javier', 'Pérez', '0702-1990-00912', '43210987', '123 Oak Lane, Apt 2C, Hillside Manor, San Francisco, California, 94102-5678, United States of America', 'Mayorista'),
('Mariana', 'Rodríguez', '0705-1994-7382', '32109876', '456 Elm Street, Unit 9A, Willowbrook Apartments, Los Angeles, California, 90001-1234, United States of America', 'Al Detalle'),
('Gabriel', 'Hernández', '0707-1996-90112', '21098765', '789 Pine Drive, Suite 5D, Riverfront Condos, New York, New York, 10001-7890, United States of America', 'Mayorista'),
('Valeria', 'Sánchez', '0708-1995-88992', '10987654', '234 Cedar Lane, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-2345, United States of America', 'Al Detalle'),
('Andrés', 'Romero', '0709-1993-82191', '09876543', '567 Elm Street, Unit 6B, Hillside Heights, Miami, Florida, 33101-6789, United States of America', 'Mayorista'),
('Fernando', 'Torres', '0711-1994-34654', '98765432', '890 Maple Avenue, Apt 3F, Bayview Heights Apartments, San Francisco, California, 94102-9012, United States of America', 'Al Detalle'),
('María', 'Ruíz', '0706-1990-77658', '87654321', '123 Oak Lane, Suite 2A, Riverfront Condos, New York, New York, 10001-3456, United States of America', 'Al Detalle'),
('Sofía', 'Guzmán', '0709-1996-19077', '76543210', '456 Cedar Lane, Unit 9B, Lakeside Apartments, Chicago, Illinois, 60601-5678, United States of America', 'Mayorista'),
('Andrea', 'Chávez', '0703-1998-34777', '65432109', '789 Elm Avenue, Apt 7C, Willowbrook Manor, Los Angeles, California, 90001-6789, United States of America', 'Al Detalle'),
('Daniel', 'Vargas', '0702-1995-65318', '54321098', '234 Pine Drive, Suite 4A, Hillside Heights, Miami, Florida, 33101-2345, United States of America', 'Mayorista'),
('María', 'Luna', '0708-1994-09652', '43210987', '567 Cedar Lane, Apt 5D, Hillside Heights, Miami, Florida, 33101-7890, United States of America', 'Al Detalle'),
('Sara', 'Ortega', '0704-1996-98774', '32109876', '890 Elm Street, Unit 8B, Willowbrook Apartments, Los Angeles, California, 90001-5678, United States of America', 'Mayorista'),
('Javier', 'Mendoza', '0701-1992-42894', '21098765', '123 Maple Drive, Suite 5D, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Al Detalle'),
('Valentina', 'Guerrero', '0703-1997-65189', '10987654', '456 Cedar Lane, Apt 3B, Lakeside Apartments, Chicago, Illinois, 60601-5678, United States of America', 'Mayorista'),
('Camila', 'Castro', '0702-1998-82788', '98765432', '123 Elm Street, Apt 1A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Al Detalle'),
('Gabriel', 'González', '0708-1993-91321', '87654321', '456 Oak Avenue, Unit 4B, Willowbrook Manor, Los Angeles, California, 90001-5678, United States of America', 'Mayorista'),
('Valentina', 'López', '0703-1992-01339', '33256798', '789 Pine Drive, Suite 9C, Riverfront Condos, New York, New York, 10001-2345, United States of America', 'Al Detalle'),
('Joaquín', 'Pulisic', '0703-1997-80177', '33568792', '567 Cedar Lane, Apt 5D, Lakeside Apartments, Chicago, Illinois, 60601-7890, United States of America', 'Mayorista'),
('Sara', 'Bravo', '0703-1992-01127', '98998643', '123 Maple Lane, Unit 5F, Hillside Heights, Miami, Florida, 33101-8901, United States of America', 'Al Detalle'),
('Carlos', 'Costa', '0702-1989-19434', '99999999', '890 Oak Lane, Apt 2C, Hillside Manor, San Francisco, California, 94102-5678, United States of America', 'Mayorista'),
('Lucía', 'Gómez', '0705-1995-21465', '88888888', '567 Elm Street, Unit 9A, Willowbrook Apartments, Los Angeles, California, 90001-1234, United States of America', 'Al Detalle'),
('Diego', 'Bale', '0704-1991-33256', '77777777', '123 Pine Drive, Suite 5D, Riverfront Condos, New York, New York, 10001-7890, United States of America', 'Mayorista'),
('Isabella', 'Sánchez', '0707-1998-44567', '66666666', '456 Cedar Lane, Apt 8B, Lakeside Apartments, Chicago, Illinois, 60601-2345, United States of America', 'Al Detalle'),
('Paul', 'Pogba', '0708-1997-55678', '55555555', '789 Maple Drive, Suite 12C, Hillside Heights, Miami, Florida, 33101-6789, United States of America', 'Al Detalle'),
('Marco', 'Reus', '0701-1991-66789', '44444444', '234 Elm Street, Apt 4F, Bayview Heights Apartments, San Francisco, California, 94102-9012, United States of America', 'Mayorista'),
('Daniela', 'Alves', '0706-1984-77890', '33333333', '567 Maple Avenue, Suite 2A, Riverfront Condos, New York, New York, 10001-3456, United States of America', 'Al Detalle'),
('Hugo', 'Lozano', '0709-1995-44903', '22222222', '890 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Mayorista'),
('Antonella', 'Griezmann', '0715-1991-95512', '11111111', '123 Cedar Lane, Unit 6B, Hillside Heights, Miami, Florida, 33101-2345, United States of America', 'Al Detalle'),
('Gabriel', 'Cavani', '0713-1987-04123', '00000000', '456 Elm Street, Apt 9D, Bayview Heights Apartments, San Francisco, California, 94102-9012, United States of America', 'Al Detalle'),
('David', 'Silva', '0706-1986-11234', '98765432', '789 Maple Drive, Suite 12C, Riverfront Condos, New York, New York, 10001-3456, United States of America', 'Mayorista'),
('Natalia', 'Jr.', '0701-1992-22845', '87654321', '234 Pine Drive, Apt 5A, Lakeside Apartments, Chicago, Illinois, 60601-5678, United States of America', 'Al Detalle'),
('Sergio', 'Ramos', '0708-1986-33456', '76543210', '567 Cedar Lane, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Mayorista'),
('Kevin', 'De Bruyne', '0707-1991-44567', '65432109', '890 Elm Avenue, Unit 8C, Willowbrook Manor, Los Angeles, California, 90001-6789, United States of America', 'Al Detalle'),
('Robert', 'Lewandowski', '0711-1988-55383', '54321098', '123 Maple Drive, Suite 10D, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Al Detalle'),
('Emma', 'Smith', '0719-2000-98743', '32165487', '789 Pine Lane, Apt 1B, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Al Detalle'),
('Valeria', 'Hernández', '0103-1998-82994', '98765432', '123 Elm Street, Apt 1A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Al Detalle'),
('Mateo', 'López', '0201-1993-94902', '87654321', '456 Oak Avenue, Unit 4B, Willowbrook Manor, Los Angeles, California, 90001-5678, United States of America', 'Mayorista'),
('Luciana', 'González', '0304-1995-01754', '76543210', '789 Pine Drive, Suite 9C, Riverfront Condos, New York, New York, 10001-2345, United States of America', 'Al Detalle'),
('Julián', 'Pérez', '0703-1999-24352', '65432109', '567 Cedar Lane, Apt 5D, Lakeside Apartments, Chicago, Illinois, 60601-7890, United States of America', 'Mayorista'),
('Valentina', 'García', '0705-2000-09122', '54321098', '890 Maple Avenue, Unit 8B, Hillside Heights, Miami, Florida, 33101-8901, United States of America', 'Al Detalle'),
('Daniel', 'Romero', '0702-2001-19962', '43210987', '123 Oak Lane, Apt 2C, Hillside Manor, San Francisco, California, 94102-5678, United States of America', 'Mayorista'),
('María', 'Ortega', '0705-2000-92013', '32109876', '456 Elm Street, Unit 9A, Willowbrook Apartments, Los Angeles, California, 90001-1234, United States of America', 'Al Detalle'),
('Alejandro', 'Herrera', '0707-1994-83822', '21098765', '789 Pine Drive, Suite 5D, Riverfront Condos, New York, New York, 10001-7890, United States of America', 'Mayorista'),
('Valentina', 'Gómez', '0708-1995-99210', '10987654', '234 Cedar Lane, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-2345, United States of America', 'Al Detalle'),
('Joaquín', 'Sánchez', '0709-1923-00921', '09876543', '567 Elm Street, Unit 6B, Hillside Heights, Miami, Florida, 33101-6789, United States of America', 'Mayorista');

INSERT INTO proveedores (empresaProveedora, rtn, telefono, correo, direccion, descripcion, nombreVendedor, telefonoVendedor) VALUES
('Dinant S de RL', '0701-1995-029922', '99993455', 'dinant@example.com', '304 Oak Street, Apt 6A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Se encarga de proveer tijeras y papel decorativo.', 'Dania Marcala', '98765431'),
('Extra S de RL', '0701-2001-028812', '98998643', 'extra@example.com', '789 Maple Drive, Suite 12C, Riverfront Condos, New York, New York, 10001-2345, United States of America', 'Se encarga de proveedor papel cebolla, papel crepé y otros más.', 'Julio Quezada', '88654210'),
('La Popular SA', '0701-1998-029932', '99568901', 'lapopular@example.com', '234 Cedar Lane, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-2345, United States of America', 'Se encarga de conseguir impresoras americanas, semi utilizadas y a buen precio', 'Francisco Paz', '99542109'),
('Diana S de RL', '0701-1999-025652', '88689012', 'diana@example.com', '123 Oak Lane, Apt 2C, Hillside Manor, San Francisco, California, 94102-5678, United States of America', 'Se encarga de brindarnos servicio de internet y cable, a su vez servicios de streaming.', 'Marcelo Miralda', '98432098'),
('Sula SA', '0701-2001-184923', '33789123', 'sula@example.com', '567 Cedar Lane, Apt 5D, Lakeside Apartments, Chicago, Illinois, 60601-7890, United States of America', 'Se encarga de vendernos las rosas y los girasoles', 'Juan Bautista', '33320987'),
('FruitCo', '0701-2003-123456', '55551234', ' fruitco@example.com', '123 Main Street, Anytown, USA', 'Provee frutas frescas y jugos naturales.', 'Maria Rodriguez', '55559876'),
('TechSupplies', '0701-2005-987654', '88889999', 'techsupplies@example.com', '456 Elm Street, Cityville, USA', 'Proveedor de equipos electrónicos y accesorios.', 'John Smith', '88887777'),
('SuperFoods', '0701-2007-246810', '77778888', 'superfoods@example.com', '789 Oak Avenue, Countryside, USA', 'Ofrece alimentos saludables y orgánicos.', 'Emily Johnson', '77776666'),
('Fashionista', '0701-2009-135792', '33334444', 'fashionista@example.com', '567 Pine Street, Fashion City, USA', 'Suministra ropa y accesorios de moda.', 'Alexandra Davis', '33335555'),
('GreenTech', '0701-2011-468135', '22221111', 'greentech@example.com', '321 Cedar Road, Greentown, USA', 'Proveedor de productos y servicios relacionados con energías renovables.', 'Robert Thompson', '22223333'),
('MegaPrint', '0701-2013-975310', '77779999', 'megaprint@example.com', '456 Oak Street, Printville, USA', 'Provee servicios de impresión y diseño gráfico.', 'Laura Martinez', '77778888'),
('Healthy Life', '0701-2015-753159', '55554444', 'healthylife@example.com', '123 Elm Avenue, Wellness City, USA', 'Proveedor de productos y servicios para un estilo de vida saludable.', 'Michael Johnson', '55553333'),
('AutoParts', '0701-2017-159753', '33332222', 'autoparts@example.com', '789 Maple Road, Car City, USA', 'Ofrece repuestos y accesorios para automóviles.', 'Jessica Adams', '33331111'),
('TechGadgets', '0701-2019-357159', '88889999', 'techgadgets@example.com', '321 Pine Avenue, Techville, USA', 'Proveedor de dispositivos electrónicos y gadgets.', 'Andrew Wilson', '88888888'),
('HomeDecor', '0701-2021-951357', '22223333', 'homedecor@example.com', '567 Oak Lane, Decor Town, USA', 'Suministra productos y accesorios de decoración para el hogar.', 'Sophia Taylor', '22224444'),
('Gourmet Delights', '0701-2023-246813', '44445555', ' gourmetdelights@example.com', '789 Pine Street, Foodville, USA', 'Proveedor de productos gourmet y delicatessen.', 'Sophie Anderson', '44446666'),
('SportsZone', '0701-2025-579135', '66667777', 'sportszone@example.com', '321 Elm Road, Sportstown, USA', 'Ofrece equipos y artículos deportivos.', 'Daniel Brown', '66668888'),
('GreenThumb', '0701-2027-791357', '22224444', 'greenthumb@example.com', '567 Maple Avenue, Gardenville, USA', 'Proveedor de plantas, flores y suministros de jardinería.', 'Olivia Johnson', '22225555'),
('Bookworm', '0701-2029-913579', '88889999', 'bookworm@example.com', '123 Cedar Road, Bookville, USA', 'Suministra libros, revistas y materiales de lectura.', 'Ethan Davis', '88882222'),
('Office Solutions', '0701-2031-135791', '33334444', 'officesolutions@example.com', '456 Oak Lane, Business City, USA', 'Proveedor de suministros de oficina y mobiliario.', 'Isabella Wilson', '33335555'),
('TechGear', '0701-2043-357911', '77776666', ' techgear@example.com ', '789 Cedar Road, Techville, USA', 'Proveedor de equipos electrónicos y gadgets tecnológicos.', 'Alexandre Dupont', '77775555'),
('Fresh Farms', '0701-2045-579113', '44443333', 'freshfarms@example.com', '321 Oak Lane, Farmville, USA', 'Ofrece productos frescos de granja y productos lácteos.', 'Sophie Martin', '44442222'),
('Stationery World', '0701-2047-791135', '11112222', 'stationeryworld@example.com', '567 Elm Avenue, Paper Town, USA', 'Proveedor de suministros de papelería y material de oficina.', 'Michael Johnson', '11113333'),
('Home Appliances', '0701-2049-913357', '88884444', 'homeappliances@example.com', '123 Maple Road, Homeville, USA', 'Suministra electrodomésticos y productos para el hogar.', 'Emily Thompson', '88885555'),
('Outdoor Adventures', '0701-2051-135799', '55556666', 'outdooradventures@example.com', '456 Pine Street, Adventure City, USA', 'Proveedor de equipos y accesorios para actividades al aire libre.', 'Daniel Wilson', '55557777'),
('Tech Solutions', '0701-2033-357911', '66665555', ' techsolutions@example.com ', '789 Elm Avenue, Tech City, USA', 'Proveedor de soluciones tecnológicas y servicios de IT.', 'David Thompson', '66664444'),
('Fashion Trends', '0701-2035-579113', '22221111', ' fashiontrends@example.com ', '321 Pine Road, Fashion City, USA', 'Ofrece ropa y accesorios de moda para hombres y mujeres.', 'Emma Davis', '22223333'),
('Healthy Eats', '0701-2037-791135', '88888888', ' healthyeats@example.com', '567 Oak Avenue, Wellness Town, USA', 'Proveedor de alimentos saludables y productos orgánicos.', 'James Smith', '88887777'),
('Artistic Crafts', '0701-2039-913357', '33336666', 'artisticcrafts@example.com', '123 Elm Road, Creativity City, USA', 'Suministra materiales y herramientas para manualidades y artesanías.', 'Sophia Wilson', '33337777'),
('Green Living', '0701-2041-135799', '99998888', 'greenliving@example.com', '456 Maple Lane, Ecoville, USA', 'Proveedor de productos ecológicos y sostenibles para el hogar.', 'Benjamin Johnson', '99997777'),
('Beauty Supplies', '0701-2053-357911', '22223333', ' beautysupplies@example.com ', '789 Cedar Lane, Beautyville, USA', 'Proveedor de productos de belleza y cuidado personal.', 'Isabella Johnson', '22224444'),
('Pet Paradise', '0701-2055-579113', '66667777', ' petparadise@example.com', '321 Oak Avenue, Pet City, USA', 'Ofrece productos y servicios para mascotas.', 'Sophia Brown', '66668888'),
('Gourmet Coffee', '0701-2057-791135', '33334444', 'gourmetcoffee@example.com', '567 Elm Road, Coffeeville, USA', 'Proveedor de café gourmet y accesorios para café.', 'Oliver Smith', '33335555'),
('Sports Apparel', '0701-2059-913357', '99990000', 'sportsapparel@example.com', '123 Maple Avenue, Sportstown, USA', 'Suministra ropa y accesorios deportivos.', 'Emma Wilson', '99991111'),
('Green Construction', '0701-2061-135799', '77778888', 'greenconstruction@example.com', '456 Pine Road, Eco City, USA', 'Proveedor de materiales y servicios para construcción ecológica.', 'Noah Davis', '77779999'),
('TechAccessories', '0701-2063-357911', '44445555', ' techaccessories@example.com ', '789 Cedar Road, Techville, USA', 'Proveedor de accesorios y complementos para dispositivos electrónicos.', 'Sophie Dupont', '44446666'),
('Organic Grocers', '0701-2065-579113', '22221111', ' organicgrocers@example.com', '321 Oak Lane, Organicville, USA', 'Ofrece alimentos orgánicos y productos naturales.', 'Alex Martin', '22223333'),
('Office Furniture', '0701-2067-791135', '88889999', 'officefurniture@example.com', '567 Elm Avenue, Furnish Town, USA', 'Proveedor de mobiliario y accesorios de oficina.', 'Daniel Thompson', '88888888'),
('Art Supplies', '0701-2069-913357', '33332222', 'artsupplies@example.com', '123 Maple Road, Artville, USA', 'Suministra materiales y herramientas para artistas y manualidades.', 'Olivia Wilson', '33331111'),
('Green Energy Solutions', '0701-2071-135799', '99998888', 'greenenergy@example.com', '456 Pine Street, Energyville, USA', 'Proveedor de soluciones de energía renovable y eficiencia energética.', 'Benjamin Johnson', '99997777'),
('Baby Essentials', '0701-2073-357911', '77776666', ' babyessentials@example.com', '789 Cedar Lane, Babyville, USA', 'Proveedor de productos esenciales para bebés y artículos de cuidado infantil.', 'Emma Dupont', '77775555'),
('Home Electronics', '0701-2075-579113', '22224444', 'homeelectronics@example.com', '321 Oak Avenue, Techville, USA', 'Ofrece dispositivos electrónicos para el hogar y sistemas de entretenimiento.', 'Sophia Martin', '22225555'),
('Office Stationery', '0701-2077-791135', '88884444', 'officestationery@example.com', '567 Elm Road, Office City, USA', 'Proveedor de suministros de papelería y material de oficina.', 'Michael Johnson', '88885555'),
('Home Decorations', '0701-2079-913357', '33336666', 'homedecorations@example.com', '123 Maple Lane, Decorville, USA', 'Suministra productos y accesorios decorativos para el hogar.', 'Sophia Wilson', '33337777'),
('Outdoor Gear', '0701-2081-135799', '99990000', 'outdoorgear@example.com', '456 Pine Street, Adventure City, USA', 'Proveedor de equipos y accesorios para actividades al aire libre.', 'Daniel Wilson', '99991111'),
('Tech Gadgets', '0701-2083-357911', '44443333', ' techgadgets@example.com ', '789 Cedar Road, Techville, USA', 'Proveedor de gadgets y dispositivos tecnológicos de última generación.', 'Sophie Dupont', '44442222'),
('Organic Market', '0701-2085-579113', '22226666', ' organicmarket@example.com ', '321 Oak Lane, Organicville, USA', 'Ofrece una amplia variedad de alimentos orgánicos y productos naturales.', 'Alex Martin', '22227777'),
('Home Office Solutions', '0701-2087-791135', '88883333', 'homeofficesolutions@example.com', '567 Elm Avenue, Furnish Town, USA', 'Proveedor de soluciones para oficinas en el hogar, mobiliario y accesorios.', 'Daniel Thompson', '88884444'),
('Art Gallery Supplies', '0701-2089-913357', '33334444', 'artgallerysupplies@example.com', '123 Maple Road, Artville, USA', 'Suministra materiales y herramientas para galerías de arte y artistas.', 'Olivia Wilson', '33335555'),
('Renewable Energy Solutions', '0701-2091-135799', '99993333', 'renewableenergy@example.com', '456 Pine Street, Energyville, USA', 'Proveedor de soluciones de energía renovable y sostenible para hogares y empresas.', 'Benjamin Johnson', '99994444'),
('Pet Accessories', '0701-2093-357911', '77776666', 'petaccessories@example.com', '789 Cedar Lane, Petville, USA', 'Proveedor de accesorios y productos para mascotas.', 'Emma Dupont', '77777777'),
('Kitchen Essentials', '0701-2095-579113', '22224444', 'kitchenessentials@example.com', '321 Oak Avenue, Culinary City, USA', 'Ofrece utensilios y accesorios de cocina de alta calidad.', 'Sophia Martin', '22223333'),
('Print Solutions', '0701-2097-791135', '88884444', 'printsolutions@example.com', '567 Elm Road, Printville, USA', 'Proveedor de servicios de impresión y diseño gráfico.', 'Michael Johnson', '88883333'),
('Home Fragrances', '0701-2099-913357', '33332222', 'homefragrances@example.com', '123 Maple Lane, Fragrance City, USA', 'Suministra velas y productos de fragancia para el hogar.', 'Sophia Wilson', '33331111'),
('Camping Gear', '0701-2101-135799', '99990000', 'campinggear@example.com', '456 Pine Street, Outdoorville, USA', 'Proveedor de equipos y accesorios para acampar y actividades al aire libre.', 'Daniel Wilson', '99998888'),
('Fashion Boutique', '0701-2103-357911', '44445555', 'fashionboutique@example.com', '789 Cedar Road, Fashion City, USA', 'Ofrece ropa y accesorios de moda exclusivos.', 'Sophie Dupont', '44446666'),
('Natural Beauty', '0701-2105-579113', '22221111', 'naturalbeauty@example.com', '321 Oak Lane, Beautyville, USA', 'Proveedor de productos naturales y orgánicos para el cuidado de la piel y belleza.', 'Alex Martin', '22222222'),
('Office Technology', '0701-2107-791135', '88889999', 'officetechnology@example.com', '567 Elm Avenue, Techville, USA', 'Proveedor de tecnología y equipos para oficinas.', 'Daniel Thompson', '88888888'),
('Craft Materials', '0701-2109-913357', '33330000', 'craftmaterials@example.com', '123 Maple Road, Craftville, USA', 'Suministra materiales y herramientas para manualidades y proyectos creativos.', 'Olivia Wilson', '33331111'),
('Green Home Solutions', '0701-2111-135799', '99998888', 'greenhomesolutions@example.com', '456 Pine Street, Eco City, USA', 'Proveedor de soluciones ecológicas para el hogar, incluyendo productos de limpieza y energía renovable.', 'Benjamin Johnson', '99999999'),
('Electronics Emporium', '0701-2113-357911', '44446666', ' electronics@example.com ', '789 Cedar Road, Techville, USA', 'Proveedor de productos electrónicos y dispositivos de última generación.', 'Sophie Dupont', '44447777'),
('Fresh Seafood', '0701-2115-579113', '22224444', ' seafood@example.com ', '321 Oak Lane, Seafood City, USA', 'Ofrece una variedad de mariscos frescos y pescado.', 'Alex Martin', '22225555'),
('Office Storage Solutions', '0701-2117-791135', '88880000', ' officestorage@example.com', '567 Elm Avenue, Organize Town, USA', 'Proveedor de soluciones de almacenamiento y mobiliario para oficinas.', 'Daniel Thompson', '88881111'),
('Art Prints', '0701-2119-913357', '33334444', 'artprints@example.com', '123 Maple Road, Artville, USA', 'Suministra impresiones artísticas y obras de arte.', 'Olivia Wilson', '33335555'),
('Green Building Materials', '0701-2121-135799', '99992222', 'greenbuilding@example.com', '456 Pine Street, Sustainable City, USA', 'Proveedor de materiales de construcción ecológicos y sostenibles.', 'Benjamin Johnson', '99993333'),
('Digital Solutions', '0701-2123-357911', '77776666', ' digitalsolutions@example.com ', '789 Cedar Lane, Techville, USA', 'Proveedor de soluciones digitales y servicios de desarrollo web.', 'Sophie Dupont', '77775555'),
('Organic Beauty', '0701-2125-579113', '22223333', ' organicbeauty@example.com ', '321 Oak Lane, Beautyville, USA', 'Ofrece productos de belleza orgánicos y naturales.', 'Alex Martin', '22222222'),
('Office Decor', '0701-2127-791135', '88889999', 'officedecor@example.com', '567 Elm Avenue, Furnish Town, USA', 'Proveedor de decoración y mobiliario de oficina.', 'Daniel Thompson', '88888888'),
('Artisan Crafts', '0701-2129-913357', '33330000', 'artisancrafts@example.com', '123 Maple Road, Artville, USA', 'Suministra artesanías y productos hechos a mano.', 'Olivia Wilson', '33331111'),
('Solar Energy Solutions', '0701-2131-135799', '99999900', 'solarenergy@example.com', '456 Pine Street, Renewable City, USA', 'Proveedor de soluciones de energía solar y paneles solares.', 'Benjamin Johnson', '99998888'),
('Pet Grooming', '0701-2133-357911', '44445555', 'petgrooming@example.com', '789 Cedar Road, Petville, USA', 'Ofrece servicios de aseo y cuidado para mascotas.', 'Emma Dupont', '44446666'),
('Bath and Body Products', '0701-2135-579113', '22226666', 'bathbodyproducts@example.com', '321 Oak Avenue, Bodyville, USA', 'Proveedor de productos para baño y cuidado corporal.', 'Sophia Martin', '22227777'),
('Promotional Items', '0701-2137-791135', '88883333', 'promotionalitems@example.com', '567 Elm Road, Promo City, USA', 'Proveedor de artículos promocionales y regalos corporativos.', 'Michael Johnson', '88884444'),
('Home Textiles', '0701-2139-913357', '33332222', 'hometextiles@example.com', '123 Maple Lane, Textileville, USA', 'Suministra productos textiles para el hogar, como cortinas y ropa de cama.', 'Sophia Wilson', '33333333'),
('Outdoor Clothing', '0701-2141-135799', '99992222', 'outdoorclothing@example.com', '456 Pine Street, Adventure City, USA', 'Proveedor de ropa y equipos para actividades al aire libre.', 'Daniel Wilson', '99991111'),
('Jewelry Boutique', '0701-2143-357911', '77776666', 'jewelryboutique@example.com', '789 Cedar Lane, Jewelry City, USA', 'Ofrece joyas y accesorios de moda.', 'Sophie Dupont', '77777777'),
('Herbal Remedies', '0701-2145-579113', '22224444', 'herbalremedies@example.com', '321 Oak Lane, Herbville, USA', 'Proveedor de productos a base de hierbas y remedios naturales.', 'Alex Martin', '22223333'),
('Office Software', '0701-2147-791135', '88889999', 'officesoftware@example.com', '567 Elm Avenue, Techville, USA', 'Proveedor de software y soluciones informáticas para oficinas.', 'Daniel Thompson', '88888888'),
('Knitting Supplies', '0701-2149-913357', '33334444', 'knittingsupplies@example.com', '123 Maple Road, Craftville, USA', 'Suministra materiales y herramientas para tejer y hacer punto.', 'Olivia Wilson', '33335555'),
('Green Roofing', '0701-2151-135799', '99991111', 'greenroofing@example.com', '456 Pine Street, Eco City, USA', 'Proveedor de materiales y servicios para techos verdes y ecológicos.', 'Benjamin Johnson', '99992222'),
('Digital Marketing Solutions', '0701-2153-357911', '44448888', ' digitalmarketing@example.com ', '789 Cedar Road, Techville, USA', 'Proveedor de soluciones de marketing digital y estrategias de promoción en línea.', 'Sophie Dupont', '44449999'),
('Organic Juices', '0701-2155-579113', '22229999', ' organicjuices@example.com', '321 Oak Lane, Healthville, USA', 'Ofrece jugos naturales y orgánicos.', 'Alex Martin', '22228888'),
('Office Supplies', '0701-2157-791135', '88886666', 'officesupplies@example.com', '567 Elm Avenue, Stationery City, USA', 'Proveedor de suministros de oficina y material de papelería.', 'Daniel Thompson', '88887777'),
('Fine Art Prints', '0701-2159-913357', '33333300', 'fineartprints@example.com', '123 Maple Road, Artville, USA', 'Suministra impresiones de arte de alta calidad y obras de arte.', 'Olivia Wilson', '33334444'),
('Smart Home Solutions', '0701-2161-135799', '99998877', 'smarthomesolutions@example.com', '456 Pine Street, Smart City, USA', 'Proveedor de soluciones para hogares inteligentes y automatización residencial.', 'Benjamin Johnson', '99999988'),
('IT Consulting', '0701-2163-357911', '77773333', ' itconsulting@example.com ', '789 Cedar Lane, Techville, USA', 'Proveedor de servicios de consultoría en tecnología de la información.', 'Sophie Dupont', '77772222'),
('Natural Supplements', '0701-2165-579113', '22227777', ' naturalsupplements@example.com ', '321 Oak Lane, Healthville, USA', 'Ofrece suplementos naturales y productos para la salud.', 'Alex Martin', '22226666'),
('Office Equipment', '0701-2167-791135', '88885555', 'officeequipment@example.com', '567 Elm Avenue, Equipment City, USA', 'Proveedor de equipos de oficina y maquinaria.', 'Daniel Thompson', '88884444'),
('Photography Supplies', '0701-2169-913357', '33332222', 'photographysupplies@example.com', '123 Maple Road, Photoville, USA', 'Suministra equipos y accesorios para fotografía.', 'Olivia Wilson', '33331111'),
('Renewable Energy Installations', '0701-2171-135799', '99999911', 'renewableinstallations@example.com', '456 Pine Street, Energyville, USA', 'Proveedor de instalaciones de energía renovable y sistemas solares.', 'Benjamin Johnson', '99998822'),
('Pet Training', '0701-2173-357911', '44446666', 'pettraining@example.com', '789 Cedar Road, Petville, USA', 'Ofrece servicios de entrenamiento y adiestramiento para mascotas.', 'Emma Dupont', '44445555'),
('Home Improvement', '0701-2175-579113', '22224444', 'homeimprovement@example.com', '321 Oak Lane, Improvement City, USA', 'Proveedor de productos y servicios para mejoras del hogar.', 'Sophia Martin', '22223333'),
('Event Planning', '0701-2177-791135', '88889999', 'eventplanning@example.com', '567 Elm Avenue, Event City, USA', 'Proveedor de servicios de planificación y organización de eventos.', 'Daniel Thompson', '88888888'),
('Home Appliances Repair', '0701-2179-913357', '33334444', 'appliancesrepair@example.com', '123 Maple Road, Repairville, USA', 'Ofrece servicios de reparación de electrodomésticos para el hogar.', 'Sophia Wilson', '33335555'),
('Cycling Gear', '0701-2181-135799', '99991111', 'cyclinggear@example.com', '456 Pine Street, Bike City, USA', 'Proveedor de equipos y accesorios para ciclismo.', 'Daniel Wilson', '99992222'),
('Vintage Clothing', '0701-2183-357911', '77776666', 'vintageclothing@example.com', '789 Cedar Lane, Vintage City, USA', 'Ofrece ropa y accesorios de moda vintage.', 'Sophie Dupont', '77777777'),
('Natural Home Cleaning', '0701-2185-579113', '22225555', 'naturalcleaning@example.com', '321 Oak Lane, Eco City, USA', 'Proveedor de productos de limpieza para el hogar naturales y ecológicos.', 'Alex Martin', '22224444'),
('Office Training Services', '0701-2187-791135', '88886666', 'officetraining@example.com', '567 Elm Avenue, Training City, USA', 'Proveedor de servicios de capacitación y formación para oficinas.', 'Daniel Thompson', '88887777'),
('Fabric and Textiles', '0701-2189-913357', '33330000', 'fabrictextiles@example.com', '123 Maple Road, Textileville, USA', 'Suministra telas y materiales textiles para la confección.', 'Olivia Wilson', '33331111'),
('Green Transportation', '0701-2191-135799', '99992222', 'greentransportation@example.com', '456 Pine Street, Eco City, USA', 'Proveedor de soluciones de transporte ecológico y sostenible.', 'Benjamin Johnson', '99993333'),
('Web Development Services', '0701-2193-357911', '44448888', ' webdevelopment@example.com ', '789 Cedar Road, Techville, USA', 'Proveedor de servicios de desarrollo web y diseño de sitios.', 'Sophie Dupont', '44449999'),
('Natural Skincare', '0701-2195-579113', '22229999', 'naturalskincare@example.com', '321 Oak Lane, Beautyville, USA', 'Ofrece productos naturales para el cuidado de la piel.', 'Alex Martin', '22228888'),
('Office Security Solutions', '0701-2197-791135', '88885555', 'officesecurity@example.com', '567 Elm Avenue, Security City, USA', 'Proveedor de soluciones de seguridad y sistemas de vigilancia para oficinas.', 'Daniel Thompson', '88884444'),
('Art Education', '0701-2199-913357', '33332222', 'arteducation@example.com', '123 Maple Road, Artville, USA', 'Ofrece programas y clases de educación artística.', 'Olivia Wilson', '33331111'),
('Energy Storage Systems', '0701-2201-135799', '99999911', 'energystorage@example.com', '456 Pine Street, Energyville, USA', 'Proveedor de sistemas de almacenamiento de energía y baterías.', 'Benjamin Johnson', '99998822'),
('Pet Boarding', '0701-2203-357911', '44446666', 'petboarding@example.com', '789 Cedar Road, Petville, USA', 'Ofrece servicios de alojamiento y cuidado para mascotas.', 'Emma Dupont', '44445555'),
('Home Interior Design', '0701-2205-579113', '22224444', 'homedesign@example.com', '321 Oak Lane, Design City, USA', 'Proveedor de servicios de diseño de interiores para el hogar.', 'Sophia Martin', '22223333'),
('Event Catering', '0701-2207-791135', '88889999', 'eventcatering@example.com', '567 Elm Avenue, Catering City, USA', 'Proveedor de servicios de catering para eventos y celebraciones.', 'Daniel Thompson', '88888888'),
('Appliance Installation', '0701-2209-913357', '33334444', 'applianceinstallation@example.com', '123 Maple Road, Installville, USA', 'Ofrece servicios de instalación de electrodomésticos para el hogar.', 'Sophia Wilson', '33335555'),
('Hiking and Camping Supplies', '0701-2211-135799', '99991111', 'hikingcamping@example.com', '456 Pine Street, Adventure City, USA', 'Proveedor de equipos y suministros para senderismo y camping.', 'Daniel Wilson', '99992222');

INSERT INTO floristeria (imagen, nombre, cantidad, precio, proveedor_id) VALUES
('imagen1.jpg', 'Rosa Roja', 10, 50.00, 1),
('imagen2.jpg', 'Tulipan Amarillo', 0, 35.50, 2),
('imagen3.jpg', 'Orquidea Blanca', 10, 75.20, 3),
('imagen4.jpg', 'Girasol', 10, 20.00, 4),
('imagen5.jpg', 'Lirio Rosa', 0, 45.80, 5),
('imagen6.jpg', 'Clavel Rojo', 18, 9.90, 6),
('imagen7.jpg', 'Margarita Blanca', 15, 10.50, 7),
('imagen8.jpg', 'Jazmin', 30, 19.00, 8),
('imagen9.jpg', 'Lavanda', 25, 15.75, 9),
('imagen10.jpg', 'Azucena', 42, 32.30, 10),
('imagen11.jpg', 'Loto', 55, 25.00, 2),
('imagen12.jpg', 'Camelia', 38, 18.20, 3),
('imagen13.jpg', 'Dalia', 27, 17.80, 1),
('imagen14.jpg', 'Petunia', 12, 12.50, 2),
('imagen15.jpg', 'Hortensia', 65, 30.40, 3),
('imagen16.jpg', 'Geranio', 19, 6.60, 1),
('imagen17.jpg', 'Begonia', 33, 15.75, 2),
('imagen18.jpg', 'Caléndula', 22, 12.80, 3),
('imagen19.jpg', 'Amapola', 47, 23.30, 1),
('imagen20.jpg', 'Crisantemo', 60, 40.00, 2),
('imagen1.jpg', 'Anémona', 40, 20.20, 3),
('imagen3.jpg', 'Diente de León', 28, 12.80, 1),
('imagen6.jpg', 'Magnolia', 14, 7.50, 2),
('imagen9.jpg', 'Narciso', 70, 40.25, 3),
('imagen12.jpg', 'Pensamiento', 17, 9.30, 1),
('imagen15.jpg', 'Peonia', 52, 30.00, 2),
('imagen18.jpg', 'Jacinto', 36, 20.20, 3),
('imagen2.jpg', 'Hierba de San Juan', 24, 14.80, 1),
('imagen4.jpg', 'Iris', 10, 8.50, 2),
('imagen6.jpg', 'Azalea', 80, 28.40, 3),
('imagen8.jpg', 'Verbena', 21, 9.60, 1),
('imagen10.jpg', 'Capuchina', 46, 21.75, 2),
('imagen12.jpg', 'Crisipo', 31, 14.80, 3),
('imagen14.jpg', 'Dama de Noche', 38, 18.30, 1),
('imagen16.jpg', 'Aster', 53, 30.00, 2),
('imagen18.jpg', 'Amapola de California', 42, 20.20, 3),
('imagen11.jpg', 'Dedalera', 29, 15.80, 1),
('imagen12.jpg', 'Jacaranda', 15, 8.50, 2),
('imagen13.jpg', 'Aster', 30, 10.00, 1),
('imagen14.jpg', 'Azalea', 25, 15.50, 2),
('imagen15.jpg', 'Begonia', 40, 20.20, 3),
('imagen16.jpg', 'Crisantemo', 35, 14.00, 4),
('imagen17.jpg', 'Dalia', 55, 25.80, 5),
('imagen18.jpg', 'Eustoma', 18, 7.90, 6),
('imagen19.jpg', 'Fresia', 15, 8.50, 7),
('imagen20.jpg', 'Geranio', 20, 10.00, 8),
('imagen1.jpg', 'Hortensia', 45, 25.75, 9),
('imagen2.jpg', 'Iris', 32, 12.30, 10),
('imagen3.jpg', 'Jacinto', 50, 20.00, 11),
('imagen4.jpg', 'Lavanda', 28, 14.20, 12),
('imagen5.jpg', 'Margarita', 17, 8.80, 13),
('imagen6.jpg', 'Narciso', 14, 6.50, 14),
('imagen7.jpg', 'Orquídea', 38, 18.50, 15),
('imagen8.jpg', 'Petunia', 23, 11.60, 16),
('imagen9.jpg', 'Rosa', 37, 21.75, 17),
('imagen10.jpg', 'Tulipan', 42, 24.80, 18),
('imagen11.jpg', 'Violeta', 27, 17.30, 19),
('imagen12.jpg', 'Zinnia', 33, 21.50, 20),
('imagen13.jpg', 'Lirio', 48, 25.20, 21),
('imagen14.jpg', 'Clavel', 22, 8.80, 22),
('imagen6.jpg', 'Clavel Verde', 18, 8.90, 6),
('imagen7.jpg', 'Margarita Negra', 15, 9.50, 7),
('imagen8.jpg', 'Jazmín Horizabal', 30, 15.20, 8),
('imagen9.jpg', 'Lavanda Enriquecido', 25, 12.75, 9),
('imagen15.jpg', 'Azucena Amarilla', 42, 25.30, 10),
('imagen16.jpg', 'Jacinto', 50, 22.00, 11),
('imagen17.jpg', 'Lavanda', 28, 11.20, 12),
('imagen18.jpg', 'Margarita', 17, 6.80, 13),
('imagen19.jpg', 'Narciso', 14, 5.50, 14),
('imagen20.jpg', 'Orquídea', 38, 16.50, 15);

INSERT INTO Empleados (Identidad, Nombres, Apellidos, Genero, Edad, Correo, Telefono, nombreContactoDeEmergencia, ContactoDeEmergencia, Direccion, TipoDeEmpleado) VALUES
('0701-1982-10822', 'Carolina', 'Lanza', 'Femenino', '25', 'carolinalanza@example.com', '99828877', 'Julian Alvarez', '98643569', '789 Maple Drive, Suite 12C, Riverfront Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0703-1989-02992', 'Milagro', 'Flores', 'Femenino', '30', 'milagroflores@example.com', '95735422', 'Lautaro Martinez', '98998643', '890 Elm Avenue, Unit 8C, Willowbrook Manor, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0801-1990-02948', 'Alan', 'Paul', 'Masculino', '35', 'alanpaul@example.com', '33567890', 'Leonel Messi', '95768969', '123 Maple Drive, Suite 10D, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0719-1994-01099', 'Jennifer', 'Aplicano', 'Femenino', '40', 'jenniferaplicano@example.com', '88792617', 'Enzo Fernandez', '99095679', '567 Cedar Lane, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0904-2000-00383', 'Eduardo', 'Maldonado', 'Masculino', '45', 'eduardomaldonado@example.com', '96288675', 'Nauel Molina', '99993455', '380 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Permanente'),
('1801-1995-20456', 'Alejandra', 'Rubio', 'Femenino', '28', 'alejandrarubio@example.com', '99123456', 'Maria Sanchez', '98765432', '567 Elm Street, Unit 9A, Willowbrook Apartments, Los Angeles, California, 90001-1234, United States of America', 'Permanente'),
('0708-1990-11111', 'Juan', 'Perez', 'Masculino', '33', 'juanperez@example.com', '99907766', 'Pedro Martinez', '98765432', '123 Oak Street, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0703-1995-22222', 'Maria', 'Lopez', 'Femenino', '28', 'marialopez@example.com', '95744392', 'Luisa Gomez', '98989898', '456 Pine Avenue, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0803-1988-33333', 'Pedro', 'Garcia', 'Masculino', '45', 'pedrogarcia@example.com', '33567799', 'Roberto Rodriguez', '95748392', '789 Elm Street, Suite 9C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Permanente'),
('0715-1993-44444', 'Laura', 'Gonzalez', 'Femenino', '32', 'lauragonzalez@example.com', '80775533', 'Andres Fernandez', '99001122', '567 Maple Drive, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0719-1994-55555', 'Carlos', 'Gomez', 'Masculino', '37', 'carlosgomez@example.com', '99662388', 'Mario Rodriguez', '98989898', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0718-1998-66666', 'Ana', 'Hernandez', 'Femenino', '24', 'anahernandez@example.com', '95514433', 'Luisa Ramirez', '98887777', '567 Pine Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0802-1990-77777', 'Jorge', 'Lopez', 'Masculino', '41', 'jorgelopez@example.com', '33221114', 'Roberto Martinez', '95551122', '789 Elm Drive, Suite 11C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Permanente'),
('0715-1995-88888', 'Patricia', 'Torres', 'Femenino', '29', 'patriciatorres@example.com', '81663311', 'Andrea Fernandez', '99002233', '567 Maple Avenue, Unit 5B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0712-1991-99999', 'Daniel', 'Castillo', 'Masculino', '31', 'danielcastillo@example.com', '96573185', 'Raul Molina', '99998888', '380 Pine Street, Apt 8C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Temporal'),
('0803-1995-11111', 'Luis', 'Gonzalez', 'Masculino', '28', 'luisgonzalez@example.com', '99776645', 'Ana Martinez', '98887766', '123 Oak Avenue, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0805-1992-22222', 'Marcela', 'Herrera', 'Femenino', '33', 'marcelaherrera@example.com', '95594466', 'Pedro Rodriguez', '98887722', '456 Pine Street, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0802-1994-33333', 'Roberto', 'Diaz', 'Masculino', '31', 'robertodiaz@example.com', '33669998', 'Laura Ramirez', '95554466', '789 Elm Drive, Suite 11C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0801-1991-44444', 'Karla', 'Rojas', 'Femenino', '27', 'karlarojas@example.com', '88774425', 'Andres Fernandez', '99002211', '567 Maple Avenue, Unit 5B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0806-1993-55555', 'Manuel', 'Santos', 'Masculino', '29', 'manuelsantos@example.com', '92665544', 'Raul Molina', '99997766', '380 Pine Street, Apt 8C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Temporal'),
('0809-1997-66666', 'Camila', 'Lopez', 'Femenino', '24', 'camilalopez@example.com', '99552433', 'Daniel Martinez', '98886655', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0807-1990-77777', 'Fernando', 'Ramirez', 'Masculino', '34', 'fernandoramirez@example.com', '95283344', 'María Fernandez', '98887744', '567 Pine Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Temporal'),
('0804-1992-88888', 'Laura', 'Perez', 'Femenino', '30', 'lauraperez@example.com', '33778855', 'Luis Rodriguez', '95554433', '789 Elm Drive, Suite 11C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Permanente'),
('0806-1995-99999', 'Diego', 'Garcia', 'Masculino', '32', 'diegogarcia@example.com', '88552266', 'Ana Martinez', '99001122', '567 Maple Avenue, Unit 5B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Permanente'),
('0809-1991-10101', 'Sofia', 'Torres', 'Femenino', '26', 'sofiatorres@example.com', '96661122', 'Andres Fernandez', '99997766', '380 Pine Street, Apt 8C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Permanente'),
('0810-1993-12121', 'Ricardo', 'Mendoza', 'Masculino', '30', 'ricardomendoza@example.com', '99551122', 'Raul Molina', '98887744', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0808-1992-14141', 'Valeria', 'Silva', 'Femenino', '29', 'valeriasilva@example.com', '95224433', 'Daniel Martinez', '99001122', '567 Pine Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0805-1994-16161', 'Luisa', 'Flores', 'Femenino', '27', 'luisaflores@example.com', '33668899', 'María Fernandez', '98886655', '789 Elm Drive, Suite 11C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Permanente'),
('0703-1997-01588', 'Pedro', 'Martinez', 'Masculino', '33', 'pedromartinez@example.com', '95724342', 'Luisa Rodriguez', '98887766', '123 Oak Avenue, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0815-1992-18181', 'Carla', 'Gonzalez', 'Femenino', '28', 'carlagonzalez@example.com', '95558877', 'Pedro Ramirez', '98889999', '456 Pine Street, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0810-1994-19191', 'Roberto', 'Santos', 'Masculino', '29', 'robertosantos@example.com', '33669977', 'Laura Hernandez', '95558877', '789 Elm Drive, Suite 11C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Permanente'),
('0812-1991-20202', 'Laura', 'Mendoza', 'Femenino', '30', 'lauramendoza@example.com', '88774499', 'Andres Ramirez', '99001133', '567 Maple Avenue, Unit 5B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Permanente'),
('0805-1996-00101', 'Andrea', 'González', 'Femenino', '25', 'andreagonzalez@example.com', '99887766', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0806-1993-00202', 'Sergio', 'Hernández', 'Masculino', '28', 'sergiohernandez@example.com', '99776655', 'Javier Rodríguez', '98654321', '456 Pine Avenue, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0807-1990-00303', 'Daniela', 'Torres', 'Femenino', '31', 'danielatorres@example.com', '99665544', 'Luisa Ramírez', '95543210', '789 Elm Street, Suite 9C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0808-1995-00404', 'Jorge', 'Gómez', 'Masculino', '26', 'jorgegomez@example.com', '99554033', 'Mario Fernández', '94432211', '567 Maple Drive, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0809-1992-00505', 'María', 'Sánchez', 'Femenino', '29', 'mariasanchez@example.com', '99443322', 'Pedro Martínez', '93321100', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0810-1989-00606', 'Alejandro', 'Herrera', 'Masculino', '32', 'alejandroherrera@example.com', '99332211', 'Roberto Rodríguez', '92210033', '890 Cedar Lane, Apt 6A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Temporal'),
('0811-1991-00707', 'Camila', 'Pérez', 'Femenino', '27', 'camilaperez@example.com', '99221100', 'Andrés Fernández', '91100332', '380 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Temporal'),
('0812-1994-00808', 'Roberto', 'López', 'Masculino', '26', 'robertolopez@example.com', '99110099', 'Julia Martínez', '90099887', '789 Cedar Road, Apt 12C, Cedar Heights, Chicago, Illinois, 60601-2345, United States of America', 'Permanente'),
('0813-1993-00909', 'Laura', 'Gutiérrez', 'Femenino', '28', 'lauragutierrez@example.com', '99019988', 'Carlos Rodríguez', '89998877', '567 Elm Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0814-1990-01010', 'Juan', 'Martínez', 'Masculino', '31', 'juanmartinez@example.com', '99998277', 'Ana Hernández', '88887766', '123 Maple Drive, Suite 6B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0815-1996-01111', 'Luis', 'Ramírez', 'Masculino', '26', 'luisramirez@example.com', '99887736', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0816-1993-02222', 'Valentina', 'Hernández', 'Femenino', '28', 'valentinahernandez@example.com', '99746655', 'Javier Rodríguez', '98654321', '456 Pine Avenue, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0817-1990-03333', 'Andrés', 'Torres', 'Masculino', '31', 'andrestorres@example.com', '99665254', 'Luisa Ramírez', '95543210', '789 Elm Street, Suite 9C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0818-1995-04444', 'Sofía', 'Gómez', 'Femenino', '26', 'sofiagomez@example.com', '99554436', 'Mario Fernández', '94432211', '567 Maple Drive, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0819-1992-05555', 'Mateo', 'Sánchez', 'Masculino', '29', 'mateosanchez@example.com', '99743322', 'Pedro Martínez', '93321100', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0820-1989-06666', 'Catalina', 'Herrera', 'Femenino', '32', 'catalinaherrera@example.com', '94332211', 'Roberto Rodríguez', '92210033', '890 Cedar Lane, Apt 6A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Temporal'),
('0821-1991-07777', 'Alejandro', 'Pérez', 'Masculino', '27', 'alejandroperez@example.com', '99421100', 'Andrés Fernández', '91100332', '380 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Temporal'),
('0822-1994-08888', 'Mariana', 'López', 'Femenino', '26', 'marianalopez@example.com', '99110069', 'Julia Martínez', '90099887', '789 Cedar Road, Apt 12C, Cedar Heights, Chicago, Illinois, 60601-2345, United States of America', 'Permanente'),
('0823-1993-09999', 'Gabriel', 'Gutiérrez', 'Masculino', '28', 'gabrielgutierrez@example.com', '99501988', 'Carlos Rodríguez', '89998877', '567 Elm Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0824-1990-10101', 'Isabella', 'Martínez', 'Femenino', '31', 'isabellamartinez@example.com', '99958877', 'Ana Hernández', '88887766', '123 Maple Drive, Suite 6B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0825-1996-11212', 'Gabriela', 'García', 'Femenino', '26', 'gabrielagarcia@example.com', '99887756', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0826-1993-22323', 'Sebastián', 'Martínez', 'Masculino', '28', 'sebastianmartinez@example.com', '95772655', 'Javier Rodríguez', '98654321', '456 Pine Avenue, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0827-1990-33434', 'Valentina', 'López', 'Femenino', '31', 'valentinalopez54@example.com', '99655544', 'Luisa Ramírez', '95543210', '789 Elm Street, Suite 9C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0828-1995-44545', 'Daniel', 'Gómez', 'Masculino', '26', 'danielgomez@example.com', '99522443', 'Mario Fernández', '94432211', '567 Maple Drive, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0829-1992-55656', 'Sofía', 'Sánchez', 'Femenino', '29', 'sofiasanchez65@example.com', '99433422', 'Pedro Martínez', '93321100', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0830-1989-66767', 'Diego', 'Herrera', 'Masculino', '32', 'diegoherrera@example.com', '99234211', 'Roberto Rodríguez', '92210033', '890 Cedar Lane, Apt 6A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Temporal'),
('0831-1991-77878', 'Valeria', 'Pérez', 'Femenino', '27', 'valeriaperez65@example.com', '99231400', 'Andrés Fernández', '91100332', '380 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Temporal'),
('0832-1994-88989', 'Javier', 'López', 'Masculino', '26', 'javierlopez64@example.com', '99113049', 'Julia Martínez', '90099887', '789 Cedar Road, Apt 12C, Cedar Heights, Chicago, Illinois, 60601-2345, United States of America', 'Permanente'),
('0833-1993-99090', 'Carolina', 'Gutiérrez', 'Femenino', '28', 'carolinagutierrez12@example.com', '99029988', 'Carlos Rodríguez', '89998877', '567 Elm Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0834-1990-10101', 'David', 'Martínez', 'Masculino', '31', 'davidmartinez32@example.com', '99998847', 'Ana Hernández', '88887766', '123 Maple Drive, Suite 6B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0835-1996-11212', 'Fernanda', 'González', 'Femenino', '26', 'fernandagonzalez32@example.com', '94887766', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0836-1993-22323', 'Sebastián', 'Hernández', 'Masculino', '28', 'sebastianhernandez54@example.com', '99476655', 'Javier Rodríguez', '98654321', '456 Pine Avenue, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0837-1990-33434', 'Valentina', 'López', 'Femenino', '31', 'valentinalopez65@example.com', '99665444', 'Luisa Ramírez', '95543210', '789 Elm Street, Suite 9C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0838-1995-44545', 'Diego', 'Gómez', 'Masculino', '26', 'diegogomez66@example.com', '99554443', 'Mario Fernández', '94432211', '567 Maple Drive, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0839-1992-55656', 'Sofía', 'Sánchez', 'Femenino', '29', 'sofiasanchez66@example.com', '99443422', 'Pedro Martínez', '93321100', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0840-1989-66767', 'Carlos', 'Herrera', 'Masculino', '32', 'carlosherrera11@example.com', '99732211', 'Roberto Rodríguez', '92210033', '890 Cedar Lane, Apt 6A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Temporal'),
('0841-1991-77878', 'Valeria', 'Pérez', 'Femenino', '27', 'valeriaperez22@example.com', '99221700', 'Andrés Fernández', '91100332', '380 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Temporal'),
('0842-1994-88989', 'Javier', 'López', 'Masculino', '26', 'javierlopez12@example.com', '99110079', 'Julia Martínez', '90099887', '789 Cedar Road, Apt 12C, Cedar Heights, Chicago, Illinois, 60601-2345, United States of America', 'Permanente'),
('0843-1993-99090', 'Carolina', 'Gutiérrez', 'Femenino', '28', 'carolinagutierrez32@example.com', '99079988', 'Carlos Rodríguez', '89998877', '567 Elm Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0844-1990-10101', 'David', 'Martínez', 'Masculino', '31', 'davidmartinez34@example.com', '99978877', 'Ana Hernández', '88887766', '123 Maple Drive, Suite 6B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0845-1997-11111', 'Lucía', 'García', 'Femenino', '24', 'luciagarcia45@example.com', '99787766', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0846-1996-11212', 'Marcela', 'González', 'Femenino', '26', 'marcelagonzalez66@example.com', '99187766', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0847-1993-22323', 'Sebastián', 'Hernández', 'Masculino', '28', 'sebastianhernandez66@example.com', '99176655', 'Javier Rodríguez', '98654321', '456 Pine Avenue, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0848-1990-33434', 'Valentina', 'López', 'Femenino', '31', 'valentinalopez@example.com', '99665144', 'Luisa Ramírez', '95543210', '789 Elm Street, Suite 9C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0849-1995-44545', 'Diego', 'Gómez', 'Masculino', '26', 'diegogomez@example.com', '99554413', 'Mario Fernández', '94432211', '567 Maple Drive, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0850-1992-55656', 'Sofía', 'Sánchez', 'Femenino', '29', 'sofiasanchez@example.com', '99413322', 'Pedro Martínez', '93321100', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0851-1989-66767', 'Carlos', 'Herrera', 'Masculino', '32', 'carlosherrera@example.com', '92335211', 'Roberto Rodríguez', '92210033', '890 Cedar Lane, Apt 6A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Temporal'),
('0852-1991-77878', 'Valeria', 'Pérez', 'Femenino', '27', 'valeriaperez@example.com', '99222150', 'Andrés Fernández', '91100332', '380 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Temporal'),
('0853-1994-88989', 'Javier', 'López', 'Masculino', '26', 'javierlopez@example.com', '99110499', 'Julia Martínez', '90099887', '789 Cedar Road, Apt 12C, Cedar Heights, Chicago, Illinois, 60601-2345, United States of America', 'Permanente'),
('0854-1993-99090', 'Carolina', 'Gutiérrez', 'Femenino', '28', 'carolinagutierrez@example.com', '39004988', 'Carlos Rodríguez', '89998877', '567 Elm Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0855-1990-10101', 'David', 'Martínez', 'Masculino', '31', 'davidmartinez@example.com', '89998477', 'Ana Hernández', '88887766', '123 Maple Drive, Suite 6B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0856-1997-11111', 'Lucía', 'García', 'Femenino', '24', 'luciagarcia@example.com', '29884766', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0857-1996-11212', 'Marcela', 'González', 'Femenino', '26', 'marcelagonzalez@example.com', '29887766', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0858-1993-22323', 'Sebastián', 'Hernández', 'Masculino', '28', 'sebastianhernandez@example.com', '23776655', 'Javier Rodríguez', '98654321', '456 Pine Avenue, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0859-1990-33434', 'Valentina', 'López', 'Femenino', '31', 'valentinalopez90@example.com', '33665544', 'Luisa Ramírez', '95543210', '789 Elm Street, Suite 9C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0860-1995-44545', 'Diego', 'Gómez', 'Masculino', '26', 'diegogomez22@example.com', '27554433', 'Mario Fernández', '94432211', '567 Maple Drive, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0861-1992-55656', 'Sofía', 'Sánchez', 'Femenino', '29', 'sofiasanchez22@example.com', '89443322', 'Pedro Martínez', '93321100', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0862-1989-66767', 'Carlos', 'Herrera', 'Masculino', '32', 'carlosherrera22@example.com', '89332211', 'Roberto Rodríguez', '92210033', '890 Cedar Lane, Apt 6A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Temporal'),
('0863-1991-77878', 'Valeria', 'Pérez', 'Femenino', '27', 'valeriaperez13@example.com', '33221100', 'Andrés Fernández', '91100332', '380 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Temporal'),
('0864-1994-88989', 'Javier', 'López', 'Masculino', '26', 'javierlopez19@example.com', '90110099', 'Julia Martínez', '90099887', '789 Cedar Road, Apt 12C, Cedar Heights, Chicago, Illinois, 60601-2345, United States of America', 'Permanente'),
('0865-1993-99090', 'Carolina', 'Gutiérrez', 'Femenino', '28', 'carolinagutierrez442@example.com', '99009988', 'Carlos Rodríguez', '89998877', '567 Elm Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0866-1990-10101', 'David', 'Martínez', 'Masculino', '31', 'davidmartinez321@example.com', '99998877', 'Ana Hernández', '88887766', '123 Maple Drive, Suite 6B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0867-1997-11111', 'Lucía', 'García', 'Femenino', '24', 'luciagarcia432@example.com', '99887466', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0868-1996-11212', 'Marcela', 'González', 'Femenino', '26', ' marcelagonzalez232@example.com ', '99887566', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0869-1993-22323', 'Sebastián', 'Hernández', 'Masculino', '28', ' sebastianhernandez335@example.com', '95776655', 'Javier Rodríguez', '98654321', '456 Pine Avenue, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0870-1990-33434', 'Valentina', 'López', 'Femenino', '31', 'valentinalopez543@example.com', '95665544', 'Luisa Ramírez', '95543210', '789 Elm Street, Suite 9C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0871-1995-44545', 'Diego', 'Gómez', 'Masculino', '26', 'diegogomez432@example.com', '99554453', 'Mario Fernández', '94432211', '567 Maple Drive, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal'),
('0872-1992-55656', 'Sofía', 'Sánchez', 'Femenino', '29', 'sofiasanchez324@example.com', '99445322', 'Pedro Martínez', '93321100', '234 Oak Avenue, Suite 7A, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0873-1989-66767', 'Carlos', 'Herrera', 'Masculino', '32', 'carlosherrera432@example.com', '99532211', 'Roberto Rodríguez', '92210033', '890 Cedar Lane, Apt 6A, Bayview Heights Apartments, San Francisco, California, 94102-1234, United States of America', 'Temporal'),
('0874-1991-77878', 'Valeria', 'Pérez', 'Femenino', '27', 'valeriaperez324@example.com', '99221500', 'Andrés Fernández', '91100332', '380 Pine Drive, Apt 7C, Lakeside Apartments, Chicago, Illinois, 60601-6789, United States of America', 'Temporal'),
('0875-1994-88989', 'Javier', 'López', 'Masculino', '26', 'javierlopez244@example.com', '99110059', 'Julia Martínez', '90099887', '789 Cedar Road, Apt 12C, Cedar Heights, Chicago, Illinois, 60601-2345, United States of America', 'Permanente'),
('0876-1993-99090', 'Carolina', 'Gutiérrez', 'Femenino', '28', 'carolinagutierrez444@example.com', '99509988', 'Carlos Rodríguez', '89998877', '567 Elm Street, Unit 4B, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0877-1990-10101', 'David', 'Martínez', 'Masculino', '31', 'davidmartinez545@example.com', '99998577', 'Ana Hernández', '88887766', '123 Maple Drive, Suite 6B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Temporal'),
('0878-1997-11111', 'Lucía', 'García', 'Femenino', '24', 'luciagarcia544@example.com', '99885766', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0879-1996-11212', 'Marcela', 'González', 'Femenino', '26', 'marcelagonzalez766@example.com', '99587766', 'María López', '98765432', '123 Maple Drive, Suite 5B, Oakville Condos, New York, New York, 10001-2345, United States of America', 'Permanente'),
('0880-1993-22323', 'Sebastián', 'Hernández', 'Masculino', '28', 'sebastianhernandez335@example.com', '99576655', 'Javier Rodríguez', '98654321', '456 Pine Avenue, Unit 2A, Pineview Apartments, Los Angeles, California, 90001-6789, United States of America', 'Permanente'),
('0881-1990-33434', 'Valentina', 'López', 'Femenino', '31', 'valentinalopez655@example.com', '94665544', 'Luisa Ramírez', '95543210', '789 Elm Street, Suite 9C, Riverfront Condos, New York, New York, 10001-9012, United States of America', 'Temporal'),
('0882-1995-44545', 'Diego', 'Gómez', 'Masculino', '26', 'diegogomez766@example.com', '99454433', 'Mario Fernández', '94432211', '567 Maple Drive, Unit 3B, Hillside Heights, Miami, Florida, 33101-1234, United States of America', 'Temporal');

INSERT INTO arreglos (nombre, imagen, cantidad, precio, disponible) VALUES
('Ramo de Rosas', 'arreglo1.jpg', 10 , 1110.00, 'Si'),
('Centro de Mesa Primaveral', 'arreglo2.jpeg', 100 , 1500.99, 'No'),
('Bouquet de Lirios', 'arreglo3.jpeg', 95 , 1689.99, 'Si'),
('Arreglo de Girasoles', 'arreglo4.jpg', 100 , 1729.99, 'Si'),
('Cesta de Tulipanes', 'arreglo5.jpg',100 , 1200.99, 'No'),
('Ramo de Orquídeas', 'arreglo6.jpg',100 , 1200.99, 'Si'),
('Corona de Flores', 'arreglo3.jpeg',100 , 1300.99, 'No'),
('Arreglo de Margaritas', 'arreglo2.jpeg',100 , 1800.99, 'Si'),
('Bouquet de Rosas y Lirios', 'arreglo7.jpg',100 , 1300.00, 'Si'),
('Centro de Mesa Elegante', 'arreglo10.jpg',100 , 1100.75, 'No'),
('Ramo de Girasoles y Margaritas', 'arreglo11.jpg',100 , 3800.50, 'Si'),
('Cesta de Flores Mixtas', 'arreglo12.jpg',100 , 4200.99, 'Si'),
('Ramo de Tulipanes', 'arreglo13.jpg',100 , 6000.49, 'No'),
('Arreglo de Rosas Blancas', 'arreglo14.jpeg',100 , 4800.99, 'Si'),
('Bouquet de Lilium', 'arreglo15.jpeg',100 , 6800.75, 'No'),
('Centro de Mesa Moderno', 'arreglo16.jpg',100 , 5200.50, 'Si'),
('Ramo de Gerberas', 'arreglo17.jpg', 100 ,3600.99, 'Si'),
('Caja de Flores Preservadas', 'arreglo18.jpg', 0 , 8000.49, 'No'),
('Ramo de Flores Silvestres', 'arreglo19.jpg',100 , 4400.99, 'Si'),
('Bouquet de Peonías', 'arreglo20.jpg', 6200.75,100 , 'No'),
('Arreglo de Rosas Rojas', 'arreglo1.jpg',100 , 5300.50, 'Si'),
('Centro de Mesa Vintage', 'arreglo2.jpg',100 , 4700.99, 'Si'),
('Cesta de Orquídeas', 'arreglo3.jpg',100 , 7800.50, 'No'),
('Ramo de Crisantemos', 'arreglo4.jpg',100 , 4000.99, 'Si'),
('Arreglo de Rosas y Lirios', 'arreglo5.jpg',100 , 5700.49, 'No'),
('Centro de Mesa Otoñal', 'arreglo6.jpg',100 , 2500.00, 'No'),
('Ramo de Crisantemos y Rosas', 'arreglo7.jpg',100 , 2100.99, 'Si'),
('Arreglo de Flores Exóticas', 'arreglo8.jpeg',0 , 3200.99, 'Si'),
('Bouquet de Calas', 'arreglo9.jpg',100 , 2800.99, 'No'),
('Centro de Mesa Navideño', 'arreglo10.jpg',100 , 3500.50, 'Si'),
('Ramo de Rosas y Tulipanes', 'arreglo1.jpg',100 , 4500.75, 'No'),
('Caja de Rosas Preservadas', 'arreglo2.jpg',100 , 5000.99, 'Si'),
('Arreglo de Hortensias', 'arreglo3.jpg',100 , 3900.99, 'Si'),
('Bouquet de Rosas Multicolores', 'arreglo4.jpg',100 , 2700.49, 'No'),
('Centro de Mesa Rústico', 'arreglo5.jpg',100 , 3200.99, 'Si'),
('Ramo de Flores Variadas', 'arreglo6.jpg',100 , 3800.75, 'No'),
('Arreglo de Orquídeas Phalaenopsis', 'arreglo17.jpg',100 , 4200.50, 'Si'),
('Cesta de Girasoles y Margaritas', 'arreglo18.jpg',100 , 3600.99, 'Si'),
('Ramo de Rosas Rojas y Blancas', 'arreglo11.jpg',100 , 1500.00, 'Si'),
('Bouquet de Orquídeas y Lirios', 'arreglo12.jpg',100 , 2500.99, 'No'),
('Centro de Mesa Vintage', 'arreglo13.jpg',100 , 2100.99, 'Si'),
('Arreglo de Girasoles y Rosas Blancas', 'arreglo14.jpeg',100 , 2200.99, 'Si'),
('Caja de Flores Secas', 'arreglo15.jpeg',100 , 2800.99, 'No'),
('Ramo de Tulipanes y Rosas', 'arreglo16.jpg',100 , 1900.99, 'Si'),
('Bouquet de Crisantemos y Margaritas', 'arreglo17.jpg',100 , 1700.00, 'Si'),
('Centro de Mesa Rústico', 'arreglo19.jpg',100 , 2900.50, 'No'),
('Arreglo de Margaritas y Lirios Blancos', 'arreglo12.jpg',100 , 2400.99, 'Si'),
('Cesta de Flores Frescas', 'arreglo13.jpg',100 , 3200.75, 'No'),
('Ramo de Rosas y Claveles Rojos', 'arreglo14.jpeg',100 , 2000.99, 'Si'),
('Bouquet de Peonías y Rosas Blancas', 'arreglo15.jpeg',100 , 3500.99, 'Si'),
('Centro de Mesa Elegante y Moderno', 'arreglo16.jpg',100 , 2600.49, 'No'),
('Arreglo de Girasoles y Calas Blancas', 'arreglo17.jpg',100 , 3800.99, 'Si'),
('Caja de Rosas Preservadas y Orquídeas', 'arreglo18.jpg',100 , 2300.75, 'No'),
('Ramo de Flores Tropicales y Girasoles', 'arreglo19.jpg',100 , 2000.50, 'Si'),
('Bouquet de Gerberas y Tulipanes', 'arreglo20.jpg',100 , 2700.99, 'Si'),
('Centro de Mesa Minimalista', 'arreglo8.jpeg',100 , 3500.49, 'No'),
('Arreglo de Orquídeas y Rosas Rojas', 'arreglo9.jpg',100 , 3300.99, 'Si'),
('Cesta de Rosas y Margaritas Blancas', 'arreglo10.jpg',100 , 2800.75, 'No'),
('Ramo de Flores Mixtas y Lirios', 'arreglo11.jpg',100 , 2600.50, 'Si'),
('Bouquet de Lilium y Rosas Rojas', 'arreglo12.jpg',100 , 3100.99, 'Si'),
('Centro de Mesa Genial', 'arreglo10.jpg',100 , 1300.75, 'No'),
('Ramo de Rosas y Pimpinelas', 'arreglo11.jpg',100 , 3800.50, 'Si'),
('Cesta de Flores Verdes', 'arreglo12.jpg',100 , 3200.99, 'Si'),
('Ramo de Tulipanes Turquesa', 'arreglo13.jpg',100 , 1200.49, 'No'),
('Arreglo de Rosas negras', 'arreglo10.jpg',100 , 5500.99, 'Si'),
('Bouquet de Lilith', 'arreglo5.jpg',100 , 6800.75, 'No'),
('Centro de Mesa Contemporaneo', 'arreglo6.jpg',100 , 5200.50, 'Si'),
('Ramo de Horizabals', 'arreglo7.jpg', 3600.99,100 , 'Si'),
('Caja de Flores Poderosas', 'arreglo8.jpeg',100 , 8000.49, 'No'),
('Ramo de Flores Cocaínoides', 'arreglo9.jpg',100 , 4400.99, 'Si');

INSERT INTO materiales (nombre, cantidad, precio, disponible, descripcion, exento, proveedor_id) VALUES 
('Tijeras', 0, 150.00, 'Si', 'La tijera es una herramienta de corte utilizada en diversos ámbitos.', 1, 1),
('Papel', 100,  13.55, 'No', 'El papel es un material versátil que se utiliza para escribir, dibujar y realizar otras actividades.',0 , 2),
('Escarcha', 100, 100.00, 'Si', 'La escarcha es un material brillante utilizado para decorar manualidades y proyectos.', 0, 3),
('Engrapadora', 100, 50.00, 'No', 'La engrapadora es una herramienta que se utiliza para unir papeles o documentos con grapas.', 1, 4),
('Pasta de colores', 100, 80.00, 'Si', 'La pasta de colores es un material utilizado para modelar y crear figuras artísticas.', 1, 5),
('Marcadores', 100, 70.00, 'No', 'Los marcadores son instrumentos de escritura que se utilizan para resaltar o hacer énfasis en información.', 1, 6),
('Cinta adhesiva', 200, 30.00, 'Si', 'La cinta adhesiva es un material que se utiliza para unir objetos o fijar elementos en diferentes superficies.', 1, 7),
('Pinceles', 220, 60.00, 'Si', 'Los pinceles son herramientas utilizadas para aplicar pintura sobre superficies.', 1, 8),
('Pegamento', 30, 40.00, 'Si', 'El pegamento es una sustancia utilizada para unir materiales y superficies.', 1, 9),
('Papel de seda', 15, 25.00, 'Si', 'El papel de seda es un material ligero y delicado utilizado para manualidades y decoraciones.', 1, 10),
('Crayones', 50.00, 0, 'No', 'Los crayones son barras de colores utilizadas para dibujar y colorear.', 1, 11),
('Acrílicos', 120.00, 0, 'Si', 'Los acrílicos son pinturas a base de agua utilizadas en diversos proyectos artísticos.', 1, 12),
('Pinceles de espuma', 0, 35.00, 'Si', 'Los pinceles de espuma son herramientas utilizadas para aplicar pintura de manera uniforme y suave.', 1, 13),
('Goma de borrar', 0, 15.00, 'Si', 'La goma de borrar es una herramienta utilizada para eliminar trazos o marcas de lápiz.',0 , 14),
('Cintas decorativas', 0, 50.00, 'No', 'Las cintas decorativas son materiales utilizados para embellecer y adornar diferentes proyectos y decoraciones.', 1, 16),
('Pinceles de pelo sintético', 0, 70.00, 'Si', 'Los pinceles de pelo sintético son herramientas utilizadas en pintura que imitan las características de los pinceles de pelo natural.', 1, 17),
('Pegatinas', 0, 20.00, 'Si', 'Las pegatinas son adhesivos decorativos utilizados para añadir diseños y elementos visuales a diferentes superficies.', 1, 18),
('Cordones', 0, 30.00, 'Si', 'Los cordones son materiales utilizados para atar, sujetar o decorar diferentes objetos y prendas de vestir.', 1, 19),
('Rotuladores', 0, 40.00, 'Si', 'Los rotuladores son instrumentos de escritura con punta de fieltro utilizados para realizar trazos precisos y coloridos.', 1, 20),
('Hilos de bordar', 0, 25.00, 'Si', 'Los hilos de bordar son materiales utilizados en bordados y costura para añadir colores y detalles a diferentes proyectos.', 1, 21),
('Lápices de colores', 0, 35.00, 'No', 'Los lápices de colores son herramientas utilizadas para colorear y dibujar, con una amplia gama de colores disponibles.', 1, 22),
('Papeles decorativos', 0, 45.00, 'Si', 'Los papeles decorativos son materiales utilizados para envolver regalos, hacer manualidades o crear proyectos decorativos.', 1, 23),
('Pintura acrílica', 0, 60.00, 'No', 'La pintura acrílica es un tipo de pintura versátil y de secado rápido utilizada en diversas técnicas y superficies.', 1, 24),
('Marcadores de pizarra blanca',0,  30.00, 'Si', 'Los marcadores de pizarra blanca son utilizados para escribir y dibujar sobre superficies no porosas, como pizarras blancas.', 1, 25),
('Perlas de colores', 0, 40.00, 'No', 'Las perlas de colores son pequeñas cuentas utilizadas en proyectos de bisutería y manualidades para añadir adornos y detalles.', 1, 26),
('Tubos de acuarela', 0, 55.00, 'Si', 'Los tubos de acuarela contienen pintura acuosa utilizada en pintura de acuarela, ofreciendo una amplia gama de colores.', 1, 27),
('Tijeras de precisión', 0, 65.00, 'No', 'Las tijeras de precisión son herramientas de corte utilizadas para cortes detallados y precisos en diferentes materiales.', 1, 28),
('Adhesivo en barra', 0, 20.00, 'Si', 'El adhesivo en barra es un producto de pegamento sólido utilizado para unir diferentes materiales de forma rápida y fácil.', 1, 29),
('Cartulinas de colores', 0, 30.00, 'Si', 'Las cartulinas de colores son papeles de grosor medio utilizados en manualidades, proyectos escolares y decoraciones.', 1, 30),
('Pinceles de cerdas suaves', 0, 50.00, 'Si', 'Los pinceles de cerdas suaves son herramientas utilizadas en pintura para aplicar trazos suaves y delicados.', 1, 31),
('Goma eva', 0, 40.00, 'Si', 'La goma eva es un material de espuma utilizado en manualidades y proyectos infantiles debido a su versatilidad y facilidad de manejo.', 1, 32),
('Pompones', 0, 25.00, 'Si', 'Los pompones son pequeñas bolas de hilo utilizadas para hacer manualidades, decoraciones y proyectos de artesanía.', 1, 33),
('Pintura en spray', 0, 55.00, 'No', 'La pintura en spray es una forma rápida y conveniente de aplicar pintura en diferentes superficies y proyectos.', 1, 34),
('Hojas de papel para origami', 0, 30.00, 'Si', 'Las hojas de papel para origami son utilizadas en la técnica de plegado de papel para crear figuras y diseños.', 1, 35),
('Cuentas de cristal', 0, 40.00, 'Si', 'Las cuentas de cristal son pequeñas esferas utilizadas en joyería y manualidades para crear accesorios y decoraciones.', 1, 36),
('Cintas Washi', 0, 30.00, 'Si', 'Las cintas washi son cintas adhesivas decorativas utilizadas en manualidades y proyectos de scrapbooking.', 1, 37),
('Lentejuelas', 0, 20.00, 'Si', 'Las lentejuelas son pequeñas piezas reflectantes utilizadas para añadir brillo y decoración a diferentes proyectos y prendas.', 1, 38),
('Pegamento en spray', 0, 45.00, 'Si', 'El pegamento en spray es un adhesivo en aerosol utilizado para fijar rápidamente diferentes materiales.', 1, 39),
('Fieltro', 0, 35.00, 'No', 'El fieltro es un material textil no tejido utilizado en manualidades, proyectos de costura y decoraciones.', 1, 40),
('Abalorios', 0, 25.00, 'Si', 'Los abalorios son pequeñas piezas decorativas utilizadas en bisutería, bordados y manualidades.', 1, 41),
('Pintura en tela', 0, 55.00, 'No', 'La pintura en tela es una pintura especializada utilizada para decorar y personalizar prendas y tejidos.', 1, 42),
('Botones', 0, 30.00, 'No', 'Los botones son elementos utilizados en costura y manualidades para unir y adornar diferentes tejidos y materiales.', 1, 43),
('Pompones de papel', 0, 40.00, 'Si', 'Los pompones de papel son elementos decorativos utilizados en fiestas, eventos y decoraciones.', 1, 44),
('Cuentas de madera', 0, 25.00, 'Si', 'Las cuentas de madera son pequeñas esferas utilizadas en joyería, manualidades y proyectos de artesanía.', 1, 45),
('Pegamento líquido', 0, 35.00, 'Si', 'El pegamento líquido es un adhesivo versátil utilizado en manualidades, proyectos escolares y reparaciones.', 1, 46),
('Hojas de scrapbooking', 0, 30.00, 'Si', 'Las hojas de scrapbooking son papeles decorativos utilizados en álbumes de recortes y proyectos de scrapbooking.', 1, 47),
('Cuentas de plástico', 0, 20.00, 'Si', 'Las cuentas de plástico son pequeñas piezas utilizadas en bisutería, manualidades y proyectos de artesanía.', 1, 48),
('Pinceles de punta fina', 0, 40.00, 'Si', 'Los pinceles de punta fina son herramientas utilizadas en pintura para trazos precisos y detalles.', 1, 49),
('Papel adhesivo', 0, 30.00, 'Si', 'El papel adhesivo es un material con respaldo adhesivo utilizado para forrar superficies y hacer etiquetas.', 1, 50),
('Cintas de encaje', 0, 25.00, 'Si', 'Las cintas de encaje son cintas decorativas utilizadas en manualidades, costura y proyectos de decoración.', 1, 51),
('Pintura para vidrio', 0, 55.00, 'No', 'La pintura para vidrio es una pintura especializada utilizada para decorar y personalizar superficies de vidrio.', 1, 52),
('Lazos', 0, 30.00, 'No', 'Los lazos son elementos decorativos utilizados para atar, adornar y embellecer diferentes objetos y prendas.', 1, 53),
('Papel de seda metalizado',0,  40.00, 'No', 'El papel de seda metalizado es un material utilizado para manualidades, decoraciones y proyectos de arte.', 1, 54),
('Tinta para sellos', 0, 35.00, 'Si', 'La tinta para sellos es una tinta especializada utilizada para imprimir sellos y estampar diseños.', 1, 55),
('Cuentas de metal', 0, 40.00, 'Si', 'Las cuentas de metal son pequeñas piezas utilizadas en joyería, manualidades y proyectos de artesanía.', 1, 56),
('Pinceles de acuarela',0 , 35.00, 'Si', 'Los pinceles de acuarela son herramientas utilizadas en pintura de acuarela para aplicar trazos suaves y fluidos.', 1, 57),
('Papel kraft', 0, 30.00, 'Si', 'El papel kraft es un papel resistente y de color marrón utilizado en manualidades, empaques y proyectos de arte.', 1, 58),
('Lentejuelas holográficas',0 , 25.00, 'No', 'Las lentejuelas holográficas son pequeñas piezas decorativas utilizadas para añadir brillo y efecto visual a diferentes proyectos.', 1, 59),
('Pegamento de secado rápido', 0, 45.00, 'No', 'El pegamento de secado rápido es un adhesivo que se seca rápidamente, utilizado para proyectos que requieren un secado rápido.', 1, 60),
('Hilos de bordar metálicos', 0, 40.00, 'Si', 'Los hilos de bordar metálicos son hilos brillantes utilizados en bordados y costura para añadir detalles y brillo.', 1, 61),
('Sellos de goma', 0, 35.00, 'Si', 'Los sellos de goma son elementos utilizados para estampar diseños y patrones en papel, tela y otras superficies.', 1, 62),
('Papel de seda estampado', 0, 30.00, 'Si', 'El papel de seda estampado es un material utilizado para manualidades, envoltorios y proyectos de decoración.', 1, 63),
('Tizas de colores', 0, 25.00, 'Si', 'Las tizas de colores son herramientas utilizadas para dibujar y escribir en pizarras y superficies no porosas.', 1, 64),
('Cuentas de cerámica', 0, 40.00, 'Si', 'Las cuentas de cerámica son pequeñas piezas utilizadas en joyería, manualidades y proyectos de artesanía.', 1, 65),
('Goma de pegar', 0, 35.00, 'Si', 'La goma de pegar es un adhesivo sólido utilizado para unir diferentes materiales de forma duradera y resistente.', 1, 66),
('Plumas decorativas', 0, 30.00, 'Si', 'Las plumas decorativas son elementos utilizados en manualidades, proyectos de decoración y accesorios.', 1, 67),
('Papel de calco', 0, 25.00, 'No', 'El papel de calco es un papel semitransparente utilizado para transferir dibujos y diseños a diferentes superficies.', 1, 68),
('Pompones de lana', 0, 40.00, 'Si', 'Los pompones de lana son pequeñas bolas de lana utilizadas para hacer manualidades, decoraciones y proyectos de artesanía.', 1, 69),
('Pintura en óleo', 0, 55.00, 'No', 'La pintura en óleo es una pintura de secado lento y pigmentación intensa utilizada en técnicas de pintura al óleo.', 1, 70),
('Perlas de plástico', 0, 30.00, 'No', 'Las perlas de plástico son pequeñas cuentas utilizadas en bisutería, manualidades y proyectos de artesanía.', 1, 71),
('Marcadores de tela', 0, 35.00, 'No', 'Los marcadores de tela son marcadores especiales utilizados para escribir y dibujar en diferentes tipos de tela.', 1, 72),
('Plumas metálicas', 0, 30.00, 'Si', 'Las plumas metálicas son elementos utilizados en manualidades, proyectos de decoración y diseño de tarjetas.', 1, 73),
('Cartulinas metalizadas', 0, 40.00, 'Si', 'Las cartulinas metalizadas son papeles de colores brillantes utilizados en manualidades y proyectos de decoración.', 1, 74),
('Cuentas de resina', 0, 35.00, 'Si', 'Las cuentas de resina son pequeñas piezas utilizadas en joyería, manualidades y proyectos de artesanía.', 1, 75),
('Papel de gamussa', 0, 35.00, 'No', 'El papel de gamussa es un material textil no tejido utilizado en manualidades, proyectos de costura y decoraciones.', 1, 40);

INSERT INTO compras (codigo_compra, fecha, proveedor_id, empleado_id) VALUES 
('FACTURA-0001', '2023-01-09', 1, 1),
('FACTURA-0002', '2023-02-08', 3, 3),
('FACTURA-0003', '2023-03-07', 4, 4),
('FACTURA-0006', '2023-05-04', 4, 4),
('FACTURA-0004', '2023-04-06', 2, 2),
('FACTURA-0005', '2023-05-05', 3, 3),
('FACTURA-0007', '2023-06-03', 1, 1),
('FACTURA-0008', '2023-07-02', 2, 2),
('FACTURA-0009', '2023-07-01', 3, 3),
('FACTURA-0010', '2023-06-30', 4, 4),
('FACTURA-0011', '2023-06-29', 1, 1),
('FACTURA-0012', '2023-06-28', 2, 2),
('FACTURA-0013', '2023-08-27', 3, 3),
('FACTURA-0014', '2023-08-26', 4, 4),
('FACTURA-0015', '2023-06-25', 1, 1),
('FACTURA-0016', '2023-09-24', 2, 2),
('FACTURA-0017', '2023-09-23', 3, 3),
('FACTURA-0018', '2023-09-22', 4, 4),
('FACTURA-0019', '2023-10-21', 1, 1),
('FACTURA-0020', '2023-11-20', 2, 2),
('FACTURA-0021', '2023-12-19', 3, 3),
('FACTURA-0022', '2023-06-18', 4, 4),
('FACTURA-0023', '2023-06-17', 1, 1),
('FACTURA-0024', '2023-06-16', 2, 2),
('FACTURA-0025', '2023-06-15', 3, 3),
('FACTURA-0026', '2023-06-14', 4, 4),
('FACTURA-0027', '2023-06-13', 1, 1),
('FACTURA-0028', '2023-06-12', 2, 2),
('FACTURA-0029', '2023-06-11', 3, 3),
('FACTURA-0030', '2023-06-11', 3, 4);

INSERT INTO detalles_compras (compra_id, material_id, cantidad, precio) VALUES 
(1, 1, 20, 10), 
(1, 2, 10, 10),
(1, 3, 10, 10),
(1, 4, 10, 10),
(2, 2, 3, 8.99),
(3, 1, 2, 10.99),
(3, 3, 2, 6.99),
(4, 4, 1, 4.99),
(5, 1, 3, 10.99),
(5, 2, 2, 8.99),
(6, 1, 1, 10.99),
(6, 3, 3, 6.99),
(7, 2, 1, 8.99),
(8, 1, 2, 10.99),
(8, 2, 1, 8.99),
(9, 2, 3, 8.99),
(10, 1, 2, 10.99),
(10, 3, 2, 6.99),
(11, 4, 1, 4.99),
(12, 1, 3, 10.99),
(13, 2, 1, 8.99),
(14, 1, 2, 10.99),
(14, 2, 2, 8.99),
(15, 3, 3, 6.99),
(16, 1, 2, 10.99),
(17, 2, 3, 8.99),
(17, 3, 1, 6.99),
(18, 1, 1, 10.99),
(18, 3, 2, 6.99),
(19, 4, 1, 4.99),
(20, 1, 3, 10.99),
(21, 2, 1, 8.99),
(22, 1, 2, 10.99),
(22, 2, 2, 8.99),
(23, 3, 3, 6.99),
(24, 1, 2, 10.99),
(24, 2, 1, 8.99),
(25, 2, 3, 8.99),
(26, 1, 1, 10.99),
(26, 3, 3, 6.99),
(27, 4, 1, 4.99),
(28, 1, 2, 10.99),
(29, 2, 1, 8.99),
(30, 1, 2, 10.99),
(30, 2, 2, 8.99);

INSERT INTO ventas (codigo_venta, fecha, cliente_id, usuario_id) VALUES
('VT22012023-173901-00001', '2023-01-15', 1, 3),
('VT16022023-180001-00002', '2023-02-16', 2, 1),
('VT17032023-190159-00002', '2023-03-17', 3, 2),
('VT18042023-200259-00003', '2023-04-18', 4, 3),
('VT19052023-210359-00004', '2023-05-19', 5, 1),
('VT20062023-170459-00005', '2023-06-20', 1, 4),
('VT20072023-170559-00006', '2023-07-21', 2, 2),
('VT20082023-170659-00007', '2023-08-22', 3, 5),
('VT23092023-170759-00008', '2023-09-23', 4, 2),
('VT24102023-260859-00009', '2023-10-24', 5, 3),
('VT25112023-270959-00010', '2023-11-25', 1, 1),
('VT26122023-281059-00011', '2023-12-26', 2, 4),
('VT27012023-091159-00012', '2024-01-27', 3, 2),
('VT28022023-101259-00013', '2024-02-28', 4, 5),
('VT29032023-111359-00014', '2024-03-29', 5, 3),
('VT30042023-121459-00015', '2024-04-30', 1, 1),
('VT31052023-031559-00016', '2024-05-31', 2, 4),
('VT01062023-041659-00017', '2024-06-01', 3, 2),
('VT02072023-151759-00018', '2024-07-02', 4, 5),
('VT03082023-061859-00019', '2024-08-03', 5, 3),
('VT04092023-071959-00020', '2024-09-04', 1, 1),
('VT05102023-082059-00021', '2024-10-05', 2, 4),
('VT06112023-082159-00022', '2024-11-06', 3, 2),
('VT07122023-092259-00023', '2024-12-07', 4, 5),
('VT08012023-092359-00024', '2025-01-08', 5, 3),
('VT09022023-112459-00025', '2025-02-09', 1, 1),
('VT10032023-112559-00026', '2025-03-10', 2, 4),
('VT11042023-112659-00027', '2025-04-11', 3, 2),
('VT12052023-112759-00028', '2025-05-12', 4, 5),
('VT13062023-102859-00029', '2025-06-13', 5, 3),
('VT13062023-102859-00030', '2025-06-13', 5, 3);

INSERT INTO detalles_ventas (venta_id, tipo_detalle, detalle_id, cantidad, precio)
VALUES 
(1, 'material', 1, 2, 150.00),
(1, 'material', 2, 2, 13.55),
(1, 'manualidad', 1, 1, 1500.00),
(1, 'manualidad', 2, 1, 1000.00),
(1, 'floristeria', 1, 1, 50.00),
(1, 'floristeria', 2, 1, 35.50),
(1, 'arreglo', 1, 2, 1110.00),
(1, 'arreglo', 2, 2, 1500.99),
(1, 'tarjeta', 1, 1, 90.00),
(1, 'tarjeta', 2, 1, 99999.999),
(1, 'desayuno', 1, 1, 80.00),
(1, 'desayuno', 2, 1, 90.00),
(2, 'material', 1, 2, 10.00),
(2, 'material', 2, 2, 20.00),
(2, 'manualidad', 1, 1, 30.00),
(2, 'floristeria', 1, 1, 40.00),
(2, 'floristeria', 2, 1, 50.00),
(2, 'arreglo', 1, 2, 60.00),
(2, 'tarjeta', 2, 1, 70.00),
(2, 'desayuno', 1, 1, 80.00),
(2, 'desayuno', 3, 1, 90.00),
(3, 'material', 1, 1, 36.00),
(4, 'floristeria', 4, 1, 38.00),
(5, 'material', 1, 1, 42.00),
(5, 'tarjeta', 1, 2, 20.00),
(5, 'desayuno', 4, 1, 15.00),
(6, 'material', 1, 1, 40.00),
(6, 'desayuno', 2, 1, 30.00),
(7, 'floristeria', 4, 1, 45.00),
(8, 'material', 1, 1, 36.00),
(8, 'tarjeta', 1, 1, 18.00),
(9, 'material', 1, 2, 60.00),
(9, 'desayuno', 4, 1, 15.00),
(10, 'material', 1, 1, 42.00),
(11, 'tarjeta', 1, 1, 25.00),
(11, 'arreglo', 2, 1, 30.00),
(12, 'material', 1, 2, 50.00),
(13, 'arreglo', 2, 1, 30.00),
(14, 'floristeria', 4, 1, 38.00),
(15, 'material', 1, 1, 42.00),
(15, 'tarjeta', 1, 2, 20.00),
(15, 'arreglo', 4, 1, 15.00),
(16, 'material', 1, 1, 40.00),
(17, 'floristeria', 4, 1, 45.00),
(18, 'material', 1, 1, 36.00),
(19, 'tarjeta', 1, 1, 20.00),
(20, 'material', 1, 2, 60.00),
(20, 'arreglo', 4, 1, 15.00),
(21, 'material', 1, 1, 42.00),
(21, 'tarjeta', 1, 2, 20.00),
(21, 'arreglo', 4, 1, 15.00),
(22, 'material', 1, 1, 40.00),
(23, 'floristeria', 4, 1, 45.00),
(24, 'material', 1, 1, 36.00),
(25, 'tarjeta', 1, 1, 20.00),
(25, 'material', 1, 2, 60.00),
(25, 'arreglo', 4, 1, 15.00),
(26, 'material', 1, 1, 42.00),
(26, 'tarjeta', 1, 2, 20.00),
(26, 'arreglo', 4, 1, 15.00),
(27, 'material', 1, 1, 40.00),
(28, 'floristeria', 4, 1, 45.00),
(29, 'material', 1, 1, 36.00),
(30, 'floristeria', 1, 1, 20.00),
(30, 'arreglo', 1, 2, 60.00),
(30, 'material', 4, 1, 15.00),
(30, 'tarjeta', 1, 1, 20.00),
(30, 'manualidad', 1, 2, 60.00),
(30, 'desayuno', 4, 1, 15.00);

-- Inserciones en la tabla "tarjetas"
INSERT INTO tarjetas (ocasion, disponible, descripcion, imagen, precio_tarjeta, mano_obra, cantidad) VALUES 
('Boda', 'Si', 'Tarjeta de felicitación para bodas', 'imagen1.jpg', 90.00, 10.00, 50),
('Cumpleaños', 'No', 'Tarjeta de cumpleaños personalizada', 'imagen2.jpg', 45.50, 5.00, 30),
('Aniversario', 'Si', 'Tarjeta de felicitación para aniversarios', 'imagen3.jpg', 70.00, 8.00, 40),
('Graduación', 'No', 'Tarjeta de felicitación para graduaciones', 'imagen4.jpg', 55.00, 7.50, 20),
('Amor', 'Si', 'Tarjeta de amor con mensaje romántico', 'imagen5.jpg', 65.00, 9.00, 25),
('Navidad', 'Si', 'Tarjeta navideña con diseño festivo', 'imagen6.jpg', 75.00, 11.00, 35),
('Agradecimiento', 'No', 'Tarjeta de agradecimiento personalizada', 'imagen7.jpg', 50.00, 6.50, 45),
('Ocasión Especial', 'Si', 'Tarjeta para ocasiones especiales', 'imagen8.jpg', 85.00, 10.50, 55),
('Boda', 'Si', 'Tarjeta de felicitación para bodas', 'imagen1.jpg', 70.00, 10.00, 50),
('Cumpleaños', 'No', 'Tarjeta de cumpleaños personalizada', 'imagen2.jpg', 77.70, 5.00, 30),
('Aniversario', 'Si', 'Tarjeta de felicitación para aniversarios', 'imagen3.jpg', 75.00, 8.00, 40),
('Graduación', 'No', 'Tarjeta de felicitación para graduaciones', 'imagen4.jpg', 85.00, 7.50, 20),
('Amor', 'Si', 'Tarjeta de amor con mensaje romántico', 'imagen5.jpg', 85.00, 9.00, 25),
('Navidad', 'Si', 'Tarjeta navideña con diseño festivo', 'imagen6.jpg', 85.00, 11.00, 35),
('Agradecimiento', 'No', 'Tarjeta de agradecimiento personalizada', 'imagen7.jpg', 52.00, 8.50, 45),
('Ocasión Especial', 'Si', 'Tarjeta para ocasiones especiales', 'imagen8.jpg', 89.00, 10.30, 55),
('Amor', 'Si', 'Tarjeta de amor con mensaje romántico', 'imagen5.jpg', 55.00, 9.40, 25),
('Navidad', 'Si', 'Tarjeta navideña con diseño festivo', 'imagen6.jpg', 55.00, 11.40, 35),
('Agradecimiento', 'No', 'Tarjeta de agradecimiento personalizada', 'imagen7.jpg', 55.00, 7.50, 45),
('Ocasión Especial', 'Si', 'Tarjeta para ocasiones especiales', 'imagen8.jpg', 84.00, 10.80, 55),
('Ocasión Especial', 'Si', 'Tarjeta para ocasiones especiales', 'imagen8.jpg', 54.00, 10.30, 55),
('Amor', 'Si', 'Tarjeta de amor con mensaje romántico', 'imagen5.jpg', 32.00, 9.40, 25),
('Navidad', 'Si', 'Tarjeta navideña con diseño festivo', 'imagen6.jpg', 89.00, 11.40, 35),
('Agradecimiento', 'No', 'Tarjeta de agradecimiento personalizada', 'imagen7.jpg', 89.00, 7.50, 45),
('Ocasión Especial', 'Si', 'Tarjeta para ocasiones especiales', 'imagen8.jpg', 81.00, 10.80, 55),
('Boda', 'Si', 'Tarjeta de felicitación para bodas', 'imagen1.jpg', 65.00, 10.00, 50),
('Cumpleaños', 'No', 'Tarjeta de cumpleaños personalizada', 'imagen2.jpg', 100.70, 5.00, 30),
('Aniversario', 'Si', 'Tarjeta de felicitación para aniversarios', 'imagen3.jpg', 92.00, 8.00, 40),
('Graduación', 'No', 'Tarjeta de felicitación para graduaciones', 'imagen4.jpg', 83.00, 7.50, 20),
('Amor', 'Si', 'Tarjeta de amor con mensaje romántico', 'imagen5.jpg', 90.00, 9.00, 25),
('Navidad', 'Si', 'Tarjeta navideña con diseño festivo', 'imagen6.jpg', 32.00, 11.00, 35);

INSERT INTO tarjetas_detalles (id_material, id_tarjeta, cantidad, precio) VALUES 
(1, 1, 50, 20.00),
(2, 2, 30, 15.00),
(3, 3, 40, 18.50),
(4, 4, 20, 12.00),
(5, 5, 25, 14.50),
(6, 6, 35, 16.80),
(7, 7, 45, 19.20),
(8, 8, 55, 22.50),
(9, 1, 50, 20.00),
(10, 2, 30, 15.00),
(11, 3, 40, 18.50),
(12, 4, 20, 12.00),
(13, 5, 25, 14.50),
(14, 6, 35, 16.80),
(15, 7, 45, 19.20),
(16, 8, 55, 22.50),
(17, 5, 25, 14.50),
(18, 6, 35, 16.80),
(19, 7, 45, 19.20),
(20, 8, 55, 22.50),
(21, 7, 45, 29.20),
(22, 8, 55, 32.50),
(23, 5, 25, 44.30),
(24, 6, 35, 56.40),
(25, 7, 45, 69.30),
(25, 8, 55, 12.20),
(26, 7, 45, 19.20),
(27, 8, 55, 22.50),
(27, 7, 45, 29.20),
(28, 8, 55, 32.50),
(29, 5, 25, 44.30),
(30, 6, 35, 56.40);

INSERT INTO manualidades (imagen, nombre, descripcion, tipo, cantidad, precio_manualidad, mano_obra)
VALUES
    ('imagen 1.JPG', 'Manualidad 1', 'Descripción de la manualidad 1', 'Centros de mesas', 100, 1500.00, 2000.12),
    ('imagen 2.JPEG', 'Manualidad 2', 'Descripción de la manualidad 2', 'Letras personalizadas',100, 1000.00, 150.00),
    ('imagen 3.JPEG', 'Manualidad 3', 'Descripción de la manualidad 3', 'Cajas personalizadas',100, 1200.00, 180.00),
    ('imagen 4.JPEG', 'Manualidad 4', 'Descripción de la manualidad 4', 'Arcos para eventos',100, 1300.00, 220.00),
    ('imagen 5.JPG', 'Manualidad 5', 'Descripción de la manualidad 5', 'Fotografía personalizadas',100, 1100.00, 170.00),
    ('imagen 6.JPG', 'Manualidad 6', 'Descripción de la manualidad 6', 'Centros de mesas',100, 1600.00, 250.00),
    ('imagen 7.JPEG', 'Manualidad 7', 'Descripción de la manualidad 7', 'Letras personalizadas',100, 1350.00, 210.00),
    ('imagen 8.JPEG', 'Manualidad 8', 'Descripción de la manualidad 8', 'Cajas personalizadas',0, 1250.00, 190.00),
    ('imagen 9.JPEG', 'Manualidad 9', 'Descripción de la manualidad 9', 'Arcos para eventos',100, 1400.00, 230.00),
    ('imagen 10.JPEG', 'Manualidad 10', 'Descripción de la manualidad 10', 'Fotografía personalizadas',100, 1550.00, 240.00),
    ('imagen 11.jpg', 'Manualidad 11', 'Descripción de la manualidad 11', 'Centros de mesas',100, 1450.00, 210.00),
    ('imagen 12.JPEG', 'Manualidad 12', 'Descripción de la manualidad 12', 'Letras personalizadas',100, 1200.00, 180.00),
    ('imagen 13.JPEG', 'Manualidad 13', 'Descripción de la manualidad 13', 'Cajas personalizadas',100, 1300.00, 220.00),
    ('imagen 14.JPEG', 'Manualidad 14', 'Descripción de la manualidad 14', 'Arcos para eventos',100, 1100.00, 170.00),
    ('imagen 15.JPEG', 'Manualidad 15', 'Descripción de la manualidad 15', 'Fotografía personalizadas',100, 1600.00, 250.00),
    ('imagen 16.JPEG', 'Manualidad 16', 'Descripción de la manualidad 16', 'Centros de mesas',100, 1350.00, 210.00),
    ('imagen 17.jpg', 'Manualidad 17', 'Descripción de la manualidad 17', 'Letras personalizadas',100, 1250.00, 190.00),
    ('imagen 18.jpg', 'Manualidad 18', 'Descripción de la manualidad 18', 'Cajas personalizadas',100, 1400.00, 230.00),
    ('imagen 19.JPEG', 'Manualidad 19', 'Descripción de la manualidad 19', 'Arcos para eventos',100, 1550.00, 240.00),
    ('imagen 20.jpg', 'Manualidad 20', 'Descripción de la manualidad 20', 'Fotografía personalizadas',100, 1450.00, 210.00),
    ('imagen 1.jpg', 'Manualidad 21', 'Descripción de la manualidad 21', 'Centros de mesas',100, 1200.00, 180.00),
    ('imagen 2.JPEG', 'Manualidad 22', 'Descripción de la manualidad 22', 'Letras personalizadas',100, 1300.00, 220.00),
    ('imagen 3.JPEG', 'Manualidad 23', 'Descripción de la manualidad 23', 'Cajas personalizadas',100, 1100.00, 170.00),
    ('imagen 4.JPEG', 'Manualidad 24', 'Descripción de la manualidad 24', 'Arcos para eventos',0, 1600.00, 250.00),
    ('imagen 5.jpg', 'Manualidad 25', 'Descripción de la manualidad 25', 'Fotografía personalizadas',95, 1350.00, 210.00),
    ('imagen 6.JPG', 'Manualidad 26', 'Descripción de la manualidad 26', 'Centros de mesas',100, 1000.00, 250.00),
    ('imagen 7.JPEG', 'Manualidad 27', 'Descripción de la manualidad 27', 'Letras personalizadas',100, 1450.00, 210.00),
    ('imagen 8.JPEG', 'Manualidad 28', 'Descripción de la manualidad 28', 'Cajas personalizadas',0, 1350.00, 190.00),
    ('imagen 9.JPEG', 'Manualidad 29', 'Descripción de la manualidad 29', 'Arcos para eventos',100, 1800.00, 230.00),
    ('imagen 10.JPEG', 'Manualidad 30', 'Descripción de la manualidad 30', 'Fotografía personalizadas',100, 1650.00, 240.00);

INSERT INTO detalles_manualidades (manualidad_id, material_id, cantidad, precio)
VALUES
    (1, 5, 5, 2005.11),
    (1, 4, 3, 11005.00),
    (1, 6, 2, 12.00),
    (2, 2, 2, 10.00),
    (2, 4, 1, 8.00),
    (2, 5, 4, 20.00),
    (3, 1, 3, 15.00),
    (3, 3, 2, 12.00),
    (4, 2, 1, 5.00),
    (4, 4, 3, 15.00),
    (5, 4, 3, 15.00),
    (6, 5, 6, 30.00),
    (7, 2, 8, 40.00),
    (8, 3, 4, 20.00),
    (9, 1, 15, 75.00),
    (10, 5, 2, 10.00),
    (11, 2, 6, 30.00),
    (12, 3, 3, 15.00),
    (13, 4, 7, 35.00),
    (14, 5, 5, 25.00),
    (15, 1, 10, 50.00),
    (16, 2, 4, 20.00),
    (17, 3, 9, 45.00),
    (18, 4, 2, 10.00),
    (19, 5, 6, 30.00),
    (20, 1, 8, 40.00),
    (21, 2, 3, 15.00),
    (22, 3, 7, 35.00),
    (23, 4, 5, 25.00),
    (24, 5, 9, 45.00),
    (25, 1, 4, 20.00),
	(26, 1, 8, 40.00),
    (27, 2, 3, 15.00),
    (28, 3, 7, 35.00),
    (29, 4, 5, 25.00),
    (30, 5, 9, 45.00),
    (30, 1, 4, 20.00);

INSERT INTO globos (imagen, codigo_globo, tipo, material, para, tamano, color, forma, cantidad_paquete, porta_globo, cantidad, precio)
VALUES 
('imagen 1.jpg','GB01', 'Cumpleaños', 'Latex', 'Aire', '12 x 12 cm', 'Rojo', 'Redondo', 10, 1, 10, 10.00),
('imagen 2.jpg','GB02', 'Graduación', 'Aluminio', 'Helio', '14 x 22 cm', 'Dorado', 'Estrella', 5, 0, 20, 10.00),
('imagen 3.jpg','GB03', 'Cumpleaños', 'Latex', 'Aire', '10 x 10 cm', 'Blanco', 'Redondo', 8, 1, 15, 8.50),
('imagen 4.jpg','GB04', 'Boda', 'Aluminio', 'Helio', '21 x 45 cm', 'Plateado', 'Corazón', 3, 1, 12, 15.00),
('imagen 5.jpg','GB05', 'Baby Shower', 'Latex', 'Aire', '12 x 12 cm', 'Azul', 'Redondo', 12, 1, 18, 7.50),
('imagen 6.jpg','GB06', 'San Valentín', 'Latex', 'Aire', '12 x 12 cm', 'Rojo', 'Redondo', 10, 1, 25, 9.00),
('imagen 7.jpg','GB07', 'Cumpleaños', 'Latex', 'Helio', '18 x 17 cm', 'Multicolor', 'Estrella', 5, 0, 30, 12.00),
('imagen 8.jpg','GB08', 'Graduación', 'Latex', 'Aire', '12 x 12 cm', 'Dorado', 'Redondo', 8, 1, 10, 8.00),
('imagen 9.jpeg','GB09', 'Boda', 'Aluminio', 'Helio', '12 x 12 cm', 'Blanco', 'Corazón', 3, 1, 20, 13.00),
('imagen 10.jpg','GB10', 'Baby Shower', 'Latex', 'Aire', '10 x 10 cm', 'Rosa', 'Redondo', 12, 1, 15, 7.00),
('imagen 11.jpg','GB11', 'Cumpleaños', 'Latex', 'Aire', '14 x 14 cm', 'Verde', 'Redondo', 10, 1, 10, 10.00),
('imagen 12.jpg','GB12', 'Graduación', 'Aluminio', 'Helio', '18 x 22 cm', 'Amarillo', 'Estrella', 5, 0, 20, 10.00),
('imagen 13.jpg','GB13', 'Cumpleaños', 'Aluminio', 'Aire', '12 x 14 cm', 'Azul', 'Redondo', 8, 1, 15, 8.50),
('imagen 14.JPG','GB14', 'Graduación', 'Aluminio', 'Helio', '22 x 25 cm', 'Morado', 'Corazón', 3, 1, 12, 15.00),
('imagen 15.jpg','GB15', 'Baby Shower', 'Latex', 'Aire', '13 x 13 cm', 'Caqui', 'Redondo', 12, 1, 18, 7.50),
('imagen 16.jpg','GB16', 'San Valentín', 'Aluminio', 'Aire', '16 x 12 cm', 'Multicolor', 'Redondo', 10, 1, 25, 9.00),
('imagen 17.jpg','GB17', 'Cumpleaños', 'Latex', 'Helio', '17 x 17 cm', 'Anaranjado', 'Estrella', 5, 0, 30, 12.00),
('imagen 18.jpg','GB18', 'Cumpleaños', 'Latex', 'Aire', '18 x 12 cm', 'Blanco', 'Redondo', 8, 1, 10, 8.00),
('imagen 19.jpg','GB19', 'Baby Shower', 'Aluminio', 'Helio', '12 x 12 cm', 'Rosa', 'Corazón', 3, 1, 20, 13.00),
('imagen 20.jpg','GB20', 'Baby Shower', 'Latex', 'Aire', '10 x 10 cm', 'Plateado', 'Redondo', 10, 1, 15, 7.00),
('imagen 11.jpg','GB21', 'Baby Shower', 'Aluminio', 'Aire', '12 x 12 cm', 'Azul', 'Redondo', 10, 1, 18, 7.50),
('imagen 12.jpg','GB22', 'San Valentín', 'Aluminio', 'Aire', '12 x 12 cm', 'Rojo', 'Redondo', 10, 1, 25, 9.00),
('imagen 13.jpg','GB23', 'Cumpleaños', 'Latex', 'Helio', '18 x 17 cm', 'Multicolor', 'Estrella', 5, 0, 30, 12.00),
('imagen 14.jpg','GB24', 'Graduación', 'Latex', 'Aire', '12 x 12 cm', 'Dorado', 'Redondo', 18, 1, 10, 8.00),
('imagen 15.JPG','GB25', 'Boda', 'Aluminio', 'Helio', '12 x 12 cm', 'Blanco', 'Corazón', 13, 1, 20, 13.00),
('imagen 16.jpg','GB26', 'Baby Shower', 'Latex', 'Aire', '10 x 10 cm', 'Rosa', 'Redondo', 12, 1, 15, 7.00),
('imagen 17.jpg','GB27', 'Cumpleaños', 'Latex', 'Aire', '14 x 14 cm', 'Verde', 'Redondo', 13, 1, 10, 10.00),
('imagen 18.jpg','GB28', 'Graduación', 'Aluminio', 'Helio', '18 x 22 cm', 'Amarillo', 'Estrella', 15, 0, 20, 10.00),
('imagen 19.jpg','GB29', 'Cumpleaños', 'Aluminio', 'Aire', '12 x 14 cm', 'Azul', 'Redondo', 28, 1, 15, 8.50),
('imagen 20.jpg','GB30', 'Graduación', 'Aluminio', 'Helio', '22 x 25 cm', 'Morado', 'Corazón', 13, 1, 12, 15.00);

INSERT INTO desayunos (imagen, nombre, descripcion, proveedor_id, cantidad, precio_desayuno, mano_obra)
VALUES 
('imagen 1.jpg', 'Desayuno 1', 'Descripción del desayuno 1', 1,100, 100.00, 20.00),
('imagen 2.jpeg', 'Desayuno 2', 'Descripción del desayuno 2', 2,100, 120.00, 25.00),
('imagen 3.jpg', 'Desayuno 3', 'Descripción del desayuno 3', 3,90, 90.00, 15.00),
('imagen 4.jpg', 'Desayuno 4', 'Descripción del desayuno 4', 4,100, 110.00, 22.00),
('imagen 5.jpg', 'Desayuno 5', 'Descripción del desayuno 5', 5,100, 95.00, 18.00),
('imagen 6.jpg', 'Desayuno 6', 'Descripción del desayuno 6', 3,1, 80.00, 15.00),
('imagen 7.jpg', 'Desayuno 7', 'Descripción del desayuno 7', 2,100, 115.00, 20.00),
('imagen 8.jpg', 'Desayuno 8', 'Descripción del desayuno 8', 1,100, 90.00, 18.00),
('imagen 9.jpeg', 'Desayuno 9', 'Descripción del desayuno 9', 5,100, 105.00, 25.00),
('imagen 10.jpg', 'Desayuno 10', 'Descripción del desayuno 10', 1, 40, 95.00, 20.00),
('imagen 11.jpg', 'Desayuno 11', 'Descripción del desayuno 11', 2, 300, 85.00, 15.00),
('imagen 12.jpg', 'Desayuno 12', 'Descripción del desayuno 12', 13, 220, 120.00, 25.00),
('imagen 13.jpg', 'Desayuno 13', 'Descripción del desayuno 13', 1,100, 100.00, 20.00),
('imagen 14.jpg', 'Desayuno 14', 'Descripción del desayuno 14', 4,100, 110.00, 22.00),
('imagen 15.jpg', 'Desayuno 15', 'Descripción del desayuno 15', 5,100, 95.00, 18.00),
('imagen 16.jpg', 'Desayuno 16', 'Descripción del desayuno 16', 3,100, 80.00, 15.00),
('imagen 17.jpg', 'Desayuno 17', 'Descripción del desayuno 17', 2,100, 115.00, 20.00),
('imagen 18.jpg', 'Desayuno 18', 'Descripción del desayuno 18', 1,100, 90.00, 18.00),
('imagen 19.jpg', 'Desayuno 19', 'Descripción del desayuno 19', 5,100, 105.00, 25.00),
('imagen 20.jpg', 'Desayuno 20', 'Descripción del desayuno 20', 4,100, 95.00, 20.00),
('imagen 11.jpg', 'Desayuno 21', 'Descripción del desayuno 21', 15, 30, 85.00, 15.00),
('imagen 12.jpg', 'Desayuno 22', 'Descripción del desayuno 22', 16, 20, 120.00, 25.00),
('imagen 13.jpg', 'Desayuno 23', 'Descripción del desayuno 23', 17,150, 100.00, 20.00),
('imagen 14.jpg', 'Desayuno 24', 'Descripción del desayuno 24', 4,150, 110.00, 22.00),
('imagen 15.jpg', 'Desayuno 25', 'Descripción del desayuno 25', 5,150, 95.00, 18.00),
('imagen 16.jpg', 'Desayuno 26', 'Descripción del desayuno 26', 3,150, 80.00, 15.00),
('imagen 17.jpg', 'Desayuno 27', 'Descripción del desayuno 27', 2,150, 115.00, 20.00),
('imagen 18.jpg', 'Desayuno 28', 'Descripción del desayuno 28', 1,150, 90.00, 18.00),
('imagen 19.jpg', 'Desayuno 29', 'Descripción del desayuno 29', 5,150, 105.00, 25.00),
('imagen 20.jpg', 'Desayuno 30', 'Descripción del desayuno 30', 4,150, 95.00, 20.00);

INSERT INTO detalles_desayunos (desayuno_id, tipo_detalle, detalle_id, cantidad, precio)
VALUES 
(1, 'material', 1, 2, 50.00),
(1, 'globo', 2, 1, 30.00),
(1, 'floristeria', 3, 1, 50.00),
(1, 'tarjeta', 1, 2, 20.00),
(2, 'floristeria', 2, 1, 30.00),
(3, 'material', 1, 1, 36.00),
(4, 'floristeria', 4, 1, 38.00),
(5, 'material', 1, 1, 42.00),
(5, 'tarjeta', 1, 2, 20.00),
(5, 'globo', 4, 1, 15.00),
(6, 'material', 1, 1, 40.00),
(6, 'globo', 2, 1, 30.00),
(7, 'floristeria', 4, 1, 45.00),
(8, 'material', 1, 1, 36.00),
(8, 'tarjeta', 1, 1, 18.00),
(9, 'material', 1, 2, 60.00),
(9, 'globo', 4, 1, 15.00),
(10, 'material', 1, 1, 42.00),
(11, 'tarjeta', 1, 1, 25.00),
(11, 'globo', 2, 1, 30.00),
(12, 'material', 1, 2, 50.00),
(13, 'globo', 2, 1, 30.00),
(14, 'floristeria', 4, 1, 38.00),
(15, 'material', 1, 1, 42.00),
(15, 'tarjeta', 1, 2, 20.00),
(15, 'globo', 4, 1, 15.00),
(16, 'material', 1, 1, 40.00),
(17, 'floristeria', 4, 1, 45.00),
(18, 'material', 1, 1, 36.00),
(19, 'tarjeta', 1, 1, 20.00),
(20, 'material', 1, 2, 60.00),
(20, 'globo', 4, 1, 15.00),
(21, 'tarjeta', 1, 1, 25.00),
(21, 'globo', 2, 1, 30.00),
(22, 'material', 1, 2, 50.00),
(23, 'globo', 2, 1, 30.00),
(24, 'floristeria', 4, 1, 38.00),
(25, 'material', 1, 1, 42.00),
(25, 'tarjeta', 1, 2, 20.00),
(25, 'globo', 4, 1, 15.00),
(26, 'material', 1, 1, 40.00),
(27, 'floristeria', 4, 1, 45.00),
(28, 'material', 1, 1, 36.00),
(29, 'tarjeta', 1, 1, 20.00),
(30, 'material', 1, 2, 60.00),
(30, 'globo', 4, 1, 15.00);

INSERT INTO mobiliario (nombreMobiliario, color, descripcion, cantidad, precioUnitario, image) VALUES
('Silla de Comedor', 'Blanco', 'Silla de comedor moderna de color blanco', 50, 120.00, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Sofá de Tela', 'Gris', 'Sofá de tela con estructura de madera', 20, 550.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Mesa de Centro', 'Madera Natural', 'Mesa de centro de estilo rústico', 30, 180.50, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Cama Doble', 'Negro', 'Cama doble con cabecera acolchada', 10, 420.75, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Armario de Roble', 'Roble Claro', 'Armario de roble con múltiples compartimentos', 15, 320.49, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Escritorio de Oficina', 'Negro', 'Escritorio de oficina con cajones', 25, 270.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Mesa de Comedor Extensible', 'Nogal', 'Mesa de comedor extensible de madera de nogal', 12, 680.00, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Silla de Oficina', 'Negro', 'Silla de oficina ergonómica', 40, 220.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Sillón Reclinable', 'Gris Oscuro', 'Sillón reclinable con reposapiés', 8, 480.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Mesa de Noche', 'Blanco', 'Mesa de noche con cajón', 18, 130.75, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Silla de Comedor', 'Marrón', 'Silla de comedor de cuero', 28, 190.50, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Mesa de Centro', 'Negro', 'Mesa de centro moderna de vidrio', 35, 250.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Cama King-Size', 'Blanco', 'Cama king-size con dosel', 7, 620.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Armario de Pino', 'Pino Natural', 'Armario de pino con espejo', 11, 360.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Escritorio de Estudio', 'Blanco', 'Escritorio de estudio con estantes', 23, 290.50, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Mesa de Comedor Redonda', 'Nogal', 'Mesa de comedor redonda de nogal', 14, 380.75, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Silla de Oficina Ergonómica', 'Negro', 'Silla de oficina ergonómica con respaldo ajustable', 32, 350.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Sofá de Cuero', 'Marrón', 'Sofá de cuero con asientos reclinables', 9, 330.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Mesa de Noche', 'Roble', 'Mesa de noche de roble con cajón', 17, 280.75, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Silla de Comedor', 'Gris Claro', 'Silla de comedor de tela con patas de metal', 22, 260.50, 'silla5.jpg'),
('Sofá de Terciopelo', 'Azul', 'Sofá de terciopelo con diseño contemporáneo', 6, 310.99, 'sofa3.jpg'),
('Mesa de Centro Ovalada', 'Negro', 'Mesa de centro ovalada de vidrio', 13, 1300.99, 'mesa7.jpg'),
('Cama Individual', 'Blanco', 'Cama individual con diseño minimalista', 16, 220.75, 'cama3.jpg'),
('Armario de Madera Maciza', 'Nogal', 'Armario de madera maciza con espejo', 19, 350.50, 'armario3.jpg'),
('Escritorio de Esquina', 'Negro', 'Escritorio de esquina con estantes', 21, 290.99, 'escritorio3.jpg'),
('Mesa de Comedor de Vidrio', 'Transparente', 'Mesa de comedor de vidrio templado', 24, 1500.00, 'mesa8.jpg'),
('Silla de Comedor Moderna', 'Blanco', 'Silla de comedor moderna con respaldo alto', 14, 450.75, 'silla6.jpg'),
('Sillón de Cuero', 'Negro', 'Sillón de cuero con diseño elegante', 18, 500.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Mesa de Noche de Diseño', 'Blanco', 'Mesa de noche de diseño contemporáneo', 27, 390.99, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg'),
('Silla de Oficina Ejecutiva', 'Negro', 'Silla de oficina ejecutiva de cuero', 20, 270.49, 'C:\\Users\\Skarleth Alvarado\\Documents\\rosa.jpg');

INSERT INTO pedidos ( codigo_pedido, fecha_pedido, fecha_entrega, descripcion, cliente_id, entrega, precio_envio, usuario_id, estado) VALUES
('PD22092023-173901-00001', '2023-09-22', '2023-09-24', 'Breve descripción del pedido 00001', 1, 'Domicilio', 120.00, 2, 'Proceso'), 
('PD25092023-133901-00002', '2023-09-25', '2023-09-26', 'Breve descripción del pedido 00002', 3, 'Domicilio', 110.00, 3, 'Proceso'), 
('PD25092023-133901-00003', '2023-09-25', '2023-09-27', 'Breve descripción del pedido 00003', 6, 'Domicilio', 120.00, 4, 'Proceso'), 
('PD25092023-143901-00004', '2023-09-25', '2023-09-28', 'Breve descripción del pedido 00004', 7, 'Tienda', 0.00, 4, 'Proceso'), 
('PD25092023-173901-00005', '2023-09-25', '2023-09-28', 'Breve descripción del pedido 00005', 8, 'Tienda', 0.00, 2, 'Proceso'), 
('PD25092023-183901-00006', '2023-09-25', '2023-09-29', 'Breve descripción del pedido 00006', 8, 'Domicilio', 110.00, 4, 'Proceso'),
('PD25092023-183901-00007', '2023-09-25', '2023-09-29', 'Breve descripción del pedido 00007', 2, 'Domicilio', 110.00, 2, 'Proceso'),
('PD25092023-183901-00008', '2023-09-25', '2023-09-29', 'Breve descripción del pedido 00008', 5, 'Tienda', 0.00, 2, 'Proceso'),
('PD25092023-183901-00009', '2023-09-25', '2023-09-29', 'Breve descripción del pedido 00009', 4, 'Domicilio', 110.00, 3, 'Proceso'),
('PD25092023-103901-00010', '2023-09-25', '2023-09-29', 'Breve descripción del pedido 00010', 10, 'Domicilio', 120.00, 4, 'Proceso'),
('PD26092023-103901-00011', '2023-09-26', '2023-09-29', 'Breve descripción del pedido 00011', 11, 'Tienda', 0.00, 2, 'Proceso'),
('PD01102023-103901-00012', '2023-10-01', '2023-10-21', 'Breve descripción del pedido 00012', 12, 'Tienda', 0.00, 3, 'Proceso'),
('PD01102023-103901-00013', '2023-10-01', '2023-10-22', 'Breve descripción del pedido 00013', 13, 'Domicilio', 110.00, 4, 'Proceso'),
('PD01102023-113901-00014', '2023-10-01', '2023-10-05', 'Breve descripción del pedido 00014', 14, 'Domicilio', 110.00, 2, 'Proceso'),
('PD02102023-111901-00015', '2023-10-02', '2023-10-09', 'Breve descripción del pedido 00015', 15, 'Tienda', 0.00, 2, 'Proceso'),
('PD05102023-122901-00016', '2023-10-05', '2023-10-21', 'Breve descripción del pedido 00016', 16, 'Tienda', 0.00, 3, 'Proceso'),
('PD05102023-931901-00017', '2023-10-05', '2023-10-21', 'Breve descripción del pedido 00017', 17, 'Domicilio', 120.00, 4, 'Proceso'),
('PD05102023-131901-00018', '2023-10-05', '2023-10-24', 'Breve descripción del pedido 0018', 18, 'Domicilio', 110.00, 2, 'Proceso'),
('PD05102023-131901-00019', '2023-10-05', '2023-10-26', 'Breve descripción del pedido 00019', 14, 'Tienda', 0.00, 2, 'Proceso'),
('PD05102023-132301-00020', '2023-10-05', '2023-10-23', 'Breve descripción del pedido 00020', 15, 'Tienda', 0.00, 3, 'Proceso'),
('PD05102023-142201-00021', '2023-10-05', '2023-10-22', 'Breve descripción del pedido 00021', 15, 'Domicilio', 120.00, 2, 'Proceso'),
('PD06102023-143101-00022', '2023-10-06', '2023-10-07', 'Breve descripción del pedido 00022', 13, 'Domicilio', 120.00, 3, 'Proceso'),
('PD01112023-153901-00023', '2023-11-01', '2023-12-03', 'Breve descripción del pedido 00023', 12, 'Tienda', 0.00, 4, 'Proceso'),
('PD02112023-163901-00024', '2023-11-02', '2023-12-10', 'Breve descripción del pedido 00024', 17, 'Tienda', 0.00, 2, 'Proceso'),
('PD02112023-173901-00025', '2023-11-02', '2023-12-12', 'Breve descripción del pedido 00025', 20, 'Domicilio', 120.00, 3, 'Proceso');

INSERT INTO detalles_pedidos (pedido_id, tipo_detalle, detalle_id, cantidad, precio)
VALUES 
(1, 'desayuno', 1, 2, 100.00),
(1, 'manualidad', 2, 1, 1000.00),
(1, 'floristeria', 3, 1, 75.20),
(1, 'manualidad', 1, 2, 1500.00),
(2, 'floristeria', 2, 1, 35.50),
(3, 'material', 1, 1, 150.00),
(4, 'floristeria', 4, 1, 20.00),
(5, 'material', 1, 1, 150.00),
(5, 'arreglo', 1, 2, 1110.00),
(5, 'manualidad', 4, 1, 1300.00),
(6, 'material', 1, 1, 150.00),
(6, 'manualidad', 2, 1, 1000.00),
(7, 'floristeria', 4, 1, 20.00),
(8, 'material', 1, 1, 150.00),
(8, 'manualidad', 1, 1, 1500.00),
(9, 'material', 1, 2, 150.00),
(9, 'manualidad', 4, 1, 1300.00),
(10, 'material', 1, 1, 150.00),
(11, 'manualidad', 1, 1, 1500.00),
(11, 'manualidad', 2, 1, 1000.00),
(12, 'material', 1, 2, 150.00),
(13, 'manualidad', 2, 1, 1000.00),
(14, 'floristeria', 4, 1, 20.00),
(15, 'material', 1, 1, 42.00),
(15, 'manualidad', 1, 2, 20.00),
(15, 'manualidad', 4, 1, 15.00),
(16, 'material', 1, 1, 40.00),
(17, 'floristeria', 4, 1, 45.00),
(18, 'material', 1, 1, 36.00),
(19, 'manualidad', 1, 1, 20.00),
(20, 'material', 1, 2, 60.00),
(20, 'manualidad', 4, 1, 15.00),
(21, 'arreglo', 1, 1, 25.00),
(21, 'manualidad', 2, 1, 30.00),
(22, 'material', 1, 2, 50.00),
(23, 'manualidad', 2, 1, 30.00),
(24, 'floristeria', 4, 1, 38.00),
(25, 'material', 1, 1, 150.00),
(25, 'arreglo', 1, 2, 1110.00),
(25, 'manualidad', 4, 1, 1300.00);

INSERT INTO eventos (codigo_evento, cliente_id, direccion, tipo, fecha, inicio, fin, usuario_id, estado) 
VALUES 
('EV21052023-130159-00001', 1, 'Dirección del Evento 1', 'Boda', '2023-09-27', '10:00:00', '14:00:00', 1, 'Pendiente'), 
('EV21052023-130604-00002', 2, 'Dirección del Evento 2', '15 años', '2023-09-27', '11:00:00', '15:00:00', 1, 'Pendiente'), 
('EV22052023-140805-00003', 3, 'Dirección del Evento 3', 'Cumpleaños', '2023-09-27', '12:00:00', '16:00:00', 1, 'Pendiente'), 
('EV28092023-130000-00004', 4, 'Dirección del Evento 4', 'Cumpleaños', '2023-09-28', '13:00:00', '17:00:00', 1, 'Pendiente'), 
('EV28092023-130000-00004', 4, 'Dirección del Evento 4', 'Cumpleaños', '2023-09-28', '13:00:00', '17:00:00', 1, 'Pendiente'),
('EV01102023-140000-00005', 5, 'Dirección del Evento 5', 'Cumpleaños', '2023-10-01', '14:00:00', '18:00:00', 1, 'Pendiente'),
('EV02102023-150000-00006', 6, 'Dirección del Evento 6', 'Cumpleaños', '2023-10-02', '15:00:00', '19:00:00', 1, 'Pendiente'),
('EV03102023-160000-00007', 7, 'Dirección del Evento 7', 'Cumpleaños', '2023-10-03', '16:00:00', '20:00:00', 1, 'Pendiente'),
('EV04102023-170000-00008', 8, 'Dirección del Evento 8', 'Baby Shower', '2023-10-04', '17:00:00', '21:00:00', 1, 'Pendiente'),
('EV05102023-180000-00009', 9, 'Dirección del Evento 9', 'Cumpleaños', '2023-10-05', '18:00:00', '22:00:00', 1, 'Pendiente'),
('EV06102023-190000-00010', 10, 'Dirección del Evento 10', 'Cumpleaños', '2023-10-06', '19:00:00', '23:00:00', 1, 'Pendiente'),
('EV07102023-100000-00011', 11, 'Dirección del Evento 11', 'Cumpleaños', '2023-10-07', '10:00:00', '14:00:00', 1, 'Pendiente'),
('EV08102023-110000-00012', 12, 'Dirección del Evento 12', 'Cumpleaños', '2023-10-08', '11:00:00', '15:00:00', 1, 'Pendiente'),
('EV09102023-120000-00013', 13, 'Dirección del Evento 13', 'Cumpleaños', '2023-10-09', '12:00:00', '16:00:00', 1, 'Pendiente'),
('EV10102023-130000-00014', 14, 'Dirección del Evento 14', 'Cumpleaños', '2023-10-10', '13:00:00', '17:00:00', 1, 'Pendiente'),
('EV11102023-140000-00015', 15, 'Dirección del Evento 15', 'Cumpleaños', '2023-10-11', '14:00:00', '18:00:00', 1, 'Pendiente'),
('EV12102023-150000-00016', 16, 'Dirección del Evento 16', 'Cumpleaños', '2023-10-12', '15:00:00', '19:00:00', 1, 'Pendiente'),
('EV13102023-160000-00017', 17, 'Dirección del Evento 17', '15 años', '2023-10-13', '16:00:00', '20:00:00', 1, 'Pendiente'),
('EV14102023-170000-00018', 18, 'Dirección del Evento 18', 'Despedida de Soltero', '2023-10-14', '17:00:00', '21:00:00', 1, 'Pendiente'),
('EV15102023-180000-00019', 19, 'Dirección del Evento 19', '15 años', '2023-10-15', '18:00:00', '22:00:00', 1, 'Pendiente'),
('EV16102023-190000-00020', 20, 'Dirección del Evento 20', 'Primera Comunión', '2023-10-16', '19:00:00', '23:00:00', 1, 'Pendiente'),
('EV17102023-100000-00021', 21, 'Dirección del Evento 21', 'Bautismo', '2023-10-17', '10:00:00', '14:00:00', 1, 'Pendiente'),
('EV18102023-110000-00022', 22, 'Dirección del Evento 22', 'Boda', '2023-10-18', '11:00:00', '15:00:00', 1, 'Pendiente'),
('EV19102023-120000-00023', 23, 'Dirección del Evento 23', 'Graduación', '2023-10-19', '12:00:00', '16:00:00', 1, 'Pendiente'),
('EV20102023-130000-00024', 24, 'Dirección del Evento 24', 'Graduación', '2023-10-20', '13:00:00', '17:00:00', 1, 'Pendiente'),
('EV21102023-140000-00025', 25, 'Dirección del Evento 25', 'Boda', '2023-10-21', '14:00:00', '18:00:00', 1, 'Pendiente'),
('EV22102023-150000-00026', 26, 'Dirección del Evento 26', 'Boda', '2023-10-22', '15:00:00', '19:00:00', 1, 'Pendiente'),
('EV23102023-160000-00027', 27, 'Dirección del Evento 27', 'Graduación', '2023-10-23', '16:00:00', '20:00:00', 1, 'Pendiente'),
('EV24102023-170000-00028', 28, 'Dirección del Evento 28', '15 años', '2023-10-24', '17:00:00', '21:00:00', 1, 'Pendiente'),
('EV24102023-170422-00029',29, 'Dirección del Evento 29', 'Cumpleaños', '2023-10-25', '18:00:00', '22:00:00', 1, 'Pendiente'),
('EV24102023-170550-00030',30, 'Dirección del Evento 30', 'Cumpleaños', '2023-10-26', '19:00:00', '23:00:00', 1, 'Pendiente');

INSERT INTO detalles_eventos (evento_id, tipo_detalle, detalle_id, cantidad, precio)
VALUES 
(1, 'mobiliario', 1, 2, 120.00),
(1, 'globo', 2, 1, 10.00),
(1, 'floristeria', 3, 1, 75.20),
(1, 'manualidad', 1, 2, 1500.00),
(1, 'arreglo', 1, 2, 1110.00),
(2, 'floristeria', 2, 1, 35.50),
(3, 'mobiliario', 1, 1, 120.00),
(4, 'floristeria', 4, 1, 20.00),
(5, 'mobiliario', 1, 1, 120.00),
(5, 'arreglo', 1, 2, 1110.00),
(5, 'globo', 4, 1, 15.00),
(6, 'mobiliario', 1, 1, 120.00),
(6, 'globo', 2, 1, 10.00),
(7, 'floristeria', 4, 1, 20.00),
(8, 'mobiliario', 1, 1, 120.00),
(8, 'manualidad', 1, 1, 1500.00),
(9, 'mobiliario', 1, 2, 120.00),
(9, 'globo', 4, 1, 15.00),
(10, 'mobiliario', 1, 1, 120.00),
(11, 'manualidad', 1, 1, 1500.00),
(11, 'globo', 2, 1, 10.00),
(12, 'mobiliario', 1, 2, 120.00),
(13, 'globo', 2, 1, 10.00),
(14, 'floristeria', 4, 1, 20.00),
(15, 'mobiliario', 1, 1, 120.00),
(15, 'manualidad', 1, 2, 1500.00),
(15, 'globo', 4, 1, 15.00),
(16, 'mobiliario', 1, 1, 120.00),
(17, 'floristeria', 4, 1, 20.00),
(18, 'mobiliario', 1, 1, 120.00),
(19, 'manualidad', 1, 1, 1500.00),
(20, 'mobiliario', 1, 2, 120.00),
(20, 'globo', 4, 1, 15.00),
(21, 'arreglo', 1, 1, 1110.00),
(21, 'globo', 2, 1, 10.00),
(22, 'mobiliario', 1, 2, 120.00),
(23, 'globo', 2, 1, 10.00),
(24, 'floristeria', 4, 1, 20.00),
(25, 'mobiliario', 1, 1, 120.00),
(25, 'arreglo', 1, 2, 1110.00),
(25, 'globo', 4, 1, 15.00),
(26, 'manualidad', 1, 1, 1500.00),
(27, 'floristeria', 4, 1, 20.00),
(28, 'mobiliario', 1, 1, 120.00),
(29, 'manualidad', 1, 1, 1500.00),
(30, 'arreglo', 1, 2, 1110.00),
(30, 'globo', 4, 1, 15.00);

INSERT INTO promociones (descripcion, inicio, fin)
VALUES 
('Promoción 1', '2023-09-27','2023-09-29'),
('Promoción 2', '2023-09-27','2023-09-29'),
('Promoción 3', '2023-09-27','2023-09-29'),
('Promoción 4', '2023-09-27','2023-09-29'),
('Promoción 5', '2023-09-27','2023-09-29'),
('Promoción 6', '2023-09-27','2023-09-29'),
('Promoción 7', '2023-09-27','2023-09-29'),
('Promoción 8', '2023-09-27','2023-09-29'),
('Promoción 9', '2023-09-27','2023-09-29'),
('Promoción 10', '2023-09-27','2023-10-29'),
('Promoción 11', '2023-09-27','2023-10-29'),
('Promoción 12', '2023-09-27','2023-10-29'),
('Promoción 13', '2023-09-27','2023-11-24'),
('Promoción 14', '2023-09-27','2023-11-25'),
('Promoción 15', '2023-09-27','2023-12-25'),
('Promoción 16', '2023-09-27','2023-09-29'),
('Promoción 17', '2023-09-27','2023-09-29'),
('Promoción 18', '2023-09-27','2023-09-29'),
('Promoción 19', '2023-09-27','2023-09-29'),
('Promoción 20', '2023-09-27','2023-09-29'),
('Promoción 21', '2023-10-27','2023-10-29'),
('Promoción 22', '2023-10-27','2023-10-29'),
('Promoción 23', '2023-10-27','2023-10-29'),
('Promoción 24', '2023-10-27','2023-10-29'),
('Promoción 25', '2023-11-27','2023-11-29'),
('Promoción 26', '2023-11-27','2023-11-29'),
('Promoción 27', '2023-11-27','2023-11-29'),
('Promoción 28', '2023-11-27','2023-11-29'),
('Promoción 29', '2023-12-01','2023-12-10'),
('Promoción 30', '2023-12-01','2023-12-10');

INSERT INTO detalles_promociones (promocion_id, tipo_detalle, detalle_id, cantidad, precio, promocion)
VALUES 
(1, 'tarjeta', 1, 2, 50.00, 45.00),
(1, 'globo', 2, 1, 30.00, 25.00),
(1, 'floristeria', 3, 1, 50.00, 45.00),
(1, 'manualidad', 1, 2, 20.00, 15.00),
(2, 'floristeria', 2, 1, 30.00, 30.00),
(3, 'mobiliario', 1, 1, 36.00, 34.00),
(4, 'floristeria', 4, 1, 38.00, 36.00),
(5, 'mobiliario', 1, 1, 42.00, 41.00),
(5, 'arreglo', 1, 2, 20.00, 18.00),
(5, 'globo', 4, 1, 15.00, 12.00),
(6, 'mobiliario', 1, 1, 40.00, 36.00),
(6, 'globo', 2, 1, 30.00, 28.00),
(7, 'floristeria', 4, 1, 45.00, 43.00),
(8, 'mobiliario', 1, 1, 36.00, 35.00),
(8, 'manualidad', 1, 1, 18.00, 15.00),
(9, 'mobiliario', 1, 2, 60.00, 55.00),
(9, 'globo', 4, 1, 15.00, 13.00),
(10, 'mobiliario', 1, 1, 42.00, 40.00),
(11, 'manualidad', 1, 1, 25.00, 24.00),
(11, 'globo', 2, 1, 30.00, 28.00),
(12, 'mobiliario', 1, 2, 50.00, 45.00),
(13, 'globo', 2, 1, 30.00, 25.00),
(14, 'floristeria', 4, 1, 38.00, 35.00),
(15, 'mobiliario', 1, 1, 42.00, 40.00),
(15, 'manualidad', 1, 2, 20.00, 15.00),
(15, 'globo', 4, 1, 15.00, 13.00),
(16, 'mobiliario', 1, 1, 40.00, 34.00),
(17, 'floristeria', 4, 1, 45.00, 40.00),
(18, 'mobiliario', 1, 1, 36.00, 34.00),
(19, 'manualidad', 1, 1, 20.00, 18.00),
(20, 'mobiliario', 1, 2, 60.00, 50.00),
(20, 'globo', 4, 1, 15.00, 14.00),
(21, 'arreglo', 1, 1, 25.00, 24.00),
(21, 'globo', 2, 1, 30.00, 28.00),
(22, 'mobiliario', 1, 2, 50.00, 34.00),
(23, 'globo', 2, 1, 30.00, 28.00),
(24, 'floristeria', 4, 1, 38.00, 36.00),
(25, 'mobiliario', 1, 1, 42.00, 32.00),
(25, 'arreglo', 1, 2, 20.00, 31.00),
(25, 'globo', 4, 1, 15.00, 14.00),
(26, 'manualidad', 1, 1, 40.00, 13.00),
(27, 'floristeria', 4, 1, 45.00, 18.00),
(28, 'mobiliario', 1, 1, 36.00, 13.00),
(29, 'manualidad', 1, 1, 20.00, 20.00),
(30, 'arreglo', 1, 2, 60.00, 45.00),
(30, 'globo', 4, 1, 15.00, 14.00);

INSERT INTO actividades (nombre, direccion, descripcion, fecha, inicio, fin)
VALUES
    ('Actividad 1', '567 Elm Street, Unit 6B, Hillside Heights, Miami, Florida, 33101-6789, Estados Unidos de América', 'Descripción de la Actividad 1', '2023-09-27', '13:00:00', '04:30:00'),
    ('Actividad 2', '123 Oak Avenue, Apt 2D, Riverside View, Los Angeles, California, 90001-2345, Estados Unidos de América', 'Descripción de la Actividad 2', '2023-09-27', '14:30:00', '17:00:00'),
    ('Actividad 3', '789 Maple Lane, Suite 5C, Mountain Side, Chicago, Illinois, 60601-4567, Estados Unidos de América', 'Descripción de la Actividad 3', '2023-09-27', '12:45:00', '14:15:00'),
    ('Actividad 4', '789 Willow Lane, Apt 4A, Parkside Park, Nueva York, Nueva York, 10001-5678, Estados Unidos de América', 'Descripción de la Actividad 4', '2023-09-30', '15:30:00', '17:00:00'),
    ('Actividad 5', '234 Birch Street, Suite 2D, Green Valley, San Francisco, California, 94101-3456, Estados Unidos de América', 'Descripción de la Actividad 5', '2023-10-24', '10:00:00', '12:00:00'),
    ('Actividad 6', '123 Spruce Avenue, Unit 7C, Lakeside Heights, Boston, Massachusetts, 02101-7890, Estados Unidos de América', 'Descripción de la Actividad 6', '2023-10-24', '14:00:00', '16:30:00'),
    ('Actividad 7', '456 Pine Road, Unit 3A, Lakeside Estates, Houston, Texas, 77001-1234, Estados Unidos de América', 'Descripción de la Actividad 7', '2023-10-24', '13:45:00', '15:15:00'),
    ('Actividad 8', '890 Oak Drive, Apt 5B, Riverside Park, Chicago, Illinois, 60601-2345, Estados Unidos de América', 'Descripción de la Actividad 8', '2023-10-24', '16:00:00', '18:00:00'),
    ('Actividad 9', '567 Cedar Avenue, Suite 1C, Mountain View, Los Ángeles, California, 90001-4567, Estados Unidos de América', 'Descripción de la Actividad 9', '2023-10-25', '11:30:00', '13:30:00'),
    ('Actividad 10', '789 Elm Street, Apt 2D, Hillside Heights, Miami, Florida, 33101-6789, Estados Unidos de América', 'Descripción de la Actividad 10', '2023-10-25', '14:30:00', '16:00:00'),
    ('Actividad 11', '234 Maple Lane, Unit 3A, Lakeside Estates, Houston, Texas, 77001-1234, Estados Unidos de América', 'Descripción de la Actividad 11', '2023-10-25', '15:00:00', '17:30:00'),
    ('Actividad 12', '456 Redwood Avenue, Suite 6B, Parkside Park, Nueva York, Nueva York, 10001-5678, Estados Unidos de América', 'Descripción de la Actividad 12', '2023-10-25', '13:15:00', '14:45:00'),
    ('Actividad 13', '123 Pine Street, Apt 4C, Oakside Manor, San Francisco, California, 94101-3456, Estados Unidos de América', 'Descripción de la Actividad 13', '2023-10-25', '10:00:00', '12:30:00'),
    ('Actividad 14', '890 Birch Avenue, Unit 8D, Meadowview Heights, Los Ángeles, California, 90001-2345, Estados Unidos de América', 'Descripción de la Actividad 14', '2023-10-26', '14:45:00', '17:00:00'),
    ('Actividad 15', '567 Cedar Avenue, Apt 3B, Hillside Heights, Miami, Florida, 33101-6789, Estados Unidos de América', 'Descripción de la Actividad 15', '2023-10-26', '12:30:00', '15:00:00'),
    ('Actividad 16', '789 Willow Lane, Suite 5A, Riverfront Park, Chicago, Illinois, 60601-6789, Estados Unidos de América', 'Descripción de la Actividad 16', '2023-10-26', '15:30:00', '18:00:00'),
    ('Actividad 17', '345 Maple Drive, Apt 2C, Lakeside Estates, Houston, Texas, 77001-5678, Estados Unidos de América', 'Descripción de la Actividad 17', '2023-10-30', '13:00:00', '14:30:00'),
    ('Actividad 18', '456 Oak Street, Apt 5D, Forest Hills, Nueva York, Nueva York, 10001-4567, Estados Unidos de América', 'Descripción de la Actividad 18', '2023-10-30', '09:30:00', '12:00:00'),
    ('Actividad 19', '789 Redwood Avenue, Suite 2B, Riverside Gardens, Los Ángeles, California, 90001-2345, Estados Unidos de América', 'Descripción de la Actividad 19', '2023-10-30', '13:15:00', '15:45:00'),
    ('Actividad 20', '234 Elm Street, Apt 3A, Hillside Heights, Miami, Florida, 33101-6789, Estados Unidos de América', 'Descripción de la Actividad 20', '2023-10-30', '10:45:00', '13:15:00'),
    ('Actividad 21', '890 Spruce Lane, Unit 6C, Pineview Estates, Chicago, Illinois, 60601-6789, Estados Unidos de América', 'Descripción de la Actividad 21', '2023-11-05', '16:30:00', '19:00:00'),
    ('Actividad 22', '567 Oak Avenue, Apt 4B, Lakefront View, Houston, Texas, 77001-5678, Estados Unidos de América', 'Descripción de la Actividad 22', '2023-11-05', '14:00:00', '16:30:00'),
    ('Actividad 23', '123 Cedar Lane, Suite 8D, Hillside Heights, Miami, Florida, 33101-6789, Estados Unidos de América', 'Descripción de la Actividad 23', '2023-11-05', '11:30:00', '14:00:00'),
    ('Actividad 24', '456 Maple Drive, Apt 2A, Lakeside Estates, Houston, Texas, 77001-5678, Estados Unidos de América', 'Descripción de la Actividad 24', '2023-11-20', '13:45:00', '16:15:00'),
    ('Actividad 25', '890 Willow Lane, Suite 3D, Riverfront Park, Chicago, Illinois, 60601-6789, Estados Unidos de América', 'Descripción de la Actividad 25', '2023-11-20', '10:30:00', '13:00:00'),
    ('Actividad 26', '234 Birch Avenue, Apt 4D, Meadowview Heights, Los Ángeles, California, 90001-2345, Estados Unidos de América', 'Descripción de la Actividad 26', '2023-11-20', '14:15:00', '16:45:00'),
    ('Actividad 27', '567 Oak Street, Unit 6A, Hillside Heights, Miami, Florida, 33101-6789, Estados Unidos de América', 'Descripción de la Actividad 27', '2023-11-20', '11:45:00', '14:15:00'),
    ('Actividad 28', '345 Redwood Avenue, Apt 5C, Riverside Gardens, Los Ángeles, California, 90001-2345, Estados Unidos de América', 'Descripción de la Actividad 28', '2023-12-26', '09:00:00', '11:30:00'),
    ('Actividad 29', '456 Cedar Lane, Suite 2D, Hillside Heights, Miami, Florida, 33101-6789, Estados Unidos de América', 'Descripción de la Actividad 29', '2023-12-26', '15:30:00', '18:00:00'),
   ('Actividad 30', '456 Pine Road, Unit 3A, Lakeside Estates, Houston, Texas, 77001-1234, Estados Unidos de América', 'Descripción de la Actividad 30', '2023-11-09', '10:00:00', '12:30:00');

INSERT INTO alquileres (id, cliente_id, empleado_id, descripcion, tipo, fecha, hora_inicial, hora_final, activo, condiciones, fechaDev) VALUES
	(1, 1, 0, 'Para una cena de bautizo', 'Bautizo', '2023-10-24', '13:00:00', '19:00:00', 'I', '', '2023-12-04'),
	(2, 2, 0, 'PAra salon de celebracion', '15 años', '2023-10-28', '19:00:00', '23:00:00', 'I', '', '2023-12-04'),
	(3, 3, 0, 'Cualquier cosa', 'Bautizo', '2023-10-25', '19:00:00', '04:00:00', 'I', '', '2023-12-04'),
	(4, 5, 0, 'Cualquiera', 'Bautizo', '2023-12-05', '23:00:00', '05:00:00', 'A', NULL, NULL);

INSERT INTO detalles_alquileres (id, alquiler_id, tipo_detalle, detalle_id, cantidad, precio, cantidad_devuelta, indennizacion) VALUES
	(1, 1, 'mobiliario', 2, 1, 550.99, 1, 0.00),
	(2, 1, 'mobiliario', 1, 4, 120.00, 4, 0.00),
	(3, 2, 'mobiliario', 4, 4, 420.75, 4, 0.00),
	(4, 2, 'mobiliario', 14, 5, 360.99, 4, 360.99),
	(5, 2, 'mobiliario', 10, 3, 130.75, 2, 130.75),
	(6, 3, 'mobiliario', 9, 2, 480.99, NULL, NULL),
	(7, 3, 'mobiliario', 6, 2, 270.99, NULL, NULL),
	(8, 3, 'mobiliario', 5, 2, 320.49, NULL, NULL),
	(9, 3, 'mobiliario', 5, 1, 320.49, NULL, NULL),
	(10, 4, 'mobiliario', 1, 2, 120.00, NULL, NULL);


use eventos;
-- select * from clientes;
-- select * from empleados;
-- select * from proveedores;
-- select * from floristeria;
-- select * from arreglos;
-- select * from materiales;
-- select * from detalles_compras;
-- select * from compras;
-- select * from tarjetas_detalles;
-- select * from tarjetas;
-- select * from materiales;
-- select * from globos;
-- select * from manualidades;
-- select * from detalles_manualidades;
-- select * from ventas;
-- select * from detalles_ventas;
-- select * from eventos;
-- select * from detalles_pedidos;
-- select * from detalles_promociones;
-- select * from detalles_eventos;
-- select * from actividades;
-- use eventos;
-- select * from resumen_promociones;
-- select * from ventas;
