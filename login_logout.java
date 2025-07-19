
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class login_logout extends DBConnection{
    private String user_id;
    private String username, password;

    public HashMap<String, String> u = new HashMap<>();


    public login_logout() {
    }

    public login_logout(String id, String n, String p) {
        this.user_id = id;
        this.username = n;
        this.password = p;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean userLogin(String id, String name, String pw) throws SQLException {
        boolean flag = true;
        Connection con = super.connect();
        PreparedStatement ps = con.prepareCall("SELECT user_name,user_password FROM userlogin");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (name.equals(rs.getString(1)) && pw.equals(rs.getString(2))) {
                System.out.println("Your account is already signed");
                flag = false;
                break;
            }
        }

        if (flag) {
            CallableStatement cs = con.prepareCall("{CALL user_login(?,?,?)}");
            cs.setString(1, id);
            cs.setString(2, name);
            cs.setString(3, pw);
            cs.execute();

            ps.close();
            rs.close();
            con.close();
            cs.close();
            System.out.println("Successfully Login");
            return flag;
        } else {
            System.out.println("Try Again");
            return flag;
        }

    }

    public String generateId() throws SQLException {
        Connection con = super.connect();
        Statement st;
        ResultSet rs;
        String newid = " ";
        st = con.createStatement();
        rs = st.executeQuery("SELECT user_id,user_name FROM userlogin ORDER BY user_id DESC LIMIT 1;");
        if (rs.next()) {
            String lastid = rs.getString(1);
            int num = Integer.parseInt(lastid.substring(1));
            num++;
            newid = String.format("u%02d", num);
        } else {
            newid = "u01";
        }

        rs.close();
        st.close();
        return newid;
    }

    public boolean userOldLogin(String name, String pw) throws SQLException {
        Connection con = super.connect();
        CallableStatement cs = con.prepareCall("{CALL search_login(?,?)}");
        cs.setString(1, name);
        cs.setString(2, pw);
        ResultSet rs = cs.executeQuery();

        boolean result = false;

        if (rs.next()) {
            result = rs.getBoolean(1);
        }

        rs.close();
        con.close();
        cs.close();

        return result;
    }

    public void userLogout(String name, String password) throws SQLException {
        Connection con = super.connect();
       
        String id = findId(name, password);
        CallableStatement cs = con.prepareCall("{CALL user_logout(?)}");
        cs.setString(1, id);
        cs.execute();
        //System.out.println("Thank you for coming");
        
        con.close();
        cs.close();

    }

    public String findId(String n,String pw) throws SQLException{
        Connection con = super.connect();
        PreparedStatement ps = con.prepareCall("SELECT user_id FROM userlogin WHERE user_name = ? AND user_password = ?");
        ps.setString(1, n);
        ps.setString(2, pw);
        ResultSet rs = ps.executeQuery();
        String id = " ";
        if (rs.next()) {
            id = rs.getString(1);
        }
        ps.close();
        rs.close();
        con.close();
        return id;
    }

    public String view_rate(String uid) throws SQLException{
        String result ="";
        Connection con = super.connect();
       
        CallableStatement cs = con.prepareCall("{CALL view_record(?)}");
        cs.setString(1, uid);
        ResultSet rs = cs.executeQuery();
        if(rs.next()){
            result = rs.getString(1);
        }
        result+="\n[The games You played are ] \n[";
        cs.close();

        PreparedStatement ps = con.prepareCall("SELECT game_name FROM gameinfo,score \n" +
                        "WHERE score.game_id = gameinfo.game_id\n" +
                        "AND score.user_id = ?;");
        ps.setString(1, uid);
        rs = ps.executeQuery();
        while(rs.next()){
            result += (rs.getString(1)+" , ");
        }
        result +="]";

        return result;
    }
}
