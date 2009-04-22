/*
 * Copyright 2009 Sikirulai Braheem
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.bramosystems.gwt.player.core.client.skin;

import com.bramosystems.gwt.player.core.client.PlayerUtil;
import com.bramosystems.gwt.player.core.client.PlayException;
import com.bramosystems.gwt.player.core.client.MediaStateListenerAdapter;
import com.bramosystems.gwt.player.core.client.AbstractMediaPlayer;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * Custom player control widget.  Used by the {@link FlashVideoPlayer}, {@link FlashMP3Player}
 * and the {@link FlatVideoPlayer} classes.
 *
 * @author Sikirulai Braheem
 * @since 0.6
 */
public class FlatCustomControl extends Composite {

    private ImagePack imgPack = GWT.create(ImagePack.class);
    private String mediaDurationString;
    private PushButton play,  stop;
    private Timer playTimer;
    private CSSSeekBar seekbar;
    private Label timeLabel;
    private long mediaDuration;
    private AbstractMediaPlayer player;
    private PlayState playState;
    private VolumeControl vc;

    /**
     * Contructs FlatCustomControl object.
     *
     * @param player the player object to control
     */
    public FlatCustomControl(AbstractMediaPlayer player) {
        this.player = player;
        playState = PlayState.Stop;

        initWidget(getPlayerWidget());
        setSize("100%", "20px");
    }

    private Widget getPlayerWidget() {
        mediaDurationString = "";
        seekbar = new CSSSeekBar(5);
        seekbar.setPlayingStyle("background", "red");
        seekbar.setWidth("90%");
        seekbar.addSeekChangeListener(new SeekChangeListener() {

            public void onSeekChanged(double newValue) {
                player.setPlayPosition(player.getMediaDuration() * newValue);
            }
        });

        playTimer = new Timer() {

            @Override
            public void run() {
                updateSeekState();
            }
        };

        play = new PushButton(imgPack.play().createImage(), new ClickHandler() {

            public void onClick(ClickEvent event) {
                switch (playState) {
                    case Stop:
                    case Pause:
                        try { // play media...
                            player.playMedia();
                        } catch (PlayException ex) {
                            player.fireError(ex.getMessage());
                        }
                        break;
                    case Playing:
                        player.pauseMedia();
                        toPlayState(PlayState.Pause);
                }
            }
        });
        play.getUpDisabledFace().setImage(imgPack.playDisabled().createImage());
        play.setEnabled(false);

        stop = new PushButton(imgPack.stop().createImage(), new ClickHandler() {

            public void onClick(ClickEvent event) {
                player.stopMedia();
                toPlayState(PlayState.Stop);
            }
        });
        stop.getUpDisabledFace().setImage(imgPack.stopDisabled().createImage());
        stop.getUpHoveringFace().setImage(imgPack.stopHover().createImage());
        stop.setEnabled(false);

        vc = new VolumeControl(imgPack.spk().createImage(), 5);
        vc.setPopupStyle("background", "url(" + GWT.getModuleBaseURL() + "flat-bg.png) repeat");
        vc.addVolumeChangeListener(new VolumeChangeListener() {

            public void onVolumeChanged(double newValue) {
                player.setVolume(newValue);
            }
        });

        player.addMediaStateListener(new MediaStateListenerAdapter() {

            @Override
            public void onError(String description) {
                Window.alert(description);
                onDebug(description);
            }

            @Override
            public void onLoadingComplete() {
                mediaDuration = player.getMediaDuration();
                mediaDurationString = PlayerUtil.formatMediaTime(mediaDuration);
                seekbar.setLoadingProgress(1.0);
                vc.setVolume(player.getVolume());
                updateSeekState();
            }

            @Override
            public void onPlayFinished() {
                toPlayState(PlayState.Stop);
            }

            @Override
            public void onLoadingProgress(double progress) {
                mediaDuration = player.getMediaDuration();
                mediaDurationString = PlayerUtil.formatMediaTime(mediaDuration);
                seekbar.setLoadingProgress(progress);
                updateSeekState();
            }

            @Override
            public void onPlayStarted() {
                toPlayState(PlayState.Playing);
            }

            @Override
            public void onPlayerReady() {
                play.setEnabled(true);
                vc.setVolume(player.getVolume());
            }
        });

        // Time label..
        timeLabel = new Label("--:-- / --:--");
        timeLabel.setWordWrap(false);
        timeLabel.setHorizontalAlignment(Label.ALIGN_CENTER);

        // build the UI...
        DockPanel face = new DockPanel();
        face.setVerticalAlignment(DockPanel.ALIGN_MIDDLE);
        face.setSpacing(3);
        face.add(vc, DockPanel.WEST);
        face.add(play, DockPanel.WEST);
        face.add(stop, DockPanel.WEST);
        face.add(timeLabel, DockPanel.EAST);
        face.add(seekbar, DockPanel.CENTER);

        face.setCellWidth(seekbar, "100%");
        face.setCellHorizontalAlignment(seekbar, DockPanel.ALIGN_CENTER);

        DOM.setStyleAttribute(timeLabel.getElement(), "font", "bold 8pt Arial,Helvetica,sans-serif");
        DOM.setStyleAttribute(timeLabel.getElement(), "color", "white");
        DOM.setStyleAttribute(face.getElement(), "background",
                "url(" + GWT.getModuleBaseURL() + "flat-bg.png) repeat");
        return face;
    }

    private void setTime(long time) {
        timeLabel.setText(PlayerUtil.formatMediaTime(time) + " / " + mediaDurationString);
    }

    private void toPlayState(PlayState state) {
        switch (state) {
            case Playing:
                play.setEnabled(true);
                playTimer.scheduleRepeating(1000);
                stop.setEnabled(true);
                vc.setVolume(player.getVolume());

                play.getUpFace().setImage(imgPack.pause().createImage());
                play.getUpHoveringFace().setImage(imgPack.pauseHover().createImage());
                break;
            case Stop:
                stop.setEnabled(false);
                seekbar.setPlayingProgress(0);
                setTime(0);
                playTimer.cancel();
            case Pause:
                play.getUpFace().setImage(imgPack.play().createImage());
                play.getUpHoveringFace().setImage(imgPack.playHover().createImage());
                break;
        }
        playState = state;
    }

    @Override
    protected void onUnload() {
        playTimer.cancel();
    }

    private void updateSeekState() {
        double pos = player.getPlayPosition();
        setTime((long) pos);
        seekbar.setPlayingProgress(pos / (double) mediaDuration);
    }

    private enum PlayState {

        Playing, Pause, Stop;
    }

    public interface ImagePack extends ImageBundle {

        public AbstractImagePrototype pause();

        public AbstractImagePrototype pauseHover();

        public AbstractImagePrototype play();

        public AbstractImagePrototype playHover();

        public AbstractImagePrototype playDisabled();

        public AbstractImagePrototype stop();

        public AbstractImagePrototype stopDisabled();

        public AbstractImagePrototype stopHover();

        public AbstractImagePrototype spk();
    }
}
