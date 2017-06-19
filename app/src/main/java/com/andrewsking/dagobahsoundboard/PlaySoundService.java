package com.andrewsking.dagobahsoundboard;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.view.View;

public class PlaySoundService extends Service implements MediaPlayer.OnCompletionListener {

    public static String SOUND_ID = "com.andrewsking.dagobahsoundboard.SOUND_ID";

    public interface OnCompletionListener {
        void onCompletion();
    }

    public class ServiceBinder extends Binder {
        PlaySoundService getService() {
            return PlaySoundService.this;
        }
    }

    private MediaPlayer mediaPlayer;
    private int soundId;
    private IBinder binder = new ServiceBinder();
    private OnCompletionListener onCompletionListenerContext;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void cleanupPlayer() {
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        onCompletionListenerContext.onCompletion();
    }

    public void playSound(int soundId) {
        if (mediaPlayer != null) cleanupPlayer();
        mediaPlayer = MediaPlayer.create(this, soundId);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();
    }

    public void setOnCompletionListener(Context context) {
        onCompletionListenerContext = (OnCompletionListener) context;
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void stop() {
        onCompletionListenerContext.onCompletion();
        cleanupPlayer();
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) mediaPlayer.release();
    }
}
