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
import entity.Users;
import entity.Tutor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Riana
 */
public class UsersDAO {
    public void addUser(Tutor tutor) {
        String email = tutor.getEmail();
        String key = email.substring(0,email.indexOf("@"));
        
        try {
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/users/" + key + ".json";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            JsonObject userData = new JsonObject();
            userData.addProperty("email", "huixin@steppingstones.com.sg");
            userData.addProperty("password", "huixin");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(userData.toString());
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteUser(String tutorID) {
        try {
            TutorDAO tutors = new TutorDAO();
            String email = tutors.retrieveSpecificTutor(tutorID).getEmail();
            String key = email.substring(0,email.indexOf("@"));
            String urlString = "https://team-exi-thriving-stones.firebaseio.com/users/" + key + ".json";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
            connection.setRequestMethod("DELETE");
            connection.setDoOutput(true);
            connection.connect();
        } catch (Exception ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public Users retrieveUserByEmail(final String email) {
        Gson gson = new GsonBuilder().create();
        try {
            URL userURL = new URL("https://team-exi-thriving-stones.firebaseio.com/users.json");
            URLConnection userConnection = userURL.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            userConnection.getInputStream()));
            String jsonString = reader.readLine();
            JsonElement jelement = new JsonParser().parse(jsonString);
            JsonObject  jobject = jelement.getAsJsonObject();
            Set entries = jobject.keySet();
            Iterator iter = entries.iterator();
            while(iter.hasNext()){
                String user = (String) iter.next();
                JsonElement userDataString = jobject.get(user);
                Users userToReturn = gson.fromJson(userDataString, Users.class);
                return userToReturn;
            }
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(UsersDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
