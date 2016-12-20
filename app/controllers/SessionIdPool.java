package controllers;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by HamedMahdavi on 12/20/2016.
 */
public class SessionIdPool {
    //using for one-to-one map
    private static HashMap<String, String> sessionIdMap = new HashMap<>();
    private static HashMap<String,String> usernameMap = new HashMap<>();
    private static final int ID_PREFIX_BOUND = 10000;
    private static Random generator = new Random();


    public static String getUsername(String sessionId) {
        return sessionIdMap.get(sessionId);
    }

    public static boolean isSessionIdValid(String sessionId) {
        return sessionIdMap.containsKey(sessionId);
    }

    public static boolean isLoggedIn(String username){
        return usernameMap.containsKey(username);
    }


    public static String generateSessionId(String username) {
        int idPrefix = generator.nextInt(ID_PREFIX_BOUND);
        return Integer.toString(idPrefix) + username;
    }

    //returns sessionId of username
    public static String addUser(String username){

        //check if user is logged in or not
        if(usernameMap.containsKey(username)){
            return usernameMap.get(username);
        }
        else{
            String sessionId = generateSessionId(username);
            usernameMap.put(username,sessionId);
            sessionIdMap.put(sessionId,username);
            return sessionId;
        }
    }


}
