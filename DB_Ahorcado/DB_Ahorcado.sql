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
	codigo_usuario int auto_increment,
    correo_usuario varchar(150) not null unique,
    contraseña_usuario varchar(100) not null,
	primary key PK_codigo_usuario (codigo_usuario)
);

-- Palabras 
Create table Palabras(
	codigo_palabra int auto_increment,
    palabra varchar(50) not null,
    pista varchar(100) not null,
    primary key PK_codigo_palabra (codigo_palabra)
);

-- Trigger
-- Insercion de datos en Usuarios
Delimiter //
	Create Trigger tr_Before_Insert_CorreoUsuarios
	Before Insert on Usuarios
    For each row
		Begin
			If (new.correo_usuario not like '%@gmail.com' and new.correo_usuario not like '%@kinal.edu.gt')  Then
				Signal Sqlstate '45000' 
				set Message_text = 'El correo electrónico debe tener el dominio @gmail.com o @kinal.edu.gt';
			end if;
        End//
Delimiter ;

Delimiter //
	Create Trigger tr_Before_Update_CorreoUsuarios
	Before Update on Usuarios
    For each row
		Begin
			If (new.correo_usuario not like '%@gmail.com' and new.correo_usuario not like '%@kinal.edu.gt')  Then
				Signal Sqlstate '45000' 
				set Message_text = 'El correo electrónico debe tener el dominio @gmail.com o @kinal.edu.gt';
			end if;
        End//
Delimiter ;

-- Procedimeintos almacenados
-- --------------------------- Entidad Usuarios --------------------------- 
-- Agregar Usuario
Delimiter //
	Create procedure sp_AgregarUsuario(
    in correo_usuario varchar(150), 
    in contraseña_usuario varchar(100))
		Begin
			Insert into Usuarios(correo_usuario, contraseña_usuario)
				Values(correo_usuario, contraseña_usuario);
        End //
Delimiter ;
call sp_AgregarUsuario('carlos.ramirez@kinal.edu.gt','CRamirez#2025');
call sp_AgregarUsuario('daniela.mejia@gmail.com', 'DMejia#89');
call sp_AgregarUsuario('mario.escobar@gmail.com', 'MEscobar!05');
call sp_AgregarUsuario('rebeca.salazar@gmail.com', 'RSalazar*44');
call sp_AgregarUsuario('oscar.cordova@gmail.com', 'OCordova#12');
call sp_AgregarUsuario('joshua.ja2007@gmail.com', '1818');
call sp_AgregarUsuario('isabel.ruiz@gmail.com', 'IRuiz@78');
call sp_AgregarUsuario('roberto.rodriguez@gmail.com', '12345');
call sp_AgregarUsuario('jefry.cruz@gmail.com', '54321');
call sp_AgregarUsuario('diego.lopez@gmail.com', '84563');

-- RegistrarseLogin
Delimiter //
	Create procedure sp_RegistroLogin(
    in correo_usuario varchar(150), 
    in contraseña_usuario varchar(100),
    out filas int)
		Begin
			Insert into Usuarios(correo_usuario, contraseña_usuario)
				Values(correo_usuario, contraseña_usuario);
                
			Set filas = row_count();
        End //
Delimiter ;

-- Listar Usuarios
Delimiter //
	Create procedure sp_ListarUsuarios()
		Begin
			Select codigo_usuario, correo_usuario, contraseña_usuario from Usuarios;
        End //
Delimiter ;
call sp_ListarUsuarios();

-- Eliminar Usuarios
Delimiter //
	Create procedure sp_EliminarUsuario(
    in _codigo_usuario int)
		Begin
			set foreign_key_checks = 0;
				Delete from Usuarios
					where codigo_usuario = _codigo_usuario;
				Select row_count() as filasEliminadas;
			set foreign_key_checks = 1;
        End//
Delimiter ;
call sp_EliminarUsuario(7);


-- Buscar Usuarios
Delimiter //
	Create procedure sp_BuscarUsuarios(
    in _codigo_usuario int)
		Begin
			Select codigo_usuario, correo_usuario, contraseña_usuario from Usuarios
				where codigo_usuario = _codigo_usuario;
        End //
Delimiter ;
call sp_BuscarUsuarios(1);

