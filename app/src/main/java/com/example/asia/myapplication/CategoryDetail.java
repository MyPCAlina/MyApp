package com.example.asia.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CategoryDetail extends AppCompatActivity implements android.view.View.OnClickListener {

    Button btnSave,btnDelete;
    Button btnClose;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextAge;
    EditText editText17;
    private int _Category_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);


        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editText17 = (EditText) findViewById(R.id.editText17);


        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Category_Id =0;
        Intent intent = getIntent();
        _Category_Id =intent.getIntExtra("id_category", 0);
        DatabaseHelper repo = new DatabaseHelper(this);
        Category category = new Category();
        category = repo.getCategoryById(_Category_Id);


        editTextName.setText(category.name);
        editTextEmail.setText(category.amount);
        editTextAge.setText(category.limit);
        editText17.setText(category.created);

    }

    public void onClick(View view) {
        // TODO Auto-generated method stub
        if (view == findViewById(R.id.btnSave)){
            DatabaseHelper repo = new DatabaseHelper(this);
            Category category = new Category();

            String namestr = editTextName.getText().toString();
            String amountstr = editTextEmail.getText().toString();
            String limitstr = editTextAge.getText().toString();
            String createdstr = editText17.getText().toString();


            boolean failed = false;


            if(TextUtils.isEmpty(namestr)){
                editTextName.setError("Nazwa nie może być pusty!");
                failed = true;
            }
            if(TextUtils.isEmpty(amountstr)){
                editTextEmail.setError("User name connot be empty!");
                failed = true;
            }
            if(TextUtils.isEmpty(limitstr)){
                editTextAge.setError("Pass connot be empty!");
                failed = true;
            }
            if(TextUtils.isEmpty(createdstr)){
                editText17.setError("Pass 2 connot be empty!");
                failed = true;
            }

            if(editText17.equals("yyyy-MM-dd"))
            {
                editText17.setError("Zły format daty! Przykład 2016-02-02");
                failed = true;
            }
            if(!failed)
            {
                category.name=editTextName.getText().toString();
                category.amount=editTextEmail.getText().toString();
                category.limit=editTextAge.getText().toString();
                category.created=editText17.getText().toString();
                category.id_category=_Category_Id;

                if (_Category_Id==0){
                    _Category_Id = repo.insert(category);

                    Toast.makeText(this, "New Category Insert", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CategoryDetail.this, Main4Activity.class));
                }else{

                    repo.update(category);
                    Toast.makeText(this,"Category Record updated",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CategoryDetail.this, Main4Activity.class));
                }

            }



        }else if (view== findViewById(R.id.btnDelete)){
            DatabaseHelper repo = new DatabaseHelper(this);
            repo.delete(_Category_Id);
            Toast.makeText(this, "Category Record Deleted", Toast.LENGTH_SHORT);
           // finish();
            startActivity(new Intent(CategoryDetail.this, Main4Activity.class));
        }else if (view== findViewById(R.id.btnClose)){
            finish();
            startActivity(new Intent(CategoryDetail.this, Main4Activity.class));
        }
    }
}
