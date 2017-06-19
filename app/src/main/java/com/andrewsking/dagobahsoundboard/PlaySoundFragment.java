package com.andrewsking.dagobahsoundboard;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;


public class PlaySoundFragment extends Fragment {

    private Handler progressHandler = new Handler();
    private DateFormat formatter = new SimpleDateFormat("mm:ss");
    private static int PROGRESS_UPDATE_INTERVAL = 100;
//    private TextView durationView;
//    private TextView progressView;

    private boolean serviceBound = false;
    private PlaySoundService mediaPlayerService;
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlaySoundService.ServiceBinder binder = (PlaySoundService.ServiceBinder) service;
            mediaPlayerService = binder.getService();
            mediaPlayerService.setOnCompletionListener(getActivity());
            serviceBound = true;
        }
    };
    private Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayerService != null) {
                setDateForId(mediaPlayerService.getCurrentPosition(), R.id.progressTextView);
                progressHandler.postDelayed(this, PROGRESS_UPDATE_INTERVAL);
            }
        }
    };

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

    private void playSound(int soundId) {
        mediaPlayerService.playSound(soundId);
        setDateForId(mediaPlayerService.getDuration(), R.id.durationTextView);
        View playingDetails = getView().findViewById(R.id.linearLayout);
        playingDetails.setVisibility(View.VISIBLE);
        progressHandler.postDelayed(updateProgress, PROGRESS_UPDATE_INTERVAL);
    }

    public void stop(View view) {
        mediaPlayerService.stop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_play_sound, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        FragmentActivity parentActivity = getActivity();
        Intent intent = new Intent(parentActivity, PlaySoundService.class);
        parentActivity.startService(intent);
        parentActivity.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onStop() {
        progressHandler.removeCallbacks(updateProgress);
        if (serviceBound) {
            getActivity().unbindService(serviceConnection);
            serviceBound = false;
        }
        super.onStop();
    }

    public void onCompletion() {
        View playingDetails = getView().findViewById(R.id.linearLayout);
        playingDetails.setVisibility(View.INVISIBLE);
        setDateForId(0, R.id.durationTextView);
        setDateForId(0, R.id.progressTextView);
        progressHandler.removeCallbacks(updateProgress);
    }

    private void setDateForId(int milliseconds, int viewId) {
        TextView textView = (TextView) getView().findViewById(viewId);
        Date date = new Date(milliseconds);
        textView.setText(formatter.format(date));
    }
}
