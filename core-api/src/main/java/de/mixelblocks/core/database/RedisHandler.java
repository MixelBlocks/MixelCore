package de.mixelblocks.core.database;

import redis.clients.jedis.Jedis;

/**
 * A simple-to-use Redis Database Handler
 * @since 22.01.2022
 * @author LuciferMorningstarDev
 */
public class RedisHandler {

    private Jedis client;

    /**
     * @param redisUrl
     */
    public RedisHandler(String redisUrl) {
        this.client = new Jedis(redisUrl);
    }

    /**
     * @param redisUrl
     * @param authentication
     */
    public RedisHandler(String redisUrl, String authentication) {
        this.client = new Jedis(redisUrl);
        client.auth(authentication);
    }

    /**
     * @param redisUrl
     * @param username
     * @param authentication
     */
    public RedisHandler(String redisUrl, String username, String authentication) {
        this.client = new Jedis(redisUrl);
        client.auth(username, authentication);
    }

    /**
     * Set a value to a key ( No Expiry )
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        return client.set(key, value);
    }

    /**
     * Set a value to a key ( Secondy Expiry )
     * @param seconds
     * @param key
     * @param value
     * @return
     */
    public String setEx(long seconds, String key, String value) {
        return client.setex(key, seconds, value);
    }

    /**
     *
     * @param key
     * @param defaultValue
     * @return value - the value which is present for a key ( If not existing or expired returns value )
     */
    public String get(String key, String defaultValue) {
        String value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     *
     * @param key
     * @return value - the value which is present for a key ( If not existing or expired returns null )
     */
    public String get(String key) {
        return client.get(key);
    }

}
