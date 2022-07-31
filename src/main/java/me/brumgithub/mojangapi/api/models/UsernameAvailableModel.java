package me.brumgithub.mojangapi.api.models;

public class UsernameAvailableModel {
    public String status;

    public enum Status {
        AVAILABLE,
        DUPLICATE,
        NOT_ALLOWED
    }
}
