package db;
import java.sql.Connection;
import java.sql.DriverManager;

public class databaseutility {
    public static void main(String[] args) {
        Connection connection = null;
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/railway","postgres","21122004");
            if(connection!=null)
                System.out.println("Connection Established");
            else
                System.out.println("Connection Not Established");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}