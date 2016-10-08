/*
 * Copyright (C) Klaus Hauschild - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Klaus Hauschild <klaus.hauschild.1984@gmail.com>, 2015
 */
package de.hauschild.ff7rl.state.soundtest;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.googlecode.lanterna.screen.Screen;

import de.hauschild.ff7rl.Threads;
import de.hauschild.ff7rl.assets.sounds.Sound;
import de.hauschild.ff7rl.assets.sounds.Sounds;
import de.hauschild.ff7rl.context.Context;
import de.hauschild.ff7rl.input.Input;
import de.hauschild.ff7rl.state.AbstractState;
import de.hauschild.ff7rl.state.StateHandler;
import de.hauschild.ff7rl.state.StateType;
import de.hauschild.ff7rl.ui.ScreenBorder;
import de.hauschild.ff7rl.ui.ScreenMenu;
import de.hauschild.ff7rl.ui.ScreenMenu.Entry;

/**
 * @author Klaus Hauschild
 */
public class SoundTestState extends AbstractState {

    private final Map<Integer, ScreenMenu<Entry>> cdMenus;
    private int                                   cd = 1;
    private Sound                                 currentSound;
    private String                                currentSoundName;
    private boolean                               autoPlay;

    public SoundTestState(final Context context) {
        super(StateType.SOUND_TEST, context);
        cdMenus = Arrays.asList(1, 2, 3, 4).stream()
                .collect(Collectors.toMap(cd -> cd,
                        cd -> new ScreenMenu<>(
                                Sounds.getAllSounds().stream().filter(sound -> sound.startsWith(Integer.toString(cd)))
                                        .map(sound -> new Entry(sound.replace(".mp3", ""))).collect(Collectors.toList()),
                                15, 30, true)));
    }

    @Override
    public void display(final Screen screen) {
        new ScreenBorder(82, 4).display(screen, 1, 17);
        screen.newTextGraphics().putString(19, 3,
                String.format("[LEFT] CD %s [RIGHT] | [UP] Track [DOWN] | [ACCEPT] play/stop | [MENU] auto play", cd));
        getCdMenu().display(screen);
    }

    @Override
    public void input(final Input input, final StateHandler stateHandler) {
        switch (input) {
            case RIGHT:
                cd++;
                if (cd == 5) {
                    cd = 1;
                }
                break;
            case LEFT:
                cd--;
                if (cd == 0) {
                    cd = 4;
                }
                break;
            case UP:
                getCdMenu().previous();
                break;
            case DOWN:
                getCdMenu().next();
                break;
            case ACCEPT:
                if (autoPlay) {
                    break;
                }
                String nextSoundName = String.format("%s.mp3", getCdMenu().select().getLabel());
                if (nextSoundName.equals(currentSoundName)) {
                    currentSound.pause();
                    currentSound = null;
                    currentSoundName = null;
                    break;
                }
                if (currentSound != null) {
                    currentSound.stop();
                }
                currentSoundName = nextSoundName;
                currentSound = Sounds.getSound(currentSoundName);
                currentSound.start();
                break;
            case MENU:
                autoPlay = !autoPlay;
                if (autoPlay) {
                    if (currentSound != null) {
                        currentSound.stop();
                        currentSound = null;
                        currentSoundName = null;
                    }
                    new Thread(() -> {
                        while (autoPlay) {
                            Sounds.getAllSounds().forEach(soundName -> {
                                if (!autoPlay) {
                                    return;
                                }
                                Sound sound = Sounds.getSound(soundName);
                                sound.start();
                                Threads.sleep(1000 * 10);
                                while (autoPlay && sound.isPlaying()) {
                                    Threads.sleep(1000);
                                }
                                sound.stop();
                            });
                        }
                    }).start();
                }
                break;
            case ABORT:
                stateHandler.nextState(StateType.INTRO);
                break;
        }
    }

    @Override
    public void leave() {
        super.leave();
        if (currentSound != null) {
            currentSound.stop();
        }
    }

    private ScreenMenu<Entry> getCdMenu() {
        return cdMenus.get(cd);
    }

}
