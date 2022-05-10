/*Esta clase contendrá todas las entradas y actividades.*/
package Parte_1;

import java.io.FileWriter;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author blanf
 */
public class Campamento {
    private boolean entradaIzq = false, entradaDer= false, finSoga = true; //Variables almacenan si entradas estan abiertas
    private int aforo, capTir = 1, capMer = 20, numLimpias = 0, numSucias = 25, numJugadores = 0, numA = 0, numB = 0, contTir = 0,contMer = 0, ganador;     //Aforo maximo del campamento y parte numérica del identificador
    private ArrayList<Child> equiA, equiB, participantes;  //Arrays que contendrán miembros de cada equipo en actividad soga
    private ListaThreads colaEntradaIzq, colaEntradaDer, colaTirolina, colaMerienda, dentro, monEnTirolina,monEnMerienda,monEnZonaComun, monEnSoga, childEnSoga, childEnMer,limpias,sucias,childEnZc,childEnTirPrep,childEnTir,childEnFinTir,equipoA,equipoB; //Colas de espera y niños dentro de cada actividad y de entrada
    private Semaphore semaforoAforo, semaforoCapTir, semaforoCapMer, señal, subido,esperaTir,esperaMer, servir,limpiar; //Variable semaforo para proteger variables
    private Lock cerrojoIzq = new ReentrantLock(); //variable cerrojo para cuando puertaIzq esta cerrada 
    private Lock cerrojoDer = new ReentrantLock(); //variable cerrojo para cuando puerta derecha esta cerrada
    private Condition cerradaIzq = cerrojoIzq.newCondition();   //Variable condition asociada al cerrojo de la puerta izq
    private Condition cerradaDer = cerrojoDer.newCondition();   //Variable condition asociada al cerrojo de la puerta derecha
    private int maxJugadores = 10;  //Numero de jugadores necesarios para jugar a actividad soga
    private CyclicBarrier barreraSoga, hacerEquipo, elegirGanador, jugar ; 
    private Detener detener;    //Para detener y reanudad ejecución del programa
    private Log log;    //Para escribir en archivo evolción de la ejecución del programa
    private FileWriter file;    //Archivo dónde se escribirá evolución del programa  
    
    /*Constructor de la clase*/
    public Campamento(int aforo,JTextArea espEn1,JTextArea espEn2,JTextArea espTir,JTextArea espMer, JTextArea den, JTextField monEnTir,JTextField monEnMer,JTextField monEnZC, JTextField monEnSo,JTextArea colaMer,JTextArea colaTir, JTextArea enSoga, JTextArea enMer, JTextField limp, JTextField suc, JTextArea zc, JTextField tirPrep,JTextField enTir, JTextField finTir, JTextArea a,JTextArea b,Detener deten)
    {
        this.aforo = aforo;
        this.detener = deten;
        this.log = new Log(file);
        semaforoAforo = new Semaphore (aforo,true); //aforo es el número de èrmisos y true para indicar salida FIFO de la cola
        semaforoCapTir = new Semaphore (capTir, true);
        semaforoCapMer = new Semaphore (capMer, true);
        esperaMer = new Semaphore (10, true);
        esperaTir = new Semaphore (0, true);
        subido = new Semaphore (0, true);
        señal = new Semaphore (0, true);
        servir = new Semaphore (numLimpias, true);
        limpiar = new Semaphore (numSucias, true);
        colaEntradaIzq = new ListaThreads (espEn1);
        colaEntradaDer = new ListaThreads (espEn2);
        colaTirolina = new ListaThreads (colaTir);
        colaMerienda = new ListaThreads (colaMer);
        dentro = new ListaThreads (den);
        monEnTirolina = new ListaThreads (monEnTir);
        monEnMerienda = new ListaThreads (monEnMer);
        monEnZonaComun = new ListaThreads (monEnZC);
        monEnSoga = new ListaThreads (monEnSo);
        childEnSoga = new ListaThreads (enSoga);
        childEnMer = new ListaThreads (enMer);
        limpias = new ListaThreads (limp);
        sucias = new ListaThreads (suc);
        childEnZc = new ListaThreads (zc);
        childEnTirPrep = new ListaThreads (tirPrep);
        childEnTir = new ListaThreads (enTir);
        childEnFinTir = new ListaThreads (finTir);
        equipoA = new ListaThreads (a);
        equipoB = new ListaThreads (b);
        equiA = new ArrayList<Child>();
        equiB = new ArrayList<Child>();
        participantes = new ArrayList<Child>();
        barreraSoga = new CyclicBarrier(maxJugadores + 1);
        hacerEquipo = new CyclicBarrier(maxJugadores + 1);
        jugar = new CyclicBarrier(maxJugadores + 1);
        elegirGanador = new CyclicBarrier(maxJugadores + 1);
        limpias.meter(" " + 0);
        sucias.meter(" " + 25);
        
    }
    
