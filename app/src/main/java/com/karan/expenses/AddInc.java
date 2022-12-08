package com.karan.expenses;


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

public class AddInc extends AppCompatActivity {

    EditText value,description;
    TextView category;
    Button save;
    List<String> list;
    DatabaseHelper databaseHelper;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inc);
       ActionBar actionBar =getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Add Income");
        databaseHelper = new DatabaseHelper(this);
        value = findViewById(R.id.et);
        description = findViewById(R.id.et2);
        category =findViewById(R.id.tv1);
        save = findViewById(R.id.save);
        list = new ArrayList<>();
        populatelist();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = value.getText().toString();
                if(TextUtils.isEmpty(val)){
                    Toast.makeText(AddInc.this, "Income can't be left empty !", Toast.LENGTH_SHORT).show();
                }
                else if(category.getText().toString().equals(DatabaseHelper.Default)){
                    Toast.makeText(AddInc.this, "Category must be selected", Toast.LENGTH_SHORT).show();
                }
                else {
                    float value = Float.parseFloat(val);
                    if(databaseHelper.insertRecord(value,DatabaseHelper.TYPE_INCOME,category.getText().toString(),description.getText().toString())){
                        SharedPreferences sp = getSharedPreferences(PREFERENCES.SP_NAME,0);
                        SharedPreferences.Editor et= sp.edit();
                        float update = sp.getFloat(PREFERENCES.INCOME,00.00f) + value;
                        et.putFloat(PREFERENCES.INCOME,update);
                        et.commit();
                        Toast.makeText(AddInc.this, "Added "+ value, Toast.LENGTH_SHORT).show();
                        clear();
                    }
                    else {
                        Toast.makeText(AddInc.this, "Oops some error occurred !", Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder ab = new AlertDialog.Builder(AddInc.this);
        ab.setTitle("Add Default Categories ?");
        ab.setMessage("No Income categories are defined. Do you want to add system default categories ?");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                list.add("Cash");
                list.add("Card 1");
                list.add("Savings");
                list.add("Loan");
                for(String i:list){
                    databaseHelper.insertIncomeCategory(i);
                }
            }
        });
        ab.setNegativeButton("No",null);
        ab.setCancelable(false);
        ab.show();
    }

    private void populatelist() {
        Cursor cursor = databaseHelper.getIncomeColumns();
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
