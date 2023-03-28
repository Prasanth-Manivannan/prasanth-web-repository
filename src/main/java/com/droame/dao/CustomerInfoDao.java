package com.droame.dao;

import com.droame.signup.SignupDetails;

import java.sql.*;

public class CustomerInfoDao {

    /**
     * To get connection from droame Database using postgres driver
     * @return
     * @throws Exception
     */
    private static Connection getConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/droame", "postgres", "postgres");
    }

    /**
     * To check is user exist
     * @param userId
     * @param password
     * @return boolean
     */
    public boolean toCheckLogin(String userId, String password)  {
        String schemaName="";
        String select_sql = "SELECT * FROM "+schemaName+"user_data WHERE username='"+userId+"' AND password='"+password+"';";
        try {
            Connection connect = getConnection();
            Statement pst = connect.createStatement();
            System.out.println("entered into tocheck");
            ResultSet rs = pst.executeQuery(select_sql);
            if (rs.next()) {
                if (rs.getString(2).equals(password)) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println("Exception in check validation");
        }
        return false;
    }

    /**
     * To add new username
     * @param newUser
     * @return
     * @throws
     */
    public boolean toRegisterNewUser( SignupDetails newUser) {
        if(this.toCheckLogin(newUser.getUserId(), newUser.getPassword())) {
            return false;
        }
        String schemaName="metaDB";
        String insert_query="INSERT INTO "+schemaName+".user_data (username,password) VALUES (?,?);";

        try {
            Connection connect=getConnection();
            PreparedStatement prepstate=connect.prepareStatement(insert_query);
            prepstate.setString(1, newUser.getUserId());
            prepstate.setString(2, newUser.getPassword());
            prepstate.executeUpdate();
        }catch(Exception e) {
            System.out.println("Exception");
        }

        return true;
    }
}
