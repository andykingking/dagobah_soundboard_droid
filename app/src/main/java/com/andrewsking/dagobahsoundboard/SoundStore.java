package com.andrewsking.dagobahsoundboard;

import android.content.Context;

public class SoundStore {

    private static SoundStore soundStore;

    private Context appContext;
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

    private SoundStore(Context appContext) {
        this.appContext = appContext;
    }

    public static SoundStore getInstance(Context context) {
        if (soundStore == null) soundStore = new SoundStore(context.getApplicationContext());
        return soundStore;
    }

    public Sound[] getSounds() { return sounds; }

}
