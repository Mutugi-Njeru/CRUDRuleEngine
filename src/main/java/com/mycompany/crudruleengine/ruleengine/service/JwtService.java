/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mycompany.crudruleengine.configuration.AppConfig;
import com.mycompany.crudruleengine.records.Authentication;
import com.mycompany.crudruleengine.records.Token;
import com.mycompany.crudruleengine.utility.Constants;
import com.mycompany.crudruleengine.utility.Util;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Base64;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class JwtService {
    
    private static final Logger logger=LogManager.getLogger(JwtService.class);
    
    public String generateAccessToken(Authentication authentication){
        Long milliseconds=(new Date().getTime()) +(Constants.TOKEN_EXPIRY_TIME_IN_SECONDS * 1000);
        Date expireDate=new Date(milliseconds);
        return JWT.create()
                .withClaim(Constants.CREATED_AT_KEY, Util.getTimestamp())
                .withClaim(Constants.USER_ID_KEY, authentication.userId())
                .withIssuer(AppConfig.APPLICATION_NAME)
                .withClaim(Constants.EXPIRES_IN_KEY, Constants.TOKEN_EXPIRY_TIME_IN_SECONDS)
                .withExpiresAt(expireDate)
                .sign(Constants.JWT_ALGORITHM);    
    }
    
    public Token decodeAccessToken(String accessToken){
        try{
            byte[] decodedBytes=Base64.getDecoder().decode(accessToken.getBytes());
        accessToken=new String(decodedBytes);
        
        JWTVerifier verifier=JWT.require(Constants.JWT_ALGORITHM)
                .withIssuer(AppConfig.APPLICATION_NAME)
                .acceptExpiresAt(Constants.TOKEN_EXPIRY_TIME_IN_SECONDS)
                .build();
        
        DecodedJWT decodedJWT=verifier.verify(accessToken);
        int userId=decodedJWT.getClaims().get(Constants.USER_ID_KEY).asInt();
        
        return new Token(true, userId);
        }
        catch(JWTVerificationException | IllegalArgumentException | JSONException ex){
            logger.error("ERROR=> {} | {}", ex.getClass().getSimpleName(), ex.getMessage());
            return new Token(false, 0);  
        }
        
    }
    
}
