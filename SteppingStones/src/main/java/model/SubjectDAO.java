/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Student;
import entity.Subject;
import entity.Tutor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author Riana
 */
public class SubjectDAO {
    public static ArrayList<Subject> listAllSubjects(){
       Gson gson = new GsonBuilder().create();
        ArrayList<Subject> tutors = new ArrayList<>();

        try {
            URL userURL = new URL("https://team-exi-thriving-stones.firebaseio.com/subjects.json");
            URLConnection connection = userURL.openConnection();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
            String jsonString = reader.readLine();
            JsonElement jelement = new JsonParser().parse(jsonString);
            JsonObject jobject = jelement.getAsJsonObject();
            Set entries = jobject.keySet();
            Iterator iter = entries.iterator();
            while (iter.hasNext()) {
                String user = (String) iter.next();
                JsonElement userDataString = jobject.get(user);
                Subject tutor = new Subject(gson.fromJson(userDataString, String.class));
                tutor.setSubjectId(user);
                tutors.add(tutor);
            }
            reader.close();
        } catch (Exception ex) {
            Logger.getLogger(SubjectDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tutors;
    }
}
