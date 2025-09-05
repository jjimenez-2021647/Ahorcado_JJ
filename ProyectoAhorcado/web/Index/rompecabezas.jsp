<%-- 
    Document   : rompecabezas
    Created on : 27/08/2025, 07:45:59
    Author     : informatica
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Styles/estilo.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/Images/LogoHK.jpg">
        <title>HK - Rompecabezas</title>
    </head>
    <body>
        <div class="main-container">
            <div class="header">
                <h1>HOLLOW KNIGHT PUZZLE</h1>
                <p>ROMPECABEZAS DESLIZANTE ÉPICO</p>
            </div>

            <div class="game-container">
                <div class="left-panel">
                    <div class="info-card">
                        <div class="timer-section">
                            <div class="timer-label">TIEMPO RESTANTE ✊🏼</div>
                            <h1 id="tiempo">03:00</h1>
                        </div>

                        <div class="image-section">
                            <div class="image-label">Objetivo 👻</div>
                            <img src="${pageContext.request.contextPath}/Images/HK.jpg" alt="Hollow Knight" class="img">
                        </div>

                        <div class="controls-section">
                            <button class="button" onclick="reiniciar()">
                                <span class="shadow"></span>
                                <span class="edge"></span>
                                <div class="front">
                                    <span>Reiniciar</span>
                                </div>
                            </button>
                            <button class="button" onclick="location.href = 'Controlador?menu=MenuPrincipal'">
                                <span class="shadow"></span>
                                <span class="edge"></span>
                                <div class="front">
                                    <span>Inicio</span>
                                </div>
                            </button>
                            <button class="button" onclick="armar()">
                                <span class="shadow"></span>
                                <span class="edge"></span>
                                <div class="front">
                                    <span>Armar</span>
                                </div>
                            </button>
                            <button class="button" onclick="location.href = 'Controlador?menu=Rompecabezas2'">
                                <span class="shadow"></span>
                                <span class="edge"></span>
                                <div class="front">
                                    <span>Berserk</span>
                                </div>
                            </button>
                        </div>
                    </div>
                </div>

                <div class="puzzle-section">
                    <div class="container">
                        <h2>Hollow Knight 4x4</h2>
                        <div id="puzzle" class="puzzle"></div>     
                        <p id="mensaje"></p>
                    </div>
                </div>
            </div>
            <br> 
            <div class="instructions">
                <h3>INSTRUCCIONES</h3>
                <p>Haz clic en una imagen adyacente al espacio para moverlo.</p>
                <p>Tu objetivo es ordenar todas las piezas para formar la imagen completa del Hollow Knight antes de que se agote el tiempo.</p>
                <p class="challenge-text">¡Tienes 3 minutos para completar el desafío!</p>
            </div>
        </div>
        <script>
            window.contextPath = '${pageContext.request.contextPath}';
        </script>
        <script src="${pageContext.request.contextPath}/Scripts/script1.js"></script>
    </body>
</html>