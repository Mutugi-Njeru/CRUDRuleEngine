/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.models;

import com.mycompany.crudruleengine.utility.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
public class Student {
    @NotNull(message="schoolId cannot be empty")
    private final int schoolId;
    @NotEmpty(message="name cannot be empty")
    private final String name;
    @NotNull(message="admission cannot be empty")
    private final int admission;
    @NotBlank(message="stream cannot be blank")
    private final String stream;
    @NotNull(message="please enter a valid contact")
    private final String contacts;
    
    public Student(JSONObject object){
        schoolId=object.optInt("schoolId", 0);
        name=object.optString("name", Constants.EMPTY_STRING);
        admission=object.optInt("admission", 0);
        stream=object.optString("stream", Constants.EMPTY_STRING);
        contacts=object.optString("contacts", Constants.EMPTY_STRING);
    }

    public int getSchoolId() {
        return schoolId;
    }

    public String getName() {
        return name;
    }

    public int getAdmission() {
        return admission;
    }

    public String getStream() {
        return stream;
    }

    public String getContacts() {
        return contacts;
    }
    
    
    
}
