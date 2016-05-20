package com.example.asia.myapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main5Activity extends ListActivity implements android.view.View.OnClickListener {

    DatabaseHelper myDb;
    Button button15,button30,button14,button34;
    EditText editText16;
    ListView list;
    //  SearchView searchView2;
    TextView record_Id;

    String[] items;
    ArrayList<String> items2;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;

    // ArrayAdapter<String> adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);



    //    button15 = (Button) findViewById(R.id.button15);
     //   button15.setOnClickListener(this);

        button34 = (Button) findViewById(R.id.button34);
        button34.setOnClickListener(this);

        button14 = (Button) findViewById(R.id.button14);
     //   button14.setOnClickListener(this);

        editText16 = (EditText) findViewById(R.id.editText16);
        widok();
    //    initList();
/*
        editText16.addTextChangedListener(new TextWatcher() {

            @Override

            public void beforeTextChanged(CharSequence s, int start, int count, int
                    after) {



            }



            @Override

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equals("")){

                    // reset listview

                    initList();

                }

                else{

                    // perform search

                    searchItem(s.toString());

                }

            }



            @Override

            public void afterTextChanged(Editable s) {



            }

        });
*/
    }

/*

    public void searchItem(String textToSearch){

        for(String item:items2){

            if(!item.contains(textToSearch)){

                listItems.remove(item);

            }

        }

        adapter.notifyDataSetChanged();

    }
*/
    /*
    public void initList(){

        ArrayList<String> tmp = new ArrayList<String>();
        DatabaseHelper repo = new DatabaseHelper(this);
        ArrayList<String> tmp2 =new ArrayList<String>();

        ArrayList<HashMap<String, String>> recordList = repo.getRecordList();
        //recordList = new String[]{repo.getRecordList()};


        for (HashMap<String, String> map : recordList)
            for (Map.Entry<String, String> entry : map.entrySet())
                tmp.add(entry.getValue());

            for (int i = 1 ; i < tmp.size() ; i += 2) {
            //System.out.print(tmp.get(i) + "  ") ;
            items2.add(tmp.get(i));

        }
       // editText21.setText(tmp2.toString());




        ListAdapter adapter2 = new SimpleAdapter(Main5Activity.this, recordList, R.layout.view_record_entry, new String[]{"id", "name"}, new int[]{R.id.record_Id, R.id.record_name});
        setListAdapter(adapter2);






        items=new String[]{"Canada","China","Japan","USA"};
        items2 = new ArrayList<String>();


        listItems=new ArrayList<>(items2);

        adapter=new ArrayAdapter<String>(this,R.layout.activity_item__layout, R.id.textView16, listItems);

        setListAdapter(adapter);




    }

*/

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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main5Activity.this);

                alertDialogBuilder.setTitle("Uwaga !!!");


                alertDialogBuilder
                        .setMessage("Czy chcesz się wylogować ?")
                        .setCancelable(false)
                        .setPositiveButton("Tak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                                startActivity(new Intent(Main5Activity.this, Main8Activity.class));

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
    public void widok() {
        DatabaseHelper repo = new DatabaseHelper(this);

        ArrayList<HashMap<String, String>> recordList = repo.getRecordList();
        if (recordList.size() != 0) {
            ListView lv = getListView();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    record_Id = (TextView) view.findViewById(R.id.record_Id);
                    String recordId = record_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(), RecordDetail.class);
                    objIndent.putExtra("id_record", Integer.parseInt(recordId));
                    startActivity(objIndent);
                }
            });
            ListAdapter adapter = new SimpleAdapter(Main5Activity.this, recordList, R.layout.view_record_entry, new String[]{"id", "name"}, new int[]{R.id.record_Id, R.id.record_name});
            setListAdapter(adapter);
        }
    }
    @Override
    public void onClick(View view) {

        if (view == findViewById(R.id.button34)) {

            Intent intent = new Intent(this, RecordDetail.class);
            intent.putExtra("id_record", 0);
            startActivity(intent);

        } else {

            DatabaseHelper repo = new DatabaseHelper(this);

            ArrayList<HashMap<String, String>> recordList = repo.getRecordList();
            if (recordList.size() != 0) {
                ListView lv = getListView();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        record_Id = (TextView) view.findViewById(R.id.record_Id);
                        String recordId = record_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), RecordDetail.class);
                        objIndent.putExtra("id_record", Integer.parseInt(recordId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter(Main5Activity.this, recordList, R.layout.view_record_entry, new String[]{"id", "name"}, new int[]{R.id.record_Id, R.id.record_name});
                setListAdapter(adapter);
            } else {
                Toast.makeText(this, "No transaction!", Toast.LENGTH_SHORT).show();
            }

        }
    }



    public void fifth(View view){
        startActivity(new Intent(Main5Activity.this, MainActivity.class));
    }

}

