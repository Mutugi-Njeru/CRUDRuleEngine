/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.dao;

import com.mycompany.crudruleengine.database.MysqlConnector;
import com.mycompany.crudruleengine.models.Results;
import com.mycompany.crudruleengine.utility.Constants;
import com.mycompany.crudruleengine.utility.Util;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class GradesDao {
    
    @Inject
    MysqlConnector mysqlConnector;
    
    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(ResultsDao.class);
    private static final String ERROR_LOG_TEMPLATE = "{}=> {} | {}";
    
    public boolean addGrades(Results results){
        String query="INSERT INTO grades(student_id, mathematics, english, chemistry, biology, physics, history, business, average)VALUES (?,?,?,?,?,?,?,?,?)";
        boolean status=false;
        try(Connection connection=mysqlConnector.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setInt(1, results.getStudentId());
            preparedStatement.setString(2, Util.computeGrade(results.getMathematics()));
            preparedStatement.setString(3, Util.computeGrade(results.getEnglish()));
            preparedStatement.setString(4, Util.computeGrade(results.getChemistry()));
            preparedStatement.setString(5, Util.computeGrade(results.getBiology()));
            preparedStatement.setString(6, Util.computeGrade(results.getPhysics()));
            preparedStatement.setString(7, Util.computeGrade(results.getHistory()));
            preparedStatement.setString(8, Util.computeGrade(results.getBusiness()));
            preparedStatement.setString(9, Util.getTotalAverageGrade(results));
            
            status=preparedStatement.executeUpdate()>0;
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return status;
        }
        return status;
        
    }
    
    //get grades by student id
    //get grades in a certail school
    //get all grades
    //get grades by student name
    
    
}
