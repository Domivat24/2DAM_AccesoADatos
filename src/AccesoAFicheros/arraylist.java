package AccesoAFicheros;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class arraylist {
	public static void main(String[] args) {
		try {
			FileWriter impares = new FileWriter("src/AccesoAFicheros/impares.txt");
			ArrayList<Integer> enteros = new ArrayList<>();
			int i = 0;
			do {
				enteros.add(i);
				i++;

			} while (i < 15);

			for (i = 0; i < enteros.size(); i++) {

				if (enteros.get(i) % 2 == 0) {
					System.out.println("Par" + enteros.get(i));

				} else {

					impares.write("Impar " + enteros.get(i)+"\n");
				}

			}
			impares.close();
		} catch (Exception io) {
			io.printStackTrace();
		}
	}
}
