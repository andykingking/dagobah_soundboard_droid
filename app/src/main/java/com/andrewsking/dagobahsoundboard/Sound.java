package com.andrewsking.dagobahsoundboard;

public class Sound {

    private final int soundId;
    private final String displayName;
    private boolean playing = false;

    public Sound(int id, String name) {
        soundId = id;
        displayName = name;
    }

    public String getDisplayName() { return displayName; }
    public int getSoundId() { return soundId; }
    public void setPlaying(boolean playing) { this.playing = playing; }
    public boolean isPlaying() { return playing; }
    public String toString() { return displayName; }
}
