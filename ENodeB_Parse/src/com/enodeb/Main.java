package com.enodeb;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
/**
 * Clase principal donde se asigna una cuota de tarea a los hilos, y crearlos.
 * @author Martin Champarini
 */
public class Main {      
    public static List<File> filesInFolder;
    public static String pathPropertieFile;
    private static ExecutorService executor;
    private static String pathCSV = "C:\\Users\\mchamparini\\Documents\\EnodeB\\Csv\\";
    
    public static void main(String[] args) throws IOException, InterruptedException{
          pathPropertieFile=args[0];                   
          filesInFolder = Files.walk(Paths.get(args[1]))//lista de archivos a procesar
                                .filter(Files::isRegularFile)
                                .map(Path::toFile)
                                .collect(Collectors.toList());
          executor = Executors.newFixedThreadPool(3);
          BuildMaps.createHeaderCsv(pathCSV+"file.csv");
          executor.execute(() -> {
              for (int i = 0; i < filesInFolder.size(); i++) {
                  try {
                      BuildMaps.process(filesInFolder.get(i).toString(), args[2]+"enodebAll.csv"); 
                      
                  } catch (IOException e) {
                      
                  }
              }
          });   
        executor.shutdown();    
    }     
}   


