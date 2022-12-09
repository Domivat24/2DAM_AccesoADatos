import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Practica1 {
    static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        String directorio;
        System.out.println("Introduzca el directorio del que desea ver los archivos");
        File ruta;
            directorio = in.nextLine();
            ruta = new File(directorio);
        try {
            if (ruta.exists()) {
                //El list crea un array de String y al que le he pasado un ArraysToString para que se lea en pantalla.
                System.out.println(Arrays.toString(ruta.list()));
                //listFiles crea un array de Files, por lo que se mostrará la ruta relativa o absoluta según
                // lo que se haya introducido por consola, a diferencia del list.
                System.out.println(Arrays.toString(ruta.listFiles()));
            } else {
                System.out.println("El directorio no se encuentra en la ruta indicada.");
            }
        } catch (Exception e) {
            System.out.println("Excepción: no se ha introducido nada.");
        }
    }
}
