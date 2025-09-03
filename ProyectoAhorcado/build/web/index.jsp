<%-- 
    Documento   : indice
    Creado en   : 2/09/2025, 08:02:15
    Autor       : jjimenez-2021647
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Juego del Ahorcado</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/Images/Icono.png">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Styles/index.css">
    </head>
    <body>
        <div class="contenedor-juego">
            <!-- Título creativo -->
            <h1 class="titulo">Ahorcado</h1>

            <!-- Sección principal con imagen del ahorcado y panel derecho -->
            <div class="seccion-principal">
                <!-- Lado izquierdo: Imagen del ahorcado -->
                <div class="seccion-ahorcado">
                    <div class="dibujo-ahorcado" id="ahorcado">

                    </div>
                </div>

                <!-- Lado derecho: Temporizador e imagen progresiva -->
                <div class="panel-derecho">
                    <!-- Temporizador -->
                    <div class="seccion-temporizador">
                        <div class="pantalla-tiempo" id="tiempo">02:00</div>
                    </div>

                    <!-- Imagen que se mostrará progresivamente -->
                    <div class="imagen-progresiva">
                        <div class="contenedor-imagen" id="imagenProgreso">

                        </div>
                    </div>
                </div>
            </div>

            <!-- Sección inferior con pista y botones -->
            <div class="seccion-inferior">
                <!-- Pista (debajo del ahorcado) -->
                <div class="seccion-pista">
                    <div class="titulo-pista">Adivinanza</div>
                    <div class="texto-pista" id="pista">¡Adivina la palabra!</div>
                </div>

                <!-- Botones de control -->
                <div class="botones-control">
                    <button class="boton" onclick="empezar()">
                        <span class="sombra"></span>
                        <span class="borde"></span>
                        <span class="frente"><span>Empezar</span></span>
                    </button>
                    <button class="boton" onclick="reiniciarJuego()">
                        <span class="sombra"></span>
                        <span class="borde"></span>
                        <span class="frente"><span>Reiniciar</span></span>
                    </button>
                    <button class="boton" onclick="pausarJuego()">
                        <span class="sombra"></span>
                        <span class="borde"></span>
                        <span class="frente"><span>Pausar</span></span>
                    </button>
                    <button class="boton" onclick="salirJuego()">
                        <span class="sombra"></span>
                        <span class="borde"></span>
                        <span class="frente"><span>Salir</span></span>
                    </button>
                </div>
            </div>

            <!-- Área del juego con palabra y teclado -->
            <div class="area-juego">
                <!-- Información del juego -->
                <div class="informacion-juego">
                    <div class="contador-errores" id="errores">Errores: 0/6</div>
                </div>

                <!-- Líneas para la palabra -->
                <div class="lineas-palabra">
                    <div class="espacios-letras" id="mostrarPalabra">_ _ _ _ _ _ _</div>

                    <!-- Teclado -->
                    <div class="teclado" id="teclado">
                        <!-- Primera fila -->
                        <div class="fila-teclado">
                            <button class="tecla" onclick="adivinarLetra('Q')">Q</button>
                            <button class="tecla" onclick="adivinarLetra('W')">W</button>
                            <button class="tecla" onclick="adivinarLetra('E')">E</button>
                            <button class="tecla" onclick="adivinarLetra('R')">R</button>
                            <button class="tecla" onclick="adivinarLetra('T')">T</button>
                            <button class="tecla" onclick="adivinarLetra('Y')">Y</button>
                            <button class="tecla" onclick="adivinarLetra('U')">U</button>
                            <button class="tecla" onclick="adivinarLetra('I')">I</button>
                            <button class="tecla" onclick="adivinarLetra('O')">O</button>
                            <button class="tecla" onclick="adivinarLetra('P')">P</button>
                        </div>

                        <!-- Segunda fila -->
                        <div class="fila-teclado">
                            <button class="tecla" onclick="adivinarLetra('A')">A</button>
                            <button class="tecla" onclick="adivinarLetra('S')">S</button>
                            <button class="tecla" onclick="adivinarLetra('D')">D</button>
                            <button class="tecla" onclick="adivinarLetra('F')">F</button>
                            <button class="tecla" onclick="adivinarLetra('G')">G</button>
                            <button class="tecla" onclick="adivinarLetra('H')">H</button>
                            <button class="tecla" onclick="adivinarLetra('J')">J</button>
                            <button class="tecla" onclick="adivinarLetra('K')">K</button>
                            <button class="tecla" onclick="adivinarLetra('L')">L</button>
                            <button class="tecla" onclick="adivinarLetra('Ñ')">Ñ</button>
                        </div>

                        <!-- Tercera fila -->
                        <div class="fila-teclado">
                            <button class="tecla" onclick="adivinarLetra('Z')">Z</button>
                            <button class="tecla" onclick="adivinarLetra('X')">X</button>
                            <button class="tecla" onclick="adivinarLetra('C')">C</button>
                            <button class="tecla" onclick="adivinarLetra('V')">V</button>
                            <button class="tecla" onclick="adivinarLetra('B')">B</button>
                            <button class="tecla" onclick="adivinarLetra('N')">N</button>
                            <button class="tecla" onclick="adivinarLetra('M')">M</button>
                        </div>
                    </div>
                </div>
            </div>  

            <!-- Modal para fin de juego -->
            <div class="modal" id="modalJuego">
                <div class="contenido-modal">
                    <h2 id="tituloModal">¡Felicitaciones!</h2>
                    <p id="mensajeModal">Has ganado el juego y lograste salvar a Jorgito</p>
                    <button class="boton boton-reiniciar" onclick="reiniciarJuego(); cerrarModal();">
                        <span class="sombra"></span>
                        <span class="borde"></span>
                        <span class="frente"><span>Jugar de nuevo</span></span>
                    </button>
                </div>
            </div>
            <script>
                const contextPath = '${pageContext.request.contextPath}';
            </script>
            <script src="${pageContext.request.contextPath}/Scripts/script.js"></script>
    </body>
</html>