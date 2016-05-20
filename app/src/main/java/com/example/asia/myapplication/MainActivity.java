package com.example.asia.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();


       Cursor c = db.rawQuery("SELECT ID_CATEGORY,NAME,CREATED_AT FROM CATEGORY_TABLE WHERE (date(CREATED_AT,'+30 days') <= date('now')) AND AMOUNT<BOUNDARY", null);

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    int tmpId = c.getInt(c.getColumnIndex("ID_CATEGORY"));
                    String tmpName = c.getString(c.getColumnIndex("NAME"));

                    String selectQuery = ("INSERT INTO GAMIFICATION(ID_GAMIFICATION,HISTORY_POINTS,STATE_POINTS) VALUES(NULL,'Punty za kategorię: " + tmpName + "',2)");
                    db.execSQL(selectQuery);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateandTime = sdf.format(new Date());
                    String selectQuery2 = ("UPDATE CATEGORY_TABLE SET CREATED_AT='" + currentDateandTime + "' WHERE ID_CATEGORY='" + tmpId + "'");
                    db.execSQL(selectQuery2);

                    String selectQuery3 = ("UPDATE CATEGORY_TABLE SET AMOUNT='0' WHERE ID_CATEGORY='" + tmpId + "'");
                    db.execSQL(selectQuery3);



                }while (c.moveToNext());
            }
        }

        Cursor c2 = db.rawQuery("SELECT ID_CATEGORY,NAME,CREATED_AT FROM CATEGORY_TABLE WHERE (date(CREATED_AT,'+30 days') <= date('now')) AND AMOUNT>BOUNDARY", null);

        if (c2 != null ) {
            if  (c2.moveToFirst()) {
                do {
                    int tmpId = c2.getInt(c2.getColumnIndex("ID_CATEGORY"));
                    String tmpName = c2.getString(c2.getColumnIndex("NAME"));

                    String selectQuery = ("INSERT INTO GAMIFICATION(ID_GAMIFICATION,HISTORY_POINTS,STATE_POINTS) VALUES(NULL,'Punty za kategorię: " + tmpName + "',-2)");
                    db.execSQL(selectQuery);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateandTime = sdf.format(new Date());
                    String selectQuery2 = ("UPDATE CATEGORY_TABLE SET CREATED_AT='" + currentDateandTime + "' WHERE ID_CATEGORY='" + tmpId + "'");
                    db.execSQL(selectQuery2);

                    String selectQuery3 = ("UPDATE CATEGORY_TABLE SET AMOUNT='0' WHERE ID_CATEGORY='" + tmpId + "'");
                    db.execSQL(selectQuery3);



                }while (c2.moveToNext());
            }
        }

        Cursor t = db.rawQuery("SELECT ID_TARGET,TARGET,LEAD_TIME FROM TARGET_TABLE WHERE (LEAD_TIME <= date('now')) AND AMOUNT <=0", null);

        if (t != null ) {
            if  (t.moveToFirst()) {
                do {
                    int tmpId = t.getInt(t.getColumnIndex("ID_TARGET"));
                    String tmpName = t.getString(t.getColumnIndex("TARGET"));
                    String tmpLeadTime = t.getString(t.getColumnIndex("LEAD_TIME"));


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String currentDateandTime = sdf.format(new Date());


                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    Date d1 = null;
                    Date d2 = null;

                    try {
                        d1 = format.parse(currentDateandTime);
                        d2 = format.parse(tmpLeadTime);

                        //in milliseconds
                        Long diff = d1.getTime() - d2.getTime();

                        Long diffDays = diff / (24 * 60 * 60 * 1000);

                        int x = diffDays.intValue();
                        if(x<=92){

                            String selectQuery = ("INSERT INTO GAMIFICATION(ID_GAMIFICATION,HISTORY_POINTS,STATE_POINTS) VALUES(NULL,'Punty za cel: " + tmpName + "',3)");
                            db.execSQL(selectQuery);

                            String selectQuery2 = ("DELETE FROM TARGET_TABLE WHERE ID_TARGET='" + tmpId + "'");
                            db.execSQL(selectQuery2);


                        }else if(x>92 && x<=182) {

                            String selectQuery = ("INSERT INTO GAMIFICATION(ID_GAMIFICATION,HISTORY_POINTS,STATE_POINTS) VALUES(NULL,'Punty za cel: " + tmpName + "',6)");
                            db.execSQL(selectQuery);

                            String selectQuery2 = ("DELETE FROM TARGET_TABLE WHERE ID_TARGET='" + tmpId + "'");
                            db.execSQL(selectQuery2);

                        }else if(x>182 && x<=274 ){

                            String selectQuery = ("INSERT INTO GAMIFICATION(ID_GAMIFICATION,HISTORY_POINTS,STATE_POINTS) VALUES(NULL,'Punty za cel: " + tmpName + "',9)");
                            db.execSQL(selectQuery);

                            String selectQuery2 = ("DELETE FROM TARGET_TABLE WHERE ID_TARGET='" + tmpId + "'");
                            db.execSQL(selectQuery2);

                        }else if(x>274 && x<=365){

                            String selectQuery = ("INSERT INTO GAMIFICATION(ID_GAMIFICATION,HISTORY_POINTS,STATE_POINTS) VALUES(NULL,'Punty za cel: " + tmpName + "',12)");
                            db.execSQL(selectQuery);

                            String selectQuery2 = ("DELETE FROM TARGET_TABLE WHERE ID_TARGET='" + tmpId + "'");
                            db.execSQL(selectQuery2);

                        }else if(x>365 && x<457){

                            String selectQuery = ("INSERT INTO GAMIFICATION(ID_GAMIFICATION,HISTORY_POINTS,STATE_POINTS) VALUES(NULL,'Punty za cel: " + tmpName + "',15)");
                            db.execSQL(selectQuery);

                            String selectQuery2 = ("DELETE FROM TARGET_TABLE WHERE ID_TARGET='" + tmpId + "'");
                            db.execSQL(selectQuery2);

                        }else{

                            String selectQuery = ("INSERT INTO GAMIFICATION(ID_GAMIFICATION,HISTORY_POINTS,STATE_POINTS) VALUES(NULL,'Punty za cel: " + tmpName + "',18)");
                            db.execSQL(selectQuery);

                            String selectQuery2 = ("DELETE FROM TARGET_TABLE WHERE ID_TARGET='" + tmpId + "'");
                            db.execSQL(selectQuery2);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }while (t.moveToNext());
            }
        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.settings_id:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                alertDialogBuilder.setTitle("Uwaga !!!");

                alertDialogBuilder
                        .setMessage("Czy chcesz się wylogować ?")
                        .setCancelable(false)
                        .setPositiveButton("Tak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                                startActivity(new Intent(MainActivity.this, Main8Activity.class));

                            }
                        })
                        .setNegativeButton("Nie",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void second(View view){

        myDb = new DatabaseHelper(this);
        SQLiteDatabase db = myDb.getReadableDatabase();

        ArrayList<String> list = new ArrayList<String>();
        try {

            String selectQuery = ("SELECT NAME FROM CATEGORY_TABLE WHERE NAME NOT NULL OR NAME = ''");
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    String name = cursor.getString(cursor.getColumnIndex("NAME"));
                    list.add(name);
                }
            }

        } catch (SQLiteException e) {
            e.printStackTrace();

        }

        ArrayList<String> list2 = new ArrayList<String>();
        try {

            String selectQuery = ("SELECT TARGET FROM TARGET_TABLE WHERE TARGET NOT NULL OR TARGET = ''");
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    String target = cursor.getString(cursor.getColumnIndex("TARGET"));
                    list2.add(target);
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();

        }

        if(list2.isEmpty()&& list.isEmpty()){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            alertDialogBuilder.setTitle("Uwaga !!!");

            alertDialogBuilder
                    .setMessage("Nie dodałeś kategorii albo celu")
                    .setCancelable(false)
                    .setPositiveButton("Przejdz do kategorii", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            startActivity(new Intent(MainActivity.this, Main4Activity.class));

                        }
                    })
                    .setNeutralButton("Przejdz do celu", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            startActivity(new Intent(MainActivity.this, Main3Activity.class));
                        }
                    })
                    .setNegativeButton("Powrót", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }else if(!(list2.isEmpty())&& list.isEmpty()){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            alertDialogBuilder.setTitle("Uwaga !!!");

            alertDialogBuilder
                    .setMessage("Nie dodałeś kategorii")
                    .setCancelable(false)
                    .setPositiveButton("Przejdz do kategorii", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            startActivity(new Intent(MainActivity.this, Main3Activity.class));

                        }
                    })
                    .setNeutralButton("Przejdz do kategorii", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            startActivity(new Intent(MainActivity.this, Main4Activity.class));
                        }
                    })
                    .setNegativeButton("Powrót", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }else if(!(list.isEmpty())&& list2.isEmpty()){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

            alertDialogBuilder.setTitle("Uwaga !!!");

            alertDialogBuilder
                    .setMessage("Nie dodałeś celu")
                    .setCancelable(false)
                    .setPositiveButton("Przejdz do celu", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            startActivity(new Intent(MainActivity.this, Main3Activity.class));

                        }
                    })
                    .setNegativeButton("Powrót", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

        }else
        {
            startActivity(new Intent(MainActivity.this,Main2Activity.class));
        }


    }
    public void third(View view){
        startActivity(new Intent(MainActivity.this,Main3Activity.class));
    }
    public void fourth(View view){
        startActivity(new Intent(MainActivity.this,Main4Activity.class));
    }
    public void fifth(View view){
        startActivity(new Intent(MainActivity.this,Main5Activity.class));
    }
    public void sixth(View view){
        startActivity(new Intent(MainActivity.this,Main6Activity.class));
    }
    public void seventh(View view){
        startActivity(new Intent(MainActivity.this, Main7Activity.class));
    }

}
