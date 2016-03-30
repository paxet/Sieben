package de.baumann.sieben;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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


public class Pause3 extends AppCompatActivity {

    private TextView textView;
    private ProgressBar progressBar;
    private TTSManager ttsManager = null;
    private ImageView imageView;

    private boolean isPaused = false;
    private boolean isCanceled = false;
    private long timeRemaining = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(R.mipmap.a04);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.pau_3);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setRotation(180);

        textView = (TextView) this.findViewById(R.id.timer);

        ttsManager = new TTSManager();
        ttsManager.init(this);

        CountDownTimer timer;
        long millisInFuture = 10000;
        long countDownInterval = 100;


        //Initialize a new CountDownTimer instance
        timer = new CountDownTimer(millisInFuture,countDownInterval){
            public void onTick(long millisUntilFinished){
                //do something in every tick
                if(isPaused || isCanceled)
                {
                    cancel();
                }
                else {
                    if (millisUntilFinished > 9650) {
                        String text = getResources().getString(R.string.pau_3);
                        ttsManager.initQueue(text);
                    }
                    textView.setText("" + millisUntilFinished / 1000);
                    int progress = (int) (millisUntilFinished/100);
                    progressBar.setProgress(progress);
                    timeRemaining = millisUntilFinished;
                }
            }
            public void onFinish(){
                progressBar.setProgress(0);
                Intent intent_in = new Intent(de.baumann.sieben.Pause3.this, MainActivity4.class);
                startActivity(intent_in);
                overridePendingTransition(0, 0);
                finish();
            }
        }.start();

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.pause);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPaused || isCanceled) {

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
                                textView.setText("" + millisUntilFinished / 1000);
                                int progress = (int) (millisUntilFinished/100);
                                progressBar.setProgress(progress);
                                timeRemaining = millisUntilFinished;
                            }
                        }
                        public void onFinish(){
                            progressBar.setProgress(0);
                            Intent intent_in = new Intent(de.baumann.sieben.Pause3.this, MainActivity4.class);
                            startActivity(intent_in);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }.start();
                    String text = getResources().getString(R.string.sn_weiter);
                    ttsManager.initQueue(text);
                    fab.setImageResource(R.drawable.pause);
                    Snackbar.make(imageView, R.string.sn_weiter, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    String text = getResources().getString(R.string.sn_pause);
                    ttsManager.initQueue(text);
                    isPaused = true;
                    Snackbar.make(imageView, R.string.sn_pause, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(R.drawable.play);
                }
            }
        });

        imageView.setOnTouchListener(new OnSwipeTouchListener(Pause3.this) {
            public void onSwipeTop() {
                if(isPaused || isCanceled) {

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
                                textView.setText("" + millisUntilFinished / 1000);
                                int progress = (int) (millisUntilFinished/100);
                                progressBar.setProgress(progress);
                                timeRemaining = millisUntilFinished;
                            }
                        }
                        public void onFinish(){
                            progressBar.setProgress(0);
                            Intent intent_in = new Intent(de.baumann.sieben.Pause3.this, MainActivity4.class);
                            startActivity(intent_in);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }.start();
                    String text = getResources().getString(R.string.sn_weiter);
                    ttsManager.initQueue(text);
                    fab.setImageResource(R.drawable.pause);
                    Snackbar.make(imageView, R.string.sn_weiter, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else {
                    String text = getResources().getString(R.string.sn_pause);
                    ttsManager.initQueue(text);
                    isPaused = true;
                    Snackbar.make(imageView, R.string.sn_pause, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(R.drawable.play);
                }
            }

            public void onSwipeRight() {
                Intent intent_in = new Intent(de.baumann.sieben.Pause3.this, Pause2.class);
                startActivity(intent_in);
                overridePendingTransition(0, 0);
                isCanceled = true;
                finish();
            }

            public void onSwipeLeft() {
                Intent intent_in = new Intent(de.baumann.sieben.Pause3.this, Pause4.class);
                startActivity(intent_in);
                overridePendingTransition(0, 0);
                isCanceled = true;
                finish();
            }

            public void onSwipeBottom() {
                String text = getResources().getString(R.string.sn_pause);
                ttsManager.initQueue(text);
                isPaused = true;
                Snackbar.make(imageView, R.string.sn_pause, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fab.setImageResource(R.drawable.play);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_license) {
            final SpannableString s = new SpannableString(Html.fromHtml(getString(R.string.about_text)));
            Linkify.addLinks(s, Linkify.WEB_URLS);

            final AlertDialog d = new AlertDialog.Builder(Pause3.this)
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

            final AlertDialog d = new AlertDialog.Builder(Pause3.this)
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

            final AlertDialog d = new AlertDialog.Builder(Pause3.this)
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
        isCanceled = true;
        finish();
    }
}
