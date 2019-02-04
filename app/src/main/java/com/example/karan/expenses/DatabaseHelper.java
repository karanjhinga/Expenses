package com.example.karan.expenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper {
    public static final String TYPE_INCOME = "income";
    public static final String TYPE_EXPENSE = "expense";
    public static final String Default = "Select Category...";
    DBHelper dbHelper;
    public DatabaseHelper(Context context){
        dbHelper = new DBHelper(context);
    }

    public Cursor getIncomeRecords(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String columns[] = {DBHelper.VALUE_COLUMN, DBHelper.Type, DBHelper.CATEGORIES_COLUMN, DBHelper.DESCRIPTION_COLUMN, DBHelper.ID};
        Cursor cursor = db.query(DBHelper.RECORDS_TABLE_NAME,columns,DBHelper.Type+" =?",new String[]{TYPE_INCOME},null,null,null);
        return cursor;
    }
    public Cursor getExpenseRecords(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String columns[] = {DBHelper.VALUE_COLUMN, DBHelper.Type, DBHelper.CATEGORIES_COLUMN, DBHelper.DESCRIPTION_COLUMN, DBHelper.ID};
        Cursor cursor = db.query(DBHelper.RECORDS_TABLE_NAME,columns,DBHelper.Type+" =?",new String[]{TYPE_EXPENSE},null,null,null);
        return cursor;
    }

    public Cursor getIncomeColumns(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DBHelper.INCOME_COLUMN};
        Cursor cursor = db.query(DBHelper.INCOME_TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }
    public Cursor getExpenseColumns(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String columns[]={DBHelper.EXPENSE_COLUMN};
        Cursor cursor = db.query(DBHelper.EXPENSE_TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }
    public Cursor getRecords(){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        String columns[] = {DBHelper.VALUE_COLUMN, DBHelper.Type, DBHelper.CATEGORIES_COLUMN, DBHelper.DESCRIPTION_COLUMN, DBHelper.ID};
        Cursor cursor = db.query(DBHelper.RECORDS_TABLE_NAME,columns,null,null,null,null,null);
        return cursor;
    }
    public boolean insertIncomeCategory(String income){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.INCOME_COLUMN,income);
        long id = db.insert(DBHelper.INCOME_TABLE_NAME,null,contentValues);
        if(id != -1){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean insertExpenseCategory(String expense){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.EXPENSE_COLUMN,expense);
        long id = db.insert(DBHelper.EXPENSE_TABLE_NAME,null,contentValues);
        if(id != -1){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean insertRecord(float value,String type,String category,String disription){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.VALUE_COLUMN,value);
        contentValues.put(DBHelper.Type,type);
        contentValues.put(DBHelper.CATEGORIES_COLUMN,category);
        contentValues.put(DBHelper.DESCRIPTION_COLUMN,disription);
        long id = db.insert(DBHelper.RECORDS_TABLE_NAME,null,
                contentValues);
        if(id != -1){
            return true;
        }
        else {
            return false;
        }
    }
    public int deleteIncomeCategory(String income){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereArgs[] ={income};
        int id= db.delete(DBHelper.INCOME_TABLE_NAME,DBHelper.INCOME_COLUMN +" =?",whereArgs);
        return id;
    }
    public int deleteExpenseCategory(String expense){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereArgs[] = {expense};
        int id = db.delete(DBHelper.EXPENSE_TABLE_NAME,DBHelper.EXPENSE_COLUMN+" =?",whereArgs);
        return id;
    }
    public int deleteRecord(float pid){
        SQLiteDatabase db= dbHelper.getWritableDatabase();
        int id = db.delete(DBHelper.RECORDS_TABLE_NAME,DBHelper.ID+" =?",new String[]{Float.toString(pid)});
        return id;
    }

    static class DBHelper extends SQLiteOpenHelper{

        public static final String DATABASE_NAME = "mydata";
        public static final int DATABASE_VERSION = 1;

        public DBHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        public static final String INCOME_COLUMN = "incomes";
        public static final String INCOME_TABLE_NAME ="income_table";
        public static final String CREATE_TABLE_INCOME = "CREATE TABLE "+INCOME_TABLE_NAME+ " ( "+INCOME_COLUMN+" VARCHAR(50) PRIMARY KEY);";

        public static final String EXPENSE_TABLE_NAME = "expense_table";
        public static final String EXPENSE_COLUMN = "expenses";
        public static final String CREATE_TABLE_EXPENSE = "CREATE TABLE "+EXPENSE_TABLE_NAME+ " ( "+EXPENSE_COLUMN +" VARCHAR(50) PRIMARY KEY);";

        public static final String RECORDS_TABLE_NAME = "records";
        public static final String ID = "id";
        public static final String Type = "inc_exp";
        public static final String CATEGORIES_COLUMN = "cats";
        public static final String VALUE_COLUMN = "value";
        public static final String DESCRIPTION_COLUMN = "descriptions";
        public static final String CREATE_TABLE_RECORDS = "CREATE TABLE "+RECORDS_TABLE_NAME+" ( "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+VALUE_COLUMN+" FLOAT, "+Type+" VARCHAR(50), "+CATEGORIES_COLUMN+" VARCHAR(50), "+DESCRIPTION_COLUMN+" TEXT);";
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_INCOME);
            db.execSQL(CREATE_TABLE_EXPENSE);
            db.execSQL(CREATE_TABLE_RECORDS);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+INCOME_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+EXPENSE_TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+RECORDS_TABLE_NAME);

            db.execSQL(CREATE_TABLE_INCOME);
            db.execSQL(CREATE_TABLE_EXPENSE);
            db.execSQL(CREATE_TABLE_RECORDS);
        }
    }
}
