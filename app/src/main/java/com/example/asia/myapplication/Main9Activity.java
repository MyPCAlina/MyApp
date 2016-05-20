package com.example.asia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main9Activity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);

    }

    public void onSignUpClick(View v)
    {
        if(v.getId()== R.id.button24)
        {
            EditText name = (EditText)findViewById(R.id.editText11);
            EditText username = (EditText)findViewById(R.id.editText12);
            EditText pass1 = (EditText)findViewById(R.id.editText13);
            EditText pass2 = (EditText)findViewById(R.id.editText14);

            String namestr = name.getText().toString();
            String userstr = username.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();

            boolean failed = false;


            if(TextUtils.isEmpty(namestr)){
                name.setError("Nickname connot be empty!");
                failed = true;
            }
            if(TextUtils.isEmpty(userstr)){
                username.setError("User name connot be empty!");
                failed = true;
            }
            if(TextUtils.isEmpty(pass1str)){
                pass1.setError("Pass connot be empty!");
                failed = true;
            }
            if(TextUtils.isEmpty(pass2str)){
                pass2.setError("Pass 2 connot be empty!");
                failed = true;
            }

            if(!pass1str.equals(pass2str))
            {
                Toast.makeText(Main9Activity.this, "Passwords don't match! ", Toast.LENGTH_SHORT).show();
                failed = true;
            }
            if(!failed)
            {
                Contact c = new Contact();
                c.setName(namestr);
                c.setUsername(userstr);
                c.setPass(pass1str);

                helper.insertContact(c);

                startActivity(new Intent(Main9Activity.this, Main8Activity.class));


            }

        }

    }



    public void ten(View view){
        startActivity(new Intent(Main9Activity.this, Main8Activity.class));
    }
}