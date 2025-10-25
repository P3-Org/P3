package com.aau.p3;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchAPI {
    private String urlString = "https://timeapi.io/api/time/current/zone?timeZone=Europe/Amsterdam";

    public void setURL(String url) {
        this.urlString = url;
    }



    public int fetchYear() {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("accept", "application/json");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(response.toString());
            return json.getInt("year");

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
