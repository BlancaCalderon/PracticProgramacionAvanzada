/*Clase donde tendremos la implementación de lo smétodos que usa el cliente
 */

package Parte_2_2;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author blanf
 */
public class Servidor extends UnicastRemoteObject implements InterfazComun
{
    private Campamento c;
    
    //cosntructor que recibe campamento
    public Servidor(Campamento c) throws RemoteException {
        this.c = c;
    }

    @Override
    //Método que devuelve al cliente el tamaño de la cola de la tirolina
    public int consultarColaTirolina() throws RemoteException {
        return c.getColaTirolina();
    }

    @Override
    //Método que devuelve al cliente el tamaño de la cola de la soga
    public int consultarColaSoga() throws RemoteException {
        return c.getColaSoga();
    }

    @Override
    //Método que devuelve al cliente el número de niñso que están merendando
    public int consultarMerendero() throws RemoteException {
         return c.getMerendero();
    }

    @Override
    //Método que devuelve al cliente número de veces se ha usado la tirolina
    public int consultarVecesTirolina() throws RemoteException {
        return c.getVecesTirolina();
    }


    @Override
    //Método que devuelve al cliente cuantas actividades a hecho un niño
    public int consultarNiño(String id) throws RemoteException {
        return c.getActChild(id);
    }

    @Override
    //Método que devuelve al cliente cuantas bandejas limpias hay
    public String consultarBandejasLimpias() throws RemoteException {
         return c.getBandejasLimpias();
    }

    @Override
    //Método que devuelve al cliente cuantas bandejas sucias hay
    public String consultarBandejasSucias() throws RemoteException {
        return c.getBandejasSucias();
    }
}
