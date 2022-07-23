package me.brumgithub.mojangapi.api;

import me.brumgithub.mojangapi.http.HttpRequest;
import me.brumgithub.mojangapi.http.RequestMethod;
import me.brumgithub.mojangapi.http.ResponseModel;

public class MojangAPI {

    public boolean isUsernameAvailable(String username) {

        if (username.length() < 3 || username.length() > 16) {
            return false;
        }

        HttpRequest request = new HttpRequest(
                "https://account.mojang.com/available/minecraft/" + username,
                RequestMethod.GET
        );
        ResponseModel response = request.sendRequest();

        return response.getResponseData().isEmpty();
    }

    public String usernameToUuid(String username) {
        HttpRequest request = new HttpRequest(
                "https://api.mojang.com/user/profile/agent/minecraft/name/" + username,
                RequestMethod.GET
        );
        ResponseModel response = request.sendRequest();

        return response.getResponseData().get("id");
    }

    public String uuidToUsername(String uuid) {
        HttpRequest request = new HttpRequest(
                "https://api.mojang.com/user/profile/" + uuid,
                RequestMethod.GET
        );
        ResponseModel response = request.sendRequest();

        return response.getResponseData().get("name");
    }
}
