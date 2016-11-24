
package com.enodeb;

import java.io.IOException;
import java.util.List;


/**
 * Clase encargada de la asignacion de tareas por hilo
 * @author Martin Champarini
 */
public class AdmThread implements Runnable{
   private List lista;
   private String fileName;   
   public AdmThread (){}          
   public AdmThread (List lista,String fileName){
       this.fileName=fileName;
       this.lista=lista;       
   }   
   @Override
   public void run(){      
        try {
           BuildMaps.createHeaderCsv(fileName);
        }catch (IOException exIO) {               
            System.out.println(exIO.getMessage());                
        }
        for(int i = 0; i < lista.size(); i++) {
                try {
                    System.out.println(""+i);                              
                    BuildMaps.process(lista.get(i).toString(),fileName);          
                }catch(IOException ex){
                    System.out.println("FileName:"+fileName+"\n"+ex.getMessage());
                }
        }//fin for  
        try {
           Thread.sleep(5000);  
       }catch(InterruptedException exInt){         
           Thread.currentThread().interrupt();
           System.out.println("Hilo interrumpido:"+Thread.currentThread().getName()+"\n"+exInt.getMessage());

       }
   }//fin run   
}

