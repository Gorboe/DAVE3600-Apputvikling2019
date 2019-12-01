package gorboe.com.s319482mappe3;

import android.app.AlertDialog;
import android.content.Context;

import java.util.regex.Pattern;

public class Validator {

    public static boolean validateCoordinate(String name, Context context){
        if(name.isEmpty()){
            displayWarningMessage("Koordinat kan ikke være et tomt felt.", context);
            return false;
        }

        if(!Pattern.matches("[0-9]{1,3}.[0-9]+", name) &&
           !Pattern.matches("-[0-9]{1,3}.[0-9]+", name) &&
           !Pattern.matches("[0-9]{1,3}", name) &&
           !Pattern.matches("-[0-9]{1,3}", name)){
            displayWarningMessage("Kan inneholde 1-3tall før punktum og så mange du ønsker etter punktum. " +
                    "Ekempel på gyldige koordinater: -32.323 eller 176.3232", context);
            return false;
        }

        return true;
    }

    public static boolean validateNotEmpty(String name, Context context){
        if(name.isEmpty()){
            displayWarningMessage("Kan ikke være et tomt felt.", context);
            return false;
        }

        if(!Pattern.matches("[A-ZÆØÅa-zæøå 0-9,.]+", name)){
            displayWarningMessage("Kan bare inneholde store bokstaver, små bokstaver og tall. Symboler er ikke lov!", context);
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
