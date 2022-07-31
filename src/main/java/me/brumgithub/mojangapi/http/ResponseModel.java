package me.brumgithub.mojangapi.http;

import com.sun.jdi.request.InvalidRequestStateException;

public class ResponseModel {

    private final String responseData;
    private final int status;

    public ResponseModel(String responseData, int status) {
        this.responseData = responseData;
        this.status = status;
        if (status > 399) {
            throw new InvalidRequestStateException("Error: " + responseData);
        }
    }

    public String getResponseData() {
        return responseData;
    }

    public int getStatus() {
        return status;
    }
}
