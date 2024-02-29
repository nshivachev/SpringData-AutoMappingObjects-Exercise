package com.softuni.gamestore.constants;

public enum Validations {
    ;
    public static final String EMAIL_PATTERN = "^([a-zA-Z0-9_!#$%&'*+=?``{|}~^.-]+)@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,5})$";
    public static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";

    public static final String EMAIL_NOT_VALID_MESSAGE = "Incorrect email";
    public static final String USERNAME_OR_PASSWORD_NOT_VALID_MESSAGE = "Incorrect username / password";
    public static final String PASSWORDS_MISS_MATCH_MESSAGE = "Passwords are not matching";

    public static final String COMMAND_NOT_FOUND_MESSAGE = "Command not found";
    public static final String EMAIL_ALREADY_EXISTS_MESSAGE = "Email already exists";

    public static final String GAME_TITLE_NOT_VALID_MESSAGE = "Not a valid game title";
    public static final String GAME_TRAILER_ID_NOT_VALID_MESSAGE = "Trailer ID should be exactly 11 symbols";
    public static final String GAME_IMAGE_THUMBNAIL_NOT_VALID_MESSAGE = "Link should begin with http:// or https://";
    public static final String GAME_SIZE_NOT_VALID_MESSAGE = "Size should be positive number";
    public static final String GAME_PRICE_NOT_VALID_MESSAGE = "Price should be positive number";
    public static final String GAME_DESCRIPTION_NOT_VALID_MESSAGE = "Description should be at least 20 symbols";
    public static final String GAME_OPERATIONS_MISSING_RIGHTS_MESSAGE = "Missing rights";
    public static final String NOT_LOGGED_MESSAGE = "Log into your account";

    public static final String NO_GAME_ID_MESSAGE = "No game found with that id";
    public static final String NO_GAME_TITLE_MESSAGE = "No game found with that title";
    public static final String EMPTY_STORE_MESSAGE = "Currently there is no any games in the store";
}
