package de.baumann.sieben;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity4 extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private boolean timerStarted = true;
    private TextView textView;
    private ProgressBar progressBar;
    private TTSManager ttsManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.a04);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.act_4);

        long startTime = 30 * 1000;
        long interval = 100;
        countDownTimer = new CountDownTimerActivity(startTime, interval);
        countDownTimer.start();

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setRotation(180);

        textView = (TextView) this.findViewById(R.id.timer);
        textView.setText(textView.getText() + String.valueOf(startTime / 1000));

        ttsManager = new TTSManager();
        ttsManager.init(this);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.pause);
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
                    String text = getResources().getString(R.string.sn_pause);
                    ttsManager.initQueue(text);
                    countDownTimer.cancel();
                    timerStarted = false;
                    fab.setImageResource(R.drawable.play);
                    Snackbar.make(view, R.string.sn_pause, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        imageView.setOnTouchListener(new OnSwipeTouchListener(MainActivity4.this) {
            public void onSwipeTop() {
                countDownTimer.start();
                timerStarted = true;
                fab.setImageResource(R.drawable.pause);
                Snackbar.make(imageView, R.string.sn_weiter, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

            public void onSwipeRight() {
                Intent intent_in = new Intent(de.baumann.sieben.MainActivity4.this, Pause2.class);
                startActivity(intent_in);
                overridePendingTransition(0, 0);
                countDownTimer.cancel();
                finish();
            }

            public void onSwipeLeft() {
                Intent intent_in = new Intent(de.baumann.sieben.MainActivity4.this, Pause4.class);
                startActivity(intent_in);
                overridePendingTransition(0, 0);
                countDownTimer.cancel();
                finish();
            }

            public void onSwipeBottom() {
                String text = getResources().getString(R.string.sn_pause);
                ttsManager.initQueue(text);
                countDownTimer.cancel();
                timerStarted = false;
                fab.setImageResource(R.drawable.play);
                Snackbar.make(imageView, R.string.sn_pause, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public class CountDownTimerActivity extends CountDownTimer {

        public CountDownTimerActivity(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            progressBar.setProgress(0);
            Intent intent_in = new Intent(MainActivity4.this, Pause4.class);
            startActivity(intent_in);
            overridePendingTransition(0, 0);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if (millisUntilFinished > 29500) {
                String text = getResources().getString(R.string.act_4);
                ttsManager.initQueue(text);
            }

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
            final SpannableString s = new SpannableString(Html.fromHtml(getString(R.string.about_text)));
            Linkify.addLinks(s, Linkify.WEB_URLS);

            final AlertDialog d = new AlertDialog.Builder(MainActivity4.this)
                    .setTitle(R.string.about_title)
                    .setMessage( s )
                    .setPositiveButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
            d.show();
            ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (id == R.id.action_changelog) {
            final SpannableString s = new SpannableString(Html.fromHtml(getString(R.string.changelog_text)));
            Linkify.addLinks(s, Linkify.WEB_URLS);

            final AlertDialog d = new AlertDialog.Builder(MainActivity4.this)
                    .setTitle(R.string.action_changelog)
                    .setMessage( s )
                    .setPositiveButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
            d.show();
            ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }

        if (id == R.id.action_help) {
            final SpannableString s = new SpannableString(Html.fromHtml(getString(R.string.help_text)));
            Linkify.addLinks(s, Linkify.WEB_URLS);

            final AlertDialog d = new AlertDialog.Builder(MainActivity4.this)
                    .setMessage( s )
                    .setPositiveButton(getString(R.string.about_yes),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).show();
            d.show();
            ((TextView)d.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        countDownTimer.cancel();
        finish();
    }
}