/*
    This file is part of the Browser webview app.

    HHS Moodle WebApp is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    HHS Moodle WebApp is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the Browser webview app.

    If not, see <http://www.gnu.org/licenses/>.
 */

package de.baumann.sieben.helper;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.baumann.sieben.R;

public class Activity_statistics extends AppCompatActivity {

    private ListView listView = null;
    private DbAdapter_Statistics db;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_statistics);

        PreferenceManager.setDefaultValues(this, R.xml.user_settings, false);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_exercises, false);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_duration, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(Activity_statistics.this);
        setTitle();

        deleteDatabase("ex_v01.db");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        listView = (ListView)findViewById(R.id.list);

        //calling Notes_DbAdapter
        db = new DbAdapter_Statistics(this);
        db.open();



        setFilesList();
    }

    private void insert(String name, String icon , int time, int number) {

        PreferenceManager.setDefaultValues(this, R.xml.user_settings, false);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_exercises, false);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_duration, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(Activity_statistics.this);

        db = new DbAdapter_Statistics(this);
        db.open();

        String hms = String.format(Locale.getDefault(), "%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(time) % TimeUnit.MINUTES.toSeconds(1));

        String number_int;
        if (number < 10) {
            number_int = "00" + String.valueOf(number);
        } else if (number >= 10 && number < 100) {
            number_int = "0" + String.valueOf(number);
        } else {
            number_int = String.valueOf(number);
        }

        String number_string = String.valueOf(number);

        long seconds;
        String average_int;
        if (number > 0) {
            seconds = (time / number);
            average_int = String.format(Locale.getDefault(), "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(seconds),
                    TimeUnit.MILLISECONDS.toSeconds(seconds) % TimeUnit.MINUTES.toSeconds(1));
        } else {
            average_int = "00:00";
        }

        db.insert(name, getString(R.string.stat_time) + " " + hms, icon,
                getString(R.string.stat_number) + " " + number_string,
                getString(R.string.stat_average) + " " + average_int,
                number_int, average_int);
        Log.w("Seven", "Exercise added");
    }

    private void setFilesList() {

        insert(getString(R.string.act), "01", sharedPref.getInt("ex1_time", 0), sharedPref.getInt("ex1_number", 0));
        insert(getString(R.string.act_2), "02", sharedPref.getInt("ex2_time", 0), sharedPref.getInt("ex2_number", 0));
        insert(getString(R.string.act_3), "03", sharedPref.getInt("ex3_time", 0), sharedPref.getInt("ex3_number", 0));
        insert(getString(R.string.act_4), "04", sharedPref.getInt("ex4_time", 0), sharedPref.getInt("ex4_number", 0));
        insert(getString(R.string.act_5), "05", sharedPref.getInt("ex5_time", 0), sharedPref.getInt("ex5_number", 0));
        insert(getString(R.string.act_6), "06", sharedPref.getInt("ex6_time", 0), sharedPref.getInt("ex6_number", 0));
        insert(getString(R.string.act_7), "07", sharedPref.getInt("ex7_time", 0), sharedPref.getInt("ex7_number", 0));
        insert(getString(R.string.act_8), "08", sharedPref.getInt("ex8_time", 0), sharedPref.getInt("ex8_number", 0));
        insert(getString(R.string.act_9), "09", sharedPref.getInt("ex9_time", 0), sharedPref.getInt("ex9_number", 0));
        insert(getString(R.string.act_10), "10", sharedPref.getInt("ex10_time", 0), sharedPref.getInt("ex10_number", 0));
        insert(getString(R.string.act_11), "11", sharedPref.getInt("ex11_time", 0), sharedPref.getInt("ex11_number", 0));
        insert(getString(R.string.act_12), "12", sharedPref.getInt("ex12_time", 0), sharedPref.getInt("ex12_number", 0));

        //display data
        final int layoutstyle=R.layout.list_item;
        int[] xml_id = new int[] {
                R.id.textView_title,
                R.id.textView_hms,
                R.id.textView_number,
                R.id.textView_average
        };
        String[] column = new String[] {
                "ex_title",
                "ex_hms",
                "ex_number",
                "ex_average"
        };
        final Cursor row = db.fetchAllData(this);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, layoutstyle, row, column, xml_id, 0) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {

                Cursor row2 = (Cursor) listView.getItemAtPosition(position);
                final String ex_icon = row2.getString(row2.getColumnIndexOrThrow("ex_icon"));

                View v = super.getView(position, convertView, parent);
                final ImageView iv = (ImageView) v.findViewById(R.id.icon_notes);

                switch (ex_icon) {
                    case "01":
                        iv.setImageResource(R.drawable.a01b);
                        break;
                    case "02":
                        iv.setImageResource(R.drawable.a02b);
                        break;
                    case "03":
                        iv.setImageResource(R.drawable.a03b);
                        break;
                    case "04":
                        iv.setImageResource(R.drawable.a04b);
                        break;
                    case "05":
                        iv.setImageResource(R.drawable.a05b);
                        break;
                    case "06":
                        iv.setImageResource(R.drawable.a06b);
                        break;
                    case "07":
                        iv.setImageResource(R.drawable.a07b);
                        break;
                    case "08":
                        iv.setImageResource(R.drawable.a08b);
                        break;
                    case "09":
                        iv.setImageResource(R.drawable.a09b);
                        break;
                    case "10":
                        iv.setImageResource(R.drawable.a10b);
                        break;
                    case "11":
                        iv.setImageResource(R.drawable.a11b);
                        break;
                    case "12":
                        iv.setImageResource(R.drawable.a12b);
                        break;
                    default:
                        iv.setImageResource(R.drawable.a01b);
                        break;
                }

                return v;
            }
        };

        listView.setAdapter(adapter);
    }

    private void setTitle () {
        if (sharedPref.getString("sortDBF", "title").equals("title")) {
            setTitle(getString(R.string.action_stat) + " | " + getString(R.string.sort_icon2));
        } else if (sharedPref.getString("sortDBF", "title").equals("number")) {
            setTitle(getString(R.string.action_stat) + " | " + getString(R.string.sort_number2));
        } else if (sharedPref.getString("sortDBF", "title").equals("hms")) {
            setTitle(getString(R.string.action_stat) + " | " + getString(R.string.sort_hms2));
        } else {
            setTitle(getString(R.string.action_stat) + " | " + getString(R.string.sort_average2));
        }
    }

    private void reset (String number, String time) {
        sharedPref.edit().putInt(number, 0).apply();
        sharedPref.edit().putInt(time, 0).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            case R.id.ex_icon:
                sharedPref.edit().putString("sortDBF", "title").apply();
                setTitle();
                setFilesList();
                return true;
            case R.id.ex_number:
                sharedPref.edit().putString("sortDBF", "number").apply();
                setTitle();
                setFilesList();
                return true;
            case R.id.ex_hms:
                sharedPref.edit().putString("sortDBF", "hms").apply();
                setTitle();
                setFilesList();
                return true;
            case R.id.ex_average:
                sharedPref.edit().putString("sortDBF", "average").apply();
                setTitle();
                setFilesList();
                return true;
            
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_reset:
                final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(Activity_statistics.this)
                        .setTitle(R.string.app_con)
                        .setMessage(R.string.app_con_message)
                        .setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                reset("ex1_number", "ex1_time");
                                reset("ex2_number", "ex2_time");
                                reset("ex3_number", "ex3_time");
                                reset("ex4_number", "ex4_time");
                                reset("ex5_number", "ex5_time");
                                reset("ex6_number", "ex6_time");
                                reset("ex7_number", "ex7_time");
                                reset("ex8_number", "ex8_time");
                                reset("ex9_number", "ex9_time");
                                reset("ex10_number", "ex10_time");
                                reset("ex11_number", "ex11_time");
                                reset("ex12_number", "ex12_time");
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.app_no, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.cancel();
                            }
                        });
                dialog.show();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}