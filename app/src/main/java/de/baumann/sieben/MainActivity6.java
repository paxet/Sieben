package de.baumann.sieben;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity6 extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private boolean timerStarted = true;
    public TextView textView;
    private final long startTime = 30 * 1000;
    private final long interval = 1 * 100;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        setSupportActionBar(toolbar);
        setTitle(R.string.act_6);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!timerStarted) {
                    countDownTimer.start();
                    timerStarted = true;
                    fab.setImageResource(R.drawable.pause);
                    Snackbar.make(view, R.string.sn_weiter, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    countDownTimer.cancel();
                    timerStarted = false;
                    fab.setImageResource(R.drawable.play);
                    Snackbar.make(view, R.string.sn_pause, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        textView = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new CountDownTimerActivity(startTime, interval);
        textView.setText(textView.getText() + String.valueOf(startTime / 1000));
        progressBar.setRotation(180);
        fab.setImageResource(R.drawable.pause);
        imageView.setImageResource(R.drawable.a06);
        countDownTimer.start();
        progressBar.setProgress(100);
    }


    public class CountDownTimerActivity extends CountDownTimer {

        public CountDownTimerActivity(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            progressBar.setProgress(0);
            Intent intent_in = new Intent(MainActivity6.this, Pause6.class);
            startActivity(intent_in);
            overridePendingTransition(0, 0);
            final ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView.setText("" + millisUntilFinished/1000);
            int progress = (int) (millisUntilFinished/300);
            progressBar.setProgress(progress);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_license) {
            new AlertDialog.Builder(MainActivity6.this)
                    .setTitle(getString(R.string.about_title))
                    .setMessage(getString(R.string.about_text))
                    .setPositiveButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/scoute-dich/Sieben"));
                                    startActivity(i);
                                    dialog.cancel();
                                }
                            })
                    .setNeutralButton(getString(R.string.about_no),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .setNegativeButton(getString(R.string.about_yes2),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://almostfearless.com/the-21-minute-workout-or-7-minutes-if-youre-really-fit/"));
                                    startActivity(i);
                                    dialog.cancel();
                                }
                            }).show();
        }

        if (id == R.id.action_next) {
            Intent intent_in = new Intent(de.baumann.sieben.MainActivity6.this, Pause6.class);
            startActivity(intent_in);
            overridePendingTransition(0, 0);
            countDownTimer.cancel();
            finish();
        }

        if (id == R.id.action_before) {
            Intent intent_in = new Intent(de.baumann.sieben.MainActivity6.this, Pause4.class);
            startActivity(intent_in);
            overridePendingTransition(0, 0);
            countDownTimer.cancel();
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        finish();
    }
}