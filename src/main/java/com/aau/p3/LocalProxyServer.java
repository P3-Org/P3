package com.aau.p3;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * The class below is intended to set up a local server to use for api calls in case of CORS issues
 */
public class LocalProxyServer {
    private static HttpServer server;
    // Maps the path on the request to a base URL
    private static Map<String, String> Route
            = Map.of("/wms-dmh-proxy", "https://api.dataforsyningen.dk/wms/dhm",
            "/wms-hip-proxy", "https://api.dataforsyningen.dk/hip_dtg_10m_100m",
            "/wms-daf-proxy", "https://api.dataforsyningen.dk/wms/MatGaeldendeOgForeloebigWMS_DAF",
            "/arc_gis-nst/export", "https://gis.nst.dk/server/rest/services/ekstern/KDI_KystAtlas/MapServer/export");

    /**
     * Method for setting up the necessary tools for starting a proxy server
     * @param port to open proxy server on
     */
    public static void startProxy(int port) {
        try {
            // Creates a httpserver which binds to a socket-address object with the specified port number
            server = HttpServer.create(new InetSocketAddress(port), 0);

            // The HTTP context specifies what to do when receiving requests on the given port + path
            server.createContext("/", LocalProxyServer::handleURL);

            // Sets 4 threads available for requests for the server
            server.setExecutor(Executors.newFixedThreadPool(4));
            server.start();
            System.out.println("Local proxy running at http://localhost:" + port + "/wms-proxy");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Server stop method so we can stop the server manually
     */
    public static void stopProxy() {
        server.stop(0);
    }

    /**
     * Helper method that checks if the query path is null,
     * and after combines the targetUrl and query for the fullQuery.
     * @param targetUrl is the URL we are sending a query on
     * @param query path
     * @return both the targetUrl and query in one string
     */
    private static String queryNullCheck(String targetUrl, String query){
        return (query == null) ? targetUrl : targetUrl + "?" + query;
    }

    /**
     * Dynamic url handler that works for all paths that are specified in the map "Route"
     * @param exchange the exchange between the server and the targetUrl
     * @throws IOException in out exception
     */
    private static void handleURL(HttpExchange exchange)  throws IOException{
        String path = exchange.getRequestURI().getPath();

        // Get the base url from the map
        String targetUrl = Route.get(path);

        // Closes the exchange if the requests is not on a path we know
        if (targetUrl == null){
            exchange.close();
        }

        String query = exchange.getRequestURI().getRawQuery();
        String fullQuery = queryNullCheck(targetUrl, query);
        handleRequest(exchange, fullQuery);
    }

    /**
     * Method for handling the GET requests
     * @param exchange the exchange between the server and the targetUrl
     * @param targetUrl url to open a connection on
     * @throws IOException in out exception
     */
    private static void handleRequest(HttpExchange exchange, String targetUrl) throws IOException {
        // Opens the connection between the proxy server and the api endpoint
        HttpURLConnection conn = (HttpURLConnection) new URL(targetUrl).openConnection();
        conn.setRequestProperty("User-Agent", "JavaFX-Proxy");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(15000);

        // Adds CORS headers
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
