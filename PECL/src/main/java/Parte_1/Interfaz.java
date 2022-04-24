/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parte_1;

import static java.lang.Thread.sleep;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author blanf
 */
public class Interfaz extends javax.swing.JFrame 
{
    /*Inicializo variables*/
    Campamento camp;
    Detener deten;
    Monitor mon;
    Child c;
    /**
     * Creates new form Interfaz
     */
    public Interfaz() {
        initComponents();
        int contActChild = 0, contActMon = 0;

        camp=new Campamento(50,colaEsperaIzq,colaEsperaDer,colaEsperaTirolina,colaEntradaMerendero,dentro,monitorTir,monitoresMerendero,monitoresZC,monitorSoga,colaEntradaMerendero,colaEsperaTirolina,hilosEntran,childMerendando,bandejasLimpias,bandejasSucias,childZC,childPreparacion,childEnTirolina,childEnFinal,equipoA,equipoB);
        deten = new Detener();
        
        //Bucle para crear los monitores
        for(int i = 1; i<=4; i++)
        {
            String n1 = String.valueOf(i);      //Convierte entero en string
            mon = new Monitor('M' + n1, camp, contActMon);
            mon.start();
        }
        //Crea hilo anónimo en el que crearé todos los hilos que componen el sistema
        new Thread (new Runnable()
        {
            public void run()
            {
                //Bucle para crear los hilos niños
                for(int i = 1; i <= 200; i++)
                {
                    String n2 = String.valueOf(i);
                    c = new Child('N' + n2, camp, contActChild);
                    c.start();
                    try
                    {
                    sleep(1000 + (int)(2000*Math.random()));    //Niños se crean cada 1 - 3 segundos
                    } catch (InterruptedException e){ }
                }
            }   
        }).start();    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollBar2 = new javax.swing.JScrollBar();
        titulo = new javax.swing.JLabel();
        tituloEntradaIzq = new javax.swing.JLabel();
        tituloEntradaDer = new javax.swing.JLabel();
        colaEsperaIzq = new javax.swing.JTextField();
        colaEsperaDer = new javax.swing.JTextField();
        tituloCamp = new javax.swing.JLabel();
        tituloSoga = new javax.swing.JLabel();
        tituloTirolina = new javax.swing.JLabel();
        hilosEntran = new javax.swing.JTextField();
        colaEsperaTirolina = new javax.swing.JTextField();
        tituloMonitorSoga = new javax.swing.JLabel();
        tituloequipoA = new javax.swing.JLabel();
        tituloEquipoB = new javax.swing.JLabel();
        monitorTir = new javax.swing.JTextField();
        tituloMonitorTir = new javax.swing.JLabel();
        tituloPreparacion = new javax.swing.JLabel();
        childPreparacion = new javax.swing.JTextField();
        tituloEnTir = new javax.swing.JLabel();
        childEnTirolina = new javax.swing.JTextField();
        tituloFinalizado = new javax.swing.JLabel();
        childEnFinal = new javax.swing.JTextField();
        monitorSoga = new javax.swing.JTextField();
        tituloZC = new javax.swing.JLabel();
        tituloMonitorZC = new javax.swing.JLabel();
        tituloChildZC = new javax.swing.JLabel();
        monitoresZC = new javax.swing.JTextField();
        childZC = new javax.swing.JTextField();
        tituloMerendero = new javax.swing.JLabel();
        colaEntradaMerendero = new javax.swing.JTextField();
        tituloBandejSucias = new javax.swing.JLabel();
        tituloBandejLimp = new javax.swing.JLabel();
        tituloMonitorMeren = new javax.swing.JLabel();
        bandejasSucias = new javax.swing.JTextField();
        bandejasLimpias = new javax.swing.JTextField();
        childMerendando = new javax.swing.JTextField();
        monitoresMerendero = new javax.swing.JTextField();
        botonPausar = new javax.swing.JButton();
        botonReanudar = new javax.swing.JButton();
        tituloDentro = new javax.swing.JLabel();
        dentro = new javax.swing.JTextField();
        equipoA = new javax.swing.JTextField();
        equipoB = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        titulo.setText("Entrada al campamento");

        tituloEntradaIzq.setText("Puerta Izquierda");

        tituloEntradaDer.setText("Puerta derecha");

        colaEsperaIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colaEsperaIzqActionPerformed(evt);
            }
        });

        colaEsperaDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colaEsperaDerActionPerformed(evt);
            }
        });

        tituloCamp.setText("Campamento");

        tituloSoga.setText("Soga");

        tituloTirolina.setText("Tirolina");

        colaEsperaTirolina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colaEsperaTirolinaActionPerformed(evt);
            }
        });

        tituloMonitorSoga.setText("Monitor");

        tituloequipoA.setText("Equipo A");

        tituloEquipoB.setText("Equipo B");

        monitorTir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monitorTirActionPerformed(evt);
            }
        });

        tituloMonitorTir.setText("Monitor");

        tituloPreparacion.setText("Preparacion");

        tituloEnTir.setText("Tirolina");

        tituloFinalizado.setText("Finalizacion");

        tituloZC.setText("Zona Comun");

        tituloMonitorZC.setText("Monitores");

        tituloChildZC.setText("Child");

        tituloMerendero.setText("Merendero");

        tituloBandejSucias.setText("Bandejas Sucias");

        tituloBandejLimp.setText("Bandejas Limpias");

        tituloMonitorMeren.setText("Monitores");

        botonPausar.setText("Pausar");
        botonPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonPausarActionPerformed(evt);
            }
        });

        botonReanudar.setText("Reanudar");
        botonReanudar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReanudarActionPerformed(evt);
            }
        });

        tituloDentro.setText("Dentro de campamento");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(tituloSoga, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tituloTirolina, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(201, 201, 201))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(tituloEntradaIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(165, 165, 165)
                        .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(120, 120, 120)
                        .addComponent(tituloEntradaDer, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(434, 434, 434)
                        .addComponent(tituloMerendero, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(146, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(colaEntradaMerendero)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(hilosEntran, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(monitorTir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tituloMonitorTir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(childPreparacion, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tituloPreparacion, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tituloEnTir)
                                    .addComponent(childEnTirolina, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(35, 35, 35)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tituloFinalizado, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(childEnFinal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(colaEsperaTirolina, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(42, 42, 42))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(colaEsperaIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 414, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(colaEsperaDer, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(monitoresMerendero, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tituloBandejSucias, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(bandejasSucias, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tituloMonitorMeren, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(51, 51, 51)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(tituloBandejLimp, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
                                            .addComponent(bandejasLimpias))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(109, 109, 109)
                                        .addComponent(childMerendando, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(botonPausar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(35, 35, 35)
                                        .addComponent(botonReanudar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tituloDentro, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(209, 209, 209)
                                .addComponent(tituloCamp, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tituloMonitorZC, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                            .addComponent(monitoresZC))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dentro)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tituloMonitorSoga, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(monitorSoga, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tituloequipoA, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(104, 104, 104)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(childZC)
                                        .addContainerGap())
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(tituloChildZC, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tituloEquipoB, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(equipoA, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
                                    .addComponent(equipoB))
                                .addGap(0, 0, Short.MAX_VALUE))))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tituloZC, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(426, 426, 426))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tituloEntradaIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51)
                        .addComponent(tituloDentro)
                        .addGap(39, 39, 39))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(titulo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(tituloEntradaDer)
                                .addGap(7, 7, 7)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(colaEsperaIzq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(colaEsperaDer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tituloCamp)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(dentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tituloTirolina, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tituloSoga))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(hilosEntran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colaEsperaTirolina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tituloPreparacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tituloMonitorSoga)
                        .addComponent(tituloequipoA)
                        .addComponent(tituloFinalizado)
                        .addComponent(tituloEnTir)
                        .addComponent(tituloMonitorTir)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(monitorTir, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(childPreparacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(childEnTirolina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(childEnFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(monitorSoga, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(equipoA, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tituloEquipoB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(equipoB, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tituloZC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tituloMonitorZC)
                    .addComponent(tituloChildZC))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monitoresZC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(childZC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(tituloMerendero)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colaEntradaMerendero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tituloBandejSucias)
                    .addComponent(tituloBandejLimp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bandejasSucias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bandejasLimpias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tituloMonitorMeren))
                    .addComponent(childMerendando))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(monitoresMerendero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonPausar)
                    .addComponent(botonReanudar))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void colaEsperaIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colaEsperaIzqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colaEsperaIzqActionPerformed

    private void colaEsperaDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colaEsperaDerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colaEsperaDerActionPerformed

    private void colaEsperaTirolinaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colaEsperaTirolinaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_colaEsperaTirolinaActionPerformed

    private void monitorTirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monitorTirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_monitorTirActionPerformed

    private void botonPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonPausarActionPerformed
        deten.cerrar();  
    }//GEN-LAST:event_botonPausarActionPerformed

    private void botonReanudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReanudarActionPerformed
        deten.abrir();
    }//GEN-LAST:event_botonReanudarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not availjTextField1tay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interfaz().setVisible(true);
            }
        });
        
        //Crea hilo anónimo en el que crearé todos los hilos que componen el sistema
        new Thread (new Runnable()
        {
            
            public void run()
            {
                   
            }
        }).start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bandejasLimpias;
    private javax.swing.JTextField bandejasSucias;
    private javax.swing.JButton botonPausar;
    private javax.swing.JButton botonReanudar;
    private javax.swing.JTextField childEnFinal;
    private javax.swing.JTextField childEnTirolina;
    private javax.swing.JTextField childMerendando;
    private javax.swing.JTextField childPreparacion;
    private javax.swing.JTextField childZC;
    private javax.swing.JTextField colaEntradaMerendero;
    private javax.swing.JTextField colaEsperaDer;
    private javax.swing.JTextField colaEsperaIzq;
    private javax.swing.JTextField colaEsperaTirolina;
    private javax.swing.JTextField dentro;
    private javax.swing.JTextField equipoA;
    private javax.swing.JTextField equipoB;
    private javax.swing.JTextField hilosEntran;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JTextField monitorSoga;
    private javax.swing.JTextField monitorTir;
    private javax.swing.JTextField monitoresMerendero;
    private javax.swing.JTextField monitoresZC;
    private javax.swing.JLabel titulo;
    private javax.swing.JLabel tituloBandejLimp;
    private javax.swing.JLabel tituloBandejSucias;
    private javax.swing.JLabel tituloCamp;
    private javax.swing.JLabel tituloChildZC;
    private javax.swing.JLabel tituloDentro;
    private javax.swing.JLabel tituloEnTir;
    private javax.swing.JLabel tituloEntradaDer;
    private javax.swing.JLabel tituloEntradaIzq;
    private javax.swing.JLabel tituloEquipoB;
    private javax.swing.JLabel tituloFinalizado;
    private javax.swing.JLabel tituloMerendero;
    private javax.swing.JLabel tituloMonitorMeren;
    private javax.swing.JLabel tituloMonitorSoga;
    private javax.swing.JLabel tituloMonitorTir;
    private javax.swing.JLabel tituloMonitorZC;
    private javax.swing.JLabel tituloPreparacion;
    private javax.swing.JLabel tituloSoga;
    private javax.swing.JLabel tituloTirolina;
    private javax.swing.JLabel tituloZC;
    private javax.swing.JLabel tituloequipoA;
    // End of variables declaration//GEN-END:variables
}
