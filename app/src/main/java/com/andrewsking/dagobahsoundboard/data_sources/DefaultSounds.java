package com.andrewsking.dagobahsoundboard.data_sources;

import com.andrewsking.dagobahsoundboard.R;
import com.andrewsking.dagobahsoundboard.Sound;

public class DefaultSounds {
    private static Sound[] defaultSounds = new Sound[] {
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

    public static Sound[] getSounds() {
        return defaultSounds;
    }
}
