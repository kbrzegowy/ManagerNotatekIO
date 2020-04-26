package Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final String DBHost = "localhost";
    private final String DBName = "managernotatek";
    private final String DBUser = "root";
    private final String DBPass = "";
    private Connection connection = null;

    public Database(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://"+DBHost+":3306/"+DBName,DBUser,DBPass);
        }catch (SQLException e){
            e.printStackTrace();
        }
    };

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        try {
            if(connection!=null)
                connection.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}