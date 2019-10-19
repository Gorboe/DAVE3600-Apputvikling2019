package gorboe.com.s319482mappe2.core;

import android.app.AlertDialog;
import android.content.Context;

import gorboe.com.s319482mappe2.R;
import gorboe.com.s319482mappe2.activities.create.CreateFriendActivity;

public class PhoneNumberValidator {
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
}
