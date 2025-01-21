package agh.ics.oop.presenter;

public enum ErrorMessages {
    FILE_READ_ERROR("File read error occured"),
    FILE_WRITE_ERROR("File write error occured"),
    OPTION_PARSE_ERROR("Option parse error occured"),
    INTERNAL_ERROR("Internal error occured"),
    WRONG_VALUES("Please provide valid values"),
    DEFAULTS_LOADING("Default settings could not be loaded"),;

    String message;
    ErrorMessages(String message) {
        this.message = message;
    }
}
