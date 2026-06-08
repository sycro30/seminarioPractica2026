//--------------------------------- CREACION DE LAS BASE DE DATOS ---------------------------------//
CREATE DATABASE Iglesia_mci;
USE Iglesia_mci;

//--------------------------------- CREACION DE LAS TABLAS ---------------------------------//
CREATE TABLE Lider (
    lider_id INT PRIMARY KEY AUTO_INCREMENT,
    miembro_id INT NULL,
    rol VARCHAR(255) NOT NULL
);

CREATE TABLE Celula (
    celula_id INT AUTO_INCREMENT PRIMARY KEY,
    lider_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fecha DATE,
    hora TIME,
    direccion VARCHAR(150),
    FOREIGN KEY (lider_id) REFERENCES Lider(lider_id)
);

CREATE TABLE Miembro (
    miembro_id INT AUTO_INCREMENT PRIMARY KEY,
    celula_id INT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    correo VARCHAR(100),
    FOREIGN KEY (celula_id) REFERENCES Celula(celula_id)
);

ALTER TABLE Lider ADD FOREIGN KEY (miembro_id) REFERENCES Miembro(miembro_id);

CREATE TABLE Actividad (
    actividad_id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_actividad DATE NOT NULL,
    hora_actividad TIME NOT NULL,
    direccion_actividad VARCHAR(150),
    estado VARCHAR(50)
);

CREATE TABLE ReunionCelula (
    actividad_id INT PRIMARY KEY,
    celula_id INT NOT NULL,
    tema VARCHAR(150),
    observaciones VARCHAR(255),
    FOREIGN KEY (actividad_id) REFERENCES Actividad(actividad_id),
    FOREIGN KEY (celula_id) REFERENCES Celula(celula_id)
);

CREATE TABLE Evento (
    actividad_id INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    FOREIGN KEY (actividad_id) REFERENCES Actividad(actividad_id)
);

CREATE TABLE Asistencia (
    asistencia_id INT AUTO_INCREMENT PRIMARY KEY,
    miembro_id INT NOT NULL,
    actividad_id INT NOT NULL,
    fecha_registro DATE,
    fecha DATE,
    estado VARCHAR(50),
    observaciones VARCHAR(255),
    FOREIGN KEY (miembro_id) REFERENCES Miembro(miembro_id),
    FOREIGN KEY (actividad_id) REFERENCES Actividad(actividad_id)
);


//--------------------------------- INSERCION DE DATOS DE PRUEBA ---------------------------------//

INSERT INTO Miembro (miembro_id, celula_id, nombre, apellido, telefono, correo) VALUES
    (1, NULL, 'Juan', 'Perez', '2901123456', 'juan@gmail.com'),
    (2, NULL, 'Maria', 'Gomez', '2901123457', 'maria@gmail.com');

INSERT INTO Lider (lider_id, miembro_id, rol) 
VALUES
    (1, 1, 'Lider de Celula'),
    (2, 2, 'Lider de Celula');

INSERT INTO Celula (celula_id,lider_id, nombre, fecha, hora, direccion) 
VALUES
    (1, 1, 'Celula Adultos', '2026-03-01', '19:00:00', 'San Martín 450'),
    (2, 2, 'Celula Jovenes', '2026-03-01', '20:00:00', 'Belgrano 890');

INSERT INTO Miembro (miembro_id, celula_id, nombre, apellido, telefono, correo) 
VALUES
    (3, 1, 'Carlos', 'Ruiz', '2901123001', 'carlos@gmail.com'),
    (4, 1, 'Lucía', 'Fernandez', '2901123002', 'lucia@gmail.com'),
    (5, 2, 'Diego', 'Ramirez', '2901123003', 'diego@gmail.com'),
    (6, 2, 'Valentina', 'Castro', '2901123004', 'vale@gmail.com');

INSERT INTO Actividad (actividad_id, fecha_actividad, hora_actividad, direccion_actividad, estado) 
VALUES
    (1, '2026-03-03', '19:00:00', 'San Martin 450', 'Finalizada'),
    (2, '2026-03-10', '19:00:00', 'San Martin 450', 'Finalizada'),
    (3, '2026-03-17', '19:00:00', 'San Martin 450', 'Finalizada'),
    (4, '2026-03-24', '19:00:00', 'San Martin 450', 'Finalizada'),

    (5, '2026-03-04', '20:00:00', 'Belgrano 890', 'Finalizada'),
    (6, '2026-03-11', '20:00:00', 'Belgrano 890', 'Finalizada'),
    (7, '2026-03-18', '20:00:00', 'Belgrano 890', 'Finalizada'),
    (8, '2026-03-25', '20:00:00', 'Belgrano 890', 'Finalizada'),

    (9, '2026-03-14', '18:00:00', 'Abel Cardenas 196', 'Finalizada'),
    (10, '2026-03-28', '17:00:00', 'Abel Cardenas 196', 'Finalizada');

