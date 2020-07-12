package ua.turskyi.simplestopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public class StopwatchActivity extends AppCompatActivity {
    private int milliseconds = 0;

    /* Is the stopwatch running? */
    private boolean running;
    private boolean wasRunning;
    private TextView timeView;
    private Handler uiHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        timeView = findViewById(R.id.time_view);
        if (savedInstanceState != null) {
            milliseconds = savedInstanceState.getInt("milliseconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runMilliseconds();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", milliseconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    /* Start the stopwatch running when the Start button is clicked. */
    public void onClickStart(View view) {
        running = true;
    }

    /* Stop the stopwatch running when the Stop button is clicked. */
    public void onClickStop(View view) { running = false; }

    /* Reset the stopwatch when the Reset button is clicked. */
    public void onClickReset(View view) {
        running = false;
        milliseconds = 0;
    }

    /**
     * Sets the number of milliseconds on the timer.
     */
    private void runMilliseconds() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (running) {
                    milliseconds++;
                    showInfo();
                }
            }
        }, 0, 10);
    }

    private void showInfo() {
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                int hours = (milliseconds / 100) / 3600;
                int minutes = ((milliseconds / 100) % 3600) / 60;
                int secs = (milliseconds / 100) % 60;
                int millis = (milliseconds) % 100;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d" +
                                ":%02d" +
                                "", hours, minutes, secs, millis
                );
                timeView.setText(time);
            }
        });
    }
}
