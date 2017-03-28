package com.nokiaaluipran;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @author Martin Champarini
 */
public class Main {      
    public static ArrayList<String> filesInList=new ArrayList();
    public static void main(String[] args) throws IOException{  
            filesInList = getFiles(args[0]);         
            //creo primero todos los csv solo con el header
            createCsvList(filesInList);                        
           ExecutorService executor= Executors.newFixedThreadPool(filesInList.size());
            try {
             for (int i = 0; i < filesInList.size(); i++) {   
              String pathXml = filesInList.get(i).toString().
                                     substring(0,filesInList.get(i).toString().indexOf("="));
              String pathPropertieFile = filesInList.get(i).toString().
                                      substring(filesInList.get(i).toString().indexOf("=")+1);
              String pathCsv = pathXml.replaceAll("\\.xml", "_xml")+".csv";
              executor.execute(new Runnable() {
                  @Override
                  public void run() {
                      try {                         
                          System.out.println("Go "+Thread.currentThread().getName()+" !!!");
                          BuildMaps.process(pathXml, pathCsv, pathPropertieFile);
                      } catch (IOException ex) {
                          System.out.println(""+ex.getMessage());
                      }
                  }
              });
             }
             executor.shutdown();
        }catch (Exception e) {
                System.out.println(""+e.getMessage());
        }
           
    }    
    public static ArrayList getFiles(String pathList){
         FileInputStream fis = null;
         BufferedReader reader = null;
         ArrayList<String> listFile = new ArrayList();
        try {
            fis = new FileInputStream(pathList);
            reader = new BufferedReader(new InputStreamReader(fis));                   
            String line = reader.readLine();
            while(line != null){
                listFile.add(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
          
        } finally {
            try {
                reader.close();
                fis.close();
            } catch (IOException ex) {
            }
        }
       return listFile;
   }
  public static void createCsvList(ArrayList list) throws IOException {
       for (int i = 0; i < list.size(); i++) {
           String pathXml = list.get(i).toString().
                                     substring(0,list.get(i).toString().indexOf("="));
           String pathPropertieFile = list.get(i).toString().
                                      substring(list.get(i).toString().indexOf("=")+1);
           BuildMaps.createHeaderCsv(pathXml.replaceAll("\\.xml", "_xml")+".csv", pathPropertieFile);
      }
  }
}   


