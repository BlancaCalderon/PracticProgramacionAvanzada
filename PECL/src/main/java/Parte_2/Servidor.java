/*Servidor al que se conectarán los clientes para relaizar métodos de consulta
 */
package Parte_2;
import java.io.*;
import static java.lang.Thread.sleep;
import java.net.*;
import java.util.ArrayList;

 
/**
 *
 * @author blanf
 */
//public class Servidor {

    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) {
        ServerSocket servidor;
        Socket conexion;
        Campamento camp;
        Detener deten;
        Monitor mon;
        ArrayList<Child> niños = new ArrayList <Child>();   //Lista dónde guardaremos niños creados
        
        
        int contActChild = 0, contActMon = 0;   //Se inicializan los contadores de actividades de los niños y monitores
        deten = new Detener();
        camp = new Campamento(50,deten);
        
        //Bucle para crear los monitores
            for(int i = 1; i<=4; i++)
            {
                String n1 = String.valueOf(i);      //Convierte entero en string
                mon = new Monitor('M' + n1, camp, contActMon,deten);    //Creo monitor
                mon.start();    //Empieza ejecución de monitor
            }
            //Crea hilo anónimo en el que crearé todos los hilos child que componen el sistema
            new Thread (new Runnable()
            {
                Child c;
                public void run()
                {
                    //Bucle para crear los hilos niños
                    for(int i = 1; i <= 100; i++)
                    {
                        String n2 = String.valueOf(i);  //Convierte valor de i en un string 
                        c = new Child('N' + n2, camp, contActChild,deten);  //Creo hilo niño
                        c.start();
                        niños.add(c);   //Añade niño creado a la lista
                        try
                        {
                        sleep(1000 + (int)(2000*Math.random()));    //Niños se crean cada 1 - 3 segundos
                        } catch (InterruptedException e){ }
                    }
                }   
            }).start();
        try
        {
            servidor = new ServerSocket(5005); // Creamos un ServerSocket en el puerto 5000
            System.out.println("Servidor Arrancado....");   
            while (true)
            {
                conexion = servidor.accept();     // Esperamos una conexión
                Hilo h = new Hilo (conexion, niños, camp);   //Crea hilo para poder recibir conexiones de varios clientes
                h.start();
                servidor.close();                          //Cierra la conexión
            }
        } catch (IOException e) {}
    }
 }*/
           
 
