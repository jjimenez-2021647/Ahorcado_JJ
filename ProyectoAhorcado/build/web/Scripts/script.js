// Cronómetro para el juego de Ahorcado
let cronometro = null;
const tiempoMaximo = 120; // 2 minutos
let tiempo = tiempoMaximo;
let juegoActivo = false;
let palabraSecreta = "";
let palabraAdivinada = [];
let errores = 0;
const maxErrores = 6;
let letrasUsadas = [];
let palabrasConPistas = []; 

// Obtener elementos del HTML
let cronometroElem = document.getElementById("tiempo");
let pistaElem = document.getElementById("pista");
let mostrarPalabraElem = document.getElementById("mostrarPalabra");
let erroresElem = document.getElementById("errores");
let ahorcadoElem = document.getElementById("ahorcado");
let imagenProgresoElem = document.getElementById("imagenProgreso");
let modalElem = document.getElementById("modalJuego");
let tituloModalElem = document.getElementById("tituloModal");
let mensajeModalElem = document.getElementById("mensajeModal");
let tecladoElem = document.getElementById("teclado");

// 🔹 Cargar palabras desde la DB
function cargarPalabras() {
    return fetch("Controlador?menu=PalabrasJuego")
        .then(response => response.json())
        .then(data => {
            palabrasConPistas = data;
        })
        .catch(error => console.error("Error cargando palabras:", error));
}

// Función para mostrar el mensaje inicial
function mostrarMensajeInicial() {
    imagenProgresoElem.innerHTML = `<p id="mensajeInicial" style="text-align: center; color: black !important; font-size: 18px !important; margin: 20px; line-height: 1.4; font-weight: bold; background-color: rgba(255,255,255,0.8); padding: 15px; border-radius: 8px;">
            Si me quieres ver, correctamente debes responder.
        </p>`;
}

// Función principal del cronómetro
function actualizarCronometro(reset = false) {
    if (reset) {
        clearInterval(cronometro);
        cronometro = null;
        tiempo = tiempoMaximo;
        cronometroElem.textContent = "02:00";
        return;
    }

    if (cronometro) return; // Evita múltiples cronómetros

    cronometro = setInterval(() => {
        tiempo--;
        const min = String(Math.floor(tiempo / 60)).padStart(2, "0");
        const seg = String(tiempo % 60).padStart(2, "0");
        cronometroElem.textContent = `${min}:${seg}`;

        if (tiempo <= 0) {
            clearInterval(cronometro);
            cronometro = null;
            tiempoAgotado();
        }
    }, 1000);
}

// Función para cuando se agota el tiempo
function tiempoAgotado() {
    juegoActivo = false;
    mostrarModal("¡Tiempo agotado!", "Se acabó el tiempo. Jorgito no pudo ser salvado.");
    deshabilitarTeclado();
}

// Función para empezar el juego
function empezar() {
    if (!juegoActivo) {
        inicializarJuego();
        actualizarCronometro();
        juegoActivo = true;
        habilitarTeclado();
        mostrarMensajeInicial();
    }
}

// Función para reiniciar el juego
function reiniciarJuego() {
    actualizarCronometro(true);
    juegoActivo = false;
    errores = 0;
    letrasUsadas = [];
    palabraAdivinada = [];

    erroresElem.textContent = "Errores: 0/6";
    pistaElem.textContent = "¡Adivina la palabra!";
    mostrarPalabraElem.textContent = "_ _ _ _ _ _ _";
    ahorcadoElem.innerHTML = "";
    imagenProgresoElem.innerHTML = ``;

    habilitarTodasLasTeclas();
    cerrarModal();
}

// Función para pausar el juego
function pausarJuego() {
    if (cronometro) {
        clearInterval(cronometro);
        cronometro = null;
        deshabilitarTeclado();
    } else if (juegoActivo) {
        actualizarCronometro();
        habilitarTeclado();
    }
}

// Función para salir del juego
function salirJuego() {
    actualizarCronometro(true);
    juegoActivo = false;
    reiniciarJuego();
    window.location.href = 'Controlador?menu=MenuPrincipal';
}

// Función para inicializar el juego
function inicializarJuego() {
    if (palabrasConPistas.length === 0) {
        alert("No hay palabras cargadas desde la base de datos.");
        return;
    }

    const palabraSeleccionada = palabrasConPistas[Math.floor(Math.random() * palabrasConPistas.length)];
    palabraSecreta = palabraSeleccionada.palabra.toUpperCase();
    pistaElem.textContent = palabraSeleccionada.pista;

    palabraAdivinada = Array(palabraSecreta.length).fill('_');
    actualizarPalabraMostrada();

    errores = 0;
    erroresElem.textContent = "Errores: 0/6";

    actualizarDibujoAhorcado();
}