INSERT INTO ReunionCelula (actividad_id, celula_id, tema, observaciones) 
VALUES
    (1, 1, 'Fe y perseverancia', 'Reunion semanal'),
    (2, 1, 'Oración', 'Reunion semanal'),
    (3, 1, 'Amor del padre', 'Reunion semanal'),
    (4, 1, 'La gran comisión', 'Reunion semanal'),

    (5, 2, 'Comunión', 'Reunion semanal'),
    (6, 2, 'Servicio', 'Reunion semanal'),
    (7, 2, 'Gratitud', 'Reunion semanal'),
    (8, 2, 'Compromiso', 'Reunion semanal');

INSERT INTO Evento (actividad_id, nombre, descripcion)
VALUES
    (9, 'Seminario de Liderazgo', 'Capacitación para lideres'),
    (10, 'Encuentro Familiar', 'Evento general de integración');

INSERT INTO Asistencia (asistencia_id, miembro_id, actividad_id, fecha_registro, fecha, estado, observaciones)
VALUES
    (1,1,1,'2026-03-03','2026-03-03','Presente',''),
    (2,3,1,'2026-03-03','2026-03-03','Presente',''),
    (3,4,1,'2026-03-03','2026-03-03','Presente',''),

    (4,1,2,'2026-03-10','2026-03-10','Presente',''),
    (5,3,2,'2026-03-10','2026-03-10','Ausente',''),
    (6,4,2,'2026-03-10','2026-03-10','Presente',''),

    (7,1,3,'2026-03-17','2026-03-17','Presente',''),
    (8,3,3,'2026-03-17','2026-03-17','Ausente',''),
    (9,4,3,'2026-03-17','2026-03-17','Presente',''),

    (10,1,4,'2026-03-24','2026-03-24','Presente',''),
    (11,3,4,'2026-03-24','2026-03-24','Presente',''),
    (12,4,4,'2026-03-24','2026-03-24','Ausente',''),

    (13,2,5,'2026-03-04','2026-03-04','Presente',''),
    (14,5,5,'2026-03-04','2026-03-04','Presente',''),
    (15,6,5,'2026-03-04','2026-03-04','Ausente',''),

    (16,2,6,'2026-03-11','2026-03-11','Presente',''),
    (17,5,6,'2026-03-11','2026-03-11','Ausente',''),
    (18,6,6,'2026-03-11','2026-03-11','Ausente',''),

    (19,2,7,'2026-03-18','2026-03-18','Presente',''),
    (20,5,7,'2026-03-18','2026-03-18','Presente',''),
    (21,6,7,'2026-03-18','2026-03-18','Ausente',''),

    (22,2,8,'2026-03-25','2026-03-25','Presente',''),
    (23,5,8,'2026-03-25','2026-03-25','Ausente',''),
    (24,6,8,'2026-03-25','2026-03-25','Presente',''),

    (25,1,9,'2026-03-14','2026-03-14','Presente',''),
    (26,2,9,'2026-03-14','2026-03-14','Presente',''),
    (27,4,9,'2026-03-14','2026-03-14','Presente',''),

    (28,1,10,'2026-03-28','2026-03-28','Presente',''),
    (29,2,10,'2026-03-28','2026-03-28','Presente',''),
    (30,4,10,'2026-03-28','2026-03-28','Presente',''),
    (31,5,10,'2026-03-28','2026-03-28','Presente','');

//--------------------------------- CONSULTAS DE DATOS ---------------------------------//

SELECT
    miembro_id, nombre, apellido
FROM
    Miembro


SELECT
    A.asistencia_id AS 'Id Asistencia',
    A.miembro_id AS 'Id Miembro',
    AC.fecha_actividad AS 'Fecha Actividad',
    A.estado AS 'Estado'
FROM
    Asistencia A,
    Actividad AC
WHERE
    A.actividad_id = AC.actividad_id
    AND A.miembro_id = 3
    AND AC.fecha_actividad BETWEEN '2026-03-01' AND '2026-03-31';

//--------------------------------- BORRADO DE DATOS ---------------------------------//
SET SQL_SAFE_UPDATES = 0;
SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM Asistencia;
DELETE FROM ReunionCelula;
DELETE FROM Evento;
DELETE FROM Actividad;
DELETE FROM Celula;
DELETE FROM Lider;
DELETE FROM Miembro;

SET SQL_SAFE_UPDATES = 1;
SET FOREIGN_KEY_CHECKS = 1;