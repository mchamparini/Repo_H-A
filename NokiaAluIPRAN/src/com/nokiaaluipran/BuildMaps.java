
package com.nokiaaluipran;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Clase encargada de la creacion de los Mapas para el armado de los csv
 * Modificacion:coma(,)-->por ;
 * @author Martin Champarini
 */
public class BuildMaps{
   // private static boolean flag=false;
    private static final String COMMA_DELIMITER = ";";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static String[] superNodos={"equipmentMediaIndependentStats","equipmentSystemCpuStats"
                                         ,"equipmentSystemMemoryStats","equipmentAvailableMemoryStats"
                                         ,"equipmentAllocatedMemoryStats","equipmentPhysicalPort"
                                         ,"equipmentCardSlot","serviceCombinedNetworkEgressOctetsLogRecord"
                                         ,"serviceCombinedNetworkIngressOctetsLogRecord"
                                        };
    /**
     * Metodo principal donde inicia el proceso de los XML
     * @param filePath direccion fisica del archivo xml
     * @param fileName direccion fisica donde esta el archivo csv
     */
    public static void process(String filePathXML,String filePathCSV,String pathPropertie) throws IOException{
        FileWriter fileWriter=new FileWriter(filePathCSV,true);
       try{ 
        int count = 0;
        TreeMap<String,String> csvMap=new TreeMap<>();
        File f = new File(filePathXML);//path del xml a leer
	FileInputStream fis =  new FileInputStream(f);        
        byte[] b = new byte[(int) f.length()];
	fis.read(b);        
        VTDGen vg = new VTDGen();
	vg.setDoc(b);
	vg.parse(true); 
	VTDNav vn = vg.getNav();
	AutoPilot ap = new AutoPilot(vn);
        ap.selectElement("*");
	while(ap.iterate()){
            int t = vn.getText();
		if (t!=-1){
                       csvMap.put(vn.toString(vn.getCurrentIndex()), vn.toNormalizedString(t)); 
                }
                else{
                  if(validateSuperNodo(vn.toString(vn.getCurrentIndex()))){
                       csvMap.put(vn.toString(vn.getCurrentIndex()), vn.toString(vn.getCurrentIndex())); 
                       if(count==1){
                           writeCSV(fileWriter,csvMap,pathPropertie);
                           count=0;
                       }
                       count=1;
                    }
                }
	}
     }
     catch (ParseException e){
	     System.out.println("error de parseo:"+e.getMessage()+"en FileName:"+filePathXML);
     }
     catch (NavException e){
	     System.out.println("error en la navegacion:"+e.getMessage());
     }catch(IOException ex){
           System.out.println(""+ex.getMessage());
     }finally{
           fileWriter.close();
       }  
   } 
    
    public static boolean validateSuperNodo(String superNodo){        
        for (int i = 0; i < superNodos.length; i++) {          
            if(superNodos[i].equals(superNodo)){              
                return true;
            }
        }        
        return false;
    }
    /**
     * Metodo que crea la cabecera de los archivos csv.
     */
   public static  void createHeaderCsv(String fileName,String pathPropertie) throws IOException{
       FileWriter fileWriter=new FileWriter(fileName,true);
       try {        
        fileWriter.append(""+PropertyFile.headerCsv(pathPropertie));
        fileWriter.append(""+NEW_LINE_SEPARATOR);
       } catch (IOException e) {
       }  finally{
           try {
               fileWriter.close();
           } catch (Exception e) {
           }
       }      
   }   
   /**
     * Metodo para escribir en archivo csv          
     */
   public static  void writeCSV(FileWriter fileWriter,TreeMap csvMap,String pathPropertie) throws IOException{
     // FileWriter fileWriter=new FileWriter(fileName,true);
        Set set = csvMap.entrySet();
        int counter=0;
        Iterator iter = set.iterator();
//        try {
             while (iter.hasNext()) {                 
             Map.Entry me = (Map.Entry)iter.next(); 
             counter++;
                if(PropertyFile.validarPropertieKey(me.getKey().toString(),pathPropertie)){                                            
                          
                          if(!(counter < csvMap.size())){
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
//           } catch (IOException ex) { 
//            }finally{
//            try {
//            fileWriter.close();
//            } catch (Exception e) {
//                e.getStackTrace();
//            }
//        }
     }
  
//   public static  TreeMap addCounterNulls(TreeMap csvMap){
//       TreeMap<String,String> newmap = new TreeMap<>();
//       PropertyFile pf=new PropertyFile();
//       TreeMap<String,String> propeMap= pf.loadPropertieMap();             
//       Set set = propeMap.entrySet();
//       Iterator iter = set.iterator();
//       while(iter.hasNext()){
//           Map.Entry me = (Map.Entry) iter.next();
//           if(!validarKeyCsvMap(me.getKey().toString(),csvMap)){
//               newmap.put(me.getKey().toString(),"");
//           }
//       }
//       return newmap;
//    }//Fin addCounternulls
//   public static  boolean validarKeyCsvMap(String clave,TreeMap csvMap){
//        Set set = csvMap.entrySet();
//        Iterator iter = set.iterator();        
//        while (iter.hasNext()){
//            Map.Entry me = (Map.Entry)iter.next();
//            if(me.getKey().equals(clave))
//            {          
//                return true;
//            }
//        }
//        return false;
//   }
   
 
}
