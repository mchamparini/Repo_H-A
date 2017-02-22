import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import com.ximpleware.NavException;
import com.ximpleware.ParseException;
import com.ximpleware.VTDGen;
import com.ximpleware.VTDNav;

public class Main {
	private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";   
	private static String attrMeasType = null;
	private static String counterMeasType = null;	
	private static ArrayList<String> arrayValue ;
	private static TreeMap<String,ArrayList<String>> tmap = new TreeMap<String,ArrayList<String>>();
	
	
	public static void main(String[] args) throws IOException {
		/*arrayValue.add("12");
		arrayValue.add("13");
		arrayValue.add("14");
		tmap.put("A", arrayValue);*/		
		//System.out.println(tmap);
								
		process("PATH DEL XML ! ! ! ");
	}
	/*
	 * Main process
	 */
	@SuppressWarnings("unused")
	public static void process(String filePath) throws IOException {
		String measTypeSTR = null;
		int count = 0;

		try {
			File f = new File(filePath);// path del xml a leer
			FileInputStream fis = new FileInputStream(f);
			byte[] b = new byte[(int) f.length()];
			fis.read(b);
			VTDGen vg = new VTDGen();
			vg.setDoc(b);
			vg.parse(true);
			VTDNav vn = vg.getNav();
			//comienzo de iter en key/val mme.prope
			Iterator it = getValuePropertie("PATH DEL PROPERTIES ! ! !")
					.entrySet().iterator();
			while (it.hasNext()) {				
				Map.Entry e = (Map.Entry) it.next();
				if (e.getValue().toString().matches(".*\\d+.*")) {		
					//print counter name
					//System.out.println(e.getKey().toString());					
				for(int i=0;i<getMapCounter(measTypeSTR, vn, e.getKey().toString()).size();i++){
					tmap.put(e.getKey().toString(),getMapCounter(measTypeSTR, vn, e.getKey().toString()));
				}
				//tmap.put(e.getKey().toString(),a);
				
				}
				
			}
			printMap(tmap);
		} catch (ParseException e) {
			System.out.println("error de parseo:" + e.getMessage() + "en FileName:" + filePath);
		} catch (NavException e) {
			System.out.println("error en la navegacion:" + e.getMessage());
		} catch (IOException ex) {
			System.out.println("" + ex.getMessage());
		}
	}// fin process
	/**
	 * @param measTypeSTR:tipo de medicion
	 * @param vn: objecto de nav
	 * @param contador:un counter de la lista del prope
	 * Print counter values
	 */
	public static void getContentCounter(String measTypeSTR, VTDNav vn, String contador) throws NavException {
		int ct = 0;
		if (vn.matchElement("measCollecFile")) {
			vn.toElement(VTDNav.FC, "measData");
			if (vn.toElement(VTDNav.FC, "measInfo")) {
				do {
					vn.toElement(VTDNav.FC, "measType");
					do {
						if (contador.equals(vn.toNormalizedString(vn.getText()))) {
							measTypeSTR = vn.toString(vn.getAttrVal("p"));							
						}
					} while (vn.toElement(VTDNav.NS, "measType"));
					vn.toElement(VTDNav.P);
					vn.toElement(VTDNav.FC, "measValue");
					vn.toElement(VTDNav.FC, "r");
					do {
						if (vn.toString(vn.getAttrVal("p")).equals(measTypeSTR)) {
							//print counter value
							//if(ct==0){
								System.out.println(vn.toNormalizedString(vn.getText()));
							//}
						//	ct=ct+1;
							
							measTypeSTR = "";
								
						}
				} while (vn.toElement(VTDNav.NS, "r"));					
					vn.toElement(VTDNav.P);
					vn.toElement(VTDNav.P);
				
				} while (vn.toElement(VTDNav.NS, "measInfo"));

			} // measInfo
			vn.toElement(VTDNav.P);
			vn.toElement(VTDNav.P);
		} // measCollecFile - root
		else {
			System.out.println(" Root is not 'measData' ");
		}
			tmap.put(contador,arrayValue);
		
		
		//System.out.println(arrayValue);
		//System.out.println(contador);
		
		//System.out.println(tmap);
		arrayValue.clear();		
	}
	
