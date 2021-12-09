
import java.util.ArrayList;

/**
 *
 * @author elias
 * Aquí se irá enviando cada línea del archivo hacia el parseador, y a su vez
 * contando la cantidad de asignaturas, profesores, alumnos y ayudantes
 */
public class Nucleo {
    
    //Estos serán los contadores finales, de acá se podrá utilizar para imprimir al cantidad total
    int asignaturas = 0;
    int profesores = 0;
    int alumnos = 0;
    int ayudantes = 0;

    //Contadores de uso temporal, se utilizarán principalmente para comprobar que se cumpla la gramática
    String asignatura_Actual = "";
    String profesor_Actual = "";
    int alumnos_Actual = 0;
    boolean ayudante_Actual = false;
    
    boolean isActualBlank = true; //Es verdadero cuando las variables de uso temporal están limpias
    String error = ""; //En caso que se produzca un error, servirá para imprimirlo al final de la ejecución
    
    public Nucleo(ArrayList<String[]> text){
        Parseador parseador = new Parseador();
        int i, returned;
        
        for (i=0; i<text.size(); i++){ //Se recorren todas las líneas de texto
            if (!error.equals("")) break; //En caso que exista un error, se detiene la ejecución
            returned = parseador.Parseador(text.get(i)[0]);
            set_Actual(text.get(i), returned);

        }
        if ((!isActualBlank) & (error.equals(""))) reset_Actual(); //Sólo en caso que existan datos en las variables temporales y no haya un error, se procede a guardar la información de la última asignatura
        print(); //Imprime los resultados
    }
    
    private boolean set_Actual(String text[], int type){ //El tipo se sabe gracias al Parseador
        
        if (type==1){ //Asignatura
            
            if (!isActualBlank) reset_Actual(); //Al guardarse una asignatura, significa que, a menos que sea la primera o
            //que la asignatura anterior haya tenido ayudante, deben limpiarse las variables temporales para guardar esta nueva asignatura
            
            if (text.length==2){ //Verifica que la asignatura tenga nombre
                asignatura_Actual=text[1];
                isActualBlank=false;
                return true;
            } else {
                error = ("Error [01]: Una asignatura no lleva su nombre");
                return false;
            }
        }
        
        else if (type==2){ //Ayudante
            
            //Se deben hacer verificaciones para saber si existe Profesor o Asignatura antes de guardar un ayudante
            
            if (!existeAsignatura()){
                error = ("Error [02]: No existe Asignatura al momento de guardar un Ayudante");
                return false;
            }
            
            if (!existeProfesor()){
                error = ("Error [03]: No existe Profesor al momento de guardar un Ayudante");
                return false;
            }
            
            //Se verifica que el ayudante tenga nombre
            
            if (text.length==2){

                ayudante_Actual=true;
                reset_Actual();
                return true;
                
            } else {
                error = ("Error [04]: Un ayudante no lleva su nombre");
                return false;
            }
        }
        
        else {
            //Si no existe una asignatura, no se puede guardar un profesor
            //NOTA: Se asume que sólo fallará con profesor, porque siempre pasa que un profesor viene antes que los estudiantes
            
            if (!existeAsignatura()){
                error = ("Error [05]: No existe asignatura al momento de guardar un profesor");
            }
            
            if (text.length==1){
            
                if (!existeProfesor()){
                    profesor_Actual=text[0];
                    return true;
                } else {
                    alumnos_Actual+=1;
                    return true;
                }
            } else {
                error = ("Error [06]: Línea de profesor o estudiante contiene información adicional");
                return false;
            }
        }
        
    }
    /**
     * Reinicia las variables temporales actuales
     * @return 
     */
    private boolean reset_Actual(){
        if (existeAsignatura()) {
            asignatura_Actual = "";
            asignaturas+=1;
        }
        
        if (existeProfesor()) {
            profesor_Actual = "";
            profesores+=1;
        } else {
            error = ("Error [07]: Falta profesor en una asignatura");
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
    
    private boolean existeAsignatura(){ //Verifica que exista una asignatura actual
        if (!asignatura_Actual.equals(""))
            return true;
        else return false;
    }
    
    private boolean existeProfesor(){ //Verifica que exista un profesor actual
        if (!profesor_Actual.equals(""))
            return true;
        else return false;
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
        //Si ocurrió un error, sólo se imprime este
        else System.out.println(error);
    }
}
