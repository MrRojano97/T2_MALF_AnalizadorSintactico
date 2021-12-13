
/**
 *
 * @author elias
 */
public class Parseador { //Aquí se interpreta qué tipo es cada línea, si es una asignatura, ayudante o un nombre
    
    public int Parseador(String palabra){
        
        if (palabra.equals("Asignatura")) {
            return 1;
        }
        
        else if (palabra.equals("Ayudante")) {
            return 2;
        }
        
        else if (palabra.equals(""))
            return 3;
        
        else
            return 0;
    }
}
