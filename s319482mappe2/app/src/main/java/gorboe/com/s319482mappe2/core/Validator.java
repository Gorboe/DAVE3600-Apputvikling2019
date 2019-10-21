package gorboe.com.s319482mappe2.core;

import android.app.AlertDialog;
import android.content.Context;
import java.util.regex.Pattern;

import gorboe.com.s319482mappe2.R;

public class Validator {

    public static boolean validatePhoneNumber(String number, Context context){
        int phonenr;
        try{
            phonenr = Integer.parseInt(number);
        }catch (Exception e){
            displayWarningMessage("Telefon nummeret som ble oppgitt er ikke gyldig. Nummeret kan ikke inneholde bokstaver eller symboler.", context);
            return false;
        }
        int overLimit = 100000000;
        int underLimit = 9999999;
        if(!(phonenr < overLimit && phonenr > underLimit)){
            displayWarningMessage("Telefon nummeret som ble oppgitt er ikke gyldig. Nummeret må ha 8 siffer.", context);
            return false;
        }
        return true;
    }

    public static boolean validateName(String name, Context context){
        if(name.isEmpty()){
            displayWarningMessage("Navn kan ikke være et tomt felt.", context);
            return false;
        }

        //First letter big rest small: ^[A-ZÆØÅ][a-zæøå]+$
        //A-Å små og store med mellomrom: [A-ZÆØÅa-zæøå ]+
        if(!Pattern.matches("[A-ZÆØÅa-zæøå ]+", name)){
            displayWarningMessage("Navn kan bare inneholde store og små bokstaver. Tall og symboler er ikke lov!", context);
            return false;
        }

        //TODO: check for length??

        return true;
    }

    public static boolean validateAddress(String name, Context context){
        if(name.isEmpty()){
            displayWarningMessage("Adresse kan ikke være et tomt felt.", context);
            return false;
        }

        return true;
    }

    public static boolean validateType(String name, Context context){
        if(name.isEmpty()){
            displayWarningMessage("Type kan ikke være et tomt felt.", context);
            return false;
        }

        return true;
    }

    private static void displayWarningMessage(String message, Context context){
        new AlertDialog.Builder(context)
                .setTitle("Advarsel")
                .setIcon(R.drawable.ic_warning_yellow)
                .setMessage(message)
                .show();
    }
}
