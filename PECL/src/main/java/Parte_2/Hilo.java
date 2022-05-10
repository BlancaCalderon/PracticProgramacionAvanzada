/*Clase hilo para poder gestionar conexiones de varios clientes uy que muestra intefaz una vez conectados*/
package Parte_2;

import Parte_2.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blanf
 */
public class Hilo extends Thread {
    private int num, consulta, tam;
    private Campamento camp;
    private String text, id;
    private Socket conexion;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private boolean fin = true;

    //Constructor
    public Hilo(Socket conexion, Campamento c) {
        this.conexion = conexion;
        this.camp = c;
    }
    
    public void run()
    {
        try {
            num++;
            System.out.println("Conexión nº "+ num +" desde: "+ conexion.getInetAddress().getHostName());   //Imprime en pantalal con quien nos hemos conectado
            entrada = new DataInputStream(conexion.getInputStream());  //Creamos los canales de entrada/salida
            salida = new DataOutputStream(conexion.getOutputStream());
            while (fin)
            {
                consulta = entrada.readInt();    //Leemos el mensaje del client
                switch (consulta)
                {
                    case 1:
                        tam = camp.consultarColaTirolina(); //Consulta cola tirolina
                        salida.writeInt(tam);               //Manda resultado de la consulta al cliente
                        break;
                    case 2:
                        tam = camp.consultarColaSoga(); //Consulta cola soga
                        salida.writeInt(tam);           //Manda resultado de la consulta al cliente
                        break; 
                    case 3:
                        tam = camp.consultarMerendero(); //Consulta niños en merendero
                        salida.writeInt(tam);           //Manda resultado de la consulta al cliente
                        break;
                    case 4:
                        text = camp.consultarBandejasSucias(); //Consulta número de bandejas sucias
                        salida.writeUTF(text);                  //Manda resultado de la consulta al cliente
                        break;
                    case 5:
                        text = camp.consultarBandejasLimpias(); //Consulta número de bandejas sucias
                        salida.writeUTF(text);                  //Manda resultado de la consulta al cliente
                        break;
                    case 6:
                        tam = camp.consultarVecesTirolina(); //Consulta veces tirolina
                        salida.writeInt(tam);               //Manda resultado de la consulta al cliente
                        break;
                    case 7:
                        id = entrada.readUTF();             //Recibe identificador del niño
                        tam = camp.consultarNiño(id);       //Consulta número de actividades realizadas por el niño
                        salida.writeInt(tam);       //Envia resultado al cliente
                        break;
                    default:
                        salida.writeUTF("desconectado");
                        fin = false;
                         
                }
            }
            conexion.close(); //Cierra la consexión con el cliente   
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
}



