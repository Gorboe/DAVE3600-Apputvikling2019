package gorboe.com.s319482mappe2.core;

import android.app.AlertDialog;
import android.content.Context;
import java.util.regex.Pattern;

import gorboe.com.s319482mappe2.R;

public class Validator {

    public static boolean validatePhoneNumber(String number, Context context){
        //This allows user to enter any phone number with +xx prefix.
        // and allows to use emulator numbers, requires +15 555 21 xxxx total of 11 digits and a + sign
        if(Pattern.matches("^[+][0-9]{10}$", number) ||
           Pattern.matches("^([+]1555521)[0-9]{4}", number)){
            return true;
        }

        //without +xx
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
            displayWarningMessage("Telefon nummeret som ble oppgitt er ikke gyldig. Nummeret må ha 8 siffer, eller ha +xx etterfulgt av 8 siffer.", context);
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
        if(context == null){
            return; //this allows me to pass context = null when i need the values check, but not the display msg
        }
        new AlertDialog.Builder(context)
                .setTitle("Advarsel")
                .setIcon(R.drawable.ic_warning_yellow)
                .setMessage(message)
                .show();
    }
}
