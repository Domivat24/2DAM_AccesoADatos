package ManejoFicheros;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Prueba {

	public static void main(String[] args) {
		File archivo = new File("src/ManejoFicheros/archivo.txt");
		if (archivo.exists()) {
			System.out.println("Clase java del archivo" + archivo.getClass());
			System.out.println("Nombre del archivo: " + archivo.getName());
			System.out.println("Ruta absoluta del archivo: " + archivo.getAbsolutePath());
			System.out.println("Ruta relativa del archivo: " + archivo.getPath());
			System.out.println("Nº de caracteres: " + archivo.length());
			System.out.println("Espacio libre del archivo: " + archivo.getFreeSpace());
			System.out.println("Se puede leer: " + archivo.canRead());
			System.out.println("Se puede escribir: " + archivo.canWrite());
			System.out.println("Se puede ejecutar: " + archivo.canExecute());
			System.out.println("Se puede ejecutar: " + archivo.lastModified());
		} else {
			System.out.println("No existe el archivo muyayo.");
		}
	}
}
