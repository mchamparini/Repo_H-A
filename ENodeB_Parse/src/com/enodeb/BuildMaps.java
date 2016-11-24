package com.enodeb;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Clase encargada de la creacion de los Mapas para el armado de los csv
 * @author Martin Champarini
 */
public class BuildMaps{
    
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";      
    private static ArrayList<String> arrayCounters = new ArrayList<>();
    private static final TreeMap<String,String> propertyMap = PropertyFile.loadPropertieMap();
    //private static ArrayList<String> array3 = new ArrayList<>();
    private static int c = 0;
    /**
     * Metodo principal donde inicia el proceso de los XML
     * @param filePath direccion fisica del archivo xml
     * @param fileName direccion fisica donde esta el archivo csv
     */
    public static void process(String filePath,String fileName) throws IOException{
        FileWriter fileWriter=new FileWriter(fileName,true);
        try{        
        TreeMap<String,String> csvMapHead=new TreeMap<>();
        TreeMap<String,String> csvMapAll=new TreeMap<>();
        TreeMap<String,String> csvMapCounter=new TreeMap<>();
        TreeMap<String,String> tm = new TreeMap<>();
        PropertyFile pf = new PropertyFile();
        File f = new File(filePath);//path del xml a leer
	FileInputStream fis =  new FileInputStream(f);        
        byte[] b = new byte[(int) f.length()];
	fis.read(b);
        int flag_gp=0;
        int flag_moid=0;
        VTDGen vg = new VTDGen();
	vg.setDoc(b);
	vg.parse(true); 
	VTDNav vn = vg.getNav();
	AutoPilot ap = new AutoPilot(vn);
        ap.selectElement("*");
	while(ap.iterate()){
            int t = vn.getText(); 
		if (t!=-1){        
                    switch (vn.toString(vn.getCurrentIndex())) {
                        case "cbt":
                            csvMapHead.put(vn.toString(vn.getCurrentIndex()), vn.toNormalizedString(t));
                            break;
                        case "neun":
                            csvMapHead.put(vn.toString(vn.getCurrentIndex()), vn.toNormalizedString(t));
                            break;
                        case "vn":
                            csvMapHead.put(vn.toString(vn.getCurrentIndex()), vn.toNormalizedString(t));
                            break;                       
                        case "st":
                            csvMapHead.put(vn.toString(vn.getCurrentIndex()),vn.toNormalizedString(t));
                            break;
                        case "nesw":
                            csvMapHead.put(vn.toString(vn.getCurrentIndex()),vn.toNormalizedString(t));
                            break;    
                        case "gp":
                            csvMapHead.put(vn.toString(vn.getCurrentIndex()),vn.toNormalizedString(t));
                            break; 
                        }
                        if ((vn.toString(vn.getCurrentIndex()).equals("gp"))){
                            //csvMapCounter.put(vn.toString(vn.getCurrentIndex()), vn.toNormalizedString(t));
                            if(flag_gp==1){
                                arrayCounters.clear();
                                  flag_gp=0;
                            }
                            flag_gp=1;
                        } 
                        if(vn.toString(vn.getCurrentIndex()).equals("mt") && flag_gp==1){
                             arrayCounters.add(vn.toNormalizedString(t));
                        }
                        if(vn.toString(vn.getCurrentIndex()).equals("moid")){
                            csvMapCounter.put(vn.toString(vn.getCurrentIndex()), vn.toNormalizedString(t).replaceAll(", ", "<->"));
                            if(flag_moid==1){
                                c=0;
                            }
                            flag_moid=1;
                        }
                        if(vn.toString(vn.getCurrentIndex()).equals("r") && (flag_moid==1)){
                            csvMapCounter.put(arrayCounters.get(c),vn.toNormalizedString(t));
                            c++;
                            if(c == (arrayCounters.size())){
                                
                                csvMapAll.putAll(csvMapHead);
                                csvMapAll.putAll(csvMapCounter);
                                tm = addCounterNulls(csvMapAll);
                                tm.putAll(csvMapAll);
                                writeCSV(fileWriter,tm);
                                csvMapCounter.clear();
                                csvMapAll.clear();
                            }
                        }                 
                } 
	}
     }
     catch (ParseException e){
	     System.out.println("error de parseo:"+e.getMessage()+"en FileName:"+filePath);
     }
     catch (NavException e){
	     System.out.println("error en la navegacion:"+e.getMessage());
     }catch(IOException ex) {
           System.out.println(""+ex.getMessage());
     }finally{
           fileWriter.close();
       }
       
   }
    /**
     * Metodo que agrega par key/val del nodo nesw al csvMap
     * @param csvMapAll
     */
   public static void mostrarMapa(TreeMap<String,String> csvMapAll){
       Set set = csvMapAll.entrySet();
       Iterator iter = set.iterator();
       while (iter.hasNext()){
           Map.Entry me = (Map.Entry) iter.next();
           System.out.println(me.getKey()+":"+me.getValue());
       }
   }
    /**
     * Metodo que crea la cabecera de los archivos csv.
     * @param fileName
     */
   public static  void createHeaderCsv(String fileName) throws IOException{
       PropertyFile pf=new PropertyFile();
       FileWriter fileWriter=new FileWriter(fileName,true);
       try {        
        fileWriter.append(""+pf.headerCsv());
        fileWriter.append(""+NEW_LINE_SEPARATOR);
       } catch (IOException e) {
       }  finally{
           try {
               fileWriter.close();
           } catch (IOException e) {
           }
       }      
   }
   
   /**
     * Metodo para escribir en archivo csv          
     * @param fileName
     */
   public static  void writeCSV(FileWriter fileWriter,TreeMap csvMapAll) throws IOException{
        // FileWriter fileWriter=new FileWriter(fileName,true);
         //TreeMap<String,String> propertieMap=pf.loadPropertieMap();
        Set set = csvMapAll.entrySet();
        int counter=0;
        Iterator iter = set.iterator();
             while (iter.hasNext()) {                 
             Map.Entry me = (Map.Entry)iter.next(); 
             counter++;
                if(propertyMap.containsKey(me.getKey().toString())){                                            
                          
                          if(!(counter < csvMapAll.size())){
                          fileWriter.append(""+me.getValue().toString());
                          fileWriter.append(""+NEW_LINE_SEPARATOR);
                            //  System.out.println("registro finalizado"+"counter: "+counter);
                              
                          }
                          else{
                                 fileWriter.append(""+me.getValue().toString());
                                 fileWriter.append(""+COMMA_DELIMITER);                                  
                        }
                }            
            }
            try {
           fileWriter.flush();
          } catch (Exception e) {
                System.out.println(e.getMessage());
          }
        
     }
   public static  TreeMap addCounterNulls(TreeMap csvMap){
       TreeMap<String,String> newmap = new TreeMap<>();
//       PropertyFile pf=new PropertyFile();
       Set set = propertyMap.entrySet();
       Iterator iter = set.iterator();
       while(iter.hasNext()){
           Map.Entry me = (Map.Entry) iter.next();
           if((validarKeyCsvMap(me.getKey().toString(),csvMap))==0){
               newmap.put(me.getKey().toString(),"");
           }
       }
       return newmap;
    }//Fin addCounternulls
   public static  int validarKeyCsvMap(String clave,TreeMap csvMap){
        Set set = csvMap.entrySet();
        Iterator iter = set.iterator();        
        while (iter.hasNext()){
            Map.Entry me = (Map.Entry)iter.next();
            if(me.getKey().equals(clave))
            {          
                return 1;
            }
        }
        return 0;
   }
   
}