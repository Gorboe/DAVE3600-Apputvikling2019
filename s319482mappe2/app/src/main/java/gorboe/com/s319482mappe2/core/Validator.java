package gorboe.com.s319482mappe2.core;

import android.app.AlertDialog;
import android.content.Context;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gorboe.com.s319482mappe2.R;

public class Validator {

    public static boolean validatePhoneNumber(String number, Context context){
        int phonenr;
        try{
            phonenr = Integer.parseInt(number);
        }catch (Exception e){
            new AlertDialog.Builder(context)
                    .setTitle("Advarsel")
                    .setIcon(R.drawable.ic_warning_yellow_24dp)
                    .setMessage("Telefon nummeret som ble oppgitt er ikke gyldig. Nummeret kan ikke inneholde bokstaver eller symboler.")
                    .show();
            return false;
        }
        int overLimit = 100000000;
        int underLimit = 9999999;
        if(!(phonenr < overLimit && phonenr > underLimit)){
            new AlertDialog.Builder(context)
                    .setTitle("Advarsel")
                    .setIcon(R.drawable.ic_warning_yellow_24dp)
                    .setMessage("Telefon nummeret som ble oppgitt er ikke gyldig. Nummeret må ha 8 siffer.")
                    .show();
            return false;
        }
        return true;
    }

    public static boolean validateName(String name, Context context){
        if(name.isEmpty()){
            new AlertDialog.Builder(context)
                    .setTitle("Advarsel")
                    .setIcon(R.drawable.ic_warning_yellow_24dp)
                    .setMessage("Navn kan ikke være et tomt felt.")
                    .show();
            return false;
        }

        //First letter big rest small: ^[A-ZÆØÅ][a-zæøå]+$
        //A-Å små og store med mellomrom: [A-ZÆØÅa-zæøå ]+
        if(!Pattern.matches("[A-ZÆØÅa-zæøå ]+", name)){
            new AlertDialog.Builder(context)
                    .setTitle("Advarsel")
                    .setIcon(R.drawable.ic_warning_yellow_24dp)
                    .setMessage("Navn kan bare inneholde store og små bokstaver. Tall og symboler er ikke lov!")
                    .show();
            return false;
        }

        return true;
    }
}
