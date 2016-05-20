package com.example.asia.myapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main4Activity extends ListActivity implements android.view.View.OnClickListener  {

    DatabaseHelper myDb;
    EditText editText9;
    EditText editText8;
    Button button12, button26;
    ListView list;
    TextView category_Id;
    ProgressBar progressBar3;

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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main4Activity.this);


                alertDialogBuilder.setTitle("Uwaga !!!");

                alertDialogBuilder
                        .setMessage("Czy chcesz się wylogować ?")
                        .setCancelable(false)
                        .setPositiveButton("Tak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                                startActivity(new Intent(Main4Activity.this, Main8Activity.class));

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




    //@Override
    public void onClick(View view) {
        if (view== findViewById(R.id.button26)){

            Intent intent = new Intent(this,CategoryDetail.class);
            intent.putExtra("id_category",0);
            startActivity(intent);

        }else {

            DatabaseHelper repo = new DatabaseHelper(this);

            ArrayList<HashMap<String, String>> categoryList =  repo.getCategoryList();
            if(categoryList.size()!=0) {
                 ListView lv = getListView();

            //    setContentView(R.layout.activity_main3);
            //    ListView list = (ListView)findViewById(R.id.list);
            //    list.addFooterView(view);

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        category_Id = (TextView) view.findViewById(R.id.category_Id);
                        String categoryId = category_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), CategoryDetail.class);
                        objIndent.putExtra("id_category", Integer.parseInt(categoryId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter( Main4Activity.this,categoryList, R.layout.view_category_entry, new String[] { "id","name"}, new int[] {R.id.category_Id, R.id.category_name});
                 //setListAdapter(adapter);
                //list.setAdapter(adapter);
                setListAdapter(adapter);
            }else{
                Toast.makeText(this, "No category!", Toast.LENGTH_SHORT).show();
            }

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);


      //  button12 = (Button) findViewById(R.id.button12);
        //button12.setOnClickListener(this);

        button26 = (Button) findViewById(R.id.button26);
        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);


/*

        DatabaseHelper datahelper = new DatabaseHelper(Main4Activity.this);
        ArrayList<String> list = datahelper.getKwota();
        List<Integer> newList = new ArrayList<Integer>(list.size());
        for (String myInt : list) {
            newList.add(Integer.valueOf(myInt));
        }
        for (Integer kwota : newList) {
                    progressBar3.

        }



*/

       // progressBar3.setProgress(10);
      //  progressBar3.setProgress();
/*
        DatabaseHelper datahelper2 = new DatabaseHelper(this);
        ArrayList<String> list2 = datahelper2.getLimit();
        List<Integer> newList2 = new ArrayList<Integer>(list2.size());
        for (String myInt : list2)
        {
            newList2.add(Integer.valueOf(myInt));
        }
        for (Integer limit: newList2) {
            progressBar3.setMax(limit);
        }

*/


        // progressBar3.setMax(list);
       // progressBar3.

        widok();


    }

    public void widok() {
        DatabaseHelper repo = new DatabaseHelper(this);

        ArrayList<HashMap<String, String>> categoryList = repo.getCategoryList();
        if (categoryList.size() != 0) {
            ListView lv = getListView();

          //  setContentView(R.layout.activity_main3);
           // ListView list = (ListView) findViewById(R.id.list);
             //list.addFooterView(view);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    category_Id = (TextView) view.findViewById(R.id.category_Id);
                    String categoryId = category_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(), CategoryDetail.class);
                    objIndent.putExtra("id_category", Integer.parseInt(categoryId));
                    startActivity(objIndent);
                }
            });
            ListAdapter adapter = new SimpleAdapter(Main4Activity.this, categoryList, R.layout.view_category_entry, new String[]{"id", "name"}, new int[]{R.id.category_Id, R.id.category_name});
            // setListAdapter(adapter);
            //lv.setAdapter(adapter);
            setListAdapter(adapter);
        }
    }


    public void fourth(View view){
        startActivity(new Intent(Main4Activity.this, MainActivity.class));
    }
}