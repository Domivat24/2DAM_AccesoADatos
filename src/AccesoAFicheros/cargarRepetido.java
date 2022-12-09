package AccesoAFicheros;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class cargarRepetido {
	public static void main(String[] args) {
		HashMap<String, Book> hashBooks = new HashMap<>();
		File xml = new File("src/AccesoAFicheros/libros.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xml);
			NodeList books = document.getElementsByTagName("book");
			for (int i = 0; i < books.getLength(); i++) {
				Node bookNode = books.item(i);
				if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
					Element bookElement = (Element) bookNode;
					hashBooks.put("Book" + i, createBook(bookElement));
				}
			}
			FileWriter fw = new FileWriter("books.txt");
			for (String b : hashBooks.keySet()) {
				fw.write(hashBooks.get(b).toString());
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Book createBook(Element bookElement) {
		String[] attributes = {"author", "title", "genre", "price", "publish_date", "description"};
		String[] values = new String[attributes.length];
		for (int i = 0; i < values.length; i++) {
			values[i] = bookElement.getElementsByTagName(attributes[i]).item(0).getTextContent().trim();
			if (values[i].equals("Romance")) {
				values[i] = "SEXOSEXO";
			}
		}
		return new Book(values);
	}
}
