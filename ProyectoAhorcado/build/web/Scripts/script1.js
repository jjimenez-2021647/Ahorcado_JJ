// Rompecabezas de numeros
let puzzleContainer = document.getElementById("puzzle");
let mensaje = document.getElementById("mensaje");

// Obtener el context path desde la variable global (igual que en el ahorcado)
const contextPath = window.contextPath || '';

let piezas = [
    "split-1.png", "split-2.png", "split-3.png", "split-4.png",
    "split-5.png", "split-6.png", "split-7.png", "split-8.png",
    "split-9.png", "split-10.png", "split-11.png", "split-12.png",
    "split-13.png", "split-14.png", "split-15.png", ""];

let terminado = [
    "split-1.png", "split-2.png", "split-3.png", "split-4.png",
    "split-5.png", "split-6.png", "split-7.png", "split-8.png",
    "split-9.png", "split-10.png", "split-11.png", "split-12.png",
    "split-13.png", "split-14.png", "split-15.png", "split-16.png"];

let estado = [];

let cronometro = null;
const tiempoMaximo = 180; // 3 minutos
let tiempo = tiempoMaximo;
let timeoutPerder = null;

// Crear el elemento del cronómetro (sin estilos en JS)
let cronometroElem = document.getElementById("tiempo");

// Cronómetro compacto
function actualizarCronometro(reset = false) {
    if (reset) {
        clearInterval(cronometro);
        cronometro = null;
        tiempo = 180;
        cronometroElem.textContent = "03:00";
        return;
    }
    if (cronometro)
        return;
    cronometro = setInterval(() => {
        tiempo--;
        const min = String(Math.floor(tiempo / 60)).padStart(2, "0");
        const seg = String(tiempo % 60).padStart(2, "0");
        cronometroElem.textContent = `${min}:${seg}`;
        if (tiempo <= 0) {
            clearInterval(cronometro);
            cronometro = null;
            perdio();
        }
    }, 1000);
}

// Función para mezclar piezas
function mezclar(array) {
    let copia = [...array];
    for (let i = copia.length - 1; i > 0; i--) {
        let j = Math.floor(Math.random() * (i + 1));
        [copia[i], copia[j]] = [copia[j], copia[i]];
    }
    return copia;
}

// Dibujar el puzzle
function dibujar() {
    puzzleContainer.innerHTML = "";
    estado.forEach((valor, i) => {
        let celda = document.createElement("div");
        celda.classList.add("celda");
        if (valor === "") {
            celda.classList.add("vacio");
        } else {
            let img = document.createElement("img");
            // CORRECCIÓN: Usar contextPath como en el ahorcado
            img.src = `${contextPath}/Images/` + valor;
            img.alt = valor;
            img.style.width = "100%";
            img.style.height = "100%";
            celda.appendChild(img);
            celda.addEventListener("click", () => mover(i));
        }
        puzzleContainer.appendChild(celda);
    });
}

// Intentar mover pieza
function mover(indice) {
    let vacio = estado.indexOf("");
    let filas = 4;
    let col = indice % filas;
    let fila = Math.floor(indice / filas);
    let colVacio = vacio % filas;
    let filaVacio = Math.floor(vacio / filas);

    // Iniciar cronómetro en el primer movimiento
    if (tiempo === 180 && !cronometro) {
        actualizarCronometro();
    }

    // Verificar si es adyacente
    if ((Math.abs(col - colVacio) === 1 && fila === filaVacio) ||
            (Math.abs(fila - filaVacio) === 1 && col === colVacio)) {
        [estado[indice], estado[vacio]] = [estado[vacio], estado[indice]];
        dibujar();
        verificar();
    }
}

// Verificar si ganó
function verificar() {
    if (JSON.stringify(estado) === JSON.stringify(piezas)) {
        clearInterval(cronometro);
        cronometro = null;
        clearTimeout(timeoutPerder);
        timeoutPerder = null;
        alert("Terminado exitosamente. 👻");
        estado = [...terminado];
        dibujar();
        actualizarCronometro(true);
    }
}

// Reiniciar juego
function reiniciar() {
    estado = mezclar(piezas);
    mensaje.innerText = "";
    dibujar();
    actualizarCronometro(true);
}

function armar() {
    actualizarCronometro(true);
    clearTimeout(timeoutPerder);
    timeoutPerder = null;
    estado = [...terminado];
    dibujar();
    alert("Hollow Knight armado automáticamente.");
}

// Iniciar al cargar
reiniciar();

function perdio() {
    alert("No lograste terminar, bot. 🫵😹");
    reiniciar();
}

// Función para manejar el clic en salir
function salir() {
    const siButton = document.querySelector('button[onclick*="rompecabezas.jsp"]');
    const noButton = document.querySelector('button[onclick="salir()"]');
    
    // Agregar clases CSS para las animaciones
    siButton.classList.add('agrandar');
    noButton.classList.add('achicar');
    
    // Opcional: cambiar el texto del botón "Sí" para ser más persuasivo
    const siButtonText = siButton.querySelector('.front span');
    const textosPersusivos = [
        "¡Vamos, juega!",
        "¡Solo un juego!",
        "¡Será divertido!",
        "¡Inténtalo!",
        "¡Por favor!",
        "¡Solo uno!",
        "¡Te gustará!"
    ];
    
    const textoAleatorio = textosPersusivos[Math.floor(Math.random() * textosPersusivos.length)];
    siButtonText.textContent = textoAleatorio;
}