package AccesoAFicheros;

import java.sql.*;

public class MysqlConnection {
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebas", "root", "root");
			Statement stmt = con.createStatement();
			//from tienda
			ResultSet rs = stmt.executeQuery("select * from conectados");
			while (rs.next()) {
				// System.out.println(rs.getInt("Part_number") + " " + rs.getString("Descripcion") + " "  + " " + rs.getString("Color") + " " + rs.getString("Familia") + " " + rs.getInt("Precio"));
				System.out.println(rs.getInt("Id") + " " + rs.getString("Nombre") + " " + rs.getString("Apellido") + " " + rs.getString("Email") + " " + rs.getString("Genero") + " " + rs.getString("Ip"));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println((System.currentTimeMillis()));
	}
}
