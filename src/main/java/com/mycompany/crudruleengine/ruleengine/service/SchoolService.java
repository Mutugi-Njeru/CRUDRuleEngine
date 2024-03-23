/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.service;

import com.mycompany.crudruleengine.dao.SchoolDao;
import com.mycompany.crudruleengine.models.School;
import com.mycompany.crudruleengine.records.ServiceResponder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class SchoolService {
    
    @Inject
    SchoolDao schoolDao;
    
    public ServiceResponder createSchool(School school){
        int schoolId=schoolDao.createSchool(school);
        
        if(schoolId>0){
            return new ServiceResponder(true, "school created successfully");
        }
        else{
            return new ServiceResponder(false, "invalid schoolId");
        }
    }
    
}
