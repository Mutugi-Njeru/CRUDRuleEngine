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
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class ResultsDao {
    @Inject
    MysqlConnector mysqlConnector;
    
    private static final Logger logger = LogManager.getLogger(ResultsDao.class);
    private static final String ERROR_LOG_TEMPLATE = "{}=> {} | {}";
    private static final String SELECT_RESULTS= "select s.student_name, s.student_adm, s.student_stream, sc.school_name, sc.school_code, sc.school_location, g.mathematics, g.english, g.chemistry, g.biology, g.physics, g.history, g.business, g.average, r.total\n" +
                                        "from students s\n" +
                                        "inner join school sc on s.school_id=sc.school_id\n" +
                                        "inner join grades g on g.student_id=s.student_id\n" +
                                        "inner join results r on r.student_id= g.student_id ";
    
    public boolean addResults(Results results){
        String query="INSERT INTO results (student_id, mathematics, english, chemistry, biology, physics, history, business, total, average) VALUES (?,?,?,?,?,?,?,?,?,?)";
        int total=Util.calculateTotalResults(results);
        int average=Util.calculateAverage(results);
        boolean status=false;
       
        try(Connection connection=mysqlConnector.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, results.getStudentId());
            preparedStatement.setInt(2, results.getMathematics());
            preparedStatement.setInt(3, results.getEnglish());
            preparedStatement.setInt(4, results.getChemistry());
            preparedStatement.setInt(5, results.getBiology());
            preparedStatement.setInt(6, results.getPhysics());
            preparedStatement.setInt(7, results.getHistory());
            preparedStatement.setInt(8, results.getBusiness());
            preparedStatement.setInt(9, total);
            preparedStatement.setInt(10, average);
            
            status=preparedStatement.executeUpdate()>0;
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return status;
        }
        return status;
    }
    
    public JSONObject getStudentResultsByAdm(int admission){
        JSONObject object= new JSONObject();
        String query=SELECT_RESULTS + "where s.student_adm=?";
        
        try(Connection connection=mysqlConnector.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            preparedStatement.setInt(1, admission);
            ResultSet resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){
                object.put("name", resultSet.getString(1))
                        .put("admission", resultSet.getInt(2))
                        .put("stream", resultSet.getString(3))
                        .put("school name", resultSet.getString(4))
                        .put("school code", resultSet.getInt(5))
                        .put("location", resultSet.getString(6))
                        .put("mathematics", resultSet.getString(7))
                        .put("english", resultSet.getString(8))
                        .put("chemistry", resultSet.getString(9))
                        .put("biology", resultSet.getString(10))
                        .put("physics", resultSet.getString(11))
                        .put("history", resultSet.getString(12))
                        .put("business", resultSet.getString(13))
                        .put("average", resultSet.getString(14))
                        .put("total", resultSet.getInt(15));             
                
            }
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return object;
        }
        return object;
    }
    
    
    public JSONObject getSchoolResultsBySchoolCode(int schoolCode){
        JSONArray array=new JSONArray();
        String query= SELECT_RESULTS + "WHERE sc.school_code=? order by r.total desc";
         
        try(Connection connection=mysqlConnector.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, schoolCode);
            ResultSet resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){
                JSONObject object=new JSONObject();
                object.put("name", resultSet.getString(1))
                        .put("admission", resultSet.getInt(2))
                        .put("stream", resultSet.getString(3))
                        .put("school name", resultSet.getString(4))
                        .put("school code", resultSet.getInt(5))
                        .put("location", resultSet.getString(6))
                        .put("mathematics", resultSet.getString(7))
                        .put("english", resultSet.getString(8))
                        .put("chemistry", resultSet.getString(9))
                        .put("biology", resultSet.getString(10))
                        .put("physics", resultSet.getString(11))
                        .put("history", resultSet.getString(12))
                        .put("business", resultSet.getString(13))
                        .put("average", resultSet.getString(14))
                        .put("total", resultSet.getInt(15));
                array.put(object);     
            }
            
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return new JSONObject().put("results", array);
        }
        return new JSONObject().put("results", array);       
    }
    
    public JSONObject getAllResults(){
        String query=SELECT_RESULTS;
        JSONArray array=new JSONArray();
        
        try (Connection connection=mysqlConnector.getConnection(); PreparedStatement preparedStatement=connection.prepareStatement(query)){
            ResultSet resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){
                JSONObject object=new JSONObject();
                object.put("name", resultSet.getString(1))
                        .put("admission", resultSet.getInt(2))
                        .put("stream", resultSet.getString(3))
                        .put("school name", resultSet.getString(4))
                        .put("school code", resultSet.getInt(5))
                        .put("location", resultSet.getString(6))
                        .put("mathematics", resultSet.getString(7))
                        .put("english", resultSet.getString(8))
                        .put("chemistry", resultSet.getString(9))
                        .put("biology", resultSet.getString(10))
                        .put("physics", resultSet.getString(11))
                        .put("history", resultSet.getString(12))
                        .put("business", resultSet.getString(13))
                        .put("average", resultSet.getString(14))
                        .put("total", resultSet.getInt(15));
                array.put(object);     
            }       
            
        } catch (SQLException ex) {
            logger.error(ERROR_LOG_TEMPLATE, Constants.ERROR, ex.getClass().getSimpleName(), ex.getMessage());
            return new JSONObject().put("results", array);
        }
        return new JSONObject().put("results", array);
    }
    
}
