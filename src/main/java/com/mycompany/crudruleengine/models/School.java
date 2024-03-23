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
public class School {
    @NotNull(message="userId cannot be empty")
    private final int userId;
    @NotBlank(message="name cannot be empty")
    private final String name;
    @NotNull(message="code cannot be empty")
    private final int code;
    @NotBlank(message="location cannot be blank")
    private final String location;
    @NotNull(message="address cannot be null")
    private final String address;
    
    public School(JSONObject object){
        userId=object.optInt("userId", 0);
        name=object.optString("name", Constants.EMPTY_STRING);
        code=object.optInt("code", 0);
        location=object.optString("location", Constants.EMPTY_STRING);
        address=object.optString("address", Constants.EMPTY_STRING);
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }
    
    
}
