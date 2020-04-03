/**
 * Copyright (c) 2010-2020 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.automowerconnect.internal.config;

import org.joda.time.DateTime;

/**
 * The {@link AutomowerConnectToken} class ...
 *
 * @author Torben Erler - Initial contribution
 */
public class AutomowerConnectToken {

    private String amcToken;
    private String amcTokenProvider;
    private DateTime amcTokenExpiresOn;

    /**
     *
     */
    public AutomowerConnectToken() {
        this.amcToken = "";
        this.amcTokenProvider = "";
        this.amcTokenExpiresOn = new DateTime().withDate(1900, 1, 1);
    }

    public String getAmcToken() {
        return amcToken;
    }

    public void setAmcToken(String amcToken) {
        this.amcToken = amcToken;
    }

    public String getAmcTokenProvider() {
        return amcTokenProvider;
    }

    public void setAmcTokenProvider(String amcTokenProvider) {
        this.amcTokenProvider = amcTokenProvider;
    }

    public DateTime getAmcTokenExpiresOn() {
        return amcTokenExpiresOn;
    }

    public void setAmcTokenExpiresOn(DateTime amcTokenExpiresOn) {
        this.amcTokenExpiresOn = amcTokenExpiresOn;
    }

    public Boolean tokenValid() {
        return (!this.amcToken.isEmpty() && !this.amcTokenExpiresOn.isBeforeNow());
    }
}
