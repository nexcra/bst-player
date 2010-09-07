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

package com.bramosystems.oss.player.youtube.client;

/**
 * Holds the parameters of the YouTube player
 *
 * @author Sikirulai Braheem <sbraheem at bramosystems dot com>
 * @since 1.1
 */
public class PlayerParameters {
    private int loadRelatedVideos = 1, autoplay, loop, enableJsApi, disableKeyboardControls,
            egm, showBorder, fullScreen, highDef, showSearch = 1, showInfo = 1,
            ivLoadPolicy = 1, closedCaptionPolicy, startTime;
    private String color1 = "", color2 = "", playerAPIId = "";

    public PlayerParameters() {
    }

    /**
     * Checks if the initial video will autoplay when loaded
     *
     * @return {@code true} if autoplay is active, {@code false} otherwise
     */
    public boolean isAutoplay() {
        return autoplay == 1;
    }

    /**
     * Sets whether or not the initial video will autoplay when the player loads.
     *
     * @param autoplay {@code true} to autoplay, {@code false} otherwise
     */
    public void setAutoplay(boolean autoplay) {
        this.autoplay = autoplay ? 1 : 0;
    }

//   User Preference ...
//    public boolean isClosedCaptionPolicy() {
//        return closedCaptionPolicy;
//    }

//    public void showClosedCaptions(boolean show) {
//        this.closedCaptionPolicy = show ? 1 : 0;
//    }

    /**
     * Returns the players primary border color.
     *
     * @return color in hexadecimal format
     */
    public String getPrimaryBorderColor() {
        return color1;
    }

    /**
     * Sets the players primary border color.
     *
     * @param color an RGB value in hexadecimal format
     */
    public void setPrimaryBorderColor(String color) {
        this.color1 = color;
    }

    /**
     * Returns the players secondary border color and control bar background color.
     *
     * @return color in hexadecimal format
     */
    public String getSecondaryBorderColor() {
        return color2;
    }

    /**
     * Sets the players video control bar background color and secondary border color.
     *
     * @param color an RGB value in hexadecimal format
     */
    public void setSecondaryBorderColor(String color) {
        this.color2 = color;
    }

    /**
     * Checks if keyboard controls are enabled
     *
     * @return {@code true} if enabled, {@code false} otherwise
     */
    public boolean isKeyboardControlsEnabled() {
        return disableKeyboardControls == 0;
    }

    /**
     * Enables or disables keyboard controls
     *
     * @param enable {@code true} to enable keyboard controls, {@code false} otherwise
     */
    public void setKeyboardControlsEnabled(boolean enable) {
        this.disableKeyboardControls = enable ? 0 : 1;
    }

    /**
     * Checks if the "Enhanced Genie Menu" is enabled.
     *
     * @return {@code true} is genie menu is enabled, {@code false} otherwise
     * @see #setEnhancedGenieMenuEnabled(boolean)
     */
    public boolean isEnhancedGenieMenuEnabled() {
        return egm == 1;
    }

    /**
     * Enables or disables the "Enhanced Genie Menu". If enabled, the genie menu (if present)
     * appears when the user's mouse enters the video display area, as opposed to only appearing
     * when the menu button is pressed
     *
     * @param enable {@code true} to enable genie menu, {@code false} otherwise
     */
    public void setEnhancedGenieMenuEnabled(boolean enable) {
        this.egm = enable ? 1 : 0;
    }

    /**
     * Checks if the Javascript API is enabled
     *
     * @return {@code true} if enabled, {@code false} otherwise
     */
    public boolean isJSApiEnabled() {
        return enableJsApi == 1;
    }

    /**
     * Enables or disables the player's Javascript API
     *
     * @param enable {@code true} to enable the Javascript API, {@code false} otherwise
     */
    public void setJSApiEnabled(boolean enable) {
        this.enableJsApi = enable ? 1 : 0;
    }

    /**
     * Checks if the fullscreen button is enabled
     *
     * @return {@code true} if enabled, {@code false} otherwise
     */
    public boolean isFullScreenEnabled() {
        return fullScreen == 1;
    }

    /**
     * Enables or disables the player's fullscreen button
     *
     * @param enable {@code true} to enable the fullscreen, {@code false} otherwise
     */
    public void setFullScreenEnabled(boolean enable) {
        this.fullScreen = enable ? 1 : 0;
    }

