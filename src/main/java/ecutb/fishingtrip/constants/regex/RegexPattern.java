package ecutb.fishingtrip.constants.regex;

public class RegexPattern {
    public static final String EMAIL_REGEX_PATTERN = "^(\\D)+(\\w)*((\\.(\\w)+)?)+@(\\D)+(\\w)*((\\.(\\D)+(\\w)*)+)?(\\.)[a-z]{2,}$";
    public static final String PASSWORD_REGEX_PATTERN = "^(?=.*[0-9]+.*)(?=.*[a-zA-Z]+.*)[0-9a-zA-Z]{6,}$";
}
