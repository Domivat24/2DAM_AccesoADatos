package AccesoAFicheros;

import java.sql.*;
import java.util.Scanner;
import java.util.TreeSet;

public class manejoTuplas {
	static Scanner inNum = new Scanner(System.in);
	static Scanner inText = new Scanner(System.in);
	static final String[] columnas = {"Id", "Nombre", "Apellido", "Email", "Genero", "Ip"};
	static Connection con;
	static String sentenciaSql;

	public static void main(String[] args) {
		int opc = 1;
		while (opc != 0) {
			opc = menu(opc);
		}
	}

	public static int menu(int opc) {
		System.out.println("1.Insertar tupla en la base de datos\n2.Actualizar un dato\n3.Eliminar tupla\n4.Insertar consulta propia\n0.Salir");
		opc = inNum.nextInt();
		switch (opc) {
			case 1:
				insertarTupla();
				break;
			case 2:
				actualizarDato();
				break;
			case 3:
				eliminarTupla();
				break;
			case 4:
				consultaTabla();
				break;
			case 0:
				break;
			default:
				return menu(opc);
		}
		return opc;
	}

	public static void insertarTupla() {
		try {
			int id;
			establecerConexion();
			PreparedStatement sentencia;
			String salida;
			sentenciaSql = "INSERT INTO pruebas.conectados (Id, Nombre, Apellido, Email, Genero, Ip) VALUES (?,?,?,?,?,?)";
			sentencia = con.prepareStatement(sentenciaSql);
			while (true) {
				System.out.println("Introduzca el id: \n(Recuerde, ha de ser diferente de los presentes en las tablas");
				id = inNum.nextInt();
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("select Id from conectados where id=" + id);
				//Si no encuentra nada, siginifica que no existe el id, por lo tanto puede salir del bucle infinito
				if (!rs.next()) {
					sentencia.setInt(1, id);
					break;
				}
				System.out.println("Id repetido");
			}
			for (int i = 1; i < columnas.length; i++) {
				System.out.println("Introduzca el valor de " + columnas[i]);
				salida = inText.nextLine();
				sentencia.setString(i + 1, salida);
			}
			sentencia.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}
	}

	public static void actualizarDato() {
		try {
			String salida;
			establecerConexion();
			PreparedStatement sentencia;
			sentenciaSql = "UPDATE conectados SET nombre = 'variable:::', apellido = 'variable:::', email = 'variable:::', genero = 'variable:::', ip = 'variable:::' " + "WHERE variable::: = ?";
			//me he tenido que adaptar a la putada más grande que me ha pasado hoy: confiar en Java y su PreparedStatement...
			for (int i = 1; i < columnas.length; i++) {
				System.out.println("Introduzca el valor de " + columnas[i]);
				salida = inText.nextLine();
				sentenciaSql = sentenciaSql.replaceFirst("variable:::", salida);

			}
			menuColumnas(sentenciaSql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

	}

	public static void eliminarTupla() {
		try {
			establecerConexion();
			//nombre raro (variable:::) para remplazarla por el valor que yo necesite mas tarde,
			// ya que el setString del PreparedStatement define el String pasado siempre entre'',
			// por lo que no reconoce las columnas de las tablas
			sentenciaSql = "DELETE from conectados WHERE variable::: = ?";
			menuColumnas(sentenciaSql);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cerrarConexion();
		}

	}

	public static void menuColumnas(String sentenciaSql) {
		int opc;
		System.out.println("Introduzca el valor que desea encontrar coincidencias en la tabla.");
		System.out.printf("0.%s\n1.%s\n2.%s\n3.%s\n4.%s\n5.%s\n6.Salir\n", columnas[0], columnas[1], columnas[2], columnas[3], columnas[4], columnas[5]);
		opc = inNum.nextInt();
		PreparedStatement sentencia;
		switch (opc) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				try {
					System.out.println("Introduzca el " + columnas[opc] + " deseado");
					String output = inText.nextLine();
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select * from conectados where " + columnas[opc] + "='" + output + "'");

					if (rs.next()) {
						//Con el parameterCount recibo el nombre total de parametros que tiene mi sentencia, así
						// independientemente de quien llame a la funcion, se añadiran los valores de busqueda
						// en el ultimo parametro
						sentencia = con.prepareStatement(sentenciaSql.replaceFirst("variable:::", columnas[opc]));
						sentencia.setString(sentencia.getParameterMetaData().getParameterCount(), rs.getString(columnas[opc]));
						sentencia.executeUpdate();
					} else {

						//si no encuentra el valor introducido, vuelve al menu
						System.out.println("No se ha encontrado " + columnas[opc] + ", muyayo");
						menuColumnas(sentenciaSql);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
				return;
			case 0:
				try {
					System.out.println("Introduzca el id deseado:");
					int id = inNum.nextInt();
					Statement stmt = con.createStatement();
					ResultSet rs = stmt.executeQuery("select Id from conectados where id=" + id);

					if (rs.next()) {
						sentencia = con.prepareStatement(sentenciaSql.replaceFirst("variable:::", columnas[opc]));
						sentencia.setInt(sentencia.getParameterMetaData().getParameterCount(), rs.getInt("Id"));
						sentencia.executeUpdate();
						System.out.println("Tupla eliminada correctamente");
						return;

					}
					//si no encuentra id, vuelve al menu
					System.out.println("No se ha encontrado id, muyayo");
					menuColumnas(sentenciaSql);

				} catch (SQLException e) {
					e.printStackTrace();
				}
			case 6:
				return;
			default:
		}
		menuColumnas(sentenciaSql);
	}

	public static void consultaTabla() {

	}

	public static void establecerConexion() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebas", "root", "root");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cerrarConexion() {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
