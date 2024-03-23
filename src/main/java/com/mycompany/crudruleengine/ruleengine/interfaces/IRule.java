/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ruleengine.interfaces;

import org.json.JSONObject;

/**
 *
 * @author Savitar
 * @param <I>
 */
public interface IRule <I>{
    boolean matches(I input);
    JSONObject apply(I input);
    
}
