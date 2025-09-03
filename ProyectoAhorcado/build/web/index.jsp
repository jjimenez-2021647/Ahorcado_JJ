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
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/Styles/index.css">
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/Images/Icono.jpg">
        <title>JJ - Juegos</title>
    </head>
    <body>
        <!-- Fondo de partículas -->
        <div class="particles" id="particles"></div>

        <!-- Fondo de puzzle -->
        <div class="puzzle-bg"></div>

        <div class="main-container">
            <div class="header">
                <h1>Juegos JJ 👻</h1>
                <p>En esta página encuentras los mejores juegos.</p>
                <p>¿Que te gustaria jugar?</p>
                <div class="controls-section">
                    <a href="Controlador?menu=Rompecabezas" class="button">
                        <span class="shadow"></span>
                        <span class="edge"></span>
                        <div class="front">
                            <span>Rompecabezas</span>
                        </div>
                    </a>
                    <a href="Controlador?menu=Ahorcado" class="button">
                        <span class="shadow"></span>
                        <span class="edge"></span>
                        <div class="front">
                            <span>Ahorcado</span>
                        </div>
                    </a>
                </div>
            </div>
        </div>
        <script src="../Scripts/script.js"></script>
    </body>
</html>