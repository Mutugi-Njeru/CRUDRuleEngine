/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.models;

import com.mycompany.crudruleengine.utility.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
public class User {
    
    @NotNull(message="firstName cannot be empty")
    private final String firstName;
    @NotBlank(message="lastName cannot be empty")
    private final String lastName;
    @Email(message="please provide a valid email")
    private final String email;
    private final String password;
    @NotNull(message="idNumber cannot be blank")
    private final int idNumber;
    @NotNull(message="address cannot be empty")
    private final String address;
    @NotBlank(message="phoneNumber cannot be empty")
    private final String phoneNumber;
    
    public User(JSONObject object){
        firstName=object.optString("firstName", Constants.EMPTY_STRING);
        lastName=object.optString("lastName", Constants.EMPTY_STRING);
        email=object.optString("email", Constants.EMPTY_STRING);
        password=object.optString("password", Constants.EMPTY_STRING);
        idNumber=object.optInt("idNumber", 0);
        address=object.optString("address", Constants.EMPTY_STRING);
        phoneNumber=object.optString("phoneNumber", Constants.EMPTY_STRING);
        
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    
}
