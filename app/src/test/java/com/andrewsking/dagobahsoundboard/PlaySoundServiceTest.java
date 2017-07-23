package com.andrewsking.dagobahsoundboard;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowMediaPlayer;
import org.robolectric.shadows.util.DataSource;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class PlaySoundServiceTest {

    private PlaySoundService service;

    @Before
    public void setUp() throws Exception {
        service = Robolectric.buildService(PlaySoundService.class).create().get();
    }

    @Test
    public void playingSound_shouldStartTheMediaPlayer() throws Exception {
        service.playSound(0);
        assertTrue(service.isPlaying());
    }
}