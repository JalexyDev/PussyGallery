package com.jalexy.pussygallery.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.jalexy.pussygallery.database.DbParams.DATABASE_VERSION
import com.jalexy.pussygallery.database.DbParams.DB_NAME
import com.jalexy.pussygallery.database.DbParams.KEY_ID
import com.jalexy.pussygallery.database.DbParams.KEY_IS_FAVORITE
import com.jalexy.pussygallery.database.DbParams.KEY_PUSSY_ID
import com.jalexy.pussygallery.database.DbParams.KEY_SUB_ID
import com.jalexy.pussygallery.database.DbParams.KEY_URL
import com.jalexy.pussygallery.database.DbParams.TABLE_NAME
import com.jalexy.pussygallery.mvp.model.entities.MyPussy

class DatabaseHandler(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createPussiesTable = "CREATE TABLE $TABLE_NAME(" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_PUSSY_ID TEXT," +
                "$KEY_SUB_ID TEXT," +
                "$KEY_URL TEXT," +
                "$KEY_IS_FAVORITE INTEGER)"

        db?.execSQL(createPussiesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addFavoritePussy(pussy: MyPussy) {
        val db = writableDatabase

        val contentValues = createPussyContentValues(pussy)

        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun getFavoritePussy(id: Int, pussyId: String): MyPussy {
        val db = readableDatabase

        val selection: String
        val selValue: Array<String>

        if (pussyId.isNullOrEmpty()) {
            selection = "$KEY_ID=?"
            selValue = arrayOf(id.toString())
        } else {
            selection = "$KEY_PUSSY_ID=?"
            selValue = arrayOf(pussyId)
        }

        val cursor = db.query(
            TABLE_NAME,
            arrayOf(KEY_ID, KEY_PUSSY_ID, KEY_SUB_ID, KEY_URL, KEY_IS_FAVORITE),
            selection, selValue,
            null, null, null, null
        )

        var pussy: MyPussy? = null

        cursor?.let {
            cursor.use { cursor ->
                it.moveToFirst()
                pussy = createPussyFromCursor(cursor)
            }
        }

        db.close()

        return pussy ?: MyPussy.EMPTY_PUSSY
    }

    fun getAllFavorites(): ArrayList<MyPussy> {
        val db = readableDatabase
        val pussiesList = ArrayList<MyPussy>()

        val selectAllPussies = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectAllPussies, null)

        cursor?.let { it ->
            if (it.moveToFirst()) {
                it.use { cur ->
                    var pussy: MyPussy?

                    do {
                        pussy = createPussyFromCursor(cur)
                        pussy?.let { pussiesList.add(pussy) }

                    } while (cur.moveToNext())
                }
            }
        }

        db.close()

        return pussiesList
    }

    fun deletePussyFromFavorites(pussy: MyPussy) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$KEY_ID=?", arrayOf(pussy.id.toString()))
        db.close()
    }

    private fun createPussyContentValues(pussy: MyPussy) =
        ContentValues().apply {
            put(KEY_PUSSY_ID, pussy.pussyId)
            put(KEY_SUB_ID, pussy.subId)
            put(KEY_URL, pussy.url)
            put(KEY_IS_FAVORITE, MyPussy.TRUE)
        }


    private fun createPussyFromCursor(cursor: Cursor): MyPussy? {
        return try {
            MyPussy(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4))
        } catch (e: CursorIndexOutOfBoundsException) {
            null
        }
    }
}