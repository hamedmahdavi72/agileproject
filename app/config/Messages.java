package config;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ARYA on 12/19/2016.
 */
public class Messages {

    private String message;
    private Map<String, ArrayList<String>> response;

    public ArrayList<String> getMessagesByFieldName(String fieldName){
        if(response.get(fieldName) != null)
            return response.get(fieldName);
        return null;
    }

    public void addMessagesToFieldName(String fieldName, String message){

        response.computeIfAbsent(fieldName, k -> new ArrayList<>());
        response.get(fieldName).add(message);
    }

    public void addMessagesToFieldName(String fieldName, List<String> messages){
            response.get(fieldName).addAll(messages);
    }

    public Messages(){
        if(response == null)
            response = new HashMap<>();
    }

    public Messages(String fieldName){
        if(response == null)
            response = new HashMap<>();
        response.computeIfAbsent(fieldName, k -> new ArrayList<>());
    }

    public Messages(List<String> fieldNames){
        for(String fieldName : fieldNames){
            response.computeIfAbsent(fieldName, k -> new ArrayList<>());
        }
    }

    public JsonNode toJsonResponse(){
        return Json.toJson(response);
    }
}
