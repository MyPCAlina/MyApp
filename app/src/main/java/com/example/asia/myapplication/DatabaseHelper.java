package com.example.asia.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.webkit.DateSorter;

import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "API.db";

    public static final String TABLE_RECORD = "RECORD_TABLE";
    public static final String REC_ID_RECORD = "ID_RECORD";
    public static final String REC_NAME = "NAME";
    public static final String REC_ID_CATEGORY = "ID_CATEGORY";
    public static final String REC_AMOUNT = "AMOUNT";
    public static final String REC_TYPE = "TYPE";
    public static final String REC_DATE = "DATE";
    public static final String REC_ID_TARGET = "ID_TARGET";

    public static final String TABLE_CATEGORY = "CATEGORY_TABLE";
    public static final String CAT_ID_CATEGORY = "ID_CATEGORY";
    public static final String CAT_NAME = "NAME";
    public static final String CAT_AMOUNT = "AMOUNT";
    public static final String CAT_BOUNDARY = "BOUNDARY";
    public static final String CAT_CREATED_AT = "CREATED_AT";

    public static final String TABLE_TARGET = "TARGET_TABLE";
    public static final String TARGET_ID_TARGET = "ID_TARGET";
    public static final String TARGET_TARGET = "TARGET";
    public static final String TARGET_AMOUNT = "AMOUNT";
    public static final String TARGET_LEAD_TIME = "LEAD_TIME";

    public static final String TABLE_GAMIFICATION = "GAMIFICATION";
    public static final String GAME_ID = "ID_GAMIFICATION";
    public static final String GAME_HISTORY_POINTS = "HISTORY_POINTS";
    public static final String GAME_STATE_POINTS = "STATE_POINTS";

    //databse
    private static final String TABLE_NAME = "CONTACTS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_USER = "USER";
    private static final String COLUMN_PASS = "PASS";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_RECORD + " (ID_RECORD INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,ID_CATEGORY TEXT,AMOUNT DECIMAL,TYPE TEXT,DATE DATE,ID_TARGET TEXT)");
        db.execSQL("create table " + TABLE_CATEGORY + " (ID_CATEGORY INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,AMOUNT DECIMAL,BOUNDARY DECIMAL,CREATED_AT DATE)");
        db.execSQL("create table " + TABLE_TARGET + " (ID_TARGET INTEGER PRIMARY KEY AUTOINCREMENT,TARGET TEXT,AMOUNT DECIMAL,LEAD_TIME DATE)");
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY,NAME TEXT,USER TEXT,PASS TEXT)");
        db.execSQL("create table " + TABLE_GAMIFICATION + "(ID_GAMIFICATION INTEGER PRIMARY KEY AUTOINCREMENT,HISTORY_POINTS TEXT,STATE_POINTS INTEGER)");
        // db.execSQL("alter table TABLE_RECORD ADD FOREIGN KEY (ID_CATEGORY) REFERENCES TABLE_CATEGORY(ID_CATEGORY)");
        // db.execSQL("alter table TABLE_RECORD ADD FOREIGN KEY (ID_TARGET) REFERENCES TABLE_TARGET(ID_TARGET)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TARGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMIFICATION);
        onCreate(db);

    }
    public void insertContact(Contact c)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from CONTACTS";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_USER, c.getUsername());
        values.put(COLUMN_PASS, c.getPass());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public String searchPass(String user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String quary = "select USER, PASS from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(quary,null);
        String a, b;
        b ="Not found";

        if(cursor.moveToFirst())
        {
            do{
                a = cursor.getString(0);


                if (a.equals(user))
                {
                    b = cursor.getString(1);
                    break;

                }


            }while(cursor.moveToNext());

        }
        return b;
    }

    public boolean insertDataRec(String name,String idCategory,String amount,String type,String date,String idTarget){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(REC_NAME,name);
        contentValues.put(REC_ID_CATEGORY,idCategory);
        contentValues.put(REC_AMOUNT, amount);
        contentValues.put(REC_TYPE, type);
        contentValues.put(REC_DATE, date);
        contentValues.put(REC_ID_TARGET, idTarget);
        long result = db.insert(TABLE_RECORD,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public boolean insertDataTarget(String target,String amount,String leadTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TARGET_TARGET, target);
        contentValues.put(TARGET_AMOUNT, amount);
        contentValues.put(TARGET_LEAD_TIME, leadTime);
        long result = db.insert(TABLE_TARGET,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_TARGET,null);
        return res;
    }
    public Cursor getAllCategory(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select NAME from "+TABLE_CATEGORY,null);
        return res;
    }

    public boolean updateData(String idTarget,String target,String amount,String leadTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TARGET_ID_TARGET,idTarget);
        contentValues.put(TARGET_TARGET,target);
        contentValues.put(TARGET_AMOUNT,amount);
        contentValues.put(TARGET_LEAD_TIME,leadTime);
        db.update(TABLE_TARGET,contentValues, "ID_TARGET = ?",new String[] { idTarget });
        return true;

    }
    public Integer deleteData (String idTarget){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TARGET, "ID_TARGET = ?", new String[] { idTarget });

    }
    public boolean insertDataCat(String name,String amount,String limit){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT_NAME, name);
        contentValues.put(CAT_AMOUNT,amount);
        contentValues.put(CAT_BOUNDARY, limit);

        long result = db.insert(TABLE_CATEGORY,null,contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }

    public ArrayList<String> getAllProvinces(){

        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();


        try
        {

            String selectQuery = "SELECT * FROM "+ TABLE_TARGET;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String name= cursor.getString(cursor.getColumnIndex("TARGET"));
                    list.add(name);

                }


            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;


    }
    public ArrayList<String> getAllP(){

        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();


        try
        {

            String selectQuery = "SELECT * FROM "+ TABLE_CATEGORY;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String name= cursor.getString(cursor.getColumnIndex("NAME"));
                    list.add(name);

                }


            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;


    }


    public ArrayList<String> getSumaWplataWeek(){

        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();


        try
        {

            String selectQuery = "SELECT SUM(AMOUNT) FROM RECORD_TABLE WHERE TYPE='WPŁATA' AND DATE between DATE_SUB(DATE(NOW()),INTERVAL 7 DAY) and DATE(NOW())";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String name= cursor.getString(cursor.getColumnIndex("AMOUNT"));
                    list.add(name);

                }


            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;


    }

    public ArrayList<String> getLimit(){

        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();


        try
        {

            String selectQuery = "SELECT * FROM "+ TABLE_CATEGORY;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String name= cursor.getString(cursor.getColumnIndex("BOUNDARY"));
                    list.add(name);

                }


            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;


    }
    public ArrayList<String> getCel(){

        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();


        try
        {

            String selectQuery = "SELECT * FROM "+ TABLE_TARGET;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String name= cursor.getString(cursor.getColumnIndex("LEAD_TIME"));
                    list.add(name);

                }


            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;


    }
    public ArrayList<String> getAllCategoryD(){

        ArrayList<String> list=new ArrayList<String>();
        // Open the database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();


        try
        {

            String selectQuery = "SELECT * FROM "+ TABLE_CATEGORY;
            Cursor cursor = db.rawQuery(selectQuery, null);
            if(cursor.getCount() >0)

            {
                while (cursor.moveToNext()) {
                    // Add province name to arraylist
                    String name= cursor.getString(cursor.getColumnIndex("NAME"));
                    list.add(name);

                }


            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            db.endTransaction();
            // End the transaction.
            db.close();

            // Close database
        }
        return list;


    }//cel2
    public ArrayList<HashMap<String, String>>  getTargetList2() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                TARGET_ID_TARGET + "," +
                TARGET_TARGET + "," +
                TARGET_AMOUNT + "," +
                TARGET_LEAD_TIME +

                " FROM " + TABLE_TARGET;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> targetList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> target = new HashMap<String, String>();
                target.put("name", cursor.getString(cursor.getColumnIndex(TARGET_TARGET)));
                target.put("leadTime", cursor.getString(cursor.getColumnIndex(TARGET_LEAD_TIME)));
                targetList.add(target);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return targetList;

    }


    //gra
    public ArrayList<HashMap<String, String>>  getAllGra() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                GAME_ID + "," +
                GAME_HISTORY_POINTS + "," +
                GAME_STATE_POINTS +
                " FROM " + TABLE_GAMIFICATION;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> gameList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> game = new HashMap<String, String>();
                game.put("name", cursor.getString(cursor.getColumnIndex(GAME_HISTORY_POINTS)));
                game.put("points", cursor.getString(cursor.getColumnIndex(GAME_STATE_POINTS)));
                gameList.add(game);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return gameList;

    }



    //moje nowe CATEGORY
    public int insert(Category category) {

        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CAT_AMOUNT,category.amount);
        values.put(CAT_NAME, category.name);
        values.put(CAT_BOUNDARY, category.limit);
        values.put(CAT_CREATED_AT,category.created);

        // Inserting Row
        long category_Id = db.insert(TABLE_CATEGORY, null, values);
        db.close(); // Closing database connection
        return (int) category_Id;
    }

    public void delete(int category_Id) {

        SQLiteDatabase db = this.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(TABLE_CATEGORY, CAT_ID_CATEGORY + "= ?", new String[]{String.valueOf(category_Id)});
        db.close(); // Closing database connection
    }

    public void update(Category category) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(CAT_AMOUNT,category.amount);
        values.put(CAT_NAME, category.name);
        values.put(CAT_BOUNDARY, category.limit);
        values.put(CAT_CREATED_AT,category.created);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TABLE_CATEGORY, values, CAT_ID_CATEGORY + "= ?", new String[]{String.valueOf(category.id_category)});
        db.close(); // Closing database connection
    }


    public ArrayList<HashMap<String, String>>  getCategoryList() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                CAT_ID_CATEGORY + "," +
                CAT_NAME + "," +
                CAT_AMOUNT + "," +
                CAT_BOUNDARY + "," +
                CAT_CREATED_AT +
                " FROM " + TABLE_CATEGORY;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> categoryList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> category = new HashMap<String, String>();
                category.put("id", cursor.getString(cursor.getColumnIndex(CAT_ID_CATEGORY)));
                category.put("name", cursor.getString(cursor.getColumnIndex(CAT_NAME)));
                categoryList.add(category);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categoryList;

    }

    public Category getCategoryById(int Id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                CAT_ID_CATEGORY + "," +
                CAT_NAME + "," +
                CAT_AMOUNT + "," +
                CAT_BOUNDARY + "," +
                CAT_CREATED_AT +
                " FROM " + TABLE_CATEGORY
                + " WHERE " +
                CAT_ID_CATEGORY + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Category category = new Category();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                category.id_category = cursor.getInt(cursor.getColumnIndex(CAT_ID_CATEGORY));
                category.name = cursor.getString(cursor.getColumnIndex(CAT_NAME));
                category.amount  = cursor.getString(cursor.getColumnIndex(CAT_AMOUNT));
                category.limit  = cursor.getString(cursor.getColumnIndex(CAT_BOUNDARY));
                category.created = cursor.getString(cursor.getColumnIndex(CAT_CREATED_AT));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return category;
    }
    //Record
    public int insertRecord(Record record) {

        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REC_NAME,record.name);
        values.put(REC_ID_CATEGORY, record.id_category);
        values.put(REC_AMOUNT, record.amount);
        values.put(REC_TYPE,record.type);
        values.put(REC_DATE, record.date);
        values.put(REC_ID_TARGET, record.id_target);

        // Inserting Row
        long record_Id = db.insert(TABLE_RECORD, null, values);
        db.close(); // Closing database connection
        return (int) record_Id;
    }

    public void deleteRecord(int record_Id) {

        SQLiteDatabase db = this.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(TABLE_RECORD, REC_ID_RECORD + "= ?", new String[]{String.valueOf(record_Id)});
        db.close(); // Closing database connection
    }


    public void updateRecord(Record record) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(REC_NAME,record.name);
        values.put(REC_ID_CATEGORY,record.id_category);
        values.put(REC_AMOUNT,record.amount);
        values.put(REC_TYPE,record.type);
        values.put(REC_DATE,record.date);
        values.put(REC_ID_TARGET,record.id_target);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TABLE_RECORD, values, REC_ID_RECORD + "= ?", new String[]{String.valueOf(record.id_record)});
        db.close(); // Closing database connection
    }


    public ArrayList<HashMap<String, String>>  getRecordList() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                REC_ID_RECORD + "," +
                REC_NAME + "," +
                REC_ID_CATEGORY + "," +
                REC_AMOUNT + "," +
                REC_TYPE + "," +
                REC_DATE + "," +
                REC_ID_TARGET +
                " FROM " + TABLE_RECORD;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> recordList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> record = new HashMap<String, String>();
                record.put("id", cursor.getString(cursor.getColumnIndex(REC_ID_RECORD)));
                record.put("name", cursor.getString(cursor.getColumnIndex(REC_NAME)));
                recordList.add(record);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return recordList;

    }

    public Record getRecordById(int Id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                REC_ID_RECORD + "," +
                REC_NAME + "," +
                REC_ID_CATEGORY + "," +
                REC_AMOUNT + "," +
                REC_TYPE + "," +
                REC_DATE + "," +
                REC_ID_TARGET +
                " FROM " + TABLE_RECORD
                + " WHERE " +
                REC_ID_RECORD + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Record record = new Record();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                record.id_record = cursor.getInt(cursor.getColumnIndex(REC_ID_RECORD));
                record.name = cursor.getString(cursor.getColumnIndex(REC_NAME));
                record.id_category  = cursor.getString(cursor.getColumnIndex(REC_ID_CATEGORY));
                record.amount  = cursor.getString(cursor.getColumnIndex(REC_AMOUNT));
                record.type  = cursor.getString(cursor.getColumnIndex(REC_TYPE));
                record.date  = cursor.getString(cursor.getColumnIndex(REC_DATE));
                record.id_target  = cursor.getString(cursor.getColumnIndex(REC_ID_TARGET));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return record;
    }
    //TARGET
    public int insertTarget(Target target) {

        //Open connection to write data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TARGET_TARGET,target.target);
        values.put(TARGET_AMOUNT, target.amount);
        values.put(TARGET_LEAD_TIME, target.lead_time);
       // values.put(TARGET_ACTUAL,target.actual);

        // Inserting Row
        long target_Id = db.insert(TABLE_TARGET, null, values);
        db.close(); // Closing database connection
        return (int) target_Id;
    }

    public void deleteTarget(int target_Id) {

        SQLiteDatabase db = this.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(TABLE_TARGET, TARGET_ID_TARGET + "= ?", new String[]{String.valueOf(target_Id)});
        db.close(); // Closing database connection
    }

    public void updateTarget(Target target) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();


        values.put(TARGET_TARGET,target.target);
        values.put(TARGET_AMOUNT,target.amount);
        values.put(TARGET_LEAD_TIME,target.lead_time);
       // values.put(TARGET_ACTUAL,target.actual);


        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(TABLE_TARGET, values, TARGET_ID_TARGET + "= ?", new String[]{String.valueOf(target.id_target)});
        db.close(); // Closing database connection
    }


    public ArrayList<HashMap<String, String>>  getTargetList() {
        //Open connection to read only
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                TARGET_ID_TARGET + "," +
                TARGET_TARGET + "," +
                TARGET_AMOUNT + "," +
                TARGET_LEAD_TIME +

                " FROM " + TABLE_TARGET;

        //Student student = new Student();
        ArrayList<HashMap<String, String>> targetList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> target = new HashMap<String, String>();
                target.put("id", cursor.getString(cursor.getColumnIndex(TARGET_ID_TARGET)));
                target.put("name", cursor.getString(cursor.getColumnIndex(TARGET_TARGET)));
                targetList.add(target);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return targetList;

    }

    public Target getTargetById(int Id){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                TARGET_ID_TARGET + "," +
                TARGET_TARGET + "," +
                TARGET_AMOUNT + "," +
                TARGET_LEAD_TIME +

                " FROM " + TABLE_TARGET
                + " WHERE " +
                TARGET_ID_TARGET + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Target target = new Target();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(Id)});

        if (cursor.moveToFirst()) {
            do {
                target.id_target = cursor.getInt(cursor.getColumnIndex(TARGET_ID_TARGET));
                target.target = cursor.getString(cursor.getColumnIndex(TARGET_TARGET));
                target.amount  = cursor.getString(cursor.getColumnIndex(TARGET_AMOUNT));
                target.lead_time  = cursor.getString(cursor.getColumnIndex(TARGET_LEAD_TIME));
            //    target.actual = cursor.getString(cursor.getColumnIndex(TARGET_ACTUAL));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return target;
    }

}