-- Editar Usuario
Delimiter //
	Create procedure sp_EditarUsuario(
    in _codigo_usuario int,
    in _correo_usuario varchar(150), 
    in _contraseña_usuario varchar(100)) 
		Begin
			Update Usuarios
				set correo_usuario = _correo_usuario,
                contraseña_usuario = _contraseña_usuario
					where codigo_usuario = _codigo_usuario;
        End //
Delimiter ;
call sp_EditarUsuario(1, 'rebeca.hernandez@gmail.com', 'RHernandez@16');
call sp_EditarUsuario(2, 'oscar.ramirez@gmail.com','ORamirez#17');
call sp_EditarUsuario(3, 'isabel.lopez@gmail.com', 'ClaveSegura#2025');
call sp_EditarUsuario(4, 'ricardo.estrada@gmail.com','REstrada!19');
call sp_EditarUsuario(5, 'valeria.guzman@kinal.edu.gt', 'VGuzman@20');

/*
-- Editar Usuario Creado en el login 
Delimiter //
	Create procedure sp_EditarUsuarioLogin(
    in _codigo_usuario int,
    in _nombre_usuario varchar(100),
    in _apellido_usuario varchar(100)) 
		Begin
			Update Usuarios
				set nombre_usuario = _nombre_usuario,
				apellido_usuario = _apellido_usuario
					where codigo_usuario = _codigo_usuario;
        End //
Delimiter ;*/

-- Busqueda del Usuario por nombre y contraseña
Delimiter //
	Create procedure sp_BuscarUsuariosNC(
    in _correo_usuario varchar(100),
    in _contraseña_usuario varchar(100))
		Begin
			Select codigo_usuario, correo_usuario, contraseña_usuario from Usuarios where correo_usuario = _correo_usuario and contraseña_usuario = _contraseña_usuario;
        End //
Delimiter ;
call sp_BuscarUsuariosNC('joshua.ja2007@gmail.com', '1818');

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
call sp_AgregarPalabras('PALABRA', 'Soy muy utilizada, siempre soy mencionada, sin mi no habrian oraciones.');
call sp_AgregarPalabras('PATINETA', 'Tiene ruedas, torinillos y lija.');
call sp_AgregarPalabras('MURCIELAGO', 'Este animal es pequeño, su nombre lleva todas las vocales.');
call sp_AgregarPalabras('COCODRILO', 'Este animal es grande, verde, de dientes fuertes.');
call sp_AgregarPalabras('MICROFONO', 'Me usan cuando quieren ser escuchados.');
call sp_AgregarPalabras('ROMPECABEZAS', 'Soy un dolor de cabeza para quienes no tienen paciencia.');
call sp_AgregarPalabras('TELEFONO', 'Emito luz y sonido, soy muy utilizado.');
call sp_AgregarPalabras('ZOMBI', 'Como cerebros y estoy podrido.');
call sp_AgregarPalabras('COMPUTADORA', 'Soy algo que siempre utilizas (informaticos).');
call sp_AgregarPalabras('ZOMPOPO', 'Soy un animal que sale en mayo.');

-- Listar Palabras
Delimiter //
	Create procedure sp_ListarPalabras()
		Begin
			Select codigo_palabra, palabra, pista from Palabras;
        End //
Delimiter ;
call sp_ListarPalabras();

-- Eliminar Palabras
Delimiter //
	Create procedure sp_EliminarPalabras(
    in _codigo_palabra int)
		Begin
			set foreign_key_checks = 0;
				Delete from Palabras
					where codigo_palabra = _codigo_palabra;
				Select row_count() as filasEliminadas;
			set foreign_key_checks = 1;
        End//
Delimiter ;
call sp_EliminarPalabras(7);

-- Buscar Usuarios
Delimiter //
	Create procedure sp_BuscarPalabras(
    in _codigo_palabra int)
		Begin
			Select codigo_palabra, palabra, pista from Palabras
				where codigo_palabra = _codigo_palabra;
        End //
Delimiter ;
call sp_BuscarPalabras(1);

-- Editar Palabras
Delimiter //
	Create procedure sp_EditarPalabras(
    in _codigo_palabra int,
    in _palabra varchar(50),
    in _pista varchar(100)) 
		Begin
			Update Palabras
				set palabra = _palabra,
				pista = _pista
					where codigo_palabra = _codigo_palabra;
        End //
Delimiter ;
call sp_EditarPalabras(1, 'HORMIGAS', 'Somos pequeñas pero juntas somos fuertes.');