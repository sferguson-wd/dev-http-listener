package com.windfall.devhttplistener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import fi.iki.elonen.NanoHTTPD;

public class App extends NanoHTTPD
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static String ENV_PORT = System.getenv("DEV_HTTP_PORT");
    private static int DEFAULT_PORT = 8080;
    private static int PORT = ENV_PORT == null ? DEFAULT_PORT : Integer.parseInt(ENV_PORT);
    private static String DEFAULT_MIME_TYPE = "application/json";

    public App() throws IOException {
        super(PORT);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        logger.info("Listening on http://localhost:{}", PORT);
    }

    public static void main(String[] args) {
        try {
            new App();
        } catch (IOException ioe) {
            logger.error("Failed to start server", ioe);
        }
    }

    @Override
    public Response serve(final IHTTPSession session) {
        logger.info("Received {} request from {}", session.getMethod(), session.getRemoteIpAddress());

        Response response;
        final Map<String, Object> request = new HashMap<>();
        try {
            session.parseBody(new HashMap<>());
            request.put("_requestType", session.getMethod());

            Map<String, List<String>> parameters = session.getParameters();
            request.put("_queryParameters", parameters);

            logger.debug("Request parameters: {}", parameters);

            final String serializedResponse = new Gson().toJson(request);
            response = newFixedLengthResponse(Response.Status.OK, DEFAULT_MIME_TYPE, serializedResponse);
        } catch (Exception ex) {
            response = newFixedLengthResponse(Response.Status.INTERNAL_ERROR, DEFAULT_MIME_TYPE,
                String.format("{error:\"%s\"}", ex.getMessage()));
        }

        return response;
    }
}
