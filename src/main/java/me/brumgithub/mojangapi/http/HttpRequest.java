package me.brumgithub.mojangapi.http;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpRequest {

    private final String url;
    private final RequestMethod requestMethod;
    private final HeaderModel headers;
    private final String body;

    public HttpRequest(String url, RequestMethod requestMethod, HeaderModel headers) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.headers = headers;
        this.body = null;
    }


    public HttpRequest(String url, RequestMethod requestMethod, String body) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.headers = null;
        this.body = body;
    }


    public HttpRequest(String url, RequestMethod requestMethod, HeaderModel headers, String body) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.headers = headers;
        this.body = body;
    }

    public HttpRequest(String url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.headers = null;
        this.body = null;
    }

    public ResponseModel sendRequest() {
        ResponseModel response = null;

        BufferedReader reader;
        String line;
        StringBuilder responseData = new StringBuilder();

        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //req setup
            connection.setRequestMethod(this.requestMethod.name());
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (headers != null) {
                for (Map.Entry<String, String> header : this.headers.getHeaders().entrySet()) {
                    connection.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            if (body != null) {
                connection.setDoOutput(true);
                OutputStream outStream = connection.getOutputStream();
                OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
                outStreamWriter.write(body);
                outStreamWriter.flush();
                outStreamWriter.close();
                outStream.close();
            }


            int status = connection.getResponseCode();

            reader =
                    status > 399 ?
                    new BufferedReader(new InputStreamReader(connection.getErrorStream())) :
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((line = reader.readLine()) != null) {
                responseData.append(line);
            }
            reader.close();

            response = new ResponseModel(responseData.toString(), status);
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
