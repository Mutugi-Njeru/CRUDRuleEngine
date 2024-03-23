/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.resources;

import com.mycompany.crudruleengine.enums.ResultIds;
import com.mycompany.crudruleengine.ruleengine.engine.Engine;
import com.mycompany.crudruleengine.utility.Constants;
import com.mycompany.crudruleengine.utility.Util;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Savitar
 */
@Path("v1")
public class RequestController {
    
    @Inject
    Engine engine;
    private static final Logger logger = LogManager.getLogger(RequestController.class);
    
    
    @POST
    @Path("/request")
    @Produces(MediaType.APPLICATION_JSON)
    public Response doSomething(InputStream inputStream)
    {
        
        try
        {
            String inputString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject object = new JSONObject(inputString);
            
            logger.info("REQUEST RECEIVED=> {}", object);
            JSONObject request = (object.has(Constants.DATA_KEY)) ? object.getJSONObject(Constants.DATA_KEY) : new JSONObject();
            String requestType = object.optString(Constants.REQUEST_TYPE_KEY, Constants.EMPTY_STRING);
            JSONObject response = engine.init(request, requestType);
            logger.info("REQUEST RESPONSE=> {}", response);

            return Response.ok()
                    .entity(response.toString())
                    .build();
        }
        catch (JSONException | IOException ex)
        {
            logger.error("ERROR=> {} | {}", ex.getClass().getSimpleName(), ex);
            JSONObject response = Util.buildResponse(Constants.FAILURE_STATUS_CODE, ResultIds.REQUEST_FAILED.name(), "Invalid request payload format");
            return Response.ok()
                    .entity(response.toString())
                    .build();
        }
    }
    
    
}
