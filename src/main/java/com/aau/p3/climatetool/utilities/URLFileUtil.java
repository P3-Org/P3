package com.aau.p3.climatetool.utilities;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import static com.aau.p3.platform.utilities.AlertPopup.errorMessage;

public class URLFileUtil {
    /**
     * Tests if a URL request returns a 200 OK response
     * @param url - The url to be tested
     */
    public static boolean urlExists(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Includes authorisation in HTTP request header
     * @param username - Login username
     * @param password - Login password
     */
    public static void authEnable(String username, String password) {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        });
    }

    /**
     * Downloads file from URL, using username and password
     * @param urlString - HTTP link to downloadable file
     * @return - Requested file from URL
     */
    public static File downloadGeoTiff(String urlString) throws IOException {
        URL url = new URL(urlString);

        if(!urlExists(url)){
            errorMessage("Adressen findes ikke i databasen!");
            throw new IOException("Url not found: " + url);
        }

        Path tempFile = Files.createTempFile("geotiff_", ".tif");
        tempFile.toFile().deleteOnExit();

        try (InputStream in = url.openStream()) {
            Files.copy(in, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }
        return tempFile.toFile();
    }
}
