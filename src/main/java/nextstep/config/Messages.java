package nextstep.config;

public enum Messages {
    LOGIN_NEEDS("Needs to be login"),
    EMPTY_TOKEN("token must have a value"),
    PASSWORD_INCORRECT("Password incorrect"),
    EMPTY_VALUE("Value must not be empty"),
    MEMBER_NOT_FOUND("Not found member"),
    THEME_NOT_FOUND("Not found theme"),
    SCHEDULE_NOT_FOUND("Not found schedule"),
    RESERVATION_NOT_FOUND("Not found reservation"),
    INVALID_TOKEN("Token don't match"),
    JWT_Exception("Token is not validate. ErrorMessage: "),
    ALREADY_USER("Already Registered User"),
    NOT_FOUND_SCHEDULE("Not Found Schedule. schedule Id: "),
    ALREADY_REGISTERED_RESERVATION("Reservation already exists"),
    ALREADY_REGISTERED_THEME("Theme already exists"),
    ALREADY_REGISTERED_SCHEDULE("Schedule already exists"),
    NOT_PERMISSION_DELETE("Not permission to delete"),
;

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
