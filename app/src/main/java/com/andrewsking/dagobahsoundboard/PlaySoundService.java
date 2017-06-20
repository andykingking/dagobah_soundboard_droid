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
        stopForeground(true);
        onCompletionListenerContext.onCompletion();
    }

    public void playSound(int soundId) {
        if (mediaPlayer != null) cleanupPlayer();
        mediaPlayer = MediaPlayer.create(this, soundId);
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
        onCompletionListenerContext.onCompletion();
        cleanupPlayer();
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) mediaPlayer.release();
    }
}
