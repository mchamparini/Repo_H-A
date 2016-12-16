/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getfilefromdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author mchamparini
 */
public class GetFileFromDB {

    /**
     * @param args the command line arguments
     */
    private static ArrayList<String> lst=new ArrayList<String>();
    public static void main(String[] args) {
        // TODO code application logic here
       Connection conn=null;
       ResultSet rs = null;
       Statement statement=null;
       String url = "jdbc:oracle:thin:@falda.claro.amx:1521:dsmart2";
       String user = "MCHAMPARINI";
       String pass = "Hj039j09";
       String sql = "SELECT NOMBRE_CSV FROM FILES WHERE STATUS = 0";       
        try {
             Class.forName("oracle.jdbc.driver.OracleDriver");
             conn = DriverManager.getConnection(url,user,pass);
             statement = conn.createStatement();
             rs =  statement.executeQuery(sql);
             while (rs.next()){
                 lst.add(rs.getString("NOMBRE_CSV"));                 
            }
        } catch (Exception e) {            
            System.out.println(""+e.getMessage());
        }finally{
                if (rs != null) {
                        try {
                            rs.close();
                        } catch (SQLException e) { /* ignored */}
                    }
                if (statement != null) {
                        try {
                            statement.close();
                        } catch (SQLException e) { /* ignored */}
                 }
                if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) { /* ignored */}
                }
            }    
       
        for (int i = 0; i < lst.size(); i++) {
                    System.out.println(lst.get(i));

        }
    }
}
