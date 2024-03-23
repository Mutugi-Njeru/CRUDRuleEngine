/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.rules.school;

import com.mycompany.crudruleengine.enums.RequestTypes;
import com.mycompany.crudruleengine.enums.ResultIds;
import com.mycompany.crudruleengine.models.School;
import com.mycompany.crudruleengine.records.BeanValidator;
import com.mycompany.crudruleengine.records.ServiceResponder;
import com.mycompany.crudruleengine.ruleengine.interfaces.ServiceRule;
import com.mycompany.crudruleengine.ruleengine.service.BeanValidatorService;
import com.mycompany.crudruleengine.ruleengine.service.SchoolService;
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
public class SchoolCreatorRule implements ServiceRule {
    
    @Inject
    SchoolService schoolService;
    
    @Inject
    BeanValidatorService beanValidator;

    @Override
    public boolean matches(Object input) {
        return (input.toString().equalsIgnoreCase(RequestTypes.CREATE_SCHOOL.name()));
         
    }

    @Override
    public JSONObject apply(Object input) {
        JSONObject request=new JSONObject(input.toString());
        School school=new School(request);
        
        BeanValidator validate=beanValidator.validate(school);
        
        if(validate.isValid()){
        ServiceResponder response=schoolService.createSchool(school);
        
        return(response.isSuccess())
                ?Util.buildResponse(Constants.SUCCESS_STATUS_CODE, ResultIds.SCHOOL_CREATED.name(), response.message())
                :Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.SCHOOL_NOT_CREATED.name(), response.message()); 
        }
        else{
            return Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.VALIDATION_FAILED.name(), validate.isValid());
        }
    }
    
}
