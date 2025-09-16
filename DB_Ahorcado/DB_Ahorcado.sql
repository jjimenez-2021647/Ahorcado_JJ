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
	nombre_usuario varchar(100),
	apellido_usuario varchar(100), 	
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

-- Procedimeintos almacenados
-- --------------------------- Entidad Usuarios --------------------------- 
-- Agregar Usuario
Delimiter //
	Create procedure sp_AgregarUsuario(
    in nombre_usuario varchar(100),
    in apellido_usuario varchar(100), 
    in correo_usuario varchar(150), 
    in contraseña_usuario varchar(100))
		Begin
			Insert into Usuarios(nombre_usuario, apellido_usuario, correo_usuario, contraseña_usuario)
				Values(nombre_usuario, apellido_usuario, correo_usuario, contraseña_usuario);
        End //
Delimiter ;
call sp_AgregarUsuario('Carlos', 'Ramírez', 'carlos.ramirez@gmail.com','CRamirez#2025');
call sp_AgregarUsuario('Daniela', 'Mejía', 'daniela.mejia@gmail.com', 'DMejia#89');
call sp_AgregarUsuario('Mario', 'Escobar', 'mario.escobar@gmail.com', 'MEscobar!05');
call sp_AgregarUsuario('Rebeca', 'Salazar', 'rebeca.salazar@gmail.com', 'RSalazar*44');
call sp_AgregarUsuario('Óscar', 'Córdova', 'oscar.cordova@gmail.com', 'OCordova#12');
call sp_AgregarUsuario('Josué', 'Jiménez', 'joshua.ja2007@gmail.com', '1818');
call sp_AgregarUsuario('Isabel', 'Ruiz', 'isabel.ruiz@gmail.com', 'IRuiz@78');

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
			Select codigo_usuario, nombre_usuario, apellido_usuario, correo_usuario, contraseña_usuario from Usuarios;
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
			Select codigo_usuario, nombre_usuario, apellido_usuario, correo_usuario, contraseña_usuario from Usuarios
				where codigo_usuario = _codigo_usuario;
        End //
Delimiter ;
call sp_BuscarUsuarios(1);

-- Editar Usuario
Delimiter //
	Create procedure sp_EditarUsuario(
    in _codigo_usuario int,
    in _nombre_usuario varchar(100),
    in _apellido_usuario varchar(100), 
    in _correo_usuario varchar(150), 
    in _contraseña_usuario varchar(100)) 
		Begin
			Update Usuarios
				set nombre_usuario = _nombre_usuario,
				apellido_usuario = _apellido_usuario,
				correo_usuario = _correo_usuario,
                contraseña_usuario = _contraseña_usuario
					where codigo_usuario = _codigo_usuario;
        End //
Delimiter ;
call sp_EditarUsuario(1, 'Rebeca', 'Hernández', 'rebeca.hernandez@gmail.com', 'RHernandez@16');
call sp_EditarUsuario(2, 'Óscar', 'Ramírez', 'oscar.ramirez@gmail.com','ORamirez#17');
call sp_EditarUsuario(3, 'Isabel', 'López', 'isabel.lopez@gmail.com', 'ClaveSegura#2025');
call sp_EditarUsuario(4, 'Ricardo', 'Estrada', 'ricardo.estrada@gmail.com','REstrada!19');
call sp_EditarUsuario(5, 'Valeria', 'Guzmán', 'valeria.guzman@gmail.com', 'VGuzman@20');

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
Delimiter ;

-- Busqueda del Usuario por nombre y contraseña
Delimiter //
	Create procedure sp_BuscarUsuariosNC(
    in _correo_usuario varchar(100),
    in _contraseña_usuario varchar(100))
		Begin
			Select codigo_usuario, nombre_usuario, apellido_usuario, correo_usuario, contraseña_usuario from Usuarios where correo_usuario = _correo_usuario and contraseña_usuario = _contraseña_usuario;
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