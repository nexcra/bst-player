/*
 * Copyright 2011 Sikirulai Braheem <sbraheem at bramosystems.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bramosystems.oss.player.core.client.impl;

import com.bramosystems.oss.player.core.client.*;
import com.bramosystems.oss.player.core.client.spi.ConfigurationContext;
import com.bramosystems.oss.player.core.client.spi.PlayerElement;
import com.bramosystems.oss.player.core.client.spi.PlayerProvider;
import com.bramosystems.oss.player.core.client.spi.PlayerProviderFactory;
import com.bramosystems.oss.player.util.client.MimeType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author Sikirulai Braheem <sbraheem at bramosystems.com>
 */
@PlayerProvider("api")
public class APIWidgetProvider implements PlayerProviderFactory {
    private String wmpFFMimeType = "application/x-ms-wmp", wmpAppMimeType = "application/x-mplayer2";

    @Override
    public void init(ConfigurationContext context) {
    }

    @Override
    public AbstractMediaPlayer getPlayer(String playerName, String mediaURL, boolean autoplay, String height, String width)
            throws PluginNotFoundException, PluginVersionException {
        return PlayerUtil.getPlayer(Plugin.valueOf(playerName), mediaURL, autoplay, height, width);
    }

    @Override
    public AbstractMediaPlayer getPlayer(String playerName, String mediaURL, boolean autoplay) throws PluginNotFoundException, PluginVersionException {
        return PlayerUtil.getPlayer(Plugin.valueOf(playerName), mediaURL, autoplay);
    }

    @Override
    public PlayerElement getPlayerElement(String playerName, String playerId, String mediaURL, boolean autoplay, HashMap<String, String> params) {
        if (playerName.equals("swf")) {
            return getSWFElement(playerId, mediaURL, params);
        } else if (playerName.equals(Plugin.WinMediaPlayer.name())) {
            return getWMPElement(playerId, mediaURL, autoplay, params);
        } else {
            throw new IllegalArgumentException("Unknown player - '" + playerName + "'");
        }
    }

    protected PlayerElement getSWFElement(String playerId, String swfURL, HashMap<String, String> params) {
        PlayerElement e = new PlayerElement(PlayerElement.Type.EmbedElement, playerId, "application/x-shockwave-flash");
        e.addParam("src", swfURL);
        e.addParam("name", playerId);

        Iterator<String> keys = params.keySet().iterator();
        while (keys.hasNext()) {
            String name = keys.next();
            e.addParam(name, params.get(name));
        }
        return e;
    }

    protected PlayerElement getNativeElement(String playerId, String mediaURL, boolean autoplay) {
        PlayerElement e = new PlayerElement(PlayerElement.Type.VideoElement, playerId, "");
        e.addParam("src", mediaURL);
        if (autoplay) {
            e.addParam("autoplay", Boolean.toString(autoplay));
        }
        e.addParam("controls", "true");
        return e;
    }

   protected PlayerElement getWMPElement(String playerId, String mediaURL, boolean autoplay,
            HashMap<String, String> params) {
        PlayerElement e = new PlayerElement(PlayerElement.Type.EmbedElement, playerId, hasWMPFFPlugin() ? wmpFFMimeType : wmpAppMimeType);
        e.addParam("autostart", hasWMPFFPlugin() ? Boolean.toString(autoplay) : (autoplay ? "1" : "0"));
        //       e.addParam(hasWMPFFPlugin() ? "URL" : "SRC", mediaURL);

        Iterator<String> keys = params.keySet().iterator();
        while (keys.hasNext()) {
            String name = keys.next();
            e.addParam(name, params.get(name));
        }
        return e;
    }
   
    private boolean hasWMPFFPlugin() {
        // check for firefox plugin mime type...
        MimeType mt = MimeType.getMimeType(wmpFFMimeType);
        if (mt != null) {
            return true;
        } else {
            return false;
        }
    }
 
    @Override
    public PluginVersion getDetectedPluginVersion(String playerName) throws PluginNotFoundException {
        if (playerName.equals("swf")) {
            return PlayerUtil.getFlashPlayerVersion();
        } else if (playerName.equals(Plugin.Auto.name()) || playerName.equals(Plugin.MatrixSupport.name())
                || playerName.equals(Plugin.PlaylistSupport.name())) {
            return PluginVersion.get(0, 0, 0);
        } else {
            throw new IllegalArgumentException("Unknown player - '" + playerName + "'");
        }
    }

    @Override
    public Set<String> getPermittedMimeTypes(String playerName, PluginVersion version) {
        return new HashSet<String>();
    }

    @Override
    public Set<String> getPermittedMediaProtocols(String playerName, PluginVersion version) {
        return new HashSet<String>();
    }

    @Override
    public PluginInfo getDetectedPluginInfo(String playerName) throws PluginNotFoundException {
        if (playerName.equals("swf")) {
            return new PluginInfo(Plugin.FlashPlayer, PlayerUtil.getFlashPlayerVersion(), PluginInfo.PlayerPluginWrapperType.Native);
        } else if (playerName.equals(Plugin.Auto.name())) {
            return new PluginInfo(Plugin.Auto, PluginVersion.get(0, 0, 0), PluginInfo.PlayerPluginWrapperType.Native);
        } else if (playerName.equals(Plugin.MatrixSupport.name())) {
            return new PluginInfo(Plugin.MatrixSupport, PluginVersion.get(0, 0, 0), PluginInfo.PlayerPluginWrapperType.Native);
        } else if (playerName.equals(Plugin.PlaylistSupport.name())) {
            return new PluginInfo(Plugin.PlaylistSupport, PluginVersion.get(0, 0, 0), PluginInfo.PlayerPluginWrapperType.Native);
        } else {
            throw new IllegalArgumentException("Unknown player - '" + playerName + "'");
        }
    }
}
