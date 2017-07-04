package com.andrewsking.dagobahsoundboard;

import android.app.Fragment;
import android.content.Context;

import java.util.OptionalInt;

public class SoundStore {

    public interface OnChangeListener {
        void onChange();
    }

    private static SoundStore soundStore;

    private Context appContext;
    private OptionalInt currentSoundIndex;
    private Sound[] sounds = new Sound[] {
            new Sound(R.raw.ambience, "Ambience"),
            new Sound(R.raw.cantina, "Cantina"),
            new Sound(R.raw.kick_it, "Kick It"),
            new Sound(R.raw.noooo, "Noooo"),
            new Sound(R.raw.push_it, "Push It"),
            new Sound(R.raw.rejoice, "Rejoice"),
            new Sound(R.raw.roar, "Roar"),
            new Sound(R.raw.shame, "Shame"),
            new Sound(R.raw.victory, "Victory")
    };
    private PlaySoundFragment onChangeListener;

    private SoundStore(Context appContext) {
        this.appContext = appContext;
    }

    public static SoundStore getInstance(Context context) {
        if (soundStore == null) soundStore = new SoundStore(context.getApplicationContext());
        return soundStore;
    }

    public Sound[] getSounds() { return sounds; }

    public void setSoundToPlaying(int index) {
        this.currentSoundIndex = OptionalInt.of(index);
        sounds[this.currentSoundIndex.getAsInt()].setPlaying(true);
        onChangeListener.onChange();
    }
    public int getPlayingSoundId() { return sounds[this.currentSoundIndex.getAsInt()].getSoundId(); }
    public void stopSound() {
        sounds[this.currentSoundIndex.getAsInt()].setPlaying(false);
        this.currentSoundIndex = OptionalInt.empty();
        onChangeListener.onChange();
    }

    public void setOnChangeListener(PlaySoundFragment fragment) {
        onChangeListener = fragment;
    }

}
