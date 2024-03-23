/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.ciphers;

import com.mycompany.crudruleengine.utility.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.text.AES256TextEncryptor;

/**
 *
 * @author Savitar
 */
public class AES256Encryptor {
    private static final Logger logger = LogManager.getLogger(AES256Encryptor.class);





    private AES256Encryptor()
    {
    }





    public static String encrypt(String plainText, String key)
    {
        AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
        textEncryptor.setPassword(key);
        return textEncryptor.encrypt(plainText);
    }





    public static String decrypt(String encryptedText, String key)
    {
        try
        {
            AES256TextEncryptor textEncryptor = new AES256TextEncryptor();
            textEncryptor.setPassword(key);
            return textEncryptor.decrypt(encryptedText);
        }
        catch (EncryptionOperationNotPossibleException ex)
        {
            logger.error("{} | Decryption failed=> {}", ex.getClass().getSimpleName(), ex.getMessage());
            return Constants.EMPTY_STRING;
        }
    }
    
}