    /*Método override que permite entrar en campamento a monitores*/
    public void entrar(Monitor m)
    {
        int num = numId(m);
        /*Si identificador de monitor es par se va a la primera puerta (asignamos puertas segun id)*/
        if(paridadId(num))
        {
            if(!entradaIzq)    /*Si puerta de la entrada izquierda esta cerrada*/
            {
                try
                {
                    cerrojoIzq.lock();  //Cierra cerrojo puertaIzq
                
                    if(!entradaIzq)
                    {
                    try 
                    {
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                        sleep(500 + (int) (1000* Math.random()));      //Monitor tarda entre 0.5 y 1 segundo en abrir la puerta
                        entradaIzq = true;          //Monitor abre entrada
                        log.escribir(" Monitor " + m.getMId() + " abre puerta izquierda\n");    //Escribe estado del monitor en archivo
                        cerradaIzq.signalAll();     //Libera hilos que estuviesen esperando en la cola de la puerta izquierda
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                        colaEntradaIzq.sacar(m.getMId());            //Sacamos a monitor de la cola de espera          
                        dentro.meter(m.getMId());                  //Llamamos a meter para indicar que ha entrado monitor en el campamento 
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
                    else
                    {
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                       colaEntradaIzq.sacar(m.getMId());            //Sacamos a monitor de la cola de espera    
                       log.escribir(" Monitor " + m.getMId() + " entra por la puerta izquierda\n"); //Escribe estado del monitor en archivo
                       dentro.meter(m.getMId());                  //Llamamos a meter para indicar que ha entrado monitor en el campamento 
                    }
                }
                finally
                {
                    cerrojoIzq.unlock();   //Abre cerrojo   
                }
            }
            else
            {
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                colaEntradaIzq.sacar(m.getMId());            //Sacamos a monitor de la cola de espera   
                log.escribir(" Monitor " + m.getMId() + " entra por la puerta izquierda\n"); //Escribe estado del monitor en archivo
                dentro.meter(m.getMId());                  //Llamamos a meter para indicar que ha entrado monitor en el campamento         
            }
            
            }   
        else        //Si id del monitor es impar entra por la puerta derecha
        {
            colaEntradaDer.meter(m.getMId());  //Introduce monitor en la cola (a efectos practicos no espera se hace para observar)
            if(!entradaDer)    /*Si puerta de la entrada derecha esta abierta*/
            {
                try
                {
                    cerrojoDer.lock();     //Cierra cerrojo puertaDer
                
                   if(!entradaDer)
                   {
                    try 
                    {
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                        sleep(500 + (int) (1000* Math.random()));      //Monitor tarda entre 0.5 y 1 segundo en abrir la puerta
                        entradaDer = true;          //Monitor abre entrada
                        log.escribir(" Monitor " + m.getMId() + " abre puerta derecha\n"); //Escribe estado del monitor en archivo
                        cerradaDer.signalAll();     //Libera hilos que estuviesen esperando en la cola de la puerta derecha
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                        colaEntradaDer.sacar(m.getMId());            //Sacamos a monitor de la cola de espera          
                        dentro.meter(m.getMId());                  //Llamamos a meter para indicar que ha entrado monitor en el campamento 
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   }
                   else
                   {
                       detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                       colaEntradaDer.sacar(m.getMId());            //Sacamos a monitor de la cola de espera     
                       log.escribir(" Monitor " + m.getMId() + " entra por la puerta derecha\n"); //Escribe estado del monitor en archivo
                       dentro.meter(m.getMId());                  //Llamamos a meter para indicar que ha entrado monitor en el campamento      
                   }
                }
                finally
                {
                    cerrojoDer.unlock();   //Abre cerrojo 
                }       
            }
            else
            {
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                colaEntradaDer.sacar(m.getMId());            //Sacamos a monitor de la cola de espera
                log.escribir(" Monitor " + m.getMId() + " entra en por la puerta derecha\n"); //Escribe estado del monitor en archivo
                dentro.meter(m.getMId());                  //Llamamos a meter para indicar que ha entrado monitor en el campamento         
            }
        }
        num = numId(m);   //guarda parte numérica del identificador del monitor
        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
        accederActividad(m,num);  //Llama a función acceder a actividad 
    }
    
    //Método por el que monitores salen del campamento
    public void salir(Monitor m)
    {
        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
        dentro.sacar(m.getMId()); //Saca a monitor del campamento
        log.escribir("Monitor " + m.getMId() + "sale del campamento\n"); //Escribe estado del monitor en archivo
    }
    
    //Método para que cada monitor accede a actividad correspondiente segun su id
    public void accederActividad(Monitor m, int num)
    {
      detener.comprobar();  //Punto de detención de la ejecución
      switch(num)
      {
        case 1:   //Monitor a actividad soga
            detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
            log.escribir(" Monitor " + m.getMId() + " accede a actividad soga\n");  //Escribe estado del monitor en archivo
            monEnSoga.meter(m.getMId());    //Monitor accede a actividad soga
            soga(m,num);    //Llama a función soga accediendo monitor
            break;
        case 2:     //Monitor entra en actividad tirolina
            detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
            log.escribir(" Monitor " + m.getMId() + " accede a actividad tirolina\n");  //Escribe estado del monitor en archivo
            monEnTirolina.meter(m.getMId());    //Monitor accede a actividad tirolina
            tirolina(m,num);    //Llama a función tirolina accediendo monitor
            break;
        case 3:     //Monitor entra en actividad merienda
            detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
            log.escribir(" Monitor " + m.getMId() + " accede a actividad merienda\n");  //Escribe estado del monitor en archivo
            monEnMerienda.meter(m.getMId());    //Accede monitor a actividad merienda
            merienda(m,num);    //Se llama a función merienda
            break;
        case 4:     //Monitor entra en actividad merienda
            detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
            log.escribir(" Monitor " + m.getMId() + " accede a actividad merienda\n"); //Escribe estado del monitor en archivo
            monEnMerienda.meter(m.getMId());    //Accede monitor a función merienda
            merienda(m,num);    //Se llama a función merienda
            break;    
      }     
    }
    
    //Método para simular el funcionamiento de la merienda por parte de los monitores
    public void merienda (Monitor m, int n) 
    {
        contMer = 0;    //Contador guarda numero de platos limpios servidos reseteandose csda vez que monitores vuelven del descanso
        
        for (int i = 0; i < 10; i ++)           {
            try //Bucle comprueba si monitor ha servido 10 platos
            {
                detener.comprobar();   //Se detiene ejecución del programa en este punto
                limpiar.acquire();  //monitor se bloquea si no hay platos que lavar
                sleep(3000 + (int) (2000 * Math.random())); //Tarda entre 3 y 5 segundos en limpiar y servir
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                log.escribir(" Monitor " + m.getMId() + " limpia bandeja\n"); //Escribe estado del monitor en archivo
                m.sumar(1); //Suma uno a contador de actividades del monitor
                contMer++;  //Aumenta número de meriendas llevadas a cabo por ambos monitores
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                limpias.sacar(" " + numLimpias);    //Actualiza el número de bandejas limpias sacando anterior valor
                numLimpias++;   //Aumenta número de bandejas limpias disponibles
                limpias.meter(" " + numLimpias);    //Muestra nuevo número de bandejas limpias en interfaz
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                sucias.sacar(" " + numSucias);      //Saca antiguo valor de bandejas sucias
                numSucias--;    //Disminuye el número de bandejas sucias
                sucias.meter(" " + numSucias);  //Introduce nuevo número de bandejas sucias
                servir.release();   //avisa de que hay un plato mas limpio disponible;
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
        log.escribir(" Monitor " + m.getMId() + " se va al descanso\n"); //Escribe estado del monitor en archivo
        monEnMerienda.sacar(m.getMId());    //Saca a monitor de la actividad
        zonaComun(m,n); //Manda monitor a zona comun
    }
    
    
    //Método simula actividad soga para los monitores
    public void soga(Monitor m, int n)
    {
        for (int i = 0; i < 10; i++)         {
            try //Bucle controla que cuando monitor haga 10 veces esta actividad se vaya al descanso
            {
                detener.comprobar();  //Ejecución se detendrá en este punto si se indica
                barreraSoga.await();    //Bloquea a monitor hasta que hay 10 niños en la actividad
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                while(!participantes.isEmpty()) //Mientras la lista de participantes no este vacia hace equipos
                {
                    int num = (int) (2 *Math.random()); //Random para formar los equipos
                    if(num == 0 && numA < 5 || numB == 5)    //si identificador del niño es par y equipo A no esta acompleto
                    {
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                        log.escribir(" Niño " + participantes.get(0).getCId() + " se mete en el quipo A\n"); //Escribe estado del niño en archivo
                        equipoA.meter(participantes.get(0).getCId());  //Introducimos niño en equipo A
                        equiA.add(participantes.get(0));   //Añade a niño al equipo
                        numA++;         //Aumentamos número de jugadores en equipo
                        participantes.remove(0); //Saca de la lista a niño introducido en equipo
                    }
                    else    //Si identificador del niño es impar o equipo A esta completo
                    {
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                        log.escribir(" Niño " + participantes.get(0).getCId() + " se mete en el equipo B\n"); //Escribe estado del niño en archivo
                        equipoB.meter(participantes.get(0).getCId());  //Introducimos niño en equipo B
                        equiB.add(participantes.get(0));   //Añade niño al array correspondiente
                        numB++;             //Aumentamos número de jugadores en equipo B
                        participantes.remove(0); //Saca de la lista a niño introducido en equipo
                    } 
                }
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                hacerEquipo.await(); //Bloquea a niños hasta que los equipos esten formados
                log.escribir(" Monitor ha formado equipos\n");
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                jugar.await();   //Monitor se bloquea hasta que termine competición para elegir ganador
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                log.escribir(" Ha terminado competición soga\n");
                ganador = (int)(2 *Math.random());  //Selecciona equipo ganador aleatoriamente
                String text = " "; //Variable donde guardaremos que niños ganan actividad
                for (int j = 0; j < 5; j ++)    //Bucle para imprimir niños que ganan actividad
                {
                    if(ganador == 0)    //Si gana equipo A
                    {
                        text = text + " " + equiA.get(j).getCId();
                    }
                    else //Si gana equipo B
                    {
                        text = text + " " + equiB.get(j).getCId();
                    }
                }
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                elegirGanador.await();  //Libera a niños cuando monitor elije ganador   
                detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
                log.escribir(" Han ganado los niños : " + text + "\n"); //Imprime por pantalla niños que han ganado soga
                m.sumar(1); //Se suma las veces que s eha realizado actividad
            } 
            catch (InterruptedException | BrokenBarrierException ex) 
            {
                Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
        log.escribir(" Monitor " + m.getMId() + " se va al descanso\n");    //Escribe estado del monitor en archivo
        monEnSoga.sacar(m.getMId());    //Se saca monitor de la actividad
        zonaComun(m,n); //Se envia monitor a la zona comun para que descanse 
    }
    
    //Método simula actividad tirolina para los monitores
    public void tirolina(Monitor m, int n)
    {
        esperaTir.release(10); //Libera permisos para que niños puede entrar en actividad
        for(int i = 0; i < 10; i++)         
        {
            try //Se hace bucle mientras monitor no haya realizado 10 veces la actividad
            {
                detener.comprobar();   //Ejecución se detendrá en este punto si se indica
                subido.acquire(); //Monitor se bloquea hast aque haya niño en tirolina
                sleep(1500 + (int)(500 *Math.random())); //Espera entre 1,5 segundos y 2 segundos para dar la señal a niño de que se tire (no especificado en enunciado)
                señal.release(); //Avisa a niño de que puede tirarse
                m.sumar(1);    //Introduce las 10 veces que se ha realizado actividad en contador
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
        esperaTir.drainPermits();   //Bloquea a niños hasta que vuelva monitor
        log.escribir(" Monitor " + m.getMId() + " se va al descanso\n");    //Escribe estado del monitor en archivo
        monEnTirolina.sacar(m.getMId());    //Se saca monitor de la actividad
        zonaComun(m,n); //Se envia monitor a la zona comun para que descanse 
    }
    
    //Método simula funcionamiento de la zona comun para los monitores
    public void zonaComun(Monitor m, int n)
    {
        try {
            detener.comprobar();   //Ejecución se detendrá en este punto
            monEnZonaComun.meter(m.getMId());   //Monitor entra en zona comun
            sleep(1000 + (int) (1000* Math.random()));  //Monitor descansa entre 1 y 2 segundos enona comun
            detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
            monEnZonaComun.sacar(m.getMId());   //Monitor sale de la zona comun
            log.escribir(" Monitor " + m.getMId() + " vuelve a su actividad\n"); //Escribe estado del monitor en archivo
            accederActividad(m,n);   //Llama a función para volver a su actividad
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }

    
    //Método para que niños entren en el campamento(una puerta u otra segun si id es par)
    public void entrar(Child c)
    {
        /*Si identificador del niño es par se va a la primera puerta (asignamos puertas segun id)*/
        int num =(int) (2* Math.random());
        if(num == 0)
        {
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            colaEntradaIzq.meter(c.getCId());  //Introduce niño en la cola hasta que se abra puerta 
            log.escribir(" Niño " + c.getCId() + " intenta entrar al campamento por la puerta izquierda \n"); //Escribe estado del niño en archivo
            if(!entradaIzq) //si la puerta izquierda esta cerrada
            {
                try
                {
                    cerrojoIzq.lock();
                    while(!entradaIzq)  //Mientras este cerrada la puerta izquierda
                        {  
                            try 
                            {
                                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                                log.escribir(" Niño " + c.getCId() + " espera en la cola de la puerta izquierda\n"); //Escribe estado del niño en archivo
                                cerradaIzq.await();     //niño se bloquea hasta que se abra puerta    
                            } 
                            catch (InterruptedException ex) 
                            {
                                Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                }
                finally
                {
                    cerrojoIzq.unlock();
                } 
                    detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                    log.escribir(" Niño " + c.getCId() + " entra al campamento por la puerta izquierda \n"); //Escribe estado del niño en archivo
                    colaEntradaIzq.sacar(c.getCId());            //Sacamos a monitor de la cola de espera          
                    dentro.meter(c.getCId());                  //Llamamos a meter para indicar que ha entrado monitor en el campamento    
            }
            else
            {
                try /*Si puerta de la entrada izquierda esta abierta*/ 
                {
                    detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                    semaforoAforo.acquire();          //Adquiere semáforo (-1 permiso disponible)
                    colaEntradaIzq.sacar(c.getCId());            //Sacamos a niño de la cola de espera
                    log.escribir(" Niño " + c.getCId() + " entra al campamento por la puerta izquierda \n"); //Escribe estado del niño en archivo
                    dentro.meter(c.getCId());                  //Llamamos a meter para indicar que ha entrado niño en el campamento
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }    
        else        //Si id del monitor es impar entra por la puerta derecha
        {
           detener.comprobar();   //Punto donde se detendrá funcionaientod el program
           colaEntradaDer.meter(c.getCId());  //Introduce niño en la cola (a efectos practicos no espera se hace para observar)
           log.escribir(" Niño " + c.getCId() + " intenta entrar al campamento por la puerta derecha \n"); //Escribe estado del niño en archivo
           if(!entradaDer)    /*Si puerta de la entrada derecha esta abierta*/
            {
                try
                {
                    cerrojoDer.lock();
                
                    while(!entradaDer)  //Mientras entrada derecha este cerrada
                    { 
                        try 
                        {
                            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                            log.escribir(" Niño " + c.getCId() + " espera en la cola de la puerta derecha\n"); //Escribe estado del niño en archivo
                            cerradaDer.await();     //hilo se bloquea hasta que se abra puerta
                        } 
                        catch (InterruptedException ex) 
                        {
                            Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                    colaEntradaDer.sacar(c.getCId());            //Sacamos a monitor de la cola de espera 
                    log.escribir(" Niño " + c.getCId() + " entra al campamento por la puerta derecha \n"); //Escribe estado del niño en archivo
                    dentro.meter(c.getCId());                  //Llamamos a meter para indicar que ha entrado monitor en el campamento         
                }
                finally
                {
                    cerrojoDer.unlock();
                }
            }
           else 
           {
               try {
                   semaforoAforo.acquire();    //Disminuye un permiso del aforo
                   detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                   colaEntradaDer.sacar(c.getCId());            //Sacamos a niño de la cola de espera
                   log.escribir(" Niño " + c.getCId() + " entra al campamento por la puerta derecha \n"); //Escribe estado del niño en archivo
                   dentro.meter(c.getCId());                  //Llamamos a meter para indicar que ha entrado niño en el campamento    
               } 
               catch (InterruptedException ex) 
               {
                   Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
        }
        detener.comprobar();   //Punto donde se detendrá funcionaientod el program
        accederActividad(c);  //Llama a función acceder a actividad (la primera vez siempre mismo orden)
    }
    
    //Método por el que niño sale del campamento
    public void salir(Child c)
    {
        detener.comprobar();   //Punto donde se detendrá funcionaientod el program
        dentro.sacar(c.getCId()); //Saca a niño del campamento
        log.escribir("Niño " + c.getCId() + " sale del campamento\n"); //Escribe estado del niño en archivo log
        semaforoAforo.release(); //Libera permiso del niños sobre aforo del campamento
    }
    
    //Método niños accedan a actividad correspondiente
     public void accederActividad(Child c)
    {
      int n = 1 + (int) (3 *Math.random()); //random  1 - 3 para determinar actividad que va a realizar
      detener.comprobar(); //Ejecución se detendrá en este punto si se indica
      if(c.getContActividades() == 15)    //Si niños realiza 15 actividades sale del campamento
      {
          detener.comprobar();   //Punto donde se detendrá funcionaientod el program
          salir(c); //Llama a función salir para sacar al niño del campamento
      }
      else  //Al niño todavia le faltan actividades por hacer
      {
        switch(n)
        {
          case 1:   //Niño a actividad soga
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                log.escribir(" Niño " + c.getCId() + " accede a la actividad soga\n");    //Escribe estado del niño en archivo
                childEnSoga.meter(c.getCId());    //Niño entra en actividad soga
                soga(c);     //Llama a función que simula actividad soga
                 break;
          case 2:     //Niño entra en actividad tirolina
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                colaTirolina.meter(c.getCId());  //Niño accede a la cola de espera de la tirolina
                log.escribir(" Niño " + c.getCId() + " accede a la cola de espera de la actividad tirolina\n"); //Escribe estado del niño en archivo
                try 
                {
                    semaforoCapTir.acquire();   //Adquiere permiso de la acpacidad de actividad tirolina
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
                }
                childEnTirPrep.meter(c.getCId());  //Accede a parte de preparción de la actividad
                colaTirolina.sacar(c.getCId());    //Niño deja cola de espera
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                log.escribir(" Niño " + c.getCId() + " accede a la actividad tirolina\n"); //Escribe estado del niño en archivo
                tirolina(c);  //Llama a función que simula la actividad de tirolina
              
              break;

          case 3:     //Niño entra en actividad merienda
                if(c.getContActividades() >= 3) //Para merendar debe haber realizado mas o tres actividades
                {  
                    try 
                    {
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                        colaMerienda.meter(c.getCId()); //Entra en la cola para la merienda
                        log.escribir(" Niño " + c.getCId() + " accede a la cola de la la actividad merienda\n"); //Escribe estado del niño en archivo
                        semaforoCapMer.acquire();       //Disminuye en uno permisos del semaforo asociado a la merienda
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                        colaMerienda.sacar(c.getCId()); //Saca al nió de la cola de espera
                        childEnMer.meter(c.getCId());         //Mete a niño en lista de niños que estan merendando
                        log.escribir(" Niño " + c.getCId() + " accede a la actividad merienda\n"); //Escribe estado del niño en archivo
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                        merienda(c);                 //Llama a función merienda para simular actividad
                    } 
                    catch (InterruptedException ex) 
                    {
                        Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
                    }    
                }
              else  //Si ha realizado menos de tres actividades
              {
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                log.escribir(" Niño " + c.getCId() + " selecciona siguiente actividad\n"); //Escribe estado del niño en archivo
                accederActividad(c);  //Accede a actividad distinta
              } //Si ha hecho menos de tres actividades vuelve a llamar a la función paar elegir otra
              break;
        }   
      }
    }
     
    //Método simula actividad merienda para los niños
    public void merienda(Child c) 
    {
        try 
        {
            detener.comprobar();   //Ejecución se detendrá en este punto
            log.escribir(" Niño " + c.getCId() + " coge plato limpio\n"); //Escribe estado del niño en archivo
            servir.acquire();   //Si no hay platos limpios se bloquea
            sleep(7000);        //Tarda 7 segundos en merendar
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            log.escribir(" Niño " + c.getCId() + " merienda\n"); //Escribe estado del niño en archivo
            limpias.sacar(" " + numLimpias);    //Quita el anterior número de bandejas limpias
            numLimpias--;       //Disminuye numero de bandejas limpias y aumenta de sucias
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            limpias.meter(" " + numLimpias);    //Introduce nuevo valor de bandejas limpias
            sucias.sacar(" " + numSucias);  //Saca antiguo valor de bandejas sucias
            numSucias++;    //Aumenta el número de bandejas sucias
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            sucias.meter(" " + numSucias);  //Introduce nuevo número de bandejas sucias
            limpiar.release();  //Avisa de que hay bandejas que limpiar
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            log.escribir(" Niño " + c.getCId() + " deja plato sucio en su pila\n"); //Escribe estado del niño en archivo
            childEnMer.sacar(c.getCId());    //Saca a niño del merendero
            semaforoCapMer.release();   //avisa de que hay un plato sucio
            c.sumar(1);   //Aumenta en uno el contador de actividades del niño
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            log.escribir(" Niño " + c.getCId() + " se va a la zona comun\n"); //Escribe estado del niño en archivo
            zonaComun(c);   //Tras terminar actividad niño va a zona comun  
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Método simula funcionamiento de la zona común para los niños
    public void zonaComun(Child c)
    {
        try 
        {
            detener.comprobar();   //Punto dónde se detendrá ejecución del programa
            log.escribir(" Niño " + c.getCId() + " accede a la zona comun\n"); //Escribe estado del niño en archivo
            childEnZc.meter(c.getCId());    //Mete a niño en la zona comun
            sleep(2000 + (int) (2000* Math.random()));  //Niño descansa entre 2 y 4 segundos entre cada actividad
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            childEnZc.sacar(c.getCId());    //Saca a niño de la zona comun
            log.escribir(" Niño " + c.getCId() + " selecciona siguiente actividad\n"); //Escribe estado del niño en archivo
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            accederActividad(c);   //Llama a función para realizar nueva actividad
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    //Método simula funcionamiento de la actividad tirolina para los niños
    public void tirolina(Child c)
    {
        try 
        {
            detener.comprobar();   //Punto donde se detendrá funcionaientod el programa
            esperaTir.acquire();    //Niño se bloquea si monitor esta en descanso
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            subido.release(); //Avisa a monitor que hay niño para tirarse por la tirolina
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            log.escribir(" Niño " + c.getCId() + " se prepara para tirarse en la tirolina\n"); //Escribe estado del niño en archivo
            sleep(1000);    //Monitor prepara al niño para tirarse
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            childEnTirPrep.sacar(c.getCId()); //Niño termina de prepararse
            log.escribir(" Niño " + c.getCId() + " se tira por la tirolina\n"); //Escribe estado del niño en archivo
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            childEnTir.meter(c.getCId());    //Pasa a estado de tirarse
            señal.acquire();   //Espera señal del monitor para tirarse
            sleep(3000);    //Tarda 3 segundos en llegar al final
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            childEnTir.sacar(c.getCId());   //Sca a niño de estado de tirarse
            log.escribir(" Niño " + c.getCId() + " llega al final de la tirolina\n"); //Escribe estado del niño en archivo
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            childEnFinTir.meter(c.getCId()); //Cabia a estado final
            sleep(500); //Niño tarda 0,5 segundos en bajarse
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            childEnFinTir.sacar(c.getCId());    //Sca a niño de tirolina
            log.escribir(" Niño " + c.getCId() + " se baja de la tirolina\n"); //Escribe estado del niño en archivo
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            semaforoCapTir.release();       //Libera permiso del semaforo de la tirolina
            c.sumar(1);   //Aumenta en uno el contador de actividades del niño
            contTir++;      //Se incremente el numero de veces que se ha realizado esta actividad
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            log.escribir(" Niño " + c.getCId() + " se va a la zona comun\n");   //Escribe estado del niño en archivo
            zonaComun(c);    //Accede a zona comun para descansar tras actividad
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //Método simula funcionamiento de la actividad soga para los niños
    public void soga(Child c) 
    {
        
        detener.comprobar();   //Punto donde se detendrá ejecución del programa
        detener.comprobar();   //Punto donde se detendrá funcionaientod el program
        childEnSoga.sacar(c.getCId());  //Saca a niño de la lista
        if (!finSoga) //Si hilo llega y ya hay 10 jugadores
        {
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            log.escribir(" Niño " + c.getCId() + " selecciona otra actividad ya que no hay hueco en actividad soga\n"); //Escribe estado del niño en archivo
            accederActividad(c);  //No espera y se va a otra actividad
        } else //Introduce al niño ene quipo correspondiente
        {
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            participantes.add(c);   //Añade a niño a lista de participantes
            numJugadores++; //Aumenta le número de jugadores
            childEnSoga.meter(c.getCId());  //Introduce niño en actividad soga
            try {
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                barreraSoga.await();    //Espera a que lleguen 10 niños
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                hacerEquipo.await(); //Esperan a que monitor forme los equipos
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                finSoga = false;    //Indica que se esta realizando la actividad
                sleep(7000);    //Tardan 7 segundos en realizar actividad
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                jugar.await();  //Espera a que niños hayan terminado de jugar
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                elegirGanador.await(); //Esperan a que monitor elija ganador
                detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                childEnSoga.sacar(c.getCId());
                if (equiA.contains(c)) //Si niño esta en el equipo A
                {
                    detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                    log.escribir(" Niño " + c.getCId() + " ha ganado competición soga\n"); //Escribe estado del niño en archivo
                    equipoA.sacar(c.getCId());  //sacamos  niño de equipo A
                    equiA.remove(c);    //Sacamos al niño de su respectivoa array
                    if (equiA.isEmpty() && equiB.isEmpty()) //Si equipos estan vacios ha terminado la actividad
                    {
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                        finSoga = true; //Indica que ha terminado actividad
                    }
                    numA--;         //Disminuimos número de jugadores en equipo
                    if (ganador == 0) //Equipo A ha ganado
                    {
                        c.sumar(2); //Se suma dos a equipo ganador
                    } else {
                        c.sumar(1); //Equipo A no ha ganadp
                    }
                    detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                    numJugadores--; //Disminuye numero de jugadores
                    log.escribir(" Niño " + c.getCId() + " se va a la zona comun\n");   //Escribe estado del niño en archivo
                    detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                    zonaComun(c); //Tras terminar actividad niños van a zona comun
                } else //Si niño estaba en el equipo B
                {
                    detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                    log.escribir(" Niño " + c.getCId() + " ha perdido competición soga\n"); //Escribe estado del niño en archivo
                    equipoB.sacar(c.getCId());  //sacamos niño de equipo B
                    equiB.remove(c);    //Saca a niño del array correspondiente
                    if (equiA.isEmpty() && equiB.isEmpty()) //Si equipos estan vacios ha terminado la actividad
                    {
                        detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                        finSoga = true; //Indica que ha terminado la actividad
                    }
                    numB--;             //Disminuimos número de jugadores en equipo B
                    if (ganador == 1) //Equipo B ha ganado
                    {
                        c.sumar(2); //Se suma dos a equipo ganador
                    } else {
                        c.sumar(1); //Equipo B ha perdido
                    }
                    detener.comprobar();   //Punto donde se detendrá funcionaientod el program
                    numJugadores--; //Disminuye numero de jugadores
                    log.escribir(" Niño " + c.getCId() + " se va a la zona comun\n"); //Escribe estado del niño en archivo
                    zonaComun(c); //Tras terminar actividad niños van a zona comun
                }
            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(Campamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    //Método para cerrar TODO el campamento
    public boolean cerrar() {
        if(entradaIzq || entradaDer)    //Si una de las dos entradas estan abiertas las cierra
        {
            detener.comprobar();   //Punto donde se detendrá funcionaientod el program
            log.escribir(" se cierra campamento\n"); //Escribe estado del campamento
            semaforoAforo.drainPermits();   //Vacia permisos para bloquear a nuevos hilos que intenten acceder al campamento
            entradaIzq = false; //Actualiza estado de las entradas
            entradaDer = false;
        }   
        return false;   //Devuelve que el campamento est acerrado
    }
    
    //Método para abrir TODO el campamento
    public boolean abrir()
    {
      if(!entradaIzq && !entradaDer)    // Si ambas entradas estan cerradas
      {
        detener.comprobar();   //Punto donde se detendrá funcionaientod el program
        semaforoAforo.release(aforo);   //Libera todos los permisos
        entradaDer = true;  //Actualiza estados de las entradas
        entradaIzq = true;
      }
      return true;
    }
    
    /*Método determina si número es par*/
    public boolean paridadId(int n)
    {
        boolean par = false;    //Variable almacena si parte numérica del id es par
        if(n%2 == 0)       
        {
            par = true; //Si parte numérica es par cambia variable a true
        }   
        
        return par; //Devuelve si es par o no
    }
    
    //Método separa parte numérica del id (para monitores)
    public int numId(Monitor m)
    {
        String id = m.getMId();  //Guarda identificador del monitor
        String[] a = id.split("M");  //Convierte identificador en array
        int n = Integer.valueOf(a[1]);  //Convierte segunda parte del identificador (parte numérica) a int
        return n;   //Devuelve parte numérica dl identificador
    }
    
    //Método saca un solo número del 1 al 4 de la parte numérica del identificador de un niño
    public int moduloId(int n)
    {
        return n % 3;       //Módulo 3 de parte numérica
    }
}
