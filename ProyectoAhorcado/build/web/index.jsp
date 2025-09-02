<%-- 
    Document   : index
    Created on : 2/09/2025, 08:02:15
    Author     : informatica
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Juego de Ahorcado</title>
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/Images/Icono.png">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Styles/index.css">
    </head>
    <body>
        <div class="game-container">
            <!-- Título creativo -->
            <h1 class="title">Ahorcado</h1>

            <!-- Sección principal con imagen del ahorcado y panel derecho -->
            <div class="main-section">
                <!-- Lado izquierdo: Imagen del ahorcado -->
                <div class="hangman-section">
                    <div class="hangman-drawing" id="hangman">
                        
                    </div>
                </div>

                <!-- Lado derecho: Temporizador e imagen progresiva -->
                <div class="right-panel">
                    <!-- Temporizador -->
                    <div class="timer-section">
                        <div class="timer-display" id="timer">00:00</div>
                    </div>

                    <!-- Imagen que se mostrará progresivamente -->
                    <div class="progress-image">
                        <div class="image-placeholder" id="progressImage">
                            
                        </div>
                    </div>
                </div>
            </div>

            <!-- Sección inferior con pista y botones -->
            <div class="bottom-section">
                <!-- Pista (debajo del ahorcado) -->
                <div class="hint-section">
                    <div class="hint-title">Adivinanza</div>
                    <div class="hint-text" id="hint">XD</div>
                </div>

                <!-- Botones de control -->
                <div class="control-buttons">
                    <button class="btn btn-start" onclick="startGame()">Empezar</button>
                    <button class="btn btn-restart" onclick="restartGame()">Reiniciar</button>
                    <button class="btn btn-pause" onclick="pauseGame()">Pausar</button>
                    <button class="btn btn-exit" onclick="exitGame()">Salir</button>
                </div>
            </div>

            <!-- Área del juego con palabra y teclado -->
            <div class="game-area">
                <!-- Información del juego -->
                <div class="game-info">
                    <div class="errors-count" id="errors">Errores: 0/6</div>
                </div>

                <!-- Líneas para la palabra -->
                <div class="word-lines">
                    <div class="letter-blanks" id="wordDisplay">_ _ _ _ _ _ _</div>

                    <!-- Teclado -->
                    <div class="keyboard" id="keyboard">
                        <!-- Primera fila -->
                        <div class="keyboard-row">
                            <button class="key" onclick="guessLetter('Q')">Q</button>
                            <button class="key" onclick="guessLetter('W')">W</button>
                            <button class="key" onclick="guessLetter('E')">E</button>
                            <button class="key" onclick="guessLetter('R')">R</button>
                            <button class="key" onclick="guessLetter('T')">T</button>
                            <button class="key" onclick="guessLetter('Y')">Y</button>
                            <button class="key" onclick="guessLetter('U')">U</button>
                            <button class="key" onclick="guessLetter('I')">I</button>
                            <button class="key" onclick="guessLetter('O')">O</button>
                            <button class="key" onclick="guessLetter('P')">P</button>
                        </div>

                        <!-- Segunda fila -->
                        <div class="keyboard-row">
                            <button class="key" onclick="guessLetter('A')">A</button>
                            <button class="key" onclick="guessLetter('S')">S</button>
                            <button class="key" onclick="guessLetter('D')">D</button>
                            <button class="key" onclick="guessLetter('F')">F</button>
                            <button class="key" onclick="guessLetter('G')">G</button>
                            <button class="key" onclick="guessLetter('H')">H</button>
                            <button class="key" onclick="guessLetter('J')">J</button>
                            <button class="key" onclick="guessLetter('K')">K</button>
                            <button class="key" onclick="guessLetter('L')">L</button>
                            <button class="key" onclick="guessLetter('Ñ')">Ñ</button>
                        </div>

                        <!-- Tercera fila -->
                        <div class="keyboard-row">
                            <button class="key" onclick="guessLetter('Z')">Z</button>
                            <button class="key" onclick="guessLetter('X')">X</button>
                            <button class="key" onclick="guessLetter('C')">C</button>
                            <button class="key" onclick="guessLetter('V')">V</button>
                            <button class="key" onclick="guessLetter('B')">B</button>
                            <button class="key" onclick="guessLetter('N')">N</button>
                            <button class="key" onclick="guessLetter('M')">M</button>
                        </div>
                    </div>
                </div>
            </div>  

            <!-- Modal para fin de juego -->
            <div class="modal" id="gameModal">
                <div class="modal-content">
                    <h2 id="modalTitle">Felicitaciones</h2>
                    <p id="modalMessage">Has ganado el juego y lograste salvar a Jorgito</p>
                    <button class="btn btn-restart" onclick="restartGame(); closeModal();">Jugar de nuevo</button>
                </div>
            </div>

            <script src="script.js"></script>
    </body>
</html>