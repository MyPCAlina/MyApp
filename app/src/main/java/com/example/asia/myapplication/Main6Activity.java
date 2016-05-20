package com.example.asia.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;


public class Main6Activity extends AppCompatActivity {
    Button button20, button21, button22, button23;

    private RelativeLayout mainLayout;
    private PieChart mChart;


    DatabaseHelper datahelper = new DatabaseHelper(this);


  //  ArrayList<String> Data = datahelper.getAllCategoryD();
  // ArrayList<String> Data2 = datahelper.getKwota();
  //  private float[] yData = {Data2.size()};
  // private String[] xData = new String[Data.size()];
    private float[] yData = {5,10,15,30,40};
    private String[] xData = {"Sony","H","lg","Apple","gg"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);


        button20 = (Button)findViewById(R.id.button20);

        button21= (Button) findViewById(R.id.button21);

        button22 = (Button) findViewById(R.id.button22);

        button23 = (Button) findViewById(R.id.button23);

        mainLayout = (RelativeLayout)findViewById(R.id.mainLayout);
        mChart = new PieChart(this);

        mainLayout.addView(mChart);
        mainLayout.setBackgroundColor(Color.LTGRAY);

        mChart.setUsePercentValues(true);
        mChart.setDescription("Smartphones Market Share");

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e == null)
                    return;

                Toast.makeText(Main6Activity.this, xData[e.getXIndex()] + " = " + e.getVal() + "%", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {


            }


        });


        addData();
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);






    }

    private void addData()
    {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i =0; i <yData.length; i++)

            yVals1.add(new Entry(yData[i],i));

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i =0; i<xData.length; i++)
            xVals.add(xData[i]);

        PieDataSet dataSet = new PieDataSet(yVals1,"Market Share");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        PieData data = new PieData(xVals,dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        mChart.highlightValues(null);

        mChart.invalidate();


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

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Main6Activity.this);


                alertDialogBuilder.setTitle("Uwaga !!!");

                alertDialogBuilder
                        .setMessage("Czy chcesz się wylogować ?")
                        .setCancelable(false)
                        .setPositiveButton("Tak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                dialog.cancel();
                                startActivity(new Intent(Main6Activity.this, Main8Activity.class));

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




    public void onClick(View v) {

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list2 = new ArrayList<String>();

        switch(v.getId()) {
            case R.id.button20:
                Toast.makeText(Main6Activity.this,"1",Toast.LENGTH_SHORT).show();
                /*
                list.clear();
                list2.clear();
                SQLiteDatabase db = datahelper.getReadableDatabase();
                Cursor c = db.rawQuery("SELECT SUM(AMOUNT) FROM RECORD_TABLE WHERE date('now')=DATE AND TYPE='WPŁATA'", null);

                if(c.moveToFirst()){
                    do{

                        String column1 = c.getString(0);
                        list.add(column1);


                    }while(c.moveToNext());

                }
                c.close();
                db.close();

                SQLiteDatabase db3 = datahelper.getReadableDatabase();
                Cursor c3 = db.rawQuery("SELECT SUM(AMOUNT) FROM RECORD_TABLE WHERE date('now')=DATE AND TYPE='WYPŁATA'", null);

                if(c3.moveToFirst()){
                    do{

                        String column1 = c3.getString(0);
                        list2.add(column1);


                    }while(c.moveToNext());

                }
                c3.close();
                db3.close();


*/

                break;
            case R.id.button21:
                Toast.makeText(Main6Activity.this,"2",Toast.LENGTH_SHORT).show();
  /*
                list.clear();
                list2.clear();

                SQLiteDatabase db1 = datahelper.getReadableDatabase();
                Cursor c1 = db1.rawQuery("SELECT SUM(AMOUNT) FROM RECORD_TABLE WHERE (strftime('%W', DATE) = strftime('%W', 'now')) AND TYPE='WPŁATA'", null);

                if(c1.moveToFirst()){
                    do{

                        String column1 = c1.getString(0);
                        list.add(column1);


                    }while(c1.moveToNext());

                }
                c1.close();
                db1.close();

                SQLiteDatabase db4 = datahelper.getReadableDatabase();
                Cursor c4 = db4.rawQuery("SELECT SUM(AMOUNT) FROM RECORD_TABLE WHERE (strftime('%W', DATE) = strftime('%W', 'now')) AND TYPE='WYPŁATA'", null);

                if(c4.moveToFirst()){
                    do{

                        String column1 = c4.getString(0);
                        list2.add(column1);


                    }while(c4.moveToNext());

                }
                c4.close();
                db4.close();


*/
                break;
            case R.id.button22:

                Toast.makeText(Main6Activity.this,"3", Toast.LENGTH_SHORT).show();
  /*
                list.clear();
                list2.clear();
                SQLiteDatabase db2 = datahelper.getReadableDatabase();
                Cursor c2 = db2.rawQuery("SELECT SUM(AMOUNT) FROM RECORD_TABLE WHERE (strftime('%m', DATE) = strftime('%m', 'now')) AND TYPE='WPŁATA'", null);

                if(c2.moveToFirst()){
                    do{

                        String column1 = c2.getString(0);
                        list.add(column1);


                    }while(c2.moveToNext());

                }
                c2.close();
                db2.close();


                SQLiteDatabase db5 = datahelper.getReadableDatabase();
                Cursor c5 = db5.rawQuery("SELECT SUM(AMOUNT) FROM RECORD_TABLE WHERE (strftime('%m', DATE) = strftime('%m', 'now')) AND TYPE='WYPŁATA'", null);

                if(c5.moveToFirst()){
                    do{

                        String column1 = c5.getString(0);
                        list2.add(column1);


                    }while(c5.moveToNext());

                }
                c5.close();
                db5.close();

                break;

   */
            case R.id.button23:
            //    list.clear();
            //    list2.clear();
                Toast.makeText(Main6Activity.this,"4",Toast.LENGTH_SHORT).show();

            //    list = datahelper.getKwota();
            //    list2 = datahelper.getAllCategoryD();



                break;
        }
    }



    public void sixth(View view){
        startActivity(new Intent(Main6Activity.this, MainActivity.class));
    }
}


