package com.example.asia.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main8Activity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);
    }
    public void onButtonClick(View v)
    {
        if(v.getId() == R.id.button8)
        {
            EditText a = (EditText)findViewById(R.id.editText7);
            String str = a.getText().toString();
            EditText b = (EditText)findViewById(R.id.editText10);
            String pass = b.getText().toString();

            String password = helper.searchPass(str);
            if(pass.equals(password))
            {

                Intent i = new Intent(Main8Activity.this,MainActivity.class);

                startActivity(i);
            }
            else
            {
                Toast.makeText(Main8Activity.this, "Username and password don't match! ", Toast.LENGTH_SHORT).show();
            }
        }
        if(v.getId()== R.id.button25)
        {
            Intent i = new Intent(Main8Activity.this,Main9Activity.class);
            startActivity(i);

        }
    }
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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main8Activity.this);

                alertDialogBuilder.setTitle("Uwaga !!!");

                alertDialogBuilder
                        .setMessage("Czy chcesz wyjść z aplikacji ?")
                        .setCancelable(false)
                        .setPositiveButton("Tak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                finish();

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
}