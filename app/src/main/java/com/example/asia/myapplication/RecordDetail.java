package com.example.asia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecordDetail extends AppCompatActivity implements android.view.View.OnClickListener  {



    Button button33,button31,button9;
    EditText editText;
    EditText editText18,editText8,editText9, editText15;
    EditText editText19,editText20;

    private int _Record_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        button33 = (Button) findViewById(R.id.button33);
        button31 = (Button) findViewById(R.id.button31);
        button9 = (Button) findViewById(R.id.button9);

        editText8 = (EditText) findViewById(R.id.editText8);
        editText9 = (EditText) findViewById(R.id.editText9);
        editText15 = (EditText) findViewById(R.id.editText15);
        editText18 = (EditText) findViewById(R.id.editText18);
        editText19 = (EditText) findViewById(R.id.editText19);
        editText20 = (EditText) findViewById(R.id.editText20);

        button33.setOnClickListener(this);
        button31.setOnClickListener(this);
        button9.setOnClickListener(this);


        _Record_Id =0;
        Intent intent = getIntent();
        _Record_Id =intent.getIntExtra("id_record", 0);
        DatabaseHelper repo = new DatabaseHelper(this);
        Record record = new Record();
        record = repo.getRecordById(_Record_Id);


        editText8.setText(record.name);
        editText9.setText(record.id_category);
        editText15.setText(record.amount);
        editText18.setText(record.type);
        editText19.setText(record.date);
        editText20.setText(record.id_target);

    }
    public void onClick(View view) {
        // TODO Auto-generated method stub
        if (view == findViewById(R.id.button31)){
            DatabaseHelper repo = new DatabaseHelper(this);
            Record record = new Record();

            record.name=editText8.getText().toString();
            record.id_category=editText9.getText().toString();
            record.amount=editText15.getText().toString();
            record.type=editText18.getText().toString();
            record.date=editText19.getText().toString();
            record.id_target=editText20.getText().toString();
            record.id_record=_Record_Id;

            if (_Record_Id==0){
                _Record_Id = repo.insertRecord(record);

                Toast.makeText(this, "New transaction Insert", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RecordDetail.this, Main5Activity.class));

            }else{

                repo.updateRecord(record);
                Toast.makeText(this,"Transaction Record updated",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RecordDetail.this, Main5Activity.class));
            }
        }else if (view== findViewById(R.id.button33)){
            DatabaseHelper repo = new DatabaseHelper(this);
            repo.deleteRecord(_Record_Id);
            Toast.makeText(this, "Transaction Record Deleted", Toast.LENGTH_SHORT);
           // finish();
            startActivity(new Intent(RecordDetail.this, Main5Activity.class));
        }else if (view== findViewById(R.id.button9)){
            finish();
            startActivity(new Intent(RecordDetail.this, Main5Activity.class));
        }
    }
}