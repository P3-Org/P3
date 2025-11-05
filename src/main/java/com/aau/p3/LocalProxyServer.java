package com.aau.p3;

// httpserver imports
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.concurrent.Executors;

public class LocalProxyServer {

    public static void startProxy(int port) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/wms-proxy", LocalProxyServer::handleRequest);
            server.setExecutor(Executors.newFixedThreadPool(4));
            server.start();
            System.out.println("Local proxy running at http://localhost:" + port + "/wms-proxy");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getRawQuery();

        // Build full WMS URL
        String targetUrl = "https://api.dataforsyningen.dk/wms/dhm";
        if (query != null && !query.isEmpty()) {
            targetUrl += "?" + query;
        }

        System.out.println("Fetching: " + targetUrl);

        HttpURLConnection conn = (HttpURLConnection) new URL(targetUrl).openConnection();
        conn.setRequestProperty("User-Agent", "JavaFX-Proxy");
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(15000);

        // Forward the response
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Content-Type", conn.getContentType());

        exchange.sendResponseHeaders(conn.getResponseCode(), 0);
        try (InputStream is = conn.getInputStream();
             OutputStream os = exchange.getResponseBody()) {
            is.transferTo(os);
        } finally {
            conn.disconnect();
            exchange.close();
        }
    }
}
