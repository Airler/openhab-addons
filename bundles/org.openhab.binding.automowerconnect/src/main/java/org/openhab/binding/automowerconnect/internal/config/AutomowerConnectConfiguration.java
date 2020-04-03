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

/**
 * The {@link AutomowerConnectConfiguration} class contains fields mapping thing configuration parameters.
 *
 * @author Torben Erler - Initial contribution
 */
public class AutomowerConnectConfiguration {

    /**
     * Sample configuration parameter. Replace with your own.
     */
    // public String config1;

    private String amcUsername;
    private String amcPassword;

    private int amcExpireStatus;

    private String amcDeviceId;

    /**
     * @param amcExpireStatus
     */
    public AutomowerConnectConfiguration() {
        this.amcExpireStatus = 30;
    }

    public String getAmcUsername() {
        return amcUsername;
    }

    public void setAmcUsername(String amcUsername) {
        this.amcUsername = amcUsername;
    }

    public String getAmcPassword() {
        return amcPassword;
    }

    public void setAmcPassword(String amcPassword) {
        this.amcPassword = amcPassword;
    }

    public String getAmcDeviceId() {
        return amcDeviceId;
    }

    public int getAmcExpireStatus() {
        return amcExpireStatus;
    }

    public void setAmcExpireStatus(int amcExpireStatus) {
        this.amcExpireStatus = amcExpireStatus;
    }

    public void setAmcDeviceId(String amcDeviceId) {
        this.amcDeviceId = amcDeviceId;
    }

}
