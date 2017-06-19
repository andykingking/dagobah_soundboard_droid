package com.andrewsking.dagobahsoundboard;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements PlaySoundService.OnCompletionListener {

    private PlaySoundFragment fragment;

    public void playAmbience(View view) {
        fragment.playAmbience(view);
    }

    public void playCantina(View view) {
        fragment.playCantina(view);
    }

    public void playKickIt(View view) {
        fragment.playKickIt(view);
    }

    public void playNoooo(View view) {
        fragment.playNoooo(view);
    }

    public void playPushIt(View view) {
        fragment.playPushIt(view);
    }

    public void playRoar(View view) {
        fragment.playRoar(view);
    }

    public void playShame(View view) {
        fragment.playShame(view);
    }

    public void playVictory(View view) {
        fragment.playVictory(view);
    }

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
