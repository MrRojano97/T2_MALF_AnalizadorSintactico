
/**
 *
 * @author Elías Avendaño | José Avendaño | Carmen Ortega
 */
public class Parseador { //Aquí se interpreta qué tipo es cada línea según está descrito en la gramática
    
    public char Parseador(String palabra){
        
        return switch (palabra) {
            case "Asignatura" -> 'a';
            case "Ayudante" -> 'h';
            case "" -> 'e';
            default -> 'p';
        }; //Gramática Asignatura
        //Gramática Ayudante
        //Si es vacío, es un error
        //Gramática persona
    }
}
