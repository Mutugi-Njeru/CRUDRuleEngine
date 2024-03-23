/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.crudruleengine.database;

import com.mycompany.crudruleengine.configuration.AppConfig;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 *
 * @author Savitar
 */
@ApplicationScoped
public class RedisConnector {
    
    public Jedis getJedisResource()
    {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(1000);
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMinIdle(10);
        jedisPoolConfig.setMaxWait(Duration.ofMillis(2000));
        jedisPoolConfig.setBlockWhenExhausted(true);
        jedisPoolConfig.setTestOnBorrow(true);

        return new JedisPool(jedisPoolConfig, AppConfig.REDIS_HOST, AppConfig.REDIS_PORT, AppConfig.CONNECTION_TIMEOUT, AppConfig.REDIS_PASSWORD, 1, false).getResource();
    }
    
}
