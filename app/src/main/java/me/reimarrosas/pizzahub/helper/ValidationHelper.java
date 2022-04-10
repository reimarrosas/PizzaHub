package me.reimarrosas.pizzahub.helper;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {

    public enum CredentialType {SIGN_IN, SIGN_UP}

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

    private static boolean isEmailValid(String inputEmail) {
        return inputEmail != null &&
                Patterns.EMAIL_ADDRESS.matcher(inputEmail.toLowerCase().trim()).matches();
    }

    private static boolean isPasswordValid(String inputPassword) {
        String input = inputPassword.trim();

        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        Pattern p = Pattern.compile(passwordRegex);
        Matcher m = p.matcher(input);

        return input.length() >= 8 && m.matches();
    }

    private static boolean isVerifyPasswordValid(String inputPassword, String inputVerifyPassword) {
        return inputPassword != null
                && inputPassword.equals(inputVerifyPassword);
    }

}
