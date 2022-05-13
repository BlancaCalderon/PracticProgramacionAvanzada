/*Clase contiene y actualiza listas que se mostrarán en la interfaz
 */
package Parte_2_RMI;


import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author blanf
 */
public class ListaThreads 
{
    //Inicializo variables
    ArrayList<String> lista;    //Array que contiene elementos a mostrar
    JTextField tf;  //Text field dónde se mostrará la información de la lista
    JTextArea ta;   //Text area dónde se mostrará la información de la lista
    
    //Constructor para JTextField
    public ListaThreads(JTextField tf)
    {
        lista=new ArrayList<String>();
        this.tf=tf;
    }
    
    //Constructor para jtestArea
    public ListaThreads (JTextArea ta)
    {
        lista = new ArrayList <>();
        this.ta = ta;
    }

    
    //Método para introducir elemento en a lista
    public synchronized void meter(String id)
    {
        lista.add(id);  //Añade string a la lista
        imprimir(); //Muestra nuevo valor por interfaz
    }
    
    //Método para sacar string de la lista
    public synchronized void sacar(String id)
    {
        lista.remove(id);   //Elimina string indicado de la lista
        imprimir(); //Imprime nueva lista por pantalla
    }
    
    //Método saca todos los elementos de la lista
    public synchronized void sacarTodos ( )
    {
        lista.clear();  //Elimina todos los elementos que contenia
        imprimir(); //Imprime lista vacia
    }
    
    //Método devuelve contenido de una lista
    public synchronized String contenido()
    {
        String contenido="";    //String contiene contenidod e la lista
        for(int i = 0; i < lista.size(); i++)   //Bucle recorre toda la lsita
        {
           contenido = contenido + lista.get(i) + " ";    //Se guarda contenido de la lista a mostrar
        }
        return contenido;
    }
    
    //Método devuelve el tamaño de la lista
    public synchronized int getTamaño()
    {
        return lista.size();    //Devuelve tamaño de la lista
    }
    
    //Método imprime por panatalla el contenido de la lista
    public void imprimir()
    {
        String contenido="";    //String contiene contenidod e la lista
        for(int i = 0; i < lista.size(); i++)   //Bucle recorre toda la lsita
        {
           contenido = contenido + lista.get(i) + " ";    //Se guarda contenido de la lista a mostrar
        }
        if(tf != null)    tf.setText(contenido);  //Se escribe contenido en hueco correspondiente en interfaz
        else if(ta != null)         ta.setText(contenido);  
        
    }
}