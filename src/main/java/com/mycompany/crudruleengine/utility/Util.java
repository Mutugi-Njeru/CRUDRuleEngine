/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.utility;

import com.mycompany.crudruleengine.models.Results;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
public class Util {
    
    private static final Logger logger = LogManager.getLogger(Util.class);
    private Util()
    {
        
    }
    
    
    public static JSONObject buildResponse(int statusCode, Object status, Object message)
    {
        try
        {
            return new JSONObject().put("statusCode", statusCode)
                    .put("status", status)
                    .put("message", message);
        }
        catch (JSONException ex)
        {
            logger.info("ERROR=> {} | {}", ex.getClass().getSimpleName(), ex.getMessage());
            return new JSONObject();
        }
    }
    
    
    public static String getTimestamp()
    {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
    
    public static int calculateTotalResults(Results results){
        int maths=results.getMathematics();
        int eng=results.getEnglish();
        int chem=results.getChemistry();
        int bio=results.getBiology();
        int physics=results.getPhysics();
        int histo=results.getHistory();
        int business=results.getBusiness();
        
        int total=maths + eng + chem + bio + physics + histo + business;
        
        return total;    
    }
    
    public static int calculateAverage(Results results){
        int total=calculateTotalResults(results);
        
        int average=total/7;
        
        return average;
    }
    
    public static String computeGrade(int marks){
        if(marks<=100 && marks>=90) {
            return "A";
        }
        else if(marks<90 && marks>=81) {
            return "A-";
        }
        else if(marks<81 && marks>=75) {
            return "B+";
        }
        else if(marks<75 && marks>=64) {
            return "B";
        }
        else if(marks<64 && marks>=58) {
            return "B-";
        }
        else if(marks<58 && marks>=54) {
            return "C+";
        }
        else if(marks<54 && marks>=48) {
            return "C";
        }
        else if(marks<48 && marks>=42) {
            return "C-";
        }
        else if(marks<42 && marks>=36) {
            return "D+";
        }
        else if(marks<36 && marks>=28) {
            return "D";
        }
        else if(marks<28 && marks>=20) {
            return "D-";
        }
        else {
            return "E";
        }
    }
    
    public static String getTotalAverageGrade(Results results){
        int average=calculateAverage(results);
        String grade=computeGrade(average);
        
        return grade;        
    }
    
}
