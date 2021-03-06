/*Clase que crearfichero log donde escirbiremos los estados de los hilos del sistema.
 */
package Parte_2;

import Parte_2.*;
import Parte_1.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author blanf
 */
public class Log 
{
    private FileWriter log; //Objeto FileWriter usado para escribir en el archivo
    private DateTimeFormatter fecha = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm:ss");   //Formato que usaremos para escribir la fecha y el tiempo

    /*Constructor de la clase*/
    public Log(FileWriter l) {
        try 
        {
            this.log = l;
            this.log = new FileWriter("evolucionCampamento.txt"); //Crea objeto file
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    //Método para escribir en archivo log
    public synchronized void escribir(String texto) 
    {
        
        try 
        {
            log.write(fecha.format(LocalDateTime.now()) + texto);   //Escribe fecha y hora actual y el texto especificado
            log.flush();    //Guardo lo escrito en el fichero
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Método para cerrar archivo
    public synchronized void cerrar()
    {
        try 
        {
            log.close();  //Cierro el archivo log
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
