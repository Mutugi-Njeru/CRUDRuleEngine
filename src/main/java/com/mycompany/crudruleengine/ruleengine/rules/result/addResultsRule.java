/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.rules.result;

import com.mycompany.crudruleengine.enums.RequestTypes;
import com.mycompany.crudruleengine.enums.ResultIds;
import com.mycompany.crudruleengine.models.Results;
import com.mycompany.crudruleengine.records.BeanValidator;
import com.mycompany.crudruleengine.records.ServiceResponder;
import com.mycompany.crudruleengine.ruleengine.interfaces.ServiceRule;
import com.mycompany.crudruleengine.ruleengine.service.BeanValidatorService;
import com.mycompany.crudruleengine.ruleengine.service.ResultsService;
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
public class addResultsRule implements ServiceRule{
    
    @Inject
    ResultsService resultsService;
    
    @Inject
    BeanValidatorService beanValidator;

    @Override
    public boolean matches(Object input) {
        return(input.toString().equalsIgnoreCase(RequestTypes.ADD_RESULTS.name()));     
    }

    @Override
    public JSONObject apply(Object input) {
        JSONObject request=new JSONObject(input.toString());
        Results results=new Results(request);
        BeanValidator validator=beanValidator.validate(results);
        
        if(validator.isValid()){
        
        ServiceResponder response=resultsService.addResults(results);
        
        return(response.isSuccess())
                ?Util.buildResponse(Constants.SUCCESS_STATUS_CODE, ResultIds.RESULTS_ADDED.name(), response.message())
                :Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.RESULTS_NOT_ADDED.name(), response.message());
        }
        else{
            return Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.VALIDATION_FAILED.name(), validator.isValid());
        }
        
    }
       
}
