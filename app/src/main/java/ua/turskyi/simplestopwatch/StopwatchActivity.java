package ua.turskyi.simplestopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES;

public class StopwatchActivity extends AppCompatActivity {
    private int seconds = 0;
    private int milliseconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            milliseconds = savedInstanceState.getInt("milliseconds");
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runSeconds();
//        runMilliseconds();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
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

    //Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {
        running = true;
    }

    //Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        running = false;
    }

    //Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        milliseconds = 0;
    }

    /**
     * Sets the number of seconds on the timer.
     * */
    private void runSeconds() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d" +
                                "", hours, minutes, secs
                );
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }


    //the following method does not work correctly for not resolved reason,
    // and implementation of it postponed for future
//    /**
//     * Sets the number of milliseconds on the timer.
//     * */
//    private void runMilliseconds() {
//        final TextView timeView = findViewById(R.id.time_view);
//        final Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                int hours = (milliseconds / 100)/3600;
//                int minutes = ((milliseconds / 100)% 3600) / 60;
//                int secs = (milliseconds / 100) % 60;
//                int millis = (milliseconds)%100;
//                String time = String.format(Locale.getDefault(),
//                        "%d:%02d:%02d" +
//                                ":%02d" +
//                                "", hours, minutes, secs
//                        , millis
//                );
//                timeView.setText(time);
//                if (running) {
//                    milliseconds++;
//                }
//                handler.postDelayed(this, 0);
//            }
//        });
//    }
}
