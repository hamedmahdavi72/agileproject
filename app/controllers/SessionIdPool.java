package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by HamedMahdavi on 12/20/2016.
 */
public class SessionIdPool {
    //using for one-to-one map
    private static HashMap<String, String> sessionIdToUsernameMap = new HashMap<>();
    private static HashMap<String,String> usernameToSessionIdMap = new HashMap<>();
    private static final int ID_PREFIX_BOUND = 10000;
    private static Random generator = new Random();


    public static String getUsername(String sessionId) {
        return sessionIdToUsernameMap.get(sessionId);
    }

    public static boolean isSessionIdValid(String sessionId) {
        return sessionIdToUsernameMap.containsKey(sessionId);
    }

    public static boolean isLoggedIn(String username){
        return usernameToSessionIdMap.containsKey(username);
    }

    public static String getSessionId(String username){
        return usernameToSessionIdMap.get(username);
    }

    public static String generateSessionId(String username) {
        int idPrefix = generator.nextInt(ID_PREFIX_BOUND);
        return Integer.toString(idPrefix) + username;
    }

    //returns sessionId of username
    public static String addUser(String username){

        //check if user is logged in or not
        if(usernameToSessionIdMap.containsKey(username)){
            return usernameToSessionIdMap.get(username);
        }
        else{
            String sessionId = generateSessionId(username);
            usernameToSessionIdMap.put(username,sessionId);
            sessionIdToUsernameMap.put(sessionId,username);
            return sessionId;
        }
    }

    public static void removeUser(String sessionId){
        String username = getUsername(sessionId);
        sessionIdToUsernameMap.remove(sessionId);
        usernameToSessionIdMap.remove(username);
    }

    public static HashMap<String, String> getUsernameToSessionIdMap() {
        return usernameToSessionIdMap;
    }
}
