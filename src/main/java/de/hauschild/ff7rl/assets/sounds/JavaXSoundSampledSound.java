/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.assets.sounds;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;

/**
 * @author Klaus Hauschild
 */
public class JavaXSoundSampledSound implements Sound {

    private final Clip clip;

    public JavaXSoundSampledSound(final InputStream inputStream) throws Exception {
        final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        final AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioInputStream.getFormat()
                .getSampleRate(), 16, audioInputStream.getFormat().getChannels(), audioInputStream.getFormat().getChannels() * 2,
                audioInputStream.getFormat().getSampleRate(), false);
        clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(audioFormat, audioInputStream));
    }

    @Override
    public boolean isPlaying() {
        return clip.isActive();
    }

    @Override
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Override
    public void start() {
        if (isPlaying()) {
            return;
        }
        clip.start();
    }

    @Override
    public void stop() {
        pause();
        clip.setFramePosition(0);
    }

    @Override
    public void pause() {
        if (!isPlaying()) {
            return;
        }
        clip.stop();
    }

}
