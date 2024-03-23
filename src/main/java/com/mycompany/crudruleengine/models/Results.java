/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.models;

import jakarta.validation.constraints.NotNull;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
public class Results {
    
    @NotNull(message="studentId cannot be empty")
    private final int studentId;
    @NotNull(message="mathematics cannot be empty")
    private final int mathematics;
    @NotNull(message="english cannot be empty")
    private final int english;
    @NotNull(message="chemistry cannot be empty")
    private final int chemistry;
    @NotNull(message="biology cannot be empty")
    private final int biology;
    @NotNull(message="physics cannot be empty")
    private final int physics;
    @NotNull(message="history cannot be empty")
    private final int history;
    @NotNull(message="business cannot be empty")
    private final int business;
    
    public Results(JSONObject object){
        studentId=object.optInt("studentId", 0);
        mathematics=object.optInt("mathematics", 0);
        english=object.optInt("english", 0);
        chemistry=object.optInt("chemistry", 0);
        biology=object.optInt("biology", 0);
        physics=object.optInt("physics", 0);
        history=object.optInt("history", 0);
        business=object.optInt("business", 0);          
    }

    public int getStudentId() {
        return studentId;
    }

    public int getMathematics() {
        return mathematics;
    }

    public int getEnglish() {
        return english;
    }

    public int getChemistry() {
        return chemistry;
    }

    public int getBiology() {
        return biology;
    }

    public int getPhysics() {
        return physics;
    }

    public int getHistory() {
        return history;
    }

    public int getBusiness() {
        return business;
    }
    
}
