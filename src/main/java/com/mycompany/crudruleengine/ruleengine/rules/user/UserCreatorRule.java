/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.rules.user;

import com.mycompany.crudruleengine.enums.RequestTypes;
import com.mycompany.crudruleengine.enums.ResultIds;
import com.mycompany.crudruleengine.models.User;
import com.mycompany.crudruleengine.records.BeanValidator;
import com.mycompany.crudruleengine.records.ServiceResponder;
import com.mycompany.crudruleengine.ruleengine.interfaces.ServiceRule;
import com.mycompany.crudruleengine.ruleengine.service.BeanValidatorService;
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
public class UserCreatorRule implements ServiceRule{
    
    @Inject
    UserService userService;
    
    @Inject
    BeanValidatorService beanValidator;

    @Override
    public boolean matches(Object input) {
        return (input.toString().equalsIgnoreCase(RequestTypes.CREATE_USER.name())); 
    }

    @Override
    public JSONObject apply(Object input) {
        JSONObject request=new JSONObject(input.toString());
        
        User user=new User(request);
        BeanValidator validation=beanValidator.validate(user);
        
        if(validation.isValid()){
            ServiceResponder response=userService.createUser(user);
            return(response.isSuccess())
                    ?Util.buildResponse(Constants.SUCCESS_STATUS_CODE, ResultIds.REQUEST_SUCCESSFULL.name(), response.message())
                    :Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.REQUEST_FAILED.name(), response.message());   
        }
        else
        {
            return Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.VALIDATION_FAILED.name(), validation.isValid());
        }  
    }
}
