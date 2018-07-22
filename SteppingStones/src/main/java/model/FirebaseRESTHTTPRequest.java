/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author DEYU
 */
public class FirebaseRESTHTTPRequest {
    public static JSONObject get(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();

        StringBuffer response;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        System.out.println("Get Response " + responseCode);
        JSONObject myResponse = null;
        if(!response.toString().equals("null")){
            myResponse = new JSONObject(response.toString());
        }
        return myResponse;
    }
    
    public static void delete(String url) throws Exception{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("DELETE");
        int responseCode = con.getResponseCode();
        System.out.println("Delete Response " + responseCode);
    }
    
    public static void put(String url, String json) throws Exception{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("PUT");
        con.setDoOutput(true);
        try (OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream())) {
            osw.write(json);
            osw.flush();
        }
        int responseCode = con.getResponseCode();
        System.out.println("Put Response " + responseCode);
    }
    
    public static void post(String url, String json) throws Exception{
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        try (OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream())) {
            osw.write(json);
            osw.flush();
        }
        int responseCode = con.getResponseCode();
        System.out.println("Post Response " + responseCode);
    }
       
    public static void patch(String url, String json) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("X-HTTP-Method-Override", "PATCH");
        con.setRequestMethod("POST"); 
        con.setDoOutput(true);
        try (OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream())) {
            osw.write(json);
            osw.flush();
        }
        int responseCode = con.getResponseCode();
        System.out.println("Patch Response " + responseCode);
    }
}
