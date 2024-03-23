/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.utility;

import com.auth0.jwt.algorithms.Algorithm;

/**
 *
 * @author Savitar
 */
public class Constants {
    private Constants(){}
    
    public static final String EMPTY_STRING="";
    public static final String SHA_256_HASH_ALGORITHM = "SHA-256";
    public static final String ERROR="ERROR";
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int FAILURE_STATUS_CODE = 199;
    public static final int TOKEN_EXPIRY_TIME_IN_SECONDS = 3600;
    
    public static final String CREATED_AT_KEY = "createdAt";
    public static final String USER_ID_KEY = "userId";
    public static final String ADMISSION_KEY = "admission";
    public static final String SCHOOL_CODE = "schoolCode";
    public static final String ADDRESS_KEY = "address";
    public static final String EXPIRES_IN_KEY = "expiresIn";
    public static final Algorithm JWT_ALGORITHM = Algorithm.HMAC512(Constants.JWT_SECRET);
    public static final String JWT_SECRET = "coder";
    
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String DATA_KEY = "data";
    public static final String REQUEST_TYPE_KEY = "requestType";
    
    
}
