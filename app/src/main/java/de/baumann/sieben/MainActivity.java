package de.baumann.sieben;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private TextView textView2;
    private ProgressBar progressBar;
    private TTSManager ttsManager = null;
    private ImageView imageView;

    private boolean isPaused = false;
    private boolean isCanceled = false;
    private long timeRemaining = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.user_settings, false);

        imageView = (ImageView) findViewById(R.id.imageView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        assert progressBar != null;
        progressBar.setRotation(180);

        textView = (TextView) this.findViewById(R.id.timer);
        textView2 = (TextView) this.findViewById(R.id.timer2);
        assert textView2 != null;
        textView2.setText(R.string.start);


        ttsManager = new TTSManager();
        ttsManager.init(this);

        long millisInFuture = 5000;
        long countDownInterval = 100;


        //Initialize a new CountDownTimer instance
        new CountDownTimer(millisInFuture,countDownInterval){
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            public void onTick(long millisUntilFinished){
                //do something in every tick
                if(isPaused || isCanceled)
                {
                    cancel();
                }
                else {
                    textView.setText(String.valueOf(millisUntilFinished / 1000));
                    textView2.setText("");
                    int progress = (int) (millisUntilFinished/50);
                    progressBar.setProgress(progress);
                    timeRemaining = millisUntilFinished;
                }
            }
            public void onFinish(){

                if (sharedPref.getBoolean ("beep", false)){
                    final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                    tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                }

                if (sharedPref.getBoolean ("tts", false)){
                    String text = getResources().getString(R.string.act);
                    ttsManager.initQueue(text);
                }

                progressBar.setProgress(0);
                Intent intent_in = new Intent(MainActivity.this, MainActivity1.class);
                startActivity(intent_in);
                overridePendingTransition(0, 0);
                finish();
            }
        };

        imageView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
            public void onSwipeTop() {
                isPaused = false;
                isCanceled = false;

                long millisInFuture = timeRemaining;
                long countDownInterval = 100;

                new CountDownTimer(millisInFuture, countDownInterval){
                    public void onTick(long millisUntilFinished){
                        if(isPaused || isCanceled)
                        {
                            cancel();
                        }
                        else {
                            textView.setText(String.valueOf(millisUntilFinished / 1000));
                            textView2.setText("");
                            int progress = (int) (millisUntilFinished/50);
                            progressBar.setProgress(progress);
                            timeRemaining = millisUntilFinished;
                        }
                    }
                    public void onFinish(){

                        if (sharedPref.getBoolean ("beep", false)){
                            final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
                            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
                        }

                        if (sharedPref.getBoolean ("tts", false)){
                            String text = getResources().getString(R.string.act);
                            ttsManager.initQueue(text);
                        }

                        progressBar.setProgress(0);
                        Intent intent_in = new Intent(MainActivity.this, MainActivity1.class);
                        startActivity(intent_in);
                        overridePendingTransition(0, 0);
                        finish();
                    }
                }.start();

                if (sharedPref.getBoolean ("tts", false)){
                    String text = getResources().getString(R.string.start2);
                    ttsManager.initQueue(text);
                }

                Snackbar.make(imageView, R.string.start2, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            public void onSwipeRight() {
                if (sharedPref.getBoolean ("tts", false)){
                    String text = getResources().getString(R.string.sn_first);
                    ttsManager.initQueue(text);
                }

                Snackbar.make(imageView, R.string.sn_first, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            public void onSwipeLeft() {
                if (sharedPref.getBoolean ("tts", false)){
                    String text = getResources().getString(R.string.pau);
                    ttsManager.initQueue(text);
                }

                Intent intent_in = new Intent(MainActivity.this, Pause.class);
                startActivity(intent_in);
                overridePendingTransition(0, 0);
                isCanceled = true;
                finish();
            }

            public void onSwipeBottom() {
                if (sharedPref.getBoolean ("tts", false)){
                    String text = getResources().getString(R.string.sn_pause);
                    ttsManager.initQueue(text);
                }

                isPaused = true;
                Snackbar.make(imageView, R.string.sn_pause, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent_in = new Intent(MainActivity.this, UserSettingsActivity.class);
            startActivity(intent_in);
            overridePendingTransition(0, 0);
            isCanceled = true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        isCanceled = true;
        finish();
    }
}
