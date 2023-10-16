package com.mx.profuturo.grupo.launchermau.utils;

import android.content.pm.PackageManager;
import android.util.Patterns;

import androidx.annotation.NonNull;

import java.util.regex.Pattern;

public class ValidateField {

    public static boolean isPackageInstalled(String packageName, @NonNull PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isCURPValid(String CURP) {
        if (CURP == null) return false;
        String regex = "^([A-Z][AEIOUX][A-Z]{2}\\d{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[12]\\d|3[01])[HM](?:AS|B[CS]|C[CLMSH]|D[FG]|G[TR]|HG|JC|M[CNS]|N[ETL]|OC|PL|Q[TR]|S[PLR]|T[CSL]|VZ|YN|ZS)[B-DF-HJ-NP-TV-Z]{3}[A-Z\\d])(\\d)$";
        Pattern patron = Pattern.compile(regex);
        return patron.matcher(CURP).matches();
    }
    public static boolean isAlpha(String str) {
        if (str == null) return false;
        return !str.equals("") && str.matches("^[ÑA-Zña-z\\s]+[ÑA-Zña-z]+$(\\.0-9+)?");
    }

    public static boolean isNumeric(String str) {
        if (str == null) return false;
        return !str.equals("") && str.matches("^[0-9]*$");
    }


    public static boolean  isValidateEmail(String email){
        if (email == null) return false;
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isAlfaNumber(String str) {
        if (str == null) return false;
        String expression = "^\\S.*\\S$";
        return str.matches(expression);
    }
}
