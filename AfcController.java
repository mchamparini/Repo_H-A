package com.afc;

import com.ximpleware.AutoPilot;
import com.ximpleware.NavException;
import com.ximpleware.VTDNav;
import com.ximpleware.XPathEvalException;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by mchamparini on 20/04/2017.
 */
public class AfcController {
    private static  String NEW_LINE_SEPARATOR = "/n";
    private static String COMMA_DELIMITER = ",";
    private static TreeMap<String, String> map_head = new TreeMap<>();

    public static void writeCSV(FileWriter fileWriter, TreeMap csvMap) throws IOException {
        Set set = csvMap.entrySet();
        int counter = 0;
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry me = (Map.Entry) iter.next();
            counter++;
            if (!(counter < csvMap.size())) {
                fileWriter.append("" + me.getValue().toString());
                fileWriter.append("" + NEW_LINE_SEPARATOR);
            } else {
                fileWriter.append("" + me.getValue().toString());
                fileWriter.append("" + COMMA_DELIMITER);
            }
        }
    }

    public static Properties loadProperties(String pathProperties) {
        FileInputStream fis;
        Properties prope = new Properties();
        try {
            fis = new FileInputStream(pathProperties);

            prope.load(fis);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return prope;
		/*
		 * for (Enumeration e = prope.keys(); e.hasMoreElements();) { Object obj
		 * = e.nextElement(); return prope.getProperty(obj.toString());
		 * //System.out.println((String) obj + " : " +
		 * prope.getProperty(obj.toString())); }
		 */
    }

    public static void createHeaderCsv(String fileName, ArrayList<String> arrayHead) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        try {
            fileWriter.append("" + headerCsv(arrayHead));
            System.out.print("createHeaderCSv");
            fileWriter.append("" + NEW_LINE_SEPARATOR);
        } catch (IOException e) {
        } finally {
            try {
                fileWriter.close();
            } catch (Exception e) {
            }
        }
    }

    public static String headerCsv(ArrayList<String> arrayHead) {


    int cantidad  = 0;
        System.out.println("Cantidad de contadores= "+arrayHead.size());
        for (int i = 0; i < arrayHead.size(); i++) {
            cantidad++;
            try{
                System.out.println(cantidad);
                System.out.println(arrayHead.get(i).toUpperCase());
                map_head.put(arrayHead.get(i).toUpperCase(), "a");
            }catch(Exception e){
                System.out.println("Message="+e.getMessage()+" : Stack="+e.toString());
            }
        }
       // map_head.put("FEMTO", "a");
        //map_head.put("FECHA", "a");
        String header = "";
        Set set = map_head.entrySet();
        Iterator iter = set.iterator();
        while (iter.hasNext()) {
            Map.Entry me = (Map.Entry) iter.next();
            header += me.getKey() + COMMA_DELIMITER;
        }
        if (header.endsWith(COMMA_DELIMITER)) {
            header = header.substring(0, header.length() - 1);
        }
        header = header.replaceAll("\\s+", "");
        return header;
    }

    public static void process(VTDNav vn, AutoPilot ap, String pathProperties) {
        try {
            int cont = 0;
            Properties prope = loadProperties(pathProperties);
            int result = 0;
            ap.bind(vn);
            while ((result = ap.evalXPath()) != -1) {
                vn.push();
                cont++;
                System.out.println(vn.toString(vn.getCurrentIndex()) + ":" + vn.toNormalizedString(vn.getAttrVal("id"))
                        + " Nro = " + cont);
                System.out.println("-----------------------------------------------------------");
                vn.toElement(VTDNav.FC, "Femto");
                do {
                    System.out.println(vn.toString(vn.getCurrentIndex()) + ":"
                            + vn.toNormalizedString(vn.getAttrVal("id")));
                    System.out.println("-----------------------------------------------------------");
                    vn.toElement(VTDNav.FC, "attributes");
                    for (Enumeration e = prope.keys(); e.hasMoreElements();) {
                        vn.toElement(VTDNav.FC, "attributes");
                        Object obj = e.nextElement();
                        if (vn.toElement(VTDNav.FC, prope.getProperty(obj.toString()))) {
                            if (vn.toElement(VTDNav.FC, "unset")) {
                                vn.toElement(VTDNav.P, prope.getProperty(obj.toString()));
                                System.out.println(vn.toString(vn.getCurrentIndex()) + ": unset");
                            }else {
                                System.out.println(vn.toString(vn.getCurrentIndex()) + " : "
                                        + vn.toNormalizedString(vn.getText()));
                            }
                        }
                        vn.toElement(VTDNav.P, "attributes");
                    }
                    vn.toElement(VTDNav.P, "Femto");
                }while (vn.toElement(VTDNav.NS, "Femto"));
                vn.pop();
                System.out.println("-----------------------------------------------------------");
            }
        } catch (NavException | XPathEvalException e) {
            System.out.println(e.getMessage());
        }
    }
}
