package com.gbc.holidayhype.common;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


import com.gbc.holidayhype.model.Favorite;
import com.gbc.holidayhype.model.IsFavorite;
import com.gbc.holidayhype.model.RoomModel;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    String TAG = "DatabaseHandler";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "HH_DB";

    // Contacts table name
    private static final String FAVORITE_TABLE = "Favorite_Table";
    private static final String ISFAVORITE_TABLE = "IsFavorite_Table";

    // Booking Table
    private static final String BOOKING_TABLE = "Booking_Table";
    private static final String ISBOOKING_TABLE = "IsBooking_Table";

    // Table Contacts
    private static final String FAVORITE_ID = "id";
    private static final String FAVORITE_TITLE = "title";
    private static final String FAVORITE_TYPE = "type";
    private static final String FAVORITE_DESCRIPTION = "description";
    private static final String FAVORITE_SRC = "src";
    private static final String FAVORITE_NAME = "name";
    private static final String FAVORITE_NID = "nid";
    private static final String FAVORITE_PAGE = "page";
    private static final String FAVORITE_LATITUDE = "latitude";
    private static final String FAVORITE_LONGITUDE = "longitude";
    private static final String FAVORITE_TIME = "time";

    private static final String ISFAVORITE_ID = "id";


    private static final String BOOKINGINCRE_ID = "id";
    private static final String BOOKING_ID = "booking_id";
    private static final String BOOKING_NAME = "name";
    private static final String BOOKING_TYPE = "type";
    private static final String BOOKING_DESCRIPTION = "description";
    private static final String BOOKING_IMGURL = "imageUrl";
    private static final String BOOKING_PRICE = "price";
    private static final String BOOKING_ADDRESS = "address";
    private static final String BOOKING_LATITUDE = "latitude";
    private static final String BOOKING_LONGITUDE = "longitude";
    private static final String BOOKING_DATE = "date";
    private static final String BOOKING_NOOFROOMS = "noOfRooms";
    private static final String BOOKING_PHONE = "phone";
    private static final String BOOKING_WEBSITE = "website";
    private static final String BOOKING_BOOKING = "booking";
    private static final String BOOKING_BOOKINGDAYS = "bookingDays";


