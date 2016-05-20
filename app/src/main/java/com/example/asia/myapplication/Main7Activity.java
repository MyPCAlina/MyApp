package com.example.asia.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Main7Activity extends AppCompatActivity {


    DatabaseHelper myDb;
    ImageView imageView;
    ProgressBar progressBar2;
    RatingBar ratingBar;
    Button button19;
    TextView textView29;
    ImageButton imageButton;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        myDb = new DatabaseHelper(this);


        imageView = (ImageView) findViewById(R.id.imageView);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        button19 = (Button) findViewById(R.id.button19);
        textView29 = (TextView) findViewById(R.id.textView29);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        listView = (ListView) findViewById(R.id.listView);
        DatabaseHelper myDb = new DatabaseHelper(this);

        SQLiteDatabase db = myDb.getReadableDatabase();

        DatabaseHelper datahelper = new DatabaseHelper(this);

        ArrayList<HashMap<String, String>> gameList = datahelper.getAllGra();
        ListAdapter adapter = new SimpleAdapter(Main7Activity.this, gameList, R.layout.activity_item_layout, new String[]{"name", "points"}, new int[]{R.id.textView43, R.id.textView44});

        listView.setAdapter(adapter);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main7Activity.this);

                alertDialogBuilder.setTitle("Informacje o grze!!!");

                alertDialogBuilder
                        .setMessage("Zwykła kontrola wydatków to nuda? Dzięki grze będziesz miał większą kontrolę oraz motywację, aby uzbierać na wymarzony cel.")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();


                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

            }
        });



        ArrayList<String> poinst=new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT SUM(STATE_POINTS) FROM GAMIFICATION", null);

        if (c.moveToFirst()) {
            do {

                String column1 = c.getString(0);
                poinst.add(column1);
               // textView29.setText(column1);


            } while (c.moveToNext());

        }
        c.close();
        db.close();






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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main7Activity.this);

                alertDialogBuilder.setTitle("Uwaga !!!");

                alertDialogBuilder
                        .setMessage("Czy chcesz się wylogować ?")
                        .setCancelable(false)
                        .setPositiveButton("Tak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                                startActivity(new Intent(Main7Activity.this, Main8Activity.class));

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



    public void seventh(View view){
        startActivity(new Intent(Main7Activity.this, MainActivity.class));
    }
}


