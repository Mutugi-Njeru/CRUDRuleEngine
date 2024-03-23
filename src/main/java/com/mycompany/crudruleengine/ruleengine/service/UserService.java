/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.service;

import com.mycompany.crudruleengine.dao.UserDao;
import com.mycompany.crudruleengine.models.User;
import com.mycompany.crudruleengine.records.ServiceResponder;
import com.mycompany.crudruleengine.utility.Constants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class UserService {
    @Inject
    UserDao userDao;
    
    public ServiceResponder createUser(User user){
        int userId=userDao.createUser(user);
        return(userId>0)
                ?new ServiceResponder(true, "user created successfully")
                :new ServiceResponder(false, "unable to create user");  
    }  
    
    public ServiceResponder updateUserAddress(JSONObject object){
        String address=object.optString(Constants.ADDRESS_KEY, Constants.EMPTY_STRING);
        int userId=object.optInt(Constants.USER_ID_KEY, 0);
        
        if(userId>0){
            boolean isUpdated=userDao.updateUserAddress(address, userId);
            
            return(isUpdated)
                    ?new ServiceResponder(true, "user address updated successfully")
                    :new ServiceResponder(false, "failed to update address");
        }
        else{
            return new ServiceResponder(false, "invalid userId");
        }
    }
    
    public ServiceResponder updateUserPassword(JSONObject object){
        String password=object.optString(Constants.PASSWORD_KEY, Constants.EMPTY_STRING);
        int userId=object.optInt(Constants.USER_ID_KEY, 0);
        
        if(userId>0){
            boolean isUpdated=userDao.updateUserPassword(password, userId);
            return(isUpdated)
                    ?new ServiceResponder(true, "password updated successfully")
                    :new ServiceResponder(false, "cannot update password"); 
        }
        else{
            return new ServiceResponder(false, "invalid userId");
        }   
    }
    
    public ServiceResponder getUserById(JSONObject object){
        int userId=object.optInt(Constants.USER_ID_KEY, 0);
        
        JSONObject user=userDao.getUserById(userId);
        
        return(!user.isEmpty())
                ?new ServiceResponder(true, user)
                :new ServiceResponder(false, "cannot get user");   
    }
    
    public ServiceResponder getAllUsers(){
        JSONObject users=userDao.getUsers();
        
        return(!users.isEmpty())
                ?new ServiceResponder(true, users)
                :new ServiceResponder(false, "cannot get users");
    }
    
    public ServiceResponder deleteUser(JSONObject object){
        int userId=object.optInt(Constants.USER_ID_KEY, 0);
        
        if(userId>0){
            boolean isDeleted=userDao.deleteUser(userId);
       
        return(isDeleted)
                ?new ServiceResponder(true, "user deleted successfully")
                :new ServiceResponder(false, "cannot delete user");
        }
        else{
            return new ServiceResponder(false, "invalid userId");
            
        }
        
    }
}
