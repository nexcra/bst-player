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

import com.bramosystems.oss.player.core.client.spi.PlayerElement;
import java.util.HashMap;
import java.util.Iterator;

/**
 * IE specific native implementation of the PluginManagerImpl class. It is not recommended to
 * interact with this class directly.
 *
 * @author Sikirulai Braheem <sbraheem at bramosystems.com>
 */
public class APIWidgetProviderIE extends APIWidgetProvider {

    @Override
    protected PlayerElement getSWFElement(String playerId, String swfURL, HashMap<String, String> params) {
        PlayerElement xo = new PlayerElement(PlayerElement.Type.ObjectElementIE, playerId, "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000");
        xo.addParam("src", swfURL);

        Iterator<String> keys = params.keySet().iterator();
        while (keys.hasNext()) {
            String name = keys.next();
            xo.addParam(name, params.get(name));
        }
        return xo;
    }
}
