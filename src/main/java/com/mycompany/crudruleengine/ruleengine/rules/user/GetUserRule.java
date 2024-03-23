/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.rules.user;

import com.mycompany.crudruleengine.enums.RequestTypes;
import com.mycompany.crudruleengine.enums.ResultIds;
import com.mycompany.crudruleengine.records.ServiceResponder;
import com.mycompany.crudruleengine.ruleengine.interfaces.ServiceRule;
import com.mycompany.crudruleengine.ruleengine.service.UserService;
import com.mycompany.crudruleengine.utility.Constants;
import com.mycompany.crudruleengine.utility.Util;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class GetUserRule implements ServiceRule {
    
    @Inject
    UserService userService;

    @Override
    public boolean matches(Object input) {
        return (input.toString().equalsIgnoreCase(RequestTypes.GET_USER_BY_ID.name()));
            
    }

    @Override
    public JSONObject apply(Object input) {
        JSONObject request=new JSONObject(input.toString());
        
        ServiceResponder response=userService.getUserById(request);
        
        return(response.isSuccess())
                ?Util.buildResponse(Constants.SUCCESS_STATUS_CODE, ResultIds.USER.name(), response.message())
                :Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.USER_NOT_FOUND.name(), response.message());
         
    }
    
}
