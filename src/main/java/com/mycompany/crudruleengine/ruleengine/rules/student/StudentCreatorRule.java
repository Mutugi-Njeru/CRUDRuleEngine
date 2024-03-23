/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.rules.student;

import com.mycompany.crudruleengine.enums.RequestTypes;
import com.mycompany.crudruleengine.enums.ResultIds;
import com.mycompany.crudruleengine.models.Student;
import com.mycompany.crudruleengine.records.BeanValidator;
import com.mycompany.crudruleengine.records.ServiceResponder;
import com.mycompany.crudruleengine.ruleengine.interfaces.ServiceRule;
import com.mycompany.crudruleengine.ruleengine.service.BeanValidatorService;
import com.mycompany.crudruleengine.ruleengine.service.StudentService;
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
public class StudentCreatorRule implements ServiceRule{
    
    @Inject
    StudentService studentService;
    
    @Inject
    BeanValidatorService beanValidator;

    @Override
    public boolean matches(Object input) {
        return (input.toString().equalsIgnoreCase(RequestTypes.CREATE_STUDENT.name()));
        
    }

    @Override
    public JSONObject apply(Object input) {
        JSONObject request=new JSONObject(input.toString());
        Student student=new Student(request);
        
        BeanValidator validate=beanValidator.validate(student);
        
        if(validate.isValid()){
            
            ServiceResponder response=studentService.createStudent(student);
        
             return(response.isSuccess())
                ?Util.buildResponse(Constants.SUCCESS_STATUS_CODE, ResultIds.STUDENT_CREATED, response.message())
                :Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.STUDENT_NOT_CREATED, response.message());
        }
        else{
            return Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.REQUEST_FAILED.name(), validate.isValid());
        }
   
    }

}