// Función para adivinar letra
function adivinarLetra(letra) {
    if (!juegoActivo || letrasUsadas.includes(letra)) return;

    letrasUsadas.push(letra);
    deshabilitarTecla(letra);

    if (palabraSecreta.includes(letra)) {
        for (let i = 0; i < palabraSecreta.length; i++) {
            if (palabraSecreta[i] === letra) {
                palabraAdivinada[i] = letra;
            }
        }
        actualizarPalabraMostrada();
        actualizarImagenProgreso();

        if (!palabraAdivinada.includes('_')) {
            juegoActivo = false;
            clearInterval(cronometro);
            mostrarModal("¡Felicitaciones!", "Has ganado el juego y lograste salvar a Jorgito");
        }
    } else {
        errores++;
        erroresElem.textContent = `Errores: ${errores}/6`;
        actualizarDibujoAhorcado();

        if (errores >= maxErrores) {
            juegoActivo = false;
            clearInterval(cronometro);
            mostrarModal("¡Perdiste!", `Se acabaron los intentos. La palabra era: ${palabraSecreta}`);
            deshabilitarTeclado();
        }
    }
}

// Mostrar palabra
function actualizarPalabraMostrada() {
    mostrarPalabraElem.textContent = palabraAdivinada.join(' ');
}

// Dibujo ahorcado
function actualizarDibujoAhorcado() {
    const imagenesAhorcado = [
        "0.png", "1.png", "2.png", "3.png", "4.png", "Cinco.png", "6.png"
    ];

    ahorcadoElem.innerHTML = "";
    const img = document.createElement("img");
    img.className = "imagen-ahorcado";

    if (errores > 0 && errores <= 6) {
        img.src = `${contextPath}/Images/${imagenesAhorcado[errores]}`;
        img.alt = `Ahorcado - Error ${errores}`;
    } else {
        img.src = `${contextPath}/Images/${imagenesAhorcado[0]}`;
        img.alt = "Ahorcado - Estado inicial";
    }

    ahorcadoElem.appendChild(img);
}

// Imagen de progreso
function actualizarImagenProgreso() {
    imagenProgresoElem.innerHTML = "";

    if (!palabraAdivinada.includes('_')) {
        const img = document.createElement("img");
        img.className = "imagen-progreso";

        // Nombre de la imagen dinámico = palabra con mayúscula inicial
        const nombreImagen = palabraSecreta.charAt(0).toUpperCase() + palabraSecreta.slice(1).toLowerCase() + ".jpg";
        img.src = `${contextPath}/Images/${nombreImagen}`;
        img.alt = `Imagen de ${palabraSecreta}`;

        imagenProgresoElem.appendChild(img);
    }
}

// Teclado
function deshabilitarTecla(letra) {
    const teclas = document.querySelectorAll('.tecla');
    teclas.forEach(tecla => {
        if (tecla.textContent === letra) {
            tecla.disabled = true;
            tecla.classList.add('deshabilitada');
        }
    });
}

function deshabilitarTeclado() {
    document.querySelectorAll('.tecla').forEach(tecla => tecla.disabled = true);
}

function habilitarTeclado() {
    document.querySelectorAll('.tecla').forEach(tecla => {
        if (!letrasUsadas.includes(tecla.textContent)) {
            tecla.disabled = false;
        }
    });
}

function habilitarTodasLasTeclas() {
    document.querySelectorAll('.tecla').forEach(tecla => {
        tecla.disabled = false;
        tecla.classList.remove('deshabilitada');
    });
}

// Modal
function mostrarModal(titulo, mensaje) {
    tituloModalElem.textContent = titulo;
    mensajeModalElem.textContent = mensaje;
    modalElem.style.display = 'flex';
}

function cerrarModal() {
    modalElem.style.display = 'none';
}

// Eventos
document.addEventListener('keydown', function (event) {
    if (juegoActivo) {
        const letra = event.key.toUpperCase();
        if (letra.match(/[A-ZÑ]/) && letra.length === 1) {
            adivinarLetra(letra);
        }
    }
});

modalElem.addEventListener('click', function (event) {
    if (event.target === modalElem) {
        cerrarModal();
    }
});

// Al cargar la página 🔹 primero carga palabras desde DB
document.addEventListener('DOMContentLoaded', function () {
    cargarPalabras().then(() => {
        reiniciarJuego();
    });
});
