package com.andrewsking.dagobahsoundboard.view_models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.andrewsking.dagobahsoundboard.Sound;
import com.andrewsking.dagobahsoundboard.repositories.SoundRepository;


public final class SoundViewModel extends ViewModel {
    private final LiveData<Sound[]> sounds;
    private SoundRepository soundRepository;

    public SoundViewModel() {
        soundRepository = SoundRepository.getInstance();
        this.sounds = soundRepository.getSounds();
    }

    public final LiveData<Sound[]> getSounds() {
        return sounds;
    }

    public void onCleared() {
        this.soundRepository = null;
    }
}
