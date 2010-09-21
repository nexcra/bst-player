/*
 *  Copyright 2010 Sikiru.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package com.bramosystems.oss.player.showcase.client;

import com.bramosystems.oss.player.common.client.BrowserInfo;
import com.bramosystems.oss.player.core.client.AbstractMediaPlayer;
import com.bramosystems.oss.player.core.client.LoadException;
import com.bramosystems.oss.player.core.client.PlayerUtil;
import com.bramosystems.oss.player.core.client.PlaylistSupport;
import com.bramosystems.oss.player.core.client.Plugin;
import com.bramosystems.oss.player.core.client.PluginNotFoundException;
import com.bramosystems.oss.player.core.client.PluginVersionException;
import com.bramosystems.oss.player.core.client.ui.NativePlayer;
import com.bramosystems.oss.player.resources.sources.ResourceBundle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.resources.client.ExternalTextResource;
import com.google.gwt.resources.client.ResourceCallback;
import com.google.gwt.resources.client.ResourceException;
import com.google.gwt.resources.client.TextResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import java.util.List;

/**
 *
 * @author Sikiru
 */
public class PlayerPane extends Composite implements ValueChangeHandler<String>,
        PlayerOptions.OptionsChangeHandler {

    private AbstractMediaPlayer player;
    private PlaylistPane playlist;

    public PlayerPane() {
        initWidget(uiBinder.createAndBindUi(this));
        History.addValueChangeHandler(this);
    }

    public void setPlaylist(PlaylistPane playlist) {
        this.playlist = playlist;
    }

    private void init(PlayOptions options) {
        try {
            List<List<String>> urls = playlist.getEntries();
            player = PlayerUtil.getPlayer(options.getPlugin(), urls.get(0).get(0), false,
                    "250px", "100%");

            if (urls.size() > 1) {
                for (int i = 1; i < urls.size(); i++) {
                    switch (options.getPlugin()) {
                        case Native:
                            ((NativePlayer) player).addToPlaylist(urls.get(i).toArray(new String[0]));
                            break;
                        default:
                            ((PlaylistSupport) player).addToPlaylist(urls.get(i).get(0));
                    }
                }
            }

            panel.setWidget(player);
            title.setText(options.getTitle());
        } catch (LoadException ex) {
        } catch (PluginVersionException ex) {
            panel.setWidget(PlayerUtil.getMissingPluginNotice(Plugin.QuickTimePlayer,
                    ex.getRequiredVersion()));
        } catch (PluginNotFoundException ex) {
            panel.setWidget(PlayerUtil.getMissingPluginNotice(Plugin.QuickTimePlayer));
        }
    }

    private void init(ExternalTextResource txtSrc, String txt) {
        final HTML src = new HTML();
        try {
            txtSrc.getText(new ResourceCallback<TextResource>() {

                @Override
                public void onError(ResourceException e) {
                    src.setHTML("<div>Failed to load code sample!</div>");
                }

                @Override
                public void onSuccess(TextResource resource) {
                    src.setHTML(resource.getText());
                }
            });
        } catch (ResourceException ex) {
            src.setHTML("<div>Failed to load code sample!</div>");
        }
        panel.setWidget(src);
        title.setText(txt);
    }

    @Override
    public void onValueChange(ValueChangeEvent<String> event) {
        PlayOptions options = PlayOptions.homeIntro;
        try {
            options = PlayOptions.valueOf(event.getValue());
        } catch (Exception e) {
        }

        switch (options) {
            case homeDocs:
                init(ResourceBundle.bundle.homeDocs(), options.getTitle());
                break;
            case homeIntro:
                init(ResourceBundle.bundle.home(), options.getTitle());
                break;
            case homePlugins:
                panel.setWidget(new BrowserInfo(BrowserInfo.InfoType.plugins));
                title.setText(options.getTitle());
                break;
            case homeMimetypes:
                panel.setWidget(new BrowserInfo(BrowserInfo.InfoType.mimeType));
                title.setText(options.getTitle());
                break;
            case homeMimePools:
                panel.setWidget(new BrowserInfo(BrowserInfo.InfoType.mimePool));
                title.setText(options.getTitle());
//                break;
//            case dynAuto:
//                c = DynaShowcase.instance;
//                break;
//            case dynVdAuto:
//                c = DynaVideoShowcase.instance;
//                break;
//            case miscBasic:
//            case miscFlash:
//            case ytubeBasic:
//            case ytubeChrome:
//                c = MiscShowcase.instance;
//                break;
//            case matrixBasic:
//                c = MatrixShowcase.instance;
//                break;
//            case divxVideo:
                init(options);
                break;
        }
    }

    @Override
    public void onShowControls(boolean show) {
        player.setControllerVisible(show);
    }

    @Override
    public void onShowLogger(boolean show) {
        player.showLogger(show);
    }

    @Override
    public void onResizeToVideoSize(boolean resize) {
        player.setResizeToVideoSize(resize);
    }
    @UiField
    SimplePanel panel;
    @UiField
    Label title;

    @UiTemplate("xml/PlayerPane.ui.xml")
    interface PlayerPaneUiBinder extends UiBinder<Widget, PlayerPane> {
    }
    private static PlayerPaneUiBinder uiBinder = GWT.create(PlayerPaneUiBinder.class);
}
