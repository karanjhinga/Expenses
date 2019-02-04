package com.example.karan.expenses;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class ShowAllRecords extends AppCompatActivity implements myInterface{

    private List<SingleRecord> list;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private DatabaseHelper databaseHelper;
    private TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_records);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        databaseHelper = new DatabaseHelper(this);
        t = findViewById(R.id.n1);
        list = new ArrayList<>();
        Cursor cursor =databaseHelper.getRecords();
        populatelist(cursor);
        t.setVisibility(View.GONE);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL|DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if(list.size()<1){
            recyclerView.setVisibility(View.GONE);
            t.setVisibility(View.VISIBLE);
        }
        adapter = new RecyclerAdapter(list,this);
        recyclerView.setAdapter(adapter);

    }

    private void populatelist(Cursor cursor) {
        list.clear();
        while (cursor.moveToNext()){
            SingleRecord singleRecord = new SingleRecord(cursor.getFloat(0),cursor.getString(2),cursor.getString(3),cursor.getString(1),cursor.getFloat(4));
            list.add(singleRecord);
        }
    }

    @Override
    public void getposition(final int position, View view) {
        PopupMenu popupMenu = new PopupMenu(this,view);
        popupMenu.getMenu().add("Remove");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getTitle().toString()){
                    case "Remove":
                        SingleRecord record = list.get(position);
                        float pid = record.getPID();
                        int id = databaseHelper.deleteRecord(pid);
                        if(id > 0){
                            Toast.makeText(ShowAllRecords.this, "Record Deleted !", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(ShowAllRecords.this, "Not deleted !", Toast.LENGTH_SHORT).show();
                        }
                        float value = record.getValue();
                        if (record.getTYPE().equals(DatabaseHelper.TYPE_INCOME)){
                            SharedPreferences sp = getSharedPreferences(PREFERENCES.SP_NAME,0);
                            SharedPreferences.Editor et= sp.edit();
                            float update = Math.abs(sp.getFloat(PREFERENCES.INCOME,00.00f) - value);
                            et.putFloat(PREFERENCES.INCOME,update);
                            et.commit();
                        }else if (record.getTYPE().equals(DatabaseHelper.TYPE_EXPENSE)){
                            SharedPreferences sp = getSharedPreferences(PREFERENCES.SP_NAME,0);
                            SharedPreferences.Editor et= sp.edit();
                            float update = Math.abs(sp.getFloat(PREFERENCES.EXPENSE,00.00f) - value);
                            et.putFloat(PREFERENCES.EXPENSE,update);
                            et.commit();
                        }
                        list.remove(position);
                        adapter.notifyDataSetChanged();

                }
                return true;
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.record_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.all:
            {
                item.setChecked(true);
                Cursor cursor = databaseHelper.getRecords();
                populatelist(cursor);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Refreshed !", Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.allinc:
            {
                item.setChecked(true);
                Cursor cursor = databaseHelper.getIncomeRecords();
                populatelist(cursor);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Refreshed !", Toast.LENGTH_SHORT).show();
            }
                break;
            case R.id.allexp:
            {       item.setChecked(true);
                populatelist(databaseHelper.getExpenseRecords());
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Refreshed !", Toast.LENGTH_SHORT).show();
            }
                break;
        }

        return true;
    }
}

