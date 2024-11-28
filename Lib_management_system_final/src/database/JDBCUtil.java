package database;

import  java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
    public static Connection getConnection() {
        Connection c = null;
        try {

            com.mysql.jdbc.Driver driver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(driver);
            String url = "jdbc:mysql://localhost:3306/lib";
            String username = "root";
            String password = "122005";
            c=DriverManager.getConnection(url, username, password);

        } catch (SQLException e){
            e.printStackTrace();
        }
        return c;
    }

    public static void closeConnection(Connection c) {
        try {
            if(c != null){
                c.close();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void  prinrinfo(Connection c) {
        try {
            if(c != null){
                DatabaseMetaData dbmd = c.getMetaData();
                System.out.println(dbmd.getDatabaseProductName());
                System.out.println(dbmd.getDatabaseProductVersion());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}