package com.example.karan.expenses;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddExp extends AppCompatActivity {

    EditText value,description;
    TextView category;
    Button save;
    List<String> list;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exp);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Add Expense");
        databaseHelper = new DatabaseHelper(this);
        value = findViewById(R.id.et3);
        description = findViewById(R.id.et4);
        category =findViewById(R.id.tv2);
        save = findViewById(R.id.save2);
        list = new ArrayList<>();
        populatelist();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if(TextUtils.isEmpty(val)){
                    Toast.makeText(AddExp.this, "Expense can't be left empty !", Toast.LENGTH_SHORT).show();
                }
                else if(category.getText().toString().equals(DatabaseHelper.Default)){
                    Toast.makeText(AddExp.this, "Category must be selected", Toast.LENGTH_SHORT).show();
                }
                else {
                    float value = Float.parseFloat(val);
                    if(databaseHelper.insertRecord(value,DatabaseHelper.TYPE_EXPENSE,category.getText().toString(),description.getText().toString())){
                        SharedPreferences sp = getSharedPreferences(PREFERENCES.SP_NAME,0);
                        SharedPreferences.Editor et= sp.edit();
                        float update = Math.abs(sp.getFloat(PREFERENCES.EXPENSE,00.00f)) + Math.abs(value);
                        et.putFloat(PREFERENCES.EXPENSE,update);
                        et.commit();
                        Toast.makeText(AddExp.this, "Spent "+value, Toast.LENGTH_SHORT).show();
                        clear();
                    }
                    else {
                        Toast.makeText(AddExp.this, "Oops some error occurred !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size()>0){
                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(),v);
                    for(String i:list){
                        popupMenu.getMenu().add(i);
                    }
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            for(String i:list){
                                if(item.getTitle().toString().equals(i)){
                                    category.setText(item.getTitle().toString());
                                }
                            }
                            return true;
                        }
                    });
                }
                else {
                    showDailogtoAddDefaults();
                }
            }
        });
    }

    private void clear() {
        value.setText("");
        category.setText(DatabaseHelper.Default);
        description.setText("");
    }

    private void showDailogtoAddDefaults() {
        AlertDialog.Builder ab = new AlertDialog.Builder(AddExp.this);
        ab.setTitle("Add Default Categories ?");
        ab.setMessage("No Expense categories are defined. Do you want to add system default categories ?");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.add("Food");
                list.add("Fuel");
                list.add("Personal");
                list.add("Pets");
                list.add("Merchandise");
                list.add("Others");
                for(String i:list){
                    databaseHelper.insertExpenseCategory(i);
                }
            }
        });
        ab.setNegativeButton("No",null);
        ab.setCancelable(false);
        ab.show();
    }

    private void populatelist() {
        Cursor cursor = databaseHelper.getExpenseColumns();
        while (cursor.moveToNext()){
            list.add(cursor.getString(0));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
