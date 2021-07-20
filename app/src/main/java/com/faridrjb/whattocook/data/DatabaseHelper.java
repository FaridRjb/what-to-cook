package com.faridrjb.whattocook.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.faridrjb.whattocook.Food;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.faridrjb.whattocook/databases/";
    // Data Base Name.
    private static final String DATABASE_NAME = "DB.sqlite";
    // Data Base Version.
    private static final int DATABASE_VERSION = 1;
    // Table Names of Data Base.
    static final String TABLE_Name = "data";

    private final String[] allColumns = {Food.KEY_NAME, Food.KEY_INITS, Food.KEY_AMOUNT, Food.KEY_ESS_INITS, Food.KEY_INSTR, Food.KEY_PHOTO};

    public Context context;
    static SQLiteDatabase sqliteDataBase;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void createDataBase() throws IOException {
        //check if the database exists
        boolean databaseExist = checkDataBase();

        this.getWritableDatabase();
        copyDataBase();
    }

    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DATABASE_NAME);
        return databaseFile.exists();
    }

    private void copyDataBase() throws IOException{
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(sqliteDataBase != null)
            sqliteDataBase.close();
        super.close();
    }

    // Food Things ---------------------------------------------------------------------------------
    public ArrayList<Food> getFood(String selection) {
        ArrayList<Food> resList = new ArrayList<>();
        Cursor cursor = sqliteDataBase.query(TABLE_Name, allColumns, Food.KEY_NAME +" LIKE '%"+ selection +"%'", null, null, null, Food.KEY_NAME);
        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setFoodName(cursor.getString(cursor.getColumnIndex(Food.KEY_NAME)));
                food.setInitsNeeded(cursor.getString(cursor.getColumnIndex(Food.KEY_INITS)));
                food.setInitsAmount(cursor.getString(cursor.getColumnIndex(Food.KEY_AMOUNT)));
                food.setEssInitsNeeded(cursor.getString(cursor.getColumnIndex(Food.KEY_ESS_INITS)));
                food.setInstruction(cursor.getString(cursor.getColumnIndex(Food.KEY_INSTR)));
                food.setPhoto(cursor.getString(cursor.getColumnIndex(Food.KEY_PHOTO)));
                resList.add(food);
            } while (cursor.moveToNext());
        }
        return resList;
    }

    public ArrayList<Food> getFoods(ArrayList<String> list) {
        ArrayList<Food> resList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String selection = list.get(i);
            Cursor cursor = sqliteDataBase.query(TABLE_Name, allColumns, Food.KEY_NAME +" LIKE '%"+ selection +"%'", null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    Food food = new Food();
                    food.setFoodName(cursor.getString(cursor.getColumnIndex(Food.KEY_NAME)));
                    food.setInitsNeeded(cursor.getString(cursor.getColumnIndex(Food.KEY_INITS)));
                    food.setInitsAmount(cursor.getString(cursor.getColumnIndex(Food.KEY_AMOUNT)));
                    food.setInstruction(cursor.getString(cursor.getColumnIndex(Food.KEY_INSTR)));
                    food.setPhoto(cursor.getString(cursor.getColumnIndex(Food.KEY_PHOTO)));
                    resList.add(food);
                } while (cursor.moveToNext());
            }
        }
        return resList;
    }

    public ArrayList<String> getFoodNames() {
        ArrayList<String> res = new ArrayList<>();
        Cursor cursor = sqliteDataBase.rawQuery("SELECT "+ Food.KEY_NAME +" FROM "+ TABLE_Name, null);
        if (cursor.moveToFirst()) {
            do {
                res.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return res;
    }

    public String getFoodInits(String foodName) {
        String res = "";
        Cursor cursor = sqliteDataBase.rawQuery("SELECT "+ Food.KEY_INITS +" FROM "+ TABLE_Name +" WHERE "+ Food.KEY_NAME +" = '"+ foodName +"'", null);
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_INITS));
        }
        return res;
    }

    public String getFoodInitsAmount(String foodName) {
        String res = "";
        Cursor cursor = sqliteDataBase.rawQuery("SELECT "+ Food.KEY_AMOUNT +" FROM "+ TABLE_Name +" WHERE "+ Food.KEY_NAME +" = '"+ foodName +"'", null);
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_AMOUNT));
        }
        return res;
    }

    public String getEssFoodInits(String foodName) {
        String res = "";
        Cursor cursor = sqliteDataBase.rawQuery("SELECT "+ Food.KEY_ESS_INITS +" FROM "+ TABLE_Name +" WHERE "+ Food.KEY_NAME +" = '"+ foodName +"'", null);
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_ESS_INITS));
        }
        return res;
    }

    public String getFoodInstruc(String foodName) {
        String res = "";
        Cursor cursor = sqliteDataBase.rawQuery("SELECT "+ Food.KEY_INSTR +" FROM "+ TABLE_Name +" WHERE "+ Food.KEY_NAME +" = '"+ foodName +"'", null);
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_INSTR));
        }
        return res;
    }

    public String getFoodPhoto(String foodName) {
        String res = "";
        Cursor cursor = sqliteDataBase.rawQuery("SELECT "+ Food.KEY_PHOTO +" FROM "+ TABLE_Name +" WHERE "+ Food.KEY_NAME +" = '"+ foodName +"'", null);
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_PHOTO));
        }
        return res;
    }
    //----------------------------------------------------------------------------------------------

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
