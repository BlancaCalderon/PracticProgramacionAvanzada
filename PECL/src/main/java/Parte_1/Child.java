/* Clase hilo niños cuyos atributos son identificador, campamento,contador de actividades, contador merienda y clase detener
 */
package Parte_1;
/**
 *
 * @author blanf
 */
public class Child extends Thread
{
    //Inicializo variables
    private String id;
    private Campamento camp;
    private int contActividades, contMer;
    private Detener detener;
    

    //Constructor
    public Child(String id, Campamento co,int c, Detener deten,int cm) 
    {
        this.id = id;   //Variable guarda identificador del niño
        this.camp = co; 
        this.contActividades = c;   //Variable guarda cuantas actividades a realizado el niño
        this.detener = deten;   
        this.contMer = cm;  //Variable guarda cuantas actividades (soga y tirolina) ha realizado niños antes de ir a merienda
        
    }
    
    //Método para devolver valor del identificador del niños
    public String getCId() 
    {
        return id;
    }
    
    //Método que establece valor del identificador
    public void setId(String id) 
    {
        this.id = id;
    }
    
    //Método que devuelve valor del contador de actividades
    public  int getContActividades() 
    {
        return contActividades;
    }
    
    //Método que establece valor del contador de merienda
    public void setContMer (int cont) 
    {
        this.contMer = cont;
    }
    //Método que devuelve valor del contador de actividades
    public  int getContMerienda() 
    {
        return contMer;
    }
    
    // Método para establecer valor del contador
    public void setContActividades( int contActividades) 
    {
        this.contActividades = contActividades;
    }
    
    //Método que suma valor pasado al contador de actividades
    public int sumar(int n)
    {
        this.contActividades = contActividades + n; //Sumar valor indicado
        this.contMer = this.contMer + n;    //Suma a valor al contador de la merienda
        return contActividades; //Deveuleve nuevo valor del contador
    }
    
    //Se ejecurta hilo
    public void run()
    {
        detener.comprobar();    //Punto de detención del programa
        camp.entrar(this); //Entra en el campamento si hay hueco; y sino espera en la cola
    }
}
