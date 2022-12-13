package Examen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class accesoAFicheros {
	static Scanner inText = new Scanner(System.in);
	static Scanner inNum = new Scanner(System.in);

	public static void main(String[] args) {
		File xml = new File("src/Examen/examen.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(xml);
			Element producto = document.createElement("producto");
			Element part_Number = document.createElement("Part_number");
			Element descripcion = document.createElement("Descripcion");
			Element color = document.createElement("Color");
			Element familia = document.createElement("Familia");
			Element precio = document.createElement("Precio");
			System.out.println("Introduzca part_number");
			int number = inNum.nextInt();
			System.out.println("Introduzca descripcion");
			String desc = inText.nextLine();
			System.out.println("Intrdouzca familia");
			String fam = inText.nextLine();
			System.out.println("Introduzca precio");
			int prec = inNum.nextInt();


			part_Number.appendChild(document.createTextNode(String.valueOf(number)));
			descripcion.appendChild(document.createTextNode(desc));
			color.appendChild(document.createTextNode("rosa"));
			familia.appendChild(document.createTextNode(fam));
			precio.appendChild(document.createTextNode(String.valueOf(prec)));
			producto.appendChild(part_Number);
			producto.appendChild(descripcion);
			producto.appendChild(color);
			producto.appendChild(familia);
			producto.appendChild(precio);
			document.appendChild(producto);
			document.getElementsByTagName("productos").item(0).appendChild(producto);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(xml);
			transformer.transform(source, result);

			int opc = 4;
			while (!(opc == 1 || opc == 2)) {
				System.out.println("1.Guardar en formato XXXX_new.txt\n2.Guardar en formato XXXXX_new.XML");
				opc = inNum.nextInt();
				if (opc == 1) {
					guardarTxt(document);
				}
				if (opc == 2) {
					guardarXML(document);
				}
			}
			insertarXML(document);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void guardarXML(Document document) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileWriter("src/Examen/XXXXX_new.xml"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void guardarTxt(Document document) {
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();


			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new FileWriter("src/Examen/XXXXX_new.txt"));
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void insertarXML(Document document) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebas", "root", "root");
			PreparedStatement sentencia;
			NodeList productos = document.getElementsByTagName("producto");
			for (int i = 0; i < productos.getLength(); i++) {
				Element productoDocument = (Element) productos.item(i);
				sentencia = con.prepareStatement("INSERT INTO tienda (Part_number, Descripcion, Color, Familia, Precio) VALUES (?,?,?,?,?)");
				if (productos.item(i).getNodeType() == Node.ELEMENT_NODE) {
					String[] valores = {productoDocument.getElementsByTagName("Part_number").item(0).getTextContent().trim(),
							productoDocument.getElementsByTagName("Descripcion").item(0).getTextContent().trim(),
							productoDocument.getElementsByTagName("Color").item(0).getTextContent().trim(),
							productoDocument.getElementsByTagName("Familia").item(0).getTextContent().trim(),
							productoDocument.getElementsByTagName("Precio").item(0).getTextContent().trim()};
					sentencia.setInt(1, Integer.parseInt(valores[0]));
					sentencia.setString(2, valores[1]);
					sentencia.setString(3, valores[2]);
					sentencia.setString(4, valores[3]);
					sentencia.setInt(5, Integer.parseInt(valores[4]));
				}
				sentencia.executeUpdate();
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}


	/*Metodo de comerme la cabeza demasiado de más que he dejado a medias, NO TENER EN CUENTA
	---------------------------------------------------------------------------------------------
	public static void guardarXML(Document document) {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document newDocument = builder.newDocument();
			//elemento root
			Element root = newDocument.createElement("productos");

			NodeList productos = document.getElementsByTagName("producto");
			for (int i = 0; i < productos.getLength(); i++) {
				Element productoDocument = (Element) productos.item(i);
				if (productos.item(i).getNodeType() == Node.ELEMENT_NODE) {
					//He tenido que hacer toda esta cosa a mano porque no dejaba meter el elemento de un documento a otro
					Element producto = document.createElement("producto");
					Element part_Number = document.createElement("Part_number");
					Element descripcion = document.createElement("especie");
					Element color = document.createElement("Color");
					Element familia = document.createElement("Familia");
					Element precio = document.createElement("Precio");
					String[] valores = {productoDocument.getElementsByTagName("Part_number").item(0).getTextContent().trim(),
							productoDocument.getElementsByTagName("Descripcion").item(0).getTextContent().trim(),
							productoDocument.getElementsByTagName("Color").item(0).getTextContent().trim(),
							productoDocument.getElementsByTagName("Familia").item(0).getTextContent().trim(),
							productoDocument.getElementsByTagName("Precio").item(0).getTextContent().trim()};
					part_Number.appendChild(newDocument.createTextNode(valores[0]));
					descripcion.appendChild(newDocument.createTextNode(valores[1]));
					color.appendChild(newDocument.createTextNode(valores[2]));
					familia.appendChild(newDocument.createTextNode(productoDocument.getElementsByTagName("Familia").item(0).getTextContent().trim()));
					precio.appendChild(newDocument.createTextNode(productoDocument.getElementsByTagName("Precio").item(0).getTextContent().trim()));
					producto.appendChild(part_Number);
					producto.appendChild(descripcion);
					producto.appendChild(color);
					producto.appendChild(familia);
					producto.appendChild(precio);
					newDocument.appendChild(producto);
					newDocument.getElementsByTagName("productos").item(i).appendChild(producto);
				}

			}
			newDocument.appendChild(root);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			//format the output xml as indented :)
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource source = new DOMSource(newDocument);
			StreamResult result = new StreamResult(new FileWriter("src/Examen/XXXXX_new.xml"));
			transformer.transform(source, result);

		} catch (ParserConfigurationException | TransformerException | IOException e) {
			e.printStackTrace();
		}

	}
}*/
