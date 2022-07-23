package me.brumgithub.mojangapi.http;

import java.util.HashMap;
import java.util.Map;

public class ResponseModel {

    private final Map<String, String> responseData = new HashMap<>();
    private final int status;

    public ResponseModel(String responseData, int status) {
        System.out.println(responseData);
        responseData = responseData.replace(" ", "").replace("{", "").replace("}", "");
        for (String str : responseData.split(",")) {
            String[] keyValue = str.split(":");
            this.responseData.put(keyValue[0].replace("\"", ""), keyValue[1].replace("\"", ""));
        }
        this.status = status;
    }

    public Map<String, String> getResponseData() {
        return responseData;
    }

    public int getStatus() {
        return status;
    }
}
