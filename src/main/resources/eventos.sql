drop database if exists eventos;
create database eventos;
use eventos;

create table Empleados(
	idEmpleados int primary key auto_increment,
	Identidad varchar (16),
	Nombres varchar(50),
	Apellidos varchar(50),
    Genero varchar(12),
    Edad char(2),
    Correo varchar(50),
    Telefono char(8),
    nombreContactoDeEmergencia varchar(50),
	ContactoDeEmergencia varchar(50),
    Direccion text,
    TipoDeEmpleado varchar(20) 
);

select *from Empleados;


create table clientes(
	id int primary key auto_increment,
    nombre varchar(100),
    apellido varchar(45),
    identidad varchar(16),
    telefono varchar(8),
    domicilio text,
    tipo_cliente varchar(45)
);

select *from clientes;

create table proveedores(
	id int primary key auto_increment,
    empresaProveedora varchar(100),
    rtn varchar(17),
    telefono varchar(9),
    correo varchar(45),
    direccion text,
    descripcion text,
    nombreVendedor varchar(100),
    telefonoVendedor varchar(45)
);

select *from proveedores;


