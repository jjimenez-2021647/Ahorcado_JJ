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

// Banco de palabras con pistas (puedes expandir esto)
const palabrasConPistas = [
    {palabra: "MURCIELAGO", pista: "Este animal es pequeño, su nombre lleva todas las vocales."},
    {palabra: "COCODRILO", pista: "Este animal es grande, verde, de dientes fuertes."},
    {palabra: "PATINETA", pista: "Tengo 4 ruedas, lija y tornillos."},
    {palabra: "MICROFONO", pista: "Me usan cuando quieren ser escuchados."},
    {palabra: "ROMPECABEZAS", pista: "Soy un dolor de cabeza para quienes no tienen paciencia."},
];

// Función para mostrar el mensaje inicial
function mostrarMensajeInicial() {
    imagenProgresoElem.innerHTML = `<p style="text-align: center; color:black; font-size: 16px; margin: 20px; line-height: 1.4;">
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

    if (cronometro)
        return; // Evita múltiples cronómetros

    cronometro = setInterval(() => {
        tiempo--;
        const min = String(Math.floor(tiempo / 60)).padStart(2, "0");
        const seg = String(tiempo % 60).padStart(2, "0");
        cronometroElem.textContent = `${min}:${seg}`;

        if (tiempo <= 0) {
            clearInterval(cronometro);
            cronometro = null;
            tiempoAgotado(); // Función que maneja cuando se acaba el tiempo
        }
    }, 1000);
}

// Función para cuando se agota el tiempo
function tiempoAgotado() {
    juegoActivo = false;
    mostrarModal("¡Tiempo agotado!", "Se acabó el tiempo. Jorgito no pudo ser salvado.");
    deshabilitarTeclado();
}

// Función para empezar el juego (conecta con el botón "Empezar")
function empezar() {
    if (!juegoActivo) {
        inicializarJuego();
        actualizarCronometro();
        juegoActivo = true;
        habilitarTeclado();
        // CAMBIO: Restaurar el mensaje inicial en lugar de dejar vacío
        mostrarMensajeInicial();

    }
}

// Función para reiniciar el juego (conecta con el botón "Reiniciar")
function reiniciarJuego() {
    actualizarCronometro(true); // Reset del cronómetro
    juegoActivo = false;
    errores = 0;
    letrasUsadas = [];
    palabraAdivinada = [];

    // Resetear interfaz
    erroresElem.textContent = "Errores: 0/6";
    pistaElem.textContent = "¡Adivina la palabra!";
    mostrarPalabraElem.textContent = "_ _ _ _ _ _ _";
    ahorcadoElem.innerHTML = "";
    imagenProgresoElem.innerHTML = ``;

    // Habilitar todas las teclas
    habilitarTodasLasTeclas();
    cerrarModal();
}

// Función para pausar el juego (conecta con el botón "Pausar")
function pausarJuego() {
    if (cronometro) {
        clearInterval(cronometro);
        cronometro = null;
        deshabilitarTeclado();
    } else if (juegoActivo) {
        // Si está pausado, reanudar
        actualizarCronometro();
        habilitarTeclado();
    }
}

// Función para salir del juego (conecta con el botón "Salir")
function salirJuego() {
    actualizarCronometro(true); // Reset del cronómetro
    juegoActivo = false;
    reiniciarJuego();
    window.location.href = 'Controlador?menu=Principal';
}

// Función para inicializar el juego
function inicializarJuego() {
    // Seleccionar palabra aleatoria
    const palabraSeleccionada = palabrasConPistas[Math.floor(Math.random() * palabrasConPistas.length)];
    palabraSecreta = palabraSeleccionada.palabra;
    pistaElem.textContent = palabraSeleccionada.pista;

    // Inicializar array de palabra adivinada
    palabraAdivinada = Array(palabraSecreta.length).fill('_');
    actualizarPalabraMostrada();

    // Resetear errores
    errores = 0;
    erroresElem.textContent = "Errores: 0/6";

    // Mostrar imagen por defecto del ahorcado
    actualizarDibujoAhorcado();
}

// Función para adivinar una letra (conecta con las teclas del teclado)
function adivinarLetra(letra) {
    if (!juegoActivo || letrasUsadas.includes(letra)) {
        return;
    }

    letrasUsadas.push(letra);
    deshabilitarTecla(letra);

    if (palabraSecreta.includes(letra)) {
        // Letra correcta
        for (let i = 0; i < palabraSecreta.length; i++) {
            if (palabraSecreta[i] === letra) {
                palabraAdivinada[i] = letra;
            }
        }
        actualizarPalabraMostrada();
        actualizarImagenProgreso();

        // Verificar si ganó
        if (!palabraAdivinada.includes('_')) {
            juegoActivo = false;
            clearInterval(cronometro);
            mostrarModal("¡Felicitaciones!", "Has ganado el juego y lograste salvar a Jorgito");
        }
    } else {
        // Letra incorrecta
        errores++;
        erroresElem.textContent = `Errores: ${errores}/6`;
        actualizarDibujoAhorcado();

        // Verificar si perdió
        if (errores >= maxErrores) {
            juegoActivo = false;
            clearInterval(cronometro);
            mostrarModal("¡Perdiste!", `Se acabaron los intentos. La palabra era: ${palabraSecreta}`);
            deshabilitarTeclado();
        }
    }
}

// Función para actualizar la palabra mostrada
function actualizarPalabraMostrada() {
    mostrarPalabraElem.textContent = palabraAdivinada.join(' ');
}

// Función para actualizar el dibujo del ahorcado con imágenes
function actualizarDibujoAhorcado() {
    // Array de nombres de las imágenes del ahorcado
    const imagenesAhorcado = [
        "0.png", // Foto por default
        "1.png", // Error 1
        "2.png", // Error 2
        "3.png", // Error 3
        "4.png", // Error 4
        "Cinco.png", // Error 5
        "6.png"  // Error 6 (juego terminado)
    ];

    // Limpiar el contenido anterior
    ahorcadoElem.innerHTML = "";

    // Crear el elemento imagen
    const img = document.createElement("img");
    img.className = "imagen-ahorcado";

    // Si hay errores, mostrar la imagen correspondiente al error
    if (errores > 0 && errores <= 6) {
        img.src = `${contextPath}/Images/${imagenesAhorcado[errores]}`;
        img.alt = `Ahorcado - Error ${errores}`;
    } else {
        // Si no hay errores, mostrar la imagen por defecto
        img.src = `${contextPath}/Images/${imagenesAhorcado[0]}`;
        img.alt = "Ahorcado - Estado inicial";
    }

    // Agregar la imagen al contenedor
    ahorcadoElem.appendChild(img);
}

// Función para actualizar la imagen progresiva (cuando resuelve completamente la palabra)
function actualizarImagenProgreso() {
    // Limpiar el contenido anterior del div imagenProgreso
    imagenProgresoElem.innerHTML = "";

    // Solo mostrar imagen si la palabra está completamente resuelta
    if (!palabraAdivinada.includes('_')) {
        // Crear el elemento imagen
        const img = document.createElement("img");
        img.className = "imagen-progreso";

        // Determinar qué imagen mostrar según la palabra secreta
        let nombreImagen = "";
        switch (palabraSecreta) {
            case "MURCIELAGO":
                nombreImagen = "Murcielago.jpg";
                break;
            case "COCODRILO":
                nombreImagen = "Cocodrilo.jpg";
                break;
            case "PATINETA":
                nombreImagen = "Patineta.jpg";
                break;
            case "MICROFONO":
                nombreImagen = "Microfono.jpg";
                break;
            case "ROMPECABEZAS":
                nombreImagen = "Rompecabezas.jpg";
                break;
            default:
                nombreImagen = "default.jpg"; // Por si agregas más palabras
        }

        img.src = `${contextPath}/Images/${nombreImagen}`;
        img.alt = `Imagen de ${palabraSecreta}`;

        // Agregar la imagen al contenedor
        imagenProgresoElem.appendChild(img);
    }
}

// Funciones para manejar el teclado
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
    const teclas = document.querySelectorAll('.tecla');
    teclas.forEach(tecla => {
        tecla.disabled = true;
    });
}

function habilitarTeclado() {
    const teclas = document.querySelectorAll('.tecla');
    teclas.forEach(tecla => {
        if (!letrasUsadas.includes(tecla.textContent)) {
            tecla.disabled = false;
        }
    });
}

function habilitarTodasLasTeclas() {
    const teclas = document.querySelectorAll('.tecla');
    teclas.forEach(tecla => {
        tecla.disabled = false;
        tecla.classList.remove('deshabilitada');
    });
}

// Funciones para el modal
function mostrarModal(titulo, mensaje) {
    tituloModalElem.textContent = titulo;
    mensajeModalElem.textContent = mensaje;
    modalElem.style.display = 'flex';
}

function cerrarModal() {
    modalElem.style.display = 'none';
}

// Event listeners adicionales
document.addEventListener('keydown', function (event) {
    if (juegoActivo) {
        const letra = event.key.toUpperCase();
        if (letra.match(/[A-ZÑ]/) && letra.length === 1) {
            adivinarLetra(letra);
        }
    }
});

// Cerrar modal al hacer clic fuera de él
modalElem.addEventListener('click', function (event) {
    if (event.target === modalElem) {
        cerrarModal();
    }
});

// Inicializar el juego al cargar la página
document.addEventListener('DOMContentLoaded', function () {
    reiniciarJuego();
});