-- Por Josué Gilberto Jiménez Ajtún
-- 2021647
-- IN5BM
-- 03/09/2025

-- Creacion de la DB
Drop database if exists DB_Ahorcado;
Create database DB_Ahorcado;
Use DB_Ahorcado;

-- Entidades
-- Usuarios
Create table Usuarios(
	codigoUsuario int auto_increment,
	nombreUsuario varchar(100),
	apellidoUsuario varchar(100), 	
    correoUsuario varchar(150) not null,
    contraseñaUsuario varchar(100) not null,
    imagenUsuario longblob,
	primary key PK_codigoUsuario (codigoUsuario)
);

-- Palabras 
Create table Palabras(
	codigoPalabra int auto_increment,
    palabra varchar(50) not null,
    pista varchar(100) not null,
    primary key PK_codigoPalabra (codigoPalabra)
);

-- Procedimeintos almacenados
-- --------------------------- Entidad Usuarios --------------------------- 
-- Agregar Usuario
Delimiter //
	Create procedure sp_AgregarUsuario(
    in nombreUsuario varchar(100),
    in apellidoUsuario varchar(100), 
    in correoUsuario varchar(150), 
    in contraseñaUsuario varchar(100))
		Begin
			Insert into Usuarios(nombreUsuario, apellidoUsuario, correoUsuario, contraseñaUsuario)
				Values(nombreUsuario, apellidoUsuario, correoUsuario, contraseñaUsuario);
        End //
Delimiter ;
call sp_AgregarUsuario('Carlos', 'Ramírez', 'carlos.ramirez@gmail.com','CRamirez#2025');
call sp_AgregarUsuario('Daniela', 'Mejía', 'daniela.mejia@gmail.com', 'DMejia#89');
call sp_AgregarUsuario('Mario', 'Escobar', 'mario.escobar@gmail.com', 'MEscobar!05');
call sp_AgregarUsuario('Rebeca', 'Salazar', 'rebeca.salazar@gmail.com', 'RSalazar*44');
call sp_AgregarUsuario('Óscar', 'Córdova', 'oscar.cordova@gmail.com', 'OCordova#12');
call sp_AgregarUsuario('Isabel', 'Ruiz', 'isabel.ruiz@gmail.com', 'IRuiz@78');
call sp_AgregarUsuario('Josué', 'Jiménez', 'joshua.ja2007@gmail.com', '1818');

-- Registrar Usuario
Delimiter //
	Create procedure sp_RegistrarUsuario(
    in correoUsuario varchar(150), 
    in contraseñaUsuario varchar(100))
		Begin
			Insert into Usuarios(correoUsuario, contraseñaUsuario)
				Values(correoUsuario, contraseñaUsuario);
        End //
Delimiter ;

-- Listar Usuarios
Delimiter //
	Create procedure sp_ListarUsuarios()
		Begin
			Select codigoUsuario, nombreUsuario, apellidoUsuario, correoUsuario, contraseñaUsuario from Usuarios;
        End //
Delimiter ;
call sp_ListarUsuarios();

-- Eliminar Usuarios
Delimiter //
	Create procedure sp_EliminarUsuario(
    in _codigoUsuario int)
		Begin
			set foreign_key_checks = 0;
				Delete from Usuarios
					where codigoUsuario = _codigoUsuario;
				Select row_count() as filasEliminadas;
			set foreign_key_checks = 1;
        End//
Delimiter ;
call sp_EliminarUsuario(7);


-- Buscar Usuarios
Delimiter //
	Create procedure sp_BuscarUsuarios(
    in _codigoUsuario int)
		Begin
			Select codigoUsuario, nombreUsuario, apellidoUsuario, correoUsuario, contraseñaUsuario from Usuarios
				where codigoUsuario = _codigoUsuario;
        End //
Delimiter ;
call sp_BuscarUsuarios(1);

