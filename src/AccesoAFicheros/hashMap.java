package AccesoAFicheros;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

public class hashMap {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String animal, especie;
		try {
			HashMap<String, String> animalitos = new HashMap<String, String>();
			File xml = new File("src/AccesoAFicheros/cuidadora.xml");
			//me aseguro de crear el fichero;

			int iteracionesAleatorias =  4+ (int) (Math.random() * 2);
			System.out.println("Va a introducir animales " + iteracionesAleatorias + " veces.");

			for (int i = 0; i < iteracionesAleatorias; i++) {
				System.out.println("Introduzca nombre del animal.");
				animal = in.nextLine();
				System.out.println("Introduzca especie del animal.");
				especie = in.nextLine();
				animalitos.put(animal, especie);
			}

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			//elemento root
			Element root = document.createElement("cuidadora");
			document.appendChild(root);

			for (String name : animalitos.keySet()) {

				//hago todos los elementos del animal(nombre,especie) para luego anexarlo al elemento origen de cada animal
				Element animalito = document.createElement("animal");
				Element nombresito = document.createElement("nombre");
				Element especiesita = document.createElement("especie");
				especiesita.appendChild(document.createTextNode(animalitos.get(name)));
				nombresito.appendChild(document.createTextNode(name));
				animalito.appendChild(nombresito);
				animalito.appendChild(especiesita);
				root.appendChild(animalito);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			//format the output xml as indented :)
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(xml);
			transformer.transform(source, result);
			//la salida es aleatoria al ser un hashmap, por eso el xml no tendrá el orden
			//que hemos implementado

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
