/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package find_counter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author mchamparini
 */
public class Find_Counter {
    private static List<File> filesInFolder;
    private static int cantidad_contador;
    //private static String nombre_contador="NumUserBits_PS64DL";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        filesInFolder = Files.walk(Paths.get(args[0]))//lista de archivos a procesar
                                .filter(Files::isRegularFile)
                                .map(Path::toFile)
                                .collect(Collectors.toList());  
        for (int i = 0; i < filesInFolder.size(); i++) {
            //System.out.println("Iterando: "+i);
            getCounter(filesInFolder.get(i).toString(),args[1]);
        }
        System.out.println("cantidad encontrada de "+args[1]+" : "+cantidad_contador);
    }
    public static void getCounter(String fileXML,String nombre_contador){
        boolean flag = false;        
        try {
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventR = factory.createXMLEventReader(new FileReader(fileXML));
            while(eventR.hasNext()){
                XMLEvent event = eventR.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        StartElement startElement = event.asStartElement();
                        String nodo = startElement.getName().getLocalPart();
                        if(nodo.equalsIgnoreCase("measType")){                            
                            flag = true;
                        }                            
                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        if(flag){                                                        
                            if(characters.getData().contains(nombre_contador)){                                
                                cantidad_contador++;
                            }
                            flag = false;
                        }                    
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
        }        
    }
}
