/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.service;

import com.mycompany.crudruleengine.records.BeanValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class BeanValidatorService {
    @Inject
    private ValidatorFactory validatorFactory;
    private static final Logger logger=LogManager.getLogger(BeanValidatorService.class);
    
    public BeanValidator validate(Object object){
        JSONArray violations=new JSONArray();
        Set<ConstraintViolation<Object>> beanViolations=validatorFactory.getValidator().validate(object);
        
        if(!beanViolations.isEmpty()){
            beanViolations.forEach(violation -> violations.put(violation.getMessage()));
            logger.error("MESSAGE=> Payload validation failed=> " + violations);

            return new BeanValidator(false, "Invalid payload parameters", violations);
        }
        else
        {
            logger.info("MESSAGE=> Payload validation successful");

            return new BeanValidator(true, "validation successful", violations);
        }
        
    }
    
}
