package com.example.undercover.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.undercover.R;

public class MainActivity extends AppCompatActivity {

    private TextView mGreetingText;
    private Button mPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGreetingText = (TextView) findViewById(R.id.activity_main_greeting_txt);
        mPlayButton = (Button) findViewById(R.id.activity_main_play_btn);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The user just clicked
                Intent choixNbJoueurs = new Intent(MainActivity.this, ChoixNbJoueurs.class);
                startActivity(choixNbJoueurs);
            }
        });

    }

}