package com.nokiaaluipran;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;


/**
 * Clase encargada de administrar el propertie file
 * @author Martin Champarini
 */
public class PropertyFile {    
   
   
    private static final String COMMA=";";
    private  String pathPropertie;
    public PropertyFile(String pathPropertie){
    this.pathPropertie = pathPropertie;
    }

    public static TreeMap loadPropertieMap(String pathPropertie){
    InputStream entrada_contadores = null;
    TreeMap<String,String> propertieMap = new TreeMap<>();
    Properties propiedades = new Properties();
    try {
        entrada_contadores = new FileInputStream(pathPropertie);
        propiedades.load(entrada_contadores);
       for (Enumeration e = propiedades.keys(); e.hasMoreElements(); ){
		        Object obj = e.nextElement();                                          
                        propertieMap.put((String)obj, propiedades.getProperty(obj.toString()));                        
		}
       return propertieMap;
        
    } catch (IOException exIO) {
                    System.out.println("Propertie File:"+exIO.getMessage());                

    } finally {
        if (entrada_contadores != null) {
            try {
                entrada_contadores.close();
            } catch (IOException e) {
                            System.out.println("Propertie File:"+e.getMessage());                

            }
            }
        }
           return propertieMap;
    }
    /** 
     * Metodo que valida si una key existe en el propertie file
     * 
     * @param clave a validar en el propertie file
     * @return true si encuentra la key, y false caso contrario
     **/
    public static boolean validarPropertieKey(String clave,String pathPropertie){
        TreeMap<String,String> propertieMap = new TreeMap<>();
        propertieMap=loadPropertieMap(pathPropertie);
        Set set = propertieMap.entrySet();
        Iterator iter = set.iterator();        
        while (iter.hasNext()){
            Map.Entry me = (Map.Entry)iter.next();
            if(me.getKey().equals(clave))
            {               
                return true;
            }
        }
        return false;
    }
    /**
     * Metodo encargado de la construccion de la cabecera de los archivos csv
     * @return cabecera del archivo csv
     */
    public static String headerCsv(String pathPropertie){
         String header="";
        TreeMap<String,String> propertieMap=loadPropertieMap(pathPropertie);
        Set set = propertieMap.entrySet();
        Iterator iter = set.iterator();        
        while (iter.hasNext()) {           
            Map.Entry me = (Map.Entry)iter.next();
            header+=me.getValue()+COMMA;
        }      
         if(header.endsWith(COMMA)){
                 header = header.substring(0,header.length() - 1);
         }
         header=header.replaceAll("\\s+","");
       return header;
    }  
   
}
        