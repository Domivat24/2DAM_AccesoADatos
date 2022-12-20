package ManejoConectores;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.Scanner;

public class starwars {
	static Scanner inNum = new Scanner(System.in);
	static Scanner inText = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/starwars", "star", "wars");
			//listPlanets(con);
			personajesVII(con);

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void personajesVII(Connection con) {
		PreparedStatement statement;
		ResultSet rs;

		try {
			Statement stmt = con.createStatement();


			for (int i = 0; i < 3; i++) {
				rs = stmt.executeQuery("SELECT * FROM starwars.characters ORDER BY id DESC");
				statement = con.prepareStatement("INSERT INTO starwars.characters (id, name, height, mass, hair_color, skin_color, eye_color, birth_year, gender, planet_id, url) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
				rs.next();
				statement.setInt(1, rs.getInt("id") + 1);
				System.out.println("Introduce name");
				statement.setString(2, inText.nextLine());
				System.out.println("Introduce height");
				statement.setString(3, inNum.nextLine());
				System.out.println("Introduce mass");
				statement.setFloat(4, inNum.nextFloat());
				System.out.println("Introduce hair_color");
				statement.setString(5, inText.nextLine());
				System.out.println("Introduce skin_color");
				statement.setString(6, inText.nextLine());
				System.out.println("Introduce eye_color");
				statement.setString(7, inText.nextLine());
				System.out.println("Introduce birth_year");
				statement.setString(8, inText.nextLine());
				System.out.println("Introduce gender");
				statement.setString(9, inText.nextLine());
				System.out.println("Introduce planet_id");
				statement.setInt(10, inNum.nextInt());
				System.out.println("Introduce url");
				statement.setString(11, inText.nextLine());
				statement.executeUpdate();
				statement = con.prepareStatement("INSERT INTO starwars.character_films (id_character, id_film) VALUES (?,7);");
				statement.setInt(1, rs.getInt("id") + 1);
				statement.executeUpdate();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void listPlanets(Connection con) {
		int valor1, valor2;
		for (int i = 0; i < 3; i++) {
			try {
				System.out.println("Introduzca valor mayor a:");
				valor1 = inNum.nextInt();
				System.out.println("Introduzca valor menor a:");
				valor2 = inNum.nextInt();
				PreparedStatement stmt = con.prepareStatement("SELECT * from planets WHERE diameter >=  ? AND diameter <= ?");
				stmt.setInt(1, valor1);
				stmt.setInt(2, valor2);
				ResultSet rs = stmt.executeQuery();

				while (rs.next()) {
					for (int j = 1; j < rs.getMetaData().getColumnCount(); j++) {
						System.out.print(rs.getString(j) + ";");
					}
					System.out.println();
				}

			} catch (SQLException e) {
				System.out.println("Problema con la consulta");
				e.printStackTrace();
			}
		}
	}
	public void personajesMuertos(Connection con) {

	}
}
