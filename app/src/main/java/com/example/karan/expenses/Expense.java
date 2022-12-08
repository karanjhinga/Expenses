package com.example.karan.expenses;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Expense extends AppCompatActivity {

    List<String> list;
    ListView listView;
    DatabaseHelper databaseHelper;
    FloatingActionButton button;
    ArrayAdapter<String> adapter;
    TextView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Expense Categories");
        databaseHelper = new DatabaseHelper(this);
        button = findViewById(R.id.fab2);
        t = findViewById(R.id.n3);
        t.setVisibility(View.GONE);
        listView = findViewById(R.id.expense_list);
        list = new ArrayList<>();
        populatelist();
        if (list.size()<1){
            listView.setVisibility(View.GONE);
            t.setVisibility(View.VISIBLE);
        }
        adapter = new ArrayAdapter<>(this,R.layout.single_item_layout,R.id.single_text,list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                PopupMenu popupMenu = new PopupMenu(Expense.this,view);
                popupMenu.getMenu().add("Remove");
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().toString().equals("Remove")){
                            int id = databaseHelper.deleteExpenseCategory(list.get(position));
                            if (id>0){
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(Expense.this, "Removed !", Toast.LENGTH_SHORT).show();}
                            else {
                                Toast.makeText(Expense.this, "Some error occured !", Toast.LENGTH_SHORT).show();
                            }
                        }
                        return true;
                    }
                });
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlert();
            }
        });
    }

    private void populatelist() {
        Cursor cursor = databaseHelper.getExpenseColumns();
        while (cursor.moveToNext()){
            String data = cursor.getString(0);
            list.add(data);
        }
    }

    private void showAlert() {
        AlertDialog.Builder ab= new AlertDialog.Builder(Expense.this);
        View v = getLayoutInflater().inflate(R.layout.single_edittext,null);
        ab.setView(v);
        ab.setTitle("Add Expense Category");
        ab.setCancelable(true);
        final EditText et = v.findViewById(R.id.single_edit);
        ab.setPositiveButton( "Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String data = et.getText().toString();
                if (!TextUtils.isEmpty(data)){
                    if(databaseHelper.insertExpenseCategory(data)){
                        list.add(data);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(Expense.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(Expense.this, "Oops some error occurred !", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(Expense.this, "Field was Empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ab.setNegativeButton("Cancel",null);
        ab.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
