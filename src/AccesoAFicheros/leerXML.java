package AccesoAFicheros;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class leerXML {
	public static void main(String[] args) {
		File xml = new File("src/AccesoAFicheros/libros.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xml);
			NodeList libros = document.getElementsByTagName("libro");

			//leer valores del xml y mostrarlos por pantalla
			for (int i = 0; i < libros.getLength(); i++) {
				Node libroNode = libros.item(i);

				if (libroNode.getNodeType() == Node.ELEMENT_NODE) {
					Element libroElement = (Element) libroNode;
					//creamos el campo
					Element nodoInventario = document.createElement("inventario");
					nodoInventario.appendChild(document.createTextNode("" + i));
					libroNode.appendChild(nodoInventario);

					document.createElement("nInventario");
					String libroTitulo = libroElement.getElementsByTagName("titulo").item(0).getTextContent().trim();
					String libroAutor = libroElement.getElementsByTagName("autor").item(0).getTextContent().trim();
					System.out.printf("Libro: %s\ndel autor: %s\n", libroTitulo, libroAutor);
				}
			}
			//enviar el document modificado al xml, sin esto no se modificaría libros.xml
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(xml);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
