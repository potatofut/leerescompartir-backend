package com.compartir.libros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Leer es Compartir.
 * Esta clase inicia la aplicación Spring Boot y configura el contexto de la aplicación.
 * 
 * <p>La aplicación está diseñada para gestionar una plataforma de intercambio de libros,
 * permitiendo a los usuarios compartir, prestar y reservar libros.</p>
 *
 * @author Sergio
 */
@SpringBootApplication
public class LibrosApplication {

	/**
	 * Método principal que inicia la aplicación Spring Boot.
	 * 
	 * @param args Argumentos de línea de comandos pasados a la aplicación
	 */
	public static void main(String[] args) {
		SpringApplication.run(LibrosApplication.class, args);
	}

}
