package me.brumgithub.mojangapi.api;

import com.google.gson.Gson;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import me.brumgithub.mojangapi.api.models.UsernameAvailableModel;
import me.brumgithub.mojangapi.http.HeaderModel;
import me.brumgithub.mojangapi.http.HttpRequest;
import me.brumgithub.mojangapi.http.RequestMethod;
import me.brumgithub.mojangapi.http.ResponseModel;

public class UserAPI {

    private final String token;
    private final Gson gson;

    public UserAPI(String email, String password) {
        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
        MicrosoftAuthResult result;
        try {
            result = authenticator.loginWithCredentials(email, password);
        } catch (MicrosoftAuthenticationException e) {
            throw new RuntimeException(e);
        }

        this.token = result.getAccessToken();
        this.gson = new Gson();
    }


    //availability
    public UsernameAvailableModel.Status getUsernameAvailability(String username) {
        if (username.length() < 3 || username.length() > 16)
            return UsernameAvailableModel.Status.NOT_ALLOWED;

        HeaderModel headers = new HeaderModel();
        headers.addHeader("Authorization", "Bearer " + token);
        HttpRequest request = new HttpRequest(
                "https://api.minecraftservices.com/minecraft/profile/name/"+username+"/available",
                RequestMethod.GET,
                headers
        );

        ResponseModel response = request.sendRequest();
        UsernameAvailableModel model = gson.fromJson(response.getResponseData(), UsernameAvailableModel.class);
        return UsernameAvailableModel.Status.valueOf(model.status);
    }

    public boolean isUsernameAvailable(String username) {
        if (username.length() < 3 || username.length() > 16)
            return false;

        HeaderModel headers = new HeaderModel();
        headers.addHeader("Authorization", "Bearer " + token);
        HttpRequest request = new HttpRequest(
                "https://api.minecraftservices.com/minecraft/profile/name/"+username+"/available",
                RequestMethod.GET,
                headers
        );

        ResponseModel response = request.sendRequest();
        UsernameAvailableModel model = gson.fromJson(response.getResponseData(), UsernameAvailableModel.class);
        return model.status.equals("AVAILABLE");
    }
}
