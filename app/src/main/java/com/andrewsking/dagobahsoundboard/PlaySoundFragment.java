package com.andrewsking.dagobahsoundboard;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;


public class PlaySoundFragment extends Fragment implements SoundStore.OnChangeListener {

    // @TODO: Move functional stuff to Activity, leaving only UI elements

    private static final Handler progressHandler = new Handler();
    private static final int PROGRESS_UPDATE_INTERVAL = 50;
    private boolean serviceBound = false;
    private PlaySoundService mediaPlayerService;
    private ListView soundListView;
    private ProgressBar progressBar;
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
            if (mediaPlayerService.isPlaying()) preparePlayDetails();
            serviceBound = true;
        }
    };
    private Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayerService != null) {
                ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", progressBar.getProgress(), mediaPlayerService.getCurrentPosition());
                animator.setDuration(PROGRESS_UPDATE_INTERVAL);
                animator.setInterpolator(new LinearInterpolator());
                animator.start();
                progressHandler.postDelayed(this, PROGRESS_UPDATE_INTERVAL);
            }
        }
    };
    private SoundArrayAdapter adapter;

    private void playSound(int soundIndex) {
        mediaPlayerService.playSound(soundIndex);
        preparePlayDetails();
    }

    private void preparePlayDetails() {
        progressBar.setMax(mediaPlayerService.getDuration());
        setPlayDetailsTo(View.VISIBLE);
        progressHandler.post(updateProgress);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity parentActivity = getActivity();
        SoundStore.getInstance(parentActivity).setOnChangeListener(this);
        adapter = new SoundArrayAdapter(parentActivity);
        soundListView = (ListView) parentActivity.findViewById(R.id.soundListView);
        progressBar = (ProgressBar) parentActivity.findViewById(R.id.progressBar);
        progressBar = (ProgressBar) parentActivity.findViewById(R.id.progressBar);
        soundListView.setAdapter(adapter);
        soundListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                playSound(position);
            }
        });
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
        setPlayDetailsTo(View.INVISIBLE);
        progressHandler.removeCallbacks(updateProgress);
    }

    private void setPlayDetailsTo(int visibility) {
        View playingDetails = getView().findViewById(R.id.playDetailsLayout);
        playingDetails.setVisibility(visibility);
    }

    @Override
    public void onChange() {
        adapter.notifyDataSetChanged();
    }
}
