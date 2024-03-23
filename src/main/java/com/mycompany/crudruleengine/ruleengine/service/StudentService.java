/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.service;

import com.mycompany.crudruleengine.dao.StudentDao;
import com.mycompany.crudruleengine.models.Student;
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
public class StudentService {
    
    @Inject
    StudentDao studentDao;
    
    public ServiceResponder createStudent(Student student){
        int studentId=studentDao.createStudent(student);
        
        return(studentId>0)
                ?new ServiceResponder(true, "student created successfully")
                :new ServiceResponder (false, "cannot create student");    
    }
    
    
    public ServiceResponder getStudentByAdm(JSONObject object){
        int admission=object.optInt(Constants.ADMISSION_KEY, 0);
        
        JSONObject student=studentDao.getStudentByAdmission(admission);
        
        return(!student.isEmpty()) 
                ? new ServiceResponder(true, student)
                :new ServiceResponder(false, "invalid student admission");
    }
    
}
