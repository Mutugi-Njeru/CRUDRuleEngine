/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.dao;

import com.mycompany.crudruleengine.database.MysqlConnector;
import com.mycompany.crudruleengine.models.School;
import com.mycompany.crudruleengine.utility.Constants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class SchoolDao {
    
    @Inject
    MysqlConnector mysqlConnector;
    
    private static final Logger logger = LogManager.getLogger(SchoolDao.class);
    private static final String ERROR_LOG_TEMPLATE = "{}=> {} | {}";
    
    public int createSchool(School school){
        String query="INSERT INTO school(user_id, school_name, school_code, school_location, school_address)VALUES(?,?,?,?,?)";
        int schoolId = 0;
        
        try(Connection connection=mysqlConnector.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, school.getUserId());
            preparedStatement.setString(2, school.getName());
            preparedStatement.setInt(3, school.getCode());
            preparedStatement.setString(4, school.getLocation());
            preparedStatement.setString(5, school.getAddress());
            preparedStatement.executeUpdate();
            
            ResultSet resultSet=preparedStatement.getGeneratedKeys();
            
            while(resultSet.next()){
                schoolId=resultSet.getInt(1);
            }
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return schoolId;
        }
        return schoolId;
    }
    
    
    
}
