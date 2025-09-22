package com.josuejimenez.AplicacionAhorcado;

//Importaciones de Spring Boot
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Importaciones para el mini servidor HTTP
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.BindException;
import java.net.InetSocketAddress;

@SpringBootApplication
public class AplicacionAhorcadoApplication implements CommandLineRunner {

    //Punto de entrada de la aplicacion
    public static void main(String[] args) {
        try {
            //Intentamos arrancar la aplicacion
            SpringApplication.run(AplicacionAhorcadoApplication.class, args);
        } catch (Exception e) { //Si pasa algun error intentamos buscarlo
            Throwable cause = e;
            boolean puertoOcupado = false; //Declaramos esta variable por si el puerto llega a estar ocupado

            //Verifica si el error fue porque el puerto ya estaba en uso
            while (cause != null) {
                if (cause instanceof BindException) { //BindException significa puerto ocupado
                    puertoOcupado = true; //Marcamos que el puerto esta ocupado
                    break; //como no necesitamos buscar más cerramos el bucle
                }
                cause = cause.getCause(); //Buscamos alguna otra causa
            }

            if (puertoOcupado) { //Si el puerto esta ocupado
                int puerto1 = leerPuertoProperties(); //Leemos el puerto que esta en application.properties
                int puerto2 = puerto1; //Empezamos desde el puerto1 y vamos sumando
                boolean servidorLevantado = false;

                while (!servidorLevantado) {
                    try {
                        puerto2++; //Probamos el siguiente puerto
                        levantarMiniServidor(puerto2, puerto1);
                        servidorLevantado = true; //Si no lanza excepción, ya está levantado
                    } catch (IOException ioException) {
                        System.err.println("Puerto " + puerto2 + " ocupado, intentando con el siguiente...");
                    }
                }
            }
        }
    }

    //Metodo para leer el puerto que esta definido en application.properties
    private static int leerPuertoProperties() {
        java.util.Properties props = new java.util.Properties(); //Se define props como nuestro objeto para manejar propiedades
        try (java.io.InputStream is = AplicacionAhorcadoApplication.class
                .getClassLoader().getResourceAsStream("application.properties")) { //Abrimos el archivo properties
            if (is != null) {
                props.load(is); //Cargamos las propiedades
                return Integer.parseInt(props.getProperty("server.port", "8080")); //sera nuestro default
                //Si hay un puerto escrito en el server.port lo usamos, si no usamos 8080 por default
            }
        } catch (Exception e) {
            e.printStackTrace(); //Si falla la lectura de las propiedades pues mostramos un error
        }
        return 8080; //Valor default si algo sale mal
    }

    //Metodo que levanta el mini-servidor HTTP para mostrar el error
    private static void levantarMiniServidor(int puerto2, int puerto1) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(puerto2), 0);
        //Creamos el mini servidor en el puerto2 porque el puerto1 esta ocupado

        //Se crea el contexto para /api/usuarios
        server.createContext("/api/usuarios", exchange -> {
            String respuesta = "Error: El puerto " + puerto1 + " esta ocupado, la API no arranco, se debe cambiar de puerto";
            exchange.sendResponseHeaders(200, respuesta.getBytes().length); //Colocamos el codigo de estado 200 para que devuelva OK
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(respuesta.getBytes()); //Respondemos con mensaje de error
            }
        });

        //Se crea el contexto para /api/palabras
        server.createContext("/api/palabras", exchange -> {
            String respuesta = "Error: El puerto " + puerto1 + " esta ocupado, la API no arranco, se debe cambiar de puerto";
            exchange.sendResponseHeaders(200, respuesta.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(respuesta.getBytes()); //Respondemos con mensaje de error
            }
        });

        server.start(); //Iniciamos el mini servidor

        //Endpoints para ver el mensaje de error
        System.out.println("Se activo un mini servidor por si desea visualizar su mensaje en el siguiente numero de puerto: " + puerto2);
        System.out.println("Endpoints disponibles para ver el error:");
        System.out.println(" - http://localhost:" + puerto2 + "/api/usuarios");
        System.out.println(" - http://localhost:" + puerto2 + "/api/palabras");
    }

    @Override
    public void run(String... args) {
        System.out.println("Api Lista 👻");
    }
}