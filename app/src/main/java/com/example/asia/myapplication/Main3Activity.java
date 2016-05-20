package com.example.asia.myapplication;

import android.app.AlertDialog;
import android.app.ListActivity;
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

public class Main3Activity extends ListActivity implements android.view.View.OnClickListener  {


    EditText editText4, editText5,editText6,editText7;
    Button button6;
    Button button7;
    Button button8;
    Button button9;
    ListView listView;
    ListView list;
    TextView target_Id;
    TextView category_Id;


    public void onClick(View view) {
        if (view == findViewById(R.id.button6)){

            Intent intent = new Intent(this,TargetDetail.class);
            intent.putExtra("id_target",0);
            startActivity(intent);

        }else {

            DatabaseHelper repo = new DatabaseHelper(this);

            ArrayList<HashMap<String, String>> targetList =  repo.getTargetList();
            if(targetList.size()!=0) {

              //  setContentView(R.layout.activity_main3);
              //  ListView list = (ListView)findViewById(R.id.list);
              //  list.addFooterView(view);

                ListView lv = getListView();

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        target_Id = (TextView) view.findViewById(R.id.target_Id);
                        String targetId = target_Id.getText().toString();
                        Intent objIndent = new Intent(getApplicationContext(), TargetDetail.class);
                        objIndent.putExtra("id_target", Integer.parseInt(targetId));
                        startActivity(objIndent);
                    }
                });
                ListAdapter adapter = new SimpleAdapter( Main3Activity.this,targetList, R.layout.view_target_entry, new String[] { "id","name"}, new int[] {R.id.target_Id, R.id.target_name});
                setListAdapter(adapter);
                //myList.setAdapter(adapter);
               // list.setAdapter(adapter);

            }else{
                Toast.makeText(this, "No target!", Toast.LENGTH_SHORT).show();
            }

        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


      //  button7 = (Button) findViewById(R.id.button7);
        //listView.setOnClickListener();



        button6 = (Button) findViewById(R.id.button6);


        ListView list = (ListView)findViewById(android.R.id.list);

        widok();






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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main3Activity.this);

                alertDialogBuilder.setTitle("Uwaga !!!");

                alertDialogBuilder
                        .setMessage("Czy chcesz się wylogować ?")
                        .setCancelable(false)
                        .setPositiveButton("Tak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                                startActivity(new Intent(Main3Activity.this, Main8Activity.class));

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

        ArrayList<HashMap<String, String>> targetList = repo.getTargetList();
        if (targetList.size() != 0) {

          //  setContentView(R.layout.activity_main3);
          //  ListView list = (ListView) findViewById(R.id.list);
          //  list.addFooterView(view);

            ListView lv = getListView();

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    target_Id = (TextView) view.findViewById(R.id.target_Id);
                    String targetId = target_Id.getText().toString();
                    Intent objIndent = new Intent(getApplicationContext(), TargetDetail.class);
                    objIndent.putExtra("id_target", Integer.parseInt(targetId));
                    startActivity(objIndent);
                }
            });
            ListAdapter adapter = new SimpleAdapter(Main3Activity.this, targetList, R.layout.view_target_entry, new String[]{"id", "name"}, new int[]{R.id.target_Id, R.id.target_name});
         //   ArrayList<HashMap<String, String>> targetList2 = repo.getTargetList2();
         //   ListAdapter adapter2 = new SimpleAdapter(Main3Activity.this, targetList2, R.layout.activity_item_layout, new String[]{"name", "leadTime"}, new int[]{R.id.textView43, R.id.textView44});
         //   ArrayList<String> list2 = repo.getCel();
         //   ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.view_target_entry, R.id.target_time, list2);
         //   setListAdapter(adapter2);
  /*          ArrayList<String> tmp = new ArrayList<String>() ;


            SQLiteDatabase db = repo.getReadableDatabase();
            Cursor c = db.rawQuery("SELECT LEAD_TIME FROM TARGET_TABLE", null);
            // Cursor c = db.rawQuery("SELECT GETDATE()",null);
            if(c.moveToFirst()){
                do{
                    //assing values
                    String column1 = c.getString(0);
                    tmp.add(column1);



                    //Do something Here with values

                }while(c.moveToNext());

            }
            c.close();
            db.close();

*/

            setListAdapter(adapter);
           // setListAdapter(adapter2);
            //myList.setAdapter(adapter);
           // list.setAdapter(adapter);
           // ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.view_target_entry, R.id.target_time, tmp);
           // setListAdapter(adapter2);

        }

    }

    public void third(View view){
        startActivity(new Intent(Main3Activity.this, MainActivity.class));
    }

}

