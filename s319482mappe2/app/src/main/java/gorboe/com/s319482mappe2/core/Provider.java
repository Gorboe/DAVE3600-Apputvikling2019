package gorboe.com.s319482mappe2.core;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Provider extends ContentProvider {

    public final static String PROVIDER = "gorboe.com.s319482mappe2";
    private static final int RESTAURANT = 1;
    private static final int MULTIPLE_RESTAURANT = 2;
    private DBHandler dbHandler;

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/restaurant");
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "restaurant", RESTAURANT);
        uriMatcher.addURI(PROVIDER, "restaurant/#", MULTIPLE_RESTAURANT);
    }

    @Override
    public boolean onCreate() {
        dbHandler = new DBHandler(getContext());
        return false;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MULTIPLE_RESTAURANT:
                return "vnd.android.cursor.dir/vnd.gorboe.restaurant";
            case RESTAURANT:
                return "vnd.android.cursor.item/vnd.gorboe.restaurant";
            default:
                throw new
                        IllegalArgumentException("Ugyldig URI" + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cur = null;
        System.out.println("TESST IN QUERY PROVIDER: " + uriMatcher.match(uri));
        if (uriMatcher.match(uri) == RESTAURANT) {
            cur = dbHandler.restQuery();
            return cur;
        } else {
            System.out.println("MULTIPLE?");
            cur = dbHandler.restQuery();
            return cur;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = dbHandler.testAddRest(values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        System.out.println("DELETE: " + uriMatcher.match(uri));
        /*
        if (uriMatcher.match(uri) == RESTAURANT) {
            dbHandler.testDeleteRest(uri.getPathSegments().get(1), selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if (uriMatcher.match(uri) == MULTIPLE_RESTAURANT) {
            dbHandler.testDeleteRest();
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }*/
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        System.out.println("UPDATE: " + uriMatcher.match(uri));
        /*
        if (uriMatcher.match(uri) == RESTAURANT) {
            dbHandler.testUpdateRest(values, uri.getPathSegments().get(1));
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        if (uriMatcher.match(uri) == MULTIPLE_RESTAURANT) {
            dbHandler.testUpdateRest();
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;
        }*/
        return 0;
    }
}