    /**
     * Checks if HD playback is enabled
     *
     * @return {@code true} if enabled, {@code false} otherwise.
     */
    public boolean isHDEnabled() {
        return highDef == 1;
    }

    /**
     * Enables or disables HD playback.
     *
     * <b>Note:</b> This method has no effect if an HD version of the current video
     * is not available.
     *
     * @param enable {@code true} to enable HD playback, {@code false} otherwise
     */
    public void setHDEnabled(boolean enable) {
        this.highDef = enable ? 1 : 0;
    }

    /**
     * Checks if video annotations are shown by default
     *
     * @return {@code true} if annotations are shown by default, {@code false} otherwise
     */
    public boolean isShowVideoAnnotations() {
        return ivLoadPolicy == 1;
    }

    /**
     * Enables or disables showing video annotations by default.
     *
     * @param show {@code true} to show annotations, {@code false} otherwise
     */
    public void showVideoAnnotations(boolean show) {
        this.ivLoadPolicy = show ? 1 : 3;
    }

    /**
     * Checks if player is set to load related videos
     *
     * @return {@code true} if related videos are loaded, {@code false} otherwise
     */
    public boolean isLoadRelatedVideos() {
        return loadRelatedVideos == 1;
    }

    /**
     * Sets whether the player should load related videos once playback of the
     * initial video starts.
     *
     * @param loadRelatedVideos {@code true} to load related videos, {@code false} otherwise
     */
    public void setLoadRelatedVideos(boolean loadRelatedVideos) {
        this.loadRelatedVideos = loadRelatedVideos ? 1 : 0;
    }

    /**
     * Checks if player is set to repeat playback
     *
     * @return {@code true} if playback is set to repeat, {@code false} otherwise
     */
    public boolean isLoopEnabled() {
        return loop == 1;
    }

    /**
     * Sets player to repeat video playback again and again.
     *
     * @param loop {@code true} to repeat playback, {@code false} otherwise
     */
    public void setLoopEnabled(boolean loop) {
        this.loop = loop ? 1 : 0;
    }

    /**
     * Checks if the showing of player's borders is enabled
     *
     * @return {@code true} if player's borders is set to show, {@code false} otherwise
     */
    public boolean isShowBorder() {
        return showBorder == 1;
    }

    /**
     * Enables or disables the player's borders
     *
     * @param show {@code true} to show the player's borders, {@code false} otherwise
     */
    public void showBorder(boolean show) {
        this.showBorder = show ? 1 : 0;
    }

    /**
     * Checks if the display of video information is enabled
     *
     * @return {@code true} if display of video information is enabled, {@code false} otherwise
     */
    public boolean isShowVideoInformation() {
        return showInfo == 1;
    }

    /**
     * Enables or disables the display of information like the video title and rating
     * before the video starts playing.
     *
     * @param show {@code true} if video information should show, {@code false} otherwise
     */
    public void showVideoInformation(boolean show) {
        this.showInfo = show ? 1 : 0;
    }

    /**
     * Returns the display status of the search box.
     *
     * @return <code>true</code> if the search box is enabled, <code>false</code> otherwise.
     */
    public boolean isShowSearchBox() {
        return showSearch == 1;
    }

    /**
     * Shows or hides the search box from displaying when the video is minimized.
     *
     * <p>Note that if {@linkplain #setLoadRelatedVideos(boolean)} is false, then the search box will
     * also be disabled.
     *
     * @param show <code>true</code> to show the search box, <code>false</code> otherwise.
     */
    public void showSearchBox(boolean show) {
        this.showSearch = show ? 1 : 0;
    }

    /**
     * Gets the time at which the player starts playback
     *
     * @return time (in seconds) at which the player starts playback
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Sets the time at which the player begins video playback.
     *
     * @param startTime time to begin playback (in seconds)
     */
    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the player identification value used internally by the YouTube player
     *
     * @return the player ID
     */
    public String getPlayerAPIId() {
        return playerAPIId;
    }

    /**
     * Sets the player identification value used internally by the YouTube player.
     *
     * @param playerAPIId player ID
     */
    public void setPlayerAPIId(String playerAPIId) {
        this.playerAPIId = playerAPIId;
    }
}