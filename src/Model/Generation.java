/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static com.sun.xml.internal.ws.model.RuntimeModeler.capitalize;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Idealisoa
 */
public class Generation {
    
    public String[] getAttributeType(HashMap<String,Object> data){
        HashMap<String, String> attribute = (HashMap<String, String>) data.get("attribut");
        ArrayList<String> libraryList = new ArrayList<>(attribute.values());
        String[] library = libraryList.toArray(new String[libraryList.size()]);
        return library;
    }
    
    public String[] getAttributeName(HashMap<String,Object> data){
        HashMap<String, String> attribute = (HashMap<String, String>) data.get("attribut");
        ArrayList<String> keysList = new ArrayList<>(attribute.keySet());
        String[] keysArray = keysList.toArray(new String[keysList.size()]);
        return keysArray;
    }
    
    public String[] getAttributeTypeSimple(String[] attr_type){
        String[] rep = new String[attr_type.length];
        for(int i=0 ; i<attr_type.length ; i++){
            String[] temp = attr_type[i].split("\\.");
            if(temp.length > 1) {
                rep[i] = temp[temp.length-1];
            } else {
                rep[i] = null;
            }
        }
        return rep;
    }
    
    public void createClass(String path, HashMap<String, Object> data, String type) throws IOException {
        if(type.equalsIgnoreCase("java")){
            this.createJavaClass(path, data);
        }
        if(type.equalsIgnoreCase("C#")){
            this.createCSharpClass(path, data);
        }
    }
    
    public void createJavaClass(String path,HashMap<String,Object> data) throws IOException{
        String[] attr_type = this.getAttributeType(data);
        String[] attr_name = this.getAttributeName(data);
        String[] attr_type_simple = this.getAttributeTypeSimple(attr_type);
        String packageName = (String) data.get("package");
        String className = capitalize((String) data.get("classe"));
        
        //package
        StringBuilder classTemplate = new StringBuilder("package #PACKAGE_NAME#;\n\n");
        
        //import
        for (int i = 0 ; i<attr_type_simple.length ; i++) {
            if(attr_type_simple[i] != null){
                classTemplate.append("import  ").append(attr_type[i]).append( "; \n\n");
            }
        }
        
        //class
        classTemplate.append("public class #CLASS_NAME# {\n");
        
        //declaration attribut
        for(int i = 0 ; i<attr_type.length ; i++) {
            if(attr_type_simple[i] != null){
                classTemplate.append("      ").append(attr_type_simple[i]).append("  ").append(attr_name[i]).append("; \n");
            } else {
                classTemplate.append("      ").append(attr_type[i]).append("  ").append(attr_name[i]).append("; \n");
            }
        }
        
        classTemplate.append("\n");
        
        //constructeur par defaut
        classTemplate.append("    public ").append(className).append("() {\n" +
                "       \n" +
                "    }\n\n");
        
        //setters et getters
        for(int i = 0 ; i<attr_type.length ; i++) {
            if(attr_type_simple[i] != null){
                classTemplate.append("    public ").append(attr_type_simple[i]).append(" get").append(capitalize(attr_name[i])).append("() {\n" +
                            "        return ").append(attr_name[i]).append(";\n" +
                            "    }\n\n");

                classTemplate.append("    public void set").append(capitalize(attr_name[i])).append("(").append(attr_type_simple[i]).append(" ").append(attr_name[i]).append(") {\n" +
                            "        this.").append(attr_name[i]).append(" = ").append(attr_name[i]).append(";\n" +
                            "    }\n\n");
            } else {
                classTemplate.append("    public ").append(attr_type[i]).append(" get").append(capitalize(attr_name[i])).append("() {\n" +
                            "        return ").append(attr_name[i]).append(";\n" +
                            "    }\n\n");

                classTemplate.append("    public void set").append(capitalize(attr_name[i])).append("(").append(attr_type[i]).append(" ").append(attr_name[i]).append(") {\n" +
                            "        this.").append(attr_name[i]).append(" = ").append(attr_name[i]).append(";\n" +
                            "    }\n\n");
            }   
        }
        
        //fermeture
        classTemplate.append("}\n");
        
        //replace
        String generatedClass = classTemplate.toString()
                .replace("#PACKAGE_NAME#", packageName)
                .replace("#CLASS_NAME#", className);

        // Écrire le résultat dans un nouveau fichier
        
        this.creationFile(path, className, "java", generatedClass);
        
    }
    
    public void createCSharpClass (String path,HashMap<String,Object> data) throws IOException{
        //setData
        String[] attr_type = this.getAttributeType(data);
        String[] attr_name = this.getAttributeName(data);
        String[] attr_type_simple = this.getAttributeTypeSimple(attr_type);
        String packageName = (String) data.get("package");
        String className = capitalize((String) data.get("classe"));
        
        
        StringBuilder classTemplate = new StringBuilder("");
        
        //import
        for (int i = 0 ; i<attr_type_simple.length ; i++) {
            if(attr_type_simple[i] != null){
                classTemplate.append("using  ").append(attr_type[i]).append( "; \n\n");
            }
        }
        
        //package
        classTemplate.append("namespace #PACKAGE_NAME# {\n\n");
        
        //class
        classTemplate.append("public class #CLASS_NAME# {\n");
        
        //declaration attribut avec getters et setters
        for(int i = 0 ; i<attr_type.length ; i++) {
            if(attr_type_simple[i] != null){
                classTemplate.append("      public ").append(attr_type_simple[i]).append("  ").append(attr_name[i]).append(" { get; set; } \n");
            } else {
                classTemplate.append("      public ").append(attr_type[i]).append("  ").append(attr_name[i]).append(" { get; set; } \n");
            }
        }
        
        classTemplate.append("\n");
        
        //constructeur par defaut
        classTemplate.append("    public ").append(className).append("() {\n" +
                "       \n" +
                "    }\n\n");
        
        //fermeture
        classTemplate.append("  }\n}\n");
        
        //replace
        String generatedClass = classTemplate.toString()
                .replace("#PACKAGE_NAME#", packageName)
                .replace("#CLASS_NAME#", className);

        // Écrire le résultat dans un nouveau fichier
        try{
            this.creationFile(path, className, "cs", generatedClass);
        }catch(Exception e){
            throw e;
        }
        
        
    }

    public void verifPath(String path) throws IOException{
        Path directory = Paths.get(path);
        if (!Files.exists(directory) && !Files.isDirectory(directory)) {
            Files.createDirectories(directory);
        }
    }
    
    public void creationFile(String path,String className,String ext,String generatedClass) throws IOException{
        this.verifPath(path);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path+className+"."+ext))) {
            writer.write(generatedClass);
            System.out.println("Classe générée avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
