
import java.util.ArrayList;

/**
 *
 * @author Elías Avendaño | José Avendaño | Carmen Ortega
 * Aquí se irá enviando cada línea del archivo hacia el parseador, y a su vez
 * contando la cantidad de asignaturas, profesores, alumnos y ayudantes
 */
public class Nucleo {
    
    //Variables totales, de acá se podrá utilizar para imprimir la cantidad total
    int asignaturas = 0;
    int profesores = 0;
    int alumnos = 0;
    int ayudantes = 0;

    //Variables Actuales, se utilizarán principalmente para comprobar que se cumpla la gramática
    boolean asignatura_Actual = false;
    boolean profesor_Actual = false;
    int alumnos_Actual = 0;
    boolean ayudante_Actual = false;
    
    int linea; //Linea actual que se está leyendo
    
    boolean isActualBlank = true; //Es verdadero cuando las variables actuales están limpias
    String error = ""; //En caso que se produzca un error, servirá para imprimirlo al final de la ejecución
    
    public Nucleo(ArrayList<String[]> text){
        Parseador parseador = new Parseador();
        char returned;
        
        for (linea=0; linea<text.size(); linea++){ //Se recorren todas las líneas de texto
            returned = parseador.Parseador(text.get(linea)[0]);
            if (!set_Actual(text.get(linea), returned))
                break; //En caso que exista un error, se detiene la ejecución

        }
        if ((!isActualBlank) & (error.equals(""))) reset_Actual(); //Sólo en caso que existan datos en las variables temporales y no haya un error, se procede a guardar la información de la última asignatura
        print(); //Imprime los resultados
    }
    
    private boolean set_Actual(String text[], char type){ //El tipo se sabe gracias al Parseador
        
        if (type=='a'){ //Asignatura
            
            //Al guardarse una asignatura, significa que, a menos que sea la primera o
            //que la asignatura anterior haya tenido ayudante, deben limpiarse las variables temporales para guardar esta nueva asignatura
            if (!isActualBlank){
                if (!reset_Actual()) return false;
            }
            
            if (text.length>=2){ //Verifica que la asignatura tenga nombre
                asignatura_Actual=true;
                isActualBlank=false;
                return true;
            } else {
                error = ("Error [01]: Una asignatura no lleva su nombre");
                return false;
            }
        }
        
        else if (type=='h'){ //Ayudante
            
            //Se deben hacer verificaciones para saber si existe Profesor antes de guardar un ayudante
            
            if (!profesor_Actual){
                error = ("Error [02]: No existe Profesor al momento de guardar un Ayudante o existen dos Ayudantes en una misma Asignatura");
                return false;
            }
            
            //Se verifica que el ayudante tenga nombre
            
            if (text.length>=2){

                ayudante_Actual=true;
                reset_Actual();
                return true;
                
            } else {
                error = ("Error [03]: Un Ayudante no lleva su nombre");
                return false;
            }
        }
        
        else if (type=='e') {
            error = ("Error [05]: Existe una línea en blanco");
            return false;
        }
        
        else if (type=='p') {
            //Si no existe una asignatura, no se puede guardar un profesor
            //NOTA: Se asume que sólo fallará con profesor, porque siempre pasa que un profesor viene antes que los estudiantes
            
            if (!asignatura_Actual){
                error = ("Error [04]: No existe Asignatura al momento de guardar un Profesor");
                return false;
            }
            
            if (!profesor_Actual){
                profesor_Actual=true;
                return true;
            } else {
                alumnos_Actual+=1;
                return true;
            }
        }
        
        error = ("Error [100]: Se ha producido un error de ejecución desconocido"); //A menos que haya una falla interna del CPU que está ejecutando el programa, no debería ser posible que el programa ejecute esta instrucción
        return false;
        
    }
    /**
     * Reinicia las variables actuales
     * @return 
     */
    private boolean reset_Actual(){
        if (asignatura_Actual) {
            asignatura_Actual = false;
            asignaturas+=1;
        }
        
        if (profesor_Actual) {
            profesor_Actual = false;
            profesores+=1;
        } else {
            error = ("Error [06]: Falta Profesor en una Asignatura");
            return false;
        }
        
        if (ayudante_Actual) {
            ayudantes+=1;
            ayudante_Actual=false;
        }
        
        alumnos+=alumnos_Actual;
        alumnos_Actual=0;
        
        isActualBlank=true;
        
        return true;      
    }
    
    
    private void print(){ //Imprime la información final
        
        if (error.equals("")){ //En caso que no haya error, se imprime la información final, dependiendo de si es plural o individual
        
            if (asignaturas<=1)
                System.out.println(asignaturas+" asignatura");
            else
                System.out.println(asignaturas+" asignaturas");

            if (profesores<=1)
                System.out.println(profesores+" profesor");
            else
                System.out.println(profesores+" profesores");

            if (alumnos<=1)
                System.out.println(alumnos+" alumno");
            else
                System.out.println(alumnos+" alumnos");

            if (ayudantes<=1)
                System.out.println(ayudantes+" ayudante");
            else
                System.out.println(ayudantes+" ayudantes");
        }
        //Si ocurrió un error, sólo se imprime este seguido de la línea donde ocurrió el problema
        else {
            System.err.println(error);
            System.err.println("Corrija el error producido en la línea: "+(linea+1));
        }
    }
}