//        private static final String ISFAVORITE_ID = "id";


    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_favorite_table = "CREATE TABLE IF NOT EXISTS "
                + FAVORITE_TABLE + " ( " + FAVORITE_ID + " INTEGER PRIMARY KEY,"
                + FAVORITE_TITLE + " Text , " + FAVORITE_TIME + " Text , " + FAVORITE_PAGE + " Text , " + FAVORITE_LATITUDE + " Text , " + FAVORITE_LONGITUDE + " Text , " + FAVORITE_NAME + " Text , " + FAVORITE_NID + " Text , " + FAVORITE_TYPE
                + " Text NOT NULL ,"
                + FAVORITE_DESCRIPTION + " Text,"
                + FAVORITE_SRC + " Text)";

        db.execSQL(create_favorite_table);

        String create_booking_table = "CREATE TABLE IF NOT EXISTS "
                + BOOKING_TABLE + " ( " + BOOKINGINCRE_ID + " INTEGER PRIMARY KEY,"
                + BOOKING_ID + " Text , " + BOOKING_NAME + " Text , " + BOOKING_ADDRESS + " Text , " + BOOKING_DESCRIPTION + " Text , " + BOOKING_PRICE + " Text , " + BOOKING_TYPE + " Text , " + BOOKING_LATITUDE + " Text , " + BOOKING_LONGITUDE + " Text , " + BOOKING_DATE
                + " Text NOT NULL ,"
                + BOOKING_NOOFROOMS + " Text,"
                + BOOKING_IMGURL + " Text,"
                + BOOKING_PHONE + " Text,"
                + BOOKING_WEBSITE + " Text,"
                + BOOKING_BOOKING + " Text,"
                + BOOKING_BOOKINGDAYS + " Text)";

        db.execSQL(create_booking_table);

        String create_Isafavorite_table = "CREATE TABLE IF NOT EXISTS "
                + ISFAVORITE_TABLE + " ( " + ISFAVORITE_ID + " INTEGER PRIMARY KEY,"
                + FAVORITE_PAGE + " Text , " + FAVORITE_NID + " Text)";

        db.execSQL(create_Isafavorite_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);

    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public void deleteTableContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + BOOKING_TABLE);
    }


    String header = "0";


    // Adding new chat
    public void addChat(Favorite fav) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(FAVORITE_TITLE, fav.getTitle()); // sender Name
        values.put(FAVORITE_TYPE, fav.getType()); // receiver Phone
        values.put(FAVORITE_DESCRIPTION, fav.getDescription());
        values.put(FAVORITE_SRC, fav.getSrc());
        values.put(FAVORITE_NAME, fav.getName());
        values.put(FAVORITE_NID, fav.getNid());
        values.put(FAVORITE_PAGE, fav.getPage());
        values.put(FAVORITE_LATITUDE, fav.getLatitude());
        values.put(FAVORITE_LONGITUDE, fav.getLongitude());
        values.put(FAVORITE_TIME, fav.getTime());
        db.insert(FAVORITE_TABLE, null, values);
        db.close(); // Closing database connection
    }

    public void addBooking(RoomModel fav) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(BOOKING_ID, fav.getId()); // sender Name
        values.put(BOOKING_NAME, fav.getName()); // receiver Phone
        values.put(BOOKING_ADDRESS, fav.getAddress());
        values.put(BOOKING_DESCRIPTION, "Hello"); // sender Name
        values.put(BOOKING_PRICE, fav.getPrice()); // receiver Phone
        values.put(BOOKING_TYPE, fav.getType());
        values.put(BOOKING_DATE, fav.getDate());
        values.put(BOOKING_NOOFROOMS, fav.getNoOfRooms());
        values.put(BOOKING_IMGURL, fav.getImageUrl());
        values.put(BOOKING_LATITUDE, fav.getLatitude());
        values.put(BOOKING_LONGITUDE, fav.getLongitude());
        values.put(BOOKING_PHONE, fav.getPhone());
        values.put(BOOKING_WEBSITE, fav.getWebsite());
        values.put(BOOKING_BOOKING, fav.getBooking());
        values.put(BOOKING_BOOKINGDAYS, fav.getBookingDays());
        db.insert(BOOKING_TABLE, null, values);

        db.close(); // Closing database connection
    }
    // Adding new chat
    public void addFav(IsFavorite fav) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(FAVORITE_PAGE, fav.getPage()); // receiver Phone

        values.put(FAVORITE_NID, fav.getNid());
        db.insert(ISFAVORITE_TABLE, null, values);

        db.close(); // Closing database connection
    }

    public boolean IsFavExist(String page, String nid) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + ISFAVORITE_TABLE
                + " where " + FAVORITE_PAGE + "='" + page + "'" + "AND " + FAVORITE_NID + "='" + nid + "'", null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return true;
        }
        return false;
    }


    public void removeISFavorite(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(ISFAVORITE_TABLE, BOOKING_ID + "=" + id, null);
            // Log.w("Database", "Item deleted");
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }

    public void removeFavorite(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(FAVORITE_TABLE, FAVORITE_NID + "=" + id, null);
            // Log.w("Database", "Item deleted");
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }
    public void removeBooking(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(BOOKING_TABLE, FAVORITE_NID + "=" + id, null);
            // Log.w("Database", "Item deleted");
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            if (db != null)
                db.close();
        }
    }
    @SuppressLint("Range")
    public ArrayList<Favorite> getAllFavorite() {

        ArrayList<Favorite> favoriteList = new ArrayList<Favorite>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + FAVORITE_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Favorite favorite = new Favorite();
                favorite.setTitle(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_TITLE)));
                favorite.setType(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_TYPE)));
                favorite.setDescription(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_DESCRIPTION)));
                favorite.setSrc(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_SRC)));
                favorite.setName(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_NAME)));
                favorite.setNid(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_NID)));
                favorite.setPage(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_PAGE)));

                favorite.setLatitude(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_LATITUDE)));
                favorite.setLongitude(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_LONGITUDE)));
                favorite.setTime(cursor.getString(cursor
                        .getColumnIndex(FAVORITE_TIME)));

                favoriteList.add(favorite);

            } while (cursor.moveToNext());

        }
        db.close();
        // return contact list
        return favoriteList;
    }


    @SuppressLint("Range")
    public ArrayList<RoomModel> getAllBooking() {

        ArrayList<RoomModel> bookingList = new ArrayList<RoomModel>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + BOOKING_TABLE ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                RoomModel roomModel = new RoomModel();
                roomModel.setId(cursor.getString(cursor
                        .getColumnIndex(BOOKING_ID)));
                roomModel.setName(cursor.getString(cursor
                        .getColumnIndex(BOOKING_NAME)));
                roomModel.setAddress(cursor.getString(cursor
                        .getColumnIndex(BOOKING_ADDRESS)));
                roomModel.setDescription(cursor.getString(cursor
                        .getColumnIndex(BOOKING_DESCRIPTION)));
                roomModel.setPrice(cursor.getString(cursor
                        .getColumnIndex(BOOKING_PRICE)));
                roomModel.setType(cursor.getString(cursor
                        .getColumnIndex(BOOKING_TYPE)));
                roomModel.setDate(cursor.getString(cursor
                        .getColumnIndex(BOOKING_DATE)));
                roomModel.setLatitude(cursor.getString(cursor
                        .getColumnIndex(BOOKING_LATITUDE)));
                roomModel.setLongitude(cursor.getString(cursor
                        .getColumnIndex(BOOKING_LONGITUDE)));

                roomModel.setNoOfRooms(cursor.getString(cursor
                        .getColumnIndex(BOOKING_NOOFROOMS)));
                roomModel.setImageUrl(cursor.getString(cursor
                        .getColumnIndex(BOOKING_IMGURL)));
                roomModel.setPhone(cursor.getString(cursor
                        .getColumnIndex(BOOKING_PHONE)));
                roomModel.setWebsite(cursor.getString(cursor
                        .getColumnIndex(BOOKING_WEBSITE)));
                roomModel.setBooking(cursor.getString(cursor
                        .getColumnIndex(BOOKING_BOOKING)));
                roomModel.setBookingDays(cursor.getInt(cursor
                        .getColumnIndex(BOOKING_BOOKINGDAYS)));
                bookingList.add(roomModel);

            } while (cursor.moveToNext());

        }
        db.close();
        // return contact list
        return bookingList;
    }


}
