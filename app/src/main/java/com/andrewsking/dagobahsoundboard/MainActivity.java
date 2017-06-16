package com.andrewsking.dagobahsoundboard;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements PlaySoundFragment.OnMediaCompletionListener {

    private PlaySoundFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playAmbience(View view) {
        playSound(R.raw.ambience);
    }

    public void playCantina(View view) {
        playSound(R.raw.cantina);
    }

    public void playKickIt(View view) {
        playSound(R.raw.kick_it);
    }

    public void playNoooo(View view) {
        playSound(R.raw.noooo);
    }

    public void playPushIt(View view) {
        playSound(R.raw.push_it);
    }

    public void playRoar(View view) {
        playSound(R.raw.roar);
    }

    public void playShame(View view) {
        playSound(R.raw.shame);
    }

    public void playVictory(View view) {
        playSound(R.raw.victory);
    }

    public void playSound(int soundId) {
        if (fragment != null) {
            removeFragment();
        }
        fragment = PlaySoundFragment.buildFragment(soundId);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onMediaCompletion() {
        removeFragment();
    }

    public void removeFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .detach(fragment)
                .commit();
        fragment = null;
    }
}