	/**
	 *@return lista de valores de un contador 
	 */
	public static ArrayList<String> getMapCounter(String measTypeSTR, VTDNav vn, String contador) throws NavException {
		ArrayList<String> arL =  new ArrayList<String>();
		if (vn.matchElement("measCollecFile")) {
			vn.toElement(VTDNav.FC, "measData");
			if (vn.toElement(VTDNav.FC, "measInfo")) {
				do {
					vn.toElement(VTDNav.FC, "measType");
					do {
						if (contador.equals(vn.toNormalizedString(vn.getText()))) {
							measTypeSTR = vn.toString(vn.getAttrVal("p"));							
						}
					} while (vn.toElement(VTDNav.NS, "measType"));
					vn.toElement(VTDNav.P);
					vn.toElement(VTDNav.FC, "measValue");
					vn.toElement(VTDNav.FC, "r");
					do {
						if (vn.toString(vn.getAttrVal("p")).equals(measTypeSTR)) {							
							//print counter value
							//if(ct==0){
							arL.add(vn.toNormalizedString(vn.getText()));							
								//System.out.println(vn.toNormalizedString(vn.getText()));
							//}
						//	ct=ct+1;
							measTypeSTR = "";
								
						}
				} while (vn.toElement(VTDNav.NS, "r"));					
					vn.toElement(VTDNav.P);
					vn.toElement(VTDNav.P);
				
				} while (vn.toElement(VTDNav.NS, "measInfo"));

			} // measInfo
			vn.toElement(VTDNav.P);
			vn.toElement(VTDNav.P);
		} // measCollecFile - root
		else {
			System.out.println(" Root is not 'measData' ");
		}
	
		return arL;
		
	}
	
	@SuppressWarnings("rawtypes")
	public static void printMap(TreeMap mapa){
		Set set = mapa.entrySet();
        Iterator iter = set.iterator();        
        while (iter.hasNext()) {           
            Map.Entry me = (Map.Entry)iter.next();
            System.out.println("KEY: "+me.getKey()+" | "+"VALUE: "+me.getValue());
        }      
	}
	/**
	 * @param vn
	 * @throws NavException
	 */
	public static void getValuesP(VTDNav vn) throws NavException {
		if (vn.matchElement("measCollecFile")) { // match
			vn.toElement(VTDNav.FC, "measData");
			if (vn.toElement(VTDNav.FC, "measInfo")) {
				do {
					if (vn.toElement(VTDNav.FC, "measType")) {
						attrMeasType = vn.toString(vn.getAttrVal("p"));
					} // measType
					vn.toElement(VTDNav.P);
					if (vn.toElement(VTDNav.FC, "measValue")) {
						vn.toElement(VTDNav.FC, "r");
						if (attrMeasType.equals(vn.toString(vn.getAttrVal("p")))) {
							arrayValue.add(vn.toNormalizedString(vn.getText()));
							vn.toElement(VTDNav.P);
							vn.toElement(VTDNav.P);
						}
					}
				} while (vn.toElement(VTDNav.NS, "measInfo"));

			} // measInfo
		} else {
			System.out.println(" Root is not 'measData' ");
		}
	}// getValuesP

	public static Properties getValuePropertie(String pathProperties) {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(pathProperties);

			// load a properties file
			prop.load(input);
			return prop;
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
		return prop;
	}// getValuePropertie
	/*
	 * create csv header
	 */
	  public static  void createHeaderCsv(String fileName,String pathPropertie) throws IOException{
	       FileWriter fileWriter=new FileWriter(fileName,true);
	       try {        
	        fileWriter.append(""+headerCsv(pathPropertie));
	        fileWriter.append(""+NEW_LINE_SEPARATOR);
	       } catch (IOException e) {
	       } finally{
	           try {
	               fileWriter.close();
	           } catch (Exception e) {
	           }
	       }      
	   }
	  /*
	   * @return header 
	   */
	  public static String headerCsv(String pathPropertie){
	         String header="";
	        TreeMap<String,String> propertieMap=loadPropertieMap(pathPropertie);
	        Set set = propertieMap.entrySet();
	        Iterator iter = set.iterator();        
	        while (iter.hasNext()) {           
	            Map.Entry me = (Map.Entry)iter.next();
	            header+=me.getValue()+COMMA_DELIMITER;
	        }      
	         if(header.endsWith(COMMA_DELIMITER)){
	                 header = header.substring(0,header.length() - 1);
	         }
	         header=header.replaceAll("\\s+","");
	       return header;
	    }
	  /*
	   * @return map counter
	   */
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
}
