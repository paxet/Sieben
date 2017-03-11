/*
    This file is part of the HHS Moodle WebApp.

    HHS Moodle WebApp is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    HHS Moodle WebApp is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the Diaspora Native WebApp.

    If not, see <http://www.gnu.org/licenses/>.
 */

package de.baumann.sieben.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import de.baumann.sieben.R;


class DbAdapter_Statistics {

    //define static variable
    private static final int dbVersion =6;
    private static final String dbName = "ex_v01.db";
    private static final String dbTable = "ex_table";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context,dbName,null, dbVersion);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS "+dbTable+" (_id INTEGER PRIMARY KEY autoincrement, ex_title, ex_hms, ex_icon, ex_number, ex_average, ex_number_int, ex_averageInt, UNIQUE(ex_title))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+dbTable);
            onCreate(db);
        }
    }

    //establish connection with SQLiteDataBase
    private final Context c;
    private SQLiteDatabase sqlDb;

    DbAdapter_Statistics(Context context) {
        this.c = context;
    }
    void open() throws SQLException {
        DatabaseHelper dbHelper = new DatabaseHelper(c);
        sqlDb = dbHelper.getWritableDatabase();
    }

    //insert data
    @SuppressWarnings("SameParameterValue")
    void insert(String ex_title, String ex_hms, String ex_icon, String ex_number, String ex_average, String ex_number_int, String ex_averageInt) {
        if(!isExist(ex_title)) {
            sqlDb.execSQL("INSERT INTO ex_table (ex_title, ex_hms, ex_icon, ex_number, ex_average, ex_number_int, ex_averageInt) VALUES('" + ex_title + "','" + ex_hms + "','" + ex_icon + "','" + ex_number + "','" + ex_average + "','" + ex_number_int + "','" + ex_averageInt + "')");
        }
    }
    //check entry already in database or not
    private boolean isExist(String ex_title){
        String query = "SELECT ex_title FROM ex_table WHERE ex_title='"+ex_title+"' LIMIT 1";
        @SuppressLint("Recycle") Cursor row = sqlDb.rawQuery(query, null);
        return row.moveToFirst();
    }

    //fetch data
    Cursor fetchAllData(Context context) {

        PreferenceManager.setDefaultValues(context, R.xml.user_settings, false);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String[] columns = new String[]{"_id", "ex_title", "ex_hms", "ex_icon","ex_number","ex_average","ex_number_int","ex_averageInt"};

        if (sp.getString("sortDBF", "title").equals("title")) {
            return sqlDb.query(dbTable, columns, null, null, null, null, "ex_icon");
        } else if (sp.getString("sortDBF", "title").equals("number")) {
            return sqlDb.query(dbTable, columns, null, null, null, null, "ex_number_int" + " DESC");
        } else if (sp.getString("sortDBF", "title").equals("hms")) {
            return sqlDb.query(dbTable, columns, null, null, null, null, "ex_hms" + " DESC");
        } else if (sp.getString("sortDBF", "title").equals("average")) {
            return sqlDb.query(dbTable, columns, null, null, null, null, "ex_averageInt" + " DESC");
        }

        return null;
    }
}