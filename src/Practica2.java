import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Practica2 {
	static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
		String directorio;
		System.out.println("Introduzca un directorio");
		File ruta;
		directorio = in.nextLine();
		ruta = new File(directorio);
		String[] archivos = {"\\PrimerDirectorio", "\\SegundoDirectorio", "\\TercerDirectorio"};
		try {

			if (ruta.exists()) {
				for (String o : archivos) {
					ruta = new File(ruta.getPath() + o);
					ruta.mkdir();
				}
				try {
					ruta = new File(ruta.getPath() + "\\Mifichero.txt");
					ruta.createNewFile();
					FileWriter fw = new FileWriter(ruta);
					fw.write(directorio);
					fw.close();
				} catch (IOException e) {
					System.out.println("El archivo Mifichero.txt ya existe.");
				}
			} else {
				System.out.println("El directorio no se encuentra en la ruta indicada.");
			}
		} catch (Exception e) {
			System.out.println("Excepción: no se ha introducido nada.");
		}
	}
}
