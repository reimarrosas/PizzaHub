package me.reimarrosas.pizzahub.helper;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {

    public enum CredentialType {SIGN_IN, SIGN_UP}

    public static boolean isEmailValid(String inputEmail) {
        return inputEmail != null && Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches();
    }

    public static boolean isPasswordValid(String inputPassword) {
        String input = inputPassword.trim();

        String passwordRegex = "^(?=.*[A-Z])(?=.*[@_.]).*$";
        Pattern p = Pattern.compile(passwordRegex);
        Matcher m = p.matcher(input);

        return input.length() >= 8 && m.matches();
    }

    public static boolean isVerifyPasswordValid(String inputPassword, String inputVerifyPassword) {
        return inputPassword != null
                && inputPassword.equals(inputVerifyPassword);
    }

    public static String isCredentialsValid(
            String inputEmail, String inputPassword,
            String inputVerifyPassword, CredentialType credentialType
    ) {
        String error = null;

        if (!isEmailValid(inputEmail)) {
            error = "Email";
        } else if (!isPasswordValid(inputPassword)) {
            error = "Password";
        } else if (credentialType == CredentialType.SIGN_UP
                && !isVerifyPasswordValid(inputPassword, inputVerifyPassword)
        ) {
            error = "Verify Password";
        }

        return error == null ? "" : error + " Invalid!";
    }

}
