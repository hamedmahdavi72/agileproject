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



    public static Messages generateInvalidUsernameMessages() {
        Messages msg;
        msg = new Messages(MessageConstants.getInstance().getUsernameField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getUsernameField(),
                MessageConstants.getInstance().getInvalidUsernameMessage());
        return msg;
    }

    public static Messages generateWrongPasswordMessages(){
        Messages msg;
        msg = new Messages(MessageConstants.getInstance().getPasswordField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getPasswordField(),
                MessageConstants.getInstance().getWrongPasswordMessage());
        return msg;
    }

    public static Messages generateSuccessfulLoginMessages() {
        Messages msg;
        msg = new Messages(MessageConstants.getInstance().getRedirectLoginPageField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getRedirectLoginPageField(),
                MessageConstants.getInstance().getRedirectMessage());
        return msg;
    }

    public static Messages generateTakenUsernameMessage() {
        Messages msg = new Messages(MessageConstants.getInstance().getUsernameField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getUsernameField(),
                MessageConstants.getInstance().getUsernameAlreadyTakenMessage());
        return msg;
    }

    public static Messages generateSuccessfulSignUpMessage(){
        Messages msg = new Messages(MessageConstants.getInstance().getRedirectDoctorSignUpPageField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getRedirectDoctorSignUpPageField(),
                MessageConstants.getInstance().getRedirectMessage());
        return msg;
    }

    public static Messages generateSuccessfulAdminLoginMessage(){
        Messages msg = new Messages(MessageConstants.getInstance().getRedirectAdminLoginPageField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getRedirectAdminLoginPageField(),
                MessageConstants.getInstance().getRedirectMessage());
        return msg;
    }

    public static Messages generateSuccessfulCustomerEditMessage(){
        Messages msg = new Messages(MessageConstants.getInstance().getCustomerEditProfileField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getCustomerEditProfileField(),
                MessageConstants.getInstance().getSuccessfulEdit());
        return msg;
    }
    public static Messages generateSuccessfulDoctorEditMessage(){
        Messages msg = new Messages(MessageConstants.getInstance().getDoctorEditProfileField());
        msg.addMessagesToFieldName(MessageConstants.getInstance().getDoctorEditProfileField(),
                MessageConstants.getInstance().getSuccessfulEdit());
        return msg;
    }

    public JsonNode toJsonResponse(){
        return Json.toJson(response);
    }
}
