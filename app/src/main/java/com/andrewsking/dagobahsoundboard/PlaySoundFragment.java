package com.andrewsking.dagobahsoundboard;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;


public class PlaySoundFragment extends Fragment implements MediaPlayer.OnCompletionListener  {

    public interface OnMediaCompletionListener {
        void onMediaCompletion();
    }

    private static String SOUND_ID = "com.andrewsking.dagobahsoundboard.SOUND_ID";

    private MediaPlayer mediaPlayer;
    private int soundId;
    private Handler progressHandler = new Handler();
    private DateFormat formatter = new SimpleDateFormat("mm:ss");
    private static int PROGRESS_UPDATE_INTERVAL = 100;
    private Runnable updateProgress = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer != null) {
                setDateForId(mediaPlayer.getCurrentPosition(), R.id.progressTextView);
                progressHandler.postDelayed(this, PROGRESS_UPDATE_INTERVAL);
            }
        }
    };
    private OnMediaCompletionListener callback;

    public static PlaySoundFragment buildFragment(int soundId) {
        Bundle bundle = new Bundle();
        bundle.putInt(SOUND_ID, soundId);
        PlaySoundFragment fragment = new PlaySoundFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        callback = (OnMediaCompletionListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        soundId = this.getArguments().getInt(SOUND_ID);
        return inflater.inflate(R.layout.fragment_play_sound, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mediaPlayer = MediaPlayer.create(getContext(), soundId);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();
        setDateForId(mediaPlayer.getDuration(), R.id.durationTextView);
        progressHandler.postDelayed(updateProgress, PROGRESS_UPDATE_INTERVAL);
    }

    @Override
    public void onStop() {
        progressHandler.removeCallbacks(updateProgress);
        mediaPlayer.release();
        mediaPlayer = null;
        super.onStop();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        callback.onMediaCompletion();
    }

    private void setDateForId(int milliseconds, int viewId) {
        TextView textView = (TextView) getView().findViewById(viewId);
        Date date = new Date(milliseconds);
        textView.setText(formatter.format(date));
    }
}
