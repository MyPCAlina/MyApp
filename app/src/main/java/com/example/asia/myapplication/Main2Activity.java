package com.example.asia.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2Activity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editText, editText2, editText3;
    Spinner spinner3, spinner2, spinner, spinner4;
    Button button3, button2;
    private int _Record_Id = 0;
    ArrayList<String> names = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    RadioGroup radio;
    RadioButton radioButton, radioButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myDb = new DatabaseHelper(this);


        editText = (EditText) findViewById(R.id.editText);
        spinner3 = (Spinner) findViewById(R.id.spinner3);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinner4 = (Spinner) findViewById(R.id.spinner4);
        button3 = (Button) findViewById(R.id.button3);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        editText3.setText(currentDateandTime);

        // AddData();

        //   viewAllC();


        DatabaseHelper datahelper = new DatabaseHelper(this);
        ArrayList<String> list = datahelper.getAllProvinces();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_item__layout, R.id.textView16, list);
        spinner.setAdapter(adapter);


        DatabaseHelper datahelper2 = new DatabaseHelper(this);
        ArrayList<String> list2 = datahelper2.getAllP();
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.activity_item__layout, R.id.textView16, list2);
        spinner4.setAdapter(adapter2);


        ArrayAdapter adapter3 = ArrayAdapter.createFromResource(this, R.array.TYPE, android.R.layout.simple_spinner_item);
        spinner3.setAdapter(adapter3);


    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButton2:
                if (checked)

                    spinner.setVisibility(View.GONE);
                spinner4.setVisibility(View.VISIBLE);
                break;
            case R.id.radioButton:
                if (checked)

                    spinner4.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);

                break;
        }
    }


    public void onClick(View view) {

        DatabaseHelper myDb = new DatabaseHelper(this);

        SQLiteDatabase db = myDb.getReadableDatabase();


        String payment = spinner3.getSelectedItem().toString();
        String payment2 = "WPŁATA";
        String payment3 = "WYPŁATA";
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();
        ArrayList<String> list3 = new ArrayList<String>();




        if (view == findViewById(R.id.button3) && payment.equals(payment2) && radioButton2.isChecked()) {



            try {

                String kat = spinner4.getSelectedItem().toString();
                String selectQuery = ("SELECT * FROM CATEGORY_TABLE WHERE NAME='" + kat + "'");
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        // Add province name to arraylist
                        String name = cursor.getString(cursor.getColumnIndex("AMOUNT"));
                        list.add(name);
                    }

                }
            } catch (SQLiteException e) {
                e.printStackTrace();

            }

            DatabaseHelper repo = new DatabaseHelper(this);
            Record record = new Record();

            String namestr = editText.getText().toString();
            String idcategorystr = spinner4.getSelectedItem().toString();
            String amountstr = editText2.getText().toString();
            String typestr = spinner3.getSelectedItem().toString();
            String datestr = editText3.getText().toString();
            String idtarget = spinner.getSelectedItem().toString();


            boolean failed = false;


            if(TextUtils.isEmpty(namestr)){
                editText.setError("Nazwa nie może być pusta!");
                failed = true;
            }
            if(TextUtils.isEmpty(amountstr)){
                editText2.setError("Kwota nie może być pusta!");
                failed = true;
            }
            if(TextUtils.isEmpty(datestr)){
                editText3.setError("Data nie może być pusta!!");
                failed = true;
            }
            if(editText3.equals("yyyy-MM-dd"))
            {
                editText3.setError("Zły format daty! Przykład: 2016-02-02");
                failed = true;
            }
            if(!failed)
            {
                record.name = editText.getText().toString();
                record.id_category = spinner4.getSelectedItem().toString();
                record.amount = editText2.getText().toString();
                record.type = spinner3.getSelectedItem().toString();
                record.date = editText3.getText().toString();
                record.id_target = "-------------------------";

                record.id_record = _Record_Id;

                if (_Record_Id == 0) {
                    _Record_Id = repo.insertRecord(record);


                    String amount = editText2.getText().toString();
                    String category = spinner4.getSelectedItem().toString();

                    //  ArrayList<String> list = getAllNa();
                    String s = list.get(0);
                    int amountP = Integer.parseInt(amount);
                    int amountL = Integer.parseInt(s);

                    int All = amountL + amountP;

                    String selectQuery = ("UPDATE CATEGORY_TABLE SET AMOUNT='" + All + "' WHERE NAME='" + category + "'");
                    db.execSQL(selectQuery);

                    try {
                        String kat = spinner4.getSelectedItem().toString();
                        String select = ("SELECT * FROM CATEGORY_TABLE WHERE NAME='" + kat + "'");
                        Cursor cursor = db.rawQuery(select, null);
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                // Add province name to arraylist
                                String name = cursor.getString(cursor.getColumnIndex("BOUNDARY"));
                                list2.add(name);
                            }
                        }
                    } catch (SQLiteException e) {
                        e.printStackTrace();

                    }
                    try {
                        String kat = spinner4.getSelectedItem().toString();
                        String select = ("SELECT * FROM CATEGORY_TABLE WHERE NAME='" + kat + "'");
                        Cursor cursor = db.rawQuery(select, null);
                        if (cursor.getCount() > 0) {
                            while (cursor.moveToNext()) {
                                // Add province name to arraylist
                                String name = cursor.getString(cursor.getColumnIndex("AMOUNT"));
                                list3.add(name);
                            }
                        }
                    } catch (SQLiteException e) {
                        e.printStackTrace();

                    }

                    String s2 = list2.get(0);
                    String s3 = list3.get(0);
                    int amountP2 = Integer.parseInt(s3);
                    int amountL2 = Integer.parseInt(s2);

                    if(amountL2<amountP2){
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity.this);


                        alertDialogBuilder.setTitle("Uwaga !!!");


                        alertDialogBuilder
                                .setMessage("Przekroczysz limit w danej kategorii")
                                .setCancelable(false)
                                .setPositiveButton("Cancel",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        DatabaseHelper myDb = new DatabaseHelper(Main2Activity.this);

                                        SQLiteDatabase db = myDb.getReadableDatabase();

                                        ArrayList<String> list = new ArrayList<String>();

                                        String amount = editText2.getText().toString();
                                        String category = spinner4.getSelectedItem().toString();
                                        try {
                                            String kat = spinner4.getSelectedItem().toString();
                                            String selectQuery = ("SELECT * FROM CATEGORY_TABLE WHERE NAME='" + kat + "'");
                                            Cursor cursor = db.rawQuery(selectQuery, null);
                                            if (cursor.getCount() > 0) {
                                                while (cursor.moveToNext()) {
                                                    // Add province name to arraylist
                                                    String name = cursor.getString(cursor.getColumnIndex("AMOUNT"));
                                                    list.add(name);
                                                }
                                            }
                                        } catch (SQLiteException e) {
                                            e.printStackTrace();

                                        }

                                        String s = list.get(0);
                                        int amountP = Integer.parseInt(amount);
                                        int amountL = Integer.parseInt(s);

                                        int All = amountL - amountP;

                                        String selectQuery = ("UPDATE CATEGORY_TABLE SET AMOUNT='" + All + "' WHERE NAME='" + category + "'");
                                        db.execSQL(selectQuery);
                                        dialog.cancel();

                                        startActivity(new Intent(Main2Activity.this, MainActivity.class));

                                    }
                                })
                                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                });


                        AlertDialog alertDialog = alertDialogBuilder.create();


                        alertDialog.show();

                    }else{

                        Toast.makeText(this, "New transaction Insert", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Main2Activity.this, MainActivity.class));


                    }


                }

            }


        } else if (view == findViewById(R.id.button3) && payment.equals(payment2) && radioButton.isChecked()) {

            try {
                String cel = spinner.getSelectedItem().toString();
                String selectQuery = ("SELECT * FROM TARGET_TABLE WHERE TARGET='" + cel + "'");
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        // Add province name to arraylist
                        String name = cursor.getString(cursor.getColumnIndex("AMOUNT"));
                        list.add(name);
                    }
                }
            } catch (SQLiteException e) {
                e.printStackTrace();

            }

            DatabaseHelper repo = new DatabaseHelper(this);
            Record record = new Record();

            String namestr = editText.getText().toString();
            String idcategorystr = spinner4.getSelectedItem().toString();
            String amountstr = editText2.getText().toString();
            String typestr = spinner3.getSelectedItem().toString();
            String datestr = editText3.getText().toString();
            String idtarget = spinner.getSelectedItem().toString();

            boolean failed = false;


            if(TextUtils.isEmpty(namestr)){
                editText.setError("Nazwa nie może być pusta!");
                failed = true;
            }
            if(TextUtils.isEmpty(amountstr)){
                editText2.setError("Kwota nie może być pusta!");
                failed = true;
            }
            if(TextUtils.isEmpty(datestr)){
                editText3.setError("Data nie może być pusta!");
                failed = true;
            }
            if(editText3.equals("yyyy-MM-dd"))
            {
                editText3.setError("Zły format daty! Przykład 2016-02-02");
                failed = true;
            }
            if(!failed) {

                record.name = editText.getText().toString();
                record.id_category = "-------------------------";
                record.amount = editText2.getText().toString();
                record.type = spinner3.getSelectedItem().toString();
                record.date = editText3.getText().toString();
                record.id_target = spinner.getSelectedItem().toString();

                record.id_record = _Record_Id;

                if (_Record_Id == 0) {
                    _Record_Id = repo.insertRecord(record);


                    String amount = editText2.getText().toString();
                    String cel = spinner.getSelectedItem().toString();

                    //  ArrayList<String> list = getAllNa();
                    String s = list.get(0);
                    int amountP = Integer.parseInt(amount);
                    int amountL = Integer.parseInt(s);

                    int All = amountL - amountP;

                    String selectQuery = ("UPDATE TARGET_TABLE SET AMOUNT='" + All + "' WHERE TARGET='" + cel + "'");
                    db.execSQL(selectQuery);

                    Toast.makeText(this, "New transaction Insert", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Main2Activity.this, MainActivity.class));

                }
            }

        } else if (view == findViewById(R.id.button3) && payment.equals(payment3) && radioButton2.isChecked()) {

            try {
                String kat = spinner4.getSelectedItem().toString();
                String selectQuery = ("SELECT * FROM CATEGORY_TABLE WHERE NAME='" + kat + "'");
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        // Add province name to arraylist
                        String name = cursor.getString(cursor.getColumnIndex("AMOUNT"));
                        list.add(name);
                    }
                }
            } catch (SQLiteException e) {
                e.printStackTrace();

            }

            DatabaseHelper repo = new DatabaseHelper(this);
            Record record = new Record();


            String namestr = editText.getText().toString();
            String idcategorystr = spinner4.getSelectedItem().toString();
            String amountstr = editText2.getText().toString();
            String typestr = spinner3.getSelectedItem().toString();
            String datestr = editText3.getText().toString();
            String idtarget = spinner.getSelectedItem().toString();

            boolean failed = false;


            if(TextUtils.isEmpty(namestr)){
                editText.setError("Nazwa nie może być pusta!");
                failed = true;
            }
            if(TextUtils.isEmpty(amountstr)){
                editText2.setError("Kwota nie może być pusta!");
                failed = true;
            }
            if(TextUtils.isEmpty(datestr)){
                editText3.setError("Data nie może być pusta!");
                failed = true;
            }
            if(editText3.equals("yyyy-MM-dd"))
            {
                editText3.setError("Zły format daty! Przykład 2016-02-02");
                failed = true;
            }
            if(!failed) {

            record.name = editText.getText().toString();
            record.id_category = spinner4.getSelectedItem().toString();
            record.amount = editText2.getText().toString();
            record.type = spinner3.getSelectedItem().toString();
            record.date = editText3.getText().toString();
            record.id_target = "-------------------------";

            record.id_record = _Record_Id;

            if (_Record_Id == 0) {
                _Record_Id = repo.insertRecord(record);


                String amount = editText2.getText().toString();
                String category = spinner4.getSelectedItem().toString();

                //  ArrayList<String> list = getAllNa();
                String s = list.get(0);
                int amountP = Integer.parseInt(amount);
                int amountL = Integer.parseInt(s);

                int All = amountL - amountP;

                String selectQuery = ("UPDATE CATEGORY_TABLE SET AMOUNT='" + All + "' WHERE NAME='" + category + "'");
                db.execSQL(selectQuery);



                Toast.makeText(this, "New transaction Insert", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, MainActivity.class));

            }
            }


        } else if (view == findViewById(R.id.button3) && payment.equals(payment3) && radioButton.isChecked()) {

            try {
                String cel = spinner.getSelectedItem().toString();
                String selectQuery = ("SELECT * FROM TARGET_TABLE WHERE TARGET='" + cel + "'");
                Cursor cursor = db.rawQuery(selectQuery, null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        // Add province name to arraylist
                        String name = cursor.getString(cursor.getColumnIndex("AMOUNT"));
                        list.add(name);
                    }
                }
            } catch (SQLiteException e) {
                e.printStackTrace();

            }

            DatabaseHelper repo = new DatabaseHelper(this);
            Record record = new Record();

            String namestr = editText.getText().toString();
            String idcategorystr = spinner4.getSelectedItem().toString();
            String amountstr = editText2.getText().toString();
            String typestr = spinner3.getSelectedItem().toString();
            String datestr = editText3.getText().toString();
            String idtarget = spinner.getSelectedItem().toString();

            boolean failed = false;


            if(TextUtils.isEmpty(namestr)){
                editText.setError("Nazwa nie może być pusta!");
                failed = true;
            }
            if(TextUtils.isEmpty(amountstr)){
                editText2.setError("Kwota nie może być pusta!");
                failed = true;
            }
            if(TextUtils.isEmpty(datestr)){
                editText3.setError("Data nie może być pusta!");
                failed = true;
            }
            if(editText3.equals("yyyy-MM-dd"))
            {
                editText3.setError("Zły format daty! Przykład 2016-02-02");
                failed = true;
            }
            if(!failed) {

            record.name = editText.getText().toString();
            record.id_category = "-------------------------";
            record.amount = editText2.getText().toString();
            record.type = spinner3.getSelectedItem().toString();
            record.date = editText3.getText().toString();
            record.id_target = spinner.getSelectedItem().toString();

            record.id_record = _Record_Id;

            if (_Record_Id == 0) {
                _Record_Id = repo.insertRecord(record);


                String amount = editText2.getText().toString();
                String cel = spinner.getSelectedItem().toString();

                //  ArrayList<String> list = getAllNa();
                String s = list.get(0);
                int amountP = Integer.parseInt(amount);
                int amountL = Integer.parseInt(s);

                int All = amountL + amountP;

                String selectQuery = ("UPDATE TARGET_TABLE SET AMOUNT='" + All + "' WHERE TARGET='" + cel + "'");
              //  String selectQuery = ("DELETE FROM TARGET_TABLE case WHEN (strftime('%m', DATE) = strftime('%m', 'now')) AND AMOUNT");
                db.execSQL(selectQuery);

                Toast.makeText(this, "New transaction Insert", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this, MainActivity.class));

            }


        }
        }
    }




    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();}



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.settings_id:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main2Activity.this);


                alertDialogBuilder.setTitle("Uwaga !!!");


                alertDialogBuilder
                        .setMessage("Czy chcesz się wylogować ?")
                        .setCancelable(false)
                        .setPositiveButton("Tak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                                startActivity(new Intent(Main2Activity.this, Main8Activity.class));

                            }
                        })
                        .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });


                AlertDialog alertDialog = alertDialogBuilder.create();


                alertDialog.show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void AddData() {

        button3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertDataRec(editText.getText().toString(), spinner2.getSelectedItem().toString(), editText2.getText().toString(), spinner3.getSelectedItem().toString(), editText3.getText().toString(), spinner.getSelectedItem().toString());
                        if (isInserted == true)
                            Toast.makeText(Main2Activity.this, "Zostało dodane", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(Main2Activity.this, "Nie zostało dodane", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void first(View view){
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
    }
}





