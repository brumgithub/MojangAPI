package me.brumgithub.mojangapi.http;

import java.util.HashMap;
import java.util.Map;

public class HeaderModel {
    private final Map<String, String> headers = new HashMap<>();

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

}
