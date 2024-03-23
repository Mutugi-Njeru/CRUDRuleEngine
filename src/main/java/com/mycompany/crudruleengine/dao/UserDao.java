/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.dao;

import com.mycompany.crudruleengine.database.MysqlConnector;
import com.mycompany.crudruleengine.models.User;
import com.mycompany.crudruleengine.utility.Constants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class UserDao {
    
    @Inject
    MysqlConnector mysqlConnector;
    
    
    private static final Logger logger=LogManager.getLogger(UserDao.class);
    private static final String ERROR_LOG_TEMPLATE="{}=> {} | {}";
    
    public int createUser(User user){
        //String password=sha256Hasher.createHashText(user.getPassword());
        String query="INSERT IGNORE INTO users (first_name, last_name, email, password, id_number, address, phone_number) VALUES (?,?,?,?,?,?,?)";
        int userId = 0;
        
        
        try(Connection connection=mysqlConnector.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getIdNumber());
            preparedStatement.setString(6, user.getAddress());
            preparedStatement.setString(7, user.getPhoneNumber());
            preparedStatement.executeUpdate();
            
            ResultSet resultSet=preparedStatement.getGeneratedKeys();
            while(resultSet.next()){
                userId=resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return userId;
        }
        return userId;   
    }
    
    
    public boolean updateUserAddress(String address, int user_id){
        boolean status=false;
        String query="UPDATE users SET address=? WHERE user_id=?";
        
        try(Connection connection=mysqlConnector.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setString(1, address);
            preparedStatement.setInt(2, user_id);
            status=preparedStatement.executeUpdate()>0;
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return status;        
        }
        return status;
    }
    
    public boolean updateUserPassword(String password, int userId){
        boolean status=false;
        
        String query="UPDATE users SET password=? WHERE user_id=?";
        
        try(Connection connection=mysqlConnector.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query);) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, userId);
            
            status=preparedStatement.executeUpdate()>0;
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return status;
        }
        return status;   
    }
    
    //get user by id
    public JSONObject getUserById(int userId){
        JSONObject object=new JSONObject(); 
        String query="SELECT first_name, last_name, email, id_number, address, phone_number FROM users WHERE user_id=?";
       
        try(Connection connection=mysqlConnector.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, userId);
            ResultSet resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){
                object.put("firstName", resultSet.getString(1))
                        .put("lastName", resultSet.getString(2))
                        .put("email", resultSet.getString(3))
                        .put("idNumber", resultSet.getString(4))
                        .put("address", resultSet.getString(5))
                        .put("phoneNumber", resultSet.getString(6));
            }
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return object;
        }
        return object;       
    }
    
    public JSONObject getUsers(){
        JSONArray result=new JSONArray();
        String query="SELECT user_id, first_name, last_name, email, id_number, address, phone_number FROM users";
        
        try(Connection connection=mysqlConnector.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            ResultSet resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){
                result.put(new JSONObject().put("userId", resultSet.getInt(1))
                        .put("firstName", resultSet.getString(2))
                        .put("lastName", resultSet.getString(3))
                        .put("email", resultSet.getString(4))
                        .put("idNumber", resultSet.getString(5))
                        .put("address", resultSet.getString(6))
                        .put("phoneNumber", resultSet.getString(7))                       
                );
            }
            
        } 
        catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return new JSONObject().put("users", result);
        }
        return new JSONObject().put("users", result);
    }
    //delete user
    public boolean deleteUser(int userId){
        String query="DELETE FROM users WHERE user_id=?";
        boolean status=false;
        
        try(Connection connection=mysqlConnector.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            status=preparedStatement.executeUpdate()>0;
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return status;
        }
        return status;        
    }
   
    
    
}
