package controller.login;

import db.DBConnection;
import model.Active_Tasks;
import model.Login;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {
    private static LoginController loginController;

    private  LoginController(){

    }

    public static LoginController getInstance(){
        if(loginController==null){
            loginController=new LoginController();
        }
        return loginController;
    }

    public boolean getAllLogin(String username,String password) {
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT * FROM login WHERE username = ? AND password = ?");
            stm.setObject(1,username);
            stm.setObject(2,password);
            ResultSet res = stm.executeQuery();
            if(res.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean addLogin(Login login){
        try {
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Insert into login values (?,?)");
            stm.setObject(1,login.getUsername());
            stm.setObject(2,login.getPassword());
            return  stm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
