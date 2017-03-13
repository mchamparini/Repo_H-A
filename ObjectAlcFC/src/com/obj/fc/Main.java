package com.obj.fc;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;
import com.ximpleware.XPathParseException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Martin Champarini
 */
public class Main {
    private static ArrayList<String> arrayL = new ArrayList<>();
    private static ArrayList<String> a_femtoid = new ArrayList<>();
    private static TreeMap<String,String> map_child = new TreeMap<>();
    private static TreeMap<String,String> map_head = new TreeMap<>();
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException  {
        //file csv a crear
        FileWriter fileWriter=new FileWriter(args[0],true);
        arrayL.add("accessMode");
        arrayL.add("administrativeState");
        arrayL.add("bSRName");
        arrayL.add("bsrId");
        arrayL.add("bsrType");
        arrayL.add("category");
        arrayL.add("groupName");
        arrayL.add("hardwareProfile");
        arrayL.add("ipsecRouterIPAddr");
        arrayL.add("latitude");
        arrayL.add("locationAreaCode");
        arrayL.add("locationName");
        arrayL.add("locationProfile");
        arrayL.add("longitude");
        arrayL.add("mimVersion");
        arrayL.add("oui");
        arrayL.add("profile");
        arrayL.add("routingAreaCode");
        arrayL.add("serialNumber");
        arrayL.add("serviceAreaCode");
        arrayL.add("softwareVersion");
        arrayL.add("userSpecificInfo");        
        try{
            createHeaderCsv(args[0],arrayL);
            VTDGen vg = new VTDGen();
            int i;
            AutoPilot ap = new AutoPilot();
            AutoPilot ap2 = new AutoPilot();
            ap.selectXPath("//Femto[@id]/attributes");
            ap2.selectXPath("//Femto[@id]");
            //file xml a procesar
            
            String fecha_obj = args[1];
            fecha_obj = (fecha_obj.substring(fecha_obj.indexOf("2"),fecha_obj.indexOf("."))).replace(".", "");
            if (vg.parseFile(args[1], false))
            {
                VTDNav vn = vg.getNav();
                ap2.bind(vn);
                 while ((i = ap2.evalXPath()) != -1) {                    
                        a_femtoid.add(vn.toString(vn.getAttrVal("id")));
                }
                vn.push();
                vn.pop();              
                ap2.resetXPath();
                ap.bind(vn);
                for (int h = 0; h < a_femtoid.size() &&  ((i = ap.evalXPath()) != -1); h++) {                                                         
                        vn.push();          
                        for (int k = 0; k < arrayL.size(); k++) {   
                            if (vn.toElement(VTDNav.FIRST_CHILD, arrayL.get(k)))
                            {
                                if(vn.toElement(VTDNav.FIRST_CHILD,"unset")){
                                    map_child.put("GROUPNAME","");
                                    vn.toElement(VTDNav.PARENT,"groupName");                           
                                }
                                int j = vn.getText();
                                if (j != -1)                                       
                                {       
                                       if("".equals(vn.toString(j)))
                                        {
                                             map_child.put(vn.toString(vn.getCurrentIndex()).toUpperCase(),"");
                                        }else{
                                             map_child.put(vn.toString(vn.getCurrentIndex()).toUpperCase(),vn.toString(j));                                                                               
                                        }                                                                
                                }                              
                                vn.toElement(VTDNav.PARENT,"attributes");                           
                            }
                        }                                       
                        vn.pop();
                        map_child.put("FEMTO", a_femtoid.get(h));
                        map_child.put("FECHA",fecha_obj);
                        writeCSV(fileWriter,map_child);
                }
                ap.resetXPath();
            }
        }catch(XPathEvalException ex){
            System.out.println("XPathEvalException-----------------------\n"+ex.getMessage());
        } catch (NavException ex) {
            System.out.println("NavException-----------------------\n"+ex.getMessage());
        } catch (XPathParseException ex) {
            System.out.println("XPathParseException-----------------------\n"+ex.getMessage());
       }catch(IOException ex){
           System.out.println(""+ex.getMessage());
        }finally{
           fileWriter.close();
       }
    }   
    public static  void writeCSV(FileWriter fileWriter,TreeMap csvMap) throws IOException{
        Set set = csvMap.entrySet();
        int counter=0;
        Iterator iter = set.iterator();
             while (iter.hasNext()) {                 
             Map.Entry me = (Map.Entry)iter.next(); 
             counter++;                          
                          if(!(counter < csvMap.size())){
                          fileWriter.append(""+me.getValue().toString());
                          fileWriter.append(""+NEW_LINE_SEPARATOR);                              
                          }
                          else{
                                 fileWriter.append(""+me.getValue().toString());
                                 fileWriter.append(""+COMMA_DELIMITER);                                  
                        }
            }             
     }
     public static  void createHeaderCsv(String fileName,ArrayList<String> arrayHead) throws IOException{
       FileWriter fileWriter=new FileWriter(fileName,true);
       try {        
        fileWriter.append(""+headerCsv(arrayHead));
        fileWriter.append(""+NEW_LINE_SEPARATOR);
       } catch (IOException e) {
       }  finally{
           try {
               fileWriter.close();
           } catch (Exception e) {
           }
       }      
   }
     public static String headerCsv(ArrayList<String> arrayHead){
         for (int i = 0; i < arrayHead.size(); i++) {
             map_head.put(arrayHead.get(i).toUpperCase(),"a");
         }
         map_head.put("FEMTO","a");
         map_head.put("FECHA","a");
         String header="";
        Set set = map_head.entrySet();
        Iterator iter = set.iterator();        
        while (iter.hasNext()) {           
            Map.Entry me = (Map.Entry)iter.next();
            header+=me.getKey()+COMMA_DELIMITER;
        }      
         if(header.endsWith(COMMA_DELIMITER)){
                 header = header.substring(0,header.length() - 1);
         }
         header=header.replaceAll("\\s+","");
       return header;
    }
   public static void insert(TreeMap<String,String> mapa){
       
       
       
       
//             StringBuilder sql = new StringBuilder("INSERT INTO ").append(tableName).append(" (");
//             StringBuilder placeholders = new StringBuilder();
//             for (Iterator<String> iter = dataMap.keySet().iterator(); iter.hasNext();) {
//                 sql.append(iter.next());
//                 placeholders.append("?");
//
//                 if (iter.hasNext()) {
//                     sql.append(",");
//                     placeholders.append(",");
//                 }
//             }
//
//             sql.append(") VALUES (").append(placeholders).append(")");
//             preparedStatement = connection.prepareStatement(sql.toString());
//             int i = 0;
//
//             for (String value : dataMap.values()) {
//                 preparedStatement.setObject(i++, value);
//             }
//
//             int affectedRows = prepatedStatement.executeUpdate();
   }
}
