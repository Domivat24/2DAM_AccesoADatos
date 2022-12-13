package Examen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class AccesoABaseDeDatos {
	static Scanner inText = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebas", "root", "root");
			blackFriday(con);
			PreparedStatement deleteBlancos = con.prepareStatement("DELETE from tienda WHERE Color = 'Blanco'");
			deleteBlancos.executeUpdate();
			PreparedStatement insertSQL = con.prepareStatement("INSERT INTO tienda (Part_number, Descripcion, Color, Familia, Precio) VALUES (?,?,?,?,?)");
			guardarTxt(con);
			insertTupla(insertSQL);
			PreparedStatement changePart_Number = con.prepareStatement("UPDATE tienda SET Part_number = 12 WHERE Part_number = 11");
			changePart_Number.executeUpdate();


			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void insertTupla(PreparedStatement insertSQL) {
		try {
			String part_number, descripcion, color, familia, precio;
			System.out.println("Introduce el part_number");
			part_number = inText.nextLine();
			System.out.println("Introduce la descripcion");
			descripcion = inText.nextLine();
			System.out.println("Introduce el color");
			color = inText.nextLine();
			System.out.println("Introduce la familia");
			familia = inText.nextLine();
			System.out.println("Introduce el precio");
			precio = inText.nextLine();
			insertSQL.setInt(1, Integer.parseInt(part_number));
			insertSQL.setString(2, descripcion);
			insertSQL.setString(3, color);
			insertSQL.setString(4, familia);
			insertSQL.setInt(5, Integer.parseInt(precio));
			insertSQL.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void guardarTxt(Connection con) {
		try {
			Statement stmt = con.createStatement();
			String producto;
			ResultSet rs = stmt.executeQuery("select * from tienda");
			FileWriter file = new FileWriter("src/Examen/elementosSeparados.txt");
			while (rs.next()) {
				producto = rs.getInt("Part_number") + "/" + rs.getString("Descripcion") + "/" + rs.getString("Color") + "/" + rs.getString("Familia") + "/" + rs.getInt("Precio");
				file.write(producto);
				file.write("\n");
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void blackFriday(Connection con) {
		try {
			Statement stmt = con.createStatement();
			PreparedStatement sentencia;
			ResultSet rs;
			rs = stmt.executeQuery("select * from tienda WHERE Familia = 'monitores'");
			while (rs.next()) {
				sentencia = con.prepareStatement("UPDATE tienda SET Precio = Precio*0.80 WHERE Part_number = ?");
				sentencia.setInt(1, rs.getInt("Part_number"));
				sentencia.executeUpdate();
			}
			rs = stmt.executeQuery("select * from tienda WHERE Familia = 'ordenadores'");
			while (rs.next()) {
				sentencia = con.prepareStatement("UPDATE tienda SET Precio = Precio*0.70 WHERE Part_number = ?");
				sentencia.setInt(1, rs.getInt("Part_number"));
				sentencia.executeUpdate();
			}
			rs = stmt.executeQuery("SELECT * from tienda where Familia != 'monitores' AND Familia != 'ordenadores'");
			while (rs.next()) {
				sentencia = con.prepareStatement("UPDATE tienda SET Precio = Precio*0.95 WHERE Part_number = ?");
				sentencia.setInt(1, rs.getInt("Part_number"));
				sentencia.executeUpdate();
			}
			//guardar en xml

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
			Element root = document.createElement("productos");
			rs = stmt.executeQuery("SELECT * from tienda");
			while (rs.next()) {
				Element producto = document.createElement("producto");
				Element part_Number = document.createElement("Part_number");
				Element descripcion = document.createElement("Descripcion");
				Element color = document.createElement("Color");
				Element familia = document.createElement("Familia");
				Element precio = document.createElement("Precio");


				part_Number.appendChild(document.createTextNode(String.valueOf(rs.getInt("Part_number"))));
				descripcion.appendChild(document.createTextNode(rs.getString("Descripcion")));
				color.appendChild(document.createTextNode(rs.getString("Color")));
				familia.appendChild(document.createTextNode(rs.getString("Familia")));
				precio.appendChild(document.createTextNode(String.valueOf(rs.getInt("Precio"))));
				producto.appendChild(part_Number);
				producto.appendChild(descripcion);
				producto.appendChild(color);
				producto.appendChild(familia);
				producto.appendChild(precio);
				root.appendChild(producto);
			}
			document.appendChild(root);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(document);
			FileWriter xml = new FileWriter("src/Examen/blackfriday.xml");
			StreamResult result = new StreamResult(xml);
			transformer.transform(source, result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
