import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Elías Avendaño | José Avendaño | Carmen Ortega
 */
public class tarea2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in); //Recibe la información por la entrada estándar
        ArrayList<String[]> file = new ArrayList<>();
        String temp = ""; //La variable temp contiene la línea antes de hacer split en las palabras que contenga
        while (entrada.hasNextLine()) { //Aquí se separan los elementos que contiene cada línea del texto
            temp = entrada.nextLine();
            String linea[] = temp.split(" ");
            file.add(linea);
        }
        new Nucleo(file); //Se envía la información ya inicializada al núcleo del programa
    }
}
