/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package framework;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import Connection.Connect;
import Model.Generation;

/**
 *
 * @author Idealisoa
 */
public class Framework {

    public static void main(String[] args) {
        // Exemple d'utilisation avec un HashMap d'attributs
        HashMap<String, String> attributes = new HashMap<>();
        attributes.put("attribute1", "java.lang.String");
        attributes.put("attribute2", "int");
        attributes.put("attribute3", "double");
        
        HashMap<String,Object> data = new HashMap<>();
        data.put("class", "Exemple");
        data.put("package", "exemple");
        data.put("attribut",attributes);

        Generation gen = new Generation();
         
      
        gen.createClass("src/folder/", data, "C#");
    }
    
}
