/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.service;

import com.mycompany.crudruleengine.dao.GradesDao;
import com.mycompany.crudruleengine.dao.ResultsDao;
import com.mycompany.crudruleengine.models.Results;
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
public class ResultsService {
    
    @Inject
    ResultsDao resultsDao;
    
    @Inject
    GradesDao gradesDao;
    
    public ServiceResponder addResults(Results results){
        
        boolean isResults=resultsDao.addResults(results);
        boolean isGrades=gradesDao.addGrades(results);
        
        return(isResults && isGrades)
                ?new ServiceResponder(true, "results added successfully")
                :new ServiceResponder(false, "invalid operation");
    }
    
    
    public ServiceResponder getStudentResultsByAdm(JSONObject object){
        int admission=object.optInt(Constants.ADMISSION_KEY, 0);
        JSONObject result=resultsDao.getStudentResultsByAdm(admission);
        
        return(!result.isEmpty())
                ?new ServiceResponder(true, result)
                :new ServiceResponder(false, "invalid student admission");     
    }
    
    public ServiceResponder getSchoolResultsBySchoolCode(JSONObject object){
        int schoolCode=object.optInt(Constants.SCHOOL_CODE, 0);
        JSONObject result=resultsDao.getSchoolResultsBySchoolCode(schoolCode);
        
        return(!result.isEmpty())
                ?new ServiceResponder(true, result)
                :new ServiceResponder(false, "invalid school code");  
    }
    
    public ServiceResponder getAllResults(){
        JSONObject result=resultsDao.getAllResults();
        
        return(!result.isEmpty())
                ?new ServiceResponder(true, result)
                :new ServiceResponder(false, "invalid results");  
    }
    
}
