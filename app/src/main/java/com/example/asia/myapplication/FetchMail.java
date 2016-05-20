package com.example.asia.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.PowerManager;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;


public final class FetchMail extends TimerTask {


    DatabaseHelper myDb;
    SQLiteDatabase db = myDb.getReadableDatabase();

    /** Construct and use a TimerTask and Timer. */
    public static void main (String... arguments ) {
        TimerTask fetchMail = new FetchMail();
        //perform the task once a day at 4 a.m., starting tomorrow morning
        //(other styles are possible as well)
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(fetchMail, getTomorrowMorning4am(), fONCE_PER_DAY);
    }

    /**
     * Implements TimerTask's abstract run method.
     */
    @Override public void run(){
        //toy implementation
       // System.out.println();
       // editText21.setText("Fetching mail...");
        Cursor t = db.rawQuery("SELECT ID_TARGET,TARGET,LEAD_TIME FROM TARGET_TABLE WHERE (LEAD_TIME < date('now')) AND AMOUNT > 0", null);

        if (t != null ) {
            if  (t.moveToFirst()) {
                do {
                    int tmpId = t.getInt(t.getColumnIndex("ID_TARGET"));
                    String tmpName = t.getString(t.getColumnIndex("TARGET"));
                    String tmpLeadTime = t.getString(t.getColumnIndex("LEAD_TIME"));

                    String selectQuery = ("INSERT INTO GAMIFICATION(ID_GAMIFICATION,HISTORY_POINTS,STATE_POINTS) VALUES(NULL,'Punty za cel: " + tmpName + "',-1)");
                    db.execSQL(selectQuery);




                }while (t.moveToNext());
            }
        }



    }

    // PRIVATE

    //expressed in milliseconds
    private final static long fONCE_PER_DAY = 1000*60*60*24;

    private final static int fONE_DAY = 1;
    private final static int fFOUR_AM = 4;
    private final static int fZERO_MINUTES = 0;

    private static Date getTomorrowMorning4am(){
        Calendar tomorrow = new GregorianCalendar();
        tomorrow.add(Calendar.DATE, fONE_DAY);
        Calendar result = new GregorianCalendar(
                tomorrow.get(Calendar.YEAR),
                tomorrow.get(Calendar.MONTH),
                tomorrow.get(Calendar.DATE),
                fFOUR_AM,
                fZERO_MINUTES
        );
        return result.getTime();
    }
}
