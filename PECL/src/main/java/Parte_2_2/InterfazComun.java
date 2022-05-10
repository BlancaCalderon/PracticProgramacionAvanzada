/*Interfaz común que tendrán los clientes con el servidor dónde definimos la cabecera de los métodos que usará el cliente y que estarán implementados en el servidor
 */
package Parte_2_2;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author blanf
 */
public interface InterfazComun extends Remote
{
    public int consultarColaTirolina() throws RemoteException; //Método para consultar número de niños en la cola de espera de actividad tirolina
    public int consultarColaSoga() throws RemoteException; //Método para sonsultar número de niños en la cola de espera de actividad soga
    public int consultarMerendero() throws RemoteException; //Método método para conusltar número de niños en actividad merienda
    public String consultarBandejasLimpias() throws RemoteException;  //Método para consultar  número de bandejas limpias en merendero
    public String consultarBandejasSucias() throws RemoteException; //Método para consultar número de bandejas sucias en merendero
    public int consultarVecesTirolina() throws RemoteException;  //Método para consultar número de veces que se ha realizado actividad tirolina
    public int consultarNiño(String id) throws RemoteException;    //Método para consultar número de actividades que ha realizado un niño
}
