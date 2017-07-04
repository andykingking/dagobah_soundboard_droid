package com.andrewsking.dagobahsoundboard;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class PlaySoundService extends Service implements MediaPlayer.OnCompletionListener {

    private static final int ONGOING_NOTIFICATION_ID = 1;
    private SoundStore soundStore;

    public interface OnCompletionListener {
        void onCompletion();
    }

    public class ServiceBinder extends Binder {
        PlaySoundService getService() {
            return PlaySoundService.this;
        }
    }

    private MediaPlayer mediaPlayer;
    private IBinder binder = new ServiceBinder();
    private OnCompletionListener onCompletionListenerContext;

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        soundStore = SoundStore.getInstance(this);
    }

    private void cleanupPlayer() {
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        stopForeground(true);
        soundStore.stopSound();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stop();
    }

    public void playSound(int soundIndex) {
        if (mediaPlayer != null) cleanupPlayer();
        soundStore.setSoundToPlaying(soundIndex);
        mediaPlayer = MediaPlayer.create(this, soundStore.getPlayingSoundId());
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();
        Notification notification = buildPlayingNotification();
        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    private Notification buildPlayingNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        return new Notification.Builder(this)
                .setContentTitle(getText(R.string.notification_title))
                .setSmallIcon(R.drawable.yoda_icon)
                .setContentIntent(pendingIntent)
                .build();
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
        cleanupPlayer();
        onCompletionListenerContext.onCompletion();
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) mediaPlayer.release();
    }
}
