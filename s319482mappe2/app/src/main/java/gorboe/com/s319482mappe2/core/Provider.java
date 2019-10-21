package gorboe.com.s319482mappe2.core;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class Provider extends ContentProvider {

    public final static String PROVIDER = "gorboe.com.s319482mappe2";
    private static final int RESTAURANT = 1;
    private static final int MULTIPLE_RESTAURANT = 2;
    private DBHandler dbHandler;

    //DB columns
    public static final String RESTAURANT_NAME = "name";
    private static String RESTAURANT_ADDRESS = "address";
    private static String RESTAURANT_NUMBER = "number";
    private static String RESTAURANT_TYPE = "type";

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
        if (uriMatcher.match(uri) == RESTAURANT) {
            cur = dbHandler.getRestaurants();
            return cur;
        } else {
            cur = dbHandler.getRestaurants();
            return cur;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        //VALIDATION OF INPUT FROM OTHER PROJECTS
        if(!Validator.validateName(String.valueOf(values.get(RESTAURANT_NAME)), null)){
            return null;
        }else if(!Validator.validateAddress(String.valueOf(values.get(RESTAURANT_ADDRESS)), null)){
            return null;
        }else if(!Validator.validatePhoneNumber(String.valueOf(values.get(RESTAURANT_NUMBER)), null)){
            return null;
        }else if(!Validator.validateType(String.valueOf(values.get(RESTAURANT_TYPE)), null)){
            return null;
        }

        long id = dbHandler.addRestaurant(values);
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //NOT IMPLEMENTED
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        //NOT IMPLEMENTED
        return 0;
    }
}
