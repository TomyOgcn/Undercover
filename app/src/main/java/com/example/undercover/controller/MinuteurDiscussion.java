package com.example.undercover.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.undercover.R;

public class MinuteurDiscussion extends AppCompatActivity{

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";

    private static final long START_TIME_IN_MILLIS = 600000;

    private TextView _text_view_countDown;
    private Button _button_start_pause;
    private Button _button_reset;
    private Button _fin_chrono;

    private CountDownTimer _countDownTimer;

    private boolean _timerRunning;

    private long _timeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minuteur_discussion);

        _text_view_countDown = (TextView) findViewById(R.id.activity_minuteur_countdown);
        _button_start_pause = (Button) findViewById(R.id.activity_minuteur_start_pause);
        _button_reset = (Button) findViewById(R.id.activity_minuteur_reset);
        _fin_chrono = (Button) findViewById(R.id.activity_minuteur_fin_chrono);

        _button_start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_timerRunning){
                    pauseTimer();
                }
                else{
                    startTimer();
                }
            }
        });
        _button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();
            }
        });

        _fin_chrono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(BUNDLE_EXTRA_SCORE, 10);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        updateCountDownText();
    }

    private void resetTimer() {
        _timeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        _button_reset.setVisibility(View.INVISIBLE);
        _button_start_pause.setVisibility(View.VISIBLE);
    }

    private void startTimer() {
        _countDownTimer = new CountDownTimer(_timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                _timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                _timerRunning = false;
                _button_start_pause.setText("Start");
                _button_start_pause.setVisibility(View.INVISIBLE);
                _button_reset.setVisibility(View.VISIBLE);
            }
        }.start();

        _timerRunning = true;
        _button_start_pause.setText("pause");
        _button_reset.setVisibility(View.INVISIBLE);
    }

    private void updateCountDownText() {
        int minutes = (int) (_timeLeftInMillis / 1000) / 60;
        int seconds = (int) (_timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);

        _text_view_countDown.setText(timeLeftFormatted);
    }

    private void pauseTimer() {
        _countDownTimer.cancel();
        _timerRunning = false;
        _button_start_pause.setText("Start");
        _button_reset.setVisibility(View.VISIBLE);
    }

}