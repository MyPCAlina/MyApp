package com.example.asia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TargetDetail extends AppCompatActivity implements android.view.View.OnClickListener  {

    Button button27,button28,button29;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText17;
    private int _Target_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_detail);

        button28 = (Button) findViewById(R.id.button28);
        button27 = (Button) findViewById(R.id.button27);
        button29 = (Button) findViewById(R.id.button29);

        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
      //  editText17 = (EditText) findViewById(R.id.editText17);


        button28.setOnClickListener(this);
        button27.setOnClickListener(this);
        button29.setOnClickListener(this);


        _Target_Id =0;
        Intent intent = getIntent();
        _Target_Id =intent.getIntExtra("id_target", 0);
        DatabaseHelper repo = new DatabaseHelper(this);
        Target target = new Target();
        target = repo.getTargetById(_Target_Id);


        editText4.setText(target.target);
        editText5.setText(target.amount);
        editText6.setText(target.lead_time);



    }
    public void onClick(View view) {
        // TODO Auto-generated method stub
        if (view == findViewById(R.id.button28)){
            DatabaseHelper repo = new DatabaseHelper(this);
            Target target = new Target();


            String targetstr = editText4.getText().toString();
            String amountstr = editText5.getText().toString();
            String lead_timestr = editText6.getText().toString();

            boolean failed = false;


            if(TextUtils.isEmpty(targetstr)){
                editText4.setError("Nazwa nie może być pusty!");
                failed = true;
            }
            if(TextUtils.isEmpty(amountstr)){
                editText5.setError("User name connot be empty!");
                failed = true;
            }
            if(TextUtils.isEmpty(lead_timestr)){
                editText6.setError("Pass connot be empty!");
                failed = true;
            }
            if(editText6.equals("yyyy-MM-dd"))
            {
                editText6.setError("Zły format daty! Przykład 2016-02-02");
                failed = true;
            }
            if(!failed)
            {
                target.target=editText4.getText().toString();
                target.amount=editText5.getText().toString();
                target.lead_time=editText6.getText().toString();
                target.id_target=_Target_Id;

                if (_Target_Id==0){
                    _Target_Id = repo.insertTarget(target);

                    Toast.makeText(this, "New Target Insert", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TargetDetail.this, Main3Activity.class));
                }else{

                    repo.updateTarget(target);
                    Toast.makeText(this,"Target Record updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TargetDetail.this, Main3Activity.class));
                }

            }

        }else if (view== findViewById(R.id.button27)){
            DatabaseHelper repo = new DatabaseHelper(this);
            repo.deleteTarget(_Target_Id);
            Toast.makeText(this, "Target Record Deleted", Toast.LENGTH_SHORT);
            //finish();
            startActivity(new Intent(TargetDetail.this, Main3Activity.class));
        }else if (view== findViewById(R.id.button29)){
            finish();

        }
    }
}