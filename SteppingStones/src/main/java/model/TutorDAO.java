/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Tutor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TutorDAO {
    public void addTutor(String tutorID, Tutor tutor) {
        Gson gson = new GsonBuilder().create();
        
        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/tutors/" + tutorID + ".json";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            String userData = gson.toJson(tutor);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(userData);
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Tutor retrieveSpecificTutor(String id) {
        Gson gson = new GsonBuilder().create();
        
        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/tutors/" + id + ".json";
            URL url = new URL(urlString);
            System.out.println(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            String jsonString = reader.readLine();
            System.out.println(jsonString);
            Tutor tutor = gson.fromJson(jsonString, Tutor.class);
            tutor.setID(id);
            reader.close();
            System.out.println(connection.getResponseCode());
            return tutor;
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static Tutor retrieveTutorByEmail(final String email) {
        Gson gson = new GsonBuilder().create();
        
        try {
            URL userURL = new URL("https://team-exi-thriving-stones.firebaseio.com/tutors.json");
            URLConnection connection = userURL.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            String jsonString = reader.readLine();
            JsonElement jelement = new JsonParser().parse(jsonString);
            JsonObject  jobject = jelement.getAsJsonObject();
            Set entries = jobject.keySet();
            Iterator iter = entries.iterator();
            while(iter.hasNext()){
                String tutor = (String) iter.next();
                JsonElement userDataString = jobject.get(tutor);
                Tutor tutorToReturn = gson.fromJson(userDataString, Tutor.class);
                if(tutorToReturn.getEmail().equals(email)) {
                    tutorToReturn.setID(tutor);
                    return tutorToReturn;
                }
            }
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Tutor> retrieveAllTutors() {
        Gson gson = new GsonBuilder().create();
        ArrayList<Tutor> tutors = new ArrayList<>();
        
        try {
            URL userURL = new URL("https://team-exi-thriving-stones.firebaseio.com/tutors.json");
            URLConnection connection = userURL.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            String jsonString = reader.readLine();
            JsonElement jelement = new JsonParser().parse(jsonString);
            JsonObject  jobject = jelement.getAsJsonObject();
            Set entries = jobject.keySet();
            Iterator iter = entries.iterator();
            while(iter.hasNext()){
                String user = (String) iter.next();
                JsonElement userDataString = jobject.get(user);
                Tutor tutor = gson.fromJson(userDataString, Tutor.class);
                tutor.setID(user);
                tutors.add(tutor);
            }
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tutors;
    }

    public boolean updateTutor(String tutorID, Map<String, Object> updates) {
        Gson gson = new GsonBuilder().create();
        
        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/tutors/" + tutorID + "/.json";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            String userData = gson.toJson(updates);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(userData);
            writer.flush();
            writer.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean removeTutor(String tutorID) {
        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/tutors/" + tutorID + ".json";
            System.out.println(urlString + " HALP LA");
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.connect();
            System.out.println(connection.getResponseCode() + " HALP LA");
            return true;
        } catch (Exception ex) {
            Logger.getLogger(TutorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
