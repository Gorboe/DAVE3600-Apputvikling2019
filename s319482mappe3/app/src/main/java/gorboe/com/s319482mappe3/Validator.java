package gorboe.com.s319482mappe3;

import android.app.AlertDialog;
import android.content.Context;

import java.util.regex.Pattern;

public class Validator {

    //todo: coordinates form: xx.xxxxx or x.xxxx or xxx.xxxx only numbers and 1 .


    public static boolean validateNotEmpty(String name, Context context){
        if(name.isEmpty()){
            displayWarningMessage("Navn kan ikke være et tomt felt.", context);
            return false;
        }

        if(!Pattern.matches("[A-ZÆØÅa-zæøå 1-9,.]+", name)){
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
