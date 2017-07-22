package com.andrewsking.dagobahsoundboard.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.andrewsking.dagobahsoundboard.Sound;
import com.andrewsking.dagobahsoundboard.data_sources.DefaultSounds;

public class SoundRepository {
    private static SoundRepository soundRepository;

    private Integer currentSoundIndex;
    private Sound[] sounds;
    final private MutableLiveData<Sound[]> data = new MutableLiveData<Sound[]>();

    public static SoundRepository getInstance() {
        if (soundRepository == null) soundRepository = new SoundRepository();
        soundRepository.initialize();
        return soundRepository;
    }

    private void initialize() {
        this.sounds = DefaultSounds.getSounds();
        data.setValue(sounds);
    }

    public LiveData<Sound[]> getSounds() {
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
