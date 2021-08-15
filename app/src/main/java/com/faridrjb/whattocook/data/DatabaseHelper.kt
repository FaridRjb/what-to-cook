package com.faridrjb.whattocook.data

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteOpenHelper
import com.faridrjb.whattocook.data.DatabaseHelper
import com.faridrjb.whattocook.Food
import kotlin.Throws
import android.database.sqlite.SQLiteDatabase
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.ArrayList
import kotlin.jvm.Synchronized

class DatabaseHelper(var context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {

    private val allColumns = arrayOf(
        Food.KEY_NAME,
        Food.KEY_INITS,
        Food.KEY_AMOUNT,
        Food.KEY_ESS_INITS,
        Food.KEY_INSTR,
        Food.KEY_PHOTO
    )
    var sqliteDataBase: SQLiteDatabase? = null

    init {
        if (checkDataBase()) {
            this.openDataBase()
        } else {
            createDataBase()
        }
    }

    @Throws(IOException::class)
    fun createDataBase() {
        this.writableDatabase
        copyDataBase()
    }

    private fun checkDataBase(): Boolean {
        val databaseFile = File(DB_PATH + DATABASE_NAME)
        return databaseFile.exists()
    }

    @Throws(IOException::class)
    private fun copyDataBase() {
        val myInput = context.assets.open(DATABASE_NAME)
        val outFileName = DB_PATH + DATABASE_NAME
        val myOutput: OutputStream = FileOutputStream(outFileName)
        val buffer = ByteArray(1024)
        var length: Int
        while (myInput.read(buffer).also { length = it } > 0) {
            myOutput.write(buffer, 0, length)
        }
        myOutput.flush()
        myOutput.close()
        myInput.close()
    }

    @Throws(SQLException::class)
    fun openDataBase() {
        //Open the database
        val myPath = DB_PATH + DATABASE_NAME
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Synchronized
    override fun close() {
        if (sqliteDataBase != null) sqliteDataBase!!.close()
        super.close()
    }

    // Food Things ---------------------------------------------------------------------------------
    fun getFood(selection: String): ArrayList<Food> {
        val resList = ArrayList<Food>()
        val cursor = sqliteDataBase!!.query(
            TABLE_Name,
            allColumns,
            Food.KEY_NAME + " LIKE '%" + selection + "%'",
            null,
            null,
            null,
            Food.KEY_NAME
        )
        if (cursor.moveToFirst()) {
            do {
                val food = Food()
                food.foodName = cursor.getString(cursor.getColumnIndex(Food.KEY_NAME))
                food.initsNeeded = cursor.getString(cursor.getColumnIndex(Food.KEY_INITS))
                food.initsAmount = cursor.getString(cursor.getColumnIndex(Food.KEY_AMOUNT))
                food.essInitsNeeded = cursor.getString(cursor.getColumnIndex(Food.KEY_ESS_INITS))
                food.instruction = cursor.getString(cursor.getColumnIndex(Food.KEY_INSTR))
                food.photo = cursor.getString(cursor.getColumnIndex(Food.KEY_PHOTO))
                resList.add(food)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return resList
    }

    fun getFoods(list: ArrayList<String>): ArrayList<Food> {
        val resList = ArrayList<Food>()
        for (i in list.indices) {
            val selection = list[i]
            val cursor = sqliteDataBase!!.query(
                TABLE_Name,
                allColumns,
                Food.KEY_NAME + " LIKE '%" + selection + "%'",
                null,
                null,
                null,
                null
            )
            if (cursor.moveToFirst()) {
                do {
                    val food = Food()
                    food.foodName = cursor.getString(cursor.getColumnIndex(Food.KEY_NAME))
                    food.initsNeeded = cursor.getString(cursor.getColumnIndex(Food.KEY_INITS))
                    food.initsAmount = cursor.getString(cursor.getColumnIndex(Food.KEY_AMOUNT))
                    food.instruction = cursor.getString(cursor.getColumnIndex(Food.KEY_INSTR))
                    food.photo = cursor.getString(cursor.getColumnIndex(Food.KEY_PHOTO))
                    resList.add(food)
                } while (cursor.moveToNext())
            }
        }
        return resList
    }

    val foodNames: ArrayList<String>
        get() {
            val res = ArrayList<String>()
            val cursor =
                sqliteDataBase!!.rawQuery("SELECT " + Food.KEY_NAME + " FROM " + TABLE_Name, null)
            if (cursor.moveToFirst()) {
                do {
                    res.add(cursor.getString(0))
                } while (cursor.moveToNext())
            }
            return res
        }

    fun getFoodInits(foodName: String): String {
        var res = ""
        val cursor = sqliteDataBase!!.rawQuery(
            "SELECT " + Food.KEY_INITS + " FROM " + TABLE_Name + " WHERE " + Food.KEY_NAME + " = '" + foodName + "'",
            null
        )
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_INITS))
        }
        cursor.close()
        return res
    }

    fun getFoodInitsAmount(foodName: String): String {
        var res = ""
        val cursor = sqliteDataBase!!.rawQuery(
            "SELECT " + Food.KEY_AMOUNT + " FROM " + TABLE_Name + " WHERE " + Food.KEY_NAME + " = '" + foodName + "'",
            null
        )
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_AMOUNT))
        }
        cursor.close()
        return res
    }

    fun getEssFoodInits(foodName: String): String {
        var res = ""
        val cursor = sqliteDataBase!!.rawQuery(
            "SELECT " + Food.KEY_ESS_INITS + " FROM " + TABLE_Name + " WHERE " + Food.KEY_NAME + " = '" + foodName + "'",
            null
        )
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_ESS_INITS))
        }
        cursor.close()
        return res
    }

    fun getFoodInstruc(foodName: String): String {
        var res = ""
        val cursor = sqliteDataBase!!.rawQuery(
            "SELECT " + Food.KEY_INSTR + " FROM " + TABLE_Name + " WHERE " + Food.KEY_NAME + " = '" + foodName + "'",
            null
        )
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_INSTR))
        }
        cursor.close()
        return res
    }

    fun getFoodPhoto(foodName: String): String {
        var res = ""
        val cursor = sqliteDataBase!!.rawQuery(
            "SELECT " + Food.KEY_PHOTO + " FROM " + TABLE_Name + " WHERE " + Food.KEY_NAME + " = '" + foodName + "'",
            null
        )
        if (cursor.moveToFirst()) {
            res = cursor.getString(cursor.getColumnIndex(Food.KEY_PHOTO))
        }
        cursor.close()
        return res
    }

    //----------------------------------------------------------------------------------------------
    override fun onCreate(db: SQLiteDatabase) {}
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        //The Android's default system path of your application database.
        private const val DB_PATH = "/data/data/com.faridrjb.whattocook/databases/"

        // Data Base Name.
        private const val DATABASE_NAME = "DB.sqlite"

        // Data Base Version.
        private const val DATABASE_VERSION = 1

        // Table Names of Data Base.
        const val TABLE_Name = "data"
    }
}