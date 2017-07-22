package com.andrewsking.dagobahsoundboard.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.andrewsking.dagobahsoundboard.R;
import com.andrewsking.dagobahsoundboard.Sound;

public class SoundRepository {
    private static SoundRepository soundRepository;

    private Integer currentSoundIndex;
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
    final private MutableLiveData<Sound[]> data = new MutableLiveData<Sound[]>();

    public static SoundRepository getInstance() {
        if (soundRepository == null) soundRepository = new SoundRepository();
        return soundRepository;
    }

    public LiveData<Sound[]> getSounds() {
        data.setValue(sounds);
        return data;
    }

    public void setSoundToPlaying(int index) {
        this.currentSoundIndex = index;
        sounds[this.currentSoundIndex].setPlaying(true);
        data.setValue(sounds);
    }
    public int getPlayingSoundId() { return sounds[this.currentSoundIndex].getSoundId(); }
    public void stopSound() {
        sounds[this.currentSoundIndex].setPlaying(false);
        this.currentSoundIndex = null;
        data.setValue(sounds);
    }
}
