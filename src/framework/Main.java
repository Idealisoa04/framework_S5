package framework;


import Model.Utilities;
import java.util.HashMap;
import java.util.Map;
import Model.Generation;
import Model.Generation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Map<String, String> columnMap = Utilities.getColumnTable("personnes");

        /*for (Map.Entry<String, String> entry : columnMap.entrySet()) {
            System.out.println("Colonne : " + entry.getKey() + ", Type: " + entry.getValue());
        }*/
        System.out.println("----------------------------------------------------------------------");
        HashMap<String, Object> javaType = Utilities.datatemplate("personnes","model");
        
        Generation gen = new Generation();
        gen.createClass("src/newclasses/",javaType, "C#");
        /*for (Map.Entry<String, Object> entry : javaType.entrySet()) {
            String columnName = (String) entry.getKey();
            System.out.println("TYPE: " + columnName);
            Class classed = entry.getValue().getClass();
            System.out.println(classed);
            if(classed.toString().equals("class java.util.HashMap")){
                HashMap<String,String> hash = (HashMap<String,String>)entry.getValue();
                    for (Map.Entry<String, String> entries : hash.entrySet()) {
                        System.out.println(entries.getKey() + " : " + entries.getValue());
                    }
                }
            else{
                System.out.println(entry.getValue());
            }
            System.out.println("---------------------------------------------------------------------------");
    }*/
    }
    
}
