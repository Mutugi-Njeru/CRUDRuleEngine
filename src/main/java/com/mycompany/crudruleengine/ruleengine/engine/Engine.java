/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.engine;

import com.mycompany.crudruleengine.enums.ResultIds;
import com.mycompany.crudruleengine.ruleengine.interfaces.ServiceRule;
import com.mycompany.crudruleengine.utility.Constants;
import com.mycompany.crudruleengine.utility.Util;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class Engine {
    
    @Inject
    private Instance<ServiceRule>rules;
    
    public JSONObject init(JSONObject request, String requestType)
    {
        
        return (!request.isEmpty())
                ? start(request, requestType)
                : Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.REQUEST_FAILED.name(), "Request failed");
    }
    
    
    
    private JSONObject start(JSONObject request, String requestType){
        JSONObject response=new JSONObject();
        boolean isStarted=false;
        
        for(ServiceRule rule: rules){
            if(rule.matches(requestType)){
                isStarted=true;
                response=rule.apply(request);
                break;
            }
        }
        return(isStarted)
                ?response
                :Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.REQUEST_FAILED.name(), "unknown Request Type");
    }
    
}
