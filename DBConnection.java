
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    protected String url = "jdbc:mysql://localhost:3306/gamelogin";
    protected String user = "root";
    protected String password = "1111252792005aha";

    protected  Connection con;
    protected  Statement stmt;
    protected  ResultSet rs;
    
    public Connection connect(){
        try{

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url,user,password);
            // System.out.println("Connected successfully");

            return con;
        }catch(ClassNotFoundException |SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    // public void executeUpdate(String sql) throws SQLException{
    //     stmt = con.createStatement();
    //     stmt.executeUpdate(sql);
    //     System.out.println("Sucessfully done");
    // }

    public void disconnect(){

        try {
            if(con!=null && !con.isClosed()){
                con.close();
            System.out.println("Disconnected Successfully");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

