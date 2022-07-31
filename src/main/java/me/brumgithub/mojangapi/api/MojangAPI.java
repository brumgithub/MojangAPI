package me.brumgithub.mojangapi.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import me.brumgithub.mojangapi.api.models.SimpleUserProfileModel;
import me.brumgithub.mojangapi.api.models.UserProfileModel;
import me.brumgithub.mojangapi.api.models.UsernameHistoryModel;
import me.brumgithub.mojangapi.api.models.objects.UsernameHistory;
import me.brumgithub.mojangapi.http.HttpRequest;
import me.brumgithub.mojangapi.http.RequestMethod;
import me.brumgithub.mojangapi.http.ResponseModel;

public class MojangAPI {

    private final Gson gson;

    public MojangAPI() {
        this.gson = new Gson();
    }

    //TODO @Deprecated //use the authenticated api instead (UserAPI)
    public boolean isUsernameAvailable(String username) {
        if (username.length() < 3 || username.length() > 16)
            return false;

        HttpRequest request = new HttpRequest(
                "https://account.mojang.com/available/minecraft/" + username,
                RequestMethod.GET
        );

        ResponseModel response = request.sendRequest();
        return response.getResponseData().isEmpty();
    }

    public String usernameToUuid(String username) {
        HttpRequest request = new HttpRequest(
                "https://api.mojang.com/users/profiles/minecraft/" + username,
                RequestMethod.GET
        );

        ResponseModel response = request.sendRequest();
        SimpleUserProfileModel model = gson.fromJson(response.getResponseData(), SimpleUserProfileModel.class);
        return model.id;
    }

    public String uuidToUsername(String uuid) {
        HttpRequest request = new HttpRequest(
                "https://api.mojang.com/user/profile/" + uuid,
                RequestMethod.GET
        );

        ResponseModel response = request.sendRequest();
        SimpleUserProfileModel model = gson.fromJson(response.getResponseData(), SimpleUserProfileModel.class);
        return model.name;
    }

    public UserProfileModel uuidToUserProfile(String uuid) {
        HttpRequest request = new HttpRequest(
                "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid,
                RequestMethod.GET
        );

        ResponseModel response = request.sendRequest();
        UserProfileModel model = gson.fromJson(response.getResponseData(), UserProfileModel.class);
        return model;
    }

    public UsernameHistoryModel uuidToNameHistory(String uuid) {
        HttpRequest request = new HttpRequest(
                "https://api.mojang.com/user/profiles/" + uuid + "/names",
                RequestMethod.GET
        );

        ResponseModel response = request.sendRequest();
        JsonArray jsonArray = gson.fromJson(response.getResponseData(), JsonArray.class);
        UsernameHistoryModel model = new UsernameHistoryModel();
        jsonArray.forEach(jsonElement -> model.usernameHistory.add(gson.fromJson(jsonElement, UsernameHistory.class)));
        return model;
    }


}
