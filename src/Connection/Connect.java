/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import Connection.Connect;

/**
 *
 * @author Idealisoa
 */
public class Connect {
    
    public static Connection dbConnect(String type,String port,String pilote,String deploy,String user, String mdp, String nameDatabase) throws Exception{
        String jdbcURL = "jdbc:"+type+"://"+deploy+":"+port+"/"+nameDatabase;
        Connection temp = null;
        try {      

            Class.forName(pilote);
            temp = DriverManager.getConnection(jdbcURL, user, mdp);
            
            temp.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Connection failed"+e.getLocalizedMessage());
        }
        return temp;
    }
    
    public static Connection dbConnect(String type) throws Exception{
        String chemin = "src/folder/config.xml";
        Connection rep = null;
        try {
            File inputFile = new File(chemin);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();


            NodeList nodeList = doc.getElementsByTagName("connect");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String con = element.getAttribute("type");
                    if(con.equalsIgnoreCase(type)){
                        String port = element.getElementsByTagName("port").item(0).getTextContent();
                        String pilote = element.getElementsByTagName("pilote").item(0).getTextContent();
                        String deploy = element.getElementsByTagName("deploy").item(0).getTextContent();
                        String user = element.getElementsByTagName("username").item(0).getTextContent();
                        String mdp = element.getElementsByTagName("mdp").item(0).getTextContent();
                        String nameDatabase = element.getElementsByTagName("databasename").item(0).getTextContent();
                          
                        rep = Connect.dbConnect(type,port,pilote,deploy,user,mdp,nameDatabase);
                        
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rep;
    }
}
