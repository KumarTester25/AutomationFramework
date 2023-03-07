//package main.java.pageEvents;
//
//import org.testng.annotations.Test;
//
//import java.sql.*;
//import java.util.*;
//import java.lang.String;
//
//public class DbConnection {
//
//    @Test
//    public static void main(String[] args) {
//        HashMap<String, String> result = new HashMap<>();
//        try{
//            //Metioning the what type of DB.
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            //
//            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/sys","root","Test123#");
//            Statement stmt=con.createStatement();
//            ResultSet rs=stmt.executeQuery("SELECT * FROM automationdata.orangehrm;");
//            ResultSetMetaData resultSetMetaData = rs.getMetaData();
//
//            int columncount = resultSetMetaData.getColumnCount();
//            List<String> ColumnNames = new LinkedList<>();
//            // 2
//            List<HashMap<String, Object>> finalTestDataFromDB = new ArrayList();
//            HashMap<String, Object> resultSetFromDB = null;
//            while (rs.next()) {
//                resultSetFromDB = new HashMap();
//                for (int i = 1; i <= columncount; i++) {
//                    ColumnNames.add(resultSetMetaData.getColumnName(i));
//                    resultSetFromDB.put(resultSetMetaData.getColumnName(i),rs.getObject(i) );
//                }
//                finalTestDataFromDB.add(resultSetFromDB);
//            }
//            System.out.println(finalTestDataFromDB);
//        }catch(Exception e){System.out.println(e);}
//    }
//}

package main.java.pageEvents;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public class DbConnection {

    public String readJsonFile(String QueryName){
        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir")+"\\src\\main\\java\\utils\\DBQuery.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String Value = (String) jsonObject.get(QueryName);
            return Value;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<LinkedHashMap<String, Object>> getDbData(String QueryName) {
        HashMap<String, String> result = new HashMap<>();
        try {
            //Metioning the what type of DB.
            String value = this.readJsonFile(QueryName);
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sys", "root", "Test123#");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(value);
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columncount = resultSetMetaData.getColumnCount();
            // 2
            List<LinkedHashMap<String, Object>> finalTestDataFromDB = new ArrayList<>();

            while (rs.next()) {
                LinkedHashMap<String, Object> resultSetFromDB = new LinkedHashMap();
                for (int i = 1; i <= columncount; i++) {
                    resultSetFromDB.put(resultSetMetaData.getColumnName(i), rs.getObject(i));
                }
                finalTestDataFromDB.add(resultSetFromDB);
            }
            System.out.println(finalTestDataFromDB);
            return finalTestDataFromDB;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
    public LinkedHashMap<String, String> getdata (String tableName, int Rowcount){
        List<LinkedHashMap<String, Object>> result = this.getDbData(tableName);
        LinkedHashMap<String, Object> set = result.get(Rowcount-1);
        LinkedHashMap<String, String> newMap = new LinkedHashMap<>();
        set.forEach((key, value) -> newMap.put(key, value.toString()));
        System.out.println(newMap);
        return newMap;
    }
}