-- Editar Usuario
Delimiter //
	Create procedure sp_EditarUsuario(
    in _codigoUsuario int,
    in _nombreUsuario varchar(100),
    in _apellidoUsuario varchar(100), 
    in _correoUsuario varchar(150), 
    in _contraseñaUsuario varchar(100)) 
		Begin
			Update Usuarios
				set nombreUsuario = _nombreUsuario,
				apellidoUsuario = _apellidoUsuario,
				correoUsuario = _correoUsuario,
                contraseñaUsuario = _contraseñaUsuario
					where codigoUsuario = _codigoUsuario;
        End //
Delimiter ;
call sp_EditarUsuario(1, 'Rebeca', 'Hernández', 'rebeca.hernandez@gmail.com', 'RHernandez@16');
call sp_EditarUsuario(2, 'Óscar', 'Ramírez', 'oscar.ramirez@gmail.com','ORamirez#17');
call sp_EditarUsuario(3, 'Isabel', 'López', 'isabel.lopez@gmail.com', 'ClaveSegura#2025');
call sp_EditarUsuario(4, 'Ricardo', 'Estrada', 'ricardo.estrada@gmail.com','REstrada!19');
call sp_EditarUsuario(5, 'Valeria', 'Guzmán', 'valeria.guzman@gmail.com', 'VGuzman@20');

-- --------------------------- Entidad Palabras --------------------------- 
-- Agregar Palabras
Delimiter //
	Create procedure sp_AgregarPalabras(
    in palabra varchar(50),
    in pista varchar(100))
		Begin
			Insert into Palabras(palabra, pista)
				Values (palabra, pista);
        End //
Delimiter ;
call sp_AgregarPalabras('HORMIGAS', 'Somos pequeñas pero juntas somos fuertes.');
call sp_AgregarPalabras('PATINETA', 'Tiene ruedas, torinillos y lija.');
call sp_AgregarPalabras('MURCIELAGO', 'Este animal es pequeño, su nombre lleva todas las vocales.');
call sp_AgregarPalabras('COCODRILO', 'Este animal es grande, verde, de dientes fuertes.');
call sp_AgregarPalabras('MICROFONO', 'Me usan cuando quieren ser escuchados.');
call sp_AgregarPalabras('ROMPECABEZAS', 'Soy un dolor de cabeza para quienes no tienen paciencia.');
call sp_AgregarPalabras('TELEFONO', 'Emito luz y sonido, soy muy utilizado.');

-- Listar Palabras
Delimiter //
	Create procedure sp_ListarPalabras()
		Begin
			Select codigoPalabra, palabra, pista from Palabras;
        End //
Delimiter ;
call sp_ListarPalabras();

-- Eliminar Palabras
Delimiter //
	Create procedure sp_EliminarPalabras(
    in _codigoPalabra int)
		Begin
			set foreign_key_checks = 0;
				Delete from Palabras
					where codigoPalabra = _codigoPalabra;
				Select row_count() as filasEliminadas;
			set foreign_key_checks = 1;
        End//
Delimiter ;
call sp_EliminarPalabras(7);

-- Buscar Usuarios
Delimiter //
	Create procedure sp_BuscarPalabras(
    in _codigoPalabra int)
		Begin
			Select codigoPalabra, palabra, pista from Palabras
				where codigoPalabra = _codigoPalabra;
        End //
Delimiter ;
call sp_BuscarPalabras(1);

-- Editar Palabras
Delimiter //
	Create procedure sp_EditarPalabras(
    in _codigoPalabra int,
    in _palabra varchar(50),
    in _pista varchar(100)) 
		Begin
			Update Palabras
				set palabra = _palabra,
				pista = _pista
					where codigoPalabra = _codigoPalabra;
        End //
Delimiter ;
call sp_EditarPalabras(6, 'PALABRA', 'Soy muy utilizada, siempre soy mencionada, sin mi no habrian oraciones.');