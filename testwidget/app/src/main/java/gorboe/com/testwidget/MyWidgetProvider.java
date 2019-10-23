package gorboe.com.testwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.Random;

public class MyWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        ComponentName thisWidget = new ComponentName(context,
                MyWidgetProvider.class);
// Lager et Widget-objekt
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
// Henter ID til alle Widgeter på hjemmeskjerm
//Traverserer lista
        for (int widgetId : allWidgetIds) {
            //Finner et tilfeldig tall
            int number = (new Random().nextInt(100));
//Lager et RemoteView lik Widget-viewet
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.widget_layout);
            //Skriver til logg
            Log.d("Widget eksempel", String.valueOf(number));
            //Oppdaterer RemoteView
            remoteViews.setTextViewText(R.id.update, String.valueOf(number));
//lager kode som skal utføres ved klikk på widget
            Intent intent = new Intent(context, MyWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            //Setter knappelytter
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
//Oppdaterer Widget-view
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
