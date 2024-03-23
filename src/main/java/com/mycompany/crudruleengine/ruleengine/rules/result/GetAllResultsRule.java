/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.rules.result;

import com.mycompany.crudruleengine.enums.RequestTypes;
import com.mycompany.crudruleengine.enums.ResultIds;
import com.mycompany.crudruleengine.records.ServiceResponder;
import com.mycompany.crudruleengine.ruleengine.interfaces.ServiceRule;
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
public class GetAllResultsRule implements ServiceRule{
    
    @Inject
    ResultsService resultsService;

    @Override
    public boolean matches(Object input) {
        return (input.toString().equalsIgnoreCase(RequestTypes.GET_ALL_RESULTS.name()));
         
    }

    @Override
    public JSONObject apply(Object input) {
        ServiceResponder response=resultsService.getAllResults();
        
        return(response.isSuccess())
                ?Util.buildResponse(Constants.SUCCESS_STATUS_CODE, ResultIds.REQUEST_SUCCESSFULL, response.message())
                :Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.REQUEST_FAILED, response.message());
         
    }
    
}
