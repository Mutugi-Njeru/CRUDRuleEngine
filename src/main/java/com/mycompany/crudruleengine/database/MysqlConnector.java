/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.database;

import jakarta.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class MysqlConnector {
    private static final Logger logger=LogManager.getLogger(MysqlConnector.class);
    
    public Connection getConnection()
    {
        Connection connection;
        try
        {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("jdbc/users");
            connection = dataSource.getConnection();
        }
        catch (NamingException | SQLException ex)
        {
            logger.error("ERROR=> {} | Connection is null | {}", ex.getClass().getSimpleName(), ex);
            return null;
        }

        return connection;
    }
    
}
