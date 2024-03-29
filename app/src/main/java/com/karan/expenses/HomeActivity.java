package com.karan.expenses;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity{

    TextView t1,t2,t3;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t1 = findViewById(R.id.Income);
        t2 = findViewById(R.id.Expense);
        t3 = findViewById(R.id.Balance);


    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences(PREFERENCES.SP_NAME,0);
        float inc=preferences.getFloat(PREFERENCES.INCOME,00.00f);
        float exp=preferences.getFloat(PREFERENCES.EXPENSE,00.00f);
        if(inc != 0){
            t1.setText(Float.toString(inc));
        }
        if(exp != 0){
            t2.setText(Float.toString(exp));
        }
        if(inc !=0 && exp !=0){
            t3.setText(Float.toString(inc-exp));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_income:
                startActivity(new Intent(HomeActivity.this, IncomeCategoriesActivity.class));
                break;
            case R.id.action_expense:
                startActivity(new Intent(HomeActivity.this, ExpenseCategoriesActivity.class));
                break;
            case R.id.action_about:
                showAbout();
                break;
        }
        return true;
    }

    private void showAbout() {
        AlertDialog.Builder ab = new AlertDialog.Builder(HomeActivity.this);
        ab.setTitle("About the application");
        ab.setMessage("Expenses is a simple app through which we can keep track on our day to day expenses. This app stores data locally and does'nt need any connectivity. This application is developed by \"Karan Jhinga\"");
        ab.setPositiveButton("OK",null);
        ab.show();

    }


    public void onBackPressed() {
        showExitDailog();
    }

    private void showExitDailog(){
        AlertDialog.Builder ab = new AlertDialog.Builder(HomeActivity.this);
        ab.setTitle("Exit Application");
        ab.setMessage("Are you sure you want to exit the application ?");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        ab.setCancelable(false);
        ab.setNegativeButton("No",null);
        ab.show();
    }

    public void addExpense(View view) {
        startActivity(new Intent(HomeActivity.this, AddExpenseActivity.class));
    }

    public void addIncome(View view) {
        startActivity(new Intent(HomeActivity.this, AddIncomeActivity.class));
    }

    public void showRecords(View view) {
        startActivity(new Intent(HomeActivity.this, AllRecordsActivity.class));
    }

}
