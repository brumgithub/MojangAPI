package me.brumgithub.mojangapi.api.models;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

class Properties {
    public String name;
    public String value;
    public String signature;
}

class TextureData {
    public long timestamp;
    public String profileId;
    public String profileName;
    public Textures textures;
}

class Textures {
    public Skin SKIN;
    public Cape CAPE;
}

class Skin {
    public String url;
    public Metadata metadata;
}

class Metadata {
    public String model;
}

class Cape {
    public String url;
}

public class UserProfileModel {
    public String id;
    public String name;
    private TextureData textureData;
    public boolean legacy;

    private Properties[] properties;
    private boolean textureDataInitialized = false;

    private void initTextureData() {
        if (!textureDataInitialized) {
            byte[] decodedValue = Base64.getDecoder().decode(properties[0].value);
            String jsonValue = new String(decodedValue, StandardCharsets.UTF_8);
            textureData = new Gson().fromJson(jsonValue, TextureData.class);
            textureDataInitialized = true;
        }
    }

    public String getSkinUrl() {
        initTextureData();
        return textureData.textures.SKIN.url;
    }

    public enum SkinType {
        CLASSIC,
        SLIM
    }

    public SkinType getSkinType() {
        initTextureData();
        return textureData.textures.SKIN.metadata.model.equals("slim") ? SkinType.SLIM : SkinType.CLASSIC;
    }

    public String getCapeUrl() {
        initTextureData();
        return textureData.textures.CAPE.url;
    }


}