package me.brumgithub.mojangapi.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {

    private final String url;
    private final RequestMethod requestMethod;
    private final HeaderModel headers;

    public HttpRequest(String url, RequestMethod requestMethod, HeaderModel headers) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.headers = headers;
    }

    public HttpRequest(String url, RequestMethod requestMethod) {
        this.url = url;
        this.requestMethod = requestMethod;
        this.headers = null;
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

            int status = connection.getResponseCode();

            reader =
                    status > 299 ?
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
