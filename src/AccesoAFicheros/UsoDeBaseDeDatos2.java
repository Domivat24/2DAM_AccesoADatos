package AccesoAFicheros;

import java.sql.*;
import java.util.Scanner;

public class UsoDeBaseDeDatos2 {

    private Connection conexion;
    private PreparedStatement sentencia;
    private ResultSet resultado;
    private Scanner teclado;

    //Iniciamos el servidor en el constructor
    public UsoDeBaseDeDatos2() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/pruebas",
                    "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.teclado = new Scanner(System.in);

    }

    //Hacemos tres consultas la primera automatizada
    //la segunda hay que introducirla a mano(asi sabemos que nos da error)
    //la tercera nos saca por pantalla las columnas resultado
    public void insertar() {
        System.out.println("Introduce nombre: ");
        String nombre = teclado.nextLine();
        System.out.println("Introduce apellido: ");
        String apellido = teclado.nextLine();

        try {
            //Hasta que no hagas sentencia.commit() no escribe ne la base de datos
            //En el try-catch esta el rollback para proteger la base de datos
            conexion.setAutoCommit(false);
            //1º Consulta
            sentencia = conexion.prepareStatement("INSERT INTO conectados (Nombre, Apellido) VALUES (?, ?)");
            sentencia.setString(1, nombre);
            sentencia.setString(2, apellido);
            sentencia.executeUpdate();
            //2º Consulta
            System.out.println("Introduce Consulta: ");
            sentencia = conexion.prepareStatement(teclado.nextLine());
            resultado = sentencia.executeQuery();
            ResultSetMetaData obtenerC = resultado.getMetaData();
            int columnas = obtenerC.getColumnCount();
            while (resultado.next()) {
                for (int i = 1; i <= columnas; i++) {
                    System.out.print(resultado.getString(i));
                    if (i != columnas) {
                        System.out.print(";");
                    }
                }
                System.out.println("");
            }
            //3º Consulta
            sentencia = conexion.prepareStatement("SELECT * FROM conectados");
            resultado = sentencia.executeQuery();
            ResultSetMetaData obtenerCs = resultado.getMetaData();
            int columnass = obtenerCs.getColumnCount();
            while (resultado.next()) {
                for (int i = 1; i <= columnass; i++) {
                    System.out.print(resultado.getString(i));
                    if (i != columnass) {
                        System.out.print(";");
                    }
                }
                System.out.println("");
            }
            conexion.commit();
            conexion.setAutoCommit(true);
            sentencia.close();
            resultado.close();
            conexion.close();
        } catch (Exception e) {
            try {
                System.out.println("Ha hecho un rollback");
                conexion.rollback();
                sentencia.close();
                resultado.close();
                conexion.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
