package de.baumann.sieben.helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import de.baumann.sieben.R;

public class UserSettingsActivity_Duration extends AppCompatActivity {

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_settings);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(R.string.action_durationEx);

        getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, new SettingsFragment())
                            .commit();

        PreferenceManager.setDefaultValues(this, R.xml.user_settings, false);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_exercises, false);
        PreferenceManager.setDefaultValues(this, R.xml.user_settings_duration, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(UserSettingsActivity_Duration.this);
    }

    public static class SettingsFragment extends PreferenceFragment {

        private void setTime (final String exercise) {

            PreferenceManager.setDefaultValues(getActivity(), R.xml.user_settings, false);
            PreferenceManager.setDefaultValues(getActivity(), R.xml.user_settings_exercises, false);
            PreferenceManager.setDefaultValues(getActivity(), R.xml.user_settings_duration, false);
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View dialogView = View.inflate(getActivity(), R.layout.seekbar_dialog_workout, null);

            final TextView edit_title = (TextView) dialogView.findViewById(R.id.textView);
            final SeekBar seekBar = (SeekBar) dialogView.findViewById(R.id.seekBar);
            String duration = sharedPref.getString(exercise, "30");
            edit_title.setText(duration);
            seekBar.setProgress(Integer.parseInt(duration) - 15);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    String text = Integer.toString(progress + 15);
                    edit_title.setText(text);
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            builder.setView(dialogView);
            builder.setTitle(getString(R.string.action_durationEx));
            builder.setPositiveButton(getString(R.string.app_ok), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    sharedPref.edit().putString(exercise, edit_title.getText().toString()).apply();
                    final String durationWorkout = getString(R.string.app_chosenTime)
                            + " " + sharedPref.getString(exercise, "30") + " " + getString(R.string.app_sec)
                            + " " + getString(R.string.app_standardTime) + " 30)";
                    Preference customPref = findPreference(exercise);
                    customPref.setSummary(durationWorkout);
                }
            });
            builder.setNegativeButton(getString(R.string.app_no), new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.cancel();
                }
            });

            final AlertDialog dialog2 = builder.create();
            // Display the custom alert dialog on interface
            dialog2.show();
        }

        private void updateSettings (final String exercise) {

            PreferenceManager.setDefaultValues(getActivity(), R.xml.user_settings, false);
            PreferenceManager.setDefaultValues(getActivity(), R.xml.user_settings_exercises, false);
            PreferenceManager.setDefaultValues(getActivity(), R.xml.user_settings_duration, false);
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

            final String durationWorkout = getString(R.string.app_chosenTime)
                    + " " + sharedPref.getString(exercise, "30") + " " + getString(R.string.app_sec)
                    + " " + getString(R.string.app_standardTime) + " 30)";
            Preference customPref = findPreference(exercise);
            customPref.setSummary(durationWorkout);
        }

        private void add_durationListener_ex1() {
            Preference reset = findPreference("duration_ex1");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex1");
                    return true;
                }
            });
        }

        private void add_durationListener_ex2() {
            Preference reset = findPreference("duration_ex2");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex2");
                    return true;
                }
            });
        }

        private void add_durationListener_ex3() {
            Preference reset = findPreference("duration_ex3");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex3");
                    return true;
                }
            });
        }

        private void add_durationListener_ex4() {
            Preference reset = findPreference("duration_ex4");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex4");
                    return true;
                }
            });
        }

        private void add_durationListener_ex5() {
            Preference reset = findPreference("duration_ex5");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex5");
                    return true;
                }
            });
        }

        private void add_durationListener_ex6() {
            Preference reset = findPreference("duration_ex6");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex6");
                    return true;
                }
            });
        }

        private void add_durationListener_ex7() {
            Preference reset = findPreference("duration_ex7");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex7");
                    return true;
                }
            });
        }

        private void add_durationListener_ex8() {
            Preference reset = findPreference("duration_ex8");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex8");
                    return true;
                }
            });
        }

        private void add_durationListener_ex9() {
            Preference reset = findPreference("duration_ex9");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex9");
                    return true;
                }
            });
        }

        private void add_durationListener_ex10() {
            Preference reset = findPreference("duration_ex10");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex10");
                    return true;
                }
            });
        }

        private void add_durationListener_ex11() {
            Preference reset = findPreference("duration_ex11");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex11");
                    return true;
                }
            });
        }

        private void add_durationListener_ex12() {
            Preference reset = findPreference("duration_ex12");
            reset.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference pref) {
                    setTime("duration_ex12");
                    return true;
                }
            });
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.user_settings_duration);
            add_durationListener_ex1();
            add_durationListener_ex2();
            add_durationListener_ex3();
            add_durationListener_ex4();
            add_durationListener_ex5();
            add_durationListener_ex6();
            add_durationListener_ex7();
            add_durationListener_ex8();
            add_durationListener_ex9();
            add_durationListener_ex10();
            add_durationListener_ex11();
            add_durationListener_ex12();

            new Handler().postDelayed(new Runnable() {
                public void run() {
                    updateSettings("duration_ex1");
                    updateSettings("duration_ex2");
                    updateSettings("duration_ex3");
                    updateSettings("duration_ex4");
                    updateSettings("duration_ex5");
                    updateSettings("duration_ex6");
                    updateSettings("duration_ex7");
                    updateSettings("duration_ex8");
                    updateSettings("duration_ex9");
                    updateSettings("duration_ex10");
                    updateSettings("duration_ex11");
                    updateSettings("duration_ex12");
                }
            }, 200);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_duration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.action_reset) {

            final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(UserSettingsActivity_Duration.this)
                    .setTitle(R.string.app_con)
                    .setMessage(R.string.app_con_message)
                    .setPositiveButton(R.string.app_ok, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            sharedPref.edit().putString("duration_ex1", "0").apply();
                            sharedPref.edit().putString("duration_ex2", "0").apply();
                            sharedPref.edit().putString("duration_ex3", "0").apply();
                            sharedPref.edit().putString("duration_ex4", "0").apply();
                            sharedPref.edit().putString("duration_ex5", "0").apply();
                            sharedPref.edit().putString("duration_ex6", "0").apply();
                            sharedPref.edit().putString("duration_ex7", "0").apply();
                            sharedPref.edit().putString("duration_ex8", "0").apply();
                            sharedPref.edit().putString("duration_ex9", "0").apply();
                            sharedPref.edit().putString("duration_ex10", "0").apply();
                            sharedPref.edit().putString("duration_ex11", "0").apply();
                            sharedPref.edit().putString("duration_ex12", "0").apply();

                            finish();
                        }
                    })
                    .setNegativeButton(R.string.app_no, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.cancel();
                        }
                    });
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
