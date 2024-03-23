/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.dao;

import com.mycompany.crudruleengine.database.MysqlConnector;
import com.mycompany.crudruleengine.models.Student;
import com.mycompany.crudruleengine.utility.Constants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class StudentDao {
    
    @Inject
    MysqlConnector mysqlConnector;
    
    private static final org.apache.logging.log4j.Logger logger=LogManager.getLogger(StudentDao.class);
    private static final String ERROR_LOG_TEMPLATE="{}=> {} | {}";
    
    public int createStudent(Student student){
        String query="INSERT INTO students (school_id, student_name, student_adm, student_stream, student_contacts) VALUES (?,?,?,?,?)";
        int studentId=0;
        
        try (Connection connection=mysqlConnector.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, student.getSchoolId());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setInt(3, student.getAdmission());
            preparedStatement.setString(4, student.getStream());
            preparedStatement.setString(5, student.getContacts());
            preparedStatement.executeUpdate();
            
            ResultSet resultSet=preparedStatement.getGeneratedKeys();
            
            while(resultSet.next()){
                studentId=resultSet.getInt(1);   
            }
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return studentId;
        }
        return studentId;
    }
    
    public JSONObject getStudentByAdmission(int admission){
        JSONObject object=new JSONObject();
        String query="SELECT s.student_name, s.student_adm, s.student_stream, s.student_contacts, sc.school_name, sc.school_code, sc.school_location\n" +
                        "FROM students s\n" +
                        "INNER JOIN school sc on sc.school_id=s.school_id\n" +
                        "WHERE s.student_adm=?";
        
        try(Connection connection=mysqlConnector.getConnection();PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setInt(1, admission);
            ResultSet resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){
                object.put("name", resultSet.getString(1))
                        .put("admission", resultSet.getInt(2))
                        .put("stream", resultSet.getString(3))
                        .put("contacts", resultSet.getString(4))
                        .put("school name", resultSet.getString(5))
                        .put("school code", resultSet.getInt(6))
                        .put("location", resultSet.getString(7)); 
            }
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return object;
        }
        return object;  
    }
    
}
