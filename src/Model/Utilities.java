/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import Connection.Connect;
import Connection.Connect;

/**
 *
 * @author user
 */
public class Utilities {
    public static Map<String, String> getColumnTable(String tableName) throws Exception {
        String request = "SELECT * FROM " + tableName;
        
        Connection con = Connect.dbConnect("postgresql");

        Map<String, String> columnMap = new HashMap<>();

        try {
            PreparedStatement pstmt = con.prepareStatement(request);
            ResultSetMetaData meta = pstmt.getMetaData();

            for (int i = 1; i <= meta.getColumnCount(); i++) {
                String columnName = meta.getColumnName(i);
                String columnType = meta.getColumnTypeName(i);
                columnMap.put(columnName, columnType);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources (Connection, PreparedStatement, etc.) here if necessary
        }

        return columnMap;
    }
    
    public static HashMap<String, Object> datatemplate(String tableName, String packageName) throws Exception {
        Map<String, String> columnMap = Utilities.getColumnTable(tableName);
        Map<String, String> javaDataType = new HashMap<>();
        Map<String, Object> template = new HashMap<>();

        for (Map.Entry<String, String> entry : columnMap.entrySet()) {
            String columnName = entry.getKey();
            String postgresType = entry.getValue();
            String javaType = mapPostgresToJava(postgresType);
            javaDataType.put(columnName, javaType);
        }
        template.put("classe", tableName);
        template.put("package", packageName);
        template.put("attribut", javaDataType);
        return (HashMap<String, Object>)template;
    }

    private static String mapPostgresToJava(String postgresType) {
        switch (postgresType) {
            case "integer":
            case "numeric":
            case "serial":
            case "int4":
                return Integer.class.getName();
            case "varchar":
                return String.class.getName();
            case "time":
                return Time.class.getName();
            case "bool":
                return Boolean.class.getName();
            case "date":
                return Date.class.getName();
            case "timestamp":
                return Timestamp.class.getName();
            default:
                return Object.class.getName(); // Default to Object if the type is not recognized
        }
    }

}
