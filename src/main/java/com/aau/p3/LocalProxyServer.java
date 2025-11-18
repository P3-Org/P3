package com.aau.p3;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.concurrent.Executors;


/**
 * The class below is intended to set up a local server to use for api calls in case of CORS issues.
 * @Param portnumber
 */
public class LocalProxyServer {
    // Method for setting up the necessary tools for starting a proxy server
    public static void startProxy(int port) {
        try {
            // Creates a httpserver which binds to a socket-address object with the specified port number
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            // The HTTP context specifies what to do when receiving requests on the given port + path
            server.createContext("/wms-dmh-proxy", LocalProxyServer::handleDmh);
            server.createContext("/wms-hip-proxy", LocalProxyServer::handleHip);
            server.createContext("/wms-daf-proxy", LocalProxyServer::handleDaf);
            // Sets 4 threads available for requests for the server
            server.setExecutor(Executors.newFixedThreadPool(4));
            server.start();
            System.out.println("Local proxy running at http://localhost:" + port + "/wms-proxy");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    * Helper method that checks if the query path is null,
    * and after combines the targetUrl and query for the fullQuery.
    */
    private static String queryNullCheck(String targetUrl, String query){
        String fullQuery = "";
        if (query != null) {
            fullQuery = targetUrl + "?" + query;
        }
        return fullQuery;
    }

    // Method that handles the request for dmh WMS, and forwards the targetUrl to handleRequest
    private static void handleDmh(HttpExchange exchange)  throws IOException{
        // Converts the request to a string
        String query = exchange.getRequestURI().getRawQuery();
        String targetUrl = "https://api.dataforsyningen.dk/wms/dhm";

        // Checks if the query is null and builds it into a full queryPath with the targetUrl for the given WMS.
        String fullQuery = queryNullCheck(targetUrl, query);
        handleRequest(exchange, fullQuery);
    }

    // Method that handles the request for hip WMS, and forwards the targetUrl to handleRequest
    private static void handleHip(HttpExchange exchange) throws IOException{
        // Converts the request to a string
        String query = exchange.getRequestURI().getRawQuery();
        String targetUrl = "https://api.dataforsyningen.dk/hip_dtg_10m_100m";

        // Checks if the query is null and builds it into a full queryPath with the targetUrl for the given WMS.
        String fullQuery = queryNullCheck(targetUrl, query);
        handleRequest(exchange, fullQuery);
    }

    private static void handleDaf(HttpExchange exchange) throws IOException{
        // Converts the request to a string
        String query = exchange.getRequestURI().getRawQuery();
        String targetUrl = "https://api.dataforsyningen.dk/wms/MatGaeldendeOgForeloebigWMS_DAF";

        // Checks if the query is null and builds it into a full queryPath with the targetUrl for the given WMS.
        String fullQuery = queryNullCheck(targetUrl, query);
        handleRequest(exchange, fullQuery);
    }

    // Method for handling the requests aimed at the server
    private static void handleRequest(HttpExchange exchange, String targetUrl) throws IOException {
        // Opens the connection between the proxy server and the api endpoint
        HttpURLConnection conn = (HttpURLConnection) new URL(targetUrl).openConnection();
        conn.setRequestProperty("User-Agent", "JavaFX-Proxy");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(15000);

        // Adds headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", conn.getContentType());

        exchange.sendResponseHeaders(conn.getResponseCode(), 0);

        // Forwards the info from the api endpoint to the javafx application
        try (InputStream is = conn.getInputStream();
             OutputStream os = exchange.getResponseBody()) {
            is.transferTo(os);
        } finally {
            conn.disconnect();
            exchange.close();
        }
    }
}
