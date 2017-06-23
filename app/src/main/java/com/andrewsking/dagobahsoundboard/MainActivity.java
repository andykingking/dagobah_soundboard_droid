package com.andrewsking.dagobahsoundboard;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements PlaySoundService.OnCompletionListener {

    private PlaySoundFragment fragment;

    public void stop(View view) {
        fragment.stop(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment = (PlaySoundFragment) getSupportFragmentManager().findFragmentById(R.id.playSoundFragment);
    }

    @Override
    public void onCompletion() {
        fragment.onCompletion();
    }

}
