package ManejoFicheros;

import java.io.*;

public class Actividad3 {
	public static void main(String[] args) {
		String cadena = "";
		int c;
		FileWriter destino = null;
		FileReader reader = null;
		FileReader destinoR = null;
		BufferedReader buffer = null;

		try {
			reader = new FileReader("src/ManejoFicheros/origen.txt");
			while (true) {
				c = reader.read();
				if (c != -1) {
					cadena += (char) c;
				} else {
					break;
				}
			}
			destino = new FileWriter("src/ManejoFicheros/destino.txt");
			StringBuilder cadenaM = new StringBuilder();

			cadenaM.append(cadena);

			cadenaM.reverse();

			destino.write(String.valueOf(cadenaM));
			destino.write("\nIvan Jimenez Guisado");
			//Cerramos el FileWriter de destino para poder leerlo con otro FileReader
			destino.close();

			destinoR = new FileReader("src/ManejoFicheros/destino.txt");
			buffer = new BufferedReader(destinoR);
			cadena = buffer.readLine();
			while (cadena!=null) {
				System.out.println(cadena);
				cadena= buffer.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				destino.close();
				reader.close();
				destinoR.close();
				buffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
}
