DROP DATABASE IF EXISTS AGENDA;
CREATE DATABASE AGENDA;
USE AGENDA;
set foreign_key_checks=0;
		
CREATE TABLE Identificacion(
Id varchar(30) PRIMARY KEY, 
Password varchar(30) NOT NULL
);
CREATE TABLE Usuarios(
Id_usuario varchar(30)primary key,
Nombre varchar(30), 
Telefono varchar(15), 
Direccion VARCHAR(100), 
Email varchar(80), 
Fecha_nacimiento date,
CONSTRAINT fk_usuarios 
	FOREIGN KEY (Id_usuario) 
		references Identificacion(Id) 
		ON UPDATE cascade
);
CREATE TABLE Regalos(
Id_usuario varchar(30), 
Articulo1 varchar(40), 
Articulo2 varchar(40), 
Articulo3 varchar(40), 
Articulo4 varchar(40),
Articulo5 varchar(40), 
PRIMARY KEY (Id_usuario), 
CONSTRAINT fk_regalos 
	FOREIGN KEY (Id_usuario) 
		references Identificacion(Id) 
		ON UPDATE cascade
);
set foreign_key_checks=1;

INSERT INTO Identificacion 
	VALUES("Admin","Admin1");
			 
		 

