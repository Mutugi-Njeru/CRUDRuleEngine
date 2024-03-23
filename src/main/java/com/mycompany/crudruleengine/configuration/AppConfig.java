/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.configuration;

import jakarta.enterprise.inject.spi.CDI;
import org.eclipse.microprofile.config.Config;

/**
 *
 * @author Savitar
 */
public class AppConfig {
    
    private static final Config CONFIG = CDI.current().select(Config.class).get();
    
     public static final String APPLICATION_NAME = CONFIG.getValue("application.name", String.class);
    
    public static final String REDIS_HOST = CONFIG.getValue("redis.host", String.class);
    public static final int REDIS_PORT = CONFIG.getValue("redis.port", Integer.class);
    public static final int CONNECTION_TIMEOUT = CONFIG.getValue("redis.connection-timeout", Integer.class);
    public static final String REDIS_PASSWORD = CONFIG.getValue("redis.password", String.class);
    public static final String REDIS_USERNAME = CONFIG.getValue("redis.username", String.class);
    
}
