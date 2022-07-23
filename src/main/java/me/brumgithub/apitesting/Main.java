package me.brumgithub.apitesting;

import me.brumgithub.mojangapi.api.MojangAPI;

public class Main {

    public static void main(String[] args) {

        MojangAPI mojangAPI = new MojangAPI();
        System.out.println(mojangAPI.uuidToUsername("e0d970e9-0bd4-47a4-b0c9-334eb96da37c"));

    }
}
